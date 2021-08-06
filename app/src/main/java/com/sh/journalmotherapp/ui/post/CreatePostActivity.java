package com.sh.journalmotherapp.ui.post;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.database.MySharedPreferences;
import com.sh.journalmotherapp.model.UserEntity;
import com.sh.journalmotherapp.network.ApiService;
import com.sh.journalmotherapp.network.RetrofitClient;
import com.sh.journalmotherapp.network.request.PostRequest;
import com.sh.journalmotherapp.util.Const;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spnType, spnMode;
    private ImageView imageView;
    private Button btnCancel, btnPost;
    private RadioButton radioPublic, radioAnonymous;
    private EditText titleEditText, descriptionEditText;

    private UserEntity userLogin;
    private MySharedPreferences preferences;

    private Uri FilePathUri;
    private int Image_Request_Code = 8;

    private ProgressDialog progressDialog;

    private ApiService apiService;
    private StorageReference storageReference;

    private String typeSpinner;
    private String modeSpinner;
    private final String arrTypeSpinner[] = {"#memories", "#ask_for_help"};
    private final String arrModeSpinner[] = {"Public", "Private"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create a post");
        initData();
        initView();
        clearData();
    }

    private void initData() {
        preferences = new MySharedPreferences(this);
        userLogin = preferences.getUserLogin(Const.KEY_SHARE_PREFERENCE.USER_LOGIN);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    private void initView() {
        imageView = this.findViewById(R.id.imageView);
        titleEditText = this.findViewById(R.id.titleEditText);
        descriptionEditText = this.findViewById(R.id.descriptionEditText);

        btnPost = this.findViewById(R.id.btnPost);
        btnCancel = this.findViewById(R.id.btnCancel);

        radioPublic = this.findViewById(R.id.radioPublic);
        radioAnonymous = this.findViewById(R.id.radioAnonymous);

        spnType = this.findViewById(R.id.spnType);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, arrTypeSpinner);
        spnType.setAdapter(typeAdapter);

        spnMode = this.findViewById(R.id.spnMode);
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, arrModeSpinner);
        spnMode.setAdapter(modeAdapter);

        progressDialog = new ProgressDialog(CreatePostActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);

        btnPost.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        imageView.setOnClickListener(this);

        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeSpinner = arrTypeSpinner[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modeSpinner = arrModeSpinner[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView: {
                onClickPickImage();
                break;
            }
            case R.id.btnPost: {
                onCreatePost();
                break;
            }

            case R.id.btnCancel: {
                onBackPressed();
                break;
            }
        }
    }

    private void onCreatePost() {
        if (FilePathUri != null) {
            progressDialog.setTitle(getResources().getString(R.string.dang_dang_bai) + "...");
            progressDialog.show();

            String imageName = userLogin.getUsername() + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri);

            StorageReference storageReference2nd = storageReference.child(imageName);
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        storageReference2nd.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();

                            String title = titleEditText.getText().toString();
                            String content = descriptionEditText.getText().toString();

                            boolean isAnonymous = false;
                            if (radioAnonymous.isChecked()) {
                                isAnonymous = true;
                            }

                            PostRequest postRequest = new PostRequest();
                            postRequest.setTitle(title);
                            postRequest.setContent(content);
                            postRequest.setImageUrl(imageUrl);
                            postRequest.setMode(modeSpinner);
                            postRequest.setType(typeSpinner);
                            postRequest.setIsAnonymous(isAnonymous);
                            postRequest.setAuthorId(userLogin.getId());

                            Call<Void> callAddPost = apiService.addPost(postRequest);
                            callAddPost.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    progressDialog.dismiss();
                                    Toast.makeText(CreatePostActivity.this, getResources().getString(R.string.create_post_success), Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Toast.makeText(CreatePostActivity.this, getResources().getString(R.string.create_post_failed), Toast.LENGTH_SHORT).show();
                                }
                            });
                        });
                    })
                    .addOnFailureListener(exception -> {
                        progressDialog.dismiss();
                        Toast.makeText(CreatePostActivity.this, getResources().getString(R.string.create_post_failed), Toast.LENGTH_SHORT).show();
                    })

                    // On progress change upload time.
                    .addOnProgressListener(taskSnapshot -> progressDialog.setTitle(getResources().getString(R.string.dang_dang_bai) + "..."));
        } else {
            Toast.makeText(CreatePostActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickPickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void clearData() {
        FilePathUri = null;
        spnType.setSelection(0);
        spnMode.setSelection(0);
        titleEditText.setText("");
        radioPublic.setChecked(true);
        descriptionEditText.setText("");
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumbnail));
    }

}
