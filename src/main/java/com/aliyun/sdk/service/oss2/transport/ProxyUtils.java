package com.aliyun.sdk.service.oss2.transport;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Helpers for resolving HTTP proxy settings, from an explicit proxy URL or from the
 * {@code HTTPS_PROXY} / {@code HTTP_PROXY} / {@code NO_PROXY} environment variables
 * (following the semantics of {@code http.ProxyFromEnvironment} in the OSS Go SDK).
 */
public final class ProxyUtils {

    private ProxyUtils() {
    }

    /**
     * Parsed proxy details, decoupled from any specific HTTP client library.
     */
    public static final class ProxyInfo {
        public final String scheme;
        public final String host;
        public final int port;
        public final String username;
        public final String password;

        ProxyInfo(String scheme, String host, int port, String username, String password) {
            this.scheme = scheme;
            this.host = host;
            this.port = port;
            this.username = username;
            this.password = password;
        }

        public boolean hasCredentials() {
            return username != null && !username.isEmpty();
        }
    }

    /**
     * Parses a proxy URL such as {@code http://user:pass@host:8080} into a {@link ProxyInfo}.
     * Returns {@code null} when the value is blank or cannot be parsed into a host.
     */
    public static ProxyInfo parse(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        String raw = value.trim();
        // Allow bare "host:port" without scheme.
        if (!raw.contains("://")) {
            raw = "http://" + raw;
        }
        try {
            URI uri = new URI(raw);
            String host = uri.getHost();
            if (host == null || host.isEmpty()) {
                return null;
            }
            String scheme = uri.getScheme() == null ? "http" : uri.getScheme().toLowerCase(Locale.ROOT);
            int port = uri.getPort();
            if (port < 0) {
                port = "https".equals(scheme) ? 443 : 80;
            }
            String username = null;
            String password = null;
            String userInfo = uri.getUserInfo();
            if (userInfo != null && !userInfo.isEmpty()) {
                int idx = userInfo.indexOf(':');
                if (idx >= 0) {
                    username = userInfo.substring(0, idx);
                    password = userInfo.substring(idx + 1);
                } else {
                    username = userInfo;
                    password = "";
                }
            }
            return new ProxyInfo(scheme, host, port, username, password);
        } catch (Exception e) {
            return null;
        }
    }

    private static String env(String upper, String lower) {
        String v = System.getenv(upper);
        if (v == null || v.isEmpty()) {
            v = System.getenv(lower);
        }
        return v;
    }

    public static String httpsProxyEnv() {
        return env("HTTPS_PROXY", "https_proxy");
    }

    public static String httpProxyEnv() {
        return env("HTTP_PROXY", "http_proxy");
    }

    public static String noProxyEnv() {
        return env("NO_PROXY", "no_proxy");
    }

    /**
     * Returns {@code true} when the given host should bypass the proxy according to the
     * {@code NO_PROXY} environment variable (comma separated; entries may start with a dot
     * to match sub-domains; {@code *} matches everything).
     */
    public static boolean isNoProxy(String host) {
        if (host == null) {
            return false;
        }
        String noProxy = noProxyEnv();
        if (noProxy == null || noProxy.trim().isEmpty()) {
            return false;
        }
        String h = host.toLowerCase(Locale.ROOT);
        for (String entry : noProxy.split(",")) {
            String e = entry.trim().toLowerCase(Locale.ROOT);
            if (e.isEmpty()) {
                continue;
            }
            if ("*".equals(e)) {
                return true;
            }
            // Strip a leading dot for suffix matching.
            String suffix = e.startsWith(".") ? e.substring(1) : e;
            if (h.equals(suffix) || h.endsWith("." + suffix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Builds a {@link ProxySelector} honoring {@code HTTPS_PROXY} / {@code HTTP_PROXY} /
     * {@code NO_PROXY}. HTTPS requests use {@code HTTPS_PROXY}, other requests use
     * {@code HTTP_PROXY}; hosts matching {@code NO_PROXY} go direct.
     */
    public static ProxySelector environmentProxySelector() {
        return new ProxySelector() {
            @Override
            public List<Proxy> select(URI uri) {
                List<Proxy> direct = Collections.singletonList(Proxy.NO_PROXY);
                if (uri == null || uri.getHost() == null) {
                    return direct;
                }
                if (isNoProxy(uri.getHost())) {
                    return direct;
                }
                boolean https = "https".equalsIgnoreCase(uri.getScheme());
                ProxyInfo info = parse(https ? httpsProxyEnv() : httpProxyEnv());
                if (info == null) {
                    return direct;
                }
                SocketAddress addr = InetSocketAddress.createUnresolved(info.host, info.port);
                List<Proxy> proxies = new ArrayList<>(1);
                proxies.add(new Proxy(Proxy.Type.HTTP, addr));
                return proxies;
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, java.io.IOException ioe) {
                // no-op
            }
        };
    }
}
