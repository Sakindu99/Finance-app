package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;

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

public class MainExpenseActivity extends AppCompatActivity {

    RecyclerView recycle_view;
    FloatingActionButton floatButton;

    ImageView empty_imageview, nav;
    TextView no_data;

    //DB helper
    MyDatabaseHelper DB;
    ExpenseAdapterRecord expenseAdapterRecord;
    ArrayList<String> expense_id, expense_note, expense_amount, expense_paymethod, expense_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //Tool bar
        ImageView left_arrow = findViewById(R.id.left_arrow);
        TextView title = findViewById(R.id.title_displaylist);

        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        nav = findViewById(R.id.navigation);

        //display list and floating action button
        recycle_view = findViewById(R.id.dataDisplay_listRv);
        floatButton = findViewById(R.id.floatingAction_add);

        //init db helper class
        DB = new MyDatabaseHelper(this);


        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainExpenseActivity.this, addExpenseActivity.class);
                startActivity(newIntent);
            }
        });

        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainExpenseActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    confirmDialog();
            }
        });

        title.setText("Manage Expense");

        //init db helper class
        DB = new MyDatabaseHelper(MainExpenseActivity.this);

        expense_id = new ArrayList<>();
        expense_note = new ArrayList<>();
        expense_amount = new ArrayList<>();
        expense_paymethod = new ArrayList<>();
        expense_category = new ArrayList<>();

        storeDataInArrays();

        ExpenseAdapterRecord adapterRecord = new ExpenseAdapterRecord(MainExpenseActivity.this, this, expense_id, expense_note, expense_amount, expense_paymethod, expense_category);
        recycle_view.setAdapter(adapterRecord);
        recycle_view.setLayoutManager(new LinearLayoutManager(MainExpenseActivity.this));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void storeDataInArrays() {
        Cursor cursor = DB.readAllDataExpense();

        if (cursor.getCount() == 0) {
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                expense_id.add(cursor.getString(0));
                expense_note.add(cursor.getString(1));
                expense_amount.add(cursor.getString(2));
                expense_paymethod.add(cursor.getString(3));
                expense_category.add(cursor.getString(4));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All ?");
        builder.setMessage("Are you sure you want to delete all ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper DB = new MyDatabaseHelper(MainExpenseActivity.this);
                DB.deleteAllDataExpense();
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


