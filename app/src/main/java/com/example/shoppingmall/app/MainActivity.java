package com.example.shoppingmall.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import androidx.fragment.app.FragmentActivity;
import com.example.shoppingmall.R;
import com.example.shoppingmall.community.fragment.CommunityFragment;
import com.example.shoppingmall.home.fragment.HomeFragment;
import com.example.shoppingmall.shoppingcart.fragment.CartFragment;
import com.example.shoppingmall.type.fragment.TypeFragment;
import com.example.shoppingmall.user.fragment.UserFragment;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/27
 */
public class MainActivity extends FragmentActivity {

    // 定义Fragment页面
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    // 定义底部导航栏
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rg_main)
    RadioGroup rgMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this); // 实例化ButterKnife，绑定控件
        initListener(); // 初始化监听器各个按钮的点击事件
    }

    /**
     * 初始化监听器各个按钮的点击事件
     */
    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // 设置RadioGroup的监听器
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) { // 当RadioGroup中的按钮被选中时触发
                switch (checkedId) { // 判断选中的按钮
                    case R.id.rb_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit(); //替换Fragment  加载首页
                        break;
                    case R.id.rb_type:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new TypeFragment()).commit(); //替换Fragment  加载分类
                        break;
                    case R.id.rb_community:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new CommunityFragment()).commit(); //替换Fragment  加载社区
                        break;
                    case R.id.rb_cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new CartFragment()).commit();  //替换Fragment  加载购物车
                        break;
                    case R.id.rb_user:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new UserFragment()).commit(); //替换Fragment  加载我的
                        break;
                }
            }
        });
        rgMain.check(R.id.rb_home); // 默认加载首页
    }
}
