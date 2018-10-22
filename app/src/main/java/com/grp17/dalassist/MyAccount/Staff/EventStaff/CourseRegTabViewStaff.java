package com.grp17.dalassist.MyAccount.Staff.EventStaff;

/* Referred following resource for fragment implementation
*       https://stackoverflow.com/questions/37380798/how-can-i-implement-onclicklistener-of-an-imageview-in-a-fragment*/

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grp17.dalassist.ModelClass.Login;
import com.grp17.dalassist.MyAccount.Staff.AddCourse;
import com.grp17.dalassist.MyAccount.Student.CourseRegister.CourseRegAdapter;
import com.grp17.dalassist.MyAccount.Student.Event.EventAdapter;
import com.grp17.dalassist.R;

import java.util.ArrayList;
import java.util.List;

public class CourseRegTabViewStaff extends Fragment implements View.OnClickListener {
    private static final String TAG = "CourseRegTabView";
    private Context context;
    private View rootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private EventAdapter adapter;
    DatabaseReference databaseReference;
    List<Login> InfoList;

    private FloatingActionButton add_cou;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        rootView = inflater.inflate(R.layout.activity_tabview_coursereg, container, false);
        databaseReference= FirebaseDatabase.getInstance().getReference("CourseInfo");
        init();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                InfoList.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()){
                    Login login=artistSnapshot.getValue(Login.class);
                     InfoList.add(login);

                }
                CourseRegAdapter adapter=new CourseRegAdapter(context,InfoList);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void init() {
        InfoList=new ArrayList<>();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerviewCourse);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        rootView.findViewById(R.id.add_cou).setVisibility(View.VISIBLE);

        rootView.findViewById(R.id.add_cou).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), AddCourse.class);
                startActivity(in);
            }
        });




    }

    @Override
    public void onClick(View v) {

    }
}
