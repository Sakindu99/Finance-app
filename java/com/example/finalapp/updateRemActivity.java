package com.example.finalapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;


public class updateRemActivity extends AppCompatActivity {

    EditText type_input, amount_input, date_input;
    Button update_button;
    DatePickerDialog.OnDateSetListener onDateSetListener;

    String id, type, amount, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reminder);

        type_input = findViewById(R.id.update_reminder_field1);
        amount_input = findViewById(R.id.update_reminder_field2);
        date_input = findViewById(R.id.update_reminder_field3);
        update_button = findViewById(R.id.update_rem_btn);

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        updateRemActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1 ;
                String date = dayOfMonth+"/"+month+"/"+year;
                date_input.setText(date);
            }
        };

        //Tool bar
        ImageButton left_arrow1 = findViewById(R.id.left_arrow1);
        ImageView check = findViewById(R.id.check);
        TextView title = findViewById(R.id.title);
        ImageButton clear = findViewById(R.id.clear);

        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(type);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = type_input.getText().toString();
                String amount = amount_input.getText().toString();
                String date = date_input.getText().toString();

                //making a function for validation and pass all parameters
                boolean check = validateinfo(type, amount, date);

                if (check == true) {

                    //Creating database connection
                    MyDatabaseHelper myDB = new MyDatabaseHelper(updateRemActivity.this);
                    type = type_input.getText().toString().trim();
                    amount = amount_input.getText().toString().trim();
                    date = date_input.getText().toString().trim();
                    myDB.updateDataRem(id, type, amount, date);

                    Toast.makeText(getApplicationContext(), "Updated Succesfully", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getApplicationContext(), "Sorry check information again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        left_arrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(updateRemActivity.this, MainRemActivity.class);
                startActivity(intent);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("type") &&
                getIntent().hasExtra("amount") && getIntent().hasExtra("date")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("type");
            amount = getIntent().getStringExtra("amount");
            date = getIntent().getStringExtra("date");

            //Setting Intent Data
            type_input.setText(type);
            amount_input.setText(amount);
            date_input.setText(date);
            Log.d("", type+" "+amount+" "+date);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }


   void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + type+ " ?");
        builder.setMessage("Are you sure you want to delete " +type  + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(updateRemActivity.this);
                myDB.deleteOneRowRem(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    //validation method
    private boolean validateinfo(String type, String amount, String date) {

        if (type.length() == 0) {
            //Checking if the bill type is empty
            type_input.requestFocus();
            type_input.setError("THIS FIELD CAN NOT BE EMPTY");
            return false;
        } else if (!type.matches("^\\s*[\\da-zA-Z][\\da-zA-Z\\s]*$")) {
            //Checking for relevant input types for the fields
            type_input.requestFocus();
            type_input.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        } else if (amount.length() == 0) {
            //Checking if the bill amount is empty
            amount_input.requestFocus();
            amount_input.setError("FIELD CAN NOT BE EMPTY");
            return false;
        } else if (!amount.matches("\\d+")) {
            //Checking for relevant input types for the fields
            amount_input.requestFocus();
            amount_input.setError("PLEASE ENTER NUMBERS");
            return false;
        } else if (date.length() == 0) {
            //Checking if the date is empty
            date_input.requestFocus();
            date_input.setError("FIELD CAN NOT BE EMPTY");
            return false;
        } else {
            return true;
        }
    }
}
