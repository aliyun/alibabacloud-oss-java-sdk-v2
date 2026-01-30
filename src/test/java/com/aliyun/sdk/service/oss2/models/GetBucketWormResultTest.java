package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketWorm;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketWormResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketWormResult result = GetBucketWormResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        WormConfiguration wormConfiguration = WormConfiguration.newBuilder()
                .wormId("1666E2CFB2B3418****")
                .state("Locked")
                .retentionPeriodInDays(1)
                .creationDate("2020-10-15T15:50:32")
                .build();

        GetBucketWormResult result = GetBucketWormResult.newBuilder()
                .headers(headers)
                .innerBody(wormConfiguration)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        assertThat(result.wormConfiguration()).isNotNull();
        assertThat(result.wormConfiguration().wormId()).isEqualTo("1666E2CFB2B3418****");
        assertThat(result.wormConfiguration().state()).isEqualTo("Locked");
        assertThat(result.wormConfiguration().retentionPeriodInDays()).isEqualTo(1);
        assertThat(result.wormConfiguration().creationDate()).isEqualTo("2020-10-15T15:50:32");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        WormConfiguration wormConfiguration = WormConfiguration.newBuilder()
                .wormId("1666E2CFB2B3418****")
                .state("Locked")
                .retentionPeriodInDays(1)
                .creationDate("2020-10-15T15:50:32")
                .build();

        GetBucketWormResult original = GetBucketWormResult.newBuilder()
                .headers(headers)
                .innerBody(wormConfiguration)
                .build();

        GetBucketWormResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        assertThat(copy.wormConfiguration()).isNotNull();
        assertThat(copy.wormConfiguration().wormId()).isEqualTo("1666E2CFB2B3418****");
        assertThat(copy.wormConfiguration().state()).isEqualTo("Locked");
        assertThat(copy.wormConfiguration().retentionPeriodInDays()).isEqualTo(1);
        assertThat(copy.wormConfiguration().creationDate()).isEqualTo("2020-10-15T15:50:32");
    }

    @Test
    public void testXmlBuilderWithEncoding() throws JsonProcessingException {
        String xml =
                "<WormConfiguration>\n" +
                "  <WormId>1666E2CFB2B3418****</WormId>\n" +
                "  <State>Locked</State>\n" +
                "  <RetentionPeriodInDays>1</RetentionPeriodInDays>\n" +
                "  <CreationDate>2020-10-15T15:50:32</CreationDate>\n" +
                "</WormConfiguration>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetBucketWormResult result = SerdeBucketWorm.toGetBucketWorm(output);

        assertThat(result.wormConfiguration()).isNotNull();
        assertThat(result.wormConfiguration().wormId()).isEqualTo("1666E2CFB2B3418****");
        assertThat(result.wormConfiguration().state()).isEqualTo("Locked");
        assertThat(result.wormConfiguration().retentionPeriodInDays()).isEqualTo(1);
        assertThat(result.wormConfiguration().creationDate()).isEqualTo("2020-10-15T15:50:32");
    }
}