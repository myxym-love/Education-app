package com.example.shoppingmall.home.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.example.shoppingmall.R;
import com.example.shoppingmall.home.Adapter.ChannelMoreAdapter;
import com.example.shoppingmall.type.bean.TypeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/5/2
 */
public class ChannelActivity extends FragmentActivity {

    public List<TypeBean.DataDTO.VideoListDTO> videoList;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.back)
    ImageView back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.saoyisao)
    ImageView saoyisao;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.channel_grid)
    GridView channel_grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        ButterKnife.bind(this);
        back.setVisibility(View.VISIBLE);
        saoyisao.setVisibility(View.GONE);
        Intent intent = getIntent();
        TypeBean.DataDTO channel_info = (TypeBean.DataDTO) intent.getSerializableExtra("channel_info");
        videoList = channel_info.getVideoList();
        listener();

        channel_grid.setAdapter(new ChannelMoreAdapter(this, videoList));

    }

    private void listener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
