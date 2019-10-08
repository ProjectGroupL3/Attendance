package com.google.firebase.samples.apps.mlkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.samples.apps.mlkit.adapters.StudentMarkAdapter;
import com.google.firebase.samples.apps.mlkit.models.StudentMarkRvModel;
import com.google.firebase.samples.apps.mlkit.models.StudentModel;
import com.google.firebase.samples.apps.mlkit.models.SubjectInStudentModel;
import com.google.firebase.samples.apps.mlkit.models.SubjectModel;
import com.google.firebase.samples.apps.mlkit.others.SharedPref;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AttendanceActivity extends AppCompatActivity {
    Context mContext;
    RecyclerView recyclerView;
    StudentMarkAdapter attendanceAdapter;
    private Button markAttendanceButton;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference subjectCollection = db.collection("subjectCollection");
    private CollectionReference studentCollection = db.collection("studentCollection");
    private int subjectId;
    private int teacherId;
    private TextView totalStudentsView;
    private SharedPref sharedPref;
    private Set<String> presentStudents;
    private int totalPresentStudents;
    private ArrayList<StudentMarkRvModel> models = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        mContext = getApplicationContext();
        markAttendanceButton = findViewById(R.id.mark_attendance_button);
        sharedPref = new SharedPref(mContext);
        teacherId = Integer.valueOf(sharedPref.getID());
        subjectId = getIntent().getIntExtra("subjectId",-1);
        Log.i("AttendanceActivity",subjectId+" "+teacherId);
        presentStudents = new HashSet<>();
        presentStudents.addAll(Arrays.asList(new String[]{"C2K17105589", "C2K17105624"}));
        totalStudentsView = findViewById(R.id.num_students);
        totalPresentStudents=0;
        for(String studentId : presentStudents)
        {
            Log.i("AttendanceActivity",studentId);
        }




        recyclerView = findViewById(R.id.rv_student_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        updateAttendanceOfSubject();
        for(String studentId : presentStudents)
        {
            Log.i("AttendanceActivity",subjectId+" "+teacherId);

            updateAttendanceOfStudent(studentId);

        }



    }
    public void updateAttendanceOfSubject() {

        subjectCollection.whereEqualTo("id", subjectId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                    ArrayList<String> dates;
                    SubjectModel subjectModel = documentSnapshot.toObject(SubjectModel.class);
                    try {
                        dates = new ArrayList<>(subjectModel.getDates());
                    } catch (NullPointerException e) {
                        dates = new ArrayList<>();
                    }

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String currentDate = sdf.format(new Date());
                    dates.add(currentDate);
                    Log.i("subjectdate ", dates.toString() + subjectModel.getName());
                    subjectModel.setDates(dates);
                    subjectCollection.document(documentSnapshot.getId()).set(subjectModel, SetOptions.merge());
                }
            }
        });

    }

    public void updateAttendanceOfStudent(final String studentId) {


        studentCollection.whereEqualTo("id", studentId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.i("Attendance inupdatefunction",studentId);

                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                StudentModel student = documentSnapshot.toObject(StudentModel.class);
                Log.i("Attendance inupdatefunction",student.getName()+" "+student.getSubjects());

                ArrayList<String> dates;
                ArrayList<SubjectInStudentModel> subjects = student.getSubjects();
                int index = findIndex(subjects);
                if(index!=-1)
                {
                    totalPresentStudents++;
                    SubjectInStudentModel subject = subjects.get(index);
                    try {
                        dates = new ArrayList<>(subject.getDates());
                    } catch (NullPointerException e) {
                        dates = new ArrayList<>();
                    }

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String currentDate = sdf.format(new Date());
                    dates.add(currentDate);

                    subject.setDates(dates);
                    subjects.set(index,subject);
                    student.setSubjects(subjects);
                    models.add(new StudentMarkRvModel(student.getName(),student.getId(),true));

                    studentCollection.document(documentSnapshot.getId()).set(student, SetOptions.merge());
                    attendanceAdapter = new StudentMarkAdapter(mContext,models,1);


                    recyclerView.setAdapter(attendanceAdapter);

                }

            }

        });


    }
    private int findIndex(ArrayList<SubjectInStudentModel> subjects)
    {
        int index=0;
        for(SubjectInStudentModel subject:subjects)
        {
            if(subject.getId()==subjectId)
            {
                return index;
            }
            index++;
        }
        return -1;
    }
}
