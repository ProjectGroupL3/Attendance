package com.google.firebase.samples.apps.mlkit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.TextView;

import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.samples.apps.mlkit.adapters.StudentAttendanceAdapter;
import com.google.firebase.samples.apps.mlkit.models.StudentAttendanceModel;
import com.google.firebase.samples.apps.mlkit.others.SharedPref;

import java.util.ArrayList;

public class StudentAttendanceActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference studentCollection = db.collection("studentCollection");
    private RecyclerView mRecyclerView;
    private StudentAttendanceAdapter studentAttendanceAdapter;
    private TextView studentNameTextView;
    private String studentName;
    private SharedPref sharedPref;
    private Context mContext;

    CardView profileCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studence_attendance);

        getSupportActionBar().setTitle("StudentModel Attendance");
        mContext = this;
        studentNameTextView = (TextView) findViewById(R.id.textView2);
        sharedPref=new SharedPref(mContext);

        studentName = sharedPref.getNAME();
        studentNameTextView = findViewById(R.id.tv_student_name);
        studentNameTextView.setText(studentName);


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        profileCard = findViewById(R.id.cv_student_info);
        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentAttendanceActivity.this,ProfileActivity.class));
            }
        });

        studentAttendanceAdapter = new StudentAttendanceAdapter(this, getMyList());

        mRecyclerView.setAdapter(studentAttendanceAdapter);

    }

    public ArrayList<StudentAttendanceModel> getMyList() {

        ArrayList<StudentAttendanceModel> studentAttendanceModels = new ArrayList<>();
        StudentAttendanceModel m =new StudentAttendanceModel();

        m.setSubject("Ce Cnl Pr");
        m.setType("Practical");
        m.setPercent("83.33%");
        studentAttendanceModels.add(m);

        m =new StudentAttendanceModel();
        m.setSubject("Ce Computer Networks Th");
        m.setType("Lecture");
        m.setPercent("86.79%");
        studentAttendanceModels.add(m);

        m =new StudentAttendanceModel();
        m.setSubject("Ce Database Management Systems Th");
        m.setType("Lecture");
        m.setPercent("82.86%");
        studentAttendanceModels.add(m);

        m =new StudentAttendanceModel();
        m.setSubject("Ce Dbmsl Pr");
        m.setType("Practical");
        m.setPercent("85.0%");
        studentAttendanceModels.add(m);

        m =new StudentAttendanceModel();
        m.setSubject("Ce Information Systems And Engineering Economics Th");
        m.setType("Lecture");
        m.setPercent("87.88%");
        studentAttendanceModels.add(m);

        m =new StudentAttendanceModel();
        m.setSubject("Ce Skills Development Lab Pr");
        m.setType("Practical");
        m.setPercent("91.3%");
        studentAttendanceModels.add(m);

        m =new StudentAttendanceModel();
        m.setSubject("Ce Skills Development Lab Pr");
        m.setType("Practical");
        m.setPercent("76.67%");
        studentAttendanceModels.add(m);

        m =new StudentAttendanceModel();
        m.setSubject("Ce Software Engineering & Project Management Th");
        m.setType("Lecture");
        m.setPercent("69.23%");
        studentAttendanceModels.add(m);

        m =new StudentAttendanceModel();
        m.setSubject("Ce Theory of Computation");
        m.setType("Lecture");
        m.setPercent("82.93%");
        studentAttendanceModels.add(m);

        return studentAttendanceModels;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.student_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {

            case R.id.myprofile :

                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                return true;

            case R.id.logout:

                return true;

            default:
                return false;
        }
    }

}
