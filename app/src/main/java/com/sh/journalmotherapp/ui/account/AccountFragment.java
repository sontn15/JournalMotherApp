package com.sh.journalmotherapp.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.database.MySharedPreferences;
import com.sh.journalmotherapp.model.UserModel;
import com.sh.journalmotherapp.util.Const;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    private View root;

    private CircleImageView imvImage;
    private TextView tvNameUser, tvBirthDayUser;
    private TextView tvUsernameUser, tvFullNameUser, tvBirthDay, tvAddressUser, tvPhoneUser;

    private MySharedPreferences preferences;
    private UserModel userLogin;

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
        tvBirthDayUser.setText(userLogin.getBirthDay());

        tvPhoneUser.setText(userLogin.getMobile());
        tvBirthDay.setText(userLogin.getBirthDay());
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
    }
}
