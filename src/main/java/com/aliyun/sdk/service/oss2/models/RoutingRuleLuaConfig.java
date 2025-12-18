package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * Lua script config for the routing rule.
 */
 @JacksonXmlRootElement(localName = "RoutingRuleLuaConfig")
public final class RoutingRuleLuaConfig {  
    @JacksonXmlProperty(localName = "Script")
    private String script;

    public RoutingRuleLuaConfig() {}

    private RoutingRuleLuaConfig(Builder builder) { 
        this.script = builder.script; 
    }

    /**
    * The name of the Lua script.
    */
    public String script() {
        return this.script;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String script;
        
        /**
        * The name of the Lua script.
        */
        public Builder script(String value) {
            requireNonNull(value);
            this.script = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(RoutingRuleLuaConfig from) { 
            this.script = from.script; 
        }

        public RoutingRuleLuaConfig build() {
            return new RoutingRuleLuaConfig(this);
        }
    }
}
