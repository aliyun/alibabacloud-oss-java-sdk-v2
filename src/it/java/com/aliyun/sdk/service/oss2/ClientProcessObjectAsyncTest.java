package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.models.internal.ImageProcessJson;
import com.aliyun.sdk.service.oss2.transport.ByteArrayBinaryData;
import org.junit.Assert;
import org.junit.Test;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientProcessObjectAsyncTest extends TestBase {

    @Test
    public void testProcessObjectAsync() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = genObjectName() + ".png";
        String targetImage = genObjectName() + "-processed.png";

        // Create a simple test image
        byte[] imageContent = createTestImage(200, 200);

        // Upload a test image object
        PutObjectResult putResult = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(new ByteArrayBinaryData(imageContent))
                .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // Process the object with image resize operation and save to new object
        String style = "image/resize,m_fixed,w_100,h_100";
        String targetBucketBase64 = Base64.getEncoder().encodeToString(bucketName.getBytes());
        String targetKeyBase64 = Base64.getEncoder().encodeToString(targetImage.getBytes());
        String process = String.format("%s|sys/saveas,o_%s,b_%s", style, targetKeyBase64, targetBucketBase64);

        ProcessObjectResult processResult = client.processObjectAsync(ProcessObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .process(process)
                .build()).get();
        Assert.assertNotNull(processResult);
        Assert.assertEquals(200, processResult.statusCode());

        ImageProcessJson imageProcess = processResult.imageProcessResult();
        Assert.assertNotNull(imageProcess);
        assertThat(imageProcess.bucket()).isEqualTo(bucketName);
        assertThat(imageProcess.object()).isNotNull();
        assertThat(imageProcess.status()).isEqualTo("OK");
        assertThat(imageProcess.fileSize()).isNotNull();

        // Verify that the processed object exists
        HeadObjectResult headResult = client.headObjectAsync(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(targetImage)
                .build()).get();
        Assert.assertNotNull(headResult);
        Assert.assertEquals(200, headResult.statusCode());
        Assert.assertNotNull(headResult.contentLength());

        // Clean up
        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build()).get();

        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(targetImage)
                .build()).get();
    }

    private String genObjectName() {
        long ticks = new Date().getTime() / 1000;
        long val = new Random().nextInt(5000);
        return OJBJECT_NAME_PREFIX + ticks + "-" + val;
    }

    private byte[] createTestImage(int width, int height) throws Exception {
        // Create a buffered image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Draw a simple pattern
        Graphics2D g2d = image.createGraphics();

        // Fill background with white color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Draw a red rectangle
        g2d.setColor(Color.RED);
        g2d.fillRect(10, 10, width - 20, height - 20);

        // Draw a blue circle
        g2d.setColor(Color.BLUE);
        g2d.fillOval(30, 30, width - 60, height - 60);

        // Draw some text
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        String text = "OSS Test Image";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (width - fm.stringWidth(text)) / 2;
        int y = height / 2;
        g2d.drawString(text, x, y);

        g2d.dispose();

        // Convert to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();

        return imageBytes;
    }
}
