package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class ClientBucketObjectWormConfigurationAsyncTest extends TestBase {

    @Test
    public void testBucketObjectWormConfigurationAsyncOperations() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();

        // 1. Enable versioning first
        PutBucketVersioningResult versioningResult = client.putBucketVersioningAsync(PutBucketVersioningRequest.newBuilder()
                .bucket(bucketName)
                .versioningConfiguration(VersioningConfiguration.newBuilder()
                        .status("Enabled")
                        .build())
                .build()).get();
        Assert.assertEquals(200, versioningResult.statusCode());

        Thread.sleep(1000);

        // 2. Put bucket object worm configuration
        ObjectWormConfigurationDefaultRetention defaultRetention = ObjectWormConfigurationDefaultRetention.newBuilder()
                .mode(ObjectWormConfigurationModeType.COMPLIANCE.name())
                .days(30)
                .build();

        ObjectWormConfigurationRule rule = ObjectWormConfigurationRule.newBuilder()
                .defaultRetention(defaultRetention)
                .build();

        ObjectWormConfiguration objectWormConfiguration = ObjectWormConfiguration.newBuilder()
                .objectWormEnabled("Enabled")
                .rule(rule)
                .build();

        PutBucketObjectWormConfigurationResult putResult = client.putBucketObjectWormConfigurationAsync(
                PutBucketObjectWormConfigurationRequest.newBuilder()
                        .bucket(bucketName)
                        .objectWormConfiguration(objectWormConfiguration)
                        .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // 3. Get bucket object worm configuration
        GetBucketObjectWormConfigurationResult getResult = client.getBucketObjectWormConfigurationAsync(
                GetBucketObjectWormConfigurationRequest.newBuilder()
                        .bucket(bucketName)
                        .build()).get();
        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.objectWormConfiguration());
        Assert.assertEquals("Enabled", getResult.objectWormConfiguration().objectWormEnabled());
        Assert.assertNotNull(getResult.objectWormConfiguration().rule());
        Assert.assertNotNull(getResult.objectWormConfiguration().rule().defaultRetention());
        Assert.assertEquals(ObjectWormConfigurationModeType.COMPLIANCE.name(), getResult.objectWormConfiguration().rule().defaultRetention().mode());
        Assert.assertEquals(Integer.valueOf(30), getResult.objectWormConfiguration().rule().defaultRetention().days());

    }
}
