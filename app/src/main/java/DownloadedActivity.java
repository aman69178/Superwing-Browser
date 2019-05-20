

        package com.example.aman.myapplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

        public class DownloadedActivity extends ListActivity {
            MediaPlayer mp;
    private List<String> fileList = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        File root = new File("sdcard/Superwing/download");
        ListDir(root);

    }

    void ListDir(File f){
        File[] files = f.listFiles();
        fileList.clear();
        for (File file : files){
            fileList.add(file.getPath());
        }

        ArrayAdapter<String> directoryList
                = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, fileList);
        setListAdapter(directoryList);

    }

    protected void onListItemClick(ListView list, View view, int position, long id) {

        super.onListItemClick(list, view, position, id);
        String url=(String)getListView().getItemAtPosition(position);
        Uri uri=Uri.parse(url);
        if(url.contains("mp3")) {
            Intent audiointent=new Intent(this,Audio.class);
            audiointent.putExtra("audiourl",url);
            startActivity(audiointent);
            //mp = MediaPlayer.create(getApplicationContext(), uri);// the song is a filename which i have pasted inside a folder **raw** created under the **res** folder.//
            //mp.start();
        }
        if(url.contains("mp4")) {
            Intent audiointent=new Intent(this,VideoActivity.class);
            audiointent.putExtra("audiourl",url);
            startActivity(audiointent);
            //mp = MediaPlayer.create(getApplicationContext(), uri);// the song is a filename which i have pasted inside a folder **raw** created under the **res** folder.//
            //mp.start();
        }
        /** Intent intent=new Intent(this,VideoActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);**/
    }



}




