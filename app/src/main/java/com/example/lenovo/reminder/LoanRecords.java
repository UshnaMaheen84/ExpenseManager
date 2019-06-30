package com.example.lenovo.reminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lenovo.reminder.adapters.LoanAdapter;
import com.example.lenovo.reminder.dbHelper.DbHelper;
import com.example.lenovo.reminder.model.LoanModel;

import java.util.ArrayList;

public class LoanRecords extends AppCompatActivity {

    ArrayList<LoanModel> arrayList= new ArrayList<>();
    RecyclerView rView;
    LoanAdapter adapter;
    DbHelper db;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_records);

        db = new DbHelper(this);

        arrayList.addAll(db.getloanrecords());


        rView = findViewById(R.id.loanrview);
        rView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LoanAdapter(arrayList, this);
        rView.setAdapter(adapter);



        toolbar = findViewById(R.id.loantoolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
