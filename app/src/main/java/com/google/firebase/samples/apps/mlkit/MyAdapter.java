package com.google.firebase.samples.apps.mlkit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<Model> models;

    public MyAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, null);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        myHolder.subject1.setText(models.get(i).getSubject());
        myHolder.percent1.setText(models.get(i).getPercent());

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
