package com.google.firebase.samples.apps.mlkit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Context mContext;
    EditText mEtMobNo;
    TextView textView;
    CreateDatabase database = new CreateDatabase();
    int subjectId = 1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i("subjectLogin","in login activity");
        database.updateDateOfSubject(subjectId);
        mContext = getApplicationContext();
        mEtMobNo = findViewById(R.id.et_mobno);
        textView = findViewById(R.id.title);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, StudentAttendance.class));
            }
        });


        mEtMobNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if((motionEvent.getRawX() >= (mEtMobNo.getRight() - mEtMobNo.getCompoundDrawables()[2].getBounds().width() - 20))) {
                        startActivity(new Intent(mContext,OTPActivity.class));
                        finish();
                        return true;
                    }
                }
                return false;
            }
        });

    }

    private void initialize() {

    }
}
