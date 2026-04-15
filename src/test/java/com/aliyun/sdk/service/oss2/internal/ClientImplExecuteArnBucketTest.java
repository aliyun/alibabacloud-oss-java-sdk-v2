package com.aliyun.sdk.service.oss2.internal;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.credentials.AnonymousCredentialsProvider;
import com.aliyun.sdk.service.oss2.retry.NopRetryer;
import com.aliyun.sdk.service.oss2.transport.ByteArrayBinaryData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test cases for ClientImpl.execute() method with ARN bucket mode.
 * These tests verify that the bucket parameter validation works correctly
 * when IS_BUCKET_ARN flag is set in OperationInput metadata.
 */
public class ClientImplExecuteArnBucketTest {

    private ClientImpl client;

    @Before
    public void setUp() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .retryer(new NopRetryer())
                .build();
        client = new ClientImpl(config);
    }

    @After
    public void tearDown() throws Exception {
        if (client != null) {
            client.close();
        }
    }

    // ==================== Valid ARN Bucket Tests ====================

    @Test
    public void execute_WithValidArnBucket_ShouldPassValidation() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isNotInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void execute_WithValidArnBucket_WithUnderscore_ShouldPassValidation() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my_bucket";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isNotInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void execute_WithValidArnBucket_WithHyphen_ShouldPassValidation() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket-name";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isNotInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void execute_WithValidArnBucket_WithNumbers_ShouldPassValidation() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:bucket123";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isNotInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void execute_WithValidArnBucket_MinLength_ShouldPassValidation() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:abc";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isNotInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void execute_WithValidArnBucket_MaxLength_ShouldPassValidation() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:" +
                "a1234567890123456789012345678901234567890123456789012345678901b";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isNotInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void execute_WithValidArnBucket_WithBody_ShouldPassValidation() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket";
        OperationInput input = OperationInput.newBuilder()
                .opName("PutObject")
                .method("PUT")
                .bucket(bucketArn)
                .key("test-object.txt")
                .body(new ByteArrayBinaryData("test body".getBytes()))
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isNotInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void execute_WithValidArnBucket_WithHeaders_ShouldPassValidation() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain");
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .headers(headers)
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isNotInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void execute_WithValidArnBucket_WithParameters_ShouldPassValidation() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("acl", "");
        OperationInput input = OperationInput.newBuilder()
                .opName("GetBucketAcl")
                .method("GET")
                .bucket(bucketArn)
                .parameters(parameters)
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isNotInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void executeAsync_WithValidArnBucket_ShouldPassValidation() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.executeAsync(input, opts).join())
                .isNotInstanceOf(IllegalArgumentException.class);
    }

    // ==================== Invalid ARN Bucket Tests ====================

    @Test
    public void execute_WithInvalidArnBucket_NotAcsPrefix_ShouldThrow() {
        String bucketArn = "ats:oss:cn-hangzhou:123456789012:bucket:my-bucket";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Malformed ARN - doesn't start with 'acs:'");
    }

    @Test
    public void execute_WithInvalidArnBucket_NoAccountId_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou::bucket:my-bucket";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("input.bucket does not contain account id");
    }

    @Test
    public void execute_WithInvalidArnBucket_WrongResourceType_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:object:my-bucket";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("input.bucket is not bucket arn");
    }

    @Test
    public void execute_WithInvalidArnBucket_EmptyResource_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("input.bucket is not bucket arn");
    }

    @Test
    public void execute_WithInvalidArnBucket_WithQualifier_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket:obj";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("input.bucket is not bucket arn");
    }

    @Test
    public void execute_WithInvalidArnBucket_InvalidBucketName_StartsWithHyphen_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:-my-bucket";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bucket resource is invalid");
    }

    @Test
    public void execute_WithInvalidArnBucket_InvalidBucketName_StartsWithUppercase_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:My-bucket";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bucket resource is invalid");
    }

    @Test
    public void execute_WithInvalidArnBucket_InvalidBucketName_TooShort_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:a";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bucket resource is invalid");
    }

    @Test
    public void execute_WithInvalidArnBucket_InvalidBucketName_TooLong_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:" +
                "this-is-a-very-long-bucket-name-that-exceeds-the-maximum-length-limit";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bucket resource is invalid");
    }

    @Test
    public void execute_WithInvalidArnBucket_InvalidBucketName_WithUppercase_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my-Bucket";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bucket resource is invalid");
    }

    @Test
    public void execute_WithInvalidArnBucket_InvalidBucketName_EndsWithHyphen_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket-";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bucket resource is invalid");
    }

    @Test
    public void execute_WithInvalidArnBucket_InvalidBucketName_EndsWithUnderscore_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket_";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bucket resource is invalid");
    }

    @Test
    public void execute_WithInvalidArnBucket_MalformedArn_ShouldThrow() {
        String bucketArn = "not-an-arn";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Malformed ARN - doesn't start with 'acs:'");
    }

    @Test
    public void execute_WithInvalidArnBucket_EmptyString_ShouldThrow() {
        String bucketArn = "";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Malformed ARN");
    }

    @Test
    public void execute_WithInvalidArnBucket_InvalidBucketName_EndsWithSlash_ShouldThrow() {
        String bucketArn = "acs:oss:cn-hangzhou:123456789012:bucket/my-bucket/";
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(bucketArn)
                .key("test-object.txt")
                .opMetadata(AttributeMap.empty().put(AttributeKey.IS_BUCKET_ARN, true))
                .build();
        OperationOptions opts = OperationOptions.defaults();

        assertThatThrownBy(() -> client.execute(input, opts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("input.bucket is not bucket arn");
    }

}