package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.exceptions.DeserializationException;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transport.StringBinaryData;
import com.aliyun.sdk.service.oss2.utils.Md5Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.w3c.dom.Element;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;

public final class SerdeUtils {

    public static BiConsumer<RequestModel, OperationInput> addContentMd5 = new CalcContentMd5();

    @SafeVarargs
    public static void serializeInput(
            RequestModel request,
            OperationInput input,
            BiConsumer<RequestModel, OperationInput>... consumers) {

        // headers
        if (request.headers() != null) {
            input.headers().putAll(request.headers());
        }

        // parameters
        if (request.parameters() != null) {
            input.parameters().putAll(request.parameters());
        }

        // custom serializer
        for (BiConsumer<RequestModel, OperationInput> consumer : consumers) {
            consumer.accept(request, input);
        }
    }

    public static BinaryData serializeXmlBody(Object value) {
        if (value == null) {
            return null;
        }
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return new StringBinaryData(xmlMapper.writeValueAsString(value));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Object deserializeXmlBody(OperationOutput output, Class<T> clz) {
        if (!output.body().isPresent()) {
            return null;
        }

        byte[] xmlBytes;
        try {
            xmlBytes = output.body().get().toBytes();
        } catch (Exception e) {
            throw new DeserializationException("Failed to read content", e);
        }

        if (xmlBytes == null || xmlBytes.length == 0) {
            return null;
        }

        try {
            ObjectMapper xmlMapper = new XmlMapper();
            xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            xmlMapper.registerModule(new JavaTimeModule());
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return xmlMapper.readValue(xmlBytes, clz);
        } catch (Exception e) {
            throw new DeserializationException("Failed to parse XML", e);
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

    static String elementToString(Element element) throws Exception {
        javax.xml.transform.Transformer transformer = javax.xml.transform.TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.OMIT_XML_DECLARATION, "yes");
        java.io.StringWriter writer = new java.io.StringWriter();
        transformer.transform(new javax.xml.transform.dom.DOMSource(element), new javax.xml.transform.stream.StreamResult(writer));
        return writer.toString();
    }
}
