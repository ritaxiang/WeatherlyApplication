package com.example.weatherapptutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

//class for showing the start-up page
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //timer used to make the splash screen stay for a set period of time
        Thread timer = new Thread() {
            public void run(){
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally{
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);//go to home page
                }

            }
        };timer.start();
    }
}