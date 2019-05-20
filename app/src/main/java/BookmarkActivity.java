package com.example.aman.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;



public class BookmarkActivity extends Fragment {
    /** Spinner spinner;**/


    ListView slist;

    private BottomNavigationView navigation;

    private String title;

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
                                            BookmarkHandler db=new BookmarkHandler(getActivity());
                                            db.deleteall();
                                            BookmarkurlHandler urldb=new BookmarkurlHandler(getActivity());
                                            urldb.deleteall();
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

        View view = inflater.inflate(R.layout.spinner_main, container, false);
        navigation = (BottomNavigationView) view.findViewById(R.id.historynavigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);


        slist=(ListView)view.findViewById(R.id.amans);
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.blistview_header,slist,false);
        slist.addHeaderView(header);
        slist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String label = parent.getItemAtPosition(position).toString();
                Bundle b = new Bundle();
                b.putString("Key", label);
                Fragment fragment = new Webv();
                fragment.setArguments(b);
                FragmentManager fragmentManager=getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.spinner, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
navigation.setVisibility(View.INVISIBLE);

            }
        });




        loadData();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

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

                }
                return false;
            }
        });

        return view;
    }


    private void loadData() {
        BookmarkHandler db = new BookmarkHandler(getActivity());
        List<String> blable = db.getAllLabels();
      BookmarkurlHandler urldb=new BookmarkurlHandler(getActivity());

      List<String> urlable=urldb.getAllLabels();
        HistoryListAdapter adapter=new HistoryListAdapter(getActivity(),urlable,blable);
        slist.setAdapter(adapter);



    }





}
