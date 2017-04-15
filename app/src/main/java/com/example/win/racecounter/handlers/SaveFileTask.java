package com.example.win.racecounter.handlers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.win.racecounter.R;
import com.example.win.racecounter.models.Racer;

import java.util.ArrayList;

public class SaveFileTask extends AsyncTask<Object, Integer, Void> {
    private ProgressDialog dialog;
    private Context context;
    private boolean isResults;

    public SaveFileTask (Context context) {
        this.dialog =  new ProgressDialog(context);
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setMessage(context.getString(R.string.dialog_save_data));
        dialog.show();
    }

    @Override
    protected Void doInBackground(Object... params) {
        String fileNameSave = (String) params[0];
        ArrayList<Racer> arrayOfRacers = (ArrayList<Racer>) params[1];
        boolean isResults = (boolean) params[2];
        this.isResults = isResults;

        try {
            Thread.sleep(2000);   //used to simulate long time loading
            FilesHandler.saveJsonFile(fileNameSave, arrayOfRacers, isResults);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        dialog.dismiss();
        if(isResults){
            Toast.makeText(context, R.string.toast_results_saved, Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, R.string.start_list_saved, Toast.LENGTH_LONG).show();
        }

    }
}
