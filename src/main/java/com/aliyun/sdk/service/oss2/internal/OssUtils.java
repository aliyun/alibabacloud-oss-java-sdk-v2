package com.aliyun.sdk.service.oss2.internal;

import com.aliyun.sdk.service.oss2.Defaults;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.types.AddressStyleType;
import com.aliyun.sdk.service.oss2.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling common logic such as endpoint construction and address formatting
 */
final class OssUtils {

    /**
     * Adds a scheme (http/https) to the given URL string if not already present
     *
     * @param value      The original URL string
     * @param disableSsl Whether to use HTTP instead of HTTPS
     * @return A URL string with the appropriate scheme prepended
     */
    public static String addScheme(String value, boolean disableSsl) {
        if (value != null && !value.matches("^[^:]+://.*")) {
            String scheme = disableSsl ? "http" : Defaults.HttpScheme;
            return scheme + "://" + value;
        }
        return value;
    }

    /**
     * Generates an OSS endpoint based on the region and endpoint type
     *
     * @param value      Region name such as "cn-hangzhou"
     * @param type       Type of the endpoint (Internal, DualStack, etc.)
     * @param disableSsl Whether to use HTTP instead of HTTPS
     * @return Formatted endpoint URL string
     */
    public static String regionToEndpoint(String value, EndpointType type, boolean disableSsl) {
        String scheme = disableSsl ? "http" : Defaults.HttpScheme;
        String endpoint;
        switch (type) {
            case DualStack:
                endpoint = String.format("%s.oss.aliyuncs.com", value);
                break;
            case Internal:
                endpoint = String.format("oss-%s-internal.aliyuncs.com", value);
                break;
            case Accelerate:
                endpoint = "oss-accelerate.aliyuncs.com";
                break;
            case Overseas:
                endpoint = "oss-accelerate-overseas.aliyuncs.com";
                break;
            default:
                endpoint = String.format("oss-%s.aliyuncs.com", value);
                break;
        }
        return String.format("%s://%s", scheme, endpoint);
    }

    /**
     * Builds the host and path portion based on the provided address style
     *
     * @param input   Operation input containing bucket and object key information
     * @param baseUrl Base domain or host name
     * @param style   Address style type (VirtualHosted / Path / CName)
     * @return Constructed host/path string based on the input and address style
     */
    public static String buildHostPath(OperationInput input, String baseUrl, AddressStyleType style) {
        List<String> paths = new ArrayList<>();
        String host = baseUrl;

        if (input.bucket().isPresent()) {
            switch (style) {
                case Path:
                    paths.add(input.bucket().get());
                    if (!input.key().isPresent()) {
                        paths.add("");
                    }
                    break;
                case CName:
                    break;
                case VirtualHosted:
                    host = String.format("%s.%s", input.bucket().get(), host);
                    break;
            }
        }

        if (input.key().isPresent()) {
            paths.add(HttpUtils.urlEncodePath(input.key().get()));
        }

        return String.format("%s/%s", host, String.join("/", paths));
    }

    /**
     * Enumeration of different types of endpoints supported by OSS service
     */
    enum EndpointType {
        Default,
        DualStack,
        Internal,
        Accelerate,
        Overseas
    }
}