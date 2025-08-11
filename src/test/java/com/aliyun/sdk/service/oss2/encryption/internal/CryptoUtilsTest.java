package com.aliyun.sdk.service.oss2.encryption.internal;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CryptoUtilsTest {


    @Test
    public void testParseRange() {
        long[] ret = CryptoUtils.parseRange("bytes=0-100");
        assertEquals(0, ret[0]);
        assertEquals(100, ret[1]);

        ret = CryptoUtils.parseRange("bytes=-100");
        assertEquals(-1, ret[0]);
        assertEquals(100, ret[1]);

        ret = CryptoUtils.parseRange("bytes=1-");
        assertEquals(1, ret[0]);
        assertEquals(-1, ret[1]);

        ret = CryptoUtils.parseRange("bytes=2165535123423-3065535123423");
        assertEquals(2165535123423L, ret[0]);
        assertEquals(3065535123423L, ret[1]);

        try {
            CryptoUtils.parseRange("12bytes=0-100");
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Range does not start with"));
        }

        try {
            CryptoUtils.parseRange("bytes=-1--1");
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Parse range fail"));
        }

        try {
            CryptoUtils.parseRange("bytes=5-2");
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid range value"));
        }

    }

    @Test
    public void testToRangeString() {
        assertNull(CryptoUtils.toRangeString(null));

        String value;
        value = CryptoUtils.toRangeString(new long[]{0});
        assertNull(value);

        value = CryptoUtils.toRangeString(new long[]{-1, -1});
        assertNull(value);

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

        value = CryptoUtils.calcCryptoRange(new long[]{-1, 20});
        assertEquals(-1, value[0]);
        assertEquals(20, value[1]);


        value = CryptoUtils.calcCryptoRange(new long[]{2, 20});
        assertEquals(0, value[0]);
        assertEquals(20, value[1]);

        value = CryptoUtils.calcCryptoRange(new long[]{2, -1});
        assertEquals(0, value[0]);
        assertEquals(-1, value[1]);

        value = CryptoUtils.calcCryptoRange(new long[]{32, 32});
        assertEquals(32, value[0]);
        assertEquals(32, value[1]);


        value = CryptoUtils.calcCryptoRange(new long[]{2, 2});
        assertEquals(0, value[0]);
        assertEquals(2, value[1]);

        try {
            long[] ignore = CryptoUtils.calcCryptoRange(new long[]{-1, -1});
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("The range is illegal"));
        }

        try {
            long[] ignore = CryptoUtils.calcCryptoRange(new long[]{100, 2});
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("The range is illegal"));
        }
    }

}