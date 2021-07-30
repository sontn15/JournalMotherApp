package com.sh.journalmotherapp.ui.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.model.UserModel;
import com.sh.journalmotherapp.util.CommonUtil;
import com.sh.journalmotherapp.util.Const;
import com.sh.journalmotherapp.util.NetworkUtils;

import org.jetbrains.annotations.NotNull;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegister;
    private ProgressDialog progressDialog;
    private EditText edtName, edtAddress, edtPhone, edtUsername, edtPassword, edtBirthDay;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        clearData();
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

        btnRegister.setOnClickListener(this);
    }

    private void clearData() {
        edtName.setText("");
        edtPhone.setText("");
        edtAddress.setText("");
        edtUsername.setText("");
        edtPassword.setText("");
        edtBirthDay.setText("");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegister) {
            onClickRegister();
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

        String id = CommonUtil.generateUUID();

        UserModel userRegister = UserModel.builder()
                .id(id)
                .likesCount(0)
                .fullName(name.trim())
                .address(address.trim())
                .password(password.trim())
                .mobile(phoneNumber.trim())
                .birthDay(birthDay.trim())
                .username(username.trim().toLowerCase())
                .imageUrl("https://firebasestorage.googleapis.com/v0/b/journalmotherapp.appspot.com/o/ic_app_512.png?alt=media&token=4b1c2535-1d28-488a-9b24-7570fb0c4f05")
                .build();

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
                        FirebaseDatabase.getInstance().getReference().child(Const.FirebaseRef.USERS).child(id).setValue(userRegister);

                        Intent mIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(mIntent);
                        finish();

                        Toast.makeText(RegisterActivity.this, getResources().getString(R.string.tao_tk_thanhcong), Toast.LENGTH_SHORT).show();
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
