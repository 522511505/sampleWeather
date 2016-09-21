package com.wrecker.sampleweather.model;

import com.wrecker.sampleweather.io.Cache;
import com.wrecker.sampleweather.listener.DataInterface;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by DELL on 2016/9/21.
 */
public class WeatherModelImpl implements WeatherModel{
    //缓存地址
    private String fileName = "/sdcard/weatherInfo.txt";

    @Override
    public void saveWeather(List list,DataInterface dataInterface) {
        Cache.writeToSDCard(list,fileName);
        dataInterface.onComplete(null);
    }

    @Override
    public void loadWeatherFromCache(List list,DataInterface dataInterface) {
        list = Cache.getSDcardForacastInfo(fileName);
        dataInterface.onComplete(list);
    }
}
