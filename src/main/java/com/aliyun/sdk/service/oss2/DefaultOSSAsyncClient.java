package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.operations.*;
import com.aliyun.sdk.service.oss2.operations.BucketPolicy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Internal implementation of {@link OSSAsyncClient}.
 */
public class DefaultOSSAsyncClient implements OSSAsyncClient {

    private final ClientImpl clientImpl;

    public DefaultOSSAsyncClient(ClientConfiguration config) {
        this(config, new ArrayList<>());
    }

    @SafeVarargs
    public DefaultOSSAsyncClient(ClientConfiguration config, Function<ClientOptions, ClientOptions>... optFns) {
        this(config, Arrays.asList(optFns));
    }

    private DefaultOSSAsyncClient(ClientConfiguration config, Collection<Function<ClientOptions, ClientOptions>> optFns) {
        this.clientImpl = new ClientImpl(config, optFns);
    }

    @Override
    public void close() throws Exception {
        this.clientImpl.close();
    }

    @Override
    public CompletableFuture<OperationOutput> invokeOperationAsync(OperationInput input, OperationOptions opts) {
        return this.clientImpl.executeAsync(input, opts);
    }

    @Override
    public CompletableFuture<ListBucketsResult> listBucketsAsync(ListBucketsRequest request, OperationOptions options) {
        return Service.listBucketsAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DescribeRegionsResult> describeRegionsAsync(DescribeRegionsRequest request, OperationOptions options) {
        return Region.describeRegionsAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutObjectResult> putObjectAsync(PutObjectRequest request, OperationOptions options) {
        return ObjectBasic.putObjectAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<CopyObjectResult> copyObjectAsync(CopyObjectRequest request, OperationOptions options) {
        return ObjectBasic.copyObjectAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetObjectResult> getObjectAsync(GetObjectRequest request, OperationOptions options) {
        return ObjectBasic.getObjectAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<AppendObjectResult> appendObjectAsync(AppendObjectRequest request, OperationOptions options) {
        return ObjectBasic.appendObjectAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteMultipleObjectsResult> deleteMultipleObjectsAsync(DeleteMultipleObjectsRequest request, OperationOptions options) {
        return ObjectBasic.deleteMultipleObjectsAsync(this.clientImpl, request, options);
    }


    @Override
    public CompletableFuture<DeleteObjectResult> deleteObjectAsync(DeleteObjectRequest request, OperationOptions options) {
        return ObjectBasic.deleteObjectAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<HeadObjectResult> headObjectAsync(HeadObjectRequest request, OperationOptions options) {
        return ObjectBasic.headObjectAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetObjectMetaResult> getObjectMetaAsync(GetObjectMetaRequest request, OperationOptions options) {
        return ObjectBasic.getObjectMetaAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<RestoreObjectResult> restoreObjectAsync(RestoreObjectRequest request, OperationOptions options) {
        return ObjectBasic.restoreObjectAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<CleanRestoredObjectResult> cleanRestoredObjectAsync(CleanRestoredObjectRequest request, OperationOptions options) {
        return ObjectBasic.cleanRestoredObjectAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutObjectAclResult> putObjectAclAsync(PutObjectAclRequest request, OperationOptions options) {
        return ObjectAcl.putObjectAclAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetObjectAclResult> getObjectAclAsync(GetObjectAclRequest request, OperationOptions options) {
        return ObjectAcl.getObjectAclAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<InitiateMultipartUploadResult> initiateMultipartUploadAsync(InitiateMultipartUploadRequest request, OperationOptions options) {
        return ObjectMultipart.initiateMultipartUploadAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<UploadPartResult> uploadPartAsync(UploadPartRequest request, OperationOptions options) {
        return ObjectMultipart.uploadPartAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<CompleteMultipartUploadResult> completeMultipartUploadAsync(CompleteMultipartUploadRequest request, OperationOptions options) {
        return ObjectMultipart.completeMultipartUploadAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<UploadPartCopyResult> uploadPartCopyAsync(UploadPartCopyRequest request, OperationOptions options) {
        return ObjectMultipart.uploadPartCopyAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<AbortMultipartUploadResult> abortMultipartUploadAsync(AbortMultipartUploadRequest request, OperationOptions options) {
        return ObjectMultipart.abortMultipartUploadAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<ListMultipartUploadsResult> listMultipartUploadsAsync(ListMultipartUploadsRequest request, OperationOptions options) {
        return ObjectMultipart.listMultipartUploadsAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<ListPartsResult> listPartsAsync(ListPartsRequest request, OperationOptions options) {
        return ObjectMultipart.listPartsAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutSymlinkResult> putSymlinkAsync(PutSymlinkRequest request, OperationOptions options) {
        return ObjectSymlink.putSymlinkAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetSymlinkResult> getSymlinkAsync(GetSymlinkRequest request, OperationOptions options) {
        return ObjectSymlink.getSymlinkAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutObjectTaggingResult> putObjectTaggingAsync(PutObjectTaggingRequest request, OperationOptions options) {
        return ObjectTagging.putObjectTaggingAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetObjectTaggingResult> getObjectTaggingAsync(GetObjectTaggingRequest request, OperationOptions options) {
        return ObjectTagging.getObjectTaggingAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteObjectTaggingResult> deleteObjectTaggingAsync(DeleteObjectTaggingRequest request, OperationOptions options) {
        return ObjectTagging.deleteObjectTaggingAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetBucketStatResult> getBucketStatAsync(GetBucketStatRequest request, OperationOptions options) {
        return BucketBasic.getBucketStatAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutBucketResult> putBucketAsync(PutBucketRequest request, OperationOptions options) {
        return BucketBasic.putBucketAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteBucketResult> deleteBucketAsync(DeleteBucketRequest request, OperationOptions options) {
        return BucketBasic.deleteBucketAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<ListObjectsResult> listObjectsAsync(ListObjectsRequest request, OperationOptions options) {
        return BucketBasic.listObjectsAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<ListObjectsV2Result> listObjectsV2Async(ListObjectsV2Request request, OperationOptions options) {
        return BucketBasic.listObjectsV2Async(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetBucketInfoResult> getBucketInfoAsync(GetBucketInfoRequest request, OperationOptions options) {
        return BucketBasic.getBucketInfoAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetBucketLocationResult> getBucketLocationAsync(GetBucketLocationRequest request, OperationOptions options) {
        return BucketBasic.getBucketLocationAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutBucketCorsResult> putBucketCorsAsync(PutBucketCorsRequest request, OperationOptions options) {
        return BucketCors.putBucketCorsAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetBucketCorsResult> getBucketCorsAsync(GetBucketCorsRequest request, OperationOptions options) {
        return BucketCors.getBucketCorsAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteBucketCorsResult> deleteBucketCorsAsync(DeleteBucketCorsRequest request, OperationOptions options) {
        return BucketCors.deleteBucketCorsAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<OptionObjectResult> optionObjectAsync(OptionObjectRequest request, OperationOptions options) {
        return BucketCors.optionObjectAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutBucketVersioningResult> putBucketVersioningAsync(PutBucketVersioningRequest request, OperationOptions options) {
        return BucketVersioning.putBucketVersioningAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetBucketVersioningResult> getBucketVersioningAsync(GetBucketVersioningRequest request, OperationOptions options) {
        return BucketVersioning.getBucketVersioningAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<ListObjectVersionsResult> listObjectVersionsAsync(ListObjectVersionsRequest request, OperationOptions options) {
        return BucketVersioning.listObjectVersionsAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutBucketAclResult> putBucketAclAsync(PutBucketAclRequest request, OperationOptions options) {
        return BucketAcl.putBucketAclAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetBucketAclResult> getBucketAclAsync(GetBucketAclRequest request, OperationOptions options) {
        return BucketAcl.getBucketAclAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutBucketAccessMonitorResult> putBucketAccessMonitorAsync(PutBucketAccessMonitorRequest request, OperationOptions options) {
        return BucketAccessmonitor.putBucketAccessMonitorAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetBucketAccessMonitorResult> getBucketAccessMonitorAsync(GetBucketAccessMonitorRequest request, OperationOptions options) {
        return BucketAccessmonitor.getBucketAccessMonitorAsync(this.clientImpl, request, options);
    }
  
    @Override
    public CompletableFuture<PutBucketRefererResult> putBucketRefererAsync(PutBucketRefererRequest request, OperationOptions options) {
        return BucketReferer.putBucketRefererAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetBucketRefererResult> getBucketRefererAsync(GetBucketRefererRequest request, OperationOptions options) {
        return BucketReferer.getBucketRefererAsync(this.clientImpl, request, options);
    }
  
    @Override
    public CompletableFuture<PutPublicAccessBlockResult> putPublicAccessBlockAsync(PutPublicAccessBlockRequest request, OperationOptions options) {
        return PublicAccessBlock.putPublicAccessBlockAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetPublicAccessBlockResult> getPublicAccessBlockAsync(GetPublicAccessBlockRequest request, OperationOptions options) {
        return PublicAccessBlock.getPublicAccessBlockAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeletePublicAccessBlockResult> deletePublicAccessBlockAsync(DeletePublicAccessBlockRequest request, OperationOptions options) {
        return PublicAccessBlock.deletePublicAccessBlockAsync(this.clientImpl, request, options);
    }
  
    @Override
    public CompletableFuture<PutBucketPublicAccessBlockResult> putBucketPublicAccessBlockAsync(PutBucketPublicAccessBlockRequest request, OperationOptions options) {
        return BucketPublicAccessBlock.putBucketPublicAccessBlockAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetBucketPublicAccessBlockResult> getBucketPublicAccessBlockAsync(GetBucketPublicAccessBlockRequest request, OperationOptions options) {
        return BucketPublicAccessBlock.getBucketPublicAccessBlockAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteBucketPublicAccessBlockResult> deleteBucketPublicAccessBlockAsync(DeleteBucketPublicAccessBlockRequest request, OperationOptions options) {
        return BucketPublicAccessBlock.deleteBucketPublicAccessBlockAsync(this.clientImpl, request, options);
    }
  
    @Override
    public CompletableFuture<PutBucketPolicyResult> putBucketPolicyAsync(PutBucketPolicyRequest request, OperationOptions options) {
        return BucketPolicy.putBucketPolicyAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetBucketPolicyResult> getBucketPolicyAsync(GetBucketPolicyRequest request, OperationOptions options) {
        return BucketPolicy.getBucketPolicyAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteBucketPolicyResult> deleteBucketPolicyAsync(DeleteBucketPolicyRequest request, OperationOptions options) {
        return BucketPolicy.deleteBucketPolicyAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetBucketPolicyStatusResult> getBucketPolicyStatusAsync(GetBucketPolicyStatusRequest request, OperationOptions options) {
        return BucketPolicy.getBucketPolicyStatusAsync(this.clientImpl, request, options);
    }
  
    @Override
    public CompletableFuture<PutBucketLoggingResult> putBucketLoggingAsync(PutBucketLoggingRequest request, OperationOptions options) {
        return BucketLogging.putBucketLoggingAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetBucketLoggingResult> getBucketLoggingAsync(GetBucketLoggingRequest request, OperationOptions options) {
        return BucketLogging.getBucketLoggingAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteBucketLoggingResult> deleteBucketLoggingAsync(DeleteBucketLoggingRequest request, OperationOptions options) {
        return BucketLogging.deleteBucketLoggingAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutUserDefinedLogFieldsConfigResult> putUserDefinedLogFieldsConfigAsync(PutUserDefinedLogFieldsConfigRequest request, OperationOptions options) {
        return BucketLogging.putUserDefinedLogFieldsConfigAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetUserDefinedLogFieldsConfigResult> getUserDefinedLogFieldsConfigAsync(GetUserDefinedLogFieldsConfigRequest request, OperationOptions options) {
        return BucketLogging.getUserDefinedLogFieldsConfigAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteUserDefinedLogFieldsConfigResult> deleteUserDefinedLogFieldsConfigAsync(DeleteUserDefinedLogFieldsConfigRequest request, OperationOptions options) {
        return BucketLogging.deleteUserDefinedLogFieldsConfigAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<ProcessObjectResult> processObjectAsync(ProcessObjectRequest request, OperationOptions options) {
        return ProcessObject.processObjectAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<AsyncProcessObjectResult> asyncProcessObjectAsync(AsyncProcessObjectRequest request, OperationOptions options) {
        return ProcessObject.asyncProcessObjectAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<SealAppendObjectResult> sealAppendObjectAsync(SealAppendObjectRequest request, OperationOptions options) {
        return ObjectBasic.sealAppendObjectAsync(this.clientImpl, request, options);
    }
}
