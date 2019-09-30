package com.google.firebase.samples.apps.mlkit.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.samples.apps.mlkit.R;
import com.google.firebase.samples.apps.mlkit.models.StudentAttendanceModel;

import java.util.ArrayList;

public class StudentAttendanceAdapter extends RecyclerView.Adapter<StudentAttendanceAdapter.MyHolder> {

    Context c;
    ArrayList<StudentAttendanceModel> studentAttendanceModels;

    public StudentAttendanceAdapter(Context c, ArrayList<StudentAttendanceModel> studentAttendanceModels) {
        this.c = c;
        this.studentAttendanceModels = studentAttendanceModels;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, null);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        myHolder.subject1.setText(studentAttendanceModels.get(i).getSubject());
        myHolder.percent1.setText(studentAttendanceModels.get(i).getPercent());

    }

    @Override
    public int getItemCount() {
        return studentAttendanceModels.size();
    }
    public class MyHolder extends RecyclerView.ViewHolder {

        TextView subject1,percent1;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            this.subject1 = itemView.findViewById(R.id.subject1);
            this.percent1 = itemView.findViewById(R.id.percent1);
        }
    }

}
