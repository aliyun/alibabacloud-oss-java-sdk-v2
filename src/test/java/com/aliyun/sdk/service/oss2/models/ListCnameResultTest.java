package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketCname;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class ListCnameResultTest {

    @Test
    public void testEmptyBuilder() {
        ListCnameResult result = ListCnameResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        List<CnameInfo> cnames = Arrays.asList(
                CnameInfo.newBuilder()
                        .domain("example.com")
                        .lastModified("2021-09-15T02:35:07.000Z")
                        .status("Enabled")
                        .build(),
                CnameInfo.newBuilder()
                        .domain("example.org")
                        .lastModified("2021-09-15T02:34:58.000Z")
                        .status("Enabled")
                        .build()
        );

        ListCnameResult result = ListCnameResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ListCnameResult original = ListCnameResult.newBuilder()
                .headers(headers)
                .build();

        ListCnameResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
    }

    @Test
    public void testXmlBuilder() throws JsonProcessingException {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        ListCnameResult result = SerdeBucketCname.toListCname(blankOutput);
        assertThat(result.bucket()).isNull();
        assertThat(result.owner()).isNull();
        assertThat(result.cnames()).isEmpty();

        String xml =
                "<ListCnameResult>\n" +
                "  <Bucket>targetbucket</Bucket>\n" +
                "  <Owner>testowner</Owner>\n" +
                "  <Cname>\n" +
                "    <Domain>example.com</Domain>\n" +
                "    <LastModified>2021-09-15T02:35:07.000Z</LastModified>\n" +
                "    <Status>Enabled</Status>\n" +
                "    <Certificate>\n" +
                "      <Type>CAS</Type>\n" +
                "      <CertId>493****-cn-hangzhou</CertId>\n" +
                "      <Status>Enabled</Status>\n" +
                "      <CreationDate>Wed, 15 Sep 2021 02:35:06 GMT</CreationDate>\n" +
                "      <Fingerprint>DE:01:CF:EC:7C:A7:98:CB:D8:6E:FB:1D:97:EB:A9:64:1D:4E:**:**</Fingerprint>\n" +
                "      <ValidStartDate>Wed, 12 Apr 2023 10:14:51 GMT</ValidStartDate>\n" +
                "      <ValidEndDate>Mon, 4 May 2048 10:14:51 GMT</ValidEndDate>\n" +
                "    </Certificate>\n" +
                "  </Cname>\n" +
                "  <Cname>\n" +
                "    <Domain>example.org</Domain>\n" +
                "    <LastModified>2021-09-15T02:34:58.000Z</LastModified>\n" +
                "    <Status>Enabled</Status>\n" +
                "  </Cname>\n" +
                "  <Cname>\n" +
                "    <Domain>example.edu</Domain>\n" +
                "    <LastModified>2021-09-15T02:50:34.000Z</LastModified>\n" +
                "    <Status>Enabled</Status>\n" +
                "  </Cname>\n" +
                "</ListCnameResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        result = SerdeBucketCname.toListCname(output);

        assertThat(result.bucket()).isEqualTo("targetbucket");
        assertThat(result.owner()).isEqualTo("testowner");
        assertThat(result.cnames()).hasSize(3);

        CnameInfo firstCname = result.cnames().get(0);
        assertThat(firstCname.domain()).isEqualTo("example.com");
        assertThat(firstCname.lastModified()).isEqualTo("2021-09-15T02:35:07.000Z");
        assertThat(firstCname.status()).isEqualTo("Enabled");

        CnameCertificate certificate = firstCname.certificate();
        assertThat(certificate).isNotNull();
        assertThat(certificate.type()).isEqualTo("CAS");
        assertThat(certificate.certId()).isEqualTo("493****-cn-hangzhou");
        assertThat(certificate.status()).isEqualTo("Enabled");
        assertThat(certificate.creationDate()).isEqualTo("Wed, 15 Sep 2021 02:35:06 GMT");
        assertThat(certificate.fingerprint()).isEqualTo("DE:01:CF:EC:7C:A7:98:CB:D8:6E:FB:1D:97:EB:A9:64:1D:4E:**:**");
        assertThat(certificate.validStartDate()).isEqualTo("Wed, 12 Apr 2023 10:14:51 GMT");
        assertThat(certificate.validEndDate()).isEqualTo("Mon, 4 May 2048 10:14:51 GMT");

        CnameInfo secondCname = result.cnames().get(1);
        assertThat(secondCname.domain()).isEqualTo("example.org");
        assertThat(secondCname.lastModified()).isEqualTo("2021-09-15T02:34:58.000Z");
        assertThat(secondCname.status()).isEqualTo("Enabled");
        assertThat(secondCname.certificate()).isNull();

        CnameInfo thirdCname = result.cnames().get(2);
        assertThat(thirdCname.domain()).isEqualTo("example.edu");
        assertThat(thirdCname.lastModified()).isEqualTo("2021-09-15T02:50:34.000Z");
        assertThat(thirdCname.status()).isEqualTo("Enabled");
        assertThat(thirdCname.certificate()).isNull();
    }
}