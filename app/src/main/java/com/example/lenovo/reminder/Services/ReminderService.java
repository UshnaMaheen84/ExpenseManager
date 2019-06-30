package com.example.lenovo.reminder.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.lenovo.reminder.DailyActivity;
import com.example.lenovo.reminder.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReminderService extends Service {

    String toBeMatchedTime = "23:30:00";
    String currentTimeToBeMatched = "";
    SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
    Handler repeatableHandler;
    Runnable reRunner = null;


    @Override
    public void onCreate() {
        super.onCreate();
        // startService()


        repeatableHandler = new Handler();

        trackTimeToShowNotification();
    }


    private void trackTimeToShowNotification() {

        repeatableHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reRunner = this;
                Date currentTime = Calendar.getInstance().getTime();
                currentTimeToBeMatched = timeFormatter.format(currentTime);
                Log.e("ExpenseNotifiy", toBeMatchedTime);
                Log.e("time",currentTimeToBeMatched);
                if (currentTimeToBeMatched.equals(toBeMatchedTime)) {
                    //Show Notification
                    Log.e("Success", "Matched");
                    showNotification();
                }

                repeatableHandler.postDelayed(reRunner, 1000);
            }
        }, 0);


    }


    private void showNotification() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(this, DailyActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String CHANNEL_ID = "my_channel_01";// The id of the channel.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Remainder Expense")
                .setContentText("Click Here To See Your Today Expense")
                .setSound(alarmSound)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                .setChannelId(CHANNEL_ID);


        notificationManager.notify(1, builder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
