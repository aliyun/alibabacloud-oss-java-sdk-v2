package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClientBucketCorsAsyncTest extends TestBase {

    @Test
    public void testBucketCORS_default() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // putBucketCors
        CORSConfiguration newConfig = CORSConfiguration.newBuilder()
                .corsRules(Arrays.asList(
                        CORSRule.newBuilder()
                                .allowedOrigins(Collections.singletonList("*"))
                                .allowedMethods(Arrays.asList("PUT", "GET"))
                                .allowedHeaders(Collections.singletonList("Authorization"))
                                .build(),
                        CORSRule.newBuilder()
                                .allowedOrigins(Arrays.asList("http://example.com", "http://example.net"))
                                .allowedMethods(Collections.singletonList("GET"))
                                .allowedHeaders(Collections.singletonList("Authorization"))
                                .exposeHeaders(Arrays.asList("x-oss-test", "x-oss-test1"))
                                .maxAgeSeconds(100L)
                                .build()))
                .responseVary(false)
                .build();

        PutBucketCorsResult putResult = client.putBucketCorsAsync(PutBucketCorsRequest.newBuilder()
                .bucket(bucketName)
                .corsConfiguration(newConfig)
                .build()).get();

        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        waitForCacheExpiration(1);

        // getBucketCors
        GetBucketCorsResult updatedResult = client.getBucketCorsAsync(GetBucketCorsRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(updatedResult.corsConfiguration());
        List<CORSRule> rules = updatedResult.corsConfiguration().corsRules();
        Assert.assertEquals(2, rules.size());

        CORSRule firstRule = rules.get(0);
        Assert.assertEquals(1, firstRule.allowedOrigins().size());
        Assert.assertEquals("*", firstRule.allowedOrigins().get(0));
        Assert.assertEquals(2, firstRule.allowedMethods().size());
        Assert.assertTrue(firstRule.allowedMethods().contains("PUT"));
        Assert.assertTrue(firstRule.allowedMethods().contains("GET"));
        Assert.assertEquals(1, firstRule.allowedHeaders().size());
        Assert.assertEquals("Authorization".toLowerCase(), firstRule.allowedHeaders().get(0));

        CORSRule secondRule = rules.get(1);
        Assert.assertEquals(2, secondRule.allowedOrigins().size());
        Assert.assertEquals("http://example.com", secondRule.allowedOrigins().get(0));
        Assert.assertEquals("http://example.net", secondRule.allowedOrigins().get(1));
        Assert.assertEquals(1, secondRule.allowedMethods().size());
        Assert.assertEquals("GET", secondRule.allowedMethods().get(0));
        Assert.assertEquals(1, secondRule.allowedHeaders().size());
        Assert.assertEquals("Authorization".toLowerCase(), secondRule.allowedHeaders().get(0));
        Assert.assertEquals(2, secondRule.exposeHeaders().size());
        Assert.assertEquals("x-oss-test", secondRule.exposeHeaders().get(0));
        Assert.assertEquals("x-oss-test1", secondRule.exposeHeaders().get(1));
        Assert.assertEquals(Long.valueOf(100), secondRule.maxAgeSeconds());
        Assert.assertEquals(Boolean.FALSE, updatedResult.corsConfiguration().responseVary());

        // deleteBucketCors
        DeleteBucketCorsResult deleteResult = client.deleteBucketCorsAsync(DeleteBucketCorsRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }

    @Test
    public void testBucketCors_fail() {

    }

}
