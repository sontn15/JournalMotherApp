package com.sh.journalmotherapp.ui.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sh.journalmotherapp.MainActivity;
import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.database.MySharedPreferences;
import com.sh.journalmotherapp.model.UserModel;
import com.sh.journalmotherapp.util.Const;
import com.sh.journalmotherapp.util.NetworkUtils;

import org.jetbrains.annotations.NotNull;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin;
    private TextView tvRegister;
    private ProgressDialog progressDialog;
    private EditText edtUsername, edtPassword;

    private MySharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        checkUserInSharePreference();
    }

    private void checkUserInSharePreference() {
        if (preferences == null) {
            preferences = new MySharedPreferences(LoginActivity.this);
        }

        UserModel userModel = preferences.getUserLogin(Const.KEY_SHARE_PREFERENCE.USER_LOGIN);
        if (userModel != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void initView() {
        tvRegister = this.findViewById(R.id.tvRegisterLogin);
        edtUsername = this.findViewById(R.id.edtUsernameLogin);
        edtPassword = this.findViewById(R.id.edtPassLogin);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.vui_long_doi) + "...");
        progressDialog.setCanceledOnTouchOutside(false);

        btnLogin = this.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        btnLogin.setTransformationMethod(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvRegisterLogin: {
                Intent mIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(mIntent);
                break;
            }
            case R.id.btnLogin: {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.vui_long_nhap_day_du_thong_tin), Toast.LENGTH_SHORT).show();
                    return;
                }
                checkUserLogin(username, password);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
                break;
            }
        }
    }

    private void checkUserLogin(String username, String password) {
        if (NetworkUtils.haveNetwork(LoginActivity.this)) {
            showProgressDialog();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Const.FirebaseRef.USERS);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnap : snapshot.getChildren()) {
                        UserModel user = dataSnap.getValue(UserModel.class);
                        if (user != null) {
                            if (username.equalsIgnoreCase(user.getUsername()) && password.equalsIgnoreCase(user.getPassword())) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    }
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.taikhoan_matkhau_khong_dung), Toast.LENGTH_SHORT).show();
                    hiddenProgressDialog();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    hiddenProgressDialog();
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.dang_nhap_thai_bai), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.check_connection_network), Toast.LENGTH_SHORT).show();
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
