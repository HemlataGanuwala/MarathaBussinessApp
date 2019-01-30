package com.maratha.hema.marathabussinessapp.TypeSelect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.maratha.hema.marathabussinessapp.CustomerRegistrtion.AccountDetailsActivity;
import com.maratha.hema.marathabussinessapp.R;
import com.maratha.hema.marathabussinessapp.CustomerRegistrtion.RegActivity;

public class InfoActivity extends AppCompatActivity {

    Button buttonnext, buttondetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        buttonnext = (Button)findViewById(R.id.btninfonext);
        buttondetails = (Button)findViewById(R.id.btndetails);

        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, RegActivity.class);
                startActivity(intent);
            }
        });

        buttondetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InfoActivity.this, AccountDetailsActivity.class);
                startActivity(i);
            }
        });
    }
}
