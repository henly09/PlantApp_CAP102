package com.henzmontera.cap102_plantapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.kibotu.timebomb.TimeBomb;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button LoginButton;
    EditText UserEditText;
    EditText PasswordEditText;
    TextView RegisText;
    SQLiteDatabase myDB;
    int ca,cb,cc,cd,a_test;
    String sca,scb,scc,scd, sa_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

      //  FirebaseTest();
      // For Intro Slide Purposes
        try {
            ForAppIntroSlidesCreateTable();
            myDB = openOrCreateDatabase("IntroSlideCheckStatus.db", 0, null);
            Cursor a = myDB.rawQuery("SELECT COUNT(*) AS count FROM logintomaincheckbox",null);
            Cursor b = myDB.rawQuery("SELECT COUNT(*) AS count FROM proc_am_to_ama",null);
            Cursor c = myDB.rawQuery("SELECT COUNT(*) AS count FROM proc_cm_to_cma",null);
            Cursor d = myDB.rawQuery("SELECT COUNT(*) AS count FROM proc_im_to_ima",null);
            while(a.moveToNext()){
                ca = a.getColumnIndex("count");
                sca = a.getString(ca);
            }
            while(b.moveToNext()){
                cb = b.getColumnIndex("count");
                scb = b.getString(cb);
            }
            while(c.moveToNext()){
                cc = c.getColumnIndex("count");
                scc = c.getString(cc);
            }
            while(d.moveToNext()){
                cd = d.getColumnIndex("count");
                scd = d.getString(cd);
            }
            if (sca.equals("0") && scb.equals("0") && scc.equals("0") && scd.equals("0") ){
                ForAppIntroSlidesInsertData();
            }
            myDB.close();
            // For Intro Slide Purposes
        } catch (Exception e) {
            Toast.makeText(this, "Error: "+ e, Toast.LENGTH_SHORT).show();
        }

        LoginButton = findViewById(R.id.LoginButton);
        UserEditText = findViewById(R.id.editLoginUsernameText);
        PasswordEditText = findViewById(R.id.editLoginPasswordText);
        RegisText = findViewById(R.id.RegisterText);

        /////////////////////////////////////////////////////////////////////////////

        RegisText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        //////////////////////////////////////////////////////////////////////////////

        LoginButton.setOnClickListener(view -> {

            Intent intentM = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intentM);

/*            myDB = openOrCreateDatabase("IntroSlideCheckStatus.db", 0, null);
            Cursor ma_checkbox = myDB.rawQuery("SELECT COUNT(*) as count FROM logintomaincheckbox WHERE logintomaincheckbox.status = ?;", new String[] {"enable"});
            while(ma_checkbox.moveToNext()){
                a_test = ma_checkbox.getColumnIndex("count");
                sa_test = ma_checkbox.getString(a_test);
            }
            if(sa_test.equals("1")){
                Intent intent = new Intent(LoginActivity.this, LogToMainSlides.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
               startActivity(intent);
                finish();
            }
            myDB.close();*/
        });
    }

    private void ForAppIntroSlidesCreateTable(){
        try {
            myDB = openOrCreateDatabase("IntroSlideCheckStatus.db", 0, null);

            myDB.execSQL("create table if not exists logintomaincheckbox (" +
                    "ID integer primary key autoincrement," +
                    "status varchar(255))");

            myDB.execSQL("create table if not exists proc_am_to_ama (" +
                    "ID integer primary key autoincrement," +
                    "status varchar(255))");

            myDB.execSQL("create table if not exists proc_cm_to_cma (" +
                    "ID integer primary key autoincrement," +
                    "status varchar(255))");

            myDB.execSQL("create table if not exists proc_im_to_ima (" +
                    "ID integer primary key autoincrement," +
                    "status varchar(255))");

            myDB.close();
        } catch (Exception e){
            Toast.makeText(this, "Error occured. Please Try Again Later." + e, Toast.LENGTH_SHORT).show();
        }

    }

    private void ForAppIntroSlidesInsertData(){

            myDB = openOrCreateDatabase("IntroSlideCheckStatus.db", 0, null);

            ContentValues cvlogintocheckbox = new ContentValues();
            cvlogintocheckbox.put("status", "enable");
            myDB.insert("logintomaincheckbox", null, cvlogintocheckbox);

            ContentValues cv_proc_am_to_ama = new ContentValues();
            cv_proc_am_to_ama.put("status", "enable");
            myDB.insert("proc_am_to_ama", null, cv_proc_am_to_ama);

            ContentValues cv_proc_cm_to_cma = new ContentValues();
            cv_proc_cm_to_cma.put("status", "enable");
            myDB.insert("proc_cm_to_cma", null, cv_proc_cm_to_cma);

            ContentValues cv_proc_im_to_ima = new ContentValues();
            cv_proc_im_to_ima.put("status", "enable");
            myDB.insert("proc_im_to_ima", null, cv_proc_im_to_ima);

            myDB.close();

    }

    public void FirebaseTest(){

        TimeBomb.enableLogging(BuildConfig.DEBUG);
        TimeBomb.bombAfterDays(this, BuildConfig.BUILD_DATE, 0);

    }

}