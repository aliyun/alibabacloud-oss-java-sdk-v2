package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketLifecycle;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketLifecycleRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketLifecycleRequest request = PutBucketLifecycleRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.allowSameActionOverlap()).isNull();
        assertThat(request.lifecycleConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        List<LifecycleRule> rules = Arrays.asList(
                LifecycleRule.newBuilder()
                        .id("delete after one day")
                        .prefix("logs1/")
                        .status("Enabled")
                        .expiration(LifecycleRuleExpiration.newBuilder()
                                .createdBeforeDate(Instant.parse("2020-06-22T11:42:32.000Z"))
                                .days(1)
                                .expiredObjectDeleteMarker(true)
                                .build())
                        .abortMultipartUpload(LifecycleRuleAbortMultipartUpload.newBuilder()
                                .days(30)
                                .build())
                        .build(),
                LifecycleRule.newBuilder()
                        .id("atime transition1")
                        .prefix("logs1/")
                        .status("Enabled")
                        .transitions(Arrays.asList(
                                LifecycleRuleTransition.newBuilder()
                                        .days(30)
                                        .storageClass("IA")
                                        .isAccessTime(true)
                                        .returnToStdWhenVisit(false)
                                        .build(),
                                LifecycleRuleTransition.newBuilder()
                                        .days(33)
                                        .storageClass("Archive")
                                        .isAccessTime(false)
                                        .returnToStdWhenVisit(true)
                                        .build()))
                        .atimeBase(1631698332L)
                        .build()
        );

        LifecycleConfiguration lifecycleConfiguration = LifecycleConfiguration.newBuilder()
                .rules(rules)
                .build();

        PutBucketLifecycleRequest request = PutBucketLifecycleRequest.newBuilder()
                .bucket("examplebucket")
                .allowSameActionOverlap("true")
                .lifecycleConfiguration(lifecycleConfiguration)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.allowSameActionOverlap()).isEqualTo("true");
        assertThat(request.lifecycleConfiguration()).isEqualTo(lifecycleConfiguration);

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        List<LifecycleRule> rules = Arrays.asList(
                LifecycleRule.newBuilder()
                        .id("test-rule")
                        .prefix("test/")
                        .status("Disabled")
                        .build()
        );

        LifecycleConfiguration lifecycleConfiguration = LifecycleConfiguration.newBuilder()
                .rules(rules)
                .build();

        PutBucketLifecycleRequest original = PutBucketLifecycleRequest.newBuilder()
                .bucket("testbucket")
                .allowSameActionOverlap("false")
                .lifecycleConfiguration(lifecycleConfiguration)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutBucketLifecycleRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.allowSameActionOverlap()).isEqualTo("false");
        assertThat(copy.lifecycleConfiguration()).isEqualTo(lifecycleConfiguration);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        List<LifecycleRule> rules = Arrays.asList(
                LifecycleRule.newBuilder()
                        .id("test-rule")
                        .prefix("prefix/")
                        .status("Enabled")
                        .build()
        );

        LifecycleConfiguration lifecycleConfiguration = LifecycleConfiguration.newBuilder()
                .rules(rules)
                .build();

        PutBucketLifecycleRequest request = PutBucketLifecycleRequest.newBuilder()
                .bucket("examplebucket")
                .allowSameActionOverlap("true")
                .lifecycleConfiguration(lifecycleConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.allowSameActionOverlap()).isEqualTo("true");
        assertThat(request.lifecycleConfiguration().rules()).isEqualTo(rules);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        List<LifecycleRule> rules = Arrays.asList(
                LifecycleRule.newBuilder()
                        .id("delete after one day")
                        .prefix("logs1/")
                        .status("Enabled")
                        .expiration(LifecycleRuleExpiration.newBuilder()
                                .createdBeforeDate(Instant.parse("2020-06-22T11:42:32.120Z"))
                                .days(1)
                                .expiredObjectDeleteMarker(true)
                                .build())
                        .abortMultipartUpload(LifecycleRuleAbortMultipartUpload.newBuilder()
                                .days(30)
                                .build())
                        .build(),
                LifecycleRule.newBuilder()
                        .id("atime transition1")
                        .prefix("logs1/")
                        .status("Enabled")
                        .transitions(Arrays.asList(
                                LifecycleRuleTransition.newBuilder()
                                        .days(30)
                                        .storageClass("IA")
                                        .isAccessTime(true)
                                        .returnToStdWhenVisit(false)
                                        .build(),
                                LifecycleRuleTransition.newBuilder()
                                        .days(33)
                                        .storageClass("Archive")
                                        .isAccessTime(false)
                                        .returnToStdWhenVisit(true)
                                        .build()))
                        .atimeBase(1631698332L)
                        .build(),
                LifecycleRule.newBuilder()
                        .id("atime transition2")
                        .prefix("logs2/")
                        .status("Enabled")
                        .noncurrentVersionTransitions(Arrays.asList(
                                NoncurrentVersionTransition.newBuilder()
                                        .noncurrentDays(10)
                                        .storageClass("IA")
                                        .isAccessTime(true)
                                        .returnToStdWhenVisit(false)
                                        .build(),
                                NoncurrentVersionTransition.newBuilder()
                                        .noncurrentDays(103)
                                        .storageClass("DeepColdArchive")
                                        .isAccessTime(false)
                                        .returnToStdWhenVisit(true)
                                        .build()))
                        .atimeBase(1631698332L)
                        .build(),
                LifecycleRule.newBuilder()
                        .id("RuleID")
                        .prefix("Prefix")
                        .status("Enabled")
                        .filter(LifecycleRuleFilter.newBuilder()
                                .objectSizeGreaterThan(500L)
                                .objectSizeLessThan(64000L)
                                .nots(Arrays.asList(
                                        LifecycleRuleNot.newBuilder()
                                                .prefix("abc/not1/")
                                                .tag(Tag.newBuilder()
                                                        .key("notkey1")
                                                        .value("notvalue1")
                                                        .build())
                                                .build(),
                                        LifecycleRuleNot.newBuilder()
                                                .prefix("abc/not2/")
                                                .tag(Tag.newBuilder()
                                                        .key("notkey2")
                                                        .value("notvalue2")
                                                        .build())
                                                .build()))
                                .build())
                        .build()
        );

        LifecycleConfiguration lifecycleConfiguration = LifecycleConfiguration.newBuilder()
                .rules(rules)
                .build();

        PutBucketLifecycleRequest request = PutBucketLifecycleRequest.newBuilder()
                .bucket("examplebucket")
                .lifecycleConfiguration(lifecycleConfiguration)
                .build();

        OperationInput input = SerdeBucketLifecycle.fromPutBucketLifecycle(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("lifecycle")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the body content can be serialized without errors
        BinaryData body = input.body().get();
        assertThat(body).isNotNull();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).isNotNull();
        // Check some key elements in the XML
        assertThat(xmlContent).contains("<LifecycleConfiguration>");
        assertThat(xmlContent).contains("delete after one day");
        assertThat(xmlContent).contains("atime transition1");
        assertThat(xmlContent).contains("atime transition2");
        assertThat(xmlContent).contains("RuleID");
        assertThat(xmlContent).contains("2020-06-22T11:42:32.120Z");
        
        // Parse the XML to verify it's well-formed and contains expected structure
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        LifecycleConfiguration value = xmlMapper.readValue(xmlContent, LifecycleConfiguration.class);


        assertThat(value.rules()).hasSize(4);
        assertThat(value.rules().get(0).id()).isEqualTo("delete after one day");
        assertThat(value.rules().get(1).id()).isEqualTo("atime transition1");
        assertThat(value.rules().get(2).id()).isEqualTo("atime transition2");
        assertThat(value.rules().get(3).id()).isEqualTo("RuleID");
        
        // Verify first rule
        LifecycleRule rule0 = value.rules().get(0);
        assertThat(rule0.prefix()).isEqualTo("logs1/");
        assertThat(rule0.status()).isEqualTo("Enabled");
        assertThat(rule0.expiration()).isNotNull();
        assertThat(rule0.expiration().days()).isEqualTo(1);
        assertThat(rule0.expiration().expiredObjectDeleteMarker()).isTrue();
        assertThat(rule0.expiration().createdBeforeDate()).isEqualTo("2020-06-22T11:42:32.120Z");
        assertThat(rule0.abortMultipartUpload()).isNotNull();
        assertThat(rule0.abortMultipartUpload().days()).isEqualTo(30);
        
        // Verify second rule
        LifecycleRule rule1 = value.rules().get(1);
        assertThat(rule1.prefix()).isEqualTo("logs1/");
        assertThat(rule1.status()).isEqualTo("Enabled");
        assertThat(rule1.transitions()).hasSize(2);
        assertThat(rule1.transitions().get(0).storageClass()).isEqualTo("IA");
        assertThat(rule1.transitions().get(0).days()).isEqualTo(30);
        assertThat(rule1.transitions().get(0).isAccessTime()).isTrue();
        assertThat(rule1.transitions().get(0).returnToStdWhenVisit()).isFalse();
        assertThat(rule1.transitions().get(1).storageClass()).isEqualTo("Archive");
        assertThat(rule1.transitions().get(1).days()).isEqualTo(33);
        assertThat(rule1.transitions().get(1).isAccessTime()).isFalse();
        assertThat(rule1.transitions().get(1).returnToStdWhenVisit()).isTrue();
        assertThat(rule1.atimeBase()).isEqualTo(1631698332L);
        
        // Verify third rule
        LifecycleRule rule2 = value.rules().get(2);
        assertThat(rule2.prefix()).isEqualTo("logs2/");
        assertThat(rule2.status()).isEqualTo("Enabled");
        assertThat(rule2.noncurrentVersionTransitions()).hasSize(2);
        assertThat(rule2.noncurrentVersionTransitions().get(0).storageClass()).isEqualTo("IA");
        assertThat(rule2.noncurrentVersionTransitions().get(0).noncurrentDays()).isEqualTo(10);
        assertThat(rule2.noncurrentVersionTransitions().get(0).isAccessTime()).isTrue();
        assertThat(rule2.noncurrentVersionTransitions().get(0).returnToStdWhenVisit()).isFalse();
        assertThat(rule2.noncurrentVersionTransitions().get(1).storageClass()).isEqualTo("DeepColdArchive");
        assertThat(rule2.noncurrentVersionTransitions().get(1).noncurrentDays()).isEqualTo(103);
        assertThat(rule2.noncurrentVersionTransitions().get(1).isAccessTime()).isFalse();
        assertThat(rule2.noncurrentVersionTransitions().get(1).returnToStdWhenVisit()).isTrue();
        assertThat(rule2.atimeBase()).isEqualTo(1631698332L);
        
        // Verify fourth rule (with Filter)
        LifecycleRule rule3 = value.rules().get(3);
        assertThat(rule3.prefix()).isEqualTo("Prefix");
        assertThat(rule3.status()).isEqualTo("Enabled");
        assertThat(rule3.filter()).isNotNull();
        assertThat(rule3.filter().objectSizeGreaterThan()).isEqualTo(500L);
        assertThat(rule3.filter().objectSizeLessThan()).isEqualTo(64000L);
        assertThat(rule3.filter().nots()).hasSize(2);
        
        LifecycleRuleNot not1 = rule3.filter().nots().get(0);
        assertThat(not1.prefix()).isEqualTo("abc/not1/");
        assertThat(not1.tag()).isNotNull();
        assertThat(not1.tag().key()).isEqualTo("notkey1");
        assertThat(not1.tag().value()).isEqualTo("notvalue1");
        
        LifecycleRuleNot not2 = rule3.filter().nots().get(1);
        assertThat(not2.prefix()).isEqualTo("abc/not2/");
        assertThat(not2.tag()).isNotNull();
        assertThat(not2.tag().key()).isEqualTo("notkey2");
        assertThat(not2.tag().value()).isEqualTo("notvalue2");
    }
}