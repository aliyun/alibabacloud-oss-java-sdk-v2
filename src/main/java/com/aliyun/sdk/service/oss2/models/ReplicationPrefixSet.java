package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores prefixes. You can specify up to 10 prefixes in each data replication rule.
 */
 @JacksonXmlRootElement(localName = "ReplicationPrefixSet")
public final class ReplicationPrefixSet {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Prefix")
    private List<String> prefixs;

    public ReplicationPrefixSet() {}

    private ReplicationPrefixSet(Builder builder) { 
        this.prefixs = builder.prefixs; 
    }

    /**
    * The prefix that is used to specify the object that you want to replicate. Only objects whose names contain the specified prefix are replicated to the destination bucket.*   The value of the Prefix parameter can be up to 1,023 characters in length.*   If you specify the Prefix parameter in a data replication rule, OSS synchronizes new data and historical data based on the value of the Prefix parameter.
    */
    public List<String> prefixs() {
        return this.prefixs;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<String> prefixs;
        
        /**
        * The prefix that is used to specify the object that you want to replicate. Only objects whose names contain the specified prefix are replicated to the destination bucket.*   The value of the Prefix parameter can be up to 1,023 characters in length.*   If you specify the Prefix parameter in a data replication rule, OSS synchronizes new data and historical data based on the value of the Prefix parameter.
        */
        public Builder prefixs(List<String> value) {
            requireNonNull(value);
            this.prefixs = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ReplicationPrefixSet from) { 
            this.prefixs = from.prefixs; 
        }

        public ReplicationPrefixSet build() {
            return new ReplicationPrefixSet(this);
        }
    }
}
