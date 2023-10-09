package com.example.lgroupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    Button otpbtn;
    CountryCodePicker ccp;
    EditText edtPhone;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        otpbtn=findViewById(R.id.getotpbtn);
        edtPhone=findViewById(R.id.edt_phone);
        progressBar=findViewById(R.id.progress_bar);
        ccp=findViewById(R.id.ccp);
        mAuth=FirebaseAuth.getInstance();
        ccp.registerCarrierNumberEditText(edtPhone);

        SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        boolean islogged = sharedPreferences.getBoolean("isLogin",false);
        if (islogged)
        {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }

        otpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum=ccp.getFullNumberWithPlus().replace(" ", "");
                SharedPreferences preferences=getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("mobile",phoneNum);
                editor.putBoolean("isLogin",true);
                editor.apply();
                if(phoneNum.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Enter your phone number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    otpbtn.setVisibility(View.INVISIBLE);

                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(phoneNum)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(LoginActivity.this)
                            .setCallbacks(
                                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                        @Override
                                        public void onCodeSent( String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                            progressBar.setVisibility(View.GONE);
                                            otpbtn.setVisibility(View.VISIBLE);
                                            Intent intent=new Intent(LoginActivity.this,OTPActivity.class);
                                            intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace("",""));
                                            intent.putExtra("verificationId",verificationId);
                                            startActivity(intent);

                                        }
                                        @Override
                                        public void onVerificationCompleted( PhoneAuthCredential phoneAuthCredential) {
                                            progressBar.setVisibility(View.GONE);
                                            otpbtn.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onVerificationFailed( FirebaseException e) {
                                            progressBar.setVisibility(View.GONE);
                                            otpbtn.setVisibility(View.VISIBLE);
                                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }


                                    }).build();
                    PhoneAuthProvider.verifyPhoneNumber(options);

                }
            }
        });
    }


}