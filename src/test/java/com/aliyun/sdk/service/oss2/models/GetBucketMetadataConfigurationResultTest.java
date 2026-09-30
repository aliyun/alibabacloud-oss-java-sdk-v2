package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.internal.GetBucketMetadataConfigurationResultXml;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketMetadataConfiguration;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketMetadataConfigurationResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketMetadataConfigurationResult result = GetBucketMetadataConfigurationResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.metadataConfigurationResult()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\"");
        MetadataConfigurationResult configuration = new MetadataConfigurationResult();
        GetBucketMetadataConfigurationResultXml innerBody = new GetBucketMetadataConfigurationResultXml();
        innerBody.metadataConfigurationResult = configuration;
        GetBucketMetadataConfigurationResult result = GetBucketMetadataConfigurationResult.newBuilder()
                .headers(headers)
                .innerBody(innerBody)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.metadataConfigurationResult()).isEqualTo(configuration);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of("x-oss-request-id", "req-765432109876543210");
        MetadataConfigurationResult configuration = new MetadataConfigurationResult();
        GetBucketMetadataConfigurationResultXml innerBody = new GetBucketMetadataConfigurationResultXml();
        innerBody.metadataConfigurationResult = configuration;
        GetBucketMetadataConfigurationResult original = GetBucketMetadataConfigurationResult.newBuilder()
                .headers(headers)
                .innerBody(innerBody)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();
        GetBucketMetadataConfigurationResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.metadataConfigurationResult()).isEqualTo(configuration);
    }

    @Test
    public void xmlBuilder() {
        String xml = "<GetBucketMetadataConfigurationResult>"
                + "<MetadataConfigurationResult>"
                + "<DestinationResult>"
                + "<TableBucketType>oss</TableBucketType>"
                + "<TableBucketArn>acs:oss:cn-hangzhou:123456789:bucket/tablebucket</TableBucketArn>"
                + "<TableNamespace>b_examplebucket</TableNamespace>"
                + "</DestinationResult>"
                + "<JournalTableConfigurationResult>"
                + "<TableStatus>ACTIVE</TableStatus>"
                + "<TableName>journal</TableName>"
                + "<TableArn>journal-arn</TableArn>"
                + "<RecordExpiration><Expiration>ENABLED</Expiration><Days>30</Days></RecordExpiration>"
                + "<EncryptionConfiguration><SseAlgorithm>AES256</SseAlgorithm>"
                + "<KmsKeyArn>kms-key-arn</KmsKeyArn></EncryptionConfiguration>"
                + "<Error><ErrorCode>JournalError</ErrorCode>"
                + "<ErrorMessage>journal error message</ErrorMessage></Error>"
                + "</JournalTableConfigurationResult>"
                + "<InventoryTableConfigurationResult>"
                + "<ConfigurationState>ENABLED</ConfigurationState>"
                + "<TableStatus>ACTIVE</TableStatus>"
                + "<TableName>inventory</TableName>"
                + "<TableArn>inventory-arn</TableArn>"
                + "<EncryptionConfiguration><SseAlgorithm>AES256</SseAlgorithm>"
                + "<KmsKeyArn>kms-key-arn</KmsKeyArn></EncryptionConfiguration>"
                + "<Error><ErrorCode>InventoryError</ErrorCode>"
                + "<ErrorMessage>inventory error message</ErrorMessage></Error>"
                + "</InventoryTableConfigurationResult>"
                + "</MetadataConfigurationResult>"
                + "</GetBucketMetadataConfigurationResult>";
        Map<String, String> headers = MapUtils.of("x-oss-request-id", "req-1234567890abcdefg");
        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();
        GetBucketMetadataConfigurationResult result = SerdeBucketMetadataConfiguration
                .toGetBucketMetadataConfiguration(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);

        MetadataConfigurationResult configuration = result.metadataConfigurationResult();
        assertThat(configuration).isNotNull();
        assertThat(configuration.destinationResult().tableBucketType()).isEqualTo("oss");
        assertThat(configuration.destinationResult().tableBucketArn())
                .isEqualTo("acs:oss:cn-hangzhou:123456789:bucket/tablebucket");
        assertThat(configuration.destinationResult().tableNamespace()).isEqualTo("b_examplebucket");

        JournalTableConfigurationResult journal =
                configuration.journalTableConfigurationResult();
        assertThat(journal.tableStatus()).isEqualTo("ACTIVE");
        assertThat(journal.tableName()).isEqualTo("journal");
        assertThat(journal.tableArn()).isEqualTo("journal-arn");
        assertThat(journal.recordExpiration().expiration()).isEqualTo("ENABLED");
        assertThat(journal.recordExpiration().days()).isEqualTo(30);
        assertThat(journal.encryptionConfiguration().sseAlgorithm()).isEqualTo("AES256");
        assertThat(journal.encryptionConfiguration().kmsKeyArn()).isEqualTo("kms-key-arn");
        assertThat(journal.error().errorCode()).isEqualTo("JournalError");
        assertThat(journal.error().errorMessage()).isEqualTo("journal error message");

        InventoryTableConfigurationResult inventory =
                configuration.inventoryTableConfigurationResult();
        assertThat(inventory.configurationState()).isEqualTo("ENABLED");
        assertThat(inventory.tableStatus()).isEqualTo("ACTIVE");
        assertThat(inventory.tableName()).isEqualTo("inventory");
        assertThat(inventory.tableArn()).isEqualTo("inventory-arn");
        assertThat(inventory.encryptionConfiguration().sseAlgorithm()).isEqualTo("AES256");
        assertThat(inventory.encryptionConfiguration().kmsKeyArn()).isEqualTo("kms-key-arn");
        assertThat(inventory.error().errorCode()).isEqualTo("InventoryError");
        assertThat(inventory.error().errorMessage()).isEqualTo("inventory error message");
    }
}
