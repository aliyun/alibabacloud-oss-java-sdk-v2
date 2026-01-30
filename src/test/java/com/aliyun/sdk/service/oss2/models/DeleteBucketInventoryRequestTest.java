package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketInventory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteBucketInventoryRequestTest {

    @Test
    public void testEmptyBuilder() {
        DeleteBucketInventoryRequest request = DeleteBucketInventoryRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        DeleteBucketInventoryRequest request = DeleteBucketInventoryRequest.newBuilder()
                .bucket("examplebucket")
                .inventoryId("list1")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.inventoryId()).isEqualTo("list1");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("inventoryId", "list1")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        DeleteBucketInventoryRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.inventoryId()).isEqualTo("list1");
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("inventoryId", "list1")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        DeleteBucketInventoryRequest original = DeleteBucketInventoryRequest.newBuilder()
                .bucket("test-bucket")
                .inventoryId("test-inventory")
                .build();

        DeleteBucketInventoryRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.inventoryId()).isEqualTo("test-inventory");
    }

    @Test
    public void testHeaderProperties() {
        DeleteBucketInventoryRequest request = DeleteBucketInventoryRequest.newBuilder()
                .bucket("test-bucket")
                .inventoryId("inventory123")
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.inventoryId()).isEqualTo("inventory123");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        DeleteBucketInventoryRequest request = DeleteBucketInventoryRequest.newBuilder()
                .bucket("xml-bucket")
                .inventoryId("list1")
                .build();

        OperationInput input = SerdeBucketInventory.fromDeleteBucketInventory(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("inventoryId")).isEqualTo("list1");
        assertThat(input.parameters().get("inventory")).isEqualTo("");
    }
}