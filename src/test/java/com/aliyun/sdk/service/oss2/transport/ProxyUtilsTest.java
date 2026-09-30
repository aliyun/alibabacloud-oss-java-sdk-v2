package com.aliyun.sdk.service.oss2.transport;

import org.junit.Test;

import java.net.Proxy;
import java.net.URI;
import java.util.List;

import static org.junit.Assert.*;

public class ProxyUtilsTest {

    @Test
    public void parseFullUrl() {
        ProxyUtils.ProxyInfo info = ProxyUtils.parse("http://proxy.example.com:8080");
        assertNotNull(info);
        assertEquals("http", info.scheme);
        assertEquals("proxy.example.com", info.host);
        assertEquals(8080, info.port);
        assertFalse(info.hasCredentials());
    }

    @Test
    public void parseBareHostPort() {
        ProxyUtils.ProxyInfo info = ProxyUtils.parse("proxy.example.com:3128");
        assertNotNull(info);
        assertEquals("http", info.scheme);
        assertEquals("proxy.example.com", info.host);
        assertEquals(3128, info.port);
    }

    @Test
    public void parseDefaultPortByScheme() {
        assertEquals(443, ProxyUtils.parse("https://p.example.com").port);
        assertEquals(80, ProxyUtils.parse("http://p.example.com").port);
    }

    @Test
    public void parseWithCredentials() {
        ProxyUtils.ProxyInfo info = ProxyUtils.parse("http://user:pass@proxy.example.com:8080");
        assertNotNull(info);
        assertTrue(info.hasCredentials());
        assertEquals("user", info.username);
        assertEquals("pass", info.password);
    }

    @Test
    public void parseBlankReturnsNull() {
        assertNull(ProxyUtils.parse(null));
        assertNull(ProxyUtils.parse(""));
        assertNull(ProxyUtils.parse("   "));
    }

    @Test
    public void environmentSelectorReturnsDirectWithoutEnv() {
        // Only meaningful when no proxy is configured in the environment running the test.
        if (ProxyUtils.httpsProxyEnv() != null || ProxyUtils.httpProxyEnv() != null) {
            return;
        }
        List<Proxy> proxies = ProxyUtils.environmentProxySelector()
                .select(URI.create("https://bucket.oss-cn-hangzhou.aliyuncs.com/"));
        assertEquals(1, proxies.size());
        assertSame(Proxy.NO_PROXY, proxies.get(0));
    }
}
