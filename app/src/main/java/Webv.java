package com.example.aman.myapplication;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Field;


class BottomNavigationVieHelpe {
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }
}


public class Webv extends Fragment {
public Dialog wgriddialog;
Context mContext;
    public Dialog wdialog;
    private WebView mywebvie;
    private ProgressBar progressBa;
    private String mUrl="http://developer.android.com/guide/index.html";
    private String mTitle = "";
    String wlist[]={"Save page","Copy url","Bookmark", "Share page"};
    private GridView wgrid;
    private ListView wlistview;
    private BottomNavigationView navigation;
    private TextView wclose;
public static String wname;

    public  boolean wvalue = true;
    String[] wweb = {
            "History",
            "Downloads",
            "Bookmark",
            "Settings",
            "Memory Sensor",
            "Share",



    } ;
    int[] wimageId = {
            R.drawable.history,
            R.drawable.download,
            R.drawable.bookmark,
            R.drawable.settings,
            R.drawable.cleaner,
            R.drawable.qrsearch,
    };

    EditText wser;

public  static  String wurl;
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.back:

                    if(mywebvie.canGoBack()) {
                        mywebvie.goBack(); }
                    else {

                        Fragment fragment = new DummySectionFragment();
                        FragmentManager fragmentManager = getChildFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.webvfragment, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        navigation.setVisibility(View.INVISIBLE);
                        return true;
                          }
                        return true;

                case R.id.forward:
                    mywebvie.goForward();

                    return true;
                case R.id.dash:
                    wgriddialog = new Dialog(getActivity(),R.style.CustomDialog);
                    wgriddialog.setContentView(R.layout.gridcustom);

                    CustomGrid gadapter = new CustomGrid(getActivity(), wweb, wimageId);
                    wgrid = (GridView) wgriddialog.findViewById(R.id.dialoggrid);
                    wgrid.setAdapter(gadapter);
                    wgrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            if(position == 0) {

                                  navigation.setVisibility(View.INVISIBLE);
                                Bundle b=new Bundle();
                                b.putString("back",wser.getText().toString());
                                Fragment fragment = new SpinnerActivity();
                                fragment.setArguments(b);
                                FragmentManager fragmentManager = getChildFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.webvfragment, fragment);
                                fragmentTransaction.addToBackStack("SpinnerFragment");
                                fragmentTransaction.commit();
                               wgriddialog.dismiss();

                            }

                            else if(position == 1) {

                                   navigation.setVisibility(View.INVISIBLE);
                                Intent intent=new Intent(getActivity(),DownloadedActivity.class);
                                startActivity(intent);
                                wgriddialog.dismiss();


                            }

                            else if(position == 2) {

                                 navigation.setVisibility(View.INVISIBLE);
                                Bundle b=new Bundle();
                                b.putString("back",wser.getText().toString());
                                Fragment fragment = new BookmarkActivity();
                                fragment.setArguments(b);
                                FragmentManager fragmentManager = getChildFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.webvfragment, fragment);
                                fragmentTransaction.addToBackStack("BookmarkFragment");
                                fragmentTransaction.commit();
                                wgriddialog.dismiss();

                            }
                            else if(position == 3) {


                                 navigation.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(getActivity(),PrefsActivity.class);
                                startActivity(intent);
                                wgriddialog.dismiss();


                            }


                            else if(position == 4) {

                                     navigation.setVisibility(View.VISIBLE);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                alertDialogBuilder.setTitle("Exit Application?");
                                alertDialogBuilder
                                        .setMessage("Click yes to exit!")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {

                                                        android.os.Process.killProcess(android.os.Process.myPid());
                                                        System.exit(1);
                                                    }
                                                })

                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                wgriddialog.dismiss();


                            }

                            else if(position == 5) {

                                   navigation.setVisibility(View.VISIBLE);
                                wgriddialog.dismiss();


                            }





                        }


                    });


                wgriddialog.show();
                    return true;

                case R.id.home:
navigation.setVisibility(View.INVISIBLE);
                    Fragment fragment = new DummySectionFragment();
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.webvfragment, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    return true;

                case R.id.tab:

                    mywebvie.reload();

                    return true;

            }

            return false;
        }
    };



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.webv, container, false);


        progressBa = (ProgressBar) view.findViewById(R.id.wprogressBar);
        progressBa.setMax(100);
        progressBa.setVisibility(View.VISIBLE);

        progressBa.setProgress(10);


         navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationVieHelpe.disableShiftMode(navigation);

        wser = (EditText) view.findViewById(R.id.wseditText);

        Bundle b = this.getArguments();



        String  s =b.getString("Key");

        ImageButton wimgButto = (ImageButton) view.findViewById(R.id.wsea);
        wimgButto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wv1 = "https://www.google.com/search?q=" + wser.getText().toString();

                mywebvie.loadUrl(wv1);
            }
        });


        ImageButton wimgButton = (ImageButton) view.findViewById(R.id.wcls);
        wimgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mywebvie.stopLoading();
            }
        });

mContext=getActivity().getApplicationContext();



        mywebvie = (WebView) view.findViewById(R.id.webView1);



        mywebvie.getSettings().setBuiltInZoomControls(true);
        mywebvie.getSettings().setDisplayZoomControls(false);
        mywebvie.getSettings().setAllowContentAccess(true);
        mywebvie.getSettings().setLoadsImagesAutomatically(true);
        mywebvie.getSettings().setJavaScriptEnabled(true);
        mywebvie.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mywebvie.canGoForward();
        mywebvie.canGoBack();
        mywebvie.getSettings().setAppCacheEnabled(true);
        mywebvie.getSettings().setAppCachePath(mContext.getCacheDir().getPath());

        mywebvie.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        mywebvie.loadUrl(s);

        mywebvie.setWebViewClient(new WebViewClientDemo());
        mywebvie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wlistview.setVisibility(View.INVISIBLE);
                navigation.setVisibility(View.VISIBLE);
                wclose.setVisibility(View.INVISIBLE);
                wgrid.setVisibility(View.INVISIBLE);

            }
        });



        mywebvie.getSettings().setPluginState(WebSettings.PluginState.ON);


        mywebvie.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                wdialog = new Dialog(getActivity(),R.style.CustomDialog);
                wdialog.setContentView(R.layout.custom);

                wlistview = (ListView) wdialog.findViewById(R.id.list);
                ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.activity_listview, wlist);
                wlistview.setAdapter(adapter);

                wlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent,
                                            View v, int position, long id) {
                        if (position == 0) {
                            wdialog.dismiss();

                        }

                        if (position == 1) {

                            wdialog.dismiss();

                        }

                        if (position == 2) {
                            BookmarkHandler bmk=new BookmarkHandler(getActivity());
                            String bmktitle=mywebvie.getTitle();
                            bmk.insertLabel(bmktitle);
                            BookmarkurlHandler bmkurl=new BookmarkurlHandler(getActivity());
                            bmkurl.insertLabel(bmktitle);
                            wdialog.dismiss();

                        }

                        if (position == 3) {
                            Intent shareIntent =   new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
                            shareIntent.putExtra(Intent.EXTRA_TEXT,wurl);
                            startActivity(Intent.createChooser(shareIntent, "Share via"));
                            wdialog.dismiss();
                                  }


                    }

                });


                wdialog.show();
                return true;
            }


        });



return view;
    }

    private  class WebViewClientDemo extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            wurl = url;
            String extension = MimeTypeMap.getFileExtensionFromUrl(url);
            if (extension != null) {
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String mimeType = mime.getMimeTypeFromExtension(extension);
                String name = URLUtil.guessFileName(url, null, extension);
                wname = name;
                if (mimeType != null) {
                    if (mimeType.toLowerCase().contains("video")
                            || extension.toLowerCase().contains("mov")
                            || extension.toLowerCase().contains("mp3")
                            || extension.toLowerCase().contains("pdf")) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setTitle("Play");
                        alertDialogBuilder
                                .setMessage(wname)
                                .setCancelable(false)
                                .setPositiveButton("Play Online",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent intent = new Intent(getActivity(), VideoActivity.class);
                                                intent.putExtra("url", wurl);
                                                startActivity(intent);
                                                wvalue = false;

                                            }
                                        })

                                .setNegativeButton("Download", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Bundle b = new Bundle();
                                        b.putString("imageurl", wurl);
                                        b.putString("urlname", wname);
                                        Fragment fragment = new DownloadFragment();
                                        fragment.setArguments(b);
                                        FragmentManager fragmentManager = getChildFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.webvfragment, fragment);
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();


                                        wvalue = false;
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();


                    }
                }
                if (wvalue) {
                    view.loadUrl(url);
                }
                        }
            return wvalue;

        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBa.setVisibility(View.VISIBLE);
            progressBa.setProgress(100);
            String title = mywebvie.getTitle();

            DatabaseHandler db = new DatabaseHandler(getActivity());
            db.insertLabel(url);
            HistoryUrlHandler hdb=new HistoryUrlHandler(getActivity());
            hdb.insertLabel(title);

            wser.setText(url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);


        }
    }

    private class WebChromeClientDemo extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            progressBa.setProgress(progress);
        }
    }


}

