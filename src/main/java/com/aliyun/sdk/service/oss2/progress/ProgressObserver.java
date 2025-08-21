package com.aliyun.sdk.service.oss2.progress;

import com.aliyun.sdk.service.oss2.io.StreamObserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.Objects;
import java.util.Optional;

public class ProgressObserver extends StreamObserver implements WritableByteChannel {

    private final ProgressListener listener;
    private final long total;
    private long written;
    private long lastWritten; // last written

    public ProgressObserver(ProgressListener listener) {
        this(listener, -1L);
    }

    public ProgressObserver(ProgressListener listener, Long total) {
        Objects.requireNonNull(listener);
        this.listener = listener;
        this.total = Optional.ofNullable(total).orElse(-1L);
    }

    private void notify(long inc) {
        if (this.written > this.lastWritten) {
            this.listener.onProgress(inc, this.written, this.total);
        }
    }

    @Override
    public void data(final byte[] buffer, final int offset, final int length) throws IOException {
        this.written += length;
        notify(length);
    }

    @Override
    public void data(final int value) throws IOException {
        this.written += 1;
        notify(1L);
    }

    @Override
    public void finished() throws IOException {
        this.listener.onFinish();
    }

    @Override
    public void reset() {
        this.lastWritten = this.written;
        this.written = 0;
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
        int len = src.limit() - src.position();
        src.position(src.limit());
        notify((long)len);
        return len;
    }

    @Override
    public boolean isOpen() {
        // NOP
        return true;
    }

    @Override
    public void close() throws IOException {
        // NOP
    }
}
