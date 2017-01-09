package com.test.collapsingtoolbar;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private static final String TAG = "MainActivity";
    public List<CollapseListener> mCollapseListeners = new ArrayList<>();

    public void addCollapseListener(CollapseListener collapseListener) {
        mCollapseListeners.add(collapseListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (appBarLayout.getTotalScrollRange() - Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    //Expanded
                    Log.d(TAG, "Expanded");
                    for (CollapseListener collapseListener : mCollapseListeners) {
                        collapseListener.onExpanded();
                    }
                } else {
                    //  Collapsed
                    Log.d(TAG, "Collapsed");
                    for (CollapseListener collapseListener : mCollapseListeners) {
                        collapseListener.onCollapsed();
                    }
                }
            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new MyFragment(), "Category 1");
        viewPagerAdapter.addFragment(new MyFragment(), "Category 2");
        viewPagerAdapter.addFragment(new MyFragment(), "Category 3");
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
    }


}
