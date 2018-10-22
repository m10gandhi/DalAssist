package com.grp17.dalassist.MyAccount.Student.AcadamicCalander;

/* Referred Android Development documentation and stack overflow */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grp17.dalassist.ModelClass.Login;
import com.grp17.dalassist.ModelClass.Tearms;
import com.grp17.dalassist.R;

import java.util.ArrayList;
import java.util.List;

public class AcadamicCalanderTabView extends Fragment implements View.OnClickListener {
    private static final String TAG = "AcadamicCalanderTabView";
    RecyclerView rvcourseInfo;
    private Context context;
    private View rootView;
    private RecyclerView mRecyclerView, rvcourseWinter;
    private LinearLayoutManager mLayoutManager;
    private AcadamicCalanderAdapter adapter;
    private List<Login> list, DataList;
    DatabaseReference databaseReference, database_terms;
    ArrayAdapter<Tearms> tadap;
    private ListView terms;
    private List<Tearms> termList = new ArrayList<>();
    String getTermsID;
    private  List<String> strings;


    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.activity_course_info, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("CourseInfo");
        database_terms = FirebaseDatabase.getInstance().getReference("Terms");
        init();

        return rootView;
    }

    private void getData() {
        DataList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataList.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Login login = artistSnapshot.getValue(Login.class);
                    DataList.add(login);
                    Log.d(TAG, "onDataChange: " + DataList.toString());
                }
                Log.d(TAG, "init: " + DataList.size());



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void init() {
        DataList = new ArrayList<>();

        initDatabse();

        initSpinner();

    }

    private void initSpinner() {
        terms = rootView.findViewById(R.id.terms);
        database_terms.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Tearms login = artistSnapshot.getValue(Tearms.class);
                    termList.add(login);
                    tadap.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        terms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getTermsID = termList.get(i).getTearms_name();
                initRecycler();
                getData();

                Log.d(TAG, "onItemClick: " + getTermsID);

            }
        });
        tadap = new ArrayAdapter<Tearms>(getContext(), android.R.layout.select_dialog_singlechoice, termList);
        terms.setAdapter(tadap);
        terms.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


    }

    private void initDatabse() {
    }

    private void initRecycler() {
        strings =new ArrayList<>();
        mRecyclerView = rootView.findViewById(R.id.rvcourseInfo);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        strings.add("Nurse");
        strings.add("Biology");
        strings.add("Engineering");
        adapter = new AcadamicCalanderAdapter(context, strings);
        mRecyclerView.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {

    }

}
