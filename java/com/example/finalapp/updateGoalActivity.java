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
import android.widget.Toast;

public class updateGoalActivity extends AppCompatActivity {

    //intialize variable
    EditText name_input, amount_input, description_input;
    Button update_button;

    String id, name, amount, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_goal);

        name_input = findViewById(R.id.update_goal_field1);
        amount_input = findViewById(R.id.update_goal_field2);
        description_input = findViewById(R.id.update_goal_field3);
        update_button = findViewById(R.id.goal_update_btn);


        //Tool bar
        ImageButton left_arrow1 = findViewById(R.id.left_arrow1);
        ImageButton clear = findViewById(R.id.clear);

        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        left_arrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //direct to MainGoalActivity
                Intent intent = new Intent(updateGoalActivity.this, MainGoalActivity.class);
                startActivity(intent);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = name_input.getText().toString();
                String amount = amount_input.getText().toString();
                String description = description_input.getText().toString();

                //making a function for validation and pass all parameters
                boolean  check= validateinfo(name,amount,description);
                if (check == true) {
                    //And only then we call this
                    MyDatabaseHelper myDB = new MyDatabaseHelper(updateGoalActivity.this);
                    name = name_input.getText().toString().trim();
                    amount = amount_input.getText().toString().trim();
                    description = description_input.getText().toString().trim();
                    myDB.updateDataGoal(id, name, amount, description);
                    Toast.makeText(getApplicationContext(),"Updated Succesfully",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Sorry check information again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("amount") && getIntent().hasExtra("description")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            amount = getIntent().getStringExtra("amount");
            description = getIntent().getStringExtra("description");

            //Setting Intent Data
            name_input.setText(name);
            amount_input.setText(amount);
            description_input.setText(description);
            Log.d("", name+" "+amount+" "+description);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(updateGoalActivity.this);
                myDB.deleteOneRowGoal(id);
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
    private boolean validateinfo(String name, String amount, String description) {
        if (name.length() == 0) {
            //Checking for null goal name inputs
            name_input.requestFocus();
            name_input.setError("THIS FIELD CAN NOT BE EMPTY");
            return false;
        } else if (!name.matches("^\\s*[\\da-zA-Z][\\da-zA-Z\\s]*$")) {
            //checking for relevant input types for the field
            name_input.requestFocus();
            name_input.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        } else if (amount.length() == 0) {
            //Checking for null goal aomount inputs
            amount_input.requestFocus();
            amount_input.setError("FIELD CAN NOT BE EMPTY");
            return false;
        } else if (!amount.matches("\\d+")) {
            //checking for relevant input types for the field
            amount_input.requestFocus();
            amount_input.setError("PLEASE ENTER NUMBERS");
            return false;
        } else if (description.length() == 0) {
            //Checking for null goal descriptions inputs
            description_input.requestFocus();
            description_input.setError("FILED CAN NOT BE EMPTY");
            return false;
        } else if (!description.matches("^\\s*[\\da-zA-Z][\\da-zA-Z\\s]*$")) {
            //checking for relevant input types for the field
            description_input.requestFocus();
            description_input.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        }
        else {
            return true;
        }
    }
}


