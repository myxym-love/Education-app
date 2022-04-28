package com.example.shoppingmall.home.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.R;
import com.example.shoppingmall.home.bean.ChannelResult;

import java.util.List;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/28
 */
public class ChannelAdapter extends BaseAdapter {
    public Context mContext; // 上下文
    public List<ChannelResult.DataDTO> channel_info; // 频道数据
    public ViewHolder viewHolder;

    /**
     * 构造方法
     * @param mContext 上下文
     * @param channel_info 频道数据
     */
    public ChannelAdapter(Context mContext, List<ChannelResult.DataDTO> channel_info) {
        this.mContext = mContext;
        this.channel_info = channel_info;
    }

    /**
     * 获取频道数量
     * @return 频道数量
     */
    @Override
    public int getCount() {
        return channel_info.size();
    }

    /**
     * 获取频道数据
     * @param i 位置
     * @return 频道数据
     */
    @Override
    public Object getItem(int i) {

        return null;
    }

    /**
     * 获取频道ID
     * @param i 位置
     * @return 频道ID
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * 获取频道视图
     * @param i 位置
     * @param view 视图
     * @param viewGroup 视图组
     * @return 频道视图
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(mContext, R.layout.channel_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_channel = view.findViewById(R.id.channel_image);
            viewHolder.tv_channel = view.findViewById(R.id.channel_title);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ChannelResult.DataDTO channelResult = channel_info.get(i);
        Glide.with(mContext)
                .load(channelResult.getImg())
                .into(viewHolder.iv_channel); // 加载图片

        viewHolder.tv_channel.setText(channelResult.getTitle()); // 设置频道名称
        return view;
    }

    static class ViewHolder {
        ImageView iv_channel;
        TextView tv_channel;
    }
}
