package com.example.weather;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements GetWeather.OnDownloadData, ReadJson.CitiesID, View.OnClickListener, ExtractTemp.TemperatureData {

    private Map<String, Integer> map = null;
    private static final String TAG = "MainActivity";
    private TextView temp;
    private Button getAverageT;
    private EditText city;
    private Temperature temperature;
    private Button detailedweather;

    GetWeather getWeather;
    @Override
    protected void onResume() {
        if (map == null){
            ReadJson readJson = new ReadJson(this);
            readJson.execute(this);
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city = findViewById(R.id.city);
        getAverageT = findViewById(R.id.getAverageT);
        temp = findViewById(R.id.temp);
        getAverageT.setOnClickListener(this);
        detailedweather = findViewById(R.id.detailedweather);
        detailedweather.setOnClickListener(this);
    }

    @Override
    public void onDownload(String data) {
        Log.d(TAG, "onDownload: Json data Obtained is "+data);
        ExtractTemp extractTemp= new ExtractTemp(this);
        extractTemp.execute(data);
    }

    @Override
    public void onParseCitiesID(Map<String, Integer> map) {
        this.map = map;
        Log.d(TAG, "onParseCitiesID: "+map);
    }


    @Override
    public void onClick(View v) {
        if (v == getAverageT) {
            String t = city.getText().toString().toUpperCase();
            if (t.equals("")) {
                Toast.makeText(this, "Enter City Name First", Toast.LENGTH_SHORT).show();
            } else if (!map.containsKey(t)) {
                Toast.makeText(this, "There is no such city", Toast.LENGTH_SHORT).show();
            } else {
                getWeather = new GetWeather(this, "http://api.openweathermap.org/data/2.5/forecast", "f2f900b7135c02a0e9972177d05557df");
                getWeather.execute(map.get(t).toString());
            }
        }
        else{
            Intent intent = new Intent(this, DetailedWeather.class);
            intent.putExtra("details",temperature);
            startActivity(intent);
        }
    }

    @Override
    public void onTemperatureData(Temperature data) {
        Log.d(TAG, "onTemperatureData: "+data.getAverage_temp());
        temperature = data;
        temp.setText("");
        temp.setText(data.getAverage_temp()+"");
    }
}
