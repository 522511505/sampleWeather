package com.wrecker.sampleweather.present;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.wrecker.sampleweather.listener.DataInterface;
import com.wrecker.sampleweather.model.WeatherModel;
import com.wrecker.sampleweather.model.WeatherModelImpl;
import com.wrecker.sampleweather.net.NetConnecttion;
import com.wrecker.sampleweather.tools.Constances;
import com.wrecker.sampleweather.view.CurrentInterface;

import java.util.*;

/**
 * Created by xiaoxin on 2016/9/21.
 */
public class CurrentWeatherPresent extends BasePresent {
    CurrentInterface currentInterface;

    WeatherModel weatherModel = new WeatherModelImpl();

    //保存天气信息
    private List currentWeatherList;

    private Handler mhandler = new Handler();

    //获取天气信息的线程
    private Thread getInfoThread = new Thread(new Runnable() {
        @Override
        public void run() {
            String curCity = "";
            synchronized (Constances.getCity()) {
                while ("" == Constances.getCity()[0]) {
                    try {
                        Constances.getCity().wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                curCity = Constances.getRequestCity();
            }

            //向服务器请求数据
            String response = NetConnecttion.request(Constances.cityNameHttpUrl, curCity);
            Map map = NetConnecttion.decodeJSONToMap(response);

            if (null != map) {
                //由于Constances.cityNameHttpUrl这个API取不到aqi这个参数，需要从Constances.weatherForecastHttpUrl这个API取。
                String requestCityAndCityCode = Constances.getRequestCityAndCityCode();
                String response2 = NetConnecttion.request(Constances.weatherForecastHttpUrl, requestCityAndCityCode);
                Map todayInfo = NetConnecttion.decodeForecastTodayJSON(response2);
                map.put("aqi", todayInfo.get("aqi"));
                for (int i = 0; i < 5; i++) {
                    map.put("detail" + i, todayInfo.get("detail" + i));
                }
                currentWeatherList = new ArrayList();
                currentWeatherList.add(map);

                writeToSDCard(currentWeatherList);

                currentInterface.setListViewAdapter(currentWeatherList);
            } else {
                Toast.makeText((Activity) currentInterface, "服务器出错", Toast.LENGTH_SHORT).show();
                ((Activity) currentInterface).finish();
                Log.e("请求出错", "error");
            }
            //更新完列表数据，则关闭对话框
            currentInterface.hideProgressDialog();
        }
    });

    public CurrentWeatherPresent(CurrentInterface currentInterface) {
        this.currentInterface = currentInterface;
    }

    public void getWeatherInfo() {
        //获取消息
        getSDcardWeatherInfo();
        if (null != currentWeatherList) {
            currentInterface.setListViewAdapter(currentWeatherList);
        } else {
            currentInterface.showProgressDialog();
            getInfoThread.start();
        }
    }

    //将天气信息写到内存
    private void writeToSDCard(List list) {
        weatherModel.saveWeather(list, new DataInterface() {
            @Override
            public void onComplete(List list) {

            }
        });
    }

    //读取内存信息
    private void getSDcardWeatherInfo() {
        weatherModel.loadWeatherFromCache(currentWeatherList, new DataInterface() {
            @Override
            public void onComplete(List list) {
                CurrentWeatherPresent.this.currentWeatherList = list;
            }
        });
    }
}
