package com.spoiledit.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by HP on 7/28/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    public ArrayList<String> titleArrayList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentArrayList.add(fragment);
        titleArrayList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        try {
            return titleArrayList.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
