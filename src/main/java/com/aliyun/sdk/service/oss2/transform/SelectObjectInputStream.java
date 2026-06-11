package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.transport.Abortable;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

final class SelectObjectInputStream extends FilterInputStream implements Abortable {

    private static final int READ_BUFFER_SIZE = 8 * 1024;

    private final SelectFrameDecoder decoder;
    private byte[] decodedBuf;
    private int decodedPos;
    private int decodedLen;

    SelectObjectInputStream(InputStream in) {
        super(in);
        this.decodedBuf = new byte[READ_BUFFER_SIZE];
        this.decodedPos = 0;
        this.decodedLen = 0;
        this.decoder = new SelectFrameDecoder(this::onDecodedData);
    }

    private void onDecodedData(byte[] data, int offset, int len) {
        ensureCapacity(len);
        System.arraycopy(data, offset, decodedBuf, decodedLen, len);
        decodedLen += len;
    }

    private void ensureCapacity(int additional) {
        int required = decodedLen + additional;
        if (required > decodedBuf.length) {
            int newSize = Math.max(decodedBuf.length * 2, required);
            byte[] newBuf = new byte[newSize];
            System.arraycopy(decodedBuf, decodedPos, newBuf, 0, decodedLen - decodedPos);
            decodedLen -= decodedPos;
            decodedPos = 0;
            decodedBuf = newBuf;
        }
    }

    private void compact() {
        if (decodedPos > 0) {
            int remaining = decodedLen - decodedPos;
            if (remaining > 0) {
                System.arraycopy(decodedBuf, decodedPos, decodedBuf, 0, remaining);
            }
            decodedLen = remaining;
            decodedPos = 0;
        }
    }

    private boolean fillDecoded() throws IOException {
        if (decoder.isFinished() || decoder.isError()) {
            return false;
        }

        compact();

        byte[] readBuf = new byte[READ_BUFFER_SIZE];
        while (decodedLen == decodedPos) {
            int bytesRead = in.read(readBuf);
            if (bytesRead < 0) {
                if (!decoder.isFinished()) {
                    throw new IOException("Unexpected end of stream before END frame");
                }
                return false;
            }
            decoder.write(readBuf, 0, bytesRead);
            if (decoder.isFinished() && decodedLen == decodedPos) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int read() throws IOException {
        if (decodedPos < decodedLen) {
            return decodedBuf[decodedPos++] & 0xFF;
        }
        if (!fillDecoded()) {
            return -1;
        }
        return decodedBuf[decodedPos++] & 0xFF;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (len == 0) {
            return 0;
        }

        int available = decodedLen - decodedPos;
        if (available <= 0) {
            if (!fillDecoded()) {
                return -1;
            }
            available = decodedLen - decodedPos;
        }

        int toReturn = Math.min(len, available);
        System.arraycopy(decodedBuf, decodedPos, b, off, toReturn);
        decodedPos += toReturn;
        return toReturn;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int available() throws IOException {
        return decodedLen - decodedPos;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }

    @Override
    public void abort() {
        if (in instanceof Abortable) {
            ((Abortable) in).abort();
        }
    }
}
