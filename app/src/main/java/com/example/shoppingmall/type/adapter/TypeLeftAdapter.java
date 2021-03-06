package com.example.shoppingmall.type.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shoppingmall.R;
import com.example.shoppingmall.type.bean.TypeBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/3.
 */
public class TypeLeftAdapter extends BaseAdapter {
    private Context mContext;
    private int mSelect = 0;//选中项
    List<TypeBean.DataDTO> resultBean;

    public TypeLeftAdapter(Context mContext, List<TypeBean.DataDTO> resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
    }

    @Override
    public int getCount() {
        return resultBean.size();
    }

    @Override
    public Object getItem(int position) {
        return resultBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_type, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(resultBean.get(position).getTitle());

        if (mSelect == position) {
            convertView.setBackgroundResource(R.drawable.type_item_background_selector);  //选中项背景
            holder.tv_name.setTextColor(Color.parseColor("#fd3f3f"));
        } else {
            convertView.setBackgroundResource(R.drawable.bg2);  //其他项背景
            holder.tv_name.setTextColor(Color.parseColor("#323437"));
        }
        return convertView;
    }

    public void changeSelected(int positon) { //刷新方法
        if (positon != mSelect) {
            mSelect = positon;
            notifyDataSetChanged();
        }
    }

    static class ViewHolder {
        private TextView tv_name;
    }
}
