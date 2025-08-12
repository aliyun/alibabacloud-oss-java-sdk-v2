package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectMultipart;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UploadPartCopyRequestTest {
    @Test
    public void testEmptyBuilder() {
        UploadPartCopyRequest request = UploadPartCopyRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.sourceBucket()).isNull();
        assertThat(request.sourceKey()).isNull();
        assertThat(request.sourceVersionId()).isNull();
        assertThat(request.copySourceIfMatch()).isNull();
        assertThat(request.copySourceIfNoneMatch()).isNull();
        assertThat(request.copySourceIfUnmodifiedSince()).isNull();
        assertThat(request.copySourceIfModifiedSince()).isNull();
        assertThat(request.partNumber()).isNull();
        assertThat(request.uploadId()).isNull();
        assertThat(request.trafficLimit()).isNull();
        assertThat(request.requestPayer()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        UploadPartCopyRequest request = UploadPartCopyRequest.newBuilder()
                .bucket("examplebucket")
                .key("examplekey")
                .sourceBucket("sourcebucket")
                .sourceKey("sourcekey")
                .sourceVersionId("version123")
                .copySourceRange("bytes=0-1024")
                .copySourceIfMatch("\"etag-match\"")
                .copySourceIfNoneMatch("\"etag-none-match\"")
                .copySourceIfUnmodifiedSince("Fri, 13 Nov 2015 14:47:53 GMT")
                .copySourceIfModifiedSince("Fri, 14 Nov 2015 14:47:53 GMT")
                .partNumber(1L)
                .uploadId("upload-id")
                .trafficLimit(838860800L)
                .requestPayer("requester")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("examplekey");
        assertThat(request.sourceBucket()).isEqualTo("sourcebucket");
        assertThat(request.sourceKey()).isEqualTo("sourcekey");
        assertThat(request.sourceVersionId()).isEqualTo("version123");
        assertThat(request.copySourceRange()).isEqualTo("bytes=0-1024");
        assertThat(request.copySourceIfMatch()).isEqualTo("\"etag-match\"");
        assertThat(request.copySourceIfNoneMatch()).isEqualTo("\"etag-none-match\"");
        assertThat(request.copySourceIfUnmodifiedSince()).isEqualTo("Fri, 13 Nov 2015 14:47:53 GMT");
        assertThat(request.copySourceIfModifiedSince()).isEqualTo("Fri, 14 Nov 2015 14:47:53 GMT");
        assertThat(request.partNumber()).isEqualTo(1L);
        assertThat(request.uploadId()).isEqualTo("upload-id");
        assertThat(request.trafficLimit()).isEqualTo(838860800L);
        assertThat(request.requestPayer()).isEqualTo("requester");

        assertThat(request.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        UploadPartCopyRequest original = UploadPartCopyRequest.newBuilder()
                .bucket("examplebucket")
                .key("examplekey")
                .sourceBucket("sourcebucket")
                .sourceKey("sourcekey2")
                .sourceVersionId("version456")
                .copySourceRange("bytes=1025-2048")
                .copySourceIfMatch("\"etag-match-2\"")
                .copySourceIfNoneMatch("\"etag-none-match-2\"")
                .copySourceIfUnmodifiedSince("Fri, 14 Nov 2015 14:47:53 GMT")
                .copySourceIfModifiedSince("Fri, 15 Nov 2015 14:47:53 GMT")
                .partNumber(2L)
                .uploadId("upload-id-2")
                .trafficLimit(245760L)
                .requestPayer("requester")
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        UploadPartCopyRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.key()).isEqualTo("examplekey");
        assertThat(copy.sourceBucket()).isEqualTo("sourcebucket");
        assertThat(copy.sourceKey()).isEqualTo("sourcekey2");
        assertThat(copy.sourceVersionId()).isEqualTo("version456");
        assertThat(copy.copySourceRange()).isEqualTo("bytes=1025-2048");
        assertThat(copy.copySourceIfMatch()).isEqualTo("\"etag-match-2\"");
        assertThat(copy.copySourceIfNoneMatch()).isEqualTo("\"etag-none-match-2\"");
        assertThat(copy.copySourceIfUnmodifiedSince()).isEqualTo("Fri, 14 Nov 2015 14:47:53 GMT");
        assertThat(copy.copySourceIfModifiedSince()).isEqualTo("Fri, 15 Nov 2015 14:47:53 GMT");
        assertThat(copy.partNumber()).isEqualTo(2L);
        assertThat(copy.uploadId()).isEqualTo("upload-id-2");
        assertThat(copy.trafficLimit()).isEqualTo(245760L);
        assertThat(copy.requestPayer()).isEqualTo("requester");

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        UploadPartCopyRequest request = UploadPartCopyRequest.newBuilder()
                .bucket("examplebucket")
                .key("examplekey")
                .sourceBucket("sourcebucket")
                .sourceKey("sourcekey3")
                .sourceVersionId("version789")
                .copySourceRange("bytes=2049-4096")
                .copySourceIfMatch("\"etag-match-3\"")
                .copySourceIfNoneMatch("\"etag-none-match-3\"")
                .copySourceIfUnmodifiedSince("Fri, 15 Nov 2015 14:47:53 GMT")
                .copySourceIfModifiedSince("Fri, 16 Nov 2015 14:47:53 GMT")
                .partNumber(3L)
                .uploadId("upload-id-3")
                .trafficLimit(400000L)
                .requestPayer("requester")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("examplekey");
        assertThat(request.sourceBucket()).isEqualTo("sourcebucket");
        assertThat(request.sourceKey()).isEqualTo("sourcekey3");
        assertThat(request.sourceVersionId()).isEqualTo("version789");
        assertThat(request.copySourceRange()).isEqualTo("bytes=2049-4096");
        assertThat(request.copySourceIfMatch()).isEqualTo("\"etag-match-3\"");
        assertThat(request.copySourceIfNoneMatch()).isEqualTo("\"etag-none-match-3\"");
        assertThat(request.copySourceIfUnmodifiedSince()).isEqualTo("Fri, 15 Nov 2015 14:47:53 GMT");
        assertThat(request.copySourceIfModifiedSince()).isEqualTo("Fri, 16 Nov 2015 14:47:53 GMT");
        assertThat(request.partNumber()).isEqualTo(3L);
        assertThat(request.uploadId()).isEqualTo("upload-id-3");
        assertThat(request.trafficLimit()).isEqualTo(400000L);
        assertThat(request.requestPayer()).isEqualTo("requester");
    }

    @Test
    public void xmlBuilder() {
        UploadPartCopyRequest request = UploadPartCopyRequest.newBuilder()
                .bucket("destbucket")
                .key("destobject.txt")
                .sourceBucket("sourcebucket")
                .sourceKey("source object.txt")
                .sourceVersionId("version123")
                .partNumber(1L)
                .uploadId("upload-id")
                .build();

        OperationInput input = SerdeObjectMultipart.fromUploadPartCopy(request);

        assertThat(input.bucket().get()).isEqualTo("destbucket");
        assertThat(input.key().get()).isEqualTo("destobject.txt");
        assertThat(input.headers().get("x-oss-copy-source")).isEqualTo("/sourcebucket/source%20object.txt?versionId=version123");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/octet-stream");
    }
}
