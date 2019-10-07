package com.google.firebase.samples.apps.mlkit.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.samples.apps.mlkit.AttendanceActivity;
import com.google.firebase.samples.apps.mlkit.LivePreviewActivity;
import com.google.firebase.samples.apps.mlkit.R;
import com.google.firebase.samples.apps.mlkit.models.SpinnerObjectModel;
import com.google.firebase.samples.apps.mlkit.models.SubjectModel;
import com.google.firebase.samples.apps.mlkit.models.TeacherModel;
import com.google.firebase.samples.apps.mlkit.others.SharedPref;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Spinner spinner;
    private Button mBtnAttendance;
    private SharedPref sharedPref;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference subjectCollection = db.collection("subjectCollection");
    private ArrayList<String> listOfSubjects;
    private ArrayList<SpinnerObjectModel> spinnerObjectModels = new ArrayList<>();
    private int teacherId;
    private Context mContext;
    private int subjectId;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity().getApplicationContext();
        sharedPref = new SharedPref(mContext);
        teacherId = Integer.valueOf(sharedPref.getID());
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



       getListOfSubjects();
       /* String nameOfSubject = spinner.getSelectedItem().toString();
        for(SpinnerObjectModel spinnerObjectModel : spinnerObjectModels)
        {
            if(spinnerObjectModel.getNameOfSubject() == nameOfSubject)
            {
                subjectId = spinnerObjectModel.getSubjectId();
                break;
            }
        }*/
        mBtnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),LivePreviewActivity.class);
                intent.putExtra("subjectId",subjectId);
                startActivity(intent);
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
    private void getListOfSubjects()
    {
        final ArrayList<String> listOfSubject = new ArrayList<>();
        subjectCollection.whereEqualTo("teacherId",teacherId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    SubjectModel subjectModel = documentSnapshot.toObject(SubjectModel.class);
                    String subjectName = subjectModel.getName();
                    String className = subjectModel.getDiv();
                    listOfSubject.add(subjectName+" ("+className+" )");
                    SpinnerObjectModel spinnerObjectModel = new SpinnerObjectModel(subjectName+" ("+className+" )",subjectModel.getId());
                    spinnerObjectModels.add(spinnerObjectModel);
                    ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, listOfSubject);
                    spinner.setAdapter(spinner_adapter);


                }
            }
        });




    }
}