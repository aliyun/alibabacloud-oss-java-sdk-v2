package com.aliyun.sdk.service.oss2.signer;

import com.aliyun.sdk.service.oss2.credentials.Credentials;
import com.aliyun.sdk.service.oss2.transport.RequestMessage;
import com.aliyun.sdk.service.oss2.utils.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.aliyun.sdk.service.oss2.utils.HttpUtils.*;

/**
 * Implements the OSS V4 signature protocol for request signing.
 */
public class VectorsSignerV4 implements Signer {
    /**
     * Date-time format string used for ISO8601 formatted timestamps.
     */
    private static final String ISO8601_FORMAT = "yyyyMMdd'T'HHmmss'Z'";

    /**
     * The algorithm name used for signing: HmacSHA256.
     */
    private static final String ALGORITHM = "HmacSHA256";

    /**
     * Format template for building a signing scope string.
     */
    private static final String SCOPE_FORMAT = "%s/%s/%s/aliyun_v4_request";

    /**
     * Checks whether the specified HTTP header is a default signed header.
     *
     * @param key HTTP header name
     * @return true if it's a default signed header, false otherwise
     */
    private static boolean isDefaultSignHeader(String key) {
        return key.startsWith("x-oss-") || key.equals("content-type") || key.equals("content-md5");
    }

    private final String userId;

    public VectorsSignerV4(String userId) {
        this.userId = userId;
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
            throw new IllegalArgumentException("SigningContext cannot be null");
        }

        if (signingCtx.getCredentials() == null) {
            throw new IllegalArgumentException("SigningContext.credentials is null");
        }

        RequestMessage request = signingCtx.getRequest();
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        if (this.userId == null || this.userId.isEmpty()) {
            throw new IllegalArgumentException("VectorsSignerV4.userId is null");
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
        Instant now = getSignTime(signingCtx);

        String iso8601Date = now.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern(ISO8601_FORMAT));
        String rfc2822Date = formatRfc2822(now);
        String dateScope = now.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        request.headers().put("x-oss-date", iso8601Date);
        request.headers().put("Date", rfc2822Date);

        if (cred.securityToken() != null && !cred.securityToken().isEmpty()) {
            request.headers().put("x-oss-security-token", cred.securityToken());
        }

        request.headers().put("x-oss-content-sha256", "UNSIGNED-PAYLOAD");

        String scope = buildScope(dateScope, signingCtx.getRegion(), signingCtx.getProduct());
        Set<String> additionalHeaders = commonAdditionalHeaders(request, signingCtx.getAdditionalHeaders());

        String region = signingCtx.getRegion() == null ? "" : signingCtx.getRegion();
        String product = signingCtx.getProduct() == null ? "" : signingCtx.getProduct();

        String canonicalRequest = calcCanonicalRequest(signingCtx, additionalHeaders);
        String stringToSign = calcStringToSign(iso8601Date, scope, canonicalRequest);
        String signature = calcSignature(cred.accessKeySecret(), dateScope,
                region, product, stringToSign);

        StringBuilder authHeader = new StringBuilder("OSS4-HMAC-SHA256 Credential=")
                .append(cred.accessKeyId()).append("/").append(scope);

        if (!additionalHeaders.isEmpty()) {
            authHeader.append(",AdditionalHeaders=").append(String.join(";", additionalHeaders));
        }
        authHeader.append(",Signature=").append(signature);

        request.headers().put("Authorization", authHeader.toString());

        signingCtx.setStringToSign(stringToSign);
        signingCtx.setSignTime(now);

        //System.out.printf("canonicalRequest:\n%s\n", canonicalRequest);
        //System.out.printf("signature:\n%s\n", signature);
    }

    /**
     * Authenticates the request using query parameters.
     *
     * @param signingCtx The context containing signing information
     */
    private void authQuery(SigningContext signingCtx) {
        RequestMessage request = signingCtx.getRequest();
        Credentials cred = signingCtx.getCredentials();
        Instant now = getSignTime(signingCtx);
        Instant expiration = getExpirationTime(signingCtx);

        String iso8601Date = now.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern(ISO8601_FORMAT));
        String dateScope = now.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long expires = Duration.between(now, expiration).getSeconds();

        String scope = buildScope(dateScope, signingCtx.getRegion(), signingCtx.getProduct());
        Set<String> additionalHeaders = commonAdditionalHeaders(request, signingCtx.getAdditionalHeaders());

        Map<String, String> parameters = uriParams(request.uri());
        removeSignatureParams(parameters);

        parameters.put("x-oss-signature-version", "OSS4-HMAC-SHA256");
        parameters.put("x-oss-date", iso8601Date);
        parameters.put("x-oss-expires", String.valueOf(expires));
        parameters.put("x-oss-credential", cred.accessKeyId() + "/" + scope);

        if (cred.securityToken() != null && !cred.securityToken().isEmpty()) {
            parameters.put("x-oss-security-token", cred.securityToken());
        }

        if (!additionalHeaders.isEmpty()) {
            parameters.put("x-oss-additional-headers", String.join(";", additionalHeaders));
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

        String region = signingCtx.getRegion() == null ? "" : signingCtx.getRegion();
        String product = signingCtx.getProduct() == null ? "" : signingCtx.getProduct();

        String signature = calcSignature(cred.accessKeySecret(), dateScope,
                region, product,
                calcStringToSign(iso8601Date, scope, calcCanonicalRequest(signingCtx, additionalHeaders)));

        url.append("&x-oss-signature=").append(urlEncode(signature));

        signingCtx.setRequest(request.toBuilder()
                .uri(url.toString())
                .build());
        signingCtx.setStringToSign(calcStringToSign(iso8601Date, scope, calcCanonicalRequest(signingCtx, additionalHeaders)));
        signingCtx.setSignTime(now);
        signingCtx.setExpiration(expiration);
    }

    /**
     * Gets the current signing time, preferring the one from the context.
     *
     * @param signingCtx The context containing signing time info
     * @return The timestamp used for signing
     */
    private Instant getSignTime(SigningContext signingCtx) {
        if (signingCtx.getSignTime() == null) {
            return Instant.now();
        }
        return signingCtx.getSignTime();
    }

    /**
     * Gets the expiration time of the signature, preferring the one from the context.
     *
     * @param signingCtx The context containing expiration info
     * @return The expiration timestamp
     */
    private Instant getExpirationTime(SigningContext signingCtx) {
        if (signingCtx.getExpiration() == null) {
            return Instant.now().plus(15, ChronoUnit.MINUTES);
        }
        return signingCtx.getExpiration();
    }

    /**
     * Builds the signing scope string.
     *
     * @param date    Signing date
     * @param region  Region identifier
     * @param product Product identifier
     * @return The constructed scope string
     */
    private String buildScope(String date, String region, String product) {
        return String.format(SCOPE_FORMAT, date, region, product);
    }

    /**
     * Extracts and normalizes additional headers that should be signed.
     *
     * @param request           Current request object
     * @param additionalHeaders Optional list of headers to include in signing
     * @return A sorted and deduplicated set of header keys
     */
    private Set<String> commonAdditionalHeaders(RequestMessage request, List<String> additionalHeaders) {
        Set<String> result = new TreeSet<>();
        if (additionalHeaders == null || request == null) {
            return result;
        }

        for (String additional : additionalHeaders) {
            String lowerKey = additional.toLowerCase();
            for (Map.Entry<String, String> header : request.headers().entrySet()) {
                String headerKey = header.getKey().toLowerCase();
                if (!isDefaultSignHeader(lowerKey) && headerKey.equals(lowerKey)) {
                    result.add(lowerKey);
                }
            }
        }
        return result;
    }

    /**
     * Builds the canonical request string used for signing.
     *
     * @param signingCtx        The signing context object
     * @param additionalHeaders Additional headers to include in signing
     * @return The canonical request string
     */
    private String calcCanonicalRequest(SigningContext signingCtx, Set<String> additionalHeaders) {
        RequestMessage request = signingCtx.getRequest();

        String canonicalUri = buildCanonicalUri(signingCtx);
        String canonicalQuery = buildCanonicalQuery(request.uri());
        String canonicalHeaders = buildCanonicalHeaders(request, additionalHeaders);
        String canonicalAdditional = String.join(";", additionalHeaders);
        String payloadHash = request.headers().getOrDefault("x-oss-content-sha256", "UNSIGNED-PAYLOAD");

        return String.join("\n",
                request.method(),
                canonicalUri,
                canonicalQuery,
                canonicalHeaders,
                canonicalAdditional,
                payloadHash);
    }

    /**
     * Builds the canonical URI path for signing.
     *
     * @param signingCtx The signing context object
     * @return The URL-encoded URI string
     */
    private String buildCanonicalUri(SigningContext signingCtx) {
        //'/acs:ossvector:{signing_ctx.region}:{self._uid}:'
        StringBuilder uri = new StringBuilder(String.format("/acs:ossvector:%s:%s", signingCtx.getRegion(), this.userId));
        if (!StringUtils.isNullOrEmpty(signingCtx.getBucket())) {
            uri.append(signingCtx.getBucket()).append("/");
        }
        if (!StringUtils.isNullOrEmpty(signingCtx.getKey())) {
            uri.append(signingCtx.getKey());
        }
        return urlEncodePath(uri.toString());
    }

    /**
     * Builds the canonical query part for signing.
     *
     * @param uri The request URI
     * @return The encoded query parameter string
     */
    private String buildCanonicalQuery(URI uri) {
        Map<String, String> queryMap = uriEncodedParams(uri);

        return queryMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    return value.isEmpty() ? key : key + "=" + value;
                })
                .collect(Collectors.joining("&"));
    }

    /**
     * Builds the canonical headers section for signature calculation.
     *
     * @param request           The request object
     * @param additionalHeaders Additional headers to include in signing
     * @return The canonical headers string
     */
    private String buildCanonicalHeaders(RequestMessage request, Set<String> additionalHeaders) {
        Map<String, String> lowKeyMap = new HashMap<>();
        for (Map.Entry<String, String> entry : request.headers().entrySet()) {
            String key = entry.getKey().toLowerCase();
            if (entry.getValue() == null || entry.getValue().isEmpty()) {
                continue;
            }
            if (isSignHeader(key, additionalHeaders)) {
                lowKeyMap.put(key, entry.getValue());
            }
        }

        return lowKeyMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + ":" + entry.getValue() + "\n")
                .collect(Collectors.joining());
    }

    /**
     * Constructs the string to be signed.
     *
     * @param iso8601Date      ISO8601-formatted date string
     * @param scope            Signing scope
     * @param canonicalRequest Canonical request string
     * @return The full string to be signed
     */
    private String calcStringToSign(String iso8601Date, String scope, String canonicalRequest) {
        String hash = sha256(canonicalRequest);
        return String.join("\n", "OSS4-HMAC-SHA256", iso8601Date, scope, hash);
    }

    /**
     * Calculates the signature using HmacSHA256 algorithm.
     *
     * @param accessKeySecret The secret key used for signing
     * @param date            Signing date
     * @param region          Region identifier
     * @param product         Product identifier
     * @param stringToSign    The string to sign
     * @return Hex-encoded signature result
     */
    private String calcSignature(String accessKeySecret, String date, String region, String product, String stringToSign) {
        byte[] key = ("aliyun_v4" + accessKeySecret).getBytes(StandardCharsets.UTF_8);

        byte[] dateKey = hmacSha256(key, date.getBytes(StandardCharsets.UTF_8));
        byte[] regionKey = hmacSha256(dateKey, region.getBytes(StandardCharsets.UTF_8));
        byte[] productKey = hmacSha256(regionKey, product.getBytes(StandardCharsets.UTF_8));
        byte[] requestKey = hmacSha256(productKey, "aliyun_v4_request".getBytes(StandardCharsets.UTF_8));
        byte[] signatureBytes = hmacSha256(requestKey, stringToSign.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(signatureBytes);
    }

    /**
     * Performs HMAC-SHA256 calculation.
     *
     * @param key  HMAC key bytes
     * @param data Data to be signed
     * @return Calculated HMAC result as byte array
     */
    private byte[] hmacSha256(byte[] key, byte[] data) {
        try {
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(new SecretKeySpec(key, ALGORITHM));
            return mac.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("HMAC calculation failed", e);
        }
    }

    /**
     * Performs HMAC-SHA256 calculation and returns hex string result.
     *
     * @param key  HMAC key bytes
     * @param data Data to be signed
     * @return Hex-encoded HMAC result string
     */
    private String hmacSha256Hex(byte[] key, byte[] data) {
        return bytesToHex(hmacSha256(key, data));
    }

    /**
     * Calculates the SHA-256 digest of the given string.
     *
     * @param input Input string
     * @return Hex-encoded digest result
     */
    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return bytesToHex(digest.digest(input.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("SHA256 calculation failed", e);
        }
    }

    /**
     * Converts a byte array to its hexadecimal string representation.
     *
     * @param bytes Input byte array
     * @return Hex string representation of the bytes
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xFF));
        }
        return sb.toString();
    }

    /**
     * Formats the timestamp into RFC 2822 format.
     *
     * @param instant Timestamp object
     * @return RFC 2822 formatted date-time string
     */
    private String formatRfc2822(Instant instant) {
        return instant.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME);
    }

    /**
     * Removes query parameters related to signing to avoid duplication.
     *
     * @param params Query parameter map
     */
    private void removeSignatureParams(Map<String, String> params) {
        params.remove("x-oss-signature");
        params.remove("x-oss-security-token");
        params.remove("x-oss-additional-headers");
    }

    /**
     * Determines if the given HTTP header should be signed.
     *
     * @param key               HTTP header name
     * @param additionalHeaders Additional headers to consider
     * @return true if the header should be included in the signature, false otherwise
     */
    private boolean isSignHeader(String key, Set<String> additionalHeaders) {
        return isDefaultSignHeader(key) ||
                (additionalHeaders != null && additionalHeaders.contains(key));
    }
}
