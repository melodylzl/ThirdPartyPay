package com.byvoid.thirdpartypay.pay;

import android.app.Activity;

import com.alipay.sdk.app.PayTask;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 第三方支付工具类
 * @author melody
 * @date 2018/8/20
 */
public class ThirdPartyPay {


    private static final ExecutorService PAY_EXECUTOR_SERVICE = new ThreadPoolExecutor(1,1, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), Util.threadFactory("thirdParty-pay-", false));

    /**
     * 支付宝支付
     * @param activity activity
     * @param orderInfo app支付请求参数字符串，服务端生成返回
     * @param iAliPayCallback 支付完成回调
     */
    public static void aliPay(final Activity activity, final String orderInfo, final IAliPayCallback iAliPayCallback){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PayTask aliPayTask = new PayTask(activity);
                Map<String, String> result = aliPayTask.payV2(orderInfo,true);
                final AliPayResult aliPayResult = new AliPayResult(result);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iAliPayCallback.finish(aliPayResult);
                    }
                });
            }
        };
        PAY_EXECUTOR_SERVICE.execute(runnable);
    }


}
