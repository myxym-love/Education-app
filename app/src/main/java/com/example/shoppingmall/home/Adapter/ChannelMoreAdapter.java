package com.example.shoppingmall.home.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.R;
import com.example.shoppingmall.type.bean.TypeBean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/5/2
 */
public class ChannelMoreAdapter extends BaseAdapter {

    public final List<TypeBean.DataDTO.VideoListDTO> mTypeBeans;
    public final Context mContext;
    public ChannelMoreAdapter.ViewHolder viewHolder;
    public DecimalFormat df = new DecimalFormat("#.00");

    public ChannelMoreAdapter(Context mContext, List<TypeBean.DataDTO.VideoListDTO> videoList) {
        this.mTypeBeans = videoList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mTypeBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(mContext, R.layout.video_list_item, null);
            viewHolder = new ChannelMoreAdapter.ViewHolder();  // 创建ViewHolder对象
            viewHolder.iv_video_img = view.findViewById(R.id.video_image);
            viewHolder.tv_video_title = view.findViewById(R.id.video_title);
            viewHolder.tv_video_price = view.findViewById(R.id.video_price);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ChannelMoreAdapter.ViewHolder) view.getTag();
        }

        TypeBean.DataDTO.VideoListDTO videoList = mTypeBeans.get(i);
        Glide.with(mContext)
                .load(videoList.getCoverImg())
                .into(viewHolder.iv_video_img);

        viewHolder.tv_video_title.setText((String) videoList.getTitle());
        String format = df.format(videoList.getPrice()/100);
        viewHolder.tv_video_price.setText(format);
        return view;
    }

    static class ViewHolder {
        ImageView iv_video_img;
        TextView tv_video_title;
        TextView tv_video_price;
    }
}
