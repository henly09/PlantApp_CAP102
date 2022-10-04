package com.henzmontera.cap102_plantapp;

// Developed by: John Henly A. Montera
// BSIT-4th-Year
// Cap102-Project

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


public class ThirdFragment extends Fragment {


    TabLayout tabLayout;
    ViewPager2 viewPager2;
    TabLayoutAdapter tabLayoutAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_third, container, false);

        tabLayout = rootview.findViewById(R.id.TabLayout);
        viewPager2 = rootview.findViewById(R.id.ViewPager);
        tabLayoutAdapter = new TabLayoutAdapter(getActivity());
        viewPager2.setAdapter(tabLayoutAdapter);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        // Inflate the layout for this fragment
        return rootview;
    }
}