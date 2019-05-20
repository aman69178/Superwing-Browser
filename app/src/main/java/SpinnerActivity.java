package com.example.aman.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.Collections;
import java.util.List;

public class SpinnerActivity extends Fragment {
    private BottomNavigationView navigation;
    WebView mywebview ;

    ListView slist;
ProgressBar progressBar;
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.historyback:

                    Bundle match=getArguments();
                    String m=match.getString("back");
                    if(m.equals("home")){
                        Fragment fragment = new DummySectionFragment();
                        FragmentManager fragmentManager = getChildFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.hbfragment, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        navigation.setVisibility(View.INVISIBLE);
                        return true;
                    }
                    else {
                        Bundle a = getArguments();
                        String u = a.getString("back");
                        Bundle b = new Bundle();
                        b.putString("Key", u);
                        Fragment fragment = new Webv();
                        fragment.setArguments(b);
                        FragmentManager fragmentManager = getChildFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.hbfragment, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        navigation.setVisibility(View.INVISIBLE);
                        return true;
                    }

                case R.id.clearall:

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle("Delete");
                    alertDialogBuilder
                            .setMessage("Clear all browsing history?")
                            .setCancelable(false)
                            .setPositiveButton("Delete",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            DatabaseHandler db=new DatabaseHandler(getActivity());
                                            HistoryUrlHandler hdb=new HistoryUrlHandler(getActivity());
                                            db.deleteall();
                                            hdb.deleteall();
                                            loadData();
                                        }
                                    })

                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    return true;

                case R.id.historyhome:

                    navigation.setVisibility(View.INVISIBLE);
                    Fragment fragment = new DummySectionFragment();
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.spinner, fragment);
                    fragmentTransaction.addToBackStack("HomeFragment");
                    fragmentTransaction.commit();


                    return true;
            }
            return false;
        }
    };



        @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      View  view = inflater.inflate(R.layout.layout_history, container, false);
      slist=(ListView)view.findViewById(R.id.amans);
            //ViewGroup header = (ViewGroup)inflater.inflate(R.layout.listview_header,slist,false);
             //slist.addHeaderView(header);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setMax(100);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(10);


            mywebview=(WebView)view.findViewById(R.id.webView);
            mywebview.setWebViewClient(new WebViewClientDemo());


            slist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String label = parent.getItemAtPosition(position).toString();
                 mywebview.loadUrl(label);
             /**   Bundle b = new Bundle();
         b.putString("Key", label);
         Fragment fragment = new Webv();
         fragment.setArguments(b);
         FragmentManager fragmentManager=getChildFragmentManager();
         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
         fragmentTransaction.replace(R.id.spinner, fragment);
         fragmentTransaction.addToBackStack(null);
         fragmentTransaction.commit();
         navigation.setVisibility(View.INVISIBLE);**/


    // }
            } });

              //  }
               // });





        loadData();


            view.setFocusableInTouchMode(true);
            view.requestFocus();
            view.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                        Bundle match = getArguments();
                        String m = match.getString("back");
                        if (m.equals("home")) {
                            Fragment fragment = new DummySectionFragment();
                            FragmentManager fragmentManager = getChildFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.hbfragment, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            navigation.setVisibility(View.INVISIBLE);
                            return true;
                        } else {
                            Bundle a = getArguments();
                            String u = a.getString("back");
                            Bundle b = new Bundle();
                            b.putString("Key", u);
                            Fragment fragment = new Webv();
                            fragment.setArguments(b);
                            FragmentManager fragmentManager = getChildFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.hbfragment, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            navigation.setVisibility(View.INVISIBLE);
                            return true;
                        }
                    }
                    return false;

                }} );



            navigation = (BottomNavigationView) view.findViewById(R.id.historynavigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            BottomNavigationViewHelper.disableShiftMode(navigation);

            return view;
    }
    private class WebViewClientDemo extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {


            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setProgress(99);


        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
progressBar.setProgress(15);
        }
    }
        private void loadData() {
            DatabaseHandler db = new DatabaseHandler(getActivity());
            List<String> lable = db.getAllLabels();
            Collections.reverse(lable);
            HistoryUrlHandler hdb = new HistoryUrlHandler(getActivity());
            List<String> hlable = hdb.getAllLabels();
            Collections.reverse(hlable);
            HistoryListAdapter adapter = new HistoryListAdapter(getActivity(), lable, hlable);
            slist.setAdapter(adapter);

        }
    }







