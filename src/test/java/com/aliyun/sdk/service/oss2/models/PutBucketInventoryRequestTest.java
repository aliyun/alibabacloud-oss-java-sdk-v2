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
import java.util.stream.Collectors;

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

        List<InventoryOptionalFieldType> fields = Arrays.asList(
                InventoryOptionalFieldType.SIZE,
                InventoryOptionalFieldType.LAST_MODIFIED_DATE,
                InventoryOptionalFieldType.E_TAG,
                InventoryOptionalFieldType.STORAGE_CLASS,
                InventoryOptionalFieldType.IS_MULTIPART_UPLOADED,
                InventoryOptionalFieldType.ENCRYPTION_STATUS
        );
        OptionalFields optionalFields = OptionalFields.newBuilder()
                .fields(fields.stream().map(InventoryOptionalFieldType::toString).collect(Collectors.toList()))
                .build();

        // Create incremental inventory configuration
        IncrementInventorySchedule incrementSchedule = IncrementInventorySchedule.newBuilder()
                .frequency(600L) // 10 minutes in seconds
                .build();

        List<IncrementalInventoryOptionalFieldType> incrementFields = Arrays.asList(
                IncrementalInventoryOptionalFieldType.SEQUENCE_NUMBER,
                IncrementalInventoryOptionalFieldType.RECORD_TYPE,
                IncrementalInventoryOptionalFieldType.RECORD_TIMESTAMP,
                IncrementalInventoryOptionalFieldType.REQUESTER,
                IncrementalInventoryOptionalFieldType.REQUEST_ID,
                IncrementalInventoryOptionalFieldType.SOURCE_IP,
                IncrementalInventoryOptionalFieldType.SIZE,
                IncrementalInventoryOptionalFieldType.STORAGE_CLASS,
                IncrementalInventoryOptionalFieldType.LAST_MODIFIED_DATE,
                IncrementalInventoryOptionalFieldType.E_TAG,
                IncrementalInventoryOptionalFieldType.IS_MULTIPART_UPLOADED,
                IncrementalInventoryOptionalFieldType.OBJECT_TYPE,
                IncrementalInventoryOptionalFieldType.OBJECT_ACL,
                IncrementalInventoryOptionalFieldType.CRC64,
                IncrementalInventoryOptionalFieldType.ENCRYPTION_STATUS
        );
        OptionalFields incrementalOptionalFields = OptionalFields.newBuilder()
                .fields(incrementFields.stream().map(IncrementalInventoryOptionalFieldType::toString).collect(Collectors.toList()))
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

        List<IncrementalInventoryOptionalFieldType> incrementFields = Arrays.asList(
                IncrementalInventoryOptionalFieldType.SEQUENCE_NUMBER,
                IncrementalInventoryOptionalFieldType.RECORD_TYPE
        );
        OptionalFields incrementalOptionalFields = OptionalFields.newBuilder()
                .fields(incrementFields.stream().map(IncrementalInventoryOptionalFieldType::toString).collect(Collectors.toList()))
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

        List<IncrementalInventoryOptionalFieldType> incrementFields = Arrays.asList(
                IncrementalInventoryOptionalFieldType.SEQUENCE_NUMBER
        );
        OptionalFields incrementalOptionalFields = OptionalFields.newBuilder()
                .fields(incrementFields.stream().map(IncrementalInventoryOptionalFieldType::toString).collect(Collectors.toList()))
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
                "    <Field>TransistionTime</Field>\n" +
                "    <Field>ETag</Field>\n" +
                "    <Field>StorageClass</Field>\n" +
                "    <Field>IsMultipartUploaded</Field>\n" +
                "    <Field>EncryptionStatus</Field>\n" +
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

        List<InventoryOptionalFieldType> fields = Arrays.asList(
                InventoryOptionalFieldType.SIZE,
                InventoryOptionalFieldType.LAST_MODIFIED_DATE,
                InventoryOptionalFieldType.E_TAG,
                InventoryOptionalFieldType.STORAGE_CLASS,
                InventoryOptionalFieldType.IS_MULTIPART_UPLOADED,
                InventoryOptionalFieldType.ENCRYPTION_STATUS
        );
        OptionalFields optionalFields = OptionalFields.newBuilder()
                .fields(fields.stream().map(InventoryOptionalFieldType::toString).collect(Collectors.toList()))
                .build();

        // Create incremental inventory configuration
        IncrementInventorySchedule incrementSchedule = IncrementInventorySchedule.newBuilder()
                .frequency(600L)
                .build();

        List<IncrementalInventoryOptionalFieldType> incrementFields = Arrays.asList(
                IncrementalInventoryOptionalFieldType.SEQUENCE_NUMBER,
                IncrementalInventoryOptionalFieldType.RECORD_TYPE,
                IncrementalInventoryOptionalFieldType.RECORD_TIMESTAMP,
                IncrementalInventoryOptionalFieldType.REQUESTER,
                IncrementalInventoryOptionalFieldType.REQUEST_ID,
                IncrementalInventoryOptionalFieldType.SOURCE_IP,
                IncrementalInventoryOptionalFieldType.SIZE,
                IncrementalInventoryOptionalFieldType.STORAGE_CLASS,
                IncrementalInventoryOptionalFieldType.LAST_MODIFIED_DATE,
                IncrementalInventoryOptionalFieldType.E_TAG,
                IncrementalInventoryOptionalFieldType.IS_MULTIPART_UPLOADED,
                IncrementalInventoryOptionalFieldType.OBJECT_TYPE,
                IncrementalInventoryOptionalFieldType.OBJECT_ACL,
                IncrementalInventoryOptionalFieldType.CRC64,
                IncrementalInventoryOptionalFieldType.ENCRYPTION_STATUS
        );
        OptionalFields incrementalOptionalFields = OptionalFields.newBuilder()
                .fields(incrementFields.stream().map(IncrementalInventoryOptionalFieldType::toString).collect(Collectors.toList()))
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
}