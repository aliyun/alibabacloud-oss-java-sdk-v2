package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.time.Instant;
import java.time.ZoneOffset;
import static com.aliyun.sdk.service.oss2.utils.DateUtils.ISO8601_DATE_FORMAT;
import static java.util.Objects.requireNonNull;

/**
 * The delete operation to perform on objects based on the lifecycle rule. For an object in a versioning-enabled bucket, the delete operation specified by this parameter is performed only on the current version of the object.The period of time from when the objects expire to when the objects are deleted must be longer than the period of time from when the objects expire to when the storage class of the objects is converted to IA or Archive.
 */
 @JacksonXmlRootElement(localName = "Expiration")
public final class LifecycleRuleExpiration {
    @JacksonXmlProperty(localName = "CreatedBeforeDate")
    private String createdBeforeDate;
 
    @JacksonXmlProperty(localName = "Days")
    private Integer days;
 
    @JacksonXmlProperty(localName = "ExpiredObjectDeleteMarker")
    private Boolean expiredObjectDeleteMarker;
 
    @JacksonXmlProperty(localName = "Date")
    private String date;

    public LifecycleRuleExpiration() {}

    private LifecycleRuleExpiration(Builder builder) {
        this.createdBeforeDate = builder.createdBeforeDate; 
        this.days = builder.days; 
        this.expiredObjectDeleteMarker = builder.expiredObjectDeleteMarker; 
        this.date = builder.date; 
    }

    /**
    * The date based on which the lifecycle rule takes effect. OSS performs the specified operation on data whose last modified date is earlier than this date. The value of this parameter is in the yyyy-MM-ddT00:00:00.000Z format.Specify the time in the ISO 8601 standard. The time must be at 00:00:00 in UTC.
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
    * Specifies whether to automatically remove expired delete markers.*   true: Expired delete markers are automatically removed. If you set this parameter to true, you cannot specify the Days or CreatedBeforeDate parameter.*   false: Expired delete markers are not automatically removed. If you set this parameter to false, you must specify the Days or CreatedBeforeDate parameter.
    */
    public Boolean expiredObjectDeleteMarker() {
        return this.expiredObjectDeleteMarker;
    }

    /**
    * The date after which the lifecycle rule takes effect. If the specified time is earlier than the current moment, it'll takes effect immediately. (This fields is NOT RECOMMENDED, please use Days or CreateDateBefore)
    */
    public String date() {
        return this.date;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String createdBeforeDate;
        private Integer days;
        private Boolean expiredObjectDeleteMarker;
        private String date;
        
        /**
        * The date based on which the lifecycle rule takes effect. OSS performs the specified operation on data whose last modified date is earlier than this date. The value of this parameter is in the yyyy-MM-ddT00:00:00.000Z format.Specify the time in the ISO 8601 standard. The time must be at 00:00:00 in UTC.
        */
        public Builder createdBeforeDate(String value) {
            requireNonNull(value);
            this.createdBeforeDate = value;
            return this;
        }
        
        /**
        * The date based on which the lifecycle rule takes effect. OSS performs the specified operation on data whose last modified date is earlier than this date. The value of this parameter is in the yyyy-MM-ddT00:00:00.000Z format.Specify the time in the ISO 8601 standard. The time must be at 00:00:00 in UTC.
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
        * Alternative way to set days using integer value
        */
        public Builder days(int value) {
            this.days = value;
            return this;
        }
        
        /**
        * Specifies whether to automatically remove expired delete markers.*   true: Expired delete markers are automatically removed. If you set this parameter to true, you cannot specify the Days or CreatedBeforeDate parameter.*   false: Expired delete markers are not automatically removed. If you set this parameter to false, you must specify the Days or CreatedBeforeDate parameter.
        */
        public Builder expiredObjectDeleteMarker(Boolean value) {
            requireNonNull(value);
            this.expiredObjectDeleteMarker = value;
            return this;
        }
        
        /**
        * The date after which the lifecycle rule takes effect. If the specified time is earlier than the current moment, it'll takes effect immediately. (This fields is NOT RECOMMENDED, please use Days or CreateDateBefore)
        */
        public Builder date(String value) {
            requireNonNull(value);
            this.date = value;
            return this;
        }
        
        /**
        * The date after which the lifecycle rule takes effect. If the specified time is earlier than the current moment, it'll takes effect immediately. (This fields is NOT RECOMMENDED, please use Days or CreateDateBefore)
        */
        public Builder date(Instant value) {
            requireNonNull(value);
            this.date = value.atOffset(ZoneOffset.UTC)
                             .format(ISO8601_DATE_FORMAT);
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(LifecycleRuleExpiration from) {
            this.createdBeforeDate = from.createdBeforeDate; 
            this.days = from.days; 
            this.expiredObjectDeleteMarker = from.expiredObjectDeleteMarker; 
            this.date = from.date; 
        }

        public LifecycleRuleExpiration build() {
            return new LifecycleRuleExpiration(this);
        }
    }
}