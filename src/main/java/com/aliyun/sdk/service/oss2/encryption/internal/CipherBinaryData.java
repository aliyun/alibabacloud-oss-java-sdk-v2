package com.aliyun.sdk.service.oss2.encryption.internal;

import com.aliyun.sdk.service.oss2.encryption.ContentCipher;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.IOUtils;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;

/**
 * A {@link BinaryData} wrapper that encrypts the underlying body on-the-fly using a
 * {@link ContentCipher}. Supports replayability: if the original body is replayable,
 * each call to {@link #toStream()} produces a fresh encrypted stream from the beginning.
 * <p>
 * For AES-CTR, the encrypted length equals the plaintext length, so {@link #getLength()}
 * delegates to the original body.
 * <p>
 * An optional {@code offset} parameter allows encryption to start at a specific byte position
 * (for multipart uploads). The cipher is cloned and seeked to the offset on each {@code toStream()} call.
 */
public final class CipherBinaryData extends BinaryData {

    private final BinaryData original;
    private final ContentCipher cipher;
    private final long offset;

    public CipherBinaryData(BinaryData original, ContentCipher cipher) {
        this(original, cipher, 0);
    }

    public CipherBinaryData(BinaryData original, ContentCipher cipher, long offset) {
        this.original = original;
        this.cipher = cipher;
        this.offset = offset;
    }

    @Override
    public InputStream toStream() {
        // Clone cipher for each stream to support replayability.
        // Each clone starts from the same IV position as the original cipher,
        // then seeks to the offset if needed (for multipart uploads).
        ContentCipher freshCipher = cipher.clone();
        if (offset > 0) {
            freshCipher.seekTo(offset);
        }
        return freshCipher.encryptContent(original.toStream());
    }

    @Override
    public Long getLength() {
        return original.getLength();
    }

    @Override
    public boolean isReplayable() {
        return original.isReplayable();
    }

    @Override
    public byte[] toBytes() {
        return IOUtils.toByteArray(toStream());
    }

    @Override
    public String toString() {
        return new String(toBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public ByteBuffer toByteBuffer() {
        return ByteBuffer.wrap(toBytes()).asReadOnlyBuffer();
    }

    @Override
    public ReadableByteChannel toByteChannel() {
        return Channels.newChannel(toStream());
    }
}
