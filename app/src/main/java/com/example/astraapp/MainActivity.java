package com.example.astraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<DataClass> dataList;
    MyAdapter myAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("astras");

        gridView = findViewById(R.id.gridView);

        dataList = new ArrayList<>();
        myAdapter = new MyAdapter(this,dataList);
        gridView.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    //dataList.add(dataClass);
                    String str1 = (String) dataSnapshot.child("name").getValue();
                    String str2 = (String) dataSnapshot.child("imageurl").getValue();
                    DataClass dataClass = new DataClass(str1,str2);
                    dataList.add(dataClass);
                    //System.out.println(str1+" "+str2);
                }

                System.out.println(dataList);
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}