package com.example.shoppingmall.home.videoDetail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.shoppingmall.R;
import com.example.shoppingmall.base.BaseFragment;
import com.example.shoppingmall.home.bean.JsonResult;

import java.io.File;


/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/29
 */
public class VideoDetailFragment extends BaseFragment {

    public SubsamplingScaleImageView imageView;

    @Override
    public View initView() {
        View view = View.inflate(mContext, com.example.shoppingmall.R.layout.fragment_video_detail, null);
        imageView = (SubsamplingScaleImageView) view.findViewById(R.id.imageView);
        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initData() {
        JsonResult video_info = (JsonResult) requireActivity().getIntent().getSerializableExtra("video_info");
        String summary = video_info.getSummary();
        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        imageView.setMinScale(1.0F);
        Glide.with(mContext)
                .load(summary)
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                        imageView.setImage(ImageSource.uri(Uri.fromFile(resource)),new ImageViewState(1.0F,new PointF(0,0),0));
                    }
                });
    }
}
