package com.example.touniversity2;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
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

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;


public class HomeScreen extends AppCompatActivity {
    EditText editText;
    RadioButton radioButton_top ;
    RadioButton radioButton_free ;
    CheckBox checkBox_math ;
    CheckBox checkBox_obschestvo;
    CheckBox checkBox_inf;
    CheckBox checkBox_phys;
    CheckBox checkBox_bio;
    CheckBox checkBox_lit;
    CheckBox checkBox_chem;
    CheckBox checkBox_lang;
    CheckBox checkBox_hist;
    CheckBox checkBox_geo;


    // CheckBox checkBox_math_base = findViewById(R.id.math_base);
    Spinner spinner_educational_place ;
//    University content;
//    UniversityDatabase universityDatabase;
public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_POINT = "POINT";
    public static final String APP_PREFERENCES_CITY = "CITY";
    public static final String APP_PREFERENCES_TOP = "TOP";
    public static final String APP_PREFERENCES_PAID = "PAID";
    Set<String> defaultSet = new HashSet<>();
    SharedPreferences mSettings;

    private int idSelectedItems;
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
        editText = findViewById(R.id.EGE_points);
        radioButton_top= findViewById(R.id.radioButton_yes);
        radioButton_free= findViewById(R.id.radioButton_yes2);
        checkBox_math= findViewById(R.id.math);
        checkBox_phys= findViewById(R.id.phys);
        checkBox_inf=findViewById(R.id.info);
        checkBox_bio=findViewById(R.id.bio);
        checkBox_lit=findViewById(R.id.lit);
        checkBox_chem=findViewById(R.id.chemistry);
        checkBox_lang=findViewById(R.id.eng);
        checkBox_hist=findViewById(R.id.history);
        checkBox_obschestvo=findViewById(R.id.obschestvo);
        checkBox_geo=findViewById(R.id.geo);

        spinner_educational_place= findViewById(R.id.spinner);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (mSettings.contains(APP_PREFERENCES_POINT)) {
            editText.setText(String.valueOf(mSettings.getInt(APP_PREFERENCES_POINT, 0)));
        }
        if(mSettings.contains(APP_PREFERENCES_PAID)){
            radioButton_free.setChecked(mSettings.getBoolean(APP_PREFERENCES_PAID,false));
        }
        if(mSettings.contains(APP_PREFERENCES_TOP)){
            radioButton_top.setChecked(mSettings.getBoolean(APP_PREFERENCES_TOP,false));
        }
        if(mSettings.contains(APP_PREFERENCES_CITY)){
            spinner_educational_place.setSelection(((ArrayAdapter<String>)spinner_educational_place.getAdapter()).getPosition(mSettings.getString(APP_PREFERENCES_CITY,"")));
        }


        List<String> checkboxesList = new ArrayList<>(Arrays.asList("Математика (профиль)", "Обществознание", "Физика", "География", "Информатика", "Биология", "Химия", "История", "Литература", "Иностранный язык"));

        Set<String> ret = mSettings.getStringSet("subject_list", new HashSet<String>());
        for (String i : ret) {
            // Проверяем, есть ли текст чекбокса во множестве данных
            if (ret.contains(i)) {
                // Сделать чекбокс выбранным или обозначить его как выбранный на экране
                // Например, если у вас есть чекбоксы CheckBox1, CheckBox2, CheckBox3 и т.д.:
                if (i.equals("Математика (профиль)")) {
                    checkBox_math.setChecked(true);
                    subject.add(i);
                } else if (i.equals("Информатика")) {
                    checkBox_inf.setChecked(true);
                    subject.add(i);
                } else if (i.equals("Физика")) {
                    checkBox_phys.setChecked(true);
                    subject.add(i);
                } else if (i.equals("Обществознание")) {
                    checkBox_obschestvo.setChecked(true);
                    subject.add(i);
                } else if (i.equals("География")) {
                    checkBox_geo.setChecked(true);
                    subject.add(i);
                } else if (i.equals("История")) {
                    checkBox_hist.setChecked(true);
                    subject.add(i);
                } else if (i.equals("Иностранный язык")) {
                    checkBox_lang.setChecked(true);
                    subject.add(i);
                } else if (i.equals("Химия")) {
                    checkBox_chem.setChecked(true);
                    subject.add(i);
                } else if (i.equals("Биология")) {
                    checkBox_bio.setChecked(true);
                    subject.add(i);
                } else if (i.equals("Литература")) {
                    checkBox_lit.setChecked(true);
                    subject.add(i);
                }
            }
        }


    }
    public void savetoSharedPreference(){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_POINT, point);
        editor.putString(APP_PREFERENCES_CITY, educational_place);
        editor.putBoolean(APP_PREFERENCES_TOP, top);
        editor.putBoolean(APP_PREFERENCES_PAID, paid);

        Set<String> set = new HashSet<>(subject);

        editor.putStringSet("subject_list", set);
        editor.apply();
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
                // universityDatabase.getUniversityDAO().deleteItems();
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

    public void ClickonWithoutFiltres(View view){
        AbiturientData.setPoint(0);
        AbiturientData.setPaid(false);
        AbiturientData.setTop(false);
        AbiturientData.setEducational_place("");
        AbiturientData.setValue_subject(0);
        Intent intent = new Intent(this, Selection.class);
        startActivity(intent);


    }
    public void checkCorrectData(){
        data_correct=true;

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
        educational_place=spinner_educational_place. getSelectedItem().toString();
        //  idSelectedItems = (int) spinner_educational_place.getSelectedItemId();
    }

    private void countSubject() {
        if(checkBox_math.isChecked()){
            value_subject++;
            subject.add(checkBox_math.getText().toString());
        }
        if(checkBox_lit.isChecked()){
            value_subject++;
            subject.add(checkBox_lit.getText().toString());
        }
        if(checkBox_phys.isChecked()){
            value_subject++;
            subject.add(checkBox_phys.getText().toString());
        }
        if(checkBox_inf.isChecked()){
            value_subject++;
            subject.add(checkBox_inf.getText().toString());
        }
        if(checkBox_geo.isChecked()){
            value_subject++;
            subject.add(checkBox_geo.getText().toString());
        }
        if(checkBox_bio.isChecked()){
            value_subject++;
            subject.add(checkBox_bio.getText().toString());
        }
        if(checkBox_chem.isChecked()){
            value_subject++;
            subject.add(checkBox_chem.getText().toString());
        }
        if(checkBox_lang.isChecked()){
            value_subject++;
            subject.add(checkBox_lang.getText().toString());
        }
        if(checkBox_hist.isChecked()){
            value_subject++;
            subject.add(checkBox_hist.getText().toString());
        }
        if(checkBox_obschestvo.isChecked()){
            value_subject++;
            subject.add(checkBox_obschestvo.getText().toString());
        }

    }

    public void ClickonNext(View view){
        value_subject=0;
        subject.clear();
        countSubject();
        checkCorrectData();
        if(data_correct){
            savetoSharedPreference();
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

            }


        }


    public void ClickonNews(View view) {
        Intent intent = new Intent(this, NewsScreen.class);
        startActivity(intent);
    }
}