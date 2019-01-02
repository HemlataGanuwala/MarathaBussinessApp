package com.maratha.hema.marathabussinessapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BusinesspersondetailsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businesspersondetails);

        tabLayout = (TabLayout)findViewById(R.id.tabsdetail);
        viewPager = (ViewPager)findViewById(R.id.viewpagerdetail);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new BusiPersonDetailFragment(),"Details");
        adapter.AddFragment(new photouploadFragment(),"Images");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
