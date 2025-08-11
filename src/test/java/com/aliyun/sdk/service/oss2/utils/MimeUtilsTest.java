package com.aliyun.sdk.service.oss2.utils;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MimeUtilsTest {
    @Test
    public void testGetMimeType() {
        assertEquals("text/html", MimeUtils.getMimetype("demo.html"));
        assertEquals("text/html", MimeUtils.getMimetype("demo.htm"));
        assertEquals("text/plain", MimeUtils.getMimetype("demo.txt"));
        assertNull(MimeUtils.getMimetype(""));
        assertNull(MimeUtils.getMimetype("bin"));
        assertEquals("application/octet-stream", MimeUtils.getMimetype("bin", "application/octet-stream"));
    }

    @Test
    public void testGetUserDefinedMimeType() {
        assertNull(MimeUtils.getMimetype("demo.my-html"));

        MimeUtils.addMimeType(MapUtils.of(".my-html", "text/my-html"));
        assertEquals("text/my-html", MimeUtils.getMimetype("demo.my-html"));

        MimeUtils.clearMimeType();
        assertNull(MimeUtils.getMimetype("demo.my-html"));
    }
}