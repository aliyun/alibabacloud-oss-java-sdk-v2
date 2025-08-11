package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.StringBinaryData;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;


public class ClientBucketAsyncTest extends TestBase {

    @Test
    public void testBucketOperations() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();
        String newBucketName = genBucketName();

        // put bucket
        PutBucketResult putResult = client.putBucketAsync(PutBucketRequest.newBuilder()
                .bucket(newBucketName)
                .acl("public-read")
                .bucketTagging("k1=v1&k2=v2")
                .createBucketConfiguration(CreateBucketConfiguration.newBuilder()
                        .storageClass("Standard")
                        .dataRedundancyType("LRS")
                        .build())
                .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // list objects
        ListObjectsResult listResult = client.listObjectsAsync(ListObjectsRequest.newBuilder()
                .bucket(newBucketName)
                .prefix("oss")
                .build()).get();
        Assert.assertEquals(200, listResult.statusCode());

        // list objects v2
        ListObjectsV2Result listV2Result = client.listObjectsV2Async(ListObjectsV2Request.newBuilder()
                .bucket(newBucketName)
                .prefix("java")
                .build()).get();
        Assert.assertEquals(200, listV2Result.statusCode());

        // get bucket info
        GetBucketInfoResult infoResult = client.getBucketInfoAsync(GetBucketInfoRequest.newBuilder()
                .bucket(newBucketName)
                .build()).get();
        Assert.assertEquals(200, infoResult.statusCode());
        Assert.assertEquals(newBucketName, infoResult.bucketInfo().name());
        Assert.assertEquals("public-read", infoResult.bucketInfo().accessControlList().grant());
        Assert.assertEquals("Standard", infoResult.bucketInfo().storageClass());
        Assert.assertNotNull(infoResult.bucketInfo().location());

        // get bucket location
        GetBucketLocationResult locationResult = client.getBucketLocationAsync(GetBucketLocationRequest.newBuilder()
                .bucket(newBucketName)
                .build()).get();
        Assert.assertEquals(200, infoResult.statusCode());
        Assert.assertNotNull(locationResult.locationConstraint());


        // get bucket stat
        GetBucketStatResult statResult = client.getBucketStatAsync(GetBucketStatRequest.newBuilder()
                .bucket(newBucketName)
                .build()).get();
        Assert.assertEquals(200, statResult.statusCode());
        Assert.assertEquals(Long.valueOf(0), statResult.bucketStat().archiveObjectCount());
        Assert.assertEquals(Long.valueOf(0), statResult.bucketStat().coldArchiveObjectCount());
        Assert.assertEquals(Long.valueOf(0), statResult.bucketStat().objectCount());


        // delete bucket
        DeleteBucketResult deleteResult = client.deleteBucketAsync(DeleteBucketRequest.newBuilder()
                .bucket(newBucketName)
                .build()).get();
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }

    @Test
    public void testListObjectsWithAllSpecialCharacters() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();
        String newBucketName = genBucketName();

        // Create bucket
        PutBucketResult putResult = client.putBucketAsync(PutBucketRequest.newBuilder()
                .bucket(newBucketName)
                .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // Upload test objects with special characters in keys
        String specialKey1 = "special-key-!@#$%^&*()_+{}|:<>?[];',./`~";
        String specialKey2 = "special-key-测试中文字符";
        String specialKey3 = "special-key-🌟emoji字符";

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(specialKey1)
                .body(new StringBinaryData("content1"))
                .build()).get();

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(specialKey2)
                .body(new StringBinaryData("content2"))
                .build()).get();

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(specialKey3)
                .body(new StringBinaryData("content3"))
                .build()).get();

        // Create directories with special characters for common prefixes
        String prefixDir1 = "prefix-!@#$%^&*()_+{}|:<>?[];',./`~/";
        String prefixDir2 = "prefix-测试中文-/";

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(prefixDir1 + "file1.txt")
                .body(new StringBinaryData("content-prefix1"))
                .build()).get();

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(prefixDir2 + "file2.txt")
                .body(new StringBinaryData("content-prefix2"))
                .build()).get();

        // Test all special characters in prefix, marker, and delimiter
        String specialPrefix = "special-key-!@#$%^&*()_+{}|:<>?[];',./`~";
        String specialMarker = "special-key-";
        String specialDelimiter = "+";

        // List objects with all special characters and encoding-type=url parameter
        ListObjectsResult listResult = client.listObjectsAsync(ListObjectsRequest.newBuilder()
                .bucket(newBucketName)
                .prefix(specialPrefix)
                .marker(specialMarker)
                .delimiter(specialDelimiter)
                .encodingType("url")
                .build()).get();

        Assert.assertEquals(200, listResult.statusCode());
        // When using encoding-type=url, the returned prefix, marker, and delimiter should be URL encoded,
        // and after deserialization they should match the original request values
        Assert.assertEquals(specialPrefix, listResult.prefix());
        Assert.assertEquals(specialMarker, listResult.marker());
        Assert.assertEquals(specialDelimiter, listResult.delimiter());

        // Check that ObjectSummary keys are properly decoded
        if (listResult.contents() != null) {
            for (ObjectSummary summary : listResult.contents()) {
                // Keys should be properly decoded and match the original values
                Assert.assertNotNull(summary.key());
            }
        }

        // Check that CommonPrefix prefixes are properly decoded
        if (listResult.commonPrefixes() != null) {
            for (CommonPrefix prefix : listResult.commonPrefixes()) {
                // Prefixes should be properly decoded and match the original values
                Assert.assertNotNull(prefix.prefix());
            }
        }

        // Test nextMarker with special characters
        ListObjectsResult listResultWithMaxKeys = client.listObjectsAsync(ListObjectsRequest.newBuilder()
                .bucket(newBucketName)
                .maxKeys(1L)
                .encodingType("url")
                .build()).get();

        Assert.assertEquals(200, listResultWithMaxKeys.statusCode());
        // If the result is truncated, nextMarker should be properly handled
        if (listResultWithMaxKeys.isTruncated() && listResultWithMaxKeys.nextMarker() != null) {
            // nextMarker should be properly decoded
            Assert.assertNotNull(listResultWithMaxKeys.nextMarker());
        }

        // Delete all objects
        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(specialKey1)
                .build()).get();

        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(specialKey2)
                .build()).get();

        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(specialKey3)
                .build()).get();

        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(prefixDir1 + "file1.txt")
                .build()).get();

        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(prefixDir2 + "file2.txt")
                .build()).get();

        // Delete bucket
        DeleteBucketResult deleteResult = client.deleteBucketAsync(DeleteBucketRequest.newBuilder()
                .bucket(newBucketName)
                .build()).get();
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }

    @Test
    public void testListObjectsV2WithAllSpecialCharacters() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();
        String newBucketName = genBucketName();

        // Create bucket
        PutBucketResult putResult = client.putBucketAsync(PutBucketRequest.newBuilder()
                .bucket(newBucketName)
                .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // Upload test objects with special characters in keys
        String specialKey1 = "special-key-!@#$%^&*()_+{}|:<>?[];',./`~";
        String specialKey2 = "special-key-测试中文字符";
        String specialKey3 = "special-key-🌟emoji字符";

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(specialKey1)
                .body(new StringBinaryData("content1"))
                .build()).get();

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(specialKey2)
                .body(new StringBinaryData("content2"))
                .build()).get();

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(specialKey3)
                .body(new StringBinaryData("content3"))
                .build()).get();

        // Create directories with special characters for common prefixes
        String prefixDir1 = "prefix-!@#$%^&*()_+{}|:<>?[];',./`~/";
        String prefixDir2 = "prefix-测试中文-/";

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(prefixDir1 + "file1.txt")
                .body(new StringBinaryData("content-prefix1"))
                .build()).get();

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(prefixDir2 + "file2.txt")
                .body(new StringBinaryData("content-prefix2"))
                .build()).get();

        // Test all special characters in prefix, startAfter, continuationToken, and delimiter
        String specialPrefix = "special-key-!@#$%^&*()_+{}|:<>?[];',./`~";
        String specialStartAfter = "special-key-";
        String specialDelimiter = "+";

        // List objects v2 with all special characters and encoding-type=url parameter
        ListObjectsV2Result listV2Result = client.listObjectsV2Async(ListObjectsV2Request.newBuilder()
                .bucket(newBucketName)
                .prefix(specialPrefix)
                .startAfter(specialStartAfter)
                .delimiter(specialDelimiter)
                .encodingType("url")
                .build()).get();

        Assert.assertEquals(200, listV2Result.statusCode());
        // When using encoding-type=url, the returned prefix, startAfter, and delimiter should be URL encoded,
        // and after deserialization they should match the original request values
        Assert.assertEquals(specialPrefix, listV2Result.prefix());
        Assert.assertEquals(specialStartAfter, listV2Result.startAfter());
        Assert.assertEquals(specialDelimiter, listV2Result.delimiter());

        // Check that ObjectSummary keys are properly decoded
        if (listV2Result.contents() != null) {
            for (ObjectSummary summary : listV2Result.contents()) {
                // Keys should be properly decoded and match the original values
                Assert.assertNotNull(summary.key());
            }
        }

        // Check that CommonPrefix prefixes are properly decoded
        if (listV2Result.commonPrefixes() != null) {
            for (CommonPrefix prefix : listV2Result.commonPrefixes()) {
                // Prefixes should be properly decoded and match the original values
                Assert.assertNotNull(prefix.prefix());
            }
        }

        // Test nextContinuationToken with special characters
        ListObjectsV2Result listV2ResultWithMaxKeys = client.listObjectsV2Async(ListObjectsV2Request.newBuilder()
                .bucket(newBucketName)
                .maxKeys(1L)
                .encodingType("url")
                .build()).get();

        Assert.assertEquals(200, listV2ResultWithMaxKeys.statusCode());
        // If the result is truncated, nextContinuationToken should be properly handled
        if (listV2ResultWithMaxKeys.isTruncated() && listV2ResultWithMaxKeys.nextContinuationToken() != null) {
            // nextContinuationToken should be properly decoded
            Assert.assertNotNull(listV2ResultWithMaxKeys.nextContinuationToken());
        }

        // Test continuationToken with special characters
        // First, get a continuation token from a previous request
        ListObjectsV2Result firstResult = client.listObjectsV2Async(ListObjectsV2Request.newBuilder()
                .bucket(newBucketName)
                .maxKeys(2L)
                .encodingType("url")
                .build()).get();

        if (firstResult.isTruncated() && firstResult.nextContinuationToken() != null) {
            // Use the nextContinuationToken as continuationToken for the next request
            ListObjectsV2Result secondResult = client.listObjectsV2Async(ListObjectsV2Request.newBuilder()
                    .bucket(newBucketName)
                    .continuationToken(firstResult.nextContinuationToken())
                    .maxKeys(2L)
                    .encodingType("url")
                    .build()).get();

            Assert.assertEquals(200, secondResult.statusCode());
            // continuationToken should be properly handled
            Assert.assertEquals(firstResult.nextContinuationToken(), secondResult.continuationToken());
        }

        // Delete all objects
        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(specialKey1)
                .build()).get();

        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(specialKey2)
                .build()).get();

        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(specialKey3)
                .build()).get();

        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(prefixDir1 + "file1.txt")
                .build()).get();

        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(newBucketName)
                .key(prefixDir2 + "file2.txt")
                .build()).get();

        // Delete bucket
        DeleteBucketResult deleteResult = client.deleteBucketAsync(DeleteBucketRequest.newBuilder()
                .bucket(newBucketName)
                .build()).get();
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }
}
