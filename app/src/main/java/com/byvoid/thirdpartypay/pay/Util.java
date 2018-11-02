package com.byvoid.thirdpartypay.pay;

import java.util.concurrent.ThreadFactory;

/**
 * @author melody
 * @date 2018/11/1
 */
public class Util {

    public static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            @Override public Thread newThread(Runnable runnable) {
                Thread result = new Thread(runnable, name);
                result.setDaemon(daemon);
                return result;
            }
        };
    }

}
