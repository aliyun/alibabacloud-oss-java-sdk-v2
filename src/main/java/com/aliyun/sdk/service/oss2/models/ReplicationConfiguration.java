package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores data replication configurations.
 */
 @JacksonXmlRootElement(localName = "ReplicationConfiguration")
public final class ReplicationConfiguration {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Rule")
    private List<ReplicationRule> rules;

    public ReplicationConfiguration() {}

    private ReplicationConfiguration(Builder builder) { 
        this.rules = builder.rules; 
    }

    /**
    * The container that stores the data replication rules.
    */
    public List<ReplicationRule> rules() {
        return this.rules;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<ReplicationRule> rules;
        
        /**
        * The container that stores the data replication rules.
        */
        public Builder rules(List<ReplicationRule> value) {
            requireNonNull(value);
            this.rules = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ReplicationConfiguration from) { 
            this.rules = from.rules; 
        }

        public ReplicationConfiguration build() {
            return new ReplicationConfiguration(this);
        }
    }
}
