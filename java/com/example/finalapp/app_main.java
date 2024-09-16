package com.example.finalapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class app_main  extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(4000);

                }catch(Exception e){
                    e.printStackTrace();

                }finally{
                    Intent intent = new Intent(app_main.this, intro_expense.class);
                    startActivity(intent);


                }

            }
        };thread.start();
    }
}
