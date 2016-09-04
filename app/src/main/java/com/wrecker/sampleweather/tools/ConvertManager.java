package com.wrecker.sampleweather.tools;

import com.wrecker.sampleweather.entity.WeatherEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by xiaoxin on 2016/8/5.
 */
public class ConvertManager {
    private static final String UTF_8 = "utf-8";// 编码形式

    public static Map convertWeatherToMap(WeatherEntity weatherEntity) {
        Map map = new HashMap();
        map.put("city", weatherEntity.getCity());
//        map.put("aqi", weatherEntity.getAqi());
        map.put("curTemp", weatherEntity.getCurTemp());
        map.put("fengli", weatherEntity.getFengli().toString());
        map.put("fengxiang", weatherEntity.getFengxiang());
        map.put("lowTemp", weatherEntity.getLowtemp());
        map.put("highTemp", weatherEntity.getHightemp());
        map.put("type", weatherEntity.getType());
        map.put("date", ConvertManager.subDateForm(weatherEntity.getDate(),3));
        return map;
    }

    //解析unicode编码
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    /**
     * 对文字进行UTF8转码
     *
     * @param str
     * @return
     */
    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, UTF_8);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 对日期改为*-*格式
     *
     * @param date
     * @param index
     * @return
     */
    public static String subDateForm(String date,int index) {
        String strDate = date;
        char firstOfMonth = date.charAt(index);
        if ('0' == firstOfMonth) {
            strDate = date.substring(index + 1);
        }
        char firstOfDay = date.charAt(index + 3);
        if('0' == firstOfDay){
            strDate = strDate.substring(0,strDate.length()-2)+strDate.substring(strDate.length()-1);
        }
        return strDate;
    }
}
