package com.sh.journalmotherapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.sh.journalmotherapp.R;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int index = intent.getExtras().getInt("index");
        String message = intent.getExtras().getString("message");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "MomMomNotification")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("MomMom")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_app_256))
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(index, builder.build());
    }
}
