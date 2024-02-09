package com.example.touniversity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
int progress = 0;
ProgressBar pb;

    static final int time_streams = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = findViewById(R.id.progressBar);
        pb.setProgress(0);
        pb.setMax(95);
        setProgressValue(progress);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                startActivity(intent);
                finish();
            }
        }, time_streams);

    }
    public void setProgressValue(int progress){
        pb.setProgress(progress);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(pb.getProgress()>=100)
                {

                }
                else {
                    setProgressValue(progress + 1);
                }

                if(progress==60)
                {
                    TextView textView = findViewById(R.id.small_text);
                    textView.setText(R.string.small_text2);
                }
            }
        });

        thread.start();
    }




}