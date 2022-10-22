package com.henzmontera.cap102_plantapp.IPfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.henzmontera.cap102_plantapp.R;

import androidx.fragment.app.Fragment;


public class RealTimeFragment extends Fragment {

    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_real_time, container, false);

        return rootview;
    }
}