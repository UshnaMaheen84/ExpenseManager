package com.example.lenovo.reminder.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.reminder.R;
import com.example.lenovo.reminder.dbHelper.DbHelper;
import com.example.lenovo.reminder.model.LoanModel;

import java.util.ArrayList;

public class ReturnLoanAdapter extends RecyclerView.Adapter<ReturnLoanAdapter.MyViewHolder>{

    ArrayList<LoanModel> arrayList=new ArrayList<>();
    Context context;
    DbHelper db;

    public ReturnLoanAdapter(ArrayList<LoanModel> arrayLis, Context context) {
        this.arrayList = arrayLis;
        this.context = context;
        db= new DbHelper(this.context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.return_date_itemview,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final LoanModel model = arrayList.get(i);
        holder.name.setText(model.getPerson());
        holder.price.setText(model.getAmount() + " Rs");
        holder.date.setText(model.getOnlyDate());
        holder.month.setText(model.getMonth());
        holder.year.setText(model.getYear());
        holder.type.setText(model.getType());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, price,date,month,year,type;

        public MyViewHolder(@NonNull View view) {
            super(view);
            name= view.findViewById(R.id.returnname);
            price= view.findViewById(R.id.returnAmount);
            date= view.findViewById(R.id.returnloandate);
            month= view.findViewById(R.id.returnmonth);
            year =view.findViewById(R.id.returnyear);
            type= view.findViewById(R.id.returntype);



        }
    }

}
