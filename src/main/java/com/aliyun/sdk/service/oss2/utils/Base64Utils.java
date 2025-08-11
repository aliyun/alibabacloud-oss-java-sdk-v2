package com.aliyun.sdk.service.oss2.utils;

import java.util.Base64;

public final class Base64Utils {

    private Base64Utils() {
    }

    public static byte[] encode(byte[] src) {
        return src == null ? null : Base64.getEncoder().encode(src);
    }

    public static byte[] encodeURLWithoutPadding(byte[] src) {
        return src == null ? null : Base64.getUrlEncoder().withoutPadding().encode(src);
    }

    public static String encodeToString(byte[] src) {
        return src == null ? null : Base64.getEncoder().encodeToString(src);
    }

    public static byte[] decode(byte[] encoded) {
        return encoded == null ? null : Base64.getDecoder().decode(encoded);
    }

    /**
     * Decodes a byte array in base64 URL format.
     */
    public static byte[] decodeURL(byte[] src) {
        return src == null ? null : Base64.getUrlDecoder().decode(src);
    }

    /**
     * Decodes a base64 encoded string.
     */
    public static byte[] decodeString(String encoded) {
        return encoded == null ? null : Base64.getDecoder().decode(encoded);
    }
}
