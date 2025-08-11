package com.aliyun.sdk.service.oss2.hash;

import com.aliyun.sdk.service.oss2.io.StreamObserver;

import java.io.IOException;
import java.util.zip.Checksum;

public class CRC64Observer extends StreamObserver {

    private final CRC64 chksum;
    private long initCrc;

    public CRC64Observer() {
        this(0);
    }

    public CRC64Observer(long value) {
        super();
        this.chksum = new CRC64(value);
    }

    public Checksum getChecksum() {
        return this.chksum;
    }

    @Override
    public void data(final byte[] input, final int offset, final int length) throws IOException {
        this.chksum.update(input, offset, length);
    }

    @Override
    public void data(final int input) throws IOException {
        this.chksum.update(input);
    }

    @Override
    public void reset() {
        this.chksum.reset();
    }
}
