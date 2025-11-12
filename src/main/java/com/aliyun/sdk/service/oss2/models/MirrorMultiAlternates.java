package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container to store the configuration for multiple origins in mirror-based back-to-origin.
 */
 @JacksonXmlRootElement(localName = "MirrorMultiAlternates")
public final class MirrorMultiAlternates {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "MirrorMultiAlternate")
    private List<MirrorMultiAlternate> mirrorMultiAlternates;

    public MirrorMultiAlternates() {}

    private MirrorMultiAlternates(Builder builder) { 
        this.mirrorMultiAlternates = builder.mirrorMultiAlternates; 
    }

    /**
    * The configuration list for multiple origins.
    */
    public List<MirrorMultiAlternate> mirrorMultiAlternates() {
        return this.mirrorMultiAlternates;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<MirrorMultiAlternate> mirrorMultiAlternates;
        
        /**
        * The configuration list for multiple origins.
        */
        public Builder mirrorMultiAlternates(List<MirrorMultiAlternate> value) {
            requireNonNull(value);
            this.mirrorMultiAlternates = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MirrorMultiAlternates from) { 
            this.mirrorMultiAlternates = from.mirrorMultiAlternates; 
        }

        public MirrorMultiAlternates build() {
            return new MirrorMultiAlternates(this);
        }
    }
}
