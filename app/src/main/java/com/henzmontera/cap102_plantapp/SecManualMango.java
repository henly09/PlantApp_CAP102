package com.henzmontera.cap102_plantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.text.LineBreaker;
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

public class SecManualMango extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sec_manual_mango);

        TextView textView = findViewById(R.id.textView11);

        String text = "In an agricultural system, trees provide a variety of purposes in general. Through the production of fruits, vegetables, starch, and fodder, trees directly support the nutritional needs of families, communities, and livestock. In terms of the environment, trees help improve the soils and microclimates in the area, which increases food production and is especially beneficial for ecosystems that are fragile. In addition to supplying readily available building materials, trees are a significant source of fuel wood for farmers and rural communities as it is sometimes the sole source of energy in these places. Fruit trees, however, are more directly useful to humans in terms of nutrition and food because they produce fruits that are high in vitamins, proteins, vital fatty acids, and energy. As a result, fruit trees, both in rural and urban areas, play a significant part in the nutrition of children, women, and men. For instance, mango fruit is a great source of vitamins A, C, and B6, which are essential elements that primarily support healthy vision and a robust immune system for bodies that work normally. Farmers do not have a lot of experience with fruit trees, especially when it comes to planting them. Most of these farmers lack the fundamental knowledge necessary to establish fruit trees, such as timing considerations, technical details, and maintenance strategies. Its crucial to remember that planting fruit trees involves planning and preparation in order to ensure their health and productivity.";

        textView.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);


        SpannableString ss = new SpannableString(text);

        //Clickable Span inside Text View
        ClickableSpan clickableSpan1= new ClickableSpan() {

            //Set onClick to dialog
            @Override
            public void onClick(View widget) {
                dialognutri();
            }
        //Set color of the Clickable Span
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan2= new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                dialogmicro();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan3= new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                dialogfundamental();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan4= new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                dialogtiming();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan5= new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                dialogmaintenance();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        //Initialize TextView
        ss.setSpan(clickableSpan1, 168, 197, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ss.setSpan(clickableSpan2, 289, 303, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ss.setSpan(clickableSpan3, 1251, 1272, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ss.setSpan(clickableSpan4, 1317, 1338, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ss.setSpan(clickableSpan5, 1363, 1385, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }


    //Dialog inside Clickable Span
    void dialognutri(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SecManualMango.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecManualMango.this).inflate(
                R.layout.dialognutri,
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

    void dialogmicro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecManualMango.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecManualMango.this).inflate(
                R.layout.dialogmicro,
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
    void dialogfundamental() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecManualMango.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecManualMango.this).inflate(
                R.layout.dialogfundamental,
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
    void dialogtiming() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecManualMango.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecManualMango.this).inflate(
                R.layout.dialogtiming,
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
    void dialogmaintenance() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecManualMango.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecManualMango.this).inflate(
                R.layout.dialogmaintenance,
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