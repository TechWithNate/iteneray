package com.tech.nate.iteneraysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

public class VerifyOTP extends AppCompatActivity {


    private MaterialButton verify_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        initViews();

        verify_btn.setOnClickListener(v -> {
            startActivity(new Intent(VerifyOTP.this, Home.class));
        });

    }

    private void initViews(){
        verify_btn = findViewById(R.id.send_btn);
    }

}