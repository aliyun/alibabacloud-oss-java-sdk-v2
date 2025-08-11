package com.aliyun.sdk.service.oss2.encryption.internal;

import com.aliyun.sdk.service.oss2.encryption.CryptoTestUtils;
import com.aliyun.sdk.service.oss2.encryption.crypto.CryptoCipher;
import com.aliyun.sdk.service.oss2.encryption.crypto.CryptoScheme;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class RenewableCipherInputStreamTest {
    @Test
    public void testMarkAndRest() throws NoSuchAlgorithmException {
        String content = "hdfyqlmrwbdskfnsdkfbs9hmgdr61axlodfsklfjkslvmdklu0whdfyqlmrwbdskfnsdkfbskfh"
                + "isfdfsklfjkslvmdklu0wrwurjjdnksh098j62kfgjsdfbsj4427gc1sfbjsfsj123y214y2hujnhdfyq";

        InputStream isOrig = new ByteArrayInputStream(content.getBytes());
        SecretKey cek = CryptoTestUtils.generateCEK();
        byte[] iv = CryptoTestUtils.generateIV();
        CryptoCipher cryptoCipher = CryptoScheme.AES_CTR.createCryptoCipher(cek, iv, Cipher.ENCRYPT_MODE, null);

        CipherInputStream isCurr = new RenewableCipherInputStream(isOrig, cryptoCipher, 2048);
        try {
            isCurr.mark(100);
            byte[] buffer = new byte[50];
            int len = isCurr.read(buffer, 0, 50);

            isCurr.reset();
            byte[] buffer2 = new byte[50];
            int len2 = isCurr.read(buffer2, 0, 50);
            assertEquals(len, len2);
            assertArrayEquals(buffer, buffer2);

            isCurr.reset();
            byte[] buffer3 = new byte[50];
            int len3 = isCurr.read(buffer3);
            assertEquals(50, len3);
            assertArrayEquals(buffer, buffer3);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testMarkUnnromal() throws NoSuchAlgorithmException {
        String content = "hdfyqlmrwbdskfnsdkfbs9hmgdr61axlodfsklfjkslvmdklu0whdfyqlmrwbdskfnsdkfbskfh"
                + "isfdfsklfjkslvmdklu0wrwurjjdnksh098j62kfgjsdfbsj4427gc1sfbjsfsj123y214y2hujnhdfyq";

        InputStream isOrig = new ByteArrayInputStream(content.getBytes());
        SecretKey cek = CryptoTestUtils.generateCEK();
        byte[] iv = CryptoTestUtils.generateIV();
        CryptoCipher cryptoCipher = CryptoScheme.AES_CTR.createCryptoCipher(cek, iv, Cipher.ENCRYPT_MODE, null);

        CipherInputStream isCurr = new RenewableCipherInputStream(isOrig, cryptoCipher, 2048);

        try {
            isCurr.mark(50);
            byte[] buffer = new byte[40];
            int ignore = isCurr.read();
            isCurr.mark(50);
            Assert.fail("The RenewableCipherInputStream marking only supported for first call to read or skip.");
        } catch (UnsupportedOperationException e) {
            // Expected exception.
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }

        try {
            isCurr.reset();
            long ignore = isCurr.skip(10);
            isCurr.mark(50);
            Assert.fail("The RenewableCipherInputStream marking only supported for first call to read or skip.");
        } catch (UnsupportedOperationException e) {
            // Expected exception.
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testConstruction() throws NoSuchAlgorithmException {
        String content = "hdfyqlmrwbdskfnsdkfbs9hmgdr61axlodfsklfjkslvmdklu0whdfyqlmrwbdskfnsdkfbskfh"
                + "isfdfsklfjkslvmdklu0wrwurjjdnksh098j62kfgjsdfbsj4427gc1sfbjsfsj123y214y2hujnhdfyq";

        InputStream isOrig = new ByteArrayInputStream(content.getBytes());
        SecretKey cek = CryptoTestUtils.generateCEK();
        byte[] iv = CryptoTestUtils.generateIV();
        CryptoCipher cryptoCipher = CryptoScheme.AES_CTR.createCryptoCipher(cek, iv, Cipher.ENCRYPT_MODE, null);
        try {
            CipherInputStream isCurr = new RenewableCipherInputStream(isOrig, cryptoCipher, 2049);
            Assert.fail("buff size if should multiple of 512.");
        } catch (IllegalArgumentException e) {
            // Expected excpetion.
        }

        try {
            CipherInputStream isCurr = new RenewableCipherInputStream(isOrig, cryptoCipher, -1);
            Assert.fail("buff size if should multiple of 512.");
        } catch (IllegalArgumentException e) {
            // Expected excpetion.
        }

        try {
            CipherInputStream isCurr = new RenewableCipherInputStream(isOrig, cryptoCipher);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        try {
            CipherInputStream isCurr = new RenewableCipherInputStream(isOrig, cryptoCipher, 2048);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

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