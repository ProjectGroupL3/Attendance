package com.google.firebase.samples.apps.mlkit;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Profile extends AppCompatActivity {
    TextView studentId;
    TextView name;
    TextView className;
    TextView phoneNumber;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference studentCollection = db.collection("studentCollection");
    String enteredName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        enteredName = "mandar";

        studentId = findViewById(R.id.textView4);
        name = findViewById(R.id.textView5);
        className = findViewById(R.id.textView6);


        studentCollection.whereEqualTo("name",enteredName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {
                    Student student = documentSnapshot.toObject(Student.class);
                    studentId.setText("Id : "+student.getId());
                    name.setText("Name : "+student.getName());
                    className.setText("Class : "+student.getDiv());

                }
            }
        });


        getSupportActionBar().setTitle("Profile");
    }
}
