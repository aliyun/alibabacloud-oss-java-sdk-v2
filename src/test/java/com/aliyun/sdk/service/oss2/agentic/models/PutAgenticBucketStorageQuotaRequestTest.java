package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketStorageQuota;
import com.aliyun.sdk.service.oss2.models.BucketStorageQuotaConfiguration;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import static org.assertj.core.api.Assertions.assertThat;

public class PutAgenticBucketStorageQuotaRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutAgenticBucketStorageQuotaRequest request = PutAgenticBucketStorageQuotaRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.bucket()).isNull();
        assertThat(request.bucketStorageQuotaConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        BucketStorageQuotaConfiguration config = BucketStorageQuotaConfiguration.newBuilder()
                .storageQuota(10737418240L)
                .mode("Strict")
                .build();

        PutAgenticBucketStorageQuotaRequest request = PutAgenticBucketStorageQuotaRequest.newBuilder()
                .bucket("example-agentic-bucket")
                .bucketStorageQuotaConfiguration(config)
                .build();

        assertThat(request.bucket()).isEqualTo("example-agentic-bucket");
        assertThat(request.bucketStorageQuotaConfiguration()).isEqualTo(config);

        PutAgenticBucketStorageQuotaRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("example-agentic-bucket");
        assertThat(copy.bucketStorageQuotaConfiguration()).isEqualTo(config);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "<QuotaConfiguration>\n" +
                "  <StorageQuota>10737418240</StorageQuota>\n" +
                "  <Mode>Warning</Mode>\n" +
                "</QuotaConfiguration>";
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        BucketStorageQuotaConfiguration xmlConfig = xmlMapper.readValue(xml, BucketStorageQuotaConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfig);

        BucketStorageQuotaConfiguration config = BucketStorageQuotaConfiguration.newBuilder()
                .storageQuota(10737418240L)
                .mode("Warning")
                .build();

        PutAgenticBucketStorageQuotaRequest request = PutAgenticBucketStorageQuotaRequest.newBuilder()
                .bucket("agentic-xml-bucket")
                .bucketStorageQuotaConfiguration(config)
                .build();

        OperationInput input = SerdeAgenticBucketStorageQuota.fromPutAgenticBucketStorageQuota(request);

        assertThat(input.bucket().get()).isEqualTo("agentic-xml-bucket");
        assertThat(input.parameters().get("agenticBucket")).isEqualTo("");
        assertThat(input.parameters().get("quota")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.method()).isEqualTo("PUT");

        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<QuotaConfiguration>");
        assertThat(xmlContent).contains("<StorageQuota>10737418240</StorageQuota>");
        assertThat(xmlContent).contains("<Mode>Warning</Mode>");
        assertThat(xmlContent).contains("</QuotaConfiguration>");

        assertThat(xmlContent).isEqualTo(expectedXml);
    }
}
