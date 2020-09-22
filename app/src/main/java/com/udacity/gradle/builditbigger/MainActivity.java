package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.jandroidlibrary.JAndroidJokeDisplayActivity;
import com.example.jjokegeneratorlib.JJokeGenerator;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.backend.myApi.model.MyBean;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    public static String JOKE_KEY = "Joke key";
    private String joke;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //new JokesEndpointAsyncTask().execute(new Pair<Context, String>(this, "Jharna"));
        //new JokesEndpointAsyncTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void tellJoke(View view) {
        JJokeGenerator joker = new JJokeGenerator();
        //Toast.makeText(this, "derp", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, joker.getJoke(), Toast.LENGTH_LONG).show();
    }


    public void launchAndroidLibActivityToDisplayAJoke(View view) {
        Intent intent = new Intent(this, JAndroidJokeDisplayActivity.class);
        startActivity(intent);
    }

    public void displayJokeViaGCEAndJavaLib(View view) {
        new JokesEndpointAsyncTask().execute();
        //new JokesEndpointAsyncTask().execute(new Pair<Context, String>(this, "Jharna"));
    }

    private void updateUI(String result) {
        joke = result;
        //Toast.makeText(this, result, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(MainActivity.this, JAndroidJokeDisplayActivity.class);
        intent.putExtra(JOKE_KEY, joke);
        startActivity(intent);

    }


    public class JokesEndpointAsyncTask  extends AsyncTask<Void, Void, String> {
        private MyApi myApiService = null;
        private Context context;

        @Override
        protected String doInBackground(Void... params) {
            if(myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            try {
                //return myApiService.sayHi(name).execute().getData();
                return myApiService.getJoke().execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        /**
         * This method is invoked on the main UI thread after the background work has been
         * completed.
         *
         * It IS okay to modify the UI within this method. We take the {@link MyBean} object
         * (which was returned from the doInBackground() method) and update the views on the screen.
         */
        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                //We cannot do any updating, as nothing was returned.
                return ;
            }
            //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            //TODO update the UI
            updateUI(result);

        }
    }




}
