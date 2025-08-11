package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.exceptions.ServiceError;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transport.StringBinaryData;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static com.aliyun.sdk.service.oss2.internal.ClientImplMockTest.findCause;

public class ClientBucketVersioningAsyncTest extends TestBase {

    @Test
    public void testBucketVersioningOperations() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // Test putBucketVersioning with Enabled status
        PutBucketVersioningResult putResult = client.putBucketVersioningAsync(PutBucketVersioningRequest.newBuilder()
                .bucket(bucketName)
                .versioningConfiguration(VersioningConfiguration.newBuilder()
                        .status("Enabled")
                        .build())
                .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        waitForCacheExpiration(1);

        // Test getBucketVersioning to check if it's enabled
        GetBucketVersioningResult getResult = client.getBucketVersioningAsync(GetBucketVersioningRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();
        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.versioningConfiguration());
        Assert.assertEquals("Enabled", getResult.versioningConfiguration().status());

        // Test putBucketVersioning with Suspended status
        putResult = client.putBucketVersioningAsync(PutBucketVersioningRequest.newBuilder()
                .bucket(bucketName)
                .versioningConfiguration(VersioningConfiguration.newBuilder()
                        .status("Suspended")
                        .build())
                .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        waitForCacheExpiration(1);

        // Test getBucketVersioning to check if it's suspended
        getResult = client.getBucketVersioningAsync(GetBucketVersioningRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();
        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.versioningConfiguration());
        Assert.assertEquals("Suspended", getResult.versioningConfiguration().status());
    }

    @Test
    public void testListObjectVersions() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // Enable versioning
        client.putBucketVersioningAsync(PutBucketVersioningRequest.newBuilder()
                .bucket(bucketName)
                .versioningConfiguration(VersioningConfiguration.newBuilder()
                        .status("Enabled")
                        .build())
                .build()).get();

        // Put some objects to create versions
        String objectName = "test-versioning-object";
        PutObjectResult putResult1 = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromString("content v1"))
                .build()).get();

        PutObjectResult putResult2 = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromString("content v2"))
                .build()).get();

        // Delete object to create a delete marker
        DeleteObjectResult deleteResult = client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build()).get();

        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());

        // List object versions to get version IDs
        ListObjectVersionsResult listResult = client.listObjectVersionsAsync(ListObjectVersionsRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(listResult);
        Assert.assertEquals(200, listResult.statusCode());
        Assert.assertNotNull(listResult.versions());
        Assert.assertTrue(listResult.versions().size() == 2);
        Assert.assertNotNull(listResult.deleteMarkers());
        Assert.assertTrue(listResult.deleteMarkers().size() == 1);

        // Get version IDs
        String versionId1 = null;
        String versionId2 = null;

        for (ObjectVersion version : listResult.versions()) {
            if (objectName.equals(version.key()) && versionId1 == null) {
                versionId1 = version.versionId();
            } else if (objectName.equals(version.key()) && versionId2 == null) {
                versionId2 = version.versionId();
            }
        }

        // Delete specific version permanently (bypass delete marker)
        if (versionId1 != null) {
            DeleteObjectResult deleteVersionResult = client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .versionId(versionId1)
                    .build()).get();

            Assert.assertNotNull(deleteVersionResult);
            Assert.assertEquals(204, deleteVersionResult.statusCode());
            // Verify that versionId is returned in the response
            Assert.assertNotNull(deleteVersionResult.versionId());
        }

        // List object versions again to verify the specific version was deleted
        ListObjectVersionsResult listResultAfterDelete = client.listObjectVersionsAsync(ListObjectVersionsRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();
        Assert.assertNotNull(listResultAfterDelete);
        Assert.assertEquals(200, listResultAfterDelete.statusCode());

        // Check that versions belong to our object
        boolean found = false;
        for (ObjectVersion version : listResultAfterDelete.versions()) {
            if (objectName.equals(version.key())) {
                found = true;
                break;
            }
        }

        // At least one version should still exist (version 2)
        Assert.assertTrue(found);

        // Check that delete markers belong to our object
        boolean deleteMarkerFound = false;
        String deleteMarkerVersionId = null;
        for (DeleteMarkerEntry deleteMarker : listResultAfterDelete.deleteMarkers()) {
            if (objectName.equals(deleteMarker.key())) {
                deleteMarkerFound = true;
                deleteMarkerVersionId = deleteMarker.versionId();
                break;
            }
        }
        Assert.assertTrue(deleteMarkerFound);

        if (versionId2 != null) {
            DeleteObjectResult deleteVersionResult = client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .versionId(versionId2)
                    .build()).get();

            Assert.assertNotNull(deleteVersionResult);
            Assert.assertEquals(204, deleteVersionResult.statusCode());
            // Verify that versionId is returned in the response
            Assert.assertNotNull(deleteVersionResult.versionId());
        }

        DeleteObjectResult deleteMarkerResult = client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .versionId(deleteMarkerVersionId)
                .build()).get();

        Assert.assertNotNull(deleteMarkerResult);
        Assert.assertEquals(204, deleteMarkerResult.statusCode());
        // Verify that versionId is returned in the response
        Assert.assertTrue(deleteMarkerResult.deleteMarker());
        Assert.assertNotNull(deleteMarkerResult.versionId());
    }

    @Test
    public void testBucketVersioningWithInvalidBucket() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();
        String invalidBucketName = "non-existent-bucket-12345";

        // Test putBucketVersioning with non-existent bucket
        try {
            client.putBucketVersioningAsync(PutBucketVersioningRequest.newBuilder()
                    .bucket(invalidBucketName)
                    .versioningConfiguration(VersioningConfiguration.newBuilder()
                            .status("Enabled")
                            .build())
                    .build()).get();
            Assert.fail("Expected exception not thrown");
        } catch (Exception ec) {
            ServiceError serr = findCause(ec, ServiceError.class);
            Assert.assertEquals(404, serr.statusCode());
        }

        // Test getBucketVersioning with non-existent bucket
        try {
            client.getBucketVersioningAsync(GetBucketVersioningRequest.newBuilder()
                    .bucket(invalidBucketName)
                    .build()).get();
            Assert.fail("Expected exception not thrown");
        } catch (Exception ec) {
            ServiceError serr = findCause(ec, ServiceError.class);
            Assert.assertEquals(404, serr.statusCode());
        }

        // Test listObjectVersions with non-existent bucket
        try {
            client.listObjectVersionsAsync(ListObjectVersionsRequest.newBuilder()
                    .bucket(invalidBucketName)
                    .build()).get();
            Assert.fail("Expected exception not thrown");
        } catch (Exception ec) {
            ServiceError serr = findCause(ec, ServiceError.class);
            Assert.assertEquals(404, serr.statusCode());
        }
    }

    @Test
    public void testListObjectVersionsWithSpecialCharacters() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // Enable versioning
        client.putBucketVersioningAsync(PutBucketVersioningRequest.newBuilder()
                .bucket(bucketName)
                .versioningConfiguration(VersioningConfiguration.newBuilder()
                        .status("Enabled")
                        .build())
                .build()).get();

        waitForCacheExpiration(1);

        // Upload test objects with special characters in keys
        String specialKey1 = "special-key-!@#$%^&*()_+{}|:<>?[];',./`~";
        String specialKey2 = "special-key-测试中文字符";
        String specialKey3 = "special-key-🌟emoji字符";

        // Create multiple versions of objects with special characters
        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(specialKey1)
                .body(new StringBinaryData("content1 v1"))
                .build()).get();

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(specialKey1)
                .body(new StringBinaryData("content1 v2"))
                .build()).get();

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(specialKey2)
                .body(new StringBinaryData("content2 v1"))
                .build()).get();

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(specialKey2)
                .body(new StringBinaryData("content2 v2"))
                .build()).get();

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(specialKey3)
                .body(new StringBinaryData("content3 v1"))
                .build()).get();

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(specialKey3)
                .body(new StringBinaryData("content3 v2"))
                .build()).get();

        // Create directories with special characters for common prefixes
        String prefixDir1 = "prefix-!@#$%^&*()_+{}|:<>?[];',./`~/";
        String prefixDir2 = "prefix-测试中文-/";

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(prefixDir1 + "file1.txt")
                .body(new StringBinaryData("content-prefix1 v1"))
                .build()).get();

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(prefixDir1 + "file1.txt")
                .body(new StringBinaryData("content-prefix1 v2"))
                .build()).get();

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(prefixDir2 + "file2.txt")
                .body(new StringBinaryData("content-prefix2 v1"))
                .build()).get();

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(prefixDir2 + "file2.txt")
                .body(new StringBinaryData("content-prefix2 v2"))
                .build()).get();

        waitForCacheExpiration(5);

        // Test all special characters in prefix, keyMarker, and delimiter
        String specialPrefix = "special-key-!@#$%^&*()_+{}|:<>?[];',./`~";
        String specialKeyMarker = "special-key-";
        String specialDelimiter = "+";

        // List object versions with all special characters and encoding-type=url parameter
        ListObjectVersionsResult listResult = client.listObjectVersionsAsync(ListObjectVersionsRequest.newBuilder()
                .bucket(bucketName)
                .prefix(specialPrefix)
                .keyMarker(specialKeyMarker)
                .delimiter(specialDelimiter)
                .encodingType("url")
                .build()).get();

        Assert.assertEquals(200, listResult.statusCode());
        // When using encoding-type=url, the returned prefix, keyMarker, and delimiter should be URL encoded,
        // and after deserialization they should match the original request values
        Assert.assertEquals(specialPrefix, listResult.prefix());
        Assert.assertEquals(specialKeyMarker, listResult.keyMarker());
        Assert.assertEquals(specialDelimiter, listResult.delimiter());

        // Check that ObjectVersionType keys are properly decoded
        Assert.assertEquals(2, listResult.versions().size());
        for (ObjectVersion version : listResult.versions()) {
            // Keys should be properly decoded and match the original values
            Assert.assertNotNull(version.key());
            Assert.assertEquals(specialPrefix, version.key());
        }


        // Check that DeleteMarkerType keys are properly decoded
        if (listResult.deleteMarkers() != null) {
            for (DeleteMarkerEntry marker : listResult.deleteMarkers()) {
                // Keys should be properly decoded and match the original values
                Assert.assertNotNull(marker.key());
            }
        }

        // Check that CommonPrefix prefixes are properly decoded
        if (listResult.commonPrefixes() != null) {
            for (CommonPrefix prefix : listResult.commonPrefixes()) {
                // Prefixes should be properly decoded and match the original values
                Assert.assertNotNull(prefix.prefix());
            }
        }


        String specialDelimiterWithDiagonal = "/";
        // List object versions with all special characters and encoding-type=url parameter
        ListObjectVersionsResult listResultWithDiagonal = client.listObjectVersionsAsync(ListObjectVersionsRequest.newBuilder()
                .bucket(bucketName)
                .prefix(specialPrefix)
                .keyMarker(specialKeyMarker)
                .delimiter(specialDelimiterWithDiagonal)
                .encodingType("url")
                .build()).get();

        Assert.assertEquals(200, listResultWithDiagonal.statusCode());
        // When using encoding-type=url, the returned prefix, keyMarker, and delimiter should be URL encoded,
        // and after deserialization they should match the original request values
        Assert.assertEquals(specialPrefix, listResultWithDiagonal.prefix());
        Assert.assertEquals(specialKeyMarker, listResultWithDiagonal.keyMarker());
        Assert.assertEquals(specialDelimiterWithDiagonal, listResultWithDiagonal.delimiter());

        for (CommonPrefix prefix : listResultWithDiagonal.commonPrefixes()) {
            // Prefixes should be properly decoded and match the original values
            Assert.assertNotNull(prefix.prefix());
            Assert.assertTrue(
                    specialPrefix.equals(prefix.prefix())
            );
        }


        String prefixDirKey1 = prefixDir1 + "file1.txt";
        String prefixDirKey2 = prefixDir1 + "file2.txt";

        // Test nextKeyMarker with special characters
        ListObjectVersionsResult listResultWithMaxKeys = client.listObjectVersionsAsync(ListObjectVersionsRequest.newBuilder()
                .bucket(bucketName)
                .maxKeys(1L)
                .encodingType("url")
                .build()).get();

        Assert.assertEquals(200, listResultWithMaxKeys.statusCode());
        // If the result is truncated, nextKeyMarker should be properly handled
        if (listResultWithMaxKeys.isTruncated() && listResultWithMaxKeys.nextKeyMarker() != null) {
            // nextKeyMarker should be properly decoded
            Assert.assertNotNull(listResultWithMaxKeys.nextKeyMarker());
            Assert.assertTrue(
                    specialKey1.equals(listResultWithMaxKeys.nextKeyMarker()) ||
                            specialKey2.equals(listResultWithMaxKeys.nextKeyMarker()) ||
                            specialKey3.equals(listResultWithMaxKeys.nextKeyMarker()) ||
                            prefixDirKey1.equals(listResultWithMaxKeys.nextKeyMarker()) ||
                            prefixDirKey2.equals(listResultWithMaxKeys.nextKeyMarker())
            );
        }

        // Delete some objects to create delete markers
        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(specialKey1)
                .build()).get();

        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(specialKey2)
                .build()).get();

        // List versions again to check delete markers
        ListObjectVersionsResult listResultWithDeleteMarkers = client.listObjectVersionsAsync(ListObjectVersionsRequest.newBuilder()
                .bucket(bucketName)
                .encodingType("url")
                .build()).get();

        Assert.assertEquals(200, listResultWithDeleteMarkers.statusCode());

        for (DeleteMarkerEntry marker : listResultWithDeleteMarkers.deleteMarkers()) {
            // Keys should be properly decoded and match the original values
            Assert.assertNotNull(marker.key());
            Assert.assertTrue(
                    specialKey1.equals(marker.key()) ||
                            specialKey2.equals(marker.key())
            );
        }

        // Should have delete markers
        Assert.assertTrue(listResultWithDeleteMarkers.deleteMarkers().size() == 2);
        Assert.assertTrue(listResultWithDeleteMarkers.versions().size() == 10);


        // Delete all objects by version ID
        ListObjectVersionsResult allVersions = client.listObjectVersionsAsync(ListObjectVersionsRequest.newBuilder()
                .bucket(bucketName)
                .encodingType("url")
                .build()).get();

        Assert.assertEquals(200, allVersions.statusCode());

        // Delete all versions explicitly by version ID
        if (allVersions.versions() != null) {
            for (ObjectVersion version : allVersions.versions()) {
                DeleteObjectResult deleteResult = client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(version.key())
                        .versionId(version.versionId())
                        .build()).get();
                Assert.assertEquals(204, deleteResult.statusCode());
                Assert.assertEquals(version.versionId(), deleteResult.versionId());
            }
        }

        // Delete all delete markers explicitly by version ID
        if (allVersions.deleteMarkers() != null) {
            for (DeleteMarkerEntry marker : allVersions.deleteMarkers()) {
                DeleteObjectResult deleteResult = client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(marker.key())
                        .versionId(marker.versionId())
                        .build()).get();
                Assert.assertEquals(204, deleteResult.statusCode());
                Assert.assertEquals(marker.versionId(), deleteResult.versionId());
            }
        }

        // Verify that all versions and delete markers have been deleted
        ListObjectVersionsResult finalVersions = client.listObjectVersionsAsync(ListObjectVersionsRequest.newBuilder()
                .bucket(bucketName)
                .encodingType("url")
                .build()).get();

        Assert.assertEquals(200, finalVersions.statusCode());
        Assert.assertTrue(finalVersions.versions().isEmpty());
        Assert.assertTrue(finalVersions.deleteMarkers().isEmpty());
    }
}
