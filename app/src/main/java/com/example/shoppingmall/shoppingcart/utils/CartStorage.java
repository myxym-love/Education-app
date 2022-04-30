package com.example.shoppingmall.shoppingcart.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.example.shoppingmall.home.bean.JsonResult;
import com.example.shoppingmall.utils.CacheUtils;
import com.example.shoppingmall.utils.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/30
 */
public class CartStorage {

    public static final String JSON_CART = "json_cart";
    @SuppressLint("StaticFieldLeak")
    private static CartStorage cartStorage;
    @SuppressLint("StaticFieldLeak")
    private final Context mContext;
    // 商品数量的集合
    private SparseArray<JsonResult> mCartMap;


    /**
     * 得到购物车的单例
     */
    public static CartStorage getInstance() {
        if (cartStorage == null) {
            cartStorage = new CartStorage(MyApplication.getContext());
        }
        return cartStorage;
    }


    public CartStorage(Context mContext) {
        this.mContext = mContext;
        // 读取之前购物车数据
        mCartMap = new SparseArray<JsonResult>(100);

        listToSparseArray();
    }

    private void listToSparseArray() {
        List<JsonResult> carts = getAllData();
        if (carts != null && carts.size() > 0) {
            for (int i = 0; i < carts.size(); i++) {
                JsonResult videoBeans = carts.get(i);
                mCartMap.put(videoBeans.getId(), videoBeans);
            }
        }
    }


    private List<JsonResult> parsesToList() {
        List<JsonResult> carts = new ArrayList<>();
        if (mCartMap != null && mCartMap.size() > 0) {
            for (int i = 0; i < mCartMap.size(); i++) {
                JsonResult shoppingCart = mCartMap.valueAt(i);
                carts.add(shoppingCart);
            }
        }
        return carts;
    }

    /**
     * 获取本地所有数据
     *
     * @return List<JsonResult>
     */
    public List<JsonResult> getAllData() {
        return getDataFromLocal();
    }

    public List<JsonResult> getDataFromLocal() {
        List<JsonResult> carts = new ArrayList<>();
        //从本地获取缓存数据
        String savaJson = CacheUtils.getString(mContext, JSON_CART);
        if (!TextUtils.isEmpty(savaJson)) {
            //把数据转换成列表
            carts = new Gson().fromJson(savaJson, new TypeToken<List<JsonResult>>() {
            }.getType());
        }
        return carts;

    }


    /**
     * 添加商品到购物车
     * @param cart
     */
    public void addData(JsonResult cart) {

        //添加数据
        JsonResult tempCart = mCartMap.get(cart.getId());
        if (tempCart != null) {
            tempCart.setNumber(tempCart.getNumber() + cart.getNumber());
        } else {
            tempCart = cart;
            tempCart.setNumber(1);
        }

        mCartMap.put(tempCart.getId(), tempCart);

        //保存数据
        commit();
    }

    private void commit() {
        //把parseArray转换成list
        List<JsonResult> carts = parsesToList();
        //把转换成String
        String json = new Gson().toJson(carts);

        // 保存
        CacheUtils.putString(mContext, JSON_CART, json);

    }

    /**
     * 删除购物车中的商品
     * @param cart
     */
    public void deleteData(JsonResult cart) {

        //删除数据
        mCartMap.delete(cart.getId());
        //保存数据
        commit();
    }



    public void updateData(JsonResult cart) {
        //修改数据
        mCartMap.put(cart.getId(), cart);
        //保存数据
        commit();
    }

    /**
     * 清空购物车
     * @return 是否清空成功
     */
    public boolean clearCart() {
        //清空数据
        mCartMap.clear();
        //保存数据
        commit();
        return true;
    }


    public JsonResult findData(JsonResult goods_bean) {
        JsonResult goodsBean = mCartMap.get(goods_bean.getId());
        if(goodsBean != null){
            return goods_bean;
        }
        return null;
    }

}
