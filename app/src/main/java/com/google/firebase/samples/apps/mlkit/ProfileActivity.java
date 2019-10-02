package com.google.firebase.samples.apps.mlkit;


import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.samples.apps.mlkit.models.StudentModel;
import com.google.firebase.samples.apps.mlkit.others.SharedPref;

public class ProfileActivity extends AppCompatActivity {
    TextView studentIdView;
    TextView nameView;
    TextView classNameView;
    TextView phoneNumberView;
    SharedPref sharedPref;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        studentIdView = findViewById(R.id.tv_profile_id);
        nameView = findViewById(R.id.tv_profile_name);
        classNameView = findViewById(R.id.tv_profile_class);
        mContext = this;
        sharedPref = new SharedPref(mContext);
        studentIdView.setText(sharedPref.getID());
        nameView.setText(sharedPref.getNAME());
        classNameView.setText(sharedPref.getDIVISION());

        getSupportActionBar().setTitle("ProfileActivity");
    }
}
