package com.example.weather;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ReadJson extends AsyncTask<Context, Void, Map<String, Integer>> {
    private static final String TAG = "ReadJson";

    interface CitiesID{
        void onParseCitiesID(Map<String, Integer> map);
    }

    private CitiesID mCallback;

    public ReadJson(CitiesID mCallback){
        this.mCallback = mCallback;
    }

    @Override
    protected void onPostExecute(Map<String, Integer> s) {
//        Log.d(TAG, "onPostExecute: data obtained "+s);
        if (mCallback != null){
            mCallback.onParseCitiesID(s);
        }
    }

    @Override
    protected Map<String, Integer> doInBackground(Context... contexts) {
        try{
            Map<String, Integer> cities = new HashMap<>();
            InputStream context = contexts[0].getResources().openRawResource(R.raw.cities);
            BufferedReader reader = new BufferedReader(new InputStreamReader(context));
            String line = null;
            StringBuilder result = new StringBuilder();
            while (null != (line = reader.readLine())){
                result.append(line).append("\n");
            }
            String data = result.toString();
            JSONArray jsonArray = new JSONArray(data);
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name").toUpperCase();
                int id = jsonObject.getInt("id");
                if (!cities.containsKey(name)) cities.put(name, id);
            }
            return cities;
        }catch (FileNotFoundException e){
            Log.e(TAG, "doInBackground: File not Found Error"+e.getMessage());
        }catch (IOException e){
            Log.e(TAG, "doInBackground: Error while reading the file"+e.getMessage());
        }catch (JSONException e){
            Log.e(TAG, "doInBackground: Error while reading json"+e.getMessage());
        }
        return null;
    }
}
