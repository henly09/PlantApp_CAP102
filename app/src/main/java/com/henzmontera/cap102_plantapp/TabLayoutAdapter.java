package com.henzmontera.cap102_plantapp;

import com.henzmontera.cap102_plantapp.IPfragments.AMfragment;
import com.henzmontera.cap102_plantapp.IPfragments.CMfragment;
import com.henzmontera.cap102_plantapp.IPfragments.Homefragment;
import com.henzmontera.cap102_plantapp.IPfragments.IDMangoFragment;
import com.henzmontera.cap102_plantapp.IPfragments.IMfragment;
import com.henzmontera.cap102_plantapp.IPfragments.RealTimeFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabLayoutAdapter extends FragmentStateAdapter {

    public TabLayoutAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Homefragment();
            case 1:
                return new RealTimeFragment();
            case 2:
                return new IDMangoFragment();
            case 3:
                return new AMfragment();
            case 4:
                return new CMfragment();
            case 5:
                return new IMfragment();
            default:
                return new Homefragment();
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

}
