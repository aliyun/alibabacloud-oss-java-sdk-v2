package com.aliyun.sdk.service.oss2.transfermanager;

public class CopierOptions {
    private final long partSize;
    private final int parallelNum;
    private final long multipartCopyThreshold;
    private final boolean leavePartsOnError;
    private final boolean disableShallowCopy;
    private final boolean enableCRC64Check;
    private final boolean noCheckSSE;
    private final boolean noCheckCrossBucket;

    private CopierOptions(Builder builder) {
        this.partSize = builder.partSize > 0 ? builder.partSize : Defaults.DEFAULT_COPY_PART_SIZE;
        this.parallelNum = builder.parallelNum > 0 ? builder.parallelNum : Defaults.DEFAULT_COPY_PARALLEL;
        this.multipartCopyThreshold = builder.multipartCopyThreshold >= 0 ? builder.multipartCopyThreshold : Defaults.DEFAULT_COPY_THRESHOLD;
        this.leavePartsOnError = builder.leavePartsOnError;
        this.disableShallowCopy = builder.disableShallowCopy;
        this.enableCRC64Check = builder.enableCRC64Check;
        this.noCheckSSE = builder.noCheckSSE;
        this.noCheckCrossBucket = builder.noCheckCrossBucket;
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

    public boolean enableCRC64Check() {
        return enableCRC64Check;
    }

    public boolean noCheckSSE() {
        return noCheckSSE;
    }

    public boolean noCheckCrossBucket() {
        return noCheckCrossBucket;
    }

    public static class Builder {
        private long partSize = Defaults.DEFAULT_COPY_PART_SIZE;
        private int parallelNum = Defaults.DEFAULT_COPY_PARALLEL;
        private long multipartCopyThreshold = Defaults.DEFAULT_COPY_THRESHOLD;
        private boolean leavePartsOnError = false;
        private boolean disableShallowCopy = false;
        private boolean enableCRC64Check = Defaults.DEFAULT_COPY_ENABLE_CRC64_CHECK;
        private boolean noCheckSSE = false;
        private boolean noCheckCrossBucket = false;

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

        public Builder enableCRC64Check(boolean value) {
            this.enableCRC64Check = value;
            return this;
        }

        public Builder noCheckSSE(boolean value) {
            this.noCheckSSE = value;
            return this;
        }

        public Builder noCheckCrossBucket(boolean value) {
            this.noCheckCrossBucket = value;
            return this;
        }

        public CopierOptions build() {
            return new CopierOptions(this);
        }
    }
}
