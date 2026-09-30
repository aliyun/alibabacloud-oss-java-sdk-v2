package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketStorageQuota;
import com.aliyun.sdk.service.oss2.models.QuotaConfiguration;
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
        assertThat(result.quotaConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        QuotaConfiguration config = QuotaConfiguration.newBuilder()
                .storageQuota(10737418240L)
                .mode("Strict")
                .currentUsage(1049600L)
                .build();

        GetAgenticBucketStorageQuotaResult result = GetAgenticBucketStorageQuotaResult.newBuilder()
                .headers(Collections.singletonMap("x-oss-request-id", "request-id"))
                .innerBody(config)
                .build();

        assertThat(result.requestId()).isEqualTo("request-id");
        assertThat(result.quotaConfiguration()).isNotNull();
        assertThat(result.quotaConfiguration()).isEqualTo(config);
        assertThat(result.quotaConfiguration().mode()).isEqualTo("Strict");
        assertThat(result.quotaConfiguration().storageQuota()).isEqualTo(10737418240L);
        assertThat(result.quotaConfiguration().currentUsage()).isEqualTo(1049600L);
    }

    @Test
    public void testToBuilderPreserveState() {
        QuotaConfiguration config = QuotaConfiguration.newBuilder()
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
        assertThat(copy.quotaConfiguration()).isNotNull();
        assertThat(copy.quotaConfiguration()).isEqualTo(config);
        assertThat(copy.quotaConfiguration().mode()).isEqualTo("Warning");
        assertThat(copy.quotaConfiguration().storageQuota()).isEqualTo(10737418240L);
        assertThat(copy.quotaConfiguration().currentUsage()).isEqualTo(2099200L);
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
        assertThat(result.quotaConfiguration()).isNotNull();
        assertThat(result.quotaConfiguration().mode()).isEqualTo("Strict");
        assertThat(result.quotaConfiguration().storageQuota()).isEqualTo(104857600L);
        assertThat(result.quotaConfiguration().currentUsage()).isNull();
    }
}
