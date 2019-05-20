
package com.example.aman.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;


public class Tab extends FragmentActivity {
    private static final String TAG = "Tag";

    private GridView tabgrid;


    public SectionsPagerAdapter mSectionsPagerAdapter;
    Context context;
    ArrayList<Fragment> mFragmentList ;
    ArrayList<String> mFragmentTitleList;



    ViewPager mViewPager;
    FloatingActionButton button;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        mFragmentList = new ArrayList<Fragment>();
         mFragmentTitleList = new ArrayList<String>();



        mFragmentTitleList.add("Tab");
        mFragmentTitleList.add("Tab");
        mFragmentTitleList.add("Tab");


        createFrags();

        setContentView(R.layout.activity_tab);

        button = (FloatingActionButton) findViewById(R.id.fab);
        button.setImageResource(R.drawable.ic_content_copy_black_24dp);


        mSectionsPagerAdapter = new
                SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutTransformer());
        mViewPager.setSaveFromParentEnabled(true);
        mViewPager.setOffscreenPageLimit(10);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager, true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // tabLayout.setVisibility(View.VISIBLE);
//tabLayout.addTab(tabLayout.newTab(),6,true);
//mSectionsPagerAdapter.
//Fragment fragment = new DummySectionFragment();
                //              frags.add(fragment);
mSectionsPagerAdapter.addFragment(new DummySectionFragment(),"Tab",mFragmentTitleList.size());
mSectionsPagerAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onBackPressed() {
        tabLayout.setVisibility(View.INVISIBLE);


    }


    public void createFrags() {
        for (int i = 0; i < mFragmentTitleList.size(); i++) {


            Fragment fragment = new DummySectionFragment();
            mFragmentList.add(fragment);

        }
    }




    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        private TextProvider mProvider;
        private long baseId = 0;


        // ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
         //ArrayList<String> mFragmentTitleList = new ArrayList<String>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title, int position) {
            mFragmentList.add(position, fragment);
            mFragmentTitleList.add(position, title);
        }

        public void removeFragment(Fragment fragment, int position) {
            mFragmentList.remove(position);
            mFragmentTitleList.remove(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}