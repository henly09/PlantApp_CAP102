package com.henzmontera.cap102_plantapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;
import java.util.Map;

public class MoreOptionBottomDialogFragment extends BottomSheetDialogFragment {

    private TextView DeleteTV;
    private TextView ReportTV;
    private TextView EditTV;
    private PostAdapter postAdapter;

    private SessionManager sessionManager;

    public static MoreOptionBottomDialogFragment newInstance() {
        return new MoreOptionBottomDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_option_bottom_dialog, container,
                false);
        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> guest = sessionManager.getGuestDetails();
        HashMap<String, String> user = sessionManager.getUserDetail();

        Bundle bundle = this.getArguments();
        String userid = bundle.getString("userid");
        String postid = bundle.getString("postid");
        String description = bundle.getString("description");

        DeleteTV = view.findViewById(R.id.moreoption_tv_btn_delete_post);
        ReportTV = view.findViewById(R.id.moreoption_tv_btn_report_post);
        EditTV = view.findViewById(R.id.more_option_tv_btn_edit_post);

        if(guest.get(sessionManager.GNAME).equals("UserGuest000")){
            DeleteTV.setVisibility(View.GONE);
            ReportTV.setVisibility(View.GONE);
            EditTV.setVisibility(View.GONE);
        }

        if (!user.get(sessionManager.UID).equals(userid)){
            DeleteTV.setVisibility(View.GONE);
            ReportTV.setVisibility(View.VISIBLE);
            EditTV.setVisibility(View.GONE);
        }

        if (user.get(sessionManager.UID).equals(userid)){
            DeleteTV.setVisibility(View.VISIBLE);
            ReportTV.setVisibility(View.GONE);
            EditTV.setVisibility(View.VISIBLE);
        }

        DeleteTV.setOnClickListener(view1 ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //Confirm Dialog
            builder.setCancelable(true);
            builder.setTitle("Deleting your post");
            builder.setMessage("Are you sure to delete your post?");
            builder.setPositiveButton("Confirm",
                    (dialog, which) -> {
                        String uid = user.get(sessionManager.UID);
                        String poist = postid;
                        DeletePost(uid, poist);
                        dismiss();
                    });
            builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                dismiss();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        EditTV.setOnClickListener(view1 -> {
            EditPost(postid, description);
            dismiss();
        });

        return view;
    }

    private void DeletePost(String userid, String postid){
        String url = getString(R.string.DeletePost);

        RequestQueue q = Volley.newRequestQueue(getActivity());

        StringRequest r = new StringRequest( //Request String type
                Request.Method.POST, //Get or Retrieve only Method of request
                url,
                response -> {
                    try {
                        Toast.makeText(getActivity(), "Delete Successfully", Toast.LENGTH_SHORT).show();
                        postAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                    }
                }, error -> {
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("userid", userid);
                param.put("postid", postid);
                return param;
            }
        };
        q.add(r);
    }

    private void EditPost(String postid, String description){
        Intent intent = new Intent(getActivity(), EditPost.class);
        intent.putExtra("postid", postid);
        intent.putExtra("description", description);
        Log.d("postid: ", postid+"");
        startActivity(intent);
    }
}