package com.example.shoppingmall.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;


/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/27
 */
public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static  Context mContext;

    public static Context getContext() {
        return mContext;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initHttpClient();
    }

    // 初始化OkHttpClient
    private void initHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        okHttpClient.dispatcher().setMaxRequests(9);
        OkHttpUtils.initClient(okHttpClient);
    }


}
