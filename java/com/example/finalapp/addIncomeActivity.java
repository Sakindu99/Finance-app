package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class addIncomeActivity extends AppCompatActivity {

    //intializing varialbes
    EditText note_input, amount_input;
    Spinner category_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        //Assign Variables
        //Income insert form
        note_input = findViewById(R.id.add_income_field1);
        amount_input = findViewById(R.id.add_income_field2);
        category_input = findViewById(R.id.add_income_spinner3);
        add_button = findViewById(R.id.income_add_btn);

        MyDatabaseHelper myDB = new MyDatabaseHelper(addIncomeActivity.this);
        add_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                String note = note_input.getText().toString();
                String amount = amount_input.getText().toString();

                //making a function for validation and pass all parameters
                boolean  check= validateinfo(note,amount);

                if (check == true) {

                    //when data are in valid formats, input data to the databaase
                    Boolean insert = myDB.addIncome (note_input.getText().toString().trim(),
                            amount_input.getText().toString().trim(),
                            category_input.getSelectedItem().toString().trim());

                    if(insert==true){
                        Toast.makeText(addIncomeActivity.this, "Inserted Successfully" , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(addIncomeActivity.this, "Error!!" , Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(getApplicationContext(), "Data is valid",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Sorry check information again",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView left_arrow = findViewById(R.id.left_arrow);
        ImageView check = findViewById(R.id.check);
        TextView title = findViewById(R.id.title);

        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(addIncomeActivity.this, "You clicked in left icon" , Toast.LENGTH_SHORT).show();
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(addIncomeActivity.this, "Inserted Successfully" , Toast.LENGTH_SHORT).show();
            }
        });

        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //direct to MainIncomeActivity page
                Intent intent = new Intent(addIncomeActivity.this, MainIncomeActivity.class);
                startActivity(intent);
            }
        });

        title.setText("Manage Income");
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
            //Checking for null income amount inputs
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