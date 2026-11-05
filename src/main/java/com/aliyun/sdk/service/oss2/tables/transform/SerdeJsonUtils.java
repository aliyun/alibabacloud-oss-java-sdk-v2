package com.aliyun.sdk.service.oss2.tables.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.exceptions.DeserializationException;
import com.aliyun.sdk.service.oss2.internal.serde.OssSerde;
import com.aliyun.sdk.service.oss2.tables.models.TableRequestModel;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.Md5Utils;

import java.util.function.BiConsumer;

public final class SerdeJsonUtils {
    public static BiConsumer<TableRequestModel, OperationInput> addContentMd5 = new CalcContentMd5();

    @SafeVarargs
    public static void serializeInput(TableRequestModel request, OperationInput input,
                                      BiConsumer<TableRequestModel, OperationInput>... consumers) {
        if (request.headers() != null) {
            input.headers().putAll(request.headers());
        }
        if (request.parameters() != null) {
            input.parameters().putAll(request.parameters());
        }
        for (BiConsumer<TableRequestModel, OperationInput> consumer : consumers) {
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
            return OssSerde.fromJson(jsonBytes, clazz);
        } catch (Exception e) {
            throw new DeserializationException("Failed to parse JSON", e);
        }
    }

    public static BinaryData toJson(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return BinaryData.fromBytes(OssSerde.toJsonBytes(value));
        } catch (Exception e) {
            throw new DeserializationException("Failed to serialize object to JSON", e);
        }
    }

    static class CalcContentMd5 implements BiConsumer<TableRequestModel, OperationInput> {
        @Override
        public void accept(TableRequestModel request, OperationInput input) {
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
