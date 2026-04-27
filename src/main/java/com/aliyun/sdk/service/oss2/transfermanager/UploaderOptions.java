package com.aliyun.sdk.service.oss2.transfermanager;

public class UploaderOptions {
    private final long partSize;
    private final int parallelNum;
    private final boolean leavePartsOnError;
    private final boolean enableCheckpoint;
    private final String checkpointDir;
    private final boolean enableCRC64Check;

    private UploaderOptions(Builder builder) {
        this.partSize = builder.partSize > 0 ? builder.partSize : Defaults.DEFAULT_UPLOAD_PART_SIZE;
        this.parallelNum = builder.parallelNum > 0 ? builder.parallelNum : Defaults.DEFAULT_UPLOAD_PARALLEL;
        this.leavePartsOnError = builder.leavePartsOnError;
        this.enableCheckpoint = builder.enableCheckpoint;
        this.checkpointDir = builder.checkpointDir;
        this.enableCRC64Check = builder.enableCRC64Check;
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

    public boolean enableCRC64Check() {
        return enableCRC64Check;
    }

    public static class Builder {
        private long partSize = Defaults.DEFAULT_UPLOAD_PART_SIZE;
        private int parallelNum = Defaults.DEFAULT_UPLOAD_PARALLEL;
        private boolean leavePartsOnError = false;
        private boolean enableCheckpoint = false;
        private String checkpointDir;
        private boolean enableCRC64Check = Defaults.DEFAULT_UPLOAD_ENABLE_CRC64_CHECK;

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

        public Builder enableCRC64Check(boolean value) {
            this.enableCRC64Check = value;
            return this;
        }

        public UploaderOptions build() {
            return new UploaderOptions(this);
        }
    }
}
