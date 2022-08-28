package com.henzmontera.cap102_plantapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class ThirdFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_third, container, false);

        Button mangoapplebutton = rootview.findViewById(R.id.applemangobutton);
        Button mangocarabaobutton = rootview.findViewById(R.id.carabaomangobutton);
        Button mangoindianbutton = rootview.findViewById(R.id.indianmangobutton);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(getActivity().getDrawable(R.drawable.side_nav_bar));

        mangoapplebutton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AppleMangoActivity.class);
            startActivity(intent);
           Toast.makeText(getActivity(), "Apple Mango", Toast.LENGTH_SHORT).show();
           Log.d("Apple Mango","Apple Mango");
        });

        mangocarabaobutton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), CarabaoMangoActivity.class);
            startActivity(intent);
            Toast.makeText(getActivity(), "Carabao Mango", Toast.LENGTH_SHORT).show();
            Log.d("Carabao Mango","Carabao Mango");
        });

        mangoindianbutton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), IndianMangoActivity.class);
            startActivity(intent);
            Toast.makeText(getActivity(), "Indian Mango", Toast.LENGTH_SHORT).show();
            Log.d("Indian Mango","Indian Mango");
        });


        // Inflate the layout for this fragment
        return rootview;
    }
}