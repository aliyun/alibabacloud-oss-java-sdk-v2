package com.aliyun.sdk.service.oss2.signer;

import com.aliyun.sdk.service.oss2.credentials.Credentials;
import com.aliyun.sdk.service.oss2.transport.RequestMessage;
import com.aliyun.sdk.service.oss2.utils.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.aliyun.sdk.service.oss2.utils.HttpUtils.*;

/**
 * Implements the OSS V1 signature protocol for request signing.
 */
public class SignerV1 implements Signer {
    /**
     * A set containing all query parameters that should be treated as sub-resources.
     */
    private static final List<String> SUBRESOURCE_KEY_SET = Arrays.asList("acl", "bucketInfo", "location", "stat", "delete", "append",
            "tagging", "objectMeta", "uploads", "uploadId", "partNumber",
            "security-token", "position", "response-content-type", "response-content-language",
            "response-expires", "response-cache-control", "response-content-disposition",
            "response-content-encoding", "restore", "callback", "callback-var",
            "versions", "versioning", "versionId", "sequential", "continuation-token",
            "regionList", "cloudboxes", "symlink", "resourceGroup", "cleanRestoredObject");

    /**
     * Checks whether the specified HTTP header should be included in the signature.
     *
     * @param header The HTTP header name
     * @return true if the header should be signed, false otherwise
     */
    public static boolean isSignedHeader(String header) {
        return isDefaultSignHeader(header.toLowerCase());
    }

    /**
     * Checks whether the specified HTTP header is a default signed header.
     *
     * @param key The HTTP header name
     * @return true if it's a default signed header, false otherwise
     */
    private static boolean isDefaultSignHeader(String key) {
        return key.startsWith("x-oss-") || key.equals("content-type") || key.equals("content-md5") || key.equals("date");
    }

    /**
     * Determines if the given HTTP header should be signed.
     *
     * @param key The HTTP header name
     * @return true if the header should be signed, false otherwise
     */
    private static boolean isSignHeader(String key) {
        return key != null && key.startsWith("x-oss-");
    }

    /**
     * Extracts the date value from HTTP headers.
     *
     * @param headers The HTTP headers map
     * @return The extracted date string
     */
    private static String getDateFromHeaders(Map<String, String> headers) {

        String xOssDate = headers.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase("x-oss-date"))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);

        String dateHeader = headers.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase("date"))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);

        String date = (xOssDate != null && !xOssDate.isEmpty()) ? xOssDate : (dateHeader != null ? dateHeader : "");

        return date;
    }

    /**
     * Signs the given request context.
     * Decides whether to use header-based or query-based authentication.
     *
     * @param signingCtx The context containing signing information
     */
    @Override
    public void sign(SigningContext signingCtx) {
        if (signingCtx == null) {
            throw new IllegalArgumentException("SigningContext is null");
        }

        if (signingCtx.getCredentials() == null) {
            throw new IllegalArgumentException("SigningContext.credentials is null");
        }

        if (signingCtx.getRequest() == null) {
            throw new IllegalArgumentException("SigningContext.request is null");
        }

        if (signingCtx.isAuthMethodQuery()) {
            authQuery(signingCtx);
        } else {
            authHeader(signingCtx);
        }
    }

    /**
     * Authenticates the request using HTTP headers.
     *
     * @param signingCtx The context containing signing information
     */
    private void authHeader(SigningContext signingCtx) {
        RequestMessage request = signingCtx.getRequest();
        Credentials cred = signingCtx.getCredentials();

        ZonedDateTime datetimeNow;
        if (signingCtx.getSignTime() == null) {
            datetimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
        } else {
            datetimeNow = signingCtx.getSignTime().atZone(ZoneId.of("UTC"));
        }

        String dateRfc2822 = formatDateTimeRfc2822(datetimeNow);
        request.headers().put("Date", dateRfc2822);

        if (!StringUtils.isEmpty(cred.securityToken())) {
            request.headers().put("security-token", cred.securityToken());
        }

        String stringToSign = calcStringToSign(signingCtx, null);
        String signature = calcSignature(cred.accessKeySecret(), stringToSign);

        String credentialHeader = "OSS " + cred.accessKeyId() + ":" + signature;
        request.headers().put("Authorization", credentialHeader);

        signingCtx.setStringToSign(stringToSign);
        signingCtx.setSignTime(Instant.from(datetimeNow));
    }

    /**
     * Authenticates the request using query parameters.
     *
     * @param signingCtx The context containing signing information
     */
    private void authQuery(SigningContext signingCtx) {

        RequestMessage request = signingCtx.getRequest();

        Credentials cred = signingCtx.getCredentials();

        ZonedDateTime datetimeNow;
        if (signingCtx.getSignTime() == null) {
            datetimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
        } else {
            datetimeNow = signingCtx.getSignTime().atZone(ZoneId.of("UTC"));
        }

        ZonedDateTime expirationTime;
        if (signingCtx.getExpiration() == null) {
            expirationTime = datetimeNow.plusMinutes(15);
        } else {
            expirationTime = signingCtx.getExpiration().atZone(ZoneId.of("UTC"));
        }

        String expires = String.valueOf(expirationTime.toEpochSecond());

        Map<String, String> parameters = uriParams(request.uri());
        parameters.remove("Signature");
        parameters.remove("security-token");

        parameters.put("OSSAccessKeyId", cred.accessKeyId());
        parameters.put("Expires", expires);

        if (!StringUtils.isEmpty(cred.securityToken())) {
            parameters.put("security-token", cred.securityToken());
        }

        Optional<String> query = encodeQueryParameters(parameters);

        StringBuilder url = new StringBuilder();
        url.append(request.uri().getScheme())
                .append("://")
                .append(request.uri().getRawAuthority())
                .append(request.uri().getRawPath());
        query.ifPresent(s -> url.append("?").append(s));

        signingCtx.setRequest(request.toBuilder()
                .uri(url.toString())
                .build());

        String stringToSign = calcStringToSign(signingCtx, expires);
        String signature = calcSignature(cred.accessKeySecret(), stringToSign);

        url.append("&Signature=").append(urlEncode(signature));
        signingCtx.setRequest(request.toBuilder()
                .uri(url.toString())
                .build());
        signingCtx.setStringToSign(stringToSign);
        signingCtx.setSignTime(Instant.from(datetimeNow));
        signingCtx.setExpiration(Instant.from(expirationTime));
    }

    /**
     * Builds the canonical string to be signed.
     *
     * @param signingCtx The context containing signing information
     * @param date       An optional date string to include
     * @return The canonical string to be signed
     */
    private String calcStringToSign(SigningContext signingCtx, String date) {
        RequestMessage request = signingCtx.getRequest();

        String canonicalUri = "/";

        if (!StringUtils.isNullOrEmpty(signingCtx.getBucket())) {
            canonicalUri += signingCtx.getBucket() + "/";
        }

        if (!StringUtils.isNullOrEmpty(signingCtx.getKey())) {
            canonicalUri += signingCtx.getKey();
        }

        String canonicalQuery = "";
        Map<String, String> queryMap = uriEncodedParams(request.uri());
        List<Map.Entry<String, String>> sortedEntries = new ArrayList<>(queryMap.entrySet());
        Collections.sort(sortedEntries, Map.Entry.comparingByKey());

        List<String> queryParts = new ArrayList<>();
        for (Map.Entry<String, String> entry : sortedEntries) {
            String key = urlDecode(entry.getKey());
            String value = urlDecode(entry.getValue());
            if (SUBRESOURCE_KEY_SET.contains(key) || (signingCtx.getSubResource() != null && signingCtx.getSubResource().contains(key))) {
                if (!value.isEmpty()) {
                    queryParts.add(key + "=" + value);
                } else {
                    queryParts.add(key);
                }
            }
        }
        if (!queryParts.isEmpty()) {
            canonicalQuery = "?" + String.join("&", queryParts);
        }

        String canonicalResource = canonicalUri + canonicalQuery;

        String canonicalHeaders = buildCanonicalHeaders(request.headers());

        String contentMd5 = "";
        String contentType = "";

        for (Map.Entry<String, String> entry : request.headers().entrySet()) {
            String key = entry.getKey().toLowerCase();
            if (key.equals("content-md5")) {
                contentMd5 = entry.getValue();
            } else if (key.equals("content-type")) {
                contentType = entry.getValue();
            }
        }

        String dateHeader = date != null ? date : getDateFromHeaders(request.headers());

        return String.join("\n",
                request.method(),
                contentMd5,
                contentType,
                dateHeader,
                canonicalHeaders + canonicalResource
        );
    }

    /**
     * Calculates the signature using HmacSHA1 algorithm.
     *
     * @param accessKeySecret The secret key used for signing
     * @param stringToSign    The canonical string to sign
     * @return The Base64 encoded signature result
     */
    private String calcSignature(String accessKeySecret, String stringToSign) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA1");
            SecretKeySpec keySpec = new SecretKeySpec(accessKeySecret.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
            hmac.init(keySpec);
            byte[] signatureBytes = hmac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalArgumentException("Failed to calculate signature", e);
        }
    }

    /**
     * Formats a time value into RFC 2822 format.
     *
     * @param zonedDateTime The time to be formatted
     * @return The formatted date-time string in RFC 2822 format
     */
    private String formatDateTimeRfc2822(ZonedDateTime zonedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        return zonedDateTime.format(formatter);
    }

    /**
     * Builds the canonical headers section for signature calculation.
     *
     * @param headers The HTTP headers map
     * @return The canonical headers string
     */
    private String buildCanonicalHeaders(Map<String, String> headers) {
        Map<String, String> lowKeyMap = new HashMap<>();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey().toLowerCase();
            if (entry.getValue() == null || entry.getValue().isEmpty()) {
                continue;
            }
            if (isSignHeader(key)) {
                lowKeyMap.put(key, entry.getValue());
            }
        }

        return lowKeyMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + ":" + entry.getValue() + "\n")
                .collect(Collectors.joining());
    }

}

