package com.aliyun.sdk.service.oss2.types;

import java.util.HashSet;
import java.util.Set;

public enum FeatureFlagsType  {
    /**
     * If the client time is different from server time by more than about 15 minutes,
     * the requests your application makes will be signed with the incorrect time, and the server will reject them.
     * The feature to help to identify this case, and SDK will correct for clock skew.
     */
    CORRECT_CLOCK_SKEW(1 << 0),

    /**
     * Content-Type is automatically added based on the object name if not specified.
     * This feature takes effect for PutObject, AppendObject and InitiateMultipartUpload
     */
    AUTO_DETECT_MIMETYPE(1 << 1),

    /**
     * Check data integrity of uploads via the crc64.
     * This feature takes effect for PutObject, AppendObject, UploadPart, Uploader.UploadFrom and Uploader.UploadFile
     */
    ENABLE_CRC64_CHECK_UPLOAD(1 << 2),

    /**
     * Check data integrity of downloads via the crc64.
     * This feature takes effect for Downloader.DownloadFile
     */
    ENABLE_CRC64_CHECK_DOWNLOAD(1 << 3);


    private final int value;

    FeatureFlagsType (int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isSet(int flags) {
        return (flags & value) != 0;
    }

    public static int combine(FeatureFlagsType ... permissions) {
        int result = 0;
        for (FeatureFlagsType  p : permissions) {
            result |= p.value;
        }
        return result;
    }

    public static Set<FeatureFlagsType > parse(int flags) {
        Set<FeatureFlagsType > result = new HashSet<>();
        for (FeatureFlagsType  p : values()) {
            if (p.isSet(flags)) {
                result.add(p);
            }
        }
        return result;
    }
}