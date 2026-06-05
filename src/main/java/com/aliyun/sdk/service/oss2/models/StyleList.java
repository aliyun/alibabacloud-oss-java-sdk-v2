package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that was used to query the information about image styles.
 */
 @JacksonXmlRootElement(localName = "StyleList")
public final class StyleList {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Style")
    private List<StyleInfo> styles;

    public StyleList() {}

    private StyleList(Builder builder) { 
        this.styles = builder.styles; 
    }

    /**
    * The list of styles.
    */
    public List<StyleInfo> styles() {
        return this.styles;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<StyleInfo> styles;
        
        /**
        * The list of styles.
        */
        public Builder styles(List<StyleInfo> value) {
            requireNonNull(value);
            this.styles = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(StyleList from) { 
            this.styles = from.styles; 
        }

        public StyleList build() {
            return new StyleList(this);
        }
    }
}
