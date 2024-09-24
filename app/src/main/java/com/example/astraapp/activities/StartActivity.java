package com.example.astraapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.astraapp.R;
import com.example.astraapp.models.DataClass;
import com.example.astraapp.adapters.MyAdapter;
import com.example.astraapp.databinding.ActivityStartBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding;
    DrawerLayout drawerLayout;
    ImageButton buttonDrawerToggle;
    NavigationView navigationView;

    ArrayList<DataClass> startList;
    DatabaseReference databaseReference;
    String id;
    MyAdapter myAdapter;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseApp.initializeApp(this);

        db = FirebaseFirestore.getInstance();
        drawerLayout = findViewById(R.id.drawerLayout);
        buttonDrawerToggle = findViewById(R.id.buttondrawertoggle);
        navigationView = findViewById(R.id.navigationView);

        navigation();
        initfunc();
        getData();
        onclickfunc();

    }

    private void navigation() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if(itemId==R.id.navHome){
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        // Close the drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                }

                if(itemId==R.id.navAbout){
                    Intent intent = new Intent(StartActivity.this, MissionAndVision.class);
                    startActivity(intent);
                }

                if(itemId==R.id.navContact){
                    Intent intent = new Intent(StartActivity.this, ContactUs.class);
                    startActivity(intent);
                }

                if(itemId==R.id.navFeedback){
                    Intent intent = new Intent(StartActivity.this, Feedback.class);
                    startActivity(intent);
                }

                if(itemId==R.id.navDonate){
                    Intent intent = new Intent(StartActivity.this, Donate.class);
                    startActivity(intent);
                    Toast.makeText(StartActivity.this, "Donate Clicked", Toast.LENGTH_SHORT).show();
                }

                if(itemId==R.id.navinstagramSymbol){
                    RedirectApp("https://www.instagram.com/shaastra.samvad/");
                }

                if(itemId==R.id.navxSymbol){
                    RedirectApp("https://x.com/ShaastraS52332");
                }

                return false;
            }
        });
    }

    private void onclickfunc() {
        binding.gridViewmain.setOnItemClickListener((adapterView, view, i, l) -> {

            String str =  startList.get(i).getKey();
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            intent.putExtra("name",str);
            intent.putExtra("id",id);
            startActivity(intent);
        });

        binding.donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,Donate.class);
                startActivity(intent);
            }
        });

        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });


    }

    private void initfunc() {

        startList = new ArrayList<>();
        myAdapter = new MyAdapter(this,startList);
        binding.gridViewmain.setAdapter(myAdapter);

    }

    private void getData() {

        loading(true);

//        db.collection("info").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                if(task.isSuccessful()){
//                    loading(false);
//                    for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
//                        Map<String, Object> mp = documentSnapshot.getData();
//                        //System.out.println("DATA : "+mp.get("imageurl"));
//                        String str1 = (String) mp.get("name");
//                        String str2 = (String) mp.get("imageurl");
//                        String str3 = (String) mp.get("id");
//                        DataClass startClass = new DataClass(str1,str2,str3);
//                        startList.add(startClass);
//
//                    }
//
//                    myAdapter.notifyDataSetChanged();
//                }
//            }
//        });
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                loading(false);
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {

                    String str1 = (String) dataSnapshot.child("name").getValue();
                    id = (String) dataSnapshot.child("id").getValue();
                    String str2 = (String) dataSnapshot.child("imageurl").getValue();
                    String str3 = dataSnapshot.getKey();
                    DataClass startClass = new DataClass(str1,str2,str3);
                    startList.add(startClass);
                }

                //System.out.println(dataList);
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void RedirectApp(String link){

        try {
            Uri uri = Uri.parse(link);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}