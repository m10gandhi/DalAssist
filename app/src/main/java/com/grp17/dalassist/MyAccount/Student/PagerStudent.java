package com.grp17.dalassist.MyAccount.Student;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.grp17.dalassist.MyAccount.Student.AcadamicCalander.AcadamicCalanderTabView;
import com.grp17.dalassist.MyAccount.Student.CourseRegister.CourseRegTabView;
import com.grp17.dalassist.MyAccount.Student.Event.EventTabView;
import com.grp17.dalassist.MyAccount.Student.Mydigest.MyDigestTabView;
import com.grp17.dalassist.MyAccount.Student.StaffFaculty.StaffFacultyTabView;

/* Referred for fragment pager adapter
* https://stackoverflow.com/questions/31260384/how-to-add-page-title-and-icon-in-android-fragmentpageradapter*/

public class PagerStudent extends FragmentStatePagerAdapter {


    int tabCount;
    private String[] tabTitles = new String[]{"Event", "Staff Faculty", "Academic Calander", "Course Information", "My Digest"};


    public PagerStudent(FragmentManager fm, int tabCount) {
        super(fm);

        this.tabCount = tabCount;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                EventTabView tab1 = new EventTabView();
                return tab1;
            case 1:
                StaffFacultyTabView tab2 = new StaffFacultyTabView();
                return tab2;
            case 2:
                AcadamicCalanderTabView tab3 = new AcadamicCalanderTabView();
                return tab3;
            case 3:
                CourseRegTabView tab4 = new CourseRegTabView();
                return tab4;

                case 4:
                MyDigestTabView tab5= new MyDigestTabView();
                return tab5;
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