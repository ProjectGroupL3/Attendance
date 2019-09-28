package com.google.firebase.samples.apps.mlkit;

import java.util.ArrayList;

public class Teacher {
    String name;
    int id;
    String phoneNumber;
    ArrayList<Integer> subjectId=new ArrayList<>();
    public Teacher(){}
    public Teacher(int id,String name,String phoneNumber)
    {
        this.id=id;
        this.name=name;
        this.phoneNumber=phoneNumber;

    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ArrayList<Integer> getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(ArrayList<Integer> subjectId) {
        this.subjectId = subjectId;
    }
}
