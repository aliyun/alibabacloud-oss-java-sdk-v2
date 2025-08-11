package com.aliyun.sdk.service.oss2.utils;

/**
 * A Base 16 codec API, which encodes into hex string in upper case.
 */

public class Base16Utils {
    private static final char[] DIGITS_LOWER;
    private static final char[] DIGITS_UPPER;

    static {
        DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }

    public static char[] encode(byte[] data) {
        return encodeHex(data, DIGITS_UPPER);
    }

    public static char[] encode(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    public static String encodeToString(byte[] src) {
        return src == null ? null : new String(encode(src));
    }

    public static String encodeToString(byte[] src, boolean toLowerCase) {
        return src == null ? null : new String(encode(src, toLowerCase));
    }

    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;

        for (int j = 0; i < l; ++i) {
            out[j++] = toDigits[(240 & data[i]) >>> 4];
            out[j++] = toDigits[15 & data[i]];
        }

        return out;
    }
}
