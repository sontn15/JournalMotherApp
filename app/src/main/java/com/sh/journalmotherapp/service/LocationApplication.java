package com.sh.journalmotherapp.service;

import static com.sh.journalmotherapp.util.Const.TOPIC_APP;

import android.app.Application;

import com.google.firebase.messaging.FirebaseMessaging;

public class LocationApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_APP);

    }
}
