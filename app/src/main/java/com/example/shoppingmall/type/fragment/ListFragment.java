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
import com.example.shoppingmall.type.bean.TypeBean;
import com.example.shoppingmall.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.lang.reflect.Type;
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
    private List<TypeBean.DataDTO> resultBean;
    private List<TypeBean.DataDTO.VideoListDTO> videoList;
    private List<TypeBean.DataDTO.CommonCategoriesDTO> commonCategoriesDTOS;

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
                .url(Constants.TYPE_URL)
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
                            leftAdapter = new TypeLeftAdapter(mContext,resultBean);
                            lv_left.setAdapter(leftAdapter);
                        }


                        initListener(leftAdapter);

                        //解析右边数据
                        if (isFirst) {
                            videoList = resultBean.get(0).getVideoList();
                            commonCategoriesDTOS = resultBean.get(0).getCommonCategories();
                        }
                        TypeRightAdapter rightAdapter = new TypeRightAdapter(mContext, videoList,commonCategoriesDTOS);
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

                getDataFromNet();
                videoList = resultBean.get(position).getVideoList();
                commonCategoriesDTOS = resultBean.get(position).getCommonCategories();
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

            String data = jsonObject.getString("data");
            resultBean = JSON.parseArray(data,TypeBean.DataDTO.class);
        }

    }
}