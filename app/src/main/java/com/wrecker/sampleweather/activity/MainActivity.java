package com.wrecker.sampleweather.activity;


import android.app.Activity;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.wrecker.sampleweather.R;
import com.wrecker.sampleweather.fragment.CurrentWeatherFrg;
import com.wrecker.sampleweather.fragment.NavigationDrawerFragment;
import com.wrecker.sampleweather.fragment.SettingFrg;
import com.wrecker.sampleweather.fragment.WeatherForecastFrg;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks  {

    private static final int OPENEDGPS = 0;

    //缓存地址
    private String fileName0 = "/sdcard/weatherInfo.txt";
    //保存天气预测的信息地址
    private String fileName1 = "/sdcard/forcastInfo.txt";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title.
     */
    private CharSequence mTitle;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ifNetWorkConnected();

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    protected void onResume() {
        String id = getIntent().getStringExtra("CurrentWeatherFrg");
        if (CurrentWeatherFrg.ARG_SECTION_NUMBER == id) {
            Fragment frgCurrentWeather = new CurrentWeatherFrg();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, frgCurrentWeather)
                    .commit();
        }
        super.onResume();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Bundle arguments = new Bundle();
        switch (position) {
            case 0:
                arguments.putInt(CurrentWeatherFrg.ARG_SECTION_NUMBER, position);
                Fragment frgCurrentWeather = new CurrentWeatherFrg();
                frgCurrentWeather.setArguments(arguments);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, frgCurrentWeather)
                        .commit();
                break;
            case 1:
                arguments.putInt(WeatherForecastFrg.ARG_SECTION_NUMBER, position);
                Fragment frgWeatherForecast = new WeatherForecastFrg();
                frgWeatherForecast.setArguments(arguments);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, frgWeatherForecast)
                        .commit();
                break;
            case 2:
                arguments.putInt(SettingFrg.ARG_SECTION_NUMBER, position);
                Fragment frgSetting = new SettingFrg();
                frgSetting.setArguments(arguments);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, frgSetting)
                        .commit();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteInfo();
    }

    public void deleteInfo(){
        try{

            FileOutputStream fos = new FileOutputStream(fileName0);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(null);

            fos = new FileOutputStream(fileName1);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(null);

            fos.close();
            oos.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.weather);
                break;
            case 2:
                mTitle = getString(R.string.forecast);
                break;
            case 3:
                mTitle = getString(R.string.settings);
                break;
        }
    }

    /**
     * 判断网络是否连接
     */
    public void ifNetWorkConnected() {
        ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if (wifi | internet) {
            //执行相关操作
        } else {
            Toast.makeText(getApplicationContext(),
                    "亲，网络连了么？", Toast.LENGTH_LONG)
                    .show();
        }
    }

}
