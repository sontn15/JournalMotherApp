package com.sh.journalmotherapp.ui.memory;

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
import com.sh.journalmotherapp.model.MemoryModel;
import com.sh.journalmotherapp.model.UserModel;
import com.sh.journalmotherapp.util.CommonUtil;
import com.sh.journalmotherapp.util.Const;

import java.io.IOException;
import java.util.Objects;

public class AddMemoryActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ImageView imv_memory;
    EditText edt_felling, edt_content;
    Button btn_Cancel, btn_Save;

    // Creating URI.
    Uri FilePathUri;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;

    ProgressDialog progressDialog;

    MySharedPreferences preferences;
    UserModel userLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_memory);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Post a memory");

        initViews();
        initData();
        clearData();
    }

    private void initViews() {
        imv_memory = this.findViewById(R.id.imageView_memory);
        edt_felling = this.findViewById(R.id.fellingEditText);
        edt_content = this.findViewById(R.id.contentEditText);
        btn_Cancel = this.findViewById(R.id.btnCancel_memory);
        btn_Save = this.findViewById(R.id.btnSave_memory);

        progressDialog = new ProgressDialog(AddMemoryActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);

        btn_Save.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);
        imv_memory.setOnClickListener(this);
    }

    private void initData() {
        preferences = new MySharedPreferences(this);
        userLogin = preferences.getUserLogin(Const.KEY_SHARE_PREFERENCE.USER_LOGIN);

        // Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();

        // Assign FirebaseDatabase instance with root database name.
        databaseReference = FirebaseDatabase.getInstance().getReference();
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
            case R.id.imageView_memory:
                onClickPickImageMemory();
                break;
            case R.id.btnCancel_memory:
                onClickCancelPostMemory();
                break;
            case R.id.btnSave_memory:
                onClickSaveMemory();
                break;
        }
    }

    private void onClickSaveMemory() {
        if (FilePathUri != null) {
            progressDialog.setTitle(getResources().getString(R.string.dang_dang_bai) + "...");
            progressDialog.show();

            String imageName = userLogin.getUsername() + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri);

            StorageReference storageReference2nd = storageReference.child(imageName);
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        storageReference2nd.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();

                            String id = CommonUtil.generateUUID();
                            String content = edt_content.getText().toString();
                            String felling = edt_felling.getText().toString();
                            String createdDate = CommonUtil.getCurrentDateStr();

                            MemoryModel memoryModel = new MemoryModel();
                            memoryModel.setId(id);
                            memoryModel.setEmotion(felling);
                            memoryModel.setContent(content);
                            memoryModel.setUserModel(userLogin);
                            memoryModel.setCreatedDate(createdDate);
                            memoryModel.setImageUrl(imageUrl);

                            databaseReference.child(Const.FirebaseRef.MEMORIES)
                                    .child(userLogin.getUsername())
                                    .child(id)
                                    .setValue(memoryModel);

                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.create_memory_success), Toast.LENGTH_SHORT).show();

                            onBackPressed();
                        });
                    })
                    .addOnFailureListener(exception -> {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.create_memory_failed), Toast.LENGTH_SHORT).show();
                    })

                    // On progress change upload time.
                    .addOnProgressListener(taskSnapshot -> progressDialog.setTitle(getResources().getString(R.string.dang_dang_bai) + "..."));
        } else {
            Toast.makeText(AddMemoryActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickCancelPostMemory() {
        onBackPressed();
    }

    private void onClickPickImageMemory() {
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
                imv_memory.setImageBitmap(bitmap);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        edt_felling.setText(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void clearData() {
        FilePathUri = null;
        edt_felling.setText("");
        edt_content.setText("");
        imv_memory.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumbnail));
    }

}
