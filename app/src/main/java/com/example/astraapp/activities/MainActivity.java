package com.example.astraapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.astraapp.models.DataClass;
import com.example.astraapp.adapters.MyAdapter;
import com.example.astraapp.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    ArrayList<DataClass> dataList;
    MyAdapter myAdapter;
    DatabaseReference databaseReference;
    String selected;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseApp.initializeApp(this);

        getSelected();
        initfunc();
        getData();
        onclickfunc();


    }

    private void onclickfunc() {

        binding.gridView.setOnItemClickListener((adapterView, view, i, l) -> {


            if(selected.equals("arrow_heads"))
            {
                intent = new Intent(MainActivity.this, ArrowHeadInfo.class);
            }
            else if(selected.equals("astras"))
            {
                intent = new Intent(MainActivity.this, AstraInfo.class);
            }
            else if(selected.equals("vyuham"))
            {
                intent = new Intent(MainActivity.this, VyuhamInfo.class);
            }

            String str =  dataList.get(i).getKey();
            intent.putExtra("name",str);
            startActivity(intent);
        });
    }

    private void initfunc() {
        dataList = new ArrayList<>();
        myAdapter = new MyAdapter(this,dataList);
        binding.gridView.setAdapter(myAdapter);
    }

    private void getData() {
        loading(true);
        databaseReference = FirebaseDatabase.getInstance().getReference(selected);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                loading(false);
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    //dataList.add(dataClass);
                    if(!dataSnapshot.hasChild("imageurl"))
                    {
                        continue;
                    }
                    String str1 = (String) dataSnapshot.child("name").getValue();
                    String str2 = (String) dataSnapshot.child("imageurl").getValue();
                    String str3 = dataSnapshot.getKey();
                    DataClass dataClass = new DataClass(str1,str2,str3);
                    dataList.add(dataClass);
                }

                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getSelected() {
        selected = getIntent().getStringExtra("name");
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
             binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }


}