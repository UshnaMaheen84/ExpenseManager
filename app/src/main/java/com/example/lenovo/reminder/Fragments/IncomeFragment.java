package com.example.lenovo.reminder.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.lenovo.reminder.IncomeRecord;
import com.example.lenovo.reminder.R;
import com.example.lenovo.reminder.dbHelper.DbHelper;
import com.example.lenovo.reminder.model.IncomeModel;
import com.example.lenovo.reminder.model.UserModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {
    EditText source, amount;
    Button insert, show;
    TextView textView;
    DbHelper dbHelper;
    ArrayList<IncomeModel> arrayList = new ArrayList<>();
    Toolbar toolbar;
    ImageView logout, wallet;
    public static String userNumber = "";
    public static String number,date ;
    Integer walletAmount, income;

    SharedPreferences.Editor preferences;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd");
    SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-M");
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat simpleDate = new SimpleDateFormat("dd");
    SimpleDateFormat simplemonth = new SimpleDateFormat("MMM");


    public IncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_income, null);

        dbHelper = new DbHelper(getActivity());
        preferences = getActivity().getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).edit();

        userNumber = getActivity().getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).getString("Number", "0");

        UserModel user = dbHelper.getUserWallet(userNumber);
        final Integer UserWallet = Integer.valueOf(user.getWallet());

        Log.e("UserNumber", userNumber);
        source = view.findViewById(R.id.income_source);
        amount =view.findViewById(R.id.income_amount);
        insert = view.findViewById(R.id.insertincome);
        show = view.findViewById(R.id.income_records);


//        logout= view.findViewById(R.id.logoutincome);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setMessage("Are you sure you want to logout?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                Intent intent=new Intent(getActivity() ,Login.class);
//                                preferences.clear();
//                                startActivity(intent);
//                                Intent intent1= new Intent(getActivity(),ReminderService.class);
//                                getActivity().stopService(intent1);
//
//                                Intent intent2= new Intent(getActivity(),LoanService.class);
//                                getActivity().stopService(intent2);
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog alert = builder.create();
//                alert.show();
//
//
//            }
//        });
//        wallet = view.findViewById(R.id.wallet);
//        wallet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity() ,User_Info.class);
//                startActivity(intent);
//            }
//        });


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String incomesource = source.getText().toString();
                String incomeamount = amount.getText().toString();

                if (!TextUtils.isEmpty(incomesource) && !TextUtils.isEmpty(incomeamount)) {

                    date = simpleDateFormat.format(Calendar.getInstance().getTime());

                    Log.e("dates", date);


                    income = Integer.valueOf(incomeamount);
                    walletAmount = UserWallet + income;

                    long id=0;

                           id = dbHelper.insertIncomeData(incomesource, incomeamount,
                            simpleDateFormat.format(Calendar.getInstance().getTime()),
                            monthFormat.format(Calendar.getInstance().getTime()),
                            yearFormat.format(Calendar.getInstance().getTime()),
                            userNumber,
                            simpleDate.format(Calendar.getInstance().getTime()),
                            simplemonth.format(Calendar.getInstance().getTime())
                           );

                    dbHelper.updateWallet(walletAmount.toString());

                    source.setText("");
                    amount.setText("");

                    }
                else {
                    Toast.makeText(getActivity(), "something missing", Toast.LENGTH_SHORT).show();
                }
            }

        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), IncomeRecord.class);
                intent.putExtra("current date",date);
                startActivity(intent);

                }
        });



        return view;
    }

}
