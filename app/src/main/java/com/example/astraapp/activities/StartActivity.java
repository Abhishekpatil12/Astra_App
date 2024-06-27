package com.example.astraapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.astraapp.models.DataClass;
import com.example.astraapp.adapters.MyAdapter;
import com.example.astraapp.databinding.ActivityStartBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding;

    ArrayList<DataClass> startList;
    DatabaseReference databaseReference;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseApp.initializeApp(this);

        initfunc();
        getData();
        onclickfunc();

    }

    private void onclickfunc() {
        binding.gridViewmain.setOnItemClickListener((adapterView, view, i, l) -> {

            String str =  startList.get(i).getKey();
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            intent.putExtra("name",str);
            startActivity(intent);

        });
    }

    private void initfunc() {

        startList = new ArrayList<>();
        myAdapter = new MyAdapter(this,startList);
        binding.gridViewmain.setAdapter(myAdapter);

    }

    private void getData() {

        loading(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                loading(false);
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {

                    String str1 = (String) dataSnapshot.child("name").getValue();
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


}