package com.aliyun.sdk.service.oss2.utils;

import org.junit.Test;

import java.net.URI;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class HttpUtilsTest {

    @Test
    public void urlValuesEncodeCorrectly() {
        String nonEncodedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~";
        String encodedCharactersInput = "\t\n\r !\"#$%&'()*+,/:;<=>?@[\\]^`{|}";
        String encodedCharactersOutput = "%09%0A%0D%20%21%22%23%24%25%26%27%28%29%2A%2B%2C%2F%3A%3B%3C%3D%3E%3F%40%5B%5C%5D%5E%60%7B%7C%7D";

        assertThat(HttpUtils.urlEncode(null)).isEqualTo(null);
        assertThat(HttpUtils.urlEncode("")).isEqualTo("");
        assertThat(HttpUtils.urlEncode(nonEncodedCharacters)).isEqualTo(nonEncodedCharacters);
        assertThat(HttpUtils.urlEncode(encodedCharactersInput)).isEqualTo(encodedCharactersOutput);
    }

    @Test
    public void pathValuesEncodeCorrectly() {
        String nonEncodedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~/";
        String encodedCharactersInput = "\t\n\r !\"#$%&'()*+,:;<=>?@[\\]^`{|}";
        String encodedCharactersOutput = "%09%0A%0D%20%21%22%23%24%25%26%27%28%29%2A%2B%2C%3A%3B%3C%3D%3E%3F%40%5B%5C%5D%5E%60%7B%7C%7D";

        assertThat(HttpUtils.urlEncodePath(null)).isEqualTo(null);
        assertThat(HttpUtils.urlEncodePath("")).isEqualTo("");
        assertThat(HttpUtils.urlEncodePath(nonEncodedCharacters)).isEqualTo(nonEncodedCharacters);
        assertThat(HttpUtils.urlEncodePath(encodedCharactersInput)).isEqualTo(encodedCharactersOutput);
    }

    @Test
    public void encodeQueryParametersCorrectly() {
        HashMap<String, String> values = new LinkedHashMap<>();
        values.put("SingleValue", "Value");
        values.put("SpaceValue", " ");
        values.put("EncodedValue", "/");
        values.put("PlusValue", "+");
        values.put("NullValue", null);
        values.put("BlankValue", "");

        String expectedQueryString = "SingleValue=Value&SpaceValue=%20&EncodedValue=%2F&PlusValue=%2B&NullValue&BlankValue=";

        assertFalse(HttpUtils.encodeQueryParameters(null).isPresent());
        assertFalse(HttpUtils.encodeQueryParameters(new HashMap<>()).isPresent());

        assertThat(HttpUtils.encodeQueryParameters(values)).hasValue(expectedQueryString);
    }

    @Test
    public void urisAppendCorrectly() {
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> HttpUtils.appendUri(null, ""));
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> HttpUtils.appendUri(null, null));
        assertThat(HttpUtils.appendUri("", null)).isEqualTo("");
        assertThat(HttpUtils.appendUri("", "")).isEqualTo("");
        assertThat(HttpUtils.appendUri("", "bar")).isEqualTo("/bar");
        assertThat(HttpUtils.appendUri("", "/bar")).isEqualTo("/bar");
        assertThat(HttpUtils.appendUri("", "bar/")).isEqualTo("/bar/");
        assertThat(HttpUtils.appendUri("", "/bar/")).isEqualTo("/bar/");
        assertThat(HttpUtils.appendUri("foo.com", null)).isEqualTo("foo.com");
        assertThat(HttpUtils.appendUri("foo.com", "")).isEqualTo("foo.com");
        assertThat(HttpUtils.appendUri("foo.com/", "")).isEqualTo("foo.com/");
        assertThat(HttpUtils.appendUri("foo.com", "bar")).isEqualTo("foo.com/bar");
        assertThat(HttpUtils.appendUri("foo.com/", "bar")).isEqualTo("foo.com/bar");
        assertThat(HttpUtils.appendUri("foo.com", "/bar")).isEqualTo("foo.com/bar");
        assertThat(HttpUtils.appendUri("foo.com/", "/bar")).isEqualTo("foo.com/bar");
        assertThat(HttpUtils.appendUri("foo.com/", "/bar/")).isEqualTo("foo.com/bar/");
        assertThat(HttpUtils.appendUri("foo.com/", "//bar/")).isEqualTo("foo.com//bar/");
    }

    @Test
    public void uriParams() {
        URI uri = URI.create("https://example.com/123?reqParam=1234&oParam=3456&noval&reqParam=456"
                + "&decoded%26Part=equals%3Dval");
        Map<String, String> uriParams = HttpUtils.uriParams(uri);
        assertThat(uriParams).contains(
                new AbstractMap.SimpleEntry<>("reqParam", "1234"),
                new AbstractMap.SimpleEntry<>("oParam", "3456"),
                new AbstractMap.SimpleEntry<>("noval", ""),
                new AbstractMap.SimpleEntry<>("decoded&Part", "equals=val"));
    }

    @Test
    public void uriParamsWithSingleEqualQuery() {
        URI uri = URI.create("http://example.com?=");
        Map<String, String> uriParams = HttpUtils.uriParams(uri);
        assertThat(uriParams).containsKey("");
        assertThat(uriParams.get("")).isEqualTo("");
    }

    @Test
    public void uriParamsWithSingleEqualWithValueQuery() {
        URI uri = URI.create("http://example.com?=nokeyvalue");
        Map<String, String> uriParams = HttpUtils.uriParams(uri);
        assertThat(uriParams).containsKey("");
        assertEquals("nokeyvalue", uriParams.get(""));
    }

    @Test
    public void uriParamsWithWithEmptyValueQuery() {
        URI uri = URI.create("http://example.com?novaluekey=");
        Map<String, String> uriParams = HttpUtils.uriParams(uri);
        assertThat(uriParams).containsKey("novaluekey");
        assertEquals("", uriParams.get("novaluekey"));
    }
}
