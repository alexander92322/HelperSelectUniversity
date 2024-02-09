package com.example.touniversity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SplashSelection extends AppCompatActivity {

    public static int openSelection=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_selection);
        final TextView emerging_text = findViewById( R.id.emerging_text );
        final int delay_ms = 150;
        emerging_text.setTag("Мы подбираем ВУЗы для вас! Желаем вам выбрать именно тот, о котором вы всегда мечтали!");
        emerging_text.postDelayed( new Runnable(){
            @Override
            public void run(){
                String text = emerging_text.getText().toString();
                String text_tag = (String)emerging_text.getTag();
                if( text.length() < text_tag.length() ){
                    emerging_text.setText( text+ text_tag.substring( text.length(), text.length()+1 ) );
                    emerging_text.postDelayed( this, delay_ms );
                }
                if(emerging_text.getText().toString().equals("Мы подбираем ВУЗы для вас! Желаем вам выбрать именно тот, о котором вы всегда мечтали!"))
                {
                    stopq();
                    openSelection++;
                }
            }
        }, delay_ms );

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        openSelection=0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        openSelection=0;
    }

    protected void stopq() {
        if(openSelection==0){
            Intent intent = new Intent(SplashSelection.this, Selection.class);
            startActivity(intent);}

    }


}