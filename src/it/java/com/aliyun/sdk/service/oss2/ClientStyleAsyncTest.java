package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class ClientStyleAsyncTest extends TestBase {

    private String genStyleName() {
        long val = new Random().nextInt(5000);
        return "test-style-async-" + val;
    }

    @Test
    public void testStyleOperations() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();
        String styleName = genStyleName();

        try {
            // Put style
            Style style = Style.newBuilder()
                    .content("image/resize,w_200")
                    .build();

            PutStyleResult putResult = client.putStyleAsync(PutStyleRequest.newBuilder()
                    .bucket(bucketName)
                    .styleName(styleName)
                    .style(style)
                    .build()).get();
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            // Get style
            GetStyleResult getResult = client.getStyleAsync(GetStyleRequest.newBuilder()
                    .bucket(bucketName)
                    .styleName(styleName)
                    .build()).get();
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.style());
            Assert.assertEquals("image/resize,w_200", getResult.style().content());

            // List styles
            ListStyleResult listResult = client.listStyleAsync(ListStyleRequest.newBuilder()
                    .bucket(bucketName)
                    .build()).get();
            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            Assert.assertNotNull(listResult.styleList());
            Assert.assertNotNull(listResult.styleList().styles());

            // Check if our style is in the list
            boolean found = false;
            if (listResult.styleList().styles() != null) {
                for (StyleInfo styleInfo : listResult.styleList().styles()) {
                    if (styleInfo.name().equals(styleName)) {
                        found = true;
                        break;
                    }
                }
            }
            Assert.assertTrue(found);

        } finally {
            // Clean up
            try {
                client.deleteStyleAsync(DeleteStyleRequest.newBuilder()
                        .bucket(bucketName)
                        .styleName(styleName)
                        .build()).get();
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        }
    }
}