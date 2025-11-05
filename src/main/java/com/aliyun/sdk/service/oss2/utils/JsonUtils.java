package com.aliyun.sdk.service.oss2.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {

    public static JsonNode getJsonRootElement(byte[] data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(data);
    }

    private JsonUtils(){}
}
