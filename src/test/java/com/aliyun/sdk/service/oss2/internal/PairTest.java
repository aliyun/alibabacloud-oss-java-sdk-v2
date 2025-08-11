package com.aliyun.sdk.service.oss2.internal;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PairTest {

    @Test
    public void equalsMethodWorksAsExpected() {
        Pair<String, Integer> foo = Pair.of("Foo", 50);
        assertThat(foo).isEqualTo(Pair.of("Foo", 50));
        assertThat(foo).isNotEqualTo(Pair.of("Foo-bar", 50));
    }

    @Test
    public void canBeUseAsMapKey() {
        Map<Pair<String, Integer>, String> map = new HashMap<>();

        map.put(Pair.of("Hello", 100), "World");

        assertThat(map.get(Pair.of("Hello", 100))).isEqualTo("World");
    }

    @Test
    public void prettyToString() {
        assertThat(Pair.of("Hello", "World").toString()).isEqualTo("Pair(first=Hello, second=World)");
    }
}
