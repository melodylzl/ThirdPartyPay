package com.byvoid.thirdpartypay.pay;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * @author melody
 * @date 2018/8/20
 */
public class AliPayResult implements Serializable {

    private static final long serialVersionUID = 6579332530306668729L;
    /**
     * 订单支付成功
     */
    private static final String PAY_SUCCESS = "9000";


    /**
     * 描述信息
     */
    private String mMemo = "";
    /**
     * 处理结果(类型为json结构字符串)
     */
    private String mResult = "";
    /**
     * 结果码
     */
    private String mResultStatus = "";


    public AliPayResult(Map<String, String> rawResult) {
        if (rawResult == null) {
            return;
        }
        for (String key : rawResult.keySet()) {
            if (TextUtils.equals(key, "resultStatus")) {
                mResultStatus = rawResult.get(key);
            } else if (TextUtils.equals(key, "result")) {
                mResult = rawResult.get(key);
            } else if (TextUtils.equals(key, "memo")) {
                mMemo = rawResult.get(key);
            }
        }
    }


    public String getResultStatus() {
        return mResultStatus;
    }

    public String getMemo() {
        return mMemo;
    }

    public String getResult() {
        return mResult;
    }

    public boolean isPaySuccess(){
        return PAY_SUCCESS.equals(mResultStatus);
    }

    @Override
    public String toString() {
        return "resultStatus=" + mResultStatus + ",result=" + mResult + ",memo=" + mMemo;
    }
}
