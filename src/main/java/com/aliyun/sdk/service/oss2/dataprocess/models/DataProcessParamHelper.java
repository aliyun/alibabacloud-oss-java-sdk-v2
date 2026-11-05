package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.internal.serde.OssSerde;

import java.util.List;

/**
 * Helper class for converting complex typed parameters to JSON query parameter strings.
 */
public final class DataProcessParamHelper {
    private DataProcessParamHelper() {}

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
     * Converts a list of SmartClusterRule to a JSON string for use as a query parameter.
     */
    public static String toSmartClusterRules(List<SmartClusterRule> value) {
        return toJson(value);
    }

    /**
     * Converts a SmartClusterRule to a JSON string for use as a query parameter.
     */
    public static String toSmartClusterRule(SmartClusterRule value) {
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
            return OssSerde.toJson(value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize parameter to JSON", e);
        }
    }
}
