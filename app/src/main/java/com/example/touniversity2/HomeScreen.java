package com.example.touniversity2;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;


public class HomeScreen extends AppCompatActivity {
//    University content;
//    UniversityDatabase universityDatabase;
    private static boolean data_correct=false;
    private static final int min_point=156;
    private static int point=0;
    private static int value_subject=0;
    private static boolean top=false;
private static boolean paid=false;
private static String educational_place = "";
    ArrayList<String> subject = new ArrayList<>();
    UniversityDatabase universityDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        deleteUncorrectData();

    }
    public void deleteUncorrectData(){

        RoomDatabase.Callback myCallback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };
        universityDatabase = Room.databaseBuilder(getApplicationContext(), UniversityDatabase.class,
                "University").addCallback(myCallback).build();
        new Thread(new Runnable() {
            public void run() {
                universityDatabase.getUniversityDAO().deleteItems();
                universityDatabase.getUniversityDAO().deletedublicate();
            }
        }).start();
    }
    @Override
    protected void onStart() {
        super.onStart();
        subject.removeAll(subject);
        value_subject = 0;
        int point = 0;
        top = false;
        paid = false;
        educational_place = "";

    }

    public void ClickonRetrait(View view){
        subject.removeAll(subject);
        value_subject=0;
        int point=0;
        top=false;
        paid=false;
        educational_place = "";
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void checkCorrectData(){
        data_correct=true;
        EditText editText = findViewById(R.id.EGE_points);
        RadioButton radioButton_top = findViewById(R.id.radioButton_yes);
        RadioButton radioButton_free = findViewById(R.id.radioButton_yes2);
        CheckBox checkBox_math = findViewById(R.id.math);
        CheckBox checkBox_math_base = findViewById(R.id.math_base);
        Spinner spinner_educational_place = findViewById(R.id.spinner);

        String str = String.valueOf(editText.getText());
        //points
        if(str.length()<=0){
            Toast.makeText(this, R.string.empty_fields_point, Toast.LENGTH_LONG).show();
            data_correct=false;
        }

        else{
            if(str.length()<5) {
                point = Integer.parseInt(str);
            }
            else{
                Toast.makeText(this, R.string.max_point, Toast.LENGTH_SHORT).show();
                data_correct=false;
            }
        }

        if(point<min_point && point>0 && data_correct){
            Toast.makeText(this, R.string.min_point, Toast.LENGTH_LONG).show();
            data_correct=false;
        }

        if(point>420 && value_subject == 3 && data_correct){
            Toast.makeText(this, R.string.max_point, Toast.LENGTH_LONG).show();
            data_correct=false;
        }
        if(point>520 && value_subject == 4 && data_correct){
            Toast.makeText(this, R.string.max_point, Toast.LENGTH_LONG).show();
            data_correct=false;
        }
        else if (point>320 && value_subject == 2 && data_correct) {
            Toast.makeText(this, R.string.max_point, Toast.LENGTH_LONG).show();
            data_correct=false;
        }


        //subject
        if(!checkBox_math.isChecked() && data_correct) {
            Toast.makeText(this, R.string.min_one_math, Toast.LENGTH_LONG).show();
            data_correct=false;
        }
        else if((value_subject!=3 && value_subject!=2 && value_subject!=4) && data_correct) {
            Toast.makeText(this, R.string.many_subjects, Toast.LENGTH_LONG).show();
            data_correct = false;
        }

        //top
        if(radioButton_top.isChecked()){
            top=true;
        }
        else{
            top=false;
        }
        //free or paid
        if(radioButton_free.isChecked()){
            paid=true;
        }
        else{
            paid=false;
        }
        educational_place=spinner_educational_place. getSelectedItem(). toString();
    }

    public void onCheckboxClicked(View view) {
        //Get and set information about selected subject
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            subject.add(checkBox.getText().toString());
            value_subject++;
        }
        else{
            subject.remove(checkBox.getText().toString());
            value_subject--;
        }
    }
    public void ClickonNext(View view){
        checkCorrectData();
        if(data_correct){
            subject.add(getString(R.string.Russian));
            Collections.sort(subject);
            AbiturientData.setPoint(point);
            AbiturientData.setPaid(paid);
            AbiturientData.setTop(top);
            AbiturientData.setEducational_place(educational_place);
            AbiturientData.setSubject(subject);
            AbiturientData.setValue_subject(value_subject);
            Intent intent = new Intent(this,Selection.class);
            startActivity(intent);
            this.finish();
            }

        }






}