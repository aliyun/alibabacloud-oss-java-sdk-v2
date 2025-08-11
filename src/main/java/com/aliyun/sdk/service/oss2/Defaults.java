package com.aliyun.sdk.service.oss2;


import com.aliyun.sdk.service.oss2.types.FeatureFlagsType;

import java.time.Duration;

public final class Defaults {

    public static final String Product = "oss";

    public static final Boolean DisableSsl = Boolean.FALSE;

    public static final String HttpScheme = "https";

    // defaults for retryer
    public static final int MAX_ATTEMPTS = 3;
    public static final Duration MAX_BACKOFF = Duration.ofSeconds(20);
    public static final Duration BASE_DELAY = Duration.ofMillis(200);

    // defaults for feature flags
    public static final int FEATURE_FLAGS = FeatureFlagsType.combine(
            FeatureFlagsType.AUTO_DETECT_MIMETYPE
    );

    private Defaults() {
    }

}
