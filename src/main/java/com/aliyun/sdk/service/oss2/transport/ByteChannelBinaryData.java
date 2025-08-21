package com.aliyun.sdk.service.oss2.transport;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A {@link BinaryData} implementation which is backed by an {@link InputStream}.
 */
public final class ByteChannelBinaryData extends BinaryData {
    private final Supplier<ReadableByteChannel> content;
    private final Long length;
    private final boolean isReplayable;
    private final ReadableByteChannel ch;
    private final long position;

    /**
     * Creates an instance of {@link ByteChannelBinaryData}.
     *
     * @param channel The ReadableByteChannel that is used as the content for this instance.
     * @param length      The length of the inputStream.
     * @throws NullPointerException if {@code content} is null.
     */
    public ByteChannelBinaryData(ReadableByteChannel channel, Long length) {
        Objects.requireNonNull(channel, "'channel' cannot be null.");
        this.ch = channel;
        if (channel instanceof SeekableByteChannel) {
            this.isReplayable = true;
            try {
                SeekableByteChannel sbb = (SeekableByteChannel) channel;
                this.position = sbb.position();
                if (length != null) {
                    long remains = sbb.size() - sbb.position();
                    length = Math.min(remains, length);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.content = () -> resettableContent((SeekableByteChannel)channel, this.position);
        } else {
            this.isReplayable = false;
            this.position = 0;
            this.content = () -> channel;
        }
        this.length = length;
    }

    private static ReadableByteChannel resettableContent(SeekableByteChannel channel, long position) {
        try {
            channel.position(position);
            return channel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void tryClose() {
        try {
            this.ch.close();
        } catch (Exception ignore) {
        }
    }

    /**
     * Unwraps this instance by returning the underlying {@link ReadableByteChannel}.
     *
     * @return the underlying {@link ReadableByteChannel}.
     */
    public ReadableByteChannel unwrap() {
        return this.ch;
    }

    @Override
    public byte[] toBytes() {
        throw  new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        throw  new UnsupportedOperationException();
    }

    @Override
    public InputStream toStream() {
        throw  new UnsupportedOperationException();
    }

    @Override
    public ByteBuffer toByteBuffer() {
        throw  new UnsupportedOperationException();
    }

    @Override
    public ReadableByteChannel toByteChannel() {
        return this.content.get();
    }

    @Override
    public Long getLength() {
        return length;
    }

    @Override
    public boolean isReplayable() {
        return isReplayable;
    }
}