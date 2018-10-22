package com.grp17.dalassist.MyAccount.Staff.EventStaff;

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
import com.grp17.dalassist.ModelClass.Login;
import com.grp17.dalassist.R;

import java.util.ArrayList;
import java.util.List;

public class EventTabStaffView extends Fragment implements View.OnClickListener {
    private static final String TAG = "EventTabStaffView";
    private Context context;
    private View rootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private EventStaffAdapter adapter;
    private Button btnUpload;
    DatabaseReference databaseReference;
    private List<Login> loginList;
    private ProgressDialog progressDialog;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.activity_tabviewevent, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("EventT");

        init();
        getData();
        clickEvent();
        return rootView;
    }

    private void getData() {
        loginList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                loginList.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Login login = artistSnapshot.getValue(Login.class);
                    loginList.add(login);
                    Log.d(TAG, "onDataChange: " + loginList.toString());
                }
                adapter = new EventStaffAdapter(context, loginList);
                mRecyclerView.setAdapter(adapter);

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
        progressDialog = new ProgressDialog(getContext());
        btnUpload = rootView.findViewById(R.id.btnUpload);
        btnUpload.setVisibility(View.VISIBLE);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);


    }

    public void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.update_layout, null);
        builder.setView(dialogView);
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.etName);
        final Button buttonname = (Button) dialogView.findViewById(R.id.buttonname);
        builder.setTitle("Events");
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


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
        String id = databaseReference.push().getKey();
        Login artist = new Login(id, pass);
        databaseReference.child(id).setValue(artist);
        Log.d(TAG, "updateEvent: " + id);
        Log.d(TAG, "updateEvent: " + pass);
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
