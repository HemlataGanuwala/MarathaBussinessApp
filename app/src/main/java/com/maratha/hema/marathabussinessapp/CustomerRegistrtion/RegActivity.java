package com.maratha.hema.marathabussinessapp.CustomerRegistrtion;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.maratha.hema.marathabussinessapp.R;
import com.maratha.hema.marathabussinessapp.ViewPagerAdapter;

public class RegActivity extends AppCompatActivity {
private TabLayout tabLayout;
private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        tabLayout = (TabLayout)findViewById(R.id.tabs);
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new RegistrationFragment(),"Registration");
        adapter.AddFragment(new AccountFragment(),"AccountDetail");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
