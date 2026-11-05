package com.aliyun.sdk.service.oss2.internal.serde;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Creates the Jackson mappers (Jackson 2 implementation).
 * This is the only file that differs between the Jackson 2 and Jackson 3 artifacts.
 */
final class JacksonMappers {
    // xml protocol: NON_NULL, java.time, ignore unknown properties
    static final ObjectMapper XML = protocol(new XmlMapper());

    // json protocol: same settings as XML
    static final ObjectMapper JSON = protocol(new ObjectMapper());

    // plain json: jackson defaults
    static final ObjectMapper PLAIN = new ObjectMapper();

    // plain json, ignore unknown properties
    static final ObjectMapper PLAIN_LENIENT = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private JacksonMappers() {
    }

    private static ObjectMapper protocol(ObjectMapper mapper) {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
