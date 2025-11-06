package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketRedundancyTransition;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateBucketDataRedundancyTransitionResultTest {

    @Test
    public void testEmptyBuilder() {
        CreateBucketDataRedundancyTransitionResult result = CreateBucketDataRedundancyTransitionResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        BucketDataRedundancyTransition bucketDataRedundancyTransition = BucketDataRedundancyTransition.newBuilder()
                .taskId("4be5beb0f74f490186311b268bf6****")
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        CreateBucketDataRedundancyTransitionResult result = CreateBucketDataRedundancyTransitionResult.newBuilder()
                .headers(headers)
                .innerBody(bucketDataRedundancyTransition)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        BucketDataRedundancyTransition transition = result.bucketDataRedundancyTransition();
        assertThat(transition).isNotNull();
        assertThat(transition.taskId()).isEqualTo("4be5beb0f74f490186311b268bf6****");
    }

    @Test
    public void testToBuilderPreserveState() {
        BucketDataRedundancyTransition bucketDataRedundancyTransition = BucketDataRedundancyTransition.newBuilder()
                .taskId("4be5beb0f74f490186311b268bf6****")
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        CreateBucketDataRedundancyTransitionResult original = CreateBucketDataRedundancyTransitionResult.newBuilder()
                .headers(headers)
                .innerBody(bucketDataRedundancyTransition)
                .build();

        CreateBucketDataRedundancyTransitionResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        BucketDataRedundancyTransition transition = copy.bucketDataRedundancyTransition();
        assertThat(transition).isNotNull();
        assertThat(transition.taskId()).isEqualTo("4be5beb0f74f490186311b268bf6****");
    }

    @Test
    public void testXmlBuilderWithEncoding() throws JsonProcessingException {
        String xml =
                "<BucketDataRedundancyTransition>\n" +
                "  <TaskId>4be5beb0f74f490186311b268bf6****</TaskId>\n" +
                "</BucketDataRedundancyTransition>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        CreateBucketDataRedundancyTransitionResult result = SerdeBucketRedundancyTransition.toCreateBucketDataRedundancyTransition(output);

        BucketDataRedundancyTransition transition = result.bucketDataRedundancyTransition();
        assertThat(transition).isNotNull();
        assertThat(transition.taskId()).isEqualTo("4be5beb0f74f490186311b268bf6****");
    }
}