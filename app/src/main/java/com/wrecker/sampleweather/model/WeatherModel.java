package com.wrecker.sampleweather.model;

import com.wrecker.sampleweather.listener.DataInterface;

import java.util.List;

/**
 * Created by xiaoxin on 2016/9/21.
 */
public interface WeatherModel {
    public void saveWeather(List list,DataInterface dataInterface);

    public void loadWeatherFromCache(List list,DataInterface dataInterface);
}
