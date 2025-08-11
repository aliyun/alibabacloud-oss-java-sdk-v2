package com.aliyun.sdk.service.oss2.utils;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Base64UtilsTest {

    @Test
    public void testVectorsPerRfc4648() {
        String[] testVectors = {"", "f", "fo", "foo", "foob", "fooba", "foobar"};
        String[] expected = {"", "Zg==", "Zm8=", "Zm9v", "Zm9vYg==", "Zm9vYmE=", "Zm9vYmFy"};

        for (int i = 0; i < testVectors.length; i++) {
            String data = testVectors[i];
            byte[] source = data.getBytes(StandardCharsets.UTF_8);
            String b64encoded = Base64Utils.encodeToString(data.getBytes(StandardCharsets.UTF_8));
            assertEquals(expected[i], b64encoded);
            byte[] b64 = b64encoded.getBytes(StandardCharsets.UTF_8);

            byte[] decoded = Base64Utils.decode(b64);
            assertArrayEquals(source, decoded);
        }
    }
}
