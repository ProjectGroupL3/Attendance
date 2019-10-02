package com.google.firebase.samples.apps.mlkit;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.samples.apps.mlkit.models.StudentModel;
import com.google.firebase.samples.apps.mlkit.others.SharedPref;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView profileImage;

    TextView studentIdView;
    TextView nameView;
    TextView classNameView;
    TextView phoneNumberView;
    SharedPref sharedPref;
    Context mContext;
    Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        logoutButton = findViewById(R.id.btn_logout);
        studentIdView = findViewById(R.id.tv_profile_id);
        nameView = findViewById(R.id.tv_profile_name);
        classNameView = findViewById(R.id.tv_profile_class);
        profileImage = findViewById(R.id.profile_image);
        mContext = this;
        sharedPref = new SharedPref(mContext);
        studentIdView.setText(sharedPref.getID());
        nameView.setText(sharedPref.getNAME());
        classNameView.setText(sharedPref.getDIVISION());
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPref.logout();
                sharedPref.setIsLoggedIn(false);
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                finishAffinity();
                startActivity(intent);
                finish();
            }
        });

        getSupportActionBar().setTitle("Profile Activity");

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                startActivityForResult(Intent.createChooser(intent, "Pick a Image"), 1);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1) {

            CircleImageView profileImage = findViewById(R.id.profile_image);

            try {

                InputStream inputStream = getContentResolver().openInputStream(data.getData());

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                profileImage.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {


                e.printStackTrace();
            }

        }

    }

}
