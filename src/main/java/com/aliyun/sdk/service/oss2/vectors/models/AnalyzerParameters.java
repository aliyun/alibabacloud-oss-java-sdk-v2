package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The parameters of the text analyzer.
 */
public class AnalyzerParameters {
    @JsonProperty("caseSensitive")
    private Boolean caseSensitive;
    @JsonProperty("delimitWord")
    private Boolean delimitWord;
    @JsonProperty("delimiter")
    private String delimiter;

    public AnalyzerParameters() {
    }

    private AnalyzerParameters(Builder builder) {
        this.caseSensitive = builder.caseSensitive;
        this.delimitWord = builder.delimitWord;
        this.delimiter = builder.delimiter;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Whether the analyzer is case sensitive. Default value: false.
     */
    public Boolean caseSensitive() {
        return caseSensitive;
    }

    /**
     * Whether to split words that contain both letters and digits. Default value: false.
     */
    public Boolean delimitWord() {
        return delimitWord;
    }

    /**
     * The custom delimiter. It is required when the analyzer is split.
     */
    public String delimiter() {
        return delimiter;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Boolean caseSensitive;
        private Boolean delimitWord;
        private String delimiter;

        private Builder() {
        }

        private Builder(AnalyzerParameters from) {
            this.caseSensitive = from.caseSensitive;
            this.delimitWord = from.delimitWord;
            this.delimiter = from.delimiter;
        }

        /**
         * Whether the analyzer is case sensitive. Default value: false.
         */
        public Builder caseSensitive(Boolean caseSensitive) {
            this.caseSensitive = caseSensitive;
            return this;
        }

        /**
         * Whether to split words that contain both letters and digits. Default value: false.
         */
        public Builder delimitWord(Boolean delimitWord) {
            this.delimitWord = delimitWord;
            return this;
        }

        /**
         * The custom delimiter. It is required when the analyzer is split.
         */
        public Builder delimiter(String delimiter) {
            this.delimiter = delimiter;
            return this;
        }

        public AnalyzerParameters build() {
            return new AnalyzerParameters(this);
        }
    }
}
