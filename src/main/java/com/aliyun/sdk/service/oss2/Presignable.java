package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;

public interface Presignable {

    /**
     * Generates the pre-signed URL for GetObject operation.
     *
     * @param request A {@link GetObjectRequest} for GetObject operation.
     * @return A {@link PresignResult} for GetObject operation.
     * @throws RuntimeException If an error occurs
     */
    default PresignResult presign(GetObjectRequest request) {
        return presign(request, PresignOptions.defaults());
    }

    /**
     * Generates the pre-signed URL for GetObject operation.
     *
     * @param request A {@link GetObjectRequest} for GetObject operation.
     * @param options The operation options.
     * @return A {@link GetObjectResult} for GetObject operation.
     * @throws RuntimeException If an error occurs
     */
    default PresignResult presign(GetObjectRequest request, PresignOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Generates the pre-signed URL for PutObject operation.
     *
     * @param request A {@link PutObjectRequest} for PutObject operation.
     * @return A {@link PresignResult} for GetObject operation.
     * @throws RuntimeException If an error occurs
     */
    default PresignResult presign(PutObjectRequest request) {
        return presign(request, PresignOptions.defaults());
    }

    /**
     * Generates the pre-signed URL for PutObject operation.
     *
     * @param request A {@link PutObjectRequest} for PutObject operation.
     * @param options The operation options.
     * @return A {@link PresignResult} for GetObject operation.
     * @throws RuntimeException If an error occurs
     */
    default PresignResult presign(PutObjectRequest request, PresignOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Generates the pre-signed URL for HeadObject operation.
     *
     * @param request A {@link HeadObjectRequest} for HeadObject operation.
     * @return A {@link PresignResult} for HeadObject operation.
     * @throws RuntimeException If an error occurs
     */
    default PresignResult presign(HeadObjectRequest request) {
        return presign(request, PresignOptions.defaults());
    }

    /**
     * Generates the pre-signed URL for HeadObject operation.
     *
     * @param request A {@link HeadObjectRequest} for HeadObject operation.
     * @param options The operation options.
     * @return A {@link PresignResult} for HeadObject operation.
     * @throws RuntimeException If an error occurs
     */
    default PresignResult presign(HeadObjectRequest request, PresignOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Generates the pre-signed URL for InitiateMultipartUpload operation.
     *
     * @param request A {@link InitiateMultipartUploadRequest} for InitiateMultipartUpload operation.
     * @return A {@link PresignResult} for InitiateMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default PresignResult presign(InitiateMultipartUploadRequest request) {
        return presign(request, PresignOptions.defaults());
    }

    /**
     * Generates the pre-signed URL for InitiateMultipartUpload operation.
     *
     * @param request A {@link InitiateMultipartUploadRequest} for InitiateMultipartUpload operation.
     * @param options The operation options.
     * @return A {@link PresignResult} for InitiateMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default PresignResult presign(InitiateMultipartUploadRequest request, PresignOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Generates the pre-signed URL for UploadPart operation.
     *
     * @param request A {@link UploadPartRequest} for UploadPart operation.
     * @return A {@link PresignResult} for UploadPartRequest operation.
     * @throws RuntimeException If an error occurs
     */
    default PresignResult presign(UploadPartRequest request) {
        return presign(request, PresignOptions.defaults());
    }

    /**
     * Generates the pre-signed URL for UploadPart operation.
     *
     * @param request A {@link UploadPartRequest} for UploadPart operation.
     * @param options The operation options.
     * @return A {@link PresignResult} for UploadPart operation.
     * @throws RuntimeException If an error occurs
     */
    default PresignResult presign(UploadPartRequest request, PresignOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Generates the pre-signed URL for CompleteMultipart operation.
     *
     * @param request A {@link CompleteMultipartUploadRequest} for CompleteMultipartUpload operation.
     * @return A {@link PresignResult} for CompleteMultipartRequest operation.
     * @throws RuntimeException If an error occurs
     */
    default PresignResult presign(CompleteMultipartUploadRequest request) {
        return presign(request, PresignOptions.defaults());
    }

    /**
     * Generates the pre-signed URL for CompleteMultipartUpload operation.
     *
     * @param request A {@link CompleteMultipartUploadRequest} for CompleteMultipartUpload operation.
     * @param options The operation options.
     * @return A {@link PresignResult} for CompleteMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default PresignResult presign(CompleteMultipartUploadRequest request, PresignOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Generates the pre-signed URL for AbortMultipartUpload operation.
     *
     * @param request A {@link AbortMultipartUploadRequest} for AbortMultipartUpload operation.
     * @return A {@link PresignResult} for AbortMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default PresignResult presign(AbortMultipartUploadRequest request) {
        return presign(request, PresignOptions.defaults());
    }

    /**
     * Generates the pre-signed URL for AbortMultipartUpload operation.
     *
     * @param request A {@link AbortMultipartUploadRequest} for AbortMultipartUpload operation.
     * @param options The operation options.
     * @return A {@link PresignResult} for AbortMultipartUpload operation.
     * @throws RuntimeException If an error occurs
     */
    default PresignResult presign(AbortMultipartUploadRequest request, PresignOptions options) {
        throw new UnsupportedOperationException();
    }

}
