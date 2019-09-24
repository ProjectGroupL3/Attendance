package com.google.firebase.samples.apps.mlkit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyHolder extends RecyclerView.ViewHolder {

    TextView subject1,percent1;

    public MyHolder(@NonNull View itemView) {
        super(itemView);
        this.subject1 = itemView.findViewById(R.id.subject1);
        this.percent1 = itemView.findViewById(R.id.percent1);
    }
}
