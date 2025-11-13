package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

/**
 * The container that stores the results of the ListCname request.
 */
@JacksonXmlRootElement(localName = "ListCnameResult")
public final class ListCnameInfo {

    @JacksonXmlProperty(localName = "Bucket")
    private String bucket;

    @JacksonXmlProperty(localName = "Owner")
    private String owner;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Cname")
    private List<CnameInfo> cnames;

    public ListCnameInfo() {}

    private ListCnameInfo(Builder builder) {
        this.bucket = builder.bucket;
        this.owner = builder.owner;
        this.cnames = builder.cnames;
    }

    /**
     * Returns the name of the bucket.
     */
    public String getBucket() {
        return bucket;
    }

    /**
     * Returns the owner of the bucket.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Returns the list of CNAME entries.
     */
    public List<CnameInfo> getCnames() {
        return cnames;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String bucket;
        private String owner;
        private List<CnameInfo> cnames;

        /**
         * Sets the bucket name.
         */
        public Builder bucket(String value) {
            this.bucket = value;
            return this;
        }

        /**
         * Sets the owner of the bucket.
         */
        public Builder owner(String value) {
            this.owner = value;
            return this;
        }

        /**
         * Sets the list of CNAME entries.
         */
        public Builder cnames(List<CnameInfo> value) {
            this.cnames = value;
            return this;
        }

        public ListCnameInfo build() {
            return new ListCnameInfo(this);
        }

        private Builder() {}

        private Builder(ListCnameInfo from) {
            this.bucket = from.bucket;
            this.owner = from.owner;
            this.cnames = from.cnames;
        }
    }

}