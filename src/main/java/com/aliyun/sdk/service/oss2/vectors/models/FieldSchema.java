package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Objects.requireNonNull;

/**
 * The schema of a single field in a fusion vector index.
 */
public class FieldSchema {
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("dataType")
    private String dataType;
    @JsonProperty("dimension")
    private Integer dimension;
    @JsonProperty("distanceMetric")
    private String distanceMetric;
    @JsonProperty("isArray")
    private Boolean isArray;
    @JsonProperty("isPartitionKey")
    private Boolean isPartitionKey;
    @JsonProperty("exactMatch")
    private Boolean exactMatch;
    @JsonProperty("text")
    private TextSchema text;

    public FieldSchema() {
    }

    private FieldSchema(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.dataType = builder.dataType;
        this.dimension = builder.dimension;
        this.distanceMetric = builder.distanceMetric;
        this.isArray = builder.isArray;
        this.isPartitionKey = builder.isPartitionKey;
        this.exactMatch = builder.exactMatch;
        this.text = builder.text;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the field. It is unique in an index and 1 to 63 characters in length.
     */
    public String name() {
        return name;
    }

    /**
     * The type of the field. Valid values: vector, double, long, bool, string, ip and geoPoint.
     */
    public String type() {
        return type;
    }

    /**
     * The data type of the vector. It is required when the type is vector. Valid value: float32.
     */
    public String dataType() {
        return dataType;
    }

    /**
     * The dimension of the vector. It is required when the type is vector. Valid values: 1 to 4096.
     */
    public Integer dimension() {
        return dimension;
    }

    /**
     * The distance metric of the vector. It is required when the type is vector.
     * Valid values: euclidean, cosine and ip.
     */
    public String distanceMetric() {
        return distanceMetric;
    }

    /**
     * Whether the field is an array. Default value: false.
     */
    @JsonProperty("isArray")
    public Boolean isArray() {
        return isArray;
    }

    /**
     * Whether the field is used as the partition key. Default value: false.
     * Only one string field can be specified as the partition key.
     */
    @JsonProperty("isPartitionKey")
    public Boolean isPartitionKey() {
        return isPartitionKey;
    }

    /**
     * Whether the string field supports the exact match query. Default value: true.
     */
    public Boolean exactMatch() {
        return exactMatch;
    }

    /**
     * The full text search configuration of the string field.
     */
    public TextSchema text() {
        return text;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String name;
        private String type;
        private String dataType;
        private Integer dimension;
        private String distanceMetric;
        private Boolean isArray;
        private Boolean isPartitionKey;
        private Boolean exactMatch;
        private TextSchema text;

        private Builder() {
        }

        private Builder(FieldSchema from) {
            this.name = from.name;
            this.type = from.type;
            this.dataType = from.dataType;
            this.dimension = from.dimension;
            this.distanceMetric = from.distanceMetric;
            this.isArray = from.isArray;
            this.isPartitionKey = from.isPartitionKey;
            this.exactMatch = from.exactMatch;
            this.text = from.text;
        }

        /**
         * The name of the field. It is unique in an index and 1 to 63 characters in length.
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * The type of the field. Valid values: vector, double, long, bool, string, ip and geoPoint.
         */
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        /**
         * Set the type of the field using FieldType enum.
         */
        public Builder type(FieldType type) {
            requireNonNull(type);
            this.type = type.toString();
            return this;
        }

        /**
         * The data type of the vector. It is required when the type is vector. Valid value: float32.
         */
        public Builder dataType(String dataType) {
            this.dataType = dataType;
            return this;
        }

        /**
         * Set the data type of the vector using VectorDataType enum.
         */
        public Builder dataType(VectorDataType dataType) {
            requireNonNull(dataType);
            this.dataType = dataType.toString();
            return this;
        }

        /**
         * The dimension of the vector. It is required when the type is vector. Valid values: 1 to 4096.
         */
        public Builder dimension(Integer dimension) {
            this.dimension = dimension;
            return this;
        }

        /**
         * The distance metric of the vector. It is required when the type is vector.
         * Valid values: euclidean, cosine and ip.
         */
        public Builder distanceMetric(String distanceMetric) {
            this.distanceMetric = distanceMetric;
            return this;
        }

        /**
         * Set the distance metric of the vector using DistanceMetricType enum.
         */
        public Builder distanceMetric(DistanceMetricType distanceMetric) {
            requireNonNull(distanceMetric);
            this.distanceMetric = distanceMetric.toString();
            return this;
        }

        /**
         * Whether the field is an array. Default value: false.
         */
        public Builder isArray(Boolean isArray) {
            this.isArray = isArray;
            return this;
        }

        /**
         * Whether the field is used as the partition key. Default value: false.
         * Only one string field can be specified as the partition key.
         */
        public Builder isPartitionKey(Boolean isPartitionKey) {
            this.isPartitionKey = isPartitionKey;
            return this;
        }

        /**
         * Whether the string field supports the exact match query. Default value: true.
         */
        public Builder exactMatch(Boolean exactMatch) {
            this.exactMatch = exactMatch;
            return this;
        }

        /**
         * The full text search configuration of the string field.
         */
        public Builder text(TextSchema text) {
            this.text = text;
            return this;
        }

        public FieldSchema build() {
            return new FieldSchema(this);
        }
    }
}
