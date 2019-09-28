package com.google.firebase.samples.apps.mlkit;

import java.util.ArrayList;

public class Subject {
    int id;
    String name;
    String div;
    int teacherId;
    ArrayList<String> dates=new ArrayList<>();
    public Subject()
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

    public Subject(String name, int id, String div, ArrayList<String> dates, int teacherId)
    {
        this.name=name;
        this.id=id;
        this.div=div;
        this.dates=dates;
        this.teacherId=teacherId;

    }
    public Subject(int id)
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
