package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketInventory;;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class ListBucketInventoryRequestTest {

    @Test
    public void testEmptyBuilder() {
        ListBucketInventoryRequest request = ListBucketInventoryRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        ListBucketInventoryRequest request = ListBucketInventoryRequest.newBuilder()
                .bucket("examplebucket")
                .continuationToken("token123")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.continuationToken()).isEqualTo("token123");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("continuation-token", "token123")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        ListBucketInventoryRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.continuationToken()).isEqualTo("token123");
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("continuation-token", "token123")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        ListBucketInventoryRequest original = ListBucketInventoryRequest.newBuilder()
                .bucket("test-bucket")
                .continuationToken("test-token")
                .build();

        ListBucketInventoryRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.continuationToken()).isEqualTo("test-token");
    }

    @Test
    public void testHeaderProperties() {
        ListBucketInventoryRequest request = ListBucketInventoryRequest.newBuilder()
                .bucket("test-bucket")
                .continuationToken("token456")
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.continuationToken()).isEqualTo("token456");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ListBucketInventoryRequest request = ListBucketInventoryRequest.newBuilder()
                .bucket("xml-bucket")
                .continuationToken("test-token")
                .build();

        OperationInput input = SerdeBucketInventory.fromListBucketInventory(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("continuation-token")).isEqualTo("test-token");
        assertThat(input.parameters().get("inventory")).isEqualTo("");
    }
}