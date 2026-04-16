package com.aliyun.sdk.service.oss2.transfermanager;

public class DownloadError extends Exception {
    private final String path;

    public DownloadError(String path, Throwable cause) {
        super(formatMessage(cause), cause);
        this.path = path;
    }

    private static String formatMessage(Throwable cause) {
        String extra = cause != null ? ", cause: " + cause.getMessage() : "";
        return "download failed" + extra;
    }

    public String path() {
        return path;
    }
}
