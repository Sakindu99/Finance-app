package com.example.finalapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class updateIncomeActivity extends AppCompatActivity {

    //initialize variables
    EditText note_input, amount_input, category_input;
    Button update_button;

    String id, note, amount, category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_income);

        note_input = findViewById(R.id.update_income_field1);
        amount_input = findViewById(R.id.update_income_field2);
        category_input = findViewById(R.id.update_income_field3);
        update_button = findViewById(R.id.income_update_btn);

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
            ab.setTitle(category);
        }

        category_input.setEnabled(false);

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note = note_input.getText().toString();
                String amount = amount_input.getText().toString();

                //making a function for validation and pass all parameters
                boolean  check= validateinfo(note,amount);
                if (check == true) {
                    //And only then we call this
                    MyDatabaseHelper myDB = new MyDatabaseHelper(updateIncomeActivity.this);
                    note = note_input.getText().toString().trim();
                    amount = amount_input.getText().toString().trim();
                    category = category_input.getText().toString().trim();
                    myDB.updateData(id, note, amount, category);
                    Toast.makeText(getApplicationContext(),"Updated Succesfully",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Sorry check information again",Toast.LENGTH_SHORT).show();
                }
            }
        });

        left_arrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //direct to MainIncomeActivity
                Intent intent = new Intent(updateIncomeActivity.this, MainIncomeActivity.class);
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
        if(getIntent().hasExtra("id") && getIntent().hasExtra("note") &&
                getIntent().hasExtra("amount") && getIntent().hasExtra("category")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            note = getIntent().getStringExtra("note");
            amount = getIntent().getStringExtra("amount");
            category = getIntent().getStringExtra("category");

            //Setting Intent Data
            note_input.setText(note);
            amount_input.setText(amount);
            category_input.setText(category);
            Log.d("", note+" "+amount+" "+category);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

   void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + note + " ?");
        builder.setMessage("Are you sure you want to delete " + note + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(updateIncomeActivity.this);
                myDB.deleteOneRow(id);
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

    //validation
    private boolean validateinfo(String incomeNote, String incomeAmount) {
        if (incomeNote.length() == 0) {
            //Checking for null income note inputs
            note_input.requestFocus();
            note_input.setError("THIS FIELD CAN NOT BE EMPTY");
            return false;
        } else if (!incomeNote.matches("^\\s*[\\da-zA-Z][\\da-zA-Z\\s]*$")) {
            //checking for relevant input types for the field
            note_input.requestFocus();
            note_input.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        } else if (incomeAmount.length() == 0) {
            //Checking for null icnome amount inputs
            amount_input.requestFocus();
            amount_input.setError("FIELD CAN NOT BE EMPTY");
            return false;
        } else if (!incomeAmount.matches("\\d+")) {
            //checking for relevant input types for the field
            amount_input.requestFocus();
            amount_input.setError("PLEASE ENTER NUMBERS");
            return false;
        }
        else {
            return true;
        }
    }
}
