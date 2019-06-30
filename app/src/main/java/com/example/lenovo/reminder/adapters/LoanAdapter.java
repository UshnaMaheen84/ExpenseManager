package com.example.lenovo.reminder.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.reminder.R;
import com.example.lenovo.reminder.dbHelper.DbHelper;
import com.example.lenovo.reminder.model.LoanModel;

import java.util.ArrayList;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.MyViewHolder> {

    ArrayList<LoanModel> arrayList = new ArrayList<>();
    Context context;
    DbHelper db;

    public LoanAdapter(ArrayList<LoanModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        db = new DbHelper(this.context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_itemview, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {

        final LoanModel model = arrayList.get(i);
        holder.name.setText(model.getPerson());
        holder.price.setText(model.getAmount() + " Rs");
        holder.returndate.setText(model.getReturnDate());
        holder.type.setText(model.getType());
        holder.date.setText(model.getOnlyDate());
        holder.month.setText(model.getMonth());
        holder.year.setText(model.getYear());
        Log.e("LoanDate", model.getType());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.deteteLoanRcord(model.getLoan_id());
                updateArrayList(arrayList.get(holder.getAdapterPosition()), holder.getAdapterPosition(), model.getLoan_id());


            }
        });

    }

    private void updateArrayList(LoanModel model, int position, String id) {
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


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, date, month, year, returndate, type;
        Button delete;

        public MyViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.loanname);
            price = view.findViewById(R.id.loanprice);
            date = view.findViewById(R.id.loandate);
            delete = view.findViewById(R.id.loandeletebtn);
            month = view.findViewById(R.id.loanmonth);
            year = view.findViewById(R.id.loanyear);
            returndate = view.findViewById(R.id.returndate);
            type = view.findViewById(R.id.loantype);


        }
    }

}
