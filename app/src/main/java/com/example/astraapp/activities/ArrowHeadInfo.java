package com.example.astraapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.astraapp.ImageViewer;
import com.example.astraapp.R;
import com.example.astraapp.databinding.ActivityArrowHeadInfoBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ArrowHeadInfo extends AppCompatActivity {

    private ActivityArrowHeadInfoBinding binding;
    DatabaseReference databaseReference;
    String name,imageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArrowHeadInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseApp.initializeApp(this);
        getSelected();
        getData();
        onclickfunc();


    }

    private void onclickfunc() {
        binding.arrowHeadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArrowHeadInfo.this, ImageViewer.class);
                intent.putExtra("imageurl",imageurl);
                startActivity(intent);
            }
        });
    }

    private void getData() {

        databaseReference = FirebaseDatabase.getInstance().getReference("arrow_heads").child(name);

        databaseReference.get().addOnCompleteListener(task -> {

            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {

                DataSnapshot dataSnapshot = task.getResult();

                imageurl = (String) dataSnapshot.child("imageurl").getValue();
                Glide.with(this).load(imageurl).into(binding.arrowHeadImage);
                binding.txtname.setText((CharSequence) dataSnapshot.child("name").getValue());
                binding.application.setText((CharSequence) dataSnapshot.child("application").getValue());
                binding.sanskritname.setText((CharSequence) dataSnapshot.child("sanskrit_name").getValue());
                binding.shape.setText((CharSequence) dataSnapshot.child("shape").getValue());

            }
        });


    }

    private void getSelected() {
        name = getIntent().getStringExtra("name");
    }
}