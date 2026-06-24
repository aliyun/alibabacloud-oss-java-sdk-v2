package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientMetaQueryTest extends TestBaseDataProcess {


    @Test
    public void testMetaQueryLifecycle() {
        OSSDataProcessClient client = getDataProcessClient();

        // 1. Get MetaQuery Status
        GetMetaQueryStatusResult statusResult = client.getMetaQueryStatus(
                GetMetaQueryStatusRequest.newBuilder()
                        .bucket(testBucketName)
                        .build());

        Assert.assertNotNull(statusResult);
        Assert.assertEquals(200, statusResult.statusCode());
        Assert.assertNotNull("metaQueryStatus should not be null", statusResult.metaQueryStatus());
        Assert.assertNotNull("state should not be null", statusResult.metaQueryStatus().state());

        // 2. Do MetaQuery (basic mode)
        DoMetaQueryResult doResult = client.doMetaQuery(
                DoMetaQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .mode("semantic")
                        .metaQueryBody(MetaQueryDoBody.newBuilder()
                                .query("{\"Field\": \"Size\", \"Value\": \"0\", \"Operation\": \"gt\"}")
                                .sort("Size")
                                .order("asc")
                                .maxResults(5)
                                .build())
                        .build());

        Assert.assertNotNull(doResult);
        Assert.assertEquals(200, doResult.statusCode());
        // files may or may not be returned depending on data
        if (doResult.files() != null && !doResult.files().isEmpty()) {
            File f = doResult.files().get(0);
            Assert.assertNotNull("URI should not be null", f.uri());
        }
    }

    @Test
    public void testDoMetaQueryWithAggregations() {
        OSSDataProcessClient client = getDataProcessClient();

        Aggregation agg = Aggregation.newBuilder()
                .field("Size")
                .operation("sum")
                .build();

        // Aggregations are NOT supported in semantic mode. Issuing such a request must
        // fail with a clear server-side error message. We assert the documented constraint
        // surfaces through the propagated exception chain.
        try {
            client.doMetaQuery(
                    DoMetaQueryRequest.newBuilder()
                            .bucket(testBucketName)
                            .mode("semantic")
                            .metaQueryBody(MetaQueryDoBody.newBuilder()
                                    .query("{\"Field\": \"Size\", \"Value\": \"0\", \"Operation\": \"gt\"}")
                                    .aggregations(Collections.singletonList(agg))
                                    .maxResults(5)
                                    .build())
                            .build());
            Assert.fail("Expected an exception: Aggregations is not supported in semantic mode.");
        } catch (Exception e) {
            String msg = collectMessages(e);
            assertThat(msg).contains("Aggregations is not supported in semantic mode.");
        }
    }

    /**
     * Concatenate the message of the exception and all its causes so that the
     * server-side error text (wrapped inside OperationException -> ServiceException)
     * can be asserted reliably.
     */
    private static String collectMessages(Throwable t) {
        StringBuilder sb = new StringBuilder();
        Throwable cur = t;
        while (cur != null) {
            if (cur.getMessage() != null) {
                sb.append(cur.getMessage()).append('\n');
            }
            cur = cur.getCause();
        }
        return sb.toString();
    }

    @Test
    public void testGetMetaQueryStatusFields() {
        OSSDataProcessClient client = getDataProcessClient();

        GetMetaQueryStatusResult statusResult = client.getMetaQueryStatus(
                GetMetaQueryStatusRequest.newBuilder()
                        .bucket(testBucketName)
                        .build());

        Assert.assertNotNull(statusResult);
        Assert.assertEquals(200, statusResult.statusCode());

        MetaQueryStatus status = statusResult.metaQueryStatus();
        Assert.assertNotNull(status);
        Assert.assertNotNull("state should not be null", status.state());
        Assert.assertNotNull("createTime should not be null", status.createTime());
    }
}
