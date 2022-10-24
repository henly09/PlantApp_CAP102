package com.henzmontera.cap102_plantapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    private List<ListComment> LISTCOMMENTS;

    public CommentAdapter(Context context, List<ListComment> LISTCOMMENTS) {
        this.context = context;
        this.LISTCOMMENTS = LISTCOMMENTS;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView COMMENTER_USERNAME, COMMENTER_TEXT, COMMENTER_DATE;
        private TextView COMMENTER_COMMENTID, COMMENTER_POSTID, COMMENTER_USERID;
        private ImageView COMMENTER_PROFILEPICTURE;
        private ConstraintLayout COMMENT_BOX;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            COMMENTER_COMMENTID = itemView.findViewById(R.id.comment_commentid);
            COMMENTER_POSTID = itemView.findViewById(R.id.comment_postid);
            COMMENTER_USERID = itemView.findViewById(R.id.comment_commenter_userid);
            COMMENTER_USERNAME = itemView.findViewById(R.id.comment_username);
            COMMENTER_TEXT = itemView.findViewById(R.id.comment_content);
            COMMENTER_PROFILEPICTURE = itemView.findViewById(R.id.comment_user_img);
            COMMENTER_DATE = itemView.findViewById(R.id.comment_date);
            COMMENT_BOX = itemView.findViewById(R.id.comment_box);

            COMMENT_BOX.setOnLongClickListener(v -> {
                MoreOptionComment();
                return false;
            });
        }

        private void MoreOptionComment(){
            String commentid = COMMENTER_COMMENTID.getText().toString();
            String postid = COMMENTER_POSTID.getText().toString();
            String commenter_userid = COMMENTER_USERID.getText().toString();
            String comment_text = COMMENTER_TEXT.getText().toString();

            Bundle bundle= new Bundle();

            bundle.putString("commentid", commentid);
            bundle.putString("postid", postid);
            bundle.putString("commenter_userid", commenter_userid);
            bundle.putString("comment_text", comment_text);

            MoreOptionCommentDialogFragment moreOptionCommentDialogFragment = MoreOptionCommentDialogFragment.newInstance();
            moreOptionCommentDialogFragment.setArguments(bundle);
            moreOptionCommentDialogFragment.show(((FragmentActivity)context).getSupportFragmentManager(),
                    "more_option_comment_dialog");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_comment,parent, false);
        return new CommentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.COMMENTER_COMMENTID.setText(LISTCOMMENTS.get(position).getCOMMENTID());
        holder.COMMENTER_POSTID.setText(LISTCOMMENTS.get(position).getPOSTID());
        holder.COMMENTER_USERID.setText(LISTCOMMENTS.get(position).getCOMMENTER_USERID());
        holder.COMMENTER_USERNAME.setText(LISTCOMMENTS.get(position).getCOMMENTER_USERNAME());
        holder.COMMENTER_TEXT.setText(LISTCOMMENTS.get(position).getTEXT());
        holder.COMMENTER_PROFILEPICTURE.setImageBitmap(StringtoImage(LISTCOMMENTS.get(position).getCOMMENTER_USERPROFILE()));
        holder.COMMENTER_DATE.setText(LISTCOMMENTS.get(position).getCREATED_AT());
    }

    @Override
    public int getItemCount() {
        return LISTCOMMENTS.size();
    }

    //Convert from String to Bitmap Image
    private Bitmap StringtoImage(String string){
        byte[] decodedString = Base64.decode(string, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
