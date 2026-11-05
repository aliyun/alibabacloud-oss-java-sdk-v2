package com.aliyun.sdk.service.oss2.internal.serde;

import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.cfg.MapperBuilder;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.xml.XmlMapper;
import tools.jackson.dataformat.xml.XmlWriteFeature;

/**
 * Creates the Jackson mappers (Jackson 3 implementation).
 * This is the only file that differs between the Jackson 2 and Jackson 3 artifacts,
 * the settings are aligned with the Jackson 2 implementation.
 */
final class JacksonMappers {
    // xml protocol: NON_NULL, java.time, ignore unknown properties
    static final ObjectMapper XML = protocol(XmlMapper.builder()
            .disable(XmlWriteFeature.WRITE_NULLS_AS_XSI_NIL)
            .disable(XmlWriteFeature.UNWRAP_ROOT_OBJECT_NODE))
            .build();

    // json protocol: same settings as XML
    static final ObjectMapper JSON = protocol(JsonMapper.builder()).build();

    // plain json: jackson 2 defaults
    static final ObjectMapper PLAIN = jackson2(JsonMapper.builder())
            .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    // plain json, ignore unknown properties
    static final ObjectMapper PLAIN_LENIENT = jackson2(JsonMapper.builder())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    private JacksonMappers() {
    }

    // Jackson 3 changed several defaults, switch them back so that both artifacts behave the same
    // (e.g. property order matters for the checkpoint md5)
    private static <M extends ObjectMapper, B extends MapperBuilder<M, B>> B jackson2(B builder) {
        return builder.configureForJackson2()
                .disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
                .disable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
                .enable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private static <M extends ObjectMapper, B extends MapperBuilder<M, B>> B protocol(B builder) {
        return jackson2(builder)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .changeDefaultPropertyInclusion(v -> JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL));
    }
}
