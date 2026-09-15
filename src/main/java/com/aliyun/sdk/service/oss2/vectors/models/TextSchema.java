package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Objects.requireNonNull;

/**
 * The full text search configuration of a string field.
 */
public class TextSchema {
    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("analyzer")
    private String analyzer;
    @JsonProperty("analyzerParameters")
    private AnalyzerParameters analyzerParameters;

    public TextSchema() {
    }

    private TextSchema(Builder builder) {
        this.enabled = builder.enabled;
        this.analyzer = builder.analyzer;
        this.analyzerParameters = builder.analyzerParameters;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Whether to enable the full text search. Default value: false.
     */
    public Boolean enabled() {
        return enabled;
    }

    /**
     * The type of the analyzer. Valid values: standard and split. Default value: standard.
     */
    public String analyzer() {
        return analyzer;
    }

    /**
     * The parameters of the analyzer.
     */
    public AnalyzerParameters analyzerParameters() {
        return analyzerParameters;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Boolean enabled;
        private String analyzer;
        private AnalyzerParameters analyzerParameters;

        private Builder() {
        }

        private Builder(TextSchema from) {
            this.enabled = from.enabled;
            this.analyzer = from.analyzer;
            this.analyzerParameters = from.analyzerParameters;
        }

        /**
         * Whether to enable the full text search. Default value: false.
         */
        public Builder enabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        /**
         * The type of the analyzer. Valid values: standard and split. Default value: standard.
         */
        public Builder analyzer(String analyzer) {
            this.analyzer = analyzer;
            return this;
        }

        /**
         * Set the type of the analyzer using AnalyzerType enum.
         */
        public Builder analyzer(AnalyzerType analyzer) {
            requireNonNull(analyzer);
            this.analyzer = analyzer.toString();
            return this;
        }

        /**
         * The parameters of the analyzer.
         */
        public Builder analyzerParameters(AnalyzerParameters analyzerParameters) {
            this.analyzerParameters = analyzerParameters;
            return this;
        }

        public TextSchema build() {
            return new TextSchema(this);
        }
    }
}
