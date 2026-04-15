package com.aliyun.sdk.service.oss2.arns;

import com.aliyun.sdk.service.oss2.Validate;
import com.aliyun.sdk.service.oss2.utils.StringUtils;

import java.util.Objects;
import java.util.Optional;

public final class Arn {

    private final String service;
    private final String region;
    private final String accountId;
    private final String resource;
    private final ArnResource arnResource;

    private Arn(Builder builder) {
        this.service = Validate.paramNotBlank(builder.service, "service");
        this.region = builder.region;
        this.accountId = builder.accountId;
        this.resource = Validate.paramNotBlank(builder.resource, "resource");
        this.arnResource = ArnResource.fromString(resource);
    }

    /**
     * @return The service namespace that identifies the product
     */
    public String service() {
        return service;
    }

    /**
     * @return The Region that the resource resides in.
     */
    public Optional<String> region() {
        return StringUtils.isEmpty(region) ? Optional.empty() : Optional.of(region);
    }

    /**
     * @return The ID of the account that owns the resource.
     */
    public Optional<String> accountId() {
        return StringUtils.isEmpty(accountId) ? Optional.empty() : Optional.of(accountId);
    }

    /**
     * @return {@link ArnResource}
     */
    public ArnResource resource() {
        return arnResource;
    }

    /**
     * @return the resource as string
     */
    public String resourceAsString() {
        return resource;
    }

    /**
     * @return a builder for {@link Arn}.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static Optional<Arn> tryFromString(String arn) {
        return parseArn(arn, false);
    }

    public static Arn fromString(String arn) {
        return parseArn(arn, true).orElseThrow(() -> new IllegalArgumentException("ARN parsing failed"));
    }

    private static Optional<Arn> parseArn(String arn, boolean throwOnError) {
        if (arn == null) {
            return Optional.empty();
        }

        int arnColonIndex = arn.indexOf(':');
        if (arnColonIndex < 0 || !"acs".equals(arn.substring(0, arnColonIndex))) {
            if (throwOnError) {
                throw new IllegalArgumentException("Malformed ARN - doesn't start with 'acs:'");
            }
            return Optional.empty();
        }

        int serviceColonIndex = arn.indexOf(':', arnColonIndex + 1);
        if (serviceColonIndex < 0) {
            if (throwOnError) {
                throw new IllegalArgumentException("Malformed ARN - no service specified");
            }
            return Optional.empty();
        }
        String service = arn.substring(arnColonIndex + 1, serviceColonIndex);

        int regionColonIndex = arn.indexOf(':', serviceColonIndex + 1);
        if (regionColonIndex < 0) {
            if (throwOnError) {
                throw new IllegalArgumentException("Malformed ARN - no region specified");
            }
            return Optional.empty();
        }
        String region = arn.substring(serviceColonIndex + 1, regionColonIndex);

        int accountColonIndex = arn.indexOf(':', regionColonIndex + 1);
        if (accountColonIndex < 0) {
            if (throwOnError) {
                throw new IllegalArgumentException("Malformed ARN - no account specified");
            }
            return Optional.empty();
        }
        String accountId = arn.substring(regionColonIndex + 1, accountColonIndex);

        String resource = arn.substring(accountColonIndex + 1);
        if (resource.isEmpty()) {
            if (throwOnError) {
                throw new IllegalArgumentException("Malformed ARN - no resource specified");
            }
            return Optional.empty();
        }

        Arn resultArn = builder()
                .service(service)
                .region(region)
                .accountId(accountId)
                .resource(resource)
                .build();

        return Optional.of(resultArn);
    }

    @Override
    public String toString() {
        return "acs:"
                + this.service
                + ":"
                + (region == null ? "" : region)
                + ":"
                + (this.accountId == null ? "" : this.accountId)
                + ":"
                + this.resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Arn arn = (Arn) o;

        if (!Objects.equals(service, arn.service)) {
            return false;
        }
        if (!Objects.equals(region, arn.region)) {
            return false;
        }
        if (!Objects.equals(accountId, arn.accountId)) {
            return false;
        }
        if (!Objects.equals(resource, arn.resource)) {
            return false;
        }
        return Objects.equals(arnResource, arn.arnResource);
    }

    @Override
    public int hashCode() {
        int result = "acs".hashCode();
        result = 31 * result + service.hashCode();
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        result = 31 * result + resource.hashCode();
        return result;
    }

    public Builder toBuilder() {
        return builder()
                .accountId(accountId)
                .region(region)
                .resource(resource)
                .service(service);
    }

    public static final class Builder {
        private String service;
        private String region;
        private String accountId;
        private String resource;

        private Builder() {
        }


        public Builder service(String service) {
            this.service = service;
            return this;
        }

        public Builder region(String region) {
            this.region = region;
            return this;
        }

        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder resource(String resource) {
            this.resource = resource;
            return this;
        }

        public Arn build() {
            return new Arn(this);
        }
    }
}
