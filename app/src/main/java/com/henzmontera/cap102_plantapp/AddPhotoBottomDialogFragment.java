package com.henzmontera.cap102_plantapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddPhotoBottomDialogFragment extends BottomSheetDialogFragment {

    private TextView SelectTV;
    private TextView ViewTV;
    private ImageView profileImage;

    private Intent profileintent;

    public static AddPhotoBottomDialogFragment newInstance() {
        return new AddPhotoBottomDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_bottomdialog_profile_picture, container,
                false);
        // Here to get views and attach the listener
        SelectTV = view.findViewById(R.id.tv_btn_select_photo_gallery);
        ViewTV = view.findViewById(R.id.tv_btn_view_photo);
        profileImage = view.findViewById(R.id.ImageProfilePost);

        SelectTV.setOnClickListener(view1 -> {
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED){
                profileintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(profileintent, 1);
            }
        });

        ViewTV.setOnClickListener(view1 -> {

        });

        return view;
    }

}
