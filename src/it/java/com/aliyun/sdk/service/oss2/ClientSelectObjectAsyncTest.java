package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.Assert;
import org.junit.Test;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

public class ClientSelectObjectAsyncTest extends TestBase {

    @Test
    public void testCSVSelectObjectMetaAsync() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = genObjectName() + ".csv";

        String csvContent = "name,school,company,age\n" +
                "Lora Francis,School,Staples Inc,27\n" +
                "Eleanor Little,School,\"Conectiv, Inc\",43\n" +
                "Rosie Hughes,School,Western Gas Resources Inc,44\n" +
                "Lawrence Ross,School,MetLife Inc.,24";
        PutObjectResult putResult = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(csvContent.getBytes(StandardCharsets.UTF_8)))
                .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        InputSerialization inputSerialization = InputSerialization.newBuilder()
                .compressionType("None")
                .csv(CSVInput.newBuilder()
                        .recordDelimiter(Base64.getEncoder().encodeToString("\n".getBytes()))
                        .fieldDelimiter(Base64.getEncoder().encodeToString(",".getBytes()))
                        .quoteCharacter(Base64.getEncoder().encodeToString("\"".getBytes()))
                        .build())
                .build();

        SelectMetaRequest metaRequest = SelectMetaRequest.newBuilder()
                .inputSerialization(inputSerialization)
                .overwriteIfExists(true)
                .build();

        CreateSelectObjectMetaResult metaResult = client.createSelectObjectMetaAsync(CreateSelectObjectMetaRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectMetaRequest(metaRequest)
                .build()).get();

        Assert.assertNotNull(metaResult);
        Assert.assertEquals(200, metaResult.statusCode());
        SelectMetaStatus status = metaResult.selectMetaStatus();
        Assert.assertNotNull(status);
        Assert.assertEquals(Long.valueOf(csvContent.getBytes(StandardCharsets.UTF_8).length), status.totalScannedBytes());
        Assert.assertEquals(Long.valueOf(1), status.splitsCount());
        Assert.assertEquals(Long.valueOf(5), status.rowsCount());
        Assert.assertEquals(Long.valueOf(4), status.colsCount());

        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build()).get();
    }

    @Test
    public void testCSVSelectObjectAsync() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = genObjectName() + ".csv";

        String csvContent = "name,school,company,age\n" +
                "Lora Francis,School,Staples Inc,27\n" +
                "#Lora Francis,School,Staples Inc,27\n" +
                "Eleanor Little,School,\"Conectiv, Inc\",43\n" +
                "Rosie Hughes,School,Western Gas Resources Inc,44\n" +
                "Lawrence Ross,School,MetLife Inc.,24\n";
        PutObjectResult putResult = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(csvContent.getBytes(StandardCharsets.UTF_8)))
                .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        client.createSelectObjectMetaAsync(CreateSelectObjectMetaRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectMetaRequest(SelectMetaRequest.newBuilder()
                        .inputSerialization(InputSerialization.newBuilder()
                                .compressionType("None")
                                .csv(CSVInput.newBuilder()
                                        .recordDelimiter(Base64.getEncoder().encodeToString("\n".getBytes()))
                                        .fieldDelimiter(Base64.getEncoder().encodeToString(",".getBytes()))
                                        .quoteCharacter(Base64.getEncoder().encodeToString("\"".getBytes()))
                                        .build())
                                .build())
                        .overwriteIfExists(true)
                        .build())
                .build()).get();

        CSVInput csvInput = CSVInput.newBuilder()
                .fileHeaderInfo("Ignore")
                .recordDelimiter(Base64.getEncoder().encodeToString("\n".getBytes()))
                .fieldDelimiter(Base64.getEncoder().encodeToString(",".getBytes()))
                .quoteCharacter(Base64.getEncoder().encodeToString("\"".getBytes()))
                .commentCharacter(Base64.getEncoder().encodeToString("#".getBytes()))
                .allowQuotedRecordDelimiter(true)
                .build();

        InputSerialization inputSerialization = InputSerialization.newBuilder()
                .compressionType("None")
                .csv(csvInput)
                .build();

        CSVOutput csvOutput = CSVOutput.newBuilder()
                .recordDelimiter(Base64.getEncoder().encodeToString("\n".getBytes()))
                .fieldDelimiter(Base64.getEncoder().encodeToString(",".getBytes()))
                .build();

        OutputSerialization outputSerialization = OutputSerialization.newBuilder()
                .csv(csvOutput)
                .keepAllColumns(true)
                .outputRawData(false)
                .enablePayloadCrc(true)
                .outputHeader(false)
                .build();

        SelectRequestOptions selectOptions = SelectRequestOptions.newBuilder()
                .skipPartialDataRecord(false)
                .build();

        SelectRequest selectRequest = SelectRequest.newBuilder()
                .expression(Base64.getEncoder().encodeToString("select * from ossobject".getBytes()))
                .inputSerialization(inputSerialization)
                .outputSerialization(outputSerialization)
                .options(selectOptions)
                .build();

        try (SelectObjectResult result = client.selectObjectAsync(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(selectRequest)
                .build()).get()) {

            Assert.assertNotNull(result);
            Assert.assertEquals(206, result.statusCode());
            Assert.assertNotNull(result.requestId());
            Assert.assertNotNull(result.body());
            Assert.assertEquals("false", result.headers().get("x-oss-select-output-raw"));

            byte[] buffer = new byte[1024];
            int bytesRead;
            int off = 0;
            while ((bytesRead = result.body().read()) != -1) {
                buffer[off++] = (byte) bytesRead;
            }
            Assert.assertTrue(off > 0);
        }

        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build()).get();
    }

    @Test
    public void testCSVSelectObject1Async() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = genObjectName() + ".csv";

        String csvContent = "name,school,company,age\r\n" +
                "Lora Francis,School A,Staples Inc,27\r\n" +
                "Eleanor Little,School B,\"Conectiv, Inc\",43\r\n" +
                "Rosie Hughes,School C,Western Gas Resources Inc,44\r\n" +
                "Lawrence Ross,School D,MetLife Inc.,24";
        PutObjectResult putResult = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(csvContent.getBytes(StandardCharsets.UTF_8)))
                .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        String expression = "select name from ossobject";

        InputSerialization inputSerialization = InputSerialization.newBuilder()
                .csv(CSVInput.newBuilder()
                        .fileHeaderInfo("Use")
                        .build())
                .build();

        OutputSerialization outputSerialization = OutputSerialization.newBuilder()
                .outputHeader(true)
                .build();

        SelectRequest selectRequest = SelectRequest.newBuilder()
                .expression(Base64.getEncoder().encodeToString(expression.getBytes()))
                .inputSerialization(inputSerialization)
                .outputSerialization(outputSerialization)
                .build();

        try (SelectObjectResult result = client.selectObjectAsync(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(selectRequest)
                .build()).get()) {

            Assert.assertNotNull(result);
            Assert.assertEquals(206, result.statusCode());
            Assert.assertEquals("false", result.headers().get("x-oss-select-output-raw"));

            byte[] buffer = new byte[1024];
            int bytesRead;
            int off = 0;
            while ((bytesRead = result.body().read()) != -1) {
                buffer[off++] = (byte) bytesRead;
            }
            Assert.assertTrue(off > 0);
        }

        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build()).get();
    }

    @Test
    public void testJSONSelectObjectMetaAsync() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = genObjectName() + ".json";

        String jsonContent = "{\n" +
                "\t\"name\": \"Lora Francis\",\n" +
                "\t\"age\": 27,\n" +
                "\t\"company\": \"Staples Inc\"\n" +
                "}\n" +
                "{\n" +
                "\t\"k2\": [-1, 79, 90],\n" +
                "\t\"k3\": {\n" +
                "\t\t\"k2\": 5,\n" +
                "\t\t\"k3\": 1,\n" +
                "\t\t\"k4\": 0\n" +
                "\t}\n" +
                "}\n" +
                "{\n" +
                "\t\"k1\": 1,\n" +
                "\t\"k2\": {\n" +
                "\t\t\"k2\": 5\n" +
                "\t},\n" +
                "\t\"k3\": []\n" +
                "}";
        PutObjectResult putResult = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(jsonContent.getBytes(StandardCharsets.UTF_8)))
                .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        InputSerialization inputSerialization = InputSerialization.newBuilder()
                .compressionType("None")
                .json(JSONInput.newBuilder()
                        .type("LINES")
                        .build())
                .build();

        SelectMetaRequest metaRequest = SelectMetaRequest.newBuilder()
                .inputSerialization(inputSerialization)
                .overwriteIfExists(true)
                .build();

        CreateSelectObjectMetaResult metaResult = client.createSelectObjectMetaAsync(CreateSelectObjectMetaRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectMetaRequest(metaRequest)
                .build()).get();

        Assert.assertNotNull(metaResult);
        Assert.assertEquals(200, metaResult.statusCode());
        SelectMetaStatus status = metaResult.selectMetaStatus();
        Assert.assertNotNull(status);
        Assert.assertEquals(Long.valueOf(jsonContent.getBytes(StandardCharsets.UTF_8).length), status.totalScannedBytes());
        Assert.assertEquals(Long.valueOf(1), status.splitsCount());
        Assert.assertEquals(Long.valueOf(3), status.rowsCount());
        Assert.assertNull(status.colsCount());

        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build()).get();
    }

    @Test
    public void testJSONSelectObjectAsync() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = genObjectName() + ".json";

        String jsonContent = "{\t\"name\":\"Eleanor Little\",\n\t\"age\":43,\n\t\"company\":\"Conectiv, Inc\"}\n{\t\"name\":\"Rosie Hughes\",\n\t\"age\":44,\n\t\"company\":\"Western Gas Resources Inc\"}\n";
        PutObjectResult putResult = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(jsonContent.getBytes(StandardCharsets.UTF_8)))
                .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        InputSerialization metaInputSerialization = InputSerialization.newBuilder()
                .compressionType("None")
                .json(JSONInput.newBuilder()
                        .type("LINES")
                        .build())
                .build();

        SelectMetaRequest metaRequest = SelectMetaRequest.newBuilder()
                .inputSerialization(metaInputSerialization)
                .overwriteIfExists(true)
                .build();

        CreateSelectObjectMetaResult metaResult = client.createSelectObjectMetaAsync(CreateSelectObjectMetaRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectMetaRequest(metaRequest)
                .build()).get();
        Assert.assertNotNull(metaResult);
        Assert.assertEquals(200, metaResult.statusCode());

        InputSerialization inputSerialization = InputSerialization.newBuilder()
                .compressionType("None")
                .json(JSONInput.newBuilder()
                        .type("LINES")
                        .parseJsonNumberAsString(true)
                        .build())
                .build();

        OutputSerialization outputSerialization = OutputSerialization.newBuilder()
                .outputRawData(false)
                .keepAllColumns(true)
                .enablePayloadCrc(true)
                .outputHeader(false)
                .build();

        SelectRequestOptions selectOptions = SelectRequestOptions.newBuilder()
                .skipPartialDataRecord(false)
                .build();

        SelectRequest selectRequest = SelectRequest.newBuilder()
                .expression(Base64.getEncoder().encodeToString("select * from ossobject as s where cast(s.age as int) > 40".getBytes()))
                .inputSerialization(inputSerialization)
                .outputSerialization(outputSerialization)
                .options(selectOptions)
                .build();

        try (SelectObjectResult result = client.selectObjectAsync(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(selectRequest)
                .build()).get()) {

            Assert.assertNotNull(result);
            Assert.assertEquals(206, result.statusCode());
            Assert.assertNotNull(result.requestId());
            Assert.assertNotNull(result.body());
            Assert.assertEquals("false", result.headers().get("x-oss-select-output-raw"));

            byte[] buffer = new byte[1024];
            int bytesRead;
            int off = 0;
            while ((bytesRead = result.body().read()) != -1) {
                buffer[off++] = (byte) bytesRead;
            }
            Assert.assertTrue(off > 0);
        }

        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build()).get();
    }

    @Test
    public void testJSONSelectObject1Async() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = genObjectName() + ".json";

        String jsonContent = "\n    {\n        \"name\": \"Lora Francis\",\n        \"age\": 27,\n        \"company\": \"Staples Inc\"\n    },\n    {\n        \"name\": \"Eleanor Little\",\n        \"age\": 43,\n        \"company\": \"Conectiv, Inc\"\n    },\n    {\n        \"name\": \"Rosie Hughes\",\n        \"age\": 44,\n        \"company\": \"Western Gas Resources Inc\"\n    },\n    {\n        \"name\": \"Lawrence Ross\",\n        \"age\": 24,\n        \"company\": \"MetLife Inc.\"\n    }\n    ";
        PutObjectResult putResult = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(jsonContent.getBytes(StandardCharsets.UTF_8)))
                .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        String expression = "select name from ossobject";

        InputSerialization inputSerialization = InputSerialization.newBuilder()
                .json(JSONInput.newBuilder()
                        .type("LINES")
                        .build())
                .build();

        OutputSerialization outputSerialization = OutputSerialization.newBuilder()
                .outputHeader(true)
                .build();

        SelectRequest selectRequest = SelectRequest.newBuilder()
                .expression(Base64.getEncoder().encodeToString(expression.getBytes()))
                .inputSerialization(inputSerialization)
                .outputSerialization(outputSerialization)
                .build();

        try (SelectObjectResult result = client.selectObjectAsync(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(selectRequest)
                .build()).get()) {

            Assert.assertNotNull(result);
            Assert.assertEquals(206, result.statusCode());
            Assert.assertEquals("false", result.headers().get("x-oss-select-output-raw"));

            byte[] buffer = new byte[1024];
            int bytesRead;
            int off = 0;
            while ((bytesRead = result.body().read()) != -1) {
                buffer[off++] = (byte) bytesRead;
            }
            Assert.assertTrue(off > 0);
        }

        client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build()).get();
    }

    private String genObjectName() {
        long ticks = new Date().getTime() / 1000;
        long val = new Random().nextInt(5000);
        return OJBJECT_NAME_PREFIX + ticks + "-" + val;
    }
}
