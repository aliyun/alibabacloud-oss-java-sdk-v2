package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdePublicAccessBlock;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class PutPublicAccessBlockRequestTest {
    @Test
    public void testEmptyBuilder() {
        PutPublicAccessBlockRequest request = PutPublicAccessBlockRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.publicAccessBlockConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        PublicAccessBlockConfiguration configuration = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(true)
                .build();

        PutPublicAccessBlockRequest request = PutPublicAccessBlockRequest.newBuilder()
                .publicAccessBlockConfiguration(configuration)
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-1234567890abcdefg"
                ))
                .parameter("param1", "value1")
                .build();

        assertThat(request.publicAccessBlockConfiguration()).isEqualTo(configuration);
        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        PublicAccessBlockConfiguration configuration = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(false)
                .build();

        PutPublicAccessBlockRequest original = PutPublicAccessBlockRequest.newBuilder()
                .publicAccessBlockConfiguration(configuration)
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-765432109876543210"
                ))
                .parameter("param3", "value3")
                .build();

        PutPublicAccessBlockRequest copy = original.toBuilder().build();

        assertThat(copy.publicAccessBlockConfiguration()).isEqualTo(configuration);
        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3")
        );
    }

    @Test
    public void testHeaderProperties() {
        PublicAccessBlockConfiguration configuration = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(true)
                .build();

        PutPublicAccessBlockRequest request = PutPublicAccessBlockRequest.newBuilder()
                .publicAccessBlockConfiguration(configuration)
                .build();

        assertThat(request.publicAccessBlockConfiguration()).isEqualTo(configuration);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        PublicAccessBlockConfiguration configuration = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(true)
                .build();

        PutPublicAccessBlockRequest request = PutPublicAccessBlockRequest.newBuilder()
                .publicAccessBlockConfiguration(configuration)
                .build();

        OperationInput input = SerdePublicAccessBlock.fromPutPublicAccessBlock(request);

        assertThat(input.parameters().get("publicAccessBlock")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<PublicAccessBlockConfiguration>");
        assertThat(xmlContent).contains("<BlockPublicAccess>true</BlockPublicAccess>");
        assertThat(xmlContent).contains("</PublicAccessBlockConfiguration>");
    }
}