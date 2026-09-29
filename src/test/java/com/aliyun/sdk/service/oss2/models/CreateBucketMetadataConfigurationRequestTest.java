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

public class CreateBucketMetadataConfigurationRequestTest {

    @Test
    public void testEmptyBuilder() {
        CreateBucketMetadataConfigurationRequest request = CreateBucketMetadataConfigurationRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.metadataConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        MetadataConfiguration configuration = newMetadataConfiguration();
        CreateBucketMetadataConfigurationRequest request = CreateBucketMetadataConfigurationRequest.newBuilder()
                .bucket("examplebucket")
                .metadataConfiguration(configuration)
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.metadataConfiguration()).isEqualTo(configuration);
        assertThat(request.headers()).contains(new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"));
        assertThat(request.parameters().get("null-param")).isNull();
    }

    @Test
    public void testToBuilderPreserveState() {
        MetadataConfiguration configuration = newMetadataConfiguration();
        CreateBucketMetadataConfigurationRequest original = CreateBucketMetadataConfigurationRequest.newBuilder()
                .bucket("testbucket")
                .metadataConfiguration(configuration)
                .header("x-header-value", "value")
                .parameter("param", "value")
                .build();

        CreateBucketMetadataConfigurationRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.metadataConfiguration()).isEqualTo(configuration);
        assertThat(copy.headers()).containsEntry("x-header-value", "value");
        assertThat(copy.parameters()).containsEntry("param", "value");
    }

    @Test
    public void testHeaderProperties() {
        MetadataConfiguration configuration = newMetadataConfiguration();
        CreateBucketMetadataConfigurationRequest request = CreateBucketMetadataConfigurationRequest.newBuilder()
                .bucket("metadata-bucket")
                .metadataConfiguration(configuration)
                .build();

        assertThat(request.bucket()).isEqualTo("metadata-bucket");
        assertThat(request.metadataConfiguration().journalTableConfiguration()
                .recordExpiration().expiration()).isEqualTo("ENABLED");
        assertThat(request.metadataConfiguration().inventoryTableConfiguration()
                .configurationState()).isEqualTo("ENABLED");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "<MetadataConfiguration>\n"
                + "  <JournalTableConfiguration>\n"
                + "    <RecordExpiration>\n"
                + "      <Expiration>ENABLED</Expiration>\n"
                + "      <Days>30</Days>\n"
                + "    </RecordExpiration>\n"
                + "    <EncryptionConfiguration>\n"
                + "      <SseAlgorithm>AES256</SseAlgorithm>\n"
                + "    </EncryptionConfiguration>\n"
                + "  </JournalTableConfiguration>\n"
                + "  <InventoryTableConfiguration>\n"
                + "    <ConfigurationState>ENABLED</ConfigurationState>\n"
                + "    <EncryptionConfiguration>\n"
                + "      <SseAlgorithm>AES256</SseAlgorithm>\n"
                + "    </EncryptionConfiguration>\n"
                + "  </InventoryTableConfiguration>\n"
                + "</MetadataConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MetadataConfiguration xmlConfiguration = xmlMapper.readValue(xml, MetadataConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        CreateBucketMetadataConfigurationRequest request = CreateBucketMetadataConfigurationRequest.newBuilder()
                .bucket("xml-bucket")
                .metadataConfiguration(newMetadataConfiguration())
                .build();
        OperationInput input = SerdeBucketMetadataConfiguration.fromCreateBucketMetadataConfiguration(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("metadataConfiguration")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.method()).isEqualTo("POST");
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).isEqualTo(expectedXml);
    }

    private static MetadataConfiguration newMetadataConfiguration() {
        MetadataTableEncryptionConfiguration encryption = MetadataTableEncryptionConfiguration.newBuilder()
                .sseAlgorithm("AES256")
                .build();
        return MetadataConfiguration.newBuilder()
                .journalTableConfiguration(JournalTableConfiguration.newBuilder()
                        .recordExpiration(MetadataTableRecordExpiration.newBuilder()
                                .expiration("ENABLED")
                                .days(30)
                                .build())
                        .encryptionConfiguration(encryption)
                        .build())
                .inventoryTableConfiguration(InventoryTableConfiguration.newBuilder()
                        .configurationState("ENABLED")
                        .encryptionConfiguration(encryption)
                        .build())
                .build();
    }
}
