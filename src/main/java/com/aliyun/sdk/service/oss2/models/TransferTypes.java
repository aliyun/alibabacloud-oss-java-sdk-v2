package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the transfer type.
 */
 @JacksonXmlRootElement(localName = "TransferTypes")
public final class TransferTypes {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Type")
    private List<String> types;

    public TransferTypes() {}

    private TransferTypes(Builder builder) { 
        this.types = builder.types; 
    }

    /**
    * The data transfer type that is used to transfer data in data replication. Valid values:*   internal (default): the default data transfer link used in OSS.*   oss_acc: the link in which data transmission is accelerated. You can set TransferType to oss_acc only when you create CRR rules.
    */
    public List<String> types() {
        return this.types;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<String> types;
        
        /**
        * The data transfer type that is used to transfer data in data replication. Valid values:*   internal (default): the default data transfer link used in OSS.*   oss_acc: the link in which data transmission is accelerated. You can set TransferType to oss_acc only when you create CRR rules.
        */
        public Builder types(List<String> value) {
            requireNonNull(value);
            this.types = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(TransferTypes from) { 
            this.types = from.types; 
        }

        public TransferTypes build() {
            return new TransferTypes(this);
        }
    }
}
