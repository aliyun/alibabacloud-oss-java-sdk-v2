package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ListBucketSpacesResultTest {

    @Test
    public void testEmptyBuilder() {
        ListBucketSpacesResult result = ListBucketSpacesResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.bucketSpaces()).isNull();
    }

    @Test
    public void testXmlDeserialization() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ListBucketSpacesResult>\n" +
                "  <Owner>\n" +
                "    <ID>1234567890</ID>\n" +
                "    <DisplayName>owner-name</DisplayName>\n" +
                "  </Owner>\n" +
                "  <Prefix>space-</Prefix>\n" +
                "  <MaxKeys>100</MaxKeys>\n" +
                "  <ContinuationToken>token-1</ContinuationToken>\n" +
                "  <NextContinuationToken>token-2</NextContinuationToken>\n" +
                "  <StartAfter>space-000</StartAfter>\n" +
                "  <IsTruncated>true</IsTruncated>\n" +
                "  <BucketSpaces>\n" +
                "    <BucketSpace>\n" +
                "      <Name>space-1</Name>\n" +
                "      <Location>oss-cn-hangzhou</Location>\n" +
                "      <CreationDate>2024-01-01T00:00:00.000Z</CreationDate>\n" +
                "      <StorageClass>Standard</StorageClass>\n" +
                "    </BucketSpace>\n" +
                "    <BucketSpace>\n" +
                "      <Name>space-2</Name>\n" +
                "      <Location>oss-cn-shanghai</Location>\n" +
                "      <CreationDate>2024-02-01T00:00:00.000Z</CreationDate>\n" +
                "      <StorageClass>IA</StorageClass>\n" +
                "    </BucketSpace>\n" +
                "  </BucketSpaces>\n" +
                "</ListBucketSpacesResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of("x-oss-request-id", "req-spaces"))
                .status("OK")
                .statusCode(200)
                .build();

        ListBucketSpacesResult result = SerdeAgenticBucketBasic.toListBucketSpaces(output);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.owner()).isNotNull();
        assertThat(result.owner().id()).isEqualTo("1234567890");
        assertThat(result.owner().displayName()).isEqualTo("owner-name");
        assertThat(result.prefix()).isEqualTo("space-");
        assertThat(result.maxKeys()).isEqualTo(100);
        assertThat(result.continuationToken()).isEqualTo("token-1");
        assertThat(result.nextContinuationToken()).isEqualTo("token-2");
        assertThat(result.startAfter()).isEqualTo("space-000");
        assertThat(result.isTruncated()).isTrue();

        List<BucketSpaceSummary> spaces = result.bucketSpaces();
        assertThat(spaces).hasSize(2);
        assertThat(spaces.get(0).name()).isEqualTo("space-1");
        assertThat(spaces.get(0).location()).isEqualTo("oss-cn-hangzhou");
        assertThat(spaces.get(0).creationDate()).isEqualTo("2024-01-01T00:00:00.000Z");
        assertThat(spaces.get(0).storageClass()).isEqualTo("Standard");
        assertThat(spaces.get(1).name()).isEqualTo("space-2");
        assertThat(spaces.get(1).location()).isEqualTo("oss-cn-shanghai");
        assertThat(spaces.get(1).storageClass()).isEqualTo("IA");
    }
}
