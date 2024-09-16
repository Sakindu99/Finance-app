package com.example.finalapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class SearchViewHolder extends RecyclerView.ViewHolder{

    public TextView note, amount, category;

    public SearchViewHolder(View itemView){
        super(itemView);
        note = (TextView)itemView.findViewById(R.id.in_note_txt);
        amount = (TextView)itemView.findViewById(R.id.in_amount_txt);
        category = (TextView)itemView.findViewById(R.id.in_cat_txt);
    }

}

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private Context context;
    private List<Income> income;

    public SearchAdapter(Context context, List<Income> income) {
        this.context = context;
        this.income = income;
    }
    @Override
    public SearchViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.my_row_income,parent, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( SearchViewHolder holder, int position) {
        holder.note.setText(income.get(position).getNote());
        holder.amount.setText(income.get(position).getAmount());
        holder.category.setText(income.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return income.size();
    }
}
