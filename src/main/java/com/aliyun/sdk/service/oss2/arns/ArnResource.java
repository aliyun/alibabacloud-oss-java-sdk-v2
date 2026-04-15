package com.aliyun.sdk.service.oss2.arns;

import com.aliyun.sdk.service.oss2.utils.StringUtils;

import java.util.Objects;
import java.util.Optional;

import static com.aliyun.sdk.service.oss2.Validate.paramNotNull;


public class ArnResource {

    private final String resourceType;
    private final String resource;
    private final String qualifier;

    private ArnResource(Builder builder) {
        this.resourceType = builder.resourceType;
        this.resource = paramNotNull(builder.resource, "resource");
        this.qualifier = builder.qualifier;
    }

    /**
     * @return the optional resource type
     */
    public Optional<String> resourceType() {
        return Optional.ofNullable(resourceType);
    }

    /**
     * @return the entire resource as a string
     */
    public String resource() {
        return resource;
    }

    /**
     * @return the optional resource qualifier
     */
    public Optional<String> qualifier() {
        return Optional.ofNullable(qualifier);
    }

    /**
     * @return a builder for {@link ArnResource}.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static ArnResource fromString(String resource) {
        Character splitter = StringUtils.findFirstOccurrence(resource, ':', '/');

        if (splitter == null) {
            return ArnResource.builder().resource(resource).build();
        }

        int resourceTypeColonIndex = resource.indexOf(splitter);

        Builder builder = ArnResource.builder().resourceType(resource.substring(0, resourceTypeColonIndex));
        int resourceColonIndex = resource.indexOf(splitter, resourceTypeColonIndex);
        int qualifierColonIndex = resource.indexOf(splitter, resourceColonIndex + 1);
        if (qualifierColonIndex < 0) {
            builder.resource(resource.substring(resourceTypeColonIndex + 1));
        } else {
            builder.resource(resource.substring(resourceTypeColonIndex + 1, qualifierColonIndex));
            builder.qualifier(resource.substring(qualifierColonIndex + 1));
        }

        return builder.build();
    }

    @Override
    public String toString() {
        return this.resourceType
                + ":"
                + this.resource
                + ":"
                + this.qualifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArnResource that = (ArnResource) o;

        if (!Objects.equals(resourceType, that.resourceType)) {
            return false;
        }
        if (!Objects.equals(resource, that.resource)) {
            return false;
        }
        return Objects.equals(qualifier, that.qualifier);
    }

    @Override
    public int hashCode() {
        int result = resourceType != null ? resourceType.hashCode() : 0;
        result = 31 * result + (resource != null ? resource.hashCode() : 0);
        result = 31 * result + (qualifier != null ? qualifier.hashCode() : 0);
        return result;
    }

    public Builder toBuilder() {
        return builder()
                .resource(resource)
                .resourceType(resourceType)
                .qualifier(qualifier);
    }


    public static final class Builder {
        private String resourceType;
        private String resource;
        private String qualifier;

        private Builder() {
        }

        public Builder resourceType(String resourceType) {
            this.resourceType = resourceType;
            return this;
        }

        public Builder resource(String resource) {
            this.resource = resource;
            return this;
        }


        public Builder qualifier(String qualifier) {
            this.qualifier = qualifier;
            return this;
        }

        public ArnResource build() {
            return new ArnResource(this);
        }
    }

}
