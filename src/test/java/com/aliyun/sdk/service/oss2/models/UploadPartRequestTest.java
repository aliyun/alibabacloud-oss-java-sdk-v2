package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UploadPartRequestTest {
    @Test
    public void testEmptyBuilder() {
        UploadPartRequest request = UploadPartRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.partNumber()).isNull();
        assertThat(request.uploadId()).isNull();
        assertThat(request.contentMD5()).isNull();
        assertThat(request.contentLength()).isNull();
        assertThat(request.trafficLimit()).isNull();
        assertThat(request.requestPayer()).isNull();
        assertThat(request.body()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        BinaryData body = BinaryData.fromString("test data");

        UploadPartRequest request = UploadPartRequest.newBuilder()
                .bucket("examplebucket")
                .key("examplekey")
                .partNumber(1L)
                .uploadId("upload-id")
                .contentMD5("md5hash")
                .contentLength(1024L)
                .trafficLimit(838860800L)
                .requestPayer("requester")
                .body(body)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("examplekey");
        assertThat(request.partNumber()).isEqualTo(1L);
        assertThat(request.uploadId()).isEqualTo("upload-id");
        assertThat(request.contentMD5()).isEqualTo("md5hash");
        assertThat(request.contentLength()).isEqualTo(1024L);
        assertThat(request.trafficLimit()).isEqualTo(838860800L);
        assertThat(request.requestPayer()).isEqualTo("requester");
        assertThat(request.body()).isEqualTo(body);

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

        BinaryData body = BinaryData.fromString("original test data");

        UploadPartRequest original = UploadPartRequest.newBuilder()
                .bucket("examplebucket")
                .key("examplekey")
                .partNumber(2L)
                .uploadId("upload-id-2")
                .contentMD5("md5hash2")
                .contentLength(2048L)
                .trafficLimit(245760L)
                .requestPayer("requester")
                .body(body)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        UploadPartRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.key()).isEqualTo("examplekey");
        assertThat(copy.partNumber()).isEqualTo(2L);
        assertThat(copy.uploadId()).isEqualTo("upload-id-2");
        assertThat(copy.contentMD5()).isEqualTo("md5hash2");
        assertThat(copy.contentLength()).isEqualTo(2048L);
        assertThat(copy.trafficLimit()).isEqualTo(245760L);
        assertThat(copy.requestPayer()).isEqualTo("requester");
        assertThat(copy.body()).isEqualTo(body);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        BinaryData body = BinaryData.fromString("another test data");

        UploadPartRequest request = UploadPartRequest.newBuilder()
                .bucket("examplebucket")
                .key("examplekey")
                .partNumber(3L)
                .uploadId("upload-id-3")
                .contentMD5("md5hash3")
                .contentLength(4096L)
                .trafficLimit(400000L)
                .requestPayer("requester")
                .body(body)
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("examplekey");
        assertThat(request.partNumber()).isEqualTo(3L);
        assertThat(request.uploadId()).isEqualTo("upload-id-3");
        assertThat(request.contentMD5()).isEqualTo("md5hash3");
        assertThat(request.contentLength()).isEqualTo(4096L);
        assertThat(request.trafficLimit()).isEqualTo(400000L);
        assertThat(request.requestPayer()).isEqualTo("requester");
        assertThat(request.body()).isEqualTo(body);
    }
}
