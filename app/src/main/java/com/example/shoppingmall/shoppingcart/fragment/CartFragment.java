package com.example.shoppingmall.shoppingcart.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.shoppingmall.R;
import com.example.shoppingmall.app.MainActivity;
import com.example.shoppingmall.base.BaseFragment;
import com.example.shoppingmall.home.bean.JsonResult;
import com.example.shoppingmall.shoppingcart.adapter.ShopCartAdapter;
import com.example.shoppingmall.shoppingcart.pay.AliPay;
import com.example.shoppingmall.shoppingcart.pay.AuthResult;
import com.example.shoppingmall.shoppingcart.pay.PayKeys;
import com.example.shoppingmall.shoppingcart.pay.PayResult;
import com.example.shoppingmall.shoppingcart.pay.SignUtils;
import com.example.shoppingmall.shoppingcart.utils.CartStorage;
import com.example.shoppingmall.utils.Constants;
import com.example.shoppingmall.utils.OrderInfoUtil2_0;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import android.provider.Settings.Global;
/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/27
 */
public class CartFragment extends BaseFragment {

    private FrameLayout fl_shopcart_content;
    private TextView tvShopcartEdit;
    private RecyclerView recyclerview;
    private LinearLayout llCheckAll;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;
    private Button btnCheckOut;
    private LinearLayout llDelete;
    private CheckBox cbAll;
    private Button btnDelete;
    private Button btnCollection;
    private ImageView ivEmpty;
    private TextView tvEmptyCartTobuy;
    private LinearLayout ll_empty_shopcart;
    public ShopCartAdapter adapter;
    /**
     * 编辑状态
     */
    private static final int ACTION_EDIT = 0;
    /**
     * 完成状态
     */
    private static final int ACTION_COMPLETE = 1;


    private void findViews(View view) {
        fl_shopcart_content = (FrameLayout) view.findViewById(R.id.fl_shopcart_content);
        tvShopcartEdit = (TextView) view.findViewById(R.id.tv_shopcart_edit);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        llCheckAll = (LinearLayout) view.findViewById(R.id.ll_check_all);
        checkboxAll = (CheckBox) view.findViewById(R.id.checkbox_all);
        tvShopcartTotal = (TextView) view.findViewById(R.id.tv_shopcart_total);
        btnCheckOut = (Button) view.findViewById(R.id.btn_check_out);
        llDelete = (LinearLayout) view.findViewById(R.id.ll_delete);
        cbAll = (CheckBox) view.findViewById(R.id.cb_all);
        btnDelete = (Button) view.findViewById(R.id.btn_delete);
        btnCollection = (Button) view.findViewById(R.id.btn_collection);
        ivEmpty = (ImageView) view.findViewById(R.id.iv_empty);
        tvEmptyCartTobuy = (TextView) view.findViewById(R.id.tv_empty_cart_tobuy);
        ll_empty_shopcart = (LinearLayout) view.findViewById(R.id.ll_empty_shopcart);
        tvEmptyCartTobuy.setClickable(true);
    }

    @Override
    public View initView() {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        View view = View.inflate(mContext, R.layout.fragment_shoppingcart, null);
        findViews(view);
        return view;
    }

    @Override
    public void initData() {
        initListener();
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
        llCheckAll.setVisibility(View.VISIBLE);
        showData();
    }


    @Override
    public void onResume() {
        super.onResume();
        showData();
    }


    /**
     * 监听器
     */
    private void initListener() {
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                payV2(v);
                AliPay aliPay = new AliPay(mContext,v,adapter,requireActivity(),tvShopcartTotal);
                aliPay.payV2(v);
            }
        });
        tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置编辑的点击事件
                int tag = (int) tvShopcartEdit.getTag();
                if (tag == ACTION_EDIT) {
                    //变成完成状态
                    showDelete();
                } else {
                    //变成编辑状态
                    hideDelete();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.deleteData();
                adapter.showTotalPrice();
                //显示空空如也
                checkData();
                adapter.checkAll();
            }
        });
        tvEmptyCartTobuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                Constants.isBackHome = true;
            }
        });

    }

    private void hideDelete() {
        tvShopcartEdit.setText("编辑");
        tvShopcartEdit.setTag(ACTION_EDIT);

        adapter.checkAll_none(true);
        llDelete.setVisibility(View.GONE);
        llCheckAll.setVisibility(View.VISIBLE);

        adapter.showTotalPrice();
    }

    private void showDelete() {
        tvShopcartEdit.setText("完成");
        tvShopcartEdit.setTag(ACTION_COMPLETE);

        adapter.checkAll_none(false);
        cbAll.setChecked(false);
        checkboxAll.setChecked(false);

        llDelete.setVisibility(View.VISIBLE);
        llCheckAll.setVisibility(View.GONE);

        adapter.showTotalPrice();
    }

    private void checkData() {
        if (adapter != null && adapter.getItemCount() > 0) {
            tvShopcartEdit.setVisibility(View.VISIBLE);
            ll_empty_shopcart.setVisibility(View.GONE);

        } else {
            ll_empty_shopcart.setVisibility(View.VISIBLE);
            tvShopcartEdit.setVisibility(View.GONE);

        }
    }


    /**
     * 显示数据
     */
    private void showData() {
        CartStorage cartProvider = CartStorage.getInstance();
        List<JsonResult> datas2 = cartProvider.getDataFromLocal();
        List<JsonResult> datas = cartProvider.getDataFromLocal();
        if (datas != null && datas.size() > 0) {
            tvShopcartEdit.setVisibility(View.VISIBLE);
            adapter = new ShopCartAdapter(mContext, datas,datas2, tvShopcartTotal, cartProvider, checkboxAll, cbAll);
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerview.setAdapter(adapter);
            ll_empty_shopcart.setVisibility(View.GONE);
        } else {
            //显示空的
            tvShopcartEdit.setVisibility(View.GONE);
            ll_empty_shopcart.setVisibility(View.VISIBLE);

        }
    }


    //-------------支付------


    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2021000118654038";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "2088621957157032";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "";

    /**
     *  pkcs8 格式的商户私钥。
     *
     * 	如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 	使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * 	RSA2_PRIVATE。
     *
     * 	建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 	工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC9JLyDw0GuJAunAvq4nSm6IXa1Hsd+4rUQDwUVFVvuqLcu7cKlIth7Fk94YkOdzrpmXIvhCCpnjGX91BHV0moX4ClwPGi5hAuQyrH7rI+hNS8K1x6w3IxDl5TNzVZo0d2nkZNc5TWLIV3kgKpmfQXGzBVhPm+Gv3uH0ThM/bewr/WYep2G8ppfBQB/6naHjr8XEGAF/Gr+0Ade/X4BBzNS67kBtOOfUCVs6XtlcG4/hQjtjX6Mpcww3y/nRBYorM0Ic8mEn5314qS/kr29TWnnClRwdH7+7gnn7G1xkdEhUxnCguM1EEL5JdPb1cgN+CEBxOlEPoJF/8Gk0q5HKIZnAgMBAAECggEAF12SbDSSyJBrYBZeIrBHl1bK81T6EBKxi2pPlmstEC6HcqSshvnnBE2dQ6Hf+H6xSDPmk8YOdvxBHTo1edSkho6Kj/LmfQRy6VAg7JZEqAWJ6izo/bBBI5h2KXZUVl6Wozy4l6E5HBbjIndClaacMBLGEV1FKcA94mlWKyyspIbhuMAvtgIl97aZTY98k1wx+pEKHIR7Z8bTlWvnn53jQMJfwskzHpWKUgQXKvPmasoalCJjazmuBbQCxUmXUuNA/kEXjy5W2vpfejSTro1hWbNYpFtrcTCLWVMSsBpioPo64BVoburpzmzS4YEOk+IIsFGzo4VysD7xcfa4HWVPAQKBgQDpvAz6gnchfOQ9utqi/fFnkGUhIvnM+qIGqXLmSLOyByHqCnpApoPJunb729vb7T+Btb8NfigdQwEk1+Hcq7Ibp5iVxPxx8+63D8F/wDRfecXfSl6K6UlRrNTubeAr+Co4g1qf7HlKviKwAsLnQQ9Yv4jlZYT4ubcrPb3S6nFZrQKBgQDPKUTlGSqmnGsZqHOuAkEYEjW45JKOc2nZXYajf3zmhkxSWBWEXrjZ8LoMuMmmmZdRpS7Vz9j2ymNXKU0VLDO9eRMtKbIorWyDzYn5FVlL+ULdoejuoGUVAnpHQwcPjW7URbmzdoX/hi+e125y24/Y4UYCL+RYSveV3jj3DuJK4wKBgAv8oenDARsNjaMAzy+muFeoVxdg9NCnVeHjlMnslpKdvQaErQpdv8p7rphyCQ95VlVvnbGyc5o0kn1yONoZUBQM/01HAe9SJD7tJu5HTChNVXGDHeAf4Ku4ol7r8aAvpPKrAd3LwdiscZj3O0mi04eoovuovi4HUDZ+fxqu/UstAoGAWmndmsuoFL1x9OnTSx/KvzG7Zq3D1Q5EJU0vZQ5QOeBlUQ8GnRYmQWBSIxlHUUH5i/W3AjRLsjJUtwbckKARGOHN38kx65T9Jm0/iYmqxoDMkSSIIBW66DeDnLSBUWVd+KFR0iSpiQNMp1vI5bI0DukLAAtc8rgNZMhvPkytsRcCgYEAnV8J8sVUqG346igNGfdi1xaatGwWaO8ruxwdm2j+33kgAKKnQUHDHYJUDAsUiOcHBKnxET0ZOv/W8tp3B/JZU6mIvbXhcUfgI/ySDmZKuAZxlgeNqPeJ2nJGBjJJBnU41Kh/LR6ZTR8BK45DfAYa4HZu+Yuox3wdJuH7v2lk7NU=";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;


    /**
     * 支付回调
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {

                        // 支付成功清除响应的商品 1.全部清空 2.清除已支付的商品


                        // 1.获取本地的数据
                        CartStorage cartProvider = CartStorage.getInstance();

                        // TODO
                        String ids = (String) ((Map<?, ?>) msg.obj).get("ids");
                        String replace = Objects.requireNonNull(ids).replace("[", "");
                        String replace1 = replace.replace("]", "");
                        String[] split = replace1.split(", ");
                        for (String s : split) {
                            if (!s.equals("null")){
                                cartProvider.delete(Integer.parseInt(s));
                            }
                        }
                        Intent intent = new Intent();
                        intent.setClass(mContext,MainActivity.class);
                        startActivity(intent);
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        showAlert(mContext, getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(mContext, getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        showAlert(mContext, getString(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        showAlert(mContext, getString(R.string.auth_failed) + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

     /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(mContext, getString(R.string.error_missing_appid_rsa_private));
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);

        /*
         * 获取本地购物车的所有数据,根据isChecked来判断是否选中,如果选中,则添加id到ids中记录,支付成功后根据id删除购物车中的数据
         */
        List<JsonResult> datas = adapter.datas;
        String[] ids = new String[datas.size()];
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).isChildSelected()) {
                ids[i] = String.valueOf(datas.get(i).getId());
            }
        }

        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,tvShopcartTotal.getText().toString().replace("￥", ""));
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                result.put("ids", Arrays.toString(ids));
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }
}
