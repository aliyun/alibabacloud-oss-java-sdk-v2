package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketStorageQuota;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketStorageQuotaRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketStorageQuotaRequest request = PutBucketStorageQuotaRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.bucketStorageQuotaConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        BucketStorageQuotaConfiguration config = BucketStorageQuotaConfiguration.newBuilder()
                .storageQuota(10737418240L)
                .mode("Strict")
                .build();

        PutBucketStorageQuotaRequest request = PutBucketStorageQuotaRequest.newBuilder()
                .bucket("examplebucket")
                .bucketStorageQuotaConfiguration(config)
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.bucketStorageQuotaConfiguration()).isEqualTo(config);
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        PutBucketStorageQuotaRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.bucketStorageQuotaConfiguration()).isEqualTo(config);
    }

    @Test
    public void testToBuilderPreserveState() {
        BucketStorageQuotaConfiguration config = BucketStorageQuotaConfiguration.newBuilder()
                .storageQuota(10737418240L)
                .mode("Warning")
                .build();

        PutBucketStorageQuotaRequest original = PutBucketStorageQuotaRequest.newBuilder()
                .bucket("test-bucket")
                .bucketStorageQuotaConfiguration(config)
                .build();

        PutBucketStorageQuotaRequest copy = original.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.bucketStorageQuotaConfiguration()).isEqualTo(config);
    }

    @Test
    public void testHeaderProperties() {
        BucketStorageQuotaConfiguration config = BucketStorageQuotaConfiguration.newBuilder()
                .storageQuota(10737418240L)
                .mode("Strict")
                .build();

        PutBucketStorageQuotaRequest request = PutBucketStorageQuotaRequest.newBuilder()
                .bucket("quota-bucket")
                .bucketStorageQuotaConfiguration(config)
                .build();

        assertThat(request.bucket()).isEqualTo("quota-bucket");
        assertThat(request.bucketStorageQuotaConfiguration().storageQuota()).isEqualTo(10737418240L);
        assertThat(request.bucketStorageQuotaConfiguration().mode()).isEqualTo("Strict");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "<QuotaConfiguration>\n" +
                "  <StorageQuota>10737418240</StorageQuota>\n" +
                "  <Mode>Strict</Mode>\n" +
                "</QuotaConfiguration>";
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        BucketStorageQuotaConfiguration xmlConfig = xmlMapper.readValue(xml, BucketStorageQuotaConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfig);

        BucketStorageQuotaConfiguration config = BucketStorageQuotaConfiguration.newBuilder()
                .storageQuota(10737418240L)
                .mode("Strict")
                .build();

        PutBucketStorageQuotaRequest request = PutBucketStorageQuotaRequest.newBuilder()
                .bucket("xml-bucket")
                .bucketStorageQuotaConfiguration(config)
                .build();

        OperationInput input = SerdeBucketStorageQuota.fromPutBucketStorageQuota(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("quota")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.method()).isEqualTo("PUT");

        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<QuotaConfiguration>");
        assertThat(xmlContent).contains("<StorageQuota>10737418240</StorageQuota>");
        assertThat(xmlContent).contains("<Mode>Strict</Mode>");
        assertThat(xmlContent).contains("</QuotaConfiguration>");

        assertThat(xmlContent).isEqualTo(expectedXml);
    }
}
