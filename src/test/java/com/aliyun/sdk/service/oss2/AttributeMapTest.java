package com.aliyun.sdk.service.oss2;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotSame;

public class AttributeMapTest {

    private static final AttributeMap.Key<String> STRING_KEY = new AttributeMap.Key<String>(String.class) {
    };

    private static final AttributeMap.Key<Integer> INTEGER_KEY = new AttributeMap.Key<Integer>(Integer.class) {
    };

    @Test
    public void copyCreatesNewOptionsObject() {
        AttributeMap orig = AttributeMap.empty()
                .put(STRING_KEY, "foo");

        assertNotSame(orig, orig.copy());
        assertThat(orig).isEqualTo(orig.copy());
        assertThat(orig.get(STRING_KEY)).isEqualTo(orig.copy().get(STRING_KEY));
    }

    @Test
    public void mergeTreatsThisObjectWithHigherPrecedence() {
        AttributeMap orig = AttributeMap.empty()
                .put(STRING_KEY, "foo");

        AttributeMap merged = orig.merge(AttributeMap.empty()
                .put(STRING_KEY, "bar")
                .put(INTEGER_KEY, 42));

        assertThat(merged.containsKey(STRING_KEY)).isTrue();
        assertThat(merged.get(STRING_KEY)).isEqualTo("foo");
        // Integer key is not in 'this' object so it should be merged in from the lower precedence
        assertThat(merged.get(INTEGER_KEY)).isEqualTo(42);
    }

    /**
     * Options are optional.
     */
    @Test
    public void mergeWithOptionNotPresentInBoth_DoesNotThrow() {
        AttributeMap orig = AttributeMap.empty()
                .put(STRING_KEY, "foo");

        AttributeMap merged = orig.merge(AttributeMap.empty()
                .put(STRING_KEY, "bar"));
        assertThat(merged.get(INTEGER_KEY)).isNull();
    }
}
