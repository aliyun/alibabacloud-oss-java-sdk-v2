package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Test;
import java.util.concurrent.CompletableFuture;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientBucketWormTestAsync extends TestBase {

    @Test
    public void testBucketWormCompleteFlowAsync() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();

        // Initiate bucket worm
        InitiateWormConfiguration initiateWormConfiguration = InitiateWormConfiguration.newBuilder()
                .retentionPeriodInDays(30)
                .build();

        InitiateBucketWormRequest initiateRequest = InitiateBucketWormRequest.newBuilder()
                .bucket(bucketName)
                .initiateWormConfiguration(initiateWormConfiguration)
                .build();

        CompletableFuture<InitiateBucketWormResult> initiateFuture = client.initiateBucketWormAsync(initiateRequest);
        InitiateBucketWormResult initiateResult = initiateFuture.get();
        assertThat(initiateResult).isNotNull();
        assertThat(initiateResult.statusCode()).isEqualTo(200);
        String wormId = initiateResult.wormId();
        assertThat(wormId).isNotNull();

        // Get bucket worm to check the configuration
        GetBucketWormRequest getRequest = GetBucketWormRequest.newBuilder()
                .bucket(bucketName)
                .build();

        CompletableFuture<GetBucketWormResult> getFuture = client.getBucketWormAsync(getRequest);
        GetBucketWormResult getResult = getFuture.get();
        assertThat(getResult).isNotNull();
        assertThat(getResult.statusCode()).isEqualTo(200);
        assertThat(getResult.wormConfiguration()).isNotNull();
        assertThat(getResult.wormConfiguration().wormId()).isEqualTo(wormId);

        // Abort the bucket worm (first worm)
        AbortBucketWormRequest abortRequest = AbortBucketWormRequest.newBuilder()
                .bucket(bucketName)
                .build();

        CompletableFuture<AbortBucketWormResult> abortFuture = client.abortBucketWormAsync(abortRequest);
        AbortBucketWormResult abortResult = abortFuture.get();
        assertThat(abortResult).isNotNull();
        assertThat(abortResult.statusCode()).isEqualTo(204);

        // Initiate another bucket worm
        InitiateWormConfiguration initiateWormConfiguration2 = InitiateWormConfiguration.newBuilder()
                .retentionPeriodInDays(45)
                .build();

        InitiateBucketWormRequest initiateRequest2 = InitiateBucketWormRequest.newBuilder()
                .bucket(bucketName)
                .initiateWormConfiguration(initiateWormConfiguration2)
                .build();

        CompletableFuture<InitiateBucketWormResult> initiateFuture2 = client.initiateBucketWormAsync(initiateRequest2);
        InitiateBucketWormResult initiateResult2 = initiateFuture2.get();
        assertThat(initiateResult2).isNotNull();
        assertThat(initiateResult2.statusCode()).isEqualTo(200);
        String wormId2 = initiateResult2.wormId();
        assertThat(wormId2).isNotNull();
        assertThat(wormId2).isNotEqualTo(wormId); // Ensure it's a different worm ID

        // Complete the second bucket worm
        CompleteBucketWormRequest completeRequest = CompleteBucketWormRequest.newBuilder()
                .bucket(bucketName)
                .wormId(wormId2)
                .build();

        CompletableFuture<CompleteBucketWormResult> completeFuture = client.completeBucketWormAsync(completeRequest);
        CompleteBucketWormResult completeResult = completeFuture.get();
        assertThat(completeResult).isNotNull();
        assertThat(completeResult.statusCode()).isEqualTo(200);

        // Get bucket worm again to verify completion
        CompletableFuture<GetBucketWormResult> getFuture2 = client.getBucketWormAsync(getRequest);
        GetBucketWormResult getResult2 = getFuture2.get();
        assertThat(getResult2).isNotNull();
        assertThat(getResult2.statusCode()).isEqualTo(200);
        assertThat(getResult2.wormConfiguration()).isNotNull();
        assertThat(getResult2.wormConfiguration().wormId()).isEqualTo(wormId2);
        assertThat(getResult2.wormConfiguration().state()).isEqualTo("Locked"); // Assuming Locked state after completion

        // Extend the completed bucket worm
        ExtendWormConfiguration extendWormConfiguration = ExtendWormConfiguration.newBuilder()
                .retentionPeriodInDays(60)
                .build();

        ExtendBucketWormRequest extendRequest = ExtendBucketWormRequest.newBuilder()
                .bucket(bucketName)
                .wormId(wormId2)
                .extendWormConfiguration(extendWormConfiguration)
                .build();

        CompletableFuture<ExtendBucketWormResult> extendFuture = client.extendBucketWormAsync(extendRequest);
        ExtendBucketWormResult extendResult = extendFuture.get();
        assertThat(extendResult).isNotNull();
        assertThat(extendResult.statusCode()).isEqualTo(200);

        // Get bucket worm again to verify extension
        CompletableFuture<GetBucketWormResult> getFuture3 = client.getBucketWormAsync(getRequest);
        GetBucketWormResult getResult3 = getFuture3.get();
        assertThat(getResult3).isNotNull();
        assertThat(getResult3.statusCode()).isEqualTo(200);
        assertThat(getResult3.wormConfiguration()).isNotNull();
        assertThat(getResult3.wormConfiguration().wormId()).isEqualTo(wormId2);
        assertThat(getResult3.wormConfiguration().retentionPeriodInDays()).isEqualTo(60);
    }
}