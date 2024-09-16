package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ImageView expense, income,goal,rem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_main);

//        Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //init image views
        expense = findViewById(R.id.expense_img);
        income = findViewById(R.id.income_img);
        goal = findViewById(R.id.goal_img);
        rem = findViewById(R.id.Rem_img);

        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,MainExpenseActivity.class);
                startActivity(intent);
            }
        });

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,MainIncomeActivity.class);
                startActivity(intent);
            }
        });

        goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,MainGoalActivity.class);
                startActivity(intent);
            }
        });

        rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,MainRemActivity.class);
                startActivity(intent);
            }
        });

//        toolbar
        setSupportActionBar(toolbar);

//        Navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                 break;

            case R.id.nav_expense:
                Intent intent = new Intent(DashboardActivity.this,MainExpenseActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_income:
                Intent intent1 = new Intent(DashboardActivity.this,MainIncomeActivity.class);
                startActivity(intent1);
                break;

            case R.id.nav_goal:
                Intent intent2 = new Intent(DashboardActivity.this,MainGoalActivity.class);
                startActivity(intent2);
                break;

            case R.id.nav_note:
                Intent intent3 = new Intent(DashboardActivity.this,MainRemActivity.class);
                startActivity(intent3);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

}

