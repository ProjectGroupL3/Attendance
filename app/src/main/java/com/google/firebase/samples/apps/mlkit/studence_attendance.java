package com.google.firebase.samples.apps.mlkit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class studence_attendance extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studence_attendance);

        getSupportActionBar().setTitle("Student Attendance");

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(this, getMyList());

        mRecyclerView.setAdapter(myAdapter);

    }

    public ArrayList<Model> getMyList() {

        ArrayList<Model> models = new ArrayList<>();
        Model m =new Model();

        m.setSubject("CE DBMS TH");
        m.setPercent("100%");
        models.add(m);

        m =new Model();
        m.setSubject("CE CN TH");
        m.setPercent("100%");
        models.add(m);

        m =new Model();
        m.setSubject("CE TOC TH");
        m.setPercent("100%");
        models.add(m);

        m =new Model();
        m.setSubject("CE SE & PM TH");
        m.setPercent("100%");
        models.add(m);

        m =new Model();
        m.setSubject("CE IS & EE TH");
        m.setPercent("100%");
        models.add(m);

        m =new Model();
        m.setSubject("CE CN PR");
        m.setPercent("100%");
        models.add(m);

        m =new Model();
        m.setSubject("CE DBMS PR");
        m.setPercent("100%");
        models.add(m);

        m =new Model();
        m.setSubject("CE SDL PR");
        m.setPercent("100%");
        models.add(m);

        m =new Model();
        m.setSubject("CE SDL TUT");
        m.setPercent("100%");
        models.add(m);

        return models;
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

                startActivity(new Intent(getApplicationContext(), profile.class));

                return true;

            case R.id.logout:

                return true;

            default:
                return false;
        }
    }

}
