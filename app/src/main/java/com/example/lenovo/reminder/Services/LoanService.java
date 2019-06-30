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

import com.example.lenovo.reminder.Broadcast;
import com.example.lenovo.reminder.R;
import com.example.lenovo.reminder.ReturndateActivity;
import com.example.lenovo.reminder.dbHelper.DbHelper;
import com.example.lenovo.reminder.model.LoanModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LoanService extends Service {

    Handler repeatableHandler;
    Runnable reRunner = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DbHelper dbHelper;
    ArrayList<LoanModel> arrayList = new ArrayList<>();
    String currentDateToBeMatched = "";
    String toBeMatchedTime = "11:30:00";
    String currentTimeToBeMatched = "";
    SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        dbHelper = new DbHelper(this);

        repeatableHandler = new Handler();
        ReturnDateNorification();

    }

    private void ReturnDateNorification() {


        repeatableHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reRunner = this;

                Log.e(LoanService.class.getSimpleName(), "SERVICE RUNNING");
                Date currentDate = Calendar.getInstance().getTime();
                currentDateToBeMatched = dateFormat.format(currentDate);
                arrayList.addAll(dbHelper.getreturnDate(currentDateToBeMatched));

                Log.e("CurrentDateToBeMatched", currentDateToBeMatched);
                //Log.e("dateToBeMatched", String.valueOf(arrayList.size()));

                Date currentTime = Calendar.getInstance().getTime();
                currentTimeToBeMatched = timeFormatter.format(currentTime);
                if (currentTimeToBeMatched.equals(toBeMatchedTime)) {

                    for (LoanModel loan : arrayList) {
                        Log.e("LoanId", loan.getLoan_id());
                        Log.e("LoanDate", loan.getReturnDate());
                        newNotification(loan);
                    }
                }
                repeatableHandler.postDelayed(reRunner, 1000);
            }

        }, 0);

    }

    private void newNotification(LoanModel loan) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(this, ReturndateActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String CHANNEL_ID = "my_channel_02";// The id of the channel.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        int notificationid = 123;

        Intent intent = new Intent(LoanService.this, Broadcast.class);
        intent.setAction("com.myaction.com");
        intent.putExtra("LoanId", loan.getLoan_id());
        intent.putExtra("notification_id", notificationid);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Return Loan")
                .setContentText(loan.getPerson() + " has " + loan.getType() + " " + loan.getAmount() + " Rs from you")
                .setSound(alarmSound)
                .setAutoCancel(true)
                .addAction(R.drawable.removeloan, "loan remove", pendingIntent)
                .setContentIntent(contentIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000});


        notificationManager.notify(notificationid, builder.build());

        //delete
//        dbHelper.deteteLoanRcord(loan.getLoan_id());
//        Log.e("loanid",""+loan.getLoan_id());
//        arrayList.remove(loan);


    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
