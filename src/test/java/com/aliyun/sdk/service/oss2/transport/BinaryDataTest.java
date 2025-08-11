package com.aliyun.sdk.service.oss2.transport;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class BinaryDataTest {

    @Test
    public void createFromString() {
        final String expected = "hello world";
        final BinaryData data = BinaryData.fromString(expected);

        // Assert
        assertEquals(expected, data.toString());
        assertArrayEquals(expected.getBytes(), data.toBytes());
        assertTrue(data.isReplayable());
    }

    @Test
    public void createFromByteArray() {
        final byte[] expected = "hello world".getBytes(StandardCharsets.UTF_8);

        final BinaryData data = BinaryData.fromBytes(expected);
        assertArrayEquals(expected, data.toBytes());
        assertTrue(data.isReplayable());
    }

    @Test
    public void createFromByteBuffer() {
        final byte[] content = "hello world".getBytes(StandardCharsets.UTF_8);
        final ByteBuffer expected = ByteBuffer.wrap(content).asReadOnlyBuffer();

        final BinaryData data = BinaryData.fromByteBuffer(expected);
        assertEquals(expected, data.toByteBuffer());
        assertTrue(data.isReplayable());
    }

    @Test
    public void createFromNullStream() {
        assertThrows(NullPointerException.class, () -> BinaryData.fromStream(null));
    }

    @Test
    public void createFromNullByteArray() {
        assertThrows(NullPointerException.class, () -> BinaryData.fromBytes(null));
    }

    @Test
    public void createFromNullFile() {
        //assertThrows(NullPointerException.class, () -> BinaryData.fromFile(null));
    }

    @Test
    public void createFromStream() {
        final byte[] expected = "hello world".getBytes(StandardCharsets.UTF_8);
        final InputStream is = new ByteArrayInputStream(expected);
        BinaryData data = BinaryData.fromStream(is);
        assertArrayEquals(expected, data.toBytes());
        assertTrue(data.isReplayable());
    }

    @Test
    public void createFromStreamWithoutReplayable() {
        final byte[] expected = "hello world".getBytes(StandardCharsets.UTF_8);
        BinaryData data = BinaryData.fromStream(new NonMarkedStream(new ByteArrayInputStream(expected)));
        assertArrayEquals(expected, data.toBytes());
        assertFalse(data.isReplayable());
    }

    @Test
    public void createFromEmptyStream() {
        final byte[] expected = "".getBytes();

        BinaryData data = BinaryData.fromStream(new ByteArrayInputStream(expected));

        assertArrayEquals(expected, data.toBytes());
    }

    @Test
    public void createFromEmptyString() {
        final String expected = "";

        final BinaryData data = BinaryData.fromString(expected);

        assertArrayEquals(expected.getBytes(), data.toBytes());
        assertEquals(expected, data.toString());
    }

    @Test
    public void createFromEmptyByteArray() {
        final byte[] expected = new byte[0];
        final BinaryData data = BinaryData.fromBytes(expected);
        assertArrayEquals(expected, data.toBytes());
    }

    @Test
    public void createFromNullString() {
        final String expected = null;
        assertThrows(NullPointerException.class, () -> BinaryData.fromString(expected));
    }

    @Test
    public void toReadOnlyByteBufferThrowsOnMutation() {
        BinaryData binaryData = BinaryData.fromString("Hello");
        assertThrows(ReadOnlyBufferException.class, () -> binaryData.toByteBuffer().put((byte) 0));
    }

    @Test
    public void testFromFile() throws Exception {
    }

    @Test
    public void testFromLargeFileStream() {
    }

    @Test
    public void testStreamWithoutReplayable() {
    }

    static class NonMarkedStream extends FilterInputStream {

        public NonMarkedStream(InputStream in) {
            super(in);
        }

        @Override
        public boolean markSupported() {
            return false;
        }
    }
}