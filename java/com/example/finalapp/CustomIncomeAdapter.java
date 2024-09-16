package com.example.finalapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CustomIncomeAdapter extends RecyclerView.Adapter<CustomIncomeAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList income_id, income_note, income_amount, income_category;

    Animation translate_anim;

    CustomIncomeAdapter(Activity activity, Context context, ArrayList income_id, ArrayList income_note, ArrayList income_amount, ArrayList income_category){

        this.activity = activity;
        this.context = context;
        this.income_id = income_id;
        this.income_note = income_note;
        this.income_amount = income_amount;
        this.income_category = income_category;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from((context));
        View view = inflater.inflate(R.layout.my_row_income, parent, false);
        return new MyViewHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.in_id_txt.setText(String.valueOf(income_id.get(position)));
        holder.in_note_txt.setText(String.valueOf(income_note.get(position)));
        holder.in_amount_txt.setText(String.valueOf(income_amount.get(position)));
        holder.in_cat_txt.setText(String.valueOf(income_category.get(position)));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, updateIncomeActivity.class);
                intent.putExtra("id", String.valueOf(income_id.get(position)));
                intent.putExtra("note", String.valueOf(income_note.get(position)));
                intent.putExtra("amount", String.valueOf(income_amount.get(position)));
                intent.putExtra("category", String.valueOf(income_category.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return income_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView in_id_txt, in_note_txt, in_amount_txt, in_cat_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            in_id_txt = itemView.findViewById(R.id.in_id_txt);
            in_note_txt = itemView.findViewById(R.id.in_note_txt);
            in_amount_txt = itemView.findViewById(R.id.in_amount_txt);
            in_cat_txt = itemView.findViewById(R.id.in_cat_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}




