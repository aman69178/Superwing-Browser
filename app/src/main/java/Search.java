package com.example.aman.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.aman.myapplication.MainActivity;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import java.util.concurrent.TimeUnit;

import static android.content.Context.DOWNLOAD_SERVICE;


class BottomNavigationVieHelper {
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


public class Search extends Fragment {
    public boolean value = true;
    public static String vurl;
      OnHeadlineSelectedListener callback=sDummyCallbacks;
    OnTabSelectedListener callbac=DummyCallbacks;

    public Dialog dialog = null;
    public Dialog griddialog = null;
    public MainActivity main;
    public static String vname;
    public static String vext;
    private String bookmark;
    private WebView mywebview;
    private ProgressBar progressBar;
    private String mUrl = "http://developer.android.com/guide/index.html";
    private String mTitle = "";
    String list[] = {"Save Image", "Select and Copy", "Bookmark", "Share page"};
    private GridView grid;
    SharedPreferences prefs;
    private ListView listview;
    private BottomNavigationView navigation;
    int curCount = 0;
    static String title;
    Context mContext;
    private boolean isChecked;
    private boolean isImageMode;
    String[] sweb = {
            "History",
            "Downloads",
            "Bookmark",
            "Settings",
            "Memory Sensor",
            "QRSearch"
    };
    int[] simageId = {
            R.drawable.history,
            R.drawable.download,
            R.drawable.bookmark,
            R.drawable.settings,
            R.drawable.cleaner,
            R.drawable.qrs,
    };
    public String uurl;
    public static final String ARG_OBJECT = "object";
    private Bundle webViewBundle;
    public String tag = "TAG";
    EditText ser;

private static OnHeadlineSelectedListener sDummyCallbacks=new OnHeadlineSelectedListener() {
    @Override
    public void onArticleSelected(String url) {

    }
};
    private static OnTabSelectedListener DummyCallbacks=new OnTabSelectedListener() {
        @Override
        public void onTabSelected(String url) {

        }
    };

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.back:

                    if (mywebview.canGoBack()) {
                        mywebview.goBack();
                    } else {

                        Fragment fragment = new DummySectionFragment();
                        FragmentManager fragmentManager = getChildFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.searchfragment, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        navigation.setVisibility(View.INVISIBLE);
                        return true;
                    }

                    return true;
                case R.id.forward:
                    mywebview.goForward();

                    return true;
                case R.id.dash:
                    griddialog = new Dialog(getActivity(), R.style.CustomDialog);

                    griddialog.setContentView(R.layout.gridcustom);
                    CustomGrid gadapter = new CustomGrid(getActivity(), sweb, simageId);
                    grid = (GridView) griddialog.findViewById(R.id.dialoggrid);
                    grid.setAdapter(gadapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            if (position == 0) {


                                navigation.setVisibility(View.INVISIBLE);

                                Bundle b = new Bundle();
                                b.putString("back", ser.getText().toString());
                                Fragment fragment = new SpinnerActivity();
                                fragment.setArguments(b);
                                FragmentManager fragmentManager = getChildFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.searchfragment, fragment);
                                fragmentTransaction.addToBackStack("SpinnerFragment");
                                fragmentTransaction.commit();
                                griddialog.dismiss();


                            } else if (position == 1) {


                                navigation.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(getActivity(), DownloadedActivity.class);
                                startActivity(intent);
                                griddialog.dismiss();


                            } else if (position == 2) {

                                navigation.setVisibility(View.INVISIBLE);
                                Bundle b = new Bundle();
                                b.putString("back", ser.getText().toString());
                                Fragment fragment = new BookmarkActivity();
                                fragment.setArguments(b);
                                FragmentManager fragmentManager = getChildFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.searchfragment, fragment);
                                fragmentTransaction.addToBackStack("BookmarkFragment");
                                fragmentTransaction.commit();
                                griddialog.dismiss();


                            } else if (position == 3) {


                                grid.setVisibility(View.INVISIBLE);
                                navigation.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(getActivity(), PrefsActivity.class);
                                startActivity(intent);
                                griddialog.dismiss();


                            } else if (position == 4) {
                                grid.setVisibility(View.INVISIBLE);
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
                                griddialog.dismiss();


                            } else if (position == 5) {


                                grid.setVisibility(View.INVISIBLE);
                                navigation.setVisibility(View.VISIBLE);
                                griddialog.dismiss();


                            }
                        }


                    });


                    griddialog.show();


                    return true;

                case R.id.home:
                    navigation.setVisibility(View.INVISIBLE);
                    callbac.onTabSelected("Home");
                    Fragment fragment = new DummySectionFragment();
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.searchfragment, fragment);
                    fragmentTransaction.addToBackStack("HomeFragment");
                    fragmentTransaction.commit();
                    return true;

                case R.id.tab:

                    mywebview.reload();

                    return true;

            }

            return false;
        }
    };


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnItemClickedListener");
        }
        try {
            callbac = (OnTabSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnItemClickedListener");
        }
    }

    public void setOnHeadlineSelectedListener(OnHeadlineSelectedListener callback) {
        this.callback = callback;
    }
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(String url);
    }
    public void setOnTabSelectedListener(OnTabSelectedListener callbac) {
        this.callbac = callbac;
    }
    public interface OnTabSelectedListener {
        public void onTabSelected(String url);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webViewBundle = new Bundle();

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)

        {
            if (mywebview.canGoBack()) {
                mywebview.goBack();
            } else {


            }
        }
        return true;
    }

    public void onResume() {
        super.onResume();
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        int position = args.getInt(ARG_OBJECT);
        View view = inflater.inflate(R.layout.search, container, false);
        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(10);
        navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        ser = (EditText) view.findViewById(R.id.seditText);
mContext=getActivity().getApplicationContext();
        bookmark = ser.getText().toString();

        ImageButton imgButto = (ImageButton) view.findViewById(R.id.sea);
        imgButto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String v1 = "https://www.google.com/search?q=" + ser.getText().toString();

                mywebview.loadUrl(v1);
            }
        });

        ImageButton imgButton = (ImageButton) view.findViewById(R.id.cls);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mywebview.stopLoading();
            }
        });
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String pur = prefs.getString("listpref", "Default list prefs");
        mywebview = (WebView) view.findViewById(R.id.webView1);
        if (mywebview != null) {
            mywebview.setWebViewClient(new WebViewClientDemo());

            if (webViewBundle == null || webViewBundle.isEmpty()) {
                Bundle b = this.getArguments();
                String s = b.getString("Key");
                String ur = "https://www.google.com/search?q=" + s;
                String sur = pur + s;
                mywebview.loadUrl(sur);
            } else {
                mywebview.restoreState(webViewBundle);
                webViewBundle.clear();
            }
        }
        isImageMode = prefs.getBoolean("Image Mode", false);
        if (!isImageMode) {

            mywebview.getSettings().setLoadsImagesAutomatically(false);

        }


        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mywebview.canGoForward();
        mywebview.canGoBack();
        mywebview.setWebViewClient(new WebViewClientDemo());
        mywebview.setWebChromeClient(new WebChromeClient());
        mywebview.getSettings().setBuiltInZoomControls(true);
        mywebview.getSettings().setDisplayZoomControls(false);
        mywebview.getSettings().setAllowContentAccess(true);



        mywebview.getSettings().setAppCacheEnabled(true);
        mywebview.getSettings().setAppCachePath(mContext.getCacheDir().getPath());

        mywebview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        mywebview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                dialog = new Dialog(getActivity(), R.style.CustomDialog);
                dialog.setContentView(R.layout.custom);

                listview = (ListView) dialog.findViewById(R.id.list);
                ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.activity_listview, list);
                listview.setAdapter(adapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent,
                                            View v, int position, long id) {
                        if (position == 0) {
                            //  main=new MainActivity();

                            //main.mSectionsPagerAdapter.addFragment(new DummySectionFragment(),"",3);
                            // main.mSectionsPagerAdapter.notifyDataSetChanged();
                 callback.onArticleSelected(imageviewer());
                            dialog.dismiss();

                        }

                        if (position == 1) {

                            dialog.dismiss();

                        }

                        if (position == 2) {
                            BookmarkHandler bmk = new BookmarkHandler(getActivity());
                            String bmktitle = mywebview.getTitle();
                            String burl = ser.getText().toString();
                            bmk.insertLabel(bmktitle);
                            BookmarkurlHandler bmkurl = new BookmarkurlHandler(getActivity());
                            bmkurl.insertLabel(burl);
                            dialog.dismiss();

                        }
                        if (position == 3) {
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Insert Subject here");
                            shareIntent.putExtra(Intent.EXTRA_TEXT, vurl);
                            startActivity(Intent.createChooser(shareIntent, "Share via"));
                            dialog.dismiss();
                        }


                    }

                });

                dialog.show();
                return true;
            }
        });


        return view;
    }





    @Override
    public void onPause() {

        super.onPause();
        if (mywebview != null && webViewBundle.isEmpty()) {
            mywebview.saveState(webViewBundle);
        }
    }


    private class WebViewClientDemo extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            vurl = url;
            String title = mywebview.getTitle();
            callbac.onTabSelected(title);

            final String extension = MimeTypeMap.getFileExtensionFromUrl(url);
            if (extension != null) {
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String mimeType = mime.getMimeTypeFromExtension(extension);
                String name = URLUtil.guessFileName(url, null, extension);
                vname = name;
                if (mimeType != null) {
                    if (mimeType.toLowerCase().contains("video")
                            || extension.toLowerCase().contains("mov")
                            || extension.toLowerCase().contains("mp3")
                            || extension.toLowerCase().contains("pdf")) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setTitle("Play");
                        alertDialogBuilder
                                .setMessage(vname)
                                .setCancelable(false)
                                .setPositiveButton("Play Online",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent intent = new Intent(getActivity(), VideoActivity.class);
                                                intent.putExtra("url", vurl);
                                                startActivity(intent);
                                                value = false;

                                            }
                                        })

                                .setNegativeButton("Download", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Bundle b = new Bundle();
                                        b.putString("imageurl", vurl);
                                        b.putString("urlname", vname);
                                        b.putString("ext", extension);
                                        Fragment fragment = new DownloadFragment();
                                        fragment.setArguments(b);
                                        FragmentManager fragmentManager = getChildFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.searchfragment, fragment);
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();


                                        value = false;
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();


                    }
                }
                if (value) {
                    view.loadUrl(url);
                }
            }

            return value;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);


            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(100);
             title = mywebview.getTitle();
            main = new MainActivity();
    callbac.onTabSelected(title);

            isChecked = prefs.getBoolean("Private Mode", false);
            if (!isChecked) {
                DatabaseHandler db = new DatabaseHandler(getActivity());
                db.insertLabel(url);
                HistoryUrlHandler hdb = new HistoryUrlHandler(getActivity());
                hdb.insertLabel(title);
                ser.setText(url);
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }


        public String getFileName(String url) {
            String filenameWithoutExtension = "";
            filenameWithoutExtension = String.valueOf(System.currentTimeMillis()
                    + ".mp4");
            return filenameWithoutExtension;
        }

    }


    public String imageviewer() {
        final WebView.HitTestResult webViewHitTestResult = mywebview.getHitTestResult();


            String imageurl = webViewHitTestResult.getExtra();

            return imageurl;

    }


}
