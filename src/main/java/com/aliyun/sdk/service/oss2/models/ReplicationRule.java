package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The data replication rule configuration.
 */
 @JacksonXmlRootElement(localName = "ReplicationRule")
public final class ReplicationRule {  
    @JacksonXmlProperty(localName = "RTC")
    private RTC rtc;
 
    @JacksonXmlProperty(localName = "Action")
    private String action;
 
    @JacksonXmlProperty(localName = "Destination")
    private ReplicationDestination destination;
 
    @JacksonXmlProperty(localName = "SyncRole")
    private String syncRole;
 
    @JacksonXmlProperty(localName = "EncryptionConfiguration")
    private ReplicationEncryptionConfiguration encryptionConfiguration;
 
    @JacksonXmlProperty(localName = "SourceSelectionCriteria")
    private ReplicationSourceSelectionCriteria sourceSelectionCriteria;
 
    @JacksonXmlProperty(localName = "ID")
    private String id;
 
    @JacksonXmlProperty(localName = "PrefixSet")
    private ReplicationPrefixSet prefixSet;
 
    @JacksonXmlProperty(localName = "Status")
    private String status;
 
    @JacksonXmlProperty(localName = "HistoricalObjectReplication")
    private String historicalObjectReplication;

    public ReplicationRule() {}

    private ReplicationRule(Builder builder) { 
        this.rtc = builder.rtc;
        this.action = builder.action; 
        this.destination = builder.destination; 
        this.syncRole = builder.syncRole; 
        this.encryptionConfiguration = builder.encryptionConfiguration; 
        this.sourceSelectionCriteria = builder.sourceSelectionCriteria; 
        this.id = builder.id;
        this.prefixSet = builder.prefixSet; 
        this.status = builder.status; 
        this.historicalObjectReplication = builder.historicalObjectReplication; 
    }

    /**
    * The container that stores the status of the RTC feature.
    */
    public RTC rtc() {
        return this.rtc;
    }

    /**
    * The operations that can be synchronized to the destination bucket. If you configure Action in a data replication rule, OSS synchronizes new data and historical data based on the specified value of Action. You can set Action to one or more of the following operation types. Valid values:*   ALL (default): PUT, DELETE, and ABORT operations are synchronized to the destination bucket.*   PUT: Write operations are synchronized to the destination bucket, including PutObject, PostObject, AppendObject, CopyObject, PutObjectACL, InitiateMultipartUpload, UploadPart, UploadPartCopy, and CompleteMultipartUpload.
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
    * The role that you want to authorize OSS to use to replicate data. If you want to use SSE-KMS to encrypt the objects that are replicated to the destination bucket, you must specify this parameter.
    */
    public String syncRole() {
        return this.syncRole;
    }

    /**
    * The encryption configuration for the objects replicated to the destination bucket. If the Status parameter is set to Enabled, you must specify this parameter.
    */
    public ReplicationEncryptionConfiguration encryptionConfiguration() {
        return this.encryptionConfiguration;
    }

    /**
    * The container that specifies other conditions used to filter the source objects that you want to replicate. Filter conditions can be specified only for source objects encrypted by using SSE-KMS.
    */
    public ReplicationSourceSelectionCriteria sourceSelectionCriteria() {
        return this.sourceSelectionCriteria;
    }

    /**
    * The ID of the rule.
    */
    public String id() {
        return this.id;
    }

    /**
    * The container that stores prefixes. You can specify up to 10 prefixes in each data replication rule.
    */
    public ReplicationPrefixSet prefixSet() {
        return this.prefixSet;
    }

    /**
    * The status of the data replication task. Valid values:*   starting: OSS creates a data replication task after a data replication rule is configured.*   doing: The replication rule is effective and the replication task is in progress.*   closing: OSS clears a data replication task after the corresponding data replication rule is deleted.
    */
    public String status() {
        return this.status;
    }

    /**
    * Specifies whether to replicate historical data that exists before data replication is enabled from the source bucket to the destination bucket. Valid values:*   enabled (default): replicates historical data to the destination bucket.*   disabled: does not replicate historical data to the destination bucket. Only data uploaded to the source bucket after data replication is enabled for the source bucket is replicated.
    */
    public String historicalObjectReplication() {
        return this.historicalObjectReplication;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private RTC rtc;
        private String action;
        private ReplicationDestination destination;
        private String syncRole;
        private ReplicationEncryptionConfiguration encryptionConfiguration;
        private ReplicationSourceSelectionCriteria sourceSelectionCriteria;
        private String id;
        private ReplicationPrefixSet prefixSet;
        private String status;
        private String historicalObjectReplication;
        
        /**
        * The container that stores the status of the RTC feature.
        */
        public Builder rtc(RTC value) {
            requireNonNull(value);
            this.rtc = value;
            return this;
        }
        
        /**
        * The operations that can be synchronized to the destination bucket. If you configure Action in a data replication rule, OSS synchronizes new data and historical data based on the specified value of Action. You can set Action to one or more of the following operation types. Valid values:*   ALL (default): PUT, DELETE, and ABORT operations are synchronized to the destination bucket.*   PUT: Write operations are synchronized to the destination bucket, including PutObject, PostObject, AppendObject, CopyObject, PutObjectACL, InitiateMultipartUpload, UploadPart, UploadPartCopy, and CompleteMultipartUpload.
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
        * The role that you want to authorize OSS to use to replicate data. If you want to use SSE-KMS to encrypt the objects that are replicated to the destination bucket, you must specify this parameter.
        */
        public Builder syncRole(String value) {
            requireNonNull(value);
            this.syncRole = value;
            return this;
        }
        
        /**
        * The encryption configuration for the objects replicated to the destination bucket. If the Status parameter is set to Enabled, you must specify this parameter.
        */
        public Builder encryptionConfiguration(ReplicationEncryptionConfiguration value) {
            requireNonNull(value);
            this.encryptionConfiguration = value;
            return this;
        }
        
        /**
        * The container that specifies other conditions used to filter the source objects that you want to replicate. Filter conditions can be specified only for source objects encrypted by using SSE-KMS.
        */
        public Builder sourceSelectionCriteria(ReplicationSourceSelectionCriteria value) {
            requireNonNull(value);
            this.sourceSelectionCriteria = value;
            return this;
        }
        
        /**
        * The ID of the rule.
        */
        public Builder id(String value) {
            requireNonNull(value);
            this.id = value;
            return this;
        }
        
        /**
        * The container that stores prefixes. You can specify up to 10 prefixes in each data replication rule.
        */
        public Builder prefixSet(ReplicationPrefixSet value) {
            requireNonNull(value);
            this.prefixSet = value;
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
        * Specifies whether to replicate historical data that exists before data replication is enabled from the source bucket to the destination bucket. Valid values:*   enabled (default): replicates historical data to the destination bucket.*   disabled: does not replicate historical data to the destination bucket. Only data uploaded to the source bucket after data replication is enabled for the source bucket is replicated.
        */
        public Builder historicalObjectReplication(String value) {
            requireNonNull(value);
            this.historicalObjectReplication = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ReplicationRule from) { 
            this.rtc = from.rtc;
            this.action = from.action; 
            this.destination = from.destination; 
            this.syncRole = from.syncRole; 
            this.encryptionConfiguration = from.encryptionConfiguration; 
            this.sourceSelectionCriteria = from.sourceSelectionCriteria; 
            this.id = from.id;
            this.prefixSet = from.prefixSet; 
            this.status = from.status; 
            this.historicalObjectReplication = from.historicalObjectReplication; 
        }

        public ReplicationRule build() {
            return new ReplicationRule(this);
        }
    }
}
