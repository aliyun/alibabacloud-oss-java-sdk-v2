package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.operations.*;
import com.aliyun.sdk.service.oss2.operations.BucketPolicy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

/**
 * Internal implementation of {@link OSSClient}.
 */
public class DefaultOSSClient implements OSSClient {

    final ClientImpl clientImpl;

    public DefaultOSSClient(ClientConfiguration config) {
        this(config, new ArrayList<>());
    }

    @SafeVarargs
    public DefaultOSSClient(ClientConfiguration config, Function<ClientOptions, ClientOptions>... optFns) {
        this(config, Arrays.asList(optFns));
    }

    private DefaultOSSClient(ClientConfiguration config, Collection<Function<ClientOptions, ClientOptions>> optFns) {
        this.clientImpl = new ClientImpl(config, optFns);
    }

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
    public void close() throws Exception {
        this.clientImpl.close();
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
    public PutBucketAclResult putBucketAcl(PutBucketAclRequest request, OperationOptions options) {
        return BucketAcl.putBucketAcl(this.clientImpl, request, options);
    }

    @Override
    public GetBucketAclResult getBucketAcl(GetBucketAclRequest request, OperationOptions options) {
        return BucketAcl.getBucketAcl(this.clientImpl, request, options);
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
}
