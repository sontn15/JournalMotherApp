package com.sh.journalmotherapp.ui.memory;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.database.MySharedPreferences;
import com.sh.journalmotherapp.model.ImageUploadInfo;
import com.sh.journalmotherapp.model.MemoryModel;
import com.sh.journalmotherapp.model.UserModel;
import com.sh.journalmotherapp.util.CommonUtil;
import com.sh.journalmotherapp.util.Const;

import java.io.IOException;
import java.util.Objects;

public class AddMemoryActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ImageView imv_memory;
    EditText edt_felling, edt_content;
    Spinner spn_feeling;
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
        initViews();
        initAdapter();
        initData();
    }

    private void initViews() {
        imv_memory = this.findViewById(R.id.imageView_memory);
        edt_felling = this.findViewById(R.id.fellingEditText);
        edt_content = this.findViewById(R.id.contentEditText);
        btn_Cancel = this.findViewById(R.id.btnCancel_memory);
        btn_Save = this.findViewById(R.id.btnSave_memory);
        spn_feeling = this.findViewById(R.id.spn_feeling);

        btn_Save.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);
        imv_memory.setOnClickListener(this);
        spn_feeling.setOnItemSelectedListener(this);
    }

    private void initAdapter() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.feelings));
        spn_feeling.setAdapter(spinnerArrayAdapter);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageContainer_memory:
                onClickPickImageMemory();
                break;
            case R.id.btnCancel_memory:

                break;
            case R.id.btnSave_memory:
                onClickSaveMemory();
                break;
        }
    }

    private void onClickSaveMemory() {
        UploadImageFileToFirebaseStorage();
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

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void UploadImageFileToFirebaseStorage() {
        if (FilePathUri != null) {
            progressDialog.setTitle("Đang đăng bài...");
            progressDialog.show();

            StorageReference storageReference2nd = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        String TempImageName = userLogin.getUsername() + System.currentTimeMillis();

                        @SuppressWarnings("VisibleForTests")
                        ImageUploadInfo imageUploadInfo = new ImageUploadInfo(TempImageName, Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl().toString());

                        String id = CommonUtil.generateUUID();
                        String content = edt_content.getText().toString();
                        String createdDate = CommonUtil.getCurrentDateStr();
                        String felling = edt_felling.getText().toString();

                        MemoryModel memoryModel = new MemoryModel();
                        memoryModel.setId(id);
                        memoryModel.setEmotion(felling);
                        memoryModel.setContent(content);
                        memoryModel.setUserModel(userLogin);
                        memoryModel.setCreatedDate(createdDate);
                        memoryModel.setImageUrl(imageUploadInfo.getImageURL());

                        databaseReference.child(Const.FirebaseRef.MEMORIES)
                                .child(userLogin.getUsername())
                                .child(id)
                                .setValue(memoryModel);



                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Đăng bài kỷ niệm thành công", Toast.LENGTH_LONG).show();
                    })
                    .addOnFailureListener(exception -> {
                        progressDialog.dismiss();
                        Toast.makeText(AddMemoryActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    })

                    // On progress change upload time.
                    .addOnProgressListener(taskSnapshot -> progressDialog.setTitle("Đang đăng bài..."));
        } else {
            Toast.makeText(AddMemoryActivity.this, "Vui lòng chọn ảnh trước khi đăng", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        edt_felling.setText(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
