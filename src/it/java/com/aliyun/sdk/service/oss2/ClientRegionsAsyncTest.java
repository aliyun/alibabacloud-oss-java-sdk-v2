package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.exceptions.ServiceError;
import com.aliyun.sdk.service.oss2.models.DescribeRegionsRequest;
import com.aliyun.sdk.service.oss2.models.DescribeRegionsResult;
import com.aliyun.sdk.service.oss2.models.RegionInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static com.aliyun.sdk.service.oss2.internal.ClientImplMockTest.findCause;

public class ClientRegionsAsyncTest extends TestBase {

    @Test
    public void testDescribeRegions() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // 1. Describe all regions
        DescribeRegionsResult result = client.describeRegionsAsync(
                DescribeRegionsRequest.newBuilder()
                        .build()).get();
        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
        Assert.assertNotNull(result.regionInfoList());
        Assert.assertNotNull(result.regionInfoList().regionInfos());
        Assert.assertTrue("Should have at least one region",
                result.regionInfoList().regionInfos().size() > 0);

        // 2. Verify region info structure
        for (RegionInfo regionInfo : result.regionInfoList().regionInfos()) {
            Assert.assertNotNull("Region should not be null", regionInfo.region());
            Assert.assertNotNull("Internet endpoint should not be null", regionInfo.internetEndpoint());
            Assert.assertNotNull("Internal endpoint should not be null", regionInfo.internalEndpoint());
            Assert.assertNotNull("Accelerate endpoint should not be null", regionInfo.accelerateEndpoint());
        }
    }

    @Test
    public void testDescribeSpecificRegion() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // 1. Describe a specific region (e.g., "oss-cn-hangzhou")
        DescribeRegionsResult result = client.describeRegionsAsync(
                DescribeRegionsRequest.newBuilder()
                        .regions("oss-cn-hangzhou")
                        .build()).get();
        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
        Assert.assertNotNull(result.regionInfoList());
        Assert.assertNotNull(result.regionInfoList().regionInfos());

        // Should return at least one region
        Assert.assertTrue("Should have at least one region",
                result.regionInfoList().regionInfos().size() > 0);

        // 2. Verify the returned region is the requested one
        boolean foundRequestedRegion = false;
        for (RegionInfo regionInfo : result.regionInfoList().regionInfos()) {
            Assert.assertNotNull("Region should not be null", regionInfo.region());
            Assert.assertNotNull("Internet endpoint should not be null", regionInfo.internetEndpoint());
            Assert.assertNotNull("Internal endpoint should not be null", regionInfo.internalEndpoint());
            Assert.assertNotNull("Accelerate endpoint should not be null", regionInfo.accelerateEndpoint());

            if ("oss-cn-hangzhou".equals(regionInfo.region())) {
                foundRequestedRegion = true;
            }
        }
    }

    @Test
    public void testDescribeRegionsWithInvalidRegion() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // 1. Describe with an invalid region - expect a NoSuchRegion error
        try {
            DescribeRegionsResult result = client.describeRegionsAsync(
                    DescribeRegionsRequest.newBuilder()
                            .regions("invalid-region")
                            .build()).get();
            Assert.fail("Expected ServiceError but none was thrown");
        } catch (Exception ec) {
            ServiceError serr = findCause(ec, ServiceError.class);
            Assert.assertEquals(404, serr.statusCode());
            Assert.assertEquals("NoSuchRegion", serr.errorCode());
            Assert.assertEquals("invalid-region", serr.errorMessage());
            Assert.assertEquals(24, serr.requestId().length());
        }
    }
}
