package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClientBucketRefererAsyncTest extends TestBase {

    @Test
    public void testBucketReferer_default() throws ExecutionException, InterruptedException {

        OSSAsyncClient client = getDefaultAsyncClient();

        // putBucketReferer
        List<String> referers = Arrays.asList(
                "http://www.aliyun.com",
                "https://www.aliyun.com",
                "http://www.*.com",
                "https://www.?.aliyuncs.com"
        );

        RefererList refererList = RefererList.newBuilder()
                .referers(referers)
                .build();

        List<String> blackListReferers = Arrays.asList(
                "http://www.refuse.com",
                "https://*.hack.com",
                "http://ban.*.com",
                "https://www.?.deny.com"
        );

        RefererBlacklist refererBlacklist = RefererBlacklist.newBuilder()
                .referers(blackListReferers)
                .build();

        RefererConfiguration refererConfiguration = RefererConfiguration.newBuilder()
                .allowEmptyReferer(false)
                .allowTruncateQueryString(true)
                .truncatePath(true)
                .refererList(refererList)
                .refererBlacklist(refererBlacklist)
                .build();

        PutBucketRefererRequest putRequest = PutBucketRefererRequest.newBuilder()
                .bucket(bucketName)
                .refererConfiguration(refererConfiguration)
                .build();

        PutBucketRefererResult putResult = client.putBucketRefererAsync(putRequest).get();

        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        waitForCacheExpiration(5);

        // getBucketReferer
        GetBucketRefererRequest getRequest = GetBucketRefererRequest.newBuilder()
                .bucket(bucketName)
                .build();

        GetBucketRefererResult getResult = client.getBucketRefererAsync(getRequest).get();

        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());

        RefererConfiguration resultConfiguration = getResult.refererConfiguration();
        Assert.assertNotNull(resultConfiguration);
        Assert.assertEquals(false, resultConfiguration.allowEmptyReferer());
        Assert.assertEquals(true, resultConfiguration.allowTruncateQueryString());
        Assert.assertEquals(true, resultConfiguration.truncatePath());

        RefererList resultRefererList = resultConfiguration.refererList();
        Assert.assertNotNull(resultRefererList);
        Assert.assertEquals(referers, resultRefererList.referers());

        RefererBlacklist resultRefererBlacklist = resultConfiguration.refererBlacklist();
        Assert.assertNotNull(resultRefererBlacklist);
        Assert.assertEquals(blackListReferers, resultRefererBlacklist.referers());
    }
}