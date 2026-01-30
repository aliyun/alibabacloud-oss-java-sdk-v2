package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the metadata information.
 */
 @JacksonXmlRootElement(localName = "MetaQueryStatus")
public final class MetaQueryStatus {  
    @JacksonXmlProperty(localName = "State")
    private String state;
 
    @JacksonXmlProperty(localName = "Phase")
    private String phase;
 
    @JacksonXmlProperty(localName = "CreateTime")
    private String createTime;
 
    @JacksonXmlProperty(localName = "UpdateTime")
    private String updateTime;

    public MetaQueryStatus() {}

    private MetaQueryStatus(Builder builder) { 
        this.state = builder.state; 
        this.phase = builder.phase; 
        this.createTime = builder.createTime; 
        this.updateTime = builder.updateTime; 
    }

    /**
    * The status of the metadata index library. Valid values:- Ready: The metadata index library is being prepared after it is created.In this case, the metadata index library cannot be used to query data.- Stop: The metadata index library is paused.- Running: The metadata index library is running.- Retrying: The metadata index library failed to be created and is being created again.- Failed: The metadata index library failed to be created.- Deleted: The metadata index library is deleted.
    */
    public String state() {
        return this.state;
    }

    /**
    * The scan type. Valid values:- FullScanning: Full scanning is in progress.- IncrementalScanning: Incremental scanning is in progress.
    */
    public String phase() {
        return this.phase;
    }

    /**
    * The time when the metadata index library was created. The value follows the RFC 3339 standard in the YYYY-MM-DDTHH:mm:ss+TIMEZONE format. YYYY-MM-DD indicates the year, month, and day. T indicates the beginning of the time element. HH:mm:ss indicates the hour, minute, and second. TIMEZONE indicates the time zone.
    */
    public String createTime() {
        return this.createTime;
    }

    /**
    * The time when the metadata index library was updated. The value follows the RFC 3339 standard in the YYYY-MM-DDTHH:mm:ss+TIMEZONE format. YYYY-MM-DD indicates the year, month, and day. T indicates the beginning of the time element. HH:mm:ss indicates the hour, minute, and second. TIMEZONE indicates the time zone.
    */
    public String updateTime() {
        return this.updateTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String state;
        private String phase;
        private String createTime;
        private String updateTime;
        
        /**
        * The status of the metadata index library. Valid values:- Ready: The metadata index library is being prepared after it is created.In this case, the metadata index library cannot be used to query data.- Stop: The metadata index library is paused.- Running: The metadata index library is running.- Retrying: The metadata index library failed to be created and is being created again.- Failed: The metadata index library failed to be created.- Deleted: The metadata index library is deleted.
        */
        public Builder state(String value) {
            requireNonNull(value);
            this.state = value;
            return this;
        }
        
        /**
        * The scan type. Valid values:- FullScanning: Full scanning is in progress.- IncrementalScanning: Incremental scanning is in progress.
        */
        public Builder phase(String value) {
            requireNonNull(value);
            this.phase = value;
            return this;
        }
        
        /**
        * The time when the metadata index library was created. The value follows the RFC 3339 standard in the YYYY-MM-DDTHH:mm:ss+TIMEZONE format. YYYY-MM-DD indicates the year, month, and day. T indicates the beginning of the time element. HH:mm:ss indicates the hour, minute, and second. TIMEZONE indicates the time zone.
        */
        public Builder createTime(String value) {
            requireNonNull(value);
            this.createTime = value;
            return this;
        }
        
        /**
        * The time when the metadata index library was updated. The value follows the RFC 3339 standard in the YYYY-MM-DDTHH:mm:ss+TIMEZONE format. YYYY-MM-DD indicates the year, month, and day. T indicates the beginning of the time element. HH:mm:ss indicates the hour, minute, and second. TIMEZONE indicates the time zone.
        */
        public Builder updateTime(String value) {
            requireNonNull(value);
            this.updateTime = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQueryStatus from) { 
            this.state = from.state; 
            this.phase = from.phase; 
            this.createTime = from.createTime; 
            this.updateTime = from.updateTime; 
        }

        public MetaQueryStatus build() {
            return new MetaQueryStatus(this);
        }
    }
}
