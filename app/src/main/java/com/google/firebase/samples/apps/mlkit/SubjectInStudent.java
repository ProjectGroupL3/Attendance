package com.google.firebase.samples.apps.mlkit;

import java.util.ArrayList;

public class SubjectInStudent {
    int id;
    ArrayList<String> dates=new ArrayList<String>();
    public SubjectInStudent()
    {

    }

    public int getId() {
        return id;
    }
    public SubjectInStudent(int id,ArrayList<String> dates)
    {
        this.id=id;
        this.dates=dates;


    }
}
