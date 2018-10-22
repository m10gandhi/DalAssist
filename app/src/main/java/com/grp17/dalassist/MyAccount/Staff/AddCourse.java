package com.grp17.dalassist.MyAccount.Staff;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grp17.dalassist.ModelClass.Locations;
import com.grp17.dalassist.ModelClass.Login;
import com.grp17.dalassist.ModelClass.Tearms;
import com.grp17.dalassist.R;

import java.util.ArrayList;
import java.util.List;

/* Referred https://www.programcreek.com/java-api-examples/android.widget.AdapterView.OnItemClickListener
 *  for adapter view */

public class AddCourse extends AppCompatActivity {

    private static final String TAG = "AddCourse";
    private Button Add;
    private EditText cou_name, cou_place, cou_time, cou_faciltyname;
    private ListView location, terms;

    DatabaseReference database_terms;
    DatabaseReference database_location;

    ArrayAdapter<Locations> ladap;
    ArrayAdapter<Tearms> tadap;

    private List<Locations> locationList = new ArrayList<>();
    private List<Tearms> termList = new ArrayList<>();

    String getTermsID, getLocationID, getName, getTime, getfaclity;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        database_terms = FirebaseDatabase.getInstance().getReference("Terms");
        database_location = FirebaseDatabase.getInstance().getReference("Locations");

        databaseReference = FirebaseDatabase.getInstance().getReference("CourseInfo");

        Add = findViewById(R.id.add);
        cou_name = findViewById(R.id.cou_name);
        cou_place = findViewById(R.id.cou_place);
        cou_time = findViewById(R.id.cou_time);
        cou_faciltyname = findViewById(R.id.cou_faciltyname);
        location = findViewById(R.id.location);
        terms = findViewById(R.id.terms);

        cou_place.setVisibility(View.GONE);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getName = cou_name.getText().toString();
                getTime = cou_time.getText().toString();
                getfaclity = cou_faciltyname.getText().toString();
                Log.d(TAG, "onClick: " + getTermsID);
                Log.d(TAG, "onClick: " + getLocationID);
                Log.d(TAG, "onClick: " + getName);
                if (getTermsID.isEmpty() || getLocationID.isEmpty() || getName.isEmpty()) {
                    Toast.makeText(AddCourse.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    AddCou();
                }
            }
        });

        DataBase();

    }

    void DataBase() {

        ladap = new ArrayAdapter<Locations>(AddCourse.this, android.R.layout.select_dialog_singlechoice, locationList);
        location.setAdapter(ladap);
        location.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        terms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getTermsID = termList.get(i).getId();
                Log.d(TAG, "onItemClick: " + getTermsID);

            }
        });
        location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getLocationID = locationList.get(i).getId();
            }
        });


        tadap = new ArrayAdapter<Tearms>(AddCourse.this, android.R.layout.select_dialog_singlechoice, termList);
        terms.setAdapter(tadap);
        terms.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        try {
            locationList.clear();
            termList.clear();
        } catch (Exception e) {

        }

        database_location.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Locations login = artistSnapshot.getValue(Locations.class);
                    locationList.add(login);
                    ladap.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
    }

    void AddCou() {


        Login model = new Login(databaseReference.push().getKey(), getName, getfaclity, getLocationID,getTime);
        String id = databaseReference.push().getKey();
        databaseReference.child(id).setValue(model);
        Toast.makeText(AddCourse.this, "Add", Toast.LENGTH_SHORT).show();
        cou_name.setText("");
        cou_time.setText("");
        cou_faciltyname.setText("");
    }

}
