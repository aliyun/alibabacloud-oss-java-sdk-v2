package com.aliyun.sdk.service.oss2.signer;

import com.aliyun.sdk.service.oss2.credentials.Credentials;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.transport.RequestMessage;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.aliyun.sdk.service.oss2.utils.HttpUtils.encodeQueryParameters;
import static com.aliyun.sdk.service.oss2.utils.HttpUtils.uriEncodedParams;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SignerV4Test {

    @Test
    public void testAuthHeader() throws UnsupportedEncodingException {
        StaticCredentialsProvider provider = new StaticCredentialsProvider("ak", "sk");
        Credentials cred = provider.getCredentials();
        RequestMessage request = RequestMessage.newBuilder()
                .method("PUT")
                .uri("http://bucket.oss-cn-hangzhou.aliyuncs.com")
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-head1", "value");
        headers.put("abc", "value");
        headers.put("ZAbc", "value");
        headers.put("XYZ", "value");
        headers.put("content-type", "text/plain");
        headers.put("x-oss-content-sha256", "UNSIGNED-PAYLOAD");
        request = updateRequestHeaders(request, headers);

        SigningContext context = new SigningContext();
        context.setBucket("bucket");
        context.setKey("1234+-/123/1.txt");
        context.setRequest(request);
        context.setCredentials(cred);
        context.setProduct("oss");
        context.setRegion("cn-hangzhou");
        context.setSignTime(Instant.ofEpochSecond(1702743657));

        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value1");
        parameters.put("+param1", "value3");
        parameters.put("|param1", "value4");
        parameters.put("+param2", "");
        parameters.put("|param2", "");
        parameters.put("param2", "");

        Optional<String> query = encodeQueryParameters(parameters);

        StringBuilder url = new StringBuilder();
        url.append(request.uri().getScheme()).append("://").append(request.uri().getAuthority()).append(request.uri().getPath());
        query.ifPresent(s -> url.append("?").append(s));

        context.setRequest(RequestMessage.newBuilder()
                .method(request.method())
                .headers(request.headers())
                .uri(url.toString())
                .build());

        SignerV4 signer = new SignerV4();
        signer.sign(context);

        String expectedAuth = "OSS4-HMAC-SHA256 Credential=ak/20231216/cn-hangzhou/oss/aliyun_v4_request,Signature=e21d18daa82167720f9b1047ae7e7f1ce7cb77a31e8203a7d5f4624fa0284afe";
        assertEquals(expectedAuth, context.getRequest().headers().get("Authorization"));
    }

    @Test
    public void testAuthHeaderWithToken() throws UnsupportedEncodingException {
        StaticCredentialsProvider provider = new StaticCredentialsProvider("ak", "sk", "token");
        Credentials cred = provider.getCredentials();
        RequestMessage request = RequestMessage.newBuilder()
                .method("PUT")
                .uri("http://bucket.oss-cn-hangzhou.aliyuncs.com")
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-head1", "value");
        headers.put("abc", "value");
        headers.put("ZAbc", "value");
        headers.put("XYZ", "value");
        headers.put("content-type", "text/plain");
        headers.put("x-oss-content-sha256", "UNSIGNED-PAYLOAD");
        request = updateRequestHeaders(request, headers);

        SigningContext context = new SigningContext();
        context.setBucket("bucket");
        context.setKey("1234+-/123/1.txt");
        context.setRequest(request);
        context.setCredentials(cred);
        context.setProduct("oss");
        context.setRegion("cn-hangzhou");
        context.setSignTime(Instant.ofEpochSecond(1702784856));

        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value1");
        parameters.put("+param1", "value3");
        parameters.put("|param1", "value4");
        parameters.put("+param2", "");
        parameters.put("|param2", "");
        parameters.put("param2", "");

        Optional<String> query = encodeQueryParameters(parameters);
        StringBuilder url = new StringBuilder();
        url.append(request.uri().getScheme()).append("://").append(request.uri().getAuthority()).append(request.uri().getPath());
        query.ifPresent(s -> url.append("?").append(s));

        context.setRequest(RequestMessage.newBuilder()
                .method(request.method())
                .headers(request.headers())
                .uri(url.toString())
                .build());

        SignerV4 signer = new SignerV4();
        signer.sign(context);

        String expectedAuth = "OSS4-HMAC-SHA256 Credential=ak/20231217/cn-hangzhou/oss/aliyun_v4_request,Signature=b94a3f999cf85bcdc00d332fbd3734ba03e48382c36fa4d5af5df817395bd9ea";
        assertEquals(expectedAuth, context.getRequest().headers().get("Authorization"));
    }

    @Test
    public void testAuthHeaderWithAdditionalHeaders() throws UnsupportedEncodingException {
        StaticCredentialsProvider provider = new StaticCredentialsProvider("ak", "sk");
        Credentials cred = provider.getCredentials();
        RequestMessage request = RequestMessage.newBuilder()
                .method("PUT")
                .uri("http://bucket.oss-cn-hangzhou.aliyuncs.com")
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-head1", "value");
        headers.put("abc", "value");
        headers.put("ZAbc", "value");
        headers.put("XYZ", "value");
        headers.put("content-type", "text/plain");
        headers.put("x-oss-content-sha256", "UNSIGNED-PAYLOAD");
        request = updateRequestHeaders(request, headers);

        SigningContext context = new SigningContext();
        context.setBucket("bucket");
        context.setKey("1234+-/123/1.txt");
        context.setRequest(request);
        context.setCredentials(cred);
        context.setProduct("oss");
        context.setRegion("cn-hangzhou");
        context.setSignTime(Instant.ofEpochSecond(1702747512));
        context.setAdditionalHeaders(Arrays.asList("ZAbc", "abc"));

        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value1");
        parameters.put("+param1", "value3");
        parameters.put("|param1", "value4");
        parameters.put("+param2", "");
        parameters.put("|param2", "");
        parameters.put("param2", "");

        Optional<String> query = encodeQueryParameters(parameters);
        StringBuilder url = new StringBuilder();
        url.append(request.uri().getScheme()).append("://").append(request.uri().getAuthority()).append(request.uri().getPath());
        query.ifPresent(s -> url.append("?").append(s));

        context.setRequest(RequestMessage.newBuilder()
                .method(request.method())
                .headers(request.headers())
                .uri(url.toString())
                .build());

        SignerV4 signer = new SignerV4();
        signer.sign(context);

        String expectedAuth = "OSS4-HMAC-SHA256 Credential=ak/20231216/cn-hangzhou/oss/aliyun_v4_request,AdditionalHeaders=abc;zabc,Signature=4a4183c187c07c8947db7620deb0a6b38d9fbdd34187b6dbaccb316fa251212f";
        assertEquals(expectedAuth, context.getRequest().headers().get("Authorization"));

        // With default signed header
        request = RequestMessage.newBuilder()
                .method("PUT")
                .uri("http://bucket.oss-cn-hangzhou.aliyuncs.com")
                .build();
        headers = new HashMap<>();
        headers.put("x-oss-head1", "value");
        headers.put("abc", "value");
        headers.put("ZAbc", "value");
        headers.put("XYZ", "value");
        headers.put("content-type", "text/plain");
        headers.put("x-oss-content-sha256", "UNSIGNED-PAYLOAD");
        request = updateRequestHeaders(request, headers);

        context = new SigningContext();
        context.setBucket("bucket");
        context.setKey("1234+-/123/1.txt");
        context.setRequest(request);
        context.setCredentials(cred);
        context.setProduct("oss");
        context.setRegion("cn-hangzhou");
        context.setSignTime(Instant.ofEpochSecond(1702747512));
        context.setAdditionalHeaders(Arrays.asList("x-oss-no-exist", "ZAbc", "x-oss-head1", "abc"));

        StringBuilder url2 = new StringBuilder();
        query.ifPresent(s -> url2.append("?").append(s));

        context.setRequest(RequestMessage.newBuilder()
                .method(request.method())
                .headers(request.headers())
                .uri(url2.toString())
                .build());

        signer = new SignerV4();
        signer.sign(context);

        assertEquals(expectedAuth, context.getRequest().headers().get("Authorization"));
    }

    @Test
    public void testAuthQuery() throws UnsupportedEncodingException {
        StaticCredentialsProvider provider = new StaticCredentialsProvider("ak", "sk");
        Credentials cred = provider.getCredentials();
        RequestMessage request = RequestMessage.newBuilder()
                .method("PUT")
                .uri("http://bucket.oss-cn-hangzhou.aliyuncs.com")
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-head1", "value");
        headers.put("abc", "value");
        headers.put("ZAbc", "value");
        headers.put("XYZ", "value");
        headers.put("content-type", "application/octet-stream");
        request = updateRequestHeaders(request, headers);

        SigningContext context = new SigningContext();
        context.setBucket("bucket");
        context.setKey("1234+-/123/1.txt");
        context.setRequest(request);
        context.setCredentials(cred);
        context.setProduct("oss");
        context.setRegion("cn-hangzhou");
        context.setSignTime(Instant.ofEpochSecond(1702781677));
        context.setExpiration(Instant.ofEpochSecond(1702782276));
        context.setAuthMethodQuery(true);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value1");
        parameters.put("+param1", "value3");
        parameters.put("|param1", "value4");
        parameters.put("+param2", "");
        parameters.put("|param2", "");
        parameters.put("param2", "");


        Optional<String> query = encodeQueryParameters(parameters);
        StringBuilder url = new StringBuilder();
        url.append(request.uri().getScheme()).append("://").append(request.uri().getAuthority()).append(request.uri().getPath());
        query.ifPresent(s -> url.append("?").append(s));

        context.setRequest(RequestMessage.newBuilder()
                .method(request.method())
                .headers(request.headers())
                .uri(url.toString())
                .build());

        SignerV4 signer = new SignerV4();
        signer.sign(context);

        Map<String, String> queries = uriEncodedParams(context.getRequest().uri());

        assertEquals("OSS4-HMAC-SHA256", queries.get("x-oss-signature-version"));
        assertEquals("599", queries.get("x-oss-expires"));
        assertEquals("ak%2F20231217%2Fcn-hangzhou%2Foss%2Faliyun_v4_request", queries.get("x-oss-credential"));
        assertEquals("a39966c61718be0d5b14e668088b3fa07601033f6518ac7b523100014269c0fe", queries.get("x-oss-signature"));
        assertNull(queries.get("x-oss-additional-headers"));
    }

    @Test
    public void testAuthQueryWithToken() throws UnsupportedEncodingException {
        StaticCredentialsProvider provider = new StaticCredentialsProvider("ak", "sk", "token");
        Credentials cred = provider.getCredentials();
        RequestMessage request = RequestMessage.newBuilder()
                .method("PUT")
                .uri("http://bucket.oss-cn-hangzhou.aliyuncs.com")
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-head1", "value");
        headers.put("abc", "value");
        headers.put("ZAbc", "value");
        headers.put("XYZ", "value");
        headers.put("content-type", "application/octet-stream");
        request = updateRequestHeaders(request, headers);

        SigningContext context = new SigningContext();
        context.setBucket("bucket");
        context.setKey("1234+-/123/1.txt");
        context.setRequest(request);
        context.setCredentials(cred);
        context.setProduct("oss");
        context.setRegion("cn-hangzhou");
        context.setSignTime(Instant.ofEpochSecond(1702785388));
        context.setExpiration(Instant.ofEpochSecond(1702785987));
        context.setAuthMethodQuery(true);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value1");
        parameters.put("+param1", "value3");
        parameters.put("|param1", "value4");
        parameters.put("+param2", "");
        parameters.put("|param2", "");
        parameters.put("param2", "");

        Optional<String> query = encodeQueryParameters(parameters);
        StringBuilder url = new StringBuilder();
        url.append(request.uri().getScheme()).append("://").append(request.uri().getAuthority()).append(request.uri().getPath());
        query.ifPresent(s -> url.append("?").append(s));

        context.setRequest(RequestMessage.newBuilder()
                .method(request.method())
                .headers(request.headers())
                .uri(url.toString())
                .build());

        SignerV4 signer = new SignerV4();
        signer.sign(context);

        Map<String, String> queries = uriEncodedParams(context.getRequest().uri());

        assertEquals("OSS4-HMAC-SHA256", queries.get("x-oss-signature-version"));
        assertEquals("20231217T035628Z", queries.get("x-oss-date"));
        assertEquals("599", queries.get("x-oss-expires"));
        assertEquals("ak%2F20231217%2Fcn-hangzhou%2Foss%2Faliyun_v4_request", queries.get("x-oss-credential"));
        assertEquals("3817ac9d206cd6dfc90f1c09c00be45005602e55898f26f5ddb06d7892e1f8b5", queries.get("x-oss-signature"));
        assertNull(queries.get("x-oss-additional-headers"));
    }

    @Test
    public void testAuthQueryWithAdditionalHeaders() throws UnsupportedEncodingException {
        StaticCredentialsProvider provider = new StaticCredentialsProvider("ak", "sk");
        Credentials cred = provider.getCredentials();
        RequestMessage request = RequestMessage.newBuilder()
                .method("PUT")
                .uri("http://bucket.oss-cn-hangzhou.aliyuncs.com")
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-head1", "value");
        headers.put("abc", "value");
        headers.put("ZAbc", "value");
        headers.put("XYZ", "value");
        headers.put("content-type", "application/octet-stream");
        request = updateRequestHeaders(request, headers);

        SigningContext context = new SigningContext();
        context.setBucket("bucket");
        context.setKey("1234+-/123/1.txt");
        context.setRequest(request);
        context.setCredentials(cred);
        context.setProduct("oss");
        context.setRegion("cn-hangzhou");
        context.setSignTime(Instant.ofEpochSecond(1702783809));
        context.setExpiration(Instant.ofEpochSecond(1702784408));
        context.setAuthMethodQuery(true);
        context.setAdditionalHeaders(Arrays.asList("ZAbc", "abc"));

        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value1");
        parameters.put("+param1", "value3");
        parameters.put("|param1", "value4");
        parameters.put("+param2", "");
        parameters.put("|param2", "");
        parameters.put("param2", "");

        Optional<String> query = encodeQueryParameters(parameters);
        StringBuilder url = new StringBuilder();
        url.append(request.uri().getScheme()).append("://").append(request.uri().getAuthority()).append(request.uri().getPath());
        query.ifPresent(s -> url.append("?").append(s));

        context.setRequest(RequestMessage.newBuilder()
                .method(request.method())
                .headers(request.headers())
                .uri(url.toString())
                .build());

        SignerV4 signer = new SignerV4();
        signer.sign(context);

        Map<String, String> queries = uriEncodedParams(context.getRequest().uri());

        assertEquals("OSS4-HMAC-SHA256", queries.get("x-oss-signature-version"));
        assertEquals("20231217T033009Z", queries.get("x-oss-date"));
        assertEquals("599", queries.get("x-oss-expires"));
        assertEquals("ak%2F20231217%2Fcn-hangzhou%2Foss%2Faliyun_v4_request", queries.get("x-oss-credential"));
        assertEquals("6bd984bfe531afb6db1f7550983a741b103a8c58e5e14f83ea474c2322dfa2b7", queries.get("x-oss-signature"));
        assertEquals("abc%3Bzabc", queries.get("x-oss-additional-headers"));

        // With default signed header
        request = RequestMessage.newBuilder()
                .method("PUT")
                .uri("http://bucket.oss-cn-hangzhou.aliyuncs.com")
                .build();
        headers = new HashMap<>();
        headers.put("x-oss-head1", "value");
        headers.put("abc", "value");
        headers.put("ZAbc", "value");
        headers.put("XYZ", "value");
        headers.put("content-type", "application/octet-stream");
        request = updateRequestHeaders(request, headers);

        context = new SigningContext();
        context.setBucket("bucket");
        context.setKey("1234+-/123/1.txt");
        context.setRequest(request);
        context.setCredentials(cred);
        context.setProduct("oss");
        context.setRegion("cn-hangzhou");
        context.setSignTime(Instant.ofEpochSecond(1702783809));
        context.setExpiration(Instant.ofEpochSecond(1702784408));
        context.setAuthMethodQuery(true);
        context.setAdditionalHeaders(Arrays.asList("x-oss-no-exist", "ZAbc", "x-oss-head1", "abc"));


        query = encodeQueryParameters(parameters);
        StringBuilder url2 = new StringBuilder();
        query.ifPresent(s -> url2.append("?").append(s));

        context.setRequest(RequestMessage.newBuilder()
                .method(request.method())
                .headers(request.headers())
                .uri(url2.toString())
                .build());

        signer = new SignerV4();
        signer.sign(context);

        queries = uriEncodedParams(context.getRequest().uri());

        assertEquals("OSS4-HMAC-SHA256", queries.get("x-oss-signature-version"));
        assertEquals("20231217T033009Z", queries.get("x-oss-date"));
        assertEquals("599", queries.get("x-oss-expires"));
        assertEquals("ak%2F20231217%2Fcn-hangzhou%2Foss%2Faliyun_v4_request", queries.get("x-oss-credential"));
        assertEquals("6bd984bfe531afb6db1f7550983a741b103a8c58e5e14f83ea474c2322dfa2b7", queries.get("x-oss-signature"));
        assertEquals("abc%3Bzabc", queries.get("x-oss-additional-headers"));
    }

    private RequestMessage updateRequestHeaders(RequestMessage request, Map<String, String> headers) throws UnsupportedEncodingException {
        Map<String, String> newHeaders = new HashMap<>(request.headers());
        newHeaders.putAll(headers);
        return RequestMessage.newBuilder()
                .method(request.method())
                .uri(request.uri())
                .headers(newHeaders)
                .build();
    }
}
