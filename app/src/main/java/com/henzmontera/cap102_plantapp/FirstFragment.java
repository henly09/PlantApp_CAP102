package com.henzmontera.cap102_plantapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 *
 *  THIS IS NEWSFEED
 *  Developed by Cyrex Joshua M. Cuizon
 */
public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_first, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(getActivity().getDrawable(R.drawable.actionbartheme));
        // Dria ka mag create

        // Inflate the layout for this fragment

        return rootview;
    }
}