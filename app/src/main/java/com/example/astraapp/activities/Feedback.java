package com.example.astraapp.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.astraapp.R;
import com.example.astraapp.databinding.ActivityFeedbackBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {

    private ActivityFeedbackBinding binding;
    ImageButton btn;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        btn =  findViewById(R.id.buttonback);
        db = FirebaseFirestore.getInstance();

        onclickfunc();

    }

    private void onclickfunc() {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedata();
            }
        });

    }

    private void savedata() {

        Date currentDate = new Date();
        Map<String, String> data = new HashMap<>();
        data.put("email", String.valueOf(binding.gmail.getText()));
        data.put("message", String.valueOf(binding.feedbackEditText.getText()));
        data.put("date", String.valueOf(currentDate));

        db.collection("feedback").add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        showThankYouDialog();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showErrorDialog();
                    }
                });
    }

    private void showThankYouDialog() {

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_feedback_thankyou, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

        Button okButton = dialogView.findViewById(R.id.btn_ok);
        okButton.setOnClickListener(v -> {
            dialog.dismiss(); // Close dialog
            // Navigate to the home screen
            Intent intent = new Intent(Feedback.this, StartActivity.class);
            startActivity(intent);
            finish(); // Close current activity
        });

    }

    private void showErrorDialog() {

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_feedback_thankyou, null);
        TextView txt1 = dialogView.findViewById(R.id.txtmsg1);
        TextView txt2 = dialogView.findViewById(R.id.txtmsg2);
        txt1.setText("Error");
        txt2.setText("Error");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();


        Button okButton = dialogView.findViewById(R.id.btn_ok);
        okButton.setOnClickListener(v -> {
            dialog.dismiss(); // Close dialog
            // Navigate to the home screen
            Intent intent = new Intent(Feedback.this, StartActivity.class);
            startActivity(intent);
            finish(); // Close current activity
        });

    }
}