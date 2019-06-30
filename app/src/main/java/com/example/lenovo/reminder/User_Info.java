package com.example.lenovo.reminder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.reminder.dbHelper.DbHelper;
import com.example.lenovo.reminder.model.UserModel;

public class User_Info extends AppCompatActivity {

    ImageView userwallet, userlimit;
    EditText editWallet, editLimit;
    TextView walletTV, limitTV;
    Button done ;
    DbHelper db;
    Toolbar toolbar;
    String walletIntent;

    public static String userNum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__info);

        userNum =getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).getString("Number", "0");

        walletIntent= getIntent().getExtras().getString("walletIntent");

        db = new DbHelper(this);
        UserModel user = db.getUserWallet(userNum);
        String UserWallet = user.getWallet();
        String UserWalletLimit = user.getLimit();

        toolbar= findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (walletIntent.equals("walletIntent")){
                    Intent intent = new Intent(User_Info.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                finish();}
            }
        });

        walletTV = findViewById(R.id.mywallet);
        walletTV.setText("Rs. "+UserWallet);

        limitTV = findViewById(R.id.mywalletlimit);
        limitTV.setText("Rs. "+UserWalletLimit);

        userwallet = findViewById(R.id.user_wallet);

        userlimit = findViewById(R.id.user_walletlimit);

        done = findViewById(R.id.done);

        userwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View view = inflater.inflate(R.layout.alert_dialog_box, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(User_Info.this);
                alertDialogBuilder.setTitle("Add your Amount");
                alertDialogBuilder.setView(view);

                editWallet = (EditText) view.findViewById(R.id.editTextAD);

                alertDialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //getting the text and insert into the database
                        String newWallet = editWallet.getText().toString();
                        walletTV.setText("Rs. "+newWallet);
                        db.updateWallet(newWallet);



                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //dismiss the dialog
                        dialog.dismiss();
                    }
                });

                alertDialogBuilder.show();
            }

        });

        userlimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View view = inflater.inflate(R.layout.alert_dialog_box, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(User_Info.this);
                alertDialogBuilder.setTitle("Add your Limit Amount");
                alertDialogBuilder.setView(view);

                editLimit = (EditText) view.findViewById(R.id.editTextAD);

                alertDialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //getting the text and insert into the database
                        String newLimit = editLimit.getText().toString();
                        limitTV.setText("Rs. "+newLimit);
                        db.updateLimit(newLimit);


                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //dismiss the dialog
                        dialog.dismiss();
                    }
                });

                alertDialogBuilder.show();
            }

        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Info.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
