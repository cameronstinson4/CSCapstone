package com.example.burni.visualizer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLngBounds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by burni on 11/15/2016.
 */

public class SetupManager {

    private Context _context;
    private String _filePath;

    public SetupManager(Context context) {
        _context = context;
        _filePath = _context.getFilesDir() + "/setup.json";
    }

    public boolean isSetup() {
        try {
            FileInputStream fis = new FileInputStream(new File(_filePath));
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
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(_context.openFileOutput("setup.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(URL + "\n" + name + "\n" + pin);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteFile() {
        try {
            File file = new File(_filePath);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getConfiguration() {

        ArrayList<String> output = new ArrayList<>();

        try {
            InputStream inputStream = _context.openFileInput("setup.json");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    output.add(receiveString);
                }

                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    public String getUrl() {
        return getConfiguration().get(0);
    }
    public String getOperationName() {
        return getConfiguration().get(1);
    }
    public String getPin() {
        return getConfiguration().get(2);
    }
}
