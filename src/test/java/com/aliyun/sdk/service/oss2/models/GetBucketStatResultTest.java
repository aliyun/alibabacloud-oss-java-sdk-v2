package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class GetBucketStatResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketStatResult result = GetBucketStatResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-request-id", "req-1234567890abcdefg");

        BucketStat bucketStat = BucketStat.newBuilder()
                .archiveObjectCount(100L)
                .multipartUploadCount(50L)
                .liveChannelCount(10L)
                .multipartPartCount(200L)
                .standardObjectCount(300L)
                .infrequentAccessRealStorage(1024L)
                .archiveStorage(2048L)
                .deleteMarkerCount(5L)
                .standardStorage(3072L)
                .infrequentAccessStorage(4096L)
                .coldArchiveStorage(5120L)
                .coldArchiveObjectCount(150L)
                .deepColdArchiveObjectCount(200L)
                .deepColdArchiveStorage(6144L)
                .deepColdArchiveRealStorage(7168L)
                .storage(8192L)
                .objectCount(400L)
                .lastModifiedTime(1660320000L)
                .infrequentAccessObjectCount(80L)
                .archiveRealStorage(9216L)
                .coldArchiveRealStorage(10240L)
                .build();

        GetBucketStatResult result = GetBucketStatResult.newBuilder()
                .headers(headers)
                .innerBody(bucketStat)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.bucketStat().archiveObjectCount()).isEqualTo(100L);
        assertThat(result.bucketStat().multipartUploadCount()).isEqualTo(50L);
        assertThat(result.bucketStat().liveChannelCount()).isEqualTo(10L);
        assertThat(result.bucketStat().multipartPartCount()).isEqualTo(200L);
        assertThat(result.bucketStat().standardObjectCount()).isEqualTo(300L);
        assertThat(result.bucketStat().infrequentAccessRealStorage()).isEqualTo(1024L);
        assertThat(result.bucketStat().archiveStorage()).isEqualTo(2048L);
        assertThat(result.bucketStat().deleteMarkerCount()).isEqualTo(5L);
        assertThat(result.bucketStat().standardStorage()).isEqualTo(3072L);
        assertThat(result.bucketStat().infrequentAccessStorage()).isEqualTo(4096L);
        assertThat(result.bucketStat().coldArchiveStorage()).isEqualTo(5120L);
        assertThat(result.bucketStat().coldArchiveObjectCount()).isEqualTo(150L);
        assertThat(result.bucketStat().deepColdArchiveObjectCount()).isEqualTo(200L);
        assertThat(result.bucketStat().deepColdArchiveStorage()).isEqualTo(6144L);
        assertThat(result.bucketStat().deepColdArchiveRealStorage()).isEqualTo(7168L);
        assertThat(result.bucketStat().storage()).isEqualTo(8192L);
        assertThat(result.bucketStat().objectCount()).isEqualTo(400L);
        assertThat(result.bucketStat().lastModifiedTime()).isEqualTo(1660320000L);
        assertThat(result.bucketStat().infrequentAccessObjectCount()).isEqualTo(80L);
        assertThat(result.bucketStat().archiveRealStorage()).isEqualTo(9216L);
        assertThat(result.bucketStat().coldArchiveRealStorage()).isEqualTo(10240L);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-request-id", "req-765432109876543210");

        BucketStat bucketStat = BucketStat.newBuilder()
                .archiveObjectCount(200L)
                .multipartUploadCount(100L)
                .liveChannelCount(20L)
                .multipartPartCount(400L)
                .standardObjectCount(600L)
                .infrequentAccessRealStorage(2048L)
                .archiveStorage(4096L)
                .deleteMarkerCount(10L)
                .standardStorage(6144L)
                .infrequentAccessStorage(8192L)
                .coldArchiveStorage(10240L)
                .coldArchiveObjectCount(300L)
                .deepColdArchiveObjectCount(400L)
                .deepColdArchiveStorage(12288L)
                .deepColdArchiveRealStorage(14336L)
                .storage(16384L)
                .objectCount(800L)
                .lastModifiedTime(1660406400L)
                .infrequentAccessObjectCount(160L)
                .archiveRealStorage(18432L)
                .coldArchiveRealStorage(20480L)
                .build();

        GetBucketStatResult original = GetBucketStatResult.newBuilder()
                .headers(headers)
                .innerBody(bucketStat)
                .build();

        GetBucketStatResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.bucketStat().archiveObjectCount()).isEqualTo(200L);
        assertThat(copy.bucketStat().multipartUploadCount()).isEqualTo(100L);
        assertThat(copy.bucketStat().liveChannelCount()).isEqualTo(20L);
        assertThat(copy.bucketStat().multipartPartCount()).isEqualTo(400L);
        assertThat(copy.bucketStat().standardObjectCount()).isEqualTo(600L);
        assertThat(copy.bucketStat().infrequentAccessRealStorage()).isEqualTo(2048L);
        assertThat(copy.bucketStat().archiveStorage()).isEqualTo(4096L);
        assertThat(copy.bucketStat().deleteMarkerCount()).isEqualTo(10L);
        assertThat(copy.bucketStat().standardStorage()).isEqualTo(6144L);
        assertThat(copy.bucketStat().infrequentAccessStorage()).isEqualTo(8192L);
        assertThat(copy.bucketStat().coldArchiveStorage()).isEqualTo(10240L);
        assertThat(copy.bucketStat().coldArchiveObjectCount()).isEqualTo(300L);
        assertThat(copy.bucketStat().deepColdArchiveObjectCount()).isEqualTo(400L);
        assertThat(copy.bucketStat().deepColdArchiveStorage()).isEqualTo(12288L);
        assertThat(copy.bucketStat().deepColdArchiveRealStorage()).isEqualTo(14336L);
        assertThat(copy.bucketStat().storage()).isEqualTo(16384L);
        assertThat(copy.bucketStat().objectCount()).isEqualTo(800L);
        assertThat(copy.bucketStat().lastModifiedTime()).isEqualTo(1660406400L);
        assertThat(copy.bucketStat().infrequentAccessObjectCount()).isEqualTo(160L);
        assertThat(copy.bucketStat().archiveRealStorage()).isEqualTo(18432L);
        assertThat(copy.bucketStat().coldArchiveRealStorage()).isEqualTo(20480L);
    }

    @Test
    public void testXmlParsing() throws Exception {
        String xml =
                "<BucketStat>\n" +
                        "  <Storage>1600</Storage>\n" +
                        "  <ObjectCount>230</ObjectCount>\n" +
                        "  <MultipartUploadCount>40</MultipartUploadCount>\n" +
                        "  <LiveChannelCount>4</LiveChannelCount>\n" +
                        "  <LastModifiedTime>1643341269</LastModifiedTime>\n" +
                        "  <StandardStorage>430</StandardStorage>\n" +
                        "  <StandardObjectCount>66</StandardObjectCount>\n" +
                        "  <InfrequentAccessStorage>2359296</InfrequentAccessStorage>\n" +
                        "  <InfrequentAccessRealStorage>360</InfrequentAccessRealStorage>\n" +
                        "  <InfrequentAccessObjectCount>54</InfrequentAccessObjectCount>\n" +
                        "  <ArchiveStorage>2949120</ArchiveStorage>\n" +
                        "  <ArchiveRealStorage>450</ArchiveRealStorage>\n" +
                        "  <ArchiveObjectCount>74</ArchiveObjectCount>\n" +
                        "  <ColdArchiveStorage>2359296</ColdArchiveStorage>\n" +
                        "  <ColdArchiveRealStorage>3610</ColdArchiveRealStorage>\n" +
                        "  <ColdArchiveObjectCount>36</ColdArchiveObjectCount>\n" +
                        "  <DeepColdArchiveStorage>23594961</DeepColdArchiveStorage>\n" +
                        "  <DeepColdArchiveRealStorage>10</DeepColdArchiveRealStorage>\n" +
                        "  <DeepColdArchiveObjectCount>16</DeepColdArchiveObjectCount>\n" +
                        "  <DeleteMarkerCount>1234355467575856878</DeleteMarkerCount>\n" +
                        "</BucketStat>";

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);

        BucketStat innerBody = xmlMapper.readValue(xml, BucketStat.class);
        GetBucketStatResult result = GetBucketStatResult.newBuilder()
                .innerBody(innerBody)
                .build();

        BucketStat bucketStat = result.bucketStat();
        assertThat(bucketStat).isNotNull();
        assertThat(bucketStat.storage()).isEqualTo(1600L);
        assertThat(bucketStat.objectCount()).isEqualTo(230L);
        assertThat(bucketStat.multipartUploadCount()).isEqualTo(40L);
        assertThat(bucketStat.liveChannelCount()).isEqualTo(4L);
        assertThat(bucketStat.lastModifiedTime()).isEqualTo(1643341269L);
        assertThat(bucketStat.standardStorage()).isEqualTo(430L);
        assertThat(bucketStat.standardObjectCount()).isEqualTo(66L);
        assertThat(bucketStat.infrequentAccessStorage()).isEqualTo(2359296L);
        assertThat(bucketStat.infrequentAccessRealStorage()).isEqualTo(360L);
        assertThat(bucketStat.infrequentAccessObjectCount()).isEqualTo(54L);
        assertThat(bucketStat.archiveStorage()).isEqualTo(2949120L);
        assertThat(bucketStat.archiveRealStorage()).isEqualTo(450L);
        assertThat(bucketStat.archiveObjectCount()).isEqualTo(74L);
        assertThat(bucketStat.coldArchiveStorage()).isEqualTo(2359296L);
        assertThat(bucketStat.coldArchiveRealStorage()).isEqualTo(3610L);
        assertThat(bucketStat.coldArchiveObjectCount()).isEqualTo(36L);
        assertThat(bucketStat.deepColdArchiveStorage()).isEqualTo(23594961L);
        assertThat(bucketStat.deepColdArchiveRealStorage()).isEqualTo(10L);
        assertThat(bucketStat.deepColdArchiveObjectCount()).isEqualTo(16L);
        assertThat(bucketStat.deleteMarkerCount()).isEqualTo(1234355467575856878L);
    }
}
