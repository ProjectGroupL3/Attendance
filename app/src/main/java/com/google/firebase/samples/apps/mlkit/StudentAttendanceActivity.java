package com.google.firebase.samples.apps.mlkit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.samples.apps.mlkit.adapters.StudentAttendanceAdapter;
import com.google.firebase.samples.apps.mlkit.models.StudentAttendanceModel;
import com.google.firebase.samples.apps.mlkit.models.StudentModel;
import com.google.firebase.samples.apps.mlkit.models.SubjectInStudentModel;
import com.google.firebase.samples.apps.mlkit.models.SubjectModel;
import com.google.firebase.samples.apps.mlkit.others.SharedPref;

import java.util.ArrayList;

public class StudentAttendanceActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference studentCollection = db.collection("studentCollection");
    private CollectionReference subjectCollection = db.collection("subjectCollection");

    private RecyclerView mRecyclerView;
    private StudentAttendanceAdapter studentAttendanceAdapter;
    private TextView studentNameTextView;
    private String studentName;
    private SharedPref sharedPref;
    private Context mContext;
    private String studentId;
    private ArrayList<Integer> attendedCount = new ArrayList<>();
    private ArrayList<Integer> totalCount=new ArrayList<>();
    private ArrayList<String> nameOfSubject=new ArrayList<>();
    private ArrayList<SubjectInStudentModel> subjects = new ArrayList<>();
    private ArrayList<StudentAttendanceModel> studentAttendanceModels = new ArrayList<>();
    private int iterator = 0;

    CardView profileCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studence_attendance);

        getSupportActionBar().setTitle("StudentModel Attendance");
//        Log.i("subject activity",getApplicationContext().getApplicationInfo().toString());
        mContext = this;
        studentNameTextView = (TextView) findViewById(R.id.textView2);

        sharedPref=new SharedPref(mContext);

        studentName = sharedPref.getNAME();
        studentId = sharedPref.getID();
        Log.i("subjectid",studentId);
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
        getMyList();
        Log.i("subjectarraylist",studentAttendanceModels.toString());




    }

    public void getMyList() {

        studentCollection.whereEqualTo("id",studentId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                StudentModel student = documentSnapshot.toObject(StudentModel.class);
                subjects = student.getSubjects();

                for(SubjectInStudentModel subject : subjects)
                {
                    int subjectId=subject.getId();
                    attendedCount.add(subject.getDates().size());
                    subjectCollection.whereEqualTo("id",subjectId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                            SubjectModel subjectModel = documentSnapshot.toObject(SubjectModel.class);
                            if(subjectModel.getDates() == null)
                            {

                                totalCount.add(0);

                            }
                            else
                            {
                                totalCount.add(subjectModel.getDates().size());

                            }


                            nameOfSubject.add(subjectModel.getName());

                            StudentAttendanceModel studentAttendanceModel = new StudentAttendanceModel();
                            studentAttendanceModel.setSubject(nameOfSubject.get(iterator));
                            float percentage;
                            Log.i("subjectattended",attendedCount.get(iterator).toString());
                            Log.i("subjecttotal",totalCount.get(iterator).toString());
                            if(totalCount.get(iterator)==0)
                            {
                                percentage = 0;
                            }
                            else
                                percentage = (float)attendedCount.get(iterator)/totalCount.get(iterator);
                            percentage = percentage*100;

                            studentAttendanceModel.setPercent(Float.toString(percentage));
                            studentAttendanceModels.add(studentAttendanceModel);
                            Log.i("subjectarraylist",studentAttendanceModel.getSubject());
                            Log.i("subjectarraylist",studentAttendanceModel.getPercent());
                            iterator++;
                            studentAttendanceAdapter = new StudentAttendanceAdapter(mContext,studentAttendanceModels);
                            mRecyclerView.setAdapter(studentAttendanceAdapter);

                        }
                    });

                }

                Log.i("subjectAttendedCount", attendedCount.toString());


            }
        });


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

                sharedPref.logout();
                sharedPref.setIsLoggedIn(false);
                Intent intent = new Intent(StudentAttendanceActivity.this, LoginActivity.class);
                finishAffinity();
                startActivity(intent);
                finish();

                return true;

            default:
                return false;
        }
    }

}