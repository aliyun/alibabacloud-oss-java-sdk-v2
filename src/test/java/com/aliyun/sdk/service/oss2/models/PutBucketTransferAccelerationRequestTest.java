package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketTransferAcceleration;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketTransferAccelerationRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketTransferAccelerationRequest request = PutBucketTransferAccelerationRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        TransferAccelerationConfiguration transferAccelerationConfiguration = TransferAccelerationConfiguration.newBuilder()
                .enabled(true)
                .build();

        PutBucketTransferAccelerationRequest request = PutBucketTransferAccelerationRequest.newBuilder()
                .bucket("examplebucket")
                .transferAccelerationConfiguration(transferAccelerationConfiguration)
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.transferAccelerationConfiguration()).isEqualTo(transferAccelerationConfiguration);
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        PutBucketTransferAccelerationRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.transferAccelerationConfiguration()).isEqualTo(transferAccelerationConfiguration);
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        TransferAccelerationConfiguration transferAccelerationConfiguration = TransferAccelerationConfiguration.newBuilder()
                .enabled(false)
                .build();

        PutBucketTransferAccelerationRequest original = PutBucketTransferAccelerationRequest.newBuilder()
                .bucket("test-bucket")
                .transferAccelerationConfiguration(transferAccelerationConfiguration)
                .build();

        PutBucketTransferAccelerationRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.transferAccelerationConfiguration()).isEqualTo(transferAccelerationConfiguration);
    }

    @Test
    public void testHeaderProperties() {
        TransferAccelerationConfiguration transferAccelerationConfiguration = TransferAccelerationConfiguration.newBuilder()
                .enabled(true)
                .build();

        PutBucketTransferAccelerationRequest request = PutBucketTransferAccelerationRequest.newBuilder()
                .bucket("acceleration-bucket")
                .transferAccelerationConfiguration(transferAccelerationConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("acceleration-bucket");
        assertThat(request.transferAccelerationConfiguration().enabled()).isEqualTo(true);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<TransferAccelerationConfiguration>\n" +
                "  <Enabled>true</Enabled>\n" +
                "</TransferAccelerationConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        TransferAccelerationConfiguration xmlConfiguration = xmlMapper.readValue(xml, TransferAccelerationConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        TransferAccelerationConfiguration transferAccelerationConfiguration = TransferAccelerationConfiguration.newBuilder()
                .enabled(true)
                .build();

        PutBucketTransferAccelerationRequest request = PutBucketTransferAccelerationRequest.newBuilder()
                .bucket("xml-bucket")
                .transferAccelerationConfiguration(transferAccelerationConfiguration)
                .build();

        OperationInput input = SerdeBucketTransferAcceleration.fromPutBucketTransferAcceleration(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("transferAcceleration")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<TransferAccelerationConfiguration>");
        assertThat(xmlContent).contains("<Enabled>true</Enabled>");
        assertThat(xmlContent).contains("</TransferAccelerationConfiguration>");

        assertThat(xmlContent).isEqualTo(expectedXml);
    }
}