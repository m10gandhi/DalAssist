package com.grp17.dalassist.MyAccount.Student.Mydigest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grp17.dalassist.ModelClass.Login;
import com.grp17.dalassist.ModelClass.StaffFaculty;
import com.grp17.dalassist.R;

import java.util.ArrayList;
import java.util.List;

/* Referred Android Development documentation and stack overflow */

public class MyDigestTabView extends Fragment implements View.OnClickListener {
    private static final String TAG = "EventTabStaffView";
    private Context context;
    private View rootView;
    private RecyclerView rvAppoint,rvCourses;
    private LinearLayoutManager mLayoutManager;
    private MydigestAppointAdapter mydigestAppointAdapter;
    private MydigestCourseAdapter mydigestCourseAdapter;
    private Button btnUpload;
    DatabaseReference database_appoint,database_couser;
    private List<StaffFaculty> loginList;
    private List<Login> couist = new ArrayList<>();

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.activity_my_digest, container, false);
        database_couser = FirebaseDatabase.getInstance().getReference("CoursSelected");
        database_appoint = FirebaseDatabase.getInstance().getReference("SatffAndFacultyAppointmnet");
        init();
        getData();

        clickEvent();
        return rootView;
    }



    private void getData() {
        loginList = new ArrayList<>();

        database_appoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                loginList.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    StaffFaculty login = artistSnapshot.getValue(StaffFaculty.class);
                    loginList.add(login);
                    Log.d(TAG, "onDataChange: " + loginList.toString());
                }
                Log.d(TAG, "init: "+loginList.size());

                mydigestAppointAdapter = new MydigestAppointAdapter(getActivity(), loginList);
                rvAppoint.setAdapter(mydigestAppointAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        database_couser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                couist.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Login login = artistSnapshot.getValue(Login.class);
                    couist.add(login);
                    Log.d(TAG, "onDataChange : " + couist.toString());
                }
                Log.d(TAG, "init: "+loginList.size());

                mydigestCourseAdapter = new MydigestCourseAdapter(getActivity(), couist);
                rvCourses.setAdapter(mydigestCourseAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




    private void clickEvent() {

    }

    private void init() {
        rvAppoint = (RecyclerView) rootView.findViewById(R.id.rvAppoint);
         mLayoutManager = new LinearLayoutManager(context);
        rvAppoint.setLayoutManager(mLayoutManager);

        rvCourses = (RecyclerView) rootView.findViewById(R.id.rvCourses);
        mLayoutManager = new LinearLayoutManager(context);
        rvCourses.setLayoutManager(mLayoutManager);

    }

    public void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.update_layout, null);
        builder.setView(dialogView);
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.etName);
        final Button buttonname = (Button) dialogView.findViewById(R.id.buttonname);
        builder.setTitle("Updated Password");
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // Log.d(TAG, "onClick: Dialog" + login.getId());

        buttonname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    editTextName.setError("Event Required");
                    return;
                }
                //  Log.d(TAG, "ID Value"+ID);
                updateEvent(name);
                alertDialog.dismiss();
            }
        });

    }

    private boolean updateEvent(String pass) {

        return true;
    }

    @Override
    public void onClick(View v) {

    }
}
