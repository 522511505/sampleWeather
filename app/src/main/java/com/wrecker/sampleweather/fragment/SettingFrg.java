package com.wrecker.sampleweather.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wrecker.sampleweather.activity.AboutAty;
import com.wrecker.sampleweather.activity.CheckNewVersionAty;
import com.wrecker.sampleweather.activity.CommonAty;
import com.wrecker.sampleweather.activity.ContactAty;
import com.wrecker.sampleweather.activity.FeedBackAty;
import com.wrecker.sampleweather.activity.MainActivity;
import com.wrecker.sampleweather.R;
import com.wrecker.sampleweather.activity.MessageWarningAty;


/**
 * Created by xiaoxin on 2016/8/3.
 * 设置界面，应该使用PreferenceActivity实现
 */
public class SettingFrg extends Fragment {
    //当前fragment视图
    View view;

    private LinearLayout llMessageWarning;
    private TextView tvMessageWarning;
    private LinearLayout llCommon;
    private TextView tvCommon;
    private LinearLayout llFeedback;
    private TextView tvFeedback;
    private LinearLayout llCheckNewVersion;
    private TextView tvCheckNewVersion;
    private LinearLayout llAbout;
    private TextView tvAbout;
    private LinearLayout llContact;
    private TextView tvContact;

    //当前fragment标记号
    public static final String ARG_SECTION_NUMBER = "2";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_setting, container, false);
        }
        onViewCreated(view);

        addListener();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public void onViewCreated(View view) {
        llMessageWarning = (LinearLayout) view.findViewById(R.id.ll_message_warning);
        tvMessageWarning = (TextView) view.findViewById(R.id.tv_message_warning);
        llCommon = (LinearLayout) view.findViewById(R.id.ll_common);
        tvCommon = (TextView) view.findViewById(R.id.tv_common);
        llFeedback = (LinearLayout) view.findViewById(R.id.ll_feedback);
        tvFeedback = (TextView) view.findViewById(R.id.tv_feedback);
        llCheckNewVersion = (LinearLayout) view.findViewById(R.id.ll_check_new_version);
        tvCheckNewVersion = (TextView) view.findViewById(R.id.tv_check_new_version);
        llAbout = (LinearLayout) view.findViewById(R.id.ll_about);
        tvAbout = (TextView) view.findViewById(R.id.tv_about);
        llContact = (LinearLayout) view.findViewById(R.id.ll_contact);
        tvContact = (TextView) view.findViewById(R.id.tv_contact);
    }

    private void addListener() {
        llMessageWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MessageWarningAty.class));
            }
        });

        llCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CommonAty.class));
            }
        });
        llFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),FeedBackAty.class));
            }
        });
        llCheckNewVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CheckNewVersionAty.class));
            }
        });
        llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AboutAty.class));
            }
        });
        llContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ContactAty.class));
            }
        });
    }
}
