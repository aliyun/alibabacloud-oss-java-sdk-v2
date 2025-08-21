package com.aliyun.sdk.service.oss2.transport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Supplier;

/**
 * A {@link BinaryData} implementation which is backed by an {@link InputStream}.
 */
public final class InputStreamBinaryData extends BinaryData {
    private static final int INITIAL_BUFFER_CHUNK_SIZE = 8 * 1024;
    private static final int MAX_BUFFER_CHUNK_SIZE = 8 * 1024 * 1024;
    private static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;
    private static final AtomicReferenceFieldUpdater<InputStreamBinaryData, byte[]> BYTES_UPDATER
            = AtomicReferenceFieldUpdater.newUpdater(InputStreamBinaryData.class, byte[].class, "bytes");
    private final Supplier<InputStream> content;
    private final Long length;
    private final boolean isReplayable;
    private final InputStream is;
    private volatile byte[] bytes;

    /**
     * Creates an instance of {@link InputStreamBinaryData}.
     *
     * @param inputStream The inputStream that is used as the content for this instance.
     * @param length      The length of the inputStream.
     * @throws NullPointerException if {@code content} is null.
     */
    public InputStreamBinaryData(InputStream inputStream, Long length) {
        Objects.requireNonNull(inputStream, "'inputStream' cannot be null.");
        this.length = length;
        this.is = inputStream;
        this.isReplayable = canMarkReset(inputStream);
        if (this.isReplayable) {
            inputStream.mark(Optional.ofNullable(length).orElse(0L).intValue());
            this.content = () -> resettableContent(inputStream);
        } else {
            this.content = () -> inputStream;
        }
    }

    private static boolean canMarkReset(InputStream inputStream) {
        return inputStream.markSupported();
    }

    private static InputStream resettableContent(InputStream stream) {
        try {
            stream.reset();
            return stream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void tryClose() {
        try {
            this.is.close();
        } catch (Exception ignore) {
        }
    }

    /**
     * Unwraps this instance by returning the underlying {@link InputStream}.
     *
     * @return the underlying {@link InputStream}.
     */
    public InputStream unwrap() {
        return is;
    }

    @Override
    public byte[] toBytes() {
        if (length != null && length > MAX_ARRAY_SIZE) {
            throw new RuntimeException(TOO_LARGE_FOR_BYTE_ARRAY + length);
        }

        return BYTES_UPDATER.updateAndGet(this, bytes -> bytes == null ? getBytes() : bytes);
    }

    @Override
    public String toString() {
        return new String(toBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public InputStream toStream() {
        return content.get();
    }

    @Override
    public ByteBuffer toByteBuffer() {
        return ByteBuffer.wrap(toBytes()).asReadOnlyBuffer();
    }

    @Override
    public ReadableByteChannel toByteChannel() {
        return Channels.newChannel(toStream());
    }

    @Override
    public Long getLength() {
        byte[] data = BYTES_UPDATER.get(this);
        if (data != null) {
            return (long) data.length;
        }
        return length;
    }

    @Override
    public boolean isReplayable() {
        return isReplayable;
    }

    private byte[] getBytes() {
        try {
            AccessibleByteArrayOutputStream dataOutputBuffer = new AccessibleByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[STREAM_READ_SIZE];
            InputStream inputStream = this.content.get();
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                dataOutputBuffer.write(data, 0, nRead);
            }
            return dataOutputBuffer.toByteArrayUnsafe();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    static class AccessibleByteArrayOutputStream extends ByteArrayOutputStream {
        /**
         * Constructs an instance of {@link AccessibleByteArrayOutputStream} with the default initial capacity.
         */
        public AccessibleByteArrayOutputStream() {
            super();
        }

        /**
         * Constructs an instance of {@link AccessibleByteArrayOutputStream} with a specified initial capacity.
         *
         * @param initialCapacity The initial capacity.
         * @throws IllegalArgumentException If {@code initialCapacity} is less than 0.
         */
        public AccessibleByteArrayOutputStream(int initialCapacity) {
            super(initialCapacity);
        }

        @Override
        public byte[] toByteArray() {
            return Arrays.copyOf(buf, count);
        }

        /**
         * Returns a {@code byte[]} representation of the content written to this stream.
         * <p>
         * If the internal buffer is the same size as the number of bytes written to the stream, then the internal buffer
         * will be returned. Otherwise, a copy of the internal buffer will be returned.
         *
         * @return A {@code byte[]} representation of the content written to this stream.
         */
        public byte[] toByteArrayUnsafe() {
            return (buf.length == count) ? buf : toByteArray();
        }

        /**
         * Returns a {@link ByteBuffer} representation of the content written to this stream.
         * <p>
         * The {@link ByteBuffer} will use a direct reference to the internal {@code byte[]} being written, so any
         * modifications to the content already written will be reflected in the {@link ByteBuffer}. Given the direct
         * reference to the internal {@code byte[]} the {@link ByteBuffer} returned by the API will be read-only. Further
         * writing to this stream won't be reflected in the {@link ByteBuffer} as the ByteBuffer will be created using
         * {@code ByteBuffer.wrap(bytes, 0, count())}.
         *
         * @return A read-only {@link ByteBuffer} represented by the internal buffer being written to.
         */
        public ByteBuffer toByteBuffer() {
            return ByteBuffer.wrap(buf, 0, count).asReadOnlyBuffer();
        }

        /**
         * The number of bytes that have been written to the stream.
         *
         * @return The number of byte written to the stream.
         */
        public int count() {
            return count;
        }
    }
}