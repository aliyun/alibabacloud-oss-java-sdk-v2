package com.aliyun.sdk.service.oss2.internal.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.xml.util.DefaultXmlPrettyPrinter;

/**
 * The single entry point for Jackson runtime calls.
 * <p>
 * Only APIs that exist with the same name in both Jackson 2 and Jackson 3 are used here,
 * so this file compiles against either version after the import prefix is replaced.
 * Mapper configuration lives in {@link JacksonMappers}.
 */
public final class OssSerde {

    private OssSerde() {
    }

    // ---- xml protocol ----

    public static String toXml(Object value) {
        try {
            return JacksonMappers.XML.writeValueAsString(value);
        } catch (Exception e) {
            throw new SerdeException("Failed to serialize XML", e);
        }
    }

    public static String toXml(Object value, String rootName, boolean pretty) {
        try {
            // ObjectMapper.writer(PrettyPrinter) is gone in Jackson 3, ObjectWriter.with(PrettyPrinter) exists in both
            ObjectWriter writer = pretty
                    ? JacksonMappers.XML.writer().with(new DefaultXmlPrettyPrinter())
                    : JacksonMappers.XML.writer();
            return writer.withRootName(rootName).writeValueAsString(value);
        } catch (Exception e) {
            throw new SerdeException("Failed to serialize XML", e);
        }
    }

    public static <T> T fromXml(byte[] data, Class<T> type) {
        try {
            return JacksonMappers.XML.readValue(data, type);
        } catch (Exception e) {
            throw new SerdeException("Failed to parse XML", e);
        }
    }

    // ---- json protocol ----

    public static String toJson(Object value) {
        try {
            return JacksonMappers.JSON.writeValueAsString(value);
        } catch (Exception e) {
            throw new SerdeException("Failed to serialize JSON", e);
        }
    }

    public static byte[] toJsonBytes(Object value) {
        try {
            return JacksonMappers.JSON.writeValueAsBytes(value);
        } catch (Exception e) {
            throw new SerdeException("Failed to serialize JSON", e);
        }
    }

    public static <T> T fromJson(byte[] data, Class<T> type) {
        try {
            return JacksonMappers.JSON.readValue(data, type);
        } catch (Exception e) {
            throw new SerdeException("Failed to parse JSON", e);
        }
    }

    // ---- plain json (jackson defaults) ----

    public static String toPlainJson(Object value) {
        try {
            return JacksonMappers.PLAIN.writeValueAsString(value);
        } catch (Exception e) {
            throw new SerdeException("Failed to serialize JSON", e);
        }
    }

    public static <T> T fromPlainJson(byte[] data, Class<T> type) {
        try {
            return JacksonMappers.PLAIN.readValue(data, type);
        } catch (Exception e) {
            throw new SerdeException("Failed to parse JSON", e);
        }
    }

    public static <T> T fromPlainJson(String data, TypeRef<T> type) {
        try {
            ObjectMapper mapper = JacksonMappers.PLAIN;
            return mapper.readValue(data, mapper.getTypeFactory().constructType(type.getType()));
        } catch (Exception e) {
            throw new SerdeException("Failed to parse JSON", e);
        }
    }

    /**
     * Converts a value to the given type, ignoring unknown properties.
     * Keeps the ObjectMapper.convertValue contract: throws IllegalArgumentException on failure.
     */
    public static <T> T convertLenient(Object value, TypeRef<T> type) {
        try {
            ObjectMapper mapper = JacksonMappers.PLAIN_LENIENT;
            return mapper.convertValue(value, mapper.getTypeFactory().constructType(type.getType()));
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}
