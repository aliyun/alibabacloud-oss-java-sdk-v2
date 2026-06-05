package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores style information.
 */
 @JacksonXmlRootElement(localName = "StyleInfo")
public final class StyleInfo {  
    @JacksonXmlProperty(localName = "Name")
    private String name;
 
    @JacksonXmlProperty(localName = "Content")
    private String content;
 
    @JacksonXmlProperty(localName = "CreateTime")
    private String createTime;
 
    @JacksonXmlProperty(localName = "LastModifyTime")
    private String lastModifyTime;
 
    @JacksonXmlProperty(localName = "Category")
    private String category;

    public StyleInfo() {}

    private StyleInfo(Builder builder) { 
        this.name = builder.name; 
        this.content = builder.content; 
        this.createTime = builder.createTime; 
        this.lastModifyTime = builder.lastModifyTime; 
        this.category = builder.category; 
    }

    /**
    * The style name.
    */
    public String name() {
        return this.name;
    }

    /**
    * The content of the style.
    */
    public String content() {
        return this.content;
    }

    /**
    * The time when the style was created.
    */
    public String createTime() {
        return this.createTime;
    }

    /**
    * The time when the style was last modified.
    */
    public String lastModifyTime() {
        return this.lastModifyTime;
    }

    /**
    * The category of this style。  Invalid value：image、document、video。
    */
    public String category() {
        return this.category;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String name;
        private String content;
        private String createTime;
        private String lastModifyTime;
        private String category;
        
        /**
        * The style name.
        */
        public Builder name(String value) {
            requireNonNull(value);
            this.name = value;
            return this;
        }
        
        /**
        * The content of the style.
        */
        public Builder content(String value) {
            requireNonNull(value);
            this.content = value;
            return this;
        }
        
        /**
        * The time when the style was created.
        */
        public Builder createTime(String value) {
            requireNonNull(value);
            this.createTime = value;
            return this;
        }
        
        /**
        * The time when the style was last modified.
        */
        public Builder lastModifyTime(String value) {
            requireNonNull(value);
            this.lastModifyTime = value;
            return this;
        }
        
        /**
        * The category of this style。  Invalid value：image、document、video。
        */
        public Builder category(String value) {
            requireNonNull(value);
            this.category = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(StyleInfo from) { 
            this.name = from.name; 
            this.content = from.content; 
            this.createTime = from.createTime; 
            this.lastModifyTime = from.lastModifyTime; 
            this.category = from.category; 
        }

        public StyleInfo build() {
            return new StyleInfo(this);
        }
    }
}
