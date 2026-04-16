package com.aliyun.sdk.service.oss2.transfermanager;

public class UploaderOptions {
    private final long partSize;
    private final int parallelNum;
    private final boolean leavePartsOnError;
    private final boolean enableCheckpoint;
    private final String checkpointDir;

    private UploaderOptions(Builder builder) {
        this.partSize = builder.partSize > 0 ? builder.partSize : Defaults.DEFAULT_UPLOAD_PART_SIZE;
        this.parallelNum = builder.parallelNum > 0 ? builder.parallelNum : Defaults.DEFAULT_UPLOAD_PARALLEL;
        this.leavePartsOnError = builder.leavePartsOnError;
        this.enableCheckpoint = builder.enableCheckpoint;
        this.checkpointDir = builder.checkpointDir;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static UploaderOptions defaults() {
        return newBuilder().build();
    }

    public long partSize() {
        return partSize;
    }

    public int parallelNum() {
        return parallelNum;
    }

    public boolean leavePartsOnError() {
        return leavePartsOnError;
    }

    public boolean enableCheckpoint() {
        return enableCheckpoint;
    }

    public String checkpointDir() {
        return checkpointDir;
    }

    public static class Builder {
        private long partSize = Defaults.DEFAULT_UPLOAD_PART_SIZE;
        private int parallelNum = Defaults.DEFAULT_UPLOAD_PARALLEL;
        private boolean leavePartsOnError = false;
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

        public Builder leavePartsOnError(boolean value) {
            this.leavePartsOnError = value;
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

        public UploaderOptions build() {
            return new UploaderOptions(this);
        }
    }
}
