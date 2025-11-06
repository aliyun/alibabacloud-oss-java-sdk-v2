package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeProcessObject;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class ProcessObjectRequestTest {

    @Test
    public void testEmptyBuilder() {
        ProcessObjectRequest request = ProcessObjectRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.process()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ProcessObjectRequest request = ProcessObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("example.jpg")
                .process("image/resize,m_fixed,w_100,h_100|sys/saveas,o_amF2YS1zZGstdGVzdC1vYmplY3QtMTc1NjgwNDM4NS0xOTEtcHJvY2Vzc2VkLnB uZw==,b_amF2YS1zZGstdGVzdC1idWNrZXQtMTc1NjgwNDM3Ni03Mi00Mzkw")
                .requestPayer("requester")
                .headers(headers)
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("example.jpg");
        assertThat(request.process()).isEqualTo("image/resize,m_fixed,w_100,h_100|sys/saveas,o_amF2YS1zZGstdGVzdC1vYmplY3QtMTc1NjgwNDM4NS0xOTEtcHJvY2Vzc2VkLnB uZw==,b_amF2YS1zZGstdGVzdC1idWNrZXQtMTc1NjgwNDM3Ni03Mi00Mzkw");
        assertThat(request.requestPayer()).isEqualTo("requester");

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ProcessObjectRequest original = ProcessObjectRequest.newBuilder()
                .bucket("testbucket")
                .key("test.jpg")
                .process("image/resize,m_fixed,w_100,h_100")
                .requestPayer("requester")
                .headers(headers)
                .build();

        ProcessObjectRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.key()).isEqualTo("test.jpg");
        assertThat(copy.process()).isEqualTo("image/resize,m_fixed,w_100,h_100");
        assertThat(copy.requestPayer()).isEqualTo("requester");

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
    }

    @Test
    public void testHeaderProperties() {
        ProcessObjectRequest request = ProcessObjectRequest.newBuilder()
                .bucket("anotherbucket")
                .key("another.jpg")
                .process("image/resize,m_fixed,w_100,h_100|sys/saveas,o_dGVzdC5qcGc=,b_dGVzdA==")
                .requestPayer("requester")
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.key()).isEqualTo("another.jpg");
        assertThat(request.process()).isEqualTo("image/resize,m_fixed,w_100,h_100|sys/saveas,o_dGVzdC5qcGc=,b_dGVzdA==");
        assertThat(request.requestPayer()).isEqualTo("requester");
    }

    @Test
    public void xmlBuilder() {
        ProcessObjectRequest request = ProcessObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("example.jpg")
                .process("image/resize,m_fixed,w_100,h_100|sys/saveas,o_amF2YS1zZGstdGVzdC1vYmplY3QtMTc1NjgwNDM4NS0xOTEtcHJvY2Vzc2VkLnBuZw==,b_amF2YS1zZGstdGVzdC1idWNrZXQtMTc1NjgwNDM3Ni03Mi00Mzkw")
                .build();

        OperationInput input = SerdeProcessObject.fromProcessObject(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.key().get()).isEqualTo("example.jpg");
        assertThat(input.parameters().get("x-oss-process")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the body content
        String bodyContent = new String(input.body().get().toBytes());
        assertThat(bodyContent).isEqualTo("x-oss-process=image/resize,m_fixed,w_100,h_100|sys/saveas,o_amF2YS1zZGstdGVzdC1vYmplY3QtMTc1NjgwNDM4NS0xOTEtcHJvY2Vzc2VkLnBuZw==,b_amF2YS1zZGstdGVzdC1idWNrZXQtMTc1NjgwNDM3Ni03Mi00Mzkw");
    }
}
