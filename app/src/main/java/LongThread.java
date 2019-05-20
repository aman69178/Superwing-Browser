package com.example.aman.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class LongThread  implements Runnable {

    int threadno;
    String threadName;
    Handler handler;
    String imageUrl;
    public static final String TAG = "LongThread";


    public LongThread(int n,String threadN, String imageUrl, Handler handler) {
        this.threadno=n;
        this.threadName = threadN;
        this.handler = handler;
        this.imageUrl = imageUrl;
    }

    @Override
        public void run () {



        File downloadFile = new File("/sdcard/Superwing/download/" + threadName);

            if (downloadFile.exists())
                downloadFile.delete();
            try {

                downloadFile.createNewFile();
                URL downloadURL = new URL(imageUrl);
                HttpURLConnection conn = (HttpURLConnection) downloadURL
                        .openConnection();
                int lenghtOfFile = conn.getContentLength();

                int responseCode = conn.getResponseCode();
                if (responseCode != 200)
                    throw new Exception("Error in connection");
                InputStream is = conn.getInputStream();
                FileOutputStream os = new FileOutputStream(downloadFile);
                byte buffer[] = new byte[1024];
                long total = 0;
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {
                    os.write(buffer, 0, byteCount);
                    total += byteCount;

                    int progress = (int) ((total * 100) / lenghtOfFile);
                    Message msg = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putInt("Progress", progress);
                    bundle.putInt("size",lenghtOfFile);
                    bundle.putLong("byte",total);
                    msg.setData(bundle);
                    handler.sendMessage(msg);


                }
                os.close();
                is.close();


            } catch (Exception e) {

                e.printStackTrace();
            }
        }


    }
