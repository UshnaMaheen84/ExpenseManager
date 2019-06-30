package com.example.lenovo.reminder.Fragments;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.reminder.Common;
import com.example.lenovo.reminder.R;
import com.example.lenovo.reminder.Records;
import com.example.lenovo.reminder.dbHelper.DbHelper;
import com.example.lenovo.reminder.model.RecordModel;
import com.example.lenovo.reminder.model.UserModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {

    EditText name, price,address;
    Button insert, show;
    TextView textView;
    DbHelper dbHelper;
    ArrayList<RecordModel> arrayList = new ArrayList<>();
    Toolbar toolbar;
    ImageView logout, wallet;
    public static String userNumber = "";
    public static String date ;
    Integer walletAmount, itemPrice;

    SharedPreferences.Editor preferences;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd");
    SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-M");
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat simpleDate = new SimpleDateFormat("dd");
    SimpleDateFormat simplemonth = new SimpleDateFormat("MMM");


    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_expense, null);

        dbHelper = new DbHelper(getActivity());
        preferences = getActivity().getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).edit();

        userNumber = getActivity().getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).getString("Number", "0");

        UserModel user = dbHelper.getUserWallet(userNumber);
        final Integer UserWallet = Integer.valueOf(user.getWallet());
        final Integer UserWalletLimit = Integer.valueOf(user.getLimit());

        name = view.findViewById(R.id.inputname);
        price =view.findViewById(R.id.inputprice);
        address = view.findViewById(R.id.inputAddresss);
        insert = view.findViewById(R.id.insert);
        show = view.findViewById(R.id.button1);


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namee = name.getText().toString();
                String pricee = price.getText().toString();
                String address1 = address.getText().toString();

                if (!TextUtils.isEmpty(namee) && !TextUtils.isEmpty(pricee)) {

                    date = simpleDateFormat.format(Calendar.getInstance().getTime());

                    itemPrice = Integer.valueOf(pricee);
                    walletAmount = UserWallet-itemPrice;

                    Log.e("dates", date);

                    long id = dbHelper.insertRecordData(namee, pricee,
                            simpleDateFormat.format(Calendar.getInstance().getTime()),
                            monthFormat.format(Calendar.getInstance().getTime()),
                            yearFormat.format(Calendar.getInstance().getTime()),
                            simpleDate.format(Calendar.getInstance().getTime()),
                            simplemonth.format(Calendar.getInstance().getTime()),
                            address1);

                    dbHelper.updateWallet(walletAmount.toString());

                    int totalSum = (int) dbHelper.dailysum(date);

                    if (totalSum > UserWalletLimit){
                        showNotification();
                    }

                    name.setText("");
                    price.setText("");
                    address.setText("");
                    Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();


                }
                else {
                    Toast.makeText(getActivity(), "something missing", Toast.LENGTH_SHORT).show();
                }
            }

        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Records.class);
                intent.putExtra("current date",date);
                startActivity(intent);

            }
        });




        return view;
    }

    private void showNotification() {


        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(
                Context.NOTIFICATION_SERVICE);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String CHANNEL_ID = "my_channel_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,"XYZ", importance
            );
            notificationManager.createNotificationChannel(channel);

        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("My Reminder")
                .setContentText("Warning !!! You have spent more then your Daily Limit.")
                .setSound(alarmSound)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000});



        notificationManager.notify(0000, builder.build());

    }

}
