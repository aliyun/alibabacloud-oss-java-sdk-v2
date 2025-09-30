package com.aliyun.sdk.service.oss2.vectors.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.exceptions.DeserializationException;
import com.aliyun.sdk.service.oss2.models.RequestModel;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.Md5Utils;
import com.aliyun.sdk.service.oss2.vectors.models.VectorRequestModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.function.BiConsumer;

public final class SerdeJsonUtils {

    public static BiConsumer<RequestModel, OperationInput> addContentMd5 = new CalcContentMd5();
    public static BiConsumer<VectorRequestModel, OperationInput> vectorAddContentMd5 = new VectorCalcContentMd5();

    @SafeVarargs
    public static void serializeVectorInput(
            VectorRequestModel request,
            OperationInput input,
            BiConsumer<VectorRequestModel, OperationInput>... consumers) {

        // headers
        if (request.headers() != null) {
            input.headers().putAll(request.headers());
        }

        // parameters
        if (request.parameters() != null) {
            input.parameters().putAll(request.parameters());
        }

        // custom serializer
        for (BiConsumer<VectorRequestModel, OperationInput> consumer : consumers) {
            consumer.accept(request, input);
        }
    }

    public static <T> T fromJsonBody(OperationOutput output, Class<T> clazz) {
        if (!output.body().isPresent()) {
            return null;
        }

        byte[] jsonBytes;
        try {
            jsonBytes = output.body().get().toBytes();
        } catch (Exception e) {
            throw new DeserializationException("Failed to read content", e);
        }

        if (jsonBytes == null || jsonBytes.length == 0) {
            return null;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(jsonBytes, clazz);
        } catch (Exception e) {
            throw new DeserializationException("Failed to parse JSON", e);
        }
    }

    public static BinaryData toJson(Object value) {
        if (value == null) {
            return null;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            byte[] jsonBytes = objectMapper.writeValueAsBytes(value);
            return BinaryData.fromBytes(jsonBytes);
        } catch (Exception e) {
            throw new DeserializationException("Failed to serialize object to JSON", e);
        }
    }

    static class CalcContentMd5 implements BiConsumer<RequestModel, OperationInput> {

        @Override
        public void accept(RequestModel request, OperationInput input) {
            if (input.headers().containsKey("Content-MD5")) {
                return;
            }

            String md5 = "1B2M2Y8AsgTpgAmY7PhCfg==";
            if (input.body().isPresent()) {
                byte[] data = input.body().get().toBytes();
                md5 = Md5Utils.md5AsBase64(data);
            }

            input.headers().put("Content-MD5", md5);
        }
    }

    static class VectorCalcContentMd5 implements BiConsumer<VectorRequestModel, OperationInput> {

        @Override
        public void accept(VectorRequestModel request, OperationInput input) {
            if (input.headers().containsKey("Content-MD5")) {
                return;
            }

            String md5 = "1B2M2Y8AsgTpgAmY7PhCfg==";
            if (input.body().isPresent()) {
                byte[] data = input.body().get().toBytes();
                md5 = Md5Utils.md5AsBase64(data);
            }

            input.headers().put("Content-MD5", md5);
        }
    }

}
