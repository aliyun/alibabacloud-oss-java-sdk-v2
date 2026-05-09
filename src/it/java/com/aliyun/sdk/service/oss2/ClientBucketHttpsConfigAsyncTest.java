package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class ClientBucketHttpsConfigAsyncTest extends TestBase {

    @Test
    public void testBucketHttpsConfig_default() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // putBucketHttpsConfig
        HttpsConfiguration newConfig = HttpsConfiguration.newBuilder()
                .tLS(TLS.newBuilder()
                        .enable(true)
                        .tLSVersions(Arrays.asList("TLSv1.2", "TLSv1.3"))
                        .build())
                .cipherSuite(CipherSuite.newBuilder()
                        .enable(true)
                        .strongCipherSuite(true)
                        .build())
                .build();

        PutBucketHttpsConfigResult putResult = client.putBucketHttpsConfigAsync(PutBucketHttpsConfigRequest.newBuilder()
                .bucket(bucketName)
                .httpsConfiguration(newConfig)
                .build()).get();

        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        waitForCacheExpiration(1);

        // getBucketHttpsConfig
        GetBucketHttpsConfigResult updatedResult = client.getBucketHttpsConfigAsync(GetBucketHttpsConfigRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(updatedResult.httpsConfiguration());
        HttpsConfiguration updatedConfig = updatedResult.httpsConfiguration();

        TLS tls = updatedConfig.tLS();
        Assert.assertNotNull(tls);
        Assert.assertEquals(Boolean.TRUE, tls.enable());
        Assert.assertEquals(2, tls.tLSVersions().size());
        Assert.assertEquals("TLSv1.2", tls.tLSVersions().get(0));
        Assert.assertEquals("TLSv1.3", tls.tLSVersions().get(1));

        CipherSuite cipherSuite = updatedConfig.cipherSuite();
        Assert.assertNotNull(cipherSuite);
        Assert.assertEquals(Boolean.TRUE, cipherSuite.enable());
        Assert.assertEquals(Boolean.TRUE, cipherSuite.strongCipherSuite());
    }


    @Test
    public void testBucketHttpsConfig_fail() {

    }

}
