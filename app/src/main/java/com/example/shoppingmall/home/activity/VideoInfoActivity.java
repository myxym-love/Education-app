package com.example.shoppingmall.home.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.R;
import com.example.shoppingmall.home.bean.JsonResult;
import com.example.shoppingmall.home.bean.VideoResult;
import com.example.shoppingmall.home.videoDetail.VideoDetailFragment;
import com.example.shoppingmall.home.videoDetail.VideoDirectoryFragment;

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

    @BindView(R.id.iv_good_info_image)
    ImageView iv_good_info_image;

    @BindView(R.id.point)
    TextView point;

    @BindView(R.id.price)
    TextView price;

    @BindView(R.id.goods_info_frameLayout)
    FrameLayout goods_info_frameLayout;

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

        rg_goods_info_main.check(R.id.rb_goods_info_intro); // 默认选中简介
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        // 获取从主页面传递过来的数据
        JsonResult video_info = (JsonResult) getIntent().getSerializableExtra("video_info");
        //设置数据
        String format = df.format(video_info.getPrice()/100);
        point.setText(video_info.getPoint() + "");
        price.setText(format);
        Glide.with(this)
                .load(video_info.getCoverImg())
                .into(iv_good_info_image);
    }

}
