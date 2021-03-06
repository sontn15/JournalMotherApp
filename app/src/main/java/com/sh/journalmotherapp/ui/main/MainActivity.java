package com.sh.journalmotherapp.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.database.MySharedPreferences;
import com.sh.journalmotherapp.model.UserEntity;
import com.sh.journalmotherapp.util.Const;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = this.findViewById(R.id.drawer_layout);
        NavigationView navigationView = this.findViewById(R.id.nav_view);

        MySharedPreferences preferences = new MySharedPreferences(this);
        UserEntity userLogin = preferences.getUserLogin(Const.KEY_SHARE_PREFERENCE.USER_LOGIN);

        View hView = navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.nav_name);
        nav_user.setText(userLogin.getFullName());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_the_journal, R.id.nav_ask_for_help, R.id.nav_news, R.id.nav_account)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}