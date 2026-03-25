package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.internal.TestUtils;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.ByteArrayBinaryData;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ClientObjectWormTest extends TestBase {

    private String genObjectName() {
        long ticks = new Date().getTime() / 1000;
        long val = new Random().nextInt(5000);
        return OJBJECT_NAME_PREFIX + ticks + "-" + val;
    }

    private void deleteAllObjects(OSSClient client, String bucketName) {
        System.out.println("\n  Cleaning up bucket: " + bucketName);

        try {
            // List all object versions
            ListObjectVersionsRequest listRequest = ListObjectVersionsRequest.newBuilder()
                    .bucket(bucketName)
                    .build();
            ListObjectVersionsResult listResult = client.listObjectVersions(listRequest);

            int totalDeleted = 0;

            if (!listResult.versions().isEmpty()) {
                List<ObjectIdentifier> objectsToDelete = new ArrayList<>();
                for (ObjectVersion version : listResult.versions()) {
                    objectsToDelete.add(ObjectIdentifier.newBuilder()
                            .key(version.key())
                            .versionId(version.versionId())
                            .build());
                }

                if (!objectsToDelete.isEmpty()) {
                    DeleteMultipleObjectsRequest deleteRequest = DeleteMultipleObjectsRequest.newBuilder()
                            .bucket(bucketName)
                            .delete(Delete.newBuilder()
                                    .objects(objectsToDelete)
                                    .quiet(true)
                                    .build())
                            .build();
                    client.deleteMultipleObjects(deleteRequest);
                    totalDeleted += objectsToDelete.size();
                }
            }

            if (!listResult.deleteMarkers().isEmpty()) {
                List<ObjectIdentifier> markersToDelete = new ArrayList<>();
                for (DeleteMarkerEntry marker : listResult.deleteMarkers()) {
                    markersToDelete.add(ObjectIdentifier.newBuilder()
                            .key(marker.key())
                            .versionId(marker.versionId())
                            .build());
                }

                if (!markersToDelete.isEmpty()) {
                    DeleteMultipleObjectsRequest deleteRequest = DeleteMultipleObjectsRequest.newBuilder()
                            .bucket(bucketName)
                            .delete(Delete.newBuilder()
                                    .objects(markersToDelete)
                                    .quiet(true)
                                    .build())
                            .build();
                    client.deleteMultipleObjects(deleteRequest);
                    totalDeleted += markersToDelete.size();
                }
            }

            if (totalDeleted > 0) {
                System.out.println("   Successfully deleted " + totalDeleted + " objects/versions from " + bucketName);
            } else {
                System.out.println("   No objects found in " + bucketName);
            }

            // Delete the bucket
            try {
                DeleteBucketResult result = client.deleteBucket(DeleteBucketRequest.newBuilder()
                        .bucket(bucketName)
                        .build());
                System.out.println("   Successfully deleted bucket: " + bucketName + " (request_id: " + result.requestId() + ")");
            } catch (Exception e) {
                System.out.println("   Error deleting bucket " + bucketName + ": " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("   Error deleting objects from " + bucketName + ": " + e.getMessage());
        }
    }

    @Test
    public void testObjectRetention() throws Exception {
        OSSClient client = getDefaultClient();
        String bucketName = genObjectName();
        String objectKey = "test-retention-object";

        // Create bucket
        PutBucketResult result = client.putBucket(PutBucketRequest.newBuilder()
                .bucket(bucketName)
                .acl("private")
                .createBucketConfiguration(CreateBucketConfiguration.newBuilder()
                        .storageClass("IA")
                        .build())
                .build());
        Assert.assertEquals(200, result.statusCode());
        Assert.assertEquals(24, result.requestId().length());
        Assert.assertEquals(24, result.headers().get("x-oss-request-id").length());

        // Enable versioning
        PutBucketVersioningResult versioningResult = client.putBucketVersioning(PutBucketVersioningRequest.newBuilder()
                .bucket(bucketName)
                .versioningConfiguration(VersioningConfiguration.newBuilder()
                        .status("Enabled")
                        .build())
                .build());
        Assert.assertEquals(200, versioningResult.statusCode());
        
        Thread.sleep(1000);
                
        // Put bucket object worm configuration
        PutBucketObjectWormConfigurationResult wormConfigResult = client.putBucketObjectWormConfiguration(PutBucketObjectWormConfigurationRequest.newBuilder()
                .bucket(bucketName)
                .objectWormConfiguration(ObjectWormConfiguration.newBuilder()
                        .objectWormEnabled("Enabled")
                        .build())
                .build());
        Assert.assertEquals(200, wormConfigResult.statusCode());
        Assert.assertEquals(24, wormConfigResult.requestId().length());

        // Put object
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectKey)
                .body(new ByteArrayBinaryData("test content".getBytes()))
                .build());
        Assert.assertEquals(200, putResult.statusCode());
        Assert.assertEquals(24, putResult.requestId().length());

        // Put object retention - use local time + 5 seconds, format to UTC
        LocalDateTime localTime = LocalDateTime.now(ZoneId.systemDefault()).plusSeconds(5);
        LocalDateTime utcTime = localTime.atZone(ZoneId.systemDefault()).toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
        String retainUntilDate = utcTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.000'Z'"));

        PutObjectRetentionResult retentionResult = client.putObjectRetention(PutObjectRetentionRequest.newBuilder()
                .bucket(bucketName)
                .key(objectKey)
                .retention(Retention.newBuilder()
                        .mode(ObjectRetentionModeType.COMPLIANCE.name())
                        .retainUntilDate(retainUntilDate)
                        .build())
                .build());
        Assert.assertEquals(200, retentionResult.statusCode());
        Assert.assertEquals(24, retentionResult.requestId().length());
        Assert.assertEquals(24, retentionResult.headers().get("x-oss-request-id").length());

        // Get object retention
        GetObjectRetentionResult getResult = client.getObjectRetention(GetObjectRetentionRequest.newBuilder()
                .bucket(bucketName)
                .key(objectKey)
                .build());
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertEquals(24, getResult.requestId().length());
        Assert.assertEquals(24, getResult.headers().get("x-oss-request-id").length());
        Assert.assertEquals(ObjectRetentionModeType.COMPLIANCE.name(), getResult.retention().mode());
        Assert.assertEquals(retainUntilDate, getResult.retention().retainUntilDate());

        Thread.sleep(6000);

        deleteAllObjects(client, bucketName);
    }

    @Test
    public void testObjectRetentionWithBypass() throws Exception {
        OSSClient client = getDefaultClient();
        String bucketName = genObjectName();
        String objectKey = "test-retention-bypass";

        // Create bucket
        PutBucketResult result = client.putBucket(PutBucketRequest.newBuilder()
                .bucket(bucketName)
                .acl("private")
                .createBucketConfiguration(CreateBucketConfiguration.newBuilder()
                        .storageClass("IA")
                        .build())
                .build());
        Assert.assertEquals(200, result.statusCode());

        // Enable versioning
        PutBucketVersioningResult versioningResult = client.putBucketVersioning(PutBucketVersioningRequest.newBuilder()
                .bucket(bucketName)
                .versioningConfiguration(VersioningConfiguration.newBuilder()
                        .status("Enabled")
                        .build())
                .build());
        Assert.assertEquals(200, versioningResult.statusCode());

        Thread.sleep(1000);

        // Put bucket object worm configuration
        PutBucketObjectWormConfigurationResult wormConfigResult = client.putBucketObjectWormConfiguration(PutBucketObjectWormConfigurationRequest.newBuilder()
                .bucket(bucketName)
                .objectWormConfiguration(ObjectWormConfiguration.newBuilder()
                        .objectWormEnabled("Enabled")
                        .build())
                .build());
        Assert.assertEquals(200, wormConfigResult.statusCode());

        // Put object
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectKey)
                .body(new ByteArrayBinaryData("test content".getBytes()))
                .build());
        Assert.assertEquals(200, putResult.statusCode());

        // Put object retention with GOVERNANCE mode
        LocalDateTime localTime = LocalDateTime.now(ZoneId.systemDefault()).plusSeconds(5);
        LocalDateTime utcTime = localTime.atZone(ZoneId.systemDefault()).toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
        String retainUntilDate = utcTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.000'Z'"));

        PutObjectRetentionResult retentionResult = client.putObjectRetention(PutObjectRetentionRequest.newBuilder()
                .bucket(bucketName)
                .key(objectKey)
                .retention(Retention.newBuilder()
                        .mode(ObjectRetentionModeType.GOVERNANCE.name())
                        .retainUntilDate(retainUntilDate)
                        .build())
                .build());
        Assert.assertEquals(200, retentionResult.statusCode());

        // Get object retention
        GetObjectRetentionResult getResult = client.getObjectRetention(GetObjectRetentionRequest.newBuilder()
                .bucket(bucketName)
                .key(objectKey)
                .build());
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertEquals(ObjectRetentionModeType.GOVERNANCE.name(), getResult.retention().mode());

        Thread.sleep(6000);

        deleteAllObjects(client, bucketName);
    }

    @Test
    public void testObjectLegalHoldOperations() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-legalhold-test";

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
        PutBucketObjectWormConfigurationResult wormConfigResult = client.putBucketObjectWormConfiguration(
                PutBucketObjectWormConfigurationRequest.newBuilder()
                        .bucket(bucketName)
                        .objectWormConfiguration(ObjectWormConfiguration.newBuilder()
                                .objectWormEnabled("Enabled")
                                .build())
                        .build());
        Assert.assertEquals(200, wormConfigResult.statusCode());


        // 3. Create an object
        byte[] content = TestUtils.generateTestData(1024);
        PutObjectResult putResult = client.putObject(
                PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .body(new ByteArrayBinaryData(content))
                        .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // 4. Put object legal hold (ON)
        LegalHold legalHoldOn = LegalHold.newBuilder()
                .status(ObjectLegalHoldStatusType.ON.name())
                .build();

        PutObjectLegalHoldResult putLegalHoldResult = client.putObjectLegalHold(
                PutObjectLegalHoldRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .legalHold(legalHoldOn)
                        .build());
        Assert.assertNotNull(putLegalHoldResult);
        Assert.assertEquals(200, putLegalHoldResult.statusCode());

        // 3. Get object legal hold
        GetObjectLegalHoldResult getLegalHoldResult = client.getObjectLegalHold(
                GetObjectLegalHoldRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(getLegalHoldResult);
        Assert.assertEquals(200, getLegalHoldResult.statusCode());
        Assert.assertNotNull(getLegalHoldResult.legalHold());
        Assert.assertEquals(ObjectLegalHoldStatusType.ON.name(), getLegalHoldResult.legalHold().status());

        // 4. Put object legal hold (OFF)
        LegalHold legalHoldOff = LegalHold.newBuilder()
                .status(ObjectLegalHoldStatusType.OFF.name())
                .build();

        putLegalHoldResult = client.putObjectLegalHold(
                PutObjectLegalHoldRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .legalHold(legalHoldOff)
                        .build());
        Assert.assertNotNull(putLegalHoldResult);
        Assert.assertEquals(200, putLegalHoldResult.statusCode());

        // 5. Get object legal hold again
        getLegalHoldResult = client.getObjectLegalHold(
                GetObjectLegalHoldRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(getLegalHoldResult);
        Assert.assertEquals(200, getLegalHoldResult.statusCode());
        Assert.assertNotNull(getLegalHoldResult.legalHold());
        Assert.assertEquals(ObjectLegalHoldStatusType.OFF.name(), getLegalHoldResult.legalHold().status());

        // 6. Clean up - delete object
        DeleteObjectResult deleteResult = client.deleteObject(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }
}
