package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketObjectFcAccessPoint;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WriteGetObjectResponseRequestTest {

    @Test
    public void testEmptyBuilder() {
        WriteGetObjectResponseRequest request = WriteGetObjectResponseRequest.newBuilder().build();
        assertThat(request).isNotNull();
    }

    @Test
    public void testFullBuilder() {
        WriteGetObjectResponseRequest request = WriteGetObjectResponseRequest.newBuilder()
                .requestRoute("test-route")
                .requestToken("test-token")
                .fwdStatus("200")
                .fwdHeaderAcceptRanges("bytes")
                .fwdHeaderCacheControl("no-cache")
                .fwdHeaderContentDisposition("attachment")
                .fwdHeaderContentEncoding("gzip")
                .fwdHeaderContentLanguage("en")
                .fwdHeaderContentRange("bytes 0-9/100")
                .fwdHeaderContentType("text/html")
                .fwdHeaderEtag("test-etag")
                .fwdHeaderExpires("Fri, 10 Nov 2023 03:17:58 GMT")
                .fwdHeaderLastModified("Tue, 10 Oct 2023 03:17:58 GMT")
                .body("test-body")
                .build();

        assertThat(request.requestRoute()).isEqualTo("test-route");
        assertThat(request.requestToken()).isEqualTo("test-token");
        assertThat(request.fwdStatus()).isEqualTo("200");
        assertThat(request.fwdHeaderAcceptRanges()).isEqualTo("bytes");
        assertThat(request.fwdHeaderCacheControl()).isEqualTo("no-cache");
        assertThat(request.fwdHeaderContentDisposition()).isEqualTo("attachment");
        assertThat(request.fwdHeaderContentEncoding()).isEqualTo("gzip");
        assertThat(request.fwdHeaderContentLanguage()).isEqualTo("en");
        assertThat(request.fwdHeaderContentRange()).isEqualTo("bytes 0-9/100");
        assertThat(request.fwdHeaderContentType()).isEqualTo("text/html");
        assertThat(request.fwdHeaderEtag()).isEqualTo("test-etag");
        assertThat(request.fwdHeaderExpires()).isEqualTo("Fri, 10 Nov 2023 03:17:58 GMT");
        assertThat(request.fwdHeaderLastModified()).isEqualTo("Tue, 10 Oct 2023 03:17:58 GMT");
        assertThat(request.body().toString()).isEqualTo("test-body");
    }

    @Test
    public void testToBuilderPreserveState() {
        WriteGetObjectResponseRequest original = WriteGetObjectResponseRequest.newBuilder()
                .requestRoute("original-route")
                .requestToken("original-token")
                .fwdStatus("404")
                .body("original-body")
                .build();

        WriteGetObjectResponseRequest copy = original.toBuilder().build();

        assertThat(copy.requestRoute()).isEqualTo("original-route");
        assertThat(copy.requestToken()).isEqualTo("original-token");
        assertThat(copy.fwdStatus()).isEqualTo("404");
        assertThat(copy.body().toString()).isEqualTo("original-body");
    }

    @Test
    public void xmlBuilder() {
        WriteGetObjectResponseRequest request = WriteGetObjectResponseRequest.newBuilder()
                .requestRoute("test-route")
                .requestToken("test-token")
                .fwdStatus("200")
                .fwdHeaderAcceptRanges("bytes")
                .fwdHeaderCacheControl("no-cache")
                .fwdHeaderContentDisposition("attachment")
                .fwdHeaderContentEncoding("gzip")
                .fwdHeaderContentLanguage("en")
                .fwdHeaderContentRange("bytes 0-9/100")
                .fwdHeaderContentType("text/html")
                .fwdHeaderEtag("test-etag")
                .fwdHeaderExpires("Fri, 10 Nov 2023 03:17:58 GMT")
                .fwdHeaderLastModified("Tue, 10 Oct 2023 03:17:58 GMT")
                .body("test-body")
                .build();

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromWriteGetObjectResponse(request);

        assertThat(input.opName()).isEqualTo("WriteGetObjectResponse");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.body().get().toString()).isEqualTo("test-body");
        assertThat(input.headers().get("x-oss-request-route")).isEqualTo("test-route");
        assertThat(input.headers().get("x-oss-request-token")).isEqualTo("test-token");
        assertThat(input.headers().get("x-oss-fwd-status")).isEqualTo("200");
        assertThat(input.headers().get("x-oss-fwd-header-Accept-Ranges")).isEqualTo("bytes");
        assertThat(input.headers().get("x-oss-fwd-header-Cache-Control")).isEqualTo("no-cache");
        assertThat(input.headers().get("x-oss-fwd-header-Content-Disposition")).isEqualTo("attachment");
        assertThat(input.headers().get("x-oss-fwd-header-Content-Encoding")).isEqualTo("gzip");
        assertThat(input.headers().get("x-oss-fwd-header-Content-Language")).isEqualTo("en");
        assertThat(input.headers().get("x-oss-fwd-header-Content-Range")).isEqualTo("bytes 0-9/100");
        assertThat(input.headers().get("x-oss-fwd-header-Content-Type")).isEqualTo("text/html");
        assertThat(input.headers().get("x-oss-fwd-header-ETag")).isEqualTo("test-etag");
        assertThat(input.headers().get("x-oss-fwd-header-Expires")).isEqualTo("Fri, 10 Nov 2023 03:17:58 GMT");
        assertThat(input.headers().get("x-oss-fwd-header-Last-Modified")).isEqualTo("Tue, 10 Oct 2023 03:17:58 GMT");
    }
}