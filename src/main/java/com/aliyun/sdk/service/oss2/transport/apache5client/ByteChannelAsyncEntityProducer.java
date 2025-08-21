package com.aliyun.sdk.service.oss2.transport.apache5client;

import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.nio.AsyncEntityProducer;
import org.apache.hc.core5.http.nio.DataStreamChannel;
import org.apache.hc.core5.util.Args;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class ByteChannelAsyncEntityProducer implements AsyncEntityProducer {
    private final ReadableByteChannel channel;
    private final ByteBuffer byteBuffer;
    private final Long length;
    private final ContentType contentType;
    private final boolean chunked;
    private final AtomicReference<Exception> exception;
    private boolean eof;
    private long remains;

    public ByteChannelAsyncEntityProducer(ReadableByteChannel channel, int bufferSize, Long length) {
        this.channel = Args.notNull(channel, "ReadableByteChannel");
        this.length = length == null ? -1 : length;
        this.byteBuffer = ByteBuffer.allocate(bufferSize);
        this.contentType = null;
        this.chunked = this.length < 0;
        this.exception = new AtomicReference<>();
        this.remains = this.length;
    }

    public ByteChannelAsyncEntityProducer(ReadableByteChannel channel, Long length) {
        this(channel, 32*1024, length);
    }

    public boolean isRepeatable() {
        return false;
    }

    public String getContentType() {
        return Objects.toString(this.contentType, (String)null);
    }

    public long getContentLength() {
        return this.length;
    }

    public int available() {
        return Integer.MAX_VALUE;
    }

    public String getContentEncoding() {
        return null;
    }

    public boolean isChunked() {
        return this.chunked;
    }

    public Set<String> getTrailerNames() {
        return Collections.emptySet();
    }

    public void produce(DataStreamChannel channel) throws IOException {
        int ignore = this.length < 0 ? readToEnd() : readToLimit();

        if (this.byteBuffer.position() > 0) {
            this.byteBuffer.flip();
            channel.write(this.byteBuffer);
            this.byteBuffer.compact();
        }

        if (this.eof && this.byteBuffer.position() == 0) {
            channel.endStream();
            this.releaseResources();
        }
    }

    public void failed(Exception cause) {
        if (this.exception.compareAndSet((Exception)null, cause)) {
            this.releaseResources();
        }
    }

    public Exception getException() {
        return this.exception.get();
    }

    public void releaseResources() {
        // nop
    }

    private int readToEnd() throws IOException {
        int bytesRead = 0;
        if (!this.eof) {
            bytesRead = this.channel.read(this.byteBuffer);
            if (bytesRead < 0) {
                this.eof = true;
            }
        }
        return bytesRead;
    }

    private int readToLimit() throws IOException {
        int bytesRead = 0;
        if (!this.eof) {
            int n = (int)Math.min(this.remains, (long)this.byteBuffer.remaining());
            if (this.byteBuffer.remaining() > n) {
                this.byteBuffer.limit(this.byteBuffer.position() + n);
            }
            bytesRead = this.channel.read(this.byteBuffer);
            if (bytesRead < 0) {
                this.eof = true;
            } else {
                this.remains -= bytesRead;
                if (this.remains <= 0) {
                    this.eof = true;
                }
            }
        }
        return bytesRead;
    }
}

