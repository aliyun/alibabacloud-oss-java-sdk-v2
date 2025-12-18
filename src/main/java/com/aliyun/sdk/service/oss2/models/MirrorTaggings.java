package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The rules for setting tags when saving files during mirror-based back-to-origin.
 */
 @JacksonXmlRootElement(localName = "MirrorTaggings")
public final class MirrorTaggings {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Taggings")
    private List<MirrorTagging> taggings;

    public MirrorTaggings() {}

    private MirrorTaggings(Builder builder) { 
        this.taggings = builder.taggings;
    }

    /**
    * The rule list for setting tags.
    */
    public List<MirrorTagging> taggings() {
        return this.taggings;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<MirrorTagging> taggings;
        
        /**
        * The rule list for setting tags.
        */
        public Builder taggings(List<MirrorTagging> value) {
            requireNonNull(value);
            this.taggings = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MirrorTaggings from) { 
            this.taggings = from.taggings;
        }

        public MirrorTaggings build() {
            return new MirrorTaggings(this);
        }
    }
}
