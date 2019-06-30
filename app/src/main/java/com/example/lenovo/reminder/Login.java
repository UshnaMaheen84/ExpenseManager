package com.example.lenovo.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.reminder.Services.LoanService;
import com.example.lenovo.reminder.Services.ReminderService;
import com.example.lenovo.reminder.dbHelper.DbHelper;

import java.util.Calendar;

public class Login extends AppCompatActivity {
    EditText number,pswrd;
    Button login;
    SharedPreferences.Editor preferences;
    DbHelper openhelper;
    SQLiteDatabase db;
    TextView signin,textView;
    String lognumber,logpassword;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        openhelper= new DbHelper(this);
        preferences= getSharedPreferences(Common.sharedPreferences,MODE_PRIVATE).edit();
        number= findViewById(R.id.phonenumber);
        pswrd= findViewById(R.id.password);
        login= findViewById(R.id.btnlog);
        signin= findViewById(R.id.signtv);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login.this,Signin.class);
                startActivity(intent);


            }
        });

        textView= findViewById(R.id.TV1);
        Typeface typeface= Typeface.createFromAsset(getAssets(),"fonts/PatchworkStitchlingsColor.ttf");
        textView.setTypeface(typeface);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lognumber=number.getText().toString();
                logpassword=pswrd.getText().toString();

                preferences.putString("Number",lognumber);
                preferences.putString("Password",logpassword);
                preferences.apply();

                boolean authentication = openhelper.getData(lognumber,logpassword);

                if (authentication) {

                    Toast.makeText(Login.this, "Login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);


                    preferences.putString("Number",lognumber);
                    preferences.putString("Password",logpassword);
                    preferences.apply();
                    Calendar calendar = Calendar.getInstance();

                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);

                    Intent intent3= new Intent(Login.this,MyAlarms.class);

                    PendingIntent pendingIntent=PendingIntent.getBroadcast(Login.this,1234,intent3,0);

                    AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);


                    finish();
                } else {
                    Toast.makeText(Login.this, "Enter valid Number and Password", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
