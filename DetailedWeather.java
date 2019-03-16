package com.example.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class DetailedWeather extends AppCompatActivity {

    TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_weather);
        details = findViewById(R.id.details);
        details.setMovementMethod(new ScrollingMovementMethod());
        Bundle extras = getIntent().getExtras();
        Temperature t = (Temperature) extras.getSerializable("details");
        details.append("Max Temperature: "+(t.temp_max-273)+"Celcius\n");
        details.append("Min Temperature: "+(t.temp_min-273)+"Celcius\n");
        details.append("Pressure: "+t.pressure+"hPa\n");
        details.append("Humidity: "+t.humidity+"hPa\n");
        details.append("Ground Level "+t.grnd_level+"hPa\n");
        details.append("Sea Level: "+t.sea_level+"hPa\n");
        details.append("Average Temperature: "+(t.average_temp-273)+"Celcius\n");

    }
}
