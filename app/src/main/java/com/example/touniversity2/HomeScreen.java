package com.example.touniversity2;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

            addToDB("tyvuzop");
            //fill_database();
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
   // public void fill_database() {

//        DbHelper dbHelper= new DbHelper(this);
//        dbHelper.DeleteDb();
//        UniversityData MGU1 = new UniversityData("МГУ", "Прикладная математика и информатика", 405, 300, " Информатика, Математикка(профиль), Физика", 0, "Москва", 391050, "bg.png","https://cs.msu.ru/", "https://vuzopedia.ru/vuz/1/napr/98", 4, "Математика");
//        UniversityData MGU2 = new UniversityData("МГУ", "Прикладнае математика и физика", 275, 126, " Информатика, Математикка(профиль), Физика", 0, "Москва", 435970, "bg.png","https://msu.ru/calc/program/58757/index.php?tmpl=clear&PROGRAM_ID=58757?tmpl=clear&PROGRAM_ID=58757", "https://vuzopedia.ru/vuz/1/napr/93", 4, "Физика");
//        UniversityData MGU3 = new UniversityData("МГУ", "Астрономия", 346, 135, "Математика(профиль), Физика", 0, "Москва", 435970, "bg.png","https://www.phys.msu.ru/rus/about/structure/div/div-astronomy/chair-astrophysics/", "https://vuzopedia.ru/vuz/1/napr/176", 3, "Физика");
//        UniversityData MGU4 = new UniversityData("МГУ", "Медиакоммуникации", 373, 140, "История, Литература, Математика(база)", 0, "Москва", 391050, "bg.png","https://www.journ.msu.ru/education/bachelor/mediakommunikatsii/index.php", "https://vuzopedia.ru/vuz/1/napr/468", 3, "Творческое испытание");
//        UniversityData MGU5 = new UniversityData("МГУ", "Медиакоммуникации", 373, 140, "Литература, Математика(база), Обществознание", 0, "Москва", 391050, "bg.png","https://www.journ.msu.ru/education/bachelor/mediakommunikatsii/index.php", "https://vuzopedia.ru/vuz/1/napr/468", 3, "Творческое испытание");
//        UniversityData MGTU1 = new UniversityData("МГТУ ", "Бизнес-информатика", 278, 241, "Математика(профиль), Обществознание", 0, "Москва",324143 , "bg.png","https://bmstu.ru/bachelor/majors/biznes-informatika-380305", "https://vuzopedia.ru/vuz/4/napr/11", 2, "Нет");
//        UniversityData MGTU2 = new UniversityData("МГТУ", "Бизнес-информатика", 278, 241, "Информатика, Математика(профиль)", 0, "Москва", 324143, "bg.png","https://bmstu.ru/bachelor/majors/biznes-informatika-380305", "https://vuzopedia.ru/vuz/4/napr/11", 2, "Нет");
//        UniversityData MGTU3 = new UniversityData("МГТУ", "Дизайн", 295, 280, "Математикка(база), Обществознание", 0, "Москва", 584823, "bg.png","https://bmstu.ru/bachelor/majors/dizajn-540301", "https://vuzopedia.ru/vuz/4/napr/28", 2, "Творческое испытание");
//        UniversityData MGTU4 = new UniversityData("МГТУ", "Дизайн", 295, 280, "История, Математикка(база)", 0, "Москва", 584823, "bg.png","https://bmstu.ru/bachelor/majors/dizajn-540301", "https://vuzopedia.ru/vuz/4/napr/28", 2, "Творческое испытание");
//        UniversityData MGTU5 = new UniversityData("МГТУ", "Компьютерная безопасность", 247, 187, "Математикка(профиль), Физика", 0, "Москва", 329761, "bg.png","https://bmstu.ru/bachelor/majors/komputernaa-bezopasnost-100501", "https://vuzopedia.ru/vuz/4/napr/200", 3, "Нет");
//        UniversityData MGTU6 = new UniversityData("МГТУ", "Компьютерная безопасность", 247, 187, "Информатика, Математикка(профиль)", 0, "Москва", 329761, "bg.png","https://bmstu.ru/bachelor/majors/komputernaa-bezopasnost-100501", "https://vuzopedia.ru/vuz/4/napr/200", 3, "Нет");
//        UniversityData MGTU7 = new UniversityData("МГТУ", "Лингвистика", 0, 167, "Иностранный язык, Математикка(база), Обществознание", 0, "Москва", 294430, "bg.png","https://bmstu.ru/faculty/lingvistika", "https://vuzopedia.ru/vuz/4/napr/61", 4, "Нет");
//        UniversityData MGTU8 = new UniversityData("МГТУ", "Лингвистика", 0, 167, "Иностранный язык, Информатика, Математикка(база)", 0, "Москва", 294430, "bg.png","https://bmstu.ru/faculty/lingvistika", "https://vuzopedia.ru/vuz/4/napr/61", 4, "Нет");
//        UniversityData MGTU9 = new UniversityData("МГТУ ", "Биотехнические системы и технологии", 225, 178, "Биология, Математика(профиль)", 0, "Москва",329761 , "bg.png","https://bmstu.ru/bachelor/majors/biotehniceskie-sistemy-i-tehnologii-120304", "https://vuzopedia.ru/vuz/4/napr/14", 3, "Нет");
//        UniversityData MGTU10 = new UniversityData("МГТУ ", "Биотехнические системы и технологии", 225, 178, "Математика(профиль), Физика", 0, "Москва",329761 , "bg.png","https://bmstu.ru/bachelor/majors/biotehniceskie-sistemy-i-tehnologii-120304", "https://vuzopedia.ru/vuz/4/napr/14", 3, "Нет");
//        UniversityData MIFI1 = new UniversityData("МИФИ", "Автоматизация технологических процессов и производств", 245, 166, "Математикка(профиль), Физика", 0, "Москва", 287000, "bg.png","https://admission2022.mephi.ru/admission/baccalaureate-and-specialty/education/programs/all/view/201", "https://vuzopedia.ru/vuz/725/napr/2", 3, "Нет");
//        UniversityData MIFI2 = new UniversityData("МИФИ", "Автоматизация технологических процессов и производств", 245, 166, "Информатика, Математикка(профиль)", 0, "Москва", 287000, "bg.png","https://admission2022.mephi.ru/admission/baccalaureate-and-specialty/education/programs/all/view/201", "https://vuzopedia.ru/vuz/725/napr/2", 3, "Нет");
//        UniversityData MIFI3 = new UniversityData("МИФИ", "Биология", 0, 119, "Биология, Математикка(профиль)", 0, "Москва", 155126, "bg.png","https://mephi.ru/node/16791", "https://vuzopedia.ru/vuz/725/napr/13", 3, "Нет");
//        UniversityData MIFI4 = new UniversityData("МИФИ", "Высокотехнологические плазменные и энергетические установки", 236, 166, "Математикка(база), Физика", 0, "Москва", 330000, "bg.png","https://admission.mephi.ru/admission/baccalaureate-and-specialty/education/programs/all/view/209", "https://vuzopedia.ru/vuz/725/napr/18", 3, "Нет");
//        UniversityData NGU1 = new UniversityData("НГУ", "Бизнес-информатика", 264, 192, "Математикка(профиль), Обществознание", 0, "Новосибирск", 195000, "bg.png","https://www.nsu.ru/n/economics-department/programs/business-informatics/", "https://vuzopedia.ru/vuz/1612/napr/11", 3, "Нет");
//        UniversityData NGU2 = new UniversityData("НГУ", "Бизнес-информатика", 264, 192, "География, Математикка(профиль)", 0, "Новосибирск", 195000, "bg.png","https://www.nsu.ru/n/economics-department/programs/business-informatics/", "https://vuzopedia.ru/vuz/1612/napr/11", 3, "Нет");
//        UniversityData NGU3 = new UniversityData("НГУ", "Бизнес-информатика", 264, 192, "История, Математикка(профиль)", 0, "Новосибирск", 195000, "bg.png","https://www.nsu.ru/n/economics-department/programs/business-informatics/", "https://vuzopedia.ru/vuz/1612/napr/11", 3, "Нет");
//        UniversityData NGU4 = new UniversityData("НГУ", "Бизнес-информатика", 264, 192, "Информатика, Математикка(профиль)", 0, "Новосибирск", 195000, "bg.png","https://www.nsu.ru/n/economics-department/programs/business-informatics/", "https://vuzopedia.ru/vuz/1612/napr/11", 3, "Нет");
//        UniversityData NGU5 = new UniversityData("НГУ", "Бизнес-информатика", 264, 192, "Иностранный язык, Математикка(профиль)", 0, "Новосибирск", 195000, "bg.png","https://www.nsu.ru/n/economics-department/programs/business-informatics/", "https://vuzopedia.ru/vuz/1612/napr/11", 3, "Нет");
//        UniversityData NGU6 = new UniversityData("НГУ", "Биология", 226, 200, "Математикка(профиль), Физика", 0, "Новосибирск", 200000, "bg.png","https://www.nsu.ru/n/research/divisions/biology-medicine/", "https://vuzopedia.ru/vuz/1612/napr/13", 3, "Нет");
//        UniversityData NGU7 = new UniversityData("НГУ", "Биология", 226, 200, "Биология, Математикка(профиль)", 0, "Новосибирск", 200000, "bg.png","https://www.nsu.ru/n/research/divisions/biology-medicine/", "https://vuzopedia.ru/vuz/1612/napr/13", 3, "Нет");
//        UniversityData NGU8 = new UniversityData("НГУ", "Журналистика", 247, 218, "Математикка(база), Литература", 0, "Новосибирск", 184000, "bg.png","https://www.nsu.ru/n/humanities-institute/programs/journ/", "https://vuzopedia.ru/vuz/1612/napr/33", 3, "Творческое испытание");
//        UniversityData VSHE1 = new UniversityData("ВШЭ", "География", 242, 180, "География, Математикка(профиль)", 0, "Москва", 450000, "bg.png","https://geography.hse.ru/", "https://vuzopedia.ru/vuz/342/napr/19", 3, "Нет");
//        UniversityData VSHE2 = new UniversityData("ВШЭ", "География", 242, 180, "География, Информатика, Математикка(база)", 0, "Москва", 450000, "bg.png","https://geography.hse.ru/", "https://vuzopedia.ru/vuz/342/napr/19", 4, "Нет");
//        UniversityData VSHE3 = new UniversityData("ВШЭ", "Бизнес-информатика", 287, 210, "Информатика, Математикка(профиль)", 0, "Москва", 700000, "bg.png","https://www.hse.ru/ba/bi/", "https://vuzopedia.ru/vuz/342/programs/bakispec/68", 3, "Нет");
//        UniversityData VSHE4 = new UniversityData("ВШЭ", "Государственное и муниципальное управление", 295, 280, "Иностранный язык, Обществознание, Математикка(профиль)", 0, "Москва", 480000, "bg.png","https://www.hse.ru/ba/gmu/", "https://vuzopedia.ru/vuz/342/napr/22", 4, "Нет");
//        UniversityData MFTI1 = new UniversityData("МФТИ", "Системный анализ и управление", 290, 240, "Математикка(профиль), Физика", 0, "Москва", 504000, "bg.png","https://fakt.mipt.ru/master/sys", "https://vuzopedia.ru/vuz/591/napr/120", 3, "Нет");
//        UniversityData MFTI2 = new UniversityData("МФТИ", "Системный анализ и управление", 290, 240, "Информатика, Математикка(профиль)", 0, "Москва", 504000, "bg.png","https://fakt.mipt.ru/master/sys", "https://vuzopedia.ru/vuz/591/napr/120", 3, "Нет");
//        UniversityData MFTI3 = new UniversityData("МФТИ", "Техническая физика", 296, 184, "Математикка(профиль), Физика", 0, "Москва", 504000, "bg.png","https://pk.mipt.ru/bachelor/2019_programs/techphys.php", "https://vuzopedia.ru/vuz/591/napr/138", 3, "Нет");
//        UniversityData MFTI4 = new UniversityData("МФТИ", "Электроника и наноэлектроника", 267, 240, "Математикка(профиль), Физика", 0, "Москва", 432000, "bg.png","https://pk.mipt.ru/bachelor/2023_programs/fptmne.php", "https://vuzopedia.ru/vuz/591/napr/166", 3, "Нет");
//
//        UniversityData SPBGU1 = new UniversityData("СПБГУ", "Менеджмент", 0, 181, "математикка(база), информатика", 0, "Санкт-Петербург", 530200, "bg.png","https://spbu.ru/postupayushchim/programms/bakalavriat/menedzhment", "https://vuzopedia.ru/vuz/1239/napr/69",3,"Нет");
//        UniversityData SPBGU2 = new UniversityData("СПБГУ", "Политология", 275, 161, "математикка(база), информатика", 0, "Санкт-Петербург", 253100, "bg.png","https://spbu.ru/postupayushchim/programms/bakalavriat/politologiya", "https://vuzopedia.ru/vuz/1239/napr/96",3,"Нет");
//        UniversityData SPBGU3 = new UniversityData("СПБГУ", "Управление персоналом", 250, 155, "математикка(база), информатика", 0, "Санкт-Петербург", 321200, "bg.png","https://spbu.ru/postupayushchim/programms/bakalavriat/upravlenie-personalom", "https://vuzopedia.ru/vuz/1239/napr/148",3,"Нет");
//        UniversityData TGU1 = new UniversityData("ТГУ", "Баллистика и гидроаэродинамика", 150, 118, "математикка(база), информатика", 0, "Томск", 214000, "bg.png","https://abiturient.tsu.ru/ru/content/%D0%B1%D0%B0%D0%BB%D0%BB%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0-%D0%B8-%D0%B3%D0%B8%D0%B4%D1%80%D0%BE%D0%B0%D1%8D%D1%80%D0%BE%D0%B4%D0%B8%D0%BD%D0%B0%D0%BC%D0%B8%D0%BA%D0%B0", "https://vuzopedia.ru/vuz/3463/napr/10",3,"Нет");
//        UniversityData TGU2 = new UniversityData("ТГУ", "Инноватика", 163, 118, "математикка(база), информатика", 1, "Томск", 56000, "bg.png","http://www.fit.tsu.ru/ru/conf/inno/2022", "https://vuzopedia.ru/vuz/3463/napr/42",3,"Нет");
//        UniversityData TGU3 = new UniversityData("ТГУ", "Информационные системы и технологии", 203, 118, "математикка(база), информатика", 0, "Томск", 181000, "bg.png","https://ff.tsu.ru/node/1885", "https://vuzopedia.ru/vuz/3463/napr/48",3,"Нет");
//        UniversityData KFU1 = new UniversityData("КФУ", "Геодезия и дистанционное зондирование", 174, 118, "математикка(база), информатика", 0, "Казань", 194580, "bg.png","https://kpfu.ru/physics/abiturientu/programmy-obucheniya/geodeziya-i-distancionnoe-zondirovanie-profil", "https://vuzopedia.ru/vuz/1751/napr/20",3,"Нет");
//        UniversityData KFU2 = new UniversityData("КФУ", "Механика и математическое моделирование", 190, 118, "математикка(база), информатика", 0, "Казань", 139620, "bg.png","https://kpfu.ru/math/admissions/obrazovatelnye-programmy/010303-mehanika-i-matematicheskoe-modelirovanie", "https://vuzopedia.ru/vuz/1751/napr/74",3,"Нет");
//        UniversityData KFU3 = new UniversityData("КФУ", "Информационная безопасность", 166, 109, "математикка(база), информатика", 0, "Казань", 171360, "bg.png","https://kpfu.ru/computing-technology/abiturientam/obrazovatelnye-programmy/informacionnaya-bezopasnost-100301", "https://vuzopedia.ru/vuz/1751/napr/39",3,"Нет");
//        UniversityData ITMO1 = new UniversityData("ИТМО", "Автоматизация технологических процессов и производств", 253, 180, "математикка(база), информатика", 1, "Санкт-Петербург", 289000, "bg.png","https://edu.itmo.ru/ru/napravleniya/4885", "https://vuzopedia.ru/vuz/1189/napr/2",3,"Нет");
//        UniversityData ITMO2 = new UniversityData("ИТМО", "Биотехнология", 261, 180, "математикка(база), информатика", 0, "Санкт-Петербург", 289000, "bg.png","https://abit.itmo.ru/program/bachelor/biotechnology", "https://vuzopedia.ru/vuz/1189/napr/12",3,"Нет");
//        UniversityData ITMO3 = new UniversityData("ИТМО", "Информатика и вычислительная техника", 288, 180, "математикка(база), информатика", 0, "Санкт-Петербург", 349000, "bg.png","https://edu.itmo.ru/ru/napravleniya/4745", "https://vuzopedia.ru/vuz/1189/napr/43",3,"Нет");
//
//        UniversityData URFU1 = new UniversityData("УРФУ", "Археология и этнология", 244, 105, "математикка(база), информатика", 0, "Екатеринбург", 150000, "bg.png","https://spbu.ru/postupayushchim/programms/bakalavriat/menedzhment", "https://vuzopedia.ru/vuz/1239/napr/69",3,"Нет");
//        UniversityData URFU2 = new UniversityData("УРФУ", "Астрономия", 148, 118, "математикка(база), информатика", 0, "Екатеринбург", 166000, "bg.png","https://spbu.ru/postupayushchim/programms/bakalavriat/menedzhment", "https://vuzopedia.ru/vuz/1239/napr/69",3,"Нет");
//        UniversityData URFU3 = new UniversityData("УРФУ", "Медицинская биофизика", 181, 118, "математикка(база), информатика", 0, "Екатеринбург", 202000, "bg.png","https://spbu.ru/postupayushchim/programms/bakalavriat/menedzhment", "https://vuzopedia.ru/vuz/1239/napr/69",3,"Нет");
//        UniversityData MGIMO1 = new UniversityData("МГИМО", "Реклама и связи с общественностью", 371, 307, "математикка(база), информатика", 0, "Москва", 598000, "bg.png","https://spbu.ru/postupayushchim/programms/bakalavriat/menedzhment", "https://vuzopedia.ru/vuz/1239/napr/69",3,"Нет");
//        UniversityData MGIMO2 = new UniversityData("МГИМО", "Торговое дело", 375, 255, "математикка(база), информатика", 0, "Москва", 563000, "bg.png","https://spbu.ru/postupayushchim/programms/bakalavriat/menedzhment", "https://vuzopedia.ru/vuz/1239/napr/69",3,"Нет");
//        UniversityData MGIMO3 = new UniversityData("МГИМО", "Экономика", 372, 313, "математикка(база), информатика", 0, "Москва", 611000, "bg.png","https://spbu.ru/postupayushchim/programms/bakalavriat/menedzhment", "https://vuzopedia.ru/vuz/1239/napr/69",3,"Нет");
//        UniversityData MGMU1 = new UniversityData("МГМУ", "Биоинженерия и биоинформатика", 270, 177, "математикка(база), информатика", 0, "Москва", 400000, "bg.png","https://spbu.ru/postupayushchim/programms/bakalavriat/menedzhment", "https://vuzopedia.ru/vuz/1239/napr/69",3,"Нет");
//        UniversityData MGMU2 = new UniversityData("МГМУ", "Интеллектуальные системы в гуманитарной сфере", 225, 156, "математикка(база), информатика", 0, "Москва", 277000, "bg.png","https://spbu.ru/postupayushchim/programms/bakalavriat/menedzhment", "https://vuzopedia.ru/vuz/1239/napr/69",3,"Нет");
//        UniversityData MGMU3 = new UniversityData("МГМУ", "Клиническая психология", 259, 186, "математикка(база), информатика", 0, "Москва", 310000, "bg.png","https://spbu.ru/postupayushchim/programms/bakalavriat/menedzhment", "https://vuzopedia.ru/vuz/1239/napr/69",3,"Нет");
//        UniversityData UFU1 = new UniversityData("ЮФУ", "Самолето- и вертолетостроение", 156, 156, "математикка(база), информатика", 0, "Ростов-на-Дону", 187000, "bg.png","https://spbu.ru/postupayushchim/programms/bakalavriat/menedzhment", "https://vuzopedia.ru/vuz/1239/napr/69",3,"Нет");
//        UniversityData UFU2 = new UniversityData("ЮФУ", "Системный анализ и управление", 165, 165, "математикка(база), информатика", 0, "Ростов-на-Дону", 200000, "bg.png","https://spbu.ru/postupayushchim/programms/bakalavriat/menedzhment", "https://vuzopedia.ru/vuz/1239/napr/69",3,"Нет");
//        UniversityData UFU3 = new UniversityData("ЮФУ", "Спорт", 170, 160, "математикка(база), информатика", 0, "Ростов-на-Дону", 187000, "bg.png","https://spbu.ru/postupayushchim/programms/bakalavriat/menedzhment", "https://vuzopedia.ru/vuz/1239/napr/69",3,"Нет");
//
//
//
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
//        dbHelper.AddData(MGU5);
//        dbHelper.AddData(MFTI1);
//        dbHelper.AddData(MFTI2);
//        dbHelper.AddData(MFTI3);
//        dbHelper.AddData(MFTI4);
//        dbHelper.AddData(MGTU1);
//        dbHelper.AddData(MGTU2);
//        dbHelper.AddData(MGTU3);
//        dbHelper.AddData(MGTU4);
//        dbHelper.AddData(MGTU5);
//        dbHelper.AddData(MGTU6);
//        dbHelper.AddData(MGTU7);
//        dbHelper.AddData(MGTU8);
//        dbHelper.AddData(MGTU9);
//        dbHelper.AddData(MGTU10);
//        dbHelper.AddData(VSHE1);
//        dbHelper.AddData(VSHE2);
//        dbHelper.AddData(VSHE3);
//        dbHelper.AddData(VSHE4);
//        dbHelper.AddData(MIFI1);
//        dbHelper.AddData(MIFI2);
//        dbHelper.AddData(MIFI3);
//        dbHelper.AddData(MIFI4);
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
//        dbHelper.AddData(NGU4);
//        dbHelper.AddData(NGU5);
//        dbHelper.AddData(NGU6);
//        dbHelper.AddData(NGU7);
//        dbHelper.AddData(NGU8);
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
//    }


        public void addToDB(String datahtml) {
            DbHelper dbHelper= new DbHelper(this);

            UniversityData universityData = new UniversityData(datahtml, "Бизнес-информатика", 264, 192, "Иностранный язык, Математика(профиль)", 0, "Новосибирск", 195000, "bg.png","https://www.nsu.ru/n/economics-department/programs/business-informatics/", "https://vuzopedia.ru/vuz/1612/napr/11", 3, "Нет");
            dbHelper.AddData(universityData);

        }



      public void fill_database() {
          //Toast.makeText(this, "qwe", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    addToDB(getContent("https://vuzopedia.ru/vuz/1239/napr/96"));
                }
                catch (IOException ex){
                    content="?";
                }
            }
        }).start();


      }

}