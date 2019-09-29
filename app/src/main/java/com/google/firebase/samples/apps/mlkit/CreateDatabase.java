package com.google.firebase.samples.apps.mlkit;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CreateDatabase {
    FirebaseFirestore db;
    private CollectionReference studentCollection;
    private CollectionReference subjectCollection;
    private CollectionReference teacherCollection;
    List<Student> students = new ArrayList<>();
    List<Subject> subjects = new ArrayList<>();
    ArrayList<SubjectInStudent> Allsubjects = new ArrayList<>();
    List<Teacher> teachers = new ArrayList<>();
    ArrayList<Integer> subids;
    Teacher teacher;

    public CreateDatabase() {
        db = FirebaseFirestore.getInstance();
        studentCollection = db.collection("studentCollection");
        subjectCollection = db.collection("subjectCollection");
        teacherCollection = db.collection("teacherCollection");
    }

    public void addSubjectInTeacher(final int tempid, final int subjectId) {
        teacherCollection.whereEqualTo("id", tempid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Teacher tempTeacher = documentSnapshot.toObject(Teacher.class);
                    ArrayList<Integer> tempSubIds = new ArrayList<>(tempTeacher.getSubjectId());
                    tempSubIds.add(subjectId);
                    tempTeacher.setSubjectId(tempSubIds);
                    Log.i("subject ", tempid + " " + subjectId);
                    teacherCollection.document(documentSnapshot.getId()).set(tempTeacher, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i("subject1 ", "tempid: " + tempid + " subid:" + subjectId);
                        }
                    });
                }
            }
        });
    }

    public void addTeachers() {
            /*teachers.add(new Teacher(1,"bagade","9999"));
            teachers.add(new Teacher(2,"jewalikar","9998"));
            teachers.add(new Teacher(3,"fakatkar","9997"));
            teachers.add(new Teacher(4,"mayur chavan","8999"));
            teachers.add(new Teacher(5,"shintre","9998"));
            teachers.add(new Teacher(6,"G.P.potdar","9988"));
            teachers.add(new Teacher(7,"urmila","8899"));
            teachers.add(new Teacher(8,"handge","8898"));
            teachers.add(new Teacher(9,"kale","8888"));

            for(int i=0;i<teachers.size();i++)
            {
                String teacherid= "teacher"+(i+1);
                teacherCollection.document(teacherid).set(teachers.get(i));
            }*/
    }

    public void addSubjects() {
   /*                     subjects.add(new Subject("dbms",1,"TE1",null,3));
        subjects.add(new Subject("dbms",2,"TE2",null,1));
        subjects.add(new Subject("dbms",3,"TE3",null,1));
        subjects.add(new Subject("cn",4,"TE1",null,2));
        subjects.add(new Subject("cn",5,"TE2",null,2));
        subjects.add(new Subject("cn",6,"TE3",null,4));
        subjects.add(new Subject("sdl",7,"TE1",null,5));
        subjects.add(new Subject("sdl",8,"TE2",null,4));
        subjects.add(new Subject("sdl",9,"TE3",null,5));
        subjects.add(new Subject("toc",10,"TE1",null,5));
        subjects.add(new Subject("toc",11,"TE2",null,6));
        subjects.add(new Subject("toc",12,"TE3",null,6));
        subjects.add(new Subject("sepm",13,"TE1",null,8));
        subjects.add(new Subject("sepm",14,"TE2",null,9));
        subjects.add(new Subject("sepm",15,"TE3",null,9));
        for(int i=0;i<subjects.size();i++) {
            String docId = "Subject" + (i + 1);
            subjectCollection.document(docId).update("teacherId",subjects.get(i).getTeacherId());
        }*/

    }

    public void addStudents() {
/*
        Allsubjects.add(new SubjectInStudent(1, null));
        Allsubjects.add(new SubjectInStudent(2, null));
        Allsubjects.add(new SubjectInStudent(3, null));
        Allsubjects.add(new SubjectInStudent(4, null));
        Allsubjects.add(new SubjectInStudent(5, null));
        Allsubjects.add(new SubjectInStudent(6, null));

        students.add(new Student("c2k17105589", "mandar", "te3", Allsubjects));
        students.add(new Student("c2k17105590", "x", "te3", Allsubjects));
        students.add(new Student("c2k17105591", "y", "te3", Allsubjects));
        students.add(new Student("c2k17105592", "z", "te3", Allsubjects));
        students.add(new Student("c2k17105593", "a", "te3", Allsubjects));
        students.add(new Student("c2k17105594", "b", "te3", Allsubjects));
        students.add(new Student("c2k17105595", "c", "te3", Allsubjects));

        for (int i = 0; i < students.size(); i++) {
            studentCollection.add(students.get(i))
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MainActivity.this, "succesfully added", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Failed to add", Toast.LENGTH_SHORT).show();
                        }
                    });
            }*/

    }

    public void updateDateOfSubject(int subjectId) {
        Log.i("subjectdate ", "currentDate");

        subjectCollection.whereEqualTo("id",subjectId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {

                    ArrayList<String> dates;
                    Subject subject = documentSnapshot.toObject(Subject.class);
                    try {
                        dates = new ArrayList<>(subject.getDates());
                    }
                    catch (NullPointerException e){
                        dates = new ArrayList<>();
                    }

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String currentDate = sdf.format(new Date());
                    dates.add(currentDate);
                    Log.i("subjectdate ",dates.toString() + subject.getName());
                    subject.setDates(dates);
                    subjectCollection.document(documentSnapshot.getId()).set(subject,SetOptions.merge());
                }
            }
        });

    }
}
