package com.google.firebase.samples.apps.mlkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Context mContext;
    EditText mEtMobNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = getApplicationContext();
        mEtMobNo = findViewById(R.id.et_mobno);


        mEtMobNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if((motionEvent.getRawX() >= (mEtMobNo.getRight() - mEtMobNo.getCompoundDrawables()[2].getBounds().width() - 20))) {
                        startActivity(new Intent(mContext,OTPActivity.class));
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
