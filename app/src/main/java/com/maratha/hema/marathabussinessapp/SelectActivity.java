package com.maratha.hema.marathabussinessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.maratha.hema.marathabussinessapp.Approval.ApprovalActivity;

public class SelectActivity extends AppCompatActivity {

    Button buttonapproval, buttonedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        buttonapproval=(Button)findViewById(R.id.btnapprove);
        buttonedit=(Button)findViewById(R.id.btnedit);

        buttonapproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelectActivity.this, ApprovalActivity.class);
                startActivity(intent);
            }
        });

        buttonedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SelectActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
    }
}
