package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;

public class ClientBucketWebsiteAsyncTest extends TestBase {

    @Test
    public void testBucketWebsite() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String testBucketName = genBucketName();

        try {
            // 1. create bucket
            PutBucketResult bucketResult = client.putBucketAsync(PutBucketRequest.newBuilder()
                    .bucket(testBucketName)
                    .acl("private")
                    .createBucketConfiguration(CreateBucketConfiguration.newBuilder()
                            .storageClass("IA")
                            .build())
                    .build()).get();
            Assert.assertEquals(200, bucketResult.statusCode());

            // 2. put bucket website
            IndexDocument indexDoc = IndexDocument.newBuilder()
                    .suffix("index.html")
                    .supportSubDir(true)
                    .type(0L)
                    .build();

            ErrorDocument errorDoc = ErrorDocument.newBuilder()
                    .key("error.html")
                    .httpStatus(404L)
                    .build();

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

            RoutingRuleCondition condition1 = RoutingRuleCondition.newBuilder()
                    .keySuffixEquals("abc/")
                    .httpErrorCodeReturnedEquals(404L)
                    .includeHeaders(Arrays.asList(includeHeader1, includeHeader2))
                    .keyPrefixEquals("key")
                    .build();

            MirrorHeadersSet mirrorHeadersSet1 = MirrorHeadersSet.newBuilder()
                    .key("myheader-key5")
                    .value("myheader-value")
                    .build();

            MirrorHeadersSet mirrorHeadersSet2 = MirrorHeadersSet.newBuilder()
                    .key("myheader-key6")
                    .value("myheader-value2")
                    .build();

            MirrorHeaders mirrorHeaders = MirrorHeaders.newBuilder()
                    .passAll(true)
                    .passs(Arrays.asList("myheader-key1", "myheader-key2"))
                    .removes(Arrays.asList("myheader-key3", "myheader-key4"))
                    .sets(Arrays.asList(mirrorHeadersSet1, mirrorHeadersSet2))
                    .build();

            RoutingRuleRedirect redirect1 = RoutingRuleRedirect.newBuilder()
                    .mirrorURL("http://example.com/")
                    .enableReplacePrefix(true)
                    .passQueryString(false)
                    .mirrorHeaders(mirrorHeaders)
                    .mirrorSNI(true)
                    .replaceKeyPrefixWith("abc/")
                    .redirectType("Mirror")
                    .mirrorPassQueryString(false)
                    .mirrorFollowRedirect(true)
                    .mirrorCheckMd5(true)
                    .mirrorPassOriginalSlashes(true)
                    .build();

            RoutingRuleCondition condition2 = RoutingRuleCondition.newBuilder()
                    .keySuffixEquals("bbc/")
                    .httpErrorCodeReturnedEquals(403L)
                    .includeHeaders(Arrays.asList(includeHeader21, includeHeader22))
                    .keyPrefixEquals("abc/")
                    .build();

            RoutingRuleRedirect redirect2 = RoutingRuleRedirect.newBuilder()
                    .replaceKeyWith("prefix/${key}.suffix")
                    .passQueryString(false)
                    .httpRedirectCode(301L)
                    .protocol("http")
                    .redirectType("AliCDN")
                    .hostName("example.com")
                    .build();

            RoutingRuleCondition condition3 = RoutingRuleCondition.newBuilder()
                    .httpErrorCodeReturnedEquals(404L)
                    .build();

            RoutingRuleRedirect redirect3 = RoutingRuleRedirect.newBuilder()
                    .replaceKeyWith("prefix/${key}")
                    .passQueryString(false)
                    .enableReplacePrefix(false)
                    .httpRedirectCode(302L)
                    .protocol("http")
                    .redirectType("External")
                    .hostName("example.com")
                    .build();

            RoutingRule routingRule1 = RoutingRule.newBuilder()
                    .ruleNumber(1L)
                    .condition(condition1)
                    .redirect(redirect1)
                    .build();

            RoutingRule routingRule2 = RoutingRule.newBuilder()
                    .ruleNumber(2L)
                    .condition(condition2)
                    .redirect(redirect2)
                    .build();

            RoutingRule routingRule3 = RoutingRule.newBuilder()
                    .ruleNumber(3L)
                    .condition(condition3)
                    .redirect(redirect3)
                    .build();

            RoutingRules routingRules = RoutingRules.newBuilder()
                    .routingRules(Arrays.asList(routingRule1, routingRule2, routingRule3))
                    .build();

            WebsiteConfiguration websiteConfiguration = WebsiteConfiguration.newBuilder()
                    .indexDocument(indexDoc)
                    .errorDocument(errorDoc)
                    .routingRules(routingRules)
                    .build();

            PutBucketWebsiteResult putResult = client.putBucketWebsiteAsync(PutBucketWebsiteRequest.newBuilder()
                    .bucket(testBucketName)
                    .websiteConfiguration(websiteConfiguration)
                    .build()).get();

            Assert.assertEquals(200, putResult.statusCode());

            waitForCacheExpiration(5);

            // 3. get bucket website
            GetBucketWebsiteResult getResult = client.getBucketWebsiteAsync(GetBucketWebsiteRequest.newBuilder()
                    .bucket(testBucketName)
                    .build()).get();

            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.websiteConfiguration());
            
            WebsiteConfiguration resultConfig = getResult.websiteConfiguration();
            Assert.assertEquals("index.html", resultConfig.indexDocument().suffix());
            Assert.assertEquals(true, resultConfig.indexDocument().supportSubDir());
            Assert.assertEquals(Long.valueOf(0), resultConfig.indexDocument().type());
            Assert.assertEquals("error.html", resultConfig.errorDocument().key());
            Assert.assertEquals(404, resultConfig.errorDocument().httpStatus().intValue());
            
            // Verify routing rules
            Assert.assertEquals(3, resultConfig.routingRules().routingRules().size());
            
            // Verify first routing rule
            RoutingRule resultRule1 = resultConfig.routingRules().routingRules().get(0);
            Assert.assertEquals(1, resultRule1.ruleNumber().intValue());
            Assert.assertEquals("abc/", resultRule1.condition().keySuffixEquals());
            Assert.assertEquals(404, resultRule1.condition().httpErrorCodeReturnedEquals().intValue());
            Assert.assertEquals("key1", resultRule1.condition().includeHeaders().get(0).key());
            Assert.assertEquals("value1", resultRule1.condition().includeHeaders().get(0).equals());
            Assert.assertEquals("key2", resultRule1.condition().includeHeaders().get(1).key());
            Assert.assertEquals("value2", resultRule1.condition().includeHeaders().get(1).equals());
            Assert.assertEquals("key", resultRule1.condition().keyPrefixEquals());
            Assert.assertEquals("http://example.com/", resultRule1.redirect().mirrorURL());
            Assert.assertEquals(true, resultRule1.redirect().enableReplacePrefix());
            Assert.assertEquals(false, resultRule1.redirect().passQueryString());
            Assert.assertEquals(true, resultRule1.redirect().mirrorHeaders().passAll());
            Assert.assertEquals("myheader-key1", resultRule1.redirect().mirrorHeaders().passs().get(0));
            Assert.assertEquals("myheader-key2", resultRule1.redirect().mirrorHeaders().passs().get(1));
            Assert.assertEquals("myheader-key3", resultRule1.redirect().mirrorHeaders().removes().get(0));
            Assert.assertEquals("myheader-key4", resultRule1.redirect().mirrorHeaders().removes().get(1));
            Assert.assertEquals("myheader-key5", resultRule1.redirect().mirrorHeaders().sets().get(0).key());
            Assert.assertEquals("myheader-value", resultRule1.redirect().mirrorHeaders().sets().get(0).value());
            Assert.assertEquals("myheader-key6", resultRule1.redirect().mirrorHeaders().sets().get(1).key());
            Assert.assertEquals("myheader-value2", resultRule1.redirect().mirrorHeaders().sets().get(1).value());
            Assert.assertEquals(true, resultRule1.redirect().mirrorSNI());
            Assert.assertEquals("abc/", resultRule1.redirect().replaceKeyPrefixWith());
            Assert.assertEquals("Mirror", resultRule1.redirect().redirectType());
            Assert.assertEquals(false, resultRule1.redirect().mirrorPassQueryString());
            Assert.assertEquals(true, resultRule1.redirect().mirrorFollowRedirect());
            Assert.assertEquals(true, resultRule1.redirect().mirrorCheckMd5());
            Assert.assertEquals(true, resultRule1.redirect().mirrorPassOriginalSlashes());

            // Verify second routing rule
            RoutingRule resultRule2 = resultConfig.routingRules().routingRules().get(1);
            Assert.assertEquals(2, resultRule2.ruleNumber().intValue());
            Assert.assertEquals("bbc/", resultRule2.condition().keySuffixEquals());
            Assert.assertEquals(403, resultRule2.condition().httpErrorCodeReturnedEquals().intValue());
            Assert.assertEquals("key21", resultRule2.condition().includeHeaders().get(0).key());
            Assert.assertEquals("value21", resultRule2.condition().includeHeaders().get(0).equals());
            Assert.assertEquals("key22", resultRule2.condition().includeHeaders().get(1).key());
            Assert.assertEquals("value22U", resultRule2.condition().includeHeaders().get(1).equals());
            Assert.assertEquals("abc/", resultRule2.condition().keyPrefixEquals());
            Assert.assertEquals("prefix/${key}.suffix", resultRule2.redirect().replaceKeyWith());
            Assert.assertEquals(false, resultRule2.redirect().passQueryString());
            Assert.assertEquals(301, resultRule2.redirect().httpRedirectCode().intValue());
            Assert.assertEquals("http", resultRule2.redirect().protocol());
            Assert.assertEquals("AliCDN", resultRule2.redirect().redirectType());
            Assert.assertEquals("example.com", resultRule2.redirect().hostName());

            // Verify third routing rule
            RoutingRule resultRule3 = resultConfig.routingRules().routingRules().get(2);
            Assert.assertEquals(3, resultRule3.ruleNumber().intValue());
            Assert.assertEquals(404, resultRule3.condition().httpErrorCodeReturnedEquals().intValue());
            Assert.assertEquals("prefix/${key}", resultRule3.redirect().replaceKeyWith());
            Assert.assertEquals(false, resultRule3.redirect().passQueryString());
            Assert.assertEquals(false, resultRule3.redirect().enableReplacePrefix());
            Assert.assertEquals(302, resultRule3.redirect().httpRedirectCode().intValue());
            Assert.assertEquals("http", resultRule3.redirect().protocol());
            Assert.assertEquals("External", resultRule3.redirect().redirectType());
            Assert.assertEquals("example.com", resultRule3.redirect().hostName());

            // 4. delete bucket website
            DeleteBucketWebsiteResult deleteResult = client.deleteBucketWebsiteAsync(DeleteBucketWebsiteRequest.newBuilder()
                    .bucket(testBucketName)
                    .build()).get();

            Assert.assertEquals(204, deleteResult.statusCode());
        } finally {
            // Clean up test bucket
            try {
                cleanBucket(testBucketName, region());
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        }
    }

    @Test
    public void testBucketWebsiteNewRedirect() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String testBucketName = genBucketName();

        try {
            // 1. create bucket
            PutBucketResult bucketResult = client.putBucketAsync(PutBucketRequest.newBuilder()
                    .bucket(testBucketName)
                    .acl("private")
                    .createBucketConfiguration(CreateBucketConfiguration.newBuilder()
                            .storageClass("IA")
                            .build())
                    .build()).get();
            Assert.assertEquals(200, bucketResult.statusCode());

            // 2. put bucket website with new redirect fields
            IndexDocument indexDoc = IndexDocument.newBuilder()
                    .suffix("index.html")
                    .supportSubDir(true)
                    .type(0L)
                    .build();

            ErrorDocument errorDoc = ErrorDocument.newBuilder()
                    .key("error.html")
                    .httpStatus(404L)
                    .build();

            RoutingRuleIncludeHeader includeHeader1 = RoutingRuleIncludeHeader.newBuilder()
                    .key("key1")
                    .equals("value1")
                    .build();

            RoutingRuleIncludeHeader includeHeader2 = RoutingRuleIncludeHeader.newBuilder()
                    .key("key2")
                    .equals("value2")
                    .build();

            RoutingRuleCondition condition = RoutingRuleCondition.newBuilder()
                    .keySuffixEquals("abc/")
                    .httpErrorCodeReturnedEquals(404L)
                    .includeHeaders(Arrays.asList(includeHeader1, includeHeader2))
                    .keyPrefixEquals("key")
                    .build();

            MirrorHeadersSet mirrorHeadersSet1 = MirrorHeadersSet.newBuilder()
                    .key("myheader-key5")
                    .value("myheader-value")
                    .build();

            MirrorHeadersSet mirrorHeadersSet2 = MirrorHeadersSet.newBuilder()
                    .key("myheader-key6")
                    .value("myheader-value2")
                    .build();

            MirrorHeaders mirrorHeaders = MirrorHeaders.newBuilder()
                    .passAll(true)
                    .passs(Arrays.asList("myheader-key1", "myheader-key2"))
                    .removes(Arrays.asList("myheader-key3", "myheader-key4"))
                    .sets(Arrays.asList(mirrorHeadersSet1, mirrorHeadersSet2))
                    .build();

            MirrorTagging mirrorTagging1 = MirrorTagging.newBuilder()
                    .key("tag-key1")
                    .value("tag-value1")
                    .build();

            MirrorTagging mirrorTagging2 = MirrorTagging.newBuilder()
                    .key("tag-key2")
                    .value("tag-value2")
                    .build();

            MirrorTaggings mirrorTaggings = MirrorTaggings.newBuilder()
                    .taggings(Arrays.asList(mirrorTagging1, mirrorTagging2))
                    .build();

            MirrorAuth mirrorAuth = MirrorAuth.newBuilder()
                    .accessKeyId("test-access-key-id")
                    .accessKeySecret("test-access-key-secret")
                    .authType("S3V4")
                    .region("cn-hangzhou")
                    .build();

            MirrorReturnHeader returnHeader1 = MirrorReturnHeader.newBuilder()
                    .key("header-key1")
                    .value("header-value1")
                    .build();

            MirrorReturnHeader returnHeader2 = MirrorReturnHeader.newBuilder()
                    .key("header-key2")
                    .value("header-value2")
                    .build();

            MirrorReturnHeaders mirrorReturnHeaders = MirrorReturnHeaders.newBuilder()
                    .returnHeaders(Arrays.asList(returnHeader1, returnHeader2))
                    .build();

            MirrorMultiAlternate mirrorMultiAlternate1 = MirrorMultiAlternate.newBuilder()
                    .mirrorMultiAlternateNumber(1L)
                    .mirrorMultiAlternateURL("http://alternate1.example.com/")
                    .mirrorMultiAlternateVpcId("vpc-alternate-1")
                    .mirrorMultiAlternateDstRegion("oss-cn-shanghai")
                    .build();

            MirrorMultiAlternate mirrorMultiAlternate2 = MirrorMultiAlternate.newBuilder()
                    .mirrorMultiAlternateNumber(2L)
                    .mirrorMultiAlternateURL("http://alternate2.example.com/")
                    .mirrorMultiAlternateVpcId("vpc-alternate-2")
                    .mirrorMultiAlternateDstRegion("oss-cn-beijing")
                    .build();

            MirrorMultiAlternates mirrorMultiAlternates = MirrorMultiAlternates.newBuilder()
                    .mirrorMultiAlternates(Arrays.asList(mirrorMultiAlternate1, mirrorMultiAlternate2))
                    .build();

            RoutingRuleRedirect redirect = RoutingRuleRedirect.newBuilder()
                    .mirrorURL("http://example.com/")
                    .enableReplacePrefix(true)
                    .passQueryString(false)
                    .mirrorHeaders(mirrorHeaders)
                    .mirrorSNI(false)
                    .replaceKeyPrefixWith("abc/")
                    .redirectType("Mirror")
                    .mirrorPassQueryString(false)
                    .mirrorFollowRedirect(true)
                    .mirrorCheckMd5(true)
                    .mirrorPassOriginalSlashes(true)
                    .mirrorAllowVideoSnapshot(true)
                    .mirrorTaggings(mirrorTaggings)
                    .mirrorAuth(mirrorAuth)
                    .mirrorDstRegion("oss-cn-hangzhou")
                    .mirrorRole("test-role")
                    .mirrorUsingRole(true)
                    .mirrorReturnHeaders(mirrorReturnHeaders)
                    .mirrorProxyPass(false)
                    .mirrorIsExpressTunnel(true)
                    .mirrorAllowHeadObject(true)
                    .transparentMirrorResponseCodes("404,500")
                    .mirrorSaveOssMeta(true)
                    .mirrorAllowGetImageInfo(true)
                    .mirrorURLProbe("http://probe.example.com/")
                    .mirrorUserLastModified(true)
                    .mirrorSwitchAllErrors(false)
                    .mirrorMultiAlternates(mirrorMultiAlternates)
                    .build();

            RoutingRule routingRule = RoutingRule.newBuilder()
                    .ruleNumber(1L)
                    .condition(condition)
                    .redirect(redirect)
                    .build();

            RoutingRules routingRules = RoutingRules.newBuilder()
                    .routingRules(Collections.singletonList(routingRule))
                    .build();

            WebsiteConfiguration websiteConfiguration = WebsiteConfiguration.newBuilder()
                    .indexDocument(indexDoc)
                    .errorDocument(errorDoc)
                    .routingRules(routingRules)
                    .build();

            PutBucketWebsiteResult putResult = client.putBucketWebsiteAsync(PutBucketWebsiteRequest.newBuilder()
                    .bucket(testBucketName)
                    .websiteConfiguration(websiteConfiguration)
                    .build()).get();

            Assert.assertEquals(200, putResult.statusCode());

            waitForCacheExpiration(5);

            // 3. get bucket website
            GetBucketWebsiteResult getResult = client.getBucketWebsiteAsync(GetBucketWebsiteRequest.newBuilder()
                    .bucket(testBucketName)
                    .build()).get();

            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.websiteConfiguration());
            
            WebsiteConfiguration resultConfig = getResult.websiteConfiguration();
            Assert.assertEquals("index.html", resultConfig.indexDocument().suffix());
            Assert.assertEquals(true, resultConfig.indexDocument().supportSubDir());
            Assert.assertEquals(Long.valueOf(0), resultConfig.indexDocument().type());
            Assert.assertEquals("error.html", resultConfig.errorDocument().key());
            Assert.assertEquals(404, resultConfig.errorDocument().httpStatus().intValue());
            
            // Verify routing rule with new fields
            Assert.assertEquals(1, resultConfig.routingRules().routingRules().size());
            RoutingRule resultRule = resultConfig.routingRules().routingRules().get(0);
            Assert.assertEquals(1, resultRule.ruleNumber().intValue());
            Assert.assertEquals("abc/", resultRule.condition().keySuffixEquals());
            Assert.assertEquals(404, resultRule.condition().httpErrorCodeReturnedEquals().intValue());
            Assert.assertEquals("key1", resultRule.condition().includeHeaders().get(0).key());
            Assert.assertEquals("value1", resultRule.condition().includeHeaders().get(0).equals());
            Assert.assertEquals("key2", resultRule.condition().includeHeaders().get(1).key());
            Assert.assertEquals("value2", resultRule.condition().includeHeaders().get(1).equals());
            Assert.assertEquals("key", resultRule.condition().keyPrefixEquals());
            Assert.assertEquals("http://example.com/", resultRule.redirect().mirrorURL());
            Assert.assertEquals(true, resultRule.redirect().enableReplacePrefix());
            Assert.assertEquals(false, resultRule.redirect().passQueryString());
            Assert.assertEquals(true, resultRule.redirect().mirrorHeaders().passAll());
            Assert.assertEquals("myheader-key1", resultRule.redirect().mirrorHeaders().passs().get(0));
            Assert.assertEquals("myheader-key2", resultRule.redirect().mirrorHeaders().passs().get(1));
            Assert.assertEquals("myheader-key3", resultRule.redirect().mirrorHeaders().removes().get(0));
            Assert.assertEquals("myheader-key4", resultRule.redirect().mirrorHeaders().removes().get(1));
            Assert.assertEquals("myheader-key5", resultRule.redirect().mirrorHeaders().sets().get(0).key());
            Assert.assertEquals("myheader-value", resultRule.redirect().mirrorHeaders().sets().get(0).value());
            Assert.assertEquals("myheader-key6", resultRule.redirect().mirrorHeaders().sets().get(1).key());
            Assert.assertEquals("myheader-value2", resultRule.redirect().mirrorHeaders().sets().get(1).value());
            Assert.assertEquals(false, resultRule.redirect().mirrorSNI());
            Assert.assertEquals("abc/", resultRule.redirect().replaceKeyPrefixWith());
            Assert.assertEquals("Mirror", resultRule.redirect().redirectType());
            Assert.assertEquals(false, resultRule.redirect().mirrorPassQueryString());
            Assert.assertEquals(true, resultRule.redirect().mirrorFollowRedirect());
            Assert.assertEquals(true, resultRule.redirect().mirrorCheckMd5());
            Assert.assertEquals(true, resultRule.redirect().mirrorPassOriginalSlashes());
            
            // Verify new fields
            Assert.assertEquals(true, resultRule.redirect().mirrorAllowVideoSnapshot());
            Assert.assertEquals("tag-key1", resultRule.redirect().mirrorTaggings().taggings().get(0).key());
            Assert.assertEquals("tag-value1", resultRule.redirect().mirrorTaggings().taggings().get(0).value());
            Assert.assertEquals("tag-key2", resultRule.redirect().mirrorTaggings().taggings().get(1).key());
            Assert.assertEquals("tag-value2", resultRule.redirect().mirrorTaggings().taggings().get(1).value());
            Assert.assertEquals("test-access-key-id", resultRule.redirect().mirrorAuth().accessKeyId());
            Assert.assertEquals("S3V4", resultRule.redirect().mirrorAuth().authType());
            Assert.assertEquals("cn-hangzhou", resultRule.redirect().mirrorAuth().region());
            Assert.assertEquals("oss-cn-hangzhou", resultRule.redirect().mirrorDstRegion());
            Assert.assertEquals("test-role", resultRule.redirect().mirrorRole());
            Assert.assertEquals(true, resultRule.redirect().mirrorUsingRole());
            Assert.assertEquals("header-key1", resultRule.redirect().mirrorReturnHeaders().returnHeaders().get(0).key());
            Assert.assertEquals("header-value1", resultRule.redirect().mirrorReturnHeaders().returnHeaders().get(0).value());
            Assert.assertEquals("header-key2", resultRule.redirect().mirrorReturnHeaders().returnHeaders().get(1).key());
            Assert.assertEquals("header-value2", resultRule.redirect().mirrorReturnHeaders().returnHeaders().get(1).value());
            Assert.assertEquals(true, resultRule.redirect().mirrorIsExpressTunnel());
            Assert.assertEquals(true, resultRule.redirect().mirrorAllowHeadObject());
            Assert.assertEquals("404,500", resultRule.redirect().transparentMirrorResponseCodes());
            Assert.assertEquals(true, resultRule.redirect().mirrorSaveOssMeta());
            Assert.assertEquals(true, resultRule.redirect().mirrorAllowGetImageInfo());
            Assert.assertEquals("http://probe.example.com/", resultRule.redirect().mirrorURLProbe());
            Assert.assertEquals(true, resultRule.redirect().mirrorUserLastModified());
            Assert.assertEquals(false, resultRule.redirect().mirrorSwitchAllErrors());
            Assert.assertEquals(1, resultRule.redirect().mirrorMultiAlternates().mirrorMultiAlternates().get(0).mirrorMultiAlternateNumber().intValue());
            Assert.assertEquals("http://alternate1.example.com/", resultRule.redirect().mirrorMultiAlternates().mirrorMultiAlternates().get(0).mirrorMultiAlternateURL());
            Assert.assertEquals("vpc-alternate-1", resultRule.redirect().mirrorMultiAlternates().mirrorMultiAlternates().get(0).mirrorMultiAlternateVpcId());
            Assert.assertEquals("oss-cn-shanghai", resultRule.redirect().mirrorMultiAlternates().mirrorMultiAlternates().get(0).mirrorMultiAlternateDstRegion());
            Assert.assertEquals(2, resultRule.redirect().mirrorMultiAlternates().mirrorMultiAlternates().get(1).mirrorMultiAlternateNumber().intValue());
            Assert.assertEquals("http://alternate2.example.com/", resultRule.redirect().mirrorMultiAlternates().mirrorMultiAlternates().get(1).mirrorMultiAlternateURL());
            Assert.assertEquals("vpc-alternate-2", resultRule.redirect().mirrorMultiAlternates().mirrorMultiAlternates().get(1).mirrorMultiAlternateVpcId());
            Assert.assertEquals("oss-cn-beijing", resultRule.redirect().mirrorMultiAlternates().mirrorMultiAlternates().get(1).mirrorMultiAlternateDstRegion());

            // 4. delete bucket website
            DeleteBucketWebsiteResult deleteResult = client.deleteBucketWebsiteAsync(DeleteBucketWebsiteRequest.newBuilder()
                    .bucket(testBucketName)
                    .build()).get();

            Assert.assertEquals(204, deleteResult.statusCode());
        } finally {
            // Clean up test bucket
            cleanBucket(testBucketName, region());
        }
    }
}