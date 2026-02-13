package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketObjectFcAccessPoint;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class ListAccessPointsForObjectProcessRequestTest {

    @Test
    public void testEmptyBuilder() {
        ListAccessPointsForObjectProcessRequest request = ListAccessPointsForObjectProcessRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        ListAccessPointsForObjectProcessRequest request = ListAccessPointsForObjectProcessRequest.newBuilder()
                .maxKeys(10L)
                .continuationToken("abcd")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.maxKeys()).isEqualTo(10L);
        assertThat(request.continuationToken()).isEqualTo("abcd");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("max-keys", "10"),
                new AbstractMap.SimpleEntry<>("continuation-token", "abcd")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        ListAccessPointsForObjectProcessRequest copy = request.toBuilder().build();
        assertThat(copy.maxKeys()).isEqualTo(10L);
        assertThat(copy.continuationToken()).isEqualTo("abcd");
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("max-keys", "10"),
                new AbstractMap.SimpleEntry<>("continuation-token", "abcd")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        ListAccessPointsForObjectProcessRequest original = ListAccessPointsForObjectProcessRequest.newBuilder()
                .maxKeys(20L)
                .continuationToken("xyz")
                .build();

        ListAccessPointsForObjectProcessRequest copy = original.toBuilder().build();

        assertThat(copy.maxKeys()).isEqualTo(20L);
        assertThat(copy.continuationToken()).isEqualTo("xyz");
    }

    @Test
    public void testHeaderProperties() {
        ListAccessPointsForObjectProcessRequest request = ListAccessPointsForObjectProcessRequest.newBuilder()
                .maxKeys(5L)
                .continuationToken("test-token")
                .build();

        assertThat(request.maxKeys()).isEqualTo(5L);
        assertThat(request.continuationToken()).isEqualTo("test-token");
    }

    @Test
    public void xmlBuilder() {
        ListAccessPointsForObjectProcessRequest request = ListAccessPointsForObjectProcessRequest.newBuilder()
                .maxKeys(10L)
                .continuationToken("abcd")
                .build();

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromListAccessPointsForObjectProcess(request);

        assertThat(input.parameters().get("accessPointForObjectProcess")).isEqualTo("");
        assertThat(input.parameters().get("max-keys")).isEqualTo("10");
        assertThat(input.parameters().get("continuation-token")).isEqualTo("abcd");
    }
}