package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClientBucketTagsAsyncTest extends TestBase {

    @Test
    public void testBucketTagsOperations() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // Create tags
        List<Tag> tags = Arrays.asList(
                Tag.newBuilder().key("key1").value("value1").build(),
                Tag.newBuilder().key("key2").value("value2").build()
        );

        Tagging tagging = Tagging.newBuilder()
                .tagSet(TagSet.newBuilder().tags(tags).build())
                .build();

        // putBucketTags
        PutBucketTagsResult putResult = client.putBucketTagsAsync(PutBucketTagsRequest.newBuilder()
                .bucket(bucketName)
                .tagging(tagging)
                .build()).get();

        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        waitForCacheExpiration(1);

        // getBucketTags
        GetBucketTagsResult getResult = client.getBucketTagsAsync(GetBucketTagsRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.tagging());
        Assert.assertNotNull(getResult.tagging().tagSet());
        Assert.assertNotNull(getResult.tagging().tagSet().tags());
        Assert.assertEquals(2, getResult.tagging().tagSet().tags().size());

        List<Tag> resultTags = getResult.tagging().tagSet().tags();
        Assert.assertTrue(resultTags.stream().anyMatch(tag -> "key1".equals(tag.key()) && "value1".equals(tag.value())));
        Assert.assertTrue(resultTags.stream().anyMatch(tag -> "key2".equals(tag.key()) && "value2".equals(tag.value())));

        // deleteBucketTags
        DeleteBucketTagsResult deleteResult = client.deleteBucketTagsAsync(DeleteBucketTagsRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());

        waitForCacheExpiration(1);

        // verify tags are deleted
        GetBucketTagsResult getResultAfterDelete = client.getBucketTagsAsync(GetBucketTagsRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(getResultAfterDelete);
        Assert.assertEquals(200, getResultAfterDelete.statusCode());
        // After deletion, the tagging should be null or empty
        if (getResultAfterDelete.tagging() != null &&
            getResultAfterDelete.tagging().tagSet() != null &&
            getResultAfterDelete.tagging().tagSet().tags() != null) {
            Assert.assertEquals(0, getResultAfterDelete.tagging().tagSet().tags().size());
        }
    }
}