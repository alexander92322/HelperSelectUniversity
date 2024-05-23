package com.example.touniversity2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
int progress = 0;
University content;
ProgressBar pb;
Handler handler = new Handler(Looper.getMainLooper());
    private int step=1;
    private static UniversityDatabase universityDatabase;

    static final int time_streams = 500;
    private static final String TRACKSTART = String.valueOf(R.string.trackstart);
private boolean hasVisited;

    public static UniversityDatabase getDatabase() {
        return universityDatabase;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        universityDatabase = Room.databaseBuilder(getApplicationContext(),
                        UniversityDatabase.class, "University")
                .build();
        pb = findViewById(R.id.progressBar);
        pb.setProgress(0);
        pb.setMax(300);
        setProgressValue(progress);

        SharedPreferences sp = getSharedPreferences(TRACKSTART, Context.MODE_PRIVATE);
        hasVisited = sp.getBoolean("hasVisited", false);
        if (!hasVisited && isOnline()) {
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("hasVisited", true);
            e.commit();
        }


        if(hasVisited){
            step=7;
        }
        else if(!isOnline()){
            step=7;

            new Thread(new Runnable() {
                public void run() {
                    UniversityDatabase db = MainActivity.getDatabase();

                    try {
                        University MGU1 = new University("МГУ", "Системное программирование и компьютерные науки", 405, 300, "Информатика, Математика (профиль), Русский язык, Физика", "Платное/Бесплатное", "Москва", 409610,"https://vuzopedia.ru/vuz/1/programs/bakispec/62",  3, "Математика");
                        University MGU2 = new University("МГУ", "Прикладная математика и физика", 275, 126, "Математика (профиль), Русский язык, Физика", "Платное/Бесплатное", "Москва", 435970, "https://vuzopedia.ru/vuz/1/napr/93", 3, "Физика");
                        University MGTU1 = new University("МГТУ", "Бизнес-информатика", 278, 241, "Математика (профиль), Русский язык, Обществознание", "Платное/Бесплатное" , "Москва",324143 , "https://vuzopedia.ru/vuz/4/programs/bakispec/603", 3, "Без вступительных испытаний");
                        University MGTU2 = new University("МГТУ", "Бизнес-информатика", 278, 241, "Информатика, Математика (профиль), Русский язык", "Платное/Бесплатное" , "Москва",324143 , "https://vuzopedia.ru/vuz/4/programs/bakispec/603", 3, "Без вступительных испытаний");

                        University MFTI1 = new University("МФТИ", "Системный анализ и управление", 295, 280, "Информатика, Математика (профиль), Русский язык", "Платное/Бесплатное", "Москва", 504000, "https://vuzopedia.ru/vuz/591/programs/bakispec/130",  3, "Без вступительных испытаний");
                        University MFTI2 = new University("МФТИ", "Системный анализ и управление", 295, 280, "Математика (профиль), Русский язык, Физика", "Платное/Бесплатное", "Москва", 504000, "https://vuzopedia.ru/vuz/591/programs/bakispec/130",  3, "Без вступительных испытаний");

                        University NGU1 = new University("НГУ", "Организационная психология", 249, 177, "Биология, Математика (профиль), Русский язык", "Платное/Бесплатное" ,"Новосибирск", 125000, "https://vuzopedia.ru/vuz/1612/programs/bakispec/561", 3, "Без вступительных испытаний");

                        University NGTU1 = new University("НГТУ", "Техническая защита информации", 230, 126, "Математика (профиль), Русский язык, Физика", "Платное/Бесплатное" ,"Новосибирск", 149000, "https://vuzopedia.ru/vuz/1567/programs/bakispec/807", 3, "Математика в инженерном деле");
                        University NGTU2 = new University("НГТУ", "Техническая защита информации", 230, 126, "Информатика, Математика (профиль), Русский язык", "Платное/Бесплатное" ,"Новосибирск", 149000, "https://vuzopedia.ru/vuz/1567/programs/bakispec/807", 3, "Математика в инженерном деле");

                        University SIN1 = new University("Синергия", "Предпринимательство", 0, 255, "Информатика, Математика (профиль), Русский язык", "Платное" ,"Москва", 230000, "https://vuzopedia.ru/vuz/1380/programs/bakispec/518", 3, "Математика в социально-экономическом профиле");
                        University SIN2 = new University("Синергия", "Предпринимательство", 0, 255, "История, Математика (профиль), Русский язык", "Платное" ,"Москва", 230000, "https://vuzopedia.ru/vuz/1380/programs/bakispec/518", 3, "Математика в социально-экономическом профиле");
                        University SIN3 = new University("Синергия", "Предпринимательство", 0, 255, "География, Математика (профиль), Русский язык", "Платное" ,"Москва", 230000, "https://vuzopedia.ru/vuz/1380/programs/bakispec/518", 3, "Математика в социально-экономическом профиле");
                        University SIN4 = new University("Синергия", "Предпринимательство", 0, 255, "Иностранный, Математика (профиль), Русский язык", "Платное" ,"Москва", 230000, "https://vuzopedia.ru/vuz/1380/programs/bakispec/518", 3, "Математика в социально-экономическом профиле");

                        University SPBGU1 = new University("СПбГУ", "Математика", 267, 260, "Информатика, Математика (профиль), Русский язык", "Платное/Бесплатное" ,"Москва", 317000, "https://vuzopedia.ru/vuz/1239/programs/bakispec/513", 3, "Без вступительных испытаний");
                        University SPBGU2 = new University("СПбГУ", "Математика", 267, 260, "Математика (профиль), Русский язык, Физика", "Платное/Бесплатное" ,"Москва", 317000, "https://vuzopedia.ru/vuz/1239/programs/bakispec/513", 3, "Без вступительных испытаний");



                        addback(MGU1);
                        addback(MGU2);
                        addback(MGTU1);
                        addback(MGTU2);
                        addback(MFTI1);
                        addback(MFTI2);
                        addback(NGU1);
                        addback(NGTU1);
                        addback(NGTU2);
                        addback(SIN1);
                        addback(SIN2);
                        addback(SIN3);
                        addback(SIN4);
                        addback(SPBGU1);
                        addback(SPBGU2);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
        else{
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
                        University MGU1 = new University("МГУ", "Системное программирование и компьютерные науки", 405, 300, "Информатика, Математика (профиль), Русский язык, Физика", "Платное/Бесплатное", "Москва", 409610,"https://vuzopedia.ru/vuz/1/programs/bakispec/62",  3, "Математика");
                        University MGU2 = new University("МГУ", "Прикладная математика и физика", 275, 126, "Математика (профиль), Русский язык, Физика", "Платное/Бесплатное", "Москва", 435970, "https://vuzopedia.ru/vuz/1/napr/93", 3, "Физика");
                        University MGTU1 = new University("МГТУ", "Бизнес-информатика", 278, 241, "Математика (профиль), Русский язык, Обществознание", "Платное/Бесплатное" , "Москва",324143 , "https://vuzopedia.ru/vuz/4/programs/bakispec/603", 3, "Без вступительных испытаний");
                        University MGTU2 = new University("МГТУ", "Бизнес-информатика", 278, 241, "Информатика, Математика (профиль), Русский язык", "Платное/Бесплатное" , "Москва",324143 , "https://vuzopedia.ru/vuz/4/programs/bakispec/603", 3, "Без вступительных испытаний");

                        University MFTI1 = new University("МФТИ", "Системный анализ и управление", 295, 280, "Информатика, Математика (профиль), Русский язык", "Платное/Бесплатное", "Москва", 504000, "https://vuzopedia.ru/vuz/591/programs/bakispec/130",  3, "Без вступительных испытаний");
                        University MFTI2 = new University("МФТИ", "Системный анализ и управление", 295, 280, "Математика (профиль), Русский язык, Физика", "Платное/Бесплатное", "Москва", 504000, "https://vuzopedia.ru/vuz/591/programs/bakispec/130",  3, "Без вступительных испытаний");

                        University NGU1 = new University("НГУ", "Организационная психология", 249, 177, "Биология, Математика (профиль), Русский язык", "Платное/Бесплатное" ,"Новосибирск", 125000, "https://vuzopedia.ru/vuz/1612/programs/bakispec/561", 3, "Без вступительных испытаний");

                        University NGTU1 = new University("НГТУ", "Техническая защита информации", 230, 126, "Математика (профиль), Русский язык, Физика", "Платное/Бесплатное" ,"Новосибирск", 149000, "https://vuzopedia.ru/vuz/1567/programs/bakispec/807", 3, "Математика в инженерном деле");
                        University NGTU2 = new University("НГТУ", "Техническая защита информации", 230, 126, "Информатика, Математика (профиль), Русский язык", "Платное/Бесплатное" ,"Новосибирск", 149000, "https://vuzopedia.ru/vuz/1567/programs/bakispec/807", 3, "Математика в инженерном деле");

//                        University SIN1 = new University("Синергия", "Предпринимательство", 270, 255, "Информатика, Математика (профиль), Русский язык", "Платное/Бесплатное" ,"Москва", 230000, "https://vuzopedia.ru/vuz/1380/programs/bakispec/518", 3, "Математика в социально-экономическом профиле");
//                        University SIN2 = new University("Синергия", "Предпринимательство", 270, 255, "История, Математика (профиль), Русский язык", "Платное/Бесплатное" ,"Москва", 230000, "https://vuzopedia.ru/vuz/1380/programs/bakispec/518", 3, "Математика в социально-экономическом профиле");
//                        University SIN3 = new University("Синергия", "Предпринимательство", 270, 255, "География, Математика (профиль), Русский язык", "Платное/Бесплатное" ,"Москва", 230000, "https://vuzopedia.ru/vuz/1380/programs/bakispec/518", 3, "Математика в социально-экономическом профиле");
//                        University SIN4 = new University("Синергия", "Предпринимательство", 270, 255, "Иностранный, Математика (профиль), Русский язык", "Платное/Бесплатное" ,"Москва", 230000, "https://vuzopedia.ru/vuz/1380/programs/bakispec/518", 3, "Математика в социально-экономическом профиле");

                        University SPBGU1 = new University("СПбГУ", "Математика", 267, 260, "Информатика, Математика (профиль), Русский язык", "Платное/Бесплатное" ,"Москва", 317000, "https://vuzopedia.ru/vuz/1239/programs/bakispec/513", 3, "Без вступительных испытаний");
                        University SPBGU2 = new University("СПбГУ", "Математика", 267, 260, "Математика (профиль), Русский язык, Физика", "Платное/Бесплатное" ,"Москва", 317000, "https://vuzopedia.ru/vuz/1239/programs/bakispec/513", 3, "Без вступительных испытаний");



                        addback(MGU1);
                        addback(MGU2);
                        addback(MGTU1);
                        addback(MGTU2);
                        addback(MFTI1);
                        addback(MFTI2);
                        addback(NGU1);
                        addback(NGTU1);
                        addback(NGTU2);
//                        addback(SIN1);
//                        addback(SIN2);
//                        addback(SIN3);
//                        addback(SIN4);
                        addback(SPBGU1);
                        addback(SPBGU2);

                        addback(getContent("https://vuzopedia.ru/vuz/1848/programs/bakispec/87"));
                        addback(getContent("https://vuzopedia.ru/vuz/725/programs/bakispec/164"));
                        Thread.sleep(1000);
                        addback(getContent("https://vuzopedia.ru/vuz/1189/programs/bakispec/2272"));
                        addback(getContent("https://vuzopedia.ru/vuz/1567/programs/bakispec/1666"));
                        Thread.sleep(1000);
                        addback(getContent("https://vuzopedia.ru/vuz/342/programs/bakispec/108"));
                        addback(getContent("https://vuzopedia.ru/vuz/342/programs/bakispec/2277"));
                        Thread.sleep(1000);
                        addback(getContent("https://vuzopedia.ru/vuz/1189/programs/bakispec/46"));
                        addback(getContent("https://vuzopedia.ru/vuz/725/programs/bakispec/1546"));
                        Thread.sleep(1000);
                        addback(getContent("https://vuzopedia.ru/vuz/342/programs/bakispec/81"));
                        addback(getContent("https://vuzopedia.ru/vuz/1751/programs/bakispec/6122"));


                        //Thread.sleep(500);


                    } catch (IOException ex) {
                        content = null;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }



    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    public void setProgressValue(int progress){
        pb.setProgress(progress);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(pb.getProgress()>=300)
                {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                            startActivity(intent);
                            finish();
                        }
                    }, time_streams);
                }
                else {
                    setProgressValue(progress + step);
                }

                if(progress==250)
                {
                    TextView textView = findViewById(R.id.small_text);
                    textView.setText(R.string.small_text2);
                }
            }
        });

        thread.start();
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
        String findcity = getString(R.string.findcity);
        String findcityEnd = getString(R.string.findcityEnd);
        String findpointf = getString(R.string.findpointf);
        String findpointp1 = getString(R.string.findpointf1);
        String findUniversityCalled = getString(R.string.findUniversityCalled);
        String findsubject1 = getString(R.string.findsubject1);
        String findsubject2 = getString(R.string.findsubject2);
        String findsubject3 = getString(R.string.findsubject3);
        String findprice = getString(R.string.findprice);
        String in= getString(R.string.in);

        String findsubjectEnd = getString(R.string.findsubjectEnd);
        String cut = "";

        BufferedReader reader=null;
        InputStream stream = null;
        HttpsURLConnection connection = null;
        try {
            URL url=new URL(path);
            connection =(HttpsURLConnection)url.openConnection();
            connection = (HttpsURLConnection) url.openConnection();
//            connection.setRequestProperty("User-Agent", "'User-Agent': Mozilla/5.0 (Android 14; Mobile; rv:68.0) Gecko/68.0 Firefox/126.0");
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
                paid=getString(R.string.paid);
            }
            else{
                paid=getString(R.string.paidandfree);
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