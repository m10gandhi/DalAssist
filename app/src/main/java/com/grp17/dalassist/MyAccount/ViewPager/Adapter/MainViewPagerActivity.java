package com.grp17.dalassist.MyAccount.ViewPager.Adapter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.grp17.dalassist.R;

/* Referred following cites for tab layout and pager activities
* https://stackoverflow.com/questions/43359837/tab-layout-with-fragments
* https://stackoverflow.com/questions/37380798/how-can-i-implement-onclicklistener-of-an-imageview-in-a-fragment*/

public class MainViewPagerActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    private TabLayout tabLayout;


    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_pager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);


        tabLayout.addTab(tabLayout.newTab().setText("Event"));
        tabLayout.addTab(tabLayout.newTab().setText("Academic Calander"));
        tabLayout.addTab(tabLayout.newTab().setText("Course Information"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        viewPager = (ViewPager) findViewById(R.id.pager);


        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());


        viewPager.setAdapter(adapter);


        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
