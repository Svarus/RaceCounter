package com.example.win.racecounter.api;

import android.view.View;
import android.widget.TextView;

import com.example.win.racecounter.R;

public class WebClient implements GetDataFromServer.AsyncResponseReceive, PostDataToServer.AsyncResponseSend {
    private static final String BASE_URL = "http://178.74.221.98:8181/RaceServer/api/";
    private static final String GET_URL = "read";
    private static final String POST_URL = "raceService";
    private static String data2Send = "empty data";

    public void setData2Send(String data2Send) {
        WebClient.data2Send = data2Send;
    }

    private View view;

    public WebClient(View view){
        this.view = view;
    }

    public void get() {
        GetDataFromServer getDataFromServer = new GetDataFromServer(view.getContext());
        //this to set delegate/listener back to this class
        getDataFromServer.setDelegate(this);
        getDataFromServer.execute(getAbsoluteUrl(GET_URL));
    }
    //receive data here:
    @Override
    public void processGetFinish(String output) {
        //Receive the result fired from async class of onPostExecute(result) method
        /*TextView tvResult = (TextView) view.findViewById(R.id.tvResult);
        tvResult.setText(output);*/
    }

    public void post() {
        PostDataToServer PostDataToServer = new PostDataToServer(view.getContext());
        PostDataToServer.setDelegate(this);
        PostDataToServer.execute(getAbsoluteUrl(POST_URL), data2Send);
        //{"Hello, ":" Oliasia!"}
    }
    //receive data here:
    @Override
    public void processPostFinish(String output) {
        //Receive the result fired from async class of onPostExecute(result) method
        /*TextView tvResult2 = (TextView) view.findViewById(R.id.tvResult2);
        tvResult2.setText(output);*/
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
