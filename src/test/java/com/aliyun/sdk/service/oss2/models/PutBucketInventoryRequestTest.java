package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketInventory;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketInventoryRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketInventoryRequest request = PutBucketInventoryRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        // Create nested objects for the inventory configuration
        SSEKMS ssekms = SSEKMS.newBuilder()
                .keyId("keyId")
                .build();

        InventoryEncryption encryption = InventoryEncryption.newBuilder()
                .sseKms(ssekms)
                .build();

        InventoryOSSBucketDestination destination = InventoryOSSBucketDestination.newBuilder()
                .format("CSV")
                .accountId("100000000000000")
                .roleArn("acs:ram::100000000000000:role/AliyunOSSRole")
                .bucket("acs:oss:::destbucket")
                .prefix("prefix1/")
                .encryption(encryption)
                .build();

        InventoryDestination inventoryDestination = InventoryDestination.newBuilder()
                .oSSBucketDestination(destination)
                .build();

        InventorySchedule schedule = InventorySchedule.newBuilder()
                .frequency("Daily")
                .build();

        InventoryFilter filter = InventoryFilter.newBuilder()
                .prefix("Pics/")
                .lastModifyBeginTimeStamp(1637883649L)
                .lastModifyEndTimeStamp(1638347592L)
                .lowerSizeBound(1024L)
                .upperSizeBound(1048576L)
                .storageClass("Standard,IA")
                .build();

        List<String> fields = Arrays.asList(
                InventoryOptionalFieldType.SIZE.toString(),
                InventoryOptionalFieldType.LAST_MODIFIED_DATE.toString(),
                InventoryOptionalFieldType.E_TAG.toString(),
                InventoryOptionalFieldType.STORAGE_CLASS.toString(),
                InventoryOptionalFieldType.IS_MULTIPART_UPLOADED.toString(),
                InventoryOptionalFieldType.ENCRYPTION_STATUS.toString()
        );
        OptionalFields optionalFields = OptionalFields.newBuilder()
                .fields(fields)
                .build();

        // Create incremental inventory configuration
        IncrementInventorySchedule incrementSchedule = IncrementInventorySchedule.newBuilder()
                .frequency(600L) // 10 minutes in seconds
                .build();

        List<String> incrementFields = Arrays.asList(
                IncrementalInventoryOptionalFieldType.SEQUENCE_NUMBER.toString(),
                IncrementalInventoryOptionalFieldType.RECORD_TYPE.toString(),
                IncrementalInventoryOptionalFieldType.RECORD_TIMESTAMP.toString(),
                IncrementalInventoryOptionalFieldType.REQUESTER.toString(),
                IncrementalInventoryOptionalFieldType.REQUEST_ID.toString(),
                IncrementalInventoryOptionalFieldType.SOURCE_IP.toString(),
                IncrementalInventoryOptionalFieldType.SIZE.toString(),
                IncrementalInventoryOptionalFieldType.STORAGE_CLASS.toString(),
                IncrementalInventoryOptionalFieldType.LAST_MODIFIED_DATE.toString(),
                IncrementalInventoryOptionalFieldType.E_TAG.toString(),
                IncrementalInventoryOptionalFieldType.IS_MULTIPART_UPLOADED.toString(),
                IncrementalInventoryOptionalFieldType.OBJECT_TYPE.toString(),
                IncrementalInventoryOptionalFieldType.OBJECT_ACL.toString(),
                IncrementalInventoryOptionalFieldType.CRC64.toString(),
                IncrementalInventoryOptionalFieldType.ENCRYPTION_STATUS.toString()
        );
        OptionalFields incrementalOptionalFields = OptionalFields.newBuilder()
                .fields(incrementFields)
                .build();

        IncrementalInventory incrementalInventory = IncrementalInventory.newBuilder()
                .isEnabled(true)
                .schedule(incrementSchedule)
                .optionalFields(incrementalOptionalFields)
                .build();

        InventoryConfiguration inventoryConfiguration = InventoryConfiguration.newBuilder()
                .id("report1")
                .isEnabled(true)
                .destination(inventoryDestination)
                .schedule(schedule)
                .filter(filter)
                .includedObjectVersions("All")
                .optionalFields(optionalFields)
                .incrementalInventory(incrementalInventory)
                .build();

        PutBucketInventoryRequest request = PutBucketInventoryRequest.newBuilder()
                .bucket("examplebucket")
                .inventoryId("report1")
                .inventoryConfiguration(inventoryConfiguration)
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.inventoryId()).isEqualTo("report1");
        assertThat(request.inventoryConfiguration()).isEqualTo(inventoryConfiguration);
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        PutBucketInventoryRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.inventoryId()).isEqualTo("report1");
        assertThat(copy.inventoryConfiguration()).isEqualTo(inventoryConfiguration);
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        SSEKMS ssekms = SSEKMS.newBuilder()
                .keyId("keyId")
                .build();

        InventoryEncryption encryption = InventoryEncryption.newBuilder()
                .sseKms(ssekms)
                .build();

        InventoryOSSBucketDestination destination = InventoryOSSBucketDestination.newBuilder()
                .format("CSV")
                .accountId("100000000000000")
                .roleArn("acs:ram::100000000000000:role/AliyunOSSRole")
                .bucket("acs:oss:::destbucket")
                .prefix("prefix1/")
                .encryption(encryption)
                .build();

        InventoryDestination inventoryDestination = InventoryDestination.newBuilder()
                .oSSBucketDestination(destination)
                .build();

        // Create incremental inventory configuration for the test
        IncrementInventorySchedule incrementSchedule = IncrementInventorySchedule.newBuilder()
                .frequency(600L)
                .build();

        List<String> incrementFields = Arrays.asList(
                IncrementalInventoryOptionalFieldType.SEQUENCE_NUMBER.toString(),
                IncrementalInventoryOptionalFieldType.RECORD_TYPE.toString()
        );
        OptionalFields incrementalOptionalFields = OptionalFields.newBuilder()
                .fields(incrementFields)
                .build();

        IncrementalInventory incrementalInventory = IncrementalInventory.newBuilder()
                .isEnabled(true)
                .schedule(incrementSchedule)
                .optionalFields(incrementalOptionalFields)
                .build();

        InventoryConfiguration inventoryConfiguration = InventoryConfiguration.newBuilder()
                .id("test-report")
                .isEnabled(false)
                .includedObjectVersions("Current")
                .destination(inventoryDestination)
                .incrementalInventory(incrementalInventory)
                .build();

        PutBucketInventoryRequest original = PutBucketInventoryRequest.newBuilder()
                .bucket("test-bucket")
                .inventoryId("test-report")
                .inventoryConfiguration(inventoryConfiguration)
                .build();

        PutBucketInventoryRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.inventoryId()).isEqualTo("test-report");
        assertThat(copy.inventoryConfiguration()).isEqualTo(inventoryConfiguration);
    }

    @Test
    public void testHeaderProperties() {
        SSEKMS ssekms = SSEKMS.newBuilder()
                .keyId("test-key")
                .build();

        InventoryEncryption encryption = InventoryEncryption.newBuilder()
                .sseKms(ssekms)
                .build();

        InventoryOSSBucketDestination destination = InventoryOSSBucketDestination.newBuilder()
                .format("CSV")
                .accountId("100000000000000")
                .roleArn("acs:ram::100000000000000:role/AliyunOSSRole")
                .bucket("acs:oss:::destbucket")
                .prefix("prefix1/")
                .encryption(encryption)
                .build();

        InventoryDestination inventoryDestination = InventoryDestination.newBuilder()
                .oSSBucketDestination(destination)
                .build();

        // Create incremental inventory configuration for the test
        IncrementInventorySchedule incrementSchedule = IncrementInventorySchedule.newBuilder()
                .frequency(600L)
                .build();

        List<String> incrementFields = Arrays.asList(
                IncrementalInventoryOptionalFieldType.SEQUENCE_NUMBER.toString()
        );
        OptionalFields incrementalOptionalFields = OptionalFields.newBuilder()
                .fields(incrementFields)
                .build();

        IncrementalInventory incrementalInventory = IncrementalInventory.newBuilder()
                .isEnabled(true)
                .schedule(incrementSchedule)
                .optionalFields(incrementalOptionalFields)
                .build();

        InventoryConfiguration inventoryConfiguration = InventoryConfiguration.newBuilder()
                .id("report-test")
                .isEnabled(true)
                .includedObjectVersions("All")
                .destination(inventoryDestination)
                .incrementalInventory(incrementalInventory)
                .build();

        PutBucketInventoryRequest request = PutBucketInventoryRequest.newBuilder()
                .bucket("inventory-bucket")
                .inventoryId("report-test")
                .inventoryConfiguration(inventoryConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("inventory-bucket");
        assertThat(request.inventoryId()).isEqualTo("report-test");
        assertThat(request.inventoryConfiguration().id()).isEqualTo("report-test");
        assertThat(request.inventoryConfiguration().isEnabled()).isEqualTo(true);
        assertThat(request.inventoryConfiguration().incrementalInventory()).isNotNull();
        assertThat(request.inventoryConfiguration().incrementalInventory().isEnabled()).isEqualTo(true);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<InventoryConfiguration>\n" +
                "  <Id>report1</Id>\n" +
                "  <IsEnabled>true</IsEnabled>\n" +
                "  <Filter>\n" +
                "    <Prefix>Pics/</Prefix>\n" +
                "    <LastModifyBeginTimeStamp>1637883649</LastModifyBeginTimeStamp>\n" +
                "    <LastModifyEndTimeStamp>1638347592</LastModifyEndTimeStamp>\n" +
                "    <LowerSizeBound>1024</LowerSizeBound>\n" +
                "    <UpperSizeBound>1048576</UpperSizeBound>\n" +
                "    <StorageClass>Standard,IA</StorageClass>\n" +
                "  </Filter>\n" +
                "  <Destination>\n" +
                "    <OSSBucketDestination>\n" +
                "      <Format>CSV</Format>\n" +
                "      <AccountId>100000000000000</AccountId>\n" +
                "      <RoleArn>acs:ram::100000000000000:role/AliyunOSSRole</RoleArn>\n" +
                "      <Bucket>acs:oss:::destbucket</Bucket>\n" +
                "      <Prefix>prefix1/</Prefix>\n" +
                "      <Encryption>\n" +
                "        <SSE-KMS>\n" +
                "          <KeyId>keyId</KeyId>\n" +
                "        </SSE-KMS>\n" +
                "      </Encryption>\n" +
                "    </OSSBucketDestination>\n" +
                "  </Destination>\n" +
                "  <Schedule>\n" +
                "    <Frequency>Daily</Frequency>\n" +
                "  </Schedule>\n" +
                "  <IncludedObjectVersions>All</IncludedObjectVersions>\n" +
                "  <OptionalFields>\n" +
                "    <Field>Size</Field>\n" +
                "    <Field>LastModifiedDate</Field>\n" +
                "    <Field>TransitionTime</Field>\n" +
                "    <Field>ETag</Field>\n" +
                "    <Field>StorageClass</Field>\n" +
                "    <Field>IsMultipartUploaded</Field>\n" +
                "    <Field>EncryptionStatus</Field>\n" +
                "    <Field>ObjectAcl</Field>\n" +
                "    <Field>TaggingCount</Field>\n" +
                "    <Field>ObjectType</Field>\n" +
                "    <Field>Crc64</Field>\n" +
                "  </OptionalFields>\n" +
                "  <IncrementalInventory>\n" +
                "    <IsEnabled>true</IsEnabled>\n" +
                "    <Schedule>\n" +
                "      <Frequency>600</Frequency>\n" +
                "    </Schedule>\n" +
                "    <OptionalFields>\n" +
                "      <Field>SequenceNumber</Field>\n" +
                "      <Field>RecordType</Field>\n" +
                "      <Field>RecordTimestamp</Field>\n" +
                "      <Field>Requester</Field>\n" +
                "      <Field>RequestId</Field>\n" +
                "      <Field>SourceIp</Field>\n" +
                "      <Field>Size</Field>\n" +
                "      <Field>StorageClass</Field>\n" +
                "      <Field>LastModifiedDate</Field>\n" +
                "      <Field>ETag</Field>\n" +
                "      <Field>IsMultipartUploaded</Field>\n" +
                "      <Field>ObjectType</Field>\n" +
                "      <Field>ObjectAcl</Field>\n" +
                "      <Field>Crc64</Field>\n" +
                "      <Field>EncryptionStatus</Field>\n" +
                "    </OptionalFields>\n" +
                "  </IncrementalInventory>\n" +
                "</InventoryConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        InventoryConfiguration xmlConfiguration = xmlMapper.readValue(xml, InventoryConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        SSEKMS ssekms = SSEKMS.newBuilder()
                .keyId("keyId")
                .build();

        InventoryEncryption encryption = InventoryEncryption.newBuilder()
                .sseKms(ssekms)
                .build();

        InventoryOSSBucketDestination destination = InventoryOSSBucketDestination.newBuilder()
                .format("CSV")
                .accountId("100000000000000")
                .roleArn("acs:ram::100000000000000:role/AliyunOSSRole")
                .bucket("acs:oss:::destbucket")
                .prefix("prefix1/")
                .encryption(encryption)
                .build();

        InventoryDestination inventoryDestination = InventoryDestination.newBuilder()
                .oSSBucketDestination(destination)
                .build();

        InventorySchedule schedule = InventorySchedule.newBuilder()
                .frequency("Daily")
                .build();

        InventoryFilter filter = InventoryFilter.newBuilder()
                .prefix("Pics/")
                .lastModifyBeginTimeStamp(1637883649L)
                .lastModifyEndTimeStamp(1638347592L)
                .lowerSizeBound(1024L)
                .upperSizeBound(1048576L)
                .storageClass("Standard,IA")
                .build();

        List<String> fields = Arrays.asList(
                InventoryOptionalFieldType.SIZE.toString(),
                InventoryOptionalFieldType.LAST_MODIFIED_DATE.toString(),
                InventoryOptionalFieldType.TRANSITION_TIME.toString(),
                InventoryOptionalFieldType.E_TAG.toString(),
                InventoryOptionalFieldType.STORAGE_CLASS.toString(),
                InventoryOptionalFieldType.IS_MULTIPART_UPLOADED.toString(),
                InventoryOptionalFieldType.ENCRYPTION_STATUS.toString(),
                InventoryOptionalFieldType.OBJECT_ACL.toString(),
                InventoryOptionalFieldType.TAGGING_COUNT.toString(),
                InventoryOptionalFieldType.OBJECT_TYPE.toString(),
                InventoryOptionalFieldType.CRC64.toString()
        );
        OptionalFields optionalFields = OptionalFields.newBuilder()
                .fields(fields)
                .build();

        // Create incremental inventory configuration
        IncrementInventorySchedule incrementSchedule = IncrementInventorySchedule.newBuilder()
                .frequency(600L)
                .build();

        List<String> incrementFields = Arrays.asList(
                IncrementalInventoryOptionalFieldType.SEQUENCE_NUMBER.toString(),
                IncrementalInventoryOptionalFieldType.RECORD_TYPE.toString(),
                IncrementalInventoryOptionalFieldType.RECORD_TIMESTAMP.toString(),
                IncrementalInventoryOptionalFieldType.REQUESTER.toString(),
                IncrementalInventoryOptionalFieldType.REQUEST_ID.toString(),
                IncrementalInventoryOptionalFieldType.SOURCE_IP.toString(),
                IncrementalInventoryOptionalFieldType.SIZE.toString(),
                IncrementalInventoryOptionalFieldType.STORAGE_CLASS.toString(),
                IncrementalInventoryOptionalFieldType.LAST_MODIFIED_DATE.toString(),
                IncrementalInventoryOptionalFieldType.E_TAG.toString(),
                IncrementalInventoryOptionalFieldType.IS_MULTIPART_UPLOADED.toString(),
                IncrementalInventoryOptionalFieldType.OBJECT_TYPE.toString(),
                IncrementalInventoryOptionalFieldType.OBJECT_ACL.toString(),
                IncrementalInventoryOptionalFieldType.CRC64.toString(),
                IncrementalInventoryOptionalFieldType.ENCRYPTION_STATUS.toString()
        );
        OptionalFields incrementalOptionalFields = OptionalFields.newBuilder()
                .fields(incrementFields)
                .build();

        IncrementalInventory incrementalInventory = IncrementalInventory.newBuilder()
                .isEnabled(true)
                .schedule(incrementSchedule)
                .optionalFields(incrementalOptionalFields)
                .build();

        InventoryConfiguration inventoryConfiguration = InventoryConfiguration.newBuilder()
                .id("report1")
                .isEnabled(true)
                .destination(inventoryDestination)
                .schedule(schedule)
                .filter(filter)
                .includedObjectVersions("All")
                .optionalFields(optionalFields)
                .incrementalInventory(incrementalInventory)
                .build();

        PutBucketInventoryRequest request = PutBucketInventoryRequest.newBuilder()
                .bucket("xml-bucket")
                .inventoryId("report1")
                .inventoryConfiguration(inventoryConfiguration)
                .build();

        OperationInput input = SerdeBucketInventory.fromPutBucketInventory(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("inventoryId")).isEqualTo("report1");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<InventoryConfiguration>");
        assertThat(xmlContent).contains("<Id>report1</Id>");
        assertThat(xmlContent).contains("<IsEnabled>true</IsEnabled>");
        assertThat(xmlContent).contains("<Schedule>");
        assertThat(xmlContent).contains("<Frequency>Daily</Frequency>");
        assertThat(xmlContent).contains("<IncludedObjectVersions>All</IncludedObjectVersions>");
        assertThat(xmlContent).contains("<IncrementalInventory>");
        assertThat(xmlContent).contains("<IsEnabled>true</IsEnabled>");
        assertThat(xmlContent).contains("<Frequency>600</Frequency>");
        assertThat(xmlContent).contains("</InventoryConfiguration>");

        // Compare with expected XML (ignoring differences in formatting)
        assertThat(xmlContent).isEqualTo(expectedXml);
    }

    @Test
    public void testMonthlyScheduleWithDayOfMonth() throws JsonProcessingException {
        String xml = "" +
                "<InventoryConfiguration>\n" +
                "  <Id>monthly-report</Id>\n" +
                "  <IsEnabled>true</IsEnabled>\n" +
                "  <Filter>\n" +
                "    <Prefix>logs/</Prefix>\n" +
                "  </Filter>\n" +
                "  <Destination>\n" +
                "    <OSSBucketDestination>\n" +
                "      <Format>CSV</Format>\n" +
                "      <AccountId>100000000000000</AccountId>\n" +
                "      <RoleArn>acs:ram::100000000000000:role/AliyunOSSRole</RoleArn>\n" +
                "      <Bucket>acs:oss:::destbucket</Bucket>\n" +
                "      <Prefix>monthly-inventory/</Prefix>\n" +
                "    </OSSBucketDestination>\n" +
                "  </Destination>\n" +
                "  <Schedule>\n" +
                "    <Frequency>Monthly</Frequency>\n" +
                "    <DayOfMonth>3</DayOfMonth>\n" +
                "  </Schedule>\n" +
                "  <IncludedObjectVersions>All</IncludedObjectVersions>\n" +
                "  <OptionalFields>\n" +
                "    <Field>Size</Field>\n" +
                "    <Field>LastModifiedDate</Field>\n" +
                "    <Field>ETag</Field>\n" +
                "    <Field>StorageClass</Field>\n" +
                "    <Field>IsMultipartUploaded</Field>\n" +
                "    <Field>EncryptionStatus</Field>\n" +
                "  </OptionalFields>\n" +
                "</InventoryConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        InventoryConfiguration xmlConfiguration = xmlMapper.readValue(xml, InventoryConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        InventoryOSSBucketDestination destination = InventoryOSSBucketDestination.newBuilder()
                .format("CSV")
                .accountId("100000000000000")
                .roleArn("acs:ram::100000000000000:role/AliyunOSSRole")
                .bucket("acs:oss:::destbucket")
                .prefix("monthly-inventory/")
                .build();

        InventoryDestination inventoryDestination = InventoryDestination.newBuilder()
                .ossBucketDestination(destination)
                .build();

        InventorySchedule schedule = InventorySchedule.newBuilder()
                .frequency(InventoryFrequencyType.MONTHLY.toString())
                .dayOfMonth(3)
                .build();

        InventoryFilter filter = InventoryFilter.newBuilder()
                .prefix("logs/")
                .build();

        List<String> fields = Arrays.asList(
                InventoryOptionalFieldType.SIZE.toString(),
                InventoryOptionalFieldType.LAST_MODIFIED_DATE.toString(),
                InventoryOptionalFieldType.E_TAG.toString(),
                InventoryOptionalFieldType.STORAGE_CLASS.toString(),
                InventoryOptionalFieldType.IS_MULTIPART_UPLOADED.toString(),
                InventoryOptionalFieldType.ENCRYPTION_STATUS.toString()
        );
        OptionalFields optionalFields = OptionalFields.newBuilder()
                .fields(fields)
                .build();

        InventoryConfiguration inventoryConfiguration = InventoryConfiguration.newBuilder()
                .id("monthly-report")
                .isEnabled(true)
                .destination(inventoryDestination)
                .schedule(schedule)
                .filter(filter)
                .includedObjectVersions("All")
                .optionalFields(optionalFields)
                .build();

        PutBucketInventoryRequest request = PutBucketInventoryRequest.newBuilder()
                .bucket("xml-bucket")
                .inventoryId("monthly-report")
                .inventoryConfiguration(inventoryConfiguration)
                .build();

        OperationInput input = SerdeBucketInventory.fromPutBucketInventory(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("inventoryId")).isEqualTo("monthly-report");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<Frequency>Monthly</Frequency>");
        assertThat(xmlContent).contains("<DayOfMonth>3</DayOfMonth>");

        // Compare with expected XML
        assertThat(xmlContent).isEqualTo(expectedXml);
    }

    @Test
    public void testOnceScheduleWithAutoDelete() throws JsonProcessingException {
        String xml = "" +
                "<InventoryConfiguration>\n" +
                "  <Id>once-report</Id>\n" +
                "  <IsEnabled>true</IsEnabled>\n" +
                "  <Filter>\n" +
                "    <Prefix>log/</Prefix>\n" +
                "  </Filter>\n" +
                "  <Destination>\n" +
                "    <OSSBucketDestination>\n" +
                "      <Format>CSV</Format>\n" +
                "      <AccountId>100000000000000</AccountId>\n" +
                "      <RoleArn>acs:ram::100000000000000:role/AliyunOSSRole</RoleArn>\n" +
                "      <Bucket>acs:oss:::destbucket</Bucket>\n" +
                "      <Prefix>once-inventory/</Prefix>\n" +
                "    </OSSBucketDestination>\n" +
                "  </Destination>\n" +
                "  <Schedule>\n" +
                "    <Frequency>Once</Frequency>\n" +
                "    <AutoDelete>true</AutoDelete>\n" +
                "  </Schedule>\n" +
                "  <IncludedObjectVersions>All</IncludedObjectVersions>\n" +
                "  <OptionalFields>\n" +
                "    <Field>Size</Field>\n" +
                "    <Field>LastModifiedDate</Field>\n" +
                "    <Field>StorageClass</Field>\n" +
                "  </OptionalFields>\n" +
                "</InventoryConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        InventoryConfiguration xmlConfiguration = xmlMapper.readValue(xml, InventoryConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        InventoryOSSBucketDestination destination = InventoryOSSBucketDestination.newBuilder()
                .format("CSV")
                .accountId("100000000000000")
                .roleArn("acs:ram::100000000000000:role/AliyunOSSRole")
                .bucket("acs:oss:::destbucket")
                .prefix("once-inventory/")
                .build();

        InventoryDestination inventoryDestination = InventoryDestination.newBuilder()
                .ossBucketDestination(destination)
                .build();

        InventorySchedule schedule = InventorySchedule.newBuilder()
                .frequency(InventoryFrequencyType.ONCE.toString())
                .autoDelete(true)
                .build();

        InventoryFilter filter = InventoryFilter.newBuilder()
                .prefix("log/")
                .build();

        List<String> fields = Arrays.asList(
                InventoryOptionalFieldType.SIZE.toString(),
                InventoryOptionalFieldType.LAST_MODIFIED_DATE.toString(),
                InventoryOptionalFieldType.STORAGE_CLASS.toString()
        );
        OptionalFields optionalFields = OptionalFields.newBuilder()
                .fields(fields)
                .build();

        InventoryConfiguration inventoryConfiguration = InventoryConfiguration.newBuilder()
                .id("once-report")
                .isEnabled(true)
                .destination(inventoryDestination)
                .schedule(schedule)
                .filter(filter)
                .includedObjectVersions("All")
                .optionalFields(optionalFields)
                .build();

        PutBucketInventoryRequest request = PutBucketInventoryRequest.newBuilder()
                .bucket("xml-bucket")
                .inventoryId("once-report")
                .inventoryConfiguration(inventoryConfiguration)
                .build();

        OperationInput input = SerdeBucketInventory.fromPutBucketInventory(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("inventoryId")).isEqualTo("once-report");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<Frequency>Once</Frequency>");
        assertThat(xmlContent).contains("<AutoDelete>true</AutoDelete>");

        // Compare with expected XML
        assertThat(xmlContent).isEqualTo(expectedXml);
    }

    @Test
    public void testNewOptionalFieldTypes() {
        // Verify the new optional field types are available
        assertThat(InventoryOptionalFieldType.KEY.toString()).isEqualTo("Key");
        assertThat(InventoryOptionalFieldType.VERSION_ID.toString()).isEqualTo("VersionId");
        assertThat(InventoryOptionalFieldType.IS_DELETE_MARKER.toString()).isEqualTo("IsDeleteMarker");

        // Verify fromString parsing
        assertThat(InventoryOptionalFieldType.fromString("Key")).isEqualTo(InventoryOptionalFieldType.KEY);
        assertThat(InventoryOptionalFieldType.fromString("VersionId")).isEqualTo(InventoryOptionalFieldType.VERSION_ID);
        assertThat(InventoryOptionalFieldType.fromString("IsDeleteMarker")).isEqualTo(InventoryOptionalFieldType.IS_DELETE_MARKER);
    }

    @Test
    public void testNewFrequencyTypes() {
        // Verify the new frequency types are available
        assertThat(InventoryFrequencyType.MONTHLY.toString()).isEqualTo("Monthly");
        assertThat(InventoryFrequencyType.ONCE.toString()).isEqualTo("Once");

        // Verify fromString parsing
        assertThat(InventoryFrequencyType.fromString("Monthly")).isEqualTo(InventoryFrequencyType.MONTHLY);
        assertThat(InventoryFrequencyType.fromString("Once")).isEqualTo(InventoryFrequencyType.ONCE);
    }

    @Test
    public void testScheduleToBuilderPreservesNewFields() {
        InventorySchedule schedule = InventorySchedule.newBuilder()
                .frequency("Monthly")
                .dayOfMonth(15)
                .build();

        InventorySchedule copy = schedule.toBuilder().build();
        assertThat(copy.frequency()).isEqualTo("Monthly");
        assertThat(copy.dayOfMonth()).isEqualTo(15);
        assertThat(copy.autoDelete()).isNull();

        InventorySchedule onceSchedule = InventorySchedule.newBuilder()
                .frequency("Once")
                .autoDelete(false)
                .build();

        InventorySchedule onceCopy = onceSchedule.toBuilder().build();
        assertThat(onceCopy.frequency()).isEqualTo("Once");
        assertThat(onceCopy.autoDelete()).isEqualTo(false);
        assertThat(onceCopy.dayOfMonth()).isNull();
    }
}