package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ListAgenticBucketsResultTest {

    @Test
    public void testEmptyBuilder() {
        ListAgenticBucketsResult result = ListAgenticBucketsResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.agenticBuckets()).isNull();
        assertThat(result.isTruncated()).isNull();
    }

    @Test
    public void testXmlDeserialization() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ListAgenticBucketsResult>\n" +
                "  <Region>cn-hangzhou</Region>\n" +
                "  <Owner>owner-123</Owner>\n" +
                "  <ContinuationToken>token-1</ContinuationToken>\n" +
                "  <NextContinuationToken>token-2</NextContinuationToken>\n" +
                "  <IsTruncated>true</IsTruncated>\n" +
                "  <AgenticBuckets>\n" +
                "    <AgenticBucket>\n" +
                "      <Name>bucket-1</Name>\n" +
                "      <StorageClass>Standard</StorageClass>\n" +
                "      <DataRedundancyType>LRS</DataRedundancyType>\n" +
                "      <CreateTime>2024-01-01T00:00:00.000Z</CreateTime>\n" +
                "    </AgenticBucket>\n" +
                "    <AgenticBucket>\n" +
                "      <Name>bucket-2</Name>\n" +
                "      <StorageClass>IA</StorageClass>\n" +
                "      <DataRedundancyType>ZRS</DataRedundancyType>\n" +
                "      <CreateTime>2024-02-01T00:00:00.000Z</CreateTime>\n" +
                "    </AgenticBucket>\n" +
                "  </AgenticBuckets>\n" +
                "</ListAgenticBucketsResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of("x-oss-request-id", "req-list-test"))
                .status("OK")
                .statusCode(200)
                .build();

        ListAgenticBucketsResult result = SerdeAgenticBucketBasic.toListAgenticBuckets(output);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.region()).isEqualTo("cn-hangzhou");
        assertThat(result.owner()).isEqualTo("owner-123");
        assertThat(result.continuationToken()).isEqualTo("token-1");
        assertThat(result.nextContinuationToken()).isEqualTo("token-2");
        assertThat(result.isTruncated()).isTrue();

        List<AgenticBucketSummary> buckets = result.agenticBuckets();
        assertThat(buckets).isNotNull();
        assertThat(buckets).hasSize(2);

        assertThat(buckets.get(0).name()).isEqualTo("bucket-1");
        assertThat(buckets.get(0).storageClass()).isEqualTo("Standard");
        assertThat(buckets.get(0).dataRedundancyType()).isEqualTo("LRS");

        assertThat(buckets.get(1).name()).isEqualTo("bucket-2");
        assertThat(buckets.get(1).storageClass()).isEqualTo("IA");
        assertThat(buckets.get(1).dataRedundancyType()).isEqualTo("ZRS");
    }

    @Test
    public void testXmlNotTruncated() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ListAgenticBucketsResult>\n" +
                "  <Region>cn-hangzhou</Region>\n" +
                "  <IsTruncated>false</IsTruncated>\n" +
                "  <AgenticBuckets>\n" +
                "    <AgenticBucket>\n" +
                "      <Name>only-bucket</Name>\n" +
                "      <StorageClass>Standard</StorageClass>\n" +
                "    </AgenticBucket>\n" +
                "  </AgenticBuckets>\n" +
                "</ListAgenticBucketsResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of("x-oss-request-id", "req-single"))
                .status("OK")
                .statusCode(200)
                .build();

        ListAgenticBucketsResult result = SerdeAgenticBucketBasic.toListAgenticBuckets(output);
        assertThat(result.isTruncated()).isFalse();
        assertThat(result.nextContinuationToken()).isNull();
        assertThat(result.agenticBuckets()).hasSize(1);
    }
}
