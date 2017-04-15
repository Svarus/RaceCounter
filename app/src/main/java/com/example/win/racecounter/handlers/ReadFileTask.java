package com.example.win.racecounter.handlers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.win.racecounter.R;
import com.example.win.racecounter.activities.MainActivity;
import com.example.win.racecounter.models.Racer;

import java.util.ArrayList;

public class ReadFileTask extends AsyncTask<String, Integer, ArrayList<Racer>> {
    private ProgressDialog dialog;
    private Context context;

    public ReadFileTask (Context context) {
        this.dialog =  new ProgressDialog(context);
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setMessage(context.getString(R.string.dialog_load_data));
        dialog.show();
    }

    @Override
    protected ArrayList<Racer> doInBackground(String... params) {
        ArrayList<Racer> loadedList = new ArrayList<Racer>();
        try {
            //Thread.sleep(2000);   //used to simulate long time loading
            loadedList = FilesHandler.readJsonFile(params[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loadedList;
    }

    @Override
    protected void onPostExecute(ArrayList<Racer> resultList) {
        super.onPostExecute(resultList);

        if (resultList!= null){
            MainActivity.arrayOfRacers.clear();
            MainActivity.arrayOfRacers.addAll(resultList);
            MainActivity.arrayOfRacersProtocol.clear(); //TODO add loaded items to the protocol
            MainActivity.notifyAllAdapters();
            dialog.dismiss();
            Toast.makeText(context, R.string.start_list_loaded, Toast.LENGTH_LONG).show();
        } else {
            dialog.dismiss();
        }
    }
}