package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * Container for Object information.
 */
 @JacksonXmlRootElement(localName = "Files")
public final class MetaQueryFiles {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "File")
    private List<MetaQueryFile> file;

    public MetaQueryFiles() {}

    private MetaQueryFiles(Builder builder) { 
        this.file = builder.file; 
    }

    /**
    * Container for Object information.
    */
    public List<MetaQueryFile> file() {
        return this.file;
    }

    /**
    * Alias for file() method.
    */
    public List<MetaQueryFile> fileList() {
        return this.file;
    }


    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<MetaQueryFile> file;
        
        /**
        * Container for individual Object information.
        * Contains a list of files that meet the query criteria.
        */
        public Builder file(List<MetaQueryFile> value) {
            requireNonNull(value);
            this.file = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQueryFiles from) { 
            this.file = from.file; 
        }

        public MetaQueryFiles build() {
            return new MetaQueryFiles(this);
        }
    }
}