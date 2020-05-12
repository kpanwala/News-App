package com.haerul.popularnews;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.haerul.popularnews.MainActivity.country;

public class Main3Activity extends AppCompatActivity {

    static boolean flag=false;
    static String country="";

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        RelativeLayout india = (RelativeLayout) findViewById(R.id.india);
        RelativeLayout us = (RelativeLayout) findViewById(R.id.usa);
        RelativeLayout canada = (RelativeLayout) findViewById(R.id.canada);
        RelativeLayout germany = (RelativeLayout) findViewById(R.id.germany);
        RelativeLayout uk = (RelativeLayout) findViewById(R.id.uk);
        RelativeLayout australia = (RelativeLayout) findViewById(R.id.australia);
        RelativeLayout switzerland = (RelativeLayout) findViewById(R.id.switzerland);
        RelativeLayout nz = (RelativeLayout) findViewById(R.id.nz);
        RelativeLayout mexico = (RelativeLayout) findViewById(R.id.mexico);
        RelativeLayout france = (RelativeLayout) findViewById(R.id.france);
        RelativeLayout norway = (RelativeLayout) findViewById(R.id.norway);

        MainActivity Main_act = new MainActivity();
        country = Main_act.getCountry();

        india.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCountry("in");
                flag = true;
                finish();
            }
        });

        us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCountry("us");
                flag = true;
                finish();
            }
        });

        canada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCountry("ca");
                flag = true;
                finish();
            }
        });

        germany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCountry("de");
                flag = true;
                finish();
            }
        });

        uk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCountry("gb");
                flag = true;
                finish();
            }
        });

        australia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCountry("au");
                flag = true;
                finish();
            }
        });

        switzerland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCountry("ch");
                flag = true;
                finish();
            }
        });

        nz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCountry("nz");
                flag = true;
                finish();
            }
        });

        mexico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCountry("mx");
                flag = true;
                finish();
            }
        });
        france.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCountry("fr");
                flag = true;
                finish();
            }
        });
        norway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCountry("no");
                flag = true;
                finish();
            }
        });


    }

    public String getCountry(){ return country; }

    public void setCountry(String country){ this.country=country; }

    public boolean hasCountry(){
        return flag;
    }

}