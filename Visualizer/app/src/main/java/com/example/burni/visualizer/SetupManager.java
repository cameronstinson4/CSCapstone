package com.example.burni.visualizer;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by burni on 11/15/2016.
 * This class houses all the setup information and stores it on permanant storage
 */

public class SetupManager {

    //Name of the configuration file stored in the phones local storage
    private static final String setupFileName = "setup.txt";

    private Context _context;
    private String _filePath;

    public SetupManager(Context context){
        _context = context;
        _filePath = _context.getFilesDir() + "/" + setupFileName;
    }

    /**
     * Returns a boolean indicating if the app is setup or not
     * @return
     */
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

    /**
     * Takes in values from the setup fragment and saves them onto the hard drive, thus configuring the application
     * @param URL
     * @param name
     * @param pin
     */
    public void setup(String URL, String name, String pin) {

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(_context.openFileOutput(setupFileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(URL + "\n" + name + "\n" + pin);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the setup file to remove config data
     */
    public void resetSetup() {
        try {
            File file = new File(_filePath);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Used by other public methods to retrieve data from the config file
     * @return
     */

    private ArrayList<String> getConfiguration() {

        ArrayList<String> output = new ArrayList<>();

        try {
            InputStream inputStream = _context.openFileInput(setupFileName);

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

    /**
     * Returns the configured URL
     * @return
     */
    public String getUrl() {
        return getConfiguration().get(0);
    }

    /**
     * Returns the configured OperationName
     * @return
     */
    public String getOperationName() {
        return getConfiguration().get(1);
    }


    /**
     * Returns the configured pin
     * @return
     */
    public String getPin() {
        return getConfiguration().get(2);
    }
}
