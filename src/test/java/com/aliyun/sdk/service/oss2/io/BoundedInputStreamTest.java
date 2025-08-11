package com.aliyun.sdk.service.oss2.io;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class BoundedInputStreamTest {
    @Test
    public void testBoundedInputStream() {
        String data = "Test";
        BoundedInputStream input;
        int ret;

        try {
            byte[] out = new byte[1];
            input = new BoundedInputStream(new ByteArrayInputStream(data.getBytes()), 2);
            input.mark(10);
            ret = input.read(out);
            assertEquals(1, ret);
            ret = input.read();
            assertEquals('e', ret);
            ret = input.read();
            assertEquals(-1, ret);

            assertEquals(0, input.available());
            input.reset();
            input.available();

            input.skip(1);
            ret = input.read();
            assertEquals('e', ret);

            input.setPropagateClose(false);
            assertFalse(input.isPropagateClose());
            input.close();
            input.setPropagateClose(true);
            assertTrue(input.isPropagateClose());

            input.toString();

        } catch (IOException e) {
            fail();
        }

        try {
            byte[] out = new byte[10];
            input = new BoundedInputStream(new ByteArrayInputStream(data.getBytes()));
            ret = input.read(out);
            assertEquals(data.length(), ret);
        } catch (Exception e) {
            fail();
        }
    }
}