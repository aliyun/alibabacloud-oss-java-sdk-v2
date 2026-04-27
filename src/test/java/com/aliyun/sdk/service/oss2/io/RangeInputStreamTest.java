package com.aliyun.sdk.service.oss2.io;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class RangeInputStreamTest {

    // region Helper: create source data

    private static byte[] createTestData(int size) {
        byte[] data = new byte[size];
        for (int i = 0; i < size; i++) {
            data[i] = (byte) (i & 0xFF);
        }
        return data;
    }

    private static byte[] readAll(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[256];
        int n;
        while ((n = is.read(buf)) != -1) {
            bos.write(buf, 0, n);
        }
        return bos.toByteArray();
    }

    // endregion

    // region Test 1: Normal full read

    @Test
    public void testReadFullObject() throws IOException {
        byte[] source = createTestData(1024);

        RangeInputStream.RangeGetFunction getter = (offset, count) -> {
            int start = (int) offset;
            int len = count > 0 ? (int) count : source.length - start;
            byte[] slice = new byte[len];
            System.arraycopy(source, start, slice, 0, len);
            String contentRange = "bytes " + start + "-" + (start + len - 1) + "/" + source.length;
            return new RangeInputStream.RangeGetOutput(
                    new ByteArrayInputStream(slice), len, contentRange, "etag-abc");
        };

        try (RangeInputStream ris = new RangeInputStream(getter)) {
            byte[] result = readAll(ris);
            assertArrayEquals(source, result);
            assertEquals(source.length, ris.getOffset());
            assertEquals(source.length, ris.getTotalSize());
        }
    }

    // endregion

    // region Test 2: Range subset read

    @Test
    public void testReadRangeSubset() throws IOException {
        byte[] source = createTestData(1024);
        int rangeOffset = 100;
        int rangeCount = 200;

        RangeInputStream.RangeGetFunction getter = (offset, count) -> {
            int start = (int) offset;
            int len = count > 0 ? (int) count : source.length - start;
            byte[] slice = new byte[len];
            System.arraycopy(source, start, slice, 0, len);
            String contentRange = "bytes " + start + "-" + (start + len - 1) + "/" + source.length;
            return new RangeInputStream.RangeGetOutput(
                    new ByteArrayInputStream(slice), len, contentRange, "etag-abc");
        };

        try (RangeInputStream ris = new RangeInputStream(getter, rangeOffset, rangeCount, null)) {
            byte[] result = readAll(ris);
            assertEquals(rangeCount, result.length);
            // Verify content matches the expected range
            for (int i = 0; i < rangeCount; i++) {
                assertEquals(source[rangeOffset + i], result[i]);
            }
        }
    }

    // endregion

    // region Test 3: Auto-reconnect on network failure

    @Test
    public void testAutoReconnectOnFailure() throws IOException {
        byte[] source = createTestData(1000);
        int breakAt = 400; // Break the stream after 400 bytes

        AtomicInteger callCount = new AtomicInteger(0);

        RangeInputStream.RangeGetFunction getter = (offset, count) -> {
            int call = callCount.incrementAndGet();
            int start = (int) offset;
            int len = count > 0 ? (int) count : source.length - start;
            byte[] slice = new byte[len];
            System.arraycopy(source, start, slice, 0, len);
            String contentRange = "bytes " + start + "-" + (start + len - 1) + "/" + source.length;

            InputStream body;
            if (call == 1) {
                // First call: stream breaks after breakAt bytes
                body = new FailingInputStream(new ByteArrayInputStream(slice), breakAt);
            } else {
                // Subsequent calls: normal stream
                body = new ByteArrayInputStream(slice);
            }
            return new RangeInputStream.RangeGetOutput(body, len, contentRange, "etag-abc");
        };

        try (RangeInputStream ris = new RangeInputStream(getter)) {
            byte[] result = readAll(ris);
            assertArrayEquals(source, result);
            // Should have called getter at least twice (once for initial, once for reconnect)
            assertTrue("Expected at least 2 rangeGet calls, got " + callCount.get(),
                    callCount.get() >= 2);
        }
    }

    // endregion

    // region Test 4: ETag change detection

    @Test
    public void testEtagChangeThrowsException() throws IOException {
        byte[] source = createTestData(500);
        AtomicInteger callCount = new AtomicInteger(0);

        RangeInputStream.RangeGetFunction getter = (offset, count) -> {
            int call = callCount.incrementAndGet();
            int start = (int) offset;
            int len = count > 0 ? (int) count : source.length - start;
            byte[] slice = new byte[len];
            System.arraycopy(source, start, slice, 0, len);
            String contentRange = "bytes " + start + "-" + (start + len - 1) + "/" + source.length;
            // Return different ETag on second call
            String etag = call == 1 ? "etag-v1" : "etag-v2";

            InputStream body;
            if (call == 1) {
                // First call breaks after 100 bytes to force a reconnect
                body = new FailingInputStream(new ByteArrayInputStream(slice), 100);
            } else {
                body = new ByteArrayInputStream(slice);
            }
            return new RangeInputStream.RangeGetOutput(body, len, contentRange, etag);
        };

        try (RangeInputStream ris = new RangeInputStream(getter)) {
            try {
                readAll(ris);
                fail("Expected IOException due to ETag change");
            } catch (IOException e) {
                assertTrue(e.getMessage().contains("Source file is changed"));
            }
        }
    }

    // endregion

    // region Test 5: Read after close

    @Test
    public void testReadAfterClose() throws IOException {
        byte[] source = createTestData(100);

        RangeInputStream.RangeGetFunction getter = (offset, count) -> {
            return new RangeInputStream.RangeGetOutput(
                    new ByteArrayInputStream(source), source.length, null, "etag-abc");
        };

        RangeInputStream ris = new RangeInputStream(getter);
        ris.close();

        try {
            ris.read(new byte[10], 0, 10);
            fail("Expected IOException after close");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains("closed"));
        }
    }

    // endregion

    // region Test 6: parseContentRange

    @Test
    public void testParseContentRange() {
        long[] result = RangeInputStream.parseContentRange("bytes 0-99/1000");
        assertNotNull(result);
        assertEquals(0, result[0]);
        assertEquals(99, result[1]);
        assertEquals(1000, result[2]);

        result = RangeInputStream.parseContentRange("bytes 500-999/2000");
        assertNotNull(result);
        assertEquals(500, result[0]);
        assertEquals(999, result[1]);
        assertEquals(2000, result[2]);

        assertNull(RangeInputStream.parseContentRange(null));
        assertNull(RangeInputStream.parseContentRange("invalid"));
    }

    // endregion

    // region Helper: FailingInputStream

    /**
     * An InputStream that reads normally for the first N bytes,
     * then throws an IOException to simulate a network failure.
     */
    private static class FailingInputStream extends InputStream {
        private final InputStream delegate;
        private int remaining;

        FailingInputStream(InputStream delegate, int bytesBeforeFailure) {
            this.delegate = delegate;
            this.remaining = bytesBeforeFailure;
        }

        @Override
        public int read() throws IOException {
            if (remaining <= 0) {
                throw new IOException("Simulated network failure");
            }
            int b = delegate.read();
            if (b != -1) {
                remaining--;
            }
            return b;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            if (remaining <= 0) {
                throw new IOException("Simulated network failure");
            }
            int toRead = Math.min(len, remaining);
            int n = delegate.read(b, off, toRead);
            if (n > 0) {
                remaining -= n;
            }
            return n;
        }

        @Override
        public void close() throws IOException {
            delegate.close();
        }
    }

    // endregion
}
