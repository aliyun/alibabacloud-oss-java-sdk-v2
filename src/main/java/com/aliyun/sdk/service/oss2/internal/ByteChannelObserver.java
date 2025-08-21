package com.aliyun.sdk.service.oss2.internal;

import com.aliyun.sdk.service.oss2.io.StreamObserver;
import com.aliyun.sdk.service.oss2.transport.ObservableByteChannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

public class ByteChannelObserver implements ObservableByteChannel {
    private final StreamObserver so;
    private final WritableByteChannel wbc;
    // for re-use in dataUpdate(ByteBuffer input)
    private byte[] tempArray;

    public ByteChannelObserver(StreamObserver so) {
        this.so = so;
        this.wbc = (so instanceof ObservableByteChannel)? (WritableByteChannel)so : null;
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
        if (wbc != null) {
            return wbc.write(src);
        }

        int first = src.position();
        dataUpdate(src);
        return src.position() - first;
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public void close() throws IOException {
        // noop
    }

    protected void dataUpdate(ByteBuffer input) throws IOException {
        if (!input.hasRemaining()) {
            return;
        }
        if (input.hasArray()) {
            byte[] b = input.array();
            int ofs = input.arrayOffset();
            int pos = input.position();
            int lim = input.limit();
            this.so.data(b, ofs + pos, lim - pos);
            input.position(lim);
        } else {
            int len = input.remaining();
            int n = Math.min(4096, len);
            if ((tempArray == null) || (n > tempArray.length)) {
                tempArray = new byte[n];
            }
            while (len > 0) {
                int chunk = Math.min(len, tempArray.length);
                input.get(tempArray, 0, chunk);
                this.so.data(tempArray, 0, chunk);
                len -= chunk;
            }
        }
    }

    @Override
    public void finished() throws IOException {
        this.so.finished();
    }
}
