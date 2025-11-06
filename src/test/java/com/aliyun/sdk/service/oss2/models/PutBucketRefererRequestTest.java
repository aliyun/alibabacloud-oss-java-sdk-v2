package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketReferer;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketRefererRequestTest {
    @Test
    public void testEmptyBuilder() {
        PutBucketRefererRequest request = PutBucketRefererRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.refererConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        List<String> referers = Arrays.asList(
                "http://www.aliyun.com",
                "https://www.aliyun.com",
                "http://www.*.com",
                "https://www.?.aliyuncs.com"
        );

        RefererList refererList = RefererList.newBuilder()
                .referers(referers)
                .build();

        List<String> blackListReferers = Arrays.asList(
                "http://www.refuse.com",
                "https://*.hack.com",
                "http://ban.*.com",
                "https://www.?.deny.com"
        );

        RefererBlacklist refererBlacklist = RefererBlacklist.newBuilder()
                .referers(blackListReferers)
                .build();

        RefererConfiguration refererConfiguration = RefererConfiguration.newBuilder()
                .allowEmptyReferer(false)
                .allowTruncateQueryString(true)
                .truncatePath(true)
                .refererList(refererList)
                .refererBlacklist(refererBlacklist)
                .build();

        PutBucketRefererRequest request = PutBucketRefererRequest.newBuilder()
                .bucket("examplebucket")
                .refererConfiguration(refererConfiguration)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.refererConfiguration()).isEqualTo(refererConfiguration);

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new java.util.AbstractMap.SimpleEntry<>("param1", "value1"),
                new java.util.AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        List<String> referers = Arrays.asList(
                "http://www.example1.com",
                "https://www.example2.com"
        );

        RefererList refererList = RefererList.newBuilder()
                .referers(referers)
                .build();

        RefererConfiguration refererConfiguration = RefererConfiguration.newBuilder()
                .allowEmptyReferer(true)
                .allowTruncateQueryString(false)
                .truncatePath(false)
                .refererList(refererList)
                .build();

        PutBucketRefererRequest original = PutBucketRefererRequest.newBuilder()
                .bucket("testbucket")
                .refererConfiguration(refererConfiguration)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutBucketRefererRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.refererConfiguration()).isEqualTo(refererConfiguration);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new java.util.AbstractMap.SimpleEntry<>("param3", "value3"),
                new java.util.AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        List<String> referers = Arrays.asList(
                "http://www.test1.com",
                "https://www.test2.com"
        );

        RefererList refererList = RefererList.newBuilder()
                .referers(referers)
                .build();

        RefererConfiguration refererConfiguration = RefererConfiguration.newBuilder()
                .allowEmptyReferer(false)
                .allowTruncateQueryString(true)
                .truncatePath(true)
                .refererList(refererList)
                .build();

        PutBucketRefererRequest request = PutBucketRefererRequest.newBuilder()
                .bucket("anotherbucket")
                .refererConfiguration(refererConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.refererConfiguration()).isEqualTo(refererConfiguration);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        List<String> referers = Arrays.asList(
                "http://www.aliyun.com",
                "https://www.aliyun.com",
                "http://www.*.com",
                "https://www.?.aliyuncs.com"
        );

        RefererList refererList = RefererList.newBuilder()
                .referers(referers)
                .build();

        List<String> blackListReferers = Arrays.asList(
                "http://www.refuse.com",
                "https://*.hack.com",
                "http://ban.*.com",
                "https://www.?.deny.com"
        );

        RefererBlacklist refererBlacklist = RefererBlacklist.newBuilder()
                .referers(blackListReferers)
                .build();

        RefererConfiguration refererConfiguration = RefererConfiguration.newBuilder()
                .allowEmptyReferer(false)
                .allowTruncateQueryString(true)
                .truncatePath(true)
                .refererList(refererList)
                .refererBlacklist(refererBlacklist)
                .build();

        PutBucketRefererRequest request = PutBucketRefererRequest.newBuilder()
                .bucket("examplebucket")
                .refererConfiguration(refererConfiguration)
                .build();

        OperationInput input = SerdeBucketReferer.fromPutBucketReferer(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("referer")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<RefererConfiguration>");
        assertThat(xmlContent).contains("<AllowEmptyReferer>false</AllowEmptyReferer>");
        assertThat(xmlContent).contains("<AllowTruncateQueryString>true</AllowTruncateQueryString>");
        assertThat(xmlContent).contains("<TruncatePath>true</TruncatePath>");
        assertThat(xmlContent).contains("<RefererList>");
        assertThat(xmlContent).contains("<Referer>http://www.aliyun.com</Referer>");
        assertThat(xmlContent).contains("<Referer>https://www.aliyun.com</Referer>");
        assertThat(xmlContent).contains("<Referer>http://www.*.com</Referer>");
        assertThat(xmlContent).contains("<Referer>https://www.?.aliyuncs.com</Referer>");
        assertThat(xmlContent).contains("</RefererList>");
        assertThat(xmlContent).contains("<RefererBlacklist>");
        assertThat(xmlContent).contains("<Referer>http://www.refuse.com</Referer>");
        assertThat(xmlContent).contains("<Referer>https://*.hack.com</Referer>");
        assertThat(xmlContent).contains("<Referer>http://ban.*.com</Referer>");
        assertThat(xmlContent).contains("<Referer>https://www.?.deny.com</Referer>");
        assertThat(xmlContent).contains("</RefererBlacklist>");
        assertThat(xmlContent).contains("</RefererConfiguration>");
    }
}