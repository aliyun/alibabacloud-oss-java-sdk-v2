package com.aliyun.sdk.service.oss2.utils;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Md5UtilsTest {

    public static void appendDataToTempFile(File file, String dataToAppend)
            throws IOException {

        try (FileWriter outputWriter = new FileWriter(file)) {
            outputWriter.append(dataToAppend);
        }
    }

    @Test
    public void testBytes() {
        byte[] md5 = Md5Utils.computeMD5Hash("Testing MD5".getBytes(StandardCharsets.UTF_8));
        assertEquals("0b4f503b8eb7714ce12402406895cf68", StringUtils.lowerCase(Base16Utils.encodeToString(md5, true)));

        String b64 = Md5Utils.md5AsBase64("Testing MD5".getBytes(StandardCharsets.UTF_8));
        assertEquals("C09QO463cUzhJAJAaJXPaA==", b64);
    }

    @Test
    public void testStream() throws IOException {
        byte[] md5 = Md5Utils.computeMD5Hash(new ByteArrayInputStream("Testing MD5".getBytes(StandardCharsets.UTF_8)));
        assertEquals("0b4f503b8eb7714ce12402406895cf68", StringUtils.lowerCase(Base16Utils.encodeToString(md5, true)));

        String b64 = Md5Utils.md5AsBase64(new ByteArrayInputStream("Testing MD5".getBytes(StandardCharsets.UTF_8)));
        assertEquals("C09QO463cUzhJAJAaJXPaA==", b64);
    }

    @Test
    public void testFile() throws Exception {
        File f = File.createTempFile("Md5UtilsTest-", "txt");
        f.deleteOnExit();
        appendDataToTempFile(f, "Testing MD5");
        byte[] md5 = Md5Utils.computeMD5Hash(f);
        assertEquals("0b4f503b8eb7714ce12402406895cf68", StringUtils.lowerCase(Base16Utils.encodeToString(md5, true)));

        String b64 = Md5Utils.md5AsBase64(f);
        assertEquals("C09QO463cUzhJAJAaJXPaA==", b64);
    }
}