package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import static com.aliyun.sdk.service.oss2.internal.ClientImplMockTest.findCause;

public class ClientUserDefinedLogFieldsConfigTest extends TestBase {

    @Test
    public void testUserDefinedLogFieldsConfigOperations() throws Exception {
        try (OSSClient client = getOssClient()) {

            LoggingHeaderSet headerSet = LoggingHeaderSet.newBuilder()
                    .headers(Arrays.asList("header1", "header2"))
                    .build();

            LoggingParamSet paramSet = LoggingParamSet.newBuilder()
                    .parameters(Arrays.asList("param1", "param2"))
                    .build();

            UserDefinedLogFieldsConfiguration userDefinedLogFieldsConfiguration = 
                    UserDefinedLogFieldsConfiguration.newBuilder()
                            .headerSet(headerSet)
                            .paramSet(paramSet)
                            .build();

            // putUserDefinedLogFieldsConfig
            PutUserDefinedLogFieldsConfigResult putResult = client.putUserDefinedLogFieldsConfig(
                    PutUserDefinedLogFieldsConfigRequest.newBuilder()
                            .bucket(bucketName)
                            .userDefinedLogFieldsConfiguration(userDefinedLogFieldsConfiguration)
                            .build());

            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            waitForCacheExpiration(5);

            // getUserDefinedLogFieldsConfig
            GetUserDefinedLogFieldsConfigResult getResult = client.getUserDefinedLogFieldsConfig(
                    GetUserDefinedLogFieldsConfigRequest.newBuilder()
                            .bucket(bucketName)
                            .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.userDefinedLogFieldsConfiguration());
            Assert.assertNotNull(getResult.userDefinedLogFieldsConfiguration().headerSet());
            Assert.assertNotNull(getResult.userDefinedLogFieldsConfiguration().paramSet());
            Assert.assertEquals(2, getResult.userDefinedLogFieldsConfiguration().headerSet().headers().size());
            Assert.assertTrue(getResult.userDefinedLogFieldsConfiguration().headerSet().headers().contains("header1"));
            Assert.assertTrue(getResult.userDefinedLogFieldsConfiguration().headerSet().headers().contains("header2"));
            Assert.assertEquals(2, getResult.userDefinedLogFieldsConfiguration().paramSet().parameters().size());
            Assert.assertTrue(getResult.userDefinedLogFieldsConfiguration().paramSet().parameters().contains("param1"));
            Assert.assertTrue(getResult.userDefinedLogFieldsConfiguration().paramSet().parameters().contains("param2"));

            // deleteUserDefinedLogFieldsConfig
            DeleteUserDefinedLogFieldsConfigResult deleteResult = client.deleteUserDefinedLogFieldsConfig(
                    DeleteUserDefinedLogFieldsConfigRequest.newBuilder()
                            .bucket(bucketName)
                            .build());

            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());

            try {
                GetUserDefinedLogFieldsConfigResult getResultAfterDelete = client.getUserDefinedLogFieldsConfig(
                        GetUserDefinedLogFieldsConfigRequest.newBuilder()
                                .bucket(bucketName)
                                .build());

                Assert.assertNotNull(getResultAfterDelete);
                Assert.assertEquals(200, getResultAfterDelete.statusCode());
                Assert.assertNull(getResultAfterDelete.userDefinedLogFieldsConfiguration());
            } catch (Exception ec) {
                ServiceException serr = findCause(ec, ServiceException.class);
                Assert.assertEquals(404, serr.statusCode());
                Assert.assertEquals("NoSuchUserDefinedLogFieldsConfig", serr.errorCode());
            }
        }
    }
}