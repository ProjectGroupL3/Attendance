package com.google.firebase.samples.apps.mlkit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAttendanceAdapter extends RecyclerView.Adapter<StudentAttendanceAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<StudentModel> studentModels;
    private int numLectures;

    public StudentAttendanceAdapter(Context context, ArrayList<StudentModel> studentModels, int numOfLectures) {
        this.context = context;
        this.studentModels = studentModels;
        this.numLectures = numOfLectures;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_student_info,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        StudentModel studentModel = studentModels.get(position);
        holder.name.setText(studentModel.getName());
        holder.rollNumber.setText(studentModel.getRollNumber());
        holder.lecture1.setChecked(studentModel.lecture1);
        holder.lecture2.setChecked(studentModel.lecture2);
    }

    @Override
    public int getItemCount() {
        return studentModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox lecture1;
        private CheckBox lecture2;
        private TextView name;
        private TextView rollNumber;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_student_name);
            rollNumber = itemView.findViewById(R.id.tv_student_r_no);
            lecture1 = itemView.findViewById(R.id.lecture1);
            lecture2 = itemView.findViewById(R.id.lecture2);
        }
    }
}
