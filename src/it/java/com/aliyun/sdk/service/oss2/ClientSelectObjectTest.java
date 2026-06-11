package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class ClientSelectObjectTest extends TestBase {

    @Test
    public void testCSVSelectObjectMeta() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".csv";

        String csvContent = "name,school,company,age\n" +
                "Lora Francis,School,Staples Inc,27\n" +
                "Eleanor Little,School,\"Conectiv, Inc\",43\n" +
                "Rosie Hughes,School,Western Gas Resources Inc,44\n" +
                "Lawrence Ross,School,MetLife Inc.,24";
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(csvContent.getBytes(StandardCharsets.UTF_8)))
                .build());
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

        CreateSelectObjectMetaResult metaResult = client.createSelectObjectMeta(CreateSelectObjectMetaRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectMetaRequest(metaRequest)
                .process("csv/meta")
                .build());

        Assert.assertNotNull(metaResult);
        Assert.assertEquals(200, metaResult.statusCode());
        SelectObjectMeta status = metaResult.selectObjectMeta();
        Assert.assertNotNull(status);
        Assert.assertEquals(Long.valueOf(csvContent.getBytes(StandardCharsets.UTF_8).length), status.totalScanned());
        Assert.assertEquals(Long.valueOf(1), status.splitsCount());
        Assert.assertEquals(Long.valueOf(5), status.rowsCount());
        Assert.assertEquals(Long.valueOf(4), status.columnsCount());

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testCSVSelectObjectMetaException() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = "get-select-object-metadata-invalid";

        String csvContent = "name,school,company,age\\n\" +\n" +
                "                \"Laura Rodriquez,School,Triad Hospitals Inc,39\\n\" +\n" +
                "                \"\\\",,,44\\n\" +\n" +
                "                \"Nora Cannon,School,Reader's Digest Association Inc.,30\\n\" +\n" +
                "                \"Louisa Weaver,School,Trinity Industries Inc,21\\n\" +\n" +
                "                \"Howard Hart,School,\\\"EOG Resources, Inc.\\\",35\\n\" +\n" +
                "                \"\\\"Ola \\\"\\\"\\\"\\\"Miller\\\",School,Trump Hotels & Casino Resorts Inc.,20";
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(csvContent.getBytes(StandardCharsets.UTF_8)))
                .build());
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

        try {
        CreateSelectObjectMetaResult metaResult = client.createSelectObjectMeta(CreateSelectObjectMetaRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectMetaRequest(metaRequest)
                .process("csv/meta")
                .build());
            Assert.fail("invalid object for get select object metadata");
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("The line is invalid"));
        }
    }

    @Test
    public void testCSVSelectObject() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".csv";

        String csvContent = "name,school,company,age\n" +
                "Lora Francis,School,Staples Inc,27\n" +
                "#Lora Francis,School,Staples Inc,27\n" +
                "Eleanor Little,School,\"Conectiv, Inc\",43\n" +
                "Rosie Hughes,School,Western Gas Resources Inc,44\n" +
                "Lawrence Ross,School,MetLife Inc.,24\n";
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(csvContent.getBytes(StandardCharsets.UTF_8)))
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        client.createSelectObjectMeta(CreateSelectObjectMetaRequest.newBuilder()
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
                .process("csv/meta")
                .build());

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

        try (SelectObjectResult result = client.selectObject(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(selectRequest)
                .process("csv/select")
                .build())) {

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

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testCSVSelectObject1() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".csv";

        String csvContent = "name,school,company,age\r\n" +
                "Lora Francis,School A,Staples Inc,27\r\n" +
                "Eleanor Little,School B,\"Conectiv, Inc\",43\r\n" +
                "Rosie Hughes,School C,Western Gas Resources Inc,44\r\n" +
                "Lawrence Ross,School D,MetLife Inc.,24";
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(csvContent.getBytes(StandardCharsets.UTF_8)))
                .build());
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

        try (SelectObjectResult result = client.selectObject(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(selectRequest)
                .process("csv/select")
                .build())) {

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

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testJSONSelectObjectMeta() throws Exception {
        OSSClient client = getDefaultClient();
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
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(jsonContent.getBytes(StandardCharsets.UTF_8)))
                .build());
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

        CreateSelectObjectMetaResult metaResult = client.createSelectObjectMeta(CreateSelectObjectMetaRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectMetaRequest(metaRequest)
                .process("json/meta")
                .build());

        Assert.assertNotNull(metaResult);
        Assert.assertEquals(200, metaResult.statusCode());
        SelectObjectMeta status = metaResult.selectObjectMeta();
        Assert.assertNotNull(status);
        Assert.assertEquals(Long.valueOf(jsonContent.getBytes(StandardCharsets.UTF_8).length), status.totalScanned());
        Assert.assertEquals(Long.valueOf(1), status.splitsCount());
        Assert.assertEquals(Long.valueOf(3), status.rowsCount());
        Assert.assertNull(status.columnsCount());


        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testJSONSelectObject() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".json";

        String jsonContent = "{\t\"name\":\"Eleanor Little\",\n\t\"age\":43,\n\t\"company\":\"Conectiv, Inc\"}\n{\t\"name\":\"Rosie Hughes\",\n\t\"age\":44,\n\t\"company\":\"Western Gas Resources Inc\"}\n";
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(jsonContent.getBytes(StandardCharsets.UTF_8)))
                .build());
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

        CreateSelectObjectMetaResult metaResult = client.createSelectObjectMeta(CreateSelectObjectMetaRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectMetaRequest(metaRequest)
                .process("json/meta")
                .build());
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

        try (SelectObjectResult result = client.selectObject(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(selectRequest)
                .process("json/select")
                .build())) {

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

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testJSONSelectObject1() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".json";

        String jsonContent = "\n    {\n        \"name\": \"Lora Francis\",\n        \"age\": 27,\n        \"company\": \"Staples Inc\"\n    },\n    {\n        \"name\": \"Eleanor Little\",\n        \"age\": 43,\n        \"company\": \"Conectiv, Inc\"\n    },\n    {\n        \"name\": \"Rosie Hughes\",\n        \"age\": 44,\n        \"company\": \"Western Gas Resources Inc\"\n    },\n    {\n        \"name\": \"Lawrence Ross\",\n        \"age\": 24,\n        \"company\": \"MetLife Inc.\"\n    }\n    ";
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(jsonContent.getBytes(StandardCharsets.UTF_8)))
                .build());
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

        try (SelectObjectResult result = client.selectObject(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(selectRequest)
                .process("json/select")
                .build())) {

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

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testCSVSelectObjectRaw() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".csv";

        String csvContent = "name,school,company,age\r\n" +
                "Lora Francis,School A,Staples Inc,27\r\n" +
                "Eleanor Little,School B,\"Conectiv, Inc\",43\r\n" +
                "Rosie Hughes,School C,Western Gas Resources Inc,44\r\n" +
                "Lawrence Ross,School D,MetLife Inc.,24\r\n";
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(csvContent.getBytes(StandardCharsets.UTF_8)))
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        SelectRequest selectRequest = SelectRequest.newBuilder()
                .expression(Base64.getEncoder().encodeToString("select * from ossobject".getBytes()))
                .inputSerialization(InputSerialization.newBuilder()
                        .csv(CSVInput.newBuilder()
                                .fileHeaderInfo("Use")
                                .recordDelimiter(Base64.getEncoder().encodeToString("\r\n".getBytes()))
                                .fieldDelimiter(Base64.getEncoder().encodeToString(",".getBytes()))
                                .quoteCharacter(Base64.getEncoder().encodeToString("\"".getBytes()))
                                .commentCharacter(Base64.getEncoder().encodeToString("#".getBytes()))
                                .build())
                        .build())
                .outputSerialization(OutputSerialization.newBuilder()
                        .outputRawData(true)
                        .enablePayloadCrc(false)
                        .build())
                .build();

        try (SelectObjectResult result = client.selectObject(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(selectRequest)
                .process("csv/select")
                .build())) {

            Assert.assertNotNull(result);
            Assert.assertEquals(206, result.statusCode());
            Assert.assertEquals("true", result.headers().get("x-oss-select-output-raw"));

            byte[] buffer = new byte[4096];
            java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
            int n;
            while ((n = result.body().read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            String body = out.toString("UTF-8");
            Assert.assertTrue(body.contains("Lora Francis"));
            Assert.assertTrue(body.contains("Lawrence Ross"));
        }

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testCSVSelectObjectRange() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".csv";

        String csvContent = "name,school,company,age\r\n" +
                "Lora Francis,School A,Staples Inc,27\r\n" +
                "Eleanor Little,School B,\"Conectiv, Inc\",43\r\n" +
                "Rosie Hughes,School C,Western Gas Resources Inc,44\r\n" +
                "Lawrence Ross,School D,MetLife Inc.,24\r\n";
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(csvContent.getBytes(StandardCharsets.UTF_8)))
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        CreateSelectObjectMetaResult metaResult = client.createSelectObjectMeta(
                CreateSelectObjectMetaRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .selectMetaRequest(SelectMetaRequest.newBuilder()
                                .inputSerialization(InputSerialization.newBuilder()
                                        .csv(CSVInput.newBuilder()
                                                .fileHeaderInfo("Use")
                                                .recordDelimiter(Base64.getEncoder().encodeToString("\r\n".getBytes()))
                                                .build())
                                        .build())
                                .build())
                        .process("csv/meta")
                        .build());
        Assert.assertNotNull(metaResult);
        Assert.assertEquals(200, metaResult.statusCode());
        Assert.assertEquals(Long.valueOf(4), metaResult.selectObjectMeta().columnsCount());

        // line-range query
        try (SelectObjectResult result = client.selectObject(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(SelectRequest.newBuilder()
                        .expression(Base64.getEncoder().encodeToString("select * from ossobject".getBytes()))
                        .inputSerialization(InputSerialization.newBuilder()
                                .csv(CSVInput.newBuilder()
                                        .fileHeaderInfo("Use")
                                        .recordDelimiter(Base64.getEncoder().encodeToString("\r\n".getBytes()))
                                        .range("line-range=1-3")
                                        .build())
                                .build())
                        .build())
                .process("csv/select")
                .build())) {

            Assert.assertNotNull(result);
            Assert.assertEquals(206, result.statusCode());

            byte[] buffer = new byte[4096];
            java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
            int n;
            while ((n = result.body().read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            String body = out.toString("UTF-8");
            Assert.assertTrue(body.contains("Lora Francis"));
            Assert.assertTrue(body.contains("Rosie Hughes"));
        }

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testCSVSelectObjectSkipPartialData() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".csv";

        String csvContent = "name,school,company,age\r\n" +
                "Lora Francis,School A,Staples Inc,27\r\n" +
                "Eleanor Little,School B,\"Conectiv, Inc\",43\r\n" +
                "Rosie Hughes,School C,Western Gas Resources Inc,44\r\n" +
                "Lawrence Ross,School D,MetLife Inc.,24\r\n" +
                "\r\n1,,3\r\n4,,6\r\n7,8,9\r\n";
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(csvContent.getBytes(StandardCharsets.UTF_8)))
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // maxSkippedRecordsAllowed=5, expect success
        try (SelectObjectResult result = client.selectObject(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(SelectRequest.newBuilder()
                        .expression(Base64.getEncoder().encodeToString("select _1,_2,_3,_4 from ossobject".getBytes()))
                        .inputSerialization(InputSerialization.newBuilder()
                                .csv(CSVInput.newBuilder()
                                        .fileHeaderInfo("Use")
                                        .recordDelimiter(Base64.getEncoder().encodeToString("\r\n".getBytes()))
                                        .fieldDelimiter(Base64.getEncoder().encodeToString(",".getBytes()))
                                        .quoteCharacter(Base64.getEncoder().encodeToString("\"".getBytes()))
                                        .commentCharacter(Base64.getEncoder().encodeToString("#".getBytes()))
                                        .build())
                                .build())
                        .options(SelectRequestOptions.newBuilder()
                                .skipPartialDataRecord(true)
                                .maxSkippedRecordsAllowed(5L)
                                .build())
                        .build())
                .process("csv/select")
                .build())) {

            Assert.assertNotNull(result);
            Assert.assertEquals(206, result.statusCode());

            byte[] buffer = new byte[4096];
            java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
            int n;
            while ((n = result.body().read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            String body = out.toString("UTF-8");
            Assert.assertTrue(body.contains("Lora Francis"));
            Assert.assertTrue(body.contains("Lawrence Ross"));
        }

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testCSVSelectObjectMetaDelimiters() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".csv";

        String csvContent = "abc,def123,456|7891334\n777,888|999,222012345\n\n";
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(csvContent.getBytes(StandardCharsets.UTF_8)))
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // field=, record=\n
        CreateSelectObjectMetaResult metaResult1 = client.createSelectObjectMeta(
                CreateSelectObjectMetaRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .selectMetaRequest(SelectMetaRequest.newBuilder()
                                .inputSerialization(InputSerialization.newBuilder()
                                        .csv(CSVInput.newBuilder()
                                                .fieldDelimiter(Base64.getEncoder().encodeToString(",".getBytes()))
                                                .recordDelimiter(Base64.getEncoder().encodeToString("\n".getBytes()))
                                                .build())
                                        .build())
                                .build())
                        .process("csv/meta")
                        .build());
        Assert.assertNotNull(metaResult1);
        Assert.assertEquals(200, metaResult1.statusCode());
        Assert.assertEquals(Long.valueOf(1), metaResult1.selectObjectMeta().splitsCount());
        Assert.assertEquals(Long.valueOf(3), metaResult1.selectObjectMeta().rowsCount());
        Assert.assertEquals(Long.valueOf(3), metaResult1.selectObjectMeta().columnsCount());

        // with overwrite, field=| record=\n\n
        CreateSelectObjectMetaResult metaResult2 = client.createSelectObjectMeta(
                CreateSelectObjectMetaRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .selectMetaRequest(SelectMetaRequest.newBuilder()
                                .inputSerialization(InputSerialization.newBuilder()
                                        .csv(CSVInput.newBuilder()
                                                .fieldDelimiter(Base64.getEncoder().encodeToString("|".getBytes()))
                                                .recordDelimiter(Base64.getEncoder().encodeToString("\n\n".getBytes()))
                                                .build())
                                        .build())
                                .overwriteIfExists(true)
                                .build())
                        .process("csv/meta")
                        .build());
        Assert.assertNotNull(metaResult2);
        Assert.assertEquals(200, metaResult2.statusCode());
        Assert.assertEquals(Long.valueOf(1), metaResult2.selectObjectMeta().splitsCount());
        Assert.assertEquals(Long.valueOf(1), metaResult2.selectObjectMeta().rowsCount());
        Assert.assertEquals(Long.valueOf(3), metaResult2.selectObjectMeta().columnsCount());

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testCSVSelectObjectMetaQuoteChar() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".csv";

        String csvContent = "'abc','def\n123','456'\n";
        client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(csvContent.getBytes(StandardCharsets.UTF_8)))
                .build());

        // quoteChar = '  → data inside quotes spans newline, so 1 row, 3 cols
        CreateSelectObjectMetaResult metaResult1 = client.createSelectObjectMeta(
                CreateSelectObjectMetaRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .selectMetaRequest(SelectMetaRequest.newBuilder()
                                .overwriteIfExists(true)
                                .inputSerialization(InputSerialization.newBuilder()
                                        .csv(CSVInput.newBuilder()
                                                .quoteCharacter(b64("'"))
                                                .build())
                                        .build())
                                .build())
                        .process("csv/meta")
                        .build());
        Assert.assertEquals(200, metaResult1.statusCode());
        Assert.assertEquals(Long.valueOf(1), metaResult1.selectObjectMeta().splitsCount());
        Assert.assertEquals(Long.valueOf(1), metaResult1.selectObjectMeta().rowsCount());
        Assert.assertEquals(Long.valueOf(3), metaResult1.selectObjectMeta().columnsCount());

        // re-put and use quoteChar = "  → newline is a real delimiter, so 2 rows, 2 cols
        client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(csvContent.getBytes(StandardCharsets.UTF_8)))
                .build());

        CreateSelectObjectMetaResult metaResult2 = client.createSelectObjectMeta(
                CreateSelectObjectMetaRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .selectMetaRequest(SelectMetaRequest.newBuilder()
                                .overwriteIfExists(true)
                                .inputSerialization(InputSerialization.newBuilder()
                                        .csv(CSVInput.newBuilder()
                                                .quoteCharacter(b64("\""))
                                                .build())
                                        .build())
                                .build())
                        .process("csv/meta")
                        .build());
        Assert.assertEquals(200, metaResult2.statusCode());
        Assert.assertEquals(Long.valueOf(1), metaResult2.selectObjectMeta().splitsCount());
        Assert.assertEquals(Long.valueOf(2), metaResult2.selectObjectMeta().rowsCount());
        Assert.assertEquals(Long.valueOf(2), metaResult2.selectObjectMeta().columnsCount());

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testCSVSelectObjectGzipData() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".csv.gz";

        byte[] gzipData = readResourceBytes("sample_data.csv.gz");
        Assert.assertNotNull("sample_data.csv.gz not found in resources", gzipData);

        client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(gzipData))
                .build());

        try (SelectObjectResult result = client.selectObject(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(SelectRequest.newBuilder()
                        .expression(b64("select * from ossobject"))
                        .inputSerialization(InputSerialization.newBuilder()
                                .compressionType("GZIP")
                                .csv(CSVInput.newBuilder()
                                        .fileHeaderInfo("Use")
                                        .recordDelimiter(b64("\n"))
                                        .build())
                                .build())
                        .build())
                .process("csv/select")
                .build())) {

            Assert.assertEquals(206, result.statusCode());
            String body = readStream(result.body());
            Assert.assertTrue(body.length() > 1000);
        }

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testCSVSelectObjectConcat() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".csv";

        String fileContent = readResourceString("sample_data.csv");
        Assert.assertNotNull("sample_data.csv not found in resources", fileContent);
        fileContent = fileContent.replace("\r\n", "\n");

        client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(fileContent.getBytes(StandardCharsets.UTF_8)))
                .build());

        String expression = "select Year,StateAbbr, CityName, Short_Question_Text from ossobject " +
                "where (data_value || data_value_unit) = '14.8%'";

        try (SelectObjectResult result = client.selectObject(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(SelectRequest.newBuilder()
                        .expression(b64(expression))
                        .inputSerialization(InputSerialization.newBuilder()
                                .csv(CSVInput.newBuilder()
                                        .fileHeaderInfo("Use")
                                        .recordDelimiter(b64("\n"))
                                        .build())
                                .build())
                        .build())
                .process("csv/select")
                .build())) {

            Assert.assertEquals(206, result.statusCode());
            Assert.assertEquals("false", result.headers().get("x-oss-select-output-raw"));

            String body = readStream(result.body());

            String expected = buildExpectedCsvConcat(fileContent);
            Assert.assertEquals(expected, body);
        }

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testCSVSelectObjectConcatWithOutputDelimiter() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".csv";

        String fileContent = readResourceString("sample_data.csv");
        Assert.assertNotNull(fileContent);
        fileContent = fileContent.replace("\r\n", "\n");

        client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(fileContent.getBytes(StandardCharsets.UTF_8)))
                .build());

        String expression = "select Year,StateAbbr, CityName, Short_Question_Text from ossobject " +
                "where (data_value || data_value_unit) = '14.8%'";

        try (SelectObjectResult result = client.selectObject(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(SelectRequest.newBuilder()
                        .expression(b64(expression))
                        .inputSerialization(InputSerialization.newBuilder()
                                .csv(CSVInput.newBuilder()
                                        .fileHeaderInfo("Use")
                                        .recordDelimiter(b64("\n"))
                                        .build())
                                .build())
                        .outputSerialization(OutputSerialization.newBuilder()
                                .csv(CSVOutput.newBuilder()
                                        .recordDelimiter(b64("\n"))
                                        .build())
                                .build())
                        .build())
                .process("csv/select")
                .build())) {

            Assert.assertEquals(206, result.statusCode());
            String body = readStream(result.body());
            String expected = buildExpectedCsvConcat(fileContent);
            Assert.assertEquals(expected, body);
        }

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testCSVSelectObjectComplexWhere() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".csv";

        String fileContent = readResourceString("sample_data.csv");
        Assert.assertNotNull(fileContent);
        fileContent = fileContent.replace("\r\n", "\n");

        client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(fileContent.getBytes(StandardCharsets.UTF_8)))
                .build());

        String expression = "select Year,StateAbbr, CityName, Short_Question_Text, data_value, " +
                "data_value_unit, category, high_confidence_limit from ossobject " +
                "where data_value > 14.8 and data_value_unit = '%' or " +
                "Measure like '%18 Years' and Category = 'Unhealthy Behaviors' " +
                "or high_confidence_limit > 70.0 ";

        try (SelectObjectResult result = client.selectObject(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(SelectRequest.newBuilder()
                        .expression(b64(expression))
                        .inputSerialization(InputSerialization.newBuilder()
                                .csv(CSVInput.newBuilder()
                                        .fileHeaderInfo("Use")
                                        .recordDelimiter(b64("\n"))
                                        .build())
                                .build())
                        .build())
                .process("csv/select")
                .build())) {

            Assert.assertEquals(206, result.statusCode());
            Assert.assertEquals("false", result.headers().get("x-oss-select-output-raw"));

            String body = readStream(result.body());
            String expected = buildExpectedCsvComplexWhere(fileContent);
            Assert.assertEquals(expected, body);
        }

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testCSVSelectObjectComplexWhereRaw() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".csv";

        String fileContent = readResourceString("sample_data.csv");
        Assert.assertNotNull(fileContent);
        fileContent = fileContent.replace("\r\n", "\n");

        client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(fileContent.getBytes(StandardCharsets.UTF_8)))
                .build());

        String expression = "select Year,StateAbbr, CityName, Short_Question_Text, data_value, " +
                "data_value_unit, category, high_confidence_limit from ossobject " +
                "where data_value > 14.8 and data_value_unit = '%' or " +
                "Measure like '%18 Years' and Category = 'Unhealthy Behaviors' " +
                "or high_confidence_limit > 70.0 ";

        try (SelectObjectResult result = client.selectObject(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(SelectRequest.newBuilder()
                        .expression(b64(expression))
                        .inputSerialization(InputSerialization.newBuilder()
                                .csv(CSVInput.newBuilder()
                                        .fileHeaderInfo("Use")
                                        .recordDelimiter(b64("\n"))
                                        .build())
                                .build())
                        .outputSerialization(OutputSerialization.newBuilder()
                                .outputRawData(true)
                                .build())
                        .build())
                .process("csv/select")
                .build())) {

            Assert.assertEquals(206, result.statusCode());
            Assert.assertEquals("true", result.headers().get("x-oss-select-output-raw"));

            String body = readStream(result.body());
            String expected = buildExpectedCsvComplexWhere(fileContent);
            Assert.assertEquals(expected, body);
        }

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testJSONSelectObjectLineRange() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".json";

        String fileContent = readResourceString("sample_json_lines.json");
        Assert.assertNotNull(fileContent);

        client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(fileContent.getBytes(StandardCharsets.UTF_8)))
                .build());

        client.createSelectObjectMeta(CreateSelectObjectMetaRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectMetaRequest(SelectMetaRequest.newBuilder()
                        .inputSerialization(InputSerialization.newBuilder()
                                .json(JSONInput.newBuilder().type("LINES").build())
                                .build())
                        .build())
                .process("json/meta")
                .build());

        String expression = "select person.firstname as firstname, person.lastname, extra from ossobject";

        try (SelectObjectResult result = client.selectObject(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(SelectRequest.newBuilder()
                        .expression(b64(expression))
                        .inputSerialization(InputSerialization.newBuilder()
                                .json(JSONInput.newBuilder()
                                        .type("LINES")
                                        .range("line-range=10-50")
                                        .build())
                                .build())
                        .outputSerialization(OutputSerialization.newBuilder()
                                .json(JSONOutput.newBuilder()
                                        .recordDelimiter(b64(","))
                                        .build())
                                .build())
                        .build())
                .process("json/select")
                .build())) {

            Assert.assertEquals(206, result.statusCode());
            Assert.assertEquals("false", result.headers().get("x-oss-select-output-raw"));

            String body = readStream(result.body());
            Assert.assertFalse(body.isEmpty());

            String[] records = splitJsonRecords(body, ",");
            Assert.assertEquals(41, records.length);
            for (String record : records) {
                Assert.assertTrue(record.contains("firstname"));
            }
        }

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testJSONSelectObjectComplexWhere() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".json";

        String fileContent = readResourceString("sample_json_lines.json");
        Assert.assertNotNull(fileContent);

        client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(fileContent.getBytes(StandardCharsets.UTF_8)))
                .build());

        client.createSelectObjectMeta(CreateSelectObjectMetaRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectMetaRequest(SelectMetaRequest.newBuilder()
                        .inputSerialization(InputSerialization.newBuilder()
                                .json(JSONInput.newBuilder().type("LINES").build())
                                .build())
                        .build())
                .process("json/meta")
                .build());

        String expression = "select person.firstname, person.lastname, congress_numbers from ossobject " +
                "where startdate > '2017-01-01' and senator_rank = 'junior' or " +
                "state = 'CA' and party = 'Republican'";

        try (SelectObjectResult result = client.selectObject(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(SelectRequest.newBuilder()
                        .expression(b64(expression))
                        .inputSerialization(InputSerialization.newBuilder()
                                .json(JSONInput.newBuilder().type("LINES").build())
                                .build())
                        .outputSerialization(OutputSerialization.newBuilder()
                                .json(JSONOutput.newBuilder()
                                        .recordDelimiter(b64(","))
                                        .build())
                                .build())
                        .build())
                .process("json/select")
                .build())) {

            Assert.assertEquals(206, result.statusCode());
            Assert.assertEquals("false", result.headers().get("x-oss-select-output-raw"));

            String body = readStream(result.body());
            Assert.assertFalse(body.isEmpty());

            String[] records = splitJsonRecords(body, ",");
            Assert.assertEquals(20, records.length);

            for (String record : records) {
                Assert.assertTrue(record.contains("firstname"));
                Assert.assertTrue(record.contains("lastname"));
                Assert.assertTrue(record.contains("congress_numbers"));
            }

            Assert.assertTrue(records[0].contains("\"firstname\":\"Roy\""));
            Assert.assertTrue(records[0].contains("\"lastname\":\"Blunt\""));
            Assert.assertTrue(records[1].contains("\"firstname\":\"Jerry\""));
            Assert.assertTrue(records[1].contains("\"lastname\":\"Moran\""));
            Assert.assertTrue(records[2].contains("\"firstname\":\"Robert\""));
            Assert.assertTrue(records[2].contains("\"lastname\":\"Portman\""));
        }

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testJSONSelectObjectComplexWhereRaw() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + ".json";

        String fileContent = readResourceString("sample_json_lines.json");
        Assert.assertNotNull(fileContent);

        client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(fileContent.getBytes(StandardCharsets.UTF_8)))
                .build());

        client.createSelectObjectMeta(CreateSelectObjectMetaRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectMetaRequest(SelectMetaRequest.newBuilder()
                        .inputSerialization(InputSerialization.newBuilder()
                                .json(JSONInput.newBuilder().type("LINES").build())
                                .build())
                        .build())
                .process("json/meta")
                .build());

        String expression = "select person.firstname, person.lastname, congress_numbers from ossobject " +
                "where startdate > '2017-01-01' and senator_rank = 'junior' or " +
                "state = 'CA' and party = 'Republican'";

        try (SelectObjectResult result = client.selectObject(SelectObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .selectRequest(SelectRequest.newBuilder()
                        .expression(b64(expression))
                        .inputSerialization(InputSerialization.newBuilder()
                                .json(JSONInput.newBuilder().type("LINES").build())
                                .build())
                        .outputSerialization(OutputSerialization.newBuilder()
                                .json(JSONOutput.newBuilder()
                                        .recordDelimiter(b64(","))
                                        .build())
                                .outputRawData(true)
                                .build())
                        .build())
                .process("json/select")
                .build())) {

            Assert.assertEquals(206, result.statusCode());
            Assert.assertEquals("true", result.headers().get("x-oss-select-output-raw"));

            String body = readStream(result.body());
            Assert.assertFalse(body.isEmpty());

            String[] records = splitJsonRecords(body, ",");
            Assert.assertEquals(20, records.length);

            Assert.assertTrue(records[0].contains("\"firstname\":\"Roy\""));
            Assert.assertTrue(records[0].contains("\"lastname\":\"Blunt\""));
            Assert.assertTrue(records[1].contains("\"firstname\":\"Jerry\""));
            Assert.assertTrue(records[1].contains("\"lastname\":\"Moran\""));
        }

        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    // --- Helper methods ---

    private String genObjectName() {
        long ticks = new Date().getTime() / 1000;
        long val = new Random().nextInt(5000);
        return OJBJECT_NAME_PREFIX + ticks + "-" + val;
    }

    private static String b64(String s) {
        return Base64.getEncoder().encodeToString(s.getBytes(StandardCharsets.UTF_8));
    }

    private static String readStream(InputStream is) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int n;
        while ((n = is.read(buf)) != -1) {
            out.write(buf, 0, n);
        }
        return out.toString("UTF-8");
    }

    private static byte[] readResourceBytes(String name) throws IOException {
        try (InputStream is = ClientSelectObjectTest.class.getClassLoader().getResourceAsStream(name)) {
            if (is == null) return null;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int n;
            while ((n = is.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
            return out.toByteArray();
        }
    }

    private static String readResourceString(String name) throws IOException {
        byte[] data = readResourceBytes(name);
        return data == null ? null : new String(data, StandardCharsets.UTF_8);
    }

    private static List<String> splitCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(field.toString());
                field.setLength(0);
            } else {
                field.append(c);
            }
        }
        fields.add(field.toString());
        return fields;
    }

    private static Map<String, Integer> parseCsvHeader(String headerLine) {
        List<String> fields = splitCsvLine(headerLine);
        Map<String, Integer> index = new LinkedHashMap<>();
        for (int i = 0; i < fields.size(); i++) {
            index.put(fields.get(i), i);
        }
        return index;
    }

    private static String csvField(List<String> fields, Map<String, Integer> header, String col) {
        Integer idx = header.get(col);
        if (idx == null || idx >= fields.size()) return "";
        return fields.get(idx);
    }

    private static String buildExpectedCsvConcat(String fileContent) {
        String[] lines = fileContent.split("\n", -1);
        if (lines.length == 0) return "";
        Map<String, Integer> header = parseCsvHeader(lines[0]);
        StringBuilder expected = new StringBuilder();
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].isEmpty()) continue;
            List<String> fields = splitCsvLine(lines[i]);
            if ("14.8".equals(csvField(fields, header, "Data_Value"))
                    && "%".equals(csvField(fields, header, "Data_Value_Unit"))) {
                expected.append(csvField(fields, header, "Year")).append(",")
                        .append(csvField(fields, header, "StateAbbr")).append(",")
                        .append(csvField(fields, header, "CityName")).append(",")
                        .append(csvField(fields, header, "Short_Question_Text")).append("\n");
            }
        }
        return expected.toString();
    }

    private static String buildExpectedCsvComplexWhere(String fileContent) {
        String[] lines = fileContent.split("\n", -1);
        if (lines.length == 0) return "";
        Map<String, Integer> header = parseCsvHeader(lines[0]);
        StringBuilder expected = new StringBuilder();
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].isEmpty()) continue;
            List<String> fields = splitCsvLine(lines[i]);
            String dataValue = csvField(fields, header, "Data_Value");
            String dataValueUnit = csvField(fields, header, "Data_Value_Unit");
            String measure = csvField(fields, header, "Measure");
            String category = csvField(fields, header, "Category");
            String highConfLimit = csvField(fields, header, "High_Confidence_Limit");

            boolean cond1 = !dataValue.isEmpty() && parseDouble(dataValue) > 14.8 && "%".equals(dataValueUnit);
            boolean cond2 = measure.endsWith("18 Years") && "Unhealthy Behaviors".equals(category);
            boolean cond3 = !highConfLimit.isEmpty() && parseDouble(highConfLimit) > 70.0;

            if (cond1 || cond2 || cond3) {
                expected.append(csvField(fields, header, "Year")).append(",")
                        .append(csvField(fields, header, "StateAbbr")).append(",")
                        .append(csvField(fields, header, "CityName")).append(",")
                        .append(csvField(fields, header, "Short_Question_Text")).append(",")
                        .append(dataValue).append(",")
                        .append(dataValueUnit).append(",")
                        .append(category).append(",")
                        .append(highConfLimit).append("\n");
            }
        }
        return expected.toString();
    }

    private static double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private static String[] splitJsonRecords(String body, String delim) {
        String trimmed = body;
        if (!trimmed.isEmpty() && trimmed.charAt(trimmed.length() - 1) == delim.charAt(0)) {
            trimmed = trimmed.substring(0, trimmed.length() - 1);
        }
        String sep = delim + "{";
        List<String> records = new ArrayList<>();
        int pos = 0;
        while (pos < trimmed.length()) {
            int next = trimmed.indexOf(sep, pos);
            if (next == -1) {
                records.add(trimmed.substring(pos));
                break;
            }
            records.add(trimmed.substring(pos, next));
            pos = next + delim.length();
        }
        return records.toArray(new String[0]);
    }
}
