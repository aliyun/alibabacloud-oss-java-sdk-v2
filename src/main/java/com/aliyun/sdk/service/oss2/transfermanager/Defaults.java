package com.aliyun.sdk.service.oss2.transfermanager;

public final class Defaults {

    public static final int MAX_UPLOAD_PARTS = 10000;

    public static final long DEFAULT_PART_SIZE = 6 * 1024 * 1024;

    public static final long DEFAULT_UPLOAD_PART_SIZE = DEFAULT_PART_SIZE;

    public static final long DEFAULT_DOWNLOAD_PART_SIZE = DEFAULT_PART_SIZE;

    public static final long DEFAULT_COPY_PART_SIZE = 64 * 1024 * 1024;

    public static final int DEFAULT_PARALLEL = 3;

    public static final int DEFAULT_UPLOAD_PARALLEL = DEFAULT_PARALLEL;

    public static final int DEFAULT_DOWNLOAD_PARALLEL = DEFAULT_PARALLEL;

    public static final int DEFAULT_COPY_PARALLEL = DEFAULT_PARALLEL;

    public static final long DEFAULT_COPY_THRESHOLD = 200 * 1024 * 1024;

    public static final String TEMP_FILE_SUFFIX = ".temp";

    public static final String CHECKPOINT_MAGIC = "92611BED-89E2-46B6-89E5-72F273D4B0A3";

    public static final String CHECKPOINT_FILE_SUFFIX_DOWNLOADER = ".dcp";

    public static final String CHECKPOINT_FILE_SUFFIX_UPLOADER = ".ucp";

    private Defaults() {
    }
}
