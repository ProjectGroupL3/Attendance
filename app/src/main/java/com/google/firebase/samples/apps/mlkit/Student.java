package com.google.firebase.samples.apps.mlkit;

import java.util.ArrayList;

public class Student {
    String id;
    String name;
    String div;
    ArrayList<SubjectInStudent> subjects=new ArrayList<>();

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public void setSubjects(ArrayList<SubjectInStudent> subjects) {
        this.subjects = subjects;
    }

    public Student()
    {

    }
    public Student(String id,String name,String div,ArrayList<SubjectInStudent> subjects)
    {

        this.id=id;
        this.name=name;
        this.div=div;
        this.subjects=subjects;


    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDiv() {
        return div;
    }

    public ArrayList<SubjectInStudent> getSubjects() {
        return subjects;
    }
}

