package com.wrecker.sampleweather.present;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by xiaoxin on 2016/9/21.
 */
public abstract class BasePresent<T> {
    protected Reference<T> mViewRef;

    public void attachView(T view){
        mViewRef = new WeakReference<T>(view);
    }

    protected T getView(){
        return mViewRef.get();
    }

    public boolean isViewAttached(){
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView(){
        if(mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }

}
