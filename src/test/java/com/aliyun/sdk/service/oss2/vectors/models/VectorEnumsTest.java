package com.aliyun.sdk.service.oss2.vectors.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the enum types of the vector service models. Each enum keeps a
 * serialized string value, parses case-insensitively and falls back to UNKNOWN.
 */
public class VectorEnumsTest {

    @Test
    public void testFieldType() {
        assertThat(FieldType.VECTOR.toString()).isEqualTo("vector");
        assertThat(FieldType.DOUBLE.toString()).isEqualTo("double");
        assertThat(FieldType.LONG.toString()).isEqualTo("long");
        assertThat(FieldType.BOOL.toString()).isEqualTo("bool");
        assertThat(FieldType.STRING.toString()).isEqualTo("string");
        assertThat(FieldType.IP.toString()).isEqualTo("ip");
        assertThat(FieldType.GEO_POINT.toString()).isEqualTo("geoPoint");
        assertThat(FieldType.UNKNOWN.toString()).isEqualTo("UNKNOWN");

        assertThat(FieldType.fromString("vector")).isEqualTo(FieldType.VECTOR);
        assertThat(FieldType.fromString("geoPoint")).isEqualTo(FieldType.GEO_POINT);
        // case-insensitive parsing
        assertThat(FieldType.fromString("VECTOR")).isEqualTo(FieldType.VECTOR);
        assertThat(FieldType.fromString("GeoPoint")).isEqualTo(FieldType.GEO_POINT);
        // unrecognized or null values fall back to UNKNOWN
        assertThat(FieldType.fromString("not-exist")).isEqualTo(FieldType.UNKNOWN);
        assertThat(FieldType.fromString("")).isEqualTo(FieldType.UNKNOWN);
        assertThat(FieldType.fromString(null)).isEqualTo(FieldType.UNKNOWN);
    }

    @Test
    public void testVectorDataType() {
        assertThat(VectorDataType.FLOAT32.toString()).isEqualTo("float32");
        assertThat(VectorDataType.UNKNOWN.toString()).isEqualTo("UNKNOWN");

        assertThat(VectorDataType.fromString("float32")).isEqualTo(VectorDataType.FLOAT32);
        assertThat(VectorDataType.fromString("FLOAT32")).isEqualTo(VectorDataType.FLOAT32);
        assertThat(VectorDataType.fromString("float64")).isEqualTo(VectorDataType.UNKNOWN);
        assertThat(VectorDataType.fromString(null)).isEqualTo(VectorDataType.UNKNOWN);
    }

    @Test
    public void testDistanceMetricType() {
        assertThat(DistanceMetricType.EUCLIDEAN.toString()).isEqualTo("euclidean");
        assertThat(DistanceMetricType.COSINE.toString()).isEqualTo("cosine");
        assertThat(DistanceMetricType.INNER_PRODUCT.toString()).isEqualTo("ip");
        assertThat(DistanceMetricType.UNKNOWN.toString()).isEqualTo("UNKNOWN");

        assertThat(DistanceMetricType.fromString("euclidean")).isEqualTo(DistanceMetricType.EUCLIDEAN);
        assertThat(DistanceMetricType.fromString("cosine")).isEqualTo(DistanceMetricType.COSINE);
        assertThat(DistanceMetricType.fromString("ip")).isEqualTo(DistanceMetricType.INNER_PRODUCT);
        assertThat(DistanceMetricType.fromString("IP")).isEqualTo(DistanceMetricType.INNER_PRODUCT);
        assertThat(DistanceMetricType.fromString("manhattan")).isEqualTo(DistanceMetricType.UNKNOWN);
        assertThat(DistanceMetricType.fromString(null)).isEqualTo(DistanceMetricType.UNKNOWN);
    }

    @Test
    public void testAnalyzerType() {
        assertThat(AnalyzerType.STANDARD.toString()).isEqualTo("standard");
        assertThat(AnalyzerType.SPLIT.toString()).isEqualTo("split");
        assertThat(AnalyzerType.UNKNOWN.toString()).isEqualTo("UNKNOWN");

        assertThat(AnalyzerType.fromString("standard")).isEqualTo(AnalyzerType.STANDARD);
        assertThat(AnalyzerType.fromString("split")).isEqualTo(AnalyzerType.SPLIT);
        assertThat(AnalyzerType.fromString("Standard")).isEqualTo(AnalyzerType.STANDARD);
        assertThat(AnalyzerType.fromString("jieba")).isEqualTo(AnalyzerType.UNKNOWN);
        assertThat(AnalyzerType.fromString(null)).isEqualTo(AnalyzerType.UNKNOWN);
    }

    @Test
    public void testNormalizerType() {
        assertThat(NormalizerType.NONE.toString()).isEqualTo("none");
        assertThat(NormalizerType.MIN_MAX.toString()).isEqualTo("minMax");
        assertThat(NormalizerType.L2.toString()).isEqualTo("l2");
        assertThat(NormalizerType.UNKNOWN.toString()).isEqualTo("UNKNOWN");

        assertThat(NormalizerType.fromString("none")).isEqualTo(NormalizerType.NONE);
        assertThat(NormalizerType.fromString("minMax")).isEqualTo(NormalizerType.MIN_MAX);
        assertThat(NormalizerType.fromString("MinMax")).isEqualTo(NormalizerType.MIN_MAX);
        assertThat(NormalizerType.fromString("l2")).isEqualTo(NormalizerType.L2);
        assertThat(NormalizerType.fromString("sigmoid")).isEqualTo(NormalizerType.UNKNOWN);
        assertThat(NormalizerType.fromString(null)).isEqualTo(NormalizerType.UNKNOWN);
    }

    @Test
    public void testIndexModeType() {
        assertThat(IndexModeType.STANDARD.toString()).isEqualTo("standard");
        assertThat(IndexModeType.FUSION.toString()).isEqualTo("fusion");
        assertThat(IndexModeType.UNKNOWN.toString()).isEqualTo("UNKNOWN");

        assertThat(IndexModeType.fromString("standard")).isEqualTo(IndexModeType.STANDARD);
        assertThat(IndexModeType.fromString("fusion")).isEqualTo(IndexModeType.FUSION);
        assertThat(IndexModeType.fromString("Fusion")).isEqualTo(IndexModeType.FUSION);
        assertThat(IndexModeType.fromString("hybrid")).isEqualTo(IndexModeType.UNKNOWN);
        assertThat(IndexModeType.fromString(null)).isEqualTo(IndexModeType.UNKNOWN);
    }
}
