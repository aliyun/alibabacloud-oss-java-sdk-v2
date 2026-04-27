package com.aliyun.sdk.service.oss2.transfermanager;

public class UploadError extends Exception {
    private final String uploadId;
    private final String path;

    public UploadError(String uploadId, String path, Throwable cause) {
        super(formatMessage(uploadId, cause), cause);
        this.uploadId = uploadId;
        this.path = path;
    }

    private static String formatMessage(String uploadId, Throwable cause) {
        String extra = cause != null ? ", cause: " + cause.getMessage() : "";
        return "upload failed, upload id: " + (uploadId != null ? uploadId : "") + extra;
    }

    public String uploadId() {
        return uploadId;
    }

    public String path() {
        return path;
    }
}
