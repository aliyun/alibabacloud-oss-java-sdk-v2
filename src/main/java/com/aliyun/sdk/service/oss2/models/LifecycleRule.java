package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores lifecycle rules.*   A lifecycle rule cannot be configured to convert the storage class of objects in an Archive bucket.*   The period of time from when the objects expire to when the objects are deleted must be longer than the period of time from when the objects expire to when the storage class of the objects is converted to IA or Archive.
 */
 @JacksonXmlRootElement(localName = "LifecycleRule")
public final class LifecycleRule {  
    @JacksonXmlProperty(localName = "ID")
    private String id;
 
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Transition")
    private List<Transition> transitions;
 
    @JacksonXmlProperty(localName = "NoncurrentVersionExpiration")
    private NoncurrentVersionExpiration noncurrentVersionExpiration;
 
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "NoncurrentVersionTransition")
    private List<NoncurrentVersionTransition> noncurrentVersionTransitions;
 
    @JacksonXmlProperty(localName = "Filter")
    private LifecycleRuleFilter filter;
 
    @JacksonXmlProperty(localName = "Prefix")
    private String prefix;
 
    @JacksonXmlProperty(localName = "Status")
    private String status;
 
    @JacksonXmlProperty(localName = "Expiration")
    private Expiration expiration;
 
    @JacksonXmlProperty(localName = "AbortMultipartUpload")
    private AbortMultipartUpload abortMultipartUpload;
 
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Tag")
    private List<Tag> tags;
 
    @JacksonXmlProperty(localName = "AtimeBase")
    private Long atimeBase;

    public LifecycleRule() {}

    private LifecycleRule(Builder builder) { 
        this.id = builder.id;
        this.transitions = builder.transitions; 
        this.noncurrentVersionExpiration = builder.noncurrentVersionExpiration; 
        this.noncurrentVersionTransitions = builder.noncurrentVersionTransitions; 
        this.filter = builder.filter; 
        this.prefix = builder.prefix; 
        this.status = builder.status; 
        this.expiration = builder.expiration; 
        this.abortMultipartUpload = builder.abortMultipartUpload; 
        this.tags = builder.tags; 
        this.atimeBase = builder.atimeBase; 
    }

    /**
    * The ID of the lifecycle rule. The ID can contain up to 255 characters. If you do not specify the ID, OSS automatically generates a unique ID for the lifecycle rule.
    */
    public String id() {
        return this.id;
    }

    /**
    * The conversion of the storage class of objects that match the lifecycle rule when the objects expire. The storage class of the objects can be converted to IA, Archive, and ColdArchive. The storage class of Standard objects in a Standard bucket can be converted to IA, Archive, or Cold Archive. The period of time from when the objects expire to when the storage class of the objects is converted to Archive must be longer than the period of time from when the objects expire to when the storage class of the objects is converted to IA. For example, if the validity period is set to 30 for objects whose storage class is converted to IA after the validity period, the validity period must be set to a value greater than 30 for objects whose storage class is converted to Archive.  Either Days or CreatedBeforeDate is required.
    */
    public List<Transition> transitions() {
        return this.transitions;
    }

    /**
    * The delete operation that you want OSS to perform on the previous versions of the objects that match the lifecycle rule when the previous versions expire.
    */
    public NoncurrentVersionExpiration noncurrentVersionExpiration() {
        return this.noncurrentVersionExpiration;
    }

    /**
    * The conversion of the storage class of previous versions of the objects that match the lifecycle rule when the previous versions expire. The storage class of the previous versions can be converted to IA or Archive. The period of time from when the previous versions expire to when the storage class of the previous versions is converted to Archive must be longer than the period of time from when the previous versions expire to when the storage class of the previous versions is converted to IA.
    */
    public List<NoncurrentVersionTransition> noncurrentVersionTransitions() {
        return this.noncurrentVersionTransitions;
    }

    /**
    * The container that stores the Not parameter that is used to filter objects.
    */
    public LifecycleRuleFilter filter() {
        return this.filter;
    }

    /**
    * The prefix in the names of the objects to which the rule applies. The prefixes specified by different rules cannot overlap.*   If Prefix is specified, this rule applies only to objects whose names contain the specified prefix in the bucket.*   If Prefix is not specified, this rule applies to all objects in the bucket.
    */
    public String prefix() {
        return this.prefix;
    }

    /**
    * Specifies whether to enable the rule. Valid values:*   Enabled: enables the rule. OSS periodically executes the rule.*   Disabled: does not enable the rule. OSS ignores the rule.
    */
    public String status() {
        return this.status;
    }

    /**
    * The delete operation to perform on objects based on the lifecycle rule. For an object in a versioning-enabled bucket, the delete operation specified by this parameter is performed only on the current version of the object.The period of time from when the objects expire to when the objects are deleted must be longer than the period of time from when the objects expire to when the storage class of the objects is converted to IA or Archive.
    */
    public Expiration expiration() {
        return this.expiration;
    }

    /**
    * The delete operation that you want OSS to perform on the parts that are uploaded in incomplete multipart upload tasks when the parts expire.
    */
    public AbortMultipartUpload abortMultipartUpload() {
        return this.abortMultipartUpload;
    }

    /**
    * The tag of the objects to which the lifecycle rule applies. You can specify multiple tags.
    */
    public List<Tag> tags() {
        return this.tags;
    }

    /**
    * Timestamp for when access tracking was enabled.
    */
    public Long atimeBase() {
        return this.atimeBase;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String id;
        private List<Transition> transitions;
        private NoncurrentVersionExpiration noncurrentVersionExpiration;
        private List<NoncurrentVersionTransition> noncurrentVersionTransitions;
        private LifecycleRuleFilter filter;
        private String prefix;
        private String status;
        private Expiration expiration;
        private AbortMultipartUpload abortMultipartUpload;
        private List<Tag> tags;
        private Long atimeBase;
        
        /**
        * The ID of the lifecycle rule. The ID can contain up to 255 characters. If you do not specify the ID, OSS automatically generates a unique ID for the lifecycle rule.
        */
        public Builder id(String value) {
            requireNonNull(value);
            this.id = value;
            return this;
        }
        
        /**
        * The conversion of the storage class of objects that match the lifecycle rule when the objects expire. The storage class of the objects can be converted to IA, Archive, and ColdArchive. The storage class of Standard objects in a Standard bucket can be converted to IA, Archive, or Cold Archive. The period of time from when the objects expire to when the storage class of the objects is converted to Archive must be longer than the period of time from when the objects expire to when the storage class of the objects is converted to IA. For example, if the validity period is set to 30 for objects whose storage class is converted to IA after the validity period, the validity period must be set to a value greater than 30 for objects whose storage class is converted to Archive.  Either Days or CreatedBeforeDate is required.
        */
        public Builder transitions(List<Transition> value) {
            requireNonNull(value);
            this.transitions = value;
            return this;
        }
        
        /**
        * The delete operation that you want OSS to perform on the previous versions of the objects that match the lifecycle rule when the previous versions expire.
        */
        public Builder noncurrentVersionExpiration(NoncurrentVersionExpiration value) {
            requireNonNull(value);
            this.noncurrentVersionExpiration = value;
            return this;
        }
        
        /**
        * The conversion of the storage class of previous versions of the objects that match the lifecycle rule when the previous versions expire. The storage class of the previous versions can be converted to IA or Archive. The period of time from when the previous versions expire to when the storage class of the previous versions is converted to Archive must be longer than the period of time from when the previous versions expire to when the storage class of the previous versions is converted to IA.
        */
        public Builder noncurrentVersionTransitions(List<NoncurrentVersionTransition> value) {
            requireNonNull(value);
            this.noncurrentVersionTransitions = value;
            return this;
        }
        
        /**
        * The container that stores the Not parameter that is used to filter objects.
        */
        public Builder filter(LifecycleRuleFilter value) {
            requireNonNull(value);
            this.filter = value;
            return this;
        }
        
        /**
        * The prefix in the names of the objects to which the rule applies. The prefixes specified by different rules cannot overlap.*   If Prefix is specified, this rule applies only to objects whose names contain the specified prefix in the bucket.*   If Prefix is not specified, this rule applies to all objects in the bucket.
        */
        public Builder prefix(String value) {
            requireNonNull(value);
            this.prefix = value;
            return this;
        }
        
        /**
        * Specifies whether to enable the rule. Valid values:*   Enabled: enables the rule. OSS periodically executes the rule.*   Disabled: does not enable the rule. OSS ignores the rule.
        */
        public Builder status(String value) {
            requireNonNull(value);
            this.status = value;
            return this;
        }
        
        /**
        * The delete operation to perform on objects based on the lifecycle rule. For an object in a versioning-enabled bucket, the delete operation specified by this parameter is performed only on the current version of the object.The period of time from when the objects expire to when the objects are deleted must be longer than the period of time from when the objects expire to when the storage class of the objects is converted to IA or Archive.
        */
        public Builder expiration(Expiration value) {
            requireNonNull(value);
            this.expiration = value;
            return this;
        }
        
        /**
        * The delete operation that you want OSS to perform on the parts that are uploaded in incomplete multipart upload tasks when the parts expire.
        */
        public Builder abortMultipartUpload(AbortMultipartUpload value) {
            requireNonNull(value);
            this.abortMultipartUpload = value;
            return this;
        }
        
        /**
        * The tag of the objects to which the lifecycle rule applies. You can specify multiple tags.
        */
        public Builder tags(List<Tag> value) {
            requireNonNull(value);
            this.tags = value;
            return this;
        }
        
        /**
        * Timestamp for when access tracking was enabled.
        */
        public Builder atimeBase(Long value) {
            requireNonNull(value);
            this.atimeBase = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(LifecycleRule from) { 
            this.id = from.id;
            this.transitions = from.transitions; 
            this.noncurrentVersionExpiration = from.noncurrentVersionExpiration; 
            this.noncurrentVersionTransitions = from.noncurrentVersionTransitions; 
            this.filter = from.filter; 
            this.prefix = from.prefix; 
            this.status = from.status; 
            this.expiration = from.expiration; 
            this.abortMultipartUpload = from.abortMultipartUpload; 
            this.tags = from.tags; 
            this.atimeBase = from.atimeBase; 
        }

        public LifecycleRule build() {
            return new LifecycleRule(this);
        }
    }
}
