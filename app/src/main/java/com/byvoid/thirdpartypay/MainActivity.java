package com.byvoid.thirdpartypay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alipay.sdk.app.EnvUtils;
import com.byvoid.thirdpartypay.R;
import com.byvoid.thirdpartypay.alipaytest.OrderInfoUtil2_0;
import com.byvoid.thirdpartypay.pay.AliPayResult;
import com.byvoid.thirdpartypay.pay.IAliPayCallback;
import com.byvoid.thirdpartypay.pay.ThirdPartyPay;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //沙箱测试开启，正式版中移除该行代码
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.payButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //在支付宝开放平台，申请支付功能应用后会提供APPID和RSA2私钥，以下是沙箱环境的测试数据
                String APPID = "2016091700529646";
                String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCAYH1c4bim8OGbXPP3xupD9+kZ6/uTSFpuEBTZ/RFeA75PSrbZakd+vhhSP6WQ1AGCQPf/Kuytv0TxkHONYUJHRdawPNejLsLcuNmsqS57k6+XqBM/MygwQhGE0chcqTTecF+3K6KRIPGECClk5F6y9ySuGZks98x1g0Wu35Aw1udE6n9RC/CbT4ykBEbMrPhF08nk52HHUu4ycJbtSyeoI9N0XQVCPRHz6zbLy8oC35adyrSBIdxgWotvwAImB119JaCPTP+7keDVGT/bFhRxfsQVxhOG+Zal3QxrnzUnRYJ2Mko50y597rNMQ7yCbIc5UQpeLIngLVrEE2La5JZ7AgMBAAECggEAHANyN7t1zNARmxJ8llwMsMo/To3FNw151jyiIDNt/8L3W1wlbGoZsdmPexsyaNkqfx7oPwFB5mQvijsITBimGuFtiMWuFIjWss2PinLcltDSZ1ig4pCqO2WfZwlUHIw74jJJYLIf5mV27mXNipue4pS1p1wkpcBpGFeN9CwKze00NJxZvC/nqPYdGzDV8a6+QFmOtODMEX8GJVRcFb3Syo7cgGQedT1CT0MUpayE/oeTWgRV/tbZYv3bKsKA5yoFpGy4i8MZRVfkInWYZrbG2DHE/HFtiJTbnXPQ+dfPtiDVGlA0rmYpts9O84ZcQaIqgrddtvbEfpty6LFGwd5zSQKBgQD4aKHGPipdcKOqJCndJJVVVhjB8c9vFTzg8J12m0ZzsKb0Fw1xAQ36psKoCr1ntV8xWI5DhZFr6MQCGTaYgzU7vGJN2jELCuluomylM+Gt44yXZa+ySRLToaTuWP/EJgADMPdV+0qk1OVaaaPbT8JqOYByzFT6sAqYcjpvCtCC9wKBgQCETNEV3LOk333unOOu1YfAKreg/ngvFn2Ga++03BttctAcQjc8lnf5M2RiRmDy2efULGnIsZUyaGLIRDG9hnwxf2sbtRkAaD8E+oYsagjIvbryfQbcOG9FJmuFYnbi1mg4fOV6RmUFOva8DxWr0VR9rxD5REBJ+gXEsrAPDiejnQKBgQDGygTcPpdsm11+JN6TqFkRJ4s8eaSEwrwrTqLUbcJdhrq0j1YkXrGvKpCQJbgZLF33gWSnyFXtwyJIg6DbP/TzzvfkUX351kukS7siIXiyzWyPI8MMq8mzLqJsUzCq6bYM17y3RBgh/HVmey+zrAprM4UFWVuQGh0AjjkQ79iZVQKBgBM2zM7uBLEXPEThSDfmDxcbnRummXHNRGG3X1LW/KzClfLksvz7pWTtz9BuBDqA5yhrYtmg4Ng3TNFlBgxOcc8GgFm/zWe/vBdPAxmENM5ir7nz6oElORPD5q2yhS2gkfZSPUmq25UM4TSixDT1691tBXwOK4AS7a+JfARTu/IJAoGAaiximORQMQ5Y4dJnYHP/YB+lzewxhcuO0xtNz+8i9TEO3okJO/LTKzXal+QHjVmlBOZn+FglXA0hHg5qluSKSKi7twybszGgsPu2Q7VHOkTZw/ssdR2gaXc0TbX7FW7qfxTBEuWxQlkUT16lhb6+QfLBAbFJOt5zyUqKtG5oygs=";

                /**
                 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
                 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
                 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
                 *
                 * orderInfo的获取必须来自服务端；
                 */
                boolean rsa2 = (RSA2_PRIVATE.length() > 0);
                Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
                String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

                String sign = OrderInfoUtil2_0.getSign(params, RSA2_PRIVATE, rsa2);
                String orderInfo = orderParam + "&" + sign;

                ThirdPartyPay.aliPay(MainActivity.this, orderInfo, new IAliPayCallback() {
                    @Override
                    public void finish(AliPayResult aliPayResult) {
                        Log.i("AliPayResult",aliPayResult.getMemo());
                        // 请求服务端再次确认是否支付成功
                    }
                });
            }
        });
    }
}
