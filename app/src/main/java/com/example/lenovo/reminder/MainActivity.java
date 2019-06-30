package com.example.lenovo.reminder;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.lenovo.reminder.Fragments.ExpenseFragment;
import com.example.lenovo.reminder.Fragments.IncomeFragment;
import com.example.lenovo.reminder.Fragments.LoanFragment;
import com.example.lenovo.reminder.Services.LoanService;
import com.example.lenovo.reminder.Services.ReminderService;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static String userNum = "";
    Toolbar toolbar;
    TextView textView, cancel;
    SharedPreferences.Editor preferences;
    Button YesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        preferences = getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).edit();

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_account_balance_wallet_white_24dp);

        userNum = getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).getString("Number", "0");
        Log.e(MainActivity.class.getSimpleName(), userNum);

        Intent intent2 = new Intent(MainActivity.this, LoanService.class);
        intent2.setAction("com.testApp.service.MY_SERVICE");

        Intent intent1 = new Intent(MainActivity.this, ReminderService.class);
        intent1.setAction("com.testApp.service.MY_SERVICE");
        startService(intent1);

        startService(intent2);
        loadFragment(new ExpenseFragment());
    }

    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.framelayout, fragment)
                    .commit();


            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {

            case R.id.navigation_record:
                fragment = new ExpenseFragment();
                break;

            case R.id.navigation_loan:
                fragment = new LoanFragment();
                break;

            case R.id.navigation_income:
                fragment = new IncomeFragment();
                break;
        }

        return loadFragment(fragment);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_wallet) {
            Intent intent=new Intent(this,User_Info.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_logout){
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View view = inflater.inflate(R.layout.alert_dialog_logout, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(view);
            final AlertDialog alert = builder.create();

            YesBtn = view.findViewById(R.id.YesBtn);
            YesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    preferences.clear();
                    preferences.apply();
                    Intent intent=new Intent(MainActivity.this ,Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });

            cancel=view.findViewById(R.id.cancel_action);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.cancel();
                }
            });

             alert.show();
        }

        return super.onOptionsItemSelected(item);
    }
}

