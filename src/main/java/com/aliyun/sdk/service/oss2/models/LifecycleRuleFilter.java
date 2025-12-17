package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores the Not parameter that is used to filter objects.
 */
 @JacksonXmlRootElement(localName = "Filter")
public final class LifecycleRuleFilter {  
    @JacksonXmlProperty(localName = "ObjectSizeGreaterThan")
    private Long objectSizeGreaterThan;
 
    @JacksonXmlProperty(localName = "ObjectSizeLessThan")
    private Long objectSizeLessThan;
 
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Not")
    private List<LifecycleRuleNot> nots;

    public LifecycleRuleFilter() {}

    private LifecycleRuleFilter(Builder builder) { 
        this.objectSizeGreaterThan = builder.objectSizeGreaterThan; 
        this.objectSizeLessThan = builder.objectSizeLessThan; 
        this.nots = builder.nots;
    }

    /**
    * This lifecycle rule only applies to files larger than this size.
    */
    public Long objectSizeGreaterThan() {
        return this.objectSizeGreaterThan;
    }

    /**
    * This lifecycle rule only applies to files smaller than this size.
    */
    public Long objectSizeLessThan() {
        return this.objectSizeLessThan;
    }

    /**
    * The condition that is matched by objects to which the lifecycle rule does not apply.
    */
    public List<LifecycleRuleNot> nots() {
        return this.nots;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private Long objectSizeGreaterThan;
        private Long objectSizeLessThan;
        private List<LifecycleRuleNot> nots;
        
        /**
        * This lifecycle rule only applies to files larger than this size.
        */
        public Builder objectSizeGreaterThan(Long value) {
            requireNonNull(value);
            this.objectSizeGreaterThan = value;
            return this;
        }
        
        /**
        * This lifecycle rule only applies to files smaller than this size.
        */
        public Builder objectSizeLessThan(Long value) {
            requireNonNull(value);
            this.objectSizeLessThan = value;
            return this;
        }
        
        /**
        * The condition that is matched by objects to which the lifecycle rule does not apply.
        */
        public Builder nots(List<LifecycleRuleNot> value) {
            requireNonNull(value);
            this.nots = value;
            return this;
        }

        /**
         * The condition that is matched by objects to which the lifecycle rule does not apply.
         */
        public Builder not(LifecycleRuleNot value) {
            requireNonNull(value);
            this.nots = Arrays.asList(value);
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(LifecycleRuleFilter from) { 
            this.objectSizeGreaterThan = from.objectSizeGreaterThan; 
            this.objectSizeLessThan = from.objectSizeLessThan; 
            this.nots = from.nots;
        }

        public LifecycleRuleFilter build() {
            return new LifecycleRuleFilter(this);
        }
    }
}