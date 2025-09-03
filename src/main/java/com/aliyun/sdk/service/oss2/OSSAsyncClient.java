package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.exceptions.OperationException;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transport.BinaryDataConsumerSupplier;
import com.aliyun.sdk.service.oss2.utils.IOUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;

/**
 * A client for accessing OSS asynchronously.
 * This can be created using the static {@link #newBuilder()} method.
 */
public interface OSSAsyncClient extends AutoCloseable {

    static OSSAsyncClientBuilder newBuilder() {
        return new DefaultOSSAsyncClientBuilder();
    }

    // common api
    //-----------------------------------------------------------------------
    default CompletableFuture<OperationOutput> invokeOperationAsync(OperationInput input, OperationOptions opts) {
        throw new UnsupportedOperationException();
    }

    // service api

    /**
     * Queries all buckets that are owned by a requester.
     *
     * @param request A {@link ListBucketsRequest} for ListBuckets operation.
     * @return A Java Future containing the {@link ListBucketsResult} of the ListBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListBucketsResult> listBucketsAsync(ListBucketsRequest request) {
        return listBucketsAsync(request, OperationOptions.defaults());
    }

    /**
     * Queries all buckets that are owned by a requester.
     *
     * @param request A {@link ListBucketsRequest} for ListBuckets operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link ListBucketsResult} of the ListBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListBucketsResult> listBucketsAsync(ListBucketsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------

    // regions api

    /**
     * Queries the endpoints of all supported regions or the endpoints of a specific region.
     *
     * @param request A {@link DescribeRegionsRequest} for DescribeRegions operation.
     * @return A Java Future containing the {@link DescribeRegionsResult} of the DescribeRegions operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DescribeRegionsResult> describeRegionsAsync(DescribeRegionsRequest request) {
        return describeRegionsAsync(request, OperationOptions.defaults());
    }

    /**
     * Queries the endpoints of all supported regions or the endpoints of a specific region.
     *
     * @param request A {@link DescribeRegionsRequest} for DescribeRegions operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link DescribeRegionsResult} of the DescribeRegions operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DescribeRegionsResult> describeRegionsAsync(DescribeRegionsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------

    // object basic api

    /**
     * You can call this operation to upload an object.
     *
     * @param request A {@link PutObjectRequest} for PutObject operation.
     * @return A Java Future containing the {@link PutObjectResult} of the PutObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutObjectResult> putObjectAsync(PutObjectRequest request) {
        return putObjectAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to upload an object.
     *
     * @param request A {@link PutObjectRequest} for PutObject operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutObjectResult} of the PutObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutObjectResult> putObjectAsync(PutObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Copies objects within a bucket or between buckets in the same region.
     *
     * @param request A {@link CopyObjectRequest} for CopyObject operation.
     * @return A Java Future containing the {@link CopyObjectResult} of the CopyObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CopyObjectResult> copyObjectAsync(CopyObjectRequest request) {
        return copyObjectAsync(request, OperationOptions.defaults());
    }

    /**
     * Copies objects within a bucket or between buckets in the same region.
     *
     * @param request A {@link CopyObjectRequest} for CopyObject operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link CopyObjectResult} of the CopyObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CopyObjectResult> copyObjectAsync(CopyObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to query an object.
     *
     * @param request A {@link GetObjectRequest} for GetObject operation.
     * @return A Java Future containing the {@link GetObjectResult} of the GetObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetObjectResult> getObjectAsync(GetObjectRequest request) {
        return getObjectAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to query an object.
     *
     * @param request A {@link GetObjectRequest} for GetObject operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetObjectResult} of the GetObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetObjectResult> getObjectAsync(GetObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * You can call this operation to upload an object by appending the object to an existing object.
     *
     * @param request A {@link AppendObjectRequest} for AppendObject operation.
     * @return A Java Future containing the {@link AppendObjectResult} of the AppendObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<AppendObjectResult> appendObjectAsync(AppendObjectRequest request) {
        return appendObjectAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to upload an object by appending the object to an existing object.
     *
     * @param request A {@link AppendObjectRequest} for AppendObject operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link AppendObjectResult} of the AppendObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<AppendObjectResult> appendObjectAsync(AppendObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to delete an object.
     *
     * @param request A {@link DeleteObjectRequest} for DeleteObject operation.
     * @return A Java Future containing the {@link DeleteObjectResult} of the DeleteObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteObjectResult> deleteObjectAsync(DeleteObjectRequest request) {
        return deleteObjectAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to delete an object.
     *
     * @param request A {@link DeleteObjectRequest} for DeleteObject operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link DeleteObjectResult} of the DeleteObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteObjectResult> deleteObjectAsync(DeleteObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * You can call this operation to delete multiple objects from a bucket.
     *
     * @param request A {@link DeleteMultipleObjectsRequest} for DeleteMultipleObjects operation.
     * @return A Java Future containing the {@link DeleteMultipleObjectsResult} of the DeleteMultipleObjects operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteMultipleObjectsResult> deleteMultipleObjectsAsync(DeleteMultipleObjectsRequest request) {
        return deleteMultipleObjectsAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to delete multiple objects from a bucket.
     *
     * @param request A {@link DeleteMultipleObjectsRequest} for DeleteMultipleObjects operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link DeleteMultipleObjectsResult} of the DeleteMultipleObjects operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteMultipleObjectsResult> deleteMultipleObjectsAsync(DeleteMultipleObjectsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * You can call this operation to query the metadata of an object.
     *
     * @param request A {@link HeadObjectRequest} for HeadObject operation.
     * @return A Java Future containing the {@link HeadObjectResult} of the HeadObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<HeadObjectResult> headObjectAsync(HeadObjectRequest request) {
        return headObjectAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to query the metadata of an object.
     *
     * @param request A {@link HeadObjectRequest} for HeadObject operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link HeadObjectResult} of the HeadObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<HeadObjectResult> headObjectAsync(HeadObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to query the metadata of an object, including ETag, Size, and LastModified. The content of the object is not returned.
     *
     * @param request A {@link GetObjectMetaRequest} for GetObjectMeta operation.
     * @return A Java Future containing the {@link GetObjectMetaResult} of the GetObjectMeta operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetObjectMetaResult> getObjectMetaAsync(GetObjectMetaRequest request) {
        return getObjectMetaAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to query the metadata of an object, including ETag, Size, and LastModified. The content of the object is not returned.
     *
     * @param request A {@link GetObjectMetaRequest} for GetObjectMeta operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetObjectMetaResult} of the GetObjectMeta operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetObjectMetaResult> getObjectMetaAsync(GetObjectMetaRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to restore objects of the Archive and Cold Archive storage classes.
     *
     * @param request A {@link RestoreObjectRequest} for RestoreObject operation.
     * @return A Java Future containing the {@link RestoreObjectResult} of the RestoreObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<RestoreObjectResult> restoreObjectAsync(RestoreObjectRequest request) {
        return restoreObjectAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to restore objects of the Archive and Cold Archive storage classes.
     *
     * @param request A {@link RestoreObjectRequest} for RestoreObject operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link RestoreObjectResult} of the RestoreObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<RestoreObjectResult> restoreObjectAsync(RestoreObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to clean an object restored from Archive or Cold Archive state. After that, the restored object returns to the frozen state.
     *
     * @param request A {@link CleanRestoredObjectRequest} for CleanRestoredObject operation.
     * @return A Java Future containing the {@link CleanRestoredObjectResult} of the CleanRestoredObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CleanRestoredObjectResult> cleanRestoredObjectAsync(CleanRestoredObjectRequest request) {
        return cleanRestoredObjectAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to clean an object restored from Archive or Cold Archive state. After that, the restored object returns to the frozen state.
     *
     * @param request A {@link CleanRestoredObjectRequest} for CleanRestoredObject operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link CleanRestoredObjectResult} of the CleanRestoredObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CleanRestoredObjectResult> cleanRestoredObjectAsync(CleanRestoredObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

    // object acl api

    /**
     * You can call this operation to modify the ACL of an object.
     *
     * @param request A {@link PutObjectAclRequest} for PutObjectAcl operation.
     * @return A Java Future containing the {@link PutObjectAclResult} of the PutObjectAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutObjectAclResult> putObjectAclAsync(PutObjectAclRequest request) {
        return putObjectAclAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to modify the ACL of an object.
     *
     * @param request A {@link PutObjectAclRequest} for PutObjectAcl operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutObjectAclResult} of the PutObjectAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutObjectAclResult> putObjectAclAsync(PutObjectAclRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to query the ACL of an object in a bucket.
     *
     * @param request A {@link GetObjectAclRequest} for GetObjectAcl operation.
     * @return A Java Future containing the {@link GetObjectAclResult} of the GetObjectAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetObjectAclResult> getObjectAclAsync(GetObjectAclRequest request) {
        return getObjectAclAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to query the ACL of an object in a bucket.
     *
     * @param request A {@link GetObjectAclRequest} for GetObjectAcl operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetObjectAclResult} of the GetObjectAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetObjectAclResult> getObjectAclAsync(GetObjectAclRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------

    // object multipart api

    /**
     * Initiates a multipart upload task.
     *
     * @param request A {@link InitiateMultipartUploadRequest} for InitiateMultipartUpload operation.
     * @return A Java Future containing the {@link InitiateMultipartUploadResult} of the InitiateMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<InitiateMultipartUploadResult> initiateMultipartUploadAsync(InitiateMultipartUploadRequest request) {
        return initiateMultipartUploadAsync(request, OperationOptions.defaults());
    }

    /**
     * Initiates a multipart upload task.
     *
     * @param request A {@link InitiateMultipartUploadRequest} for InitiateMultipartUpload operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link InitiateMultipartUploadResult} of the InitiateMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<InitiateMultipartUploadResult> initiateMultipartUploadAsync(InitiateMultipartUploadRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * You can call this operation to upload an object by part based on the object name and the upload ID that you specify.
     *
     * @param request A {@link UploadPartRequest} for UploadPart operation.
     * @return A Java Future containing the {@link UploadPartResult} of the UploadPart operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<UploadPartResult> uploadPartAsync(UploadPartRequest request) {
        return uploadPartAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to upload an object by part based on the object name and the upload ID that you specify.
     *
     * @param request A {@link UploadPartRequest} for UploadPart operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link UploadPartResult} of the UploadPart operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<UploadPartResult> uploadPartAsync(UploadPartRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to complete the multipart upload task of an object.
     *
     * @param request A {@link CompleteMultipartUploadRequest} for CompleteMultipartUpload operation.
     * @return A Java Future containing the {@link CompleteMultipartUploadResult} of the CompleteMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CompleteMultipartUploadResult> completeMultipartUploadAsync(CompleteMultipartUploadRequest request) {
        return completeMultipartUploadAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to complete the multipart upload task of an object.
     *
     * @param request A {@link CompleteMultipartUploadRequest} for CompleteMultipartUpload operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link CompleteMultipartUploadResult} of the CompleteMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CompleteMultipartUploadResult> completeMultipartUploadAsync(CompleteMultipartUploadRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Upload a single shard by copying an existing file.
     *
     * @param request A {@link UploadPartCopyRequest} for UploadPartCopy operation.
     * @return A Java Future containing the {@link UploadPartCopyResult} of the UploadPartCopy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<UploadPartCopyResult> uploadPartCopyAsync(UploadPartCopyRequest request) {
        return uploadPartCopyAsync(request, OperationOptions.defaults());
    }

    /**
     * Upload a single shard by copying an existing file.
     *
     * @param request A {@link UploadPartCopyRequest} for UploadPartCopy operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link UploadPartCopyResult} of the UploadPartCopy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<UploadPartCopyResult> uploadPartCopyAsync(UploadPartCopyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * You can call this operation to cancel a multipart upload task and delete the parts that are uploaded by the multipart upload task.
     *
     * @param request A {@link AbortMultipartUploadRequest} for AbortMultipartUpload operation.
     * @return A Java Future containing the {@link AbortMultipartUploadResult} of the AbortMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<AbortMultipartUploadResult> abortMultipartUploadAsync(AbortMultipartUploadRequest request) {
        return abortMultipartUploadAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to cancel a multipart upload task and delete the parts that are uploaded by the multipart upload task.
     *
     * @param request A {@link AbortMultipartUploadRequest} for AbortMultipartUpload operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link AbortMultipartUploadResult} of the AbortMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<AbortMultipartUploadResult> abortMultipartUploadAsync(AbortMultipartUploadRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to list all ongoing multipart upload tasks.
     *
     * @param request A {@link ListMultipartUploadsRequest} for ListMultipartUploads operation.
     * @return A Java Future containing the {@link ListMultipartUploadsResult} of the ListMultipartUploads operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListMultipartUploadsResult> listMultipartUploadsAsync(ListMultipartUploadsRequest request) {
        return listMultipartUploadsAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to list all ongoing multipart upload tasks.
     *
     * @param request A {@link ListMultipartUploadsRequest} for ListMultipartUploads operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link ListMultipartUploadsResult} of the ListMultipartUploads operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListMultipartUploadsResult> listMultipartUploadsAsync(ListMultipartUploadsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * You can call this operation to list all parts that are uploaded by using a specified upload ID.
     *
     * @param request A {@link ListPartsRequest} for ListParts operation.
     * @return A Java Future containing the {@link ListPartsResult} of the ListParts operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListPartsResult> listPartsAsync(ListPartsRequest request) {
        return listPartsAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to list all parts that are uploaded by using a specified upload ID.
     *
     * @param request A {@link ListPartsRequest} for ListParts operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link ListPartsResult} of the ListParts operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListPartsResult> listPartsAsync(ListPartsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------

    // bucket basic api

    /**
     * You can create a symbolic link for a target object.
     *
     * @param request A {@link PutSymlinkRequest} for PutSymlink operation.
     * @return A Java Future containing the {@link PutSymlinkResult} of the PutSymlink operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutSymlinkResult> putSymlinkAsync(PutSymlinkRequest request) {
        return putSymlinkAsync(request, OperationOptions.defaults());
    }

    /**
     * You can create a symbolic link for a target object.
     *
     * @param request A {@link PutSymlinkRequest} for PutSymlink operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutSymlinkResult} of the PutSymlink operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutSymlinkResult> putSymlinkAsync(PutSymlinkRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to query a symbolic link of an object.
     *
     * @param request A {@link GetSymlinkRequest} for GetSymlink operation.
     * @return A Java Future containing the {@link GetSymlinkResult} of the GetSymlink operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetSymlinkResult> getSymlinkAsync(GetSymlinkRequest request) {
        return getSymlinkAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to query a symbolic link of an object.
     *
     * @param request A {@link GetSymlinkRequest} for GetSymlink operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetSymlinkResult} of the GetSymlink operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetSymlinkResult> getSymlinkAsync(GetSymlinkRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------

    // object tagging api

    /**
     * You can call this operation to add tags to or modify the tags of an object.
     *
     * @param request A {@link PutObjectTaggingRequest} for PutObjectTagging operation.
     * @return A Java Future containing the {@link PutObjectTaggingResult} of the PutObjectTagging operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutObjectTaggingResult> putObjectTaggingAsync(PutObjectTaggingRequest request) {
        return putObjectTaggingAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to add tags to or modify the tags of an object.
     *
     * @param request A {@link PutObjectTaggingRequest} for PutObjectTagging operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutObjectTaggingResult} of the PutObjectTagging operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutObjectTaggingResult> putObjectTaggingAsync(PutObjectTaggingRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * You can call this operation to query the tags of an object.
     *
     * @param request A {@link GetObjectTaggingRequest} for GetObjectTagging operation.
     * @return A Java Future containing the {@link GetObjectTaggingResult} of the GetObjectTagging operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetObjectTaggingResult> getObjectTaggingAsync(GetObjectTaggingRequest request) {
        return getObjectTaggingAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to query the tags of an object.
     *
     * @param request A {@link GetObjectTaggingRequest} for GetObjectTagging operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetObjectTaggingResult} of the GetObjectTagging operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetObjectTaggingResult> getObjectTaggingAsync(GetObjectTaggingRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * You can call this operation to delete the tags of a specified object.
     *
     * @param request A {@link DeleteObjectTaggingRequest} for DeleteObjectTagging operation.
     * @return A Java Future containing the {@link DeleteObjectTaggingResult} of the DeleteObjectTagging operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteObjectTaggingResult> deleteObjectTaggingAsync(DeleteObjectTaggingRequest request) {
        return deleteObjectTaggingAsync(request, OperationOptions.defaults());
    }

    /**
     * You can call this operation to delete the tags of a specified object.
     *
     * @param request A {@link DeleteObjectTaggingRequest} for DeleteObjectTagging operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link DeleteObjectTaggingResult} of the DeleteObjectTagging operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteObjectTaggingResult> deleteObjectTaggingAsync(DeleteObjectTaggingRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------

    // bucket basic api

    /**
     * Queries the storage capacity of a bucket and the number of objects that are stored in the bucket.
     *
     * @param request A {@link GetBucketStatRequest} for GetBucketStat operation.
     * @return A Java Future containing the {@link GetBucketStatResult} of the GetBucketStat operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetBucketStatResult> getBucketStatAsync(GetBucketStatRequest request) {
        return getBucketStatAsync(request, OperationOptions.defaults());
    }

    /**
     * Queries the storage capacity of a bucket and the number of objects that are stored in the bucket.
     *
     * @param request A {@link GetBucketStatRequest} for GetBucketStat operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetBucketStatResult} of the GetBucketStat operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetBucketStatResult> getBucketStatAsync(GetBucketStatRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a bucket.
     *
     * @param request A {@link PutBucketRequest} for PutBucket operation.
     * @return A Java Future containing the {@link PutBucketResult} of the PutBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutBucketResult> putBucketAsync(PutBucketRequest request) {
        return putBucketAsync(request, OperationOptions.defaults());
    }

    /**
     * Creates a bucket.
     *
     * @param request A {@link PutBucketRequest} for PutBucket operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutBucketResult} of the PutBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutBucketResult> putBucketAsync(PutBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * Deletes a bucket.
     *
     * @param request A {@link DeleteBucketRequest} for DeleteBucket operation.
     * @return A Java Future containing the {@link DeleteBucketResult} of the DeleteBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteBucketResult> deleteBucketAsync(DeleteBucketRequest request) {
        return deleteBucketAsync(request, OperationOptions.defaults());
    }

    /**
     * Deletes a bucket.
     *
     * @param request A {@link DeleteBucketRequest} for DeleteBucket operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link DeleteBucketResult} of the DeleteBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteBucketResult> deleteBucketAsync(DeleteBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * Queries the information about all objects in a bucket.
     *
     * @param request A {@link ListObjectsRequest} for ListObjects operation.
     * @return A Java Future containing the {@link ListObjectsResult} of the ListObjects operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListObjectsResult> listObjectsAsync(ListObjectsRequest request) {
        return listObjectsAsync(request, OperationOptions.defaults());
    }

    /**
     * Queries the information about all objects in a bucket.
     *
     * @param request A {@link ListObjectsRequest} for ListObjects operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link ListObjectsResult} of the ListObjects operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListObjectsResult> listObjectsAsync(ListObjectsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * Queries the information about all objects in a bucket.
     *
     * @param request A {@link ListObjectsV2Request} for ListObjectsV2 operation.
     * @return A Java Future containing the {@link ListObjectsV2Result} of the ListObjectsV2 operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListObjectsV2Result> listObjectsV2Async(ListObjectsV2Request request) {
        return listObjectsV2Async(request, OperationOptions.defaults());
    }

    /**
     * Queries the information about all objects in a bucket.
     *
     * @param request A {@link ListObjectsV2Request} for ListObjectsV2 operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link ListObjectsV2Result} of the ListObjectsV2 operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListObjectsV2Result> listObjectsV2Async(ListObjectsV2Request request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Queries the information about a bucket. Only the owner of a bucket can query the information about the bucket. You can call this operation from an Object Storage Service (OSS) endpoint.
     *
     * @param request A {@link GetBucketInfoRequest} for GetBucketInfo operation.
     * @return A Java Future containing the {@link GetBucketInfoResult} of the GetBucketInfo operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetBucketInfoResult> getBucketInfoAsync(GetBucketInfoRequest request) {
        return getBucketInfoAsync(request, OperationOptions.defaults());
    }

    /**
     * Queries the information about a bucket. Only the owner of a bucket can query the information about the bucket. You can call this operation from an Object Storage Service (OSS) endpoint.
     *
     * @param request A {@link GetBucketInfoRequest} for GetBucketInfo operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetBucketInfoResult} of the GetBucketInfo operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetBucketInfoResult> getBucketInfoAsync(GetBucketInfoRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * Queries the region in which a bucket resides. Only the owner of a bucket can query the region in which the bucket resides.
     *
     * @param request A {@link GetBucketLocationRequest} for GetBucketLocation operation.
     * @return A Java Future containing the {@link GetBucketLocationResult} of the GetBucketLocation operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetBucketLocationResult> getBucketLocationAsync(GetBucketLocationRequest request) {
        return getBucketLocationAsync(request, OperationOptions.defaults());
    }

    /**
     * Queries the region in which a bucket resides. Only the owner of a bucket can query the region in which the bucket resides.
     *
     * @param request A {@link GetBucketLocationRequest} for GetBucketLocation operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetBucketLocationResult} of the GetBucketLocation operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetBucketLocationResult> getBucketLocationAsync(GetBucketLocationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------


    // bucket cors api

    /**
     * Configures cross-origin resource sharing (CORS) rules for a bucket.
     *
     * @param request A {@link PutBucketCorsRequest} for PutBucketCors operation.
     * @return A Java Future containing the {@link PutBucketCorsResult} of the PutBucketCors operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutBucketCorsResult> putBucketCorsAsync(PutBucketCorsRequest request) {
        return putBucketCorsAsync(request, OperationOptions.defaults());
    }

    /**
     * Configures cross-origin resource sharing (CORS) rules for a bucket.
     *
     * @param request A {@link PutBucketCorsRequest} for PutBucketCors operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutBucketCorsResult} of the PutBucketCors operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutBucketCorsResult> putBucketCorsAsync(PutBucketCorsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Queries the cross-origin resource sharing (CORS) rules that are configured for a bucket.
     *
     * @param request A {@link GetBucketCorsRequest} for GetBucketCors operation.
     * @return A Java Future containing the {@link GetBucketCorsResult} of the GetBucketCors operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetBucketCorsResult> getBucketCorsAsync(GetBucketCorsRequest request) {
        return getBucketCorsAsync(request, OperationOptions.defaults());
    }

    /**
     * Queries the cross-origin resource sharing (CORS) rules that are configured for a bucket.
     *
     * @param request A {@link GetBucketCorsRequest} for GetBucketCors operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetBucketCorsResult} of the GetBucketCors operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetBucketCorsResult> getBucketCorsAsync(GetBucketCorsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Disables the cross-origin resource sharing (CORS) feature and deletes all CORS rules for a bucket.
     *
     * @param request A {@link DeleteBucketCorsRequest} for DeleteBucketCors operation.
     * @return A Java Future containing the {@link DeleteBucketCorsResult} of the DeleteBucketCors operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteBucketCorsResult> deleteBucketCorsAsync(DeleteBucketCorsRequest request) {
        return deleteBucketCorsAsync(request, OperationOptions.defaults());
    }

    /**
     * Disables the cross-origin resource sharing (CORS) feature and deletes all CORS rules for a bucket.
     *
     * @param request A {@link DeleteBucketCorsRequest} for DeleteBucketCors operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link DeleteBucketCorsResult} of the DeleteBucketCors operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteBucketCorsResult> deleteBucketCorsAsync(DeleteBucketCorsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Determines whether to send a cross-origin request. Before a cross-origin request is sent, the browser sends a preflight OPTIONS request that includes a specific origin, HTTP method, and header information to Object Storage Service (OSS) to determine whether to send the cross-origin request.
     *
     * @param request A {@link OptionObjectRequest} for OptionObject operation.
     * @return A Java Future containing the {@link OptionObjectResult} of the OptionObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<OptionObjectResult> optionObjectAsync(OptionObjectRequest request) {
        return optionObjectAsync(request, OperationOptions.defaults());
    }

    /**
     * Determines whether to send a cross-origin request. Before a cross-origin request is sent, the browser sends a preflight OPTIONS request that includes a specific origin, HTTP method, and header information to Object Storage Service (OSS) to determine whether to send the cross-origin request.
     *
     * @param request A {@link OptionObjectRequest} for OptionObject operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link OptionObjectResult} of the OptionObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<OptionObjectResult> optionObjectAsync(OptionObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------


    // bucket versioning api
    /**
     * Configures the versioning state for a bucket.
     *
     * @param request A {@link PutBucketVersioningRequest} for PutBucketVersioning operation.
     * @return A Java Future containing the {@link PutBucketVersioningResult} of the PutBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutBucketVersioningResult> putBucketVersioningAsync(PutBucketVersioningRequest request) {
        return putBucketVersioningAsync(request, OperationOptions.defaults());
    }

    /**
     * Configures the versioning state for a bucket.
     *
     * @param request A {@link PutBucketVersioningRequest} for PutBucketVersioning operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutBucketVersioningResult} of the PutBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutBucketVersioningResult> putBucketVersioningAsync(PutBucketVersioningRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Queries the versioning state of a bucket.
     *
     * @param request A {@link GetBucketVersioningRequest} for GetBucketVersioning operation.
     * @return A Java Future containing the {@link GetBucketVersioningResult} of the GetBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetBucketVersioningResult> getBucketVersioningAsync(GetBucketVersioningRequest request) {
        return getBucketVersioningAsync(request, OperationOptions.defaults());
    }

    /**
     * Queries the versioning state of a bucket.
     *
     * @param request A {@link GetBucketVersioningRequest} for GetBucketVersioning operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetBucketVersioningResult} of the GetBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetBucketVersioningResult> getBucketVersioningAsync(GetBucketVersioningRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Queries the information about the versions of all objects in a bucket, including the delete markers.
     *
     * @param request A {@link ListObjectVersionsRequest} for ListObjectVersions operation.
     * @return A Java Future containing the {@link ListObjectVersionsResult} of the ListObjectVersions operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListObjectVersionsResult> listObjectVersionsAsync(ListObjectVersionsRequest request) {
        return listObjectVersionsAsync(request, OperationOptions.defaults());
    }

    /**
     * Queries the information about the versions of all objects in a bucket, including the delete markers.
     *
     * @param request A {@link ListObjectVersionsRequest} for ListObjectVersions operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link ListObjectVersionsResult} of the ListObjectVersions operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListObjectVersionsResult> listObjectVersionsAsync(ListObjectVersionsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

    // bucket acl api
    /**
     * Configures or modifies the access control list (ACL) for a bucket.
     *
     * @param request A {@link PutBucketAclRequest} for PutBucketAcl operation.
     * @return A Java Future containing the {@link PutBucketAclResult} of the PutBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutBucketAclResult> putBucketAclAsync(PutBucketAclRequest request) {
        return putBucketAclAsync(request, OperationOptions.defaults());
    }

    /**
     * Configures or modifies the access control list (ACL) for a bucket.
     *
     * @param request A {@link PutBucketAclRequest} for PutBucketAcl operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutBucketAclResult} of the PutBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutBucketAclResult> putBucketAclAsync(PutBucketAclRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Queries the access control list (ACL) of a bucket. Only the owner of a bucket can query the ACL of the bucket.
     *
     * @param request A {@link GetBucketAclRequest} for GetBucketAcl operation.
     * @return A Java Future containing the {@link GetBucketAclResult} of the GetBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetBucketAclResult> getBucketAclAsync(GetBucketAclRequest request) {
        return getBucketAclAsync(request, OperationOptions.defaults());
    }

    /**
     * Queries the access control list (ACL) of a bucket. Only the owner of a bucket can query the ACL of the bucket.
     *
     * @param request A {@link GetBucketAclRequest} for GetBucketAcl operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetBucketAclResult} of the GetBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetBucketAclResult> getBucketAclAsync(GetBucketAclRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

    // extensions api
    /**
     * Use GetBucketAcl to check if the bucket exists.
     *
     * @param bucket The bucket name.
     * @return A Java Future containing the result. True if the bucket exists and False if not.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<Boolean> doesBucketExistAsync(String bucket) {
        return doesBucketExistAsync(GetBucketAclRequest.newBuilder().bucket(bucket).build());
    }

    /**
     * Use GetBucketAcl to check if the bucket exists.
     *
     * @param request A {@link GetBucketAclRequest} for GetBucketAcl operation.
     * @return A Java Future containing the result. True if the bucket exists and False if not.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<Boolean> doesBucketExistAsync(GetBucketAclRequest request) {
        return getBucketAclAsync(request).handle((result, exception) -> {
            if (exception != null) {
                Throwable cause = exception.getCause();
                ServiceException err = ServiceException.asCause(cause);
                if (err != null) {
                    return !"NoSuchBucket".equals(err.errorCode());
                }
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException)cause;
                } else {
                    throw new RuntimeException(cause);
                }
            } else {
                return Boolean.TRUE;
            }
        });
    }

    /**
     * Use GetObjectMeta to check if the object exists.
     *
     * @param bucket The bucket name.
     * @return A Java Future containing the result. True if the object exists and False if not.
     * @throws RuntimeException If an error occurs
     *         Throw an exception when encountering a NoSuchBucket error.
     */
    default CompletableFuture<Boolean>  doesObjectExistAsync(String bucket, String key) {
        return doesObjectExistAsync(GetObjectMetaRequest.newBuilder().bucket(bucket).key(key).build());
    }

    /**
     * Use GetObjectMeta to check if the object exists.
     *
     * @param request A {@link GetObjectMetaRequest} for GetObjectMeta operation.
     * @return A Java Future containing the result. True if the object exists and False if not.
     * @throws RuntimeException If an error occurs,
     *         Throw an exception when encountering a NoSuchBucket error.
     */
    default CompletableFuture<Boolean> doesObjectExistAsync(GetObjectMetaRequest request) {
        return getObjectMetaAsync(request).handle((result, exception) -> {
            if (exception != null) {
                Throwable cause = exception.getCause();
                ServiceException err = ServiceException.asCause(cause);
                if (err != null) {
                    if ("NoSuchKey".equals(err.errorCode()) ||
                            (err.statusCode() == 404 && "BadErrorResponse".equals(err.errorCode()))) {
                        return Boolean.FALSE;
                    }
                }
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException)cause;
                } else {
                    throw new RuntimeException(cause);
                }
            } else {
                return Boolean.TRUE;
            }
        });
    }

    /**
     * Use GetObjectMeta to check if the object exists.
     *
     * @param bucket The bucket name.
     * @return  A Java Future containing the result.
     *          True if the object exists and False if not.
     *          False when encountering a NoSuchBucket error like oss sdk v1.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<Boolean>  doesObjectExistLegacyAsync(String bucket, String key) {
        return doesObjectExistLegacyAsync(GetObjectMetaRequest.newBuilder().bucket(bucket).key(key).build());
    }

    /**
     * Use GetObjectMeta to check if the object exists.
     *
     * @param request A {@link GetObjectMetaRequest} for GetObjectMeta operation.
     * @return  A Java Future containing the result.
     *          True if the object exists and False if not.
     *          False when encountering a NoSuchBucket error like oss sdk v1.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<Boolean>  doesObjectExistLegacyAsync(GetObjectMetaRequest request) {
        return getObjectMetaAsync(request).handle((result, exception) -> {
            if (exception != null) {
                Throwable cause = exception.getCause();
                ServiceException err = ServiceException.asCause(cause);
                if (err != null) {
                    if ("NoSuchBucket".equals(err.errorCode()) ||
                            "NoSuchKey".equals(err.errorCode()) ||
                            (err.statusCode() == 404 && "BadErrorResponse".equals(err.errorCode()))) {
                        return Boolean.FALSE;
                    }
                }
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException)cause;
                } else {
                    throw new RuntimeException(cause);
                }
            } else {
                return Boolean.TRUE;
            }
        });
    }

    /**
     * Creates a new object from the local file.
     *
     * @param request A {@link PutObjectRequest} for PutObject operation.
     * @param filePath    The local file path.
     * @return A Java Future containing the {@link PutObjectResult} of the PutObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutObjectResult> putObjectFromFileAsync(PutObjectRequest request, Path filePath) {
        return putObjectFromFileAsync(request, filePath, OperationOptions.defaults());
    }

    /**
     * Creates a new object from the local file.
     *
     * @param request A {@link PutObjectRequest} for PutObject operation.
     * @param filePath    The local file path.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutObjectResult} of the PutObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutObjectResult>  putObjectFromFileAsync(PutObjectRequest request, Path filePath, OperationOptions options) {
        CompletableFuture<PutObjectResult> future = new CompletableFuture<>();
        try {
            final FileChannel fileChannel = FileChannel.open(filePath);
            CompletableFuture<PutObjectResult> resultFuture = putObjectAsync(
                    request.toBuilder().body(BinaryData.fromByteChannel(fileChannel, fileChannel.size())).build(),
                    options
            );
            resultFuture.whenComplete((result, exception) -> {
                IOUtils.closeQuietly(fileChannel);
                if (exception != null) {
                    future.completeExceptionally(exception.getCause());
                    return;
                }
                future.complete(result);
            });
        } catch (Exception e) {
            future.completeExceptionally(new OperationException("PutObject", e));
        }
        return future;
    }

    /**
     * Downloads a object into the local file.
     *
     * @param request  A {@link GetObjectRequest} for GetObject operation.
     * @param filePath The local file path.
     * @return A Java Future containing the {@link GetObjectResult} of the PutObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetObjectResult> getObjectToFileAsync(GetObjectRequest request, final Path filePath) {
        return getObjectToFileAsync(request, filePath, OperationOptions.defaults());
    }

    /**
     * Downloads a object into the local file.
     *
     * @param request A {@link GetObjectRequest} for GetObject operation.
     * @param filePath The local file path.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetObjectResult} of the PutObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetObjectResult> getObjectToFileAsync(GetObjectRequest request, final Path filePath, OperationOptions options) {
        return getObjectAsync(request.toBuilder()
                .dataConsumerSupplier(new BinaryDataConsumerSupplier() {

                    @Override
                    public Object get() {
                        try {
                            return FileChannel.open(filePath,
                                    StandardOpenOption.CREATE,
                                    StandardOpenOption.TRUNCATE_EXISTING,
                                    StandardOpenOption.WRITE);
                        } catch (IOException e) {
                            throw new RuntimeException("Open channel fail.", e);
                        }
                    }

                    @Override
                    public boolean isReplayable() {
                        return true;
                    }

                    @Override
                    public boolean autoRelease() {
                        return true;
                    }
                })
                .build(), options).thenApply(x -> x.toBuilder().innerBody(null).build());
    }


    //-----------------------------------------------------------------------

    // processObject api
    /**
     * Applies process on the specified image file.
     *
     * @param request A {@link ProcessObjectRequest} for ProcessObject operation.
     * @return A Java Future containing the {@link ProcessObjectResult} of the ProcessObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ProcessObjectResult> processObjectAsync(ProcessObjectRequest request) {
        return processObjectAsync(request, OperationOptions.defaults());
    }

    /**
     * Applies process on the specified image file.
     *
     * @param request A {@link ProcessObjectRequest} for ProcessObject operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link ProcessObjectResult} of the ProcessObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ProcessObjectResult> processObjectAsync(ProcessObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Applies async process on the specified image file.
     *
     * @param request A {@link AsyncProcessObjectRequest} for AsyncProcessObject operation.
     * @return A Java Future containing the {@link AsyncProcessObjectResult} of the AsyncProcessObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<AsyncProcessObjectResult> asyncProcessObjectAsync(AsyncProcessObjectRequest request) {
        return asyncProcessObjectAsync(request, OperationOptions.defaults());
    }

    /**
     * Applies async process on the specified image file.
     *
     * @param request A {@link AsyncProcessObjectRequest} for AsyncProcessObject operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link AsyncProcessObjectResult} of the AsyncProcessObject operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<AsyncProcessObjectResult> asyncProcessObjectAsync(AsyncProcessObjectRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------
}
