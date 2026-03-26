package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientBucketObjectWormConfigurationTest extends TestBase {

    @Test
    public void testBucketObjectWormConfigurationOperations() throws Exception {
        OSSClient client = getDefaultClient();

        // 1. Enable versioning first
        PutBucketVersioningResult versioningResult = client.putBucketVersioning(PutBucketVersioningRequest.newBuilder()
                .bucket(bucketName)
                .versioningConfiguration(VersioningConfiguration.newBuilder()
                        .status("Enabled")
                        .build())
                .build());
        Assert.assertEquals(200, versioningResult.statusCode());

        Thread.sleep(1000);

        // 2. Put bucket object worm configuration
        ObjectWormConfigurationDefaultRetention defaultRetention = ObjectWormConfigurationDefaultRetention.newBuilder()
                .mode(ObjectWormConfigurationModeType.COMPLIANCE.name())
                .days(1)
                .build();

        ObjectWormConfigurationRule rule = ObjectWormConfigurationRule.newBuilder()
                .defaultRetention(defaultRetention)
                .build();

        ObjectWormConfiguration objectWormConfiguration = ObjectWormConfiguration.newBuilder()
                .objectWormEnabled("Enabled")
                .rule(rule)
                .build();

        PutBucketObjectWormConfigurationResult putResult = client.putBucketObjectWormConfiguration(
                PutBucketObjectWormConfigurationRequest.newBuilder()
                        .bucket(bucketName)
                        .objectWormConfiguration(objectWormConfiguration)
                        .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // 3. Get bucket object worm configuration
        GetBucketObjectWormConfigurationResult getResult = client.getBucketObjectWormConfiguration(
                GetBucketObjectWormConfigurationRequest.newBuilder()
                        .bucket(bucketName)
                        .build());
        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.objectWormConfiguration());
        Assert.assertEquals("Enabled", getResult.objectWormConfiguration().objectWormEnabled());
        Assert.assertNotNull(getResult.objectWormConfiguration().rule());
        Assert.assertNotNull(getResult.objectWormConfiguration().rule().defaultRetention());
        Assert.assertEquals(ObjectWormConfigurationModeType.COMPLIANCE.name(), getResult.objectWormConfiguration().rule().defaultRetention().mode());
        Assert.assertEquals(Integer.valueOf(1), getResult.objectWormConfiguration().rule().defaultRetention().days());

    }
}
