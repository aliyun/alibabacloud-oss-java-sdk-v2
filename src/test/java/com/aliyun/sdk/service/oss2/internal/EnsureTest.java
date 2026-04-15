package com.aliyun.sdk.service.oss2.internal;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EnsureTest {

    @Test
    public void assertValidateArnBucket_ValidBucketArn_ShouldPass() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket";

        Ensure.assertValidateArnBucket(bucketArn);
    }

    @Test
    public void assertValidateArnBucket_ValidBucketArn_WithUnderscore_ShouldPass() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my_bucket";

        Ensure.assertValidateArnBucket(bucketArn);
    }

    @Test
    public void assertValidateArnBucket_ValidBucketArn_WithHyphen_ShouldPass() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket-name";

        Ensure.assertValidateArnBucket(bucketArn);
    }

    @Test
    public void assertValidateArnBucket_ValidBucketArn_WithNumbers_ShouldPass() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:bucket123";

        Ensure.assertValidateArnBucket(bucketArn);
    }

    @Test
    public void assertValidateArnBucket_InvalidArn_NotStartingWithAcs_ShouldThrow() {
        String bucketArn = "ats:oss:cn-hangzhou:123456789012:bucket:my-bucket";

        assertThatThrownBy(() -> Ensure.assertValidateArnBucket(bucketArn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Malformed ARN - doesn't start with 'acs:'");
    }

    @Test
    public void assertValidateArnBucket_InvalidArn_Null_ShouldThrow() {
        // When null is passed, Arn.fromString throws IllegalArgumentException with "ARN parsing failed"
        assertThatThrownBy(() -> Ensure.assertValidateArnBucket(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ARN parsing failed");
    }

    @Test
    public void assertValidateArnBucket_InvalidArn_NoAccountId_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou::bucket:my-bucket";

        assertThatThrownBy(() -> Ensure.assertValidateArnBucket(bucketArn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("input.bucket does not contain account id");
    }

    @Test
    public void assertValidateArnBucket_InvalidArn_WrongResourceType_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:object:my-bucket";

        assertThatThrownBy(() -> Ensure.assertValidateArnBucket(bucketArn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("input.bucket is not bucket arn");
    }

    @Test
    public void assertValidateArnBucket_InvalidArn_EmptyResource_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:";

        assertThatThrownBy(() -> Ensure.assertValidateArnBucket(bucketArn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("input.bucket is not bucket arn");
    }

    @Test
    public void assertValidateArnBucket_InvalidArn_WithQualifier_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket:obj";

        assertThatThrownBy(() -> Ensure.assertValidateArnBucket(bucketArn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("input.bucket is not bucket arn");
    }

    @Test
    public void assertValidateArnBucket_InvalidBucketName_StartsWithHyphen_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:-my-bucket";

        assertThatThrownBy(() -> Ensure.assertValidateArnBucket(bucketArn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bucket resource is invalid");
    }

    @Test
    public void assertValidateArnBucket_InvalidBucketName_StartsWithUppercase_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:My-bucket";

        assertThatThrownBy(() -> Ensure.assertValidateArnBucket(bucketArn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bucket resource is invalid");
    }

    @Test
    public void assertValidateArnBucket_InvalidBucketName_TooShort_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:a";

        assertThatThrownBy(() -> Ensure.assertValidateArnBucket(bucketArn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bucket resource is invalid");
    }

    @Test
    public void assertValidateArnBucket_InvalidBucketName_TooLong_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:" +
                "this-is-a-very-long-bucket-name-that-exceeds-the-maximum-length-limit";

        assertThatThrownBy(() -> Ensure.assertValidateArnBucket(bucketArn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bucket resource is invalid");
    }

    @Test
    public void assertValidateArnBucket_InvalidBucketName_WithUppercase_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my-Bucket";

        assertThatThrownBy(() -> Ensure.assertValidateArnBucket(bucketArn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bucket resource is invalid");
    }

    @Test
    public void assertValidateArnBucket_InvalidBucketName_EndsWithHyphen_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket-";

        assertThatThrownBy(() -> Ensure.assertValidateArnBucket(bucketArn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bucket resource is invalid");
    }

    @Test
    public void assertValidateArnBucket_InvalidBucketName_EndsWithUnderscore_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket_";

        assertThatThrownBy(() -> Ensure.assertValidateArnBucket(bucketArn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bucket resource is invalid");
    }

    @Test
    public void assertValidateArnBucket_ValidBucketName_MinLength_ShouldPass() {
        // Minimum length is 3: first char + 1 middle char + last char
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:abc";

        Ensure.assertValidateArnBucket(bucketArn);
    }

    @Test
    public void assertValidateArnBucket_ValidBucketName_MaxLength_ShouldPass() {
        // Maximum length is 63: first char + 61 middle chars + last char
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:" +
                "a1234567890123456789012345678901234567890123456789012345678901b";

        Ensure.assertValidateArnBucket(bucketArn);
    }

    @Test
    public void assertValidateArnBucket_InvalidArn_Malformed_ShouldThrow() {
        String bucketArn = "not-an-arn";

        assertThatThrownBy(() -> Ensure.assertValidateArnBucket(bucketArn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Malformed ARN - doesn't start with 'acs:'");
    }

    @Test
    public void assertValidateArnBucket_InvalidArn_EmptyString_ShouldThrow() {
        String bucketArn = "";

        assertThatThrownBy(() -> Ensure.assertValidateArnBucket(bucketArn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Malformed ARN - doesn't start with 'acs:'");
    }

    @Test
    public void assertValidateArnBucket_InvalidArn_SimpleBucketName_ShouldThrow() {
        String bucketArn = "my-bucket";

        assertThatThrownBy(() -> Ensure.assertValidateArnBucket(bucketArn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Malformed ARN - doesn't start with 'acs:'");
    }
}
