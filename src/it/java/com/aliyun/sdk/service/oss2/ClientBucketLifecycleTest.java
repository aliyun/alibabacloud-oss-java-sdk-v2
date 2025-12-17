package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClientBucketLifecycleTest extends TestBase {

    @Test
    public void testBucketLifecycleOperations() {
        OSSClient client = getDefaultClient();
        String ruleId = "test-rule-" + System.currentTimeMillis();
        
        try {
            // Put bucket lifecycle
            LifecycleRule rule = LifecycleRule.newBuilder()
                    .id(ruleId)
                    .status("Enabled")
                    .prefix("test/")
                    .expiration(Expiration.newBuilder().days(30).build())
                    .build();

            LifecycleConfiguration lifecycleConfiguration = LifecycleConfiguration.newBuilder()
                    .rules(Arrays.asList(rule))
                    .build();

            PutBucketLifecycleResult putResult = client.putBucketLifecycle(PutBucketLifecycleRequest.newBuilder()
                    .bucket(bucketName)
                    .lifecycleConfiguration(lifecycleConfiguration)
                    .build());
            
            Assert.assertNotNull("Put bucket lifecycle result should not be null", putResult);
            Assert.assertEquals("Put bucket lifecycle should return status code 200", 200, putResult.statusCode());

            // Get bucket lifecycle
            GetBucketLifecycleResult getResult = client.getBucketLifecycle(GetBucketLifecycleRequest.newBuilder()
                    .bucket(bucketName)
                    .build());
            
            Assert.assertNotNull("Get bucket lifecycle result should not be null", getResult);
            Assert.assertEquals("Get bucket lifecycle should return status code 200", 200, getResult.statusCode());
            Assert.assertNotNull("Get bucket lifecycle result should have a request ID", getResult.requestId());
            
            LifecycleConfiguration resultLifecycleConfiguration = getResult.lifecycleConfiguration();
            Assert.assertNotNull("Lifecycle configuration should not be null", resultLifecycleConfiguration);
            List<LifecycleRule> rules = resultLifecycleConfiguration.rules();
            Assert.assertNotNull("Lifecycle rules should not be null", rules);
            Assert.assertFalse("Lifecycle rules should not be empty", rules.isEmpty());
            
            LifecycleRule resultRule = rules.get(0);
            Assert.assertEquals("Rule ID should match", ruleId, resultRule.id());
            Assert.assertEquals("Rule status should match", "Enabled", resultRule.status());
            Assert.assertEquals("Rule prefix should match", "test/", resultRule.prefix());
            Assert.assertNotNull("Rule expiration should not be null", resultRule.expiration());
            Assert.assertEquals("Rule expiration days should match", Integer.valueOf(30), resultRule.expiration().days());

            // Delete bucket lifecycle
            DeleteBucketLifecycleResult deleteResult = client.deleteBucketLifecycle(DeleteBucketLifecycleRequest.newBuilder()
                    .bucket(bucketName)
                    .build());
            
            Assert.assertNotNull("Delete bucket lifecycle result should not be null", deleteResult);
            Assert.assertEquals("Delete bucket lifecycle should return status code 204", 204, deleteResult.statusCode());
            Assert.assertNotNull("Delete bucket lifecycle result should have a request ID", deleteResult.requestId());
        } catch (Exception e) {
            Assert.fail("Bucket lifecycle operations failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testBucketLifecycleAdvancedOperations() {
        OSSClient client = getDefaultClient();
        String baseRuleId = "advanced-rule-" + System.currentTimeMillis();
        
        try {
            // Create multiple lifecycle rules including advanced features
            // 1. Expiration rule
            LifecycleRule expirationRule = LifecycleRule.newBuilder()
                    .id(baseRuleId + "-expiration")
                    .status("Enabled")
                    .prefix("logs/")
                    .expiration(Expiration.newBuilder().days(30).build())
                    .build();
            
            // 2. Transition rule
            LifecycleRule transitionRule = LifecycleRule.newBuilder()
                    .id(baseRuleId + "-transition")
                    .status("Enabled")
                    .prefix("data/")
                    .transitions(Arrays.asList(
                        Transition.newBuilder()
                            .days(30)
                            .storageClass("IA")
                            .build(),
                        Transition.newBuilder()
                            .days(90)
                            .storageClass("Archive")
                            .build()
                    ))
                    .build();
            
            // 3. Abort multipart upload rule
            LifecycleRule abortMultipartRule = LifecycleRule.newBuilder()
                    .id(baseRuleId + "-abort")
                    .prefix("multipart/")
                    .status("Enabled")
                    .abortMultipartUpload(AbortMultipartUpload.newBuilder().days(5).build())
                    .build();
            
            // 4. Non-current version expiration rule
            LifecycleRule noncurrentVersionRule = LifecycleRule.newBuilder()
                    .id(baseRuleId + "-noncurrent")
                    .status("Enabled")
                    .prefix("backup/")
                    .noncurrentVersionExpiration(NoncurrentVersionExpiration.newBuilder().noncurrentDays(10).build())
                    .build();
            
            // 5. Non-current version transition rule
            LifecycleRule noncurrentVersionTransitionRule = LifecycleRule.newBuilder()
                    .id(baseRuleId + "-noncurrent-transition")
                    .status("Enabled")
                    .prefix("archive/")
                    .noncurrentVersionTransitions(Arrays.asList(
                        NoncurrentVersionTransition.newBuilder()
                            .noncurrentDays(30)
                            .storageClass("IA")
                            .build(),
                        NoncurrentVersionTransition.newBuilder()
                            .noncurrentDays(90)
                            .storageClass("Archive")
                            .build()
                    ))
                    .build();
            
            // 6. Tag filter rule
            LifecycleRule tagFilterRule = LifecycleRule.newBuilder()
                    .id(baseRuleId + "-tag-filter")
                    .status("Enabled")
                    .prefix("tag/")
                    .filter(LifecycleRuleFilter.newBuilder()
                            .objectSizeGreaterThan(500L)
                            .objectSizeLessThan(64000L)
                            .filterNot(Arrays.asList(
                                    LifecycleRuleNot.newBuilder()
                                            .prefix("tag/not1/")
                                            .tag(Tag.newBuilder()
                                                    .key("notkey1")
                                                    .value("notvalue1")
                                                    .build())
                                            .build()))
                            .build())
                    .expiration(Expiration.newBuilder().days(10).build())
                    .build();
            
            // 7. Rule with exclusion
            LifecycleRule ruleWithExclusion = LifecycleRule.newBuilder()
                    .id(baseRuleId + "-exclusion")
                    .status("Enabled")
                    .prefix("exclusion/")
                    .transitions(Collections.singletonList(
                        Transition.newBuilder()
                            .days(30)
                            .storageClass("IA")
                            .build()))
                    .filter(LifecycleRuleFilter.newBuilder()
                            .objectSizeGreaterThan(500L)
                            .objectSizeLessThan(64000L)
                            .filterNot(Arrays.asList(
                                    LifecycleRuleNot.newBuilder()
                                            .prefix("exclusion/not1/")
                                            .tag(Tag.newBuilder()
                                                    .key("notkey2")
                                                    .value("notvalue2")
                                                    .build())
                                            .build()))
                            .build())
                    .build();

            List<LifecycleRule> originalRules = Arrays.asList(
                    expirationRule,
                    transitionRule,
                    abortMultipartRule,
                    noncurrentVersionRule,
                    noncurrentVersionTransitionRule,
                    tagFilterRule,
                    ruleWithExclusion
            );

            LifecycleConfiguration lifecycleConfiguration = LifecycleConfiguration.newBuilder()
                    .rules(originalRules)
                    .build();

            // Put advanced bucket lifecycle
            PutBucketLifecycleResult putResult = client.putBucketLifecycle(PutBucketLifecycleRequest.newBuilder()
                    .bucket(bucketName)
                    .lifecycleConfiguration(lifecycleConfiguration)
                    .build());
            
            Assert.assertNotNull("Put advanced bucket lifecycle result should not be null", putResult);
            Assert.assertEquals("Put advanced bucket lifecycle should return status code 200", 200, putResult.statusCode());
            Assert.assertNotNull("Put advanced bucket lifecycle result should have a request ID", putResult.requestId());

            // Get and verify advanced bucket lifecycle
            GetBucketLifecycleResult getResult = client.getBucketLifecycle(GetBucketLifecycleRequest.newBuilder()
                    .bucket(bucketName)
                    .build());
            
            Assert.assertNotNull("Get advanced bucket lifecycle result should not be null", getResult);
            Assert.assertEquals("Get advanced bucket lifecycle should return status code 200", 200, getResult.statusCode());
            Assert.assertNotNull("Get advanced bucket lifecycle result should have a request ID", getResult.requestId());
            
            LifecycleConfiguration resultLifecycleConfiguration = getResult.lifecycleConfiguration();
            Assert.assertNotNull("Advanced lifecycle configuration should not be null", resultLifecycleConfiguration);
            List<LifecycleRule> rules = resultLifecycleConfiguration.rules();
            Assert.assertNotNull("Advanced lifecycle rules should not be null", rules);
            Assert.assertFalse("Advanced lifecycle rules should not be empty", rules.isEmpty());
            Assert.assertEquals("Should have 7 rules", 7, rules.size());
            
            // Verify each rule matches the original
            for (int i = 0; i < rules.size(); i++) {
                LifecycleRule originalRule = originalRules.get(i);
                LifecycleRule resultRule = rules.get(i);
                
                Assert.assertEquals("Rule ID should match", originalRule.id(), resultRule.id());
                Assert.assertEquals("Rule status should match", originalRule.status(), resultRule.status());
                Assert.assertEquals("Rule prefix should match", originalRule.prefix(), resultRule.prefix());
                
                // Verify expiration if present
                if (originalRule.expiration() != null) {
                    Assert.assertNotNull("Rule expiration should not be null", resultRule.expiration());
                    Assert.assertEquals("Rule expiration days should match", 
                            originalRule.expiration().days(), resultRule.expiration().days());
                }
                
                // Verify transitions if present
                if (originalRule.transitions() != null) {
                    Assert.assertNotNull("Rule transitions should not be null", resultRule.transitions());
                    Assert.assertEquals("Rule transitions count should match", 
                            originalRule.transitions().size(), resultRule.transitions().size());
                    
                    for (int j = 0; j < originalRule.transitions().size(); j++) {
                        Transition originalTransition = originalRule.transitions().get(j);
                        Transition resultTransition = resultRule.transitions().get(j);
                        
                        Assert.assertEquals("Transition days should match", 
                                originalTransition.days(), resultTransition.days());
                        Assert.assertEquals("Transition storage class should match", 
                                originalTransition.storageClass(), resultTransition.storageClass());
                    }
                }
                
                // Verify abort multipart upload if present
                if (originalRule.abortMultipartUpload() != null) {
                    Assert.assertNotNull("Rule abort multipart upload should not be null", resultRule.abortMultipartUpload());
                    Assert.assertEquals("Abort multipart upload days should match", 
                            originalRule.abortMultipartUpload().days(), resultRule.abortMultipartUpload().days());
                }
                
                // Verify non-current version expiration if present
                if (originalRule.noncurrentVersionExpiration() != null) {
                    Assert.assertNotNull("Rule non-current version expiration should not be null", resultRule.noncurrentVersionExpiration());
                    Assert.assertEquals("Non-current version expiration days should match", 
                            originalRule.noncurrentVersionExpiration().noncurrentDays(), 
                            resultRule.noncurrentVersionExpiration().noncurrentDays());
                }
                
                // Verify non-current version transitions if present
                if (originalRule.noncurrentVersionTransitions() != null) {
                    Assert.assertNotNull("Rule non-current version transitions should not be null", resultRule.noncurrentVersionTransitions());
                    Assert.assertEquals("Rule non-current version transitions count should match", 
                            originalRule.noncurrentVersionTransitions().size(), resultRule.noncurrentVersionTransitions().size());
                    
                    for (int j = 0; j < originalRule.noncurrentVersionTransitions().size(); j++) {
                        NoncurrentVersionTransition originalTransition = originalRule.noncurrentVersionTransitions().get(j);
                        NoncurrentVersionTransition resultTransition = resultRule.noncurrentVersionTransitions().get(j);
                        
                        Assert.assertEquals("Non-current version transition days should match", 
                                originalTransition.noncurrentDays(), resultTransition.noncurrentDays());
                        Assert.assertEquals("Non-current version transition storage class should match", 
                                originalTransition.storageClass(), resultTransition.storageClass());
                    }
                }
            }
            
            // Delete bucket lifecycle
            DeleteBucketLifecycleResult deleteResult = client.deleteBucketLifecycle(DeleteBucketLifecycleRequest.newBuilder()
                    .bucket(bucketName)
                    .build());
            
            Assert.assertNotNull("Delete advanced bucket lifecycle result should not be null", deleteResult);
            Assert.assertEquals("Delete advanced bucket lifecycle should return status code 204", 204, deleteResult.statusCode());
            Assert.assertNotNull("Delete advanced bucket lifecycle result should have a request ID", deleteResult.requestId());
        } catch (Exception e) {
            Assert.fail("Advanced bucket lifecycle operations failed: " + e.getMessage());
        }
    }
}