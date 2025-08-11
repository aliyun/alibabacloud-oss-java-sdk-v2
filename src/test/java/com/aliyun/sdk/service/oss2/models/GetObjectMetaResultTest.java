package com.aliyun.sdk.service.oss2.models;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetObjectMetaResultTest {

    @Test
    public void testEmptyBuilder() {
        GetObjectMetaResult result = GetObjectMetaResult.newBuilder().build();
        assertThat(result.transitionTime()).isNull();
        assertThat(result.versionId()).isNull();
        assertThat(result.eTag()).isNull();
        assertThat(result.contentLength()).isNull();
        assertThat(result.lastAccessTime()).isNull();
        assertThat(result.lastModified()).isNull();

    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-transition-time", "2022-10-12T00:00:00.000Z");
        headers.put("x-oss-version-id", "v1234567890abcdefg");
        headers.put("ETag", "\"B5eJF1ptWaXm4bijSPyxw==\"");
        headers.put("Content-Length", "1024");
        headers.put("x-oss-last-access-time", "2022-10-12T00:00:00.000Z");
        headers.put("Last-Modified", "Fri, 12 Oct 2022 00:00:00 GMT");

        GetObjectMetaResult result = GetObjectMetaResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.transitionTime()).isEqualTo("2022-10-12T00:00:00.000Z");
        assertThat(result.versionId()).isEqualTo("v1234567890abcdefg");
        assertThat(result.eTag()).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.contentLength()).isEqualTo(1024L);
        assertThat(result.lastAccessTime()).isEqualTo("2022-10-12T00:00:00.000Z");
        assertThat(result.lastModified()).isEqualTo("Fri, 12 Oct 2022 00:00:00 GMT");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-transition-time", "2022-10-13T00:00:00.000Z");
        headers.put("x-oss-version-id", "v9876543210abcdefg");
        headers.put("ETag", "\"original-etag\"");
        headers.put("Content-Length", "2048");
        headers.put("x-oss-last-access-time", "2022-10-13T00:00:00.000Z");
        headers.put("Last-Modified", "Sat, 13 Oct 2022 00:00:00 GMT");

        GetObjectMetaResult original = GetObjectMetaResult.newBuilder()
                .headers(headers)
                .build();

        GetObjectMetaResult copy = original.toBuilder().build();

        assertThat(copy.transitionTime()).isEqualTo("2022-10-13T00:00:00.000Z");
        assertThat(copy.versionId()).isEqualTo("v9876543210abcdefg");
        assertThat(copy.eTag()).isEqualTo("\"original-etag\"");
        assertThat(copy.contentLength()).isEqualTo(2048L);
        assertThat(copy.lastAccessTime()).isEqualTo("2022-10-13T00:00:00.000Z");
        assertThat(copy.lastModified()).isEqualTo("Sat, 13 Oct 2022 00:00:00 GMT");
    }

    @Test
    public void testXmlBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/xml");
        headers.put("x-oss-transition-time", "2022-10-12T00:00:00.000Z");

        GetObjectMetaResult result = GetObjectMetaResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(result.transitionTime()).isEqualTo("2022-10-12T00:00:00.000Z");
    }
}
