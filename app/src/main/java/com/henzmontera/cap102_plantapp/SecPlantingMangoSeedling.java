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

public class SecPlantingMangoSeedling extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sec_planting_mango_seedling);


        TextView textView = findViewById(R.id.textView14);
        TextView textView1 = findViewById(R.id.textView16);
        TextView textView2 = findViewById(R.id.textView18);
        TextView textView3 = findViewById(R.id.textView20);
        TextView textView4 = findViewById(R.id.textView22);
        TextView textView5 = findViewById(R.id.textView24);
        TextView textView6 = findViewById(R.id.textView29);

        String text = "African nations widely cultivate mango trees (Mangifera indica L.). Mangoes are both a revenue crop and a food source for farmer families. For its sweet and aromatic fruits, which may be consumed fresh or made into juice, jam, fruit leather, chutney, or dried fruits, Kenyans grow both indigenous and alien species that have been naturalized there. The demand for high-quality mango fruits for both domestic and international markets has significantly expanded in mango-producing regions.";
        String text1 = "That there is sufficient land available for planting the seedling, ideally away from buildings and other structures or installations like telephone or power poles, water pipelines, or power lines, that the topography, fertility, and soil characteristics of the chosen place are suitable for planting the mango type, and the planted mango sapling has access to enough sunshine.";
        String text2 = "Gather all the tools and supplies required, including the grafted mango seedlings, manure and/or fertilizer, a watering container, a shovel, and a hoe (jembe).";
        String text3 = "A healthy seedling that is large enough to plant should be sought out since they have a better chance of surviving. For an excellent production/high yields, quality planting material (grafted) is essential. Seedlings should be chosen if they do not exhibit illness or pest symptoms.";
        String text4 = "Depending on how far it is from the nursery to the designated planting location, several methods of conveyance are employed for seedlings. Take care while transporting seedlings to avoid stacking them on top of one another, since this might harm the young trees. As this will lessen the possibility of harming them, it is advised that the seedlings be transported upright in boxes, plastic crates, or bags. Before moving them from the nursery to the planting location, water the seedlings you have chosen. To prevent the seedling from drying out while being transported, watering is done.";
        String text5 = "Mango trees grow best in humid tropical and semi-arid subtropical climates, as long as there is a dry interval of at least three to four months and there is enough light to trigger blooming. Mango tree spacing varies depending on the kind and growth conditions (dry and wet zone). The optimum spacing is 12 m × 12 m in moist and rich soils due to extensive vegetative development, however in the dry zone the spacing varies from 10 m x 10 m due to the restricted vegetative growth. Wherever feasible, planting holes should be created prior to the start of a rainy season so that water can accumulate there to improve the survival of the seedling that is planted. The holes should be excavated to a depth of one meter, one meter in breadth, and one meter in length (1m x 1m x 1m). These holes should be spaced taking into account both the soil fertility and the mango tree canopy.";
        String text6 = "By gently pushing down the earth surrounding the seedling, you may create a basin around the trees base. After watering, the basin will aid in holding the water. Make sure the seedling is positioned upright, just how it was on the polythene tube at the nursery. Under young trees, spread a layer of mulch. Mulch prevents moisture loss and weed competition while providing organic matter, a key source of tree nutrients and food for beneficial soil microorganisms.";

        SpannableString ss = new SpannableString(text);
        SpannableString ss1 = new SpannableString(text1);
        SpannableString ss2 = new SpannableString(text2);
        SpannableString ss3 = new SpannableString(text3);
        SpannableString ss4 = new SpannableString(text4);
        SpannableString ss5 = new SpannableString(text5);
        SpannableString ss6 = new SpannableString(text6);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                dialogmangi();
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
                dialogel();
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
                dialogsoil();
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
                dialogmanure();
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
                dialogpest();
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
                dialogmethod();
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
                dialogsemi();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan8 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                dialogpolythene();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan9 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                dialogmulch();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan10 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                dialogmulch();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };


        ss.setSpan(clickableSpan1, 46, 62, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan2, 63, 64, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(clickableSpan3, 233, 253, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(clickableSpan4, 83, 89, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss3.setSpan(clickableSpan5, 268, 281, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss4.setSpan(clickableSpan6, 89, 110, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss5.setSpan(clickableSpan7, 44, 74, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss6.setSpan(clickableSpan8, 230, 244, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss6.setSpan(clickableSpan9, 299, 304, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss6.setSpan(clickableSpan10, 306, 311, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        textView.setText(ss);
        textView1.setText(ss1);
        textView2.setText(ss2);
        textView3.setText(ss3);
        textView4.setText(ss4);
        textView5.setText(ss5);
        textView6.setText(ss6);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView1.setMovementMethod(LinkMovementMethod.getInstance());
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
        textView3.setMovementMethod(LinkMovementMethod.getInstance());
        textView4.setMovementMethod(LinkMovementMethod.getInstance());
        textView5.setMovementMethod(LinkMovementMethod.getInstance());
        textView6.setMovementMethod(LinkMovementMethod.getInstance());
    }
    void dialogmangi() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecPlantingMangoSeedling.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecPlantingMangoSeedling.this).inflate(
                R.layout.dialogmangi,
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
    void dialogel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecPlantingMangoSeedling.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecPlantingMangoSeedling.this).inflate(
                R.layout.dialogel,
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
    void dialogsoil() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecPlantingMangoSeedling.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecPlantingMangoSeedling.this).inflate(
                R.layout.dialogsoil,
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
    void dialogmanure() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecPlantingMangoSeedling.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecPlantingMangoSeedling.this).inflate(
                R.layout.dialogmanure,
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
    void dialogpest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecPlantingMangoSeedling.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecPlantingMangoSeedling.this).inflate(
                R.layout.dialogpest,
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
    void dialogmethod() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecPlantingMangoSeedling.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecPlantingMangoSeedling.this).inflate(
                R.layout.dialogmethod,
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
    void dialogsemi() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecPlantingMangoSeedling.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecPlantingMangoSeedling.this).inflate(
                R.layout.dialogsemi,
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

    void dialogpolythene() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecPlantingMangoSeedling.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecPlantingMangoSeedling.this).inflate(
                R.layout.dialogpolythene,
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

    void dialogmulch() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecPlantingMangoSeedling.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SecPlantingMangoSeedling.this).inflate(
                R.layout.dialogmulch,
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
