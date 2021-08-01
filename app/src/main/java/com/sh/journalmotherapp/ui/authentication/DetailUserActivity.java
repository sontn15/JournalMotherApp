package com.sh.journalmotherapp.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.database.MySharedPreferences;
import com.sh.journalmotherapp.model.UserModel;
import com.sh.journalmotherapp.util.Const;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailUserActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView imvImage;
    private TextView tvNameUser, tvBirthDayUser;
    private TextView tvMarital, tvPregnant, tvHobbies;
    private TextView tvUsernameUser, tvFullNameUser, tvBirthDay, tvAddressUser, tvPhoneUser;
    private TextView tvLogOut;

    private UserModel userLogin;
    private MySharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Account Information");

        initView();
        initData();
    }

    private void initData() {
        preferences = new MySharedPreferences(this);
        userLogin = preferences.getUserLogin(Const.KEY_SHARE_PREFERENCE.USER_LOGIN);

        tvNameUser.setText(userLogin.getFullName());
        tvBirthDayUser.setText(userLogin.getBirthDay());

        tvMarital.setText(userLogin.getMaritalStatus());
        tvPregnant.setText(userLogin.getPregnantStatus());
        tvHobbies.setText(userLogin.getHobbies());

        tvPhoneUser.setText(userLogin.getMobile());
        tvBirthDay.setText(userLogin.getBirthDay());
        tvAddressUser.setText(userLogin.getAddress());
        tvFullNameUser.setText(userLogin.getFullName());
        tvUsernameUser.setText(userLogin.getUsername());

        Picasso.get().load(userLogin.getImageUrl()).placeholder(R.drawable.ic_app_256)
                .error(R.drawable.ic_app_256).into(imvImage);
    }

    private void initView() {
        imvImage = this.findViewById(R.id.imvUser);
        tvBirthDay = this.findViewById(R.id.tvBirthDay);
        tvNameUser = this.findViewById(R.id.tvNameUser);
        tvPhoneUser = this.findViewById(R.id.tvPhoneUser);
        tvAddressUser = this.findViewById(R.id.tvAddressUser);
        tvBirthDayUser = this.findViewById(R.id.tvBirthDayUser);
        tvUsernameUser = this.findViewById(R.id.tvUsernameUser);
        tvFullNameUser = this.findViewById(R.id.tvFullNameUser);
        tvMarital = this.findViewById(R.id.tvMaritalStatus);
        tvPregnant = this.findViewById(R.id.tvPregnantStatus);
        tvHobbies = this.findViewById(R.id.tvHobbies);
        tvLogOut = this.findViewById(R.id.tvLogOut);

        tvLogOut.setOnClickListener(this);
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
            case R.id.tvLogOut: {
                onClickLogOut();
                break;
            }
        }
    }

    private void onClickLogOut() {
        preferences.clearAllData();

        Intent intent = new Intent(DetailUserActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
