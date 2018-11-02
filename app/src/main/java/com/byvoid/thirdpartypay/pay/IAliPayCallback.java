package com.byvoid.thirdpartypay.pay;


/**
 * @author melody
 * @date 2018/8/20
 */
public interface IAliPayCallback {

    /**
     * 支付宝支付结束回调，不根据支付同步返回结果校验成功失败
     * 需要请求服务端根据异步通知最终确定支付的结果
     * @param aliPayResult 同步返回的数据，不能用于真实的支付结果
     */
    void finish(AliPayResult aliPayResult);

}
