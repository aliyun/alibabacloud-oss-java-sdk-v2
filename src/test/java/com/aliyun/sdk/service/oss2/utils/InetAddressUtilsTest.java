package com.aliyun.sdk.service.oss2.utils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InetAddressUtilsTest {

    /**
     * Test obviously broken IPs.
     */
    @Test
    public void testBrokenInetAddresses() {
        assertFalse(InetAddressUtils.isValid("124.14.32.abc"));
        // TODO: there is some debate as to whether leading zeros should be allowed
        // They are ambiguous: does the leading 0 mean octal?
        assertFalse(InetAddressUtils.isValid("124.14.32.01"));
        assertFalse(InetAddressUtils.isValid("23.64.12"));
        assertFalse(InetAddressUtils.isValid("26.34.23.77.234"));
        assertFalse(InetAddressUtils.isValidInet6Address("")); // empty string
    }

    /**
     * Test valid and invalid IPs from each address class.
     */
    @Test
    public void testInetAddressesByClass() {
        assertTrue(InetAddressUtils.isValid("24.25.231.12"));
        assertFalse(InetAddressUtils.isValid("2.41.32.324"));

        assertTrue(InetAddressUtils.isValid("135.14.44.12"));
        assertFalse(InetAddressUtils.isValid("154.123.441.123"));

        assertTrue(InetAddressUtils.isValid("213.25.224.32"));
        assertFalse(InetAddressUtils.isValid("201.543.23.11"));

        assertTrue(InetAddressUtils.isValid("229.35.159.6"));
        assertFalse(InetAddressUtils.isValid("231.54.11.987"));

        assertTrue(InetAddressUtils.isValid("248.85.24.92"));
        assertFalse(InetAddressUtils.isValid("250.21.323.48"));
    }

    /**
     * Test IPs that point to real, well-known hosts (without actually looking them up).
     */
    @Test
    public void testInetAddressesFromTheWild() {
        assertTrue(InetAddressUtils.isValid("140.211.11.130"));
        assertTrue(InetAddressUtils.isValid("72.14.253.103"));
        assertTrue(InetAddressUtils.isValid("199.232.41.5"));
        assertTrue(InetAddressUtils.isValid("216.35.123.87"));
    }

    /**
     * Test IPv6 addresses.
     * <p>
     * These tests were ported from a <a href="https://download.dartware.com/thirdparty/test-ipv6-regex.pl">Perl script</a>.
     * </p>
     */
    @Test
    public void testIPv6() {
        // The original Perl script contained a lot of duplicate tests.
        // I removed the duplicates I noticed, but there may be more.
        assertFalse(InetAddressUtils.isValidInet6Address("")); // empty string
        assertTrue(InetAddressUtils.isValidInet6Address("::1")); // loopback, compressed, non-routable
        assertTrue(InetAddressUtils.isValidInet6Address("::")); // unspecified, compressed, non-routable
        assertTrue(InetAddressUtils.isValidInet6Address("0:0:0:0:0:0:0:1")); // loopback, full
        assertTrue(InetAddressUtils.isValidInet6Address("0:0:0:0:0:0:0:0")); // unspecified, full
        assertTrue(InetAddressUtils.isValidInet6Address("2001:DB8:0:0:8:800:200C:417A")); // unicast, full
        assertTrue(InetAddressUtils.isValidInet6Address("FF01:0:0:0:0:0:0:101")); // multicast, full
        assertTrue(InetAddressUtils.isValidInet6Address("2001:DB8::8:800:200C:417A")); // unicast, compressed
        assertTrue(InetAddressUtils.isValidInet6Address("FF01::101")); // multicast, compressed
        assertFalse(InetAddressUtils.isValidInet6Address("2001:DB8:0:0:8:800:200C:417A:221")); // unicast,
        // full
        assertFalse(InetAddressUtils.isValidInet6Address("FF01::101::2")); // multicast, compressed
        assertTrue(InetAddressUtils.isValidInet6Address("fe80::217:f2ff:fe07:ed62"));
        assertTrue(InetAddressUtils.isValidInet6Address("2001:0000:1234:0000:0000:C1C0:ABCD:0876"));
        assertTrue(InetAddressUtils.isValidInet6Address("3ffe:0b00:0000:0000:0001:0000:0000:000a"));
        assertTrue(InetAddressUtils.isValidInet6Address("FF02:0000:0000:0000:0000:0000:0000:0001"));
        assertTrue(InetAddressUtils.isValidInet6Address("0000:0000:0000:0000:0000:0000:0000:0001"));
        assertTrue(InetAddressUtils.isValidInet6Address("0:a:b:c:d:e:f::"));
        assertTrue(InetAddressUtils.isValidInet6Address("::0:a:b:c:d:e:f")); // syntactically correct, but bad form (::0:...
        // could be combined)
        assertTrue(InetAddressUtils.isValidInet6Address("a:b:c:d:e:f:0::"));
        assertFalse(InetAddressUtils.isValidInet6Address("':10.0.0.1"));
    }

    /**
     * Test reserved IPs.
     */
    @Test
    public void testReservedInetAddresses() {
        assertTrue(InetAddressUtils.isValid("127.0.0.1"));
        assertTrue(InetAddressUtils.isValid("255.255.255.255"));
    }

    @Test
    public void testValidator335() {
        assertTrue(InetAddressUtils.isValid("2001:0438:FFFE:0000:0000:0000:0000:0A35"));
    }

    @Test
    public void testValidator419() {
        String addr;
        addr = "0:0:0:0:0:0:13.1.68.3";
        assertTrue(InetAddressUtils.isValid(addr));
        addr = "0:0:0:0:0:FFFF:129.144.52.38";
        assertTrue(InetAddressUtils.isValid(addr));
        addr = "::13.1.68.3";
        assertTrue(InetAddressUtils.isValid(addr));
        addr = "::FFFF:129.144.52.38";
        assertTrue(InetAddressUtils.isValid(addr));

        addr = "::ffff:192.168.1.1:192.168.1.1";
        assertFalse(InetAddressUtils.isValid(addr));
        addr = "::192.168.1.1:192.168.1.1";
        assertFalse(InetAddressUtils.isValid(addr));
    }

    /**
     * Inet6Address may also contain a scope id.
     */
    @Test
    public void testValidator445() {
        final String[] valid = {"2001:0000:1234:0000:0000:C1C0:ABCD:0876", "2001:0000:1234:0000:0000:C1C0:ABCD:0876/123",
                "2001:0000:1234:0000:0000:C1C0:ABCD:0876/0", "2001:0000:1234:0000:0000:C1C0:ABCD:0876%0", "2001:0000:1234:0000:0000:C1C0:ABCD:0876%abcdefgh",};
        final String[] invalid = {"2001:0000:1234:0000:0000:C1C0:ABCD:0876/129", // too big
                "2001:0000:1234:0000:0000:C1C0:ABCD:0876/-0", // sign not allowed
                "2001:0000:1234:0000:0000:C1C0:ABCD:0876/+0", // sign not allowed
                "2001:0000:1234:0000:0000:C1C0:ABCD:0876/10O", // non-digit
                "2001:0000:1234:0000:0000:C1C0:ABCD:0876/0%0", // /bits before %node-id
                "2001:0000:1234:0000:0000:C1C0:ABCD:0876%abc defgh", // space in node id
                "2001:0000:1234:0000:0000:C1C0:ABCD:0876%abc%defgh", // '%' in node id
        };
        for (final String item : valid) {
            assertTrue(item + " should be valid", InetAddressUtils.isValid(item));
        }
        for (final String item : invalid) {
            assertFalse(item + " should be invalid", InetAddressUtils.isValid(item));
        }
    }

}
