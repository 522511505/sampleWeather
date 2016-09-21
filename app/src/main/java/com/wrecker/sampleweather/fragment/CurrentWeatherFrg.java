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
import com.wrecker.sampleweather.tools.Constances;
import com.wrecker.sampleweather.tools.ConvertManager;
import com.yalantis.phoenix.PullToRefreshView;


/**
 * Created by xiaoxin on 2016/8/3.
 */
public class CurrentWeatherFrg extends Fragment {
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

    //菜单
    public static ImageView imgMenu;

    //缓存地址
    private String fileName = "/sdcard/weatherInfo.txt";

    //获取天气信息的线程
    private Thread getInfoThread = new Thread(new Runnable() {
        @Override
        public void run() {
            //线程休眠0.5秒等待主线程获取定位地址
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            String curCity = Constances.getRequestCity();
//            //若仍为空，再休眠一秒
//            if (null == curCity) {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                curCity = Constances.getRequestCity();
//            }

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
                list = new ArrayList();
                list.add(map);

                writeToSDCard(list);

                setListAdapter();
            } else {
                Toast.makeText(getActivity(),"服务器出错",Toast.LENGTH_SHORT).show();
                getActivity().finish();
                Log.e("请求出错", "error");
            }
            //更新完列表数据，则关闭对话框
            progressDialog.dismiss();
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        }

        initView(view);

        addListener();

        boolean ifSaved = getSDcardWeatherInfo();

        if(true == ifSaved){

            setListAdapter();

        }else{

            progressDialog = ProgressDialog.show(getActivity(), "请稍等...", "获取位置中...", true);

            getInfoThread.start();
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        writeToSDCard(null);
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

    //设置适配器
    public void setListAdapter() {
        mhandler.post(new Runnable() {
            public void run() {
                //将获取到的数据更新到列表中
                adapter = new WeatherListViewAdapter(getContext(), list, getWindowHeight(), mFragmentContainerView, mDrawerLayout);
                listView.setAdapter(adapter);
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

    //获得状态栏高度
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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

    private boolean getSDcardWeatherInfo(){

        try{
            FileInputStream fin = new FileInputStream(fileName);

            ObjectInputStream ois = new ObjectInputStream(fin);

            List obj = (List)ois.readObject();

            if(null != obj){
                list = obj;
                return true;
            }

            fin.close();
            ois.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
