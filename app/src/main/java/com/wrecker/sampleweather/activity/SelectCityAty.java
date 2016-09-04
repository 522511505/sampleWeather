package com.wrecker.sampleweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wrecker.sampleweather.R;
import com.wrecker.sampleweather.entity.WeatherEntity;
import com.wrecker.sampleweather.fragment.CurrentWeatherFrg;
import com.wrecker.sampleweather.net.NetConnecttion;
import com.wrecker.sampleweather.tools.Constances;
import com.wrecker.sampleweather.tools.ConvertManager;
import com.wrecker.sampleweather.tools.WeatherImageManager;
import com.wrecker.sampleweather.widget.SearchView;

import java.util.*;
import java.util.Map;

/**
 * Created by xiaoxin on 2016/8/23.
 */

public class SelectCityAty extends Activity {
    private EditText edSearch;
    private SearchView btSearch;

    private ListView lvSearch;

    private ImageView imgClear;
    private ImageView imgLeftArrow;

    private TextView tvHotCity0;
    private TextView tvHotCity1;
    private TextView tvHotCity2;
    private TextView tvHotCity3;
    private TextView tvHotCity4;
    private TextView tvHotCity5;
    private TextView tvHotCity6;
    private TextView tvHotCity7;
    private TextView tvHotCity8;
    private TextView tvHotCity9;
    private TextView tvHotCity10;
    private TextView tvHotCity11;
    private TextView tvHotCity12;
    private TextView tvHotCity13;
    private TextView tvHotCity14;
    private TextView tvHotCity15;
    private TextView tvHotCity16;
    private TextView tvHotCity17;
    private TextView tvHotCity18;
    private TextView tvHotCity19;
    private TextView tvHotCity20;
    private TextView tvHotCity21;
    private TextView tvHotCity22;
    private TextView tvHotCity23;
    private TextView tvHotCity24;
    private TextView tvHotCity25;
    private TextView tvHotCity26;
    private TextView tvHotCity27;
    private TextView tvHotCity28;
    private TextView tvHotCity29;
    private TextView tvHotCity30;
    private TextView tvHotCity31;

    private ArrayList cityList;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            cityList = (ArrayList) msg.obj;
            if (null == cityList) {
                Toast.makeText(SelectCityAty.this, "抱歉，暂不支持该地区", Toast.LENGTH_LONG).show();
                edSearch.setText("");
            } else {
                String[] strs = new String[cityList.size()];
                for (int i = 0; i < cityList.size(); i++) {
                    Map map = (HashMap) cityList.get(i);
                    strs[i] = (String) map.get("province_cn") + " , " + map.get("name_cn");
                }
                lvSearch.setAdapter(new ArrayAdapter<String>(SelectCityAty.this,
                        android.R.layout.simple_list_item_1, strs));
                lvSearch.setVisibility(View.VISIBLE);
            }
            btSearch.stopSearch();
            imgClear.setVisibility(View.INVISIBLE);
            btSearch.setClickable(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        initView();

        addListener();
    }

    private void initView() {
        edSearch = (EditText) findViewById(R.id.ed_search);
        btSearch = (SearchView) findViewById(R.id.bt_search);

        lvSearch = (ListView) findViewById(R.id.lv_search);

        imgClear = (ImageView) findViewById(R.id.img_clear);
        imgLeftArrow = (ImageView) findViewById(R.id.img_left_arrow);
        imgLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Drawable originImg = getResources().getDrawable(R.drawable.left_arrow);
        imgLeftArrow.setImageDrawable(WeatherImageManager.tintDrawable(originImg, ColorStateList.valueOf(Color.WHITE)));


        tvHotCity0 = (TextView) findViewById(R.id.tv_hot_city0);
        tvHotCity1 = (TextView) findViewById(R.id.tv_hot_city1);
        tvHotCity2 = (TextView) findViewById(R.id.tv_hot_city2);
        tvHotCity3 = (TextView) findViewById(R.id.tv_hot_city3);
        tvHotCity4 = (TextView) findViewById(R.id.tv_hot_city4);
        tvHotCity5 = (TextView) findViewById(R.id.tv_hot_city5);
        tvHotCity6 = (TextView) findViewById(R.id.tv_hot_city6);
        tvHotCity7 = (TextView) findViewById(R.id.tv_hot_city7);
        tvHotCity8 = (TextView) findViewById(R.id.tv_hot_city8);
        tvHotCity9 = (TextView) findViewById(R.id.tv_hot_city9);
        tvHotCity10 = (TextView) findViewById(R.id.tv_hot_city10);
        tvHotCity11 = (TextView) findViewById(R.id.tv_hot_city11);
        tvHotCity12 = (TextView) findViewById(R.id.tv_hot_city12);
        tvHotCity13 = (TextView) findViewById(R.id.tv_hot_city13);
        tvHotCity14 = (TextView) findViewById(R.id.tv_hot_city14);
        tvHotCity15 = (TextView) findViewById(R.id.tv_hot_city15);
        tvHotCity16 = (TextView) findViewById(R.id.tv_hot_city16);
        tvHotCity17 = (TextView) findViewById(R.id.tv_hot_city17);
        tvHotCity18 = (TextView) findViewById(R.id.tv_hot_city18);
        tvHotCity19 = (TextView) findViewById(R.id.tv_hot_city19);
        tvHotCity20 = (TextView) findViewById(R.id.tv_hot_city20);
        tvHotCity21 = (TextView) findViewById(R.id.tv_hot_city21);
        tvHotCity22 = (TextView) findViewById(R.id.tv_hot_city22);
        tvHotCity23 = (TextView) findViewById(R.id.tv_hot_city23);
        tvHotCity24 = (TextView) findViewById(R.id.tv_hot_city24);
        tvHotCity25 = (TextView) findViewById(R.id.tv_hot_city25);
        tvHotCity26 = (TextView) findViewById(R.id.tv_hot_city26);
        tvHotCity27 = (TextView) findViewById(R.id.tv_hot_city27);
        tvHotCity28 = (TextView) findViewById(R.id.tv_hot_city28);
        tvHotCity29 = (TextView) findViewById(R.id.tv_hot_city29);
        tvHotCity30 = (TextView) findViewById(R.id.tv_hot_city30);
        tvHotCity31 = (TextView) findViewById(R.id.tv_hot_city31);
    }

    private void addListener() {
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                imgClear.setVisibility(View.VISIBLE);
            }
        });

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetCityListThread getCityListThread = new GetCityListThread();
                String requestCityName = "cityname=" + ConvertManager.encode(edSearch.getText().toString().trim());
                getCityListThread.setCityName(requestCityName);
                Thread thread = new Thread(getCityListThread);
                thread.start();
                btSearch.startSearch();
                btSearch.setClickable(false);
            }
        });

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map map = (Map) cityList.get(position);
                String citySelected = (String) map.get("name_cn");
                Constances.setCity(citySelected);
                turnToMainFrg();
            }
        });

        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edSearch.setText("");
            }
        });
    }

    //查询可用城市
    private class GetCityListThread implements Runnable {
        private String cityName;

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        @Override
        public void run() {
            //向服务器请求数据
            String response = NetConnecttion.request(Constances.cityListHttpUrl, cityName);
            List cityList = NetConnecttion.decodeCityListJSON(response);
            Message msg = Message.obtain();
            msg.obj = cityList;
            handler.sendMessage(msg);
        }
    }

    public void tvOnClick(View view) {
        TextView tv_hot_vity = (TextView) view;
        String citySelected = tv_hot_vity.getText().toString();
        Constances.setCity(citySelected);
        turnToMainFrg();
    }

    private void turnToMainFrg() {
        Intent intent = new Intent(SelectCityAty.this, MainActivity.class);
        intent.putExtra("CurrentWeatherFrg", CurrentWeatherFrg.ARG_SECTION_NUMBER);
        startActivity(intent);
    }
}