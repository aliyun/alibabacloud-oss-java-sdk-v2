package com.aliyun.sdk.service.oss2.tables.models;

/**
 * Interface for complex types supported by {@link IcebergSchemaV2}, including
 * {@link IcebergStructType}, {@link IcebergListType}, and {@link IcebergMapType}.
 * Positions accepting complex types also accept primitive type names such as "long" and "string".
 */
public interface IcebergComplexType {

    /**
     * The type name: "struct", "list", or "map".
     */
    String type();
}
