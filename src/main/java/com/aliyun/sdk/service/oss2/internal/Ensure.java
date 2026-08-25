package com.aliyun.sdk.service.oss2.internal;

import com.aliyun.sdk.service.oss2.arns.Arn;
import com.aliyun.sdk.service.oss2.arns.ArnResource;
import com.aliyun.sdk.service.oss2.utils.StringUtils;

import java.util.Optional;

/**
 * Utility class for validating data formats used in OSS operations
 */
public final class Ensure {

    /**
     * Regular expression for validating bucket name format
     */
    private static final String BUCKET_NAMING_REGEX = "^[a-z0-9][a-z0-9-_]{1,61}[a-z0-9]$";

    /**
     * Regular expression for validating region format
     */
    private static final String REGION_REGEX = "^[a-z0-9-]+$";

    /**
     * Checks whether the given string is a valid region identifier
     *
     * @param value The string to validate
     * @return true if the string matches the region format, false otherwise
     */
    public static boolean isValidRegion(String value) {
        if (value == null) {
            return false;
        }
        return value.matches(REGION_REGEX);
    }

    /**
     * Checks whether the given string is a valid account id (non-empty pure digits)
     *
     * @param value The account id to validate
     * @return true if the string is non-empty and contains only digits, false otherwise
     */
    public static boolean isValidAccountId(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates whether the given string is a properly formatted bucket name
     *
     * @param value The bucket name to validate
     * @return true if the name conforms to the naming rules, false otherwise
     */
    public static boolean isValidateBucketName(String value) {
        if (value == null) {
            return false;
        }
        return value.matches(BUCKET_NAMING_REGEX);
    }

    /**
     * Validates whether the given string is a valid object key (Object Name)
     *
     * @param value The object key to validate
     * @return true if the length is within limits and not empty, false otherwise
     */
    public static boolean isValidateObjectName(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return value.length() < 1024;
    }

    public static void assertValidateArnBucket(String bucket) {
        Arn arn = Arn.fromString(bucket);

        // must have account id
        String accountId = arn.accountId().orElse(null);
        if (StringUtils.isNullOrEmpty(accountId)) {
            throw new IllegalArgumentException("input.bucket does not contain account id");
        }

        // account id must be valid (pure digits)
        if (!isValidAccountId(accountId)) {
            throw new IllegalArgumentException("input.bucket contains invalid account id: " + accountId);
        }

        // must have bucket resource
        ArnResource resource = arn.resource();
        if (!"bucket".equals(resource.resourceType().orElse("")) ||
                StringUtils.isBlank(resource.resource()) ||
                !StringUtils.isBlank(resource.qualifier().orElse(null)) ||
                bucket.endsWith("/")) {
            throw new IllegalArgumentException(String.format("input.bucket is not bucket arn, got " + bucket + "."));
        }

        // check bucket value
        if (!isValidateBucketName(resource.resource())) {
            throw new IllegalArgumentException("bucket resource is invalid, got " + bucket + ".");
        }
    }
}
