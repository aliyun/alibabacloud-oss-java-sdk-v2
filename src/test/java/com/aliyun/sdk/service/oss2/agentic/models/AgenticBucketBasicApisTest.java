package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AgenticBucketBasicApisTest {

    @Test
    public void testCreateAgenticBucketSerde() {
        CreateAgenticBucketConfiguration config = CreateAgenticBucketConfiguration.newBuilder()
                .storageClass("Standard")
                .dataRedundancyType("LRS")
                .build();

        CreateAgenticBucketRequest request = CreateAgenticBucketRequest.newBuilder()
                .bucket("test-bucket")
                .createAgenticBucketConfiguration(config)
                .build();

        OperationInput input = SerdeAgenticBucketBasic.fromCreateAgenticBucket(request);
        assertThat(input.opName()).isEqualTo("CreateAgenticBucket");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.parameters().get("agenticBucket")).isEqualTo("");
        assertThat(input.bucket().get()).isEqualTo("test-bucket");
        assertThat(input.body()).isPresent();

        String body = input.body().get().toString();
        assertThat(body).contains("CreateAgenticBucketConfiguration");
        assertThat(body).contains("<StorageClass>Standard</StorageClass>");
        assertThat(body).contains("<DataRedundancyType>LRS</DataRedundancyType>");
    }

    @Test
    public void testDeleteAgenticBucketSerde() {
        DeleteAgenticBucketRequest request = DeleteAgenticBucketRequest.newBuilder()
                .bucket("test-bucket")
                .build();

        OperationInput input = SerdeAgenticBucketBasic.fromDeleteAgenticBucket(request);
        assertThat(input.opName()).isEqualTo("DeleteAgenticBucket");
        assertThat(input.method()).isEqualTo("DELETE");
        assertThat(input.parameters().get("agenticBucket")).isEqualTo("");
        assertThat(input.bucket().get()).isEqualTo("test-bucket");
    }

    @Test
    public void testGetAgenticBucketSerde() {
        GetAgenticBucketRequest request = GetAgenticBucketRequest.newBuilder()
                .bucket("test-bucket")
                .build();

        OperationInput input = SerdeAgenticBucketBasic.fromGetAgenticBucket(request);
        assertThat(input.opName()).isEqualTo("GetAgenticBucket");
        assertThat(input.method()).isEqualTo("GET");
        assertThat(input.parameters().get("agenticBucket")).isEqualTo("");
        assertThat(input.bucket().get()).isEqualTo("test-bucket");

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<AgenticBucketInfo>\n" +
                "  <Name>test-bucket</Name>\n" +
                "  <Owner>owner-id</Owner>\n" +
                "  <Region>cn-hangzhou</Region>\n" +
                "  <StorageClass>Standard</StorageClass>\n" +
                "  <DataRedundancyType>LRS</DataRedundancyType>\n" +
                "  <Status>enabled</Status>\n" +
                "  <BucketResourceType>agentic</BucketResourceType>\n" +
                "</AgenticBucketInfo>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of("x-oss-request-id", "req-get"))
                .status("OK")
                .statusCode(200)
                .build();

        GetAgenticBucketResult result = SerdeAgenticBucketBasic.toGetAgenticBucket(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.agenticBucketInfo()).isNotNull();
        assertThat(result.agenticBucketInfo().name()).isEqualTo("test-bucket");
        assertThat(result.agenticBucketInfo().region()).isEqualTo("cn-hangzhou");
    }

    @Test
    public void testListAgenticBucketsSerde() {
        // ListAgenticBuckets is a region-level operation: it must NOT set a bucket,
        // so the request is routed to the region host rather than a bucket host.
        ListAgenticBucketsRequest request = ListAgenticBucketsRequest.newBuilder()
                .continuationToken("token-1")
                .maxKeys(50L)
                .build();

        OperationInput input = SerdeAgenticBucketBasic.fromListAgenticBuckets(request);
        assertThat(input.opName()).isEqualTo("ListAgenticBuckets");
        assertThat(input.method()).isEqualTo("GET");
        assertThat(input.parameters().get("agenticBucket")).isEqualTo("");
        assertThat(input.bucket().isPresent()).isFalse();
        assertThat(input.parameters().get("continuation-token")).isEqualTo("token-1");
        assertThat(input.parameters().get("max-keys")).isEqualTo("50");

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ListAgenticBucketsResult>\n" +
                "  <Region>cn-hangzhou</Region>\n" +
                "  <Owner>owner-id</Owner>\n" +
                "  <IsTruncated>true</IsTruncated>\n" +
                "  <NextContinuationToken>token-2</NextContinuationToken>\n" +
                "  <AgenticBuckets>\n" +
                "    <AgenticBucket>\n" +
                "      <Name>bkt-1</Name>\n" +
                "      <StorageClass>Standard</StorageClass>\n" +
                "    </AgenticBucket>\n" +
                "  </AgenticBuckets>\n" +
                "</ListAgenticBucketsResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of("x-oss-request-id", "req-list"))
                .status("OK")
                .statusCode(200)
                .build();

        ListAgenticBucketsResult result = SerdeAgenticBucketBasic.toListAgenticBuckets(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.region()).isEqualTo("cn-hangzhou");
        assertThat(result.isTruncated()).isTrue();
        assertThat(result.nextContinuationToken()).isEqualTo("token-2");
        List<AgenticBucketSummary> buckets = result.agenticBuckets();
        assertThat(buckets).hasSize(1);
        assertThat(buckets.get(0).name()).isEqualTo("bkt-1");
    }

    @Test
    public void testPutAgenticBucketStatusSerde() {
        AgenticBucketStatus status = AgenticBucketStatus.newBuilder()
                .status("enabled")
                .build();

        PutAgenticBucketStatusRequest request = PutAgenticBucketStatusRequest.newBuilder()
                .bucket("test-bucket")
                .agenticBucketStatus(status)
                .build();

        OperationInput input = SerdeAgenticBucketBasic.fromPutAgenticBucketStatus(request);
        assertThat(input.opName()).isEqualTo("PutAgenticBucketStatus");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.parameters().get("agenticBucket")).isEqualTo("");
        assertThat(input.parameters().get("status")).isEqualTo("");
        assertThat(input.bucket().get()).isEqualTo("test-bucket");
        assertThat(input.body()).isPresent();
        assertThat(input.body().get().toString()).contains("<Status>enabled</Status>");
    }

    @Test
    public void testListBucketSpacesSerde() {
        // ListBucketSpaces must emit BOTH the agenticBucket and bucketSpace subresources.
        ListBucketSpacesRequest request = ListBucketSpacesRequest.newBuilder()
                .bucket("test-bucket")
                .prefix("space-")
                .continuationToken("token-1")
                .maxKeys(20L)
                .build();

        OperationInput input = SerdeAgenticBucketBasic.fromListBucketSpaces(request);
        assertThat(input.opName()).isEqualTo("ListBucketSpaces");
        assertThat(input.method()).isEqualTo("GET");
        assertThat(input.parameters().get("agenticBucket")).isEqualTo("");
        assertThat(input.parameters().get("bucketSpace")).isEqualTo("");
        assertThat(input.bucket().get()).isEqualTo("test-bucket");
        assertThat(input.parameters().get("prefix")).isEqualTo("space-");
        assertThat(input.parameters().get("continuation-token")).isEqualTo("token-1");
        assertThat(input.parameters().get("max-keys")).isEqualTo("20");

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ListBucketSpacesResult>\n" +
                "  <Prefix>space-</Prefix>\n" +
                "  <MaxKeys>20</MaxKeys>\n" +
                "  <IsTruncated>false</IsTruncated>\n" +
                "  <BucketSpaces>\n" +
                "    <BucketSpace>\n" +
                "      <Name>space-1</Name>\n" +
                "      <StorageClass>Standard</StorageClass>\n" +
                "    </BucketSpace>\n" +
                "  </BucketSpaces>\n" +
                "</ListBucketSpacesResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of("x-oss-request-id", "req-spaces"))
                .status("OK")
                .statusCode(200)
                .build();

        ListBucketSpacesResult result = SerdeAgenticBucketBasic.toListBucketSpaces(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.prefix()).isEqualTo("space-");
        assertThat(result.maxKeys()).isEqualTo(20);
        assertThat(result.isTruncated()).isFalse();
        List<BucketSpaceSummary> spaces = result.bucketSpaces();
        assertThat(spaces).hasSize(1);
        assertThat(spaces.get(0).name()).isEqualTo("space-1");
    }
}
