package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CompleteMultipartUploadRequestTest {
    @Test
    public void testEmptyBuilder() {
        CompleteMultipartUploadRequest request = CompleteMultipartUploadRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.forbidOverwrite()).isNull();
        assertThat(request.completeAll()).isNull();
        assertThat(request.uploadId()).isNull();
        assertThat(request.encodingType()).isNull();
        assertThat(request.acl()).isNull();
        assertThat(request.callback()).isNull();
        assertThat(request.callbackVar()).isNull();
        assertThat(request.requestPayer()).isNull();
        assertThat(request.completeMultipartUpload()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        List<Part> parts = Arrays.asList(
                Part.newBuilder()
                        .partNumber(1L)
                        .eTag("\"25A9F4ABFCC05743DF6E2C886C56****\"")
                        .build(),
                Part.newBuilder()
                        .partNumber(5L)
                        .eTag("\"25A9F4ABFCC05743DF6E2C886C56****\"")
                        .build()
        );

        CompleteMultipartUpload completeMultipartUpload = CompleteMultipartUpload.newBuilder()
                .parts(parts)
                .build();

        CompleteMultipartUploadRequest request = CompleteMultipartUploadRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject")
                .forbidOverwrite("true")
                .completeAll("yes")
                .uploadId("upload-id-123456")
                .encodingType("url")
                .acl("public-read")
                .callback("callback-content")
                .callbackVar("callback-var-content")
                .requestPayer("requester")
                .completeMultipartUpload(completeMultipartUpload)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("exampleobject");
        assertThat(request.forbidOverwrite()).isEqualTo("true");
        assertThat(request.completeAll()).isEqualTo("yes");
        assertThat(request.uploadId()).isEqualTo("upload-id-123456");
        assertThat(request.encodingType()).isEqualTo("url");
        assertThat(request.acl()).isEqualTo("public-read");
        assertThat(request.callback()).isEqualTo("callback-content");
        assertThat(request.callbackVar()).isEqualTo("callback-var-content");
        assertThat(request.requestPayer()).isEqualTo("requester");
        assertThat(request.completeMultipartUpload()).isEqualTo(completeMultipartUpload);

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

        List<Part> parts = Arrays.asList(
                Part.newBuilder()
                        .partNumber(2L)
                        .eTag("\"35A9F4ABFCC05743DF6E2C886C56****\"")
                        .build(),
                Part.newBuilder()
                        .partNumber(6L)
                        .eTag("\"35A9F4ABFCC05743DF6E2C886C56****\"")
                        .build()
        );

        CompleteMultipartUpload completeMultipartUpload = CompleteMultipartUpload.newBuilder()
                .parts(parts)
                .build();

        CompleteMultipartUploadRequest original = CompleteMultipartUploadRequest.newBuilder()
                .bucket("testbucket")
                .key("testobject")
                .forbidOverwrite("false")
                .completeAll("no")
                .uploadId("upload-id-654321")
                .encodingType("url")
                .acl("private")
                .callback("callback-content-test")
                .callbackVar("callback-var-content-test")
                .requestPayer("requester")
                .completeMultipartUpload(completeMultipartUpload)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        CompleteMultipartUploadRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.key()).isEqualTo("testobject");
        assertThat(copy.forbidOverwrite()).isEqualTo("false");
        assertThat(copy.completeAll()).isEqualTo("no");
        assertThat(copy.uploadId()).isEqualTo("upload-id-654321");
        assertThat(copy.encodingType()).isEqualTo("url");
        assertThat(copy.acl()).isEqualTo("private");
        assertThat(copy.callback()).isEqualTo("callback-content-test");
        assertThat(copy.callbackVar()).isEqualTo("callback-var-content-test");
        assertThat(copy.requestPayer()).isEqualTo("requester");
        assertThat(copy.completeMultipartUpload()).isEqualTo(completeMultipartUpload);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        List<Part> parts = Arrays.asList(
                Part.newBuilder()
                        .partNumber(3L)
                        .eTag("\"45A9F4ABFCC05743DF6E2C886C56****\"")
                        .build(),
                Part.newBuilder()
                        .partNumber(7L)
                        .eTag("\"45A9F4ABFCC05743DF6E2C886C56****\"")
                        .build()
        );

        CompleteMultipartUpload completeMultipartUpload = CompleteMultipartUpload.newBuilder()
                .parts(parts)
                .build();

        CompleteMultipartUploadRequest request = CompleteMultipartUploadRequest.newBuilder()
                .bucket("anotherbucket")
                .key("anotherobject")
                .forbidOverwrite("true")
                .completeAll("yes")
                .uploadId("upload-id-another")
                .encodingType("url")
                .acl("public-read-write")
                .callback("another-callback-content")
                .callbackVar("another-callback-var-content")
                .requestPayer("requester")
                .completeMultipartUpload(completeMultipartUpload)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.key()).isEqualTo("anotherobject");
        assertThat(request.forbidOverwrite()).isEqualTo("true");
        assertThat(request.completeAll()).isEqualTo("yes");
        assertThat(request.uploadId()).isEqualTo("upload-id-another");
        assertThat(request.encodingType()).isEqualTo("url");
        assertThat(request.acl()).isEqualTo("public-read-write");
        assertThat(request.callback()).isEqualTo("another-callback-content");
        assertThat(request.callbackVar()).isEqualTo("another-callback-var-content");
        assertThat(request.requestPayer()).isEqualTo("requester");
        assertThat(request.completeMultipartUpload()).isEqualTo(completeMultipartUpload);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        List<Part> parts = Arrays.asList(
                Part.newBuilder()
                        .partNumber(1L)
                        .eTag("\"25A9F4ABFCC05743DF6E2C886C56****\"")
                        .build(),
                Part.newBuilder()
                        .partNumber(5L)
                        .eTag("\"25A9F4ABFCC05743DF6E2C886C56****\"")
                        .build()
        );

        CompleteMultipartUpload completeMultipartUpload = CompleteMultipartUpload.newBuilder()
                .parts(parts)
                .build();

        String xml = xmlMapper.writeValueAsString(completeMultipartUpload);

        CompleteMultipartUpload value = xmlMapper.readValue(xml, CompleteMultipartUpload.class);

        assertThat(value.parts()).hasSize(2);
        assertThat(value.parts().get(0).partNumber()).isEqualTo(1L);
        assertThat(value.parts().get(0).eTag()).isEqualTo("\"25A9F4ABFCC05743DF6E2C886C56****\"");
        assertThat(value.parts().get(1).partNumber()).isEqualTo(5L);
        assertThat(value.parts().get(1).eTag()).isEqualTo("\"25A9F4ABFCC05743DF6E2C886C56****\"");
    }
}
