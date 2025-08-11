package com.aliyun.sdk.service.oss2.exceptions;

import java.util.Map;

/**
 * Inconsistent Error Class that indicates a mismatch between client and server CRC checksum values
 */
public class InconsistentException extends RuntimeException {

    public static final String FMT = "crc is inconsistent, client crc:%s, server crc:%s, requestId:%s.";
    private final String clientCrc;
    private final String serverCrc;
    private final Map<String, String> headers;

    /**
     * Constructor that creates an inconsistency error using the provided parameters
     *
     * @param clientCrc The client crc value
     * @param serverCrc The server crc value
     * @param headers   The response headers
     */
    public InconsistentException(String clientCrc, String serverCrc, Map<String, String> headers) {
        super(String.format(FMT, clientCrc, serverCrc, toRequestId(headers)));
        this.clientCrc = clientCrc;
        this.serverCrc = serverCrc;
        this.headers = headers;
    }

    private static String toRequestId(Map<String, String> headers) {
        if (headers == null) {
            return "";
        }
        return headers.getOrDefault("x-oss-request-id", "");
    }

    public String clientCRC() {
        return this.clientCrc;
    }

    public String serverCRC() {
        return this.serverCrc;
    }

    public Map<String, String> headers() {
        return this.headers;
    }

    public String requestId() {
        return toRequestId(this.headers);
    }
}