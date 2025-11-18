package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that is used to store the progress of data replication tasks.
 */
 @JacksonXmlRootElement(localName = "ReplicationProgress")
public final class ReplicationProgress {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Rule")
    private List<ReplicationProgressRule> rules;

    public ReplicationProgress() {}

    private ReplicationProgress(Builder builder) { 
        this.rules = builder.rules; 
    }

    /**
    * The container that stores the progress of the data replication task corresponding to each data replication rule.
    */
    public List<ReplicationProgressRule> rules() {
        return this.rules;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<ReplicationProgressRule> rules;
        
        /**
        * The container that stores the progress of the data replication task corresponding to each data replication rule.
        */
        public Builder rules(List<ReplicationProgressRule> value) {
            requireNonNull(value);
            this.rules = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ReplicationProgress from) { 
            this.rules = from.rules; 
        }

        public ReplicationProgress build() {
            return new ReplicationProgress(this);
        }
    }
}
