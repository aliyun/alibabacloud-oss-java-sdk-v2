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
 * Internal implementation of {@link OSSDualClient}.
 */
public class DefaultOSSDualClient implements OSSDualClient {
    final ClientImpl clientImpl;

    public DefaultOSSDualClient(ClientConfiguration config) {
        this(config, new ArrayList<>());
    }

    @SafeVarargs
    public DefaultOSSDualClient(ClientConfiguration config, Function<ClientOptions, ClientOptions>... optFns) {
        this(config, Arrays.asList(optFns));
    }

    private DefaultOSSDualClient(ClientConfiguration config, Collection<Function<ClientOptions, ClientOptions>> optFns) {
        this.clientImpl = new ClientImpl(config, optFns);
    }

    @Override
    public void close() throws Exception {
        this.clientImpl.close();
    }

    // sync operation

    @Override
    public OperationOutput invokeOperation(OperationInput input, OperationOptions opts) {
        return this.clientImpl.execute(input, opts);
    }

    @Override
    public ListBucketsResult listBuckets(ListBucketsRequest request, OperationOptions options) {
        return Service.listBuckets(this.clientImpl, request, options);
    }

    @Override
    public DescribeRegionsResult describeRegions(DescribeRegionsRequest request, OperationOptions options) {
        return Region.describeRegions(this.clientImpl, request, options);
    }

    @Override
    public PutObjectResult putObject(PutObjectRequest request, OperationOptions options) {
        return ObjectBasic.putObject(this.clientImpl, request, options);
    }

    @Override
    public CopyObjectResult copyObject(CopyObjectRequest request, OperationOptions options) {
        return ObjectBasic.copyObject(this.clientImpl, request, options);
    }

    @Override
    public GetObjectResult getObject(GetObjectRequest request, OperationOptions options) {
        return ObjectBasic.getObject(this.clientImpl, request, options);
    }

    @Override
    public AppendObjectResult appendObject(AppendObjectRequest request, OperationOptions options) {
        return ObjectBasic.appendObject(this.clientImpl, request, options);
    }

    @Override
    public DeleteObjectResult deleteObject(DeleteObjectRequest request, OperationOptions options) {
        return ObjectBasic.deleteObject(this.clientImpl, request, options);
    }

    @Override
    public DeleteMultipleObjectsResult deleteMultipleObjects(DeleteMultipleObjectsRequest request, OperationOptions options) {
        return ObjectBasic.deleteMultipleObjects(this.clientImpl, request, options);
    }

    @Override
    public HeadObjectResult headObject(HeadObjectRequest request, OperationOptions options) {
        return ObjectBasic.headObject(this.clientImpl, request, options);
    }

    @Override
    public GetObjectMetaResult getObjectMeta(GetObjectMetaRequest request, OperationOptions options) {
        return ObjectBasic.getObjectMeta(this.clientImpl, request, options);
    }

    @Override
    public RestoreObjectResult restoreObject(RestoreObjectRequest request, OperationOptions options) {
        return ObjectBasic.restoreObject(this.clientImpl, request, options);
    }

    @Override
    public CleanRestoredObjectResult cleanRestoredObject(CleanRestoredObjectRequest request, OperationOptions options) {
        return ObjectBasic.cleanRestoredObject(this.clientImpl, request, options);
    }

    @Override
    public PutObjectAclResult putObjectAcl(PutObjectAclRequest request, OperationOptions options) {
        return ObjectAcl.putObjectAcl(this.clientImpl, request, options);
    }

    @Override
    public GetObjectAclResult getObjectAcl(GetObjectAclRequest request, OperationOptions options) {
        return ObjectAcl.getObjectAcl(this.clientImpl, request, options);
    }

    @Override
    public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest request, OperationOptions options) {
        return ObjectMultipart.initiateMultipartUpload(this.clientImpl, request, options);
    }

    @Override
    public UploadPartResult uploadPart(UploadPartRequest request, OperationOptions options) {
        return ObjectMultipart.uploadPart(this.clientImpl, request, options);
    }

    @Override
    public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest request, OperationOptions options) {
        return ObjectMultipart.completeMultipartUpload(this.clientImpl, request, options);
    }

    @Override
    public UploadPartCopyResult uploadPartCopy(UploadPartCopyRequest request, OperationOptions options) {
        return ObjectMultipart.uploadPartCopy(this.clientImpl, request, options);
    }

    @Override
    public AbortMultipartUploadResult abortMultipartUpload(AbortMultipartUploadRequest request, OperationOptions options) {
        return ObjectMultipart.abortMultipartUpload(this.clientImpl, request, options);
    }

    @Override
    public ListMultipartUploadsResult listMultipartUploads(ListMultipartUploadsRequest request, OperationOptions options) {
        return ObjectMultipart.listMultipartUploads(this.clientImpl, request, options);
    }

    @Override
    public ListPartsResult listParts(ListPartsRequest request, OperationOptions options) {
        return ObjectMultipart.listParts(this.clientImpl, request, options);
    }

    @Override
    public PutSymlinkResult putSymlink(PutSymlinkRequest request, OperationOptions options) {
        return ObjectSymlink.putSymlink(this.clientImpl, request, options);
    }

    @Override
    public GetSymlinkResult getSymlink(GetSymlinkRequest request, OperationOptions options) {
        return ObjectSymlink.getSymlink(this.clientImpl, request, options);
    }

    @Override
    public PutObjectTaggingResult putObjectTagging(PutObjectTaggingRequest request, OperationOptions options) {
        return ObjectTagging.putObjectTagging(this.clientImpl, request, options);
    }

    @Override
    public GetObjectTaggingResult getObjectTagging(GetObjectTaggingRequest request, OperationOptions options) {
        return ObjectTagging.getObjectTagging(this.clientImpl, request, options);
    }

    @Override
    public DeleteObjectTaggingResult deleteObjectTagging(DeleteObjectTaggingRequest request, OperationOptions options) {
        return ObjectTagging.deleteObjectTagging(this.clientImpl, request, options);
    }

    @Override
    public GetBucketStatResult getBucketStat(GetBucketStatRequest request, OperationOptions options) {
        return BucketBasic.getBucketStat(this.clientImpl, request, options);
    }

    @Override
    public PutBucketResult putBucket(PutBucketRequest request, OperationOptions options) {
        return BucketBasic.putBucket(this.clientImpl, request, options);
    }

    @Override
    public DeleteBucketResult deleteBucket(DeleteBucketRequest request, OperationOptions options) {
        return BucketBasic.deleteBucket(this.clientImpl, request, options);
    }

    @Override
    public ListObjectsResult listObjects(ListObjectsRequest request, OperationOptions options) {
        return BucketBasic.listObjects(this.clientImpl, request, options);
    }


    @Override
    public ListObjectsV2Result listObjectsV2(ListObjectsV2Request request, OperationOptions options) {
        return BucketBasic.listObjectsV2(this.clientImpl, request, options);
    }

    @Override
    public GetBucketInfoResult getBucketInfo(GetBucketInfoRequest request, OperationOptions options) {
        return BucketBasic.getBucketInfo(this.clientImpl, request, options);
    }

    @Override
    public GetBucketLocationResult getBucketLocation(GetBucketLocationRequest request, OperationOptions options) {
        return BucketBasic.getBucketLocation(this.clientImpl, request, options);
    }

    @Override
    public PutBucketCorsResult putBucketCors(PutBucketCorsRequest request, OperationOptions options) {
        return BucketCors.putBucketCors(this.clientImpl, request, options);
    }

    @Override
    public GetBucketCorsResult getBucketCors(GetBucketCorsRequest request, OperationOptions options) {
        return BucketCors.getBucketCors(this.clientImpl, request, options);
    }

    @Override
    public DeleteBucketCorsResult deleteBucketCors(DeleteBucketCorsRequest request, OperationOptions options) {
        return BucketCors.deleteBucketCors(this.clientImpl, request, options);
    }

    @Override
    public OptionObjectResult optionObject(OptionObjectRequest request, OperationOptions options) {
        return BucketCors.optionObject(this.clientImpl, request, options);
    }

    @Override
    public ProcessObjectResult processObject(ProcessObjectRequest request, OperationOptions options) {
        return ProcessObject.processObject(this.clientImpl, request, options);
    }

    @Override
    public AsyncProcessObjectResult asyncProcessObject(AsyncProcessObjectRequest request, OperationOptions options) {
        return ProcessObject.asyncProcessObject(this.clientImpl, request, options);
    }

    // async operation

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
    public CompletableFuture<DeleteObjectResult> deleteObjectAsync(DeleteObjectRequest request, OperationOptions options) {
        return ObjectBasic.deleteObjectAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteMultipleObjectsResult> deleteMultipleObjectsAsync(DeleteMultipleObjectsRequest request, OperationOptions options) {
        return ObjectBasic.deleteMultipleObjectsAsync(this.clientImpl, request, options);
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
    public PresignResult presign(GetObjectRequest request, PresignOptions options) {
        return Presigner.getObject(this.clientImpl, request, options);
    }

    @Override
    public PresignResult presign(PutObjectRequest request, PresignOptions options) {
        return Presigner.putObject(this.clientImpl, request, options);
    }

    @Override
    public PresignResult presign(HeadObjectRequest request, PresignOptions options) {
        return Presigner.headObject(this.clientImpl, request, options);
    }

    @Override
    public PresignResult presign(InitiateMultipartUploadRequest request, PresignOptions options) {
        return Presigner.initiateMultipartUpload(this.clientImpl, request, options);
    }

    @Override
    public PresignResult presign(UploadPartRequest request, PresignOptions options) {
        return Presigner.uploadPart(this.clientImpl, request, options);
    }

    @Override
    public PresignResult presign(CompleteMultipartUploadRequest request, PresignOptions options) {
        return Presigner.completeMultipartUpload(this.clientImpl, request, options);
    }

    @Override
    public PresignResult presign(AbortMultipartUploadRequest request, PresignOptions options) {
        return Presigner.abortMultipartUpload(this.clientImpl, request, options);
    }

    @Override
    public PutBucketVersioningResult putBucketVersioning(PutBucketVersioningRequest request, OperationOptions options) {
        return BucketVersioning.putBucketVersioning(this.clientImpl, request, options);
    }

    @Override
    public GetBucketVersioningResult getBucketVersioning(GetBucketVersioningRequest request, OperationOptions options) {
        return BucketVersioning.getBucketVersioning(this.clientImpl, request, options);
    }

    @Override
    public ListObjectVersionsResult listObjectVersions(ListObjectVersionsRequest request, OperationOptions options) {
        return BucketVersioning.listObjectVersions(this.clientImpl, request, options);
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
    public PutBucketAclResult putBucketAcl(PutBucketAclRequest request, OperationOptions options) {
        return BucketAcl.putBucketAcl(this.clientImpl, request, options);
    }

    @Override
    public GetBucketAclResult getBucketAcl(GetBucketAclRequest request, OperationOptions options) {
        return BucketAcl.getBucketAcl(this.clientImpl, request, options);
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
    public CreateBucketDataRedundancyTransitionResult createBucketDataRedundancyTransition(CreateBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        return BucketRedundancyTransition.createBucketDataRedundancyTransition(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<CreateBucketDataRedundancyTransitionResult> createBucketDataRedundancyTransitionAsync(CreateBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        return BucketRedundancyTransition.createBucketDataRedundancyTransitionAsync(this.clientImpl, request, options);
    }

    @Override
    public GetBucketDataRedundancyTransitionResult getBucketDataRedundancyTransition(GetBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        return BucketRedundancyTransition.getBucketDataRedundancyTransition(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetBucketDataRedundancyTransitionResult> getBucketDataRedundancyTransitionAsync(GetBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        return BucketRedundancyTransition.getBucketDataRedundancyTransitionAsync(this.clientImpl, request, options);
    }

    @Override
    public DeleteBucketDataRedundancyTransitionResult deleteBucketDataRedundancyTransition(DeleteBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        return BucketRedundancyTransition.deleteBucketDataRedundancyTransition(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteBucketDataRedundancyTransitionResult> deleteBucketDataRedundancyTransitionAsync(DeleteBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        return BucketRedundancyTransition.deleteBucketDataRedundancyTransitionAsync(this.clientImpl, request, options);
    }

    @Override
    public ListBucketDataRedundancyTransitionResult listBucketDataRedundancyTransition(ListBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        return BucketRedundancyTransition.listBucketDataRedundancyTransition(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<ListBucketDataRedundancyTransitionResult> listBucketDataRedundancyTransitionAsync(ListBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        return BucketRedundancyTransition.listBucketDataRedundancyTransitionAsync(this.clientImpl, request, options);
    }

    @Override
    public ListUserDataRedundancyTransitionResult listUserDataRedundancyTransition(ListUserDataRedundancyTransitionRequest request, OperationOptions options) {
        return BucketRedundancyTransition.listUserDataRedundancyTransition(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<ListUserDataRedundancyTransitionResult> listUserDataRedundancyTransitionAsync(ListUserDataRedundancyTransitionRequest request, OperationOptions options) {
        return BucketRedundancyTransition.listUserDataRedundancyTransitionAsync(this.clientImpl, request, options);
    }
  
    @Override
    public PutBucketEncryptionResult putBucketEncryption(PutBucketEncryptionRequest request, OperationOptions options) {
        return BucketEncryption.putBucketEncryption(this.clientImpl, request, options);
    }

    @Override
    public GetBucketEncryptionResult getBucketEncryption(GetBucketEncryptionRequest request, OperationOptions options) {
        return BucketEncryption.getBucketEncryption(this.clientImpl, request, options);
    }

    @Override
    public DeleteBucketEncryptionResult deleteBucketEncryption(DeleteBucketEncryptionRequest request, OperationOptions options) {
        return BucketEncryption.deleteBucketEncryption(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutBucketEncryptionResult> putBucketEncryptionAsync(PutBucketEncryptionRequest request, OperationOptions options) {
        return BucketEncryption.putBucketEncryptionAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetBucketEncryptionResult> getBucketEncryptionAsync(GetBucketEncryptionRequest request, OperationOptions options) {
        return BucketEncryption.getBucketEncryptionAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteBucketEncryptionResult> deleteBucketEncryptionAsync(DeleteBucketEncryptionRequest request, OperationOptions options) {
        return BucketEncryption.deleteBucketEncryptionAsync(this.clientImpl, request, options);
    }
  
    @Override
    public GetBucketResourceGroupResult getBucketResourceGroup(GetBucketResourceGroupRequest request, OperationOptions options) {
        return BucketResourceGroup.getBucketResourceGroup(this.clientImpl, request, options);
    }

    @Override
    public PutBucketResourceGroupResult putBucketResourceGroup(PutBucketResourceGroupRequest request, OperationOptions options) {
        return BucketResourceGroup.putBucketResourceGroup(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetBucketResourceGroupResult> getBucketResourceGroupAsync(GetBucketResourceGroupRequest request, OperationOptions options) {
        return BucketResourceGroup.getBucketResourceGroupAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutBucketResourceGroupResult> putBucketResourceGroupAsync(PutBucketResourceGroupRequest request, OperationOptions options) {
        return BucketResourceGroup.putBucketResourceGroupAsync(this.clientImpl, request, options);
    }

    @Override
    public PutBucketTagsResult putBucketTags(PutBucketTagsRequest request, OperationOptions options) {
        return BucketTags.putBucketTags(this.clientImpl, request, options);
    }

    @Override
    public GetBucketTagsResult getBucketTags(GetBucketTagsRequest request, OperationOptions options) {
        return BucketTags.getBucketTags(this.clientImpl, request, options);
    }

    @Override
    public DeleteBucketTagsResult deleteBucketTags(DeleteBucketTagsRequest request, OperationOptions options) {
        return BucketTags.deleteBucketTags(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutBucketTagsResult> putBucketTagsAsync(PutBucketTagsRequest request, OperationOptions options) {
        return BucketTags.putBucketTagsAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetBucketTagsResult> getBucketTagsAsync(GetBucketTagsRequest request, OperationOptions options) {
        return BucketTags.getBucketTagsAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteBucketTagsResult> deleteBucketTagsAsync(DeleteBucketTagsRequest request, OperationOptions options) {
        return BucketTags.deleteBucketTagsAsync(this.clientImpl, request, options);
    }
  
    @Override
    public PutBucketAccessMonitorResult putBucketAccessMonitor(PutBucketAccessMonitorRequest request, OperationOptions options) {
        return BucketAccessmonitor.putBucketAccessMonitor(this.clientImpl, request, options);
    }

    @Override
    public GetBucketAccessMonitorResult getBucketAccessMonitor(GetBucketAccessMonitorRequest request, OperationOptions options) {
        return BucketAccessmonitor.getBucketAccessMonitor(this.clientImpl, request, options);
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
    public PutBucketRefererResult putBucketReferer(PutBucketRefererRequest request, OperationOptions options) {
        return BucketReferer.putBucketReferer(this.clientImpl, request, options);
    }

    @Override
    public GetBucketRefererResult getBucketReferer(GetBucketRefererRequest request, OperationOptions options) {
        return BucketReferer.getBucketReferer(this.clientImpl, request, options);
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
    public PutPublicAccessBlockResult putPublicAccessBlock(PutPublicAccessBlockRequest request, OperationOptions options) {
        return PublicAccessBlock.putPublicAccessBlock(this.clientImpl, request, options);
    }

    @Override
    public GetPublicAccessBlockResult getPublicAccessBlock(GetPublicAccessBlockRequest request, OperationOptions options) {
        return PublicAccessBlock.getPublicAccessBlock(this.clientImpl, request, options);
    }

    @Override
    public DeletePublicAccessBlockResult deletePublicAccessBlock(DeletePublicAccessBlockRequest request, OperationOptions options) {
        return PublicAccessBlock.deletePublicAccessBlock(this.clientImpl, request, options);
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
    public PutBucketPublicAccessBlockResult putBucketPublicAccessBlock(PutBucketPublicAccessBlockRequest request, OperationOptions options) {
        return BucketPublicAccessBlock.putBucketPublicAccessBlock(this.clientImpl, request, options);
    }

    @Override
    public GetBucketPublicAccessBlockResult getBucketPublicAccessBlock(GetBucketPublicAccessBlockRequest request, OperationOptions options) {
        return BucketPublicAccessBlock.getBucketPublicAccessBlock(this.clientImpl, request, options);
    }

    @Override
    public DeleteBucketPublicAccessBlockResult deleteBucketPublicAccessBlock(DeleteBucketPublicAccessBlockRequest request, OperationOptions options) {
        return BucketPublicAccessBlock.deleteBucketPublicAccessBlock(this.clientImpl, request, options);
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
    public PutBucketPolicyResult putBucketPolicy(PutBucketPolicyRequest request, OperationOptions options) {
        return BucketPolicy.putBucketPolicy(this.clientImpl, request, options);
    }

    @Override
    public GetBucketPolicyResult getBucketPolicy(GetBucketPolicyRequest request, OperationOptions options) {
        return BucketPolicy.getBucketPolicy(this.clientImpl, request, options);
    }

    @Override
    public DeleteBucketPolicyResult deleteBucketPolicy(DeleteBucketPolicyRequest request, OperationOptions options) {
        return BucketPolicy.deleteBucketPolicy(this.clientImpl, request, options);
    }

    @Override
    public GetBucketPolicyStatusResult getBucketPolicyStatus(GetBucketPolicyStatusRequest request, OperationOptions options) {
        return BucketPolicy.getBucketPolicyStatus(this.clientImpl, request, options);
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
    public PutBucketLoggingResult putBucketLogging(PutBucketLoggingRequest request, OperationOptions options) {
        return BucketLogging.putBucketLogging(this.clientImpl, request, options);
    }

    @Override
    public GetBucketLoggingResult getBucketLogging(GetBucketLoggingRequest request, OperationOptions options) {
        return BucketLogging.getBucketLogging(this.clientImpl, request, options);
    }

    @Override
    public DeleteBucketLoggingResult deleteBucketLogging(DeleteBucketLoggingRequest request, OperationOptions options) {
        return BucketLogging.deleteBucketLogging(this.clientImpl, request, options);
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
    public PutUserDefinedLogFieldsConfigResult putUserDefinedLogFieldsConfig(PutUserDefinedLogFieldsConfigRequest request, OperationOptions options) {
        return BucketLogging.putUserDefinedLogFieldsConfig(this.clientImpl, request, options);
    }

    @Override
    public GetUserDefinedLogFieldsConfigResult getUserDefinedLogFieldsConfig(GetUserDefinedLogFieldsConfigRequest request, OperationOptions options) {
        return BucketLogging.getUserDefinedLogFieldsConfig(this.clientImpl, request, options);
    }

    @Override
    public DeleteUserDefinedLogFieldsConfigResult deleteUserDefinedLogFieldsConfig(DeleteUserDefinedLogFieldsConfigRequest request, OperationOptions options) {
        return BucketLogging.deleteUserDefinedLogFieldsConfig(this.clientImpl, request, options);
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
    public SealAppendObjectResult sealAppendObject(SealAppendObjectRequest request, OperationOptions options) {
        return ObjectBasic.sealAppendObject(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<SealAppendObjectResult> sealAppendObjectAsync(SealAppendObjectRequest request, OperationOptions options) {
        return ObjectBasic.sealAppendObjectAsync(this.clientImpl, request, options);
    }
}
