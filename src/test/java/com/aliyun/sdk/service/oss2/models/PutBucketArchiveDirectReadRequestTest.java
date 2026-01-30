package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketArchiveDirectRead;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketArchiveDirectReadRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketArchiveDirectReadRequest request = PutBucketArchiveDirectReadRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        ArchiveDirectReadConfiguration archiveDirectReadConfiguration = ArchiveDirectReadConfiguration.newBuilder()
                .enabled(true)
                .build();

        PutBucketArchiveDirectReadRequest request = PutBucketArchiveDirectReadRequest.newBuilder()
                .bucket("examplebucket")
                .archiveDirectReadConfiguration(archiveDirectReadConfiguration)
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.archiveDirectReadConfiguration()).isEqualTo(archiveDirectReadConfiguration);
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        PutBucketArchiveDirectReadRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.archiveDirectReadConfiguration()).isEqualTo(archiveDirectReadConfiguration);
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        ArchiveDirectReadConfiguration archiveDirectReadConfiguration = ArchiveDirectReadConfiguration.newBuilder()
                .enabled(false)
                .build();

        PutBucketArchiveDirectReadRequest original = PutBucketArchiveDirectReadRequest.newBuilder()
                .bucket("test-bucket")
                .archiveDirectReadConfiguration(archiveDirectReadConfiguration)
                .build();

        PutBucketArchiveDirectReadRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.archiveDirectReadConfiguration()).isEqualTo(archiveDirectReadConfiguration);
    }

    @Test
    public void testHeaderProperties() {
        ArchiveDirectReadConfiguration archiveDirectReadConfiguration = ArchiveDirectReadConfiguration.newBuilder()
                .enabled(true)
                .build();

        PutBucketArchiveDirectReadRequest request = PutBucketArchiveDirectReadRequest.newBuilder()
                .bucket("direct-read-bucket")
                .archiveDirectReadConfiguration(archiveDirectReadConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("direct-read-bucket");
        assertThat(request.archiveDirectReadConfiguration().enabled()).isEqualTo(true);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<ArchiveDirectReadConfiguration>\n" +
                "  <Enabled>true</Enabled>\n" +
                "</ArchiveDirectReadConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ArchiveDirectReadConfiguration xmlConfiguration = xmlMapper.readValue(xml, ArchiveDirectReadConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        ArchiveDirectReadConfiguration archiveDirectReadConfiguration = ArchiveDirectReadConfiguration.newBuilder()
                .enabled(true)
                .build();

        PutBucketArchiveDirectReadRequest request = PutBucketArchiveDirectReadRequest.newBuilder()
                .bucket("xml-bucket")
                .archiveDirectReadConfiguration(archiveDirectReadConfiguration)
                .build();

        OperationInput input = SerdeBucketArchiveDirectRead.fromPutBucketArchiveDirectRead(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("bucketArchiveDirectRead")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<ArchiveDirectReadConfiguration>");
        assertThat(xmlContent).contains("<Enabled>true</Enabled>");
        assertThat(xmlContent).contains("</ArchiveDirectReadConfiguration>");

        assertThat(xmlContent).isEqualTo(expectedXml);
    }
}