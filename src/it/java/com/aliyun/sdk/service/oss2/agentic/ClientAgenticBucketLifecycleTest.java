package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.Test;

/**
 * The two-phase deletion of an agentic bucket.
 * <p>
 * PutAgenticBucketStatus(Disabled) succeeds, but the bucket only becomes deletable roughly 24 hours
 * later, so the DeleteAgenticBucket that follows immediately is answered with
 * 409 / AgenticBucketNotReady. A create-then-delete round trip is therefore impossible within a
 * single run and the 409 is what gets asserted; the reaper reclaims the bucket in a later run.
 * <p>
 * The scenario runs against the bucket created by the {@code @BeforeClass} of this class only, so
 * that disabling it cannot disturb the scenarios of the sibling test classes: JUnit guarantees no
 * order between classes, and every class gets its own bucket.
 */
public class ClientAgenticBucketLifecycleTest extends TestBaseAgentic {

    @Test
    public void testDisableThenDeleteNotReady() {
        OSSAgenticBucketClient client = agenticClient;
        String bucket = agenticBucketName;

        PutAgenticBucketStatusResult putResult = client.putAgenticBucketStatus(
                PutAgenticBucketStatusRequest.newBuilder()
                        .bucket(bucket)
                        .agenticBucketStatus(AgenticBucketStatus.newBuilder()
                                .status("Disabled")
                                .build())
                        .build());
        Assert.assertEquals(200, putResult.statusCode());

        try {
            client.deleteAgenticBucket(DeleteAgenticBucketRequest.newBuilder().bucket(bucket).build());
            Assert.fail("Expected exception not thrown");
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertNotNull(serr);
            Assert.assertTrue("expected 409/AgenticBucketNotReady, got status=" + serr.statusCode()
                            + " code=" + serr.errorCode(),
                    serr.statusCode() == 409 || "AgenticBucketNotReady".equals(serr.errorCode()));
        }
    }
}
