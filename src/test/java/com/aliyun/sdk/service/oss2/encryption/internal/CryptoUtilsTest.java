package com.aliyun.sdk.service.oss2.encryption.internal;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CryptoUtilsTest {


    @Test
    public void testParseRange_valid() {
        // Normal range
        long[] ret = CryptoUtils.parseRange("bytes=0-100");
        assertEquals(0, ret[0]);
        assertEquals(100, ret[1]);

        // Open-ended range (to end)
        ret = CryptoUtils.parseRange("bytes=1-");
        assertEquals(1, ret[0]);
        assertEquals(-1, ret[1]);

        // Start from 0, open-ended
        ret = CryptoUtils.parseRange("bytes=0-");
        assertEquals(0, ret[0]);
        assertEquals(-1, ret[1]);

        // Large values
        ret = CryptoUtils.parseRange("bytes=2165535123423-3065535123423");
        assertEquals(2165535123423L, ret[0]);
        assertEquals(3065535123423L, ret[1]);

        // Single byte
        ret = CryptoUtils.parseRange("bytes=0-0");
        assertEquals(0, ret[0]);
        assertEquals(0, ret[1]);

        // Same start and end
        ret = CryptoUtils.parseRange("bytes=16-16");
        assertEquals(16, ret[0]);
        assertEquals(16, ret[1]);

        // null input
        assertNull(CryptoUtils.parseRange(null));
    }

    @Test
    public void testParseRange_suffixRange_rejected() {
        // Suffix range like "bytes=-100" (last 100 bytes) is not supported
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> CryptoUtils.parseRange("bytes=-100"));
        assertTrue(e.getMessage().contains("Suffix-range"));
    }

    @Test
    public void testParseRange_multiRange_rejected() {
        // Multi-range like "bytes=0-10,20-30" is not supported
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> CryptoUtils.parseRange("bytes=0-10,20-30"));
        assertTrue(e.getMessage().contains("Multi-range"));

        // Also rejected with spaces
        e = assertThrows(IllegalArgumentException.class,
                () -> CryptoUtils.parseRange("bytes=0-10, 20-30"));
        assertTrue(e.getMessage().contains("Multi-range"));
    }

    @Test
    public void testParseRange_invalidFormat() {
        // Missing prefix
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> CryptoUtils.parseRange("12bytes=0-100"));
        assertTrue(e.getMessage().contains("Range does not start with"));

        // Negative numbers in range
        assertThrows(IllegalArgumentException.class,
                () -> CryptoUtils.parseRange("bytes=-1--1"));

        // Start > end
        e = assertThrows(IllegalArgumentException.class,
                () -> CryptoUtils.parseRange("bytes=5-2"));
        assertTrue(e.getMessage().contains("Invalid range value"));

        // Both empty (just "bytes=-")
        e = assertThrows(IllegalArgumentException.class,
                () -> CryptoUtils.parseRange("bytes=-"));
        assertTrue(e.getMessage().contains("Invalid range value"));

        // Non-numeric
        assertThrows(IllegalArgumentException.class,
                () -> CryptoUtils.parseRange("bytes=abc-def"));
    }

    @Test
    public void testToRangeString() {
        assertNull(CryptoUtils.toRangeString(null));

        String value;
        value = CryptoUtils.toRangeString(new long[]{0});
        assertNull(value);

        value = CryptoUtils.toRangeString(new long[]{-1, -1});
        assertNull(value);

        // Note: suffix range serialization still works (utility function),
        // but parseRange rejects it for encrypted getObject
        value = CryptoUtils.toRangeString(new long[]{-1, 200});
        assertEquals("bytes=-200", value);

        value = CryptoUtils.toRangeString(new long[]{5, -1});
        assertEquals("bytes=5-", value);

        value = CryptoUtils.toRangeString(new long[]{10, 20});
        assertEquals("bytes=10-20", value);

        value = CryptoUtils.toRangeString(new long[]{2165535123423L, 3065535123423L});
        assertEquals("bytes=2165535123423-3065535123423", value);
    }

    @Test
    public void testCalcCryptoRange() {
        long[] value = CryptoUtils.calcCryptoRange(null);
        assertNull(value);

        // Open-ended range
        value = CryptoUtils.calcCryptoRange(new long[]{2, -1});
        assertEquals(0, value[0]);
        assertEquals(-1, value[1]);

        // Aligned start
        value = CryptoUtils.calcCryptoRange(new long[]{2, 20});
        assertEquals(0, value[0]);
        assertEquals(20, value[1]);

        // Already aligned
        value = CryptoUtils.calcCryptoRange(new long[]{32, 32});
        assertEquals(32, value[0]);
        assertEquals(32, value[1]);

        // Block-aligned
        value = CryptoUtils.calcCryptoRange(new long[]{16, 31});
        assertEquals(16, value[0]);
        assertEquals(31, value[1]);

        // Non-aligned start
        value = CryptoUtils.calcCryptoRange(new long[]{2, 2});
        assertEquals(0, value[0]);
        assertEquals(2, value[1]);

        // Invalid: both negative
        try {
            CryptoUtils.calcCryptoRange(new long[]{-1, -1});
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("The range is illegal"));
        }

        // Invalid: start > end
        try {
            CryptoUtils.calcCryptoRange(new long[]{100, 2});
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("The range is illegal"));
        }
    }

}
