package com.aliyun.sdk.service.oss2.arns;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class ArnTest {

    @Test
    public void testBuilder_WithAllFields() {
        Arn arn = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .accountId("123456789012")
                .resource("bucket:my-bucket")
                .build();

        assertEquals("oss", arn.service());
        assertEquals(Optional.of("cn-hangzhou"), arn.region());
        assertEquals(Optional.of("123456789012"), arn.accountId());
        assertEquals("bucket:my-bucket", arn.resourceAsString());
        assertNotNull(arn.resource());
    }

    @Test
    public void testBuilder_WithOnlyRequiredFields() {
        Arn arn = Arn.builder()
                .service("oss")
                .resource("my-bucket")
                .build();

        assertEquals("oss", arn.service());
        assertEquals(Optional.empty(), arn.region());
        assertEquals(Optional.empty(), arn.accountId());
        assertEquals("my-bucket", arn.resourceAsString());
    }

    @Test(expected = NullPointerException.class)
    public void testBuilder_WithoutService_ThrowsException() {
        Arn.builder()
                .resource("my-bucket")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilder_WithBlankService_ThrowsException() {
        Arn.builder()
                .service("")
                .resource("my-bucket")
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void testBuilder_WithoutResource_ThrowsException() {
        Arn.builder()
                .service("oss")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilder_WithBlankResource_ThrowsException() {
        Arn.builder()
                .service("oss")
                .resource("")
                .build();
    }

    @Test
    public void testFromString_ValidArn_WithAllComponents() {
        String arnString = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket";
        Arn arn = Arn.fromString(arnString);

        assertEquals("oss", arn.service());
        assertEquals(Optional.of("cn-hangzhou"), arn.region());
        assertEquals(Optional.of("123456789012"), arn.accountId());
        assertEquals("bucket:my-bucket", arn.resourceAsString());
    }

    @Test
    public void testFromString_ValidArn_WithEmptyRegionAndAccount() {
        String arnString = "acs:oss:::bucket:my-bucket";
        Arn arn = Arn.fromString(arnString);

        assertEquals("oss", arn.service());
        assertEquals(Optional.empty(), arn.region());
        assertEquals(Optional.empty(), arn.accountId());
        assertEquals("bucket:my-bucket", arn.resourceAsString());
    }

    @Test
    public void testFromString_ValidArn_WithEmptyRegion() {
        String arnString = "acs:oss::123456789012:bucket:my-bucket";
        Arn arn = Arn.fromString(arnString);

        assertEquals("oss", arn.service());
        assertEquals(Optional.empty(), arn.region());
        assertEquals(Optional.of("123456789012"), arn.accountId());
    }

    @Test
    public void testFromString_ValidArn_WithEmptyAccount() {
        String arnString = "acs:oss:cn-hangzhou::bucket:my-bucket";
        Arn arn = Arn.fromString(arnString);

        assertEquals("oss", arn.service());
        assertEquals(Optional.of("cn-hangzhou"), arn.region());
        assertEquals(Optional.empty(), arn.accountId());
    }

    @Test
    public void testFromString_ComplexResource() {
        String arnString = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket:obj/file.txt";
        Arn arn = Arn.fromString(arnString);

        assertEquals("oss", arn.service());
        ArnResource resource = arn.resource();
        assertEquals(Optional.of("bucket"), resource.resourceType());
        assertEquals("my-bucket", resource.resource());
        assertEquals(Optional.of("obj/file.txt"), resource.qualifier());
    }

    @Test
    public void testFromString_TableArn() {
        String arnString = "acs:osstables:cn-beijing:123456:bucket/test-bucket-9326/table/ad3fca49-9de8-4e5f-8d7c-e15c2588c2ad";
        Arn arn = Arn.fromString(arnString);

        assertEquals("osstables", arn.service());
        assertEquals(Optional.of("cn-beijing"), arn.region());
        assertEquals(Optional.of("123456"), arn.accountId());

        assertTrue(arn.resource().resourceType().isPresent());
        assertEquals("bucket", arn.resource().resourceType().get());
        assertEquals("test-bucket-9326", arn.resource().resource());
        assertEquals("table/ad3fca49-9de8-4e5f-8d7c-e15c2588c2ad", arn.resource().qualifier().get());

        int index = arnString.indexOf("/table/");
        assertEquals("acs:osstables:cn-beijing:123456:bucket/test-bucket-9326", arnString.substring(0, index));
    }

    @Test
    public void testFromString_InvalidArn_NoService() {
        // When service is empty string, Validate.paramNotBlank throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            Arn.fromString("acs::cn-hangzhou:123456789012:bucket:my-bucket");
        });
    }

    @Test
    public void testFromString_InvalidArn_NoRegion() {
        // When there's no region colon after service, returns empty optional
        // Note: "acs:oss:123456789012:bucket:my-bucket" is actually valid (123456789012 becomes region)
        // To trigger no region error, we need something like acs:oss without enough colons
        Optional<Arn> result = Arn.tryFromString("acs:oss");
        assertFalse(result.isPresent());
    }

    @Test
    public void testFromString_InvalidArn_NoAccount() {
        // Similar - need truly malformed ARN
        Optional<Arn> result = Arn.tryFromString("acs:oss:cn-hangzhou");
        assertFalse(result.isPresent());
    }

    @Test
    public void testFromString_InvalidArn_NoResource() {
        // When resource is empty, throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            Arn.fromString("acs:oss:cn-hangzhou:123456789012:");
        });
    }

    @Test
    public void testFromString_InvalidArn_Null() {
        // When arn is null, fromString throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            Arn.fromString(null);
        });
    }

    @Test
    public void testTryFromString_ValidArn() {
        String arnString = "acs:oss:cn-hangzhou:123456789012:bucket:my-bucket";
        Optional<Arn> arn = Arn.tryFromString(arnString);

        assertTrue(arn.isPresent());
        assertEquals("oss", arn.get().service());
        assertEquals(Optional.of("cn-hangzhou"), arn.get().region());
        assertEquals(Optional.of("123456789012"), arn.get().accountId());
    }

    @Test
    public void testTryFromString_InvalidArn_NoAcsPrefix() {
        Optional<Arn> arn = Arn.tryFromString("ats:oss:cn-hangzhou:123456789012:bucket:my-bucket");
        assertFalse(arn.isPresent());
    }

    @Test
    public void testTryFromString_InvalidArn_NoRegion() {
        Optional<Arn> arn = Arn.tryFromString("acs:oss");
        assertFalse(arn.isPresent());
    }

    @Test
    public void testTryFromString_InvalidArn_NoAccount() {
        Optional<Arn> arn = Arn.tryFromString("acs:oss:cn-hangzhou");
        assertFalse(arn.isPresent());
    }

    @Test
    public void testTryFromString_InvalidArn_NoResource() {
        Optional<Arn> arn = Arn.tryFromString("acs:oss:cn-hangzhou:123456789012:");
        assertFalse(arn.isPresent());
    }

    @Test
    public void testToString_WithAllFields() {
        Arn arn = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .accountId("123456789012")
                .resource("bucket:my-bucket")
                .build();

        assertEquals("acs:oss:cn-hangzhou:123456789012:bucket:my-bucket", arn.toString());
    }

    @Test
    public void testToString_WithEmptyRegionAndAccount() {
        Arn arn = Arn.builder()
                .service("oss")
                .resource("my-bucket")
                .build();

        assertEquals("acs:oss:::my-bucket", arn.toString());
    }

    @Test
    public void testToString_WithEmptyRegion() {
        Arn arn = Arn.builder()
                .service("oss")
                .accountId("123456789012")
                .resource("my-bucket")
                .build();

        assertEquals("acs:oss::123456789012:my-bucket", arn.toString());
    }

    @Test
    public void testToString_WithEmptyAccount() {
        Arn arn = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .resource("my-bucket")
                .build();

        assertEquals("acs:oss:cn-hangzhou::my-bucket", arn.toString());
    }

    @Test
    public void testEquals_SameObject() {
        Arn arn = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .accountId("123456789012")
                .resource("bucket:my-bucket")
                .build();

        assertEquals(arn, arn);
    }

    @Test
    public void testEquals_EqualObjects() {
        Arn arn1 = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .accountId("123456789012")
                .resource("bucket:my-bucket")
                .build();

        Arn arn2 = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .accountId("123456789012")
                .resource("bucket:my-bucket")
                .build();

        assertEquals(arn1, arn2);
        assertEquals(arn1.hashCode(), arn2.hashCode());
    }

    @Test
    public void testEquals_DifferentService() {
        Arn arn1 = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .accountId("123456789012")
                .resource("bucket:my-bucket")
                .build();

        Arn arn2 = Arn.builder()
                .service("ots")
                .region("cn-hangzhou")
                .accountId("123456789012")
                .resource("bucket:my-bucket")
                .build();

        assertNotEquals(arn1, arn2);
    }

    @Test
    public void testEquals_DifferentRegion() {
        Arn arn1 = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .accountId("123456789012")
                .resource("bucket:my-bucket")
                .build();

        Arn arn2 = Arn.builder()
                .service("oss")
                .region("cn-shanghai")
                .accountId("123456789012")
                .resource("bucket:my-bucket")
                .build();

        assertNotEquals(arn1, arn2);
    }

    @Test
    public void testEquals_DifferentAccountId() {
        Arn arn1 = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .accountId("123456789012")
                .resource("bucket:my-bucket")
                .build();

        Arn arn2 = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .accountId("987654321098")
                .resource("bucket:my-bucket")
                .build();

        assertNotEquals(arn1, arn2);
    }

    @Test
    public void testEquals_DifferentResource() {
        Arn arn1 = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .accountId("123456789012")
                .resource("bucket:my-bucket")
                .build();

        Arn arn2 = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .accountId("123456789012")
                .resource("bucket:other-bucket")
                .build();

        assertNotEquals(arn1, arn2);
    }

    @Test
    public void testEquals_NullObject() {
        Arn arn = Arn.builder()
                .service("oss")
                .resource("my-bucket")
                .build();

        assertNotEquals(arn, null);
    }

    @Test
    public void testEquals_DifferentClass() {
        Arn arn = Arn.builder()
                .service("oss")
                .resource("my-bucket")
                .build();

        assertNotEquals(arn, "acs:oss:::my-bucket");
    }

    @Test
    public void testHashCode_ConsistentWithEquals() {
        Arn arn1 = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .accountId("123456789012")
                .resource("bucket:my-bucket")
                .build();

        Arn arn2 = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .accountId("123456789012")
                .resource("bucket:my-bucket")
                .build();

        assertEquals(arn1, arn2);
        assertEquals(arn1.hashCode(), arn2.hashCode());
    }

    @Test
    public void testToBuilder() {
        Arn original = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .accountId("123456789012")
                .resource("bucket:my-bucket")
                .build();

        Arn copy = original.toBuilder().build();

        assertEquals(original, copy);
    }

    @Test
    public void testToBuilder_WithModification() {
        Arn original = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .accountId("123456789012")
                .resource("bucket:my-bucket")
                .build();

        Arn modified = original.toBuilder()
                .region("cn-shanghai")
                .build();

        assertNotEquals(original, modified);
        assertEquals(Optional.of("cn-shanghai"), modified.region());
    }

    @Test
    public void testResource_AsArnResource() {
        Arn arn = Arn.builder()
                .service("oss")
                .region("cn-hangzhou")
                .accountId("123456789012")
                .resource("bucket:my-bucket:obj/file.txt")
                .build();

        ArnResource resource = arn.resource();
        assertEquals(Optional.of("bucket"), resource.resourceType());
        assertEquals("my-bucket", resource.resource());
        assertEquals(Optional.of("obj/file.txt"), resource.qualifier());
    }

    @Test
    public void testResource_SimpleResource() {
        Arn arn = Arn.builder()
                .service("oss")
                .resource("my-bucket")
                .build();

        ArnResource resource = arn.resource();
        assertEquals(Optional.empty(), resource.resourceType());
        assertEquals("my-bucket", resource.resource());
        assertEquals(Optional.empty(), resource.qualifier());
    }
}
