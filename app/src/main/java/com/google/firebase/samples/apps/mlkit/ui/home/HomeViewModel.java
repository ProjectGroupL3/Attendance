package com.google.firebase.samples.apps.mlkit.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private ArrayList<String> listOfSubjects;

    public ArrayList<String> getListOfSubjects() {
        return listOfSubjects;
    }

    public void setListOfSubjects(ArrayList<String> listOfSubjects) {
        this.listOfSubjects = listOfSubjects;
    }

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Take Attendance");
        listOfSubjects = new ArrayList<>();
        listOfSubjects.add("CE TOC (TE3)");
        listOfSubjects.add("CE TOC (TE2)");
        listOfSubjects.add("CE SEPM (TE3)");
        listOfSubjects.add("CE DSL (SE4)");
        listOfSubjects.add("CE SDL (TE1)");
        listOfSubjects.add("CE SDL (TE2)");
    }

    public LiveData<String> getText() {
        return mText;
    }
}