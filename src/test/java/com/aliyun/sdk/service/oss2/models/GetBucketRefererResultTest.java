package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketReferer;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketRefererResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketRefererResult result = GetBucketRefererResult.newBuilder().build();
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

        List<String> referers = Arrays.asList(
                "http://www.aliyun.com",
                "https://www.aliyun.com",
                "http://www.*.com",
                "https://www.?.aliyuncs.com"
        );

        RefererList refererList = RefererList.newBuilder()
                .referers(referers)
                .build();

        List<String> blackListReferers = Arrays.asList(
                "http://www.refuse.com",
                "https://*.hack.com",
                "http://ban.*.com",
                "https://www.?.deny.com"
        );

        RefererBlacklist refererBlacklist = RefererBlacklist.newBuilder()
                .referers(blackListReferers)
                .build();

        RefererConfiguration refererConfiguration = RefererConfiguration.newBuilder()
                .allowEmptyReferer(false)
                .allowTruncateQueryString(true)
                .truncatePath(true)
                .refererList(refererList)
                .refererBlacklist(refererBlacklist)
                .build();

        GetBucketRefererResult result = GetBucketRefererResult.newBuilder()
                .headers(headers)
                .innerBody(refererConfiguration)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        RefererConfiguration resultConfiguration = result.refererConfiguration();
        assertThat(resultConfiguration).isNotNull();
        assertThat(resultConfiguration.allowEmptyReferer()).isEqualTo(false);
        assertThat(resultConfiguration.allowTruncateQueryString()).isEqualTo(true);
        assertThat(resultConfiguration.truncatePath()).isEqualTo(true);

        RefererList resultRefererList = resultConfiguration.refererList();
        assertThat(resultRefererList).isNotNull();
        assertThat(resultRefererList.referers()).containsExactly(
                "http://www.aliyun.com",
                "https://www.aliyun.com",
                "http://www.*.com",
                "https://www.?.aliyuncs.com"
        );

        RefererBlacklist resultRefererBlacklist = resultConfiguration.refererBlacklist();
        assertThat(resultRefererBlacklist).isNotNull();
        assertThat(resultRefererBlacklist.referers()).containsExactly(
                "http://www.refuse.com",
                "https://*.hack.com",
                "http://ban.*.com",
                "https://www.?.deny.com"
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        List<String> referers = Arrays.asList(
                "http://www.example1.com",
                "https://www.example2.com"
        );

        RefererList refererList = RefererList.newBuilder()
                .referers(referers)
                .build();

        RefererConfiguration refererConfiguration = RefererConfiguration.newBuilder()
                .allowEmptyReferer(true)
                .allowTruncateQueryString(false)
                .truncatePath(false)
                .refererList(refererList)
                .build();

        GetBucketRefererResult original = GetBucketRefererResult.newBuilder()
                .headers(headers)
                .innerBody(refererConfiguration)
                .build();

        GetBucketRefererResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        RefererConfiguration resultConfiguration = copy.refererConfiguration();
        assertThat(resultConfiguration).isNotNull();
        assertThat(resultConfiguration.allowEmptyReferer()).isEqualTo(true);
        assertThat(resultConfiguration.allowTruncateQueryString()).isEqualTo(false);
        assertThat(resultConfiguration.truncatePath()).isEqualTo(false);

        RefererList resultRefererList = resultConfiguration.refererList();
        assertThat(resultRefererList).isNotNull();
        assertThat(resultRefererList.referers()).containsExactly(
                "http://www.example1.com",
                "https://www.example2.com"
        );
    }

    @Test
    public void testXmlBuilder() {
        String xml =
                "<RefererConfiguration>\n" +
                "  <AllowEmptyReferer>false</AllowEmptyReferer>\n" +
                "  <AllowTruncateQueryString>true</AllowTruncateQueryString>\n" +
                "  <TruncatePath>true</TruncatePath>\n" +
                "  <RefererList>\n" +
                "    <Referer>http://www.aliyun.com</Referer>\n" +
                "    <Referer>https://www.aliyun.com</Referer>\n" +
                "    <Referer>http://www.*.com</Referer>\n" +
                "    <Referer>https://www.?.aliyuncs.com</Referer>\n" +
                "  </RefererList>\n" +
                "  <RefererBlacklist>\n" +
                "    <Referer>http://www.refuse.com</Referer>\n" +
                "    <Referer>https://*.hack.com</Referer>\n" +
                "    <Referer>http://ban.*.com</Referer>\n" +
                "    <Referer>https://www.?.deny.com</Referer>\n" +
                "  </RefererBlacklist>\n" +
                "</RefererConfiguration>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetBucketRefererResult result = SerdeBucketReferer.toGetBucketReferer(output);

        RefererConfiguration refererConfiguration = result.refererConfiguration();
        assertThat(refererConfiguration).isNotNull();
        assertThat(refererConfiguration.allowEmptyReferer()).isEqualTo(false);
        assertThat(refererConfiguration.allowTruncateQueryString()).isEqualTo(true);
        assertThat(refererConfiguration.truncatePath()).isEqualTo(true);

        RefererList refererList = refererConfiguration.refererList();
        assertThat(refererList).isNotNull();
        assertThat(refererList.referers()).containsExactly(
                "http://www.aliyun.com",
                "https://www.aliyun.com",
                "http://www.*.com",
                "https://www.?.aliyuncs.com"
        );

        RefererBlacklist refererBlacklist = refererConfiguration.refererBlacklist();
        assertThat(refererBlacklist).isNotNull();
        assertThat(refererBlacklist.referers()).containsExactly(
                "http://www.refuse.com",
                "https://*.hack.com",
                "http://ban.*.com",
                "https://www.?.deny.com"
        );
    }
}