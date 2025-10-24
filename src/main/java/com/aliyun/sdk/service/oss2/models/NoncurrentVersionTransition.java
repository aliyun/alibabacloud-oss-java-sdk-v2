package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The conversion of the storage class of previous versions of the objects that match the lifecycle rule when the previous versions expire. The storage class of the previous versions can be converted to IA or Archive. The period of time from when the previous versions expire to when the storage class of the previous versions is converted to Archive must be longer than the period of time from when the previous versions expire to when the storage class of the previous versions is converted to IA.
 */
 @JacksonXmlRootElement(localName = "NoncurrentVersionTransition")
public final class NoncurrentVersionTransition {  
    @JacksonXmlProperty(localName = "StorageClass")
    private String storageClass;
 
    @JacksonXmlProperty(localName = "IsAccessTime")
    private Boolean isAccessTime;
 
    @JacksonXmlProperty(localName = "ReturnToStdWhenVisit")
    private Boolean returnToStdWhenVisit;
 
    @JacksonXmlProperty(localName = "AllowSmallFile")
    private Boolean allowSmallFile;
 
    @JacksonXmlProperty(localName = "NoncurrentDays")
    private Integer noncurrentDays;

    public NoncurrentVersionTransition() {}

    private NoncurrentVersionTransition(Builder builder) { 
        this.storageClass = builder.storageClass; 
        this.isAccessTime = builder.isAccessTime; 
        this.returnToStdWhenVisit = builder.returnToStdWhenVisit; 
        this.allowSmallFile = builder.allowSmallFile; 
        this.noncurrentDays = builder.noncurrentDays; 
    }

    /**
    * The storage class to which objects are converted. Valid values:*   IA*   Archive*   ColdArchive  You can convert the storage class of objects in an IA bucket to only Archive or Cold Archive.
    */
    public String storageClass() {
        return this.storageClass;
    }

    /**
    * Specifies whether the lifecycle rule applies to objects based on their last access time. Valid values:*   true: The rule applies to objects based on their last access time.*   false: The rule applies to objects based on their last modified time.
    */
    public Boolean isAccessTime() {
        return this.isAccessTime;
    }

    /**
    * Specifies whether to convert the storage class of non-Standard objects back to Standard after the objects are accessed. This parameter takes effect only when the IsAccessTime parameter is set to true. Valid values:*   true: converts the storage class of the objects to Standard.*   false: does not convert the storage class of the objects to Standard.
    */
    public Boolean returnToStdWhenVisit() {
        return this.returnToStdWhenVisit;
    }

    /**
    * Specifies whether to convert the storage class of objects whose sizes are less than 64 KB to IA, Archive, or Cold Archive based on their last access time. Valid values:*   true: converts the storage class of objects that are smaller than 64 KB to IA, Archive, or Cold Archive. Objects that are smaller than 64 KB are charged as 64 KB. Objects that are greater than or equal to 64 KB are charged based on their actual sizes. If you set this parameter to true, the storage fees may increase.*   false: does not convert the storage class of an object that is smaller than 64 KB.
    */
    public Boolean allowSmallFile() {
        return this.allowSmallFile;
    }

    /**
    * The number of days from when the objects became previous versions to when the lifecycle rule takes effect.
    */
    public Integer noncurrentDays() {
        return this.noncurrentDays;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String storageClass;
        private Boolean isAccessTime;
        private Boolean returnToStdWhenVisit;
        private Boolean allowSmallFile;
        private Integer noncurrentDays;
        
        /**
        * The storage class to which objects are converted. Valid values:*   IA*   Archive*   ColdArchive  You can convert the storage class of objects in an IA bucket to only Archive or Cold Archive.
        */
        public Builder storageClass(String value) {
            requireNonNull(value);
            this.storageClass = value;
            return this;
        }
        
        /**
        * Specifies whether the lifecycle rule applies to objects based on their last access time. Valid values:*   true: The rule applies to objects based on their last access time.*   false: The rule applies to objects based on their last modified time.
        */
        public Builder isAccessTime(Boolean value) {
            requireNonNull(value);
            this.isAccessTime = value;
            return this;
        }
        
        /**
        * Specifies whether to convert the storage class of non-Standard objects back to Standard after the objects are accessed. This parameter takes effect only when the IsAccessTime parameter is set to true. Valid values:*   true: converts the storage class of the objects to Standard.*   false: does not convert the storage class of the objects to Standard.
        */
        public Builder returnToStdWhenVisit(Boolean value) {
            requireNonNull(value);
            this.returnToStdWhenVisit = value;
            return this;
        }
        
        /**
        * Specifies whether to convert the storage class of objects whose sizes are less than 64 KB to IA, Archive, or Cold Archive based on their last access time. Valid values:*   true: converts the storage class of objects that are smaller than 64 KB to IA, Archive, or Cold Archive. Objects that are smaller than 64 KB are charged as 64 KB. Objects that are greater than or equal to 64 KB are charged based on their actual sizes. If you set this parameter to true, the storage fees may increase.*   false: does not convert the storage class of an object that is smaller than 64 KB.
        */
        public Builder allowSmallFile(Boolean value) {
            requireNonNull(value);
            this.allowSmallFile = value;
            return this;
        }
        
        /**
        * The number of days from when the objects became previous versions to when the lifecycle rule takes effect.
        */
        public Builder noncurrentDays(Integer value) {
            requireNonNull(value);
            this.noncurrentDays = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(NoncurrentVersionTransition from) { 
            this.storageClass = from.storageClass; 
            this.isAccessTime = from.isAccessTime; 
            this.returnToStdWhenVisit = from.returnToStdWhenVisit; 
            this.allowSmallFile = from.allowSmallFile; 
            this.noncurrentDays = from.noncurrentDays; 
        }

        public NoncurrentVersionTransition build() {
            return new NoncurrentVersionTransition(this);
        }
    }
}
