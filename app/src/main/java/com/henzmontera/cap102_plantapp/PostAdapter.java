package com.henzmontera.cap102_plantapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ListViewHolder>{
    private final int limit = 10;
    private Context context;
    private List<ListPost> LISTPOSTS;

    public PostAdapter(Context context, List<ListPost> LISTPOSTS) {
        this.context = context;
        this.LISTPOSTS = LISTPOSTS;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView UPostNameTV, UPostTimeTV, UPostDesc, UPostLikeC, UPostCommentC, UPostID, UPostUserId;
        private ImageView UPostProfPic;
        private ImageButton UMoreOption;
        private Button ULikeButton, UCommentButton;
        private SessionManager sessionManager = new SessionManager(context);

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

            //Button
            ULikeButton = itemView.findViewById(R.id.like);
            UCommentButton = itemView.findViewById(R.id.comment);

            //Image Button
            UMoreOption = itemView.findViewById(R.id.upostoption);

            //Identification
            UPostID = itemView.findViewById(R.id.upostid);
            UPostUserId = itemView.findViewById(R.id.uuserId);

            HashMap<String, String> GuestPov = sessionManager.getGuestDetails();
            String guestName = GuestPov.get(sessionManager.GNAME);
            if(guestName.equals("UserGuest000")){
                UMoreOption.setVisibility(View.GONE);
            } else {
                UMoreOption.setVisibility(View.VISIBLE);
            }

            UCommentButton.setOnClickListener(view ->{
                Intent intent = new Intent(context, ViewCommentPostActivity.class);
                intent.putExtra("POSTID", UPostID.getText().toString());
                context.startActivity(intent);
            });

            ULikeButton.setOnClickListener(view ->{
                HashMap<String, String> guest = sessionManager.getGuestDetails();
                if(guest.get(sessionManager.GNAME).equals("UserGuest000")){
                    Toast.makeText(context, "Please Login or Register to interact.", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, String> user = sessionManager.getUserDetail();
                    String userid = user.get(sessionManager.UID); // Get Current User's ID
                    String postid = UPostID.getText().toString(); // Get Poster's Post ID

                    Like(userid, postid);
                }
            });

            UMoreOption.setOnClickListener(view ->{
                String userid = UPostUserId.getText().toString();
                String postid = UPostID.getText().toString();
                String descrip = UPostDesc.getText().toString();

                Bundle bundle = new Bundle();

                bundle.putString("userid", userid);
                bundle.putString("postid", postid);
                bundle.putString("description", descrip);

                MoreOptionBottomDialogFragment moreoptionBottomDialogFragment = MoreOptionBottomDialogFragment.newInstance();
                moreoptionBottomDialogFragment.setArguments(bundle);
                moreoptionBottomDialogFragment.show(((FragmentActivity)context).getSupportFragmentManager(),
                        "more_option_dialog_fragment");
            });
        }

        private void UpdateLike(){  // Update object
            Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
            ULikeButton.startAnimation(shake);
            ULikeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_thumbs_up, 0, 0, 0);
            String likeC = UPostLikeC.getText().toString();
            StringTokenizer tokens = new StringTokenizer(likeC);
            String count = tokens.nextToken();
            int totallike = Integer.parseInt(count) + 1;
            String likeCount = String.valueOf(totallike);
            if(totallike<=1){
                UPostLikeC.setText(likeCount + " Like");
            } else {
                UPostLikeC.setText(likeCount + " Likes");
            }
        }

        private void UpdateUnLike(){ //Update object
            Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
            ULikeButton.startAnimation(shake);
            ULikeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_thumbs_up_trans, 0, 0, 0);
            String likeC = UPostLikeC.getText().toString();
            StringTokenizer tokens = new StringTokenizer(likeC);
            String count = tokens.nextToken();
            int totallike = Integer.parseInt(count) - 1;
            String likeCount = String.valueOf(totallike);
            if(totallike<=1){
                UPostLikeC.setText(likeCount + " Like");
            } else {
                UPostLikeC.setText(likeCount + " Likes");
            }
        }

        private void CheckingIfLiked(String userid, String postid){
            Log.d("userid:", userid+"");
            Log.d("postid:", postid+"");

            if (sessionManager.isLoggin()) {
                String StoreURL = context.getString(R.string.Like);
                RequestQueue q = Volley.newRequestQueue(context);
                StringRequest r = new StringRequest(
                        Request.Method.POST,
                        StoreURL,
                        response -> {
                            try {
                                if(response.equals("CANT ACCEPT EXISTING LIKE") || response.equals("Subquery returns more than 1 row")){
                                    ULikeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_thumbs_up, 0, 0, 0);
                                }
                            } catch (Exception e) {
                                Toast.makeText(context, e + "", Toast.LENGTH_SHORT).show();
                            }
                        }, error -> {
                    Toast.makeText(context, error + "", Toast.LENGTH_SHORT).show();
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
        }

        private void Like(String userid, String postid){
            Log.d("userid:", userid+"");
            Log.d("postid:", postid+"");

                if (sessionManager.isLoggin()) {
                    String StoreURL = context.getString(R.string.Like);
                    RequestQueue q = Volley.newRequestQueue(context);
                    StringRequest r = new StringRequest(
                            Request.Method.POST,
                            StoreURL,
                            response -> {
                                try {
                                    if(response.equals("CANT ACCEPT EXISTING LIKE") || response.equals("Subquery returns more than 1 row")){
                                        Toast.makeText(context, "Unlike", Toast.LENGTH_SHORT).show();
                                        Unlike(userid, postid);
                                    } else {
                                        Toast.makeText(context, "Liked!", Toast.LENGTH_SHORT).show();
                                        UpdateLike();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(context, e + "", Toast.LENGTH_SHORT).show();
                                }
                            }, error -> {
                        Toast.makeText(context, error + "", Toast.LENGTH_SHORT).show();
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
            }

        private void Unlike(String userid, String postid){
            if (sessionManager.isLoggin()) {
                String StoreURL = context.getString(R.string.Unlike);
                RequestQueue q = Volley.newRequestQueue(context);
                StringRequest r = new StringRequest(
                        Request.Method.POST,
                        StoreURL,
                        response -> {
                            try {
                                UpdateUnLike();
                            } catch (Exception e) {
                                Toast.makeText(context, e + "", Toast.LENGTH_SHORT).show();
                            }
                        }, error -> {
                    Toast.makeText(context, error + "", Toast.LENGTH_SHORT).show();
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
        holder.UPostNameTV.setText(LISTPOSTS.get(position).getUSERNAME());  //Username
        holder.UPostDesc.setText(LISTPOSTS.get(position).getPOSTDESCRIPTION()); // Description

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            format.setLenient(true);
            Date date = format.parse(LISTPOSTS.get(position).getPOSTTIME());

            Log.d("PostedDate:", date+"");
            holder.UPostTimeTV.setText(getDate2(date.getTime()));  // Time Stamp
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(LISTPOSTS.get(position).getCOMMENTCOUNT().isEmpty() || LISTPOSTS.get(position).getCOMMENTCOUNT().equals("0")){ // If Empty, set 0
            holder.UPostCommentC.setText("0 Comment");
        } else if(LISTPOSTS.get(position).getCOMMENTCOUNT().equals("1")){
            holder.UPostCommentC.setText(LISTPOSTS.get(position).getCOMMENTCOUNT() + " Comment");
        } else { // Else , display result
            holder.UPostCommentC.setText(LISTPOSTS.get(position).getCOMMENTCOUNT() + " Comments"); // Comment Count
        }

        if(LISTPOSTS.get(position).getLIKECOUNT().isEmpty() || LISTPOSTS.get(position).getLIKECOUNT().equals("0")){ // If Empty set 0
            holder.UPostLikeC.setText("0 Like");
        } else if(LISTPOSTS.get(position).getLIKECOUNT().equals("1")){
            holder.UPostLikeC.setText(LISTPOSTS.get(position).getLIKECOUNT() + " Like");
        }  else { // Else , display result
            holder.UPostLikeC.setText(LISTPOSTS.get(position).getLIKECOUNT() + " Likes");  //Like Count
        }

        if(LISTPOSTS.get(position).getPROFILEPIC().isEmpty()){  // If Empty, default profile image
            holder.UPostProfPic.setBackgroundResource(R.mipmap.ic_nature_foreground);
        }
        else{
            holder.UPostProfPic.setImageBitmap(StringtoImage(LISTPOSTS.get(position).getPROFILEPIC()));
        }

        holder.UPostID.setText(LISTPOSTS.get(position).getPOSTID());    // Post Id
        HashMap<String, String> user = holder.sessionManager.getUserDetail();
        String userid = user.get(holder.sessionManager.UID); // Get Current User's ID
        holder.CheckingIfLiked(userid, LISTPOSTS.get(position).getPOSTID());

        holder.UPostUserId.setText(LISTPOSTS.get(position).getPOSTUSERID());    // Poster's User Id
    }

    @Override
    public int getItemCount() {
        return LISTPOSTS.size();
//        if(LISTPOSTS.size() > limit){
//            return limit;
//        }
//        else
//        {
//            return Math.min(LISTPOSTS.size(), limit);
//        }
    }

    //Convert from String to Bitmap Image
    private Bitmap StringtoImage(String string) {
        byte[] decodedString = Base64.decode(string, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    private String getDate2(long timeAtMiliseconds){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat formatterYear = new SimpleDateFormat("MM/dd/yyyy");

            if (timeAtMiliseconds == 0) {
                return "";
            }

            String result = "now";
            String dataSot = formatter.format(new Date());
            Calendar calendar = Calendar.getInstance();

            long dayagolong = timeAtMiliseconds;
            calendar.setTimeInMillis(dayagolong);
            String agoformater = formatter.format(calendar.getTime());

            Date CurrentDate = null;
            Date CreateDate = null;

            try {
                CurrentDate = formatter.parse(dataSot);
                CreateDate = formatter.parse(agoformater);

                long different = Math.abs(CurrentDate.getTime() - CreateDate.getTime());

                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;
                long daysInMilli = hoursInMilli * 24;

                long elapsedDays = different / daysInMilli;
                different = different % daysInMilli;

                long elapsedHours = different / hoursInMilli;
                different = different % hoursInMilli;

                long elapsedMinutes = different / minutesInMilli;
                different = different % minutesInMilli;

                long elapsedSeconds = different / secondsInMilli;

                if (elapsedDays == 0) {
                    if (elapsedHours == 0) {
                        if (elapsedMinutes == 0) {
                            if (elapsedSeconds < 0) {
                                return "0" + " s";
                            } else {
                                if (elapsedSeconds > 0 && elapsedSeconds < 59) {
                                    return "now";
                                }
                            }
                        } else {
                            return String.valueOf(elapsedMinutes) + "m ago";
                        }
                    } else {
                        return String.valueOf(elapsedHours) + "h ago";
                    }

                } else {
                    if (elapsedDays <= 29) {
                        return String.valueOf(elapsedDays) + "d ago";
                    }
                    if (elapsedDays > 29 && elapsedDays <= 58) {
                        return "1Mth ago";
                    }
                    if (elapsedDays > 58 && elapsedDays <= 87) {
                        return "2Mth ago";
                    }
                    if (elapsedDays > 87 && elapsedDays <= 116) {
                        return "3Mth ago";
                    }
                    if (elapsedDays > 116 && elapsedDays <= 145) {
                        return "4Mth ago";
                    }
                    if (elapsedDays > 145 && elapsedDays <= 174) {
                        return "5Mth ago";
                    }
                    if (elapsedDays > 174 && elapsedDays <= 203) {
                        return "6Mth ago";
                    }
                    if (elapsedDays > 203 && elapsedDays <= 232) {
                        return "7Mth ago";
                    }
                    if (elapsedDays > 232 && elapsedDays <= 261) {
                        return "8Mth ago";
                    }
                    if (elapsedDays > 261 && elapsedDays <= 290) {
                        return "9Mth ago";
                    }
                    if (elapsedDays > 290 && elapsedDays <= 319) {
                        return "10Mth ago";
                    }
                    if (elapsedDays > 319 && elapsedDays <= 348) {
                        return "11Mth ago";
                    }
                    if (elapsedDays > 348 && elapsedDays <= 360) {
                        return "12Mth ago";
                    }

                    if (elapsedDays > 360 && elapsedDays <= 720) {
                        return "1 year ago";
                    }

                    if (elapsedDays > 720) {
                        Calendar calendarYear = Calendar.getInstance();
                        calendarYear.setTimeInMillis(dayagolong);
                        return formatterYear.format(calendarYear.getTime()) + "";
                    }
                }
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            return result;
    }
}
