package com.aliyun.sdk.service.oss2.utils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Md5Utils {
    private static final int SIXTEEN_K = 1 << 14;

    private Md5Utils() {
    }

    /**
     * Computes the MD5 hash of the data in the given input stream and returns
     * it as an array of bytes.
     * Note this method closes the given input stream upon completion.
     */
    public static byte[] computeMD5Hash(InputStream is) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[SIXTEEN_K];
            int bytesRead;
            while ((bytesRead = bis.read(buffer, 0, buffer.length)) != -1) {
                messageDigest.update(buffer, 0, bytesRead);
            }
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            // should never get here
            throw new IllegalStateException(e);
        } finally {
            try {
                bis.close();
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Returns the MD5 in base64 for the data from the given input stream.
     * Note this method closes the given input stream upon completion.
     */
    public static String md5AsBase64(InputStream is) throws IOException {
        return Base64Utils.encodeToString(computeMD5Hash(is));
    }

    /**
     * Computes the MD5 hash of the given data and returns it as an array of
     * bytes.
     */
    public static byte[] computeMD5Hash(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(input);
        } catch (NoSuchAlgorithmException e) {
            // should never get here
            throw new IllegalStateException(e);
        }
    }

    /**
     * Returns the MD5 in base64 for the given byte array.
     */
    public static String md5AsBase64(byte[] input) {
        return Base64Utils.encodeToString(computeMD5Hash(input));
    }

    /**
     * Computes the MD5 of the given file.
     */
    public static byte[] computeMD5Hash(File file) throws IOException {
        return computeMD5Hash(new FileInputStream(file));
    }

    /**
     * Returns the MD5 in base64 for the given file.
     */
    public static String md5AsBase64(File file) throws IOException {
        return Base64Utils.encodeToString(computeMD5Hash(file));
    }
}