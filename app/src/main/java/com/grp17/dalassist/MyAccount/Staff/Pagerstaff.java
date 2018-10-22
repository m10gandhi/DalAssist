package com.grp17.dalassist.MyAccount.Staff;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.grp17.dalassist.MyAccount.Staff.EventStaff.CourseRegTabViewStaff;
import com.grp17.dalassist.MyAccount.Staff.EventStaff.EventTabStaffView;
import com.grp17.dalassist.MyAccount.Staff.StaffAndFaculty.StaffFacultyTabView;

/* Referred for fragment pager adapter
* https://stackoverflow.com/questions/31260384/how-to-add-page-title-and-icon-in-android-fragmentpageradapter*/

public class Pagerstaff extends FragmentStatePagerAdapter {


    int tabCount;

    private String[] tabTitles = new String[]{"Event", "Staff Faculty", "Course Information", "Brightspace"};


    public Pagerstaff(FragmentManager fm, int tabCount) {
        super(fm);

        this.tabCount = tabCount;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                EventTabStaffView eventTabView = new EventTabStaffView();
                return eventTabView;
            case 1:
                StaffFacultyTabView courseTabView = new StaffFacultyTabView();
                return courseTabView;

            case 2:
                CourseRegTabViewStaff courseRegTabView = new CourseRegTabViewStaff();
                return courseRegTabView;
            case 3:
                CourseInformationTabView staffFacultyTabView = new CourseInformationTabView();
                return staffFacultyTabView;

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