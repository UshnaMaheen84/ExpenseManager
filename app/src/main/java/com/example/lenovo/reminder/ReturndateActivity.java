package com.example.lenovo.reminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.reminder.adapters.ReturnLoanAdapter;
import com.example.lenovo.reminder.dbHelper.DbHelper;
import com.example.lenovo.reminder.model.LoanModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ReturndateActivity extends AppCompatActivity {

    ArrayList<LoanModel> arrayList= new ArrayList<>();
    RecyclerView rView;
    ReturnLoanAdapter adapter;
    DbHelper db;
    TextView total;
    Toolbar toolbar;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returndate);

        db = new DbHelper(this);

        String date = simpleDateFormat.format(Calendar.getInstance().getTime());

        arrayList.addAll(db.getReturnLoanrecords(date));

        rView = findViewById(R.id.rview_returndate);
        rView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReturnLoanAdapter(arrayList, this);
        rView.setAdapter(adapter);

        toolbar = findViewById(R.id.toolbar_returndate);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReturndateActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
