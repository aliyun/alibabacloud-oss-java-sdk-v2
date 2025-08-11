package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores the results of the GetObjectACL request.
 */
@JacksonXmlRootElement(localName = "AccessControlPolicy")
public final class AccessControlPolicy {
    @JacksonXmlProperty(localName = "Owner")
    private Owner owner;

    @JacksonXmlProperty(localName = "AccessControlList")
    private AccessControlList accessControlList;

    public AccessControlPolicy() {
    }

    private AccessControlPolicy(Builder builder) {
        this.owner = builder.owner;
        this.accessControlList = builder.accessControlList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The container that stores the information about the bucket owner.
     */
    public Owner owner() {
        return this.owner;
    }

    /**
     * The container that stores the ACL information.
     */
    public AccessControlList accessControlList() {
        return this.accessControlList;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Owner owner;
        private AccessControlList accessControlList;

        private Builder() {
            super();
        }

        private Builder(AccessControlPolicy from) {
            this.owner = from.owner;
            this.accessControlList = from.accessControlList;
        }

        /**
         * The container that stores the information about the bucket owner.
         */
        public Builder owner(Owner value) {
            requireNonNull(value);
            this.owner = value;
            return this;
        }

        /**
         * The container that stores the ACL information.
         */
        public Builder accessControlList(AccessControlList value) {
            requireNonNull(value);
            this.accessControlList = value;
            return this;
        }

        public AccessControlPolicy build() {
            return new AccessControlPolicy(this);
        }
    }
}
