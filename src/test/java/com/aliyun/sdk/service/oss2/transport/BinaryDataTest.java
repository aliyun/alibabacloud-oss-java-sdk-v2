package com.aliyun.sdk.service.oss2.transport;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

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
    public void testFromReadableByteChannel() throws IOException {
        // non seekable
        {
            byte[] content = "hello".getBytes(StandardCharsets.UTF_8);
            BinaryData binaryData = BinaryData.fromByteChannel(Channels.newChannel(new ByteArrayInputStream(content)));
            assertThat(binaryData.isReplayable()).isFalse();
            assertThat(binaryData.getLength()).isNull();

            // 1st
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            ReadableByteChannel channel = binaryData.toByteChannel();
            int got = channel.read(buffer);
            assertEquals(content.length, got);
            buffer.flip();
            byte []data = copyBytesFrom(buffer);
            assertArrayEquals(content, data);

            // 2st
            buffer = ByteBuffer.allocate(1024);
            channel = binaryData.toByteChannel();
            got = channel.read(buffer);
            assertEquals(-1, got);
        }

        // seekable
        byte[] content =  "Hello World".getBytes(StandardCharsets.UTF_8);
        Path path = Files.createTempFile("file", ".txt");
        Files.deleteIfExists(path);
        Files.write(path, content);
        try (FileChannel fileChannel = FileChannel.open(path)) {
            BinaryData binaryData = BinaryData.fromByteChannel(fileChannel);
            assertThat(binaryData.isReplayable()).isTrue();
            assertThat(binaryData.getLength()).isNull();

            ReadableByteChannel channel1 = binaryData.toByteChannel();
            ByteBuffer buffer1 = ByteBuffer.allocate(1024);
            channel1.read(buffer1);
            buffer1.flip();
            byte[] data = copyBytesFrom(buffer1);
            assertArrayEquals(content, data);

            ReadableByteChannel channel2 = binaryData.toByteChannel();
            ByteBuffer buffer2 = ByteBuffer.allocate(1024);
            channel2.read(buffer2);
            buffer2.flip();
            data = copyBytesFrom(buffer2);
            assertArrayEquals(content, data);
        }

        // seekable + size
        try (FileChannel fileChannel = FileChannel.open(path)) {
            BinaryData binaryData = BinaryData.fromByteChannel(fileChannel, 5L);
            assertThat(binaryData.isReplayable()).isTrue();
            assertThat(binaryData.getLength()).isEqualTo(5L);

            binaryData = BinaryData.fromByteChannel(fileChannel, 100L);
            assertThat(binaryData.isReplayable()).isTrue();
            assertThat(binaryData.getLength()).isEqualTo(content.length);
        }

        // set position
        try (FileChannel fileChannel = FileChannel.open(path)) {
            byte[] cut = new byte[content.length -1];
            System.arraycopy(content, 1, cut, 0, content.length -1);
            fileChannel.position(1);
            BinaryData binaryData = BinaryData.fromByteChannel(fileChannel);
            assertThat(binaryData.isReplayable()).isTrue();
            assertThat(binaryData.getLength()).isNull();

            ReadableByteChannel channel1 = binaryData.toByteChannel();
            ByteBuffer buffer1 = ByteBuffer.allocate(1024);
            channel1.read(buffer1);
            buffer1.flip();
            byte[] data = copyBytesFrom(buffer1);
            assertArrayEquals(cut, data);

            ReadableByteChannel channel2 = binaryData.toByteChannel();
            ByteBuffer buffer2 = ByteBuffer.allocate(1024);
            channel2.read(buffer2);
            buffer2.flip();
            data = copyBytesFrom(buffer2);
            assertArrayEquals(cut, data);
        }
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

    public static File createSampleFile(String fileName, long size) throws IOException {
        File file = File.createTempFile(fileName, "");
        file.deleteOnExit();
        String context = "abcdefghijklmnopqrstuvwxyz0123456789011234567890\n";

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        for (int i = 0; i < size / context.length(); i++) {
            writer.write(context);
        }
        writer.close();

        return file;
    }

    public static byte[] copyBytesFrom(ByteBuffer bb) {
        if (bb == null) {
            return null;
        }

        if (bb.hasArray()) {
            return Arrays.copyOfRange(
                    bb.array(),
                    bb.arrayOffset() + bb.position(),
                    bb.arrayOffset() + bb.limit());
        }

        byte[] dst = new byte[bb.remaining()];
        bb.asReadOnlyBuffer().get(dst);
        return dst;
    }
}