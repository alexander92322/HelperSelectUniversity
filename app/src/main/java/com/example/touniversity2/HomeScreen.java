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
    UniversityData content;
    UniversityDatabase universityDatabase;
    private static boolean data_correct=false;
    private static final int min_point=156;
    private static int point=0;
    private static int value_subject=0;
    private static boolean top=false;
private static boolean paid=false;
private static String educational_place = "";
    ArrayList<String> subject = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

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
        if(checkBox_math.isChecked() && checkBox_math_base.isChecked() && data_correct)
        {
            Toast.makeText(this, R.string.two_equals_subject, Toast.LENGTH_LONG).show();
            data_correct=false;
        }
        if(!checkBox_math.isChecked() && !checkBox_math_base.isChecked() && data_correct) {
            Toast.makeText(this, R.string.min_one_math, Toast.LENGTH_LONG).show();
            data_correct=false;
        }
        else if((value_subject!=3 && value_subject!=2) && data_correct && !checkBox_math_base.isChecked()) {
            Toast.makeText(this, R.string.many_subjects, Toast.LENGTH_LONG).show();
            data_correct = false;
        }
        else if (checkBox_math_base.isChecked() && !(value_subject==4 || value_subject==3) && data_correct){
            Toast.makeText(this, R.string.mathbase_checked, Toast.LENGTH_LONG).show();
            data_correct=false;
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
            subject.add("Русский язык");
            Collections.sort(subject);
            AbiturientData.setPoint(point);
            AbiturientData.setPaid(paid);
            AbiturientData.setTop(top);
            AbiturientData.setEducational_place(educational_place);
            AbiturientData.setSubject(subject);
            AbiturientData.setValue_subject(value_subject);
            Intent intent = new Intent(this,SplashSelection.class);
            startActivity(intent);
            this.finish();

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
                        try {
                            addback(getContent("https://vuzopedia.ru/vuz/1848/programs/bakispec/87"));
                            addback(getContent("https://vuzopedia.ru/vuz/1848/programs/bakispec/87"));
                            addback(getContent("https://vuzopedia.ru/vuz/1848/programs/bakispec/87"));
//                            addback(getContent("https://vuzopedia.ru/vuz/249/programs/bakispec/821"));
//                            addback(getContent("https://vuzopedia.ru/vuz/1612/programs/bakispec/9"));
//                            addback(getContent("https://vuzopedia.ru/vuz/1567/programs/bakispec/1670"));
//                            addback(getContent("https://vuzopedia.ru/vuz/1612/programs/bakispec/9"));
                            addback(getContent("https://vuzopedia.ru/vuz/1567/programs/bakispec/1666"));
//                            addback(getContent("https://vuzopedia.ru/vuz/4950/programs/bakispec/216"));
//                            addback(getContent("https://vuzopedia.ru/vuz/342/programs/bakispec/994"));
//                            addback(getContent("https://vuzopedia.ru/vuz/342/programs/bakispec/139"));
//                            addback(getContent("https://vuzopedia.ru/vuz/342/programs/bakispec/2455"));
//                            addback(getContent("https://vuzopedia.ru/vuz/342/programs/bakispec/199"));
//                            addback(getContent("https://vuzopedia.ru/vuz/1751/programs/bakispec/90"));
//                            addback(getContent("https://vuzopedia.ru/vuz/1380/programs/bakispec/43"));
//                            addback(getContent("https://vuzopedia.ru/vuz/5167/programs/bakispec/453"));
//                            addback(getContent("https://vuzopedia.ru/vuz/5167/programs/bakispec/932"));
//                            addback(getContent("https://vuzopedia.ru/vuz/1/programs/bakispec/731"));
//                            addback(getContent("https://vuzopedia.ru/vuz/1/programs/bakispec/44"));
//                            addback(getContent("https://vuzopedia.ru/vuz/1/programs/bakispec/10"));
//                            addback(getContent("https://vuzopedia.ru/vuz/4/programs/bakispec/94"));
//                            addback(getContent("https://vuzopedia.ru/vuz/4/programs/bakispec/833"));
//                            addback(getContent("https://vuzopedia.ru/vuz/1230/programs/bakispec/91"));
//                            addback(getContent("https://vuzopedia.ru/vuz/1230/programs/bakispec/142"));
//                            addback(getContent("https://vuzopedia.ru/vuz/4312/programs/bakispec/1282"));
//                            addback(getContent("https://vuzopedia.ru/vuz/4312/programs/bakispec/1109"));

                        } catch (IOException ex) {
                            content = null;
                        }
                    }
                }).start();
            }

        }
    public String sortSubjects(String input) {
        // Разделить строку по запятым и удалить пробелы в начале и конце каждого предмета
        String[] subjects = input.split(",\\s*");

        // Отсортировать массив предметов по алфавиту
        Arrays.sort(subjects);
        StringBuilder sortedSubjects = new StringBuilder();
        for (int i = 0; i < subjects.length; i++) {
            sortedSubjects.append(subjects[i]);
            // Добавить запятую после каждого предмета, кроме последнего
            if (i < subjects.length - 1) {
                sortedSubjects.append(", ");
            }
        }

        return sortedSubjects.toString();
    }

    private University getContent(String path) throws  IOException {
        String title;
        String called_program="";
        String called_university="";
        int subject_value;
        int pointf;
        int pointp;
        String subject=" ";
        String sortedsubject;
        String paid;
        String city;
        int price;
        String dvi="";

        int index;
        int index2;
        String findcity = "id=\"newChooseq\" class=\"mmob\">";
        String findcityEnd = "</span>";
        String findpointf = "которые есть в базе сайта";
        String findpointp1 = "<div class=\"itemNewBlockCombActive\" style=\"position: relative;";
        String findUniversityCalled = ", профиль";
        String findsubject1 = "1. ";
        String findsubject2 = "2. ";
        String findsubject3 = "3. ";
        String findprice = "Стоимость: <strong>от ";
        String in= " в ";

        String findsubjectEnd = " - ";
        String cut = "";

        BufferedReader reader=null;
        InputStream stream = null;
        HttpsURLConnection connection = null;
        try {
            URL url=new URL(path);
            connection =(HttpsURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(1000);
            connection.connect();
            stream = connection.getInputStream();
            reader= new BufferedReader(new InputStreamReader(stream));
            StringBuilder buf=new StringBuilder();
            String line;
            while ((line=reader.readLine()) != null) {
                buf.append(line).append("\n");
            }

            //get info for db fields..........................
            Document html = Jsoup.parse(String.valueOf(buf));
            String text = html.toString();

            index = text.indexOf(findpointf);
            try {
                pointf = Integer.parseInt(text.substring(index+82, index+85));
            }catch (Exception e){
                pointf=0;
            }

            try {
                index=0;
                index = text.indexOf(findpointp1);
                try {
                    pointp = Integer.parseInt(text.substring(index + 108, index + 111));
                }catch (Exception e){
                    pointp = Integer.parseInt(text.substring(index + 111, index + 113));
                }
            } catch (Exception e){
                pointp=0;
            }

            title = html.title();
            try{
                index=0;
                index = title.indexOf(findUniversityCalled);
                title = title.substring(0,index);
                called_program=title.substring(0, title.indexOf(in));
            }catch(Exception e) {
                title = html.title();
                called_program="";
            }

            try{
                index=0;
                called_university = title.substring(title.indexOf(in)+3,title.length());
            }catch(Exception e) {
                called_university = "";
            }



            if(pointf==0){
                paid="Платное";
            }
            else{
                paid="Платное/Бесплатное";
            }

            try {
                index = 0;
                index = text.indexOf(findcity);

                cut = text.substring(index + findcity.length()+1, index + findcity.length() + 31);
                index2 = cut.indexOf(findcityEnd);
                city = cut.substring(0, index2);
            }catch (Exception e){
                city="";
            }

            try{
                index = 0;
                index = text.indexOf(findprice);
                cut = text.substring(index+findcity.length()-7, index + findcity.length()-1);
                price = Integer.parseInt(cut);
            }catch (Exception e){
                price =0;
            }

            try{
                index=0;
                index = text.indexOf(findsubject1);
                cut = text.substring(index+findsubject1.length(),index+findsubject1.length()+31);
                index2 = cut.indexOf(findsubjectEnd);
                subject = cut.substring(0,index2);

                index = text.indexOf(findsubject2);
                cut = text.substring(index+findsubject2.length(),index+findsubject2.length()+31);
                index2 = cut.indexOf(findsubjectEnd);
                subject += ", " + cut.substring(0,index2);

                index = text.indexOf(findsubject3);
                cut = text.substring(index+findsubject3.length(),index+findsubject3.length()+31);
                index2 = cut.indexOf(findsubjectEnd);
                if(!cut.substring(0,1).equals("Д")){
                    subject += ", " + cut.substring(0,index2);
                    dvi=getString(R.string.BVI);
                }
                else{
                    dvi = getString(R.string.DVI);

                }
            }catch(Exception e) {
                subject="";
                dvi="";
            }
            subject_value = subject.length() - subject.replace(String.valueOf(","), "").length()+1;
            sortedsubject=sortSubjects(subject);


            title = html.title();
            //.....................
            University university = new University(called_university, called_program, pointf, pointp, sortedsubject,paid, city, price,path, subject_value,dvi);

            return(university);
        }
        finally {
            if (reader != null) {
                reader.close();
            }
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    public void addback(University university){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                universityDatabase.getUniversityDAO().addUniversity(university);
            }
        });

    }





}