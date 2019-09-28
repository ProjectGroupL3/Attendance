package com.google.firebase.samples.apps.mlkit;

public class StudentModel {
    String name,rollNumber;
    boolean lecture1,lecture2;

    public StudentModel(String name, String rollNumber, boolean lecture1, boolean lecture2) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.lecture1 = lecture1;
        this.lecture2 = lecture2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public boolean isLecture1() {
        return lecture1;
    }

    public void setLecture1(boolean lecture1) {
        this.lecture1 = lecture1;
    }

    public boolean isLecture2() {
        return lecture2;
    }

    public void setLecture2(boolean lecture2) {
        this.lecture2 = lecture2;
    }
}
