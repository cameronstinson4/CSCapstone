package com.example.burni.visualizer.web;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public abstract class GetJsonDataTaskBase extends AsyncTask<String, Void, JSONObject> {

    protected ResultCallback call;

    public GetJsonDataTaskBase(ResultCallback call)
    {
        this.call = call;
    }

    public static JSONObject readJsonObjectFromUrl(String url) throws IOException, JSONException {

        JSONObject json = null;

        try {
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            json = new JSONObject(jsonText);
            is.close();
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    @Override
    protected void onPreExecute()
    {

    }

    @Override
    protected JSONObject doInBackground(String... params) {
        try {
            //URL url = new URL(urls[0]);
            JSONObject obj = readJsonObjectFromUrl(params[0]);

            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}