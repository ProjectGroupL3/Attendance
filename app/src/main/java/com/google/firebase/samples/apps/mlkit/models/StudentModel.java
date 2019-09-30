package com.google.firebase.samples.apps.mlkit.models;

import java.util.ArrayList;

public class StudentModel {
    String id;
    String name;
    String div;
    ArrayList<SubjectInStudentModel> subjects=new ArrayList<>();

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public void setSubjects(ArrayList<SubjectInStudentModel> subjects) {
        this.subjects = subjects;
    }

    public StudentModel()
    {

    }
    public StudentModel(String id, String name, String div, ArrayList<SubjectInStudentModel> subjects)
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

    public ArrayList<SubjectInStudentModel> getSubjects() {
        return subjects;
    }
}

