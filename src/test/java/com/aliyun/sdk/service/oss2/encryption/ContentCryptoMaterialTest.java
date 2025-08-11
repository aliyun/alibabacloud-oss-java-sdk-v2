package com.aliyun.sdk.service.oss2.encryption;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContentCryptoMaterialTest {
    @Test
    public void testConstruction() {
        byte[] iv = new byte[2];
        byte[] encryptedCEK = new byte[2];
        byte[] encryptedIV = new byte[2];
        Map<String, String> matDesc = new HashMap<String, String>();
        ContentCryptoMaterial material = new ContentCryptoMaterial(null, iv, null, encryptedCEK, encryptedIV, null, matDesc);
        assertEquals(-196513505, material.hashCode());

        material = new ContentCryptoMaterial(null, iv, "", encryptedCEK, encryptedIV, "", matDesc);
        assertEquals(-196513505, material.hashCode());
    }
}