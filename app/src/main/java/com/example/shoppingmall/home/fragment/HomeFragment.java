package com.example.shoppingmall.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.example.shoppingmall.R;
import com.example.shoppingmall.base.BaseFragment;
import com.example.shoppingmall.home.Adapter.HomeFragmentAdapter;
import com.example.shoppingmall.home.Adapter.VideoListAdapter;
import com.example.shoppingmall.home.activity.SearchActivity;
import com.example.shoppingmall.home.bean.ActResult;
import com.example.shoppingmall.home.bean.BannerResult;
import com.example.shoppingmall.home.bean.ChannelResult;
import com.example.shoppingmall.home.bean.ChapterResult;
import com.example.shoppingmall.home.bean.EpisodeResult;
import com.example.shoppingmall.home.bean.JsonResult;
import com.example.shoppingmall.home.bean.ResultBean;
import com.example.shoppingmall.home.bean.VideoResult;
import com.example.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

import okhttp3.Call;

/**
 * @Description 主页面布局
 * @Author Mao Yu
 * @Date 2022/4/27
 */

public class HomeFragment extends BaseFragment {

    public JsonResult jsonResult;
    private RecyclerView rvHome;
    private ImageButton ib_top;
    private EditText search_text;
    private ImageButton search_icon;
    public ResultBean resultBean = new ResultBean();

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null); // 加载主页面布局
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home); // 加载RecyclerView
        ib_top = (ImageButton) view.findViewById(R.id.ib_top); // 加载回到顶部按钮
        search_text = (EditText) view.findViewById(R.id.search_content); // 加载搜索框
        search_icon = (ImageButton) view.findViewById(R.id.search_icon); // 加载搜索按钮
        return view; // 返回布局
    }

    @Override
    public void initData() {
        getDataFromNetHome(); // 请求首页数据
    }

    /**
     * 请求首页数据
     */
    private void getDataFromNetHome() {
        OkHttpUtils.get()
                .url(Constants.HOME_URL)
                .id(100)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String json, int id) {
                        processData(json);
                    }
                });

    }

    /**
     * 解析数据 并且显示数据
     *
     * @param json 返回的json数据
     */
    private void processData(String json) {
        if (!TextUtils.isEmpty(json)) {
            JSONObject jsonObject = JSON.parseObject(json);

            String actList = jsonObject.getString("actList");
            String videoBanner = jsonObject.getString("videoBanner");
            String video = jsonObject.getString("video");
            String channelList = jsonObject.getString("channelList");

            List<ActResult.DataDTO> actResult = JSON.parseArray(actList, ActResult.DataDTO.class);
            List<BannerResult.DataDTO> bannerResult = JSON.parseArray(videoBanner, BannerResult.DataDTO.class);
            List<VideoResult> videoResult = JSON.parseArray(video, VideoResult.class);

            List<JsonResult> jsonResults = JSONObject.parseArray(video, JsonResult.class);

            List<ChannelResult.DataDTO> channelResult = JSON.parseArray(channelList, ChannelResult.DataDTO.class);

            resultBean.setJsonResult(jsonResults);
            resultBean.setAct_info(actResult); // 活动数据
            resultBean.setChannel_info(channelResult); // 频道数据
            resultBean.setBanner_info(bannerResult); // 设置banner数据
            resultBean.setVideo_info(videoResult); // 视频数据

            // 判断数据是否为空
            HomeFragmentAdapter adapter = new HomeFragmentAdapter(mContext, resultBean); // 创建适配器
            rvHome.setAdapter(adapter); // 设置适配器

            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1); // 创建网格布局管理器
            rvHome.setLayoutManager(gridLayoutManager); // 设置布局管理器

            initListener(); // 初始化监听器
        }
    }

    /**
     * 监听器实现
     */
    private void initListener() {
        ib_top.setOnClickListener(view -> rvHome.scrollToPosition(0));
        search_icon.setOnClickListener(view -> {
            String search_text_str = search_text.getText().toString();
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
                        public void onResponse(String s, int id) {
                            JSONObject json = JSONObject.parseObject(s);
                            String data = json.getString("data");
                            List<JsonResult> jsonResult = JSONObject.parseArray(data, JsonResult.class);
                            Intent intent = new Intent(); // 创建Intent
                            intent.setClass(mContext, SearchActivity.class); // 设置跳转的activity
                            intent.putExtra("search_info", (Serializable) jsonResult); // 将关键字传过去传递过去
                            startActivity(intent); // 启动搜索页面
                        }
                    });

        });

        // 设置网格布局管理器的监听器
        rvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 判断是否滑动到顶部
                if (rvHome.canScrollVertically(-1)) {
                    ib_top.setVisibility(View.VISIBLE); // 显示回到顶部按钮
                } else {
                    ib_top.setVisibility(View.GONE); // 隐藏回到顶部按钮
                }
            }
        });

        // 设置回到顶部按钮的监听器
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvHome.smoothScrollToPosition(0); // 平滑滚动到顶部
            }
        });
    }


    class Contract extends ActivityResultContract<Boolean, String> {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context mContext, Boolean input) {
            Intent intent = new Intent(mContext, SearchActivity.class);
            intent.putExtra("b", input);
            return intent;
        }

        @Override
        public String parseResult(int resultCode, @Nullable Intent intent) {
            assert intent != null;
            return intent.getStringExtra("search_key");
        }
    }
}