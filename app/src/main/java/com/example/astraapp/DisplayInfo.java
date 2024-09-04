package com.example.astraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.astraapp.databinding.ActivityDisplayInfoBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DisplayInfo extends AppCompatActivity {

    private ActivityDisplayInfoBinding binding;
    DatabaseReference databaseReference;
    String name,selected,imageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseApp.initializeApp(this);
        getSelected();
        onClickfunc();
        getData();

    }

    private void onClickfunc() {

        binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayInfo.this, ImageViewer.class);
                intent.putExtra("imageurl",imageurl);
                startActivity(intent);
            }
        });

        binding.infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DisplayInfo.this, ImageViewer.class);
                intent.putExtra("imageurl","https://firebasestorage.googleapis.com/v0/b/astra-bcf1a.appspot.com/o/Vyuham%2FSymbol-2-info.png?alt=media&token=d62a4b34-ed22-4e02-af58-594db3d0ae72");
                startActivity(intent);

            }
        });
    }

    private void getData() {
        databaseReference = FirebaseDatabase.getInstance().getReference(selected).child(name);

        databaseReference.get().addOnCompleteListener(task -> {

            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {

                DataSnapshot dataSnapshot = task.getResult();

                imageurl = (String) dataSnapshot.child("imageurl").getValue();
                Glide.with(this).load(imageurl).into(binding.image);

                if(selected.equals("arrow_heads")){

                    binding.first.setText("Application : ");
                    binding.second.setText("Sanskrit Name : ");
                    binding.third.setText("Shape :");
                    ViewGroup parent = (ViewGroup) binding.fourth.getParent();
                    if (parent != null) {
                        parent.removeView(binding.fourth);
                    }


                    binding.displayFirst.setText((CharSequence) dataSnapshot.child("application").getValue());
                    binding.displaySecond.setText((CharSequence) dataSnapshot.child("sanskrit_name").getValue());
                    binding.displayThird.setText((CharSequence) dataSnapshot.child("shape").getValue());

                }
                else if(selected.equals("astras")){

                    binding.first.setText("Name : ");
                    binding.second.setText("Used for : ");
                    binding.third.setText("Counter By :");
                    binding.fourth.setText("Deity :");
                    binding.displayFirst.setText((CharSequence) dataSnapshot.child("name").getValue());
                    binding.displaySecond.setText((CharSequence) dataSnapshot.child("used_for").getValue());
                    binding.displayThird.setText((CharSequence) dataSnapshot.child("counter_by").getValue());
                    binding.displayFourth.setText((CharSequence) dataSnapshot.child("deity").getValue());

                }
                else if(selected.equals("dhanurmushti")){

                    binding.first.setText("Name : ");
                    binding.second.setText("Used for : ");
                    binding.third.setText("Position :");

                    ViewGroup parent = (ViewGroup) binding.fourth.getParent();
                    if (parent != null) {
                        parent.removeView(binding.fourth);
                    }

                    binding.displayFirst.setText((CharSequence) dataSnapshot.child("name").getValue());
                    binding.displaySecond.setText((CharSequence) dataSnapshot.child("used_for").getValue());
                    binding.displayThird.setText((CharSequence) dataSnapshot.child("position").getValue());

                }
                else if(selected.equals("gunmushti")){

                    binding.first.setText("Name : ");
                    binding.second.setText("Sanskrit Name : ");
                    binding.third.setText("Use :");
                    binding.fourth.setText("Holding :");
                    binding.displayFirst.setText((CharSequence) dataSnapshot.child("name").getValue());
                    binding.displaySecond.setText((CharSequence) dataSnapshot.child("sanskrit_name").getValue());
                    binding.displayThird.setText((CharSequence) dataSnapshot.child("use").getValue());
                    binding.displayFourth.setText((CharSequence) dataSnapshot.child("holding").getValue());

                }
                else if(selected.equals("lakshya")){

                    binding.first.setText("Name : ");
                    binding.second.setText("Sanskrit Name : ");
                    binding.third.setText("Aimming :");

                    ViewGroup parent = (ViewGroup) binding.fourth.getParent();
                    if (parent != null) {
                        parent.removeView(binding.fourth);
                    }

                    binding.displayFirst.setText((CharSequence) dataSnapshot.child("name").getValue());
                    binding.displaySecond.setText((CharSequence) dataSnapshot.child("sanskrit_name").getValue());
                    binding.displayThird.setText((CharSequence) dataSnapshot.child("aiming").getValue());

                }
                else if(selected.equals("modes_of_shooting_arrow")){

                    binding.first.setText("Name : ");
                    binding.second.setText("Sanskrit Name : ");
                    binding.third.setText("Pose :");

                    ViewGroup parent = (ViewGroup) binding.fourth.getParent();
                    if (parent != null) {
                        parent.removeView(binding.fourth);
                    }

                    binding.displayFirst.setText((CharSequence) dataSnapshot.child("name").getValue());
                    binding.displaySecond.setText((CharSequence) dataSnapshot.child("sanskrit_name").getValue());
                    binding.displayThird.setText((CharSequence) dataSnapshot.child("pose").getValue());

                }
                else if(selected.equals("naracha_nalika_sataghani")){

                    binding.first.setText("Name : ");
                    binding.second.setText("Sanskrit Name : ");
                    binding.third.setText("Info :");

                    ViewGroup parent = (ViewGroup) binding.fourth.getParent();
                    if (parent != null) {
                        parent.removeView(binding.fourth);
                    }

                    binding.displayFirst.setText((CharSequence) dataSnapshot.child("name").getValue());
                    binding.displaySecond.setText((CharSequence) dataSnapshot.child("sanskrit_name").getValue());
                    binding.displayThird.setText((CharSequence) dataSnapshot.child("info").getValue());

                }
                else if(selected.equals("vyuham")){

                    binding.first.setText("Name : ");
                    binding.second.setText("Sanskrit Name : ");
                    binding.third.setText("When to Use :");
                    binding.fourth.setText("Position :");
                    binding.displayFirst.setText((CharSequence) dataSnapshot.child("name").getValue());
                    binding.displaySecond.setText((CharSequence) dataSnapshot.child("sanskrit_name").getValue());
                    binding.displayThird.setText((CharSequence) dataSnapshot.child("when_to_use_it").getValue());
                    binding.displayFourth.setText((CharSequence) dataSnapshot.child("position_of_the_army").getValue());
                    binding.infoButton.setVisibility(View.VISIBLE);

                }
            }
        });

    }

    private void getSelected() {
        selected = getIntent().getStringExtra("selected");
        name = getIntent().getStringExtra("name");
        System.out.println(selected+" "+name);
    }
}