package com.example.win.racecounter.handlers;

import android.app.Application;
import android.os.Environment;

import com.example.win.racecounter.R;
import com.example.win.racecounter.activities.MainActivity;
import com.example.win.racecounter.models.Racer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FilesHandler {
    public static void saveJsonFile(String fileName, ArrayList arrayOfRacers, boolean isResults){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            String basePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            String filePath = basePath + File.separator + fileName;
            FileOutputStream stream = null;
            try {
                File file = new File(filePath);
                file.createNewFile();
                stream = new FileOutputStream(file);

                JsonHandler jsonHandler = new JsonHandler();
                jsonHandler.writeJsonStream(stream, arrayOfRacers, isResults);

                stream.close();
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Racer> readJsonFile(String fileName){
        ArrayList<Racer> arrayOfRacers = new ArrayList<Racer>(MainActivity.INITIAL_CAPACITY);
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            String basePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            String filePath = basePath + File.separator + fileName;
            FileInputStream stream = null;
            try {
                File file = new File(filePath);
                file.createNewFile();
                stream = new FileInputStream(file);

                JsonHandler jsonHandler = new JsonHandler();
                arrayOfRacers = jsonHandler.readJsonStream(stream);

                stream.close();
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return arrayOfRacers;
    }

    //public static String convertRacersToString(ArrayList<Racer> arrayOfRacers){
    public static String readJsonFileToString(String fileName){
        String resultString = "";

        //String fileName = "NewData.json";   //#TODO filename
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            String basePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            String filePath = basePath + File.separator + fileName;
            FileInputStream stream = null;
            try {
                File file = new File(filePath);
                file.createNewFile();
                stream = new FileInputStream(file);

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder stringBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine).append('\n');
                }
                resultString = stringBuilder.toString();
                reader.close();
                stream.close();
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultString;
    }
}
