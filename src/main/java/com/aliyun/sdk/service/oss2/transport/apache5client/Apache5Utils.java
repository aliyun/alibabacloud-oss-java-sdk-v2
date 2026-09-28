package com.aliyun.sdk.service.oss2.transport.apache5client;

import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

public class Apache5Utils {

    /**
     * Whether HttpCore5 encodes String bodies with no explicit charset as UTF-8.
     * <p>
     * 5.3 and later encode with UTF-8; earlier releases fall back to ISO-8859-1 (blocking
     * {@link StringEntity}) or US-ASCII (async), so the bytes sent would depend on the HttpCore5
     * version. Detected behaviorally, so it stays correct even when the jar manifest is
     * unavailable (shaded or repackaged builds).
     */
    public static final boolean STRING_ENTITY_UTF8_BY_DEFAULT = detectStringEntityUtf8ByDefault();

    private static boolean detectStringEntityUtf8ByDefault() {
        // U+4E2D encodes to 3 bytes in UTF-8 but is not representable in ISO-8859-1.
        return new StringEntity("\u4e2d", (ContentType) null).getContentLength() == 3L;
    }

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
