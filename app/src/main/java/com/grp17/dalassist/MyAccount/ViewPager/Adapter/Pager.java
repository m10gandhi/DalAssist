package com.grp17.dalassist.MyAccount.ViewPager.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/* Referred for fragment pager adapter
* https://stackoverflow.com/questions/31260384/how-to-add-page-title-and-icon-in-android-fragmentpageradapter*/

public class Pager extends FragmentStatePagerAdapter {


    int tabCount;


    public Pager(FragmentManager fm, int tabCount) {
        super(fm);

        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                Tabviewone tab1 = new Tabviewone();
                return tab1;
            case 1:
                Tabviewone tab2 = new Tabviewone();
                return tab2;
            case 2:
                Tabviewone tab3 = new Tabviewone();
                return tab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}