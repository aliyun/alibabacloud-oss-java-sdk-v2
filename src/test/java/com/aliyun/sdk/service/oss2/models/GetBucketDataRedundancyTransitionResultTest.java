package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketRedundancyTransition;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.ZoneOffset;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketDataRedundancyTransitionResultTest {

    private static DateTimeFormatter ISO_INSTANT = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .toFormatter();
    @Test
    public void testEmptyBuilder() {
        GetBucketDataRedundancyTransitionResult result = GetBucketDataRedundancyTransitionResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        String createTimeStr = Instant.parse("2023-11-17T09:14:39.000Z").atOffset(ZoneOffset.UTC).format(ISO_INSTANT);
        String startTimeStr = Instant.parse("2023-11-17T09:14:39.000Z").atOffset(ZoneOffset.UTC).format(ISO_INSTANT);
        String endTimeStr = Instant.parse("2023-11-18T09:14:39.000Z").atOffset(ZoneOffset.UTC).format(ISO_INSTANT);

        BucketDataRedundancyTransition bucketDataRedundancyTransition = BucketDataRedundancyTransition.newBuilder()
                .bucket("examplebucket")
                .taskId("909c6c818dd041d1a44e0fdc66aa****")
                .status("Finished")
                .createTime(createTimeStr)
                .startTime(startTimeStr)
                .processPercentage(100)
                .estimatedRemainingTime(0L)
                .endTime(endTimeStr)
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetBucketDataRedundancyTransitionResult result = GetBucketDataRedundancyTransitionResult.newBuilder()
                .headers(headers)
                .innerBody(bucketDataRedundancyTransition)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        BucketDataRedundancyTransition transition = result.bucketDataRedundancyTransition();
        assertThat(transition).isNotNull();
        assertThat(transition.bucket()).isEqualTo("examplebucket");
        assertThat(transition.taskId()).isEqualTo("909c6c818dd041d1a44e0fdc66aa****");
        assertThat(transition.status()).isEqualTo("Finished");
        assertThat(transition.createTime()).isEqualTo(createTimeStr);
        assertThat(transition.startTime()).isEqualTo(startTimeStr);
        assertThat(transition.processPercentage()).isEqualTo(100);
        assertThat(transition.estimatedRemainingTime()).isEqualTo(0L);
        assertThat(transition.endTime()).isEqualTo(endTimeStr);
    }

    @Test
    public void testToBuilderPreserveState() {
        String createTimeStr = Instant.parse("2023-11-17T09:14:39.000Z").atOffset(ZoneOffset.UTC).format(ISO_INSTANT);
        String startTimeStr = Instant.parse("2023-11-17T09:14:39.000Z").atOffset(ZoneOffset.UTC).format(ISO_INSTANT);
        String endTimeStr = Instant.parse("2023-11-18T09:14:39.000Z").atOffset(ZoneOffset.UTC).format(ISO_INSTANT);

        BucketDataRedundancyTransition bucketDataRedundancyTransition = BucketDataRedundancyTransition.newBuilder()
                .bucket("examplebucket")
                .taskId("909c6c818dd041d1a44e0fdc66aa****")
                .status("Finished")
                .createTime(createTimeStr)
                .startTime(startTimeStr)
                .processPercentage(100)
                .estimatedRemainingTime(0L)
                .endTime(endTimeStr)
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        GetBucketDataRedundancyTransitionResult original = GetBucketDataRedundancyTransitionResult.newBuilder()
                .headers(headers)
                .innerBody(bucketDataRedundancyTransition)
                .build();

        GetBucketDataRedundancyTransitionResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        BucketDataRedundancyTransition transition = copy.bucketDataRedundancyTransition();
        assertThat(transition).isNotNull();
        assertThat(transition.bucket()).isEqualTo("examplebucket");
        assertThat(transition.taskId()).isEqualTo("909c6c818dd041d1a44e0fdc66aa****");
        assertThat(transition.status()).isEqualTo("Finished");
        assertThat(transition.createTime()).isEqualTo(createTimeStr);
        assertThat(transition.startTime()).isEqualTo(startTimeStr);
        assertThat(transition.processPercentage()).isEqualTo(100);
        assertThat(transition.estimatedRemainingTime()).isEqualTo(0L);
        assertThat(transition.endTime()).isEqualTo(endTimeStr);
    }

    @Test
    public void testXmlBuilderWithEncoding() throws JsonProcessingException {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<BucketDataRedundancyTransition>\n" +
                "  <Bucket>examplebucket</Bucket>\n" +
                "  <TaskId>909c6c818dd041d1a44e0fdc66aa****</TaskId>\n" +
                "  <Status>Finished</Status>\n" +
                "  <CreateTime>2023-11-17T09:14:39.000Z</CreateTime>\n" +
                "  <StartTime>2023-11-17T09:14:39.000Z</StartTime>\n" +
                "  <ProcessPercentage>100</ProcessPercentage>\n" +
                "  <EstimatedRemainingTime>0</EstimatedRemainingTime>\n" +
                "  <EndTime>2023-11-18T09:14:39.000Z</EndTime>\n" +
                "</BucketDataRedundancyTransition>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetBucketDataRedundancyTransitionResult result = SerdeBucketRedundancyTransition.toGetBucketDataRedundancyTransition(output);

        BucketDataRedundancyTransition transition = result.bucketDataRedundancyTransition();
        assertThat(transition).isNotNull();
        assertThat(transition.bucket()).isEqualTo("examplebucket");
        assertThat(transition.taskId()).isEqualTo("909c6c818dd041d1a44e0fdc66aa****");
        assertThat(transition.status()).isEqualTo("Finished");
        assertThat(transition.createTime()).isEqualTo("2023-11-17T09:14:39.000Z");
        assertThat(transition.startTime()).isEqualTo("2023-11-17T09:14:39.000Z");
        assertThat(transition.processPercentage()).isEqualTo(100);
        assertThat(transition.estimatedRemainingTime()).isEqualTo(0L);
        assertThat(transition.endTime()).isEqualTo("2023-11-18T09:14:39.000Z");
    }
}