package com.wrecker.sampleweather.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wrecker.sampleweather.R;

/**
 * Created by xiaoxin on 2016/8/31.
 */
public class DrawerAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mTitles;

    public DrawerAdapter(Context context) {
        mContext = context;
        mTitles = new String[]{
                context.getResources().getString(R.string.weather),
                context.getResources().getString(R.string.forecast),
//                context.getResources().getString(R.string.settings),
        };
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mTitles.length;
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return mTitles[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public int getIconId(int position) {
        switch (position) {
            case 0:
                return R.drawable.item_first;
            case 1:
                return R.drawable.item_second;
//            case 2:
//                return R.drawable.item_third;
            default:
                return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_drawer_item, null);
        }
        TextView tvItem = (TextView) convertView.findViewById(R.id.drawer_tv_item);
        ImageView imgItem = (ImageView) convertView.findViewById(R.id.drawer_img_item);

        tvItem.setText(getItem(position));

        Resources res = mContext.getResources();
        Drawable myImage = res.getDrawable(getIconId(position));
        imgItem.setImageDrawable(myImage);
        return convertView;
    }
}
