package com.google.firebase.samples.apps.mlkit;

import android.annotation.SuppressLint;
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
import com.google.firebase.samples.apps.mlkit.models.TeacherModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateDatabase {
    FirebaseFirestore db;
    private CollectionReference studentCollection;
    private CollectionReference subjectCollection;
    private CollectionReference teacherCollection;
    List<StudentModel> students = new ArrayList<>();
    List<SubjectModel> subjectModels = new ArrayList<>();
    ArrayList<SubjectInStudentModel> Allsubjects = new ArrayList<>();
    List<TeacherModel> teacherModels = new ArrayList<>();
    ArrayList<Integer> subids;
    TeacherModel teacherModel;

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
                    TeacherModel tempTeacherModel = documentSnapshot.toObject(TeacherModel.class);
                    ArrayList<Integer> tempSubIds = new ArrayList<>(tempTeacherModel.getSubjectId());
                    tempSubIds.add(subjectId);
                    tempTeacherModel.setSubjectId(tempSubIds);
                    Log.i("subject ", tempid + " " + subjectId);
                    teacherCollection.document(documentSnapshot.getId()).set(tempTeacherModel, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
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
            /*teacherModels.add(new TeacherModel(1,"bagade","9999"));
            teacherModels.add(new TeacherModel(2,"jewalikar","9998"));
            teacherModels.add(new TeacherModel(3,"fakatkar","9997"));
            teacherModels.add(new TeacherModel(4,"mayur chavan","8999"));
            teacherModels.add(new TeacherModel(5,"shintre","9998"));
            teacherModels.add(new TeacherModel(6,"G.P.potdar","9988"));
            teacherModels.add(new TeacherModel(7,"urmila","8899"));
            teacherModels.add(new TeacherModel(8,"handge","8898"));
            teacherModels.add(new TeacherModel(9,"kale","8888"));

            for(int i=0;i<teacherModels.size();i++)
            {
                String teacherid= "teacherModel"+(i+1);
                teacherCollection.document(teacherid).set(teacherModels.get(i));
            }*/
    }

    public void addSubjects() {
   /*                     subjectModels.add(new SubjectModel("dbms",1,"TE1",null,3));
        subjectModels.add(new SubjectModel("dbms",2,"TE2",null,1));
        subjectModels.add(new SubjectModel("dbms",3,"TE3",null,1));
        subjectModels.add(new SubjectModel("cn",4,"TE1",null,2));
        subjectModels.add(new SubjectModel("cn",5,"TE2",null,2));
        subjectModels.add(new SubjectModel("cn",6,"TE3",null,4));
        subjectModels.add(new SubjectModel("sdl",7,"TE1",null,5));
        subjectModels.add(new SubjectModel("sdl",8,"TE2",null,4));
        subjectModels.add(new SubjectModel("sdl",9,"TE3",null,5));
        subjectModels.add(new SubjectModel("toc",10,"TE1",null,5));
        subjectModels.add(new SubjectModel("toc",11,"TE2",null,6));
        subjectModels.add(new SubjectModel("toc",12,"TE3",null,6));
        subjectModels.add(new SubjectModel("sepm",13,"TE1",null,8));
        subjectModels.add(new SubjectModel("sepm",14,"TE2",null,9));
        subjectModels.add(new SubjectModel("sepm",15,"TE3",null,9));
        for(int i=0;i<subjectModels.size();i++) {
            String docId = "SubjectModel" + (i + 1);
            subjectCollection.document(docId).update("teacherId",subjectModels.get(i).getTeacherId());
        }*/

    }

    public void addStudents() {
/*
        Allsubjects.add(new SubjectInStudentModel(1, null));
        Allsubjects.add(new SubjectInStudentModel(2, null));
        Allsubjects.add(new SubjectInStudentModel(3, null));
        Allsubjects.add(new SubjectInStudentModel(4, null));
        Allsubjects.add(new SubjectInStudentModel(5, null));
        Allsubjects.add(new SubjectInStudentModel(6, null));

        students.add(new StudentModel("c2k17105589", "mandar", "te3", Allsubjects));
        students.add(new StudentModel("c2k17105590", "x", "te3", Allsubjects));
        students.add(new StudentModel("c2k17105591", "y", "te3", Allsubjects));
        students.add(new StudentModel("c2k17105592", "z", "te3", Allsubjects));
        students.add(new StudentModel("c2k17105593", "a", "te3", Allsubjects));
        students.add(new StudentModel("c2k17105594", "b", "te3", Allsubjects));
        students.add(new StudentModel("c2k17105595", "c", "te3", Allsubjects));

        for (int i = 0; i < students.size(); i++) {
            studentCollection.add(students.get(i))
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(TeacherAttendanceActivity.this, "succesfully added", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TeacherAttendanceActivity.this, "Failed to add", Toast.LENGTH_SHORT).show();
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
                    subjectCollection.document(documentSnapshot.getId()).set(subjectModel,SetOptions.merge());
                }
            }
        });

    }
}
