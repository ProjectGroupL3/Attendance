package com.google.firebase.samples.apps.mlkit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    Context mContext;
    Button btnVerify;

    private String verificationId;
    private FirebaseAuth mAuth;
    private EditText editText;
    String phoneNumber;
    boolean isStudent;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        btnVerify = findViewById(R.id.btn_verify);
        mContext = getApplicationContext();

        mAuth = FirebaseAuth.getInstance();
        editText = findViewById(R.id.editText);

        phoneNumber = getIntent().getStringExtra("phoneNumber");

        isStudent = getIntent().getBooleanExtra("isStudent",true);

        sendVerificationCode(phoneNumber);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = editText.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    editText.setError("Enter valid code!");
                    editText.requestFocus();
                    return;

                }

                verifyCode(code);
            }
        });
    }

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);

    }

    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(isStudent)
                            {
                                Intent intent = new Intent(OTPActivity.this,StudentAttendanceActivity.class);
                                intent.putExtra("phoneNumber",phoneNumber);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else
                            {
                                Intent intent = new Intent(OTPActivity.this, TeacherAttendanceActivity.class);
                                intent.putExtra("phoneNumber",phoneNumber);

                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }



                        } else {

                            Toast.makeText(OTPActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }

    private void sendVerificationCode(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60, TimeUnit.SECONDS, this, mCallBack);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {

                editText.setText(code);
                verifyCode(code);

            }
            else {
                Toast.makeText(mContext, "Instant Verified", Toast.LENGTH_SHORT).show();
                signInWithCredential(phoneAuthCredential);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };
}