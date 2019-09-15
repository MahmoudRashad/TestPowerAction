package com.engedny.testpoweraction;

import android.app.IntentService;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.engedny.testpoweraction.App.CHANNEL_ID;


public class ExampleIntentService extends IntentService {
    private static final String TAG = "ExampleIntentService";

    private PowerManager.WakeLock wakeLock;

    public ExampleIntentService() {
        super("ExampleIntentService");
        setIntentRedelivery(false);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "ExampleApp:Wakelock");
        wakeLock.acquire();
        Log.d(TAG, "Wakelock acquired");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Example IntentService")
                    .setContentText("Running...")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .build();

            startForeground(1, notification);
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent");

        String input = intent.getStringExtra("inputExtra");

        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        final BroadcastReceiver mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
        Log.d("rashad", filter.toString() );

        int i =1;
        while (true){
            Log.d(TAG, input + " - " + i);
            SystemClock.sleep(100);
            if (!MainActivity.isruning )
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

        wakeLock.release();
        Log.d(TAG, "Wakelock released");
    }
}
