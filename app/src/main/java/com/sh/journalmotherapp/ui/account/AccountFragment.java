package com.sh.journalmotherapp.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.database.MySharedPreferences;
import com.sh.journalmotherapp.model.UserEntity;
import com.sh.journalmotherapp.ui.authentication.LoginActivity;
import com.sh.journalmotherapp.util.Const;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private View root;

    private CircleImageView imvImage;
    private TextView tvNameUser, tvBirthDayUser;
    private TextView tvKernelStatus, tvVotingStatus, tvNumberBaby;
    private TextView tvUsernameUser, tvFullNameUser, tvBirthDay, tvAddressUser, tvPhoneUser;
    private TextView tvLogOut;

    private MySharedPreferences preferences;
    private UserEntity userLogin;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_detail_user, container, false);
        initView();
        initData();
        return root;
    }

    private void initData() {
        preferences = new MySharedPreferences(requireContext());
        userLogin = preferences.getUserLogin(Const.KEY_SHARE_PREFERENCE.USER_LOGIN);

        tvNameUser.setText(userLogin.getFullName());
        tvBirthDayUser.setText(userLogin.getYearOfBirth() + "");


        tvKernelStatus.setText(userLogin.getKernelStatus());
        tvVotingStatus.setText(userLogin.getVotingStatus());
        tvNumberBaby.setText(userLogin.getNumberBaby());

        tvPhoneUser.setText(userLogin.getMobile());
        tvBirthDay.setText(userLogin.getYearOfBirth() + "");
        tvAddressUser.setText(userLogin.getAddress());
        tvFullNameUser.setText(userLogin.getFullName());
        tvUsernameUser.setText(userLogin.getUsername());

        Picasso.get().load(userLogin.getImageUrl()).placeholder(R.drawable.ic_app_256)
                .error(R.drawable.ic_app_256).into(imvImage);
    }

    private void initView() {
        imvImage = root.findViewById(R.id.imvUser);
        tvBirthDay = root.findViewById(R.id.tvBirthDay);
        tvNameUser = root.findViewById(R.id.tvNameUser);
        tvPhoneUser = root.findViewById(R.id.tvPhoneUser);
        tvAddressUser = root.findViewById(R.id.tvAddressUser);
        tvBirthDayUser = root.findViewById(R.id.tvBirthDayUser);
        tvUsernameUser = root.findViewById(R.id.tvUsernameUser);
        tvFullNameUser = root.findViewById(R.id.tvFullNameUser);
        tvLogOut = root.findViewById(R.id.tvLogOut);
        tvKernelStatus = root.findViewById(R.id.tvKernelStatus);
        tvVotingStatus = root.findViewById(R.id.tvVotingStatus);
        tvNumberBaby = root.findViewById(R.id.tvNumberBaby);

        tvLogOut.setOnClickListener(this);
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

        Intent intent = new Intent(requireContext(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
}
