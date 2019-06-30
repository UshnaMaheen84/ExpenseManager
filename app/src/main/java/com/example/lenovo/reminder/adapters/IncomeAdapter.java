package com.example.lenovo.reminder.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.reminder.R;
import com.example.lenovo.reminder.dbHelper.DbHelper;
import com.example.lenovo.reminder.model.IncomeModel;

import java.util.ArrayList;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.MyViewHolder> {

    ArrayList<IncomeModel> arrayList =new ArrayList<>();
    Context context;
    DbHelper db;

    public IncomeAdapter(ArrayList<IncomeModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        db= new DbHelper(this.context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_itemview,parent,false);

        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {

        final IncomeModel model= arrayList.get(i);
        holder.name.setText(model.getSource());
        holder.price.setText(model.getAmount()+" Rs");
        holder.date.setText(model.getOnlyDate());
        holder.month.setText(model.getOnlyMonth());
        holder.year.setText(model.getYear());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.deteteIncomeRcord(model.getIncome_id());
                updateArrayList(arrayList.get(holder.getAdapterPosition()), holder.getAdapterPosition(), model.getIncome_id());


            }
        });

    }
    private void updateArrayList(IncomeModel model, int position, String id) {
        arrayList.remove(model);
        try {
            notifyItemRemoved(position);

        } catch (Exception e) {

            notifyItemRemoved(position);

        }
        Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {

        return arrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, price,date,month,year;
        Button delete;
        public MyViewHolder(@NonNull View view) {
            super(view);
            name= view.findViewById(R.id.tvsource);
            price= view.findViewById(R.id.tvamount);
            date= view.findViewById(R.id.incomedate);
            delete= view.findViewById(R.id.incomedeletebtn);
            month= view.findViewById(R.id.incomemonth);
            year =view.findViewById(R.id.incmyear);



        }
    }

}
