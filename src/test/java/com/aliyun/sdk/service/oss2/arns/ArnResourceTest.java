package com.aliyun.sdk.service.oss2.arns;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ArnResourceTest {

    @Test
    public void testBuilder_WithAllFields() {
        ArnResource resource = ArnResource.builder()
                .resourceType("bucket")
                .resource("my-bucket")
                .qualifier("obj")
                .build();

        assertEquals(Optional.of("bucket"), resource.resourceType());
        assertEquals("my-bucket", resource.resource());
        assertEquals(Optional.of("obj"), resource.qualifier());
    }

    @Test
    public void testBuilder_WithOnlyResource() {
        ArnResource resource = ArnResource.builder()
                .resource("my-bucket")
                .build();

        assertEquals(Optional.empty(), resource.resourceType());
        assertEquals("my-bucket", resource.resource());
        assertEquals(Optional.empty(), resource.qualifier());
    }

    @Test
    public void testBuilder_WithResourceAndQualifier() {
        ArnResource resource = ArnResource.builder()
                .resource("my-bucket")
                .qualifier("obj")
                .build();

        assertEquals(Optional.empty(), resource.resourceType());
        assertEquals("my-bucket", resource.resource());
        assertEquals(Optional.of("obj"), resource.qualifier());
    }

    @Test(expected = NullPointerException.class)
    public void testBuilder_WithoutResource_ThrowsException() {
        ArnResource.builder().build();
    }

    @Test
    public void testFromString_SimpleResource() {
        ArnResource resource = ArnResource.fromString("my-bucket");

        assertEquals(Optional.empty(), resource.resourceType());
        assertEquals("my-bucket", resource.resource());
        assertEquals(Optional.empty(), resource.qualifier());
    }

    @Test
    public void testFromString_WithResourceTypeAndResource() {
        ArnResource resource = ArnResource.fromString("bucket:my-bucket");

        assertEquals(Optional.of("bucket"), resource.resourceType());
        assertEquals("my-bucket", resource.resource());
        assertEquals(Optional.empty(), resource.qualifier());
    }

    @Test
    public void testFromString_WithResourceTypeResourceAndQualifier() {
        ArnResource resource = ArnResource.fromString("bucket:my-bucket:obj");

        assertEquals(Optional.of("bucket"), resource.resourceType());
        assertEquals("my-bucket", resource.resource());
        assertEquals(Optional.of("obj"), resource.qualifier());
    }

    @Test
    public void testFromString_WithSlashSplitter() {
        ArnResource resource = ArnResource.fromString("bucket/my-bucket/obj");

        assertEquals(Optional.of("bucket"), resource.resourceType());
        assertEquals("my-bucket", resource.resource());
        assertEquals(Optional.of("obj"), resource.qualifier());
    }

    @Test
    public void testFromString_WithSlashSplitter1() {
        ArnResource resource = ArnResource.fromString("bucket/my-bucket/");

        assertEquals(Optional.of("bucket"), resource.resourceType());
        assertEquals("my-bucket", resource.resource());
        assertEquals(Optional.of(""), resource.qualifier());
    }

    @Test
    public void testFromString_ComplexResourceName() {
        ArnResource resource = ArnResource.fromString("bucket:my-bucket-name-123");

        assertEquals(Optional.of("bucket"), resource.resourceType());
        assertEquals("my-bucket-name-123", resource.resource());
        assertEquals(Optional.empty(), resource.qualifier());
    }

    @Test
    public void testFromString_ComplexQualifier() {
        ArnResource resource = ArnResource.fromString("bucket:my-bucket:obj/file.txt");

        assertEquals(Optional.of("bucket"), resource.resourceType());
        assertEquals("my-bucket", resource.resource());
        assertEquals(Optional.of("obj/file.txt"), resource.qualifier());
    }

    @Test
    public void testToString_WithAllFields() {
        ArnResource resource = ArnResource.builder()
                .resourceType("bucket")
                .resource("my-bucket")
                .qualifier("obj")
                .build();

        assertEquals("bucket:my-bucket:obj", resource.toString());
    }

    @Test
    public void testToString_WithNullFields() {
        ArnResource resource = ArnResource.builder()
                .resource("my-bucket")
                .build();

        assertEquals("null:my-bucket:null", resource.toString());
    }

    @Test
    public void testEquals_SameObject() {
        ArnResource resource = ArnResource.builder()
                .resourceType("bucket")
                .resource("my-bucket")
                .qualifier("obj")
                .build();

        assertEquals(resource, resource);
    }

    @Test
    public void testEquals_EqualObjects() {
        ArnResource resource1 = ArnResource.builder()
                .resourceType("bucket")
                .resource("my-bucket")
                .qualifier("obj")
                .build();

        ArnResource resource2 = ArnResource.builder()
                .resourceType("bucket")
                .resource("my-bucket")
                .qualifier("obj")
                .build();

        assertEquals(resource1, resource2);
        assertEquals(resource1.hashCode(), resource2.hashCode());
    }

    @Test
    public void testEquals_DifferentResourceType() {
        ArnResource resource1 = ArnResource.builder()
                .resourceType("bucket")
                .resource("my-bucket")
                .qualifier("obj")
                .build();

        ArnResource resource2 = ArnResource.builder()
                .resourceType("object")
                .resource("my-bucket")
                .qualifier("obj")
                .build();

        assertNotEquals(resource1, resource2);
    }

    @Test
    public void testEquals_DifferentResource() {
        ArnResource resource1 = ArnResource.builder()
                .resourceType("bucket")
                .resource("my-bucket")
                .qualifier("obj")
                .build();

        ArnResource resource2 = ArnResource.builder()
                .resourceType("bucket")
                .resource("other-bucket")
                .qualifier("obj")
                .build();

        assertNotEquals(resource1, resource2);
    }

    @Test
    public void testEquals_DifferentQualifier() {
        ArnResource resource1 = ArnResource.builder()
                .resourceType("bucket")
                .resource("my-bucket")
                .qualifier("obj")
                .build();

        ArnResource resource2 = ArnResource.builder()
                .resourceType("bucket")
                .resource("my-bucket")
                .qualifier("file")
                .build();

        assertNotEquals(resource1, resource2);
    }

    @Test
    public void testEquals_NullQualifier() {
        ArnResource resource1 = ArnResource.builder()
                .resourceType("bucket")
                .resource("my-bucket")
                .build();

        ArnResource resource2 = ArnResource.builder()
                .resourceType("bucket")
                .resource("my-bucket")
                .qualifier(null)
                .build();

        assertEquals(resource1, resource2);
    }

    @Test
    public void testEquals_NullResourceType() {
        ArnResource resource1 = ArnResource.builder()
                .resource("my-bucket")
                .qualifier("obj")
                .build();

        ArnResource resource2 = ArnResource.builder()
                .resourceType(null)
                .resource("my-bucket")
                .qualifier("obj")
                .build();

        assertEquals(resource1, resource2);
    }

    @Test
    public void testEquals_NullObject() {
        ArnResource resource = ArnResource.builder()
                .resource("my-bucket")
                .build();

        assertNotEquals(resource, null);
    }

    @Test
    public void testEquals_DifferentClass() {
        ArnResource resource = ArnResource.builder()
                .resource("my-bucket")
                .build();

        assertNotEquals(resource, "my-bucket");
    }

    @Test
    public void testHashCode_ConsistentWithEquals() {
        ArnResource resource1 = ArnResource.builder()
                .resourceType("bucket")
                .resource("my-bucket")
                .qualifier("obj")
                .build();

        ArnResource resource2 = ArnResource.builder()
                .resourceType("bucket")
                .resource("my-bucket")
                .qualifier("obj")
                .build();

        assertEquals(resource1, resource2);
        assertEquals(resource1.hashCode(), resource2.hashCode());
    }

    @Test
    public void testToBuilder() {
        ArnResource original = ArnResource.builder()
                .resourceType("bucket")
                .resource("my-bucket")
                .qualifier("obj")
                .build();

        ArnResource copy = original.toBuilder().build();

        assertEquals(original, copy);
    }

    @Test
    public void testToBuilder_WithModification() {
        ArnResource original = ArnResource.builder()
                .resourceType("bucket")
                .resource("my-bucket")
                .qualifier("obj")
                .build();

        ArnResource modified = original.toBuilder()
                .resourceType("object")
                .build();

        assertNotEquals(original, modified);
        assertEquals(Optional.of("object"), modified.resourceType());
    }
}
