package com.example.shoppingmall.home.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.R;
import com.example.shoppingmall.home.bean.JsonResult;
import com.example.shoppingmall.home.bean.VideoResult;
import com.example.shoppingmall.home.videoDetail.VideoDetailFragment;
import com.example.shoppingmall.home.videoDetail.VideoDirectoryFragment;
import com.example.shoppingmall.shoppingcart.activity.VideoCartActivity;
import com.example.shoppingmall.shoppingcart.utils.CartStorage;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/29
 */
public class VideoInfoActivity extends FragmentActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rg_goods_info_main)
    RadioGroup rg_goods_info_main;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_good_info_image)
    ImageView iv_good_info_image;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.point)
    TextView point;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.price)
    TextView price;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.goods_info_frameLayout)
    FrameLayout goods_info_frameLayout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_good_info_addcart)
    Button btn_good_info_addcart;

    public JsonResult video_info;

    public DecimalFormat df = new DecimalFormat("#.00");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_info);
        ButterKnife.bind(this);
        init();
        listener();
    }

    /**
     * 设置监听
     */
    private void listener() {
        /**
         * 切换fragment
         */
        rg_goods_info_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_goods_info_intro:
                        getSupportFragmentManager().beginTransaction().replace(R.id.goods_info_frameLayout, new VideoDetailFragment()).commit();
                        break;
                    case R.id.rb_goods_info_directory:
                        getSupportFragmentManager().beginTransaction().replace(R.id.goods_info_frameLayout, new VideoDirectoryFragment()).commit();
                        break;
                }
            }
        });


        /**
         * 添加购物车
         */
        btn_good_info_addcart.setOnClickListener(v -> {
            if (video_info != null) {
                CartStorage.getInstance().addData(video_info);
                Toast.makeText(VideoInfoActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(VideoInfoActivity.this, "添加购物车失败", Toast.LENGTH_SHORT).show();
            }
                });

        rg_goods_info_main.check(R.id.rb_goods_info_intro); // 默认选中简介
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        // 获取从主页面传递过来的数据
        video_info = (JsonResult) getIntent().getSerializableExtra("video_info");
        //设置数据
        String format = df.format(video_info.getPrice()/100);
        point.setText(video_info.getPoint() + "");
        price.setText(format);
        Glide.with(this)
                .load(video_info.getCoverImg())
                .into(iv_good_info_image);
    }

}
