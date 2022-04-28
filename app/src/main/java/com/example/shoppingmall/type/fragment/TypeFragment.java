package com.example.shoppingmall.type.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.shoppingmall.base.BaseFragment;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/27
 */
public class TypeFragment extends BaseFragment {


    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        textView.setText("分类");
    }
}
