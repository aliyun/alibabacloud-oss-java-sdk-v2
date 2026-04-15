package com.aliyun.sdk.service.oss2.tables.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TableBucketSummaryTest {

    @Test
    public void testEmptyBuilder() {
        TableBucketSummary result = TableBucketSummary.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.arn()).isNull();
        assertThat(result.name()).isNull();
        assertThat(result.ownerAccountId()).isNull();
        assertThat(result.createdAt()).isNull();
        assertThat(result.tableBucketId()).isNull();
        assertThat(result.type()).isNull();
    }

    @Test
    public void testFullBuilder() {
        TableBucketSummary bucket = TableBucketSummary.newBuilder()
                .arn("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket")
                .name("test-bucket")
                .ownerAccountId("123456789012")
                .tableBucketId("tb-1234567890abcdef")
                .createdAt("2023-12-17T00:20:57.000Z")
                .type("TABLE")
                .build();

        assertThat(bucket.arn()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket");
        assertThat(bucket.name()).isEqualTo("test-bucket");
        assertThat(bucket.ownerAccountId()).isEqualTo("123456789012");
        assertThat(bucket.tableBucketId()).isEqualTo("tb-1234567890abcdef");
        assertThat(bucket.createdAt()).isEqualTo("2023-12-17T00:20:57.000Z");
        assertThat(bucket.type()).isEqualTo("TABLE");
    }

    @Test
    public void testToBuilderPreserveState() {
        TableBucketSummary original = TableBucketSummary.newBuilder()
                .arn("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket")
                .name("original-bucket")
                .ownerAccountId("123456789012")
                .tableBucketId("tb-original-1234567890")
                .createdAt("2023-12-17T00:20:57.000Z")
                .type("TABLE")
                .build();

        TableBucketSummary copy = original.toBuilder().build();

        assertThat(copy.arn()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket");
        assertThat(copy.name()).isEqualTo("original-bucket");
        assertThat(copy.ownerAccountId()).isEqualTo("123456789012");
        assertThat(copy.tableBucketId()).isEqualTo("tb-original-1234567890");
        assertThat(copy.createdAt()).isEqualTo("2023-12-17T00:20:57.000Z");
        assertThat(copy.type()).isEqualTo("TABLE");
    }

    @Test
    public void testToBuilderWithModifications() {
        TableBucketSummary original = TableBucketSummary.newBuilder()
                .arn("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket")
                .name("original-bucket")
                .ownerAccountId("123456789012")
                .tableBucketId("tb-original-1234567890")
                .createdAt("2023-12-17T00:20:57.000Z")
                .type("TABLE")
                .build();

        TableBucketSummary modified = original.toBuilder()
                .name("modified-bucket")
                .type("VECTOR")
                .build();

        assertThat(modified.arn()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket");
        assertThat(modified.name()).isEqualTo("modified-bucket");
        assertThat(modified.ownerAccountId()).isEqualTo("123456789012");
        assertThat(modified.tableBucketId()).isEqualTo("tb-original-1234567890");
        assertThat(modified.createdAt()).isEqualTo("2023-12-17T00:20:57.000Z");
        assertThat(modified.type()).isEqualTo("VECTOR");
    }

    @Test
    public void testBuilderWithoutType() {
        TableBucketSummary bucket = TableBucketSummary.newBuilder()
                .arn("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket")
                .name("test-bucket")
                .ownerAccountId("123456789012")
                .tableBucketId("tb-1234567890abcdef")
                .createdAt("2023-12-17T00:20:57.000Z")
                .build();

        assertThat(bucket.arn()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket");
        assertThat(bucket.name()).isEqualTo("test-bucket");
        assertThat(bucket.ownerAccountId()).isEqualTo("123456789012");
        assertThat(bucket.tableBucketId()).isEqualTo("tb-1234567890abcdef");
        assertThat(bucket.createdAt()).isEqualTo("2023-12-17T00:20:57.000Z");
        assertThat(bucket.type()).isNull();
    }

    @Test
    public void testDifferentTypes() {
        TableBucketSummary tableType = TableBucketSummary.newBuilder()
                .type("TABLE")
                .build();

        TableBucketSummary vectorType = TableBucketSummary.newBuilder()
                .type("VECTOR")
                .build();

        assertThat(tableType.type()).isEqualTo("TABLE");
        assertThat(vectorType.type()).isEqualTo("VECTOR");
    }
}
