package com.aliyun.sdk.service.oss2.encryption.internal;


import com.aliyun.sdk.service.oss2.encryption.crypto.CryptoScheme;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CryptoUtils {
    private static final String RANGE_PREFIX = "bytes=";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final TypeReference<Map<String, String>> MAP_TYPE = new TypeReference<Map<String, String>>() {};

    /**
     * Checks there an encryption info in the metadata.
     */
    public static boolean hasEncryptionInfo(Map<String, String> headers) {
        return headers != null &&
                headers.containsKey(CryptoHeaders.X_OSS_META_CRYPTO_KEY) &&
                headers.containsKey(CryptoHeaders.X_OSS_META_CRYPTO_IV);
    }

    /**
     * @return the corresponding material description from the given json string.
     */
    public static Map<String, String> getDescFromJsonString(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return Collections.emptyMap();
        }
        try {
            return MAPPER.readValue(jsonString, MAP_TYPE);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse matdesc JSON: " + e.getMessage(), e);
        }
    }

    /**
     * @return JSON string representation of the material description map.
     */
    public static String toJsonString(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(map);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize matdesc to JSON: " + e.getMessage(), e);
        }
    }

    public static long[] parseRange(String range) {
        String oriRange = range;
        if (range == null) {
            return null;
        }

        if (!range.startsWith(RANGE_PREFIX)) {
            throw new IllegalArgumentException("Range does not start with " + RANGE_PREFIX + ": " + oriRange);
        }

        range = range.substring(RANGE_PREFIX.length());

        // Reject multi-range (e.g. "bytes=0-10,20-30")
        if (range.contains(",")) {
            throw new IllegalArgumentException(
                    "Multi-range is not supported for encrypted getObject: " + oriRange);
        }

        long lstart = -1;
        long lend = -1;
        try {
            int dashIdx = range.indexOf('-');
            if (dashIdx < 0) {
                throw new IllegalArgumentException("Invalid range format (missing '-'): " + oriRange);
            }
            String start = range.substring(0, dashIdx);
            if (!start.isEmpty()) {
                lstart = Long.parseLong(start);
            }
            String end = range.substring(dashIdx + 1);
            if (!end.isEmpty()) {
                lend = Long.parseLong(end);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Failed to parse range: " + oriRange, e);
        }

        // Reject suffix range (e.g. "bytes=-100")
        if (lstart < 0 && lend >= 0) {
            throw new IllegalArgumentException(
                    "Suffix-range is not supported for encrypted getObject: " + oriRange);
        }

        if ((lstart < 0 && lend < 0) || (lstart >= 0 && lend >= 0 && lstart > lend)) {
            throw new IllegalArgumentException("Invalid range value: " + oriRange);
        }

        return new long[]{lstart, lend};
    }

    public static String toRangeString(long[] range) {
        if (range == null || range.length != 2) {
            return null;
        }

        long start = range[0];
        long end = range[1];

        if (start < 0 && end < 0 || (start > 0 && end > 0 && start > end)) {
            return null;
        }

        if (start < 0) {
            return String.format("%s-%d", RANGE_PREFIX, end);
        } else if (end < 0) {
            return String.format("%s%d-", RANGE_PREFIX, start);
        } else {
            return String.format("%s%d-%d", RANGE_PREFIX, start, end);
        }
    }

    /**
     * Adjusts the range-get start offset to align with cipher block.
     */
    public static long[] calcCryptoRange(long[] range) {
        if (range == null) {
            return null;
        }

        if (range[0] < 0 && range[1] < 0 || (range[0] > 0 && range[1] > 0 && range[0] > range[1])) {
            throw new RuntimeException("The range is illegal. + range:" + range[0] + "~" + range[1], null);
        }

        long[] adjustedCryptoRange = new long[2];
        adjustedCryptoRange[0] = range[0] < 0 ? range[0] : ((range[0] / CryptoScheme.BLOCK_SIZE) * CryptoScheme.BLOCK_SIZE);
        adjustedCryptoRange[1] = range[1];
        return adjustedCryptoRange;
    }

    /**
     * Adjusts a Content-Range header value by adding discardCount to the start position.
     * E.g. "bytes 0-100/200" with discardCount=5 → "bytes 5-100/200"
     */
    public static String adjustContentRange(String contentRange, long discardCount) {
        if (contentRange == null || contentRange.isEmpty()) {
            return null;
        }
        // Format: "bytes start-end/total"
        int space = contentRange.indexOf(' ');
        if (space < 0) return null;
        int dash = contentRange.indexOf('-', space);
        if (dash < 0) return null;
        try {
            long start = Long.parseLong(contentRange.substring(space + 1, dash).trim());
            start += discardCount;
            String prefix = contentRange.substring(0, space + 1);
            String tail = contentRange.substring(dash);
            return prefix + start + tail;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
