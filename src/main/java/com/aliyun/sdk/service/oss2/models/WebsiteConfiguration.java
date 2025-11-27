package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The root node for website configuration.
 */
 @JacksonXmlRootElement(localName = "WebsiteConfiguration")
public final class WebsiteConfiguration {  
    @JacksonXmlProperty(localName = "RoutingRules")
    private RoutingRules routingRules;
 
    @JacksonXmlProperty(localName = "IndexDocument")
    private IndexDocument indexDocument;
 
    @JacksonXmlProperty(localName = "ErrorDocument")
    private ErrorDocument errorDocument;

    public WebsiteConfiguration() {}

    private WebsiteConfiguration(Builder builder) { 
        this.routingRules = builder.routingRules; 
        this.indexDocument = builder.indexDocument; 
        this.errorDocument = builder.errorDocument; 
    }

    /**
    * The container that stores the redirection rules.  You must specify at least one of the following containers: IndexDocument, ErrorDocument, and RoutingRules.
    */
    public RoutingRules routingRules() {
        return this.routingRules;
    }

    /**
    * The container that stores the default homepage.  You must specify at least one of the following containers: IndexDocument, ErrorDocument, and RoutingRules.
    */
    public IndexDocument indexDocument() {
        return this.indexDocument;
    }

    /**
    * The container that stores the default 404 page.  You must specify at least one of the following containers: IndexDocument, ErrorDocument, and RoutingRules.
    */
    public ErrorDocument errorDocument() {
        return this.errorDocument;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private RoutingRules routingRules;
        private IndexDocument indexDocument;
        private ErrorDocument errorDocument;
        
        /**
        * The container that stores the redirection rules.  You must specify at least one of the following containers: IndexDocument, ErrorDocument, and RoutingRules.
        */
        public Builder routingRules(RoutingRules value) {
            requireNonNull(value);
            this.routingRules = value;
            return this;
        }
        
        /**
        * The container that stores the default homepage.  You must specify at least one of the following containers: IndexDocument, ErrorDocument, and RoutingRules.
        */
        public Builder indexDocument(IndexDocument value) {
            requireNonNull(value);
            this.indexDocument = value;
            return this;
        }
        
        /**
        * The container that stores the default 404 page.  You must specify at least one of the following containers: IndexDocument, ErrorDocument, and RoutingRules.
        */
        public Builder errorDocument(ErrorDocument value) {
            requireNonNull(value);
            this.errorDocument = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(WebsiteConfiguration from) { 
            this.routingRules = from.routingRules; 
            this.indexDocument = from.indexDocument; 
            this.errorDocument = from.errorDocument; 
        }

        public WebsiteConfiguration build() {
            return new WebsiteConfiguration(this);
        }
    }
}
