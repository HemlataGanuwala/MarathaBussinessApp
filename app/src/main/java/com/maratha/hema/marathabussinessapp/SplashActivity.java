package com.maratha.hema.marathabussinessapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
//       globalVariable.setconstr("http://192.168.0.102:8056/api/");
        globalVariable.setconstr("http://192.168.0.117:8014/api/");
//        globalVariable.setconstr("http://marathabusiness.skyvisioncables.com/api/");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
