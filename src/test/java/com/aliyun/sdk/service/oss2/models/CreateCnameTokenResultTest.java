package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketCname;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateCnameTokenResultTest {

    @Test
    public void testEmptyBuilder() {
        CreateCnameTokenResult result = CreateCnameTokenResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        CnameToken cnameToken = CnameToken.newBuilder()
                .bucket("examplebucket")
                .cname("example.com")
                .token("be1d49d863dea9ffeff3df7d6455****")
                .expireTime("Wed, 23 Feb 2022 21:16:37 GMT")
                .build();

        CreateCnameTokenResult result = CreateCnameTokenResult.newBuilder()
                .headers(headers)
                .innerBody(cnameToken)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.cnameToken()).isEqualTo(cnameToken);
        
        CnameToken token = result.cnameToken();
        assertThat(token.bucket()).isEqualTo("examplebucket");
        assertThat(token.cname()).isEqualTo("example.com");
        assertThat(token.token()).isEqualTo("be1d49d863dea9ffeff3df7d6455****");
        assertThat(token.expireTime()).isEqualTo("Wed, 23 Feb 2022 21:16:37 GMT");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210"
        );

        CnameToken cnameToken = CnameToken.newBuilder()
                .bucket("testbucket")
                .cname("test.example.com")
                .token("abcd1234efgh5678ijkl90****")
                .expireTime("Thu, 24 Feb 2022 10:30:45 GMT")
                .build();

        CreateCnameTokenResult original = CreateCnameTokenResult.newBuilder()
                .headers(headers)
                .innerBody(cnameToken)
                .build();

        CreateCnameTokenResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.cnameToken()).isEqualTo(cnameToken);
        
        CnameToken token = copy.cnameToken();
        assertThat(token.bucket()).isEqualTo("testbucket");
        assertThat(token.cname()).isEqualTo("test.example.com");
        assertThat(token.token()).isEqualTo("abcd1234efgh5678ijkl90****");
        assertThat(token.expireTime()).isEqualTo("Thu, 24 Feb 2022 10:30:45 GMT");
    }

    @Test
    public void testXmlBuilder() throws JsonProcessingException {
        String xml =
                "<CnameToken>\n" +
                "    <Bucket>examplebucket</Bucket>\n" +
                "    <Cname>example.com</Cname>\n" +
                "    <Token>be1d49d863dea9ffeff3df7d6455****</Token>\n" +
                "    <ExpireTime>Wed, 23 Feb 2022 21:16:37 GMT</ExpireTime>\n" +
                "</CnameToken>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        CreateCnameTokenResult result = SerdeBucketCname.toCreateCnameToken(output);

        CnameToken cnameToken = result.cnameToken();
        assertThat(cnameToken.bucket()).isEqualTo("examplebucket");
        assertThat(cnameToken.cname()).isEqualTo("example.com");
        assertThat(cnameToken.token()).isEqualTo("be1d49d863dea9ffeff3df7d6455****");
        assertThat(cnameToken.expireTime()).isEqualTo("Wed, 23 Feb 2022 21:16:37 GMT");
    }
}