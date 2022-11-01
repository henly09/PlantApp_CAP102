package com.henzmontera.cap102_plantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SecManagePlanting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sec_manage_planting);

        TextView textView = findViewById(R.id.textView36);
        TextView textView1 = findViewById(R.id.textView42);

        String text = "Start putting nitrogenous fertilizers on the ground after around 3 to 6 months (in the case of fruit trees e.g. mango, pawpaw, oranges). In order to prevent leaching out, use nitrogenous fertilizers at intervals (downward movement of useful minerals or nutrients in water). Fertilizers can also be replaced by mulching, correctly prepared compost or manure from the farmyard, and ash from the kitchen (leftover from cooking).";
        String text1 = "The most frequent pest for mangoes and other fruits is the fruit fly, which typically results in significant damage to and substantial losses of these crops. Among other techniques, the usage of fruit fly traps is a well-liked approach to managing fruit flies. Additionally, scale insects and gall midges are pests. The most prevalent mango diseases, powdery mildew and anthracnose, primarily attack the blooms and sensitive foliage and cause the formation of black blemishes on mango fruits. While anthracnose may be managed using copper fungicides, powdery mildew can be sprayed with Sulphur or Bayleton.";

        SpannableString ss = new SpannableString(text);
        SpannableString ss1 = new SpannableString(text1);


        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                dialognitro();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                dialognitro();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan3 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                dialogpowdery();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan4 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                dialogant();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan5 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                dialogant();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan6 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                dialogcopper();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan7 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                dialogpowdery();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(clickableSpan1, 14, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan2, 175, 198, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(clickableSpan3, 351, 365, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(clickableSpan4, 370, 381, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(clickableSpan5, 499, 510, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(clickableSpan6, 532, 549, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(clickableSpan7, 551, 565, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(ss);
        textView1.setText(ss1);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView1.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void dialognitro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecManagePlanting.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecManagePlanting.this).inflate(
                R.layout.dialognitro,
                (LinearLayout) findViewById(R.id.linear_dialog)
        );
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.okbutton).setOnClickListener(view1 -> alertDialog.dismiss());
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
    private void dialogant() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecManagePlanting.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecManagePlanting.this).inflate(
                R.layout.dialogant,
                (LinearLayout) findViewById(R.id.linear_dialog)
        );
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.okbutton).setOnClickListener(view1 -> alertDialog.dismiss());
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
    private void dialogcopper() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecManagePlanting.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecManagePlanting.this).inflate(
                R.layout.dialogcopper,
                (LinearLayout) findViewById(R.id.linear_dialog)
        );
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.okbutton).setOnClickListener(view1 -> alertDialog.dismiss());
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
    private void dialogpowdery() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecManagePlanting.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecManagePlanting.this).inflate(
                R.layout.dialogpowdery,
                (LinearLayout) findViewById(R.id.linear_dialog)
        );
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.okbutton).setOnClickListener(view1 -> alertDialog.dismiss());
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}