package com.example.touniversity2;

import static com.example.touniversity2.R.string.NotFoundedVUZes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.touniversity2.databinding.ActivitySelectionBinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Selection extends AppCompatActivity {
    List<University> universityList = new LinkedList<>();
    private static int point_EGE= 0;
    private static boolean top=false;
    private boolean call_adapter=false;
    private static boolean paid=false;
    private static String educational_place = "";
    private static int value_subject=0;
    private int sch;
    ArrayList<String> subject = new ArrayList<String>();
    String subjects;
    private ActivitySelectionBinding binding;
    private UniversityAdapter adapter; // Переменная класса для хранения адаптера
    private List<University> universityListAdapter; // Список университетов
    private static UniversityDatabase universityDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        universityDatabase = Room.databaseBuilder(getApplicationContext(), UniversityDatabase.class, "University").build();

        getAbiturientData();
         subjects = String.valueOf(subject);

        subjects=subjects.replace("[","");
        subjects=subjects.replace("]","");
        subjects=subjects.replace("]","");


    }
    public static UniversityDatabase getDatabase() {
        return universityDatabase;
    }
    public void onClickBack(View view){
        Intent intent = new Intent(Selection.this, HomeScreen.class);
        startActivity(intent);
        finish();
    }
    public void setDataToAdapter(){
        universityListAdapter = universityList; // Загрузите данные университетов

        // Создание адаптера один раз и сохранение его в переменной класса
        adapter = new UniversityAdapter(universityListAdapter);

        // Установка адаптера в RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(adapter);
        call_adapter=true;
    }
    public void BtClick(View view){
        sch++;
        Button btn = findViewById(R.id.button2);
        new Thread(new Runnable() {
            public void run() {
                UniversityDatabase db = Selection.getDatabase();

                if(point_EGE==0){
                    universityList = db.getUniversityDAO().getAllUniversity();
                }
                else {
                    if (paid && (educational_place.equals("Москва") || educational_place.equals("Санкт-Петербург"))) {
                        universityList = (universityDatabase.getUniversityDAO().chooseUniversityPaid(point_EGE, subjects, educational_place));
                    }
                    if (!paid && (educational_place.equals("Москва") || educational_place.equals("Санкт-Петербург"))) {
                        universityList = (universityDatabase.getUniversityDAO().chooseUniversity(point_EGE, subjects, educational_place));
                    }
                    if (paid && !(educational_place.equals("Москва") || educational_place.equals("Санкт-Петербург"))) {
                        universityList = (universityDatabase.getUniversityDAO().chooseUniversityPaidRegion(point_EGE, subjects));
                    }
                    if (!paid && !(educational_place.equals("Москва") || educational_place.equals("Санкт-Петербург"))) {
                        universityList = (universityDatabase.getUniversityDAO().chooseUniversityRegion(point_EGE, subjects));
                    }
                }

            }
        }).start();



       setDataToAdapter();
       if(sch==2){btn.setVisibility(View.INVISIBLE);}
       if(sch==2 && universityList.isEmpty()){
           Toast.makeText(this, NotFoundedVUZes, Toast.LENGTH_SHORT).show();
       }
    }


    public void getAbiturientData(){
        point_EGE = AbiturientData.getPoint();
        top = AbiturientData.isTop();
        paid = AbiturientData.isPaid();
        educational_place = AbiturientData.getEducational_place();
        value_subject = AbiturientData.getValue_subject();
        subject = AbiturientData.getSubject();
    }
    public void clickonSearch(View view){
        if(!universityList.isEmpty() && call_adapter){
        EditText editText = findViewById(R.id.search);
        adapter.getFilter().filter(editText.getText());}
    }

}