package com.example.shoppingmall.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * @author maoyu
 * @date 2022-04-27 下午 12:00
 * @description BaseFragment 基类
 * @package com.example.shoppingmall.base BaseFragment
 * @projectName shoppingmall
 * @email 1602271854@qq.com
 */
public abstract class BaseFragment extends Fragment {

    public Context mContext; // 环境上下文

    /**
     * 初始化
     * @param savedInstanceState 保存的数据
     *  在onCreateView方法中调用
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity(); // 获取环境上下文
    }

    /**
     * 获取布局文件
     * @param inflater 布局填充器
     * @param container 容器
     * @param savedInstanceState 保存的数据
     * @return 返回视图
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }

    /**
     * 抽象类,由孩子实现,实现不同的效果
     * @return 返回视图
     */
    public abstract View initView();


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }


    /**
     * 初始化数据,当子类需要请求数据的时候,可以重写该方法,在该方法中请求
     */
    public void initData() {

    }
}
