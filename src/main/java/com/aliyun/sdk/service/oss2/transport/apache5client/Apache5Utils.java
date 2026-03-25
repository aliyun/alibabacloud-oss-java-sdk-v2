package com.aliyun.sdk.service.oss2.transport.apache5client;

public class Apache5Utils {

    public static boolean hasBuildClassicMethod() {
        try {
            Class<?> clazz = Class.forName("org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder");
            clazz.getMethod("buildClassic");
            return true;
        } catch (ClassNotFoundException e) {
            // TODO
        } catch (NoSuchMethodException e) {
            // no buildClassic < 5.4
        }
        return false;
    }

    public static boolean hasBuildAsyncMethod() {
        try {
            Class<?> clazz = Class.forName("org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder");
            clazz.getMethod("buildAsync");
            return true;
        } catch (ClassNotFoundException e) {
            // TODO
        } catch (NoSuchMethodException e) {
            // no buildClassic < 5.4
        }
        return false;
    }

    public static boolean hasTlsSocketStrategy() {
        try {
            Class.forName("org.apache.hc.client5.http.ssl.TlsSocketStrategy");
            return true;
        } catch (ClassNotFoundException e) {
            // TODO
        }
        return false;
    }
}
