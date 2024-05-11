package com.example.touniversity2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.touniversity2.databinding.ActivityMainBinding;
import com.example.touniversity2.databinding.ActivitySelectionBinding;

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
    private int sch;
    ArrayList<String> subject = new ArrayList<String>();
    String subjects;
    private ActivitySelectionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getAbiturientData();
        textView = findViewById(R.id.textView);
//        bt.setText(point_EGE+ " " + top+ " " + paid +" "+ educational_place + " " + value_subject+ " "+ subject);
        deleteUncorrectData();
         subjects = String.valueOf(subject);

        subjects=subjects.replace("[","");
        subjects=subjects.replace("]","");
        subjects=subjects.replace("]","");
    }
    public void onClickBack(View view){
        Intent intent = new Intent(Selection.this, HomeScreen.class);
        startActivity(intent);
    }
    public void setDataToAdapter(){
        List<University> items=new ArrayList<>();
        items=universityList;
        UniversityAdapter adapter = new UniversityAdapter(items);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(adapter);
    }
    public void BtClick(View view){
        sch++;
        Button btn = findViewById(R.id.button2);
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
       setDataToAdapter();
       if(sch==2){btn.setVisibility(View.INVISIBLE);}
       if(sch==2 && universityList.isEmpty()){
           Toast.makeText(this, "К сожалению, по вашим фильтрам нет ВУЗов", Toast.LENGTH_SHORT).show();
       }
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

    public void getAbiturientData(){
        point_EGE = AbiturientData.getPoint();
        top = AbiturientData.isTop();
        paid = AbiturientData.isPaid();
        educational_place = AbiturientData.getEducational_place();
        value_subject = AbiturientData.getValue_subject();
        subject = AbiturientData.getSubject();
    }

}