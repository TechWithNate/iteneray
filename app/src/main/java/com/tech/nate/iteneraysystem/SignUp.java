package com.tech.nate.iteneraysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
    private EditText phoneNumber;
    private MaterialButton sendOTPBtn;
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

        countryCode = countryCodePicker.getSelectedCountryCodeWithPlus();

        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = countryCodePicker.getSelectedCountryCodeWithPlus();
            }
        });

        sendOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String number;

                number = phoneNumber.getText().toString();
                if (number.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please Enter number", Toast.LENGTH_SHORT).show();
                }else if (number.length() < 10){
                    Toast.makeText(SignUp.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.setTitle("Verification");
                    progressDialog.setMessage("Sending OTP...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    tel = countryCode + number;

                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                            .setPhoneNumber(tel)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(SignUp.this)
                            .setCallbacks(mCallbacks)
                            .build();

                    PhoneAuthProvider.verifyPhoneNumber(options);
                }



            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                progressDialog.dismiss();

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressDialog.dismiss();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(SignUp.this, "OTP sent", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                code_sent = s;
                Intent intent = new Intent(SignUp.this, VerifyOTP.class);
                Toast.makeText(SignUp.this, "Code is "+ code_sent, Toast.LENGTH_SHORT).show();
                intent.putExtra("otp", code_sent);
                intent.putExtra("phone", tel);
                startActivity(intent);
            }
        };

    }




    private void initViews(){
        phoneNumber = findViewById(R.id.phone_number);
        sendOTPBtn = findViewById(R.id.send_btn);
        countryCodePicker = findViewById(R.id.ccp);
        progressDialog = new ProgressDialog(this);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseApp.initializeApp(/*context=*/ this);
//        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
//        firebaseAppCheck.installAppCheckProviderFactory(
//                PlayIntegrityAppCheckProviderFactory.getInstance());
//        super.onStart();
//        if (FirebaseAuth.getInstance().getCurrentUser() != null){
//            Intent intent = new Intent(SignUp.this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//    }


}