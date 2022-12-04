package com.henzmontera.cap102_plantapp.IPfragments;

// Developed by: John Henly A. Montera
// BSIT-4th-Year
// Cap102-Project

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.henzmontera.cap102_plantapp.ImageProcAM;
import com.henzmontera.cap102_plantapp.ImageProcCM;
import com.henzmontera.cap102_plantapp.ImageProcIM;
import com.henzmontera.cap102_plantapp.Penetrometer;
import com.henzmontera.cap102_plantapp.R;
import com.henzmontera.cap102_plantapp.Refractometer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Calendar;

import androidx.fragment.app.Fragment;

public class Homefragment extends Fragment {

    Button amguide,cmguide,imguide,pmguide,rmguide;
    View rootview;
    ImageView logo;
    YouTubePlayerView youtube1,youtube2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_homefragment, container, false);

        // Button initialization
        amguide = rootview.findViewById(R.id.amguide);
        cmguide = rootview.findViewById(R.id.cmguide);
        imguide = rootview.findViewById(R.id.imguide);
        pmguide = rootview.findViewById(R.id.pmguide);
        rmguide = rootview.findViewById(R.id.rmguide);

        logo = rootview.findViewById(R.id.logo);

        youtube1 = rootview.findViewById(R.id.youTubePlayerView);
        youtube2 = rootview.findViewById(R.id.youTubePlayerView2);


        logo.setOnClickListener(view -> {
            Toast.makeText(getActivity(), "ManGo© "+ Calendar.getInstance().get(Calendar.YEAR), Toast.LENGTH_SHORT).show();
        });

        amguide.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), ImageProcAM.class);
            startActivity(intent);
            Toast.makeText(getActivity(), "Apple Mango Guide", Toast.LENGTH_SHORT).show();
            Log.d("Apple Mango Guide","Apple Mango Guide");
        });

        cmguide.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), ImageProcCM.class);
            startActivity(intent);
            Toast.makeText(getActivity(), "Carabao Mango Guide", Toast.LENGTH_SHORT).show();
            Log.d("Carabao Mango Guide","Carabao Mango Guide");
        });

        imguide.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), ImageProcIM.class);
            startActivity(intent);
            Toast.makeText(getActivity(), "Indian Mango Guide", Toast.LENGTH_SHORT).show();
            Log.d("Indian Mango Guide","Indian Mango Guide");
        });

        pmguide.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), Penetrometer.class);
            startActivity(intent);
            Toast.makeText(getActivity(), "Penetrometer Guide", Toast.LENGTH_SHORT).show();
            Log.d("Penetrometer Guide","Penetrometer Guide");
        });

        rmguide.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), Refractometer.class);
            startActivity(intent);
            Toast.makeText(getActivity(), "Refractometer Guide", Toast.LENGTH_SHORT).show();
            Log.d("Refractometer Guide","Refractometer Guide");
        });

        return rootview;
    }
}