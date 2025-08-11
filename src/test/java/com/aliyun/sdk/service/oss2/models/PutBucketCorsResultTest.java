package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertNotNull;

public class PutBucketCorsResultTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketCorsResult result = PutBucketCorsResult.newBuilder().build();
        Assert.assertEquals(0, result.headers().size());
        assertNotNull(result);
    }


    @Test
    public void testXmlParsing() throws Exception {
        String xml = "";

        OperationOutput output = new OperationOutput.Builder()
                .body(SerdeUtils.serializeXmlBody(xml))
                .build();

        ObjectMapper xmlMapper = new XmlMapper();

        String xmlContent = new String(output.body.toBytes(), StandardCharsets.UTF_8);
        Object innerBody = xmlMapper.readValue(xmlContent, CORSConfiguration.class);
        GetBucketCorsResult result = GetBucketCorsResult.newBuilder()
                .innerBody(innerBody)
                .build();
        CORSConfiguration config = result.corsConfiguration();

        assertNotNull(config);
    }
}
