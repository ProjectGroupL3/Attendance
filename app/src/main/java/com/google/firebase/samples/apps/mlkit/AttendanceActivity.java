package com.google.firebase.samples.apps.mlkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.samples.apps.mlkit.adapters.StudentMarkAdapter;
import com.google.firebase.samples.apps.mlkit.models.StudentMarkRvModel;

import java.util.ArrayList;
import java.util.Arrays;

public class AttendanceActivity extends AppCompatActivity {
    Context mContext;
    RecyclerView recyclerView;
    StudentMarkAdapter attendanceAdapter;
    private Button markAttendanceButton;
    private UpdateAttendance updateAttendance;
    private ArrayList<String> presentStudentIds;
    private CreateDatabase createDatabase;
    private int subjectId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        mContext = getApplicationContext();
        markAttendanceButton = findViewById(R.id.mark_attendance_button);
        ArrayList<StudentMarkRvModel> models = new ArrayList<>();
        models.add(new StudentMarkRvModel("Harsh Saglani","31332",true,true));
        models.add(new StudentMarkRvModel("Yash Kasat","31343",true,true));
        models.add(new StudentMarkRvModel("Siddhant Hirve","31334",true,true));
        models.add(new StudentMarkRvModel("Mandar Jadhav","31334",true,true));
        models.add(new StudentMarkRvModel("Onkar Guha","31330",false,true));
        models.add(new StudentMarkRvModel("Manav Shaha","31323",true,true));
        models.add(new StudentMarkRvModel("Vedant Joshi","31332",true,true));
        models.add(new StudentMarkRvModel("Yashodhara Kulkarni","31343",true,true));
        models.add(new StudentMarkRvModel("Minal Kokade","31334",true,true));
        models.add(new StudentMarkRvModel("Mhosin Khan","31334",true,true));
        models.add(new StudentMarkRvModel("Kunal Kamra","31330",false,true));
        models.add(new StudentMarkRvModel("Carry Minati","31323",true,true));


        recyclerView = findViewById(R.id.rv_student_list);
        attendanceAdapter = new StudentMarkAdapter(mContext,models,2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(attendanceAdapter);

        presentStudentIds = new ArrayList<>(Arrays.asList("c2k17105589","c2k17105590"));
        subjectId = 1;
/*        updateAttendance = new UpdateAttendance(mContext,subjectId);
        updateAttendance.updateAttendanceOfSubject();
        for(String id : presentStudentIds)
        {
            updateAttendance.updateAttendanceOfStudent(id);
        }
        createDatabase = new CreateDatabase();
        createDatabase.addSubjects();*/

    }
}
