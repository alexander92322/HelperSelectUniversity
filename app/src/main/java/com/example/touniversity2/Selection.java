package com.example.touniversity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Selection extends AppCompatActivity {
    public static int point_EGE= 0;
    public static boolean top=false;

    public static boolean paid=false;
    public static String educational_place = "";
    public static int value_subject=0;
    ArrayList<String> subject = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        getAbiturientData();
        Button bt = findViewById(R.id.button2);
//        bt.setText(AbiturientData.getPoint());
        bt.setText(point_EGE+ " " + top+ " " + paid +" "+ educational_place + " " + value_subject+ " "+ subject);


    }
    public void getAbiturientData(){
        point_EGE = AbiturientData.getPoint();
        top = AbiturientData.isTop();
        paid = AbiturientData.isPaid();
        educational_place = AbiturientData.getEducational_place();
        value_subject = AbiturientData.getValue_subject();
        subject = AbiturientData.getSubject();
    }

}