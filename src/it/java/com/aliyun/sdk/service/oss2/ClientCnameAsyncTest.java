package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import static com.aliyun.sdk.service.oss2.internal.ClientImplMockTest.findCause;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientCnameAsyncTest extends TestBase {

    @Test
    public void testCnameOperations() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String domain = "example.com";

        // Put cname
        Cname cname = Cname.newBuilder()
                .domain(domain)
                .build();

        BucketCnameConfiguration bucketCnameConfiguration = BucketCnameConfiguration.newBuilder()
                .cname(cname)
                .build();

        PutCnameRequest putRequest = PutCnameRequest.newBuilder()
                .bucket(bucketName)
                .bucketCnameConfiguration(bucketCnameConfiguration)
                .build();

        try {
            CompletableFuture<PutCnameResult> putFuture = client.putCnameAsync(putRequest);
            PutCnameResult putResult = putFuture.get();
            assertThat(putResult).isNotNull();
            assertThat(putResult.statusCode()).isEqualTo(200);
            assertThat(putResult.requestId()).isNotNull();
        } catch (ExecutionException e) {
            ServiceException serr = findCause(e, ServiceException.class);
            if (serr != null && serr.statusCode() == 403 && 
                "NeedVerifyDomainOwnership".equals(serr.errorCode())) {
                System.out.printf("Http Status Code: %d.\n", serr.statusCode());
                System.out.printf("Error Code: %s.\n", serr.errorCode());
                System.out.printf("Request Id: %s.\n", serr.requestId());
                System.out.printf("Message: %s.\n", serr.errorMessage());
                System.out.printf("EC: %s.\n", serr.ec());
                // This is expected error, the test should pass in this case
                return;
            }
            // Re-throw if it's not the expected error
            throw e;
        }

        // list cnames
        ListCnameRequest listRequest = ListCnameRequest.newBuilder()
                .bucket(bucketName)
                .build();

        CompletableFuture<ListCnameResult> listFuture = client.listCnameAsync(listRequest);
        ListCnameResult listResult = listFuture.get();
        assertThat(listResult).isNotNull();
        assertThat(listResult.statusCode()).isEqualTo(200);
        assertThat(listResult.requestId()).isNotNull();
        assertThat(listResult.bucket()).isEqualTo(bucketName);
        assertThat(listResult.cnames()).isNotNull();

        // Delete cname
        DeleteCnameRequest deleteRequest = DeleteCnameRequest.newBuilder()
                .bucket(bucketName)
                .bucketCnameConfiguration(bucketCnameConfiguration)
                .build();

        CompletableFuture<DeleteCnameResult> deleteFuture = client.deleteCnameAsync(deleteRequest);
        DeleteCnameResult deleteResult = deleteFuture.get();
        assertThat(deleteResult).isNotNull();
        assertThat(deleteResult.statusCode()).isEqualTo(200);
        assertThat(deleteResult.requestId()).isNotNull();
    }

    @Test
    public void testCnameTokenOperations() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String domain = "example.com";

        // Create cname token
        Cname cname = Cname.newBuilder()
                .domain(domain)
                .build();

        BucketCnameConfiguration bucketCnameConfiguration = BucketCnameConfiguration.newBuilder()
                .cname(cname)
                .build();

        CreateCnameTokenRequest createRequest = CreateCnameTokenRequest.newBuilder()
                .bucket(bucketName)
                .bucketCnameConfiguration(bucketCnameConfiguration)
                .build();

        CompletableFuture<CreateCnameTokenResult> createFuture = client.createCnameTokenAsync(createRequest);
        CreateCnameTokenResult createResult = createFuture.get();
        assertThat(createResult).isNotNull();
        assertThat(createResult.statusCode()).isEqualTo(200);
        assertThat(createResult.requestId()).isNotNull();
        assertThat(createResult.cnameToken().bucket()).isEqualTo(bucketName);
        assertThat(createResult.cnameToken().cname()).isEqualTo(domain);
        assertThat(createResult.cnameToken().token()).isNotNull();

        // Get cname token
        GetCnameTokenRequest getRequest = GetCnameTokenRequest.newBuilder()
                .bucket(bucketName)
                .cname(domain)
                .build();

        CompletableFuture<GetCnameTokenResult> getFuture = client.getCnameTokenAsync(getRequest);
        GetCnameTokenResult getResult = getFuture.get();
        assertThat(getResult).isNotNull();
        assertThat(getResult.statusCode()).isEqualTo(200);
        assertThat(getResult.requestId()).isNotNull();
        assertThat(getResult.cnameToken().bucket()).isEqualTo(bucketName);
        assertThat(getResult.cnameToken().cname()).isEqualTo(createResult.cnameToken().cname());
        assertThat(getResult.cnameToken().token()).isEqualTo(createResult.cnameToken().token());
    }
}