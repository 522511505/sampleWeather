package com.wrecker.sampleweather.tools;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.wrecker.sampleweather.R;

/**
 * Created by DELL on 2016/8/31.
 */
public class WeatherImageManager {
    public static Drawable getImgByWeather(Context context,String weather) {
        Drawable originLocation =null;
        String imgName = "00";
        if ("晴".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.qingtian);
        } else if ("多云".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.cloudy);
        } else if ("阴".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.hazy);
        } else if ("阵雨".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.middlerain);
        } else if ("雷阵雨".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.tstorms);
        } else if ("雷阵雨伴有冰雹".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.tstorms);
        } else if ("雨夹雪".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.middlerain);
        } else if ("小雨".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.rain);
        } else if ("中雨".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.middlerain);
        } else if ("大雨".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.bigrain);
        } else if ("暴雨".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.bigrain);
        } else if ("大暴雨".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.tstorms);
        } else if ("特大暴雨".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.bigbigrain);
        } else if ("阵雪".equals(weather)) {
            imgName = "13";
        } else if ("小雪".equals(weather)) {
            imgName = "14";
        } else if ("中雪".equals(weather)) {
            imgName = "15";
        } else if ("大雪".equals(weather)) {
            imgName = "16";
        } else if ("暴雪".equals(weather)) {
            imgName = "17";
        } else if ("雾".equals(weather)) {
            imgName = "18";
        } else if ("冻雨".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.rain);
        } else if ("沙尘暴".equals(weather)) {
            imgName = "20";
        } else if ("小到中雨".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.middlerain);
        } else if ("中到大雨".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.bigrain);
        } else if ("大到暴雨".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.bigrain);
        } else if ("暴雨到大暴雨".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.bigbigrain);
        } else if ("大暴雨到特大暴雨".equals(weather)) {
            originLocation = context.getResources().getDrawable(R.drawable.bigbigrain);
        } else if ("小到中雪".equals(weather)) {
            imgName = "26";
        } else if ("中到大雪".equals(weather)) {
            imgName = "27";
        } else if ("大到暴雪".equals(weather)) {
            imgName = "28";
        } else if ("浮尘".equals(weather)) {
            imgName = "29";
        } else if ("扬沙".equals(weather)) {
            imgName = "30";
        } else if ("强沙尘暴".equals(weather)) {
            imgName = "31";
        } else if ("霾".equals(weather)) {
            imgName = "53";
        }
        return originLocation;
    }

    //修改图片颜色
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;

    }
}
