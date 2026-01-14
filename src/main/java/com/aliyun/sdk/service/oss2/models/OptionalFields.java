package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the configuration fields in inventory lists.
 */
 @JacksonXmlRootElement(localName = "OptionalFields")
public final class OptionalFields {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Field")
    private List<InventoryOptionalFieldType> fields;

    public OptionalFields() {}

    private OptionalFields(Builder builder) { 
        this.fields = builder.fields; 
    }

    /**
    * The configuration fields that are included in inventory lists. Available configuration fields:*   Size: the size of the object.*   LastModifiedDate: the time when the object was last modified.*   ETag: the ETag of the object. It is used to identify the content of the object.*   StorageClass: the storage class of the object.*   IsMultipartUploaded: specifies whether the object is uploaded by using multipart upload.*   EncryptionStatus: the encryption status of the object.
    */
    public List<InventoryOptionalFieldType> fields() {
        return this.fields;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<InventoryOptionalFieldType> fields;
        
        /**
        * The configuration fields that are included in inventory lists. Available configuration fields:*   Size: the size of the object.*   LastModifiedDate: the time when the object was last modified.*   ETag: the ETag of the object. It is used to identify the content of the object.*   StorageClass: the storage class of the object.*   IsMultipartUploaded: specifies whether the object is uploaded by using multipart upload.*   EncryptionStatus: the encryption status of the object.
        */
        public Builder fields(List<InventoryOptionalFieldType> value) {
            requireNonNull(value);
            this.fields = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(OptionalFields from) { 
            this.fields = from.fields; 
        }

        public OptionalFields build() {
            return new OptionalFields(this);
        }
    }
}
