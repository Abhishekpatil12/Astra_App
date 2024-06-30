package com.example.astraapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.example.astraapp.ImageViewer;
import com.example.astraapp.R;
import com.example.astraapp.databinding.ActivityVyuhamInfoBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VyuhamInfo extends AppCompatActivity {

    private ActivityVyuhamInfoBinding binding;
    DatabaseReference databaseReference;
    String name,imageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVyuhamInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseApp.initializeApp(this);
        getSelected();
        getData();
        onclickfunc();
        showpopup();
    }

    private void showpopup() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        View rootview = findViewById(android.R.id.content);
        rootview.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(rootview, Gravity.CENTER,0,0);
            }
        });


        Button okButton = popupView.findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        ImageView imageView = popupView.findViewById(R.id.popup_image);
        imageView.setImageResource(R.drawable.symbolinfo);
    }

    private void onclickfunc() {
        binding.vyuhamImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VyuhamInfo.this, ImageViewer.class);
                intent.putExtra("imageurl",imageurl);
                startActivity(intent);
            }
        });

        binding.infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpopup();
            }
        });
    }

    private void getData() {

        databaseReference = FirebaseDatabase.getInstance().getReference("vyuham").child(name);

        databaseReference.get().addOnCompleteListener(task -> {

            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {

                DataSnapshot dataSnapshot = task.getResult();

                imageurl = (String) dataSnapshot.child("imageurl").getValue();
                Glide.with(this).load(imageurl).into(binding.vyuhamImage);
                binding.txtname.setText((CharSequence) dataSnapshot.child("name").getValue());
                binding.position.setText((CharSequence) dataSnapshot.child("position_of_the_army").getValue());
                binding.sanskritname.setText((CharSequence) dataSnapshot.child("sanskrit_name").getValue());
                binding.whentouse.setText((CharSequence) dataSnapshot.child("when_to_use_it").getValue());

            }
        });


    }

    private void getSelected() {
        name = getIntent().getStringExtra("name");
    }
}