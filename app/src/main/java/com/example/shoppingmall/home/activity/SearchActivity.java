package com.example.shoppingmall.home.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSONObject;
import com.example.shoppingmall.R;
import com.example.shoppingmall.home.Adapter.VideoListAdapter;
import com.example.shoppingmall.home.bean.ResultBean;
import com.example.shoppingmall.home.bean.VideoResult;
import com.example.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/28
 */
public class SearchActivity extends Activity {


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.search_item)
    public GridView gridView;

    @BindView(R.id.search_text)
    public EditText search_text;

    @BindView(R.id.search_icon)
    public ImageButton search_icon;

    public List<VideoResult> videoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        /**
         * 设置搜索按钮的点击事件
         */
        search_icon.setOnClickListener(v -> {
            // 获取输入框的内容
            String search_text_str = search_text.getText().toString();
            // 网络请求
            OkHttpUtils.get()
                    .url(Constants.SEARCH_URL)
                    .id(100)
                    .addParams("key", search_text_str)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            Log.e("TAG", "onError: " + e.getMessage());
                        }

                        @Override
                        public void onResponse(String s, int i) {
                            Log.e("TAG", "onResponse: " + s);
                            JSONObject json = JSONObject.parseObject(s);
                            String data = json.getString("data");
                            // 解析数据
                            videoList = JSONObject.parseArray(data, VideoResult.class);
                            VideoListAdapter adapter;
                            // 创建适配器
                            adapter = new VideoListAdapter(SearchActivity.this, videoList);
                            // 设置适配器
                            gridView.setAdapter(adapter);
                        }
                    });
        });

        ArrayList<VideoResult> search_info = (ArrayList<VideoResult>) getIntent().getSerializableExtra("search_info");
        VideoListAdapter adapter;
        adapter = new VideoListAdapter(this, search_info);
        gridView.setAdapter(adapter);
    }


}
