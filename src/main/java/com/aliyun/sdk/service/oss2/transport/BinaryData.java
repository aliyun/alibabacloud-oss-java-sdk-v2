package com.aliyun.sdk.service.oss2.transport;

import java.io.InputStream;
import java.nio.ByteBuffer;

public abstract class BinaryData {

    static final int STREAM_READ_SIZE = 8192;
    static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    static final String TOO_LARGE_FOR_BYTE_ARRAY
            = "The content length is too large for a byte array. Content length is: ";

    public BinaryData() {
    }

    /**
     * Creates an instance of {@link BinaryData} from the given {@link InputStream}.
     *
     * @param inputStream The {@link InputStream} that {@link BinaryData} will represent.
     * @return A {@link BinaryData} representing the {@link InputStream}.
     */
    public static BinaryData fromStream(InputStream inputStream) {
        return fromStream(inputStream, null);
    }

    /**
     * Creates an instance of {@link BinaryData} from the given {@link InputStream}.
     *
     * @param inputStream inputStream The {@link InputStream} that {@link BinaryData} will represent.
     * @param length      length The length of data in bytes.
     * @return A {@link BinaryData} representing the {@link InputStream}.
     */
    public static BinaryData fromStream(InputStream inputStream, Long length) {
        return new InputStreamBinaryData(inputStream, length);
    }

    /**
     * Creates an instance of {@link BinaryData} from the given {@link String}.
     *
     * @param data The {@link String} that {@link BinaryData} will represent.
     * @return A {@link BinaryData} representing the {@link String}.
     */
    public static BinaryData fromString(String data) {
        return new StringBinaryData(data);
    }

    /**
     * Creates an instance of {@link BinaryData} from the given byte array.
     *
     * @param data The byte array that {@link BinaryData} will represent.
     * @return A {@link BinaryData} representing the byte array.
     */
    public static BinaryData fromBytes(byte[] data) {
        return new ByteArrayBinaryData(data);
    }

    /**
     * Creates an instance of {@link BinaryData} from the given {@link ByteBuffer}.
     *
     * @param data The {@link ByteBuffer} that {@link BinaryData} will represent.
     * @return A {@link BinaryData} representing the {@link ByteBuffer}.
     */
    public static BinaryData fromByteBuffer(ByteBuffer data) {
        return new ByteBufferBinaryData(data);
    }

    /**
     * Returns a byte array representation of this {@link BinaryData}.
     *
     * @return A byte array representing this {@link BinaryData}.
     */
    public abstract byte[] toBytes();

    /**
     * Returns a {@link String} representation of this {@link BinaryData} by converting its data using the UTF-8
     * character set.
     *
     * @return A {@link String} representing this {@link BinaryData}.
     */
    public abstract String toString();

    /**
     * Returns an {@link InputStream} representation of this {@link BinaryData}.
     *
     * @return An {@link InputStream} representing the {@link BinaryData}.
     */
    public abstract InputStream toStream();

    /**
     * Returns a read-only {@link ByteBuffer} representation of this {@link BinaryData}.
     * <p>
     * Attempting to mutate the returned {@link ByteBuffer} will throw a Exception.
     *
     * @return A read-only {@link ByteBuffer} representing the {@link BinaryData}.
     */
    public abstract ByteBuffer toByteBuffer();

    /**
     * Gets the length of the {@link BinaryData} if it can be calculated.
     * If the content length isn't able to be calculated null will be returned.
     *
     * @return The length of the {@link BinaryData} if it can be calculated, otherwise null.
     */
    public abstract Long getLength();

    /**
     * Returns a flag indicating whether the content can be repeatedly consumed using all accessors.
     * Replayability does not imply thread-safety. The caller must not use data accessors simultaneously regardless of
     * what this method returns.
     *
     * @return a flag indicating whether the content can be repeatedly.
     */
    public abstract boolean isReplayable();

}