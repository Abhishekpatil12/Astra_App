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

import com.example.astraapp.R;
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

    TextView sectioninfo;
    LinearLayout layout1;

    LinearLayout info_card;
    ImageButton btnExpand;
    private boolean isClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        layout1 = binding.sectioninfolayout;

        FirebaseApp.initializeApp(this);

        layout1.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        btnExpand = binding.btnExpand;

        info_card = binding.infoCard;

        btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isClicked)
                {
                    isClicked = true;
                    btnExpand.setImageDrawable(getResources().getDrawable(R.drawable.baseline_keyboard_arrow_up_24));
                }
                else
                {
                    isClicked = false;
                    btnExpand.setImageDrawable(getResources().getDrawable(R.drawable.baseline_keyboard_arrow_down_24));
                }

                int v = (binding.sectiontext.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
                TransitionManager.beginDelayedTransition(layout1,new AutoTransition());
                binding.sectiontext.setVisibility(v);
            }
        });

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

                    if(selected.equals("arrow_heads"))
                    {
                        info_card.setVisibility(View.GONE);
                    }
                    else if(selected.equals("astras"))
                    {
                        info_card.setVisibility(View.VISIBLE);
                        binding.sectioninfo.setText("What is a Astra? Click to know");
                        binding.sectiontext.setText("Astra in ancient Indian history refers to a supernatural weapon associated with a specific deity, possessing unique powers.Their use required knowledge of mantras, concentration, and divine blessings, making them potent tools in epic battles. The Astras lack a definitive form and shape. They can be invoked in any material thing using mantras, even in piece of a grass.");
                    }
                    else if(selected.equals("vyuham"))
                    {
                        info_card.setVisibility(View.VISIBLE);
                        binding.sectioninfo.setText("What is a Vyuham? Click to know");
                        binding.sectiontext.setText("Vyuham in ancient Indian history refers to a strategic military formation used in battles. These formations were meticulously planned and executed to gain tactical advantages over the enemy. Vyuhams required precise coordination and discipline among the warriors, with each position playing a specific role in the overall strategy. They showcased advanced military tactics and the importance of planning in ancient warfare.");
                    }
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
            btnExpand.setImageDrawable(getResources().getDrawable(R.drawable.baseline_keyboard_arrow_up_24));
        }
        else
        {
            isClicked = false;
            btnExpand.setImageDrawable(getResources().getDrawable(R.drawable.baseline_keyboard_arrow_down_24));
        }

        int v = (binding.sectiontext.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(layout1,new AutoTransition());
        binding.sectiontext.setVisibility(v);
    }
}