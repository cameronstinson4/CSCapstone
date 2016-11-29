package com.example.burni.visualizer;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by burni on 11/15/2016.
 */

public class SetupManager {

    private Context _context;
    private String _filepath;

    public SetupManager(Context context) {
        _context = context;
        _filepath = _context.getFilesDir() + "/setup.json";
    }

    public boolean isSetup() {
        try {
            FileInputStream fis = new FileInputStream(new File(_filepath));
            fis.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static LatLngBounds getBoundaries() {
        //TODO
        return MockServer.getBoundaries();
    }

    public void setup(String URL, String name, String pin) {

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(_context.openFileOutput(_filepath, Context.MODE_PRIVATE));
            outputStreamWriter.write(URL + "\n" + name + "\n" + pin);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        String ret = "";

        try {
            InputStream inputStream = _context.openFileInput(_filepath);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
