package com.google.firebase.samples.apps.mlkit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.samples.apps.mlkit.models.StudentModel;
import com.google.firebase.samples.apps.mlkit.models.SubjectModel;

public class LoginActivity extends AppCompatActivity {

    Context mContext;
    Context context;
    EditText mEtMobNo;
    TextView textView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference teacherCollection = db.collection("teacherCollection");
    CollectionReference studentCollection = db.collection("studentCollection");
    CreateDatabase database = new CreateDatabase();
    int subjectId = 1;
    String phoneNumber;
    Button sendPhoneNumber;
    boolean isStudent=false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i("subjectLogin","in login activity");
       // database.updateDateOfSubject(subjectId);
        //database.addStudents();
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

        sendPhoneNumber = (Button) findViewById(R.id.sendPhoneNumber);
        sendPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber=mEtMobNo.getText().toString().trim();
                if(phoneNumber.isEmpty() || phoneNumber.length()<10)
                {
                    mEtMobNo.setError("valid number is required");
                    mEtMobNo.requestFocus();
                    return;
                }

                Log.i("phone ",phoneNumber);
                studentCollection.whereEqualTo("phoneNumber",phoneNumber).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty())
                        {
                            isStudent=true;
                            phoneNumber="+91"+phoneNumber;

                            Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                            intent.putExtra("phoneNumber", phoneNumber);
                            intent.putExtra("isStudent",isStudent);
                            startActivity(intent);
                        }
                        else{
                            teacherCollection.whereEqualTo("phoneNumber",phoneNumber).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if (queryDocumentSnapshots.isEmpty()) {
                                        Toast.makeText(mContext, "Enter valid phone number !!", Toast.LENGTH_SHORT).show();
                                    } else{
                                        isStudent=false;
                                        phoneNumber="+91"+phoneNumber;

                                        Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                                        intent.putExtra("phoneNumber", phoneNumber);
                                        intent.putExtra("isStudent",isStudent);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }

                    }
                });


            }
        });


    }

    private void initialize() {

    }
}
