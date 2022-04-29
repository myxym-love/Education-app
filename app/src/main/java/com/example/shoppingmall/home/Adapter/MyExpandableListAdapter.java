package com.example.shoppingmall.home.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.example.shoppingmall.R;
import com.example.shoppingmall.home.bean.JsonResult;

import java.util.List;


public class MyExpandableListAdapter implements ExpandableListAdapter {

    //数据
    private List<JsonResult.ChapterDTO> chapter_list;
    private Context context;
    private GroupViewHolder groupViewHolder;
    private ChildViewHolder childViewHolder;

    public MyExpandableListAdapter(List<JsonResult.ChapterDTO> chapter_list, Context context) {
        this.chapter_list = chapter_list;
        this.context = context;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    /***
     * 一级列表个数
     * @return
     */
    @Override
    public int getGroupCount() {
        return chapter_list.size();
    }

    /***
     * 每个二级列表的个数
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return chapter_list.get(groupPosition).getEpisodeList().size();
    }

    /***
     * 一级列表中单个item
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return chapter_list.get(groupPosition).getTitle();
    }

    /***
     * 二级列表中单个item
     * @param groupPosition 一级列表的位置
     * @param childPosition 二级列表的位置
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return chapter_list.get(groupPosition).getEpisodeList().get(childPosition).getTitle();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /***
     * 每个item的id是否固定，一般为true
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /***
     * 填充一级列表
     * @param groupPosition
     * @param isExpanded 是否展开
     * @param convertView
     * @param parent
     * @return
     */
    @SuppressLint("SetTextI18n")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_video_chapter, null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tv_group = convertView.findViewById(R.id.tv_group);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        //设置显示数据
        groupViewHolder.tv_group.setText("第" + (groupPosition + 1) + "章  " + chapter_list.get(groupPosition).getTitle());
        return convertView;
    }

    /***
     * 填充二级列表
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @SuppressLint("SetTextI18n")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_video_episode, null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tv_child = convertView.findViewById(R.id.tv_child);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.tv_child.setText("第" + (childPosition + 1) + "节  " + chapter_list.get(groupPosition).getEpisodeList().get(childPosition).getTitle());
        return convertView;
    }

    /***
     * 二级列表中每个能否被选中，如果有点击事件一定要设为true
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    class GroupViewHolder {
        TextView tv_group;
    }

    class ChildViewHolder {
        TextView tv_child;
    }
}
