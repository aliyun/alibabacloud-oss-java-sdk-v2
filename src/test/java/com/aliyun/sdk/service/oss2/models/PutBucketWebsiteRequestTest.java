package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketWebsite;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketWebsiteRequestTest {
    @Test
    public void emptyBuilder() {
        PutBucketWebsiteRequest request = PutBucketWebsiteRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.websiteConfiguration()).isNull();
    }

    @Test
    public void fullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        IndexDocument indexDocument = IndexDocument.newBuilder()
                .suffix("index.html")
                .supportSubDir(true)
                .type(0L)
                .build();

        ErrorDocument errorDocument = ErrorDocument.newBuilder()
                .key("error.html")
                .httpStatus(404L)
                .build();

        WebsiteConfiguration websiteConfiguration = WebsiteConfiguration.newBuilder()
                .indexDocument(indexDocument)
                .errorDocument(errorDocument)
                .build();

        PutBucketWebsiteRequest request = PutBucketWebsiteRequest.newBuilder()
                .bucket("examplebucket")
                .websiteConfiguration(websiteConfiguration)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.websiteConfiguration()).isEqualTo(websiteConfiguration);

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void toBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        IndexDocument indexDocument = IndexDocument.newBuilder()
                .suffix("index.html")
                .supportSubDir(true)
                .type(0L)
                .build();

        ErrorDocument errorDocument = ErrorDocument.newBuilder()
                .key("error.html")
                .httpStatus(404L)
                .build();

        WebsiteConfiguration websiteConfiguration = WebsiteConfiguration.newBuilder()
                .indexDocument(indexDocument)
                .errorDocument(errorDocument)
                .build();

        PutBucketWebsiteRequest original = PutBucketWebsiteRequest.newBuilder()
                .bucket("testbucket")
                .websiteConfiguration(websiteConfiguration)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutBucketWebsiteRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.websiteConfiguration()).isEqualTo(websiteConfiguration);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void headerProperties() {
        IndexDocument indexDocument = IndexDocument.newBuilder()
                .suffix("index.html")
                .supportSubDir(true)
                .type(0L)
                .build();

        ErrorDocument errorDocument = ErrorDocument.newBuilder()
                .key("error.html")
                .httpStatus(404L)
                .build();

        WebsiteConfiguration websiteConfiguration = WebsiteConfiguration.newBuilder()
                .indexDocument(indexDocument)
                .errorDocument(errorDocument)
                .build();

        PutBucketWebsiteRequest request = PutBucketWebsiteRequest.newBuilder()
                .bucket("anotherbucket")
                .websiteConfiguration(websiteConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.websiteConfiguration()).isEqualTo(websiteConfiguration);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        IndexDocument indexDocument = IndexDocument.newBuilder()
                .suffix("index.html")
                .supportSubDir(true)
                .type(0L)
                .build();

        ErrorDocument errorDocument = ErrorDocument.newBuilder()
                .key("error.html")
                .httpStatus(404L)
                .build();

        // Build IncludeHeader objects
        RoutingRuleIncludeHeader includeHeader1 = RoutingRuleIncludeHeader.newBuilder()
                .key("key1")
                .equals("value1")
                .build();
        
        RoutingRuleIncludeHeader includeHeader2 = RoutingRuleIncludeHeader.newBuilder()
                .key("key2")
                .equals("value2")
                .build();
        
        RoutingRuleIncludeHeader includeHeader21 = RoutingRuleIncludeHeader.newBuilder()
                .key("key21")
                .equals("value21")
                .build();
        
        RoutingRuleIncludeHeader includeHeader22 = RoutingRuleIncludeHeader.newBuilder()
                .key("key22")
                .equals("value22U")
                .build();

        // Build RoutingRuleCondition objects
        RoutingRuleCondition condition1 = RoutingRuleCondition.newBuilder()
                .keyPrefixEquals("abc/")
                .httpErrorCodeReturnedEquals(404L)
                .keySuffixEquals("abc/")
                .includeHeaders(Arrays.asList(includeHeader1, includeHeader2))
                .build();
                
        RoutingRuleCondition condition2 = RoutingRuleCondition.newBuilder()
                .keyPrefixEquals("abc/")
                .httpErrorCodeReturnedEquals(403L)
                .keySuffixEquals("bbc/")
                .includeHeaders(Arrays.asList(includeHeader21, includeHeader22))
                .build();

        // Build MirrorReturnHeaders
        MirrorReturnHeaders mirrorReturnHeader1 = MirrorReturnHeaders.newBuilder()
                .returnHeaders(Arrays.asList(
                        ReturnHeader.newBuilder()
                                .key("key1")
                                .value("value1")
                                .build()
                ))
                .build();
        
        MirrorReturnHeaders mirrorReturnHeader2 = MirrorReturnHeaders.newBuilder()
                .returnHeaders(Arrays.asList(
                        ReturnHeader.newBuilder()
                                .key("key2")
                                .value("value2")
                                .build()
                ))
                .build();

        // Build MirrorTaggings
        MirrorTaggings mirrorTagging1 = MirrorTaggings.newBuilder()
                .taggings(Arrays.asList(
                        MirrorTagging.newBuilder()
                                .key("key1")
                                .value("value1")
                                .build()
                ))
                .build();
                
        MirrorTaggings mirrorTagging2 = MirrorTaggings.newBuilder()
                .taggings(Arrays.asList(
                        MirrorTagging.newBuilder()
                                .key("key2")
                                .value("value2")
                                .build()
                ))
                .build();

        // Build MirrorAuth
        MirrorAuth mirrorAuth1 = MirrorAuth.newBuilder()
                .accessKeyId("test-access-key-id")
                .accessKeySecret("test-access-key-secret")
                .authType("type")
                .region("region")
                .build();
                
        MirrorAuth mirrorAuth2 = MirrorAuth.newBuilder()
                .accessKeyId("test-access-key-id-2")
                .accessKeySecret("test-access-key-secret-2")
                .authType("type2")
                .region("region2")
                .build();

        // Build MirrorMultiAlternate
        MirrorMultiAlternate mirrorMultiAlternate1 = MirrorMultiAlternate.newBuilder()
                .mirrorMultiAlternateNumber(1L)
                .mirrorMultiAlternateURL("http://multi1.com/")
                .mirrorMultiAlternateVpcId("multi-vpc-id-1")
                .mirrorMultiAlternateDstRegion("multi-region-1")
                .build();
                
        MirrorMultiAlternate mirrorMultiAlternate2 = MirrorMultiAlternate.newBuilder()
                .mirrorMultiAlternateNumber(2L)
                .mirrorMultiAlternateURL("http://multi2.com/")
                .mirrorMultiAlternateVpcId("multi-vpc-id-2")
                .mirrorMultiAlternateDstRegion("multi-region-2")
                .build();

        // Build MirrorHeadersSet objects
        MirrorHeadersSet mirrorHeadersSet1 = MirrorHeadersSet.newBuilder()
                .key("myheader-key5")
                .value("myheader-value")
                .build();
                
        MirrorHeadersSet mirrorHeadersSet2 = MirrorHeadersSet.newBuilder()
                .key("myheader-key6")
                .value("myheader-valu2")
                .build();
                
        MirrorHeadersSet mirrorHeadersSet3 = MirrorHeadersSet.newBuilder()
                .key("myheader-key25")
                .value("myheader-value2")
                .build();
                
        MirrorHeadersSet mirrorHeadersSet4 = MirrorHeadersSet.newBuilder()
                .key("myheader-key26")
                .value("myheader-value22")
                .build();

        // Build MirrorHeaders
        MirrorHeaders mirrorHeaders1 = MirrorHeaders.newBuilder()
                .passAll(true)
                .pass("myheader-key1")
                .pass("myheader-key2")
                .remove("myheader-key3")
                .remove("myheader-key4")
                .sets(Arrays.asList(mirrorHeadersSet1, mirrorHeadersSet2))
                .build();
                
        MirrorHeaders mirrorHeaders2 = MirrorHeaders.newBuilder()
                .passAll(true)
                .pass("myheader-key21")
                .pass("myheader-key22")
                .remove("myheader-key23")
                .remove("myheader-key24")
                .sets(Arrays.asList(mirrorHeadersSet3, mirrorHeadersSet4))
                .build();

        // Build RoutingRuleRedirect objects
        RoutingRuleRedirect redirect1 = RoutingRuleRedirect.newBuilder()
                .redirectType("Mirror")
                .passQueryString(true)
                .mirrorURL("http://example.com/")
                .mirrorPassQueryString(true)
                .mirrorFollowRedirect(true)
                .mirrorCheckMd5(false)
                .mirrorPassOriginalSlashes(false)
                .mirrorAllowVideoSnapshot(true)
                .mirrorAsyncStatus(1L)
                .mirrorSNI(false)
                .protocol("http")
                .replaceKeyPrefixWith("abc/")
                .httpRedirectCode(203L)
                .replaceKeyWith("aab/")
                .hostName("example.com")
                .enableReplacePrefix(true)
                .mirrorDstRegion("region")
                .mirrorDstVpcId("vpc-id")
                .mirrorTunnelId("tunnel-id")
                .mirrorRole("role")
                .mirrorUsingRole(true)
                .mirrorReturnHeaders(mirrorReturnHeader1)
                .mirrorProxyPass(true)
                .mirrorIsExpressTunnel(true)
                .mirrorDstSlaveVpcId("slave-vpc-id")
                .mirrorAllowHeadObject(true)
                .transparentMirrorResponseCodes("404,500")
                .mirrorSaveOssMeta(true)
                .mirrorAllowGetImageInfo(true)
                .mirrorURLProbe("http://probe.com/")
                .mirrorURLSlave("http://slave.com/")
                .mirrorUserLastModified(true)
                .mirrorSwitchAllErrors(true)
                .mirrorTaggings(mirrorTagging1)
                .mirrorAuth(mirrorAuth1)
                .mirrorMultiAlternates(MirrorMultiAlternates.newBuilder()
                        .mirrorMultiAlternates(Arrays.asList(mirrorMultiAlternate1))
                        .build())
                .mirrorHeaders(mirrorHeaders1)
                .build();

        RoutingRuleRedirect redirect2 = RoutingRuleRedirect.newBuilder()
                .redirectType("AliCDN")
                .passQueryString(true)
                .mirrorURL("http://example.com/")
                .mirrorPassQueryString(false)
                .mirrorFollowRedirect(true)
                .mirrorCheckMd5(false)
                .mirrorPassOriginalSlashes(true)
                .mirrorAllowVideoSnapshot(false)
                .mirrorAsyncStatus(2L)
                .mirrorSNI(true)
                .protocol("https")
                .replaceKeyPrefixWith("prefix/${key}.suffix")
                .httpRedirectCode(2970L)
                .replaceKeyWith("prefix/${key}")
                .hostName("example.com")
                .enableReplacePrefix(true)
                .mirrorDstRegion("region2")
                .mirrorDstVpcId("vpc-id-2")
                .mirrorTunnelId("tunnel-id-2")
                .mirrorRole("role2")
                .mirrorUsingRole(false)
                .mirrorReturnHeaders(mirrorReturnHeader2)
                .mirrorProxyPass(false)
                .mirrorIsExpressTunnel(false)
                .mirrorDstSlaveVpcId("slave-vpc-id-2")
                .mirrorAllowHeadObject(false)
                .transparentMirrorResponseCodes("403")
                .mirrorSaveOssMeta(false)
                .mirrorAllowGetImageInfo(false)
                .mirrorURLProbe("http://probe2.com/")
                .mirrorURLSlave("http://slave2.com/")
                .mirrorUserLastModified(false)
                .mirrorSwitchAllErrors(false)
                .mirrorTaggings(mirrorTagging2)
                .mirrorAuth(mirrorAuth2)
                .mirrorMultiAlternates(MirrorMultiAlternates.newBuilder()
                        .mirrorMultiAlternates(Arrays.asList(mirrorMultiAlternate2))
                        .build())
                .mirrorHeaders(mirrorHeaders2)
                .build();

        // Build RoutingRuleLuaConfig
        RoutingRuleLuaConfig luaConfig1 = RoutingRuleLuaConfig.newBuilder()
                .script("test.lua")
                .build();
                
        RoutingRuleLuaConfig luaConfig2 = RoutingRuleLuaConfig.newBuilder()
                .script("script2.lua")
                .build();

        // Build RoutingRule objects
        RoutingRule routingRule1 = RoutingRule.newBuilder()
                .ruleNumber(1L)
                .condition(condition1)
                .redirect(redirect1)
                .luaConfig(luaConfig1)
                .build();
                
        RoutingRule routingRule2 = RoutingRule.newBuilder()
                .ruleNumber(2L)
                .condition(condition2)
                .redirect(redirect2)
                .luaConfig(luaConfig2)
                .build();

        // Build RoutingRules object
        RoutingRules routingRules = RoutingRules.newBuilder()
                .routingRules(Arrays.asList(routingRule1, routingRule2))
                .build();

        WebsiteConfiguration websiteConfiguration = WebsiteConfiguration.newBuilder()
                .indexDocument(indexDocument)
                .errorDocument(errorDocument)
                .routingRules(routingRules)
                .build();

        PutBucketWebsiteRequest request = PutBucketWebsiteRequest.newBuilder()
                .bucket("examplebucket")
                .websiteConfiguration(websiteConfiguration)
                .build();

        OperationInput input = SerdeBucketWebsite.fromPutBucketWebsite(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("website")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<WebsiteConfiguration>");
        assertThat(xmlContent).contains("<IndexDocument>");
        assertThat(xmlContent).contains("<Suffix>index.html</Suffix>");
        assertThat(xmlContent).contains("<SupportSubDir>true</SupportSubDir>");
        assertThat(xmlContent).contains("<Type>0</Type>");
        assertThat(xmlContent).contains("<ErrorDocument>");
        assertThat(xmlContent).contains("<Key>error.html</Key>");
        assertThat(xmlContent).contains("<HttpStatus>404</HttpStatus>");
        
        // Verify RoutingRules section
        assertThat(xmlContent).contains("<RoutingRules>");
        assertThat(xmlContent).contains("<RoutingRule>");
        assertThat(xmlContent).contains("<RuleNumber>1</RuleNumber>");
        assertThat(xmlContent).contains("<RuleNumber>2</RuleNumber>");
        assertThat(xmlContent).contains("<KeyPrefixEquals>abc/</KeyPrefixEquals>");
        assertThat(xmlContent).contains("<HttpErrorCodeReturnedEquals>404</HttpErrorCodeReturnedEquals>");
        assertThat(xmlContent).contains("<KeySuffixEquals>abc/</KeySuffixEquals>");
        assertThat(xmlContent).contains("<KeySuffixEquals>bbc/</KeySuffixEquals>");
        assertThat(xmlContent).contains("<IncludeHeader>");
        assertThat(xmlContent).contains("<Key>key1</Key>");
        assertThat(xmlContent).contains("<Equals>value1</Equals>");
        assertThat(xmlContent).contains("<Key>key2</Key>");
        assertThat(xmlContent).contains("<Equals>value2</Equals>");
        assertThat(xmlContent).contains("<Key>key21</Key>");
        assertThat(xmlContent).contains("<Equals>value21</Equals>");
        assertThat(xmlContent).contains("<Key>key22</Key>");
        assertThat(xmlContent).contains("<Equals>value22U</Equals>");
        
        // Verify Redirect section
        assertThat(xmlContent).contains("<Redirect>");
        assertThat(xmlContent).contains("<RedirectType>Mirror</RedirectType>");
        assertThat(xmlContent).contains("<RedirectType>AliCDN</RedirectType>");
        assertThat(xmlContent).contains("<PassQueryString>true</PassQueryString>");
        assertThat(xmlContent).contains("<MirrorURL>http://example.com/</MirrorURL>");
        assertThat(xmlContent).contains("<MirrorPassQueryString>true</MirrorPassQueryString>");
        assertThat(xmlContent).contains("<MirrorPassQueryString>false</MirrorPassQueryString>");
        assertThat(xmlContent).contains("<MirrorFollowRedirect>true</MirrorFollowRedirect>");
        assertThat(xmlContent).contains("<MirrorCheckMd5>false</MirrorCheckMd5>");
        assertThat(xmlContent).contains("<MirrorPassOriginalSlashes>false</MirrorPassOriginalSlashes>");
        assertThat(xmlContent).contains("<MirrorPassOriginalSlashes>true</MirrorPassOriginalSlashes>");
        assertThat(xmlContent).contains("<MirrorAllowVideoSnapshot>true</MirrorAllowVideoSnapshot>");
        assertThat(xmlContent).contains("<MirrorAllowVideoSnapshot>false</MirrorAllowVideoSnapshot>");
        assertThat(xmlContent).contains("<MirrorAsyncStatus>1</MirrorAsyncStatus>");
        assertThat(xmlContent).contains("<MirrorAsyncStatus>2</MirrorAsyncStatus>");
        assertThat(xmlContent).contains("<MirrorSNI>false</MirrorSNI>");
        assertThat(xmlContent).contains("<MirrorSNI>true</MirrorSNI>");
        assertThat(xmlContent).contains("<Protocol>http</Protocol>");
        assertThat(xmlContent).contains("<Protocol>https</Protocol>");
        assertThat(xmlContent).contains("<ReplaceKeyPrefixWith>abc/</ReplaceKeyPrefixWith>");
        assertThat(xmlContent).contains("<ReplaceKeyPrefixWith>prefix/${key}.suffix</ReplaceKeyPrefixWith>");
        assertThat(xmlContent).contains("<HttpRedirectCode>203</HttpRedirectCode>");
        assertThat(xmlContent).contains("<HttpRedirectCode>2970</HttpRedirectCode>");
        assertThat(xmlContent).contains("<ReplaceKeyWith>aab/</ReplaceKeyWith>");
        assertThat(xmlContent).contains("<ReplaceKeyWith>prefix/${key}</ReplaceKeyWith>");
        assertThat(xmlContent).contains("<HostName>example.com</HostName>");
        assertThat(xmlContent).contains("<EnableReplacePrefix>true</EnableReplacePrefix>");
        assertThat(xmlContent).contains("<MirrorDstRegion>region</MirrorDstRegion>");
        assertThat(xmlContent).contains("<MirrorDstRegion>region2</MirrorDstRegion>");
        assertThat(xmlContent).contains("<MirrorDstVpcId>vpc-id</MirrorDstVpcId>");
        assertThat(xmlContent).contains("<MirrorDstVpcId>vpc-id-2</MirrorDstVpcId>");
        assertThat(xmlContent).contains("<MirrorTunnelId>tunnel-id</MirrorTunnelId>");
        assertThat(xmlContent).contains("<MirrorTunnelId>tunnel-id-2</MirrorTunnelId>");
        assertThat(xmlContent).contains("<MirrorRole>role</MirrorRole>");
        assertThat(xmlContent).contains("<MirrorRole>role2</MirrorRole>");
        assertThat(xmlContent).contains("<MirrorUsingRole>true</MirrorUsingRole>");
        assertThat(xmlContent).contains("<MirrorUsingRole>false</MirrorUsingRole>");
        assertThat(xmlContent).contains("<MirrorReturnHeaders>");
        assertThat(xmlContent).contains("<ReturnHeader>");
        assertThat(xmlContent).contains("<Key>key1</Key>");
        assertThat(xmlContent).contains("<Value>value1</Value>");
        assertThat(xmlContent).contains("<Key>key2</Key>");
        assertThat(xmlContent).contains("<Value>value2</Value>");
        assertThat(xmlContent).contains("<MirrorProxyPass>true</MirrorProxyPass>");
        assertThat(xmlContent).contains("<MirrorProxyPass>false</MirrorProxyPass>");
        assertThat(xmlContent).contains("<MirrorIsExpressTunnel>true</MirrorIsExpressTunnel>");
        assertThat(xmlContent).contains("<MirrorIsExpressTunnel>false</MirrorIsExpressTunnel>");
        assertThat(xmlContent).contains("<MirrorDstSlaveVpcId>slave-vpc-id</MirrorDstSlaveVpcId>");
        assertThat(xmlContent).contains("<MirrorDstSlaveVpcId>slave-vpc-id-2</MirrorDstSlaveVpcId>");
        assertThat(xmlContent).contains("<MirrorAllowHeadObject>true</MirrorAllowHeadObject>");
        assertThat(xmlContent).contains("<MirrorAllowHeadObject>false</MirrorAllowHeadObject>");
        assertThat(xmlContent).contains("<TransparentMirrorResponseCodes>404,500</TransparentMirrorResponseCodes>");
        assertThat(xmlContent).contains("<TransparentMirrorResponseCodes>403</TransparentMirrorResponseCodes>");
        assertThat(xmlContent).contains("<MirrorSaveOssMeta>true</MirrorSaveOssMeta>");
        assertThat(xmlContent).contains("<MirrorSaveOssMeta>false</MirrorSaveOssMeta>");
        assertThat(xmlContent).contains("<MirrorAllowGetImageInfo>true</MirrorAllowGetImageInfo>");
        assertThat(xmlContent).contains("<MirrorAllowGetImageInfo>false</MirrorAllowGetImageInfo>");
        assertThat(xmlContent).contains("<MirrorURLProbe>http://probe.com/</MirrorURLProbe>");
        assertThat(xmlContent).contains("<MirrorURLProbe>http://probe2.com/</MirrorURLProbe>");
        assertThat(xmlContent).contains("<MirrorURLSlave>http://slave.com/</MirrorURLSlave>");
        assertThat(xmlContent).contains("<MirrorURLSlave>http://slave2.com/</MirrorURLSlave>");
        assertThat(xmlContent).contains("<MirrorUserLastModified>true</MirrorUserLastModified>");
        assertThat(xmlContent).contains("<MirrorUserLastModified>false</MirrorUserLastModified>");
        assertThat(xmlContent).contains("<MirrorSwitchAllErrors>true</MirrorSwitchAllErrors>");
        assertThat(xmlContent).contains("<MirrorSwitchAllErrors>false</MirrorSwitchAllErrors>");
        assertThat(xmlContent).contains("<MirrorTaggings>");
        assertThat(xmlContent).contains("<Taggings>");
        assertThat(xmlContent).contains("<Key>key1</Key>");
        assertThat(xmlContent).contains("<Value>value1</Value>");
        assertThat(xmlContent).contains("<Key>key2</Key>");
        assertThat(xmlContent).contains("<Value>value2</Value>");
        assertThat(xmlContent).contains("<MirrorAuth>");
        assertThat(xmlContent).contains("<AccessKeyId>test-access-key-id</AccessKeyId>");
        assertThat(xmlContent).contains("<AccessKeyId>test-access-key-id-2</AccessKeyId>");
        assertThat(xmlContent).contains("<AccessKeySecret>test-access-key-secret</AccessKeySecret>");
        assertThat(xmlContent).contains("<AccessKeySecret>test-access-key-secret-2</AccessKeySecret>");
        assertThat(xmlContent).contains("<AuthType>type</AuthType>");
        assertThat(xmlContent).contains("<AuthType>type2</AuthType>");
        assertThat(xmlContent).contains("<Region>region</Region>");
        assertThat(xmlContent).contains("<Region>region2</Region>");
        assertThat(xmlContent).contains("<MirrorMultiAlternates>");
        assertThat(xmlContent).contains("<MirrorMultiAlternate>");
        assertThat(xmlContent).contains("<MirrorMultiAlternateNumber>1</MirrorMultiAlternateNumber>");
        assertThat(xmlContent).contains("<MirrorMultiAlternateNumber>2</MirrorMultiAlternateNumber>");
        assertThat(xmlContent).contains("<MirrorMultiAlternateURL>http://multi1.com/</MirrorMultiAlternateURL>");
        assertThat(xmlContent).contains("<MirrorMultiAlternateURL>http://multi2.com/</MirrorMultiAlternateURL>");
        assertThat(xmlContent).contains("<MirrorMultiAlternateVpcId>multi-vpc-id-1</MirrorMultiAlternateVpcId>");
        assertThat(xmlContent).contains("<MirrorMultiAlternateVpcId>multi-vpc-id-2</MirrorMultiAlternateVpcId>");
        assertThat(xmlContent).contains("<MirrorMultiAlternateDstRegion>multi-region-1</MirrorMultiAlternateDstRegion>");
        assertThat(xmlContent).contains("<MirrorMultiAlternateDstRegion>multi-region-2</MirrorMultiAlternateDstRegion>");
        assertThat(xmlContent).contains("<MirrorHeaders>");
        assertThat(xmlContent).contains("<PassAll>true</PassAll>");
        assertThat(xmlContent).contains("<Pass>myheader-key1</Pass>");
        assertThat(xmlContent).contains("<Pass>myheader-key2</Pass>");
        assertThat(xmlContent).contains("<Pass>myheader-key21</Pass>");
        assertThat(xmlContent).contains("<Pass>myheader-key22</Pass>");
        assertThat(xmlContent).contains("<Remove>myheader-key3</Remove>");
        assertThat(xmlContent).contains("<Remove>myheader-key4</Remove>");
        assertThat(xmlContent).contains("<Remove>myheader-key23</Remove>");
        assertThat(xmlContent).contains("<Remove>myheader-key24</Remove>");
        assertThat(xmlContent).contains("<Set>");
        assertThat(xmlContent).contains("<Key>myheader-key5</Key>");
        assertThat(xmlContent).contains("<Value>myheader-value</Value>");
        assertThat(xmlContent).contains("<Key>myheader-key6</Key>");
        assertThat(xmlContent).contains("<Value>myheader-valu2</Value>");
        assertThat(xmlContent).contains("<Key>myheader-key25</Key>");
        assertThat(xmlContent).contains("<Value>myheader-value2</Value>");
        assertThat(xmlContent).contains("<Key>myheader-key26</Key>");
        assertThat(xmlContent).contains("<Value>myheader-value22</Value>");
        
        // Verify LuaConfig section
        assertThat(xmlContent).contains("<LuaConfig>");
        assertThat(xmlContent).contains("<Script>test.lua</Script>");
        assertThat(xmlContent).contains("<Script>script2.lua</Script>");
        
        assertThat(xmlContent).contains("</WebsiteConfiguration>");
    }
}