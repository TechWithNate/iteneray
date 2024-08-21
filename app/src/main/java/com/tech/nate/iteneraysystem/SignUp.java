package com.tech.nate.iteneraysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "Login";
    private EditText email;
    private EditText password;
    private EditText confirm_password;
    private MaterialButton createBtn;
    private CountryCodePicker countryCodePicker;
    String countryCode;
    String tel;
    private ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String code_sent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        initViews();
        //verify();

        firebaseAuth = FirebaseAuth.getInstance();

        createBtn.setOnClickListener(view -> checkFields());

//        countryCode = countryCodePicker.getSelectedCountryCodeWithPlus();
//
//        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
//            @Override
//            public void onCountrySelected() {
//                countryCode = countryCodePicker.getSelectedCountryCodeWithPlus();
//            }
//        });
//
//        sendOTPBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String number;
//
//                number = phoneNumber.getText().toString();
//                if (number.isEmpty()) {
//                    Toast.makeText(SignUp.this, "Please Enter number", Toast.LENGTH_SHORT).show();
//                }else if (number.length() < 10){
//                    Toast.makeText(SignUp.this, "Please enter correct phone number", Toast.LENGTH_SHORT).show();
//                }else{
//                    progressDialog.setTitle("Verification");
//                    progressDialog.setMessage("Sending OTP...");
//                    progressDialog.setCanceledOnTouchOutside(false);
//                    progressDialog.show();
//
//                    tel = countryCode + number;
//
//                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
//                            .setPhoneNumber(tel)
//                            .setTimeout(60L, TimeUnit.SECONDS)
//                            .setActivity(SignUp.this)
//                            .setCallbacks(mCallbacks)
//                            .build();
//
//                    PhoneAuthProvider.verifyPhoneNumber(options);
//                }
//
//
//
//            }
//        });


//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                progressDialog.dismiss();
//
//            }
//
//            @Override
//            public void onVerificationFailed(@NonNull FirebaseException e) {
//                progressDialog.dismiss();
//                Toast.makeText(SignUp.this, "Verification Sent", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                super.onCodeSent(s, forceResendingToken);
//                Toast.makeText(SignUp.this, "OTP sent", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//                code_sent = s;
//                Intent intent = new Intent(SignUp.this, VerifyOTP.class);
//                Toast.makeText(SignUp.this, "Code is "+ s, Toast.LENGTH_SHORT).show();
//                intent.putExtra("otp", code_sent);
//                intent.putExtra("phone", tel);
//                startActivity(intent);
//            }
//        };


    }


    private void checkFields(){
        if (email.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
        }else if (password.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else if (!password.getText().toString().equals(confirm_password.getText().toString())) {
            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().length() < 8) {
            Toast.makeText(this, "Password less than 8", Toast.LENGTH_SHORT).show();
        }else{
            createAccount(email.getText().toString(), password.getText().toString());
        }
    }

    private void createAccount(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(SignUp.this, Home.class));
                    finish();
                }else{
                    Toast.makeText(SignUp.this, "Error: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "Error: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
        });
    }

    private void initViews(){
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        createBtn = findViewById(R.id.create_account_btn);

    }



}