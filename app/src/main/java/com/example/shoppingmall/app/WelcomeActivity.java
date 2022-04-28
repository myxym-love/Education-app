package com.example.shoppingmall.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import com.example.shoppingmall.R;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/27
 */
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // 两秒进入主页面
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000); // 休眠2秒
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class)); // 跳转到主页面
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
