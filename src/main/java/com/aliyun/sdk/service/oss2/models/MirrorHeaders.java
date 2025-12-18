package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The headers contained in the response that is returned when you use mirroring-based back-to-origin. This parameter takes effect only when the value of RedirectType is Mirror.
 */
 @JacksonXmlRootElement(localName = "MirrorHeaders")
public final class MirrorHeaders {  
    @JacksonXmlProperty(localName = "PassAll")
    private Boolean passAll;
 
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Pass")
    private List<String> passs;
 
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Remove")
    private List<String> removes;
 
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Set")
    private List<MirrorHeadersSet> sets;

    public MirrorHeaders() {}

    private MirrorHeaders(Builder builder) { 
        this.passAll = builder.passAll; 
        this.passs = builder.passs; 
        this.removes = builder.removes; 
        this.sets = builder.sets; 
    }

    /**
    * Specifies whether to pass through all request headers other than the following headers to the origin. This parameter takes effect only when the value of RedirectType is Mirror.*   Headers such as content-length, authorization2, authorization, range, and date*   Headers that start with oss-, x-oss-, and x-drs-Default value: false.Valid values:*   true            *   false            
    */
    public Boolean passAll() {
        return this.passAll;
    }

    /**
    * The headers to pass through to the origin. This parameter takes effect only when the value of RedirectType is Mirror. Each specified header can be up to 1,024 bytes in length and can contain only letters, digits, and hyphens (-). You can specify up to 10 headers.
    */
    public List<String> passs() {
        return this.passs;
    }

    /**
    * The headers that are not allowed to pass through to the origin. This parameter takes effect only when the value of RedirectType is Mirror. Each header can be up to 1,024 bytes in length and can contain only letters, digits, and hyphens (-). You can specify up to 10 headers. This parameter is used together with PassAll.
    */
    public List<String> removes() {
        return this.removes;
    }

    /**
    * The headers that are sent to the origin. The specified headers are configured in the data returned by the origin regardless of whether the headers are contained in the request. This parameter takes effect only when the value of RedirectType is Mirror. You can specify up to 10 headers.
    */
    public List<MirrorHeadersSet> sets() {
        return this.sets;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private Boolean passAll;
        private List<String> passs;
        private List<String> removes;
        private List<MirrorHeadersSet> sets;
        
        /**
        * Specifies whether to pass through all request headers other than the following headers to the origin. This parameter takes effect only when the value of RedirectType is Mirror.*   Headers such as content-length, authorization2, authorization, range, and date*   Headers that start with oss-, x-oss-, and x-drs-Default value: false.Valid values:*   true            *   false            
        */
        public Builder passAll(Boolean value) {
            requireNonNull(value);
            this.passAll = value;
            return this;
        }
        
        /**
        * The headers to pass through to the origin. This parameter takes effect only when the value of RedirectType is Mirror. Each specified header can be up to 1,024 bytes in length and can contain only letters, digits, and hyphens (-). You can specify up to 10 headers.
        */
        public Builder pass(String value) {
            requireNonNull(value);
            if (this.passs == null) {
                this.passs = new ArrayList<>();
            }
            this.passs.add(value);
            return this;
        }
        
        /**
        * The headers to pass through to the origin. This parameter takes effect only when the value of RedirectType is Mirror. Each specified header can be up to 1,024 bytes in length and can contain only letters, digits, and hyphens (-). You can specify up to 10 headers.
        */
        public Builder passs(List<String> value) {
            requireNonNull(value);
            this.passs = value;
            return this;
        }
        
        /**
        * The headers that are not allowed to pass through to the origin. This parameter takes effect only when the value of RedirectType is Mirror. Each header can be up to 1,024 bytes in length and can contain only letters, digits, and hyphens (-). You can specify up to 10 headers. This parameter is used together with PassAll.
        */
        public Builder remove(String value) {
            requireNonNull(value);
            if (this.removes == null) {
                this.removes = new ArrayList<>();
            }
            this.removes.add(value);
            return this;
        }
        
        /**
        * The headers that are not allowed to pass through to the origin. This parameter takes effect only when the value of RedirectType is Mirror. Each header can be up to 1,024 bytes in length and can contain only letters, digits, and hyphens (-). You can specify up to 10 headers. This parameter is used together with PassAll.
        */
        public Builder removes(List<String> value) {
            requireNonNull(value);
            this.removes = value;
            return this;
        }
        
        /**
        * The headers that are sent to the origin. The specified headers are configured in the data returned by the origin regardless of whether the headers are contained in the request. This parameter takes effect only when the value of RedirectType is Mirror. You can specify up to 10 headers.
        */
        public Builder sets(List<MirrorHeadersSet> value) {
            requireNonNull(value);

            this.sets = value;
            return this;
        }
        
        /**
        * The headers that are sent to the origin. The specified headers are configured in the data returned by the origin regardless of whether the headers are contained in the request. This parameter takes effect only when the value of RedirectType is Mirror. You can specify up to 10 headers.
        */
        public Builder set(String key, String value) {
            requireNonNull(key);
            requireNonNull(value);
            
            if (this.sets == null) {
                this.sets = new ArrayList<>();
            }
            
            MirrorHeadersSet mirrorHeadersSet = MirrorHeadersSet.newBuilder()
                    .key(key)
                    .value(value)
                    .build();
            this.sets.add(mirrorHeadersSet);
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(MirrorHeaders from) { 
            this.passAll = from.passAll; 
            this.passs = from.passs; 
            this.removes = from.removes; 
            this.sets = from.sets; 
        }

        public MirrorHeaders build() {
            return new MirrorHeaders(this);
        }
    }
}
