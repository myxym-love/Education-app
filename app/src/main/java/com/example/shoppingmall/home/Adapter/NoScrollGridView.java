package com.example.shoppingmall.home.Adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/28
 */
public class NoScrollGridView extends GridView {

    public NoScrollGridView(Context context) {
        super(context);
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 解决GridView和ScrollView滑动冲突
     * @param widthMeasureSpec 宽度
     * @param heightMeasureSpec 高度
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

