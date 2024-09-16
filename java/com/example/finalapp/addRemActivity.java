package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class addRemActivity extends AppCompatActivity {

    EditText Rtype_input, Ramount_input, Rdate_input;
    Button add_button;
    DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rem);


        //Assign Variables
        //Bill Notes insert form
        Rtype_input = findViewById(R.id.add_reminder_field1);
        Ramount_input = findViewById(R.id.add_reminder_field2);
        Rdate_input = findViewById(R.id.add_reminder_field3);
        add_button = findViewById(R.id.add_rem_btn);

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Rdate_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        addRemActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                Rdate_input.setText(date);
            }
        };


        MyDatabaseHelper myDB = new MyDatabaseHelper(addRemActivity.this);
        add_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String type = Rtype_input.getText().toString();
                String amount = Ramount_input.getText().toString();
                String date = Rdate_input.getText().toString();

                //making a function for validation and pass all parameters
                boolean check = validateinfo(type, amount, date);
                if (check == true) {
                    //When data are in valid formats, input data to the database
                    Boolean insert = myDB.addReminder(Rtype_input.getText().toString().trim(),
                            Ramount_input.getText().toString().trim(),
                            Rdate_input.getText().toString().trim());

                    if (insert == true) {
                        //Display success message when data inserted Successfully to the data base
                        Toast.makeText(addRemActivity.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        //Display an error message when data is not entered to the database
                        Toast.makeText(addRemActivity.this, "Error!!", Toast.LENGTH_SHORT).show();
                    }
                    //Display a toast message when all entered data ara valid
                    Toast.makeText(getApplicationContext(), "Data is valid", Toast.LENGTH_SHORT).show();
                } else {
                    //Display a toast message when invalid data is entered
                    Toast.makeText(getApplicationContext(), "Sorry check information again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView left_arrow = findViewById(R.id.left_arrow);
        ImageView check = findViewById(R.id.check);
        TextView title = findViewById(R.id.title);

        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(addRemActivity.this, "You clicked in left icon", Toast.LENGTH_SHORT).show();
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(addRemActivity.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addRemActivity.this, MainRemActivity.class);
                startActivity(intent);
            }
        });

        title.setText("Manage Reminder");
    }

    //validation method
    private boolean validateinfo(String type, String amount, String date) {

        if (type.length() == 0) {
            //Checking if the bill type is empty
            Rtype_input.requestFocus();
            Rtype_input.setError("THIS FIELD CAN NOT BE EMPTY");
            return false;
        } else if (!type.matches("^\\s*[\\da-zA-Z][\\da-zA-Z\\s]*$")) {
            //Checking for relevant input types for the fields
            Rtype_input.requestFocus();
            Rtype_input.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        } else if (amount.length() == 0) {
            //Checking if the bill amount is empty
            Ramount_input.requestFocus();
            Ramount_input.setError("FIELD CAN NOT BE EMPTY");
            return false;
        } else if (!amount.matches("\\d+")) {
            //Checking for relevant input types for the fields
            Ramount_input.requestFocus();
            Ramount_input.setError("PLEASE ENTER NUMBERS");
            return false;
        } else if (date.length() == 0) {
            //Checking if the date is empty
            Rdate_input.requestFocus();
            Rdate_input.setError("FIELD CAN NOT BE EMPTY");
            return false;

        } else {
            return true;
        }
    }
}