package com.google.firebase.samples.apps.mlkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity {
    Context mContext;
    RecyclerView recyclerView;
    StudentAttendanceAdapter attendanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        mContext = getApplicationContext();

        ArrayList<StudentModel> models = new ArrayList<>();
        models.add(new StudentModel("Harsh Saglani","31332",true,true));
        models.add(new StudentModel("Yash Kasat","31343",true,true));
        models.add(new StudentModel("Siddhant Hirve","31334",true,true));
        models.add(new StudentModel("Mandar Jadhav","31334",true,true));
        models.add(new StudentModel("Onkar Guha","31330",false,true));
        models.add(new StudentModel("Manav Shaha","31323",true,true));
        models.add(new StudentModel("Vedant Joshi","31332",true,true));
        models.add(new StudentModel("Yashodhara Kulkarni","31343",true,true));
        models.add(new StudentModel("Minal Kokade","31334",true,true));
        models.add(new StudentModel("Mhosin Khan","31334",true,true));
        models.add(new StudentModel("Kunal Kamra","31330",false,true));
        models.add(new StudentModel("Carry Minati","31323",true,true));


        recyclerView = findViewById(R.id.rv_student_list);
        attendanceAdapter = new StudentAttendanceAdapter(mContext,models,2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(attendanceAdapter);
    }
}
