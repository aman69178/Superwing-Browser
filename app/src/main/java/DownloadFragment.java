package com.example.aman.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.math.*;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DownloadFragment extends Fragment implements Handler.Callback {
    private Handler handler;
    private static int delay=0;

    RemoteViews contentView;
    NotificationManager mNotifyManager;
    float totalCount = 50F;
    NotificationCompat.Builder mBuilder;
    int a;
    String name;
    private int ID=0;
   private View view;
static boolean test=true;
ThreadPoolExecutor executor;
    PendingIntent open;
Intent n;
    String ext;


   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.fragment_download, container, false);
       Random r = new Random();

        a = r.nextInt((100-10)+1)+10;
      Bundle b = getArguments();
      String url=b.getString("imageurl");
       ext=b.getString("ext");
       name=b.getString("urlname");
      contentView = new RemoteViews(getActivity().getPackageName(), R.layout.mactivity);

       int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
       mBuilder=new NotificationCompat.Builder(getActivity());
     mNotifyManager =
              (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
      mBuilder = new NotificationCompat.Builder(getActivity());
       contentView.setImageViewResource(R.id.image, R.drawable.notification);
       contentView.setTextViewText(R.id.title, name);
        contentView.setProgressBar(R.id.notibar,100,0,false);


           mBuilder.setCustomContentView(contentView)
                .setSmallIcon(R.drawable.filedownload);
     String hurl="/sdcard/Superwing/download/" + name;

       Intent intent=new Intent(getActivity(),VideoActivity.class);
       intent.putExtra("url",hurl);

       PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);

     mBuilder.setContentIntent(pendingIntent);
contentView.setOnClickPendingIntent(a,pendingIntent);

       mNotifyManager.notify(a, mBuilder.build());

       executor = new ThreadPoolExecutor(
              NUMBER_OF_CORES * 2,
              NUMBER_OF_CORES * 2,
              60L,
              TimeUnit.SECONDS,
              new LinkedBlockingQueue<Runnable>()
      );

      executor.execute(new LongThread(a,name, url, new Handler(this)));

      return view;

   }


   @Override
   public boolean handleMessage(Message msg) {
       Bundle bundle=msg.getData();
       int prog=bundle.getInt("Progress");
       int size=bundle.getInt("size");
 int tbd=size/1000000;
 int tad=(size%1000000)/10000;
Long yte=bundle.getLong("byte");
Long bd=yte/1000000;
Long ad=(yte%1000000)/10000;

if(delay==80) {
    if(prog==99){

mBuilder.setContentIntent(open);
        contentView.setProgressBar(R.id.notibar, 100, 100, false);
         mNotifyManager.notify(a, mBuilder.build());

    }
    else {
        contentView.setTextViewText(R.id.status,tbd+"."+tad+" of "+bd+"."+ad+" mb Completed");

        contentView.setProgressBar(R.id.notibar, 100, prog, false);
               mNotifyManager.notify(a, mBuilder.build());
            delay = 0;

    }

}
else{
    delay=delay+5;
}
       return true;

   }

}