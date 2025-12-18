package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientBucketOverwriteConfigTest extends TestBase {

    @Test
    public void testBucketOverwriteConfig_default() throws Exception {

        try (OSSClient client = getOssClient()) {

            // putBucketOverwriteConfig
            List<String> principals = Arrays.asList(
                    "11111",
                    "22222"
            );

            OverwritePrincipals overwritePrincipals = OverwritePrincipals.newBuilder()
                    .principals(principals)
                    .build();

            OverwriteRule rule = OverwriteRule.newBuilder()
                    .id("forbid-write-rule1")
                    .action("forbid")
                    .prefix("a/")
                    .suffix(".txt")
                    .principals(overwritePrincipals)
                    .build();

            OverwriteConfiguration overwriteConfiguration = OverwriteConfiguration.newBuilder()
                    .rules(Arrays.asList(rule))
                    .build();

            PutBucketOverwriteConfigRequest putRequest = PutBucketOverwriteConfigRequest.newBuilder()
                    .bucket(bucketName)
                    .overwriteConfiguration(overwriteConfiguration)
                    .build();

            PutBucketOverwriteConfigResult putResult = client.putBucketOverwriteConfig(putRequest);

            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            waitForCacheExpiration(5);

            // getBucketOverwriteConfig
            GetBucketOverwriteConfigRequest getRequest = GetBucketOverwriteConfigRequest.newBuilder()
                    .bucket(bucketName)
                    .build();

            GetBucketOverwriteConfigResult getResult = client.getBucketOverwriteConfig(getRequest);

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());

            OverwriteConfiguration resultConfiguration = getResult.overwriteConfiguration();
            assertThat(resultConfiguration).isNotNull();
            assertThat(resultConfiguration.rules()).isNotNull();
            assertThat(resultConfiguration.rules()).hasSize(1);

            OverwriteRule resultRule = resultConfiguration.rules().get(0);
            assertThat(resultRule.id()).isEqualTo("forbid-write-rule1");
            assertThat(resultRule.action()).isEqualTo("forbid");
            assertThat(resultRule.prefix()).isEqualTo("a/");
            assertThat(resultRule.suffix()).isEqualTo(".txt");

            OverwritePrincipals resultPrincipals = resultRule.principals();
            assertThat(resultPrincipals).isNotNull();
            assertThat(resultPrincipals.principals()).isNotNull();
            assertThat(resultPrincipals.principals()).hasSize(2);

            assertThat(resultPrincipals.principals().get(0)).isEqualTo("11111");
            assertThat(resultPrincipals.principals().get(1)).isEqualTo("22222");


            // deleteBucketOverwriteConfig
            DeleteBucketOverwriteConfigRequest deleteRequest = DeleteBucketOverwriteConfigRequest.newBuilder()
                    .bucket(bucketName)
                    .build();
            DeleteBucketOverwriteConfigResult deleteResult = client.deleteBucketOverwriteConfig(deleteRequest);

            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());
        }
    }
}