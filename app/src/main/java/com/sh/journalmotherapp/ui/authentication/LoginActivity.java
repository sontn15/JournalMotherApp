package com.sh.journalmotherapp.ui.authentication;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
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
import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.database.MySharedPreferences;
import com.sh.journalmotherapp.model.ReminderModel;
import com.sh.journalmotherapp.model.UserModel;
import com.sh.journalmotherapp.service.NotificationReceiver;
import com.sh.journalmotherapp.ui.main.MainActivity;
import com.sh.journalmotherapp.util.Const;
import com.sh.journalmotherapp.util.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


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
        createNotificationChannel();
        checkUserInSharePreference();
    }

    private void checkUserInSharePreference() {
        if (preferences == null) {
            preferences = new MySharedPreferences(LoginActivity.this);
        }

        UserModel userModel = preferences.getUserLogin(Const.KEY_SHARE_PREFERENCE.USER_LOGIN);
        if (userModel != null) {
            createReminderNotification(userModel);

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
                    boolean isCheck = false;
                    for (DataSnapshot dataSnap : snapshot.getChildren()) {
                        UserModel user = dataSnap.getValue(UserModel.class);
                        if (user != null) {
                            if (username.equalsIgnoreCase(user.getUsername()) && password.equalsIgnoreCase(user.getPassword())) {
                                isCheck = true;
                                preferences.putUserLogin(Const.KEY_SHARE_PREFERENCE.USER_LOGIN, user);

                                createReminderNotification(user);

                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    }
                    if (!isCheck) {
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.taikhoan_matkhau_khong_dung), Toast.LENGTH_SHORT).show();
                    }
                    hiddenProgressDialog();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    hiddenProgressDialog();
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.taikhoan_matkhau_khong_dung), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.check_connection_network), Toast.LENGTH_SHORT).show();
            hiddenProgressDialog();
        }
    }

    private void createReminderNotification(UserModel userModel) {
        List<ReminderModel> reminderModels = new ArrayList<>();
        reminderModels.add(new ReminderModel("Good morning " + userModel.getUsername() + ", remember to start your day with a smile to make it bright.", 6, 0));
        reminderModels.add(new ReminderModel("Good morning " + userModel.getUsername() + ", a new day is a new page of your story! Make it boomed!", 6, 15));
        reminderModels.add(new ReminderModel("Wake up " + userModel.getUsername() + "... and think about what you can achieve today!", 6, 30));
        reminderModels.add(new ReminderModel("New day, new thought, new energy, new opportunities!", 6, 45));
        reminderModels.add(new ReminderModel("Have you had your breakfast? If not, please eat some good food for a good mood and good health.", 7, 0));
        reminderModels.add(new ReminderModel("Every day is a new beginning. Take a deep breath for your revolution!", 7, 30));
        reminderModels.add(new ReminderModel("Each morning you are born again! Therefore, what you do today matters most.", 8, 0));
        reminderModels.add(new ReminderModel("Look at the mirror! You are doing well!", 8, 30));
        reminderModels.add(new ReminderModel("How was your morning? Have you had a tasty lunch yet?", 10, 0));
        reminderModels.add(new ReminderModel("Take a nap, " + userModel.getUsername() + "! I know you have worked hard!", 11, 0));

        reminderModels.add(new ReminderModel("Mommy, let's play with me!", 13, 0));
        reminderModels.add(new ReminderModel("Mom, thank you for bringing me to this life!", 13, 15));
        reminderModels.add(new ReminderModel("Don’t worry, Mom! I will be your big support!", 14, 0));
        reminderModels.add(new ReminderModel("Do you want to have some snacks in the afternoon, Mom?", 14, 15));
        reminderModels.add(new ReminderModel("Everything is still okay, right, Mom?", 14, 30));
        reminderModels.add(new ReminderModel("Good afternoon " + userModel.getUsername() + ", your biggest motivation is right next to you.", 15, 0));
        reminderModels.add(new ReminderModel("Mom, no matter how hard you work, you need to have time for yourself. I don't want to see you tired…", 15, 15));
        reminderModels.add(new ReminderModel("Mommy, have a light exercise to alleviate negativity!", 15, 30));
        reminderModels.add(new ReminderModel("Mom, I am so blessed to have you in my life. You are my superpower!", 16, 0));
        reminderModels.add(new ReminderModel("Mama, you are more than enough! Just take a rest when you feel exhausted!", 16, 30));
        reminderModels.add(new ReminderModel("Mama, remember that you are the special gift in my life.", 17, 0));
        reminderModels.add(new ReminderModel("You are my Mommy! The greatest, sweetest, and warmest Mommy in the world.", 17, 15));
        reminderModels.add(new ReminderModel("Take a deep breath, Mommy!", 17, 25));
        reminderModels.add(new ReminderModel("Have you drunk enough water today, Mommy?", 17, 30));

        reminderModels.add(new ReminderModel("Mommy, I love eating dinner with you every night!", 18, 0));
        reminderModels.add(new ReminderModel("It's time to have a sweet dream in a beautiful sleep, Mommy!", 19, 0));
        reminderModels.add(new ReminderModel("Kiss me and hug me, Mama!", 19, 30));
        reminderModels.add(new ReminderModel("Have you done your skincare routine?", 20, 0));
        reminderModels.add(new ReminderModel("No matter what happens, you are always the best woman ever to me, Mom! I am proud of you!", 20, 15));
        reminderModels.add(new ReminderModel("Mama, I love you as always! Good night, Mom!", 21, 0));
        reminderModels.add(new ReminderModel("May serenity and peace be your companion tonight, Mom! Your baby sweetheart loves you the most!", 21, 5));
        reminderModels.add(new ReminderModel("Mom, I know that behind those soft and comforting hugs lies a woman whose heart is strong and soul is selfless.", 21, 10));
        reminderModels.add(new ReminderModel("Have a deep sleep to gain power for a fresh start!", 21, 15));
        reminderModels.add(new ReminderModel("Mom, I love you to the moon and back!", 21, 20));
        reminderModels.add(new ReminderModel("The day is over, night has come! What’s done is done! Don't bother!", 21, 25));
        reminderModels.add(new ReminderModel("It's time to go to bed, " + userModel.getUsername() + "! Have a sweet dream!", 21, 30));
        reminderModels.add(new ReminderModel("You worked hard, Mom! All you need is a good sleep.", 21, 35));
        reminderModels.add(new ReminderModel("Remember to end your day with a positive thought!", 21, 40));

        for (int i = 0; i < reminderModels.size(); i++) {
            ReminderModel reminderModel = reminderModels.get(i);
            sendNotificationToBroadcast(reminderModel, i);
        }

    }

    private void sendNotificationToBroadcast(ReminderModel reminderModel, int index) {
        Intent intent = new Intent(LoginActivity.this, NotificationReceiver.class);
        intent.putExtra("message", reminderModel.getMessage());
        intent.putExtra("index", index);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(LoginActivity.this, index, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, reminderModel.getHour());
        calendar.set(Calendar.MINUTE, reminderModel.getMinute());
        calendar.set(Calendar.SECOND, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "JournalMomMomChannel";
            String description = "Change for Notification Reminder";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("MomMomNotification", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
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
