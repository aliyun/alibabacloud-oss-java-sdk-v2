package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectTagging;
import com.aliyun.sdk.service.oss2.transform.SerdeRegion;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class DescribeRegionsResultTest {

    @Test
    public void testEmptyBuilder() {
        DescribeRegionsResult result = DescribeRegionsResult.newBuilder().build();
        assertThat(result).isNotNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        RegionInfo regionInfo1 = RegionInfo.newBuilder()
                .region("oss-cn-hangzhou")
                .internetEndpoint("oss-cn-hangzhou.aliyuncs.com")
                .internalEndpoint("oss-cn-hangzhou-internal.aliyuncs.com")
                .accelerateEndpoint("oss-accelerate.aliyuncs.com")
                .build();

        RegionInfo regionInfo2 = RegionInfo.newBuilder()
                .region("oss-cn-shanghai")
                .internetEndpoint("oss-cn-shanghai.aliyuncs.com")
                .internalEndpoint("oss-cn-shanghai-internal.aliyuncs.com")
                .accelerateEndpoint("oss-accelerate.aliyuncs.com")
                .build();

        RegionInfoList regionInfoList = RegionInfoList.newBuilder()
                .regionInfos(java.util.Arrays.asList(regionInfo1, regionInfo2))
                .build();

        DescribeRegionsResult result = DescribeRegionsResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .innerBody(regionInfoList)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
        assertThat(result.regionInfoList()).isNotNull();
        assertThat(result.regionInfoList().regionInfos()).hasSize(2);

        RegionInfo resultRegionInfo1 = result.regionInfoList().regionInfos().get(0);
        assertThat(resultRegionInfo1.region()).isEqualTo("oss-cn-hangzhou");
        assertThat(resultRegionInfo1.internetEndpoint()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(resultRegionInfo1.internalEndpoint()).isEqualTo("oss-cn-hangzhou-internal.aliyuncs.com");
        assertThat(resultRegionInfo1.accelerateEndpoint()).isEqualTo("oss-accelerate.aliyuncs.com");

        RegionInfo resultRegionInfo2 = result.regionInfoList().regionInfos().get(1);
        assertThat(resultRegionInfo2.region()).isEqualTo("oss-cn-shanghai");
        assertThat(resultRegionInfo2.internetEndpoint()).isEqualTo("oss-cn-shanghai.aliyuncs.com");
        assertThat(resultRegionInfo2.internalEndpoint()).isEqualTo("oss-cn-shanghai-internal.aliyuncs.com");
        assertThat(resultRegionInfo2.accelerateEndpoint()).isEqualTo("oss-accelerate.aliyuncs.com");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210"
        );

        RegionInfo regionInfo1 = RegionInfo.newBuilder()
                .region("oss-cn-hangzhou")
                .internetEndpoint("oss-cn-hangzhou.aliyuncs.com")
                .internalEndpoint("oss-cn-hangzhou-internal.aliyuncs.com")
                .accelerateEndpoint("oss-accelerate.aliyuncs.com")
                .build();

        RegionInfo regionInfo2 = RegionInfo.newBuilder()
                .region("oss-cn-shanghai")
                .internetEndpoint("oss-cn-shanghai.aliyuncs.com")
                .internalEndpoint("oss-cn-shanghai-internal.aliyuncs.com")
                .accelerateEndpoint("oss-accelerate.aliyuncs.com")
                .build();

        RegionInfoList regionInfoList = RegionInfoList.newBuilder()
                .regionInfos(java.util.Arrays.asList(regionInfo1, regionInfo2))
                .build();

        DescribeRegionsResult original = DescribeRegionsResult.newBuilder()
                .headers(headers)
                .status("Found")
                .statusCode(302)
                .innerBody(regionInfoList)
                .build();

        DescribeRegionsResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("Found");
        assertThat(copy.statusCode()).isEqualTo(302);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
        assertThat(copy.regionInfoList()).isNotNull();
        assertThat(copy.regionInfoList().regionInfos()).hasSize(2);

        RegionInfo resultRegionInfo1 = copy.regionInfoList().regionInfos().get(0);
        assertThat(resultRegionInfo1.region()).isEqualTo("oss-cn-hangzhou");
        assertThat(resultRegionInfo1.internetEndpoint()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(resultRegionInfo1.internalEndpoint()).isEqualTo("oss-cn-hangzhou-internal.aliyuncs.com");
        assertThat(resultRegionInfo1.accelerateEndpoint()).isEqualTo("oss-accelerate.aliyuncs.com");

        RegionInfo resultRegionInfo2 = copy.regionInfoList().regionInfos().get(1);
        assertThat(resultRegionInfo2.region()).isEqualTo("oss-cn-shanghai");
        assertThat(resultRegionInfo2.internetEndpoint()).isEqualTo("oss-cn-shanghai.aliyuncs.com");
        assertThat(resultRegionInfo2.internalEndpoint()).isEqualTo("oss-cn-shanghai-internal.aliyuncs.com");
        assertThat(resultRegionInfo2.accelerateEndpoint()).isEqualTo("oss-accelerate.aliyuncs.com");
    }

    @Test
    public void testXmlBuilder() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeRegion.toDescribeRegions(blankOutput);


        String xml = "<RegionInfoList>\n" +
                "  <RegionInfo>\n" +
                "     <Region>oss-cn-hangzhou</Region>\n" +
                "     <InternetEndpoint>oss-cn-hangzhou.aliyuncs.com</InternetEndpoint>\n" +
                "     <InternalEndpoint>oss-cn-hangzhou-internal.aliyuncs.com</InternalEndpoint>\n" +
                "     <AccelerateEndpoint>oss-accelerate.aliyuncs.com</AccelerateEndpoint>\n" +
                "  </RegionInfo>\n" +
                "  <RegionInfo>\n" +
                "     <Region>oss-cn-shanghai</Region>\n" +
                "     <InternetEndpoint>oss-cn-shanghai.aliyuncs.com</InternetEndpoint>\n" +
                "     <InternalEndpoint>oss-cn-shanghai-internal.aliyuncs.com</InternalEndpoint>\n" +
                "     <AccelerateEndpoint>oss-accelerate.aliyuncs.com</AccelerateEndpoint>\n" +
                "  </RegionInfo>\n" +
                "</RegionInfoList>";

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-111111111111111111"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .body(BinaryData.fromString(xml))
                .build();

        DescribeRegionsResult result = SerdeRegion.toDescribeRegions(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-111111111111111111");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-111111111111111111");
        assertThat(result.regionInfoList()).isNotNull();
        assertThat(result.regionInfoList().regionInfos()).hasSize(2);

        RegionInfo resultRegionInfo1 = result.regionInfoList().regionInfos().get(0);
        assertThat(resultRegionInfo1.region()).isEqualTo("oss-cn-hangzhou");
        assertThat(resultRegionInfo1.internetEndpoint()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(resultRegionInfo1.internalEndpoint()).isEqualTo("oss-cn-hangzhou-internal.aliyuncs.com");
        assertThat(resultRegionInfo1.accelerateEndpoint()).isEqualTo("oss-accelerate.aliyuncs.com");

        RegionInfo resultRegionInfo2 = result.regionInfoList().regionInfos().get(1);
        assertThat(resultRegionInfo2.region()).isEqualTo("oss-cn-shanghai");
        assertThat(resultRegionInfo2.internetEndpoint()).isEqualTo("oss-cn-shanghai.aliyuncs.com");
        assertThat(resultRegionInfo2.internalEndpoint()).isEqualTo("oss-cn-shanghai-internal.aliyuncs.com");
        assertThat(resultRegionInfo2.accelerateEndpoint()).isEqualTo("oss-accelerate.aliyuncs.com");
    }
}
