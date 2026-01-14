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

public class ListBucketInventoryResultTest {

    @Test
    public void testEmptyBuilder() {
        ListBucketInventoryResult result = ListBucketInventoryResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        // Build the first complete InventoryConfiguration object
        InventorySchedule schedule1 = InventorySchedule.newBuilder()
                .frequency("Daily")
                .build();

        InventoryFilter filter1 = InventoryFilter.newBuilder()
                .prefix("prefix/One")
                .build();

        SSEKMS ssekms1 = SSEKMS.newBuilder()
                .keyId("keyId1")
                .build();

        InventoryEncryption encryption1 = InventoryEncryption.newBuilder()
                .sseKms(ssekms1)
                .build();

        InventoryOSSBucketDestination bucketDestination1 = InventoryOSSBucketDestination.newBuilder()
                .format("CSV")
                .accountId("1000000000000000")
                .roleArn("acs:ram::1000000000000000:role/AliyunOSSRole")
                .bucket("acs:oss:::destination-bucket")
                .prefix("prefix1")
                .encryption(encryption1)
                .build();

        InventoryDestination destination1 = InventoryDestination.newBuilder()
                .oSSBucketDestination(bucketDestination1)
                .build();

        List<InventoryOptionalFieldType> optionalFields1 = Arrays.asList(
                InventoryOptionalFieldType.SIZE,
                InventoryOptionalFieldType.LAST_MODIFIED_DATE,
                InventoryOptionalFieldType.E_TAG,
                InventoryOptionalFieldType.STORAGE_CLASS,
                InventoryOptionalFieldType.IS_MULTIPART_UPLOADED,
                InventoryOptionalFieldType.ENCRYPTION_STATUS
        );

        OptionalFields optionalFieldsContainer1 = OptionalFields.newBuilder()
                .fields(optionalFields1)
                .build();

        InventoryConfiguration inventoryConfiguration1 = InventoryConfiguration.newBuilder()
                .id("report1")
                .isEnabled(true)
                .destination(destination1)
                .schedule(schedule1)
                .filter(filter1)
                .includedObjectVersions("All")
                .optionalFields(optionalFieldsContainer1)
                .build();

        // Build the second complete InventoryConfiguration object
        InventorySchedule schedule2 = InventorySchedule.newBuilder()
                .frequency("Daily")
                .build();

        InventoryFilter filter2 = InventoryFilter.newBuilder()
                .prefix("prefix/Two")
                .build();

        SSEKMS ssekms2 = SSEKMS.newBuilder()
                .keyId("keyId2")
                .build();

        InventoryEncryption encryption2 = InventoryEncryption.newBuilder()
                .sseKms(ssekms2)
                .build();

        InventoryOSSBucketDestination bucketDestination2 = InventoryOSSBucketDestination.newBuilder()
                .format("CSV")
                .accountId("1000000000000000")
                .roleArn("acs:ram::1000000000000000:role/AliyunOSSRole")
                .bucket("acs:oss:::destination-bucket")
                .prefix("prefix2")
                .encryption(encryption2)
                .build();

        InventoryDestination destination2 = InventoryDestination.newBuilder()
                .oSSBucketDestination(bucketDestination2)
                .build();

        List<InventoryOptionalFieldType> optionalFields2 = Arrays.asList(
                InventoryOptionalFieldType.SIZE,
                InventoryOptionalFieldType.LAST_MODIFIED_DATE,
                InventoryOptionalFieldType.E_TAG,
                InventoryOptionalFieldType.STORAGE_CLASS,
                InventoryOptionalFieldType.IS_MULTIPART_UPLOADED,
                InventoryOptionalFieldType.ENCRYPTION_STATUS
        );

        OptionalFields optionalFieldsContainer2 = OptionalFields.newBuilder()
                .fields(optionalFields2)
                .build();

        InventoryConfiguration inventoryConfiguration2 = InventoryConfiguration.newBuilder()
                .id("report2")
                .isEnabled(true)
                .destination(destination2)
                .schedule(schedule2)
                .filter(filter2)
                .includedObjectVersions("All")
                .optionalFields(optionalFieldsContainer2)
                .build();

        // Build the third complete InventoryConfiguration object
        InventorySchedule schedule3 = InventorySchedule.newBuilder()
                .frequency("Daily")
                .build();

        InventoryFilter filter3 = InventoryFilter.newBuilder()
                .prefix("prefix/Three")
                .build();

        SSEKMS ssekms3 = SSEKMS.newBuilder()
                .keyId("keyId3")
                .build();

        InventoryEncryption encryption3 = InventoryEncryption.newBuilder()
                .sseKms(ssekms3)
                .build();

        InventoryOSSBucketDestination bucketDestination3 = InventoryOSSBucketDestination.newBuilder()
                .format("CSV")
                .accountId("1000000000000000")
                .roleArn("acs:ram::1000000000000000:role/AliyunOSSRole")
                .bucket("acs:oss:::destination-bucket")
                .prefix("prefix3")
                .encryption(encryption3)
                .build();

        InventoryDestination destination3 = InventoryDestination.newBuilder()
                .oSSBucketDestination(bucketDestination3)
                .build();

        List<InventoryOptionalFieldType> optionalFields3 = Arrays.asList(
                InventoryOptionalFieldType.SIZE,
                InventoryOptionalFieldType.LAST_MODIFIED_DATE,
                InventoryOptionalFieldType.E_TAG,
                InventoryOptionalFieldType.STORAGE_CLASS,
                InventoryOptionalFieldType.IS_MULTIPART_UPLOADED,
                InventoryOptionalFieldType.ENCRYPTION_STATUS
        );

        OptionalFields optionalFieldsContainer3 = OptionalFields.newBuilder()
                .fields(optionalFields3)
                .build();

        InventoryConfiguration inventoryConfiguration3 = InventoryConfiguration.newBuilder()
                .id("report3")
                .isEnabled(true)
                .destination(destination3)
                .schedule(schedule3)
                .filter(filter3)
                .includedObjectVersions("All")
                .optionalFields(optionalFieldsContainer3)
                .build();

        List<InventoryConfiguration> inventoryConfigurations = Arrays.asList(
                inventoryConfiguration1,
                inventoryConfiguration2,
                inventoryConfiguration3
        );

        ListInventoryConfigurationsResult listResult = ListInventoryConfigurationsResult.newBuilder()
                .inventoryConfigurations(inventoryConfigurations)
                .isTruncated(true)
                .nextContinuationToken("...")
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ListBucketInventoryResult result = ListBucketInventoryResult.newBuilder()
                .headers(headers)
                .innerBody(listResult)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        assertThat(result.listInventoryConfigurationsResult().inventoryConfigurations()).hasSize(3);
        assertThat(result.listInventoryConfigurationsResult().isTruncated()).isEqualTo(true);
        assertThat(result.listInventoryConfigurationsResult().nextContinuationToken()).isEqualTo("...");

        // Check the first configuration
        InventoryConfiguration config1 = result.listInventoryConfigurationsResult().inventoryConfigurations().get(0);
        assertThat(config1.id()).isEqualTo("report1");
        assertThat(config1.isEnabled()).isEqualTo(true);
        assertThat(config1.destination().oSSBucketDestination().format()).isEqualTo("CSV");
        assertThat(config1.destination().oSSBucketDestination().accountId()).isEqualTo("1000000000000000");
        assertThat(config1.destination().oSSBucketDestination().roleArn()).isEqualTo("acs:ram::1000000000000000:role/AliyunOSSRole");
        assertThat(config1.destination().oSSBucketDestination().bucket()).isEqualTo("acs:oss:::destination-bucket");
        assertThat(config1.destination().oSSBucketDestination().prefix()).isEqualTo("prefix1");
        assertThat(config1.schedule().frequency()).isEqualTo("Daily");
        assertThat(config1.filter().prefix()).isEqualTo("prefix/One");
        assertThat(config1.includedObjectVersions()).isEqualTo("All");
        assertThat(config1.optionalFields().fields()).hasSize(6);

        // Check the second configuration
        InventoryConfiguration config2 = result.listInventoryConfigurationsResult().inventoryConfigurations().get(1);
        assertThat(config2.id()).isEqualTo("report2");
        assertThat(config2.isEnabled()).isEqualTo(true);
        assertThat(config2.destination().oSSBucketDestination().format()).isEqualTo("CSV");
        assertThat(config2.destination().oSSBucketDestination().accountId()).isEqualTo("1000000000000000");
        assertThat(config2.destination().oSSBucketDestination().roleArn()).isEqualTo("acs:ram::1000000000000000:role/AliyunOSSRole");
        assertThat(config2.destination().oSSBucketDestination().bucket()).isEqualTo("acs:oss:::destination-bucket");
        assertThat(config2.destination().oSSBucketDestination().prefix()).isEqualTo("prefix2");
        assertThat(config2.schedule().frequency()).isEqualTo("Daily");
        assertThat(config2.filter().prefix()).isEqualTo("prefix/Two");
        assertThat(config2.includedObjectVersions()).isEqualTo("All");
        assertThat(config2.optionalFields().fields()).hasSize(6);

        // Check the third configuration
        InventoryConfiguration config3 = result.listInventoryConfigurationsResult().inventoryConfigurations().get(2);
        assertThat(config3.id()).isEqualTo("report3");
        assertThat(config3.isEnabled()).isEqualTo(true);
        assertThat(config3.destination().oSSBucketDestination().format()).isEqualTo("CSV");
        assertThat(config3.destination().oSSBucketDestination().accountId()).isEqualTo("1000000000000000");
        assertThat(config3.destination().oSSBucketDestination().roleArn()).isEqualTo("acs:ram::1000000000000000:role/AliyunOSSRole");
        assertThat(config3.destination().oSSBucketDestination().bucket()).isEqualTo("acs:oss:::destination-bucket");
        assertThat(config3.destination().oSSBucketDestination().prefix()).isEqualTo("prefix3");
        assertThat(config3.schedule().frequency()).isEqualTo("Daily");
        assertThat(config3.filter().prefix()).isEqualTo("prefix/Three");
        assertThat(config3.includedObjectVersions()).isEqualTo("All");
        assertThat(config3.optionalFields().fields()).hasSize(6);
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

        SSEKMS ssekms = SSEKMS.newBuilder()
                .keyId("test-key")
                .build();

        InventoryEncryption encryption = InventoryEncryption.newBuilder()
                .sseKms(ssekms)
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

        List<InventoryConfiguration> inventoryConfigurations = Arrays.asList(inventoryConfiguration);

        ListInventoryConfigurationsResult listResult = ListInventoryConfigurationsResult.newBuilder()
                .inventoryConfigurations(inventoryConfigurations)
                .isTruncated(false)
                .nextContinuationToken("next-token")
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ListBucketInventoryResult original = ListBucketInventoryResult.newBuilder()
                .headers(headers)
                .innerBody(listResult)
                .build();

        ListBucketInventoryResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        assertThat(copy.listInventoryConfigurationsResult().inventoryConfigurations()).hasSize(1);
        assertThat(copy.listInventoryConfigurationsResult().isTruncated()).isEqualTo(false);
        assertThat(copy.listInventoryConfigurationsResult().nextContinuationToken()).isEqualTo("next-token");

        InventoryConfiguration config = copy.listInventoryConfigurationsResult().inventoryConfigurations().get(0);
        assertThat(config.id()).isEqualTo("report2");
        assertThat(config.isEnabled()).isEqualTo(false);
        assertThat(config.destination().oSSBucketDestination().format()).isEqualTo("CSV");
        assertThat(config.destination().oSSBucketDestination().accountId()).isEqualTo("2000000000000000");
        assertThat(config.destination().oSSBucketDestination().roleArn()).isEqualTo("acs:ram::2000000000000000:role/AliyunOSSRole");
        assertThat(config.destination().oSSBucketDestination().bucket()).isEqualTo("acs:oss:::bucket_0002");
        assertThat(config.destination().oSSBucketDestination().prefix()).isEqualTo("prefix2");
        assertThat(config.schedule().frequency()).isEqualTo("Weekly");
        assertThat(config.filter().prefix()).isEqualTo("testprefix/");
        assertThat(config.includedObjectVersions()).isEqualTo("Current");
        assertThat(config.optionalFields().fields()).hasSize(2);
        assertThat(config.optionalFields().fields().get(0)).isEqualTo(InventoryOptionalFieldType.SIZE);
        assertThat(config.optionalFields().fields().get(1)).isEqualTo(InventoryOptionalFieldType.LAST_MODIFIED_DATE);
    }

    @Test
    public void testXmlBuilderWithListInventoryConfigurationsResult() throws JsonProcessingException {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeBucketInventory.toListBucketInventory(blankOutput);

        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ListInventoryConfigurationsResult>\n" +
                "     <InventoryConfiguration>\n" +
                "        <Id>report1</Id>\n" +
                "        <IsEnabled>true</IsEnabled>\n" +
                "        <Destination>\n" +
                "           <OSSBucketDestination>\n" +
                "              <Format>CSV</Format>\n" +
                "              <AccountId>1000000000000000</AccountId>\n" +
                "              <RoleArn>acs:ram::1000000000000000:role/AliyunOSSRole</RoleArn>\n" +
                "              <Bucket>acs:oss:::destination-bucket</Bucket>\n" +
                "              <Prefix>prefix1</Prefix>\n" +
                "              <Encryption>\n" +
                "                 <SSE-KMS>\n" +
                "                    <KeyId>keyId</KeyId>\n" +
                "                 </SSE-KMS>\n" +
                "              </Encryption>\n" +
                "           </OSSBucketDestination>\n" +
                "        </Destination>\n" +
                "        <Schedule>\n" +
                "           <Frequency>Daily</Frequency>\n" +
                "        </Schedule>\n" +
                "        <Filter>\n" +
                "           <Prefix>prefix/One</Prefix>\n" +
                "        </Filter>\n" +
                "        <IncludedObjectVersions>All</IncludedObjectVersions>\n" +
                "        <OptionalFields>\n" +
                "           <Field>Size</Field>\n" +
                "           <Field>LastModifiedDate</Field>\n" +
                "           <Field>ETag</Field>\n" +
                "           <Field>StorageClass</Field>\n" +
                "           <Field>IsMultipartUploaded</Field>\n" +
                "           <Field>EncryptionStatus</Field>\n" +
                "        </OptionalFields>\n" +
                "     </InventoryConfiguration>\n" +
                "     <InventoryConfiguration>\n" +
                "        <Id>report2</Id>\n" +
                "        <IsEnabled>true</IsEnabled>\n" +
                "        <Destination>\n" +
                "           <OSSBucketDestination>\n" +
                "              <Format>CSV</Format>\n" +
                "              <AccountId>1000000000000000</AccountId>\n" +
                "              <RoleArn>acs:ram::1000000000000000:role/AliyunOSSRole</RoleArn>\n" +
                "              <Bucket>acs:oss:::destination-bucket</Bucket>\n" +
                "              <Prefix>prefix2</Prefix>\n" +
                "              <Encryption>\n" +
                "                 <SSE-OSS>SSE-OSS</SSE-OSS>\n" +
                "              </Encryption>\n" +
                "           </OSSBucketDestination>\n" +
                "        </Destination>\n" +
                "        <Schedule>\n" +
                "           <Frequency>Daily</Frequency>\n" +
                "        </Schedule>\n" +
                "        <Filter>\n" +
                "           <Prefix>prefix/Two</Prefix>\n" +
                "        </Filter>\n" +
                "        <IncludedObjectVersions>All</IncludedObjectVersions>\n" +
                "        <OptionalFields>\n" +
                "           <Field>Size</Field>\n" +
                "           <Field>LastModifiedDate</Field>\n" +
                "           <Field>ETag</Field>\n" +
                "           <Field>StorageClass</Field>\n" +
                "           <Field>IsMultipartUploaded</Field>\n" +
                "           <Field>EncryptionStatus</Field>\n" +
                "        </OptionalFields>\n" +
                "     </InventoryConfiguration>\n" +
                "     <InventoryConfiguration>\n" +
                "        <Id>report3</Id>\n" +
                "        <IsEnabled>true</IsEnabled>\n" +
                "        <Destination>\n" +
                "           <OSSBucketDestination>\n" +
                "              <Format>CSV</Format>\n" +
                "              <AccountId>1000000000000000</AccountId>\n" +
                "              <RoleArn>acs:ram::1000000000000000:role/AliyunOSSRole</RoleArn>\n" +
                "              <Bucket>acs:oss:::destination-bucket</Bucket>\n" +
                "              <Prefix>prefix3</Prefix>\n" +
                "           </OSSBucketDestination>\n" +
                "        </Destination>\n" +
                "        <Schedule>\n" +
                "           <Frequency>Daily</Frequency>\n" +
                "        </Schedule>\n" +
                "        <Filter>\n" +
                "           <Prefix>prefix/Three</Prefix>\n" +
                "        </Filter>\n" +
                "        <IncludedObjectVersions>All</IncludedObjectVersions>\n" +
                "        <OptionalFields>\n" +
                "           <Field>Size</Field>\n" +
                "           <Field>LastModifiedDate</Field>\n" +
                "           <Field>ETag</Field>\n" +
                "           <Field>StorageClass</Field>\n" +
                "           <Field>IsMultipartUploaded</Field>\n" +
                "           <Field>EncryptionStatus</Field>\n" +
                "        </OptionalFields>\n" +
                "     </InventoryConfiguration>\n" +
                "     <IsTruncated>true</IsTruncated>\n" +
                "     <NextContinuationToken>...</NextContinuationToken>\n" +
                "  </ListInventoryConfigurationsResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        ListBucketInventoryResult result = SerdeBucketInventory.toListBucketInventory(output);

        assertThat(result.listInventoryConfigurationsResult().inventoryConfigurations()).hasSize(3);
        assertThat(result.listInventoryConfigurationsResult().isTruncated()).isEqualTo(true);
        assertThat(result.listInventoryConfigurationsResult().nextContinuationToken()).isEqualTo("...");

        // Check the first configuration
        InventoryConfiguration config1 = result.listInventoryConfigurationsResult().inventoryConfigurations().get(0);
        assertThat(config1.id()).isEqualTo("report1");
        assertThat(config1.isEnabled()).isEqualTo(true);
        assertThat(config1.destination().oSSBucketDestination().format()).isEqualTo("CSV");
        assertThat(config1.destination().oSSBucketDestination().accountId()).isEqualTo("1000000000000000");
        assertThat(config1.destination().oSSBucketDestination().roleArn()).isEqualTo("acs:ram::1000000000000000:role/AliyunOSSRole");
        assertThat(config1.destination().oSSBucketDestination().bucket()).isEqualTo("acs:oss:::destination-bucket");
        assertThat(config1.destination().oSSBucketDestination().prefix()).isEqualTo("prefix1");
        assertThat(config1.destination().oSSBucketDestination().encryption().sseKms().keyId()).isEqualTo("keyId");
        assertThat(config1.schedule().frequency()).isEqualTo("Daily");
        assertThat(config1.filter().prefix()).isEqualTo("prefix/One");
        assertThat(config1.includedObjectVersions()).isEqualTo("All");
        assertThat(config1.optionalFields().fields()).hasSize(6);
        assertThat(config1.optionalFields().fields().get(0)).isEqualTo(InventoryOptionalFieldType.SIZE);
        assertThat(config1.optionalFields().fields().get(1)).isEqualTo(InventoryOptionalFieldType.LAST_MODIFIED_DATE);
        assertThat(config1.optionalFields().fields().get(2)).isEqualTo(InventoryOptionalFieldType.E_TAG);
        assertThat(config1.optionalFields().fields().get(3)).isEqualTo(InventoryOptionalFieldType.STORAGE_CLASS);
        assertThat(config1.optionalFields().fields().get(4)).isEqualTo(InventoryOptionalFieldType.IS_MULTIPART_UPLOADED);
        assertThat(config1.optionalFields().fields().get(5)).isEqualTo(InventoryOptionalFieldType.ENCRYPTION_STATUS);

        // Check the second configuration
        InventoryConfiguration config2 = result.listInventoryConfigurationsResult().inventoryConfigurations().get(1);
        assertThat(config2.id()).isEqualTo("report2");
        assertThat(config2.isEnabled()).isEqualTo(true);
        assertThat(config2.destination().oSSBucketDestination().format()).isEqualTo("CSV");
        assertThat(config2.destination().oSSBucketDestination().accountId()).isEqualTo("1000000000000000");
        assertThat(config2.destination().oSSBucketDestination().roleArn()).isEqualTo("acs:ram::1000000000000000:role/AliyunOSSRole");
        assertThat(config2.destination().oSSBucketDestination().bucket()).isEqualTo("acs:oss:::destination-bucket");
        assertThat(config2.destination().oSSBucketDestination().prefix()).isEqualTo("prefix2");
        assertThat(config2.destination().oSSBucketDestination().encryption().sseOss()).isEqualTo("SSE-OSS");
        assertThat(config2.schedule().frequency()).isEqualTo("Daily");
        assertThat(config2.filter().prefix()).isEqualTo("prefix/Two");
        assertThat(config2.includedObjectVersions()).isEqualTo("All");
        assertThat(config2.optionalFields().fields()).hasSize(6);

        // Check the third configuration
        InventoryConfiguration config3 = result.listInventoryConfigurationsResult().inventoryConfigurations().get(2);
        assertThat(config3.id()).isEqualTo("report3");
        assertThat(config3.isEnabled()).isEqualTo(true);
        assertThat(config3.destination().oSSBucketDestination().format()).isEqualTo("CSV");
        assertThat(config3.destination().oSSBucketDestination().accountId()).isEqualTo("1000000000000000");
        assertThat(config3.destination().oSSBucketDestination().roleArn()).isEqualTo("acs:ram::1000000000000000:role/AliyunOSSRole");
        assertThat(config3.destination().oSSBucketDestination().bucket()).isEqualTo("acs:oss:::destination-bucket");
        assertThat(config3.destination().oSSBucketDestination().prefix()).isEqualTo("prefix3");
        // Third configuration has no encryption configured
        assertThat(config3.destination().oSSBucketDestination().encryption()).isNull();
        assertThat(config3.schedule().frequency()).isEqualTo("Daily");
        assertThat(config3.filter().prefix()).isEqualTo("prefix/Three");
        assertThat(config3.includedObjectVersions()).isEqualTo("All");
        assertThat(config3.optionalFields().fields()).hasSize(6);
    }
}