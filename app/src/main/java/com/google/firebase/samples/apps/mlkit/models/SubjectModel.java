package com.google.firebase.samples.apps.mlkit.models;

import java.util.ArrayList;

public class SubjectModel {
    int id;
    String name;
    String div;
    int teacherId;
    ArrayList<String> dates=new ArrayList<>();
    public SubjectModel()
    {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDiv() {
        return div;
    }


    public int getTeacherId() {
        return teacherId;
    }

    public SubjectModel(String name, int id, String div, ArrayList<String> dates, int teacherId)
    {
        this.name=name;
        this.id=id;
        this.div=div;
        this.dates=dates;
        this.teacherId=teacherId;

    }
    public SubjectModel(int id)
    {
        this.id=id;

    }
    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }

    public ArrayList<String> getDates() {
        return dates;
    }

}
