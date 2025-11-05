package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketLogging;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketLoggingRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketLoggingRequest request = PutBucketLoggingRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.bucketLoggingStatus()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        LoggingEnabled loggingEnabled = LoggingEnabled.newBuilder()
                .targetBucket("target-bucket")
                .targetPrefix("target-prefix")
                .loggingRole("")
                .build();

        BucketLoggingStatus bucketLoggingStatus = BucketLoggingStatus.newBuilder()
                .loggingEnabled(loggingEnabled)
                .build();

        PutBucketLoggingRequest request = PutBucketLoggingRequest.newBuilder()
                .bucket("examplebucket")
                .bucketLoggingStatus(bucketLoggingStatus)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.bucketLoggingStatus()).isEqualTo(bucketLoggingStatus);

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        LoggingEnabled loggingEnabled = LoggingEnabled.newBuilder()
                .targetBucket("target-bucket")
                .targetPrefix("target-prefix")
                .loggingRole("test-role")
                .build();

        BucketLoggingStatus bucketLoggingStatus = BucketLoggingStatus.newBuilder()
                .loggingEnabled(loggingEnabled)
                .build();

        PutBucketLoggingRequest original = PutBucketLoggingRequest.newBuilder()
                .bucket("testbucket")
                .bucketLoggingStatus(bucketLoggingStatus)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutBucketLoggingRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.bucketLoggingStatus()).isEqualTo(bucketLoggingStatus);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {

        LoggingEnabled loggingEnabled = LoggingEnabled.newBuilder()
                .targetBucket("target-bucket")
                .targetPrefix("target-prefix")
                .loggingRole("")
                .build();

        BucketLoggingStatus bucketLoggingStatus = BucketLoggingStatus.newBuilder()
                .loggingEnabled(loggingEnabled)
                .build();

        PutBucketLoggingRequest request = PutBucketLoggingRequest.newBuilder()
                .bucket("anotherbucket")
                .bucketLoggingStatus(bucketLoggingStatus)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.bucketLoggingStatus()).isEqualTo(bucketLoggingStatus);
    }

    @Test
    public void xmlBuilder() {

        LoggingEnabled loggingEnabled = LoggingEnabled.newBuilder()
                .targetBucket("TargetBucket")
                .targetPrefix("TargetPrefix")
                .loggingRole("all")
                .build();

        BucketLoggingStatus bucketLoggingStatus = BucketLoggingStatus.newBuilder()
                .loggingEnabled(loggingEnabled)
                .build();

        PutBucketLoggingRequest request = PutBucketLoggingRequest.newBuilder()
                .bucket("examplebucket")
                .bucketLoggingStatus(bucketLoggingStatus)
                .build();

        OperationInput input = SerdeBucketLogging.fromPutBucketLogging(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("logging")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<BucketLoggingStatus>");
        assertThat(xmlContent).contains("<LoggingEnabled>");
        assertThat(xmlContent).contains("<TargetBucket>TargetBucket</TargetBucket>");
        assertThat(xmlContent).contains("<TargetPrefix>TargetPrefix</TargetPrefix>");
        assertThat(xmlContent).contains("<LoggingRole>all</LoggingRole>");
        assertThat(xmlContent).contains("</LoggingEnabled>");
        assertThat(xmlContent).contains("</BucketLoggingStatus>");
    }
}