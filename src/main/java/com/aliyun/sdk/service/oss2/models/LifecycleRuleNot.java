package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The condition that is matched by objects to which the lifecycle rule does not apply.
 */
@JacksonXmlRootElement(localName = "Not")
public final class LifecycleRuleNot {  
    @JacksonXmlProperty(localName = "Prefix")
    private String prefix;
 
    @JacksonXmlProperty(localName = "Tag")
    private Tag tag;

    public LifecycleRuleNot() {}

    private LifecycleRuleNot(Builder builder) { 
        this.prefix = builder.prefix; 
        this.tag = builder.tag; 
    }

    /**
    * The prefix in the names of the objects to which the lifecycle rule does not apply.
    */
    public String prefix() {
        return this.prefix;
    }

    /**
    * The tag of the objects to which the lifecycle rule does not apply.
    */
    public Tag tag() {
        return this.tag;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String prefix;
        private Tag tag;
        
        /**
        * The prefix in the names of the objects to which the lifecycle rule does not apply.
        */
        public Builder prefix(String value) {
            requireNonNull(value);
            this.prefix = value;
            return this;
        }
        
        /**
        * The tag of the objects to which the lifecycle rule does not apply.
        */
        public Builder tag(Tag value) {
            requireNonNull(value);
            this.tag = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(LifecycleRuleNot from) { 
            this.prefix = from.prefix; 
            this.tag = from.tag; 
        }

        public LifecycleRuleNot build() {
            return new LifecycleRuleNot(this);
        }
    }
}