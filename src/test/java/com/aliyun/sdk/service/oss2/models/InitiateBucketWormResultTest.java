package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketWorm;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class InitiateBucketWormResultTest {

    @Test
    public void testEmptyBuilder() {
        InitiateBucketWormResult result = InitiateBucketWormResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "x-oss-worm-id", "CAEQNRiBgMD5iN64ogiBgODA0ODI3MzI2YzI1YTQxNjJiZjA5ZjIyYjIyOWQ3Mg****"
        );

        InitiateBucketWormResult result = InitiateBucketWormResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("x-oss-worm-id")).isEqualTo("CAEQNRiBgMD5iN64ogiBgODA0ODI3MzI2YzI1YTQxNjJiZjA5ZjIyYjIyOWQ3Mg****");
        assertThat(result.wormId()).isEqualTo("CAEQNRiBgMD5iN64ogiBgODA0ODI3MzI2YzI1YTQxNjJiZjA5ZjIyYjIyOWQ3Mg****");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "x-oss-worm-id", "CAEQNRiBgMD5iN64ogiBgODA0ODI3MzI2YzI1YTQxNjJiZjA5ZjIyYjIyOWQ3Mg****"
        );

        InitiateBucketWormResult original = InitiateBucketWormResult.newBuilder()
                .headers(headers)
                .build();

        InitiateBucketWormResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("x-oss-worm-id")).isEqualTo("CAEQNRiBgMD5iN64ogiBgODA0ODI3MzI2YzI1YTQxNjJiZjA5ZjIyYjIyOWQ3Mg****");
        assertThat(copy.wormId()).isEqualTo("CAEQNRiBgMD5iN64ogiBgODA0ODI3MzI2YzI1YTQxNjJiZjA5ZjIyYjIyOWQ3Mg****");
    }

    @Test
    public void testXmlBuilderWithEncoding() throws JsonProcessingException {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeBucketWorm.toInitiateBucketWorm(blankOutput);

        String xml = "";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        InitiateBucketWormResult result = SerdeBucketWorm.toInitiateBucketWorm(output);

        assertThat(result.wormId()).isNull();
    }

    @Test
    public void testXmlBuilderWithWormId() {
        String xml = "";
        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of("x-oss-worm-id", "CAEQNRiBgMD5iN64ogiBgODA0ODI3MzI2YzI1YTQxNjJiZjA5ZjIyYjIyOWQ3Mg****"))
                .build();
        InitiateBucketWormResult result = SerdeBucketWorm.toInitiateBucketWorm(output);

        assertThat(result.wormId()).isEqualTo("CAEQNRiBgMD5iN64ogiBgODA0ODI3MzI2YzI1YTQxNjJiZjA5ZjIyYjIyOWQ3Mg****");
    }
}