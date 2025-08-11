package com.aliyun.sdk.service.oss2.utils;

import com.aliyun.sdk.service.oss2.Validate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.aliyun.sdk.service.oss2.utils.FunctionalUtils.invokeSafely;

public class HttpUtils {
    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Characters that we need to fix up after URLEncoder.encode().
     */
    private static final String[] ENCODED_CHARACTERS_WITH_SLASHES = new String[]{"+", "*", "%7E", "%2F"};
    private static final String[] ENCODED_CHARACTERS_WITH_SLASHES_REPLACEMENTS = new String[]{"%20", "%2A", "~", "/"};

    private static final String[] ENCODED_CHARACTERS_WITHOUT_SLASHES = new String[]{"+", "*", "%7E"};
    private static final String[] ENCODED_CHARACTERS_WITHOUT_SLASHES_REPLACEMENTS = new String[]{"%20", "%2A", "~"};

    private HttpUtils() {
    }

    /**
     * Encode a string according to RFC 3986: encoding for URI paths, query strings, etc.
     */
    public static String urlEncode(String value) {
        return urlEncode(value, false);
    }

    /**
     * Encode a string according to RFC 3986, but ignore "/" characters.
     * This is useful for encoding the components of a path without encoding the path separators.
     */
    public static String urlEncodePath(String value) {
        return urlEncode(value, true);
    }

    /**
     * Decode the string according to RFC 3986: encoding for URI paths, query strings, etc.
     *
     * @param value The string to decode.
     * @return The decoded string.
     */
    public static String urlDecode(String value) {
        if (value == null) {
            return null;
        }
        try {
            return URLDecoder.decode(value, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unable to decode value", e);
        }
    }

    /**
     * Encode a string for use in the path of a URL.
     *
     * @param value         the value to encode
     * @param ignoreSlashes true if the value is intended to represent a path
     * @return the encoded value
     */
    private static String urlEncode(String value, boolean ignoreSlashes) {
        if (value == null) {
            return null;
        }

        String encoded = invokeSafely(() -> URLEncoder.encode(value, DEFAULT_ENCODING));

        if (!ignoreSlashes) {
            return StringUtils.replaceEach(encoded,
                    ENCODED_CHARACTERS_WITHOUT_SLASHES,
                    ENCODED_CHARACTERS_WITHOUT_SLASHES_REPLACEMENTS);
        }

        return StringUtils.replaceEach(encoded,
                ENCODED_CHARACTERS_WITH_SLASHES,
                ENCODED_CHARACTERS_WITH_SLASHES_REPLACEMENTS);
    }

    /**
     * Append the given path to the given baseUri, separating them with a slash, if required.
     */
    public static String appendUri(String baseUri, String path) {
        Validate.paramNotNull(baseUri, "baseUri");
        StringBuilder resultUri = new StringBuilder(baseUri);

        if (!StringUtils.isEmpty(path)) {
            if (!baseUri.endsWith("/")) {
                resultUri.append("/");
            }

            resultUri.append(path.startsWith("/") ? path.substring(1) : path);
        }

        return resultUri.toString();
    }

    /**
     * Encode request parameters to URL segment.
     */
    public static Optional<String> encodeQueryParameters(Map<String, String> parameters) {

        if (parameters == null || parameters.isEmpty()) {
            return Optional.empty();
        }

        boolean first = true;
        StringBuilder queryString = new StringBuilder();
        for (Map.Entry<String, String> p : parameters.entrySet()) {
            String key = p.getKey();
            String value = p.getValue();

            if (!first) {
                queryString.append("&");
            }
            queryString.append(urlEncode(key));
            if (value != null) {
                queryString.append("=").append(urlEncode(value));
            }
            first = false;
        }

        return Optional.of(queryString.toString());
    }

    /**
     * Extracts query parameters from the given URI
     */
    public static Map<String, String> uriParams(URI uri) {
        return splitQueryString(uri.getRawQuery())
                .stream()
                .map(s -> s.split("="))
                .map(s -> s.length == 0 ? new String[]{""} : s)
                .map(s -> s.length == 1 ? new String[]{s[0], ""} : s)
                .map(s -> new String[]{urlDecode(s[0]), urlDecode(s[1])})
                .collect(Collectors.toMap(s -> s[0], s -> s[1], (oldValue, newValue) -> oldValue));
    }

    /**
     * Extracts query parameters from the given URI, do not do url decode
     */
    public static Map<String, String> uriEncodedParams(URI uri) {
        return splitQueryString(uri.getRawQuery())
                .stream()
                .map(s -> s.split("="))
                .map(s -> s.length == 0 ? new String[]{""} : s)
                .map(s -> s.length == 1 ? new String[]{s[0], ""} : s)
                .collect(Collectors.toMap(s -> s[0], s -> s[1], (oldValue, newValue) -> oldValue));
    }

    public static List<String> splitQueryString(String queryString) {
        List<String> results = new ArrayList<>();
        if (StringUtils.isEmpty(queryString)) {
            return results;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < queryString.length(); i++) {
            char character = queryString.charAt(i);
            if (character != '&') {
                result.append(character);
            } else {
                results.add(StringUtils.trimToEmpty(result.toString()));
                result.setLength(0);
            }
        }
        results.add(StringUtils.trimToEmpty(result.toString()));
        return results;
    }
}
