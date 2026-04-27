package com.aliyun.sdk.service.oss2.transfermanager;

public class DownloaderOptions {
    private static final int ALIGN_SIZE = 4 * 1024;

    private final long partSize;
    private final int parallelNum;
    private final boolean useTempFile;
    private final boolean enableCheckpoint;
    private final String checkpointDir;
    private final int writeBufferSize;
    private final boolean enableCRC64Check;

    private DownloaderOptions(Builder builder) {
        this.partSize = builder.partSize > 0 ? builder.partSize : Defaults.DEFAULT_DOWNLOAD_PART_SIZE;
        this.parallelNum = builder.parallelNum > 0 ? builder.parallelNum : Defaults.DEFAULT_DOWNLOAD_PARALLEL;
        this.useTempFile = builder.useTempFile;
        this.enableCheckpoint = builder.enableCheckpoint;
        this.checkpointDir = builder.checkpointDir;
        // Align to 4KB
        this.writeBufferSize = builder.writeBufferSize > 0
                ? (builder.writeBufferSize + ALIGN_SIZE - 1) & ~(ALIGN_SIZE - 1)
                : 0;
        this.enableCRC64Check = builder.enableCRC64Check;
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

    /**
     * Returns the write buffer size in bytes (aligned to 4KB), or 0 for unbuffered writes.
     */
    public int writeBufferSize() {
        return writeBufferSize;
    }

    public boolean enableCRC64Check() {
        return enableCRC64Check;
    }

    public static class Builder {
        private long partSize = Defaults.DEFAULT_DOWNLOAD_PART_SIZE;
        private int parallelNum = Defaults.DEFAULT_DOWNLOAD_PARALLEL;
        private boolean useTempFile = true;
        private boolean enableCheckpoint = false;
        private String checkpointDir;
        private int writeBufferSize = 0;
        private boolean enableCRC64Check = Defaults.DEFAULT_DOWNLOAD_ENABLE_CRC64_CHECK;

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

        /**
         * Sets the write buffer size in bytes. Will be aligned to 4KB.
         * Set to 0 (default) for unbuffered writes.
         */
        public Builder writeBufferSize(int value) {
            this.writeBufferSize = value;
            return this;
        }

        public Builder enableCRC64Check(boolean value) {
            this.enableCRC64Check = value;
            return this;
        }

        public DownloaderOptions build() {
            return new DownloaderOptions(this);
        }
    }
}
