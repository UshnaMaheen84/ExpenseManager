package com.example.lenovo.reminder;



import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.reminder.adapters.IncomeAdapter;
import com.example.lenovo.reminder.dbHelper.DbHelper;
import com.example.lenovo.reminder.model.IncomeModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class IncomeRecord extends AppCompatActivity {

    ArrayList<IncomeModel> arrayList= new ArrayList<>();
    RecyclerView rView;
    IncomeAdapter adapter;
    DbHelper db;
    TextView total;
    ImageView imageView;
    String selectedMonth, abc;
    String selectedYear;
    int monthcount;
    AlertDialog alertDialog;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_record);

        db = new DbHelper(this);

        arrayList.addAll(db.getIncomerecords());


        total= findViewById(R.id.totalincome);
        total.setText("Total : " + String.valueOf(db.totalincomesum())+" rps");

        rView = findViewById(R.id.income_rview);
        rView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IncomeAdapter(arrayList, this);
        rView.setAdapter(adapter);

        toolbar= findViewById(R.id.toolbarincme);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageView= findViewById(R.id.filter_incm);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");

                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View v2 = inflater.inflate(R.layout.month_dialog, null);

                final Spinner smonth, syear;
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IncomeRecord.this);
                alertDialogBuilder.setView(v2);

                smonth = v2.findViewById(R.id.month);
                syear = v2.findViewById(R.id.year);

                final ArrayAdapter<String> monthadapter = new ArrayAdapter<String>(IncomeRecord.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.SelectMonth));
                monthadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                smonth.setAdapter(monthadapter);
                smonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedMonth = adapterView.getItemAtPosition(i).toString();
                        monthcount = i;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                        ;
                    }
                });
                syear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedYear = adapterView.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                int currentYear = Integer.parseInt(simpleDateFormat.format(Calendar.getInstance().getTime()));
                List<Integer> year = new ArrayList<>();

                for (int i = currentYear; i >= 2010; i--) {
                    year.add(i);
                }
                final ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<>(IncomeRecord.this,
                        android.R.layout.simple_spinner_item, year);
                syear.setAdapter(dataAdapter);

                alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!smonth.getSelectedItem().toString().equalsIgnoreCase("Choose a month")) {
                            Toast.makeText(IncomeRecord.this,
                                    smonth.getSelectedItem().toString(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                        if (!syear.getSelectedItem().toString().equalsIgnoreCase("Choose a year")) {
                            Toast.makeText(IncomeRecord.this,
                                    syear.getSelectedItem().toString(),
                                    Toast.LENGTH_SHORT)
                                    .show();


                        }
                        String monthly = selectedYear + "-" + monthcount;

                        ArrayList<IncomeModel> filterarray = db.incomebyMonth(monthly);


                        dialogInterface.dismiss();

                        Log.e("month", selectedMonth);
                        Log.e("year", selectedYear);

                        arrayList.clear();
                        arrayList.addAll(filterarray);
                        adapter.notifyDataSetChanged();
                        total.setText("Total : " + String.valueOf(db.totalincomeofMonth(monthly)));
                        adapter.notifyDataSetChanged();

                    }
                });


                alertDialogBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                alertDialogBuilder.setView(v2);

                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();


            }
        });
    }
}
