package com.grp17.dalassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.grp17.dalassist.Assist.googlesignin;
import com.grp17.dalassist.MyAccount.LoginFrontActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



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
                                Intent third = new Intent(MainActivity.this, LoginFrontActivity.class);
                                startActivity(third);
                                break;
                        }
                        return true;
                    }
                });
    }
    //[18][17]

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}


