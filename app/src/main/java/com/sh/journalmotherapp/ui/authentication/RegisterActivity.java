package com.sh.journalmotherapp.ui.authentication;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.database.MySharedPreferences;
import com.sh.journalmotherapp.model.UserModel;
import com.sh.journalmotherapp.util.CommonUtil;
import com.sh.journalmotherapp.util.Const;
import com.sh.journalmotherapp.util.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegister;
    private CircleImageView imvImage;
    private ProgressDialog progressDialog;
    private EditText edtName, edtAddress, edtPhone, edtUsername, edtPassword, edtBirthDay;

    // Creating URI.
    private Uri FilePathUri;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    // Image request code for onActivityResult() .
    private final int Image_Request_Code = 9;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        initData();
        clearData();
    }

    private void initData() {
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void initViews() {
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.vui_long_doi) + "...");
        progressDialog.setCanceledOnTouchOutside(false);

        edtName = this.findViewById(R.id.edtFullNameRegister);
        edtAddress = this.findViewById(R.id.edtAddressRegister);
        edtPhone = this.findViewById(R.id.edtPhoneNumberRegister);
        edtUsername = this.findViewById(R.id.edtUsernameRegister);
        edtPassword = this.findViewById(R.id.edtPassRegister);
        edtBirthDay = this.findViewById(R.id.edtBirthdayRegister);
        btnRegister = this.findViewById(R.id.btnRegister);
        imvImage = this.findViewById(R.id.imvImage);

        imvImage.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void clearData() {
        FilePathUri = null;
        edtName.setText("");
        edtPhone.setText("");
        edtAddress.setText("");
        edtUsername.setText("");
        edtPassword.setText("");
        edtBirthDay.setText("");
        imvImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_stub));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imvImage:
                onClickPickImage();
                break;
            case R.id.btnRegister:
                onClickRegister();
                break;
        }
    }

    private void onClickRegister() {
        String name = edtName.getText().toString();
        String address = edtAddress.getText().toString();
        String phoneNumber = edtPhone.getText().toString();
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String birthDay = edtBirthDay.getText().toString();

        if (name.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (NetworkUtils.haveNetwork(RegisterActivity.this)) {
            showProgressDialog();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference.child(Const.FirebaseRef.USERS).orderByChild("username").equalTo(username);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Toast.makeText(RegisterActivity.this, getResources().getString(R.string.username_ton_tai), Toast.LENGTH_SHORT).show();
                    } else {
                        String imageName = username.trim() + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri);

                        StorageReference storageReference2nd = storageReference.child(imageName);
                        storageReference2nd.putFile(FilePathUri)
                                .addOnSuccessListener(taskSnapshot -> {
                                    storageReference2nd.getDownloadUrl().addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();

                                        String id = CommonUtil.generateUUID();

                                        UserModel userRegister = UserModel.builder()
                                                .id(id)
                                                .likesCount(0)
                                                .imageUrl(imageUrl)
                                                .fullName(name.trim())
                                                .address(address.trim())
                                                .password(password.trim())
                                                .mobile(phoneNumber.trim())
                                                .birthDay(birthDay.trim())
                                                .username(username.trim().toLowerCase())
                                                .build();

                                        databaseReference.child(Const.FirebaseRef.USERS).child(id).setValue(userRegister);

                                        hiddenProgressDialog();
                                        Toast.makeText(getApplicationContext(), "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();

                                        Intent mIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(mIntent);
                                        finish();
                                    });
                                })
                                .addOnFailureListener(exception -> {
                                    hiddenProgressDialog();
                                    Toast.makeText(getApplicationContext(), "Đăng ký tài khoản không thành công", Toast.LENGTH_SHORT).show();
                                })

                                // On progress change upload time.
                                .addOnProgressListener(taskSnapshot -> progressDialog.setTitle("Vui lòng đợi..."));
                    }
                    hiddenProgressDialog();
                }

                @Override
                public void onCancelled(@NotNull DatabaseError databaseError) {
                    hiddenProgressDialog();
                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.dang_ky_thai_bai), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.check_connection_network), Toast.LENGTH_SHORT).show();
            hiddenProgressDialog();
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
                imvImage.setImageBitmap(bitmap);
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


    private void showProgressDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hiddenProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
