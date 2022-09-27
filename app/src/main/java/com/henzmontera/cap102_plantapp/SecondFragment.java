package com.henzmontera.cap102_plantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class SecondFragment extends Fragment {
    ImageButton carbtn, indbtn, appbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_second, container, false);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(getActivity().getDrawable(R.drawable.actionbartheme));

        carbtn = rootview.findViewById(R.id.ib_carabao);
        indbtn = rootview.findViewById(R.id.ib_indian);
        appbtn = rootview.findViewById(R.id.ib_apple);

        //Carabao Button
        carbtn.setOnClickListener(view -> {
            Intent intentIntro = new Intent(getActivity(), CarInfo_sec.class);
            startActivity(intentIntro);
        });

        //Indian Button
        indbtn.setOnClickListener(view -> {
            Intent intentIntro = new Intent(getActivity(), IndInfo_sec.class);
            startActivity(intentIntro);
        });

        //Apple Button
        appbtn.setOnClickListener(view -> {
            Intent intentIntro = new Intent(getActivity(), AppleInfo_sec.class);
            startActivity(intentIntro);
        });
        // Inflate the layout for this fragment
        return rootview;
    }
}