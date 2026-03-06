package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "PointInt64")
public final class PointInt64 {

    @JacksonXmlProperty(localName = "X")
    private Long x;

    @JacksonXmlProperty(localName = "Y")
    private Long y;

    public PointInt64() {
    }

    private PointInt64(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
    }

    public Long x() {
        return this.x;
    }

    public Long y() {
        return this.y;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Long x;
        private Long y;

        public Builder x(Long value) {
            this.x = value;
            return this;
        }

        public Builder y(Long value) {
            this.y = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(PointInt64 from) {
            this.x = from.x;
            this.y = from.y;
        }

        public PointInt64 build() {
            return new PointInt64(this);
        }
    }
}
