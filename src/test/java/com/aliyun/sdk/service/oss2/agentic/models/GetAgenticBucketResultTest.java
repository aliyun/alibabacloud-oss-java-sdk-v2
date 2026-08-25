package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAgenticBucketResultTest {

    @Test
    public void testEmptyBuilder() {
        GetAgenticBucketResult result = GetAgenticBucketResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.agenticBucketInfo()).isNull();
    }

    @Test
    public void testXmlDeserialization() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<AgenticBucketInfo>\n" +
                "  <Name>test-bucket</Name>\n" +
                "  <Owner>owner-123</Owner>\n" +
                "  <Region>cn-hangzhou</Region>\n" +
                "  <StorageClass>Standard</StorageClass>\n" +
                "  <DataRedundancyType>LRS</DataRedundancyType>\n" +
                "  <Status>enabled</Status>\n" +
                "  <BucketResourceType>AgenticBucket</BucketResourceType>\n" +
                "  <CreateTime>2024-01-01T00:00:00.000Z</CreateTime>\n" +
                "  <ACL>private</ACL>\n" +
                "  <Versioning>Enabled</Versioning>\n" +
                "</AgenticBucketInfo>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of("x-oss-request-id", "req-xml-test"))
                .status("OK")
                .statusCode(200)
                .build();

        GetAgenticBucketResult result = SerdeAgenticBucketBasic.toGetAgenticBucket(output);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);

        AgenticBucketInfo info = result.agenticBucketInfo();
        assertThat(info).isNotNull();
        assertThat(info.name()).isEqualTo("test-bucket");
        assertThat(info.owner()).isEqualTo("owner-123");
        assertThat(info.region()).isEqualTo("cn-hangzhou");
        assertThat(info.storageClass()).isEqualTo("Standard");
        assertThat(info.dataRedundancyType()).isEqualTo("LRS");
        assertThat(info.status()).isEqualTo("enabled");
        assertThat(info.bucketResourceType()).isEqualTo("AgenticBucket");
        assertThat(info.createTime()).isEqualTo("2024-01-01T00:00:00.000Z");
        assertThat(info.acl()).isEqualTo("private");
        assertThat(info.versioning()).isEqualTo("Enabled");
    }
}
