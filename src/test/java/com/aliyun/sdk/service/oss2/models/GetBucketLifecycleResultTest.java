package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketLifecycle;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketLifecycleResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketLifecycleResult result = GetBucketLifecycleResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        LifecycleConfiguration lifecycleConfiguration = createLifecycleConfiguration();

        GetBucketLifecycleResult result = GetBucketLifecycleResult.newBuilder()
                .headers(headers)
                .innerBody(lifecycleConfiguration)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        assertThat(result.lifecycleConfiguration()).isNotNull();
        assertThat(result.lifecycleConfiguration().rules()).hasSize(4);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        LifecycleConfiguration lifecycleConfiguration = createLifecycleConfiguration();

        GetBucketLifecycleResult original = GetBucketLifecycleResult.newBuilder()
                .headers(headers)
                .innerBody(lifecycleConfiguration)
                .build();

        GetBucketLifecycleResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        assertThat(copy.lifecycleConfiguration()).isNotNull();
        assertThat(copy.lifecycleConfiguration().rules()).hasSize(4);
    }

    @Test
    public void testXmlBuilder() {
        String xml =
                "<LifecycleConfiguration>\n" +
                        "             <Rule>\n" +
                        "                 <ID>delete after one day</ID>\n" +
                        "                 <Prefix>logs1/</Prefix>\n" +
                        "                 <Status>Enabled</Status>\n" +
                        "                 <Expiration>\n" +
                        "                     <CreatedBeforeDate>2020-06-22T11:42:32.000Z</CreatedBeforeDate>\n" +
                        "                     <Days>1</Days>\n" +
                        "                     <ExpiredObjectDeleteMarker>true</ExpiredObjectDeleteMarker>\n" +
                        "                 </Expiration>\n" +
                        "                 <AbortMultipartUpload>\n" +
                        "                     <Days>30</Days>\n" +
                        "                 </AbortMultipartUpload>\n" +
                        "             </Rule>\n" +
                        "             <Rule>\n" +
                        "                 <ID>atime transition1</ID>\n" +
                        "                 <Prefix>logs1/</Prefix>\n" +
                        "                 <Status>Enabled</Status>\n" +
                        "                 <Transition>\n" +
                        "                     <Days>30</Days>\n" +
                        "                     <StorageClass>IA</StorageClass>\n" +
                        "                     <IsAccessTime>true</IsAccessTime>\n" +
                        "                     <ReturnToStdWhenVisit>false</ReturnToStdWhenVisit>\n" +
                        "                 </Transition>\n" +
                        "                 <Transition>\n" +
                        "                     <Days>33</Days>\n" +
                        "                     <StorageClass>Archive</StorageClass>\n" +
                        "                     <IsAccessTime>false</IsAccessTime>\n" +
                        "                     <ReturnToStdWhenVisit>true</ReturnToStdWhenVisit>\n" +
                        "                 </Transition>\n" +
                        "                 <AtimeBase>1631698332</AtimeBase>\n" +
                        "             </Rule>\n" +
                        "             <Rule>\n" +
                        "                 <ID>atime transition2</ID>\n" +
                        "                 <Prefix>logs2/</Prefix>\n" +
                        "                 <Status>Enabled</Status>\n" +
                        "                 <NoncurrentVersionTransition>\n" +
                        "                     <NoncurrentDays>10</NoncurrentDays>\n" +
                        "                     <StorageClass>IA</StorageClass>\n" +
                        "                     <IsAccessTime>true</IsAccessTime>\n" +
                        "                     <ReturnToStdWhenVisit>false</ReturnToStdWhenVisit>\n" +
                        "                 </NoncurrentVersionTransition>\n" +
                        "                <NoncurrentVersionTransition>\n" +
                        "                     <NoncurrentDays>103</NoncurrentDays>\n" +
                        "                     <StorageClass>DeepColdArchive</StorageClass>\n" +
                        "                     <IsAccessTime>false</IsAccessTime>\n" +
                        "                     <ReturnToStdWhenVisit>true</ReturnToStdWhenVisit>\n" +
                        "                 </NoncurrentVersionTransition>\n" +
                        "                 <AtimeBase>1631698332</AtimeBase>\n" +
                        "             </Rule>\n" +
                        "             <Rule>\n" +
                        "                 <ID>RuleID</ID>\n" +
                        "                 <Prefix>Prefix</Prefix>\n" +
                        "                 <Status>Enabled</Status>\n" +
                        "                 <Filter>\n" +
                        "                     <ObjectSizeGreaterThan>500</ObjectSizeGreaterThan>\n" +
                        "                     <ObjectSizeLessThan>64000</ObjectSizeLessThan>\n" +
                        "                     <Not>\n" +
                        "                         <Prefix>abc/not1/</Prefix>\n" +
                        "                         <Tag>\n" +
                        "                             <Key>notkey1</Key>\n" +
                        "                             <Value>notvalue1</Value>\n" +
                        "                         </Tag>\n" +
                        "                     </Not>\n" +
                        "                     <Not>\n" +
                        "                         <Prefix>abc/not2/</Prefix>\n" +
                        "                         <Tag>\n" +
                        "                             <Key>notkey2</Key>\n" +
                        "                             <Value>notvalue2</Value>\n" +
                        "                         </Tag>\n" +
                        "                     </Not>\n" +
                        "                 </Filter>\n" +
                        "             </Rule>\n" +
                        "         </LifecycleConfiguration>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-1234567890abcdefg"))
                .build();
        GetBucketLifecycleResult result = SerdeBucketLifecycle.toGetBucketLifecycle(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");

        LifecycleConfiguration lifecycleConfiguration = result.lifecycleConfiguration();
        assertThat(lifecycleConfiguration).isNotNull();
        assertThat(lifecycleConfiguration.rules()).hasSize(4);

        // Check first rule
        LifecycleRule rule1 = lifecycleConfiguration.rules().get(0);
        assertThat(rule1.id()).isEqualTo("delete after one day");
        assertThat(rule1.prefix()).isEqualTo("logs1/");
        assertThat(rule1.status()).isEqualTo("Enabled");

        // Check expiration
        Expiration expiration = rule1.expiration();
        assertThat(expiration).isNotNull();
        assertThat(expiration.createdBeforeDate()).isEqualTo("2020-06-22T11:42:32.000Z");
        assertThat(expiration.days()).isEqualTo(1);
        assertThat(expiration.expiredObjectDeleteMarker()).isEqualTo(true);

        // Check abort multipart upload
        AbortMultipartUpload abortMultipartUpload = rule1.abortMultipartUpload();
        assertThat(abortMultipartUpload).isNotNull();
        assertThat(abortMultipartUpload.days()).isEqualTo(30);

        // Check second rule
        LifecycleRule rule2 = lifecycleConfiguration.rules().get(1);
        assertThat(rule2.id()).isEqualTo("atime transition1");
        assertThat(rule2.prefix()).isEqualTo("logs1/");
        assertThat(rule2.status()).isEqualTo("Enabled");

        // Check transitions
        List<Transition> transitions = rule2.transitions();
        assertThat(transitions).hasSize(2);

        Transition transition1 = transitions.get(0);
        assertThat(transition1.days()).isEqualTo(30);
        assertThat(transition1.storageClass()).isEqualTo("IA");
        assertThat(transition1.isAccessTime()).isEqualTo(true);
        assertThat(transition1.returnToStdWhenVisit()).isEqualTo(false);

        Transition transition2 = transitions.get(1);
        assertThat(transition2.days()).isEqualTo(33);
        assertThat(transition2.storageClass()).isEqualTo("Archive");
        assertThat(transition2.isAccessTime()).isEqualTo(false);
        assertThat(transition2.returnToStdWhenVisit()).isEqualTo(true);

        // Check atime base
        assertThat(rule2.atimeBase()).isEqualTo(1631698332L);

        // Check third rule
        LifecycleRule rule3 = lifecycleConfiguration.rules().get(2);
        assertThat(rule3.id()).isEqualTo("atime transition2");
        assertThat(rule3.prefix()).isEqualTo("logs2/");
        assertThat(rule3.status()).isEqualTo("Enabled");

        // Check noncurrent version transitions
        List<NoncurrentVersionTransition> noncurrentTransitions = rule3.noncurrentVersionTransitions();
        assertThat(noncurrentTransitions).hasSize(2);

        NoncurrentVersionTransition noncurrentTransition1 = noncurrentTransitions.get(0);
        assertThat(noncurrentTransition1.noncurrentDays()).isEqualTo(10);
        assertThat(noncurrentTransition1.storageClass()).isEqualTo("IA");
        assertThat(noncurrentTransition1.isAccessTime()).isEqualTo(true);
        assertThat(noncurrentTransition1.returnToStdWhenVisit()).isEqualTo(false);

        NoncurrentVersionTransition noncurrentTransition2 = noncurrentTransitions.get(1);
        assertThat(noncurrentTransition2.noncurrentDays()).isEqualTo(103);
        assertThat(noncurrentTransition2.storageClass()).isEqualTo("DeepColdArchive");
        assertThat(noncurrentTransition2.isAccessTime()).isEqualTo(false);
        assertThat(noncurrentTransition2.returnToStdWhenVisit()).isEqualTo(true);

        // Check atime base
        assertThat(rule3.atimeBase()).isEqualTo(1631698332L);

        // Check fourth rule
        LifecycleRule rule4 = lifecycleConfiguration.rules().get(3);
        assertThat(rule4.id()).isEqualTo("RuleID");
        assertThat(rule4.prefix()).isEqualTo("Prefix");
        assertThat(rule4.status()).isEqualTo("Enabled");

        // Check filter
        LifecycleRuleFilter filter = rule4.filter();
        assertThat(filter).isNotNull();
        assertThat(filter.objectSizeGreaterThan()).isEqualTo(500L);
        assertThat(filter.objectSizeLessThan()).isEqualTo(64000L);

        List<LifecycleRuleNot> notList = filter.filterNot();
        assertThat(notList).hasSize(2);

        LifecycleRuleNot not1 = notList.get(0);
        assertThat(not1.prefix()).isEqualTo("abc/not1/");
        Tag tag1 = not1.tag();
        assertThat(tag1).isNotNull();
        assertThat(tag1.key()).isEqualTo("notkey1");
        assertThat(tag1.value()).isEqualTo("notvalue1");

        LifecycleRuleNot not2 = notList.get(1);
        assertThat(not2.prefix()).isEqualTo("abc/not2/");
        Tag tag2 = not2.tag();
        assertThat(tag2).isNotNull();
        assertThat(tag2.key()).isEqualTo("notkey2");
        assertThat(tag2.value()).isEqualTo("notvalue2");
    }

    private LifecycleConfiguration createLifecycleConfiguration() {
        // Create expiration
        Expiration expiration = Expiration.newBuilder()
                .createdBeforeDate("2020-06-22T11:42:32.000Z")
                .days(1)
                .expiredObjectDeleteMarker(true)
                .build();

        // Create abort multipart upload
        AbortMultipartUpload abortMultipartUpload = AbortMultipartUpload.newBuilder()
                .days(30)
                .build();

        // Create transitions
        List<Transition> transitions = Arrays.asList(
                Transition.newBuilder()
                        .days(30)
                        .storageClass("IA")
                        .isAccessTime(true)
                        .returnToStdWhenVisit(false)
                        .build(),
                Transition.newBuilder()
                        .days(33)
                        .storageClass("Archive")
                        .isAccessTime(false)
                        .returnToStdWhenVisit(true)
                        .build()
        );

        // Create noncurrent version transitions
        List<NoncurrentVersionTransition> noncurrentTransitions = Arrays.asList(
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
                        .build()
        );

        // Create filter tags
        Tag tag1 = Tag.newBuilder()
                .key("notkey1")
                .value("notvalue1")
                .build();

        Tag tag2 = Tag.newBuilder()
                .key("notkey2")
                .value("notvalue2")
                .build();

        // Create filter not conditions
        List<LifecycleRuleNot> notList = Arrays.asList(
                LifecycleRuleNot.newBuilder()
                        .prefix("abc/not1/")
                        .tag(tag1)
                        .build(),
                LifecycleRuleNot.newBuilder()
                        .prefix("abc/not2/")
                        .tag(tag2)
                        .build()
        );

        // Create filter
        LifecycleRuleFilter filter = LifecycleRuleFilter.newBuilder()
                .objectSizeGreaterThan(500L)
                .objectSizeLessThan(64000L)
                .filterNot(notList)
                .build();

        // Create rules
        List<LifecycleRule> rules = Arrays.asList(
                LifecycleRule.newBuilder()
                        .id("delete after one day")
                        .prefix("logs1/")
                        .status("Enabled")
                        .expiration(expiration)
                        .abortMultipartUpload(abortMultipartUpload)
                        .build(),
                LifecycleRule.newBuilder()
                        .id("atime transition1")
                        .prefix("logs1/")
                        .status("Enabled")
                        .transitions(transitions)
                        .atimeBase(1631698332L)
                        .build(),
                LifecycleRule.newBuilder()
                        .id("atime transition2")
                        .prefix("logs2/")
                        .status("Enabled")
                        .noncurrentVersionTransitions(noncurrentTransitions)
                        .atimeBase(1631698332L)
                        .build(),
                LifecycleRule.newBuilder()
                        .id("RuleID")
                        .prefix("Prefix")
                        .status("Enabled")
                        .filter(filter)
                        .build()
        );

        return LifecycleConfiguration.newBuilder()
                .rules(rules)
                .build();
    }
}