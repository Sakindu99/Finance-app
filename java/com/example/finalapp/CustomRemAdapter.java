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


public class CustomRemAdapter extends RecyclerView.Adapter<CustomRemAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList reminder_id, reminder_type, reminder_amount, reminder_date;

    Animation translate_anim;

    CustomRemAdapter(Activity activity, Context context, ArrayList reminder_id, ArrayList reminder_type, ArrayList reminder_amount, ArrayList reminder_date){

        this.activity = activity;
        this.context = context;
        this.reminder_id = reminder_id;
        this.reminder_type = reminder_type;
        this.reminder_amount = reminder_amount;
        this.reminder_date = reminder_date;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from((context));
        View view = inflater.inflate(R.layout.my_row_rem, parent, false);
        return new MyViewHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.rm_id_txt.setText(String.valueOf(reminder_id.get(position)));
        holder.rm_type_txt.setText(String.valueOf(reminder_type.get(position)));
        holder.rm_amount_txt.setText(String.valueOf(reminder_amount.get(position)));
        holder.rm_date_txt.setText(String.valueOf(reminder_date.get(position)));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, updateRemActivity.class);
                intent.putExtra("id", String.valueOf(reminder_id.get(position)));
                intent.putExtra("type", String.valueOf(reminder_type.get(position)));
                intent.putExtra("amount", String.valueOf(reminder_amount.get(position)));
                intent.putExtra("date", String.valueOf(reminder_date.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return reminder_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView rm_id_txt, rm_type_txt, rm_amount_txt, rm_date_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rm_id_txt = itemView.findViewById(R.id.rm_id_txt);
            rm_type_txt = itemView.findViewById(R.id.rm_type_txt);
            rm_amount_txt = itemView.findViewById(R.id.rm_amount_txt);
            rm_date_txt = itemView.findViewById(R.id.rm_date_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}



