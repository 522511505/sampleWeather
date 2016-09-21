package com.wrecker.sampleweather.view;

import android.widget.Adapter;

import java.util.*;
/**
 * Created by DELL on 2016/9/21.
 */
public interface CurrentInterface {
    public void showProgressDialog();

    public void hideProgressDialog();

    public void showWeatherInfo(List list);

    public void setListViewAdapter(List list);
}
