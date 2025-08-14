package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.utils.IOUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpHead;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.ProtocolException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

public class ClientPresignTest extends TestBase {

    private static OSSClient clientV4_;

    private static OSSClient clientV1_;


    @BeforeClass
    public static void oneTimeSetUp() {
        TestBase.oneTimeSetUp();

        CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
        clientV4_ = OSSClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .credentialsProvider(provider)
                .build();

        clientV1_ = OSSClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .signatureVersion("v1")
                .credentialsProvider(provider)
                .build();
    }

    @AfterClass
    public static void oneTimeSetDown() {
        try {
            clientV4_.close();
            clientV1_.close();
        } catch (Exception ignore) {
        }
        TestBase.oneTimeSetDown();
    }

    @Test
    public void presignGetAndPutObjectOnlyBody() throws IOException, ParseException {
        OSSClient client = clientV4_;
        String objectName = "root/sub-folder/1+2.bin";

        String content= "hello world";

        String resultContent = null;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            // put object
            PresignResult result = client.presign(PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build());

            assertThat(result.url()).isNotNull();
            assertThat(result.url()).contains("/root/sub-folder/1%2B2.bin?", "x-oss-expires=900", "x-oss-signature-version=OSS4-HMAC-SHA256");
            assertThat(result.method()).isEqualTo("PUT");
            assertThat(result.expiration()).isPresent();
            assertThat(result.signedHeaders()).isPresent();
            assertThat(result.signedHeaders().get()).isEmpty();

            HttpPut httpPut = new HttpPut(result.url());
            httpPut.setEntity(new StringEntity(content, (ContentType) null));

            try (CloseableHttpResponse response = httpclient.execute(httpPut)) {
                assertThat(response.getCode()).isEqualTo(200);
            }

            // get object
            result = client.presign(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build());

            assertThat(result.url()).isNotNull();
            assertThat(result.url()).contains("/root/sub-folder/1%2B2.bin?", "x-oss-expires=900", "x-oss-signature-version=OSS4-HMAC-SHA256");
            assertThat(result.method()).isEqualTo("GET");
            assertThat(result.expiration()).isPresent();
            assertThat(result.signedHeaders()).isPresent();
            assertThat(result.signedHeaders().get()).isEmpty();

            HttpGet httpGet = new HttpGet(result.url());
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                assertThat(response.getCode()).isEqualTo(200);
                HttpEntity entity = response.getEntity();
                resultContent = EntityUtils.toString(entity);
                assertThat(resultContent).isEqualTo(content);
            }

            // head object
            result = client.presign(HeadObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build());

            assertThat(result.url()).isNotNull();
            assertThat(result.url()).contains("/root/sub-folder/1%2B2.bin?", "x-oss-expires=900", "x-oss-signature-version=OSS4-HMAC-SHA256");
            assertThat(result.method()).isEqualTo("HEAD");
            assertThat(result.expiration()).isPresent();
            assertThat(result.signedHeaders()).isPresent();
            assertThat(result.signedHeaders().get()).isEmpty();

            HttpHead httpHead = new HttpHead(result.url());
            try (CloseableHttpResponse response = httpclient.execute(httpHead)) {
                assertThat(response.getCode()).isEqualTo(200);
                assertThat(response.getEntity()).isNull();
            }
        }
    }

    @Test
    public void presignGetAndPutObjectFullProps() throws IOException, ProtocolException {
        OSSClient client = clientV4_;
        String objectName = "root/sub-folder/full /1+2.bin";

        String content= "hello world";

        String resultContent = null;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            // put object
            PresignResult result = client.presign(PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .storageClass("IA")
                    .objectAcl("private")
                    .contentDisposition("1.txt")
                    .cacheControl("no-cache")
                    .contentEncoding("deflate")
                    .expires("Wed, 21 Oct 2015 07:28:00 GMT")
                    .contentType("text/txt")
                    .contentMd5("XrY7u+Ae7tCTyyK7j1rNww==")
                    .metadata(MapUtils.of("key1", "value1", "key2", "value2"))
                    .tagging("tag-key1=val1")
                    .build());

            assertThat(result.url()).isNotNull();
            assertThat(result.url()).contains("/root/sub-folder/full%20/1%2B2.bin?", "x-oss-expires=900", "x-oss-signature-version=OSS4-HMAC-SHA256");
            assertThat(result.method()).isEqualTo("PUT");
            assertThat(result.expiration()).isPresent();
            assertThat(result.signedHeaders()).isPresent();
            assertThat(result.signedHeaders().get()).isNotEmpty();

            HttpPut httpPut = new HttpPut(result.url());
            httpPut.setEntity(new StringEntity(content, (ContentType) null));
            result.signedHeaders().get().forEach((k, v)->{
                httpPut.setHeader(k, v);
            });

            try (CloseableHttpResponse response = httpclient.execute(httpPut)) {
                assertThat(response.getCode()).isEqualTo(200);
            }

            // get object
            result = client.presign(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build());

            assertThat(result.url()).isNotNull();
            assertThat(result.url()).contains("/root/sub-folder/full%20/1%2B2.bin?", "x-oss-expires=900", "x-oss-signature-version=OSS4-HMAC-SHA256");
            assertThat(result.method()).isEqualTo("GET");
            assertThat(result.expiration()).isPresent();
            assertThat(result.signedHeaders()).isPresent();
            assertThat(result.signedHeaders().get()).isEmpty();

            HttpGet httpGet = new HttpGet(result.url());
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                assertThat(response.getCode()).isEqualTo(200);
                HttpEntity entity = response.getEntity();
                resultContent = EntityUtils.toString(entity);
                assertThat(resultContent).isEqualTo(content);
                assertThat(response.getHeader("x-oss-storage-class").getValue()).isEqualTo("IA");
                assertThat(response.getHeader("Content-Type").getValue()).isEqualTo("text/txt");

                assertThat(response.getHeader("x-oss-meta-key1").getValue()).isEqualTo("value1");
                assertThat(response.getHeader("x-oss-meta-key2").getValue()).isEqualTo("value2");
            }

            // head object
            result = client.presign(HeadObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build());

            assertThat(result.url()).isNotNull();
            assertThat(result.url()).contains("/root/sub-folder/full%20/1%2B2.bin?", "x-oss-expires=900", "x-oss-signature-version=OSS4-HMAC-SHA256");
            assertThat(result.method()).isEqualTo("HEAD");
            assertThat(result.expiration()).isPresent();
            assertThat(result.signedHeaders()).isPresent();
            assertThat(result.signedHeaders().get()).isEmpty();

            HttpHead httpHead = new HttpHead(result.url());
            try (CloseableHttpResponse response = httpclient.execute(httpHead)) {
                assertThat(response.getCode()).isEqualTo(200);
                assertThat(response.getEntity()).isNull();
                assertThat(response.getHeader("x-oss-storage-class").getValue()).isEqualTo("IA");
                assertThat(response.getHeader("Content-Type").getValue()).isEqualTo("text/txt");

                assertThat(response.getHeader("x-oss-meta-key1").getValue()).isEqualTo("value1");
                assertThat(response.getHeader("x-oss-meta-key2").getValue()).isEqualTo("value2");
            }
        }
    }

    @Test
    public void presignMultipartObject() throws Exception {
        OSSClient client = clientV4_;
        String objectName = "multipart/sub-folder/1+2.bin";

        String content= "hello world";

        String resultContent = null;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            // init
            PresignResult result = client.presign(InitiateMultipartUploadRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build());

            assertThat(result.url()).isNotNull();
            assertThat(result.url()).contains("/multipart/sub-folder/1%2B2.bin?", "uploads", "x-oss-expires=900", "x-oss-signature-version=OSS4-HMAC-SHA256");
            assertThat(result.method()).isEqualTo("POST");
            assertThat(result.expiration()).isPresent();
            assertThat(result.signedHeaders()).isPresent();
            assertThat(result.signedHeaders().get()).isEmpty();

            HttpPost httpPost = new HttpPost(result.url());
            String uploadId = "id";
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                assertThat(response.getCode()).isEqualTo(200);
                HttpEntity entity = response.getEntity();
                resultContent = EntityUtils.toString(entity);
                //System.out.println(resultContent);
                uploadId = resultContent.substring(
                        resultContent.indexOf("<UploadId>") + 10,
                        resultContent.indexOf("</UploadId>")
                );
                //System.out.println(uploadId);
            }

            // upload part
            result = client.presign(UploadPartRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .uploadId(uploadId)
                    .partNumber(1L)
                    .build());

            assertThat(result.url()).isNotNull();
            assertThat(result.url()).contains(
                    "/multipart/sub-folder/1%2B2.bin?",
                    "uploadId=",
                    "x-oss-expires=900",
                    "x-oss-signature-version=OSS4-HMAC-SHA256");
            assertThat(result.method()).isEqualTo("PUT");
            assertThat(result.expiration()).isPresent();
            assertThat(result.signedHeaders()).isPresent();
            assertThat(result.signedHeaders().get()).isEmpty();

            HttpPut httpPut = new HttpPut(result.url());
            httpPut.setEntity(new StringEntity(content, (ContentType) null));
            try (CloseableHttpResponse response = httpclient.execute(httpPut)) {
                assertThat(response.getCode()).isEqualTo(200);
            }

            // complete
            result = client.presign(CompleteMultipartUploadRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .uploadId(uploadId)
                    .completeAll("yes")
                    .build());

            assertThat(result.url()).isNotNull();
            assertThat(result.url()).contains(
                    "/multipart/sub-folder/1%2B2.bin?",
                    "uploadId=",
                    "x-oss-expires=900",
                    "x-oss-signature-version=OSS4-HMAC-SHA256");
            assertThat(result.method()).isEqualTo("POST");
            assertThat(result.expiration()).isPresent();
            assertThat(result.signedHeaders()).isPresent();
            assertThat(result.signedHeaders().get()).hasSize(1);

            HttpPost httpPost1 = new HttpPost(result.url());
            result.signedHeaders().get().forEach((k, v)->{
                httpPost1.setHeader(k, v);
            });
            try (CloseableHttpResponse response = httpclient.execute(httpPost1)) {
                assertThat(response.getCode()).isEqualTo(200);
            }

            // get object
            try (GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build())) {
                byte[] gotData  = IOUtils.toByteArray(getResult.body());
                assertThat(gotData).isEqualTo(content.getBytes());
            }
        }
    }

}
