package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container for the redirection rule or mirroring-based back-to-origin rule. You can specify up to 20 rules.
 */
 @JacksonXmlRootElement(localName = "RoutingRule")
public final class RoutingRule {  
    @JacksonXmlProperty(localName = "LuaConfig")
    private RoutingRuleLuaConfig luaConfig;
 
    @JacksonXmlProperty(localName = "RuleNumber")
    private Long ruleNumber;
 
    @JacksonXmlProperty(localName = "Condition")
    private RoutingRuleCondition condition;
 
    @JacksonXmlProperty(localName = "Redirect")
    private RoutingRuleRedirect redirect;

    public RoutingRule() {}

    private RoutingRule(Builder builder) { 
        this.luaConfig = builder.luaConfig; 
        this.ruleNumber = builder.ruleNumber; 
        this.condition = builder.condition; 
        this.redirect = builder.redirect; 
    }

    /**
    * The Lua script config of this rule.
    */
    public RoutingRuleLuaConfig luaConfig() {
        return this.luaConfig;
    }

    /**
    * The sequence number that is used to match and run the redirection rules. OSS matches redirection rules based on this parameter. If a match succeeds, only the rule is run and the subsequent rules are not run.  This parameter must be specified if RoutingRule is specified.
    */
    public Long ruleNumber() {
        return this.ruleNumber;
    }

    /**
    * The matching condition. If all of the specified conditions are met, the rule is run. A rule is considered matched only when the rule meets the conditions that are specified by all nodes in Condition.  This parameter must be specified if RoutingRule is specified.
    */
    public RoutingRuleCondition condition() {
        return this.condition;
    }

    /**
    * The operation to perform after the rule is matched.  This parameter must be specified if RoutingRule is specified.
    */
    public RoutingRuleRedirect redirect() {
        return this.redirect;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private RoutingRuleLuaConfig luaConfig;
        private Long ruleNumber;
        private RoutingRuleCondition condition;
        private RoutingRuleRedirect redirect;
        
        /**
        * The Lua script config of this rule.
        */
        public Builder luaConfig(RoutingRuleLuaConfig value) {
            requireNonNull(value);
            this.luaConfig = value;
            return this;
        }
        
        /**
        * The sequence number that is used to match and run the redirection rules. OSS matches redirection rules based on this parameter. If a match succeeds, only the rule is run and the subsequent rules are not run.  This parameter must be specified if RoutingRule is specified.
        */
        public Builder ruleNumber(Long value) {
            requireNonNull(value);
            this.ruleNumber = value;
            return this;
        }
        
        /**
        * The matching condition. If all of the specified conditions are met, the rule is run. A rule is considered matched only when the rule meets the conditions that are specified by all nodes in Condition.  This parameter must be specified if RoutingRule is specified.
        */
        public Builder condition(RoutingRuleCondition value) {
            requireNonNull(value);
            this.condition = value;
            return this;
        }
        
        /**
        * The operation to perform after the rule is matched.  This parameter must be specified if RoutingRule is specified.
        */
        public Builder redirect(RoutingRuleRedirect value) {
            requireNonNull(value);
            this.redirect = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(RoutingRule from) { 
            this.luaConfig = from.luaConfig; 
            this.ruleNumber = from.ruleNumber; 
            this.condition = from.condition; 
            this.redirect = from.redirect; 
        }

        public RoutingRule build() {
            return new RoutingRule(this);
        }
    }
}
