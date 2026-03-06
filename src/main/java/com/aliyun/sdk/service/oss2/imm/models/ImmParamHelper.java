package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Helper class for converting complex typed parameters to JSON query parameter strings.
 */
public final class ImmParamHelper {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private ImmParamHelper() {}

    /**
     * Converts a list of WorkflowParameter to a JSON string for use as a query parameter.
     */
    public static String toWorkflowParameters(List<WorkflowParameter> value) {
        return toJson(value);
    }

    /**
     * Converts a DatasetConfig to a JSON string for use as a query parameter.
     */
    public static String toDatasetConfig(DatasetConfig value) {
        return toJson(value);
    }

    /**
     * Converts a SimpleQuery to a JSON string for use as a query parameter.
     */
    public static String toSimpleQuery(SimpleQuery value) {
        return toJson(value);
    }

    /**
     * Converts a list of Aggregation to a JSON string for use as a query parameter.
     */
    public static String toAggregations(List<Aggregation> value) {
        return toJson(value);
    }

    /**
     * Converts a list of strings to a JSON array string for use as a query parameter.
     */
    public static String toStringList(List<String> value) {
        return toJson(value);
    }

    private static String toJson(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return JSON_MAPPER.writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize parameter to JSON", e);
        }
    }
}
