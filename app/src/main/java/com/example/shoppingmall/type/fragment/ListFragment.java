package com.example.shoppingmall.type.fragment;


import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.shoppingmall.type.adapter.TypeLeftAdapter;
import com.example.shoppingmall.type.adapter.TypeRightAdapter;
import com.example.shoppingmall.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 分类页面
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends BaseFragment {
    private FrameLayout fl_list_container;
    private ListView lv_left;
    private RecyclerView rv_right;
    private ResultBean resultBean = new ResultBean();

//
//

    private TypeLeftAdapter leftAdapter;
    private boolean isFirst = true;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_list, null);
        lv_left = (ListView) view.findViewById(R.id.lv_left);
        rv_right = (RecyclerView) view.findViewById(R.id.rv_right);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //联网请求
        getDataFromNet();
    }

    /**
     * 具体的联网请求代码
     */
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
            //两位请求成功

            switch (id) {
                case 100:
//                    Toast.makeText(mContext, "http", Toast.LENGTH_SHORT).show();
                    if (response != null) {
                        //解析数据
                        processData(response);
                        if (isFirst) {
                            leftAdapter = new TypeLeftAdapter(mContext);
                            lv_left.setAdapter(leftAdapter);
                        }


                        initListener(leftAdapter);

                        //解析右边数据
                        TypeRightAdapter rightAdapter = new TypeRightAdapter(mContext, resultBean);
                        rv_right.setAdapter(rightAdapter);

                        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);

                        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                if (position == 0) {
                                    return 3;
                                } else {
                                    return 1;
                                }
                            }
                        });
                        rv_right.setLayoutManager(manager);
                    }

                    break;
                case 101:
                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    private void initListener(final TypeLeftAdapter adapter) {
        //点击监听
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelected(position);//刷新
                if (position != 0) {
                    isFirst = false;
                }
                leftAdapter.notifyDataSetChanged();
            }
        });

        //选中监听
        lv_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelected(position);//刷新

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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