package com.aliyun.sdk.service.oss2.vectors.models;

/**
 * The analyzer type used for the full text search of a string field.
 */
public enum AnalyzerType {

    STANDARD("standard"),
    SPLIT("split"),
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a AnalyzerType instance.
     */
    private final String value;

    AnalyzerType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a AnalyzerType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed AnalyzerType object, or UNKNOWN if unable to parse.
     */
    public static AnalyzerType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        AnalyzerType[] items = AnalyzerType.values();
        for (AnalyzerType item : items) {
            if (item.toString().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return UNKNOWN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.value;
    }
}
