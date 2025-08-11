package com.aliyun.sdk.service.oss2.utils;

import org.junit.Test;

import java.io.*;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IOUtilsTest {

    // Create and init a byte array as input data
    byte[] genTestBytes() {
        byte[] iarr = new byte[200];
        Arrays.fill(iarr, (byte) -1);
        for (int i = 0; i < 80; i++) {
            iarr[i] = (byte) i;
        }
        return iarr;
    }

    @Test
    public void testCopyLarge_ExtraLength() throws IOException {
        byte[] iarr = genTestBytes();

        try (ByteArrayInputStream is = new ByteArrayInputStream(iarr);
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            // Create streams

            // Test our copy method
            // for extra length, it reads till EOF
            assertEquals(200, IOUtils.copyLarge(is, os, 0, 2000));
            final byte[] oarr = os.toByteArray();

            // check that output length is correct
            assertEquals(200, oarr.length);
            // check that output data corresponds to input data
            assertEquals(1, oarr[1]);
            assertEquals(79, oarr[79]);
            assertEquals(-1, oarr[80]);
        }
    }

    @Test
    public void testCopyLarge_FullLength() throws IOException {
        byte[] iarr = genTestBytes();

        try (ByteArrayInputStream is = new ByteArrayInputStream(iarr);
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            // Test our copy method
            assertEquals(200, IOUtils.copyLarge(is, os, 0, -1));
            final byte[] oarr = os.toByteArray();

            // check that output length is correct
            assertEquals(200, oarr.length);
            // check that output data corresponds to input data
            assertEquals(1, oarr[1]);
            assertEquals(79, oarr[79]);
            assertEquals(-1, oarr[80]);
        }
    }

    @Test
    public void testCopyLarge_NoSkip() throws IOException {
        byte[] iarr = genTestBytes();
        try (ByteArrayInputStream is = new ByteArrayInputStream(iarr);
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            // Test our copy method
            assertEquals(100, IOUtils.copyLarge(is, os, 0, 100));
            final byte[] oarr = os.toByteArray();

            // check that output length is correct
            assertEquals(100, oarr.length);
            // check that output data corresponds to input data
            assertEquals(1, oarr[1]);
            assertEquals(79, oarr[79]);
            assertEquals(-1, oarr[80]);
        }
    }

    @Test
    public void testCopyLarge_Skip() throws IOException {
        byte[] iarr = genTestBytes();
        try (ByteArrayInputStream is = new ByteArrayInputStream(iarr);
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            // Test our copy method
            assertEquals(100, IOUtils.copyLarge(is, os, 10, 100));
            final byte[] oarr = os.toByteArray();

            // check that output length is correct
            assertEquals(100, oarr.length);
            // check that output data corresponds to input data
            assertEquals(11, oarr[1]);
            assertEquals(79, oarr[69]);
            assertEquals(-1, oarr[70]);
        }
    }

    @Test
    public void testCopyLarge_SkipInvalid() throws IOException {
        byte[] iarr = genTestBytes();
        try (ByteArrayInputStream is = new ByteArrayInputStream(iarr);
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            // Test our copy method
            assertThrows(EOFException.class, () -> IOUtils.copyLarge(is, os, 1000, 100));
        }
    }

    @Test
    public void testCopyLarge_SkipWithInvalidOffset() throws IOException {
        byte[] iarr = genTestBytes();
        ByteArrayInputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            // Create streams
            is = new ByteArrayInputStream(iarr);
            os = new ByteArrayOutputStream();

            // Test our copy method
            assertEquals(100, IOUtils.copyLarge(is, os, -10, 100));
            final byte[] oarr = os.toByteArray();

            // check that output length is correct
            assertEquals(100, oarr.length);
            // check that output data corresponds to input data
            assertEquals(1, oarr[1]);
            assertEquals(79, oarr[79]);
            assertEquals(-1, oarr[80]);

        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }
    }

    @Test
    public void testSkipFully_InputStream() throws Exception {
        final int size = 1027;

        try (InputStream input = new ByteArrayInputStream(new byte[size])) {
            assertThrows(IllegalArgumentException.class, () -> IOUtils.skipFully(input, -1), "Should have failed with IllegalArgumentException");

            IOUtils.skipFully(input, 0);
            IOUtils.skipFully(input, size - 1);
            assertThrows(IOException.class, () -> IOUtils.skipFully(input, 2), "Should have failed with IOException");
        }
    }

}
