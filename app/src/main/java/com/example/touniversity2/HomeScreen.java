package com.example.touniversity2;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
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
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

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
    ArrayList<String> subject = new ArrayList<String>();
    public static final ArrayList<String> EmptyList = new ArrayList<String>();

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

        if(point<min_point && point>0 && data_correct==true){
            Toast.makeText(this, R.string.min_point, Toast.LENGTH_LONG).show();
            data_correct=false;
        }

        if(point>420 && value_subject == 3 && data_correct==true){
            Toast.makeText(this, R.string.max_point, Toast.LENGTH_LONG).show();
            data_correct=false;
        } else if (point>320 && value_subject == 2 && data_correct==true) {
            Toast.makeText(this, R.string.max_point, Toast.LENGTH_LONG).show();
            data_correct=false;
        }


        //subject
        if(checkBox_math.isChecked() && checkBox_math_base.isChecked() && data_correct==true)
        {
            Toast.makeText(this, R.string.two_equals_subject, Toast.LENGTH_LONG).show();
            data_correct=false;
        }
        if(checkBox_math.isChecked()==false && checkBox_math_base.isChecked() == false && data_correct==true) {
            Toast.makeText(this, R.string.min_one_math, Toast.LENGTH_LONG).show();
            data_correct=false;
        }
        else if((value_subject!=3 && value_subject!=2) && data_correct==true) {
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
        if(data_correct==true){
            AbiturientData.setPoint(point);
            AbiturientData.setPaid(paid);
            AbiturientData.setTop(top);
            AbiturientData.setEducational_place(educational_place);
            AbiturientData.setSubject(subject);
            AbiturientData.setValue_subject(value_subject);

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
    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onResume() {
//
//        Toast.makeText(this, "qwe", Toast.LENGTH_SHORT).show();
        super.onResume();
//        value_subject=;
//        point=0;
//        top=false;
//        paid=false;
//        educational_place = "";
//        subject.removeAll(subject);
//
//        AbiturientData.setPaid(false);
//        AbiturientData.setTop(false);
//        AbiturientData.setEducational_place("");
//        AbiturientData.setPoint(0);
//        AbiturientData.setSubject(EmptyList);
//        AbiturientData.setValue_subject(0);



    }
}