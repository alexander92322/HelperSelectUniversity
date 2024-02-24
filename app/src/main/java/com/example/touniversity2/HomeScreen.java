package com.example.touniversity2;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.net.ssl.HttpsURLConnection;
import javax.security.auth.Subject;

public class HomeScreen extends AppCompatActivity {
    public static boolean data_correct=false;
    public static final int min_point=156;
    public static int point=0;
    public static int value_subject=0;

public static boolean top=false;
public String content = "?";
public String content2 = "?";
public static boolean paid=false;
public static String educational_place = "";
    ArrayList<String> subject = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        new Thread(new Runnable() {
            public void run() {
                try{
                     content = getContent("https://stackoverflow.com/");
                     content2 = getContent("https://javarush.com/groups/posts/isklyucheniya-java");
                }
                catch (IOException ex){
                    content="?";
                }
            }
        }).start();
    }


    public void ClickonRetrait(View view){
        subject.removeAll(subject);
        value_subject=0;
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
            Collections.sort(subject);
            AbiturientData.setPoint(point);
            AbiturientData.setPaid(paid);
            AbiturientData.setTop(top);
            AbiturientData.setEducational_place(educational_place);
            AbiturientData.setSubject(subject);
            AbiturientData.setValue_subject(value_subject);

            fill_database();
            Intent intent = new Intent(this,SplashSelection.class);
            startActivity(intent);
        }

    }



    private String getContent(String path) throws  IOException {
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
            return(buf.toString());
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
    public void fill_database() {
//        DbHelper dbHelper= new DbHelper(this);
//        dbHelper.DeleteDb();
//        UniversityData MGU1 = new UniversityData("МГУ", "Прикладная математика и информатика", 405, 300, "Математикка(профиль), Информатика, Физика", 1, "Москва", 391050, "bg.png","https://cs.msu.ru/", "https://vuzopedia.ru/vuz/1/napr/98", 3, "Математика");
//        UniversityData MGU2 = new UniversityData("МГУ", "Прикладнае математика и физика", 275, 126, "Математикка(профиль), Информатика, Физика", 1, "Москва", 435970, "bg.png","https://msu.ru/calc/program/58757/index.php?tmpl=clear&PROGRAM_ID=58757?tmpl=clear&PROGRAM_ID=58757", "https://vuzopedia.ru/vuz/1/napr/93", 3, "Физика");
//        UniversityData MGU3 = new UniversityData("МГУ", "Системное программирование и компьютерные науки", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//
//        UniversityData MGU4 = new UniversityData("МГУ", "Медиакоммуникации", 373, 140, "Литература, История,", 1, "Москва", 391050, "bg.png","https//:", "3", 3, "Творческое испытание");
//        UniversityData MGU5 = new UniversityData("МГУ", "Медиакоммуникации", 373, 140, "Литература, Обществознание", 1, "Москва", 391050, "bg.png","https//:", "3", 3, "Творческое испытание");
//        UniversityData MGTU1 = new UniversityData("МГТУ ", "Бизнес-информатика", 278, 241, "Обществознание, Математика(профиль)", 1, "Москва",324143 , "bg.png","https//:", "3", 3, "");
//        UniversityData MGTU2 = new UniversityData("МГТУ", "Биотехнические системы и технологии", 225, 178, "математикка(база), информатика", 1, "Москва", 329761, "bg.png","https//:", "3", 3, "");
//        UniversityData MGTU3 = new UniversityData("МГТУ", "Дизайн", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", "3", 3, "");
//        UniversityData MGTU4 = new UniversityData("МГТУ", "Компьютерная безопасность", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", "3", 3, "");
//        UniversityData MGTU5 = new UniversityData("МГТУ", "Лингвистика", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", "3", 3, "");
//        UniversityData MGTU6 = new UniversityData("МГТУ ", "Бизнес-информатика", 278, 241, "Информатика, Математика(профиль)", 1, "Москва",324143 , "bg.png","https//:", "3", 3, "");
//
//        UniversityData MIFI1 = new UniversityData("МИФИ", "Автоматизация технологических процессов и производств", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", "3", 3, "");
//        UniversityData MIFI2 = new UniversityData("МИФИ", "Биология", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", "3", 3, "");
//        UniversityData MIFI3 = new UniversityData("МИФИ", "Высокотехнологические плазменные и энергетические установки", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", "3", 3, "");
//        UniversityData NGU1 = new UniversityData("НГУ", "Бизнес-информатика", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", "3", 3, "");
//        UniversityData NGU2 = new UniversityData("НГУ", "Биология", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", "3", 3, "");
//        UniversityData NGU3 = new UniversityData("НГУ", "Журналистика", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", "3", 3, "");
//        UniversityData VSHE1 = new UniversityData("ВШЭ", "География", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", "3", 3, "");
//        UniversityData VSHE2 = new UniversityData("ВШЭ", "Бизнес-информатика", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", "3", 3, "");
//        UniversityData VSHE3 = new UniversityData("ВШЭ", "Государственное и муниципальное управление", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", "3", 3, "");
//        UniversityData MFTI1 = new UniversityData("МФТИ", "Системный анализ и управление", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", "3", 3, "");
//        UniversityData MFTI2 = new UniversityData("МФТИ", "Техническая физика", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", "3", 3, "");
//        UniversityData MFTI3 = new UniversityData("МФТИ", "Электроника и наноэлектроника", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", "3", 3, "");
//
//
//        UniversityData SPBGU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SPBGU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SPBGU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData TGU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData TGU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData TGU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData KFU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData KFU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData KFU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData ITMO1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData ITMO2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData ITMO3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData URFU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData URFU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData URFU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MGIMO1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MGIMO2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MGIMO3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RESH1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RESH2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RESH3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MGMU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MGMU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MGMU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData UFU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData UFU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData UFU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData NINGU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData NINGU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData NINGU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RGPU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RGPU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RGPU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData LETE1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData LETE2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData LETE3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RHTU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RHTU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RHTU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData VGU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData VGU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData VGU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SNIU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SNIU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SNIU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData NMIC1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData NMIC2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData NMIC3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SGMU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SGMU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SGMU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData NGTU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData NGTU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData NGTU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RGAU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RGAU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RGAU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MIREA1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MIREA2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MIREA3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SVFU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SVFU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SVFU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData UUGU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData UUGU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData UUGU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RGGU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RGGU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RGGU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData BFU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData BFU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData BFU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SKFU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SKFU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SKFU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData DGTU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData DGTU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData DGTU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData KGMU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData KGMU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData KGMU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MIET1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MIET2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MIET3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData PGU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData PGU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData PGU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MTUSI1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MTUSI2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:" , 3);
//        UniversityData MTUSI3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData INOPOLIS1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData INOPOLIS2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData INOPOLIS3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SINERGIA1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SINERGIA2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SINERGIA3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RMES1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RMES2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RMES3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MMU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MMU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData MMU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SPGEU1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SPGEU2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData SPGEU3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RANHIGS1 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RANHIGS2 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//        UniversityData RANHIGS3 = new UniversityData("Мгу", "Мехмат", 295, 280, "математикка(база), информатика", 1, "Москва", 120000, "bg.png","https//:", 3);
//
//        dbHelper.AddData(MGU1);
//        dbHelper.AddData(MGU2);
//        dbHelper.AddData(MGU3);
//        dbHelper.AddData(MGU4);
//        dbHelper.AddData(MFTI1);
//        dbHelper.AddData(MFTI2);
//        dbHelper.AddData(MFTI3);
//        dbHelper.AddData(MGTU1);
//        dbHelper.AddData(MGTU2);
//        dbHelper.AddData(MGTU3);
//        dbHelper.AddData(MGTU4);
//        dbHelper.AddData(MGTU5);
//        dbHelper.AddData(MGTU6);
//        dbHelper.AddData(VSHE1);
//        dbHelper.AddData(VSHE2);
//        dbHelper.AddData(VSHE3);
//        dbHelper.AddData(MIFI1);
//        dbHelper.AddData(MIFI2);
//        dbHelper.AddData(MIFI3);
//        dbHelper.AddData(SPBGU1);
//        dbHelper.AddData(SPBGU2);
//        dbHelper.AddData(SPBGU3);
//        dbHelper.AddData(ITMO1);
//        dbHelper.AddData(ITMO2);
//        dbHelper.AddData(ITMO3);
//        dbHelper.AddData(MGIMO1);
//        dbHelper.AddData(MGIMO2);
//        dbHelper.AddData(MGIMO3);
//        dbHelper.AddData(NGU1);
//        dbHelper.AddData(NGU2);
//        dbHelper.AddData(NGU3);
//        dbHelper.AddData(TGU1);
//        dbHelper.AddData(TGU2);
//        dbHelper.AddData(TGU3);
//        //top10
//        dbHelper.AddData(KFU1);
//        dbHelper.AddData(KFU2);
//        dbHelper.AddData(KFU3);
//        dbHelper.AddData(URFU1);
//        dbHelper.AddData(URFU2);
//        dbHelper.AddData(URFU3);
//        dbHelper.AddData(RESH1);
//        dbHelper.AddData(RESH2);
//        dbHelper.AddData(RESH3);
//        dbHelper.AddData(MGMU1);
//        dbHelper.AddData(MGMU2);
//        dbHelper.AddData(MGMU3);
//        dbHelper.AddData(UFU1);
//        dbHelper.AddData(UFU2);
//        dbHelper.AddData(UFU3);
//        dbHelper.AddData(NINGU1);
//        dbHelper.AddData(NINGU2);
//        dbHelper.AddData(NINGU3);
//        dbHelper.AddData(RGPU1);
//        dbHelper.AddData(RGPU2);
//        dbHelper.AddData(RGPU3);
//        dbHelper.AddData(LETE1);
//        dbHelper.AddData(LETE2);
//        dbHelper.AddData(LETE3);
//        dbHelper.AddData(RHTU1);
//        dbHelper.AddData(RHTU2);
//        dbHelper.AddData(RHTU3);
//        dbHelper.AddData(VGU1);
//        dbHelper.AddData(VGU2);
//        dbHelper.AddData(VGU3);
//        dbHelper.AddData(SNIU1);
//        dbHelper.AddData(SNIU2);
//        dbHelper.AddData(SNIU3);
//        dbHelper.AddData(NMIC1);
//        dbHelper.AddData(NMIC2);
//        dbHelper.AddData(NMIC3);
//        dbHelper.AddData(SGMU1);
//        dbHelper.AddData(SGMU2);
//        dbHelper.AddData(SGMU3);
//        dbHelper.AddData(NGTU1);
//        dbHelper.AddData(NGTU2);
//        dbHelper.AddData(NGTU3);
//        dbHelper.AddData(RGAU1);
//        dbHelper.AddData(RGAU2);
//        dbHelper.AddData(RGAU3);
//        dbHelper.AddData(MIREA1);
//        dbHelper.AddData(MIREA2);
//        dbHelper.AddData(MIREA3);
//        dbHelper.AddData(SVFU1);
//        dbHelper.AddData(SVFU2);
//        dbHelper.AddData(SVFU3);
//        dbHelper.AddData(UUGU1);
//        dbHelper.AddData(UUGU2);
//        dbHelper.AddData(UUGU3);
//        dbHelper.AddData(RGGU1);
//        dbHelper.AddData(RGGU2);
//        dbHelper.AddData(RGGU3);
//        dbHelper.AddData(BFU1);
//        dbHelper.AddData(BFU2);
//        dbHelper.AddData(BFU3);
//        dbHelper.AddData(SKFU1);
//        dbHelper.AddData(SKFU2);
//        dbHelper.AddData(SKFU3);
//        dbHelper.AddData(DGTU1);
//        dbHelper.AddData(DGTU2);
//        dbHelper.AddData(DGTU3);
//        dbHelper.AddData(KGMU1);
//        dbHelper.AddData(KGMU2);
//        dbHelper.AddData(KGMU3);
//        dbHelper.AddData(MIET1);
//        dbHelper.AddData(MIET2);
//        dbHelper.AddData(MIET3);
//        dbHelper.AddData(PGU1);
//        dbHelper.AddData(PGU2);
//        dbHelper.AddData(PGU3);
//        dbHelper.AddData(MTUSI1);
//        dbHelper.AddData(MTUSI2);
//        dbHelper.AddData(MTUSI3);
//        dbHelper.AddData(INOPOLIS1);
//        dbHelper.AddData(INOPOLIS2);
//        dbHelper.AddData(INOPOLIS3);
//        dbHelper.AddData(SINERGIA1);
//        dbHelper.AddData(SINERGIA2);
//        dbHelper.AddData(SINERGIA3);
//        dbHelper.AddData(RMES1);
//        dbHelper.AddData(RMES2);
//        dbHelper.AddData(RMES3);
//        dbHelper.AddData(MMU1);
//        dbHelper.AddData(MMU2);
//        dbHelper.AddData(MMU3);
//        dbHelper.AddData(SPGEU1);
//        dbHelper.AddData(SPGEU2);
//        dbHelper.AddData(SPGEU3);
//        dbHelper.AddData(RANHIGS1);
//        dbHelper.AddData(RANHIGS2);
//        dbHelper.AddData(RANHIGS3);








    }
}