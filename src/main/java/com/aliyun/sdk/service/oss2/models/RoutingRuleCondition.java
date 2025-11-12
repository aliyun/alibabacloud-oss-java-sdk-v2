package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The matching condition. If all of the specified conditions are met, the rule is run. A rule is considered matched only when the rule meets the conditions that are specified by all nodes in Condition.  This parameter must be specified if RoutingRule is specified.
 */
 @JacksonXmlRootElement(localName = "RoutingRuleCondition")
public final class RoutingRuleCondition {  
    @JacksonXmlProperty(localName = "KeyPrefixEquals")
    private String keyPrefixEquals;
 
    @JacksonXmlProperty(localName = "KeySuffixEquals")
    private String keySuffixEquals;
 
    @JacksonXmlProperty(localName = "HttpErrorCodeReturnedEquals")
    private Long httpErrorCodeReturnedEquals;
 
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "IncludeHeader")
    private List<RoutingRuleIncludeHeader> includeHeaders;

    public RoutingRuleCondition() {}

    private RoutingRuleCondition(Builder builder) { 
        this.keyPrefixEquals = builder.keyPrefixEquals; 
        this.keySuffixEquals = builder.keySuffixEquals; 
        this.httpErrorCodeReturnedEquals = builder.httpErrorCodeReturnedEquals; 
        this.includeHeaders = builder.includeHeaders; 
    }

    /**
    * The prefix of object names. Only objects whose names contain the specified prefix match the rule.
    */
    public String keyPrefixEquals() {
        return this.keyPrefixEquals;
    }

    /**
    * Only Objects that match this suffix can match this rule.
    */
    public String keySuffixEquals() {
        return this.keySuffixEquals;
    }

    /**
    * The HTTP status code. The rule is matched only when the specified object is accessed and the specified HTTP status code is returned. If the redirection rule is the mirroring-based back-to-origin rule, the value of this parameter is 404.
    */
    public Long httpErrorCodeReturnedEquals() {
        return this.httpErrorCodeReturnedEquals;
    }

    /**
    * The rule will only be matched when the request contains the specified Header with the specified value. Up to 10 of these can be specified in the container.
    */
    public List<RoutingRuleIncludeHeader> includeHeaders() {
        return this.includeHeaders;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String keyPrefixEquals;
        private String keySuffixEquals;
        private Long httpErrorCodeReturnedEquals;
        private List<RoutingRuleIncludeHeader> includeHeaders;
        
        /**
        * The prefix of object names. Only objects whose names contain the specified prefix match the rule.
        */
        public Builder keyPrefixEquals(String value) {
            requireNonNull(value);
            this.keyPrefixEquals = value;
            return this;
        }
        
        /**
        * Only Objects that match this suffix can match this rule.
        */
        public Builder keySuffixEquals(String value) {
            requireNonNull(value);
            this.keySuffixEquals = value;
            return this;
        }
        
        /**
        * The HTTP status code. The rule is matched only when the specified object is accessed and the specified HTTP status code is returned. If the redirection rule is the mirroring-based back-to-origin rule, the value of this parameter is 404.
        */
        public Builder httpErrorCodeReturnedEquals(Long value) {
            requireNonNull(value);
            this.httpErrorCodeReturnedEquals = value;
            return this;
        }
        
        /**
        * The rule will only be matched when the request contains the specified Header with the specified value. Up to 10 of these can be specified in the container.
        */
        public Builder includeHeaders(List<RoutingRuleIncludeHeader> value) {
            requireNonNull(value);
            this.includeHeaders = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(RoutingRuleCondition from) { 
            this.keyPrefixEquals = from.keyPrefixEquals; 
            this.keySuffixEquals = from.keySuffixEquals; 
            this.httpErrorCodeReturnedEquals = from.httpErrorCodeReturnedEquals; 
            this.includeHeaders = from.includeHeaders; 
        }

        public RoutingRuleCondition build() {
            return new RoutingRuleCondition(this);
        }
    }
}
