package com.wrecker.sampleweather.net;

import com.wrecker.sampleweather.entity.WeatherEntity;
import com.wrecker.sampleweather.tools.Constances;
import com.wrecker.sampleweather.tools.ConvertManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.*;

/**
 * Created by xiaoxin on 2016/8/4.
 */
public class NetConnecttion {
    /**
     * @param :请求接口
     * @param httpArg :参数
     * @return 返回结果
     */
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", Constances.APIKEY);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解析一天的天气信息
     *
     * @param jsonStr
     * @return WeatherEntity
     */
    public static WeatherEntity decodeJSON(String jsonStr) {
        jsonStr = ConvertManager.decodeUnicode(jsonStr);
        JSONObject jsonObj = null;
        String errMsg = "";
        int errNum = 0;
        String curTemp = "";
        String date = "";
        String type = "";
        String lowTemp = "";
        String highTemp = "";
        int aqi = 0;
        String fengli = "";
        String fengxiang = "";
        String city = "";
        try {
            jsonObj = new JSONObject(jsonStr);
            errMsg = jsonObj.getString("errMsg");
            errNum = jsonObj.getInt("errNum");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (-1 == errNum) {
            return null;
        }

        if (errMsg.equals("success")) {
            try {
                JSONObject jsonRetData = jsonObj.getJSONObject("retData");
                //保存城市编码
                Constances.setCityCode(jsonRetData.getString("citycode"));
                curTemp = jsonRetData.getString("temp");
                date = jsonRetData.getString("date");
                type = jsonRetData.getString("weather");
                lowTemp = jsonRetData.getString("l_tmp");
                highTemp = jsonRetData.getString("h_tmp");
                city = jsonRetData.getString("city");
                aqi = 0;
                fengli = jsonRetData.getString("WS");
                fengxiang = jsonRetData.getString("WD");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        WeatherEntity weatherEntity = new WeatherEntity.Builder().curTemp(curTemp).date(date).type(type).city(city).fengli(fengli).fengxiang(fengxiang).lowtemp(lowTemp).hightemp(highTemp).aqi(aqi).build();
        return weatherEntity;
    }

    /**
     * 解析一天的天气信息
     *
     * @param jsonStr
     * @return WeatherEntity
     */
    public static Map decodeJSONToMap(String jsonStr) {
        jsonStr = ConvertManager.decodeUnicode(jsonStr);
        JSONObject jsonObj = null;
        String errMsg = "";
        int errNum = 0;
        String curTemp = "";
        String date = "";
        String type = "";
        String lowTemp = "";
        String highTemp = "";
        int aqi = 0;
        String fengli = "";
        String fengxiang = "";
        String city = "";
        try {
            jsonObj = new JSONObject(jsonStr);
            errMsg = jsonObj.getString("errMsg");
            errNum = jsonObj.getInt("errNum");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (-1 == errNum) {
            return null;
        }

        if (errMsg.equals("success")) {
            try {
                JSONObject jsonRetData = jsonObj.getJSONObject("retData");
                //保存城市编码
                Constances.setCityCode(jsonRetData.getString("citycode"));
                curTemp = jsonRetData.getString("temp");
                date = jsonRetData.getString("date");
                type = jsonRetData.getString("weather");
                lowTemp = jsonRetData.getString("l_tmp");
                highTemp = jsonRetData.getString("h_tmp");
                city = jsonRetData.getString("city");
                aqi = 0;
                fengli = jsonRetData.getString("WS");
                fengxiang = jsonRetData.getString("WD");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Map map = new HashMap();
        map.put("curTemp",curTemp);
        map.put("city",city);
        map.put("fengli",fengli);
        map.put("fengxiang",fengxiang);
        map.put("lowTemp",lowTemp);
        map.put("highTemp",highTemp);
        map.put("type",type);
        map.put("date",date);
        return map;
    }

    /**
     * 解析7天的天气信息
     *
     * @param jsonStr
     * @return list, map泛型应该为WeatherEntity比较准确，不改了
     */
    public static List<Map<String, String>> decodeForecastJSON(String jsonStr) {
        jsonStr = ConvertManager.decodeUnicode(jsonStr);
        JSONObject jsonObj = null;
        JSONObject today;
        JSONArray forecast;
        JSONArray history;
        Map map;

        String errMsg = "";
        List forecastList = new ArrayList();
        int errNum = 0;
        try {
            jsonObj = new JSONObject(jsonStr);
            errMsg = jsonObj.getString("errMsg");
            errNum = jsonObj.getInt("errNum");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (-1 == errNum) {
            return null;
        }

        if (errMsg.equals("success")) {
            try {
                JSONObject jsonRetData = jsonObj.getJSONObject("retData");

                //昨天天气数据
                history = jsonRetData.getJSONArray("history");
                JSONObject yesterday = history.getJSONObject(6);
                map = new HashMap();
                map.put("date", yesterday.getString("date"));
                map.put("type", yesterday.getString("type"));
                map.put("week", yesterday.getString("week"));
                map.put("lowtemp", yesterday.getString("lowtemp"));
                map.put("hightemp", yesterday.getString("hightemp"));
                map.put("fengli", yesterday.getString("fengli"));
                forecastList.add(map);
                //今天天气数据
                today = jsonRetData.getJSONObject("today");
                map = new HashMap();
                map.put("date", today.getString("date"));
                map.put("type", today.getString("type"));
                map.put("week", today.getString("week"));
                map.put("lowtemp", today.getString("lowtemp"));
                map.put("hightemp", today.getString("hightemp"));
                map.put("fengli", today.getString("fengli"));
                forecastList.add(map);
                //未来四天数据
                forecast = jsonRetData.getJSONArray("forecast");
                for (int i = 0; i < forecast.length(); i++) {
                    JSONObject oneDayWeather = forecast.getJSONObject(i);
                    map = new HashMap();
                    map.put("date", oneDayWeather.getString("date"));
                    map.put("week", oneDayWeather.getString("week"));
                    map.put("type", oneDayWeather.getString("type"));
                    map.put("lowtemp", oneDayWeather.getString("lowtemp"));
                    map.put("hightemp", oneDayWeather.getString("hightemp"));
                    map.put("fengli", oneDayWeather.getString("fengli"));
                    forecastList.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return forecastList;
    }

    /**
     * 解析一天的天气信息（因为另外一个api查询不到aqi这个参数）
     *
     * @param jsonStr
     * @return
     */
    public static Map<String, String> decodeForecastTodayJSON(String jsonStr) {
        jsonStr = ConvertManager.decodeUnicode(jsonStr);
        JSONObject jsonObj = null;
        JSONObject today;
        JSONArray index;
        Map map = null;

        String errMsg = "";
        int errNum = 0;
        try {
            jsonObj = new JSONObject(jsonStr);
            errMsg = jsonObj.getString("errMsg");
            errNum = jsonObj.getInt("errNum");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (-1 == errNum) {
            return null;
        }

        if (errMsg.equals("success")) {
            try {
                JSONObject jsonRetData = jsonObj.getJSONObject("retData");
                today = jsonRetData.getJSONObject("today");
                map = new HashMap();
                map.put("date", today.getString("date"));
                map.put("type", today.getString("type"));
                map.put("lowtemp", today.getString("lowtemp"));
                map.put("hightemp", today.getString("hightemp"));
                map.put("aqi", today.getString("aqi"));
                map.put("fengli", today.getString("fengli"));
                map.put("fengxiang", today.getString("fengxiang"));
                index = today.getJSONArray("index");
                for (int i = 0; i < index.length(); i++) {
                    JSONObject details = index.getJSONObject(i);
                    String strDetails = details.getString("details");
                    map.put("detail" + i, strDetails);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 解析可用的城市
     *
     * @param jsonStr
     * @return
     */
    public static List<Map<String, String>> decodeCityListJSON(String jsonStr) {
        List cityNameList = null;
        jsonStr = ConvertManager.decodeUnicode(jsonStr);
        JSONObject jsonObj = null;
        String errMsg = "";
        Map map = null;
        int errNum = 0;
        try {
            jsonObj = new JSONObject(jsonStr);
            errMsg = jsonObj.getString("errMsg");
            errNum = jsonObj.getInt("errNum");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (-1 == errNum) {
            return null;
        }

        if (errMsg.equals("success")) {
            cityNameList = new ArrayList();
            try {
                JSONArray jsonRetData = jsonObj.getJSONArray("retData");
                for (int i = 0; i < jsonRetData.length(); i++) {
                    JSONObject citys = jsonRetData.getJSONObject(i);
                    String province_cn = citys.getString("province_cn");
                    String district_cn = citys.getString("district_cn");
                    String name_cn = citys.getString("name_cn");
                    String area_id = citys.getString("area_id");
                    map = new HashMap();
                    map.put("province_cn", province_cn);
                    map.put("district_cn", district_cn);
                    map.put("name_cn", name_cn);
                    map.put("area_id", area_id);
                    cityNameList.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return cityNameList;
    }
}
