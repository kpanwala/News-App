package com.haerul.popularnews;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    static boolean flag=false;
    static int theme;
    static String category="";

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        RelativeLayout ent = (RelativeLayout) findViewById(R.id.ent);
        RelativeLayout sports = (RelativeLayout) findViewById(R.id.sports);
        RelativeLayout business = (RelativeLayout) findViewById(R.id.business);
        RelativeLayout tech = (RelativeLayout) findViewById(R.id.technology);
        RelativeLayout health = (RelativeLayout) findViewById(R.id.health);
        RelativeLayout sci = (RelativeLayout) findViewById(R.id.science);

        MainActivity Main_act = new MainActivity();
        final String country = Main_act.getCountry();

        ent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("entertainment");
                flag = true;
                finish();
            }
        });

        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("technology");
                flag = true;
                finish();
            }
        });

        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("business");
                flag = true;
                finish();
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("sports");
                flag = true;
                finish();
            }
        });

        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("health");
                flag = true;
                finish();
            }
        });

        sci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("science");
                flag = true;
                finish();
            }
        });

    }

    public boolean hasCategory(){
        return flag;
    }

    public String getCategory(){ return category; }

    public void setCategory(String category){ this.category=category; }

    public int getTheme1(){ return theme; }

}