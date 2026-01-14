package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketInventory;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketInventoryResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketInventoryResult result = GetBucketInventoryResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        // Build a complete InventoryConfiguration object
        InventorySchedule schedule = InventorySchedule.newBuilder()
                .frequency("Daily")
                .build();

        InventoryFilter filter = InventoryFilter.newBuilder()
                .prefix("myprefix/")
                .build();

        InventoryEncryption encryption = InventoryEncryption.newBuilder()
                .sseOss("")
                .build();

        InventoryOSSBucketDestination bucketDestination = InventoryOSSBucketDestination.newBuilder()
                .format("CSV")
                .accountId("1000000000000000")
                .roleArn("acs:ram::1000000000000000:role/AliyunOSSRole")
                .bucket("acs:oss:::bucket_0001")
                .prefix("prefix1")
                .encryption(encryption)
                .build();

        InventoryDestination destination = InventoryDestination.newBuilder()
                .oSSBucketDestination(bucketDestination)
                .build();

        List<InventoryOptionalFieldType> optionalFields = Arrays.asList(
                InventoryOptionalFieldType.SIZE,
                InventoryOptionalFieldType.LAST_MODIFIED_DATE,
                InventoryOptionalFieldType.E_TAG,
                InventoryOptionalFieldType.STORAGE_CLASS,
                InventoryOptionalFieldType.IS_MULTIPART_UPLOADED,
                InventoryOptionalFieldType.ENCRYPTION_STATUS
        );

        OptionalFields optionalFieldsContainer = OptionalFields.newBuilder()
                .fields(optionalFields)
                .build();

        InventoryConfiguration inventoryConfiguration = InventoryConfiguration.newBuilder()
                .id("report1")
                .isEnabled(true)
                .destination(destination)
                .schedule(schedule)
                .filter(filter)
                .includedObjectVersions("All")
                .optionalFields(optionalFieldsContainer)
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetBucketInventoryResult result = GetBucketInventoryResult.newBuilder()
                .headers(headers)
                .innerBody(inventoryConfiguration)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        assertThat(result.inventoryConfiguration().id()).isEqualTo("report1");
        assertThat(result.inventoryConfiguration().isEnabled()).isEqualTo(true);
        assertThat(result.inventoryConfiguration().destination().oSSBucketDestination().format()).isEqualTo("CSV");
        assertThat(result.inventoryConfiguration().destination().oSSBucketDestination().accountId()).isEqualTo("1000000000000000");
        assertThat(result.inventoryConfiguration().destination().oSSBucketDestination().roleArn()).isEqualTo("acs:ram::1000000000000000:role/AliyunOSSRole");
        assertThat(result.inventoryConfiguration().destination().oSSBucketDestination().bucket()).isEqualTo("acs:oss:::bucket_0001");
        assertThat(result.inventoryConfiguration().destination().oSSBucketDestination().prefix()).isEqualTo("prefix1");
        assertThat(result.inventoryConfiguration().schedule().frequency()).isEqualTo("Daily");
        assertThat(result.inventoryConfiguration().filter().prefix()).isEqualTo("myprefix/");
        assertThat(result.inventoryConfiguration().includedObjectVersions()).isEqualTo("All");
        assertThat(result.inventoryConfiguration().optionalFields().fields()).hasSize(6);
        assertThat(result.inventoryConfiguration().optionalFields().fields().get(0)).isEqualTo(InventoryOptionalFieldType.SIZE);
        assertThat(result.inventoryConfiguration().optionalFields().fields().get(1)).isEqualTo(InventoryOptionalFieldType.LAST_MODIFIED_DATE);
        assertThat(result.inventoryConfiguration().optionalFields().fields().get(2)).isEqualTo(InventoryOptionalFieldType.E_TAG);
        assertThat(result.inventoryConfiguration().optionalFields().fields().get(3)).isEqualTo(InventoryOptionalFieldType.STORAGE_CLASS);
        assertThat(result.inventoryConfiguration().optionalFields().fields().get(4)).isEqualTo(InventoryOptionalFieldType.IS_MULTIPART_UPLOADED);
        assertThat(result.inventoryConfiguration().optionalFields().fields().get(5)).isEqualTo(InventoryOptionalFieldType.ENCRYPTION_STATUS);
    }

    @Test
    public void testToBuilderPreserveState() {
        // Build a complete InventoryConfiguration object
        InventorySchedule schedule = InventorySchedule.newBuilder()
                .frequency("Weekly")
                .build();

        InventoryFilter filter = InventoryFilter.newBuilder()
                .prefix("testprefix/")
                .build();

        InventoryEncryption encryption = InventoryEncryption.newBuilder()
                .sseOss("")
                .build();

        InventoryOSSBucketDestination bucketDestination = InventoryOSSBucketDestination.newBuilder()
                .format("CSV")
                .accountId("2000000000000000")
                .roleArn("acs:ram::2000000000000000:role/AliyunOSSRole")
                .bucket("acs:oss:::bucket_0002")
                .prefix("prefix2")
                .encryption(encryption)
                .build();

        InventoryDestination destination = InventoryDestination.newBuilder()
                .oSSBucketDestination(bucketDestination)
                .build();

        List<InventoryOptionalFieldType> optionalFields = Arrays.asList(
                InventoryOptionalFieldType.SIZE,
                InventoryOptionalFieldType.LAST_MODIFIED_DATE
        );

        OptionalFields optionalFieldsContainer = OptionalFields.newBuilder()
                .fields(optionalFields)
                .build();

        InventoryConfiguration inventoryConfiguration = InventoryConfiguration.newBuilder()
                .id("report2")
                .isEnabled(false)
                .destination(destination)
                .schedule(schedule)
                .filter(filter)
                .includedObjectVersions("Current")
                .optionalFields(optionalFieldsContainer)
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        GetBucketInventoryResult original = GetBucketInventoryResult.newBuilder()
                .headers(headers)
                .innerBody(inventoryConfiguration)
                .build();

        GetBucketInventoryResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        assertThat(copy.inventoryConfiguration().id()).isEqualTo("report2");
        assertThat(copy.inventoryConfiguration().isEnabled()).isEqualTo(false);
        assertThat(copy.inventoryConfiguration().destination().oSSBucketDestination().format()).isEqualTo("CSV");
        assertThat(copy.inventoryConfiguration().destination().oSSBucketDestination().accountId()).isEqualTo("2000000000000000");
        assertThat(copy.inventoryConfiguration().destination().oSSBucketDestination().roleArn()).isEqualTo("acs:ram::2000000000000000:role/AliyunOSSRole");
        assertThat(copy.inventoryConfiguration().destination().oSSBucketDestination().bucket()).isEqualTo("acs:oss:::bucket_0002");
        assertThat(copy.inventoryConfiguration().destination().oSSBucketDestination().prefix()).isEqualTo("prefix2");
        assertThat(copy.inventoryConfiguration().schedule().frequency()).isEqualTo("Weekly");
        assertThat(copy.inventoryConfiguration().filter().prefix()).isEqualTo("testprefix/");
        assertThat(copy.inventoryConfiguration().includedObjectVersions()).isEqualTo("Current");
        assertThat(copy.inventoryConfiguration().optionalFields().fields()).hasSize(2);
        assertThat(copy.inventoryConfiguration().optionalFields().fields().get(0)).isEqualTo(InventoryOptionalFieldType.SIZE);
        assertThat(copy.inventoryConfiguration().optionalFields().fields().get(1)).isEqualTo(InventoryOptionalFieldType.LAST_MODIFIED_DATE);
    }

    @Test
    public void testXmlBuilderWithInventoryConfiguration() throws JsonProcessingException {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeBucketInventory.toGetBucketInventory(blankOutput);

        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<InventoryConfiguration>\n" +
                "   <Id>report1</Id>\n" +
                "   <IsEnabled>true</IsEnabled>\n" +
                "   <Destination>\n" +
                "      <OSSBucketDestination>\n" +
                "         <Format>CSV</Format>\n" +
                "         <AccountId>1000000000000000</AccountId>\n" +
                "         <RoleArn>acs:ram::1000000000000000:role/AliyunOSSRole</RoleArn>\n" +
                "         <Bucket>acs:oss:::bucket_0001</Bucket>\n" +
                "         <Prefix>prefix1</Prefix>\n" +
                "         <Encryption>\n" +
                "            <SSE-KMS>\n" +
                "               <KeyId>keyId</KeyId>\n" +
                "            </SSE-KMS>\n" +
                "         </Encryption>\n" +
                "      </OSSBucketDestination>\n" +
                "   </Destination>\n" +
                "   <Schedule>\n" +
                "      <Frequency>Daily</Frequency>\n" +
                "   </Schedule>\n" +
                "   <Filter>\n" +
                "     <Prefix>myprefix/</Prefix>\n" +
                "   </Filter>\n" +
                "   <IncludedObjectVersions>All</IncludedObjectVersions>\n" +
                "   <OptionalFields>\n" +
                "      <Field>Size</Field>\n" +
                "      <Field>LastModifiedDate</Field>\n" +
                "      <Field>ETag</Field>\n" +
                "      <Field>StorageClass</Field>\n" +
                "      <Field>IsMultipartUploaded</Field>\n" +
                "      <Field>EncryptionStatus</Field>\n" +
                "   </OptionalFields>\n" +
                "</InventoryConfiguration>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetBucketInventoryResult result = SerdeBucketInventory.toGetBucketInventory(output);

        assertThat(result.inventoryConfiguration().id()).isEqualTo("report1");
        assertThat(result.inventoryConfiguration().isEnabled()).isEqualTo(true);
        assertThat(result.inventoryConfiguration().destination().oSSBucketDestination().format()).isEqualTo("CSV");
        assertThat(result.inventoryConfiguration().destination().oSSBucketDestination().accountId()).isEqualTo("1000000000000000");
        assertThat(result.inventoryConfiguration().destination().oSSBucketDestination().roleArn()).isEqualTo("acs:ram::1000000000000000:role/AliyunOSSRole");
        assertThat(result.inventoryConfiguration().destination().oSSBucketDestination().bucket()).isEqualTo("acs:oss:::bucket_0001");
        assertThat(result.inventoryConfiguration().destination().oSSBucketDestination().prefix()).isEqualTo("prefix1");
        assertThat(result.inventoryConfiguration().schedule().frequency()).isEqualTo("Daily");
        assertThat(result.inventoryConfiguration().filter().prefix()).isEqualTo("myprefix/");
        assertThat(result.inventoryConfiguration().includedObjectVersions()).isEqualTo("All");
        assertThat(result.inventoryConfiguration().optionalFields().fields()).hasSize(6);
        assertThat(result.inventoryConfiguration().optionalFields().fields().get(0)).isEqualTo(InventoryOptionalFieldType.SIZE);
        assertThat(result.inventoryConfiguration().optionalFields().fields().get(1)).isEqualTo(InventoryOptionalFieldType.LAST_MODIFIED_DATE);
        assertThat(result.inventoryConfiguration().optionalFields().fields().get(2)).isEqualTo(InventoryOptionalFieldType.E_TAG);
        assertThat(result.inventoryConfiguration().optionalFields().fields().get(3)).isEqualTo(InventoryOptionalFieldType.STORAGE_CLASS);
        assertThat(result.inventoryConfiguration().optionalFields().fields().get(4)).isEqualTo(InventoryOptionalFieldType.IS_MULTIPART_UPLOADED);
        assertThat(result.inventoryConfiguration().optionalFields().fields().get(5)).isEqualTo(InventoryOptionalFieldType.ENCRYPTION_STATUS);
    }
}