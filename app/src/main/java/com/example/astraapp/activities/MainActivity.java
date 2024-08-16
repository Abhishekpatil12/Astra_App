package com.example.astraapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.ChangeTransform;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.astraapp.DisplayInfo;
import com.example.astraapp.R;
import com.example.astraapp.models.DataClass;
import com.example.astraapp.adapters.MyAdapter;
import com.example.astraapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    ArrayList<DataClass> dataList;
    MyAdapter myAdapter;
    DatabaseReference databaseReference;
    String selected;
    Intent intent;
    LinearLayout layout1;
    private boolean isClicked;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        layout1 = binding.sectioninfolayout;

        FirebaseApp.initializeApp(this);

        layout1.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);




        db = FirebaseFirestore.getInstance();


        getSelected();
        initfunc();
        getData();
        onclickfunc();



    }

    private void onclickfunc() {

        binding.gridView.setOnItemClickListener((adapterView, view, i, l) -> {


//            if(selected.equals("arrow_heads"))
//            {
//                intent = new Intent(MainActivity.this, ArrowHeadInfo.class);
//            }
//            else if(selected.equals("astras"))
//            {
//                intent = new Intent(MainActivity.this, AstraInfo.class);
//            }
//            else if(selected.equals("vyuham"))
//            {
//                intent = new Intent(MainActivity.this, VyuhamInfo.class);
//            }

            intent = new Intent(MainActivity.this, DisplayInfo.class);

            String str =  dataList.get(i).getKey();
            intent.putExtra("selected",selected);
            intent.putExtra("name",str);
            startActivity(intent);
        });

        binding.btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                expand(view);

            }
        });
    }

    private void initfunc() {

        dataList = new ArrayList<>();
        myAdapter = new MyAdapter(this,dataList);
        binding.gridView.setAdapter(myAdapter);

        if(selected.equals("arrow_heads"))
        {
            binding.infoCard.setVisibility(View.GONE);
        }
        else if(selected.equals("astras"))
        {
            binding.infoCard.setVisibility(View.VISIBLE);
            binding.sectioninfo.setText("What is a Astra? Click to know");
            binding.sectiontext.setText("Astra in ancient Indian history refers to a supernatural weapon associated with a specific deity, possessing unique powers.Their use required knowledge of mantras, concentration, and divine blessings, making them potent tools in epic battles. The Astras lack a definitive form and shape. They can be invoked in any material thing using mantras, even in piece of a grass.");
        }
        else if(selected.equals("vyuham"))
        {
            binding.infoCard.setVisibility(View.VISIBLE);
            binding.sectioninfo.setText("What is a Vyuham? Click to know");
            binding.sectiontext.setText("Vyuham in ancient Indian history refers to a strategic military formation used in battles. These formations were meticulously planned and executed to gain tactical advantages over the enemy. Vyuhams required precise coordination and discipline among the warriors, with each position playing a specific role in the overall strategy. They showcased advanced military tactics and the importance of planning in ancient warfare.");
        }
    }

    private void getData() {

        loading(true);
//        db.collection(selected).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                if(task.isSuccessful()){
//                    loading(false);
//                    for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
//                        Map<String, Object> mp = documentSnapshot.getData();
//                        //System.out.println("DATA : "+documentSnapshot.getData());
//                        String str1 = (String) mp.get("name");
//                        String str2 = (String) mp.get("imageurl");
//                        String str3 = (String) mp.get("id");
//                        DataClass dataClass = new DataClass(str1,str2,str3);
//                        dataList.add(dataClass);
//                    }
//                    myAdapter.notifyDataSetChanged();
//                }
//            }
//        });

        databaseReference = FirebaseDatabase.getInstance().getReference(selected);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                System.out.println(snapshot.child("meaning").getValue());
//
//                    Map<String, Object> user = new HashMap<>();
//                    user.put("id", "naracha_nalika_sataghani");
//                    user.put("imageurl",snapshot.child("imageurl").getValue());
//                    user.put("name",snapshot.child("name").getValue());
//                    user.put("sanskrit_name", snapshot.child("sanskrit_name").getValue());
//                    user.put("shloka",snapshot.child("shloka").getValue());
//                    user.put("meaning",snapshot.child("meaning").getValue());
//
//                db.collection("info").document("naracha_nalika_sataghani").set(user)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    System.out.println("done");
//                                }
//                            });

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

//                    Map<String, Object> user = new HashMap<>();
//                    user.put("id", dataSnapshot.child("id").getValue());
//                    user.put("imageurl",dataSnapshot.child("imageurl").getValue());
//                    user.put("name",dataSnapshot.child("name").getValue());
//                    user.put("sanskrit_name", dataSnapshot.child("sanskrit_name").getValue());
//                    user.put("info",dataSnapshot.child("info").getValue());
//                    user.put("holding",dataSnapshot.child("holding").getValue());
//
//
//                    db.collection("naracha_nalika_sataghani").document(str3).set(user)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    System.out.println("done");
//                                }
//                            });


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


    public void expand(View view) {
        if(!isClicked)
        {
            isClicked = true;
            binding.btnExpand.setImageDrawable(getResources().getDrawable(R.drawable.baseline_keyboard_arrow_up_24));
        }
        else
        {
            isClicked = false;
            binding.btnExpand.setImageDrawable(getResources().getDrawable(R.drawable.baseline_keyboard_arrow_down_24));
        }

        int v = (binding.sectiontext.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(layout1,new AutoTransition());
        binding.sectiontext.setVisibility(v);
    }
}