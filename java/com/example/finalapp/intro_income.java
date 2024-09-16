package com.example.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class intro_income extends AppCompatActivity {

    Button button1,button2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_income);

        button1 = findViewById(R.id.billStart);
        button2 = findViewById(R.id.incomebtn2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(intro_income.this, intro_goal.class);
                startActivity(intent2);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(intro_income.this, intro_expense.class);
                startActivity(intent);
            }
        });

    }
}