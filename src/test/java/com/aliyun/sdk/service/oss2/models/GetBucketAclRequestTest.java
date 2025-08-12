package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketAcl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GetBucketAclRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketAclRequest request = GetBucketAclRequest.newBuilder().build();
        assertEquals(0, request.headers().size());
        assertEquals(0, request.parameters().size());
        assertNull(request.bucket());
    }

    @Test
    public void testFullBuilder() {
        GetBucketAclRequest request = GetBucketAclRequest.newBuilder()
                .bucket("bucket")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("bucket");
        assertEquals(1, request.headers().size());
        Assertions.assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertEquals(1, request.headers().size());
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertNull(request.parameters().get("null-param"));

        //to builder
        GetBucketAclRequest.Builder builder = request.toBuilder();
        request = builder
                .header("x-header-value", "value1")
                .parameters(new HashMap<>())
                .build();

        assertThat(request.bucket()).isEqualTo("bucket");
        assertEquals(1, request.headers().size());
        Assertions.assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value1"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


        GetBucketAclRequest request = GetBucketAclRequest.newBuilder()
                .bucket("examplebucket")
                .build();

        OperationInput input = SerdeBucketAcl.fromGetBucketAcl(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("acl")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");






//

//        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Owner owner = Owner.newBuilder()
                .id("test-id")
                .displayName("test-display-name")
                .build();

        AccessControlList accessControlList = AccessControlList.newBuilder()
                .grant("private")
                .build();

        AccessControlPolicy accessControlPolicy = AccessControlPolicy.newBuilder()
                .owner(owner)
                .accessControlList(accessControlList)
                .build();

        String xml = xmlMapper.writeValueAsString(accessControlPolicy);

        AccessControlPolicy value = xmlMapper.readValue(xml, AccessControlPolicy.class);

        assertEquals("test-id", value.owner().id());
        assertEquals("test-display-name", value.owner().displayName());
        assertEquals("private", value.accessControlList().grant());
    }
}
