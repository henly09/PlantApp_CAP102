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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            format.setLenient(true);
            Date date = format.parse(LISTCOMMENTS.get(position).getCREATED_AT());
            holder.COMMENTER_DATE.setText(getDate2(date.getTime()));  // Time Stamp
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    private String getDate2(long timeAtMiliseconds){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatterYear = new SimpleDateFormat("MM/dd/yyyy");

        if (timeAtMiliseconds == 0 ) {
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
            long different = Math.abs(CreateDate.getTime() - CurrentDate.getTime());

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
                        if (elapsedSeconds == 0) {
                            return "0" + " s";
                        } else {
                            if (elapsedSeconds > 0 && elapsedSeconds < 59) {
                                return "now";
                            }
                        }
                    } else {
                        if(elapsedMinutes == 1){
                            return elapsedMinutes + " minute ago";
                        } else {
                            return elapsedMinutes + " minutes ago";
                        }
                    }
                } else {
                    if(elapsedHours == 1){
                        return elapsedHours + " hour ago";
                    } else {
                        return elapsedHours + " hours ago";
                    }
                }

            } else {
                if (elapsedDays <= 29) {
                    if(elapsedDays == 1){
                        return elapsedDays + " day ago";
                    } else {
                        return elapsedDays + " days ago";
                    }
                }
                if (elapsedDays > 29 && elapsedDays <= 58) {
                    return "1 Month ago";
                }
                if (elapsedDays > 58 && elapsedDays <= 87) {
                    return "2 Months ago";
                }
                if (elapsedDays > 87 && elapsedDays <= 116) {
                    return "3 Months ago";
                }
                if (elapsedDays > 116 && elapsedDays <= 145) {
                    return "4 Months ago";
                }
                if (elapsedDays > 145 && elapsedDays <= 174) {
                    return "5 Months ago";
                }
                if (elapsedDays > 174 && elapsedDays <= 203) {
                    return "6 Months ago";
                }
                if (elapsedDays > 203 && elapsedDays <= 232) {
                    return "7 Months ago";
                }
                if (elapsedDays > 232 && elapsedDays <= 261) {
                    return "8 Months ago";
                }
                if (elapsedDays > 261 && elapsedDays <= 290) {
                    return "9 Months ago";
                }
                if (elapsedDays > 290 && elapsedDays <= 319) {
                    return "10 Months ago";
                }
                if (elapsedDays > 319 && elapsedDays <= 348) {
                    return "11 Months ago";
                }
                if (elapsedDays > 348 && elapsedDays <= 360) {
                    return "12 Months ago";
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
