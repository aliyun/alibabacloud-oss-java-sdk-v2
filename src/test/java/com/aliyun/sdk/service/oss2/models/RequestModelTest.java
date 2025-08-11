package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.TreeMap;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RequestModelTest {

    @Test
    public void setterAndGetterTest() {
        // empty
        PutApiRequest request = new PutApiRequest.Builder().build();
        assertNull(request.strField());
        assertNull(request.longField());
        assertNull(request.strHeader());
        assertNull(request.longHeader());
        assertNull(request.boolHeader());
        assertNull(request.strParameter());
        assertNull(request.longParameter());
        assertNull(request.boolParameter());

        // full
        request = new PutApiRequest.Builder()
                .strField("strField")
                .longField(12L)
                .strHeader("strHeader")
                .strParameter("strParameter")
                .longParameter(123L)
                .longHeader(124L)
                .boolHeader(true)
                .boolParameter(false)
                .build();

        assertEquals("strField", request.strField());
        assertEquals(Long.valueOf(12L), request.longField());
        assertEquals("strHeader", request.strHeader());
        assertEquals(Long.valueOf(124L), request.longHeader());
        assertEquals(true, request.boolHeader());
        assertEquals("strParameter", request.strParameter());
        assertEquals(Long.valueOf(123L), request.longParameter());
        assertEquals(false, request.boolParameter());

        // duplicate set
        request = new PutApiRequest.Builder()
                .strField("strField")
                .longField(12L)
                .strHeader("strHeader")
                .strParameter("strParameter")
                .strParameter("strParameter-1")
                .longParameter(123L)
                .longHeader(124L)
                .boolHeader(true)
                .boolParameter(false)
                .build();

        assertEquals("strField", request.strField());
        assertEquals(Long.valueOf(12L), request.longField());
        assertEquals("strHeader", request.strHeader());
        assertEquals(Long.valueOf(124L), request.longHeader());
        assertEquals(true, request.boolHeader());
        assertEquals("strParameter-1", request.strParameter());
        assertEquals(Long.valueOf(123L), request.longParameter());
        assertEquals(false, request.boolParameter());

        // from headers & parameters
        request = new PutApiRequest.Builder()
                .strField("strField")
                .longField(12L)
                .headers(new TreeMap<String, String>() {{
                    put("x-str-header", "str-header");
                    put("x-long-header", "123");
                    put("x-bool-header", "true");
                }})
                .parameters(new HashMap<String, String>() {{
                    put("x-str-parameter", "str-parameter");
                    put("x-long-parameter", "124");
                    put("x-bool-parameter", "false");
                }})
                .build();

        assertEquals("strField", request.strField());
        assertEquals(Long.valueOf(12L), request.longField());
        assertEquals("str-header", request.strHeader());
        assertEquals(Long.valueOf(123L), request.longHeader());
        assertEquals(true, request.boolHeader());
        assertEquals("str-parameter", request.strParameter());
        assertEquals(Long.valueOf(124L), request.longParameter());
        assertEquals(false, request.boolParameter());

        // from headers & parameters & overwrite
        request = new PutApiRequest.Builder()
                .strField("strField")
                .longField(12L)
                .headers(new TreeMap<String, String>() {{
                    put("x-str-header", "str-header");
                    put("x-long-header", "123");
                    put("x-bool-header", "true");
                }})
                .parameters(new HashMap<String, String>() {{
                    put("x-str-parameter", "str-parameter");
                    put("x-long-parameter", "124");
                    put("x-bool-parameter", "false");
                }})
                .strHeader("strHeader")
                .strParameter("strParameter")
                .build();

        assertEquals("strField", request.strField());
        assertEquals(Long.valueOf(12L), request.longField());
        assertEquals("strHeader", request.strHeader());
        assertEquals(Long.valueOf(123L), request.longHeader());
        assertEquals(true, request.boolHeader());
        assertEquals("strParameter", request.strParameter());
        assertEquals(Long.valueOf(124L), request.longParameter());
        assertEquals(false, request.boolParameter());

    }

    static class PutApiRequest extends RequestModel {
        private final String strField;
        private final Long longField;

        PutApiRequest(Builder builder) {
            super(builder);
            this.strField = builder.strField;
            this.longField = builder.longField;
        }

        public String strField() {
            return strField;
        }

        public Long longField() {
            return longField;
        }

        public String strHeader() {
            return headers.get("x-str-header");
        }

        public Long longHeader() {
            return ConvertUtils.toLongOrNull(headers.get("x-long-header"));
        }

        public Boolean boolHeader() {
            return ConvertUtils.toBoolOrNull(headers.get("x-bool-header"));
        }

        public String strParameter() {
            return parameters.get("x-str-parameter");
        }

        public Long longParameter() {
            return ConvertUtils.toLongOrNull(parameters.get("x-long-parameter"));
        }

        public Boolean boolParameter() {
            return ConvertUtils.toBoolOrNull(parameters.get("x-bool-parameter"));
        }

        public static class Builder extends RequestModel.Builder<PutApiRequest.Builder> {
            private String strField;
            private Long longField;

            private Builder() {
                super();
            }

            private Builder(PutApiRequest from) {
                super(from);
                this.strField = from.strField;
                this.longField = from.longField;
            }

            public Builder strField(String value) {
                requireNonNull(value);
                this.strField = value;
                return this;
            }

            public Builder longField(Long value) {
                requireNonNull(value);
                this.longField = value;
                return this;
            }

            public Builder strHeader(String value) {
                requireNonNull(value);
                this.headers.put("x-str-header", value);
                return this;
            }

            public Builder longHeader(Long value) {
                requireNonNull(value);
                this.headers.put("x-long-header", value.toString());
                return this;
            }

            public Builder boolHeader(Boolean value) {
                requireNonNull(value);
                this.headers.put("x-bool-header", value.toString().toLowerCase());
                return this;
            }

            public Builder strParameter(String value) {
                requireNonNull(value);
                this.parameters.put("x-str-parameter", value);
                return this;
            }

            public Builder longParameter(Long value) {
                requireNonNull(value);
                this.parameters.put("x-long-parameter", value.toString());
                return this;
            }

            public Builder boolParameter(Boolean value) {
                requireNonNull(value);
                this.parameters.put("x-bool-parameter", value.toString().toLowerCase());
                return this;
            }

            PutApiRequest build() {
                return new PutApiRequest(this);
            }
        }
    }
}
