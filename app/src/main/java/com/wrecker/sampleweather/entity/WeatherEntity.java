package com.wrecker.sampleweather.entity;

import java.io.Serializable;

/**
 * Created by DELL on 2016/8/3.
 */
public class WeatherEntity implements Serializable {
    //当前气温
    private final String curTemp;

    //城市
    private final String city;

    //风向
    private final String fengxiang;

    //风力
    private final String fengli;

    //天气状态
    private final String type;

    //最高温度,形式为30℃
    private final String hightemp;

    //最低温度
    private final String lowtemp;

    //日期,形式为2015-08-03
    private final String date;

    public String getCurTemp() {
        return curTemp;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public String getFengli() {
        return fengli;
    }

    public String getType() { return type;}

    public String getHightemp() {
        return hightemp;
    }

    public String getLowtemp() {
        return lowtemp;
    }

    public String getDate() {
        return date;
    }

    public String getWeek() {
        return week;
    }

    public int getAqi() {
        return aqi;
    }

    public String getCity(){return city;}

    //星期,形式为"星期一"
    private final String week;

    //pm值,形式为"92"
    private final int aqi;

    public static class Builder {
        private String curTemp = "";
        private String fengxiang = "";
        private String fengli = "";
        private String type = "";
        private String hightemp = "";
        private String lowtemp = "";
        private String date = "";
        private String week = "";
        private String city = "";
        private int aqi = 0;

        public Builder curTemp(String curTemp) {
            this.curTemp = curTemp;
            return this;
        }

        public Builder fengxiang(String fengxiang) {
            this.fengxiang = fengxiang;
            return this;
        }

        public Builder fengli(String fengli) {
            this.fengli = fengli;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder hightemp(String hightemp) {
            this.hightemp = hightemp;
            return this;
        }

        public Builder lowtemp(String lowtemp) {
            this.lowtemp = lowtemp;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder week(String week) {
            this.week = week;
            return this;
        }

        public Builder aqi(int aqi) {
            this.aqi = aqi;
            return this;
        }

        public WeatherEntity build() {
            return new WeatherEntity(this);
        }
    }

    private WeatherEntity(Builder builder) {
        curTemp = builder.curTemp;
        fengxiang = builder.fengxiang;
        fengli = builder.fengli;
        type = builder.type;
        hightemp = builder.hightemp;
        lowtemp = builder.lowtemp;
        date = builder.date;
        week = builder.week;
        aqi = builder.aqi;
        city = builder.city;
    }
}
