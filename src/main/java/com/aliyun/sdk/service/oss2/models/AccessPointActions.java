package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the operations.
 */
 @JacksonXmlRootElement(localName = "Actions")
public final class AccessPointActions {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Action")
    private List<String> action;

    public AccessPointActions() {}

    private AccessPointActions(Builder builder) {
        this.action = builder.action;
    }

    /**
    * The supported OSS API operations. Only the GetObject operation is supported.
    */
    public List<String> action() {
        return this.action;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<String> action;
        
        /**
        * The supported OSS API operations. Only the GetObject operation is supported.
        */
        public Builder action(List<String> value) {
            requireNonNull(value);
            this.action = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(AccessPointActions from) {
            this.action = from.action;
        }

        public AccessPointActions build() {
            return new AccessPointActions(this);
        }
    }
}
