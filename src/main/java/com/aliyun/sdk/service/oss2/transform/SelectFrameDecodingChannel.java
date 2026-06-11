package com.aliyun.sdk.service.oss2.transform;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

final class SelectFrameDecodingChannel implements WritableByteChannel {

    private final WritableByteChannel inner;
    private final SelectFrameDecoder decoder;
    private volatile boolean open = true;

    SelectFrameDecodingChannel(WritableByteChannel inner) {
        this.inner = inner;
        this.decoder = new SelectFrameDecoder((data, offset, len) -> {
            ByteBuffer buf = ByteBuffer.wrap(data, offset, len);
            while (buf.hasRemaining()) {
                inner.write(buf);
            }
        });
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
        int n = src.remaining();
        if (n == 0) {
            return 0;
        }

        byte[] bytes;
        int offset;
        if (src.hasArray()) {
            bytes = src.array();
            offset = src.arrayOffset() + src.position();
            src.position(src.limit());
        } else {
            bytes = new byte[n];
            src.get(bytes);
            offset = 0;
        }

        decoder.write(bytes, offset, n);
        return n;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public void close() throws IOException {
        open = false;
        inner.close();
    }
}
