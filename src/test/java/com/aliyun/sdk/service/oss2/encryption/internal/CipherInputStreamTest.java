package com.aliyun.sdk.service.oss2.encryption.internal;

import com.aliyun.sdk.service.oss2.encryption.CryptoTestUtils;
import com.aliyun.sdk.service.oss2.encryption.crypto.CryptoCipher;
import com.aliyun.sdk.service.oss2.encryption.crypto.CryptoScheme;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class CipherInputStreamTest {
    @Test
    public void testCipherInputStream() throws NoSuchAlgorithmException {
        String content = "hdfyqlmrwbdskfnsdkfbs9hmgdr61axlodfsklfjkslvmdklu0whdfyqlmrwbdskfnsdkfbskfh"
                + "isfdfsklfjkslvmdklu0wrwurjjdnksh098j62kfgjsdfbsj4427gc1sfbjsfsj123y214y2hujnhdfyq";

        InputStream isOrig = new ByteArrayInputStream(content.getBytes());
        SecretKey cek = CryptoTestUtils.generateCEK();
        byte[] iv = CryptoTestUtils.generateIV();
        CryptoCipher cryptoCipher = CryptoScheme.AES_CTR.createCryptoCipher(cek, iv, Cipher.ENCRYPT_MODE, null);

        CipherInputStream isCurr = new CipherInputStream(isOrig, cryptoCipher, 2048);
        assertFalse(isCurr.markSupported());
        try {
            isCurr.mark(10);
            isCurr.reset();
            Assert.fail("{@link CipherInputStream} instance not support mark and reset.");
        } catch (Exception e) {
            // Expected excpetion.
        }
    }

}