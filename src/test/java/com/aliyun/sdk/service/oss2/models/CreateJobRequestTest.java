package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transform.SerdeBatchOperations;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateJobRequestTest {

    @Test
    public void testBasicBuilder() {
        CreateJobRequestBody body = CreateJobRequestBody.newBuilder()
                .confirmationRequired(false)
                .description("test job")
                .priority(10L)
                .roleArn("arn:acs:ram::uid:role/BatchOperationRole")
                .clientRequestToken("unique-token-123")
                .build();

        CreateJobRequest request = CreateJobRequest.newBuilder()
                .createJobBody(body)
                .build();

        assertThat(request).isNotNull();
        assertThat(request.createJobBody()).isNotNull();
        assertThat(request.createJobBody().confirmationRequired()).isFalse();
        assertThat(request.createJobBody().description()).isEqualTo("test job");
        assertThat(request.createJobBody().priority()).isEqualTo(10L);
        assertThat(request.createJobBody().roleArn()).isEqualTo("arn:acs:ram::uid:role/BatchOperationRole");
        assertThat(request.createJobBody().clientRequestToken()).isEqualTo("unique-token-123");
    }

    @Test
    public void testWithPutObjectTaggingOperation() {
        Tag tag1 = Tag.newBuilder().key("Environment").value("Production").build();
        Tag tag2 = Tag.newBuilder().key("Project").value("MyProject").build();
        TagSet tagSet = TagSet.newBuilder().tags(Arrays.asList(tag1, tag2)).build();

        JobPutObjectTagging putTagging = JobPutObjectTagging.newBuilder()
                .tagSet(tagSet)
                .build();

        JobOperation operation = JobOperation.newBuilder()
                .putObjectTagging(putTagging)
                .build();

        CreateJobRequestBody body = CreateJobRequestBody.newBuilder()
                .operation(operation)
                .priority(10L)
                .roleArn("arn:acs:ram::uid:role/BatchOperationRole")
                .clientRequestToken("token-1")
                .build();

        assertThat(body.operation()).isNotNull();
        assertThat(body.operation().putObjectTagging()).isNotNull();
        assertThat(body.operation().putObjectTagging().tagSet()).isNotNull();
        assertThat(body.operation().putObjectTagging().tagSet().tags()).hasSize(2);
        assertThat(body.operation().putObjectTagging().tagSet().tags().get(0).key()).isEqualTo("Environment");
        assertThat(body.operation().putObjectTagging().tagSet().tags().get(0).value()).isEqualTo("Production");
    }

    @Test
    public void testWithDeleteObjectTaggingOperation() {
        JobDeleteObjectTagging deleteTagging = JobDeleteObjectTagging.newBuilder().build();
        JobOperation operation = JobOperation.newBuilder()
                .deleteObjectTagging(deleteTagging)
                .build();

        CreateJobRequestBody body = CreateJobRequestBody.newBuilder()
                .operation(operation)
                .build();

        assertThat(body.operation().deleteObjectTagging()).isNotNull();
        assertThat(body.operation().putObjectTagging()).isNull();
    }

    @Test
    public void testWithPutObjectAclOperation() {
        JobPutObjectAcl putAcl = JobPutObjectAcl.newBuilder()
                .objectAcl("private")
                .build();

        JobOperation operation = JobOperation.newBuilder()
                .putObjectAcl(putAcl)
                .build();

        CreateJobRequestBody body = CreateJobRequestBody.newBuilder()
                .operation(operation)
                .build();

        assertThat(body.operation().putObjectAcl()).isNotNull();
        assertThat(body.operation().putObjectAcl().objectAcl()).isEqualTo("private");
    }

    @Test
    public void testWithRestoreObjectOperation() {
        JobRestoreObject restore = JobRestoreObject.newBuilder()
                .days(7L)
                .tier("Standard")
                .build();

        JobOperation operation = JobOperation.newBuilder()
                .restoreObject(restore)
                .build();

        CreateJobRequestBody body = CreateJobRequestBody.newBuilder()
                .operation(operation)
                .build();

        assertThat(body.operation().restoreObject()).isNotNull();
        assertThat(body.operation().restoreObject().days()).isEqualTo(7L);
        assertThat(body.operation().restoreObject().tier()).isEqualTo("Standard");
    }

    @Test
    public void testWithManifest() {
        JobManifestLocation location = JobManifestLocation.newBuilder()
                .bucket("manifest-bucket")
                .object("manifest.csv")
                .eTag("d41d8cd98f00b204e9800998ecf8427e")
                .versionId("version-1")
                .build();

        JobManifestSpec spec = JobManifestSpec.newBuilder()
                .format("OSS_BatchOperations_CSV_20250611")
                .fields("Bucket,Key")
                .build();

        JobManifest manifest = JobManifest.newBuilder()
                .location(location)
                .spec(spec)
                .build();

        CreateJobRequestBody body = CreateJobRequestBody.newBuilder()
                .manifest(manifest)
                .build();

        assertThat(body.manifest()).isNotNull();
        assertThat(body.manifest().location().bucket()).isEqualTo("manifest-bucket");
        assertThat(body.manifest().location().object()).isEqualTo("manifest.csv");
        assertThat(body.manifest().location().eTag()).isEqualTo("d41d8cd98f00b204e9800998ecf8427e");
        assertThat(body.manifest().spec().format()).isEqualTo("OSS_BatchOperations_CSV_20250611");
        assertThat(body.manifest().spec().fields()).isEqualTo("Bucket,Key");
    }

    @Test
    public void testWithKeyPrefixManifestGenerator() {
        KeyPrefixManifestGenerator generator = KeyPrefixManifestGenerator.newBuilder()
                .sourceBucket("source-bucket")
                .prefix("prefix/")
                .build();

        CreateJobRequestBody body = CreateJobRequestBody.newBuilder()
                .keyPrefixManifestGenerator(generator)
                .build();

        assertThat(body.keyPrefixManifestGenerator()).isNotNull();
        assertThat(body.keyPrefixManifestGenerator().sourceBucket()).isEqualTo("source-bucket");
        assertThat(body.keyPrefixManifestGenerator().prefix()).isEqualTo("prefix/");
    }

    @Test
    public void testWithReport() {
        JobReport report = JobReport.newBuilder()
                .bucket("report-bucket")
                .enabled(true)
                .prefix("batch-reports/")
                .reportScope("AllTasks")
                .build();

        CreateJobRequestBody body = CreateJobRequestBody.newBuilder()
                .report(report)
                .build();

        assertThat(body.report()).isNotNull();
        assertThat(body.report().bucket()).isEqualTo("report-bucket");
        assertThat(body.report().enabled()).isTrue();
        assertThat(body.report().prefix()).isEqualTo("batch-reports/");
        assertThat(body.report().reportScope()).isEqualTo("AllTasks");
    }

    @Test
    public void testXmlSerialization() throws JsonProcessingException {
        Tag tag = Tag.newBuilder().key("Environment").value("Production").build();
        TagSet tagSet = TagSet.newBuilder().tags(Arrays.asList(tag)).build();

        JobPutObjectTagging putTagging = JobPutObjectTagging.newBuilder()
                .tagSet(tagSet)
                .build();

        JobOperation operation = JobOperation.newBuilder()
                .putObjectTagging(putTagging)
                .build();

        JobReport report = JobReport.newBuilder()
                .bucket("report-bucket")
                .enabled(true)
                .prefix("batch-reports/")
                .reportScope("AllTasks")
                .build();

        JobManifestLocation location = JobManifestLocation.newBuilder()
                .bucket("manifest-bucket")
                .object("manifest.csv")
                .eTag("d41d8cd98f00b204e9800998ecf8427e")
                .build();

        JobManifestSpec spec = JobManifestSpec.newBuilder()
                .format("OSS_BatchOperations_CSV_20250611")
                .fields("Bucket,Key")
                .build();

        JobManifest manifest = JobManifest.newBuilder()
                .location(location)
                .spec(spec)
                .build();

        CreateJobRequestBody body = CreateJobRequestBody.newBuilder()
                .confirmationRequired(false)
                .operation(operation)
                .report(report)
                .clientRequestToken("unique-token-123")
                .manifest(manifest)
                .description("batch tagging job")
                .priority(10L)
                .roleArn("arn:acs:ram::uid:role/BatchOperationRole")
                .build();

        CreateJobRequest request = CreateJobRequest.newBuilder()
                .createJobBody(body)
                .build();

        OperationInput input = SerdeBatchOperations.fromCreateJob(request);

        assertThat(input.opName()).isEqualTo("CreateJob");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.parameters().get("batchJob")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData bodyData = input.body().get();
        String xmlContent = new String(bodyData.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<CreateJobRequest>");
        assertThat(xmlContent).contains("<ConfirmationRequired>false</ConfirmationRequired>");
        assertThat(xmlContent).contains("<Operation>");
        assertThat(xmlContent).contains("<PutObjectTagging>");
        assertThat(xmlContent).contains("<TagSet>");
        assertThat(xmlContent).contains("<Key>Environment</Key>");
        assertThat(xmlContent).contains("<Value>Production</Value>");
        assertThat(xmlContent).contains("<Report>");
        assertThat(xmlContent).contains("<Bucket>report-bucket</Bucket>");
        assertThat(xmlContent).contains("<Enabled>true</Enabled>");
        assertThat(xmlContent).contains("<ReportScope>AllTasks</ReportScope>");
        assertThat(xmlContent).contains("<Manifest>");
        assertThat(xmlContent).contains("<Format>OSS_BatchOperations_CSV_20250611</Format>");
        assertThat(xmlContent).contains("<Description>batch tagging job</Description>");
        assertThat(xmlContent).contains("<Priority>10</Priority>");
        assertThat(xmlContent).contains("<RoleArn>arn:acs:ram::uid:role/BatchOperationRole</RoleArn>");
        assertThat(xmlContent).contains("<ClientRequestToken>unique-token-123</ClientRequestToken>");
    }

    @Test
    public void testToBuilder() {
        JobRestoreObject restore = JobRestoreObject.newBuilder()
                .days(7L)
                .tier("Standard")
                .build();

        JobOperation operation = JobOperation.newBuilder()
                .restoreObject(restore)
                .build();

        CreateJobRequestBody body = CreateJobRequestBody.newBuilder()
                .operation(operation)
                .priority(5L)
                .roleArn("arn:acs:ram::uid:role/Role")
                .clientRequestToken("token")
                .build();

        CreateJobRequest request = CreateJobRequest.newBuilder()
                .createJobBody(body)
                .parameter("param1", "value1")
                .build();

        CreateJobRequest copy = request.toBuilder().build();
        assertThat(copy.createJobBody().priority()).isEqualTo(5L);
        assertThat(copy.createJobBody().roleArn()).isEqualTo("arn:acs:ram::uid:role/Role");
        assertThat(copy.createJobBody().operation().restoreObject().days()).isEqualTo(7L);
        assertThat(copy.parameters().get("param1")).isEqualTo("value1");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<CreateJobRequest>\n" +
                "  <ConfirmationRequired>false</ConfirmationRequired>\n" +
                "  <Operation>\n" +
                "    <PutObjectTagging>\n" +
                "      <TagSet>\n" +
                "        <Tag>\n" +
                "          <Key>Environment</Key>\n" +
                "          <Value>Production</Value>\n" +
                "        </Tag>\n" +
                "      </TagSet>\n" +
                "    </PutObjectTagging>\n" +
                "    <DeleteObjectTagging>\n" +
                "    </DeleteObjectTagging>\n" +
                "    <PutObjectAcl>\n" +
                "      <ObjectAcl>private</ObjectAcl>\n" +
                "    </PutObjectAcl>\n" +
                "    <RestoreObject>\n" +
                "      <Days>7</Days>\n" +
                "      <Tier>Standard</Tier>\n" +
                "    </RestoreObject>\n" +
                "  </Operation>\n" +
                "  <Report>\n" +
                "    <Bucket>report-bucket</Bucket>\n" +
                "    <Enabled>true</Enabled>\n" +
                "    <Prefix>batch-reports/</Prefix>\n" +
                "    <ReportScope>AllTasks</ReportScope>\n" +
                "  </Report>\n" +
                "  <ClientRequestToken>unique-token-123</ClientRequestToken>\n" +
                "  <Manifest>\n" +
                "    <Location>\n" +
                "      <ETag>d41d8cd98f00b204e9800998ecf8427e</ETag>\n" +
                "      <Bucket>manifest-bucket</Bucket>\n" +
                "      <Object>manifest.csv</Object>\n" +
                "      <VersionId>CAEQNRiBgICk8K7b0hciIDkzZjQ2MjA4YjRkMDQ3Yjk2YWEyNzk2NTdjYjQ0</VersionId>\n" +
                "    </Location>\n" +
                "    <Spec>\n" +
                "      <Fields>Bucket,Key,VersionId</Fields>\n" +
                "      <Format>OSS_BatchOperations_CSV_20250611</Format>\n" +
                "    </Spec>\n" +
                "  </Manifest>\n" +
                "  <KeyPrefixManifestGenerator>\n" +
                "    <SourceBucket>source-bucket</SourceBucket>\n" +
                "    <Prefix>data/</Prefix>\n" +
                "  </KeyPrefixManifestGenerator>\n" +
                "  <Description>batch tagging job</Description>\n" +
                "  <Priority>10</Priority>\n" +
                "  <RoleArn>arn:acs:ram::uid:role/BatchOperationRole</RoleArn>\n" +
                "</CreateJobRequest>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        CreateJobRequestBody xmlBody = xmlMapper.readValue(xml, CreateJobRequestBody.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlBody);

        Tag tag = Tag.newBuilder().key("Environment").value("Production").build();
        TagSet tagSet = TagSet.newBuilder().tags(Arrays.asList(tag)).build();

        JobPutObjectTagging putTagging = JobPutObjectTagging.newBuilder()
                .tagSet(tagSet)
                .build();

        JobDeleteObjectTagging deleteTagging = JobDeleteObjectTagging.newBuilder().build();

        JobPutObjectAcl putAcl = JobPutObjectAcl.newBuilder()
                .objectAcl("private")
                .build();

        JobRestoreObject restore = JobRestoreObject.newBuilder()
                .days(7L)
                .tier("Standard")
                .build();

        JobOperation operation = JobOperation.newBuilder()
                .putObjectTagging(putTagging)
                .deleteObjectTagging(deleteTagging)
                .putObjectAcl(putAcl)
                .restoreObject(restore)
                .build();

        JobReport report = JobReport.newBuilder()
                .bucket("report-bucket")
                .enabled(true)
                .prefix("batch-reports/")
                .reportScope("AllTasks")
                .build();

        JobManifestLocation location = JobManifestLocation.newBuilder()
                .bucket("manifest-bucket")
                .object("manifest.csv")
                .eTag("d41d8cd98f00b204e9800998ecf8427e")
                .versionId("CAEQNRiBgICk8K7b0hciIDkzZjQ2MjA4YjRkMDQ3Yjk2YWEyNzk2NTdjYjQ0")
                .build();

        JobManifestSpec spec = JobManifestSpec.newBuilder()
                .format("OSS_BatchOperations_CSV_20250611")
                .fields("Bucket,Key,VersionId")
                .build();

        JobManifest manifest = JobManifest.newBuilder()
                .location(location)
                .spec(spec)
                .build();

        KeyPrefixManifestGenerator keyPrefixGenerator = KeyPrefixManifestGenerator.newBuilder()
                .sourceBucket("source-bucket")
                .prefix("data/")
                .build();

        CreateJobRequestBody body = CreateJobRequestBody.newBuilder()
                .confirmationRequired(false)
                .operation(operation)
                .report(report)
                .clientRequestToken("unique-token-123")
                .manifest(manifest)
                .keyPrefixManifestGenerator(keyPrefixGenerator)
                .description("batch tagging job")
                .priority(10L)
                .roleArn("arn:acs:ram::uid:role/BatchOperationRole")
                .build();

        CreateJobRequest request = CreateJobRequest.newBuilder()
                .createJobBody(body)
                .build();

        OperationInput input = SerdeBatchOperations.fromCreateJob(request);

        assertThat(input.opName()).isEqualTo("CreateJob");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.parameters().get("batchJob")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData bodyData = input.body().get();
        String xmlContent = new String(bodyData.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).isEqualTo(expectedXml);
    }
}
