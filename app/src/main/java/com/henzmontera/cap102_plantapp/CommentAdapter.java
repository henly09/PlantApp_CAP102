package com.henzmontera.cap102_plantapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        private ImageView COMMENTER_PROFILEPICTURE;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            COMMENTER_USERNAME = itemView.findViewById(R.id.comment_username);
            COMMENTER_TEXT = itemView.findViewById(R.id.comment_content);
            COMMENTER_PROFILEPICTURE = itemView.findViewById(R.id.comment_user_img);
            COMMENTER_DATE = itemView.findViewById(R.id.comment_date);
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
