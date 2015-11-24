package com.guillalva.pruebas.gotoheaven;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {

    String txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //txt = (TextView)findViewById(R.id.txt);
        AsyncTask<String,Void,String> task = new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                StringBuilder builder = new StringBuilder();
                HttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("https://maps.googleapis.com/maps/api/directions/json?origin=Chicago,IL&destination=Joplin,MO&key=AIzaSyDnnmgi40JjFpgC_tEywJ07ZZQiaoQ3Swk");
                System.out.println(httpGet.getURI());
                try {
                    HttpResponse response = client.execute(httpGet);
                    StatusLine statusLine = response.getStatusLine();
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                    Log.e(MainActivity.class.toString(),
                            "GET request failed" + e.getLocalizedMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(MainActivity.class.toString(),
                            "GET request failed"+e.getLocalizedMessage());
                }
                System.out.println(builder.toString());
                return builder.toString();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //txt.setText(s);
                Log.i("TAG ",s);
            }
        };
        task.execute();
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
}
