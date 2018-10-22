package com.grp17.dalassist.MyAccount;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.grp17.dalassist.Database.Database;
import com.grp17.dalassist.ModelClass.Login;
import com.grp17.dalassist.MyAccount.Staff.MyAccounStafftActivity;
import com.grp17.dalassist.MyAccount.Student.MainViewPagerActivity;
import com.grp17.dalassist.R;

import java.util.ArrayList;
import java.util.List;
/* Referred
*   https://stackoverflow.com/questions/48731166/saving-data-from-a-textview-to-a-firebase-database*/

public class MyAccountActivity extends AppCompatActivity {
    private static final String TAG = "MyAccountActivity";
    public static String PREF_USERNAME="student"; //[19][21]
    public static String PREF_PASSWORD="student"; //[19][21]
    //textView,textView2,textView3,button2
    Button btLogin;
    EditText etname, etPass;
  //  Database database;
    DatabaseReference databaseStaff;
    Login login;
    TextView tvForgotPass;
    List<Login> dataLogin;
    public String ID = "";
    public static String nameOfStudent = "";
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_student);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseStaff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Login login = artistSnapshot.getValue(Login.class);
                    dataLogin.add(login);
                    Log.d(TAG, "onDataChange: " + login.getId());
                    Log.d(TAG, "onDataChange: " + login.getName());
                    Log.d(TAG, "onDataChange: " + login.getPassword());

                  /*  dataLogin.add(login.getPassword());
                    dataLogin.add(login.getId());
              */
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        btLogin = (Button) findViewById(R.id.button);
        etname = (EditText) findViewById(R.id.editText);
        etPass = (EditText) findViewById(R.id.editText2);
        tvForgotPass = (TextView) findViewById(R.id.tvForgotPass);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.ch_rememberme);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
  //[19][21]
        dataLogin = new ArrayList<>();
        databaseStaff = FirebaseDatabase.getInstance().getReference("student");

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            etname.setText(loginPreferences.getString("student", ""));
            etPass.setText(loginPreferences.getString("student", ""));
            saveLoginCheckBox.setChecked(true);
            //[19][21][20]
        }
        login = new Login();
        cliclListner();
    }



    private void cliclListner() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etname.getText().toString();
                String pass = etPass.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    if (TextUtils.isEmpty(pass)) {
                }
                } else {


                    if (saveLoginCheckBox.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("student", PREF_USERNAME);
                        loginPrefsEditor.putString("student", PREF_PASSWORD);
                        loginPrefsEditor.commit();

                        Toast.makeText(getApplicationContext(),
                                "Redirecting...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainViewPagerActivity.class);
                        startActivity(intent);
//[19][21][22][23]
                    } else {


                    if (etname.getText().toString().equals("student") &&
                            etPass.getText().toString().equals("student") ||etname.getText().toString().equals("student1") &&
                            etPass.getText().toString().equals("student1")||etname.getText().toString().equals("student2") &&
                            etPass.getText().toString().equals("student2")) {
                        Toast.makeText(getApplicationContext(),
                                "Redirecting...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainViewPagerActivity.class);
                        startActivity(intent);

                        nameOfStudent = etname.getText().toString();
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();

                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }
//[19][21]
                    }

                }


            }
        });



        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog();
            }
        });
    }

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.update_layout, null);
        builder.setView(dialogView);
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.etName);
        final Button buttonname = (Button) dialogView.findViewById(R.id.buttonname);
        builder.setTitle("Updated Password");
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Log.d(TAG, "onClick: Dialog" + login.getId());

        buttonname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    editTextName.setError("Name Required");
                    return;
                }
                //  Log.d(TAG, "ID Value"+ID);
                updatePassword(name);
                alertDialog.dismiss();
            }
        });

    }

    private boolean updatePassword(String pass) {
        //  Log.d(TAG, "updatePassword: p"+login.getPassword());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("student").child(login.getId());
        Log.d(TAG, "updatePassword: " + ID);
        Log.d(TAG, "updatePassword: " + login.getName());
        Log.d(TAG, "updatePassword: " + login.getId());
        Log.d(TAG, "updatePassword: " + login.getPassword());
        Login artist = new Login(login.getName(), pass);
        databaseReference.setValue(artist);
        Toast.makeText(getApplicationContext(), "Password Update", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MyAccounStafftActivity.class);
        startActivity(intent);
        return true;
    }

    private void addValue() {
        String name = etname.getText().toString();
        String pass = etPass.getText().toString();
        String id = databaseStaff.push().getKey();
        Login login = new Login(name, pass, id);
        databaseStaff.child(id).setValue(login);
        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainViewPagerActivity.class);
        startActivity(intent);


    }


}

