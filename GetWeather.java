package com.example.weather;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetWeather extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetWeather";

    interface OnDownloadData{
        void onDownload(String data);
    }

    private OnDownloadData mCallback;
    private String mBaseURL;
    private String appid;

    public GetWeather(OnDownloadData mCallback, String mBaseURL, String appid){
        this.mCallback = mCallback;
        this.mBaseURL = mBaseURL;
        this.appid = appid;
    }


    private String createURI(String id){
        return Uri.parse(mBaseURL).buildUpon()
                .appendQueryParameter("id", id)
                .appendQueryParameter("appid", appid)
                .build().toString();
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: the json obtained is "+s);
        mCallback.onDownload(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader=null;
        try{
            String destinationURI = createURI(strings[0]);
            URL url = new URL(destinationURI);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responce = connection.getResponseCode();
            Log.d(TAG, "doInBackground: The responce code from the net "+responce);
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String s, result = "";
            if (null != (s=reader.readLine())){
                result = s;
            }
            return result;
        }catch (MalformedURLException e){
            Log.e(TAG, "doInBackground: the URL isnt OK "+e.getMessage());
        }catch (IOException e){
            Log.e(TAG, "doInBackground: IOException while reading from URL "+e.getMessage());
        }
        return null;
    }
}
