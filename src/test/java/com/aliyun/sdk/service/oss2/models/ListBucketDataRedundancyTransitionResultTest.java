package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketRedundancyTransition;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class ListBucketDataRedundancyTransitionResultTest {

    @Test
    public void testEmptyBuilder() {
        ListBucketDataRedundancyTransitionResult result = ListBucketDataRedundancyTransitionResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        List<BucketDataRedundancyTransition> bucketDataRedundancyTransitions = Arrays.asList(
                BucketDataRedundancyTransition.newBuilder()
                        .bucket("examplebucket1")
                        .taskId("4be5beb0f74f490186311b268bf6****")
                        .status("Queueing")
                        .createTime("2023-11-17T08:40:17.000Z")
                        .build(),
                BucketDataRedundancyTransition.newBuilder()
                        .bucket("examplebucket2")
                        .taskId("4be5beb0f74f490186311b268bf6j****")
                        .status("Processing")
                        .createTime("2023-11-17T08:40:17.000Z")
                        .startTime("2023-11-17T10:40:17.000Z")
                        .processPercentage(50)
                        .estimatedRemainingTime(16L)
                        .build()
        );

        ListBucketDataRedundancyTransition listBucketDataRedundancyTransition = ListBucketDataRedundancyTransition.newBuilder()
                .bucketDataRedundancyTransition(bucketDataRedundancyTransitions)
                .isTruncated(false)
                .nextContinuationToken("")
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ListBucketDataRedundancyTransitionResult result = ListBucketDataRedundancyTransitionResult.newBuilder()
                .headers(headers)
                .innerBody(listBucketDataRedundancyTransition)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        assertThat(result.listBucketDataRedundancyTransition()).isNotNull();
        assertThat(result.listBucketDataRedundancyTransition().isTruncated()).isEqualTo(false);
        assertThat(result.listBucketDataRedundancyTransition().nextContinuationToken()).isEqualTo("");
        assertThat(result.listBucketDataRedundancyTransition().bucketDataRedundancyTransition()).hasSize(2);
    }

    @Test
    public void testFullBuilderWithInstant() {
        Instant createTime = Instant.parse("2023-11-17T08:40:17.000Z");
        Instant startTime = Instant.parse("2023-11-17T10:40:17.000Z");
        Instant endTime = Instant.parse("2023-11-18T09:40:17.000Z");

        List<BucketDataRedundancyTransition> bucketDataRedundancyTransitions = Arrays.asList(
                BucketDataRedundancyTransition.newBuilder()
                        .bucket("examplebucket1")
                        .taskId("4be5beb0f74f490186311b268bf6****")
                        .status("Queueing")
                        .createTime(createTime)
                        .build(),
                BucketDataRedundancyTransition.newBuilder()
                        .bucket("examplebucket2")
                        .taskId("4be5beb0f74f490186311b268bf6j****")
                        .status("Processing")
                        .createTime(createTime)
                        .startTime(startTime)
                        .processPercentage(50)
                        .estimatedRemainingTime(16L)
                        .endTime(endTime)
                        .build()
        );

        ListBucketDataRedundancyTransition listBucketDataRedundancyTransition = ListBucketDataRedundancyTransition.newBuilder()
                .bucketDataRedundancyTransition(bucketDataRedundancyTransitions)
                .isTruncated(false)
                .nextContinuationToken("")
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ListBucketDataRedundancyTransitionResult result = ListBucketDataRedundancyTransitionResult.newBuilder()
                .headers(headers)
                .innerBody(listBucketDataRedundancyTransition)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        assertThat(result.listBucketDataRedundancyTransition()).isNotNull();
        assertThat(result.listBucketDataRedundancyTransition().isTruncated()).isEqualTo(false);
        assertThat(result.listBucketDataRedundancyTransition().nextContinuationToken()).isEqualTo("");
        assertThat(result.listBucketDataRedundancyTransition().bucketDataRedundancyTransition()).hasSize(2);

        // Verify the time fields are correctly formatted
        List<BucketDataRedundancyTransition> transitions = result.listBucketDataRedundancyTransition().bucketDataRedundancyTransition();
        BucketDataRedundancyTransition first = transitions.get(0);
        assertThat(first.createTime()).isEqualTo("2023-11-17T08:40:17.000Z");

        BucketDataRedundancyTransition second = transitions.get(1);
        assertThat(second.createTime()).isEqualTo("2023-11-17T08:40:17.000Z");
        assertThat(second.startTime()).isEqualTo("2023-11-17T10:40:17.000Z");
        assertThat(second.endTime()).isEqualTo("2023-11-18T09:40:17.000Z");
    }

    @Test
    public void testToBuilderPreserveState() {
        List<BucketDataRedundancyTransition> bucketDataRedundancyTransitions = Arrays.asList(
                BucketDataRedundancyTransition.newBuilder()
                        .bucket("examplebucket1")
                        .taskId("4be5beb0f74f490186311b268bf6****")
                        .status("Queueing")
                        .createTime("2023-11-17T08:40:17.000Z")
                        .build(),
                BucketDataRedundancyTransition.newBuilder()
                        .bucket("examplebucket2")
                        .taskId("4be5beb0f74f490186311b268bf6j****")
                        .status("Processing")
                        .createTime("2023-11-17T08:40:17.000Z")
                        .startTime("2023-11-17T10:40:17.000Z")
                        .processPercentage(50)
                        .estimatedRemainingTime(16L)
                        .build()
        );

        ListBucketDataRedundancyTransition listBucketDataRedundancyTransition = ListBucketDataRedundancyTransition.newBuilder()
                .bucketDataRedundancyTransition(bucketDataRedundancyTransitions)
                .isTruncated(false)
                .nextContinuationToken("")
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ListBucketDataRedundancyTransitionResult original = ListBucketDataRedundancyTransitionResult.newBuilder()
                .headers(headers)
                .innerBody(listBucketDataRedundancyTransition)
                .build();

        ListBucketDataRedundancyTransitionResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        assertThat(copy.listBucketDataRedundancyTransition()).isNotNull();
        assertThat(copy.listBucketDataRedundancyTransition().isTruncated()).isEqualTo(false);
        assertThat(copy.listBucketDataRedundancyTransition().nextContinuationToken()).isEqualTo("");
        assertThat(copy.listBucketDataRedundancyTransition().bucketDataRedundancyTransition()).hasSize(2);
    }

    @Test
    public void testXmlBuilderWithEncoding() throws JsonProcessingException {
        String xml =
                "<ListBucketDataRedundancyTransition>\n" +
                        "  <IsTruncated>false</IsTruncated>\n" +
                        "  <NextContinuationToken></NextContinuationToken>\n" +
                        "  <BucketDataRedundancyTransition>\n" +
                        "    <Bucket>examplebucket1</Bucket>\n" +
                        "    <TaskId>4be5beb0f74f490186311b268bf6****</TaskId>\n" +
                        "    <Status>Queueing</Status>\n" +
                        "    <CreateTime>2023-11-17T08:40:17.000Z</CreateTime>\n" +
                        "  </BucketDataRedundancyTransition>\n" +
                        "  <BucketDataRedundancyTransition>\n" +
                        "    <Bucket>examplebucket2</Bucket>\n" +
                        "    <TaskId>4be5beb0f74f490186311b268bf6j****</TaskId>\n" +
                        "    <Status>Processing</Status>\n" +
                        "    <CreateTime>2023-11-17T08:40:17.000Z</CreateTime>\n" +
                        "    <StartTime>2023-11-17T10:40:17.000Z</StartTime>\n" +
                        "    <ProcessPercentage>50</ProcessPercentage>\n" +
                        "    <EstimatedRemainingTime>16</EstimatedRemainingTime>\n" +
                        "  </BucketDataRedundancyTransition>\n" +
                        "  <BucketDataRedundancyTransition>\n" +
                        "    <Bucket>examplebucket3</Bucket>\n" +
                        "    <TaskId>4be5beb0er4f490186311b268bf6j****</TaskId>\n" +
                        "    <Status>Finished</Status>\n" +
                        "    <CreateTime>2023-11-17T08:40:17.000Z</CreateTime>\n" +
                        "    <StartTime>2023-11-17T11:40:17.000Z</StartTime>\n" +
                        "    <ProcessPercentage>100</ProcessPercentage>\n" +
                        "    <EstimatedRemainingTime>0</EstimatedRemainingTime>\n" +
                        "    <EndTime>2023-11-18T09:40:17.000Z</EndTime>\n" +
                        "  </BucketDataRedundancyTransition>\n" +
                        "</ListBucketDataRedundancyTransition>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        ListBucketDataRedundancyTransitionResult result = SerdeBucketRedundancyTransition.toListBucketDataRedundancyTransition(output);

        assertThat(result.listBucketDataRedundancyTransition()).isNotNull();
        assertThat(result.listBucketDataRedundancyTransition().isTruncated()).isEqualTo(false);
        assertThat(result.listBucketDataRedundancyTransition().nextContinuationToken()).isEqualTo("");
        assertThat(result.listBucketDataRedundancyTransition().bucketDataRedundancyTransition()).hasSize(3);

        List<BucketDataRedundancyTransition> transitions = result.listBucketDataRedundancyTransition().bucketDataRedundancyTransition();
        BucketDataRedundancyTransition first = transitions.get(0);
        assertThat(first.bucket()).isEqualTo("examplebucket1");
        assertThat(first.taskId()).isEqualTo("4be5beb0f74f490186311b268bf6****");
        assertThat(first.status()).isEqualTo("Queueing");
        assertThat(first.createTime()).isEqualTo("2023-11-17T08:40:17.000Z");

        BucketDataRedundancyTransition second = transitions.get(1);
        assertThat(second.bucket()).isEqualTo("examplebucket2");
        assertThat(second.taskId()).isEqualTo("4be5beb0f74f490186311b268bf6j****");
        assertThat(second.status()).isEqualTo("Processing");
        assertThat(second.createTime()).isEqualTo("2023-11-17T08:40:17.000Z");
        assertThat(second.startTime()).isEqualTo("2023-11-17T10:40:17.000Z");
        assertThat(second.processPercentage()).isEqualTo(50);
        assertThat(second.estimatedRemainingTime()).isEqualTo(16L);

        BucketDataRedundancyTransition third = transitions.get(2);
        assertThat(third.bucket()).isEqualTo("examplebucket3");
        assertThat(third.taskId()).isEqualTo("4be5beb0er4f490186311b268bf6j****");
        assertThat(third.status()).isEqualTo("Finished");
        assertThat(third.createTime()).isEqualTo("2023-11-17T08:40:17.000Z");
        assertThat(third.startTime()).isEqualTo("2023-11-17T11:40:17.000Z");
        assertThat(third.processPercentage()).isEqualTo(100);
        assertThat(third.estimatedRemainingTime()).isEqualTo(0L);
        assertThat(third.endTime()).isEqualTo("2023-11-18T09:40:17.000Z");
    }
}