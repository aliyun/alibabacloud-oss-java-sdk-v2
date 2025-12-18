package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the default homepage.
 */
 @JacksonXmlRootElement(localName = "IndexDocument")
public final class IndexDocument {  
    @JacksonXmlProperty(localName = "Suffix")
    private String suffix;
 
    @JacksonXmlProperty(localName = "SupportSubDir")
    private Boolean supportSubDir;
 
    @JacksonXmlProperty(localName = "Type")
    private Long type;

    public IndexDocument() {}

    private IndexDocument(Builder builder) { 
        this.suffix = builder.suffix; 
        this.supportSubDir = builder.supportSubDir; 
        this.type = builder.type; 
    }

    /**
    * The default homepage.
    */
    public String suffix() {
        return this.suffix;
    }

    /**
    * Specifies whether to redirect the access to the default homepage of the subdirectory when the subdirectory is accessed. Valid values:*   **true**: The access is redirected to the default homepage of the subdirectory.*   **false** (default): The access is redirected to the default homepage of the root directory.For example, the default homepage is set to index.html, and `bucket.oss-cn-hangzhou.aliyuncs.com/subdir/` is the site that you want to access. If **SupportSubDir** is set to false, the access is redirected to `bucket.oss-cn-hangzhou.aliyuncs.com/index.html`. If **SupportSubDir** is set to true, the access is redirected to `bucket.oss-cn-hangzhou.aliyuncs.com/subdir/index.html`.
    */
    public Boolean supportSubDir() {
        return this.supportSubDir;
    }

    /**
    * The operation to perform when the default homepage is set, the name of the accessed object does not end with a forward slash (/), and the object does not exist. This parameter takes effect only when **SupportSubDir** is set to true. It takes effect after RoutingRule but before ErrorFile. For example, the default homepage is set to index.html, `bucket.oss-cn-hangzhou.aliyuncs.com/abc` is the site that you want to access, and the abc object does not exist. In this case, different operations are performed based on the value of **Type**.*   **0** (default): OSS checks whether the object named abc/index.html, which is in the `Object + Forward slash (/) + Homepage` format, exists. If the object exists, OSS returns HTTP status code 302 and the Location header value that contains URL-encoded `/abc/`. The URL-encoded /abc/ is in the `Forward slash (/) + Object + Forward slash (/)` format. If the object does not exist, OSS returns HTTP status code 404 and continues to check ErrorFile.*   **1**: OSS returns HTTP status code 404 and the NoSuchKey error code and continues to check ErrorFile.*   **2**: OSS checks whether abc/index.html exists. If abc/index.html exists, the content of the object is returned. If abc/index.html does not exist, OSS returns HTTP status code 404 and continues to check ErrorFile.
    */
    public Long type() {
        return this.type;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String suffix;
        private Boolean supportSubDir;
        private Long type;
        
        /**
        * The default homepage.
        */
        public Builder suffix(String value) {
            requireNonNull(value);
            this.suffix = value;
            return this;
        }
        
        /**
        * Specifies whether to redirect the access to the default homepage of the subdirectory when the subdirectory is accessed. Valid values:*   **true**: The access is redirected to the default homepage of the subdirectory.*   **false** (default): The access is redirected to the default homepage of the root directory.For example, the default homepage is set to index.html, and `bucket.oss-cn-hangzhou.aliyuncs.com/subdir/` is the site that you want to access. If **SupportSubDir** is set to false, the access is redirected to `bucket.oss-cn-hangzhou.aliyuncs.com/index.html`. If **SupportSubDir** is set to true, the access is redirected to `bucket.oss-cn-hangzhou.aliyuncs.com/subdir/index.html`.
        */
        public Builder supportSubDir(Boolean value) {
            requireNonNull(value);
            this.supportSubDir = value;
            return this;
        }
        
        /**
        * The operation to perform when the default homepage is set, the name of the accessed object does not end with a forward slash (/), and the object does not exist. This parameter takes effect only when **SupportSubDir** is set to true. It takes effect after RoutingRule but before ErrorFile. For example, the default homepage is set to index.html, `bucket.oss-cn-hangzhou.aliyuncs.com/abc` is the site that you want to access, and the abc object does not exist. In this case, different operations are performed based on the value of **Type**.*   **0** (default): OSS checks whether the object named abc/index.html, which is in the `Object + Forward slash (/) + Homepage` format, exists. If the object exists, OSS returns HTTP status code 302 and the Location header value that contains URL-encoded `/abc/`. The URL-encoded /abc/ is in the `Forward slash (/) + Object + Forward slash (/)` format. If the object does not exist, OSS returns HTTP status code 404 and continues to check ErrorFile.*   **1**: OSS returns HTTP status code 404 and the NoSuchKey error code and continues to check ErrorFile.*   **2**: OSS checks whether abc/index.html exists. If abc/index.html exists, the content of the object is returned. If abc/index.html does not exist, OSS returns HTTP status code 404 and continues to check ErrorFile.
        */
        public Builder type(Long value) {
            requireNonNull(value);
            this.type = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(IndexDocument from) { 
            this.suffix = from.suffix; 
            this.supportSubDir = from.supportSubDir; 
            this.type = from.type; 
        }

        public IndexDocument build() {
            return new IndexDocument(this);
        }
    }
}
