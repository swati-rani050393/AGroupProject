package com.example.lgroupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {
    EditText inputcode1,inputcode2,inputcode3,inputcode4,inputcode5,inputcode6;
    TextView txtresend,phoneNo,timertxt;
    Button verifybtn;
    String verificationId;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    private CountDownTimer resendTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        inputcode1=findViewById(R.id.inputcode1);
        inputcode2=findViewById(R.id.inputcode2);
        inputcode3=findViewById(R.id.inputcode3);
        inputcode4=findViewById(R.id.inputcode4);

        inputcode5=findViewById(R.id.inputcode5);
        inputcode6=findViewById(R.id.inputcode6);
        txtresend=findViewById(R.id.txt_resend);
        progressBar=findViewById(R.id.pb_verify);
        verifybtn=findViewById(R.id.verifybtn);
        phoneNo=findViewById(R.id.phoneno);
        mAuth=FirebaseAuth.getInstance();
        phoneNo.setText(getIntent().getStringExtra("mobile"));

        txtresend.setEnabled(false);
        resendTimer = createResendTimer();



        setupotpInputs();
        verificationId=getIntent().getStringExtra("verificationId");

        txtresend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendOTP();
            }
        });
        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputcode1.getText().toString().trim().isEmpty()
                        ||inputcode2.getText().toString().trim().isEmpty()
                        ||inputcode3.getText().toString().trim().isEmpty()
                        ||inputcode4.getText().toString().trim().isEmpty()
                        || inputcode5.getText().toString().trim().isEmpty()
                        ||inputcode6.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(OTPActivity.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code=inputcode1.getText().toString()+
                        inputcode2.getText().toString()+
                        inputcode3.getText().toString()+
                        inputcode4.getText().toString()+
                        inputcode5.getText().toString()+
                        inputcode6.getText().toString() ;
                if(verificationId!=null)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    verifybtn.setVisibility(View.INVISIBLE);
                    resendTimer.cancel(); // Cancel the existing OTP verification timer
                    createResendTimer();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });




    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(OTPActivity.this,MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(OTPActivity.this, "SignIn code Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }
    private void setupotpInputs(){

        inputcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    private CountDownTimer createResendTimer() {
        resendTimer=new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                // String timeText = String.format(Locale.getDefault(), "Resend OTP in: %02d:%02d", seconds / 60, seconds % 60);
                // Update the Resend button text with the countdown
                txtresend.setText(String.valueOf(millisUntilFinished/1000));

                if (seconds <= 10) {
                    // Enable the Resend button
                    txtresend.setEnabled(true);
                } else {
                    // Disable the Resend button if not within the threshold
                    txtresend.setEnabled(false);
                }
            }

            public void onFinish() {
                // Enable the Resend button and reset its text

                // Enable the Resend button
                txtresend.setEnabled(true);
                txtresend.setText("Resend OTP");



            }
        };
        return    resendTimer.start();
    }
    private void resendOTP() {
        // Disable the Resend button to prevent multiple clicks
        txtresend.setEnabled(false);

        // Generate a new OTP by re-sending the verification code
        String phone=getIntent().getStringExtra("mobile");
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(OTPActivity.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                  @Override
                                  public void onCodeSent(String newVerificationId,
                                                         PhoneAuthProvider.ForceResendingToken token) {
                                      // New OTP code has been sent successfully
                                      // Store the new verificationId if needed
                                      verificationId = newVerificationId;
                                  }

                                  @Override
                                  public void onVerificationCompleted(PhoneAuthCredential credential) {
                                      // Automatic SMS verification (not used in this example)
                                  }
                                  @Override
                                  public void onVerificationFailed(FirebaseException e) {
                                      // Handle errors during OTP resend
                                      Toast.makeText(OTPActivity.this, "OTP Resend Error", Toast.LENGTH_SHORT).show();
                                  }
                              }
                ).build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }
}