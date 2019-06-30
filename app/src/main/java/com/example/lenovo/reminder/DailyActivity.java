package com.example.lenovo.reminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.reminder.adapters.PendingAdapter;
import com.example.lenovo.reminder.dbHelper.DbHelper;
import com.example.lenovo.reminder.model.RecordModel;
import com.example.lenovo.reminder.model.UserModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DailyActivity extends AppCompatActivity {

    ArrayList<RecordModel> arrayList= new ArrayList<>();
    RecyclerView rView;
    PendingAdapter adapter;
    DbHelper db;
    TextView total, summeryTV;
    Toolbar toolbar;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd");
    public static String userNum = "";
    Integer UserWalletLimit, totalSum, summeryValue;
    String summery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        db = new DbHelper(this);
       String date = simpleDateFormat.format(Calendar.getInstance().getTime());

        userNum =getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).getString("Number", "0");


        arrayList.addAll(db.getdailyrecords(date));

        UserModel user = db.getUserWallet(userNum);
        UserWalletLimit = Integer.valueOf(user.getLimit());
        totalSum = (int) db.dailysum(date);

        if (totalSum > UserWalletLimit){
            summeryValue = totalSum-UserWalletLimit;
            summery="Warning !!! You have spent Rs. "+ summeryValue.toString() + " more from your Limit. Be careful for next time.";

        }
        if (totalSum.equals(UserWalletLimit)){
            summery="Good Job ! You have spend according to your Limit. Keep it up.";
        }
        if (totalSum < UserWalletLimit){
            summeryValue = UserWalletLimit - totalSum;
            summery="Great Job! You have save Rs. " + summeryValue.toString() + " from your Limit. Keep saving :) ";
        }

        total = findViewById(R.id.totalsum);
        summeryTV = findViewById(R.id.summary);
        rView = findViewById(R.id.rview2);
        rView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PendingAdapter(arrayList, this);
        rView.setAdapter(adapter);

        summeryTV.setText(summery);

        total.setText("Total : " + String.valueOf(db.dailysum(date))+" rps");

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}
