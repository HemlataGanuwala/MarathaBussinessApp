package com.maratha.hema.marathabussinessapp.Approval;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.maratha.hema.marathabussinessapp.R;
import com.maratha.hema.marathabussinessapp.ViewPagerAdapter;

public class ApprovalDetailsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_details);

        tabLayout = (TabLayout)findViewById(R.id.tabsdetailoperator);
        viewPager = (ViewPager)findViewById(R.id.viewpagerdetailoperator);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new OperatorDetailsFragment(),"Details");
        adapter.AddFragment(new PhotoDetailsFragment(),"Images");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }


}
