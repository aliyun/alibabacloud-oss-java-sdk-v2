package com.aliyun.sdk.service.oss2.transport;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.requireNonNull;

/**
 * A {@link BinaryData} implementation which is backed by a {@code byte[]}.
 */
public final class ByteArrayBinaryData extends BinaryData {
    private final byte[] content;

    /**
     * Creates a new instance of {@link ByteArrayBinaryData}.
     *
     * @param content The byte array content.
     * @throws NullPointerException if {@code content} is null.
     */
    public ByteArrayBinaryData(byte[] content) {
        this.content = requireNonNull(content, "'content' cannot be null");
    }

    @Override
    public Long getLength() {
        return (long) this.content.length;
    }

    @Override
    public boolean isReplayable() {
        return true;
    }

    @Override
    public String toString() {
        return new String(content, StandardCharsets.UTF_8);
    }

    @Override
    public byte[] toBytes() {
        return content;
    }

    @Override
    public InputStream toStream() {
        return new ByteArrayInputStream(this.content);
    }

    @Override
    public ByteBuffer toByteBuffer() {
        return ByteBuffer.wrap(this.content).asReadOnlyBuffer();
    }

    @Override
    public ReadableByteChannel toByteChannel() {
        return Channels.newChannel(toStream());
    }
}