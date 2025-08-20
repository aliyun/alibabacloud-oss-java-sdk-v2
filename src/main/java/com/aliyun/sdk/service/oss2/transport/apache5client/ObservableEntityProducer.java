package com.aliyun.sdk.service.oss2.transport.apache5client;

import com.aliyun.sdk.service.oss2.transport.ObservableByteChannel;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.nio.AsyncEntityProducer;
import org.apache.hc.core5.http.nio.DataStreamChannel;
import org.apache.hc.core5.util.Args;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.List;
import java.util.Set;

public class ObservableEntityProducer implements AsyncEntityProducer {
    private final AsyncEntityProducer wrapped;
    private final List<ObservableByteChannel>  channels;

    public ObservableEntityProducer(AsyncEntityProducer wrapped, List<ObservableByteChannel>  channels) {
        this.wrapped = (AsyncEntityProducer) Args.notNull(wrapped, "Entity consumer");
        this.channels = (List<ObservableByteChannel>) Args.notNull(channels, "Writable byte channels");
    }

    public boolean isRepeatable() {
        return this.wrapped.isRepeatable();
    }

    public long getContentLength() {
        return this.wrapped.getContentLength();
    }

    public String getContentType() {
        return this.wrapped.getContentType();
    }

    public String getContentEncoding() {
        return this.wrapped.getContentEncoding();
    }

    public boolean isChunked() {
        return this.wrapped.isChunked();
    }

    public Set<String> getTrailerNames() {
        return this.wrapped.getTrailerNames();
    }

    public int available() {
        return this.wrapped.available();
    }

    public void produce(final DataStreamChannel channel) throws IOException {
        this.wrapped.produce(new DataStreamChannel() {
            private final List<ObservableByteChannel> cs = channels;
            public void requestOutput() {
                channel.requestOutput();
            }

            public int write(ByteBuffer src) throws IOException {
                ByteBuffer dup = src.duplicate();
                int writtenBytes = channel.write(src);
                if (writtenBytes > 0) {
                    for (int i = 0; i < cs.size() - 1; i++) {
                        ByteBuffer dup1 = dup.duplicate();
                        dup1.limit(dup.position() + writtenBytes);
                        cs.get(i).write(dup1);
                    }
                    dup.limit(dup.position() + writtenBytes);
                    cs.get(cs.size() - 1).write(dup);
                }
                return writtenBytes;
            }

            public void endStream(List<? extends Header> trailers) throws IOException {
                channel.endStream(trailers);
                for (ObservableByteChannel ch: cs) {
                    ch.finished();
                }
            }

            public void endStream() throws IOException {
                this.endStream(null);
            }
        });
    }

    public void failed(Exception cause) {
        this.wrapped.failed(cause);
    }

    public void releaseResources() {
        this.wrapped.releaseResources();
    }
}
