package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

public class ClientBucketTagsTest extends TestBase {

    @Test
    public void testBucketTagsOperations() throws Exception {
        try (OSSClient client = getOssClient()) {
            
            // Create tags
            List<Tag> tags = Arrays.asList(
                    Tag.newBuilder().key("key1").value("value1").build(),
                    Tag.newBuilder().key("key2").value("value2").build()
            );
            
            Tagging tagging = Tagging.newBuilder()
                    .tagSet(TagSet.newBuilder().tags(tags).build())
                    .build();

            // putBucketTags
            PutBucketTagsResult putResult = client.putBucketTags(PutBucketTagsRequest.newBuilder()
                    .bucket(bucketName)
                    .tagging(tagging)
                    .build());

            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            waitForCacheExpiration(1);

            // getBucketTags
            GetBucketTagsResult getResult = client.getBucketTags(GetBucketTagsRequest.newBuilder()
                    .bucket(bucketName)
                    .build());

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
            DeleteBucketTagsResult deleteResult = client.deleteBucketTags(DeleteBucketTagsRequest.newBuilder()
                    .bucket(bucketName)
                    .build());

            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());
            
            waitForCacheExpiration(1);
            
            // verify tags are deleted
            GetBucketTagsResult getResultAfterDelete = client.getBucketTags(GetBucketTagsRequest.newBuilder()
                    .bucket(bucketName)
                    .build());
            
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
}