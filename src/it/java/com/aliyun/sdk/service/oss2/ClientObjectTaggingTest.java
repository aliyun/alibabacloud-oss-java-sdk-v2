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

public class ClientObjectTaggingTest extends TestBase {

    private String genObjectName() {
        long ticks = new Date().getTime() / 1000;
        long val = new Random().nextInt(5000);
        return OJBJECT_NAME_PREFIX + ticks + "-" + val;
    }

    @Test
    public void testObjectTaggingOperations() {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-tagging-test";

        // 1. Create an object first
        byte[] content = TestUtils.generateTestData(1024);
        PutObjectResult putResult = client.putObject(
                PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .body(new ByteArrayBinaryData(content))
                        .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // 2. Put object tagging
        List<Tag> tags = new ArrayList<>();
        tags.add(Tag.newBuilder().key("key1").value("value1").build());
        tags.add(Tag.newBuilder().key("key2").value("value2").build());

        TagSet tagSet = TagSet.newBuilder().tags(tags).build();
        Tagging tagging = Tagging.newBuilder().tagSet(tagSet).build();

        PutObjectTaggingResult putTaggingResult = client.putObjectTagging(
                PutObjectTaggingRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .tagging(tagging)
                        .build());
        Assert.assertNotNull(putTaggingResult);
        Assert.assertEquals(200, putTaggingResult.statusCode());

        // 3. Get object tagging
        GetObjectTaggingResult getTaggingResult = client.getObjectTagging(
                GetObjectTaggingRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
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
        DeleteObjectTaggingResult deleteTaggingResult = client.deleteObjectTagging(
                DeleteObjectTaggingRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(deleteTaggingResult);
        Assert.assertEquals(204, deleteTaggingResult.statusCode());

        // 5. Get object tagging again (should be empty)
        getTaggingResult = client.getObjectTagging(
                GetObjectTaggingRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(getTaggingResult);
        Assert.assertEquals(200, getTaggingResult.statusCode());
        Assert.assertNotNull(getTaggingResult.tagging());
        Assert.assertNotNull(getTaggingResult.tagging().tagSet());
        // TagSet should exist but tags list should be empty or null
        if (getTaggingResult.tagging().tagSet().tags() != null) {
            Assert.assertEquals(0, getTaggingResult.tagging().tagSet().tags().size());
        }

        // 6. Clean up - delete object
        DeleteObjectResult deleteResult = client.deleteObject(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }

    //TODO
    @Test
    public void testObjectTaggingWithVersionId() {
        OSSClient client = getDefaultClient();
        String bucketNameVersion = bucketName + "-versions";
        String objectName = genObjectName() + "-tagging-version-test";

        // Test putBucketVersioning with Enabled status
        client.putBucket(PutBucketRequest.newBuilder()
                .bucket(bucketNameVersion)
                .build());

        client.putBucketVersioning(PutBucketVersioningRequest.newBuilder()
                .bucket(bucketNameVersion)
                .versioningConfiguration(VersioningConfiguration.newBuilder()
                        .status("Enabled")
                        .build())
                .build());

        // 1. Create an object first
        byte[] content = TestUtils.generateTestData(1024);
        PutObjectResult putResult = client.putObject(
                PutObjectRequest.newBuilder()
                        .bucket(bucketNameVersion)
                        .key(objectName)
                        .body(new ByteArrayBinaryData(content))
                        .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());
        String versionId = putResult.versionId();

        // 2. Put object tagging with version id
        List<Tag> tags = new ArrayList<>();
        tags.add(Tag.newBuilder().key("versionKey").value("versionValue").build());

        TagSet tagSet = TagSet.newBuilder().tags(tags).build();
        Tagging tagging = Tagging.newBuilder().tagSet(tagSet).build();

        PutObjectTaggingResult putTaggingResult = client.putObjectTagging(
                PutObjectTaggingRequest.newBuilder()
                        .bucket(bucketNameVersion)
                        .key(objectName)
                        .versionId(versionId)
                        .tagging(tagging)
                        .build());
        Assert.assertNotNull(putTaggingResult);
        Assert.assertEquals(200, putTaggingResult.statusCode());

        // 3. Get object tagging with version id
        GetObjectTaggingResult getTaggingResult = client.getObjectTagging(
                GetObjectTaggingRequest.newBuilder()
                        .bucket(bucketNameVersion)
                        .key(objectName)
                        .versionId(versionId)
                        .build());
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
        DeleteObjectTaggingResult deleteTaggingResult = client.deleteObjectTagging(
                DeleteObjectTaggingRequest.newBuilder()
                        .bucket(bucketNameVersion)
                        .key(objectName)
                        .versionId(versionId)
                        .build());
        Assert.assertNotNull(deleteTaggingResult);
        Assert.assertEquals(204, deleteTaggingResult.statusCode());
    }
}
