package com.example.shoppingmall.utils;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;


/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/27
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initHttpClient();
    }

    // 初始化OkHttpClient
    private void initHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }


}