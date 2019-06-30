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
import com.example.lenovo.reminder.model.RecordModel;

import java.util.ArrayList;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.MyViewHolder> {

    ArrayList<RecordModel> arrayLis=new ArrayList<>();
    Context context;
    DbHelper db;

    public PendingAdapter(ArrayList<RecordModel> arrayLis, Context context) {
        this.arrayLis = arrayLis;
        this.context = context;
        db= new DbHelper(this.context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_itemview,parent,false);

        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final RecordModel model= arrayLis.get(i);
        holder.name.setText(model.getItem());
        holder.price.setText(model.getPrice()+" Rs");
        holder.date.setText(model.getOnlyDate());
        holder.month.setText(model.getOnlyMonth());
        holder.year.setText(model.getYear());

    }

    @Override
    public int getItemCount() {
        return arrayLis.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, price,date,month,year;

        public MyViewHolder(@NonNull View view) {
            super(view);
            name= view.findViewById(R.id.dailyname);
            price= view.findViewById(R.id.dailyprice);
            date= view.findViewById(R.id.dailydate);
            month= view.findViewById(R.id.dailymonth);
            year =view.findViewById(R.id.dailyyear);



        }
    }

}
