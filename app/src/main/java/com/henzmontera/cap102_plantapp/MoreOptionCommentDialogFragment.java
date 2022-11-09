package com.henzmontera.cap102_plantapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoreOptionCommentDialogFragment extends BottomSheetDialogFragment {

    private TextView CopyTv;
    private TextView DeleteTV;
    private TextView EditTv;
    private CommentAdapter commentAdapter;
    private List<ListComment> listComments;

    private SessionManager sessionManager;

    public static MoreOptionCommentDialogFragment newInstance() {
        return new MoreOptionCommentDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_option_comment_bottom_dialog, container,
                false);
        listComments = new ArrayList<>();
        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> guest = sessionManager.getGuestDetails();
        HashMap<String, String> user = sessionManager.getUserDetail();

        Bundle bundle = this.getArguments();
        String commentid = bundle.getString("commentid");
        String postid = bundle.getString("postid");
        String commenter_userid = bundle.getString("commenter_userid");
        String comment_text = bundle.getString("comment_text");

        Log.d("Comment ID: ", commentid+"");
        Log.d("Post ID: ", postid+"");
        Log.d("Commenter User ID: ", commenter_userid+"");
        Log.d("Text : ", comment_text+"");

        CopyTv = view.findViewById(R.id.more_option_comment_Copy);
        DeleteTV = view.findViewById(R.id.more_option_comment_Delete);
        EditTv = view.findViewById(R.id.more_option_comment_Edit);

        if(guest.get(sessionManager.GNAME).equals("UserGuest000")){
            CopyTv.setVisibility(View.VISIBLE);
            DeleteTV.setVisibility(View.GONE);
            EditTv.setVisibility(View.GONE);
        }
        if(user.get(sessionManager.UID).equals(commenter_userid)){
            CopyTv.setVisibility(View.VISIBLE);
            DeleteTV.setVisibility(View.VISIBLE);
            EditTv.setVisibility(View.VISIBLE);
        }
        if(!user.get(sessionManager.UID).equals(commenter_userid)){
            CopyTv.setVisibility(View.VISIBLE);
            DeleteTV.setVisibility(View.GONE);
            EditTv.setVisibility(View.GONE);
        }

        CopyTv.setOnClickListener(view2 -> {
            CopyComment(comment_text);
            dismiss();
        });

        DeleteTV.setOnClickListener(view2 -> {
            DeleteComment(commentid, postid, commenter_userid);
            dismiss();
        });

        EditTv.setOnClickListener(view2 -> {
            Edit(commentid, postid, commenter_userid, comment_text);
            dismiss();
        });

        return view;
    }

    private void ReportComment(){

    }

    private void CopyComment(String text){
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Comment Text", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getContext(), "Saved to clip board", Toast.LENGTH_SHORT).show();
    }

    private void DeleteComment(String commentid, String postid, String commenter_userid){
        String url = getString(R.string.DeleteComment);

        RequestQueue q = Volley.newRequestQueue(getContext());

        StringRequest r = new StringRequest( //Request String type
                Request.Method.POST, //Get or Retrieve only Method of request
                url,
                response -> {
                    try {

                        listComments.clear();
                        commentAdapter = new CommentAdapter(getContext(), listComments);
                        commentAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                }, error -> {
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("commentid", commentid);
                param.put("postid", postid);
                param.put("commenter_userid", commenter_userid);
                return param;
            }
        };
        q.add(r);
    }

    private void Edit(String commentid, String postid, String commenter_userid, String comment){
        Intent intent = new Intent(getContext(), EditComment.class);
        intent.putExtra("commentid", commentid);
        intent.putExtra("postid", postid);
        intent.putExtra("commenter_userid", commenter_userid);
        intent.putExtra("comment_text", comment);
        getContext().startActivity(intent);
    }
}
