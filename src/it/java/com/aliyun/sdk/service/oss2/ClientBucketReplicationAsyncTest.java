package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;
import static com.aliyun.sdk.service.oss2.internal.ClientImplMockTest.findCause;

public class ClientBucketReplicationAsyncTest extends TestBase {

    @Test
    public void testBucketReplication_default() throws Exception {
        String destRegion = "cn-shenzhen";

        CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
        OSSAsyncClient ossClient = OSSAsyncClient.newBuilder()
                .region(destRegion)
                .endpoint("http://oss-"+destRegion+".aliyuncs.com")
                .credentialsProvider(provider)
                .build();

        // Create a new bucket for replication destination
        String destBucketName = genBucketName();
        ossClient.putBucketAsync(PutBucketRequest.newBuilder()
                .bucket(destBucketName)
                .createBucketConfiguration(CreateBucketConfiguration.newBuilder()
                        .storageClass("Standard")
                        .build())
                .build()).get();

        try (OSSAsyncClient client = getDefaultAsyncClient()) {

            try {

                // 1. putBucketReplication with RTC, PrefixSet, SyncRole
                String ruleId = "test-replication-rule";

                // Create RTC configuration
                RTC rtc = RTC.newBuilder()
                        .status("disabled")
                        .build();

                // Create PrefixSet
                ReplicationPrefixSet prefixSet = ReplicationPrefixSet.newBuilder()
                        .prefixs(Arrays.asList("source1", "video"))
                        .build();

                ReplicationRule rule = ReplicationRule.newBuilder()
                        .id(ruleId)
                        .rtc(rtc)
                        .prefixSet(prefixSet)
                        .action("PUT")
                        .destination(
                                ReplicationDestination.newBuilder()
                                        .bucket(destBucketName)
                                        .location("oss-"+destRegion)
                                        .transferType("internal")
                                        .build()
                        )
                        .status("enabled")
                        .historicalObjectReplication("enabled")
                        .syncRole("ramosstest")
                        .build();

                ReplicationConfiguration replicationConfiguration = ReplicationConfiguration.newBuilder()
                        .rules(Collections.singletonList(rule))
                        .build();

                PutBucketReplicationResult putResult = client.putBucketReplicationAsync(PutBucketReplicationRequest.newBuilder()
                        .bucket(bucketName)
                        .replicationConfiguration(replicationConfiguration)
                        .build()).get();

                Assert.assertNotNull(putResult);
                Assert.assertEquals(200, putResult.statusCode());

                waitForCacheExpiration(5);

                // 2. getBucketReplication
                GetBucketReplicationResult getResult = client.getBucketReplicationAsync(GetBucketReplicationRequest.newBuilder()
                        .bucket(bucketName)
                        .build()).get();

                Assert.assertNotNull(getResult);
                Assert.assertEquals(200, getResult.statusCode());
                Assert.assertNotNull(getResult.replicationConfiguration());
                Assert.assertNotNull(getResult.replicationConfiguration().rules());
                Assert.assertEquals(1, getResult.replicationConfiguration().rules().size());

                ReplicationRule retrievedRule = getResult.replicationConfiguration().rules().get(0);
                Assert.assertEquals(ruleId, retrievedRule.id());
                Assert.assertEquals("starting", retrievedRule.status());
                Assert.assertEquals("enabled", retrievedRule.historicalObjectReplication());
                Assert.assertEquals("PUT", retrievedRule.action());
                Assert.assertEquals("ramosstest", retrievedRule.syncRole());
                Assert.assertNotNull(retrievedRule.destination());
                Assert.assertEquals("oss-"+destRegion, retrievedRule.destination().location());

                // Assert PrefixSet
                Assert.assertNotNull(retrievedRule.prefixSet());
                Assert.assertNotNull(retrievedRule.prefixSet().prefixs());
                Assert.assertEquals(2, retrievedRule.prefixSet().prefixs().size());
                Assert.assertTrue(retrievedRule.prefixSet().prefixs().contains("source1"));
                Assert.assertTrue(retrievedRule.prefixSet().prefixs().contains("video"));


                // 3. getBucketReplicationProgress and assert the replication progress
                GetBucketReplicationProgressResult progressResult = client.getBucketReplicationProgressAsync(
                        GetBucketReplicationProgressRequest.newBuilder()
                                .bucket(bucketName)
                                .ruleId(ruleId)
                                .build()).get();

                Assert.assertNotNull(progressResult);
                Assert.assertEquals(200, progressResult.statusCode());
                Assert.assertNotNull(progressResult.replicationProgress());
                Assert.assertNotNull(progressResult.replicationProgress().rules());
                Assert.assertFalse(progressResult.replicationProgress().rules().isEmpty());

                ReplicationProgressRule progressRule = progressResult.replicationProgress().rules().get(0);
                Assert.assertEquals(ruleId, progressRule.id());
                Assert.assertEquals("starting", progressRule.status());
                Assert.assertEquals("PUT", progressRule.action());
                Assert.assertEquals("enabled", progressRule.historicalObjectReplication());

                // Assert Destination
                Assert.assertNotNull(progressRule.destination());
                Assert.assertEquals(destBucketName, progressRule.destination().bucket());
                Assert.assertEquals("oss-"+destRegion, progressRule.destination().location());

                // Assert PrefixSet
                Assert.assertNotNull(progressRule.prefixSet());
                Assert.assertNotNull(progressRule.prefixSet().prefixs());
                Assert.assertEquals(2, progressRule.prefixSet().prefixs().size());
                Assert.assertTrue(progressRule.prefixSet().prefixs().contains("source1"));
                Assert.assertTrue(progressRule.prefixSet().prefixs().contains("video"));

                // 4. getBucketReplicationLocation
                GetBucketReplicationLocationResult locationResult = client.getBucketReplicationLocationAsync(GetBucketReplicationLocationRequest.newBuilder()
                        .bucket(bucketName)
                        .build()).get();

                Assert.assertNotNull(locationResult);
                Assert.assertEquals(200, locationResult.statusCode());
                Assert.assertNotNull(locationResult.replicationLocation());
                Assert.assertNotNull(locationResult.replicationLocation().locations());
                Assert.assertTrue(locationResult.replicationLocation().locations().size() > 0);

                // 5. putBucketRtc
                RtcConfiguration rtcConfiguration = RtcConfiguration.newBuilder()
                        .rtc(rtc)
                        .id(ruleId)
                        .build();

                PutBucketRtcResult putRtcResult = client.putBucketRtcAsync(PutBucketRtcRequest.newBuilder()
                        .bucket(bucketName)
                        .rtcConfiguration(rtcConfiguration)
                        .build()).get();

                Assert.assertNotNull(putRtcResult);
                Assert.assertEquals(200, putRtcResult.statusCode());

                // 6. deleteBucketReplication
                ReplicationRules replicationRules = ReplicationRules.newBuilder()
                        .id(ruleId)
                        .build();

                DeleteBucketReplicationResult deleteResult = client.deleteBucketReplicationAsync(DeleteBucketReplicationRequest.newBuilder()
                        .bucket(bucketName)
                        .replicationRules(replicationRules)
                        .build()).get();

                Assert.assertNotNull(deleteResult);
                Assert.assertEquals(200, deleteResult.statusCode());

            } finally {
                // Clean up the destination bucket
                ossClient.deleteBucketAsync(DeleteBucketRequest.newBuilder()
                        .bucket(destBucketName)
                        .build()).get();
            }
        }
    }

    @Test
    public void testBucketReplicationProgress() throws Exception {
        try {
            CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
            OSSAsyncClient asyncClient = OSSAsyncClient.newBuilder()
                    .region(region())
                    .endpoint(endpoint())
                    .credentialsProvider(provider)
                    .build();

            GetBucketReplicationProgressResult result = asyncClient.getBucketReplicationProgressAsync(GetBucketReplicationProgressRequest.newBuilder()
                    .bucket(bucketName)
                    .ruleId("non-existent-rule")
                    .build()).get();

            Assert.assertNotNull(result);
        } catch (Exception e) {
            ServiceException serr = findCause(e, ServiceException.class);
            Assert.assertEquals(404, serr.statusCode());
            Assert.assertEquals("NoSuchReplicationRule", serr.errorCode());
            Assert.assertEquals("The BucketReplicationRule you specified does not exist", serr.errorMessage());
        }
    }
}