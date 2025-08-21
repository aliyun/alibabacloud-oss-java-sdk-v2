package com.aliyun.sdk.service.oss2.transport.apache5client;

import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.nio.entity.AbstractBinAsyncEntityConsumer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

public class ByteChannelAsyncEntityConsumer extends AbstractBinAsyncEntityConsumer<byte[]> {

    private final WritableByteChannel channel;
    private final boolean release;

    ByteChannelAsyncEntityConsumer(WritableByteChannel channel, boolean release) {
        super();
        this.channel = channel;
        this.release = release;
    }

    @Override
    protected void streamStart(final ContentType contentType) throws HttpException, IOException {
    }

    @Override
    protected int capacityIncrement() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected void data(final ByteBuffer src, final boolean endOfStream) throws IOException {
        if (src == null) {
            return;
        }
        this.channel.write(src);
    }

    @Override
    protected byte[] generateContent() throws IOException {
        return null;
    }

    @Override
    public void releaseResources() {
        if (this.release) {
            try {
                this.channel.close();
            } catch (Exception ignore) {}
        }
    }
}

