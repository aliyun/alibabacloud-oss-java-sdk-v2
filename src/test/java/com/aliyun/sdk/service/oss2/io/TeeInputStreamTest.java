package com.aliyun.sdk.service.oss2.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests {@link TeeInputStream}.
 */
public class TeeInputStreamTest {

    private final String ASCII = StandardCharsets.US_ASCII.name();

    private InputStream tee;

    private ByteArrayOutputStream output;

    @BeforeEach
    public void setUp() throws Exception {
        final InputStream input = new ByteArrayInputStream("abc".getBytes(ASCII));
        output = new ByteArrayOutputStream();
        tee = new TeeInputStream(input, output);
    }

    @Test
    void testMarkReset() throws Exception {
        assertEquals('a', tee.read());
        tee.mark(1);
        assertEquals('b', tee.read());
        tee.reset();
        assertEquals('b', tee.read());
        assertEquals('c', tee.read());
        assertEquals(-1, tee.read());
        assertEquals("abbc", output.toString(ASCII));
    }

    @Test
    void testReadEverything() throws Exception {
        assertEquals('a', tee.read());
        assertEquals('b', tee.read());
        assertEquals('c', tee.read());
        assertEquals(-1, tee.read());
        assertEquals("abc", output.toString(ASCII));
    }

    @Test
    void testReadNothing() throws Exception {
        assertEquals("", output.toString(ASCII));
    }

    @Test
    void testReadOneByte() throws Exception {
        assertEquals('a', tee.read());
        assertEquals("a", output.toString(ASCII));
    }

    @Test
    void testReadToArray() throws Exception {
        final byte[] buffer = new byte[8];
        assertEquals(3, tee.read(buffer));
        assertEquals('a', buffer[0]);
        assertEquals('b', buffer[1]);
        assertEquals('c', buffer[2]);
        assertEquals(-1, tee.read(buffer));
        assertEquals("abc", output.toString(ASCII));
    }

    @Test
    void testReadToArrayWithOffset() throws Exception {
        final byte[] buffer = new byte[8];
        assertEquals(3, tee.read(buffer, 4, 4));
        assertEquals('a', buffer[4]);
        assertEquals('b', buffer[5]);
        assertEquals('c', buffer[6]);
        assertEquals(-1, tee.read(buffer, 4, 4));
        assertEquals("abc", output.toString(ASCII));
    }

    @Test
    void testSkip() throws Exception {
        assertEquals('a', tee.read());
        assertEquals(1, tee.skip(1));
        assertEquals('c', tee.read());
        assertEquals(-1, tee.read());
        assertEquals("ac", output.toString(ASCII));
    }

    @Test
    void testReadToMultiOutputStream() throws Exception {
        final InputStream input = new ByteArrayInputStream("abc".getBytes(ASCII));
        ByteArrayOutputStream output1 = new ByteArrayOutputStream();
        ByteArrayOutputStream output2 = new ByteArrayOutputStream();
        TeeInputStream tee1 = new TeeInputStream(input, new MultiOutputStream(output1, output2));

        final byte[] buffer = new byte[8];
        assertEquals(3, tee1.read(buffer));
        assertEquals('a', buffer[0]);
        assertEquals('b', buffer[1]);
        assertEquals('c', buffer[2]);
        assertEquals(-1, tee1.read(buffer));
        assertEquals("abc", output1.toString(ASCII));
        assertEquals("abc", output2.toString(ASCII));
    }

}