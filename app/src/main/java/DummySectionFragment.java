package com.example.aman.myapplication;


import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

class BottomNavigationViewHelper {
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


public  class DummySectionFragment extends Fragment {
    public Dialog griddialog = null;

    Button b1;
    EditText ed1;
    ImageView img;
    private GridView dgrid;
    private BottomNavigationView navigation;
    private TextView close;
    private ImageButton voicesrch;
    private ImageButton imagesrch;

    GridView grid;
    String[] web = {
            "Amazon",
            "Google",
            "Facebook",
            "Myntra",
            "Instagram",
            "Ebay",
            "Flipkart",
            "Olx",

    };

    int[] imageId = {
            R.drawable.amazon,
            R.drawable.google,
            R.drawable.facebook,
            R.drawable.myntra,
            R.drawable.instagram,
            R.drawable.ebay,
            R.drawable.flipkart,
            R.drawable.olx,


    };

    String[] dweb = {
            "History",
            "Downloads",
            "Bookmark",
            "Settings",
            "Memory Sensor",
            "QRSearch",


    };
    int[] dimageId = {
            R.drawable.history,
            R.drawable.download,
            R.drawable.bookmark,
            R.drawable.settings,
            R.drawable.cleaner,
            R.drawable.qrs,


    };
    private String Tag = "TAG";
    private SpeechRecognizer sr;


    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.homeback:
                    Fragment fragment = new DummySectionFragment();
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.homefragment, fragment);
                    fragmentTransaction.addToBackStack("SpinnerFragment");
                    fragmentTransaction.commit();


                    return true;
                case R.id.homeforward:


                    return true;
                case R.id.homedash:

                    griddialog = new Dialog(getActivity(), R.style.CustomDialog);

                    griddialog.setContentView(R.layout.gridcustom);
                    CustomGrid gadapter = new CustomGrid(getActivity(), dweb, dimageId);
                    grid = (GridView) griddialog.findViewById(R.id.dialoggrid);
                    grid.setAdapter(gadapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            if (position == 0) {


                                navigation.setVisibility(View.INVISIBLE);

                                Bundle b = new Bundle();
                                b.putString("back", "home");
                                Fragment fragment = new SpinnerActivity();
                                fragment.setArguments(b);
                                FragmentManager fragmentManager = getChildFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.homefragment, fragment);
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
                                b.putString("back", "home");
                                Fragment fragment = new BookmarkActivity();
                                fragment.setArguments(b);
                                FragmentManager fragmentManager = getChildFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.homefragment, fragment);
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
                               Intent intent = new Intent(getActivity(), Tab.class);
                                startActivity(intent);
                                griddialog.dismiss();


                            } else if (position == 5) {


                                //grid.setVisibility(View.INVISIBLE);
                                //navigation.setVisibility(View.VISIBLE);
                                navigation.setVisibility(View.INVISIBLE);

                                Fragment fragment = new Barcode();
                                FragmentManager fragmentManager = getChildFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.homefragment, fragment);
                                fragmentTransaction.addToBackStack("Barcode");
                                fragmentTransaction.commit();
                                griddialog.dismiss();

                                // Intent intent=new Intent(getActivity(),Barcode.class);
                                //startActivity(intent);
                                // griddialog.dismiss();
                            }
                        }


                    });

                    griddialog.show();
                    return true;
                //  dgrid.setVisibility(View.VISIBLE);
                //navigation.setVisibility(View.INVISIBLE);
                //close.setVisibility(View.VISIBLE);
                //return true;
                case R.id.homehome:

                    Fragment fra = new DummySectionFragment();
                    FragmentManager fragmentManage = getChildFragmentManager();
                    FragmentTransaction fragmentTransactio = fragmentManage.beginTransaction();
                    fragmentTransactio.replace(R.id.homefragment, fra);
                    fragmentTransactio.addToBackStack("HomeFragment");
                    fragmentTransactio.commit();

                    return true;

                case R.id.tab:

                    return true;
            }

            return false;
        }
    };
    public static final String ARG_SECTION_NUMBER = "section_number";

    private String mText;

    public static DummySectionFragment newInstance() {
        DummySectionFragment f = new DummySectionFragment();
        return f;
    }

    public DummySectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup
            container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home,
                container, false);

        navigation = (BottomNavigationView) view.findViewById(R.id.homenavigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        img = (ImageView) view.findViewById(R.id.logo);
        img.setImageResource(R.drawable.sup);
        TextView lg = (TextView) view.findViewById(R.id.name);
        lg.setText("superwing");
        b1 = (Button) view.findViewById(R.id.button);
        ed1 = (EditText) view.findViewById(R.id.editText);

        close = (TextView) view.findViewById(R.id.clos);
        close.setText("Close");
        close.setVisibility(View.INVISIBLE);


        //get the SpeechRecognizer and set a listener for it.


        voicesrch = (ImageButton) view.findViewById(R.id.voicesrch);
        voicesrch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               navigation.setVisibility(View.INVISIBLE);
                Fragment fragment = new VoiceSearch();
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.homefragment, fragment);
                fragmentTransaction.addToBackStack("SearchFragment");
                fragmentTransaction.commit();


            }
        });


        imagesrch = (ImageButton) view.findViewById(R.id.imagesrch);
        imagesrch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), SearchGoogleLauncherActivity.class);
                startActivity(intent);



            }
        });




        ed1.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on Enter key press
                    if (ed1.getText() != null) {

                        String url = ed1.getText().toString();
                        Bundle b = new Bundle();
                        b.putString("Key", url);
                        navigation.setVisibility(View.INVISIBLE);

                        Fragment fragment = new Search();
                        fragment.setArguments(b);
                        FragmentManager fragmentManager = getChildFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.homefragment, fragment);
                        fragmentTransaction.addToBackStack("SearchFragment");
                        fragmentTransaction.commit();


                    }
                    return true;
                }
                return false;
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigation.setVisibility(View.VISIBLE);
                close.setVisibility(View.INVISIBLE);
                dgrid.setVisibility(View.INVISIBLE);

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ed1.getText().toString();
                Bundle b = new Bundle();
                b.putString("Key", url);
                navigation.setVisibility(View.INVISIBLE);

                Fragment fragment = new Search();
                fragment.setArguments(b);
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.homefragment, fragment);
                fragmentTransaction.addToBackStack("SearchFragment");
                fragmentTransaction.commit();


            }


        });

        CustomGrid adapter = new CustomGrid(getActivity(), web, imageId);
        grid = (GridView) view.findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                if (position == 0) {

                    navigation.setVisibility(View.INVISIBLE);

                    Bundle b = new Bundle();
                    b.putString("Key", "https://www.amazon.in/gp/site-directory/ref=nav_shopall_btn");
                    Fragment fragment = new Webv();
                    fragment.setArguments(b);
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.homefragment, fragment);
                    fragmentTransaction.addToBackStack("WebvFragment");
                    fragmentTransaction.commit();


                } else if (position == 1) {

                    Bundle b = new Bundle();
                    b.putString("Key", "www.google.com");
                    navigation.setVisibility(View.INVISIBLE);
                    Fragment fragment = new Webv();
                    fragment.setArguments(b);
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.homefragment, fragment);
                    fragmentTransaction.addToBackStack("WebvFragment");
                    fragmentTransaction.commit();


                } else if (position == 2) {
                    Bundle b = new Bundle();
                    b.putString("Key", "https://www.facebook.com/");

                    navigation.setVisibility(View.INVISIBLE);

                    Fragment fragment = new Webv();
                    fragment.setArguments(b);
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.homefragment, fragment);
                    fragmentTransaction.addToBackStack("WebvFragment");
                    fragmentTransaction.commit();


                } else if (position == 3) {
                    Bundle b = new Bundle();
                    b.putString("Key", "https://www.myntra.com/");

                    navigation.setVisibility(View.INVISIBLE);

                    Fragment fragment = new Webv();
                    fragment.setArguments(b);
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.homefragment, fragment);
                    fragmentTransaction.addToBackStack("WebvFragment");
                    fragmentTransaction.commit();


                } else if (position == 4) {
                    Bundle b = new Bundle();
                    b.putString("Key", "https://www.instagram.com/?hl=en");

                    navigation.setVisibility(View.INVISIBLE);

                    Fragment fragment = new Webv();
                    fragment.setArguments(b);
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.homefragment, fragment);
                    fragmentTransaction.addToBackStack("WebvFragment");
                    fragmentTransaction.commit();


                } else if (position == 5) {
                    Bundle b = new Bundle();
                    b.putString("Key", "https://www.ebay.in/");

                    navigation.setVisibility(View.INVISIBLE);

                    Fragment fragment = new Webv();
                    fragment.setArguments(b);
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.homefragment, fragment);
                    fragmentTransaction.addToBackStack("WebvFragment");
                    fragmentTransaction.commit();


                } else if (position == 6) {
                    Bundle b = new Bundle();
                    b.putString("Key", "https://www.flipkart.com/?affid=partnershi1");
                    navigation.setVisibility(View.INVISIBLE);


                    Fragment fragment = new Webv();
                    fragment.setArguments(b);
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.homefragment, fragment);
                    fragmentTransaction.addToBackStack("WebvFragment");
                    fragmentTransaction.commit();


                } else if (position == 7) {
                    Bundle b = new Bundle();
                    b.putString("Key", "https://www.olx.in/");

                    navigation.setVisibility(View.INVISIBLE);

                    Fragment fragment = new Webv();
                    fragment.setArguments(b);
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.homefragment, fragment);
                    fragmentTransaction.addToBackStack("WebvFragment");
                    fragmentTransaction.commit();

                }


            }


        });


        CustomGrid dadapter = new CustomGrid(getActivity(), dweb, dimageId);
        dgrid = (GridView) view.findViewById(R.id.dgrid);
        dgrid.setAdapter(dadapter);
        dgrid.setVisibility(View.INVISIBLE);
        dgrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (position == 0) {

                    close.setVisibility(View.INVISIBLE);
                    dgrid.setVisibility(View.INVISIBLE);
                    navigation.setVisibility(View.INVISIBLE);
                    Bundle b = new Bundle();
                    b.putInt("match", 1);
                    navigation.setVisibility(View.INVISIBLE);
                    Fragment fragment = new SpinnerActivity();
                    fragment.setArguments(b);
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.homefragment, fragment);
                    fragmentTransaction.addToBackStack("SearchFragment");
                    fragmentTransaction.commit();


                } else if (position == 1) {
                    close.setVisibility(View.INVISIBLE);
                    dgrid.setVisibility(View.INVISIBLE);
                    // navigation.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(getActivity(), DownloadedActivity.class);
                    startActivity(intent);


                } else if (position == 2) {

                    close.setVisibility(View.INVISIBLE);
                    dgrid.setVisibility(View.INVISIBLE);
                    navigation.setVisibility(View.INVISIBLE);
                    Bundle b = new Bundle();
                    b.putInt("match", 1);
                    navigation.setVisibility(View.INVISIBLE);
                    Fragment fragment = new BookmarkActivity();
                    fragment.setArguments(b);
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.homefragment, fragment);
                    fragmentTransaction.addToBackStack("SearchFragment");
                    fragmentTransaction.commit();


                } else if (position == 3) {


                    close.setVisibility(View.INVISIBLE);
                    dgrid.setVisibility(View.INVISIBLE);
                    navigation.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(getActivity(), PrefsActivity.class);
                    startActivity(intent);
                } else if (position == 4) {

                    close.setVisibility(View.INVISIBLE);
                    dgrid.setVisibility(View.INVISIBLE);
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

                } else if (position == 5) {


                    close.setVisibility(View.INVISIBLE);
                    dgrid.setVisibility(View.INVISIBLE);
                    navigation.setVisibility(View.INVISIBLE);
                    //  Intent intent=new Intent(getActivity(),Barcode.class);
                    //startActivity(intent);

                }


            }


        });

        return view;

    }


}




