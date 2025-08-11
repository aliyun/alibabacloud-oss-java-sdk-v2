package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.BucketInfoXml;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketInfoResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketInfoResult result = GetBucketInfoResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();

    }

    @Test
    public void testFullBuilder() {
        Owner owner = Owner.newBuilder()
                .displayName("username")
                .id("27183473914****")
                .build();

        AccessControlList accessControlList = AccessControlList.newBuilder()
                .grant("private")
                .build();

        BucketPolicy bucketPolicy = BucketPolicy.newBuilder()
                .logBucket("examplebucket")
                .logPrefix("log/")
                .build();

        BucketInfoServerSideEncryptionRule encryptionRule = BucketInfoServerSideEncryptionRule.newBuilder()
                .sSEAlgorithm("KMS")
                .kMSMasterKeyID("shUhih687675***32edghadg")
                .kMSDataEncryption("SM4")
                .build();

        BucketInfo bucket = BucketInfo.newBuilder()
                .extranetEndpoint("oss-cn-hangzhou.aliyuncs.com")
                .resourceGroupId("rg-aek27tc********")
                .serverSideEncryptionRule(encryptionRule)
                .bucketPolicy(bucketPolicy)
                .comment("test")
                .blockPublicAccess(true)
                .accessMonitor("Enabled")
                .crossRegionReplication("Disabled")
                .location("oss-cn-hangzhou")
                .name("oss-example")
                .storageClass("Standard")
                .versioning("Enabled")
                .creationDate(Instant.parse("2013-07-31T10:56:21.000Z"))
                .intranetEndpoint("oss-cn-hangzhou-internal.aliyuncs.com")
                .transferAcceleration("Disabled")
                .accessControlList(accessControlList)
                .dataRedundancyType("LRS")
                .owner(owner)
                .build();

        BucketInfoXml bucketInfo = BucketInfoXml.newBuilder()
                .bucket(bucket)
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetBucketInfoResult result = GetBucketInfoResult.newBuilder()
                .headers(headers)
                .innerBody(bucketInfo)
                .build();

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        BucketInfo resultBucket = result.bucketInfo();

        assertThat(resultBucket.extranetEndpoint()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(resultBucket.resourceGroupId()).isEqualTo("rg-aek27tc********");
        assertThat(resultBucket.comment()).isEqualTo("test");
        assertThat(resultBucket.blockPublicAccess()).isEqualTo(true);
        assertThat(resultBucket.accessMonitor()).isEqualTo("Enabled");
        assertThat(resultBucket.crossRegionReplication()).isEqualTo("Disabled");
        assertThat(resultBucket.location()).isEqualTo("oss-cn-hangzhou");
        assertThat(resultBucket.name()).isEqualTo("oss-example");
        assertThat(resultBucket.storageClass()).isEqualTo("Standard");
        assertThat(resultBucket.versioning()).isEqualTo("Enabled");
        assertThat(resultBucket.creationDate()).isEqualTo(Instant.parse("2013-07-31T10:56:21.000Z"));
        assertThat(resultBucket.intranetEndpoint()).isEqualTo("oss-cn-hangzhou-internal.aliyuncs.com");
        assertThat(resultBucket.transferAcceleration()).isEqualTo("Disabled");
        assertThat(resultBucket.dataRedundancyType()).isEqualTo("LRS");

        assertThat(resultBucket.serverSideEncryptionRule().sseAlgorithm()).isEqualTo("KMS");
        assertThat(resultBucket.serverSideEncryptionRule().kmsMasterKeyID()).isEqualTo("shUhih687675***32edghadg");
        assertThat(resultBucket.serverSideEncryptionRule().kmsDataEncryption()).isEqualTo("SM4");

        assertThat(resultBucket.bucketPolicy().logBucket()).isEqualTo("examplebucket");
        assertThat(resultBucket.bucketPolicy().logPrefix()).isEqualTo("log/");

        assertThat(resultBucket.accessControlList().grant()).isEqualTo("private");

        assertThat(resultBucket.owner().displayName()).isEqualTo("username");
        assertThat(resultBucket.owner().id()).isEqualTo("27183473914****");
    }

    @Test
    public void testToBuilderPreserveState() {
        Owner owner = Owner.newBuilder()
                .displayName("username")
                .id("27183473914****")
                .build();

        AccessControlList accessControlList = AccessControlList.newBuilder()
                .grant("private")
                .build();

        BucketPolicy bucketPolicy = BucketPolicy.newBuilder()
                .logBucket("examplebucket")
                .logPrefix("log/")
                .build();

        BucketInfoServerSideEncryptionRule encryptionRule = BucketInfoServerSideEncryptionRule.newBuilder()
                .sSEAlgorithm("KMS")
                .kMSMasterKeyID("shUhih687675***32edghadg")
                .kMSDataEncryption("SM4")
                .build();

        BucketInfo originalBucket = BucketInfo.newBuilder()
                .extranetEndpoint("oss-cn-hangzhou.aliyuncs.com")
                .resourceGroupId("rg-aek27tc********")
                .serverSideEncryptionRule(encryptionRule)
                .bucketPolicy(bucketPolicy)
                .comment("test")
                .blockPublicAccess(true)
                .accessMonitor("Enabled")
                .crossRegionReplication("Disabled")
                .location("oss-cn-hangzhou")
                .name("oss-example")
                .storageClass("Standard")
                .versioning("Enabled")
                .creationDate(Instant.parse("2013-07-31T10:56:21.000Z"))
                .intranetEndpoint("oss-cn-hangzhou-internal.aliyuncs.com")
                .transferAcceleration("Disabled")
                .accessControlList(accessControlList)
                .dataRedundancyType("LRS")
                .owner(owner)
                .build();

        BucketInfoXml originalBucketInfo = BucketInfoXml.newBuilder()
                .bucket(originalBucket)
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        GetBucketInfoResult original = GetBucketInfoResult.newBuilder()
                .headers(headers)
                .innerBody(originalBucketInfo)
                .build();

        GetBucketInfoResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        BucketInfo copyBucket = copy.bucketInfo();

        assertThat(copyBucket.extranetEndpoint()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(copyBucket.resourceGroupId()).isEqualTo("rg-aek27tc********");
        assertThat(copyBucket.comment()).isEqualTo("test");
        assertThat(copyBucket.blockPublicAccess()).isEqualTo(true);
        assertThat(copyBucket.accessMonitor()).isEqualTo("Enabled");
        assertThat(copyBucket.crossRegionReplication()).isEqualTo("Disabled");
        assertThat(copyBucket.location()).isEqualTo("oss-cn-hangzhou");
        assertThat(copyBucket.name()).isEqualTo("oss-example");
        assertThat(copyBucket.storageClass()).isEqualTo("Standard");
        assertThat(copyBucket.versioning()).isEqualTo("Enabled");
        assertThat(copyBucket.creationDate()).isEqualTo(Instant.parse("2013-07-31T10:56:21.000Z"));
        assertThat(copyBucket.intranetEndpoint()).isEqualTo("oss-cn-hangzhou-internal.aliyuncs.com");
        assertThat(copyBucket.transferAcceleration()).isEqualTo("Disabled");
        assertThat(copyBucket.dataRedundancyType()).isEqualTo("LRS");

        assertThat(copyBucket.serverSideEncryptionRule().sseAlgorithm()).isEqualTo("KMS");
        assertThat(copyBucket.serverSideEncryptionRule().kmsMasterKeyID()).isEqualTo("shUhih687675***32edghadg");
        assertThat(copyBucket.serverSideEncryptionRule().kmsDataEncryption()).isEqualTo("SM4");

        assertThat(copyBucket.bucketPolicy().logBucket()).isEqualTo("examplebucket");
        assertThat(copyBucket.bucketPolicy().logPrefix()).isEqualTo("log/");

        assertThat(copyBucket.accessControlList().grant()).isEqualTo("private");

        assertThat(copyBucket.owner().displayName()).isEqualTo("username");
        assertThat(copyBucket.owner().id()).isEqualTo("27183473914****");
    }

    @Test
    public void testXmlBuilder() throws JsonProcessingException {
        String xml =
                "<BucketInfo>\n" +
                        "  <Bucket>\n" +
                        "    <AccessMonitor>Enabled</AccessMonitor>\n" +
                        "    <CreationDate>2013-07-31T10:56:21.000Z</CreationDate>\n" +
                        "    <ExtranetEndpoint>oss-cn-hangzhou.aliyuncs.com</ExtranetEndpoint>\n" +
                        "    <IntranetEndpoint>oss-cn-hangzhou-internal.aliyuncs.com</IntranetEndpoint>\n" +
                        "    <Location>oss-cn-hangzhou</Location>\n" +
                        "    <StorageClass>Standard</StorageClass>\n" +
                        "    <TransferAcceleration>Disabled</TransferAcceleration>\n" +
                        "    <CrossRegionReplication>Disabled</CrossRegionReplication>\n" +
                        "    <DataRedundancyType>LRS</DataRedundancyType>\n" +
                        "    <Name>oss-example</Name>\n" +
                        "    <ResourceGroupId>rg-aek27tc********</ResourceGroupId>\n" +
                        "    <Owner>\n" +
                        "      <DisplayName>username</DisplayName>\n" +
                        "      <ID>27183473914****</ID>\n" +
                        "    </Owner>\n" +
                        "    <AccessControlList>\n" +
                        "      <Grant>private</Grant>\n" +
                        "    </AccessControlList>\n" +
                        "    <ServerSideEncryptionRule>\n" +
                        "      <SSEAlgorithm>KMS</SSEAlgorithm>\n" +
                        "      <KMSMasterKeyID>shUhih687675***32edghadg</KMSMasterKeyID>\n" +
                        "      <KMSDataEncryption>SM4</KMSDataEncryption>\n" +
                        "    </ServerSideEncryptionRule>\n" +
                        "    <BucketPolicy>\n" +
                        "      <LogBucket>examplebucket</LogBucket>\n" +
                        "      <LogPrefix>log/</LogPrefix>\n" +
                        "    </BucketPolicy>\n" +
                        "    <Comment>test</Comment>\n" +
                        "    <Versioning>Enabled</Versioning>\n" +
                        "    <BlockPublicAccess>true</BlockPublicAccess>\n" +
                        "  </Bucket>\n" +
                        "</BucketInfo>";

        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        BucketInfoXml innerBody = xmlMapper.readValue(xml, BucketInfoXml.class);
        GetBucketInfoResult result = GetBucketInfoResult.newBuilder()
                .innerBody(innerBody)
                .build();

        BucketInfo resultBucket = result.bucketInfo();

        assertThat(resultBucket.extranetEndpoint()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(resultBucket.resourceGroupId()).isEqualTo("rg-aek27tc********");
        assertThat(resultBucket.comment()).isEqualTo("test");
        assertThat(resultBucket.blockPublicAccess()).isEqualTo(true);
        assertThat(resultBucket.accessMonitor()).isEqualTo("Enabled");
        assertThat(resultBucket.crossRegionReplication()).isEqualTo("Disabled");
        assertThat(resultBucket.location()).isEqualTo("oss-cn-hangzhou");
        assertThat(resultBucket.name()).isEqualTo("oss-example");
        assertThat(resultBucket.storageClass()).isEqualTo("Standard");
        assertThat(resultBucket.versioning()).isEqualTo("Enabled");
        assertThat(resultBucket.creationDate()).isEqualTo(Instant.parse("2013-07-31T10:56:21.000Z"));
        assertThat(resultBucket.intranetEndpoint()).isEqualTo("oss-cn-hangzhou-internal.aliyuncs.com");
        assertThat(resultBucket.transferAcceleration()).isEqualTo("Disabled");
        assertThat(resultBucket.dataRedundancyType()).isEqualTo("LRS");

        assertThat(resultBucket.serverSideEncryptionRule().sseAlgorithm()).isEqualTo("KMS");
        assertThat(resultBucket.serverSideEncryptionRule().kmsMasterKeyID()).isEqualTo("shUhih687675***32edghadg");
        assertThat(resultBucket.serverSideEncryptionRule().kmsDataEncryption()).isEqualTo("SM4");

        assertThat(resultBucket.bucketPolicy().logBucket()).isEqualTo("examplebucket");
        assertThat(resultBucket.bucketPolicy().logPrefix()).isEqualTo("log/");

        assertThat(resultBucket.accessControlList().grant()).isEqualTo("private");

        assertThat(resultBucket.owner().displayName()).isEqualTo("username");
        assertThat(resultBucket.owner().id()).isEqualTo("27183473914****");
    }
}
