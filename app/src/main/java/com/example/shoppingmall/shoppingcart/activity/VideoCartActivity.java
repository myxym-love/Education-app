package com.example.shoppingmall.shoppingcart.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.EnvUtils;
import com.example.shoppingmall.R;
import com.example.shoppingmall.app.MainActivity;
import com.example.shoppingmall.home.bean.JsonResult;
import com.example.shoppingmall.shoppingcart.adapter.ShopCartAdapter;
import com.example.shoppingmall.shoppingcart.utils.CartStorage;

import java.util.List;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/30
 */
public class VideoCartActivity extends Activity implements View.OnClickListener{

    private ImageButton ibShopcartBack;
    private TextView tvShopcartEdit;
    private RecyclerView recyclerview;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;
    private LinearLayout ll_check_all;
    private LinearLayout ll_delete;
    private CheckBox cb_all;
    private Button btn_delete;
    private Button btn_collection;
    private Button btnCheckOut;
    private ShopCartAdapter adapter;
    private LinearLayout ll_empty_shopcart;
    private TextView tv_empty_cart_tobuy;


    /**
     * 编辑状态
     */
    private static final int ACTION_EDIT = 0;
    /**
     * 完成状态
     */
    private static final int ACTION_COMPLETE = 1;


    private void findViews() {
        ibShopcartBack = (ImageButton) findViewById(R.id.ib_shopcart_back);
        tvShopcartEdit = (TextView) findViewById(R.id.tv_shopcart_edit);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        checkboxAll = (CheckBox) findViewById(R.id.checkbox_all);
        tvShopcartTotal = (TextView) findViewById(R.id.tv_shopcart_total);
        btnCheckOut = (Button) findViewById(R.id.btn_check_out);
        ll_check_all = (LinearLayout) findViewById(R.id.ll_check_all);
        ll_delete = (LinearLayout) findViewById(R.id.ll_delete);
        cb_all = (CheckBox) findViewById(R.id.cb_all);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_collection = (Button) findViewById(R.id.btn_collection);
        ll_empty_shopcart = (LinearLayout) findViewById(R.id.ll_empty_shopcart);
        tv_empty_cart_tobuy = (TextView) findViewById(R.id.tv_empty_cart_tobuy);

        ibShopcartBack.setOnClickListener(this);
        btnCheckOut.setOnClickListener(this);
        tvShopcartEdit.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        tv_empty_cart_tobuy.setClickable(true);
        tv_empty_cart_tobuy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == ibShopcartBack) {
            finish();
        } else if (v == btnCheckOut) {
            // TODO
            Toast.makeText(VideoCartActivity.this, "结算", Toast.LENGTH_SHORT).show();
        } else if (v == tvShopcartEdit) {
            //设置编辑的点击事件
            int tag = (int) tvShopcartEdit.getTag();
            if (tag == ACTION_EDIT) {
                //变成完成状态
                showDelete();
            } else {
                //变成编辑状态
                hideDelete();
            }
        } else if (v == btn_delete) {
            adapter.deleteData();
            adapter.showTotalPrice();
            //显示空空如也
            checkData();
            adapter.checkAll();
        } else if (v == tv_empty_cart_tobuy) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void hideDelete() {
        tvShopcartEdit.setText("编辑");
        tvShopcartEdit.setTag(ACTION_EDIT);

        adapter.checkAll_none(true);
        ll_delete.setVisibility(View.GONE);
        ll_check_all.setVisibility(View.VISIBLE);

        adapter.showTotalPrice();
    }


    private void showDelete() {
        tvShopcartEdit.setText("完成");
        tvShopcartEdit.setTag(ACTION_COMPLETE);

        adapter.checkAll_none(false);
        cb_all.setChecked(false);
        checkboxAll.setChecked(false);

        ll_delete.setVisibility(View.VISIBLE);
        ll_check_all.setVisibility(View.GONE);

        adapter.showTotalPrice();
    }



    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_cart);
        findViews();

        showData();
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
    }

    //-----------------------------------------
    private void checkData() {
        if (adapter != null && adapter.getItemCount() > 0) {
            tvShopcartEdit.setVisibility(View.VISIBLE);
            ll_empty_shopcart.setVisibility(View.GONE);
            ll_check_all.setVisibility(View.GONE);
        } else {
            ll_empty_shopcart.setVisibility(View.VISIBLE);
            tvShopcartEdit.setVisibility(View.GONE);
            ll_check_all.setVisibility(View.GONE);
            ll_delete.setVisibility(View.GONE);
        }
    }

    private void showData() {
        CartStorage cartProvider = CartStorage.getInstance();

        List<JsonResult> datas = cartProvider.getDataFromLocal();
        List<JsonResult> datas2 = cartProvider.getDataFromLocal();
        if (datas != null && datas.size() > 0) {
            tvShopcartEdit.setVisibility(View.VISIBLE);
            ll_empty_shopcart.setVisibility(View.GONE);
            adapter = new ShopCartAdapter(this, datas, datas2,tvShopcartTotal, cartProvider, checkboxAll, cb_all);
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
            recyclerview.setAdapter(adapter);
        } else {
            //显示空的
            tvShopcartEdit.setVisibility(View.GONE);
            ll_empty_shopcart.setVisibility(View.VISIBLE);
            ll_check_all.setVisibility(View.GONE);
            ll_delete.setVisibility(View.GONE);
        }

    }
}
