package com.example.nikta.api_1;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"button enter",Toast.LENGTH_SHORT).show();
    }

    public void buttonGetAPIClick(View view) {
        Toast.makeText(this,"button enter",Toast.LENGTH_SHORT).show();
        String joke ="";
        try {
            String url = "http://api.icndb.com/jokes/random";
            Toast.makeText(this,"button",Toast.LENGTH_SHORT).show();
            String jSource = getStream(url);
            JSONObject jObject = new JSONObject(jSource);
            /*Toast.makeText(this,"json ob",Toast.LENGTH_SHORT).show(); */
            JSONObject jobjectInner = jObject.getJSONObject("value");
            joke =jobjectInner.getString("joke");
        }
        catch(JSONException e)
        {
            Log.d("Exception", e.toString());
        }
        TextView tv = (TextView)findViewById(R.id.textView);
        tv.setText(joke.toString());
    }

    public static String getStream(String urlAddress) {
        InputStream is = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL u = new URL(urlAddress);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            is = conn.getInputStream();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            is.close();
        } catch (Exception e) {
            Log.d("Buffer Error", "Error converting result " + e.toString());
        }
        return(sb.toString());
    }
}
