package com.google.firebase.samples.apps.mlkit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Context mContext;
    Context context;
    EditText mEtMobNo;
    TextView textView;
    CreateDatabase database = new CreateDatabase();
    int subjectId = 1;
    String phoneNumber;
    Button sendPhoneNumber;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i("subjectLogin","in login activity");
       // database.updateDateOfSubject(subjectId);
        mContext = getApplicationContext();
        context=this;
        mEtMobNo = (EditText)findViewById(R.id.et_mobno);

      //  Log.i("phone ",phoneNumber);
        textView = findViewById(R.id.title);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, StudentAttendanceActivity.class));
            }
        });

        mEtMobNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if((motionEvent.getRawX() >= (mEtMobNo.getRight() - mEtMobNo.getCompoundDrawables()[2].getBounds().width() - 20))) {
                        if(validate())
                            validateUserLogin();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void validateUserLogin() {
        phoneNumber=mEtMobNo.getText().toString().trim();
        phoneNumber="+91"+phoneNumber;
        Log.i("phone ",phoneNumber);
        Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
        intent.putExtra("phoneNumber", phoneNumber);
        startActivity(intent);
    }

    private boolean validate() {
        if(mEtMobNo.getText().length() != 10) {
            mEtMobNo.setError("Invalid mobile number ");
            mEtMobNo.requestFocus();
            return false;
        }
        return true;
    }

    private void initialize()
    {

    }
}
