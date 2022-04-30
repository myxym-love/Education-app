package com.example.shoppingmall.home.videoDetail;

import android.annotation.SuppressLint;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingmall.R;
import com.example.shoppingmall.base.BaseFragment;
import com.example.shoppingmall.home.Adapter.MyExpandableListAdapter;
import com.example.shoppingmall.home.bean.ChannelResult;
import com.example.shoppingmall.home.bean.ChapterResult;
import com.example.shoppingmall.home.bean.EpisodeResult;
import com.example.shoppingmall.home.bean.JsonResult;
import com.example.shoppingmall.home.bean.JsonResult.ChapterDTO;
import com.example.shoppingmall.home.bean.VideoResult;

import java.util.List;
import java.util.Objects;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/29
 */
public class VideoDirectoryFragment extends BaseFragment {

    public List<ChapterDTO> chapter_list;
    public List<JsonResult.EpisodeListDTO> episode_list;
    public ExpandableListView elv;
    public MyExpandableListAdapter myExpandableListAdapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_video_directory, null);
        elv = view.findViewById(R.id.elv);
        return view;
    }

    @Override
    public void initData() {
        JsonResult video_info = (JsonResult) requireActivity().getIntent().getSerializableExtra("video_info");
        chapter_list = (List<ChapterDTO>) video_info.getChapterList();
        myExpandableListAdapter = new MyExpandableListAdapter(chapter_list, requireContext());
        elv.setAdapter(myExpandableListAdapter); // 设置适配器
        initListener(); // 初始化监听器
    }

    private void initListener() {

        /**
         * 设置组点击监听
         */
        elv.setOnGroupClickListener((parent, view, position, id) -> {
//            Toast.makeText(requireActivity(), chapter_list.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            return false;
        });

        /**
         * 设置子项点击监听
         */
        elv.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
//            Toast.makeText(requireActivity(), chapter_list.get(groupPosition).getEpisodeList().get(childPosition).getTitle(), Toast.LENGTH_SHORT).show();
            return false;
        });
    }
}
