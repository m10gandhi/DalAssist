package com.grp17.dalassist.MyAccount.Student.StaffFaculty;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grp17.dalassist.ModelClass.StaffFaculty;
import com.grp17.dalassist.R;

import java.util.ArrayList;
import java.util.List;

/* Referred Android Development documentation and stack overflow */

public class StaffFacultyTabView extends Fragment implements View.OnClickListener {
    private static final String TAG = "StaffFacultyTabView";
    private Context context;
    private View rootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private StaffFacultyAdapter adapter;
    private Button btnUpload;
    DatabaseReference databaseReference;
    private List<StaffFaculty> loginList;


    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.activity_tabview_staff_faculty, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("SatffAndFaculty");
        init();
        getData();
        clickEvent();
        return rootView;
    }

    private void clickEvent() {
    }

    private void getData() {
        loginList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                loginList.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    StaffFaculty login = artistSnapshot.getValue(StaffFaculty.class);
                    loginList.add(login);
                    Log.d(TAG, "onDataChange: " + loginList.toString());
                }
                Log.d(TAG, "init: " + loginList.size());

                adapter = new StaffFacultyAdapter(context,loginList);
                mRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void init() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        btnUpload = rootView.findViewById(R.id.btnUpload);
        btnUpload.setVisibility(View.GONE);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    public void onClick(View v) {

    }
}
