package com.example.shoppingmall.home.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
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

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_good_info_cart)
    TextView tv_good_info_cart;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ib_good_info_more)
    ImageButton ib_good_info_more;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ib_good_info_back)
    ImageButton ib_good_info_back;


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
     * ????????????
     */
    private void listener() {
        /**
         * ??????fragment
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
         * ???????????????
         */
        btn_good_info_addcart.setOnClickListener(v -> {
            if (video_info != null) {
                CartStorage.getInstance().addData(video_info);
                Toast.makeText(VideoInfoActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(VideoInfoActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
            }
                });


        // ?????????????????????
        tv_good_info_cart.setOnClickListener(v -> {
            Intent intent = new Intent(VideoInfoActivity.this, VideoCartActivity.class);
            startActivity(intent);
                });

        // ??????
        ib_good_info_back.setOnClickListener(v -> {
            System.out.println("??????");
            finish();
        });

        rg_goods_info_main.check(R.id.rb_goods_info_intro); // ??????????????????
    }


    @SuppressLint("SetTextI18n")
    private void init() {
        // ???????????????????????????????????????
        video_info = (JsonResult) getIntent().getSerializableExtra("video_info");
        //????????????
        String format = df.format(video_info.getPrice()/100);
        point.setText(video_info.getPoint() + "");
        price.setText(format);
        Glide.with(this)
                .load(video_info.getCoverImg())
                .into(iv_good_info_image);
    }

}
