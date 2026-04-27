package com.aliyun.sdk.service.oss2.transfermanager;

public class DownloadResult {
    private final long written;

    public DownloadResult(long written) {
        this.written = written;
    }

    public long written() {
        return written;
    }
}
