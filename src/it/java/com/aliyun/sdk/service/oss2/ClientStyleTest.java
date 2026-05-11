package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.Random;

public class ClientStyleTest extends TestBase {

    private String genStyleName() {
        long val = new Random().nextInt(5000);
        return "test-style-" + val;
    }

    @Test
    public void testStyleOperations() {
        OSSClient client = getDefaultClient();
        String styleName = genStyleName();

        try {
            // Put style
            Style style = Style.newBuilder()
                    .content("image/resize,w_200")
                    .build();

            PutStyleResult putResult = client.putStyle(PutStyleRequest.newBuilder()
                    .bucket(bucketName)
                    .styleName(styleName)
                    .style(style)
                    .build());
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            // Get style
            GetStyleResult getResult = client.getStyle(GetStyleRequest.newBuilder()
                    .bucket(bucketName)
                    .styleName(styleName)
                    .build());
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.style());
            Assert.assertEquals("image/resize,w_200", getResult.style().content());

            // List styles
            ListStyleResult listResult = client.listStyle(ListStyleRequest.newBuilder()
                    .bucket(bucketName)
                    .build());
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
                client.deleteStyle(DeleteStyleRequest.newBuilder()
                        .bucket(bucketName)
                        .styleName(styleName)
                        .build());
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        }
    }
}