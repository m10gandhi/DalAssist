package com.grp17.dalassist.MyAccount.Staff.StaffAndFaculty;

/* Referred Android Development documentation and stack overflow */

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grp17.dalassist.ModelClass.StaffFaculty;
import com.grp17.dalassist.R;

import java.util.ArrayList;
import java.util.List;

public class StaffFacultyTabView extends Fragment implements View.OnClickListener {
    private static final String TAG = "StaffFacultyTabView";
    private Context context;
    private View rootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private StaffFacultyAdapter adapter;
    private Button btnUpload;
    DatabaseReference databaseReference;
    private List<StaffFaculty> loginList = new ArrayList<>();
    private ProgressDialog progressDialog;



    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        rootView = inflater.inflate(R.layout.activity_tabview_staff_faculty, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("SatffAndFacultyAppointmnet");

        init();
        getData();
        clickEvent();

        return rootView;
    }

    private void getData() {


        try{

        }catch (Exception e){

        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                loginList.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    StaffFaculty login = artistSnapshot.getValue(StaffFaculty.class);
                    loginList.add(login);
                    Log.d(TAG, "onDataChange: " + loginList.toString());
                    adapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void clickEvent() {
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog();
            }
        });
    }

    private void init() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        loginList = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        btnUpload = rootView.findViewById(R.id.btnUpload);
        btnUpload.setVisibility(View.VISIBLE);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new StaffFacultyAdapter(context, loginList);
        mRecyclerView.setAdapter(adapter);


    }

    public void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.update_layout_staff, null);
        builder.setView(dialogView);
        final EditText etTime = (EditText) dialogView.findViewById(R.id.etTime);
        final EditText etContactNo = (EditText) dialogView.findViewById(R.id.etContactNo);
        final EditText etEmail = (EditText) dialogView.findViewById(R.id.etEmail);
        final Button buttonname = (Button) dialogView.findViewById(R.id.buttonname);
        buttonname.setText("Uploaded");
        builder.setTitle("Post Timing");
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // Log.d(TAG, "onClick: Dialog" + login.getId());

        buttonname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = etTime.getText().toString().trim();
                String contactNo = etContactNo.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(time)) {
                    if (!TextUtils.isEmpty(contactNo)){
                        if (!TextUtils.isEmpty(email)){
                            updateEvent(time,contactNo,email);
                            alertDialog.dismiss();
                        }else {
                            etEmail.setError("Email is Required");
                        }
                    }else {
                        etContactNo.setError("Contact is Required");
                    }

                }else {
                    etEmail.setError("Email is Required");
                }
                return;
                //  Log.d(TAG, "ID Value"+ID);

            }
        });

    }

    private boolean updateEvent(String time,String contactNo, String email) {
        String id = databaseReference.push().getKey();
        StaffFaculty artist = new StaffFaculty(id,time,contactNo,email);
        databaseReference.child(id).setValue(artist);
        getData();

        Log.d(TAG, "updateEvent: " + id);
        Toast.makeText(getContext(), "Event Update", Toast.LENGTH_SHORT).show();
       /* Intent intent = new Intent(getContext(), MainViewPagerStaffActivity.class);
        startActivity(intent);
       */
        return true;
    }

    @Override
    public void onClick(View v) {

    }

}
