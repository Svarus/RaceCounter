package com.example.win.racecounter.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDataFromServer extends AsyncTask<String, Integer, String> {
    private static final String TAG = GetDataFromServer.class.getSimpleName();
    private ProgressDialog dialog;
    private Context context;
    private String resultString;
    private AsyncResponseReceive delegate;

    public void setDelegate(AsyncResponseReceive delegate) {
        this.delegate = delegate;
    }

    public GetDataFromServer(Context context){
        this.context = context;
        this.dialog =  new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setMessage("Loading data from Server...");
        dialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];
        // Making a request to url and getting response
        try {
            //Thread.sleep(2000);   //used to simulate long time loading
            URL url = new URL(urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            // read the response
            BufferedInputStream in = new BufferedInputStream(httpConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine).append('\n');
            }
            resultString = stringBuilder.toString();
            reader.close();
            in.close();
            httpConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }

        if (resultString == null) {
            Log.e(TAG, "Couldn't get json from server");
        } else {
            Log.e(TAG, "Response from url: " + resultString);
        }
        return resultString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        delegate.processGetFinish(result);
        dialog.dismiss();
    }

    public interface AsyncResponseReceive {
        void processGetFinish(String output);
    }
}
