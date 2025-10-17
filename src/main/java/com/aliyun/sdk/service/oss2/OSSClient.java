package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.exceptions.OperationException;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.paginator.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.IOUtils;

import java.io.*;
import java.nio.file.Path;

/**
 * A client for accessing OSS synchronously.
 * This can be created using the static {@link #newBuilder()} method.
 */
public interface OSSClient extends AutoCloseable, Presignable {

    static OSSClientBuilder newBuilder() {
        return new DefaultOSSClientBuilder();
    }

    // common api
    //-----------------------------------------------------------------------
    default OperationOutput invokeOperation(OperationInput input, OperationOptions opts) {
        throw new UnsupportedOperationException();
    }

    // service api

    /**
     * Queries all buckets that are owned by a requester.
     *
     * @param request A {@link ListBucketsRequest} for ListBuckets operation.
     * @return A {@link ListBucketsResult} for ListBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    default ListBucketsResult listBuckets(ListBucketsRequest request) {
        return listBuckets(request, OperationOptions.defaults());
    }

    /**
     * Queries all buckets that are owned by a requester.
     *
     * @param request A {@link ListBucketsRequest} for ListBuckets operation.
     * @param options The operation options.
     * @return A {@link ListBucketsResult} for ListBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    default ListBucketsResult listBuckets(ListBucketsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------

    // regions api

    /**
     * Queries the endpoints of all supported regions or the endpoints of a specific region.
     *
     * @param request A {@link DescribeRegionsRequest} for DescribeRegions operation.
     * @return A {@link DescribeRegionsResult} for DescribeRegions operation.
     * @throws RuntimeException If an error occurs
     */
    default DescribeRegionsResult describeRegions(DescribeRegionsRequest request) {
        return describeRegions(request, OperationOptions.defaults());
    }

    /**
     * Queries the endpoints of all supported regions or the endpoints of a specific region.
     *
     * @param request A {@link DescribeRegionsRequest} for DescribeRegions operation.
     * @param options The operation options.
     * @return A {@link DescribeRegionsResult} for DescribeRegions operation.
     * @throws RuntimeException If an error occurs
     */
    default DescribeRegionsResult describeRegions(DescribeRegionsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------

    // object basic api

    /**
     * You can call this operation to upload an object.
     *
     * @param request A {@link PutObjectRequest} for PutObject operation.
     * @return A {@link PutObjectResult} for PutObject operation.
     * @throws RuntimeException If an error occurs
     */
    default PutObjectResult putObject(PutObjectRequest request) {
        return putObject(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to upload an object.
     *
     * @param request A {@link PutObjectRequest} for PutObject operation.
     * @param options The operation options.
     * @return A {@link PutObjectResult} for PutObject operation.
     * @throws RuntimeException If an error occurs
     */
    default PutObjectResult putObject(PutObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Copies objects within a bucket or between buckets in the same region.
     *
     * @param request A {@link CopyObjectRequest} for CopyObject operation.
     * @return A {@link CopyObjectResult} for CopyObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CopyObjectResult copyObject(CopyObjectRequest request) {
        return copyObject(request, OperationOptions.defaults());
    }

    /**
     * Copies objects within a bucket or between buckets in the same region.
     *
     * @param request A {@link CopyObjectRequest} for CopyObject operation.
     * @param options The operation options.
     * @return A {@link CopyObjectResult} for CopyObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CopyObjectResult copyObject(CopyObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to query an object.
     *
     * @param request A {@link GetObjectRequest} for GetObject operation.
     * @return A {@link GetObjectResult} for GetObject operation.
     * @throws RuntimeException If an error occurs
     */
    default GetObjectResult getObject(GetObjectRequest request) {
        return getObject(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to query an object.
     *
     * @param request A {@link GetObjectRequest} for GetObject operation.
     * @param options The operation options.
     * @return A {@link GetObjectResult} for GetObject operation.
     * @throws RuntimeException If an error occurs
     */
    default GetObjectResult getObject(GetObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * You can call this operation to upload an object by appending the object to an existing object.
     *
     * @param request A {@link AppendObjectRequest} for AppendObject operation.
     * @return A {@link AppendObjectResult} for AppendObject operation.
     * @throws RuntimeException If an error occurs
     */
    default AppendObjectResult appendObject(AppendObjectRequest request) {
        return appendObject(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to upload an object by appending the object to an existing object.
     *
     * @param request A {@link AppendObjectRequest} for AppendObject operation.
     * @param options The operation options.
     * @return A {@link AppendObjectResult} for AppendObject operation.
     * @throws RuntimeException If an error occurs
     */
    default AppendObjectResult appendObject(AppendObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to delete an object.
     *
     * @param request A {@link DeleteObjectRequest} for DeleteObject operation.
     * @return A {@link DeleteObjectResult} for DeleteObject operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteObjectResult deleteObject(DeleteObjectRequest request) {
        return deleteObject(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to delete an object.
     *
     * @param request A {@link DeleteObjectRequest} for DeleteObject operation.
     * @param options The operation options.
     * @return A {@link DeleteObjectResult} for DeleteObject operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteObjectResult deleteObject(DeleteObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to delete multiple objects from a bucket.
     *
     * @param request A {@link DeleteMultipleObjectsRequest} for DeleteMultipleObjects operation.
     * @return A {@link DeleteMultipleObjectsResult} for DeleteMultipleObjects operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteMultipleObjectsResult deleteMultipleObjects(DeleteMultipleObjectsRequest request) {
        return deleteMultipleObjects(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to delete multiple objects from a bucket.
     *
     * @param request A {@link DeleteMultipleObjectsRequest} for DeleteMultipleObjects operation.
     * @param options The operation options.
     * @return A {@link DeleteMultipleObjectsResult} for DeleteMultipleObjects operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteMultipleObjectsResult deleteMultipleObjects(DeleteMultipleObjectsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to query the metadata of an object.
     *
     * @param request A {@link HeadObjectRequest} for HeadObject operation.
     * @return A {@link HeadObjectResult} for HeadObject operation.
     * @throws RuntimeException If an error occurs
     */
    default HeadObjectResult headObject(HeadObjectRequest request) {
        return headObject(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to query the metadata of an object.
     *
     * @param request A {@link HeadObjectRequest} for HeadObject operation.
     * @param options The operation options.
     * @return A {@link HeadObjectResult} for HeadObject operation.
     * @throws RuntimeException If an error occurs
     */
    default HeadObjectResult headObject(HeadObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to query the metadata of an object, including ETag, Size, and LastModified. The content of the object is not returned.
     *
     * @param request A {@link GetObjectMetaRequest} for GetObjectMeta operation.
     * @return A {@link GetObjectMetaResult} for GetObjectMeta operation.
     * @throws RuntimeException If an error occurs
     */
    default GetObjectMetaResult getObjectMeta(GetObjectMetaRequest request) {
        return getObjectMeta(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to query the metadata of an object, including ETag, Size, and LastModified. The content of the object is not returned.
     *
     * @param request A {@link GetObjectMetaRequest} for GetObjectMeta operation.
     * @param options The operation options.
     * @return A {@link GetObjectMetaResult} for GetObjectMeta operation.
     * @throws RuntimeException If an error occurs
     */
    default GetObjectMetaResult getObjectMeta(GetObjectMetaRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to restore objects of the Archive and Cold Archive storage classes.
     *
     * @param request A {@link RestoreObjectRequest} for RestoreObject operation.
     * @return A {@link RestoreObjectResult} for RestoreObject operation.
     * @throws RuntimeException If an error occurs
     */
    default RestoreObjectResult restoreObject(RestoreObjectRequest request) {
        return restoreObject(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to restore objects of the Archive and Cold Archive storage classes.
     *
     * @param request A {@link RestoreObjectRequest} for RestoreObject operation.
     * @param options The operation options.
     * @return A {@link RestoreObjectResult} for RestoreObject operation.
     * @throws RuntimeException If an error occurs
     */
    default RestoreObjectResult restoreObject(RestoreObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to clean an object restored from Archive or Cold Archive state. After that, the restored object returns to the frozen state.
     *
     * @param request A {@link CleanRestoredObjectRequest} for CleanRestoredObject operation.
     * @return A {@link CleanRestoredObjectResult} for CleanRestoredObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CleanRestoredObjectResult cleanRestoredObject(CleanRestoredObjectRequest request) {
        return cleanRestoredObject(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to clean an object restored from Archive or Cold Archive state. After that, the restored object returns to the frozen state.
     *
     * @param request A {@link CleanRestoredObjectRequest} for CleanRestoredObject operation.
     * @param options The operation options.
     * @return A {@link CleanRestoredObjectResult} for CleanRestoredObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CleanRestoredObjectResult cleanRestoredObject(CleanRestoredObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

    // object acl api

    /**
     * You can call this operation to modify the ACL of an object.
     *
     * @param request A {@link PutObjectAclRequest} for PutObjectAcl operation.
     * @return A {@link PutObjectAclResult} for PutObjectAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default PutObjectAclResult putObjectAcl(PutObjectAclRequest request) {
        return putObjectAcl(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to modify the ACL of an object.
     *
     * @param request A {@link PutObjectAclRequest} for PutObjectAcl operation.
     * @param options The operation options.
     * @return A {@link PutObjectAclResult} for PutObjectAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default PutObjectAclResult putObjectAcl(PutObjectAclRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to query the ACL of an object in a bucket.
     *
     * @param request A {@link GetObjectAclRequest} for GetObjectAcl operation.
     * @return A {@link GetObjectAclResult} for GetObjectAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default GetObjectAclResult getObjectAcl(GetObjectAclRequest request) {
        return getObjectAcl(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to query the ACL of an object in a bucket.
     *
     * @param request A {@link GetObjectAclRequest} for GetObjectAcl operation.
     * @param options The operation options.
     * @return A {@link GetObjectAclResult} for GetObjectAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default GetObjectAclResult getObjectAcl(GetObjectAclRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------

    // object multipart api

    /**
     * Initiates a multipart upload task.
     *
     * @param request A {@link InitiateMultipartUploadRequest} for InitiateMultipartUpload operation.
     * @return A {@link InitiateMultipartUploadResult} for InitiateMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest request) {
        return initiateMultipartUpload(request, OperationOptions.defaults());
    }

    /**
     * Initiates a multipart upload task.
     *
     * @param request A {@link InitiateMultipartUploadRequest} for InitiateMultipartUpload operation.
     * @param options The operation options.
     * @return A {@link InitiateMultipartUploadResult} for InitiateMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * You can call this operation to upload an object by part based on the object name and the upload ID that you specify.
     *
     * @param request A {@link UploadPartRequest} for UploadPart operation.
     * @return A {@link UploadPartResult} for UploadPart operation.
     * @throws RuntimeException If an error occurs
     */
    default UploadPartResult uploadPart(UploadPartRequest request) {
        return uploadPart(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to upload an object by part based on the object name and the upload ID that you specify.
     *
     * @param request A {@link UploadPartRequest} for UploadPart operation.
     * @param options The operation options.
     * @return A {@link UploadPartResult} for UploadPart operation.
     * @throws RuntimeException If an error occurs
     */
    default UploadPartResult uploadPart(UploadPartRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to complete the multipart upload task of an object.
     *
     * @param request A {@link CompleteMultipartUploadRequest} for CompleteMultipartUpload operation.
     * @return A {@link CompleteMultipartUploadResult} for CompleteMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest request) {
        return completeMultipartUpload(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to complete the multipart upload task of an object.
     *
     * @param request A {@link CompleteMultipartUploadRequest} for CompleteMultipartUpload operation.
     * @param options The operation options.
     * @return A {@link CompleteMultipartUploadResult} for CompleteMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Upload a single shard by copying an existing file.
     *
     * @param request A {@link UploadPartCopyRequest} for UploadPartCopy operation.
     * @return A {@link UploadPartCopyResult} for UploadPartCopy operation.
     * @throws RuntimeException If an error occurs
     */
    default UploadPartCopyResult uploadPartCopy(UploadPartCopyRequest request) {
        return uploadPartCopy(request, OperationOptions.defaults());
    }

    /**
     * Upload a single shard by copying an existing file.
     *
     * @param request A {@link UploadPartCopyRequest} for UploadPartCopy operation.
     * @param options The operation options.
     * @return A {@link UploadPartCopyResult} for UploadPartCopy operation.
     * @throws RuntimeException If an error occurs
     */
    default UploadPartCopyResult uploadPartCopy(UploadPartCopyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * You can call this operation to cancel a multipart upload task and delete the parts that are uploaded by the multipart upload task.
     *
     * @param request A {@link AbortMultipartUploadRequest} for AbortMultipartUpload operation.
     * @return A {@link AbortMultipartUploadResult} for AbortMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default AbortMultipartUploadResult abortMultipartUpload(AbortMultipartUploadRequest request) {
        return abortMultipartUpload(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to cancel a multipart upload task and delete the parts that are uploaded by the multipart upload task.
     *
     * @param request A {@link AbortMultipartUploadRequest} for AbortMultipartUpload operation.
     * @param options The operation options.
     * @return A {@link AbortMultipartUploadResult} for AbortMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default AbortMultipartUploadResult abortMultipartUpload(AbortMultipartUploadRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to list all ongoing multipart upload tasks.
     *
     * @param request A {@link ListMultipartUploadsRequest} for ListMultipartUploads operation.
     * @return A {@link ListMultipartUploadsResult} for ListMultipartUploads operation.
     * @throws RuntimeException If an error occurs
     */
    default ListMultipartUploadsResult listMultipartUploads(ListMultipartUploadsRequest request) {
        return listMultipartUploads(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to list all ongoing multipart upload tasks.
     *
     * @param request A {@link ListMultipartUploadsRequest} for ListMultipartUploads operation.
     * @param options The operation options.
     * @return A {@link ListMultipartUploadsResult} for ListMultipartUploads operation.
     * @throws RuntimeException If an error occurs
     */
    default ListMultipartUploadsResult listMultipartUploads(ListMultipartUploadsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * You can call this operation to list all parts that are uploaded by using a specified upload ID.
     *
     * @param request A {@link ListPartsRequest} for ListParts operation.
     * @return A {@link ListPartsResult} for ListParts operation.
     * @throws RuntimeException If an error occurs
     */
    default ListPartsResult listParts(ListPartsRequest request) {
        return listParts(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to list all parts that are uploaded by using a specified upload ID.
     *
     * @param request A {@link ListPartsRequest} for ListParts operation.
     * @param options The operation options.
     * @return A {@link ListPartsResult} for ListParts operation.
     * @throws RuntimeException If an error occurs
     */
    default ListPartsResult listParts(ListPartsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------

    // bucket basic api

    /**
     * You can create a symbolic link for a target object.
     *
     * @param request A {@link PutSymlinkRequest} for PutSymlink operation.
     * @return A {@link PutSymlinkResult} for PutSymlink operation.
     * @throws RuntimeException If an error occurs
     */
    default PutSymlinkResult putSymlink(PutSymlinkRequest request) {
        return putSymlink(request, OperationOptions.defaults());
    }

    /**
     * You can create a symbolic link for a target object.
     *
     * @param request A {@link PutSymlinkRequest} for PutSymlink operation.
     * @param options The operation options.
     * @return A {@link PutSymlinkResult} for PutSymlink operation.
     * @throws RuntimeException If an error occurs
     */
    default PutSymlinkResult putSymlink(PutSymlinkRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to query a symbolic link of an object.
     *
     * @param request A {@link GetSymlinkRequest} for GetSymlink operation.
     * @return A {@link GetSymlinkResult} for GetSymlink operation.
     * @throws RuntimeException If an error occurs
     */
    default GetSymlinkResult getSymlink(GetSymlinkRequest request) {
        return getSymlink(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to query a symbolic link of an object.
     *
     * @param request A {@link GetSymlinkRequest} for GetSymlink operation.
     * @param options The operation options.
     * @return A {@link GetSymlinkResult} for GetSymlink operation.
     * @throws RuntimeException If an error occurs
     */
    default GetSymlinkResult getSymlink(GetSymlinkRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------

    // object tagging api

    /**
     * You can call this operation to add tags to or modify the tags of an object.
     *
     * @param request A {@link PutObjectTaggingRequest} for PutObjectTagging operation.
     * @return A {@link PutObjectTaggingResult} for PutObjectTagging operation.
     * @throws RuntimeException If an error occurs
     */
    default PutObjectTaggingResult putObjectTagging(PutObjectTaggingRequest request) {
        return putObjectTagging(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to add tags to or modify the tags of an object.
     *
     * @param request A {@link PutObjectTaggingRequest} for PutObjectTagging operation.
     * @param options The operation options.
     * @return A {@link PutObjectTaggingResult} for PutObjectTagging operation.
     * @throws RuntimeException If an error occurs
     */
    default PutObjectTaggingResult putObjectTagging(PutObjectTaggingRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * You can call this operation to query the tags of an object.
     *
     * @param request A {@link GetObjectTaggingRequest} for GetObjectTagging operation.
     * @return A {@link GetObjectTaggingResult} for GetObjectTagging operation.
     * @throws RuntimeException If an error occurs
     */
    default GetObjectTaggingResult getObjectTagging(GetObjectTaggingRequest request) {
        return getObjectTagging(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to query the tags of an object.
     *
     * @param request A {@link GetObjectTaggingRequest} for GetObjectTagging operation.
     * @param options The operation options.
     * @return A {@link GetObjectTaggingResult} for GetObjectTagging operation.
     * @throws RuntimeException If an error occurs
     */
    default GetObjectTaggingResult getObjectTagging(GetObjectTaggingRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to delete the tags of a specified object.
     *
     * @param request A {@link DeleteObjectTaggingRequest} for DeleteObjectTagging operation.
     * @return A {@link DeleteObjectTaggingResult} for DeleteObjectTagging operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteObjectTaggingResult deleteObjectTagging(DeleteObjectTaggingRequest request) {
        return deleteObjectTagging(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to delete the tags of a specified object.
     *
     * @param request A {@link DeleteObjectTaggingRequest} for DeleteObjectTagging operation.
     * @param options The operation options.
     * @return A {@link DeleteObjectTaggingResult} for DeleteObjectTagging operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteObjectTaggingResult deleteObjectTagging(DeleteObjectTaggingRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------

    // bucket basic api

    /**
     * Queries the storage capacity of a bucket and the number of objects that are stored in the bucket.
     *
     * @param request A {@link GetBucketStatRequest} for GetBucketStat operation.
     * @return A {@link GetBucketStatResult} for GetBucketStat operation.
     * @throws RuntimeException If an error occurs
     */
    default GetBucketStatResult getBucketStat(GetBucketStatRequest request) {
        return getBucketStat(request, OperationOptions.defaults());
    }

    /**
     * Queries the storage capacity of a bucket and the number of objects that are stored in the bucket.
     *
     * @param request A {@link GetBucketStatRequest} for GetBucketStat operation.
     * @param options The operation options.
     * @return A {@link GetBucketStatResult} for GetBucketStat operation.
     * @throws RuntimeException If an error occurs
     */
    default GetBucketStatResult getBucketStat(GetBucketStatRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a bucket.
     *
     * @param request A {@link PutBucketRequest} for PutBucket operation.
     * @return A {@link PutBucketResult} for PutBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default PutBucketResult putBucket(PutBucketRequest request) {
        return putBucket(request, OperationOptions.defaults());
    }

    /**
     * Creates a bucket.
     *
     * @param request A {@link PutBucketRequest} for PutBucket operation.
     * @param options The operation options.
     * @return A {@link PutBucketResult} for PutBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default PutBucketResult putBucket(PutBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * Deletes a bucket.
     *
     * @param request A {@link DeleteBucketRequest} for DeleteBucket operation.
     * @return A {@link DeleteBucketResult} for DeleteBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteBucketResult deleteBucket(DeleteBucketRequest request) {
        return deleteBucket(request, OperationOptions.defaults());
    }

    /**
     * Deletes a bucket.
     *
     * @param request A {@link DeleteBucketRequest} for DeleteBucket operation.
     * @param options The operation options.
     * @return A {@link DeleteBucketResult} for DeleteBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteBucketResult deleteBucket(DeleteBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * Queries the information about all objects in a bucket.
     *
     * @param request A {@link ListObjectsRequest} for ListObjects operation.
     * @return A {@link ListObjectsResult} for ListObjects operation.
     * @throws RuntimeException If an error occurs
     */
    default ListObjectsResult listObjects(ListObjectsRequest request) {
        return listObjects(request, OperationOptions.defaults());
    }

    /**
     * Queries the information about all objects in a bucket.
     *
     * @param request A {@link ListObjectsRequest} for ListObjects operation.
     * @param options The operation options.
     * @return A {@link ListObjectsResult} for ListObjects operation.
     * @throws RuntimeException If an error occurs
     */
    default ListObjectsResult listObjects(ListObjectsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * Queries the information about all objects in a bucket.
     *
     * @param request A {@link ListObjectsV2Request} for ListObjectsV2 operation.
     * @return A {@link ListObjectsV2Result} for ListObjectsV2 operation.
     * @throws RuntimeException If an error occurs
     */
    default ListObjectsV2Result listObjectsV2(ListObjectsV2Request request) {
        return listObjectsV2(request, OperationOptions.defaults());
    }

    /**
     * Queries the information about all objects in a bucket.
     *
     * @param request A {@link ListObjectsV2Request} for ListObjectsV2 operation.
     * @param options The operation options.
     * @return A {@link ListObjectsV2Result} for ListObjectsV2 operation.
     * @throws RuntimeException If an error occurs
     */
    default ListObjectsV2Result listObjectsV2(ListObjectsV2Request request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Queries the information about a bucket. Only the owner of a bucket can query the information about the bucket. You can call this operation from an Object Storage Service (OSS) endpoint.
     *
     * @param request A {@link GetBucketInfoRequest} for GetBucketInfo operation.
     * @return A {@link GetBucketInfoResult} for GetBucketInfo operation.
     * @throws RuntimeException If an error occurs
     */
    default GetBucketInfoResult getBucketInfo(GetBucketInfoRequest request) {
        return getBucketInfo(request, OperationOptions.defaults());
    }

    /**
     * Queries the information about a bucket. Only the owner of a bucket can query the information about the bucket. You can call this operation from an Object Storage Service (OSS) endpoint.
     *
     * @param request A {@link GetBucketInfoRequest} for GetBucketInfo operation.
     * @param options The operation options.
     * @return A {@link GetBucketInfoResult} for GetBucketInfo operation.
     * @throws RuntimeException If an error occurs
     */
    default GetBucketInfoResult getBucketInfo(GetBucketInfoRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * Queries the region in which a bucket resides. Only the owner of a bucket can query the region in which the bucket resides.
     *
     * @param request A {@link GetBucketLocationRequest} for GetBucketLocation operation.
     * @return A {@link GetBucketLocationResult} for GetBucketLocation operation.
     * @throws RuntimeException If an error occurs
     */
    default GetBucketLocationResult getBucketLocation(GetBucketLocationRequest request) {
        return getBucketLocation(request, OperationOptions.defaults());
    }

    /**
     * Queries the region in which a bucket resides. Only the owner of a bucket can query the region in which the bucket resides.
     *
     * @param request A {@link GetBucketLocationRequest} for GetBucketLocation operation.
     * @param options The operation options.
     * @return A {@link GetBucketLocationResult} for GetBucketLocation operation.
     * @throws RuntimeException If an error occurs
     */
    default GetBucketLocationResult getBucketLocation(GetBucketLocationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------


    // bucket cors api

    /**
     * Configures cross-origin resource sharing (CORS) rules for a bucket.
     *
     * @param request A {@link PutBucketCorsRequest} for PutBucketCors operation.
     * @return A {@link PutBucketCorsResult} for PutBucketCors operation.
     * @throws RuntimeException If an error occurs
     */
    default PutBucketCorsResult putBucketCors(PutBucketCorsRequest request) {
        return putBucketCors(request, OperationOptions.defaults());
    }

    /**
     * Configures cross-origin resource sharing (CORS) rules for a bucket.
     *
     * @param request A {@link PutBucketCorsRequest} for PutBucketCors operation.
     * @param options The operation options.
     * @return A {@link PutBucketCorsResult} for PutBucketCors operation.
     * @throws RuntimeException If an error occurs
     */
    default PutBucketCorsResult putBucketCors(PutBucketCorsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Queries the cross-origin resource sharing (CORS) rules that are configured for a bucket.
     *
     * @param request A {@link GetBucketCorsRequest} for GetBucketCors operation.
     * @return A {@link GetBucketCorsResult} for GetBucketCors operation.
     * @throws RuntimeException If an error occurs
     */
    default GetBucketCorsResult getBucketCors(GetBucketCorsRequest request) {
        return getBucketCors(request, OperationOptions.defaults());
    }

    /**
     * Queries the cross-origin resource sharing (CORS) rules that are configured for a bucket.
     *
     * @param request A {@link GetBucketCorsRequest} for GetBucketCors operation.
     * @param options The operation options.
     * @return A {@link GetBucketCorsResult} for GetBucketCors operation.
     * @throws RuntimeException If an error occurs
     */
    default GetBucketCorsResult getBucketCors(GetBucketCorsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Disables the cross-origin resource sharing (CORS) feature and deletes all CORS rules for a bucket.
     *
     * @param request A {@link DeleteBucketCorsRequest} for DeleteBucketCors operation.
     * @return A {@link DeleteBucketCorsResult} for DeleteBucketCors operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteBucketCorsResult deleteBucketCors(DeleteBucketCorsRequest request) {
        return deleteBucketCors(request, OperationOptions.defaults());
    }

    /**
     * Disables the cross-origin resource sharing (CORS) feature and deletes all CORS rules for a bucket.
     *
     * @param request A {@link DeleteBucketCorsRequest} for DeleteBucketCors operation.
     * @param options The operation options.
     * @return A {@link DeleteBucketCorsResult} for DeleteBucketCors operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteBucketCorsResult deleteBucketCors(DeleteBucketCorsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Determines whether to send a cross-origin request. Before a cross-origin request is sent, the browser sends a preflight OPTIONS request that includes a specific origin, HTTP method, and header information to Object Storage Service (OSS) to determine whether to send the cross-origin request.
     *
     * @param request A {@link OptionObjectRequest} for OptionObject operation.
     * @return A {@link OptionObjectResult} for OptionObject operation.
     * @throws RuntimeException If an error occurs
     */
    default OptionObjectResult optionObject(OptionObjectRequest request) {
        return optionObject(request, OperationOptions.defaults());
    }

    /**
     * Determines whether to send a cross-origin request. Before a cross-origin request is sent, the browser sends a preflight OPTIONS request that includes a specific origin, HTTP method, and header information to Object Storage Service (OSS) to determine whether to send the cross-origin request.
     *
     * @param request A {@link OptionObjectRequest} for OptionObject operation.
     * @param options The operation options.
     * @return A {@link OptionObjectResult} for OptionObject operation.
     * @throws RuntimeException If an error occurs
     */
    default OptionObjectResult optionObject(OptionObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------

    // bucket versioning api
    /**
     * Configures the versioning state for a bucket.
     *
     * @param request A {@link PutBucketVersioningRequest} for PutBucketVersioning operation.
     * @return A {@link PutBucketVersioningResult} for PutBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    default PutBucketVersioningResult putBucketVersioning(PutBucketVersioningRequest request) {
        return putBucketVersioning(request, OperationOptions.defaults());
    }

    /**
     * Configures the versioning state for a bucket.
     *
     * @param request A {@link PutBucketVersioningRequest} for PutBucketVersioning operation.
     * @param options The operation options.
     * @return A {@link PutBucketVersioningResult} for PutBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    default PutBucketVersioningResult putBucketVersioning(PutBucketVersioningRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Queries the versioning state of a bucket.
     *
     * @param request A {@link GetBucketVersioningRequest} for GetBucketVersioning operation.
     * @return A {@link GetBucketVersioningResult} for GetBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    default GetBucketVersioningResult getBucketVersioning(GetBucketVersioningRequest request) {
        return getBucketVersioning(request, OperationOptions.defaults());
    }

    /**
     * Queries the versioning state of a bucket.
     *
     * @param request A {@link GetBucketVersioningRequest} for GetBucketVersioning operation.
     * @param options The operation options.
     * @return A {@link GetBucketVersioningResult} for GetBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    default GetBucketVersioningResult getBucketVersioning(GetBucketVersioningRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Queries the information about the versions of all objects in a bucket, including the delete markers.
     *
     * @param request A {@link ListObjectVersionsRequest} for ListObjectVersions operation.
     * @return A {@link ListObjectVersionsResult} for ListObjectVersions operation.
     * @throws RuntimeException If an error occurs
     */
    default ListObjectVersionsResult listObjectVersions(ListObjectVersionsRequest request) {
        return listObjectVersions(request, OperationOptions.defaults());
    }

    /**
     * Queries the information about the versions of all objects in a bucket, including the delete markers.
     *
     * @param request A {@link ListObjectVersionsRequest} for ListObjectVersions operation.
     * @param options The operation options.
     * @return A {@link ListObjectVersionsResult} for ListObjectVersions operation.
     * @throws RuntimeException If an error occurs
     */
    default ListObjectVersionsResult listObjectVersions(ListObjectVersionsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    // Paginator
    /**
     * This is a variant of the ListBuckets operation.
     * The return type is a custom iterable that can be used to iterate through all the pages.
     * SDK will internally handle making service calls for you.
     *
     * @param request A {@link ListBucketsRequest} for ListBuckets operation.
     * @return A {@link ListBucketsIterable} that can be used to iterate through all the response pages.
     * @throws RuntimeException If an error occurs
     */
    default ListBucketsIterable listBucketsPaginator(ListBucketsRequest request) {
        return listBucketsPaginator(request, PaginatorOptions.defaults());
    }

    /**
     * This is a variant of the ListBuckets operation.
     * The return type is a custom iterable that can be used to iterate through all the pages.
     * SDK will internally handle making service calls for you.
     *
     * @param request A {@link ListBucketsRequest} for ListBuckets operation.
     * @param options The paginator options.
     * @return A {@link ListBucketsIterable} that can be used to iterate through all the response pages.
     * @throws RuntimeException If an error occurs
     */
    default ListBucketsIterable listBucketsPaginator(ListBucketsRequest request, PaginatorOptions options) {
        return new ListBucketsIterable(this, request, options);
    }

    /**
     * This is a variant of the ListObjectsV2 operation.
     * The return type is a custom iterable that can be used to iterate through all the pages.
     * SDK will internally handle making service calls for you.
     *
     * @param request A {@link ListObjectsV2Request} for ListObjectsV2 operation.
     * @return A {@link ListObjectsV2Iterable} that can be used to iterate through all the response pages.
     * @throws RuntimeException If an error occurs
     */
    default ListObjectsV2Iterable listObjectsV2Paginator(ListObjectsV2Request request) {
        return listObjectsV2Paginator(request, PaginatorOptions.defaults());
    }

    /**
     * This is a variant of the ListObjectsV2 operation.
     * The return type is a custom iterable that can be used to iterate through all the pages.
     * SDK will internally handle making service calls for you.
     *
     * @param request A {@link ListObjectsV2Request} for ListObjectsV2 operation.
     * @param options The paginator options.
     * @return A {@link ListObjectsV2Iterable} that can be used to iterate through all the response pages.
     * @throws RuntimeException If an error occurs
     */
    default ListObjectsV2Iterable listObjectsV2Paginator(ListObjectsV2Request request, PaginatorOptions options) {
        return new ListObjectsV2Iterable(this, request, options);
    }

    /**
     * This is a variant of the ListObjects operation.
     * The return type is a custom iterable that can be used to iterate through all the pages.
     * SDK will internally handle making service calls for you.
     *
     * @param request A {@link ListObjectsRequest} for ListObjects operation.
     * @return A {@link ListObjectsIterable} that can be used to iterate through all the response pages.
     * @throws RuntimeException If an error occurs
     */
    default ListObjectsIterable listObjectsPaginator(ListObjectsRequest request) {
        return listObjectsPaginator(request, PaginatorOptions.defaults());
    }

    /**
     * This is a variant of the ListObjects operation.
     * The return type is a custom iterable that can be used to iterate through all the pages.
     * SDK will internally handle making service calls for you.
     *
     * @param request A {@link ListObjectsRequest} for ListObjects operation.
     * @param options The paginator options.
     * @return A {@link ListObjectsIterable} that can be used to iterate through all the response pages.
     * @throws RuntimeException If an error occurs
     */
    default ListObjectsIterable listObjectsPaginator(ListObjectsRequest request, PaginatorOptions options) {
        return new ListObjectsIterable(this, request, options);
    }

    /**
     * This is a variant of the ListObjectVersions operation.
     * The return type is a custom iterable that can be used to iterate through all the pages.
     * SDK will internally handle making service calls for you.
     *
     * @param request A {@link ListObjectVersionsRequest} for ListObjectVersions operation.
     * @return A {@link ListObjectVersionsIterable} that can be used to iterate through all the response pages.
     * @throws RuntimeException If an error occurs
     */
    default ListObjectVersionsIterable listObjectVersionsPaginator(ListObjectVersionsRequest request) {
        return listObjectVersionsPaginator(request, PaginatorOptions.defaults());
    }

    /**
     * This is a variant of the ListObjectVersions operation.
     * The return type is a custom iterable that can be used to iterate through all the pages.
     * SDK will internally handle making service calls for you.
     *
     * @param request A {@link ListObjectVersionsRequest} for ListObjectVersions operation.
     * @param options The paginator options.
     * @return A {@link ListObjectVersionsIterable} that can be used to iterate through all the response pages.
     * @throws RuntimeException If an error occurs
     */
    default ListObjectVersionsIterable listObjectVersionsPaginator(ListObjectVersionsRequest request, PaginatorOptions options) {
        return new ListObjectVersionsIterable(this, request, options);
    }

    /**
     * This is a variant of the ListMultipartUploads operation.
     * The return type is a custom iterable that can be used to iterate through all the pages.
     * SDK will internally handle making service calls for you.
     *
     * @param request A {@link ListMultipartUploadsRequest} for ListMultipartUploads operation.
     * @return A {@link ListMultipartUploadsIterable} that can be used to iterate through all the response pages.
     * @throws RuntimeException If an error occurs
     */
    default ListMultipartUploadsIterable listMultipartUploadsPaginator(ListMultipartUploadsRequest request) {
        return listMultipartUploadsPaginator(request, PaginatorOptions.defaults());
    }

    /**
     * This is a variant of the ListMultipartUploads operation.
     * The return type is a custom iterable that can be used to iterate through all the pages.
     * SDK will internally handle making service calls for you.
     *
     * @param request A {@link ListMultipartUploadsRequest} for ListMultipartUploads operation.
     * @param options The paginator options.
     * @return A {@link ListMultipartUploadsIterable} that can be used to iterate through all the response pages.
     * @throws RuntimeException If an error occurs
     */
    default ListMultipartUploadsIterable listMultipartUploadsPaginator(ListMultipartUploadsRequest request, PaginatorOptions options) {
        return new ListMultipartUploadsIterable(this, request, options);
    }

    /**
     * This is a variant of the ListParts operation.
     * The return type is a custom iterable that can be used to iterate through all the pages.
     * SDK will internally handle making service calls for you.
     *
     * @param request A {@link ListPartsRequest} for ListParts operation.
     * @return A {@link ListPartsIterable} that can be used to iterate through all the response pages.
     * @throws RuntimeException If an error occurs
     */
    default ListPartsIterable listPartsPaginator(ListPartsRequest request) {
        return listPartsPaginator(request, PaginatorOptions.defaults());
    }

    /**
     * This is a variant of the ListParts operation.
     * The return type is a custom iterable that can be used to iterate through all the pages.
     * SDK will internally handle making service calls for you.
     *
     * @param request A {@link ListPartsRequest} for ListParts operation.
     * @param options The paginator options.
     * @return A {@link ListPartsIterable} that can be used to iterate through all the response pages.
     * @throws RuntimeException If an error occurs
     */
    default ListPartsIterable listPartsPaginator(ListPartsRequest request, PaginatorOptions options) {
        return new ListPartsIterable(this, request, options);
    }
    //-----------------------------------------------------------------------

    // bucket acl api
    /**
     * Configures or modifies the access control list (ACL) for a bucket.
     *
     * @param request A {@link PutBucketAclRequest} for PutBucketAcl operation.
     * @return A {@link PutBucketAclResult} for PutBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default PutBucketAclResult putBucketAcl(PutBucketAclRequest request) {
        return putBucketAcl(request, OperationOptions.defaults());
    }

    /**
     * Configures or modifies the access control list (ACL) for a bucket.
     *
     * @param request A {@link PutBucketAclRequest} for PutBucketAcl operation.
     * @param options The operation options.
     * @return A {@link PutBucketAclResult} for PutBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default PutBucketAclResult putBucketAcl(PutBucketAclRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Queries the access control list (ACL) of a bucket. Only the owner of a bucket can query the ACL of the bucket.
     *
     * @param request A {@link GetBucketAclRequest} for GetBucketAcl operation.
     * @return A {@link GetBucketAclResult} for GetBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default GetBucketAclResult getBucketAcl(GetBucketAclRequest request) {
        return getBucketAcl(request, OperationOptions.defaults());
    }

    /**
     * Queries the access control list (ACL) of a bucket. Only the owner of a bucket can query the ACL of the bucket.
     *
     * @param request A {@link GetBucketAclRequest} for GetBucketAcl operation.
     * @param options The operation options.
     * @return A {@link GetBucketAclResult} for GetBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default GetBucketAclResult getBucketAcl(GetBucketAclRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

    // extensions api

    /**
     * Use GetBucketAcl to check if the bucket exists.
     *
     * @param bucket The bucket name.
     * @return Returns true if the bucket exists and false if not.
     * @throws RuntimeException If an error occurs
     */
    default boolean doesBucketExist(String bucket) {
        return doesBucketExist(GetBucketAclRequest.newBuilder().bucket(bucket).build());
    }

    /**
     * Use GetBucketAcl to check if the bucket exists.
     *
     * @param request A {@link GetBucketAclRequest} for GetBucketAcl operation.
     * @return Returns true if the bucket exists and false if not.
     * @throws RuntimeException If an error occurs
     */
    default boolean doesBucketExist(GetBucketAclRequest request) {
        try {
            getBucketAcl(request);
            return true;
        } catch (Exception e) {
            ServiceException err = ServiceException.asCause(e);
            if (err != null) {
                return !"NoSuchBucket".equals(err.errorCode());
            }
            throw e;
        }
    }

    /**
     * Use GetObjectMeta to check if the object exists.
     *
     * @param bucket The bucket name.
     * @return Returns true if the bucket exists and false if not.
     * @throws RuntimeException If an error occurs
     *                          Throw an exception when encountering a NoSuchBucket error.
     */
    default boolean doesObjectExist(String bucket, String key) {
        return doesObjectExist(GetObjectMetaRequest.newBuilder().bucket(bucket).key(key).build());
    }

    /**
     * Use GetObjectMeta to check if the object exists.
     *
     * @param request A {@link GetObjectMetaRequest} for GetObjectMeta operation.
     * @return Returns true if the bucket exists and false if not.
     * @throws RuntimeException If an error occurs,
     *                          Throw an exception when encountering a NoSuchBucket error.
     */
    default boolean doesObjectExist(GetObjectMetaRequest request) {
        try {
            getObjectMeta(request);
            return true;
        } catch (Exception e) {
            ServiceException err = ServiceException.asCause(e);
            if (err != null) {
                if ("NoSuchKey".equals(err.errorCode()) ||
                        (err.statusCode() == 404 && "BadErrorResponse".equals(err.errorCode()))) {
                    return false;
                }
            }
            throw e;
        }
    }

    /**
     * Use GetObjectMeta to check if the object exists.
     *
     * @param bucket The bucket name.
     * @return Returns true if the bucket exists and false if not.
     * Returns false when encountering a NoSuchBucket error like oss sdk v1.
     * @throws RuntimeException If an error occurs
     */
    default boolean doesObjectExistLegacy(String bucket, String key) {
        return doesObjectExistLegacy(GetObjectMetaRequest.newBuilder().bucket(bucket).key(key).build());
    }

    /**
     * Use GetObjectMeta to check if the object exists.
     *
     * @param request A {@link GetObjectMetaRequest} for GetObjectMeta operation.
     * @return Returns true if the bucket exists and false if not.
     * Returns false when encountering a NoSuchBucket error like oss sdk v1.
     * @throws RuntimeException If an error occurs
     */
    default boolean doesObjectExistLegacy(GetObjectMetaRequest request) {
        try {
            getObjectMeta(request);
            return true;
        } catch (Exception e) {
            ServiceException err = ServiceException.asCause(e);
            if (err != null) {
                if ("NoSuchBucket".equals(err.errorCode()) ||
                        "NoSuchKey".equals(err.errorCode()) ||
                        (err.statusCode() == 404 && "BadErrorResponse".equals(err.errorCode()))) {
                    return false;
                }
            }
            throw e;
        }
    }

    /**
     * Creates a new object from the local file.
     *
     * @param request A {@link PutObjectRequest} for PutObject operation.
     * @param file    The local file.
     * @return A {@link PutObjectResult} for PutObject operation.
     * @throws RuntimeException If an error occurs
     */
    default PutObjectResult putObjectFromFile(PutObjectRequest request, File file) {
        return putObjectFromFile(request, file, OperationOptions.defaults());
    }

    /**
     * Creates a new object from the local file.
     *
     * @param request  A {@link PutObjectRequest} for PutObject operation.
     * @param filePath The local file path.
     * @return A {@link PutObjectResult} for PutObject operation.
     * @throws RuntimeException If an error occurs
     */
    default PutObjectResult putObjectFromFile(PutObjectRequest request, Path filePath) {
        return putObjectFromFile(request, filePath.toFile(), OperationOptions.defaults());
    }

    /**
     * Creates a new object from the local file.
     *
     * @param request A {@link PutObjectRequest} for PutObject operation.
     * @param file    The local file.
     * @param options The operation options.
     * @return A {@link PutObjectResult} for PutObject operation.
     * @throws RuntimeException If an error occurs
     */
    default PutObjectResult putObjectFromFile(PutObjectRequest request, File file, OperationOptions options) {
        try (InputStream in = new FileInputStream(file)) {
            return putObject(request.toBuilder().body(BinaryData.fromStream(in)).build(), options);
        } catch (IOException e) {
            throw new OperationException("PutObject", e);
        }
    }

    /**
     * Downloads a object into the local file.
     *
     * @param request A {@link GetObjectRequest} for GetObject operation.
     * @param file    The local file.
     * @return A {@link GetObjectResult} for GetObject operation.
     * @throws RuntimeException If an error occurs
     */
    default GetObjectResult getObjectToFile(GetObjectRequest request, File file) {
        return getObjectToFile(request, file, OperationOptions.defaults());
    }

    /**
     * Downloads a object into the local file.
     *
     * @param request  A {@link GetObjectRequest} for GetObject operation.
     * @param filePath The local file path.
     * @return A {@link GetObjectResult} for GetObject operation.
     * @throws RuntimeException If an error occurs
     */
    default GetObjectResult getObjectToFile(GetObjectRequest request, Path filePath) {
        return getObjectToFile(request, filePath.toFile(), OperationOptions.defaults());
    }

    /**
     * Downloads a object into the local file.
     *
     * @param request A {@link GetObjectRequest} for GetObject operation.
     * @param file    The local file.
     * @param options The operation options.
     * @return A {@link GetObjectResult} for GetObject operation.
     * @throws RuntimeException If an error occurs
     */
    default GetObjectResult getObjectToFile(GetObjectRequest request, File file, OperationOptions options) {
        try (OutputStream os = new FileOutputStream(file)) {
            try (GetObjectResult result = getObject(request, options)) {
                long ignore = IOUtils.copyLarge(result.body(), os, IOUtils.byteArray(32 * 1024));
                return result.toBuilder().innerBody(null).build();
            }
        } catch (Exception e) {
            if (e instanceof OperationException) {
                throw (OperationException) e;
            }
            throw new OperationException("PutObject", e);
        }
    }

    //-----------------------------------------------------------------------

    // public access block api
    /**
     * Configures the block public access feature for a bucket.
     *
     * @param request A {@link PutPublicAccessBlockRequest} for PutPublicAccessBlock operation.
     * @return A {@link PutPublicAccessBlockResult} for PutPublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default PutPublicAccessBlockResult putPublicAccessBlock(PutPublicAccessBlockRequest request) {
        return putPublicAccessBlock(request, OperationOptions.defaults());
    }

    /**
     * Configures the block public access feature for a bucket.
     *
     * @param request A {@link PutPublicAccessBlockRequest} for PutPublicAccessBlock operation.
     * @param options The operation options.
     * @return A {@link PutPublicAccessBlockResult} for PutPublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default PutPublicAccessBlockResult putPublicAccessBlock(PutPublicAccessBlockRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Queries the block public access configuration of a bucket.
     *
     * @param request A {@link GetPublicAccessBlockRequest} for GetPublicAccessBlock operation.
     * @return A {@link GetPublicAccessBlockResult} for GetPublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default GetPublicAccessBlockResult getPublicAccessBlock(GetPublicAccessBlockRequest request) {
        return getPublicAccessBlock(request, OperationOptions.defaults());
    }

    /**
     * Queries the block public access configuration of a bucket.
     *
     * @param request A {@link GetPublicAccessBlockRequest} for GetPublicAccessBlock operation.
     * @param options The operation options.
     * @return A {@link GetPublicAccessBlockResult} for GetPublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default GetPublicAccessBlockResult getPublicAccessBlock(GetPublicAccessBlockRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes the block public access configuration of a bucket.
     *
     * @param request A {@link DeletePublicAccessBlockRequest} for DeletePublicAccessBlock operation.
     * @return A {@link DeletePublicAccessBlockResult} for DeletePublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default DeletePublicAccessBlockResult deletePublicAccessBlock(DeletePublicAccessBlockRequest request) {
        return deletePublicAccessBlock(request, OperationOptions.defaults());
    }

    /**
     * Deletes the block public access configuration of a bucket.
     *
     * @param request A {@link DeletePublicAccessBlockRequest} for DeletePublicAccessBlock operation.
     * @param options The operation options.
     * @return A {@link DeletePublicAccessBlockResult} for DeletePublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default DeletePublicAccessBlockResult deletePublicAccessBlock(DeletePublicAccessBlockRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

}
