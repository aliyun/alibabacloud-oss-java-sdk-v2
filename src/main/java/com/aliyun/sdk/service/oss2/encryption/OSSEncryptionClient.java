package com.aliyun.sdk.service.oss2.encryption;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.encryption.crypto.CryptoScheme;
import com.aliyun.sdk.service.oss2.encryption.internal.AesCtrContentCipherBuilder;
import com.aliyun.sdk.service.oss2.encryption.internal.CipherBinaryData;
import com.aliyun.sdk.service.oss2.encryption.internal.CryptoHeaders;
import com.aliyun.sdk.service.oss2.encryption.internal.CryptoUtils;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.Base64Utils;
import com.aliyun.sdk.service.oss2.utils.IOUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * OSS client wrapper that transparently encrypts uploads and decrypts downloads.
 * Supports putObject, getObject (including range reads), and multipart upload.
 * <p>
 * For multipart upload, the encryption context is returned by {@link #initiateMultipartUpload}
 * and must be passed to {@link #uploadPart} — the client itself holds no per-upload state.
 * <p>
 * For operations not directly supported, use {@link #unwrap()} to access the inner OSSClient.
 */
public final class OSSEncryptionClient implements AutoCloseable {

    private final OSSClient innerClient;
    private final ContentCipherBuilder cipherBuilder;

    public OSSEncryptionClient(OSSClient innerClient, EncryptionConfiguration encryptionConfig) {
        this.innerClient = Objects.requireNonNull(innerClient, "innerClient");
        Objects.requireNonNull(encryptionConfig, "encryptionConfig");
        CryptoConfiguration cryptoConfig = encryptionConfig.cryptoConfig() != null
                ? encryptionConfig.cryptoConfig()
                : CryptoConfiguration.DEFAULT;
        this.cipherBuilder = new AesCtrContentCipherBuilder(encryptionConfig.masterCipher(), cryptoConfig);
    }

    /**
     * Returns the underlying OSSClient for operations not supported by the encryption client.
     */
    public OSSClient unwrap() {
        return innerClient;
    }

    @Override
    public void close() throws Exception {
        innerClient.close();
    }

    // ==================== putObject ====================

    public PutObjectResult putObject(PutObjectRequest request) {
        return putObject(request, OperationOptions.defaults());
    }

    public PutObjectResult putObject(PutObjectRequest request, OperationOptions options) {
        // 1. Create encrypting ContentCipher with fresh key material
        ContentCipher cc = cipherBuilder.create();

        // 2. Get plaintext length (AES-CTR preserves length, so encrypted length == plain length)
        BinaryData body = request.body();
        Long plainLen = body != null ? body.getLength() : null;

        // 3. Build crypto metadata headers
        Map<String, String> cryptoMeta = buildCryptoHeaders(cc.getCipherData(), plainLen, null, null);

        // 4. Merge with existing metadata
        Map<String, String> mergedMeta = MapUtils.caseInsensitiveMap();
        if (request.metadata() != null) {
            mergedMeta.putAll(request.metadata());
        }
        mergedMeta.putAll(cryptoMeta);

        // 5. Wrap body with streaming encryption (supports replayability)
        BinaryData encryptedBody = body != null ? new CipherBinaryData(body, cc) : null;

        // 6. Build encrypted request and delegate
        PutObjectRequest encReq = request.toBuilder()
                .body(encryptedBody)
                .metadata(mergedMeta)
                .build();

        return innerClient.putObject(encReq, options);
    }

    // ==================== getObject ====================

    public GetObjectResult getObject(GetObjectRequest request) {
        return getObject(request, OperationOptions.defaults());
    }

    public GetObjectResult getObject(GetObjectRequest request, OperationOptions options) {
        // 1. Parse and align range
        String originalRange = request.range();
        long[] range = CryptoUtils.parseRange(originalRange);
        long alignedStart = 0;
        long discardCount = 0;

        GetObjectRequest adjustedRequest = request;
        if (range != null && range[0] >= 0) {
            long[] cryptoRange = CryptoUtils.calcCryptoRange(range);
            alignedStart = cryptoRange[0];
            discardCount = range[0] - alignedStart;

            // Build adjusted range string
            String newRange = "bytes=" + alignedStart + "-";
            if (range[1] >= 0) {
                newRange += range[1];
            }
            adjustedRequest = request.toBuilder().range(newRange).build();
        }

        // 2. Delegate to inner client
        GetObjectResult result = innerClient.getObject(adjustedRequest, options);

        // 3. Check if response has encryption info
        Map<String, String> headers = result.headers();
        if (!CryptoUtils.hasEncryptionInfo(headers)) {
            return result;
        }

        // 4. Parse envelope and create decrypting ContentCipher
        Envelope envelope = Envelope.fromHeaders(headers);
        ContentCipher cc = cipherBuilder.fromEnvelope(envelope);

        // 5. Seek to aligned position for range get
        if (alignedStart > 0) {
            cc.seekTo(alignedStart);
        }

        // 6. Wrap body with decrypting stream
        InputStream decryptedStream = cc.decryptContent(result.body());

        // 7. Build result with decrypted body; skip alignment bytes and adjust headers if needed
        GetObjectResult.Builder builder = result.toBuilder().innerBody(decryptedStream);

        if (discardCount > 0) {
            // Skip discarded alignment bytes
            try {
                IOUtils.skipFully(decryptedStream, discardCount);
            } catch (IOException e) {
                throw new RuntimeException("Failed to skip discarded bytes", e);
            }

            // Adjust Content-Length and Content-Range to reflect actual decrypted content
            // (after discarding alignment bytes). Mirrors the C++ SDK's overwriteRange logic.
            Map<String, String> adjustedHeaders = MapUtils.caseInsensitiveMap();
            adjustedHeaders.putAll(result.headers());

            String contentLength = adjustedHeaders.get("Content-Length");
            if (contentLength != null) {
                try {
                    long len = Long.parseLong(contentLength);
                    adjustedHeaders.put("Content-Length", String.valueOf(len - discardCount));
                } catch (NumberFormatException ignored) {
                }
            }

            String contentRange = adjustedHeaders.get("Content-Range");
            if (contentRange != null) {
                String adjusted = CryptoUtils.adjustContentRange(contentRange, discardCount);
                if (adjusted != null) {
                    adjustedHeaders.put("Content-Range", adjusted);
                }
            }

            builder.headers(adjustedHeaders);
        }

        return builder.build();
    }

    // ==================== initiateMultipartUpload ====================

    /**
     * Initiates an encrypted multipart upload.
     * <p>
     * The returned result contains both the standard {@code uploadId} and a
     * {@link InitiateMultipartUploadResult.CSEContext} (via {@link InitiateMultipartUploadResult#cseContext()})
     * that must be passed to subsequent {@link #uploadPart} calls.
     *
     * @param request  the initiate request; csePartSize and cseDataSize are read from the request
     * @param options  operation options
     * @return the result containing the uploadId and encryption context
     */
    public InitiateMultipartUploadResult initiateMultipartUpload(
            InitiateMultipartUploadRequest request, OperationOptions options) {
        Long partSize = request.csePartSize();
        Long dataSize = request.cseDataSize();

        if (partSize == null || dataSize == null) {
            throw new IllegalArgumentException("csePartSize and cseDataSize are required for encrypted multipart upload");
        }
        if (partSize <= 0 || partSize % CryptoScheme.BLOCK_SIZE != 0) {
            throw new IllegalArgumentException(
                    "csePartSize must be a positive multiple of " + CryptoScheme.BLOCK_SIZE + " bytes");
        }

        // 1. Create encrypting ContentCipher with fresh key material
        ContentCipher cc = cipherBuilder.create();

        // 2. Build crypto metadata with part size and data size
        Map<String, String> cryptoMeta = buildCryptoHeaders(cc.getCipherData(), (Long) null, partSize, dataSize);

        // 3. Merge with existing metadata
        Map<String, String> mergedMeta = MapUtils.caseInsensitiveMap();
        if (request.metadata() != null) {
            mergedMeta.putAll(request.metadata());
        }
        mergedMeta.putAll(cryptoMeta);

        // 4. Build request and delegate
        InitiateMultipartUploadRequest encReq = request.toBuilder()
                .metadata(mergedMeta)
                .build();

        InitiateMultipartUploadResult result = innerClient.initiateMultipartUpload(encReq, options);

        // 5. Attach encryption context to result (no client-side state)
        InitiateMultipartUploadResult.CSEContext ctx =
                new InitiateMultipartUploadResult.CSEContext(partSize, dataSize, cc);
        return result.toBuilder().cseContext(ctx).build();
    }

    public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest request) {
        return initiateMultipartUpload(request, OperationOptions.defaults());
    }

    // ==================== uploadPart ====================

    /**
     * Encrypts and uploads a single part using the encryption context from
     * {@link #initiateMultipartUpload}.
     *
     * @param ctx     the encryption context returned by initiateMultipartUpload
     * @param request the upload part request
     * @param options operation options
     * @return the upload part result
     */
    public UploadPartResult uploadPart(InitiateMultipartUploadResult.CSEContext ctx,
                                       UploadPartRequest request, OperationOptions options) {
        validateCSEContext(ctx);

        BinaryData body = request.body();
        long offset = (request.partNumber() - 1) * ctx.partSize();
        ContentCipher baseCipher = (ContentCipher) ctx.contentCipher();

        // Wrap body with streaming encryption using CipherBinaryData with offset.
        // Each toStream() call clones the base cipher and seeks to the correct part offset.
        BinaryData encryptedBody = new CipherBinaryData(body, baseCipher, offset);

        // Build encrypted request and delegate
        UploadPartRequest encReq = request.toBuilder()
                .body(encryptedBody)
                .build();

        return innerClient.uploadPart(encReq, options);
    }

    public UploadPartResult uploadPart(InitiateMultipartUploadResult.CSEContext ctx, UploadPartRequest request) {
        return uploadPart(ctx, request, OperationOptions.defaults());
    }

    // ==================== completeMultipartUpload ====================

    public CompleteMultipartUploadResult completeMultipartUpload(
            CompleteMultipartUploadRequest request, OperationOptions options) {
        return innerClient.completeMultipartUpload(request, options);
    }

    public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest request) {
        return completeMultipartUpload(request, OperationOptions.defaults());
    }

    // ==================== abortMultipartUpload ====================

    public AbortMultipartUploadResult abortMultipartUpload(
            AbortMultipartUploadRequest request, OperationOptions options) {
        return innerClient.abortMultipartUpload(request, options);
    }

    public AbortMultipartUploadResult abortMultipartUpload(AbortMultipartUploadRequest request) {
        return abortMultipartUpload(request, OperationOptions.defaults());
    }

    // ==================== listParts ====================

    public ListPartsResult listParts(ListPartsRequest request, OperationOptions options) {
        return innerClient.listParts(request, options);
    }

    public ListPartsResult listParts(ListPartsRequest request) {
        return listParts(request, OperationOptions.defaults());
    }

    // ==================== headObject ====================

    public HeadObjectResult headObject(HeadObjectRequest request) {
        return headObject(request, OperationOptions.defaults());
    }

    public HeadObjectResult headObject(HeadObjectRequest request, OperationOptions options) {
        return innerClient.headObject(request, options);
    }

    // ==================== getObjectMeta ====================

    public GetObjectMetaResult getObjectMeta(GetObjectMetaRequest request) {
        return getObjectMeta(request, OperationOptions.defaults());
    }

    public GetObjectMetaResult getObjectMeta(GetObjectMetaRequest request, OperationOptions options) {
        return innerClient.getObjectMeta(request, options);
    }

    // ==================== Private helpers ====================

    /**
     * Validates the CSE context for uploadPart operations.
     */
    private static void validateCSEContext(InitiateMultipartUploadResult.CSEContext ctx) {
        if (ctx == null) {
            throw new IllegalArgumentException("CSEContext must not be null");
        }
        if (ctx.partSize() <= 0 || ctx.partSize() % CryptoScheme.BLOCK_SIZE != 0) {
            throw new IllegalArgumentException(
                    "CSEContext.partSize must be a positive multiple of "
                            + CryptoScheme.BLOCK_SIZE + " bytes, got: " + ctx.partSize());
        }
        if (!(ctx.contentCipher() instanceof ContentCipher)) {
            throw new IllegalArgumentException("CSEContext.contentCipher must be a ContentCipher instance");
        }
    }

    /**
     * Build crypto metadata map. Keys are WITHOUT x-oss-meta- prefix (the metadata() builder adds it).
     */
    private Map<String, String> buildCryptoHeaders(CipherData cipherData,
                                                   Long plainContentLen,
                                                   Long partSize, Long dataSize) {
        Map<String, String> meta = new LinkedHashMap<>();

        meta.put(CryptoHeaders.CRYPTO_KEY, Base64Utils.encodeToString(cipherData.encryptedKey()));
        meta.put(CryptoHeaders.CRYPTO_IV, Base64Utils.encodeToString(cipherData.encryptedIV()));

        CipherMetadata cm = cipherBuilder.getCipherMetadata();
        meta.put(CryptoHeaders.CRYPTO_CEK_ALG, cm.cekAlgorithm());
        meta.put(CryptoHeaders.CRYPTO_WRAP_ALG, cm.wrapAlgorithm());

        String matDesc = cm.matDesc();
        if (matDesc != null) {
            meta.put(CryptoHeaders.CRYPTO_MATDESC, matDesc);
        }

        if (plainContentLen != null && plainContentLen >= 0) {
            meta.put(CryptoHeaders.CRYPTO_UNENCRYPTION_CONTENT_LENGTH, String.valueOf(plainContentLen));
        }

        if (partSize != null) {
            meta.put(CryptoHeaders.CRYPTO_PART_SIZE, partSize.toString());
        }
        if (dataSize != null && dataSize > 0) {
            meta.put(CryptoHeaders.CRYPTO_DATA_SIZE, dataSize.toString());
        }

        return meta;
    }
}
