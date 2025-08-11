package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.internal.TestUtils;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.ByteArrayBinaryData;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class ClientObjectTaggingAsyncTest extends TestBase {

    private String genObjectName() {
        long ticks = new Date().getTime() / 1000;
        long val = new Random().nextInt(5000);
        return OJBJECT_NAME_PREFIX + ticks + "-" + val;
    }

    @Test
    public void testObjectTaggingOperations() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = genObjectName() + "-tagging-test";

        // 1. Create an object first
        byte[] content = TestUtils.generateTestData(1024);
        PutObjectResult putResult = client.putObjectAsync(
                PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .body(new ByteArrayBinaryData(content))
                        .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // 2. Put object tagging
        List<Tag> tags = new ArrayList<>();
        tags.add(Tag.newBuilder().key("key1").value("value1").build());
        tags.add(Tag.newBuilder().key("key2").value("value2").build());

        TagSet tagSet = TagSet.newBuilder().tags(tags).build();
        Tagging tagging = Tagging.newBuilder().tagSet(tagSet).build();

        PutObjectTaggingResult putTaggingResult = client.putObjectTaggingAsync(
                PutObjectTaggingRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .tagging(tagging)
                        .build()).get();
        Assert.assertNotNull(putTaggingResult);
        Assert.assertEquals(200, putTaggingResult.statusCode());

        // 3. Get object tagging
        GetObjectTaggingResult getTaggingResult = client.getObjectTaggingAsync(
                GetObjectTaggingRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build()).get();
        Assert.assertNotNull(getTaggingResult);
        Assert.assertEquals(200, getTaggingResult.statusCode());
        Assert.assertNotNull(getTaggingResult.tagging());
        Assert.assertNotNull(getTaggingResult.tagging().tagSet());
        Assert.assertNotNull(getTaggingResult.tagging().tagSet().tags());
        Assert.assertEquals(2, getTaggingResult.tagging().tagSet().tags().size());

        // Verify tag values
        List<Tag> retrievedTags = getTaggingResult.tagging().tagSet().tags();
        for (Tag tag : retrievedTags) {
            if ("key1".equals(tag.key())) {
                Assert.assertEquals("value1", tag.value());
            } else if ("key2".equals(tag.key())) {
                Assert.assertEquals("value2", tag.value());
            } else {
                Assert.fail("Unexpected tag key: " + tag.key());
            }
        }

        // 4. Delete object tagging
        DeleteObjectTaggingResult deleteTaggingResult = client.deleteObjectTaggingAsync(
                DeleteObjectTaggingRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build()).get();
        Assert.assertNotNull(deleteTaggingResult);
        Assert.assertEquals(204, deleteTaggingResult.statusCode());

        // 5. Get object tagging again (should be empty)
        getTaggingResult = client.getObjectTaggingAsync(
                GetObjectTaggingRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build()).get();
        Assert.assertNotNull(getTaggingResult);
        Assert.assertEquals(200, getTaggingResult.statusCode());
        Assert.assertNotNull(getTaggingResult.tagging());
        Assert.assertNotNull(getTaggingResult.tagging().tagSet());
        // TagSet should exist but tags list should be empty or null
        if (getTaggingResult.tagging().tagSet().tags() != null) {
            Assert.assertEquals(0, getTaggingResult.tagging().tagSet().tags().size());
        }

        // 6. Clean up - delete object
        DeleteObjectResult deleteResult = client.deleteObjectAsync(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build()).get();
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }

    //TODO
    @Ignore
    @Test
    public void testObjectTaggingWithVersionId() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = genObjectName() + "-tagging-version-test";

        // 1. Create an object first
        byte[] content = TestUtils.generateTestData(1024);
        PutObjectResult putResult = client.putObjectAsync(
                PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .body(new ByteArrayBinaryData(content))
                        .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());
        String versionId = putResult.versionId();

        // 2. Put object tagging with version id
        List<Tag> tags = new ArrayList<>();
        tags.add(Tag.newBuilder().key("versionKey").value("versionValue").build());

        TagSet tagSet = TagSet.newBuilder().tags(tags).build();
        Tagging tagging = Tagging.newBuilder().tagSet(tagSet).build();

        PutObjectTaggingResult putTaggingResult = client.putObjectTaggingAsync(
                PutObjectTaggingRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .versionId(versionId)
                        .tagging(tagging)
                        .build()).get();
        Assert.assertNotNull(putTaggingResult);
        Assert.assertEquals(200, putTaggingResult.statusCode());

        // 3. Get object tagging with version id
        GetObjectTaggingResult getTaggingResult = client.getObjectTaggingAsync(
                GetObjectTaggingRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .versionId(versionId)
                        .build()).get();
        Assert.assertNotNull(getTaggingResult);
        Assert.assertEquals(200, getTaggingResult.statusCode());
        Assert.assertNotNull(getTaggingResult.tagging());
        Assert.assertNotNull(getTaggingResult.tagging().tagSet());
        Assert.assertNotNull(getTaggingResult.tagging().tagSet().tags());
        Assert.assertEquals(1, getTaggingResult.tagging().tagSet().tags().size());

        Tag retrievedTag = getTaggingResult.tagging().tagSet().tags().get(0);
        Assert.assertEquals("versionKey", retrievedTag.key());
        Assert.assertEquals("versionValue", retrievedTag.value());

        // 4. Delete object tagging with version id
        DeleteObjectTaggingResult deleteTaggingResult = client.deleteObjectTaggingAsync(
                DeleteObjectTaggingRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .versionId(versionId)
                        .build()).get();
        Assert.assertNotNull(deleteTaggingResult);
        Assert.assertEquals(204, deleteTaggingResult.statusCode());

        // 5. Clean up - delete object
        DeleteObjectResult deleteResult = client.deleteObjectAsync(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build()).get();
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }
}
