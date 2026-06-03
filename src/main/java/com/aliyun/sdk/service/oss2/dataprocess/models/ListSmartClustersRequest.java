package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import com.aliyun.sdk.service.oss2.utils.ConvertUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static java.util.Objects.requireNonNull;

public final class ListSmartClustersRequest extends RequestModel {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private final String bucket;

    private ListSmartClustersRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    public static Builder newBuilder() { return new Builder(); }

    public String bucket() { return bucket; }
    public String datasetName() { return parameters.get("datasetName"); }
    public String nextToken() { return parameters.get("nextToken"); }

    public Integer maxResults() {
        return ConvertUtils.toIntegerOrNull(parameters.get("maxResults"));
    }

    public String clusterType() { return parameters.get("clusterType"); }

    /**
     * Returns the rule types filter as a list. The underlying parameter is stored as a JSON
     * array string (e.g. {@code ["face","keywords"]}) per the IMM POP spec; this getter
     * decodes it back to a list. Returns {@code null} when not set or when the stored value
     * cannot be decoded as a JSON array.
     */
    public List<String> ruleTypes() {
        String raw = parameters.get("ruleTypes");
        if (raw == null || raw.isEmpty()) {
            return null;
        }
        try {
            return JSON_MAPPER.readValue(raw, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return null;
        }
    }

    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() { super(); }

        private Builder(ListSmartClustersRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public Builder datasetName(String value) {
            requireNonNull(value);
            this.parameters.put("datasetName", value);
            return this;
        }

        public Builder nextToken(String value) {
            this.parameters.put("nextToken", value);
            return this;
        }

        public Builder maxResults(Integer value) {
            this.parameters.put("maxResults", value.toString());
            return this;
        }

        public Builder clusterType(String value) {
            this.parameters.put("clusterType", value);
            return this;
        }

        /**
         * Sets the rule types filter. The list is encoded as a JSON array string (e.g.
         * {@code ["face","keywords"]}) before being placed into the URL query, as required
         * by the IMM POP cspec. Valid values are {@code face} and {@code keywords}.
         */
        public Builder ruleTypes(List<String> values) {
            if (values == null || values.isEmpty()) {
                this.parameters.remove("ruleTypes");
                return this;
            }
            try {
                this.parameters.put("ruleTypes", JSON_MAPPER.writeValueAsString(values));
            } catch (Exception e) {
                throw new IllegalArgumentException("Failed to encode ruleTypes as JSON", e);
            }
            return this;
        }

        public ListSmartClustersRequest build() { return new ListSmartClustersRequest(this); }
    }
}
