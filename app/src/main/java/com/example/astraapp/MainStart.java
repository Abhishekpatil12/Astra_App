package com.example.astraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainStart extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageButton buttonDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);

        drawerLayout = findViewById(R.id.drawerLayout);
        //buttonDrawerToggle = findViewById(R.id.buttondrawertoggle);
        navigationView = findViewById(R.id.navigationView);

        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if(itemId==R.id.navContact){
                    Intent intent = new Intent(MainStart.this,ContactUs.class);
                    startActivity(intent);
                    Toast.makeText(MainStart.this, "Donate Clicked", Toast.LENGTH_SHORT).show();
                }

                if(itemId==R.id.navMission){
                    Intent intent = new Intent(MainStart.this,MissionAndVision.class);
                    startActivity(intent);
                    Toast.makeText(MainStart.this, "Menu Clicked", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
}