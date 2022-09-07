package com.henzmontera.cap102_plantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.ListViewHolder>{

    private Context context;
    private List<ListPost> LISTPOSTS;

    public UserPostAdapter(Context context, List<ListPost> LISTPOSTS) {
        this.context = context;
        this.LISTPOSTS = LISTPOSTS;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView UPostNameTV, UPostTimeTV, UPostDesc, UPostLikeC, UPostCommentC;
        private ImageView UPostProfPic, UPostImage;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            //textView
            UPostNameTV = itemView.findViewById(R.id.unametv);
            UPostTimeTV = itemView.findViewById(R.id.utimetv);
            UPostDesc = itemView.findViewById(R.id.descript);
            UPostLikeC = itemView.findViewById(R.id.plikeb);
            UPostCommentC = itemView.findViewById(R.id.pcommentco);

            //Image
            UPostProfPic = itemView.findViewById(R.id.picturetv);
            UPostImage = itemView.findViewById(R.id.pimagetv);
        }
    }

    @NonNull
    @Override
    public UserPostAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_post,parent, false);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostAdapter.ListViewHolder holder, int position) {

        holder.UPostNameTV.setText(LISTPOSTS.get(position).getUSERNAME());
        holder.UPostDesc.setText(LISTPOSTS.get(position).getPOSTDESC());
        holder.UPostTimeTV.setText(LISTPOSTS.get(position).getPOSTTIME());
        holder.UPostCommentC.setText(LISTPOSTS.get(position).getCOMMENTC());
        holder.UPostLikeC.setText(LISTPOSTS.get(position).getLIKEC());
        holder.UPostProfPic.setBackgroundResource(LISTPOSTS.get(position).getPROFILEPIC());
        holder.UPostImage.setBackgroundResource(LISTPOSTS.get(position).getPOSTIMAGES());
    }

    @Override
    public int getItemCount() {
        return LISTPOSTS.size();
    }


}
