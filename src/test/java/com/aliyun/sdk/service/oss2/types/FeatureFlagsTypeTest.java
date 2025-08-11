package com.aliyun.sdk.service.oss2.types;

import org.junit.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FeatureFlagsTypeTest {

    @Test
    public void testCombine() {

        int combined = FeatureFlagsType.combine(
                FeatureFlagsType.CORRECT_CLOCK_SKEW, FeatureFlagsType.AUTO_DETECT_MIMETYPE);
        assertEquals(0b0011, combined);

        int all = FeatureFlagsType.combine(FeatureFlagsType.values());
        assertEquals(0b1111, all);
    }

    @Test
    public void testIsSet() {
        int flags = FeatureFlagsType.combine(
                FeatureFlagsType.CORRECT_CLOCK_SKEW,
                FeatureFlagsType.ENABLE_CRC64_CHECK_DOWNLOAD);

        assertTrue(FeatureFlagsType.CORRECT_CLOCK_SKEW.isSet(flags));
        assertFalse(FeatureFlagsType.AUTO_DETECT_MIMETYPE.isSet(flags));
        assertTrue(FeatureFlagsType.ENABLE_CRC64_CHECK_DOWNLOAD.isSet(flags));
    }

    @Test
    public void testParse() {
        int flags = FeatureFlagsType.combine(
                FeatureFlagsType.AUTO_DETECT_MIMETYPE,
                FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD);

        Set<FeatureFlagsType> result = FeatureFlagsType.parse(flags);
        assertTrue(result.contains(FeatureFlagsType.AUTO_DETECT_MIMETYPE));
        assertTrue(result.contains(FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD));

        assertFalse(result.contains(FeatureFlagsType.CORRECT_CLOCK_SKEW));
        assertFalse(result.contains(FeatureFlagsType.ENABLE_CRC64_CHECK_DOWNLOAD));
    }

    @Test
    public void testEmptyFlags() {
        int empty = 0;
        assertTrue(FeatureFlagsType.parse(empty).isEmpty());
        assertFalse(FeatureFlagsType.CORRECT_CLOCK_SKEW.isSet(empty));
    }

    @Test
    public void testSingleFlag() {
        int single = FeatureFlagsType.CORRECT_CLOCK_SKEW.getValue();
        Set<FeatureFlagsType> result = FeatureFlagsType.parse(single);

        assertEquals(1, result.size());
        assertTrue(result.contains(FeatureFlagsType.CORRECT_CLOCK_SKEW));
    }

}