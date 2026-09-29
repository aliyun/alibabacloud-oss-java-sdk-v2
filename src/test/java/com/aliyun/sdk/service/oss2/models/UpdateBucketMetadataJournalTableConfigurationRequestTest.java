package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketMetadataConfiguration;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateBucketMetadataJournalTableConfigurationRequestTest {

    @Test
    public void testEmptyBuilder() {
        UpdateBucketMetadataJournalTableConfigurationRequest request =
                UpdateBucketMetadataJournalTableConfigurationRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.journalTableConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        JournalTableConfiguration configuration = newConfiguration();
        UpdateBucketMetadataJournalTableConfigurationRequest request =
                UpdateBucketMetadataJournalTableConfigurationRequest.newBuilder()
                        .bucket("examplebucket")
                        .journalTableConfiguration(configuration)
                        .header("x-header-value", "value1")
                        .header("x-header-value", "value2")
                        .parameter("empty-param", "")
                        .parameter("null-param", null)
                        .parameter("str-param", "value")
                        .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.journalTableConfiguration()).isEqualTo(configuration);
        assertThat(request.headers()).contains(new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"));
        assertThat(request.parameters().get("null-param")).isNull();
    }

    @Test
    public void testToBuilderPreserveState() {
        JournalTableConfiguration configuration = newConfiguration();
        UpdateBucketMetadataJournalTableConfigurationRequest original =
                UpdateBucketMetadataJournalTableConfigurationRequest.newBuilder()
                        .bucket("testbucket")
                        .journalTableConfiguration(configuration)
                        .header("x-header-value", "value")
                        .parameter("param", "value")
                        .build();
        UpdateBucketMetadataJournalTableConfigurationRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.journalTableConfiguration()).isEqualTo(configuration);
        assertThat(copy.headers()).containsEntry("x-header-value", "value");
        assertThat(copy.parameters()).containsEntry("param", "value");
    }

    @Test
    public void testHeaderProperties() {
        UpdateBucketMetadataJournalTableConfigurationRequest request =
                UpdateBucketMetadataJournalTableConfigurationRequest.newBuilder()
                        .bucket("metadata-bucket")
                        .journalTableConfiguration(newConfiguration())
                        .build();
        assertThat(request.bucket()).isEqualTo("metadata-bucket");
        assertThat(request.journalTableConfiguration().recordExpiration()
                .expiration()).isEqualTo("ENABLED");
        assertThat(request.journalTableConfiguration().recordExpiration().days()).isEqualTo(30);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "<JournalTableConfiguration>\n"
                + "  <RecordExpiration>\n"
                + "    <Expiration>ENABLED</Expiration>\n"
                + "    <Days>30</Days>\n"
                + "  </RecordExpiration>\n"
                + "</JournalTableConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JournalTableConfiguration xmlConfiguration = xmlMapper.readValue(
                xml, JournalTableConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        UpdateBucketMetadataJournalTableConfigurationRequest request =
                UpdateBucketMetadataJournalTableConfigurationRequest.newBuilder()
                        .bucket("xml-bucket")
                        .journalTableConfiguration(newConfiguration())
                        .build();
        OperationInput input = SerdeBucketMetadataConfiguration
                .fromUpdateBucketMetadataJournalTableConfiguration(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("metadataJournalTable")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.method()).isEqualTo("PUT");
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).isEqualTo(expectedXml);
    }

    private static JournalTableConfiguration newConfiguration() {
        return JournalTableConfiguration.newBuilder()
                .recordExpiration(MetadataTableRecordExpiration.newBuilder()
                        .expiration("ENABLED")
                        .days(30)
                        .build())
                .build();
    }
}
