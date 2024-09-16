package com.example.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class addExpenseActivity extends AppCompatActivity {


    //insert form
    private EditText note_text;
    private EditText amount_text;
    private Button add_button;

    Spinner method_text, category_text;

    MyDatabaseHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tool bar
        ImageView left_arrow = findViewById(R.id.left_arrow);
        ImageView check = findViewById(R.id.check);
        TextView title = findViewById(R.id.title);
        ImageView clear = findViewById(R.id.clear);


        //Expense insert form

        note_text = findViewById(R.id.add_expense_field1);
        amount_text = findViewById(R.id.add_expense_field2);
        method_text = findViewById(R.id.add_expense_spinner3);
        category_text = findViewById(R.id.add_expense_spinner4);
        add_button = findViewById(R.id.expense_add_btn);


        //date base connection
        MyDatabaseHelper DB = new MyDatabaseHelper(addExpenseActivity.this);


        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //direct to MainExpenseActivity
                Intent intent = new Intent(addExpenseActivity.this, MainExpenseActivity.class);
                startActivity(intent);
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String expenseNote = note_text.getText().toString();
                String expenseAmount = amount_text.getText().toString();

                //making a function for validation and pass all parameters
                boolean  check= validateinfo(expenseNote,expenseAmount);

                if (check == true) {
                    //when data are in valid formats, input data to the databaase
                    String note = note_text.getText().toString().trim();
                    String amount = amount_text.getText().toString().trim();
                    String pay = method_text.getSelectedItem().toString().trim();
                    String cat = category_text.getSelectedItem().toString().trim();

                    //Database connection
                    boolean insert = DB.insertData(note, amount, pay, cat);

                    if(insert==true){
                        Toast.makeText(addExpenseActivity.this, "Inserted Successfully" , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(addExpenseActivity.this, "Error!!" , Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(getApplicationContext(), "Data is valid",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Sorry check information again",Toast.LENGTH_SHORT).show();
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(addExpenseActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });

        title.setText("Manage Expense");

    }

    //validation
    private boolean validateinfo(String expenseNote, String expenseAmount) {
        if (expenseNote.length() == 0) {
            //Checking for null expense note inputs
            note_text.requestFocus();
            note_text.setError("THIS FIELD CAN NOT BE EMPTY");
            return false;
        } else if (!expenseNote.matches("^\\s*[\\da-zA-Z][\\da-zA-Z\\s]*$")) {
            //checking for relevant input types for the field
            note_text.requestFocus();
            note_text.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        } else if (expenseAmount.length() == 0) {
            //Checking for null expense amount inputs
            amount_text.requestFocus();
            amount_text.setError("FIELD CAN NOT BE EMPTY");
            return false;
        } else if (!expenseAmount.matches("\\d+")) {
            //checking for relevant input types for the field
            amount_text.requestFocus();
            amount_text.setError("PLEASE ENTER NUMBERS");
            return false;
        }
        else {
            return true;
        }
    }
}






