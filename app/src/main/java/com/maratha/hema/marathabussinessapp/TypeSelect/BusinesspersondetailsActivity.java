package com.maratha.hema.marathabussinessapp.TypeSelect;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.maratha.hema.marathabussinessapp.CustomerRegistrtion.AccountFragment;
import com.maratha.hema.marathabussinessapp.R;
import com.maratha.hema.marathabussinessapp.ViewPagerAdapter;


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
        adapter.AddFragment(new ProductsFragment(),"Products");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
