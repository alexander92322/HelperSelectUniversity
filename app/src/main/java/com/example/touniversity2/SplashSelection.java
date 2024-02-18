package com.example.touniversity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
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
        final int delay_ms = 100;
        Button button;
        button = findViewById(R.id.bt_next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emerging_text.setText("Мы подбираем лучшие ВУЗы для вас. Надеемся, вам понравится!");
            }
        });

        emerging_text.setTag("Мы подбираем лучшие ВУЗы для вас. Надеемся, вам понравится!");
        emerging_text.postDelayed( new Runnable(){
            @Override
            public void run(){
                String text = emerging_text.getText().toString();
                String text_tag = (String)emerging_text.getTag();
                if( text.length() < text_tag.length() ){
                    emerging_text.setText( text+ text_tag.substring( text.length(), text.length()+1 ) );
                    emerging_text.postDelayed( this, delay_ms );
                }
                if(emerging_text.getText().toString().equals("Мы подбираем лучшие ВУЗы для вас. Надеемся, вам понравится!"))
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