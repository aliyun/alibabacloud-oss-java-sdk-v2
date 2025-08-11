package com.aliyun.sdk.service.oss2.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InetAddressUtils {

    private static final int MAX_BYTE = 128;

    private static final int IPV4_MAX_OCTET_VALUE = 255;

    private static final int MAX_UNSIGNED_SHORT = 0xffff;

    private static final int BASE_16 = 16;

    private static final String IPV4_REGEX = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";

    // Max number of hex groups (separated by :) in an IPV6 address
    private static final int IPV6_MAX_HEX_GROUPS = 8;

    // Max hex digits in each IPv6 group
    private static final int IPV6_MAX_HEX_DIGITS_PER_GROUP = 4;

    private static final Pattern DIGITS_PATTERN = Pattern.compile("\\d{1,3}");

    private static final Pattern ID_CHECK_PATTERN = Pattern.compile("[^\\s/%]+");

    /**
     * Checks if the specified string is a valid IPv4 or IPv6 address.
     *
     * @param inetAddress the string to validate
     * @return true if the string validates as an IP address
     */
    public static boolean isValid(final String inetAddress) {
        return isValidInet4Address(inetAddress) || isValidInet6Address(inetAddress);
    }

    /**
     * Validates an IPv4 address. Returns true if valid.
     *
     * @param inet4Address the IPv4 address to validate
     * @return true if the argument contains a valid IPv4 address
     */
    public static boolean isValidInet4Address(final String inet4Address) {
        // verify that address conforms to generic IPv4 format
        final String[] groups = ipv4Match(inet4Address);
        if (groups == null) {
            return false;
        }
        // verify that address subgroups are legal
        for (final String ipSegment : groups) {
            if (isBlankOrNull(ipSegment)) {
                return false;
            }
            int iIpSegment = 0;
            try {
                iIpSegment = Integer.parseInt(ipSegment);
            } catch (final NumberFormatException e) {
                return false;
            }
            if (iIpSegment > IPV4_MAX_OCTET_VALUE || ipSegment.length() > 1 && ipSegment.startsWith("0")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates an IPv6 address. Returns true if valid.
     *
     * @param inet6Address the IPv6 address to validate
     * @return true if the argument contains a valid IPv6 address
     */
    public static boolean isValidInet6Address(String inet6Address) {
        String[] parts;
        // remove prefix size. This will appear after the zone id (if any)
        parts = inet6Address.split("/", -1);
        if (parts.length > 2) {
            return false; // can only have one prefix specifier
        }
        if (parts.length == 2) {
            if (!DIGITS_PATTERN.matcher(parts[1]).matches()) {
                return false; // not a valid number
            }
            final int bits = Integer.parseInt(parts[1]); // cannot fail because of RE check
            if (bits < 0 || bits > MAX_BYTE) {
                return false; // out of range
            }
        }
        // remove zone-id
        parts = parts[0].split("%", -1);
        // The id syntax is implementation independent, but it presumably cannot allow:
        // whitespace, '/' or '%'
        if ((parts.length > 2) || (parts.length == 2 && !ID_CHECK_PATTERN.matcher(parts[1]).matches())) {
            return false; // invalid id
        }
        inet6Address = parts[0];
        final boolean containsCompressedZeroes = inet6Address.contains("::");
        if (containsCompressedZeroes && inet6Address.indexOf("::") != inet6Address.lastIndexOf("::")) {
            return false;
        }
        final boolean startsWithCompressed = inet6Address.startsWith("::");
        final boolean endsWithCompressed = inet6Address.endsWith("::");
        final boolean endsWithSep = inet6Address.endsWith(":");
        if (inet6Address.startsWith(":") && !startsWithCompressed || endsWithSep && !endsWithCompressed) {
            return false;
        }
        String[] octets = inet6Address.split(":");
        if (containsCompressedZeroes) {
            final List<String> octetList = new ArrayList<>(Arrays.asList(octets));
            if (endsWithCompressed) {
                // String.split() drops ending empty segments
                octetList.add("");
            } else if (startsWithCompressed && !octetList.isEmpty()) {
                octetList.remove(0);
            }
            octets = octetList.toArray(new String[0]);
        }
        if (octets.length > IPV6_MAX_HEX_GROUPS) {
            return false;
        }
        int validOctets = 0;
        int emptyOctets = 0; // consecutive empty chunks
        for (int index = 0; index < octets.length; index++) {
            final String octet = octets[index];
            if (isBlankOrNull(octet)) {
                emptyOctets++;
                if (emptyOctets > 1) {
                    return false;
                }
            } else {
                emptyOctets = 0;
                // Is last chunk an IPv4 address?
                if (index == octets.length - 1 && octet.contains(".")) {
                    if (!isValidInet4Address(octet)) {
                        return false;
                    }
                    validOctets += 2;
                    continue;
                }
                if (octet.length() > IPV6_MAX_HEX_DIGITS_PER_GROUP) {
                    return false;
                }
                int octetInt = 0;
                try {
                    octetInt = Integer.parseInt(octet, BASE_16);
                } catch (final NumberFormatException e) {
                    return false;
                }
                if (octetInt < 0 || octetInt > MAX_UNSIGNED_SHORT) {
                    return false;
                }
            }
            validOctets++;
        }
        return validOctets <= IPV6_MAX_HEX_GROUPS && (validOctets >= IPV6_MAX_HEX_GROUPS || containsCompressedZeroes);
    }

    static boolean isBlankOrNull(final String value) {
        // Don't trim is already empty.
        return value == null || value.isEmpty() || value.trim().isEmpty();
    }

    static String[] ipv4Match(final String value) {
        if (value == null) {
            return null;
        }
        Pattern pattern = Pattern.compile(IPV4_REGEX);
        final Matcher matcher = pattern.matcher(value);
        if (matcher.matches()) {
            final int count = matcher.groupCount();
            final String[] groups = new String[count];
            for (int j = 0; j < count; j++) {
                groups[j] = matcher.group(j + 1);
            }
            return groups;
        }
        return null;
    }

}
