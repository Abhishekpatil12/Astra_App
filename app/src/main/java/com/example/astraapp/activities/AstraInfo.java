package com.example.astraapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.astraapp.ImageViewer;
import com.example.astraapp.R;
import com.example.astraapp.databinding.ActivityAstraInfoBinding;
import com.example.astraapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AstraInfo extends AppCompatActivity {


    private ActivityAstraInfoBinding binding;
    String name;
    DatabaseReference databaseReference;
    String imageurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAstraInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseApp.initializeApp(this);
        getSelected();
        getData();
        onClickfunc();

    }

    private void onClickfunc() {

        binding.astraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AstraInfo.this, ImageViewer.class);
                intent.putExtra("imageurl",imageurl);
                startActivity(intent);
            }
        });
    }

    private void getSelected() {
        name = getIntent().getStringExtra("name");
    }

    private void getData() {

        databaseReference = FirebaseDatabase.getInstance().getReference("astras").child(name);

        databaseReference.get().addOnCompleteListener(task -> {

            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {

                DataSnapshot dataSnapshot = task.getResult();

                imageurl = (String) dataSnapshot.child("imageurl").getValue();
                Glide.with(this).load(imageurl).into(binding.astraImage);
                binding.astraUsedfor.setText((CharSequence) dataSnapshot.child("used_for").getValue());
                binding.astraCounteredby.setText((CharSequence) dataSnapshot.child("counter_by").getValue());
                binding.astraDeity.setText((CharSequence) dataSnapshot.child("deity").getValue());
            }
        });


    }
}