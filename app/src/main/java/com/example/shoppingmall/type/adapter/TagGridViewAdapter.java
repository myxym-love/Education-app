package com.example.shoppingmall.type.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.shoppingmall.R;
import com.example.shoppingmall.home.bean.ResultBean;

/**
 * Created by Administrator on 2016/10/2.
 */
public class TagGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private ResultBean result;
    private int[] colors = {Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"),
            Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"), Color.parseColor("#f0a420"),
            Color.parseColor("#f0839a"), Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2")
    };

    public TagGridViewAdapter(Context mContext, ResultBean result) {
        this.mContext = mContext;
        this.result = result;
    }

    @Override
    public int getCount() {

        return result.getChannel_info().size();
    }

    @Override
    public Object getItem(int position) {
        return result.getChannel_info().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_tab_gridview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvTag.setText(result.getChannel_info().get(position).getTitle());
        holder.tvTag.setTextColor(colors[position % colors.length]);

        return convertView;
    }

    static class ViewHolder {
        private TextView tvTag;

        ViewHolder(View view) {
            tvTag = (TextView) view.findViewById(R.id.tv_tag);
        }
    }
}
