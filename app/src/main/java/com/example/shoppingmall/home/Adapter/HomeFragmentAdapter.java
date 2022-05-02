package com.example.shoppingmall.home.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoppingmall.R;
import com.example.shoppingmall.home.activity.ChannelActivity;
import com.example.shoppingmall.home.activity.VideoInfoActivity;
import com.example.shoppingmall.home.bean.ActResult;
import com.example.shoppingmall.home.bean.BannerResult;
import com.example.shoppingmall.home.bean.ChannelResult;
import com.example.shoppingmall.home.bean.JsonResult;
import com.example.shoppingmall.home.bean.ResultBean;
import com.example.shoppingmall.home.bean.VideoResult;
import com.example.shoppingmall.home.uitls.AlphaPageTransformer;
import com.example.shoppingmall.home.uitls.ScaleInTransformer;
import com.example.shoppingmall.type.bean.TypeBean;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/27
 */
public class HomeFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 横幅广告
     */
    public static final int BANNER = 0;

    /**
     * 频道
     */
    public static final int CHANNEL = 1;

    /**
     * 活动
     */
    public static final int ACT = 2;

    /**
     * 视频列表
     */
    public static final int VIDEO = 3;

    /**
     * 初始化布局
     */
    private final LayoutInflater mLayoutInflater;

    private final Context mContext;

    /**
     * 数据
     */
    private final ResultBean resultBean;

    /**
     * 当前类型
     */
    private int currentType = BANNER;

    /**
     * 构造方法
     * @param mContext 上下文
     * @param resultBean 数据
     */
    public HomeFragmentAdapter(Context mContext, ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 创建ViewHolder
     * @param parent   父容器
     * @param viewType 视图类型
     * @return ViewHolder
     */
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BANNER) { // 横幅广告
            return new BannerViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_banner, null), resultBean); // 创建ViewHolder
        } else if (viewType == CHANNEL) { // 频道
            return new ChannelViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_channel, null), resultBean); // 创建ViewHolder
        } else if (viewType == ACT) { // 活动
            return new ActViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_act, null), resultBean); // 创建ViewHolder
        } else if (viewType == VIDEO) { // 视频列表
            return new VideoListViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_video_list, null), resultBean); // 创建ViewHolder
        }
        return null;
    }

    /**
     * 绑定ViewHolder,传数据
     * @param holder   ViewHolder
     * @param position 位置
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info()); // 设置数据
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info()); // 设置数据
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info()); // 设置数据
        } else if (getItemViewType(position) == VIDEO) {
            VideoListViewHolder videoListViewHolder = (VideoListViewHolder) holder;
            videoListViewHolder.setData(resultBean.getJsonResult()); // 设置数据
        }
    }

    /**
     * 得到类型
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case VIDEO:
                currentType = VIDEO;
                break;

        }
        return currentType;
    }

    /**
     * 获取数据总数
     *
     * @return 数据总数
     */
    @Override
    public int getItemCount() {
        // 1->2->3->4->5->6
        return 4;
    }

    /**
     * 轮播图ViewHolder
     */
    static class BannerViewHolder extends RecyclerView.ViewHolder {
        public Banner banner; // 轮播图
        public Context mContext; // 上下文
        public ResultBean resultBean; // 数据

        /**
         * 构造方法
         * @param context 上下文
         * @param itemView itemView
         * @param resultBean 数据
         */
        public BannerViewHolder(Context context, View itemView, ResultBean resultBean) {
            super(itemView); // 继承父类
            banner = (Banner) itemView.findViewById(R.id.banner); // 得到轮播图
            this.mContext = context; // 得到上下文
            this.resultBean = resultBean; // 得到数据
        }

        /**
         * 设置数据
         *
         * @param data 数据
         */
        public void setData(List<BannerResult.DataDTO> data) {

            // 设置banner样式
            banner.setAdapter(new BannerImageAdapter<BannerResult.DataDTO>(data) {
                @Override
                public void onBindView(BannerImageHolder holder, BannerResult.DataDTO data, int position, int size) {
                    Glide.with(holder.itemView) // 传入Context
                            .load(data.getImg()) // 图片地址
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(30))) // 圆角
                            .into(holder.imageView); // 设置图片
                }
            })
                    .addBannerLifecycleObserver((LifecycleOwner) mContext) // 添加生命周期观察者
                    .setIndicator(new CircleIndicator(mContext)); // 设置指示器

            // 设置点击事件
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(Object data, int position) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(resultBean.getBanner_info().get(position).getUrl()))); // 打开网页
                        }
                    }.start(); // 启动线程
                }
            });

        }
    }

    /**
     * 频道ViewHolder
     */
    static class ChannelViewHolder extends RecyclerView.ViewHolder {


        public GridView gridView;
        public Context mContext;
        public ResultBean resultBean;
        public ChannelAdapter adapter;

        public ChannelViewHolder(Context mContext, View itemView, ResultBean resultBean) {
            super(itemView);
            gridView = (GridView) itemView.findViewById(R.id.gv_channel);
            this.mContext = mContext;
            this.resultBean = resultBean;
        }

        public void setData(List<ChannelResult.DataDTO> channel_info) {
            adapter = new ChannelAdapter(mContext, channel_info);
            gridView.setAdapter(adapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(mContext, ChannelActivity.class);
                    intent.putExtra("channel_info", resultBean.getListChannel().get(i));
                    mContext.startActivity(intent);
                }
            });
        }
    }

    /**
     * 活动ViewHolder
     */
    static class ActViewHolder extends RecyclerView.ViewHolder {

        public Context mContext;
        public ResultBean resultBean;
        public ViewPager viewPager;

        public ActViewHolder(Context mContext, View itemView, ResultBean resultBean) {
            super(itemView);
            viewPager = (ViewPager) itemView.findViewById(R.id.act_viewpager); // 得到ViewPager
            this.mContext = mContext;
            this.resultBean = resultBean;
        }

        public void setData(List<ActResult.DataDTO> act_info) {

            viewPager.setPageMargin(20);
            viewPager.setOffscreenPageLimit(3);
            viewPager.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));

            viewPager.setAdapter(new PagerAdapter() {
                /**
                 * 返回页面的数量
                 * @return 页面的数量
                 */
                @Override
                public int getCount() {
                    return act_info.size();
                }

                /**
                 * 判断是否由对象生成界面
                 * @param view 界面
                 * @param object 对象
                 * @return 是否
                 */
                @Override
                public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                    return view == object;
                }

                /**
                 * 初始化item
                 * @param container ViewPager
                 * @param position 位置
                 * @return View
                 */
                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    ImageView imageView = new ImageView(mContext);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(mContext)
                            .load(act_info.get(position).getImg())
                            .into(imageView); // 设置图片
                    // 设置ViewPager的监听器
                    imageView.setOnClickListener(new ViewPager.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(resultBean.getAct_info().get(position).getUrl()))); // 打开网页
                                }
                            }.start();
                        }
                    });
                    container.addView(imageView);
                    return imageView;
                }

                /**
                 * 销毁item
                 * @param container ViewPager
                 * @param position 位置
                 * @param object 对象
                 */
                @Override
                public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                    container.removeView((View) object);
                }
            });


        }
    }

    /**
     * 视频列表VideoHolder
     */

    static class VideoListViewHolder extends RecyclerView.ViewHolder {

        public GridView gridView;
        public Context mContext;
        public ResultBean resultBean;
        public VideoListAdapter adapter;

        public VideoListViewHolder(Context mContext, View itemView, ResultBean resultBean) {
            super(itemView);
            gridView = itemView.findViewById(R.id.gc_video_list);
            this.mContext = mContext;
            this.resultBean = resultBean;
        }

        public void setData(List<JsonResult> video_info) {
            adapter = new VideoListAdapter(mContext, video_info);
            gridView.setAdapter(adapter);


            /**
             * 设置item点击事件
             */
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                /**
                 * 点击事件
                 * @param adapterView AdapterView
                 * @param view View
                 * @param i 位置
                 * @param l l
                 */
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, VideoInfoActivity.class);
                    intent.putExtra("video_info", resultBean.getJsonResult().get(i));
                    mContext.startActivity(intent);
                }
            });
        }

    }
}
