package com.maratha.hema.marathabussinessapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maratha.hema.marathabussinessapp.Approval.OperatorDetailsFragment;
import com.maratha.hema.marathabussinessapp.Approval.PhotoDetailsFragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EditDetailsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);


        tabLayout = (TabLayout)findViewById(R.id.tabupdatedetails);
        viewPager = (ViewPager)findViewById(R.id.viewpagerupdate);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new UpdateDetailFragment(),"Update Details");
        adapter.AddFragment(new UpdateImageFragment(),"Update Images");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

}
