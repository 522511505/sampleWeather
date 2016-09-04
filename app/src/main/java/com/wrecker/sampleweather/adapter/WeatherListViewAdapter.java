package com.wrecker.sampleweather.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunfusheng.marqueeview.MarqueeView;
import com.wrecker.sampleweather.R;

import java.util.*;

import com.wrecker.sampleweather.activity.SelectCityAty;
import com.wrecker.sampleweather.net.NetConnecttion;
import com.wrecker.sampleweather.tools.Constances;
import com.wrecker.sampleweather.tools.ConvertManager;
import com.wrecker.sampleweather.tools.WeatherImageManager;

/**
 * Created by xiaoxin on 2016/8/3.
 */
public class WeatherListViewAdapter extends BaseAdapter {
    //上下文
    private Context context = null;

    //数据List
    private List list;

    //屏幕高度
    private int windowHeigh;

    //状态栏高度
    private int windowTop;

    // 用于控制drawer
    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;

    private static class ViewHolder {
        public static ImageView imgGlobal;
        public static ImageView imgLocation;
        public static ImageView imgMenu;
        public static ImageView imgType;
        public static TextView curTemp;
        public static TextView fengxiang;
        public static TextView fengli;
        public static TextView type;
        public static TextView lowHightemp;
        public static TextView date;
        public static TextView aqi;
        public static TextView city;
        public static MarqueeView marqueeView;
        public static TextView tvThermometer;
        public static TextView tvPmTip;
        public static TextView tvFengliTip;
        public static TextView tvFengxiangTip;
    }

    private LayoutInflater listContainer; // 视图容器

    public WeatherListViewAdapter(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public WeatherListViewAdapter(Context context, int windowHeigh, int windowTop) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.windowHeigh = windowHeigh;
        this.windowTop = windowTop;
    }

    /*
    *@parm context 上下文
    *@parm list 天气信息列表
    *@parm  windowHeigh  屏幕高度，用于适配
    * @parm view和dy  用于点击菜单打开drawerLayout
     */
    public WeatherListViewAdapter(Context context, List<Map<String, Object>> list, int windowHeigh, View view, DrawerLayout dy) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.list = list;
        this.windowHeigh = windowHeigh;
        this.mDrawerLayout = dy;
        this.mFragmentContainerView = view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (list != null) {
            ViewHolder listItemView;
            if (convertView == null) {
                listItemView = new ViewHolder();

                convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, null);

                initView(convertView);

                RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.ll_subject);

                RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) rl
                        .getLayoutParams();

                linearParams.height = Constances.getWindowHeight();

                rl.setLayoutParams(linearParams);

                convertView.setTag(listItemView);
            } else {
                listItemView = (ViewHolder) convertView.getTag();
            }

            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) list.get(i);
                listItemView.city.setText(Constances.getCity()[0]);
                listItemView.aqi.setText((String) map.get("aqi"));
                listItemView.curTemp.setText(map.get("curTemp") + "°");
                listItemView.date.setText(ConvertManager.subDateForm((String)map.get("date"), 3));
                listItemView.fengli.setText(getFengliForm((String) map.get("fengli")));
                listItemView.fengxiang.setText((String) map.get("fengxiang"));
                listItemView.type.setText((String) map.get("type"));
                Drawable drawable = WeatherImageManager.getImgByWeather(getContext(), (String) map.get("type"));
                listItemView.imgType.setImageDrawable(drawable);
                listItemView.city.setText((String) map.get("city"));
                listItemView.lowHightemp.setText(map.get("lowTemp") + "/" + map.get("highTemp") + "°C");
                List<String> info = new ArrayList();
                for (int j = 0; j < 6; j++) {
                    info.add((String) map.get("detail" + j));
                }
                listItemView.marqueeView.startWithList(info);
            }
        }
        return convertView;
    }

    //初始化控件
    private void initView(View view) {
        ViewHolder.imgGlobal = (ImageView) view.findViewById(R.id.img_global);
        //着色改为白色
        final Drawable originGlobal = getContext().getResources().getDrawable(R.drawable.global);
        ViewHolder.imgGlobal.setImageDrawable(WeatherImageManager.tintDrawable(originGlobal, ColorStateList.valueOf(Color.WHITE)));
        ViewHolder.imgGlobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), SelectCityAty.class));
            }
        });

        ViewHolder.imgLocation = (ImageView) view.findViewById(R.id.icon_location);
        final Drawable originLocation = getContext().getResources().getDrawable(R.drawable.location);
        ViewHolder.imgLocation.setImageDrawable(WeatherImageManager.tintDrawable(originLocation, ColorStateList.valueOf(Color.WHITE)));

        ViewHolder.imgMenu = (ImageView) view.findViewById(R.id.img_menu);
        final Drawable originImgMenu = getContext().getResources().getDrawable(R.drawable.menu);
        ViewHolder.imgMenu.setImageDrawable(WeatherImageManager.tintDrawable(originImgMenu, ColorStateList.valueOf(Color.WHITE)));
        //点击菜单打开drawerlayout
        ViewHolder.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mFragmentContainerView);
            }
        });

        ViewHolder.city = (TextView) view.findViewById(R.id.tv_location);
        ViewHolder.date = (TextView) view.findViewById(R.id.tv_curTime);
        ViewHolder.curTemp = (TextView) view.findViewById(R.id.tv_curTemp);
        //设置字体
        ViewHolder.curTemp.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts.otf"));
        ViewHolder.type = (TextView) view.findViewById(R.id.tv_curTemp_type);
        ViewHolder.imgType = (ImageView) view.findViewById(R.id.icon_curTemp);
        ViewHolder.lowHightemp = (TextView) view.findViewById(R.id.tv_low_high_temp);
        ViewHolder.aqi = (TextView) view.findViewById(R.id.tv_pm);
        ViewHolder.fengli = (TextView) view.findViewById(R.id.tv_fengli);
        ViewHolder.fengxiang = (TextView) view.findViewById(R.id.tv_fengxiang);
        ViewHolder.marqueeView = (MarqueeView) view.findViewById(R.id.marqueeView);

        ViewHolder.tvThermometer = (TextView) view.findViewById(R.id.tv_thermometer);
        ViewHolder.tvPmTip = (TextView) view.findViewById(R.id.tv_pm_tip);
        ViewHolder.tvFengliTip = (TextView) view.findViewById(R.id.tv_fengli_tip);
        ViewHolder.tvFengxiangTip = (TextView) view.findViewById(R.id.tv_fengxiang_tip);

        Resources res = context.getResources();
        Drawable imgThermometer = res.getDrawable(R.drawable.thermometer);
        imgThermometer.setBounds(0, 0, 60, 60);
        ViewHolder.tvThermometer.setCompoundDrawables(imgThermometer, null, null, null);

        Drawable imgLeft = res.getDrawable(R.drawable.left);
        imgLeft.setBounds(0, 0, 60, 60);
        ViewHolder.tvPmTip.setCompoundDrawables(imgLeft, null, null, null);

        Drawable imgFengli = res.getDrawable(R.drawable.fengli);
        imgFengli.setBounds(0, 0, 60, 60);
        ViewHolder.tvFengliTip.setCompoundDrawables(imgFengli, null, null, null);

        Drawable imgFengxiang = res.getDrawable(R.drawable.fengxiang);
        imgFengxiang.setBounds(0, 0, 60, 60);
        ViewHolder.tvFengxiangTip.setCompoundDrawables(imgFengxiang, null, null, null);
    }

    public Context getContext() {
        return context;
    }

    private String getFengliForm(String str) {
        String[] strs = str.split("\\(");
        return strs[0];
    }
}
