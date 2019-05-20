
package com.example.aman.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity   implements Search.OnHeadlineSelectedListener,Search.OnTabSelectedListener{
    private static final String TAG = "Tag";

    private GridView tabgrid;

public static int  tabposition;
    public SectionsPagerAdapter mSectionsPagerAdapter;
    Context context;
    ArrayList<Fragment> mFragmentList ;
    ArrayList<String> mFragmentTitleList;


    ViewPager mViewPager;
    FloatingActionButton button;
    FloatingActionButton button2;
    TabLayout tabLayout;
     ImageButton addt;
public  TabLayout.Tab atab;
    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof Search) {
            Search headlinesFragment = (Search) fragment;
            headlinesFragment.setOnHeadlineSelectedListener(this);
        }
        if (fragment instanceof Search) {
            Search tabFragment = (Search) fragment;
            tabFragment.setOnTabSelectedListener(this);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        mFragmentList = new ArrayList<Fragment>();
        mFragmentTitleList = new ArrayList<String>();







        mFragmentTitleList.add("Home");
       mFragmentTitleList.add("Home");
        //mFragmentTitleList.add("Home");


        createFrags();

        setContentView(R.layout.activity_main);

        button = (FloatingActionButton) findViewById(R.id.fab);
        button.setImageResource(R.drawable.ic_content_copy_black_24dp);

       // button2 = (FloatingActionButton) findViewById(R.id.fbtn);
        //button2.setImageResource(R.drawable.ic_content_copy_black_24dp);

        mSectionsPagerAdapter = new
                SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutTransformer());
        mViewPager.setSaveFromParentEnabled(true);
        mViewPager.setOffscreenPageLimit(10);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager, true);

       tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
          tabposition=tab.getPosition();
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {
               tabposition=tab.getPosition();

           }
       });
       addt=(ImageButton)findViewById(R.id.addt);
       addt.setVisibility(View.INVISIBLE);
       tabLayout.setVisibility(View.INVISIBLE);
       addt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mSectionsPagerAdapter.addFragment(new DummySectionFragment(),"Home",mFragmentTitleList.size());
             mSectionsPagerAdapter.notifyDataSetChanged();

           }
       });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 mViewPager.removeViewAt(tabposition);

                //mSectionsPagerAdapter.removeFragment(tabposition);

                //mSectionsPagerAdapter.notifyDataSetChanged();
tabLayout.setVisibility(View.VISIBLE);
                    addt.setVisibility(View.VISIBLE);

            }
        });



    }
    public void onArticleSelected(String url) {
        Bundle b = new Bundle();
        b.putString("Key", url);
        Fragment fragmen = new Webv();
        fragmen.setArguments(b);

        mSectionsPagerAdapter.addFragment(fragmen,url,mFragmentTitleList.size());
        mSectionsPagerAdapter.notifyDataSetChanged();



    }
    public void onTabSelected(String tabtitle){
        TabLayout.Tab tab = tabLayout.getTabAt(tabposition);
        tab.setText(tabtitle+"\n"+tabtitle);
        tab.setIcon(R.drawable.sup);
mFragmentTitleList.set(tabposition,tabtitle);
    }


    @Override
    public void onBackPressed() {
        tabLayout.setVisibility(View.INVISIBLE);
addt.setVisibility(View.INVISIBLE);

    }


    public void createFrags() {
        for (int i = 0; i < mFragmentTitleList.size(); i++) {


            Fragment fragment = new DummySectionFragment();
            mFragmentList.add(fragment);

        }
    }
public void setTabTitle(String title){
    TabLayout.Tab tab = tabLayout.getTabAt(tabposition);
    tab.setText(title);

}



    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        private TextProvider mProvider;
        private long baseId = 0;



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

        public void removeFragment( int position) {
            mFragmentList.remove(position);
            mFragmentTitleList.remove(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}