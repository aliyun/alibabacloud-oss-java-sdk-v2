package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.internal.AsyncProcessJson;
import com.aliyun.sdk.service.oss2.transform.SerdeProcessObject;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class AsyncProcessObjectResultTest {

    @Test
    public void testEmptyBuilder() {
        AsyncProcessObjectResult result = AsyncProcessObjectResult.newBuilder().build();
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

        AsyncProcessJson asyncProcess = AsyncProcessJson.newBuilder()
                .eventId("event-1234567890")
                .taskId("task-1234567890")
                .processRequestId("process-1234567890")
                .build();

        AsyncProcessObjectResult result = AsyncProcessObjectResult.newBuilder()
                .headers(headers)
                .innerBody(asyncProcess)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        AsyncProcessJson asyncProcessResult = result.asyncProcessJson();
        assertThat(asyncProcessResult.eventId()).isEqualTo("event-1234567890");
        assertThat(asyncProcessResult.taskId()).isEqualTo("task-1234567890");
        assertThat(asyncProcessResult.processRequestId()).isEqualTo("process-1234567890");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        AsyncProcessJson asyncProcess = AsyncProcessJson.newBuilder()
                .eventId("event-0987654321")
                .taskId("task-0987654321")
                .processRequestId("process-0987654321")
                .build();

        AsyncProcessObjectResult original = AsyncProcessObjectResult.newBuilder()
                .headers(headers)
                .innerBody(asyncProcess)
                .build();

        AsyncProcessObjectResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        AsyncProcessJson asyncProcessResult = copy.asyncProcessJson();
        assertThat(asyncProcessResult.eventId()).isEqualTo("event-0987654321");
        assertThat(asyncProcessResult.taskId()).isEqualTo("task-0987654321");
        assertThat(asyncProcessResult.processRequestId()).isEqualTo("process-0987654321");
    }

    @Test
    public void xmlBuilder() {
        String json = "{\n" +
                "    \"EventId\": \"event-1234567890\",\n" +
                "    \"TaskId\": \"task-1234567890\",\n" +
                "    \"RequestId\": \"process-1234567890\"\n" +
                "}";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(json))
                .build();

        AsyncProcessObjectResult result = SerdeProcessObject.toAsyncProcessObject(output);

        AsyncProcessJson asyncProcessResult = result.asyncProcessJson();
        assertThat(asyncProcessResult.eventId()).isEqualTo("event-1234567890");
        assertThat(asyncProcessResult.taskId()).isEqualTo("task-1234567890");
        assertThat(asyncProcessResult.processRequestId()).isEqualTo("process-1234567890");
    }
}
