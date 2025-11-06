package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatterBuilder;
import static java.util.Objects.requireNonNull;

/**
 * The container in which the redundancy type conversion task is stored.
 */
 @JacksonXmlRootElement(localName = "BucketDataRedundancyTransition")
public final class BucketDataRedundancyTransition {  
    @JacksonXmlProperty(localName = "Bucket")
    private String bucket;
    
    @JacksonXmlProperty(localName = "TaskId")
    private String taskId;
    
    @JacksonXmlProperty(localName = "Status")
    private String status;
    
    @JacksonXmlProperty(localName = "CreateTime")
    private String createTime;
    
    @JacksonXmlProperty(localName = "StartTime")
    private String startTime;
    
    @JacksonXmlProperty(localName = "ProcessPercentage")
    private Integer processPercentage;
    
    @JacksonXmlProperty(localName = "EstimatedRemainingTime")
    private Long estimatedRemainingTime;
    
    @JacksonXmlProperty(localName = "EndTime")
    private String endTime;

    public BucketDataRedundancyTransition() {}

    private BucketDataRedundancyTransition(Builder builder) { 
        this.bucket = builder.bucket;
        this.taskId = builder.taskId; 
        this.status = builder.status;
        this.createTime = builder.createTime;
        this.startTime = builder.startTime;
        this.processPercentage = builder.processPercentage;
        this.estimatedRemainingTime = builder.estimatedRemainingTime;
        this.endTime = builder.endTime;
    }

    /**
    * The name of the bucket.
    */
    public String bucket() {
        return this.bucket;
    }
    
    /**
    * The ID of the redundancy type conversion task. The ID can be used to view and delete the redundancy type conversion task.
    */
    public String taskId() {
        return this.taskId;
    }
    
    /**
    * The status of the redundancy type conversion task.
    */
    public String status() {
        return this.status;
    }
    
    /**
    * The time when the redundancy type conversion task was created.
    */
    public String createTime() {
        return this.createTime;
    }
    
    /**
    * The time when the redundancy type conversion task was started.
    */
    public String startTime() {
        return this.startTime;
    }
    
    /**
    * The progress of the redundancy type conversion task.
    */
    public Integer processPercentage() {
        return this.processPercentage;
    }
    
    /**
    * The estimated remaining time of the redundancy type conversion task.
    */
    public Long estimatedRemainingTime() {
        return this.estimatedRemainingTime;
    }
    
    /**
    * The time when the redundancy type conversion task was completed.
    */
    public String endTime() {
        return this.endTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String bucket;
        private String taskId;
        private String status;
        private String createTime;
        private String startTime;
        private Integer processPercentage;
        private Long estimatedRemainingTime;
        private String endTime;
        
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }
        
        /**
        * The ID of the redundancy type conversion task. The ID can be used to view and delete the redundancy type conversion task.
        */
        public Builder taskId(String value) {
            requireNonNull(value);
            this.taskId = value;
            return this;
        }
        
        /**
        * The status of the redundancy type conversion task.
        */
        public Builder status(String value) {
            requireNonNull(value);
            this.status = value;
            return this;
        }
        
        /**
        * The time when the redundancy type conversion task was created.
        */
        public Builder createTime(String value) {
            requireNonNull(value);
            this.createTime = value;
            return this;
        }
        
        /**
        * The time when the redundancy type conversion task was created.
        */
        public Builder createTime(Instant value) {
            requireNonNull(value);
            this.createTime = value.atOffset(ZoneOffset.UTC)
                                  .format(new DateTimeFormatterBuilder()
                                          .appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                          .toFormatter());
            return this;
        }
        
        /**
        * The time when the redundancy type conversion task was started.
        */
        public Builder startTime(String value) {
            requireNonNull(value);
            this.startTime = value;
            return this;
        }
        
        /**
        * The time when the redundancy type conversion task was started.
        */
        public Builder startTime(Instant value) {
            requireNonNull(value);
            this.startTime = value.atOffset(ZoneOffset.UTC)
                                 .format(new DateTimeFormatterBuilder()
                                         .appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                         .toFormatter());
            return this;
        }
        
        /**
        * The progress of the redundancy type conversion task.
        */
        public Builder processPercentage(Integer value) {
            requireNonNull(value);
            this.processPercentage = value;
            return this;
        }
        
        /**
        * The estimated remaining time of the redundancy type conversion task.
        */
        public Builder estimatedRemainingTime(Long value) {
            requireNonNull(value);
            this.estimatedRemainingTime = value;
            return this;
        }
        
        /**
        * The time when the redundancy type conversion task was completed.
        */
        public Builder endTime(String value) {
            requireNonNull(value);
            this.endTime = value;
            return this;
        }
        
        /**
        * The time when the redundancy type conversion task was completed.
        */
        public Builder endTime(Instant value) {
            requireNonNull(value);
            this.endTime = value.atOffset(ZoneOffset.UTC)
                               .format(new DateTimeFormatterBuilder()
                                       .appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                       .toFormatter());
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(BucketDataRedundancyTransition from) { 
            this.bucket = from.bucket;
            this.taskId = from.taskId;
            this.status = from.status;
            this.createTime = from.createTime;
            this.startTime = from.startTime;
            this.processPercentage = from.processPercentage;
            this.estimatedRemainingTime = from.estimatedRemainingTime;
            this.endTime = from.endTime;
        }

        public BucketDataRedundancyTransition build() {
            return new BucketDataRedundancyTransition(this);
        }
    }
}
