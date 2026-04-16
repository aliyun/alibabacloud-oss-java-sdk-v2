package com.aliyun.sdk.service.oss2.transfermanager;

public class CopierOptions {
    private final long partSize;
    private final int parallelNum;
    private final long multipartCopyThreshold;
    private final boolean leavePartsOnError;
    private final boolean disableShallowCopy;

    private CopierOptions(Builder builder) {
        this.partSize = builder.partSize > 0 ? builder.partSize : Defaults.DEFAULT_COPY_PART_SIZE;
        this.parallelNum = builder.parallelNum > 0 ? builder.parallelNum : Defaults.DEFAULT_COPY_PARALLEL;
        this.multipartCopyThreshold = builder.multipartCopyThreshold >= 0 ? builder.multipartCopyThreshold : Defaults.DEFAULT_COPY_THRESHOLD;
        this.leavePartsOnError = builder.leavePartsOnError;
        this.disableShallowCopy = builder.disableShallowCopy;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static CopierOptions defaults() {
        return newBuilder().build();
    }

    public long partSize() {
        return partSize;
    }

    public int parallelNum() {
        return parallelNum;
    }

    public long multipartCopyThreshold() {
        return multipartCopyThreshold;
    }

    public boolean leavePartsOnError() {
        return leavePartsOnError;
    }

    public boolean disableShallowCopy() {
        return disableShallowCopy;
    }

    public static class Builder {
        private long partSize = Defaults.DEFAULT_COPY_PART_SIZE;
        private int parallelNum = Defaults.DEFAULT_COPY_PARALLEL;
        private long multipartCopyThreshold = Defaults.DEFAULT_COPY_THRESHOLD;
        private boolean leavePartsOnError = false;
        private boolean disableShallowCopy = false;

        public Builder partSize(long value) {
            this.partSize = value;
            return this;
        }

        public Builder parallelNum(int value) {
            this.parallelNum = value;
            return this;
        }

        public Builder multipartCopyThreshold(long value) {
            this.multipartCopyThreshold = value;
            return this;
        }

        public Builder leavePartsOnError(boolean value) {
            this.leavePartsOnError = value;
            return this;
        }

        public Builder disableShallowCopy(boolean value) {
            this.disableShallowCopy = value;
            return this;
        }

        public CopierOptions build() {
            return new CopierOptions(this);
        }
    }
}
