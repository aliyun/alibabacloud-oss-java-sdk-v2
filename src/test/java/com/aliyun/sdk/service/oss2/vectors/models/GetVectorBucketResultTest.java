package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorBucketBasic;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetVectorBucketResultTest {

    @Test
    public void testEmptyBuilder() {
        GetVectorBucketResult result = GetVectorBucketResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.bucketInfo()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        VectorBucketInfo vectorBucket = VectorBucketInfo.newBuilder()
                .name("oss-example")
                .location("oss-cn-hangzhou")
                .creationDate(Instant.parse("2013-07-31T10:56:21.000Z"))
                .extranetEndpoint("oss-cn-hangzhou.aliyuncs.com")
                .intranetEndpoint("oss-cn-hangzhou-internal.aliyuncs.com")
                .resourceGroupId("rg-aek27t")
                .build();

        GetVectorBucketResult result = GetVectorBucketResult.newBuilder()
                .headers(headers)
                .innerBody(vectorBucket)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        VectorBucketInfo resultVectorBucket = result.bucketInfo();
        assertThat(resultVectorBucket).isNotNull();
        assertThat(resultVectorBucket.name()).isEqualTo("oss-example");
        assertThat(resultVectorBucket.location()).isEqualTo("oss-cn-hangzhou");
        assertThat(resultVectorBucket.creationDate()).isEqualTo(Instant.parse("2013-07-31T10:56:21.000Z"));
        assertThat(resultVectorBucket.extranetEndpoint()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(resultVectorBucket.intranetEndpoint()).isEqualTo("oss-cn-hangzhou-internal.aliyuncs.com");
        assertThat(resultVectorBucket.resourceGroupId()).isEqualTo("rg-aek27t");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        VectorBucketInfo vectorBucket = VectorBucketInfo.newBuilder()
                .name("oss-example-copy")
                .location("oss-cn-shanghai")
                .creationDate(Instant.parse("2014-08-01T11:57:22.000Z"))
                .extranetEndpoint("oss-cn-shanghai.aliyuncs.com")
                .intranetEndpoint("oss-cn-shanghai-internal.aliyuncs.com")
                .resourceGroupId("rg-bfk38u")
                .build();

        GetVectorBucketResult original = GetVectorBucketResult.newBuilder()
                .headers(headers)
                .innerBody(vectorBucket)
                .status("Created")
                .statusCode(201)
                .build();

        GetVectorBucketResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        VectorBucketInfo resultVectorBucket = copy.bucketInfo();
        assertThat(resultVectorBucket).isNotNull();
        assertThat(resultVectorBucket.name()).isEqualTo("oss-example-copy");
        assertThat(resultVectorBucket.location()).isEqualTo("oss-cn-shanghai");
        assertThat(resultVectorBucket.creationDate()).isEqualTo(Instant.parse("2014-08-01T11:57:22.000Z"));
        assertThat(resultVectorBucket.extranetEndpoint()).isEqualTo("oss-cn-shanghai.aliyuncs.com");
        assertThat(resultVectorBucket.intranetEndpoint()).isEqualTo("oss-cn-shanghai-internal.aliyuncs.com");
        assertThat(resultVectorBucket.resourceGroupId()).isEqualTo("rg-bfk38u");
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {
        String jsonData = "{\n" +
                "  \"BucketInfo\": {\n" +
                "    \"CreationDate\": \"2013-07-31T10:56:21.000Z\",\n" +
                "    \"ExtranetEndpoint\": \"oss-cn-hangzhou.aliyuncs.com\",\n" +
                "    \"IntranetEndpoint\": \"oss-cn-hangzhou-internal.aliyuncs.com\",\n" +
                "    \"Location\": \"oss-cn-hangzhou\",\n" +
                "    \"Name\": \"oss-example\",\n" +
                "    \"ResourceGroupId\": \"rg-aek27t\"\n" +
                "  }\n" +
                "}";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(jsonData))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-xml-builder-test",
                        "ETag", "\"xml-builder-etag\""
                ))
                .status("OK")
                .statusCode(200)
                .build();

        GetVectorBucketResult result = SerdeVectorBucketBasic.toGetVectorBucket(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");

        VectorBucketInfo resultVectorBucket = result.bucketInfo();
        assertThat(resultVectorBucket).isNotNull();
        assertThat(resultVectorBucket.name()).isEqualTo("oss-example");
        assertThat(resultVectorBucket.location()).isEqualTo("oss-cn-hangzhou");
        assertThat(resultVectorBucket.creationDate()).isEqualTo(Instant.parse("2013-07-31T10:56:21.000Z"));
        assertThat(resultVectorBucket.extranetEndpoint()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(resultVectorBucket.intranetEndpoint()).isEqualTo("oss-cn-hangzhou-internal.aliyuncs.com");
        assertThat(resultVectorBucket.resourceGroupId()).isEqualTo("rg-aek27t");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}