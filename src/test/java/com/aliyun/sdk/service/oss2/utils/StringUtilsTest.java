package com.aliyun.sdk.service.oss2.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class StringUtilsTest {
    private static final String FOO_UNCAP = "foo";
    private static final String FOO_CAP = "Foo";

    private static final String SENTENCE_UNCAP = "foo bar baz";
    private static final String SENTENCE_CAP = "Foo Bar Baz";

    @Test
    public void testUpperCase() {
        assertNull(StringUtils.upperCase(null));
        assertEquals("upperCase(String) failed",
                "FOO TEST THING", StringUtils.upperCase("fOo test THING"));
        assertEquals("upperCase(empty-string) failed",
                "", StringUtils.upperCase(""));
    }

    @Test
    public void testLowerCase() {
        assertNull(StringUtils.lowerCase(null));
        assertEquals("lowerCase(String) failed",
                "foo test thing", StringUtils.lowerCase("fOo test THING"));
        assertEquals("lowerCase(empty-string) failed",
                "", StringUtils.lowerCase(""));
    }

    @Test
    public void testStartsWithIgnoreCase() {
        assertTrue(StringUtils.startsWithIgnoreCase("helloworld", "hello"));
        assertTrue(StringUtils.startsWithIgnoreCase("hELlOwOrlD", "hello"));
        assertFalse(StringUtils.startsWithIgnoreCase("hello", "world"));
    }

    @Test
    public void safeStringTooBoolean_mixedSpaceTrue_shouldReturnTrue() {
        assertTrue(StringUtils.safeStringToBoolean("TrUe"));
    }

    @Test
    public void safeStringTooBoolean_mixedSpaceFalse_shouldReturnFalse() {
        assertFalse(StringUtils.safeStringToBoolean("fAlSE"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void safeStringTooBoolean_invalidValue_shouldThrowException() {
        assertFalse(StringUtils.safeStringToBoolean("foobar"));
    }

    @Test(timeout = 10 * 1000)
    public void replace_ReplacementStringContainsMatchString_DoesNotCauseInfiniteLoop() {
        assertEquals("aabc", StringUtils.replace("abc", "a", "aa"));
    }

    @Test
    public void replace_EmptyReplacementString_RemovesAllOccurencesOfMatchString() {
        assertEquals("bbb", StringUtils.replace("ababab", "a", ""));
    }

    @Test
    public void replace_MatchNotFound_ReturnsOriginalString() {
        assertEquals("abc", StringUtils.replace("abc", "d", "e"));
    }

    @Test
    public void lowerCase_NonEmptyString() {
        String input = "x-amz-InvocAtion-typE";
        String expected = "x-amz-invocation-type";
        assertEquals(expected, StringUtils.lowerCase(input));
    }

    @Test
    public void lowerCase_NullString() {
        assertNull(StringUtils.lowerCase(null));
    }

    @Test
    public void lowerCase_EmptyString() {
        assertEquals(StringUtils.lowerCase(""), "");
    }

    @Test
    public void upperCase_NonEmptyString() {
        String input = "dHkdjj139_)(e";
        String expected = "DHKDJJ139_)(E";
        assertEquals(expected, StringUtils.upperCase(input));
    }

    @Test
    public void upperCase_NullString() {
        assertNull(StringUtils.upperCase((null)));
    }

    @Test
    public void upperCase_EmptyString() {
        assertEquals(StringUtils.upperCase(""), "");
    }

    @Test
    public void testJoinString() {
        String part1 = "hello";
        String part2 = "world";
        String join = StringUtils.join("-", part1, part2);
        assertEquals("hello-world", join);

        Collection<String> collection = new ArrayList<String>();
        collection.add("hello");
        collection.add("world");
        join = StringUtils.join("-", collection);
        assertEquals("hello-world", join);
    }
}
