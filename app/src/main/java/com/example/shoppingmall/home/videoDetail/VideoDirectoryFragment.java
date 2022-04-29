package com.example.shoppingmall.home.videoDetail;

import android.view.View;
import android.widget.TextView;

import com.example.shoppingmall.R;
import com.example.shoppingmall.base.BaseFragment;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/29
 */
public class VideoDirectoryFragment extends BaseFragment {

    public TextView textView_directory_name;

    @Override
    public View initView() {

        View view = View.inflate(mContext, R.layout.fragment_video_directory, null);
        textView_directory_name = view.findViewById(R.id.textView_directory_name);
        textView_directory_name.setGravity(View.TEXT_ALIGNMENT_CENTER);
        return view;
    }
}
