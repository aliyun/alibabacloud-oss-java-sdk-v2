package com.aliyun.sdk.service.oss2.internal;

/**
 * Utility class for validating data formats used in OSS operations
 */
final class Ensure {

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
}
