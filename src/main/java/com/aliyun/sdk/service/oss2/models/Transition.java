package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.time.Instant;
import java.time.ZoneOffset;
import static com.aliyun.sdk.service.oss2.utils.DateUtils.ISO8601_DATE_FORMAT;
import static java.util.Objects.requireNonNull;

/**
 * The conversion of the storage class of objects that match the lifecycle rule when the objects expire. The storage class of the objects can be converted to IA, Archive, and ColdArchive. The storage class of Standard objects in a Standard bucket can be converted to IA, Archive, or Cold Archive. The period of time from when the objects expire to when the storage class of the objects is converted to Archive must be longer than the period of time from when the objects expire to when the storage class of the objects is converted to IA. For example, if the validity period is set to 30 for objects whose storage class is converted to IA after the validity period, the validity period must be set to a value greater than 30 for objects whose storage class is converted to Archive.  Either Days or CreatedBeforeDate is required.
 */
 @JacksonXmlRootElement(localName = "Transition")
public final class Transition {  
    @JacksonXmlProperty(localName = "AllowSmallFile")
    private Boolean allowSmallFile;
 
    @JacksonXmlProperty(localName = "CreatedBeforeDate")
    private String createdBeforeDate;
 
    @JacksonXmlProperty(localName = "Days")
    private Integer days;
 
    @JacksonXmlProperty(localName = "StorageClass")
    private String storageClass;
 
    @JacksonXmlProperty(localName = "IsAccessTime")
    private Boolean isAccessTime;
 
    @JacksonXmlProperty(localName = "ReturnToStdWhenVisit")
    private Boolean returnToStdWhenVisit;

    public Transition() {}

    private Transition(Builder builder) { 
        this.allowSmallFile = builder.allowSmallFile; 
        this.createdBeforeDate = builder.createdBeforeDate; 
        this.days = builder.days; 
        this.storageClass = builder.storageClass; 
        this.isAccessTime = builder.isAccessTime; 
        this.returnToStdWhenVisit = builder.returnToStdWhenVisit; 
    }

    /**
    * Specifies whether to convert the storage class of objects whose sizes are less than 64 KB to IA, Archive, or Cold Archive based on their last access time. Valid values:*   true: converts the storage class of objects that are smaller than 64 KB to IA, Archive, or Cold Archive. Objects that are smaller than 64 KB are charged as 64 KB. Objects that are greater than or equal to 64 KB are charged based on their actual sizes. If you set this parameter to true, the storage fees may increase.*   false: does not convert the storage class of an object that is smaller than 64 KB.
    */
    public Boolean allowSmallFile() {
        return this.allowSmallFile;
    }

    /**
    * The date based on which the lifecycle rule takes effect. OSS performs the specified operation on data whose last modified date is earlier than this date. Specify the time in the ISO 8601 standard. The time must be at 00:00:00 in UTC.
    */
    public String createdBeforeDate() {
        return this.createdBeforeDate;
    }

    /**
    * The number of days from when the objects were last modified to when the lifecycle rule takes effect.
    */
    public Integer days() {
        return this.days;
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
    @JsonIgnore
    public Boolean isAccessTime() {
        return this.isAccessTime;
    }

    /**
    * Specifies whether to convert the storage class of non-Standard objects back to Standard after the objects are accessed. This parameter takes effect only when the IsAccessTime parameter is set to true. Valid values:*   true: converts the storage class of the objects to Standard.*   false: does not convert the storage class of the objects to Standard.
    */
    public Boolean returnToStdWhenVisit() {
        return this.returnToStdWhenVisit;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Boolean allowSmallFile;
        private String createdBeforeDate;
        private Integer days;
        private String storageClass;
        private Boolean isAccessTime;
        private Boolean returnToStdWhenVisit;
        
        /**
        * Specifies whether to convert the storage class of objects whose sizes are less than 64 KB to IA, Archive, or Cold Archive based on their last access time. Valid values:*   true: converts the storage class of objects that are smaller than 64 KB to IA, Archive, or Cold Archive. Objects that are smaller than 64 KB are charged as 64 KB. Objects that are greater than or equal to 64 KB are charged based on their actual sizes. If you set this parameter to true, the storage fees may increase.*   false: does not convert the storage class of an object that is smaller than 64 KB.
        */
        public Builder allowSmallFile(Boolean value) {
            requireNonNull(value);
            this.allowSmallFile = value;
            return this;
        }
        
        /**
        * The date based on which the lifecycle rule takes effect. OSS performs the specified operation on data whose last modified date is earlier than this date. Specify the time in the ISO 8601 standard. The time must be at 00:00:00 in UTC.
        */
        public Builder createdBeforeDate(String value) {
            requireNonNull(value);
            this.createdBeforeDate = value;
            return this;
        }
        
        /**
        * The date based on which the lifecycle rule takes effect. OSS performs the specified operation on data whose last modified date is earlier than this date. Specify the time in the ISO 8601 standard. The time must be at 00:00:00 in UTC.
        */
        public Builder createdBeforeDate(Instant value) {
            requireNonNull(value);
            this.createdBeforeDate = value.atOffset(ZoneOffset.UTC)
                                          .format(ISO8601_DATE_FORMAT);
            return this;
        }
        
        /**
        * The number of days from when the objects were last modified to when the lifecycle rule takes effect.
        */
        public Builder days(Integer value) {
            requireNonNull(value);
            this.days = value;
            return this;
        }
        
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
        

        private Builder() {
            super();
        }

        private Builder(Transition from) { 
            this.allowSmallFile = from.allowSmallFile; 
            this.createdBeforeDate = from.createdBeforeDate; 
            this.days = from.days; 
            this.storageClass = from.storageClass; 
            this.isAccessTime = from.isAccessTime; 
            this.returnToStdWhenVisit = from.returnToStdWhenVisit; 
        }

        public Transition build() {
            return new Transition(this);
        }
    }
}