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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.model.UserEntity;
import com.sh.journalmotherapp.network.ApiService;
import com.sh.journalmotherapp.network.RetrofitClient;
import com.sh.journalmotherapp.network.request.RegisterRequest;
import com.sh.journalmotherapp.util.NetworkUtils;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegister;
    private CircleImageView imvImage;
    private ProgressDialog progressDialog;
    private Spinner spnKernelStatus, spnVotingStatus, spnNumberBaby;
    private EditText edtName, edtAddress, edtPhone, edtUsername, edtPassword, edtBirthDay, edtEmail;

    // Creating URI.
    private Uri FilePathUri;

    private StorageReference storageReference;

    // Image request code for onActivityResult() .
    private final int Image_Request_Code = 9;

    private ApiService apiService;

    private final String arrKernelStatus[] = {"Single", "Married", "Separated", "Divorced"};
    private final String arrNumberBabies[] = {"1 baby", "2 babies", "3 babies", "4 babies", "5 babies", "5 babies more"};
    private final String arrVotingStatus[] = {"First trimester – conception to 12 weeks", "Second trimester – 12 to 24 weeks", "Third trimester – 24 to 40 weeks",
            "Postpartum period (6 weeks after birth)", "3 to 6 months after birth", "New normal mom (6 to 18 months of postpartum)"};

    private String kernelStatus;
    private String votingStatus;
    private String numberBaby;

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
        apiService = RetrofitClient.getClient().create(ApiService.class);
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
        edtBirthDay = this.findViewById(R.id.edtYearOfBirthRegister);
        btnRegister = this.findViewById(R.id.btnRegister);
        imvImage = this.findViewById(R.id.imvImage);
        edtEmail = this.findViewById(R.id.edtEmailRegister);

        spnKernelStatus = this.findViewById(R.id.spnKernelStatus);
        spnVotingStatus = this.findViewById(R.id.spnVotingStatus);
        spnNumberBaby = this.findViewById(R.id.spnNumberBaby);

        ArrayAdapter<String> kernelStatusAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, arrKernelStatus);
        ArrayAdapter<String> votingStatusAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, arrVotingStatus);
        ArrayAdapter<String> numberBabiesAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, arrNumberBabies);

        spnKernelStatus.setAdapter(kernelStatusAdapter);
        spnVotingStatus.setAdapter(votingStatusAdapter);
        spnNumberBaby.setAdapter(numberBabiesAdapter);


        imvImage.setOnClickListener(this);
        btnRegister.setOnClickListener(this);


        spnKernelStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kernelStatus = arrKernelStatus[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnVotingStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                votingStatus = arrVotingStatus[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnNumberBaby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numberBaby = arrNumberBabies[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void clearData() {
        FilePathUri = null;
        edtName.setText("");
        edtPhone.setText("");
        edtAddress.setText("");
        edtUsername.setText("");
        edtPassword.setText("");
        edtBirthDay.setText("");
        edtEmail.setText("");
        imvImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_stub));

        spnKernelStatus.setSelection(0);
        spnVotingStatus.setSelection(0);
        spnNumberBaby.setSelection(0);
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
        String email = edtEmail.getText().toString();

        if (name.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() || username.isEmpty() || password.isEmpty() || FilePathUri == null || email.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.vui_long_nhap_day_du_thong_tin), Toast.LENGTH_SHORT).show();
            return;
        }

        if (NetworkUtils.haveNetwork(RegisterActivity.this)) {
            showProgressDialog();

            String imageName = username.trim() + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri);

            StorageReference storageReference2nd = storageReference.child(imageName);
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        storageReference2nd.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();

                            RegisterRequest registerRequest = RegisterRequest.builder()
                                    .username(username)
                                    .password(password)
                                    .fullName(name)
                                    .mobile(phoneNumber)
                                    .address(address)
                                    .email(email)
                                    .imageUrl(imageUrl)
                                    .yearOfBirth(Integer.valueOf(birthDay))
                                    .kernelStatus(kernelStatus)
                                    .votingStatus(votingStatus)
                                    .numberBaby(numberBaby)
                                    .build();

                            Call<UserEntity> callRegister = apiService.register(registerRequest);
                            callRegister.enqueue(new Callback<UserEntity>() {
                                @Override
                                public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        hiddenProgressDialog();

                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.dang_ky_thanh_cong), Toast.LENGTH_SHORT).show();

                                        Intent mIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(mIntent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.dang_ky_thai_bai), Toast.LENGTH_SHORT).show();
                                    }
                                    hiddenProgressDialog();
                                }

                                @Override
                                public void onFailure(Call<UserEntity> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.dang_ky_thai_bai), Toast.LENGTH_SHORT).show();
                                    hiddenProgressDialog();
                                }
                            });

                        });
                    })
                    .addOnFailureListener(exception -> {
                        hiddenProgressDialog();
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.dang_ky_thai_bai), Toast.LENGTH_SHORT).show();
                    })

                    // On progress change upload time.
                    .addOnProgressListener(taskSnapshot -> progressDialog.setTitle(getResources().getString(R.string.vui_long_doi) + "..."));
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
