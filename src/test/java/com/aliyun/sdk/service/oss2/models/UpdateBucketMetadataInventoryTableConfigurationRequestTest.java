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

public class UpdateBucketMetadataInventoryTableConfigurationRequestTest {

    @Test
    public void testEmptyBuilder() {
        UpdateBucketMetadataInventoryTableConfigurationRequest request =
                UpdateBucketMetadataInventoryTableConfigurationRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.inventoryTableConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        InventoryTableConfiguration configuration = newConfiguration();
        UpdateBucketMetadataInventoryTableConfigurationRequest request =
                UpdateBucketMetadataInventoryTableConfigurationRequest.newBuilder()
                        .bucket("examplebucket")
                        .inventoryTableConfiguration(configuration)
                        .header("x-header-value", "value1")
                        .header("x-header-value", "value2")
                        .parameter("empty-param", "")
                        .parameter("null-param", null)
                        .parameter("str-param", "value")
                        .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.inventoryTableConfiguration()).isEqualTo(configuration);
        assertThat(request.headers()).contains(new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"));
        assertThat(request.parameters().get("null-param")).isNull();
    }

    @Test
    public void testToBuilderPreserveState() {
        InventoryTableConfiguration configuration = newConfiguration();
        UpdateBucketMetadataInventoryTableConfigurationRequest original =
                UpdateBucketMetadataInventoryTableConfigurationRequest.newBuilder()
                        .bucket("testbucket")
                        .inventoryTableConfiguration(configuration)
                        .header("x-header-value", "value")
                        .parameter("param", "value")
                        .build();
        UpdateBucketMetadataInventoryTableConfigurationRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.inventoryTableConfiguration()).isEqualTo(configuration);
        assertThat(copy.headers()).containsEntry("x-header-value", "value");
        assertThat(copy.parameters()).containsEntry("param", "value");
    }

    @Test
    public void testHeaderProperties() {
        UpdateBucketMetadataInventoryTableConfigurationRequest request =
                UpdateBucketMetadataInventoryTableConfigurationRequest.newBuilder()
                        .bucket("metadata-bucket")
                        .inventoryTableConfiguration(newConfiguration())
                        .build();
        assertThat(request.bucket()).isEqualTo("metadata-bucket");
        assertThat(request.inventoryTableConfiguration().configurationState()).isEqualTo("ENABLED");
        assertThat(request.inventoryTableConfiguration().encryptionConfiguration()
                .sseAlgorithm()).isEqualTo("oss:kms");
        assertThat(request.inventoryTableConfiguration().encryptionConfiguration()
                .kmsKeyArn()).isEqualTo("string");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "<InventoryTableConfiguration>\n"
                + "  <ConfigurationState>ENABLED</ConfigurationState>\n"
                + "  <EncryptionConfiguration>\n"
                + "    <SseAlgorithm>oss:kms</SseAlgorithm>\n"
                + "    <KmsKeyArn>string</KmsKeyArn>\n"
                + "  </EncryptionConfiguration>\n"
                + "</InventoryTableConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        InventoryTableConfiguration xmlConfiguration = xmlMapper.readValue(
                xml, InventoryTableConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        UpdateBucketMetadataInventoryTableConfigurationRequest request =
                UpdateBucketMetadataInventoryTableConfigurationRequest.newBuilder()
                        .bucket("xml-bucket")
                        .inventoryTableConfiguration(newConfiguration())
                        .build();
        OperationInput input = SerdeBucketMetadataConfiguration
                .fromUpdateBucketMetadataInventoryTableConfiguration(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("metadataInventoryTable")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.method()).isEqualTo("PUT");
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).isEqualTo(expectedXml);
    }

    private static InventoryTableConfiguration newConfiguration() {
        return InventoryTableConfiguration.newBuilder()
                .configurationState("ENABLED")
                .encryptionConfiguration(MetadataTableEncryptionConfiguration.newBuilder()
                        .sseAlgorithm("oss:kms")
                        .kmsKeyArn("string")
                        .build())
                .build();
    }
}
