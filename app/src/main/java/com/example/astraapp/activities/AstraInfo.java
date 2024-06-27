package com.example.astraapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.astraapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AstraInfo extends AppCompatActivity {


    ImageView imageView;
    TextView txtusedfor,txtcounter,txtdeity;
    DatabaseReference databaseReference;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astra_info);

        FirebaseApp.initializeApp(this);

        String name = getIntent().getStringExtra("name");
        //System.out.println("abc "+name);

        imageView = findViewById(R.id.astra_image);
        txtusedfor = findViewById(R.id.astra_usedfor);
        txtcounter = findViewById(R.id.astra_counteredby);
        txtdeity = findViewById(R.id.astra_deity);
        context = this;


        databaseReference = FirebaseDatabase.getInstance().getReference("astras").child(name);

        databaseReference.get().addOnCompleteListener(task -> {

            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                //Log.d("firebase", String.valueOf(task.getResult().getValue()));
                DataSnapshot dataSnapshot = task.getResult();

                String imageurl = (String) dataSnapshot.child("imageurl").getValue();
                System.out.println(imageurl);

                Glide.with(context).load(imageurl).into(imageView);

                txtusedfor.setText((CharSequence) dataSnapshot.child("used_for").getValue());
                txtcounter.setText((CharSequence) dataSnapshot.child("counter_by").getValue());
                txtdeity.setText((CharSequence) dataSnapshot.child("deity").getValue());
            }
        });


    }
}