package com.example.shoppingmall.shoppingcart.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.R;
import com.example.shoppingmall.home.bean.JsonResult;
import com.example.shoppingmall.shoppingcart.activity.VideoCartActivity;
import com.example.shoppingmall.shoppingcart.utils.CartStorage;
import com.example.shoppingmall.shoppingcart.view.NumberAddSubView;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/30
 */
public class ShopCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private CartStorage cartProvider;
    public DecimalFormat df = new DecimalFormat("#.00");
    private Context mContext;
    private List<JsonResult> datas;
    private List<JsonResult> datas2;
    private TextView tvShopcartTotal;
    private CheckBox checkboxAll;
    private CheckBox cb_all;

    public ShopCartAdapter(Context mContext, List<JsonResult> datas,List<JsonResult> datas2, TextView tvShopcartTotal, CartStorage cartProvider, CheckBox checkboxAll, CheckBox cbAll) {
        this.mContext = mContext;
        this.datas = datas;
        this.datas2 = datas2;
        this.tvShopcartTotal = tvShopcartTotal;
        this.cartProvider = cartProvider;
        this.checkboxAll = checkboxAll;
        this.cb_all = cbAll;

        // 首次加载数据
        showTotalPrice();
        checkboxAll.setChecked(true);
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setChildSelected(true);
            datas2.get(i).setChildSelected(true);
        }
        showTotalPrice();

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                JsonResult goodsBean = datas.get(position);
                JsonResult goodsBean2 = datas2.get(position);
                goodsBean.setChildSelected(!goodsBean.isChildSelected());
                goodsBean2.setChildSelected(!goodsBean2.isChildSelected());
                notifyItemChanged(position);
                checkAll();
                showTotalPrice();
            }
        });


        // 设置全选点击事件
        checkboxAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean checked = getCheckboxAll().isChecked();
                checkAll_none(checked);
                showTotalPrice();
            }
        });

        cb_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = getCb_all().isChecked();
                checkAll_none(checked);
                showTotalPrice();
            }
        });
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_shop_cart, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(datas.get(position));

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public void checkAll_none(boolean checked) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                datas.get(i).setChildSelected(checked);
                datas2.get(i).setChildSelected(checked);
                checkboxAll.setChecked(checked);
                notifyItemChanged(i);
            }
        } else {
            checkboxAll.setChecked(false);
        }
    }


    /**
     * 删除购物车商品
     */
    public void deleteData() {
        if (datas != null && datas.size() > 0) {
            for (Iterator iterator = datas.iterator(); iterator.hasNext(); ) {

                JsonResult cart = (JsonResult) iterator.next();

                if (cart.isChildSelected()) {

                    //这行代码放在前面
                    int position = datas.indexOf(cart);
                    //1.删除本地缓存的
                    cartProvider.deleteData(cart);

                    //2.删除当前内存的
                    iterator.remove();

                    //3.刷新数据
                    notifyItemRemoved(position);

                }
            }
            for (Iterator iterator = datas2.iterator(); iterator.hasNext(); ) {

                JsonResult cart2 = (JsonResult) iterator.next();

                if (cart2.isChildSelected()) {

                    //1.删除本地缓存的
                    cartProvider.deleteData(cart2);

                    //2.删除当前内存的
                    iterator.remove();

                }
            }
        }
    }


    /**
     * 全选
     */
    public void checkAll() {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                if (!datas.get(i).isChildSelected()) {
                    checkboxAll.setChecked(false);
                    cb_all.setChecked(false);
                    return;
                } else {
                    checkboxAll.setChecked(true);
                    cb_all.setChecked(true);
                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cbGov;
        private ImageView ivGov;
        private TextView tvDescGov;
        private TextView tvPriceGov;
        private NumberAddSubView numberAddSubView;

        ViewHolder(View itemView) {
            super(itemView);
            cbGov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            ivGov = (ImageView) itemView.findViewById(R.id.iv_gov);
            tvDescGov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tvPriceGov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            numberAddSubView = (NumberAddSubView) itemView.findViewById(R.id.numberAddSubView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClickListener(v, getLayoutPosition());
                    }
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void setData(final JsonResult goodsBean) {
            cbGov.setChecked(goodsBean.isChildSelected());
            Glide.with(mContext)
                    .load(goodsBean.getCoverImg())
                    .into(ivGov);
            tvDescGov.setText(goodsBean.getTitle());
            String format = df.format(goodsBean.getPrice() / 100);
            tvPriceGov.setText("￥" + format);

            //设置数字加减回调
            numberAddSubView.setValue(goodsBean.getNumber());

            //-------------------------------------------
            //cartProvider = new CartProvider(mContext);

            numberAddSubView.setOnNumberChangeListener(new NumberAddSubView.OnNumberChangeListener() {
                @Override
                public void addNumber(View view, int value) {
                    goodsBean.setNumber(value);
                    cartProvider.updateData(goodsBean);
                    showTotalPrice();
                }

                @Override
                public void subNumner(View view, int value) {
                    goodsBean.setNumber(value);
                    cartProvider.updateData(goodsBean);
                    showTotalPrice();
                }
            });
        }
    }


    @SuppressLint("SetTextI18n")
    public void showTotalPrice() {
        tvShopcartTotal.setText(getTotalPrice() + "");
    }

    private double getTotalPrice() {
        double total = 0;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                JsonResult goodsBean = datas.get(i);
                if (goodsBean.isChildSelected())
                    total += Double.parseDouble(df.format(((long) goodsBean.getPrice() * goodsBean.getNumber())/100) + "");
            }
        }
        return total;
    }

    //回调点击事件的监听
    private OnItemClickListener onItemClickListener;

    interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public CheckBox getCb_all() {
        return cb_all;
    }
    public void setCb_all(CheckBox cb_all) {
        this.cb_all = cb_all;
    }

    public CheckBox getCheckboxAll() {
        return checkboxAll;
    }

    public void setCheckboxAll(CheckBox checkboxAll) {
        this.checkboxAll = checkboxAll;
    }
}
