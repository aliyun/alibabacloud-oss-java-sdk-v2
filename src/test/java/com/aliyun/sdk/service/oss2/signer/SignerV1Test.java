package com.aliyun.sdk.service.oss2.signer;

import com.aliyun.sdk.service.oss2.credentials.Credentials;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.transport.RequestMessage;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.aliyun.sdk.service.oss2.utils.HttpUtils.encodeQueryParameters;
import static com.aliyun.sdk.service.oss2.utils.HttpUtils.uriEncodedParams;

public class SignerV1Test {

    @Test
    public void testAuthHeader1() {
        StaticCredentialsProvider provider = new StaticCredentialsProvider("ak", "sk");
        Credentials cred = provider.getCredentials();

        RequestMessage request = RequestMessage.newBuilder()
                .method("PUT")
                .uri("http://examplebucket.oss-cn-hangzhou.aliyuncs.com")
                .build();

        request.headers().put("Content-MD5", "eB5eJF1ptWaXm4bijSPyxw==");
        request.headers().put("Content-Type", "text/html");
        request.headers().put("x-oss-meta-author", "alice");
        request.headers().put("x-oss-meta-magic", "abracadabra");
        request.headers().put("x-oss-date", "Wed, 28 Dec 2022 10:27:41 GMT");

        SigningContext context = new SigningContext();
        context.setBucket("examplebucket");
        context.setKey("nelson");
        context.setRequest(request);
        context.setCredentials(cred);
        context.setSignTime(Instant.ofEpochSecond(1702743657));

        SignerV1 signer = new SignerV1();
        signer.sign(context);

        Assert.assertEquals(
                "PUT\neB5eJF1ptWaXm4bijSPyxw==\ntext/html\nWed, 28 Dec 2022 10:27:41 GMT\nx-oss-date:Wed, 28 Dec 2022 10:27:41 GMT\nx-oss-meta-author:alice\nx-oss-meta-magic:abracadabra\n/examplebucket/nelson",
                context.getStringToSign()
        );
        Assert.assertEquals("OSS ak:kSHKmLxlyEAKtZPkJhG9bZb5k7M=", request.headers().get("Authorization"));
    }

    @Test
    public void testAuthHeader2() {
        StaticCredentialsProvider provider = new StaticCredentialsProvider("ak", "sk");
        Credentials cred = provider.getCredentials();

        RequestMessage request = RequestMessage.newBuilder()
                .method("PUT")
                .uri("http://examplebucket.oss-cn-hangzhou.aliyuncs.com/?acl")
                .build();
        request.headers().put("Content-MD5", "eB5eJF1ptWaXm4bijSPyxw==");
        request.headers().put("Content-Type", "text/html");
        request.headers().put("x-oss-meta-author", "alice");
        request.headers().put("x-oss-meta-magic", "abracadabra");
        request.headers().put("x-oss-date", "Wed, 28 Dec 2022 10:27:41 GMT");

        SigningContext context = new SigningContext();
        context.setBucket("examplebucket");
        context.setKey("nelson");
        context.setRequest(request);
        context.setCredentials(cred);
        context.setSignTime(Instant.ofEpochSecond(1702743657));

        SignerV1 signer = new SignerV1();
        signer.sign(context);

        Assert.assertEquals(
                "PUT\neB5eJF1ptWaXm4bijSPyxw==\ntext/html\nWed, 28 Dec 2022 10:27:41 GMT\nx-oss-date:Wed, 28 Dec 2022 10:27:41 GMT\nx-oss-meta-author:alice\nx-oss-meta-magic:abracadabra\n/examplebucket/nelson?acl",
                context.getStringToSign()
        );
        Assert.assertEquals("OSS ak:/afkugFbmWDQ967j1vr6zygBLQk=", request.headers().get("Authorization"));
    }

    @Test
    public void testAuthHeader3() {
        StaticCredentialsProvider provider = new StaticCredentialsProvider("ak", "sk");
        Credentials cred = provider.getCredentials();

        RequestMessage request = RequestMessage.newBuilder()
                .method("GET")
                .uri("http://examplebucket.oss-cn-hangzhou.aliyuncs.com/?resourceGroup&non-resousce=null")
                .build();
        request.headers().put("x-oss-date", "Wed, 28 Dec 2022 10:27:41 GMT");

        SigningContext context = new SigningContext();
        context.setBucket("examplebucket");
        context.setRequest(request);
        context.setCredentials(cred);
        context.setSignTime(Instant.ofEpochSecond(1702743657));

        SignerV1 signer = new SignerV1();
        signer.sign(context);

        Assert.assertEquals(
                "GET\n\n\nWed, 28 Dec 2022 10:27:41 GMT\nx-oss-date:Wed, 28 Dec 2022 10:27:41 GMT\n/examplebucket/?resourceGroup",
                context.getStringToSign()
        );
        Assert.assertEquals("OSS ak:vkQmfuUDyi1uDi3bKt67oemssIs=", request.headers().get("Authorization"));
    }

    @Test
    public void testAuthHeader4() {
        StaticCredentialsProvider provider = new StaticCredentialsProvider("ak", "sk");
        Credentials cred = provider.getCredentials();

        RequestMessage request = RequestMessage.newBuilder()
                .method("GET")
                .uri("http://examplebucket.oss-cn-hangzhou.aliyuncs.com/?resourceGroup&acl")
                .build();
        request.headers().put("x-oss-date", "Wed, 28 Dec 2022 10:27:41 GMT");

        SigningContext context = new SigningContext();
        context.setBucket("examplebucket");
        context.setRequest(request);
        context.setCredentials(cred);
        context.setSignTime(Instant.ofEpochSecond(1702743657));

        SignerV1 signer = new SignerV1();
        signer.sign(context);

        Assert.assertEquals(
                "GET\n\n\nWed, 28 Dec 2022 10:27:41 GMT\nx-oss-date:Wed, 28 Dec 2022 10:27:41 GMT\n/examplebucket/?acl&resourceGroup",
                context.getStringToSign()
        );
        Assert.assertEquals("OSS ak:x3E5TgOvl/i7PN618s5mEvpJDYk=", request.headers().get("Authorization"));
    }

    @Test
    public void testAuthQuery() {
        StaticCredentialsProvider provider = new StaticCredentialsProvider("ak", "sk");
        Credentials cred = provider.getCredentials();

        RequestMessage request = RequestMessage.newBuilder()
                .method("GET")
                .uri("http://bucket.oss-cn-hangzhou.aliyuncs.com/key?versionId=versionId")
                .build();

        SigningContext context = new SigningContext();
        context.setBucket("bucket");
        context.setKey("key");
        context.setRequest(request);
        context.setCredentials(cred);
        context.setExpiration(Instant.ofEpochSecond(1699807420));
        context.setAuthMethodQuery(true);

        SignerV1 signer = new SignerV1();
        signer.sign(context);

        Map<String, String> queries = uriEncodedParams(context.getRequest().uri());

        Assert.assertEquals("versionId", queries.get("versionId"));
        Assert.assertNotNull(queries.get("Expires"));
        Assert.assertEquals("ak", queries.get("OSSAccessKeyId"));
        Assert.assertEquals("dcLTea%2BYh9ApirQ8o8dOPqtvJXQ%3D", queries.get("Signature"));
    }

    @Test
    public void testAuthQueryWithToken() {
        StaticCredentialsProvider provider = new StaticCredentialsProvider("ak", "sk", "attachment; /file/name==example.txt++");
        Credentials cred = provider.getCredentials();

        RequestMessage request = RequestMessage.newBuilder()
                .method("GET")
                .uri("http://bucket.oss-cn-hangzhou.aliyuncs.com/key+123?versionId=versionId")
                .build();

        SigningContext context = new SigningContext();
        context.setBucket("bucket");
        context.setKey("key+123");
        context.setRequest(request);
        context.setCredentials(cred);
        context.setExpiration(Instant.ofEpochSecond(1699808204));
        context.setAuthMethodQuery(true);

        SignerV1 signer = new SignerV1();
        signer.sign(context);

        Map<String, String> queries = uriEncodedParams(context.getRequest().uri());

        Assert.assertNotNull(queries.get("Expires"));
        Assert.assertEquals("ak", queries.get("OSSAccessKeyId"));
        Assert.assertEquals("attachment%3B%20%2Ffile%2Fname%3D%3Dexample.txt%2B%2B", queries.get("security-token"));
        Assert.assertEquals("su58IVk06Q73DHwcMsXft%2FRTZ98%3D", queries.get("Signature"));
    }

    @Test
    public void testAuthQueryParamWithToken() {
        StaticCredentialsProvider provider = new StaticCredentialsProvider("ak", "sk", "token");
        Credentials cred = provider.getCredentials();

        RequestMessage request = RequestMessage.newBuilder()
                .method("GET")
                .uri("http://bucket.oss-cn-hangzhou.aliyuncs.com/key?versionId=versionId")
                .build();
        request.headers().put("x-oss-head1", "value");
        request.headers().put("abc", "value");
        request.headers().put("ZAbc", "value");
        request.headers().put("XYZ", "value");
        request.headers().put("content-type", "application/octet-stream");

        SigningContext context = new SigningContext();
        context.setBucket("bucket");
        context.setKey("key");
        context.setRequest(request);
        context.setCredentials(cred);
        context.setExpiration(Instant.ofEpochSecond(1699808204));
        context.setAuthMethodQuery(true);


        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("param1", "value1");
        parameters.put("+param1", "value3");
        parameters.put("|param1", "value4");
        parameters.put("+param2", "");
        parameters.put("|param2", "");
        parameters.put("param2", "");
        parameters.put("response-content-disposition", "attachment; filename=example.txt");

        Optional<String> query = encodeQueryParameters(parameters);
        StringBuilder url = new StringBuilder();
        url.append(request.uri().toString());
        query.ifPresent(s -> url.append("&").append(s));

        context.setRequest(RequestMessage.newBuilder()
                .method(request.method())
                .headers(request.headers())
                .uri(url.toString())
                .build());

        SignerV1 signer = new SignerV1();
        signer.sign(context);

        Map<String, String> queries = uriEncodedParams(context.getRequest().uri());

        Assert.assertNotNull(queries.get("Expires"));
        Assert.assertEquals("ak", queries.get("OSSAccessKeyId"));
        Assert.assertEquals("attachment%3B%20filename%3Dexample.txt", queries.get("response-content-disposition"));
        Assert.assertEquals("token", queries.get("security-token"));
        Assert.assertEquals("VmWfLWfxbR3MSFvUx5%2BnyQhCa3g%3D", queries.get("Signature"));

    }
}

