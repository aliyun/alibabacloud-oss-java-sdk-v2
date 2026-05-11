package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketStyle;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class ListStyleResultTest {

    @Test
    public void testEmptyBuilder() {
        ListStyleResult result = ListStyleResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Date", "Wed, 20 May 2020 12:07:15 GMT"
        );

        ListStyleResult result = ListStyleResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("Date")).isEqualTo("Wed, 20 May 2020 12:07:15 GMT");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "Date", "Thu, 21 May 2020 12:08:04 GMT"
        );

        ListStyleResult original = ListStyleResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        ListStyleResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("Date")).isEqualTo("Thu, 21 May 2020 12:08:04 GMT");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<StyleList>\n" +
                " <Style>\n" +
                "  <Name>imagestyle</Name>\n" +
                "  <Content>image/resize,p_50</Content>\n" +
                "  <Category>image</Category>\n" +
                "  <CreateTime>Wed, 20 May 2020 12:07:15 GMT</CreateTime>\n" +
                "  <LastModifyTime>Wed, 21 May 2020 12:07:15 GMT</LastModifyTime>\n" +
                " </Style>\n" +
                " <Style>\n" +
                "  <Name>imagestyle1</Name>\n" +
                "  <Content>image/resize,w_200</Content>\n" +
                "  <Category>image</Category>\n" +
                "  <CreateTime>Wed, 20 May 2020 12:08:04 GMT</CreateTime>\n" +
                "  <LastModifyTime>Wed, 21 May 2020 12:08:04 GMT</LastModifyTime>\n" +
                " </Style>\n" +
                " <Style>\n" +
                "  <Name>imagestyle2</Name>\n" +
                "  <Content>image/resize,w_300</Content>\n" +
                "  <Category>image</Category>\n" +
                "  <CreateTime>Fri, 12 Mar 2021 06:19:13 GMT</CreateTime>\n" +
                "  <LastModifyTime>Fri, 13 Mar 2021 06:27:21 GMT</LastModifyTime>\n" +
                " </Style>\n" +
                "</StyleList>";

        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Date", "Wed, 20 May 2020 12:07:15 GMT"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        ListStyleResult result = SerdeBucketStyle.toListStyle(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("Date")).isEqualTo("Wed, 20 May 2020 12:07:15 GMT");
        assertThat(result.styleList()).isNotNull();
        assertThat(result.styleList().styles()).isNotNull();
        assertThat(result.styleList().styles().size()).isEqualTo(3);

        List<StyleInfo> styles = result.styleList().styles();
        assertThat(styles.get(0).name()).isEqualTo("imagestyle");
        assertThat(styles.get(0).content()).isEqualTo("image/resize,p_50");
        assertThat(styles.get(0).category()).isEqualTo("image");
        assertThat(styles.get(0).createTime()).isEqualTo("Wed, 20 May 2020 12:07:15 GMT");
        assertThat(styles.get(0).lastModifyTime()).isEqualTo("Wed, 21 May 2020 12:07:15 GMT");

        assertThat(styles.get(1).name()).isEqualTo("imagestyle1");
        assertThat(styles.get(1).content()).isEqualTo("image/resize,w_200");
        assertThat(styles.get(1).category()).isEqualTo("image");
        assertThat(styles.get(1).createTime()).isEqualTo("Wed, 20 May 2020 12:08:04 GMT");
        assertThat(styles.get(1).lastModifyTime()).isEqualTo("Wed, 21 May 2020 12:08:04 GMT");

        assertThat(styles.get(2).name()).isEqualTo("imagestyle2");
        assertThat(styles.get(2).content()).isEqualTo("image/resize,w_300");
        assertThat(styles.get(2).category()).isEqualTo("image");
        assertThat(styles.get(2).createTime()).isEqualTo("Fri, 12 Mar 2021 06:19:13 GMT");
        assertThat(styles.get(2).lastModifyTime()).isEqualTo("Fri, 13 Mar 2021 06:27:21 GMT");
    }
}