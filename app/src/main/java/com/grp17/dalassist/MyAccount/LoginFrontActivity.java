package com.grp17.dalassist.MyAccount;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.grp17.dalassist.Assist.googlesignin;
import com.grp17.dalassist.Catalog;
import com.grp17.dalassist.MyAccount.Staff.MyAccounStafftActivity;
import com.grp17.dalassist.R;

public class LoginFrontActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    TextView tvStaff, tvStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_front);
        init();
        click();
    }

    private void click() {
        tvStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(getApplicationContext(),MyAccounStafftActivity.class);
                startActivity(intent);
            }
        });
        tvStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(getApplicationContext(),MyAccountActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener( ) {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId( )) {
                            case R.id.nav_assist:
                                Intent intent=new Intent(getApplicationContext(),googlesignin.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_catalog:
                                Intent second = new Intent(getApplicationContext(), Catalog.class);
                                startActivity(second);
                                break;
                            case R.id.nav_myaccount:
                                Intent third = new Intent(getApplicationContext(), LoginFrontActivity.class);
                                startActivity(third);
                                break;
                        }
                        return true;
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }



    private void init() {
        tvStaff = findViewById(R.id.tvstaff);
        tvStudent = findViewById(R.id.tvStudent);
    }
}
