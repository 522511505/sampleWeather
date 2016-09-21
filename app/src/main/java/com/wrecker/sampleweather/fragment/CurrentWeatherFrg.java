package com.wrecker.sampleweather.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.util.TimeUtils;
import android.support.v4.widget.DrawerLayout;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

import com.wrecker.sampleweather.R;
import com.wrecker.sampleweather.activity.MyApplication;
import com.wrecker.sampleweather.activity.SelectCityAty;
import com.wrecker.sampleweather.adapter.WeatherListViewAdapter;
import com.wrecker.sampleweather.entity.WeatherEntity;
import com.wrecker.sampleweather.net.NetConnecttion;
import com.wrecker.sampleweather.present.BasePresent;
import com.wrecker.sampleweather.present.CurrentWeatherPresent;
import com.wrecker.sampleweather.tools.Constances;
import com.wrecker.sampleweather.tools.ConvertManager;
import com.wrecker.sampleweather.view.CurrentInterface;
import com.yalantis.phoenix.PullToRefreshView;


/**
 * Created by xiaoxin on 2016/8/3.
 */
public class CurrentWeatherFrg<V,T extends BasePresent<V>> extends Fragment implements CurrentInterface{
    //当前fragment标记号
    public static final String ARG_SECTION_NUMBER = "0";

    //数据list
    private List list;

    //当前天气视图
    private View view;

    //下拉刷新控件
    private static PullToRefreshView mPullToRefreshView;

    //Weather的ListView
    private ListView listView;

    //listView的适配器
    private WeatherListViewAdapter adapter;

    private Handler mhandler = new Handler();

    //网络获取进度
    private ProgressDialog progressDialog = null;

    //显示当前时间
    private SimpleDateFormat formatter = new SimpleDateFormat("HH时mm分");
    private Date curDate = new Date(System.currentTimeMillis());

    // 用于控制drawer
    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;

    private CurrentWeatherPresent currentWeatherPresent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        }

        initView(view);

        //构建CurrentWeatherPresent，与CurrentView建立连接
//        currentWeatherPresent.attachView(this);

        currentWeatherPresent = new CurrentWeatherPresent(this);
        //请求数据
        currentWeatherPresent.getWeatherInfo();

        addListener();

        return view;
    }

    @Override
    public void showProgressDialog() {
        progressDialog = ProgressDialog.show(getActivity(), "请稍等...", "获取位置中...", true);
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showWeatherInfo(List list) {

    }

    //present获取信息后回调
    @Override
    public void setListViewAdapter(final List list) {
        mhandler.post(new Runnable() {
            public void run() {
                //将获取到的数据更新到列表中
                adapter = new WeatherListViewAdapter(getContext(), list, getWindowHeight(), mFragmentContainerView, mDrawerLayout);
                listView.setAdapter(adapter);
            }
        });
    }

    //初始化控件
    private void initView(View view) {
        mFragmentContainerView = getActivity().findViewById(R.id.navigation_drawer);
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        // 列表的ListView
        listView = (ListView) view.findViewById(R.id.list_view);
    }

    //添加监听器
    private void addListener() {
        //下拉刷新
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ConnectivityManager con = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
                        boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
                        boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
                        if (wifi | internet) {
                            Toast.makeText(getActivity(), "刷新成功，更新于" + formatter.format(curDate), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "当前无网络连接", Toast.LENGTH_LONG).show();
                        }
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    //获得屏幕高度
    private int getWindowHeight() {
        Rect rect = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        Constances.setWindowHeight(rect.height());
        return rect.height();
    }
}
