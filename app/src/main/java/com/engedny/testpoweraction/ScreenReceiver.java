package com.engedny.testpoweraction;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.app.NotificationCompat;

import static com.engedny.testpoweraction.App.CHANNEL_ID;

public class ScreenReceiver extends BroadcastReceiver {

    public static boolean wasScreenOn = true;
    public static int Counter = 0;
    NotificationChannel mChannel;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.d("rashad", "on receive ");

        Log.e("LOB","onReceive");
        if (Counter>=4){
            Counter=0;
            Log.d("Aniss", "Hi Man  ");

            sendNotification(context);

        } if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Counter++;
            // do whatever you need to do here
            wasScreenOn = false;
            Log.e("LOB","wasScreenOn"+wasScreenOn);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Counter++;
            // and do whatever you need to do here
            wasScreenOn = true;
        }
    }


    public void sendNotification(Context context) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, "Notification Channel", NotificationManager.IMPORTANCE_HIGH);
            mBuilder.setChannelId(CHANNEL_ID);
        }
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannel(mChannel);
        }

        mNotificationManager.notify(1, mBuilder.build());


    }


}
