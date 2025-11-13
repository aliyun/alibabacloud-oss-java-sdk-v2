package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the custom domain names.
 */
 @JacksonXmlRootElement(localName = "Cnames")
public final class Cnames {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Domain")
    private List<String> domains;

    public Cnames() {}

    private Cnames(Builder builder) { 
        this.domains = builder.domains; 
    }

    /**
    * The custom domain names.
    */
    public List<String> domains() {
        return this.domains;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<String> domains;
        
        /**
        * The custom domain names.
        */
        public Builder domains(List<String> value) {
            requireNonNull(value);
            this.domains = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(Cnames from) { 
            this.domains = from.domains; 
        }

        public Cnames build() {
            return new Cnames(this);
        }
    }
}
