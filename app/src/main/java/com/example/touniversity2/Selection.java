package com.example.touniversity2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Selection extends AppCompatActivity {
    University university = new University("","",0,0,"","","",0,"",0,"");
    List<University> universityList = new LinkedList<>();
    private static int point_EGE= 0;
    private static boolean top=false;
    UniversityDatabase universityDatabase;
    private static boolean paid=false;
    private static String educational_place = "";
    private static int value_subject=0;
    TextView textView;
    private int university_id;
    ArrayList<String> subject = new ArrayList<String>();
    String subjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        getAbiturientData();
        Button bt = findViewById(R.id.button2);
        textView = findViewById(R.id.textView);
//        bt.setText(AbiturientData.getPoint());
        bt.setText(point_EGE+ " " + top+ " " + paid +" "+ educational_place + " " + value_subject+ " "+ subject);
        deleteUncorrectData();
        //subjects.replace("[", "").replace("]", "");
         subjects = String.valueOf(subject);

        subjects=subjects.replace("[","");
        subjects=subjects.replace("]","");
        subjects=subjects.replace("]","");
//        if(paid){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {

//                }
//            }).start();
//        }
//        else if (!paid && (educational_place.equals("Москва") || educational_place.equals("Санкт-Петербург"))){
//            getList();
//        }
//        else if(paid && (!educational_place.equals("Москва") || !educational_place.equals("Санкт-Петербург"))){
//            getPaidListRegion();
//        }
//        else{
//            getListRegion();
//        }

    }
    public void BtClick(View view){
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
                if(paid && (educational_place.equals("Москва")||educational_place.equals("Санкт-Петербург"))){
                universityList=(universityDatabase.getUniversityDAO().chooseUniversityPaid(point_EGE,subjects, educational_place));}
                if(!paid && (educational_place.equals("Москва")||educational_place.equals("Санкт-Петербург"))){
                universityList=(universityDatabase.getUniversityDAO().chooseUniversity(point_EGE,subjects, educational_place));}
                if(paid && !(educational_place.equals("Москва")||educational_place.equals("Санкт-Петербург"))){
                    universityList=(universityDatabase.getUniversityDAO().chooseUniversityPaidRegion(point_EGE,subjects));}
                if(!paid && !(educational_place.equals("Москва")||educational_place.equals("Санкт-Петербург"))){
                    universityList=(universityDatabase.getUniversityDAO().chooseUniversityRegion(point_EGE,subjects));}

            }
        }).start();



        textView.setText(universityList.toString()+"");
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
            }
        }).start();
    }

    public void getPaidList(){
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
                //universityList= universityDatabase.getUniversityDAO().chooseUniversityPaid(university_id, point_EGE, String.valueOf(subject), educational_place);
               // universityList= universityDatabase.getUniversityDAO().chooseUniversityPaid(point_EGE);
                universityList.add(universityDatabase.getUniversityDAO().getUniversity(2));
            }
        }).start();

    }
    public void getList(){
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
                universityList= universityDatabase.getUniversityDAO().chooseUniversity(university_id, String.valueOf(subject), educational_place);
            }
        }).start();


    }
    public void getPaidListRegion(){
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
                //  universityList= universityDatabase.getUniversityDAO().chooseUniversityPaidRegion(university_id, point_EGE, String.valueOf(subject));
            }
        }).start();


    }
    public void getListRegion(){
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
               // universityList= universityDatabase.getUniversityDAO().chooseUniversityRegion(university_id, point_EGE, String.valueOf(subject));
            }
        }).start();


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