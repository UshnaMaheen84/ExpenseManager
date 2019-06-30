package com.example.lenovo.reminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.reminder.adapters.RecordAdapter;
import com.example.lenovo.reminder.dbHelper.DbHelper;
import com.example.lenovo.reminder.model.RecordModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Records extends AppCompatActivity {

    RecyclerView recyclerView;
    RecordAdapter adapter;
    ArrayList<RecordModel> arrayList = new ArrayList<>();
    DbHelper db;
    TextView total;
    Toolbar toolbar;
    ImageView filter;
    SharedPreferences.Editor preferences;
    AlertDialog alertDialog;
    String selectedMonth, abc;
    String selectedYear;
    int monthcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        db = new DbHelper(this);
        preferences = getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).edit();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        filter = findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View v = inflater.inflate(R.layout.alert_dialog, null);

                final Button date_btn, monthBtn, yearBtn;

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Records.this);
                alertDialogBuilder.setView(v);


                date_btn = v.findViewById(R.id.byDatebtn);
                monthBtn = v.findViewById(R.id.byMonthbtn);
                yearBtn = v.findViewById(R.id.byYearbtn);


                date_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();
                        showDialog(999);

                    }
                });

                monthBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");

                        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                        View v2 = inflater.inflate(R.layout.month_dialog, null);

                        final Spinner smonth, syear;
                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Records.this);
                        alertDialogBuilder.setView(v2);

                        smonth = v2.findViewById(R.id.month);
                        syear = v2.findViewById(R.id.year);

                        final ArrayAdapter<String> monthadapter = new ArrayAdapter<String>(Records.this,
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
                        final ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<>(Records.this,
                                android.R.layout.simple_spinner_item, year);
                        syear.setAdapter(dataAdapter);

                        alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!smonth.getSelectedItem().toString().equalsIgnoreCase("Choose a month")) {
                                    Toast.makeText(Records.this,
                                            smonth.getSelectedItem().toString(),
                                            Toast.LENGTH_SHORT)
                                            .show();
                                }
                                if (!syear.getSelectedItem().toString().equalsIgnoreCase("Choose a year")) {
                                    Toast.makeText(Records.this,
                                            syear.getSelectedItem().toString(),
                                            Toast.LENGTH_SHORT)
                                            .show();


                                }
                                String monthly = selectedYear + "-" + monthcount;

                                ArrayList<RecordModel> filterarray = db.byMonth(monthly);
                                dialogInterface.dismiss();

                                Log.e("month", selectedMonth);
                                Log.e("year", selectedYear);

                                arrayList.clear();
                                arrayList.addAll(filterarray);
                                adapter.notifyDataSetChanged();
                                total.setText("Total : " + String.valueOf(db.totalsumofMonth(monthly)));
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

                yearBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();

                        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                        View v = inflater.inflate(R.layout.year_dialog, null);

                        final Spinner spinneryear;
                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Records.this);
                        alertDialogBuilder.setView(v);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");

                        spinneryear = v.findViewById(R.id.yearly);
                        int currentYear = Integer.parseInt(simpleDateFormat.format(Calendar.getInstance().getTime()));
                        List<Integer> year = new ArrayList<Integer>();

                        for (int i = currentYear; i >= 2010; i--) {
                            year.add(i);
                        }
                        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(Records.this,
                                android.R.layout.simple_spinner_item, year);
                        spinneryear.setAdapter(dataAdapter);
                        spinneryear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                selectedYear = adapterView.getSelectedItem().toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!spinneryear.getSelectedItem().toString().equalsIgnoreCase("Choose a year")) {
                                    Toast.makeText(Records.this,
                                            spinneryear.getSelectedItem().toString(),
                                            Toast.LENGTH_SHORT)
                                            .show();


                                }

                                ArrayList<RecordModel> filterarray = db.byYear(selectedYear);
                                dialogInterface.dismiss();

                                arrayList.clear();
                                arrayList.addAll(filterarray);
                                total.setText("Total : " + String.valueOf(db.totalsumofYear(selectedYear)));
                                adapter.notifyDataSetChanged();

                            }
                        });
                        alertDialogBuilder.setView(v);
                        AlertDialog dialog = alertDialogBuilder.create();
                        dialog.show();


                    }
                });

                alertDialog = alertDialogBuilder.show();


            }
        });
        total = findViewById(R.id.total);
        recyclerView = findViewById(R.id.rview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecordAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);


        arrayList.addAll(db.getallrecords());

        total.setText("Total : " + String.valueOf(db.totalsum())+" rps");


    }


    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int arg1, int arg2, int arg3) {
            int month = arg2 + 1;
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            if (arg3 < 10) {
                String day = "0" + arg3;
                abc = arg1 + "-" + month + "-" + day;

            } else {
                abc = arg1 + "-" + month + "-" + arg3;

            }

            Log.e("abccc", abc);
            preferences.putString("Date", abc);
            preferences.apply();

            ArrayList<RecordModel> filteredList = db.byDate(abc);
            //clear previous list and add new list to it then call notifydatasetchanged

            arrayList.clear();
            total.setText("Total: " + String.valueOf(db.totalsumofDate(abc)));

            arrayList.addAll(filteredList);
            adapter.notifyDataSetChanged();


            //

        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        }
        return null;

    }
}
