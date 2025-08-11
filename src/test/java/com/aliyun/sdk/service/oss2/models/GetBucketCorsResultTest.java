package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetBucketCorsResultTest {
    @Test
    public void testEmptyBuilder() {
        GetBucketCorsResult result = GetBucketCorsResult.newBuilder().build();
        Assert.assertEquals(0, result.headers().size());
        assertNotNull(result);
    }


    @Test
    public void testXmlParsing() throws IOException {
        String xml = "";

        OperationOutput output = new OperationOutput.Builder()
                .body(SerdeUtils.serializeXmlBody(xml))
                .build();

        ObjectMapper xmlMapper = new XmlMapper();

        String xmlContent = new String(output.body.toBytes(), StandardCharsets.UTF_8);
        Object innerBody = xmlMapper.readValue(xmlContent, CORSConfiguration.class);
        GetBucketCorsResult getBucketCorsResult = GetBucketCorsResult.newBuilder()
                .innerBody(innerBody)
                .build();
        CORSConfiguration config = getBucketCorsResult.corsConfiguration();

        assertNotNull(config);


        xml =
                "<CORSConfiguration>\n" +
                        "    <CORSRule>\n" +
                        "      <AllowedOrigin>*</AllowedOrigin>\n" +
                        "      <AllowedMethod>GET</AllowedMethod>\n" +
                        "      <AllowedHeader>*</AllowedHeader>\n" +
                        "      <ExposeHeader>x-oss-test</ExposeHeader>\n" +
                        "      <MaxAgeSeconds>100</MaxAgeSeconds>\n" +
                        "    </CORSRule>\n" +
                        "    <ResponseVary>false</ResponseVary>\n" +
                        "</CORSConfiguration>";


        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        config = xmlMapper.readValue(xml, CORSConfiguration.class);

        assertNotNull(config);

        List<CORSRule> rules = config.corsRules();
        assertEquals(1, rules.size());

        CORSRule rule = rules.get(0);
        assertNotNull(rule);

        // AllowedOrigins
        List<String> allowedOrigins = rule.allowedOrigins();
        assertEquals(1, allowedOrigins.size());
        assertEquals("*", allowedOrigins.get(0));

        // AllowedMethods
        List<String> allowedMethods = rule.allowedMethods();
        assertEquals(1, allowedMethods.size());
        assertEquals("GET", allowedMethods.get(0));

        // AllowedHeaders
        List<String> allowedHeaders = rule.allowedHeaders();
        assertEquals(1, allowedHeaders.size());
        assertEquals("*", allowedHeaders.get(0));

        // ExposeHeaders
        List<String> exposeHeaders = rule.exposeHeaders();
        assertEquals(1, exposeHeaders.size());
        assertEquals("x-oss-test", exposeHeaders.get(0));

        // MaxAgeSeconds
        Long maxAgeSeconds = rule.maxAgeSeconds();
        assertEquals(Long.valueOf(100), maxAgeSeconds);

        // ResponseVary
        Boolean responseVary = config.responseVary();
        assertEquals(Boolean.FALSE, responseVary);
    }
}
