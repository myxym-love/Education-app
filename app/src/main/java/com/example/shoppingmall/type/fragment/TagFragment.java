package com.example.shoppingmall.type.fragment;


import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.shoppingmall.R;
import com.example.shoppingmall.base.BaseFragment;
import com.example.shoppingmall.home.bean.ActResult;
import com.example.shoppingmall.home.bean.BannerResult;
import com.example.shoppingmall.home.bean.ChannelResult;
import com.example.shoppingmall.home.bean.JsonResult;
import com.example.shoppingmall.home.bean.ResultBean;
import com.example.shoppingmall.home.bean.VideoResult;
import com.example.shoppingmall.type.adapter.TagGridViewAdapter;
import com.example.shoppingmall.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class TagFragment extends BaseFragment {

    private GridView gv_tag;
    private TagGridViewAdapter adapter;
    private ResultBean resultBean = new ResultBean();

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_tag, null);
        gv_tag = (GridView) view.findViewById(R.id.gv_tag);

        return view;
    }

    @Override
    public void initData() {
        getDataFromNet();

    }


    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.HOME_URL)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {


        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:
//                    Toast.makeText(mContext, "http", Toast.LENGTH_SHORT).show();
                    if (response != null) {
                        processData(response);
                        adapter = new TagGridViewAdapter(mContext, resultBean);
                        gv_tag.setAdapter(adapter);
                    }
                    break;
                case 101:
                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

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
        }

    }
}
