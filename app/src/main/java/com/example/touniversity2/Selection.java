package com.example.touniversity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Selection extends AppCompatActivity {
    public static int point_EGE= 0;
    public static boolean top=false;

    public static boolean paid=false;
    public static String educational_place = "";
    ArrayList<String> subject = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String a="yt";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        Button bt = findViewById(R.id.button2);
//        bt.setText(AbiturientData.getPoint());
        bt.setText(AbiturientData.getEducational_place());


    }

}