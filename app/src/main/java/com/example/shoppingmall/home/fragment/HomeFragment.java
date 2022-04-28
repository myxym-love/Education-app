package com.example.shoppingmall.home.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.fastjson.JSONObject;
import com.example.shoppingmall.R;
import com.example.shoppingmall.base.BaseFragment;
import com.example.shoppingmall.home.Adapter.HomeFragmentAdapter;
import com.example.shoppingmall.home.activity.SearchActivity;
import com.example.shoppingmall.home.bean.ActResult;
import com.example.shoppingmall.home.bean.BannerResult;
import com.example.shoppingmall.home.bean.ChannelResult;
import com.example.shoppingmall.home.bean.ResultBean;
import com.example.shoppingmall.home.bean.VideoResult;
import com.example.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.alibaba.fastjson.JSON;

import java.util.List;

import okhttp3.Call;

/**
 * @Description 主页面布局
 * @Author Mao Yu
 * @Date 2022/4/27
 */

public class HomeFragment extends BaseFragment {

    private RecyclerView rvHome;
    private ImageButton ib_top;
    private EditText search_text;
    private ImageButton search_icon;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null); // 加载主页面布局
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home); // 加载RecyclerView
        ib_top = (ImageButton) view.findViewById(R.id.ib_top); // 加载回到顶部按钮
        search_text = (EditText) view.findViewById(R.id.search_text); // 加载搜索框
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
            List<ChannelResult.DataDTO> channelResult = JSON.parseArray(channelList, ChannelResult.DataDTO.class);

            ResultBean resultBean = new ResultBean();
            resultBean.setAct_info(actResult); // 活动数据
            resultBean.setChannel_info(channelResult); // 频道数据
            resultBean.setBanner_info(bannerResult); // 设置banner数据
            resultBean.setVideo_info(videoResult); // 视频数据

            // 判断数据是否为空
            HomeFragmentAdapter adapter = new HomeFragmentAdapter(mContext, resultBean); // 创建适配器
            rvHome.setAdapter(adapter); // 设置适配器

            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1); // 创建网格布局管理器
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                     //判断当前位置是否是banner位置
                    if (position <= 2) { // 判断是否为banner
                        ib_top.setVisibility(View.GONE); // 隐藏回到顶部按钮

                    } else {
                        ib_top.setVisibility(View.VISIBLE); // 显示回到顶部按钮
                    }
                    return 1; // 设置网格布局管理器的SpanSizeLookup
                }
            });
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
            Intent intent = new Intent(mContext, SearchActivity.class);
            startActivity(intent);
        });
    }

}
