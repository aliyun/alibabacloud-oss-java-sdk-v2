package com.aliyun.sdk.service.oss2.models;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class RestoreObjectRequestTest {
    @Test
    public void testEmptyBuilder() {
        RestoreObjectRequest request = RestoreObjectRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.restoreRequest()).isNull();
    }

    @Test
    public void testFullBuilder() {
        JobParameters jobParameters = JobParameters.newBuilder()
                .tier("Expedited")
                .build();

        RestoreRequest restoreRequest = RestoreRequest.newBuilder()
                .days(3L)
                .jobParameters(jobParameters)
                .build();

        RestoreObjectRequest request = RestoreObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .restoreRequest(restoreRequest)
                .parameter("versionId", "1234567890")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("exampleobject.txt");
        assertThat(request.restoreRequest()).isNotNull();
        assertThat(request.restoreRequest().days()).isEqualTo(3L);
        assertThat(request.restoreRequest().jobParameters()).isNotNull();
        assertThat(request.restoreRequest().jobParameters().tier()).isEqualTo("Expedited");
        assertThat(request.parameters()).contains(new AbstractMap.SimpleEntry<>("versionId", "1234567890"));
    }

    @Test
    public void testToBuilderPreserveState() {
        JobParameters jobParameters = JobParameters.newBuilder()
                .tier("Standard")
                .build();

        RestoreRequest restoreRequest = RestoreRequest.newBuilder()
                .days(5L)
                .jobParameters(jobParameters)
                .build();

        RestoreObjectRequest original = RestoreObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .restoreRequest(restoreRequest)
                .parameter("versionId", "9876543210")
                .build();

        RestoreObjectRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.key()).isEqualTo("exampleobject.txt");
        assertThat(copy.restoreRequest()).isNotNull();
        assertThat(copy.restoreRequest().days()).isEqualTo(5L);
        assertThat(copy.restoreRequest().jobParameters()).isNotNull();
        assertThat(copy.restoreRequest().jobParameters().tier()).isEqualTo("Standard");
        assertThat(copy.parameters()).contains(new AbstractMap.SimpleEntry<>("versionId", "9876543210"));
    }

    @Test
    public void testHeaderProperties() {
        JobParameters jobParameters = JobParameters.newBuilder()
                .tier("Bulk")
                .build();

        RestoreRequest restoreRequest = RestoreRequest.newBuilder()
                .days(7L)
                .jobParameters(jobParameters)
                .build();

        RestoreObjectRequest request = RestoreObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .restoreRequest(restoreRequest)
                .versionId("1234567890")
                .build();

        assertThat(request.versionId()).isEqualTo("1234567890");
        assertThat(request.restoreRequest()).isNotNull();
        assertThat(request.restoreRequest().days()).isEqualTo(7L);
        assertThat(request.restoreRequest().jobParameters()).isNotNull();
        assertThat(request.restoreRequest().jobParameters().tier()).isEqualTo("Bulk");
    }
}