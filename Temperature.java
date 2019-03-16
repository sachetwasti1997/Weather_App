package com.example.weather;

import java.io.Serializable;

public class Temperature implements Serializable {
    int temp_max;
    int temp_min;
    float average_temp;
    int pressure;
    int sea_level;
    int grnd_level;
    int humidity;

    public Temperature(int temp_max, int temp_min, int pressure, int sea_level, int grnd_level, int humidity) {
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.average_temp = (float)(temp_max+temp_min)/2;
        this.pressure = pressure;
        this.sea_level = sea_level;
        this.grnd_level = grnd_level;
        this.humidity = humidity;
    }

    public int getTemp_max() {
        return temp_max;
    }

    public int getTemp_min() {
        return temp_min;
    }

    public float getAverage_temp() {
        return average_temp;
    }
}
