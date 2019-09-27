package com.google.firebase.samples.apps.mlkit.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.samples.apps.mlkit.AttendanceActivity;
import com.google.firebase.samples.apps.mlkit.LivePreviewActivity;
import com.google.firebase.samples.apps.mlkit.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Spinner spinner;
    private Button mBtnAttendance;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        spinner = root.findViewById(R.id.spinner_list);
        mBtnAttendance = root.findViewById(R.id.btn_take_attendance);

        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, homeViewModel.getListOfSubjects());
        spinner.setAdapter(spinner_adapter);

        mBtnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), LivePreviewActivity.class));
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AttendanceActivity.class));
            }
        });

        return root;
    }
}