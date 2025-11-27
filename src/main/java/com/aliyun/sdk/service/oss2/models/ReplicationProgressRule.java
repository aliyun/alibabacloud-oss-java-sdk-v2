package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * Information about the progress of the data replication task.
 */
 @JacksonXmlRootElement(localName = "ReplicationProgressRule")
public final class ReplicationProgressRule {  
    @JacksonXmlProperty(localName = "PrefixSet")
    private ReplicationPrefixSet prefixSet;
 
    @JacksonXmlProperty(localName = "Action")
    private String action;
 
    @JacksonXmlProperty(localName = "Destination")
    private ReplicationDestination destination;
 
    @JacksonXmlProperty(localName = "Status")
    private String status;
 
    @JacksonXmlProperty(localName = "HistoricalObjectReplication")
    private String historicalObjectReplication;
 
    @JacksonXmlProperty(localName = "Progress")
    private Progress progress;
 
    @JacksonXmlProperty(localName = "ID")
    private String id;

    public ReplicationProgressRule() {}

    private ReplicationProgressRule(Builder builder) { 
        this.prefixSet = builder.prefixSet; 
        this.action = builder.action; 
        this.destination = builder.destination; 
        this.status = builder.status; 
        this.historicalObjectReplication = builder.historicalObjectReplication; 
        this.progress = builder.progress; 
        this.id = builder.id;
    }

    /**
    * The container that stores prefixes. You can specify up to 10 prefixes in each data replication rule.
    */
    public ReplicationPrefixSet prefixSet() {
        return this.prefixSet;
    }

    /**
    * The operations that are synchronized to the destination bucket.*   ALL: PUT, DELETE, and ABORT operations are synchronized to the destination bucket.*   PUT: Write operations are synchronized to the destination bucket, including PutObject, PostObject, AppendObject, CopyObject, PutObjectACL, InitiateMultipartUpload, UploadPart, UploadPartCopy, and CompleteMultipartUpload.
    */
    public String action() {
        return this.action;
    }

    /**
    * The container that stores the information about the destination bucket.
    */
    public ReplicationDestination destination() {
        return this.destination;
    }

    /**
    * The status of the data replication task. Valid values:*   starting: OSS creates a data replication task after a data replication rule is configured.*   doing: The replication rule is effective and the replication task is in progress.*   closing: OSS clears a data replication task after the corresponding data replication rule is deleted.
    */
    public String status() {
        return this.status;
    }

    /**
    * Specifies whether to replicate historical data that exists before data replication is enabled from the source bucket to the destination bucket.*   enabled (default): replicates historical data to the destination bucket.*   disabled: ignores historical data and replicates only data uploaded to the source bucket after data replication is enabled for the source bucket.
    */
    public String historicalObjectReplication() {
        return this.historicalObjectReplication;
    }

    /**
    * The container that stores the progress of the data replication task. This parameter is returned only when the data replication task is in the doing state.
    */
    public Progress progress() {
        return this.progress;
    }

    /**
    * The ID of the data replication rule.
    */
    public String id() {
        return this.id;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private ReplicationPrefixSet prefixSet;
        private String action;
        private ReplicationDestination destination;
        private String status;
        private String historicalObjectReplication;
        private Progress progress;
        private String id;
        
        /**
        * The container that stores prefixes. You can specify up to 10 prefixes in each data replication rule.
        */
        public Builder prefixSet(ReplicationPrefixSet value) {
            requireNonNull(value);
            this.prefixSet = value;
            return this;
        }
        
        /**
        * The operations that are synchronized to the destination bucket.*   ALL: PUT, DELETE, and ABORT operations are synchronized to the destination bucket.*   PUT: Write operations are synchronized to the destination bucket, including PutObject, PostObject, AppendObject, CopyObject, PutObjectACL, InitiateMultipartUpload, UploadPart, UploadPartCopy, and CompleteMultipartUpload.
        */
        public Builder action(String value) {
            requireNonNull(value);
            this.action = value;
            return this;
        }
        
        /**
        * The container that stores the information about the destination bucket.
        */
        public Builder destination(ReplicationDestination value) {
            requireNonNull(value);
            this.destination = value;
            return this;
        }
        
        /**
        * The status of the data replication task. Valid values:*   starting: OSS creates a data replication task after a data replication rule is configured.*   doing: The replication rule is effective and the replication task is in progress.*   closing: OSS clears a data replication task after the corresponding data replication rule is deleted.
        */
        public Builder status(String value) {
            requireNonNull(value);
            this.status = value;
            return this;
        }
        
        /**
        * Specifies whether to replicate historical data that exists before data replication is enabled from the source bucket to the destination bucket.*   enabled (default): replicates historical data to the destination bucket.*   disabled: ignores historical data and replicates only data uploaded to the source bucket after data replication is enabled for the source bucket.
        */
        public Builder historicalObjectReplication(String value) {
            requireNonNull(value);
            this.historicalObjectReplication = value;
            return this;
        }
        
        /**
        * The container that stores the progress of the data replication task. This parameter is returned only when the data replication task is in the doing state.
        */
        public Builder progress(Progress value) {
            requireNonNull(value);
            this.progress = value;
            return this;
        }
        
        /**
        * The ID of the data replication rule.
        */
        public Builder id(String value) {
            requireNonNull(value);
            this.id = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ReplicationProgressRule from) { 
            this.prefixSet = from.prefixSet; 
            this.action = from.action; 
            this.destination = from.destination; 
            this.status = from.status; 
            this.historicalObjectReplication = from.historicalObjectReplication; 
            this.progress = from.progress; 
            this.id = from.id;
        }

        public ReplicationProgressRule build() {
            return new ReplicationProgressRule(this);
        }
    }
}
