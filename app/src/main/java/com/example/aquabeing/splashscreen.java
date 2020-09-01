package com.example.aquabeing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Thread thread = new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(1500);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(splashscreen.this , First.class);
                    startActivity(intent);

                }
            }
        };thread.start();


    }
}