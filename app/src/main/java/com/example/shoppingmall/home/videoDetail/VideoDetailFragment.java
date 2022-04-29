package com.example.shoppingmall.home.videoDetail;

import android.view.View;
import android.widget.TextView;

import com.example.shoppingmall.base.BaseFragment;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/29
 */
public class VideoDetailFragment extends BaseFragment {

    public TextView textView_directory_name;

    @Override
    public View initView() {
        View view = View.inflate(mContext, com.example.shoppingmall.R.layout.fragment_video_detail, null);
        textView_directory_name = view.findViewById(com.example.shoppingmall.R.id.textView_directory_name);
        textView_directory_name.setGravity(View.TEXT_ALIGNMENT_CENTER);
        return view;
    }
}
