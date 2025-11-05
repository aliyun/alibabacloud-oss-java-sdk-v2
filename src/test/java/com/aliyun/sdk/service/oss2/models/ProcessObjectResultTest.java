package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.internal.ImageProcessJson;
import com.aliyun.sdk.service.oss2.transform.SerdeProcessObject;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class ProcessObjectResultTest {

    @Test
    public void testEmptyBuilder() {
        ProcessObjectResult result = ProcessObjectResult.newBuilder().build();
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

        ImageProcessJson imageProcess = ImageProcessJson.newBuilder()
                .bucket("java-sdk-test-bucket-1756804376-72-4390")
                .fileSize(5225L)
                .object("java-sdk-test-object-1756804385-191-processed.png")
                .status("OK")
                .build();

        ProcessObjectResult result = ProcessObjectResult.newBuilder()
                .headers(headers)
                .innerBody(imageProcess)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        ImageProcessJson imageProcessResult = result.imageProcessResult();
        assertThat(imageProcessResult.bucket()).isEqualTo("java-sdk-test-bucket-1756804376-72-4390");
        assertThat(imageProcessResult.fileSize()).isEqualTo(5225L);
        assertThat(imageProcessResult.object()).isEqualTo("java-sdk-test-object-1756804385-191-processed.png");
        assertThat(imageProcessResult.status()).isEqualTo("OK");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ImageProcessJson imageProcess = ImageProcessJson.newBuilder()
                .bucket("java-sdk-test-bucket-1756804376-72-4390")
                .fileSize(5225L)
                .object("java-sdk-test-object-1756804385-191-processed.png")
                .status("OK")
                .build();

        ProcessObjectResult original = ProcessObjectResult.newBuilder()
                .headers(headers)
                .innerBody(imageProcess)
                .build();

        ProcessObjectResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        ImageProcessJson imageProcessResult = copy.imageProcessResult();
        assertThat(imageProcessResult.bucket()).isEqualTo("java-sdk-test-bucket-1756804376-72-4390");
        assertThat(imageProcessResult.fileSize()).isEqualTo(5225L);
        assertThat(imageProcessResult.object()).isEqualTo("java-sdk-test-object-1756804385-191-processed.png");
        assertThat(imageProcessResult.status()).isEqualTo("OK");
    }

    @Test
    public void xmlBuilder() {
        String json = "{\n" +
                "    \"bucket\": \"java-sdk-test-bucket-1756804376-72-4390\",\n" +
                "    \"fileSize\": 5225,\n" +
                "    \"object\": \"java-sdk-test-object-1756804385-191-processed.png\",\n" +
                "    \"status\": \"OK\"\n" +
                "}";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(json))
                .build();

        ProcessObjectResult result = SerdeProcessObject.toProcessObject(output);

        ImageProcessJson imageProcessResult = result.imageProcessResult();
        assertThat(imageProcessResult.bucket()).isEqualTo("java-sdk-test-bucket-1756804376-72-4390");
        assertThat(imageProcessResult.fileSize()).isEqualTo(5225L);
        assertThat(imageProcessResult.object()).isEqualTo("java-sdk-test-object-1756804385-191-processed.png");
        assertThat(imageProcessResult.status()).isEqualTo("OK");
    }
}
