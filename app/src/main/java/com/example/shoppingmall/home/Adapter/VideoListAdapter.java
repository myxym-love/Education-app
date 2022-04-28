package com.example.shoppingmall.home.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.R;
import com.example.shoppingmall.home.bean.VideoResult;

import java.util.List;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/28
 */
public class VideoListAdapter extends BaseAdapter {

    public Context mContext;
    public List<VideoResult> video_info;
    public ViewHolder viewHolder;

    public VideoListAdapter(Context mContext, List<VideoResult> video_info) {
        this.mContext = mContext;
        this.video_info = video_info;
    }

    @Override
    public int getCount() {
        return video_info.size();
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
            viewHolder = new ViewHolder();
            viewHolder.iv_video_img = view.findViewById(R.id.video_image);
            viewHolder.tv_video_title = view.findViewById(R.id.video_title);
//            viewHolder.tv_video_price = view.findViewById(R.id.video_price);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        VideoResult videoResult = video_info.get(i);
        Glide.with(mContext)
                .load(videoResult.get("cover_img"))
                .into(viewHolder.iv_video_img);

        viewHolder.tv_video_title.setText((String) videoResult.get("title"));
//        viewHolder.tv_video_price.setText(videoResult.getPrice());

        return view;
    }


    static class ViewHolder {
        ImageView iv_video_img;
        TextView tv_video_title;
//        TextView tv_video_price;
    }
}
