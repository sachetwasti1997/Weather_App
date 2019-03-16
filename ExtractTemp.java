package com.example.weather;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.util.MalformedJsonException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExtractTemp extends AsyncTask<String, Void, Temperature> {
    private static final String TAG = "ExtractTemp";

    interface TemperatureData{
        void onTemperatureData(Temperature data);
    }

    private TemperatureData mCallback;

    public ExtractTemp(TemperatureData mCallback){
        this.mCallback = mCallback;
    }

    @Override
    protected void onPostExecute(Temperature s) {
        Log.d(TAG, "onPostExecute: data obtained is "+s.getAverage_temp());
        mCallback.onTemperatureData(s);
    }

    @Override
    protected Temperature doInBackground(String... strings) {
        try{
            JSONObject jsonObject = new JSONObject(strings[0]);
            JSONArray jsonArray = (JSONArray) jsonObject.get("list");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            JSONObject jsonObject2 =(JSONObject) jsonObject1.get("main");
            int temp_max = jsonObject2.getInt("temp_max");
            int temp_min = jsonObject2.getInt("temp_min");
            int humidity = jsonObject2.getInt("humidity");
            int grnd_level = jsonObject2.getInt("grnd_level");
            int sea_level = jsonObject2.getInt("sea_level");
            int pressure = jsonObject2.getInt("pressure");
            Temperature temperature = new Temperature(temp_max, temp_min, pressure, sea_level, grnd_level, humidity);
            return temperature;
        }catch (JSONException e){
            Log.e(TAG, "doInBackground: JSON is not OK"+e.getMessage());
        }
        return null;
    }
}
