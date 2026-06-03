package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteFileMetaRequestTest {

    @Test
    public void testEmptyBuilder() {
        DeleteFileMetaRequest request = DeleteFileMetaRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.datasetName()).isNull();
        assertThat(request.uri()).isNull();
    }

    @Test
    public void testFullBuilder() {
        DeleteFileMetaRequest request = DeleteFileMetaRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("my-dataset")
                .uri("oss://examplebucket/prefix/test.jpg")
                .header("x-custom-header", "value1")
                .parameter("extra-param", "extra-value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.datasetName()).isEqualTo("my-dataset");
        assertThat(request.uri()).isEqualTo("oss://examplebucket/prefix/test.jpg");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-custom-header", "value1"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("extra-param", "extra-value"));

        DeleteFileMetaRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.datasetName()).isEqualTo("my-dataset");
        assertThat(copy.uri()).isEqualTo("oss://examplebucket/prefix/test.jpg");
    }

    @Test
    public void testToBuilderPreserveState() {
        DeleteFileMetaRequest original = DeleteFileMetaRequest.newBuilder()
                .bucket("test-bucket")
                .datasetName("test-dataset")
                .uri("oss://test-bucket/file.jpg")
                .build();

        DeleteFileMetaRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.datasetName()).isEqualTo("test-dataset");
        assertThat(copy.uri()).isEqualTo("oss://test-bucket/file.jpg");
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §12 DeleteFileMeta URL params
        DeleteFileMetaRequest request = DeleteFileMetaRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("photos-2026")
                .uri("oss://examplebucket/photos/sunset.jpg")
                .build();

        OperationInput input = SerdeDatasetBasic.fromDeleteFileMeta(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("action")).isEqualTo("deleteFileMeta");
        assertThat(input.parameters().get("metaQuery")).isEqualTo("");
        assertThat(input.parameters().get("datasetName")).isEqualTo("photos-2026");
        assertThat(input.parameters().get("uri")).isEqualTo("oss://examplebucket/photos/sunset.jpg");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.body().isPresent()).isFalse();
    }
}
