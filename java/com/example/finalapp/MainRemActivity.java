package com.example.finalapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainRemActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add;
    CustomRemAdapter customAdapter;

    ImageView empty_imageview , nav;
    TextView no_data;

    MyDatabaseHelper myDB;
    ArrayList<String> rem_id, rem_type, rem_amount, rem_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rem);

        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        nav = findViewById(R.id.navigation);

        recyclerView = findViewById(R.id.recycleView);
        add = findViewById(R.id.add_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainRemActivity.this, addRemActivity.class);
                startActivity(intent);
            }
        });

        ImageView left_arrow = findViewById(R.id.left_arrow);
        ImageView check = findViewById(R.id.check);
        ImageView clear = findViewById(R.id.clear);
        TextView title = findViewById(R.id.title);

        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainRemActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

        /*clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainRemActivity.this, "Deleted Successfully" , Toast.LENGTH_SHORT).show();
            }
        });

        title.setText("Manage Income");*/

       myDB = new MyDatabaseHelper(MainRemActivity.this);
        rem_id = new ArrayList<>();
        rem_type = new ArrayList<>();
        rem_amount = new ArrayList<>();
        rem_date = new ArrayList<>();

     storeDataInArrays();

        customAdapter = new CustomRemAdapter(MainRemActivity.this,this,rem_id, rem_type, rem_amount, rem_date);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainRemActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
       Cursor cursor = myDB.readAllDataRem();
       if(cursor.getCount() == 0){
           empty_imageview.setVisibility(View.VISIBLE);
           no_data.setVisibility(View.VISIBLE);
        }else{
           while (cursor.moveToNext()){
               rem_id.add(cursor.getString(0));
               rem_type.add(cursor.getString(1));
               rem_amount.add(cursor.getString(2));
               rem_date.add(cursor.getString(3));
            }
           empty_imageview.setVisibility(View.GONE);
           no_data.setVisibility(View.GONE);
       }
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All ?");
        builder.setMessage("Are you sure you want to delete all ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper DB = new MyDatabaseHelper(MainRemActivity.this);
                DB.deleteAllDataRem();
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

}