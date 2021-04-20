package com.arife.adnbikerider.mvp.v;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.arife.adnbikerider.AppData.Adapters.ViewPagerAdapter;
import com.arife.adnbikerider.AppData.Fragments.FragmentGroups;
import com.arife.adnbikerider.AppData.Fragments.FragmentMyRoutes;
import com.arife.adnbikerider.AppData.Fragments.FragmentPost;
import com.arife.adnbikerider.AppData.Fragments.PendingMove;
import com.arife.adnbikerider.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements PendingMove {
    private TabLayout tabLayoutHome;
    private ViewPager viewPagerHome;
    private ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.tabLayoutHome = findViewById(R.id.tab_layout_home);
        this.viewPagerHome = findViewById(R.id.view_pager_home);
        this.pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        Toolbar toolbar = (Toolbar) findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Mi nombre");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.pagerAdapter.AddFragment(new FragmentGroups(this),"");
        this.pagerAdapter.AddFragment(new FragmentMyRoutes(),"");
        this.pagerAdapter.AddFragment(new FragmentPost(),"");

        this.viewPagerHome.setAdapter(this.pagerAdapter);
        this.tabLayoutHome.setupWithViewPager(this.viewPagerHome);

        this.tabLayoutHome.getTabAt(0).setIcon(R.drawable.ic_network);
        this.tabLayoutHome.getTabAt(1).setIcon(R.drawable.ic_placeholder);
        this.tabLayoutHome.getTabAt(2).setIcon(R.drawable.ic_social_media);
    }

    @Override
    public void PendingMoveAction() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }
}
