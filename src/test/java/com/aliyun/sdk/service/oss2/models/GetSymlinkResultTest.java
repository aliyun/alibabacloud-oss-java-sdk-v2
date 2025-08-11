package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectSymlink;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetSymlinkResultTest {

    @Test
    public void testEmptyBuilder() {
        GetSymlinkResult result = GetSymlinkResult.newBuilder().build();
        assertThat(result).isNotNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "x-oss-symlink-target", "target-object.txt",
                "x-oss-version-id", "CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl"
        );

        GetSymlinkResult result = GetSymlinkResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("x-oss-symlink-target")).isEqualTo("target-object.txt");
        assertThat(result.headers().get("x-oss-version-id")).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.symlinkTarget()).isEqualTo("target-object.txt");
        assertThat(result.versionId()).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210",
                "x-oss-symlink-target", "another-target.txt",
                "x-oss-version-id", "CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl"
        );

        GetSymlinkResult original = GetSymlinkResult.newBuilder()
                .headers(headers)
                .status("Found")
                .statusCode(302)
                .build();

        GetSymlinkResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("x-oss-symlink-target")).isEqualTo("another-target.txt");
        assertThat(copy.headers().get("x-oss-version-id")).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(copy.status()).isEqualTo("Found");
        assertThat(copy.statusCode()).isEqualTo(302);
        assertThat(copy.symlinkTarget()).isEqualTo("another-target.txt");
        assertThat(copy.versionId()).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void testXmlBuilder() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeObjectSymlink.toGetSymlink(blankOutput);


        String xml = "<Error>\n" +
                "  <Code>NoSuchKey</Code>\n" +
                "  <Message>The specified key does not exist.</Message>\n" +
                "  <RequestId>req-111111111111111111</RequestId>\n" +
                "  <HostId>example-bucket.oss-cn-hangzhou.aliyuncs.com</HostId>\n" +
                "</Error>";

        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-111111111111111111",
                "x-oss-symlink-target", "xml-target.txt",
                "x-oss-version-id", "CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .body(BinaryData.fromString(xml))
                .build();

        GetSymlinkResult result = SerdeObjectSymlink.toGetSymlink(output);

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-111111111111111111");
        assertThat(result.headers().get("x-oss-symlink-target")).isEqualTo("xml-target.txt");
        assertThat(result.headers().get("x-oss-version-id")).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.symlinkTarget()).isEqualTo("xml-target.txt");
        assertThat(result.versionId()).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(result.requestId()).isEqualTo("req-111111111111111111");
    }
}
