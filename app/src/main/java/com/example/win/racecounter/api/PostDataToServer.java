package com.example.win.racecounter.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostDataToServer extends AsyncTask<String,String,String> {
    private static final String TAG = PostDataToServer.class.getSimpleName();
    private ProgressDialog dialog;
    private Context context;
    private AsyncResponseSend delegate;

    public void setDelegate(AsyncResponseSend delegate) {
        this.delegate = delegate;
    }

    public PostDataToServer(Context context){
        this.context = context;
        this.dialog =  new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setMessage("Sending data to Server...");
        dialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];
        String jsonDATA = params[1];
        String jsonResponse = "";

        try {
            URL url = new URL(urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setDoOutput(true);
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setRequestProperty("Accept", "application/json");    //modify in future
            // send the response
            BufferedOutputStream out = new BufferedOutputStream(httpConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(jsonDATA);
            writer.flush();
            writer.close();
            out.close();

            //read the response
            BufferedInputStream in = new BufferedInputStream(httpConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine).append('\n');
            }
            jsonResponse = stringBuilder.toString();
            reader.close();
            in.close();
            httpConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }

        if (jsonResponse == null) {
            Log.e(TAG, "Couldn't post json to server");
        } else {
            Log.e(TAG, "Response from url: " + jsonResponse);
        }
        return jsonResponse;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        delegate.processPostFinish(result);
        dialog.dismiss();
    }

    public interface AsyncResponseSend {
        void processPostFinish(String output);
    }
}
