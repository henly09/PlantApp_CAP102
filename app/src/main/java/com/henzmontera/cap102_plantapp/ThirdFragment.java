package com.henzmontera.cap102_plantapp;

// Developed by: John Henly A. Montera
// BSIT-4th-Year
// Cap102-Project

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class ThirdFragment extends Fragment {

    SQLiteDatabase myDB;
    int ma_test,cm_test,im_test;
    String sma_test, scm_test, sim_test;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_third, container, false);

        Button mangoapplebutton = rootview.findViewById(R.id.applemangobutton);
        Button mangocarabaobutton = rootview.findViewById(R.id.carabaomangobutton);
        Button mangoindianbutton = rootview.findViewById(R.id.indianmangobutton);
        Button mangopicobutton = rootview.findViewById(R.id.picomangobutton);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(getActivity().getDrawable(R.drawable.actionbartheme));

        mangoapplebutton.setOnClickListener(view -> {
            try {
                myDB = getActivity().getBaseContext().openOrCreateDatabase("IntroSlideCheckStatus.db", 0, null);
                Cursor ma_checkbox = myDB.rawQuery("SELECT COUNT(*) as count FROM proc_am_to_ama WHERE proc_am_to_ama.status = ?;", new String[] {"enable"});
                while(ma_checkbox.moveToNext()){
                    ma_test = ma_checkbox.getColumnIndex("count");
                    sma_test = ma_checkbox.getString(ma_test);
                }
                if(sma_test.equals("1")){
                    Intent intent = new Intent(getActivity(), ImageProcAM.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(), "Apple Mango Guide", Toast.LENGTH_SHORT).show();
                    Log.d("Apple MangoGuide","Apple Mango Guide");
                } else {
                    Intent intent = new Intent(getActivity(), AppleMangoActivity.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(), "Apple Mango Image Classification", Toast.LENGTH_SHORT).show();
                    Log.d("Apple Mango Image Classification","Apple Mango Image Classification");
                }
                myDB.close();
            } catch (Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "Error: "+ e, Toast.LENGTH_SHORT).show();
            }
        });

        mangocarabaobutton.setOnClickListener(view -> {
            try {
                myDB = getActivity().getBaseContext().openOrCreateDatabase("IntroSlideCheckStatus.db", 0, null);
                Cursor cm_checkbox = myDB.rawQuery("SELECT COUNT(*) as count FROM proc_cm_to_cma WHERE proc_cm_to_cma.status = ?;", new String[] {"enable"});
                while(cm_checkbox.moveToNext()){
                    cm_test = cm_checkbox.getColumnIndex("count");
                    scm_test = cm_checkbox.getString(cm_test);
                }
                if(scm_test.equals("1")){
                    Intent intent = new Intent(getActivity(), ImageProcCM.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(), "Carabao Mango Guide", Toast.LENGTH_SHORT).show();
                    Log.d("Carabao Mango Guide","Carabao Mango Guide");
                } else {
                    Intent intent = new Intent(getActivity(), CarabaoMangoActivity.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(), "Carabao Mango Image Classification", Toast.LENGTH_SHORT).show();
                    Log.d("Carabao Mango Image Classification","Carabao Mango Image Classification");
                }
                myDB.close();
            } catch (Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "Error:"+e, Toast.LENGTH_SHORT).show();
            }

        });

        mangoindianbutton.setOnClickListener(view -> {
            try {
                myDB = getActivity().getBaseContext().openOrCreateDatabase("IntroSlideCheckStatus.db", 0, null);
                Cursor mi_checkbox = myDB.rawQuery("SELECT COUNT(*) as count FROM proc_im_to_ima WHERE proc_im_to_ima.status = ?;", new String[] {"enable"});
                while(mi_checkbox.moveToNext()){
                    im_test = mi_checkbox.getColumnIndex("count");
                    sim_test = mi_checkbox.getString(im_test);
                }
                if(sim_test.equals("1")){
                    Intent intent = new Intent(getActivity(), ImageProcIM.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(), "Indian Mango Guide", Toast.LENGTH_SHORT).show();
                    Log.d("Indian Mango Guide","Indian Mango Guide");
                } else {
                    Intent intent = new Intent(getActivity(), IndianMangoActivity.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(), "Indian Mango Image Classification", Toast.LENGTH_SHORT).show();
                    Log.d("Indian Mango Image Classification","Indian Mango Image Classification");
                }
                myDB.close();
            } catch (Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "Error:"+e, Toast.LENGTH_SHORT).show();
            }

        });

        mangopicobutton.setOnClickListener(view -> {
            try {
                Toast.makeText(getActivity(), "Coming Soon!!", Toast.LENGTH_SHORT).show();
                Log.d("Pico Mango Image Classification","Coming Soon!!");
            } catch (Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "Error: "+ e, Toast.LENGTH_SHORT).show();
            }

        });

        // Inflate the layout for this fragment
        return rootview;
    }
}