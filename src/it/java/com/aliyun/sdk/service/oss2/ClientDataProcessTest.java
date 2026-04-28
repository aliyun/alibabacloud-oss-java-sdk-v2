package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientDataProcessTest extends TestBase {

    @Test
    public void testDoMetaQueryAction() throws Exception {
        try (OSSClient client = getOssClient()) {
            // null body
            try {
                DoMetaQueryActionResult putResult = client.doMetaQueryAction(DoMetaQueryActionRequest.newBuilder()
                        .bucket(bucketName)
                        .action("createDataset")
                        .build());
                Assert.fail("should not here");
            } catch (Exception e) {
                ServiceException se = ServiceException.asCause(e);
                assertThat(se).isNotNull();
                assertThat(se.errorCode()).isEqualTo("MethodNotAllowed");
                assertThat(se.requestTarget()).contains("MetaQuery=&action=createDataset");
                assertThat(se.requestTarget()).contains(bucketName + ".");
            }

            // non-null body
            try {
                DoMetaQueryActionResult putResult = client.doMetaQueryAction(DoMetaQueryActionRequest.newBuilder()
                        .bucket(bucketName)
                        .action("createDataset")
                        .body(BinaryData.fromString(""))
                        .build());
                Assert.fail("should not here");
            } catch (Exception e) {
                ServiceException se = ServiceException.asCause(e);
                assertThat(se).isNotNull();
                assertThat(se.errorCode()).isEqualTo("MethodNotAllowed");
                assertThat(se.requestTarget()).contains("MetaQuery=&action=createDataset");
                assertThat(se.requestTarget()).contains(bucketName + ".");
            }
        }
    }

    @Test
    public void testDoMetaQueryActionRequiredField() throws Exception {
        try (OSSClient client = getOssClient()) {

            // Bucket
            try {
                client.doMetaQueryAction(DoMetaQueryActionRequest.newBuilder()
                        .build());
                Assert.fail("should not here");
            } catch (Exception e) {
                assertThat(e.getMessage()).contains("request.bucket is required");
            }

            // Action
            try {
                client.doMetaQueryAction(DoMetaQueryActionRequest.newBuilder()
                        .bucket(bucketName)
                        .build());

            } catch (Exception e) {
                assertThat(e.getMessage()).contains("request.action is required");
            }
        }
    }


    @Test
    public void testDoDataPipelineAction() throws Exception {
        try (OSSClient client = getOssClient()) {

            // null body
            try {
                client.doDataPipelineAction(DoDataPipelineActionRequest.newBuilder()
                        .action("putDataPipelineConfiguration")
                        .build());
                Assert.fail("should not here");
            } catch (Exception e) {
                ServiceException se = ServiceException.asCause(e);
                assertThat(se).isNotNull();
                assertThat(se.requestTarget()).contains("dataPipeline=&action=putDataPipelineConfiguration");
                assertThat(se.requestTarget()).doesNotContain(bucketName);
            }

            // non-null body
            try {
                client.doDataPipelineAction(DoDataPipelineActionRequest.newBuilder()
                        .action("putDataPipelineConfiguration")
                        .body(BinaryData.fromString(""))
                        .build());
                Assert.fail("should not here");
            } catch (Exception e) {
                ServiceException se = ServiceException.asCause(e);
                assertThat(se).isNotNull();
                assertThat(se.requestTarget()).contains("dataPipeline=&action=putDataPipelineConfiguration");
                assertThat(se.requestTarget()).doesNotContain(bucketName);
            }
        }
    }

    @Test
    public void testDoDataPipelineActionRequiredField() throws Exception {
        try (OSSClient client = getOssClient()) {

            // Action
            try {
                client.doDataPipelineAction(DoDataPipelineActionRequest.newBuilder()
                        .build());

            } catch (Exception e) {
                assertThat(e.getMessage()).contains("request.action is required");
            }
        }
    }
}