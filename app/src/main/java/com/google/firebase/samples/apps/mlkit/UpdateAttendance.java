package com.google.firebase.samples.apps.mlkit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.samples.apps.mlkit.models.StudentModel;
import com.google.firebase.samples.apps.mlkit.models.SubjectInStudentModel;
import com.google.firebase.samples.apps.mlkit.models.SubjectModel;
import com.google.firebase.samples.apps.mlkit.others.SharedPref;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpdateAttendance {
    private SharedPref sharedPref;
    private Context mContext;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference subjectCollection = db.collection("subjectCollection");
    private CollectionReference studentCollection = db.collection("studentCollection");
    private int subjectId;
    public UpdateAttendance(Context context)
    {
        mContext = context;
        sharedPref = new SharedPref(mContext);

    }
    public void updateAttendanceOfSubject(int subjectId) {
        Log.i("subjectdate ", "currentDate");

        subjectCollection.whereEqualTo("id",subjectId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {

                    ArrayList<String> dates;
                    SubjectModel subjectModel = documentSnapshot.toObject(SubjectModel.class);
                    try {
                        dates = new ArrayList<>(subjectModel.getDates());
                    }
                    catch (NullPointerException e){
                        dates = new ArrayList<>();
                    }

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String currentDate = sdf.format(new Date());
                    dates.add(currentDate);
                    Log.i("subjectdate ",dates.toString() + subjectModel.getName());
                    subjectModel.setDates(dates);
                    subjectCollection.document(documentSnapshot.getId()).set(subjectModel, SetOptions.merge());
                }
            }
        });

    }

//    public void updateAttendanceOfStudent(String studentId) {
//
//
//        studentCollection.whereEqualTo("id",studentId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots)
//                {
//                    int index;
//                    ArrayList<String> dates;
//
//                    StudentModel student = documentSnapshot.toObject(StudentModel.class);
//                    ArrayList<SubjectInStudentModel> subjects = student.getSubjects();
//                    findIndex(subjects);
//                    try {
//                        dates = new ArrayList<>(student.getDates());
//                    }
//                    catch (NullPointerException e){
//                        dates = new ArrayList<>();
//                    }
//
//                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//                    String currentDate = sdf.format(new Date());
//                    dates.add(currentDate);
//                    Log.i("subjectdate ",dates.toString() + subjectModel.getName());
//                    subjectModel.setDates(dates);
//                    subjectCollection.document(documentSnapshot.getId()).set(subjectModel, SetOptions.merge());
//                }
//            }
//        });
//
//    }
//    int findIndex(ArrayList<SubjectInStudentModel> subjects)
//    {
//        int index;
//        for(SubjectInStudentModel subject:subjects)
//        {
//            if(subject.getId()==subjectId)
//            {
//                return
//            }
//        }
//    }

}
