package com.example.aman.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import android.graphics.Color;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.Menu;

import android.view.MenuItem;

import android.view.View;



import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;

// Activity that intercepts view and share intents for images
public class SearchGoogleIntentActivity extends AppCompatActivity
{



    // Asynchronous task for fetching Google hash of image
    class GoogleImageHashJob extends AsyncTask<Bitmap, Void, Integer>
    {
        @Override
        protected Integer doInBackground(Bitmap[]  bitmap)
        {

            try
            {
                // Send bitmap to google hash server
                String hash = GoogleImageHash.hashFromBitmap(bitmap[0]);

                //Send hash link to browser
               Intent openHashURLinBrowser = new Intent(SearchGoogleIntentActivity.this,ImageResult.class);
                openHashURLinBrowser.putExtra("url",hash);
                startActivity(openHashURLinBrowser);
                //Bundle b = new Bundle();
                //b.putString("Key", hash);
                //Fragment fragment = new Webv();
                //fragment.setArguments(b);


            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return 0;
        }

        // Close splash screen after hash has been fetched and browser intent sent
        @Override
        protected void onPostExecute(Integer result)
        {

            Toast.makeText(SearchGoogleIntentActivity.this, "Opening browser", Toast.LENGTH_LONG).show();

            //if (result == 0)
              //  SearchGoogleIntentActivity.this.finish();
        }
    }

    // Load splash screen
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Setup activity
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reverse_image_search_google);


        Intent intent = getIntent();
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);

        // Load image
      //  ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Bitmap bitmap = ImageLoader.getInstance().loadImageSync(imageUri.toString());
        //imageView.setImageBitmap(bitmap);

        // Run hash job
        GoogleImageHashJob job = new GoogleImageHashJob();
        job.execute(bitmap);


    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reverse_image_search_google, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
