package com.tech.nate.iteneraysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class VerifyOTP extends AppCompatActivity {


    private EditText email;
    private EditText password;
    private MaterialButton login_btn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        initViews();

        login_btn.setOnClickListener(v -> {
            checkFields();
        });

    }

    private void checkFields(){
        if (email.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
        }else if (password.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }else{
           login(email.getText().toString(), password.getText().toString());
        }
    }

    private void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        startActivity(new Intent(VerifyOTP.this, Home.class));
                        finish();
                    }else{
                        Toast.makeText(VerifyOTP.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(VerifyOTP.this, "Error: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
    }

    private void initViews(){
        login_btn = findViewById(R.id.login_btn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
    }

}