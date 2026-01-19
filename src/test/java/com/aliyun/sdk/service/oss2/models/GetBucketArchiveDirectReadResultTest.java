package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketArchiveDirectRead;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketArchiveDirectReadResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketArchiveDirectReadResult result = GetBucketArchiveDirectReadResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.archiveDirectReadConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ArchiveDirectReadConfiguration configuration = ArchiveDirectReadConfiguration.newBuilder()
                .enabled(true)
                .build();

        GetBucketArchiveDirectReadResult result = GetBucketArchiveDirectReadResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .innerBody(configuration)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.archiveDirectReadConfiguration().enabled()).isEqualTo(true);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ArchiveDirectReadConfiguration configuration = ArchiveDirectReadConfiguration.newBuilder()
                .enabled(false)
                .build();

        GetBucketArchiveDirectReadResult original = GetBucketArchiveDirectReadResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .innerBody(configuration)
                .build();

        GetBucketArchiveDirectReadResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.archiveDirectReadConfiguration().enabled()).isEqualTo(false);
    }

    @Test
    public void serdeBuilder() {
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ArchiveDirectReadConfiguration>\n" +
                "    <Enabled>true</Enabled>\n" +
                "</ArchiveDirectReadConfiguration>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xmlContent))
                .build();

        GetBucketArchiveDirectReadResult result = SerdeBucketArchiveDirectRead.toGetBucketArchiveDirectRead(output);

        assertThat(result).isNotNull();
        assertThat(result.archiveDirectReadConfiguration()).isNotNull();
        assertThat(result.archiveDirectReadConfiguration().enabled()).isEqualTo(true);
    }
}