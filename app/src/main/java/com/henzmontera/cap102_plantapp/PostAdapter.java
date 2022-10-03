package com.henzmontera.cap102_plantapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ListViewHolder>{

    private Context context;
    private List<ListPost> LISTPOSTS;

    public PostAdapter(Context context, List<ListPost> LISTPOSTS) {
        this.context = context;
        this.LISTPOSTS = LISTPOSTS;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView UPostNameTV, UPostTimeTV, UPostDesc, UPostLikeC, UPostCommentC, UPostID, UPostUserId;
        private ImageView UPostProfPic;
        private ImageView UPostImage;
        private Button ULikeButton, UCommentButton;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            //textView
            UPostNameTV = itemView.findViewById(R.id.unametv);  //username of user posted
            UPostTimeTV = itemView.findViewById(R.id.utimetv);  // timestamp posted
            UPostDesc = itemView.findViewById(R.id.descript);   // description
            UPostLikeC = itemView.findViewById(R.id.plikeb); // display count of likes
            UPostCommentC = itemView.findViewById(R.id.pcommentco); // display count of comments

            //Image
            UPostProfPic = itemView.findViewById(R.id.picturetv); //user's profile picture
            UPostImage = itemView.findViewById(R.id.pimagetv);  //user's posted picture

            //Button
            ULikeButton = itemView.findViewById(R.id.like);
            UCommentButton = itemView.findViewById(R.id.comment);

            //Identification
            UPostID = itemView.findViewById(R.id.upostid);
            UPostUserId = itemView.findViewById(R.id.uuserId);

            UCommentButton.setOnClickListener(view ->{
                Intent intent = new Intent(context, ViewCommentPostActivity.class);
                intent.putExtra("POSTID", UPostID.getText().toString());
                context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public PostAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_post,parent, false);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ListViewHolder holder, int position) {
        holder.UPostNameTV.setText(LISTPOSTS.get(position).getUSERNAME());
        holder.UPostDesc.setText(LISTPOSTS.get(position).getPOSTDESC());
        holder.UPostTimeTV.setText(LISTPOSTS.get(position).getPOSTTIME());
        holder.UPostCommentC.setText(LISTPOSTS.get(position).getCOMMENTC());
        holder.UPostLikeC.setText(LISTPOSTS.get(position).getLIKEC());
        if(LISTPOSTS.get(position).getPROFILEPIC().isEmpty()){
            holder.UPostProfPic.setBackgroundResource(R.mipmap.ic_nature_foreground);
        }
        else{
            holder.UPostProfPic.setImageBitmap(StringtoImage(LISTPOSTS.get(position).getPROFILEPIC()));
        }

        if(LISTPOSTS.get(position).getPOSTIMAGES().isEmpty()){
            ViewGroup.LayoutParams layoutParams = holder.UPostImage.getLayoutParams();
            layoutParams.width = 0;
            holder.UPostImage.setLayoutParams(layoutParams);
        }
        else{
            holder.UPostImage.setImageBitmap(StringtoImage(LISTPOSTS.get(position).getPOSTIMAGES()));
            ViewGroup.LayoutParams layoutParams = holder.UPostImage.getLayoutParams();
            layoutParams.width = 1100;
            layoutParams.height = 1100;
            holder.UPostImage.setLayoutParams(layoutParams);
        }
        holder.UPostID.setText(LISTPOSTS.get(position).getPOSTID());
        holder.UPostUserId.setText(LISTPOSTS.get(position).getUSERID());
    }

    @Override
    public int getItemCount() {
        return LISTPOSTS.size();
    }

    //Convert from String to Bitmap Image
    private Bitmap StringtoImage(String string){
        byte[] decodedString = Base64.decode(string, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
