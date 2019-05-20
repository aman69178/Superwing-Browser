package com.example.aman.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.ActivityNotFoundException;

import android.content.Intent;

import android.os.Bundle;

import android.speech.RecognizerIntent;

import android.support.v7.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ImageButton;

import android.widget.TextView;


import java.util.ArrayList;

import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class VoiceSearch extends Fragment {


    private static final int REQ_CODE_SPEECH_INPUT = 100;

    private TextView mVoiceInputTv;
    private ImageButton mSpeakBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup
            container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_voice_search,
                container, false);


        mVoiceInputTv = (TextView) view.findViewById(R.id.voiceInput);

        mSpeakBtn = (ImageButton) view.findViewById(R.id.btnSpeak);

        mSpeakBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                startVoiceInput();
            }
        });
    return view;
    }


    private void startVoiceInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        //intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 1000);
        //intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 1000);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 1500);


        try {

            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);

        } catch (ActivityNotFoundException a) {

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQ_CODE_SPEECH_INPUT: {

                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                   mVoiceInputTv.setText(result.get(0));
                    String url = result.get(0);
                    Bundle b = new Bundle();
                    b.putString("Key", url);
                   // navigation.setVisibility(View.INVISIBLE);

                    Fragment fragment = new Search();
                    fragment.setArguments(b);
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.activity_main, fragment);
                    fragmentTransaction.addToBackStack("SearchFragment");
                    fragmentTransaction.commit();

                }

                break;
            }

        }


    }
}
