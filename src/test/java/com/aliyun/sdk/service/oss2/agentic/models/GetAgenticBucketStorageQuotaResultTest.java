package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketStorageQuota;
import com.aliyun.sdk.service.oss2.models.BucketStorageQuotaConfiguration;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetAgenticBucketStorageQuotaResultTest {

    @Test
    public void testEmptyBuilder() {
        GetAgenticBucketStorageQuotaResult result = GetAgenticBucketStorageQuotaResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.bucketStorageQuotaConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        BucketStorageQuotaConfiguration config = BucketStorageQuotaConfiguration.newBuilder()
                .storageQuota(10737418240L)
                .mode("Strict")
                .currentUsage(1049600L)
                .build();

        GetAgenticBucketStorageQuotaResult result = GetAgenticBucketStorageQuotaResult.newBuilder()
                .headers(Collections.singletonMap("x-oss-request-id", "request-id"))
                .innerBody(config)
                .build();

        assertThat(result.requestId()).isEqualTo("request-id");
        assertThat(result.bucketStorageQuotaConfiguration()).isNotNull();
        assertThat(result.bucketStorageQuotaConfiguration()).isEqualTo(config);
        assertThat(result.bucketStorageQuotaConfiguration().mode()).isEqualTo("Strict");
        assertThat(result.bucketStorageQuotaConfiguration().storageQuota()).isEqualTo(10737418240L);
        assertThat(result.bucketStorageQuotaConfiguration().currentUsage()).isEqualTo(1049600L);
    }

    @Test
    public void testToBuilderPreserveState() {
        BucketStorageQuotaConfiguration config = BucketStorageQuotaConfiguration.newBuilder()
                .storageQuota(10737418240L)
                .mode("Warning")
                .currentUsage(2099200L)
                .build();

        GetAgenticBucketStorageQuotaResult original = GetAgenticBucketStorageQuotaResult.newBuilder()
                .headers(Collections.singletonMap("x-oss-request-id", "request-id"))
                .innerBody(config)
                .build();

        GetAgenticBucketStorageQuotaResult copy = original.toBuilder().build();

        assertThat(copy.requestId()).isEqualTo("request-id");
        assertThat(copy.bucketStorageQuotaConfiguration()).isNotNull();
        assertThat(copy.bucketStorageQuotaConfiguration()).isEqualTo(config);
        assertThat(copy.bucketStorageQuotaConfiguration().mode()).isEqualTo("Warning");
        assertThat(copy.bucketStorageQuotaConfiguration().storageQuota()).isEqualTo(10737418240L);
        assertThat(copy.bucketStorageQuotaConfiguration().currentUsage()).isEqualTo(2099200L);
    }

    @Test
    public void testXmlBuilder() {
        String xml = "<QuotaConfiguration>\n" +
                "  <Mode>Strict</Mode>\n" +
                "  <StorageQuota>104857600</StorageQuota>\n" +
                "</QuotaConfiguration>";

        Map<String, String> headers = new HashMap<>();
        OperationOutput output = OperationOutput.newBuilder()
                .status("OK")
                .statusCode(200)
                .headers(headers)
                .body(BinaryData.fromString(xml))
                .build();

        GetAgenticBucketStorageQuotaResult result = SerdeAgenticBucketStorageQuota.toGetAgenticBucketStorageQuota(output);

        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.bucketStorageQuotaConfiguration()).isNotNull();
        assertThat(result.bucketStorageQuotaConfiguration().mode()).isEqualTo("Strict");
        assertThat(result.bucketStorageQuotaConfiguration().storageQuota()).isEqualTo(104857600L);
        assertThat(result.bucketStorageQuotaConfiguration().currentUsage()).isNull();
    }
}
