package com.aliyun.sdk.service.oss2.encryption.internal;

import com.aliyun.sdk.service.oss2.encryption.ContentCryptoMaterial;
import com.aliyun.sdk.service.oss2.encryption.CryptoConfiguration;
import com.aliyun.sdk.service.oss2.encryption.EncryptionMaterials;
import com.aliyun.sdk.service.oss2.encryption.crypto.CryptoCipher;
import com.aliyun.sdk.service.oss2.encryption.crypto.CryptoScheme;
import com.aliyun.sdk.service.oss2.utils.Base64Utils;

import javax.crypto.SecretKey;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;


public abstract class CryptoModuleBase implements CryptoModule {
    protected static final int DEFAULT_BUFFER_SIZE = 1024 * 2;
    protected final EncryptionMaterials encryptionMaterials;
    protected final CryptoConfiguration cryptoConfig;
    protected final OSSDirect ossDirect;
    protected final String encryptionClientUserAgent;

    protected CryptoModuleBase(OSSDirect ossDirect,
                               EncryptionMaterials encryptionMaterials,
                               CryptoConfiguration cryptoConfig) {
        this.encryptionMaterials = encryptionMaterials;
        this.ossDirect = ossDirect;
        this.cryptoConfig = cryptoConfig;
        this.encryptionClientUserAgent = "";
    }


    abstract SecretKey generateCEK();

    abstract byte[] generateIV();

    abstract String getContentCipherAlgorithm();

    abstract CryptoCipher createEncryptCryptoCipher(ContentCryptoMaterial cekMaterial, long[] cryptoRange, long skipBlock);

    abstract CryptoCipher createDecryptCryptoCipher(ContentCryptoMaterial cekMaterial, long[] cryptoRange, long skipBlock);

    /**
     * build the encryption materials in the object metadata.
     */
    protected final Map<String, String> generateCryptoMetadata(ContentCryptoMaterial contentCryptoMaterial, Map<String, Object> headers) {
        Map<String, String> metadata = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        //x-oss-meta-client-side-encryption-key
        byte[] encryptedCEK = contentCryptoMaterial.getEncryptedCEK();
        metadata.put(CryptoHeaders.CRYPTO_KEY, Base64Utils.encodeToString(encryptedCEK));

        //x-oss-meta-client-side-encryption-start //iv
        // Put the iv into the object metadata
        byte[] encryptedIV = contentCryptoMaterial.getEncryptedIV();
        metadata.put(CryptoHeaders.CRYPTO_IV, Base64Utils.encodeToString(encryptedIV));

        //x-oss-meta-client-side-encryption-cek-alg
        // Put the content encrypt key algorithm into the object metadata
        String contentCryptoAlgo = contentCryptoMaterial.getContentCryptoAlgorithm();
        metadata.put(CryptoHeaders.CRYPTO_CEK_ALG, contentCryptoAlgo);

        //x-oss-meta-client-side-encryption-wrap-alg
        // Put the key wrap algorithm into the object metadata
        String keyWrapAlgo = contentCryptoMaterial.getKeyWrapAlgorithm();
        metadata.put(CryptoHeaders.CRYPTO_WRAP_ALG, keyWrapAlgo);

        //x-oss-meta-client-side-encryption-matdesc,json format
        // Put the crypto description into the object metadata
        Map<String, String> materialDesc = contentCryptoMaterial.getMaterialsDescription();
        if (materialDesc != null && !materialDesc.isEmpty()) {
            // TODO
            //String descStr = ParseUtil.toJSONString(materialDesc);
            //metadata.put(CryptoHeaders.CRYPTO_MATDESC, descStr);
        }

        //x-oss-meta-client-side-encryption-magic-number-hmac

        //x-oss-meta-client-side-encryption-unencrypted-content-md5
        if (headers.containsKey("Content-MD5")) {
            metadata.put(CryptoHeaders.CRYPTO_UNENCRYPTION_CONTENT_MD5, (String) headers.get("Content-MD5"));
        }

        return metadata;
    }

    protected final Map<String, String> generateMultiPartCryptoMetadata(ContentCryptoMaterial contentCryptoMaterial,
                                                                        Map<String, Object> headers,
                                                                        Long partSize,
                                                                        Long dataSize) {
        Map<String, String> metaData = generateCryptoMetadata(contentCryptoMaterial, headers);
        metaData.put(CryptoHeaders.CRYPTO_PART_SIZE, partSize.toString());
        if (dataSize != null) {
            metaData.put(CryptoHeaders.CRYPTO_DATA_SIZE, dataSize.toString());
        }
        return metaData;
    }

    /**
     * Builds a new content crypto material for encrypting the object achieved.
     */
    protected ContentCryptoMaterial createContentCryptoMaterialDefault() {
        // Generate random CEK IV
        byte[] iv = generateIV();
        SecretKey cek = generateCEK();

        // Build content crypto Materials by encryptionMaterials.
        ContentCryptoMaterial contentMaterial = new ContentCryptoMaterial();
        contentMaterial.setIV(iv);
        contentMaterial.setCEK(cek);
        contentMaterial.setContentCryptoAlgorithm(getContentCipherAlgorithm());
        encryptionMaterials.encryptCEK(contentMaterial);

        return contentMaterial;
    }

    /**
     * Builds a new content crypto material for decrypting the object achieved.
     */
    protected ContentCryptoMaterial createContentMaterialFromMetadata(Map<String, String> headers) {
        // Encrypted CEK and encrypted IV.
        String b64CEK = headers.get(CryptoHeaders.X_OSS_META_CRYPTO_KEY);
        String b64IV = headers.get(CryptoHeaders.X_OSS_META_CRYPTO_IV);
        if (b64CEK == null || b64IV == null) {
            throw new RuntimeException("Content encrypted key  or encrypted iv not found.", null);
        }
        byte[] encryptedCEK = Base64Utils.decodeString(b64CEK);
        byte[] encryptedIV = Base64Utils.decodeString(b64IV);

        // Key wrap algorithm
        final String keyWrapAlgo = headers.get(CryptoHeaders.X_OSS_META_CRYPTO_WRAP_ALG);
        if (keyWrapAlgo == null)
            throw new RuntimeException("Key wrap algorithm should not be null.", null);

        // CEK algorithm
        String cekAlgo = headers.get(CryptoHeaders.X_OSS_META_CRYPTO_CEK_ALG);

        // Description
        String mateDescString = headers.get(CryptoHeaders.X_OSS_META_CRYPTO_MATDESC);
        Map<String, String> matDesc = CryptoUtils.getDescFromJsonString(mateDescString);

        // Decrypt the secured CEK to CEK.
        ContentCryptoMaterial contentMaterial = new ContentCryptoMaterial();
        contentMaterial.setEncryptedCEK(encryptedCEK);
        contentMaterial.setEncryptedIV(encryptedIV);
        contentMaterial.setMaterialsDescription(matDesc);
        contentMaterial.setContentCryptoAlgorithm(cekAlgo);
        contentMaterial.setKeyWrapAlgorithm(keyWrapAlgo);
        encryptionMaterials.decryptCEK(contentMaterial);

        return contentMaterial;
    }

    private void checkMultipartPartSize(Long partSize) {
        Objects.requireNonNull(partSize, "PartSize");
        if (partSize <= 0 || 0 != (partSize % CryptoScheme.BLOCK_SIZE)) {
            throw new IllegalArgumentException("PartSize is not 16 bytes alignment.");
        }
    }
}
