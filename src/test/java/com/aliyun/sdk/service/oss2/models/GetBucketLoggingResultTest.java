package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketLogging;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketLoggingResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketLoggingResult result = GetBucketLoggingResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        TargetSuffix targetSuffix = TargetSuffix.newBuilder()
                .useRandomPart(false)
                .build();

        LoggingEnabled loggingEnabled = LoggingEnabled.newBuilder()
                .targetBucket("mybucketlogs")
                .targetPrefix("mybucket-access_log/")
                .pushSuccessMarker(false)
                .loggingRole("")
                .targetSuffix(targetSuffix)
                .build();

        BucketLoggingStatus bucketLoggingStatus = BucketLoggingStatus.newBuilder()
                .loggingEnabled(loggingEnabled)
                .build();

        GetBucketLoggingResult result = GetBucketLoggingResult.newBuilder()
                .headers(headers)
                .innerBody(bucketLoggingStatus)
                .statusCode(200)
                .status("OK")
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.bucketLoggingStatus()).isEqualTo(bucketLoggingStatus);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        TargetSuffix targetSuffix = TargetSuffix.newBuilder()
                .useRandomPart(true)
                .build();

        LoggingEnabled loggingEnabled = LoggingEnabled.newBuilder()
                .targetBucket("mybucketlogs")
                .targetPrefix("mybucket-access_log/")
                .pushSuccessMarker(true)
                .loggingRole("test-role")
                .targetSuffix(targetSuffix)
                .build();

        BucketLoggingStatus bucketLoggingStatus = BucketLoggingStatus.newBuilder()
                .loggingEnabled(loggingEnabled)
                .build();

        GetBucketLoggingResult original = GetBucketLoggingResult.newBuilder()
                .headers(headers)
                .innerBody(bucketLoggingStatus)
                .statusCode(200)
                .status("OK")
                .build();

        GetBucketLoggingResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.status()).isEqualTo("OK");
        assertThat(copy.bucketLoggingStatus()).isEqualTo(bucketLoggingStatus);
    }

    @Test
    public void xmlBuilder() {
        String xml = 
                "<BucketLoggingStatus>\n" +
                "  <LoggingEnabled>\n" +
                "    <TargetBucket>mybucketlogs</TargetBucket>\n" +
                "    <TargetPrefix>mybucket-access_log/</TargetPrefix>\n" +
                "    <PushSuccessMarker>false</PushSuccessMarker>\n" +
                "    <LoggingRole>all</LoggingRole>\n" +
                "    <TargetSuffix>\n" +
                "      <UseRandomPart>false</UseRandomPart>\n" +
                "    </TargetSuffix>\n" +
                "  </LoggingEnabled>\n" +
                "</BucketLoggingStatus>";

        OperationOutput output = new OperationOutput.Builder()
                .body(com.aliyun.sdk.service.oss2.transport.BinaryData.fromString(xml))
                .build();

        GetBucketLoggingResult result = SerdeBucketLogging.toGetBucketLogging(output);
        BucketLoggingStatus bucketLoggingStatus = result.bucketLoggingStatus();

        assertThat(bucketLoggingStatus).isNotNull();
        assertThat(bucketLoggingStatus.loggingEnabled()).isNotNull();
        assertThat(bucketLoggingStatus.loggingEnabled().targetBucket()).isEqualTo("mybucketlogs");
        assertThat(bucketLoggingStatus.loggingEnabled().targetPrefix()).isEqualTo("mybucket-access_log/");
        assertThat(bucketLoggingStatus.loggingEnabled().pushSuccessMarker()).isEqualTo(false);
        assertThat(bucketLoggingStatus.loggingEnabled().loggingRole()).isEqualTo("all");
        assertThat(bucketLoggingStatus.loggingEnabled().targetSuffix()).isNotNull();
        assertThat(bucketLoggingStatus.loggingEnabled().targetSuffix().useRandomPart()).isEqualTo(false);
    }
}