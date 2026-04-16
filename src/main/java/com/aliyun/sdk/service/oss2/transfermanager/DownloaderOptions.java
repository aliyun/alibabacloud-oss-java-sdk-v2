package com.aliyun.sdk.service.oss2.transfermanager;

public class DownloaderOptions {
    private final long partSize;
    private final int parallelNum;
    private final boolean useTempFile;
    private final boolean enableCheckpoint;
    private final String checkpointDir;

    private DownloaderOptions(Builder builder) {
        this.partSize = builder.partSize > 0 ? builder.partSize : Defaults.DEFAULT_DOWNLOAD_PART_SIZE;
        this.parallelNum = builder.parallelNum > 0 ? builder.parallelNum : Defaults.DEFAULT_DOWNLOAD_PARALLEL;
        this.useTempFile = builder.useTempFile;
        this.enableCheckpoint = builder.enableCheckpoint;
        this.checkpointDir = builder.checkpointDir;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static DownloaderOptions defaults() {
        return newBuilder().build();
    }

    public long partSize() {
        return partSize;
    }

    public int parallelNum() {
        return parallelNum;
    }

    public boolean useTempFile() {
        return useTempFile;
    }

    public boolean enableCheckpoint() {
        return enableCheckpoint;
    }

    public String checkpointDir() {
        return checkpointDir;
    }

    public static class Builder {
        private long partSize = Defaults.DEFAULT_DOWNLOAD_PART_SIZE;
        private int parallelNum = Defaults.DEFAULT_DOWNLOAD_PARALLEL;
        private boolean useTempFile = true;
        private boolean enableCheckpoint = false;
        private String checkpointDir;

        public Builder partSize(long value) {
            this.partSize = value;
            return this;
        }

        public Builder parallelNum(int value) {
            this.parallelNum = value;
            return this;
        }

        public Builder useTempFile(boolean value) {
            this.useTempFile = value;
            return this;
        }

        public Builder enableCheckpoint(boolean value) {
            this.enableCheckpoint = value;
            return this;
        }

        public Builder checkpointDir(String value) {
            this.checkpointDir = value;
            return this;
        }

        public DownloaderOptions build() {
            return new DownloaderOptions(this);
        }
    }
}
