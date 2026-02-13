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
    public CreateBucketDataRedundancyTransitionResult createBucketDataRedundancyTransition(CreateBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        return BucketRedundancyTransition.createBucketDataRedundancyTransition(this.clientImpl, request, options);
    }

    @Override
    public GetBucketDataRedundancyTransitionResult getBucketDataRedundancyTransition(GetBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        return BucketRedundancyTransition.getBucketDataRedundancyTransition(this.clientImpl, request, options);
    }

    @Override
    public DeleteBucketDataRedundancyTransitionResult deleteBucketDataRedundancyTransition(DeleteBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        return BucketRedundancyTransition.deleteBucketDataRedundancyTransition(this.clientImpl, request, options);
    }


    @Override
    public ListBucketDataRedundancyTransitionResult listBucketDataRedundancyTransition(ListBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        return BucketRedundancyTransition.listBucketDataRedundancyTransition(this.clientImpl, request, options);
    }

    @Override
    public ListUserDataRedundancyTransitionResult listUserDataRedundancyTransition(ListUserDataRedundancyTransitionRequest request, OperationOptions options) {
        return BucketRedundancyTransition.listUserDataRedundancyTransition(this.clientImpl, request, options);
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
    public PutBucketResourceGroupResult putBucketResourceGroup(PutBucketResourceGroupRequest request, OperationOptions options) {
        return BucketResourceGroup.putBucketResourceGroup(this.clientImpl, request, options);
    }

    @Override
    public GetBucketResourceGroupResult getBucketResourceGroup(GetBucketResourceGroupRequest request, OperationOptions options) {
        return BucketResourceGroup.getBucketResourceGroup(this.clientImpl, request, options);
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
    public PutBucketAccessMonitorResult putBucketAccessMonitor(PutBucketAccessMonitorRequest request, OperationOptions options) {
        return BucketAccessmonitor.putBucketAccessMonitor(this.clientImpl, request, options);
    }

    @Override
    public GetBucketAccessMonitorResult getBucketAccessMonitor(GetBucketAccessMonitorRequest request, OperationOptions options) {
        return BucketAccessmonitor.getBucketAccessMonitor(this.clientImpl, request, options);
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
    public ProcessObjectResult processObject(ProcessObjectRequest request, OperationOptions options) {
        return ProcessObject.processObject(this.clientImpl, request, options);
    }

    @Override
    public AsyncProcessObjectResult asyncProcessObject(AsyncProcessObjectRequest request, OperationOptions options) {
        return ProcessObject.asyncProcessObject(this.clientImpl, request, options);
    }

    @Override
    public SealAppendObjectResult sealAppendObject(SealAppendObjectRequest request, OperationOptions options) {
        return ObjectBasic.sealAppendObject(this.clientImpl, request, options);
    }

    @Override
    public PutBucketTransferAccelerationResult putBucketTransferAcceleration(PutBucketTransferAccelerationRequest request, OperationOptions options) {
        return BucketTransferAcceleration.putBucketTransferAcceleration(this.clientImpl, request, options);
    }

    @Override
    public GetBucketTransferAccelerationResult getBucketTransferAcceleration(GetBucketTransferAccelerationRequest request, OperationOptions options) {
        return BucketTransferAcceleration.getBucketTransferAcceleration(this.clientImpl, request, options);
    }

    @Override
    public PutBucketLifecycleResult putBucketLifecycle(PutBucketLifecycleRequest request, OperationOptions options) {
        return BucketLifecycle.putBucketLifecycle(this.clientImpl, request, options);
    }

    @Override
    public GetBucketLifecycleResult getBucketLifecycle(GetBucketLifecycleRequest request, OperationOptions options) {
        return BucketLifecycle.getBucketLifecycle(this.clientImpl, request, options);
    }

    @Override
    public DeleteBucketLifecycleResult deleteBucketLifecycle(DeleteBucketLifecycleRequest request, OperationOptions options) {
        return BucketLifecycle.deleteBucketLifecycle(this.clientImpl, request, options);
    }

    @Override
    public CreateCnameTokenResult createCnameToken(CreateCnameTokenRequest request, OperationOptions options) {
        return BucketCname.createCnameToken(this.clientImpl, request, options);
    }

    @Override
    public GetCnameTokenResult getCnameToken(GetCnameTokenRequest request, OperationOptions options) {
        return BucketCname.getCnameToken(this.clientImpl, request, options);
    }

    @Override
    public PutCnameResult putCname(PutCnameRequest request, OperationOptions options) {
        return BucketCname.putCname(this.clientImpl, request, options);
    }

    @Override
    public ListCnameResult listCname(ListCnameRequest request, OperationOptions options) {
        return BucketCname.listCname(this.clientImpl, request, options);
    }

    @Override
    public DeleteCnameResult deleteCname(DeleteCnameRequest request, OperationOptions options) {
        return BucketCname.deleteCname(this.clientImpl, request, options);
    }

    @Override
    public PutBucketWebsiteResult putBucketWebsite(PutBucketWebsiteRequest request, OperationOptions options) {
        return BucketWebsite.putBucketWebsite(this.clientImpl, request, options);
    }

    @Override
    public GetBucketWebsiteResult getBucketWebsite(GetBucketWebsiteRequest request, OperationOptions options) {
        return BucketWebsite.getBucketWebsite(this.clientImpl, request, options);
    }

    @Override
    public DeleteBucketWebsiteResult deleteBucketWebsite(DeleteBucketWebsiteRequest request, OperationOptions options) {
        return BucketWebsite.deleteBucketWebsite(this.clientImpl, request, options);
    }

    @Override
    public PutBucketOverwriteConfigResult putBucketOverwriteConfig(PutBucketOverwriteConfigRequest request, OperationOptions options) {
        return BucketOverwriteConfig.putBucketOverwriteConfig(this.clientImpl, request, options);
    }

    @Override
    public GetBucketOverwriteConfigResult getBucketOverwriteConfig(GetBucketOverwriteConfigRequest request, OperationOptions options) {
        return BucketOverwriteConfig.getBucketOverwriteConfig(this.clientImpl, request, options);
    }

    @Override
    public DeleteBucketOverwriteConfigResult deleteBucketOverwriteConfig(DeleteBucketOverwriteConfigRequest request, OperationOptions options) {
        return BucketOverwriteConfig.deleteBucketOverwriteConfig(this.clientImpl, request, options);
    }

    @Override
    public CreateAccessPointResult createAccessPoint(CreateAccessPointRequest request, OperationOptions options) {
        return BucketAccessPoint.createAccessPoint(this.clientImpl, request, options);
    }

    @Override
    public GetAccessPointResult getAccessPoint(GetAccessPointRequest request, OperationOptions options) {
        return BucketAccessPoint.getAccessPoint(this.clientImpl, request, options);
    }

    @Override
    public ListAccessPointsResult listAccessPoints(ListAccessPointsRequest request, OperationOptions options) {
        return BucketAccessPoint.listAccessPoints(this.clientImpl, request, options);
    }

    @Override
    public DeleteAccessPointResult deleteAccessPoint(DeleteAccessPointRequest request, OperationOptions options) {
        return BucketAccessPoint.deleteAccessPoint(this.clientImpl, request, options);
    }

    @Override
    public PutAccessPointPolicyResult putAccessPointPolicy(PutAccessPointPolicyRequest request, OperationOptions options) {
        return BucketAccessPoint.putAccessPointPolicy(this.clientImpl, request, options);
    }

    @Override
    public GetAccessPointPolicyResult getAccessPointPolicy(GetAccessPointPolicyRequest request, OperationOptions options) {
        return BucketAccessPoint.getAccessPointPolicy(this.clientImpl, request, options);
    }

    @Override
    public DeleteAccessPointPolicyResult deleteAccessPointPolicy(DeleteAccessPointPolicyRequest request, OperationOptions options) {
        return BucketAccessPoint.deleteAccessPointPolicy(this.clientImpl, request, options);
    }
  
    @Override
    public PutBucketInventoryResult putBucketInventory(PutBucketInventoryRequest request, OperationOptions options) {
        return BucketInventory.putBucketInventory(this.clientImpl, request, options);
    }

    @Override
    public GetBucketInventoryResult getBucketInventory(GetBucketInventoryRequest request, OperationOptions options) {
        return BucketInventory.getBucketInventory(this.clientImpl, request, options);
    }

    @Override
    public ListBucketInventoryResult listBucketInventory(ListBucketInventoryRequest request, OperationOptions options) {
        return BucketInventory.listBucketInventory(this.clientImpl, request, options);
    }

    @Override
    public DeleteBucketInventoryResult deleteBucketInventory(DeleteBucketInventoryRequest request, OperationOptions options) {
        return BucketInventory.deleteBucketInventory(this.clientImpl, request, options);
    }
  
    @Override
    public OpenMetaQueryResult openMetaQuery(OpenMetaQueryRequest request, OperationOptions options) {
        return BucketMetaquery.openMetaQuery(this.clientImpl, request, options);
    }

    @Override
    public GetMetaQueryStatusResult getMetaQueryStatus(GetMetaQueryStatusRequest request, OperationOptions options) {
        return BucketMetaquery.getMetaQueryStatus(this.clientImpl, request, options);
    }

    @Override
    public DoMetaQueryResult doMetaQuery(DoMetaQueryRequest request, OperationOptions options) {
        return BucketMetaquery.doMetaQuery(this.clientImpl, request, options);
    }

    @Override
    public CloseMetaQueryResult closeMetaQuery(CloseMetaQueryRequest request, OperationOptions options) {
        return BucketMetaquery.closeMetaQuery(this.clientImpl, request, options);
    }
  
    @Override
    public PutBucketArchiveDirectReadResult putBucketArchiveDirectRead(PutBucketArchiveDirectReadRequest request, OperationOptions options) {
        return BucketArchiveDirectRead.putBucketArchiveDirectRead(this.clientImpl, request, options);
    }

    @Override
    public GetBucketArchiveDirectReadResult getBucketArchiveDirectRead(GetBucketArchiveDirectReadRequest request, OperationOptions options) {
        return BucketArchiveDirectRead.getBucketArchiveDirectRead(this.clientImpl, request, options);
    }
  
    @Override
    public InitiateBucketWormResult initiateBucketWorm(InitiateBucketWormRequest request, OperationOptions options) {
        return BucketWorm.initiateBucketWorm(this.clientImpl, request, options);
    }

    @Override
    public AbortBucketWormResult abortBucketWorm(AbortBucketWormRequest request, OperationOptions options) {
        return BucketWorm.abortBucketWorm(this.clientImpl, request, options);
    }

    @Override
    public CompleteBucketWormResult completeBucketWorm(CompleteBucketWormRequest request, OperationOptions options) {
        return BucketWorm.completeBucketWorm(this.clientImpl, request, options);
    }

    @Override
    public ExtendBucketWormResult extendBucketWorm(ExtendBucketWormRequest request, OperationOptions options) {
        return BucketWorm.extendBucketWorm(this.clientImpl, request, options);
    }

    @Override
    public GetBucketWormResult getBucketWorm(GetBucketWormRequest request, OperationOptions options) {
        return BucketWorm.getBucketWorm(this.clientImpl, request, options);
    }

    @Override
    public CreateAccessPointForObjectProcessResult createAccessPointForObjectProcess(CreateAccessPointForObjectProcessRequest request, OperationOptions options) {
        return BucketObjectFcAccessPoint.createAccessPointForObjectProcess(this.clientImpl, request, options);
    }

    @Override
    public GetAccessPointForObjectProcessResult getAccessPointForObjectProcess(GetAccessPointForObjectProcessRequest request, OperationOptions options) {
        return BucketObjectFcAccessPoint.getAccessPointForObjectProcess(this.clientImpl, request, options);
    }

    @Override
    public ListAccessPointsForObjectProcessResult listAccessPointsForObjectProcess(ListAccessPointsForObjectProcessRequest request, OperationOptions options) {
        return BucketObjectFcAccessPoint.listAccessPointsForObjectProcess(this.clientImpl, request, options);
    }

    @Override
    public DeleteAccessPointForObjectProcessResult deleteAccessPointForObjectProcess(DeleteAccessPointForObjectProcessRequest request, OperationOptions options) {
        return BucketObjectFcAccessPoint.deleteAccessPointForObjectProcess(this.clientImpl, request, options);
    }

    @Override
    public GetAccessPointConfigForObjectProcessResult getAccessPointConfigForObjectProcess(GetAccessPointConfigForObjectProcessRequest request, OperationOptions options) {
        return BucketObjectFcAccessPoint.getAccessPointConfigForObjectProcess(this.clientImpl, request, options);
    }

    @Override
    public PutAccessPointConfigForObjectProcessResult putAccessPointConfigForObjectProcess(PutAccessPointConfigForObjectProcessRequest request, OperationOptions options) {
        return BucketObjectFcAccessPoint.putAccessPointConfigForObjectProcess(this.clientImpl, request, options);
    }

    @Override
    public PutAccessPointPolicyForObjectProcessResult putAccessPointPolicyForObjectProcess(PutAccessPointPolicyForObjectProcessRequest request, OperationOptions options) {
        return BucketObjectFcAccessPoint.putAccessPointPolicyForObjectProcess(this.clientImpl, request, options);
    }

    @Override
    public GetAccessPointPolicyForObjectProcessResult getAccessPointPolicyForObjectProcess(GetAccessPointPolicyForObjectProcessRequest request, OperationOptions options) {
        return BucketObjectFcAccessPoint.getAccessPointPolicyForObjectProcess(this.clientImpl, request, options);
    }

    @Override
    public DeleteAccessPointPolicyForObjectProcessResult deleteAccessPointPolicyForObjectProcess(DeleteAccessPointPolicyForObjectProcessRequest request, OperationOptions options) {
        return BucketObjectFcAccessPoint.deleteAccessPointPolicyForObjectProcess(this.clientImpl, request, options);
    }
  
    @Override
    public WriteGetObjectResponseResult writeGetObjectResponse(WriteGetObjectResponseRequest request, OperationOptions options) {
        return BucketObjectFcAccessPoint.writeGetObjectResponse(this.clientImpl, request, options);
    }
}
