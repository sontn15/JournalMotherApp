package com.sh.journalmotherapp.ui.support;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.database.MySharedPreferences;
import com.sh.journalmotherapp.model.PostModel;
import com.sh.journalmotherapp.model.UserModel;
import com.sh.journalmotherapp.util.CommonUtil;
import com.sh.journalmotherapp.util.Const;

import java.io.IOException;
import java.util.Objects;

public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private EditText titleEditText, descriptionEditText;
    private Button btnCancel, btnPost;

    MySharedPreferences preferences;
    UserModel userLogin;

    Uri FilePathUri;
    int Image_Request_Code = 8;

    ProgressDialog progressDialog;

    StorageReference storageReference;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đăng bài");
        initData();
        initView();
        clearData();
    }

    private void initData() {
        preferences = new MySharedPreferences(this);
        userLogin = preferences.getUserLogin(Const.KEY_SHARE_PREFERENCE.USER_LOGIN);

        // Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();

        // Assign FirebaseDatabase instance with root database name.
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void initView() {
        imageView = this.findViewById(R.id.imageView);
        titleEditText = this.findViewById(R.id.titleEditText);
        descriptionEditText = this.findViewById(R.id.descriptionEditText);

        btnPost = this.findViewById(R.id.btnPost);
        btnCancel = this.findViewById(R.id.btnCancel);

        progressDialog = new ProgressDialog(CreatePostActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);

        btnPost.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        imageView.setOnClickListener(this);
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
            progressDialog.setTitle("Đang đăng bài...");
            progressDialog.show();

            String imageName = userLogin.getUsername() + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri);

            StorageReference storageReference2nd = storageReference.child(imageName);
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        storageReference2nd.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();

                            String id = CommonUtil.generateUUID();
                            String title = titleEditText.getText().toString();
                            String createdDate = CommonUtil.getCurrentDateStr();
                            String content = descriptionEditText.getText().toString();

                            PostModel model = new PostModel();
                            model.setId(id);
                            model.setTitle(title);
                            model.setContent(content);
                            model.setAuthor(userLogin);
                            model.setImageUrl(imageUrl);
                            model.setCreatedDate(createdDate);

                            databaseReference.child(Const.FirebaseRef.POSTS)
                                    .child(id)
                                    .setValue(model);

                            progressDialog.dismiss();
                            Toast.makeText(CreatePostActivity.this, "Đăng bài thành công", Toast.LENGTH_SHORT).show();

                            onBackPressed();
                        });
                    })
                    .addOnFailureListener(exception -> {
                        progressDialog.dismiss();
                        Toast.makeText(CreatePostActivity.this, "Đăng bài không thành công", Toast.LENGTH_SHORT).show();
                    })

                    // On progress change upload time.
                    .addOnProgressListener(taskSnapshot -> progressDialog.setTitle("Đang đăng bài..."));
        } else {
            Toast.makeText(CreatePostActivity.this, "Vui lòng chọn ảnh trước khi đăng", Toast.LENGTH_SHORT).show();
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
        titleEditText.setText("");
        descriptionEditText.setText("");
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumbnail));
    }

}
