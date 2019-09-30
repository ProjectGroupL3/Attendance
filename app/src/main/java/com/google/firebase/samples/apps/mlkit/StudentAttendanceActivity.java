package com.google.firebase.samples.apps.mlkit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.samples.apps.mlkit.adapters.StudentAttendanceAdapter;
import com.google.firebase.samples.apps.mlkit.models.StudentAttendanceModel;
import com.google.firebase.samples.apps.mlkit.models.StudentModel;
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
    private SharedPreferences sharedPreferences;
    private String nameOfSharedPref;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studence_attendance);

        getSupportActionBar().setTitle("StudentModel Attendance");
        mContext = this;
        studentNameTextView = (TextView) findViewById(R.id.textView2);
        sharedPref=new SharedPref(mContext);
        nameOfSharedPref = sharedPref.getPREF_NAME();
        sharedPreferences = sharedPref.getSharedPreferences();
        studentName = sharedPreferences.getString("NAME","");

        studentNameTextView.setText(studentName);


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentAttendanceAdapter = new StudentAttendanceAdapter(this, getMyList());

        mRecyclerView.setAdapter(studentAttendanceAdapter);

    }

    public ArrayList<StudentAttendanceModel> getMyList() {

        ArrayList<StudentAttendanceModel> studentAttendanceModels = new ArrayList<>();
        StudentAttendanceModel m =new StudentAttendanceModel();

        m.setSubject("CE DBMS TH");
        m.setPercent("100%");
        studentAttendanceModels.add(m);

        m =new StudentAttendanceModel();
        m.setSubject("CE CN TH");
        m.setPercent("100%");
        studentAttendanceModels.add(m);

        m =new StudentAttendanceModel();
        m.setSubject("CE TOC TH");
        m.setPercent("100%");
        studentAttendanceModels.add(m);

        m =new StudentAttendanceModel();
        m.setSubject("CE SE & PM TH");
        m.setPercent("100%");
        studentAttendanceModels.add(m);

        m =new StudentAttendanceModel();
        m.setSubject("CE IS & EE TH");
        m.setPercent("100%");
        studentAttendanceModels.add(m);

        m =new StudentAttendanceModel();
        m.setSubject("CE CN PR");
        m.setPercent("100%");
        studentAttendanceModels.add(m);

        m =new StudentAttendanceModel();
        m.setSubject("CE DBMS PR");
        m.setPercent("100%");
        studentAttendanceModels.add(m);

        m =new StudentAttendanceModel();
        m.setSubject("CE SDL PR");
        m.setPercent("100%");
        studentAttendanceModels.add(m);

        m =new StudentAttendanceModel();
        m.setSubject("CE SDL TUT");
        m.setPercent("100%");
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
