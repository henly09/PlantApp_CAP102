package com.henzmontera.cap102_plantapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class SecondFragment extends Fragment {
    ImageButton carbtn, indbtn, appbtn;
    ListView listView;
    String[] mylist = {"Introduction", "Planting Mango Seedling", "Management of Fruit Tree after Planting"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_second, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(getActivity().getDrawable(R.drawable.actionbartheme));

        listView = rootview.findViewById(R.id.listview);

        ArrayAdapter myArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mylist);

        listView.setAdapter(myArrayAdapter);
        listView.setSelector(R.color.bounding_box_color);
        carbtn = rootview.findViewById(R.id.ib_carabao);
        indbtn = rootview.findViewById(R.id.ib_indian);
        appbtn = rootview.findViewById(R.id.ib_apple);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    Intent intentIntro = new Intent(getActivity(), SecManualMango.class);
                    startActivity(intentIntro);
                }else if(i==1){
                    Intent intentIntro = new Intent(getActivity(), SecPlantingMangoSeedling.class);
                    startActivity(intentIntro);
                }else if(i==2) {
                    Intent intentIntro = new Intent(getActivity(), SecManagePlanting.class);
                    startActivity(intentIntro);
                }
            }
        });

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