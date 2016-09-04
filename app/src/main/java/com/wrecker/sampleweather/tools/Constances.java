package com.wrecker.sampleweather.tools;

import java.net.URLEncoder;

/**
 * Created by DELL on 2016/8/4.
 */
public class Constances {
    //天气listView的item数目，目前为1，若投放广告可拓展
    public static int LISTVIEWITEMNUMBER = 1;

    //城市名查询接口
    public static String cityNameHttpUrl = "http://apis.baidu.com/apistore/weatherservice/cityname";

    //7天天气预测接口
    public static String weatherForecastHttpUrl = "http://apis.baidu.com/apistore/weatherservice/recentweathers";

    //查询可用城市
    public static String cityListHttpUrl = "http://apis.baidu.com/apistore/weatherservice/citylist";
    //apiKEY
    public static String APIKEY = "ef15227bc5a1f6df78d70aa339a2d8c2";

    //当前城市
    private volatile static String city[] = {""};

    public synchronized static String[] getCity() {
        return city;
    }

    public synchronized static void setCity(String city) {
        Constances.city[0] = city;
    }

    public static String getRequestCity() {
        String curCity = getCity()[0];
        if ("" == curCity) {
            return null;
        }
        //判断城市名是否包含'市'
        if ("市".equals(curCity.substring(curCity.length() - 1))) {
            curCity = "cityname=" + ConvertManager.encode(curCity.substring(0, curCity.length() - 1));
        } else {
            curCity = "cityname=" + ConvertManager.encode(curCity);
        }
        return curCity;
    }

    //当前城市编码
    private volatile static String cityCode = "";

    public synchronized static String getCityCode() {
        return cityCode;
    }

    public synchronized static void setCityCode(String cityCode) {
        Constances.cityCode = cityCode;
    }

    public static String getRequestCityAndCityCode() {
        String curCity = getRequestCity();
        String curCityAndCityCode = getCityCode();
        if ("" == curCity || "" == curCityAndCityCode) {
            return null;
        }
        curCityAndCityCode = "&cityid=" + curCityAndCityCode;
        return curCity + curCityAndCityCode;
    }

    public static int windowHeight = 0;

    public static void setWindowHeight(int windowHeight) {
        if (0 == Constances.windowHeight) {
            Constances.windowHeight = windowHeight;
        }
    }

    public static int getWindowHeight() {
        return windowHeight;
    }
}
