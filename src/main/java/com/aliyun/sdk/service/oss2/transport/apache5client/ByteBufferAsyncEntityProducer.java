package com.aliyun.sdk.service.oss2.transport.apache5client;

import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.nio.AsyncEntityProducer;
import org.apache.hc.core5.http.nio.DataStreamChannel;
import org.apache.hc.core5.util.Args;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class ByteBufferAsyncEntityProducer implements AsyncEntityProducer {

    private final ByteBuffer bytebuf;
    private final int length;
    private final ContentType contentType;
    private final boolean chunked;
    private final AtomicReference<Exception> exception;

    public ByteBufferAsyncEntityProducer(final ByteBuffer content, final ContentType contentType, final boolean chunked) {
        Args.notNull(content, "Content");
        this.bytebuf = content;
        this.length = this.bytebuf.remaining();
        this.contentType = contentType;
        this.chunked = chunked;
        this.exception = new AtomicReference<>();
    }

    public ByteBufferAsyncEntityProducer(final ByteBuffer content, final ContentType contentType) {
        this(content, contentType, false);
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }

    @Override
    public final String getContentType() {
        return Objects.toString(contentType, null);
    }

    @Override
    public long getContentLength() {
        return length;
    }

    @Override
    public int available() {
        return Integer.MAX_VALUE;
    }

    @Override
    public String getContentEncoding() {
        return null;
    }

    @Override
    public boolean isChunked() {
        return chunked;
    }

    @Override
    public Set<String> getTrailerNames() {
        return Collections.emptySet();
    }

    @Override
    public final void produce(final DataStreamChannel channel) throws IOException {
        if (bytebuf.hasRemaining()) {
            channel.write(bytebuf);
        }
        if (!bytebuf.hasRemaining()) {
            channel.endStream();
        }
    }

    @Override
    public final void failed(final Exception cause) {
        if (exception.compareAndSet(null, cause)) {
            releaseResources();
        }
    }

    public final Exception getException() {
        return exception.get();
    }

    @Override
    public void releaseResources() {
        bytebuf.clear();
        bytebuf.limit(length);
    }

}
