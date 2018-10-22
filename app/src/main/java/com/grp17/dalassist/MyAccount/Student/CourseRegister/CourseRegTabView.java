package com.grp17.dalassist.MyAccount.Student.CourseRegister;

import android.content.Context;
import android.os.Bundle;
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
import com.grp17.dalassist.MyAccount.Student.Event.EventAdapter;
import com.grp17.dalassist.R;

import java.util.ArrayList;
import java.util.List;

/* Referred Android Development documentation and stack overflow */

public class CourseRegTabView extends Fragment implements View.OnClickListener {
    private static final String TAG = "CourseRegTabView";
    private Context context;
    private View rootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private EventAdapter adapter;
    DatabaseReference databaseReference;
    List<Login> InfoList;



    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


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
                CourseRegAdapter adapter=new CourseRegAdapter(getActivity(),InfoList);
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

    }

    @Override
    public void onClick(View v) {

    }
}
