package com.wrecker.sampleweather.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wrecker.sampleweather.activity.MainActivity;
import com.wrecker.sampleweather.R;
import com.wrecker.sampleweather.net.NetConnecttion;
import com.wrecker.sampleweather.tools.Constances;
import com.wrecker.sampleweather.tools.ConvertManager;
import com.wrecker.sampleweather.tools.WeatherImageManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import lecho.lib.hellocharts.formatter.LineChartValueFormatter;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


/**
 * Created by xiaoxin on 2016/8/3.
 */
public class WeatherForecastFrg extends Fragment {
    //当前fragment视图
    View view;

    private TextView tvLocation;
    private TextView tvCurTime;

    private TextView date0;
    private TextView tvWeatherForecast0;
    private TextView tvTemp0;
    private TextView date1;
    private TextView tvWeatherForecast1;
    private TextView tvTemp1;
    private TextView date2;
    private TextView tvWeatherForecast2;
    private TextView tvTemp2;
    private TextView date3;
    private TextView tvWeatherForecast3;
    private TextView tvTemp3;
    private TextView date4;
    private TextView tvWeatherForecast4;
    private TextView tvTemp4;
    private TextView date5;
    private TextView tvWeatherForecast5;
    private TextView tvTemp5;
    private TextView week0;
    private TextView week1;
    private TextView week2;
    private TextView week3;
    private TextView week4;
    private TextView week5;
    private ImageView imgType0;
    private ImageView imgType1;
    private ImageView imgType2;
    private ImageView imgType3;
    private ImageView imgType4;
    private ImageView imgType5;
    private ImageView icon_location;

    private ImageView img_left_arrow;

    private LineChartView tempChart;

    // 用于控制drawer
    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;

    List<Map<String, String>> forecastList = new ArrayList();

    //网络获取进度
    private ProgressDialog progressDialog = null;

    //当前fragment标记号
    public static final String ARG_SECTION_NUMBER = "2";

    //保存天气预测的信息地址
    private String fileName = "/sdcard/forcastInfo.txt";

    private Thread getforecastThread = new Thread(new Runnable() {
        @Override
        public void run() {
            String requestCityAndCityCode = Constances.getRequestCityAndCityCode();
            //向服务器请求数据
            String response = NetConnecttion.request(Constances.weatherForecastHttpUrl, requestCityAndCityCode);
            forecastList = NetConnecttion.decodeForecastJSON(response);

            writeToSDCard(forecastList);

            handler.sendEmptyMessage(0);
        }
    });

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setText();
            progressDialog.dismiss();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);
        }

        initView(view);

        boolean ifSaved =  getSDcardForacastInfo();

        if(true == ifSaved){
            setText();
        }else{
            progressDialog = ProgressDialog.show(getActivity(), "请稍等...", "获取数据中...", true);

            getforecastThread.start();
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public void initView(View view) {
//        super.onViewCreated(view, savedInstanceState);
        mFragmentContainerView = getActivity().findViewById(R.id.navigation_drawer);
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        img_left_arrow = (ImageView)view.findViewById(R.id.img_left_arrow);
        final Drawable originBack = getContext().getResources().getDrawable(R.drawable.left_arrow);
        img_left_arrow.setImageDrawable(WeatherImageManager.tintDrawable(originBack, ColorStateList.valueOf(Color.WHITE)));
        img_left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mFragmentContainerView);
            }
        });

        icon_location = (ImageView)view.findViewById(R.id.icon_location);
        final Drawable originLocation = getContext().getResources().getDrawable(R.drawable.location);
        icon_location.setImageDrawable(WeatherImageManager.tintDrawable(originLocation, ColorStateList.valueOf(Color.WHITE)));

        tvLocation = (TextView) view.findViewById(R.id.tv_location);
        tvCurTime = (TextView) view.findViewById(R.id.tv_curTime);
        date0 = (TextView) view.findViewById(R.id.date0);
        tvWeatherForecast0 = (TextView) view.findViewById(R.id.tv_weather_forecast0);
        tvTemp0 = (TextView) view.findViewById(R.id.tv_temp0);
        date1 = (TextView) view.findViewById(R.id.date1);
        tvWeatherForecast1 = (TextView) view.findViewById(R.id.tv_weather_forecast1);
        tvTemp1 = (TextView) view.findViewById(R.id.tv_temp1);
        date2 = (TextView) view.findViewById(R.id.date2);
        tvWeatherForecast2 = (TextView) view.findViewById(R.id.tv_weather_forecast2);
        tvTemp2 = (TextView) view.findViewById(R.id.tv_temp2);
        date3 = (TextView) view.findViewById(R.id.date3);
        tvWeatherForecast3 = (TextView) view.findViewById(R.id.tv_weather_forecast3);
        tvTemp3 = (TextView) view.findViewById(R.id.tv_temp3);
        date4 = (TextView) view.findViewById(R.id.date4);
        tvWeatherForecast4 = (TextView) view.findViewById(R.id.tv_weather_forecast4);
        tvTemp4 = (TextView) view.findViewById(R.id.tv_temp4);
        date5 = (TextView) view.findViewById(R.id.date5);
        tvWeatherForecast5 = (TextView) view.findViewById(R.id.tv_weather_forecast5);
        tvTemp5 = (TextView) view.findViewById(R.id.tv_temp5);
        week0 = (TextView) view.findViewById(R.id.week0);
        week1 = (TextView) view.findViewById(R.id.week1);
        week2 = (TextView) view.findViewById(R.id.week2);
        week3 = (TextView) view.findViewById(R.id.week3);
        week4 = (TextView) view.findViewById(R.id.week4);
        week5 = (TextView) view.findViewById(R.id.week5);
        imgType0 = (ImageView) view.findViewById(R.id.img_type0);
        imgType1 = (ImageView) view.findViewById(R.id.img_type1);
        imgType2 = (ImageView) view.findViewById(R.id.img_type2);
        imgType3 = (ImageView) view.findViewById(R.id.img_type3);
        imgType4 = (ImageView) view.findViewById(R.id.img_type4);
        imgType5 = (ImageView) view.findViewById(R.id.img_type5);

        tempChart = (LineChartView) view.findViewById(R.id.highTemp_char);
        //设置为不可缩放
        tempChart.setInteractive(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        writeToSDCard(null);
    }

    private void setText() {
        tvLocation.setText(Constances.getCity()[0]);
        setSingleText(0, tvWeatherForecast0, tvTemp0, date0, week0, imgType0);
        setSingleText(1, tvWeatherForecast1, tvTemp1, date1, week1, imgType1);
        setSingleText(2, tvWeatherForecast2, tvTemp2, date2, week2, imgType2);
        setSingleText(3, tvWeatherForecast3, tvTemp3, date3, week3, imgType3);
        setSingleText(4, tvWeatherForecast4, tvTemp4, date4, week4, imgType4);
        setSingleText(5, tvWeatherForecast5, tvTemp5, date5, week5, imgType5);

        List<PointValue> highValues = new ArrayList();
        List<PointValue> lowValues = new ArrayList();
        for (int i = 0; i < forecastList.size(); i++) {
            Map<String, String> map = (HashMap) forecastList.get(i);
            int highTemp = cutTempString(map.get("hightemp"));
            int lowTemp = cutTempString(map.get("lowtemp"));
            highValues.add(new PointValue(i, highTemp));
            lowValues.add(new PointValue(i, lowTemp));
        }

        //In most cased you can call data model methods in builder-pattern-like manner.
        Line highLine = new Line(highValues).setColor(Color.RED).setCubic(false).setPointRadius(4).setHasLabels(true).setStrokeWidth(2);
        Line lowLine = new Line(lowValues).setColor(Color.WHITE).setCubic(false).setPointRadius(4).setHasLabels(true).setStrokeWidth(2);

        List<Line> lines = new ArrayList();
        lines.add(highLine);
        lines.add(lowLine);

        LineChartData data = new LineChartData();
        //设置坐标点旁边的文字背景
        data.setValueLabelBackgroundColor(Color.TRANSPARENT);
        data.setValueLabelBackgroundEnabled(false);
        data.setValueLabelTextSize(15);
        data.setLines(lines);
        tempChart.setLineChartData(data);
    }

    private void setSingleText(int position, TextView type, TextView temp, TextView date, TextView week, ImageView imgType) {
        Map<String, String> map = (HashMap) forecastList.get(position);
        temp.setText(getFengliForm(map.get("fengli")));
        type.setText(map.get("type"));
        week.setText(convertWeekForm(map.get("week")));
        date.setText(ConvertManager.subDateForm(map.get("date"), 5));
        if (0 == position) {
            week.setText("昨天");
        } else if (1 == position) {
            tvCurTime.setText(ConvertManager.subDateForm(map.get("date"), 5));
            week.setText("今天");
        }
        Drawable drawable = WeatherImageManager.getImgByWeather(getContext(), (String) map.get("type"));
        imgType.setImageDrawable(drawable);
    }

    private String convertWeekForm(String week) {
        if ("星期天".equals(week)) {
            return "周日";
        }
        return "周" + week.substring(2);
    }

    private int cutTempString(String temp) {
        String str = temp.substring(0, temp.length() - 1);
        int intTemp = Integer.valueOf(str);
        return intTemp;
    }

    private String getFengliForm(String str) {
        if (str.contains("-")) {
            return str;
        } else {
            return str.substring(0, str.length() - 1);
        }
    }

    private void writeToSDCard(List list){
        try{

            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);

            fos.close();
            oos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean getSDcardForacastInfo(){

        FileInputStream fin = null;
        ObjectInputStream ois = null;
        try{
            fin = new FileInputStream(fileName);
            ois = new ObjectInputStream(fin);
            List obj = (List)ois.readObject();

            if(null != obj){
                forecastList = obj;
                return true;
            }

            fin.close();
            ois.close();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                fin.close();
                ois.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
}
