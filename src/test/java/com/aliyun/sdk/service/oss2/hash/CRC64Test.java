package com.aliyun.sdk.service.oss2.hash;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CRC64Test {
    @Test
    public void testCRC64() {
        String data1 = "123456789";
        String data2 = "This is a test of the emergency broadcast system.";
        CRC64 crc64;

        crc64 = new CRC64(0);
        crc64.update(data1.getBytes(), data1.length());
        long pat = Long.parseLong("-7395533204333446662");
        assertEquals(pat, crc64.getValue());

        crc64 = new CRC64(data2.getBytes(), data2.length());
        pat = Long.parseLong("2871916124362751090");
        assertEquals(pat, crc64.getValue());

        crc64.reset();
        crc64.update(data1.getBytes(), data1.length());
        pat = Long.parseLong("-7395533204333446662");
        assertEquals(pat, crc64.getValue());

        byte[] init = new byte[4];
        init[0] = init[1] = init[2] = init[3] = 0;
        crc64 = CRC64.fromBytes(init);
        assertEquals(0, crc64.getValue());


        String total = data1 + data2;
        CRC64 crc1 = new CRC64();
        crc1.update(data1.getBytes(), data1.length());

        CRC64 crc2 = new CRC64();
        crc2.update(data2.getBytes(), data2.length());

        CRC64 crc3 = new CRC64();
        crc3.update(total.getBytes(), total.length());

        CRC64 crc4 = CRC64.combine(crc1, crc2, data2.length());
        assertEquals(crc3.getValue(), crc4.getValue());

        CRC64 crc5 = CRC64.combine(crc1, crc2, 0);
        assertEquals(crc1.getValue(), crc5.getValue());

        assertEquals(2, CRC64.combine(2, 3, 0));

        assertTrue(crc4.getBytes().length > 0);
    }

    @Test
    public void testCRC64AndReset() {
        String data1 = "123456789";
        String data2 = "This is a test of the emergency broadcast system.";
        String data = data1 + data2;

        // total crc
        CRC64 crc = new CRC64();
        crc.update(data.getBytes(), data.length());
        long pat = Long.parseLong("7672622622872733320");
        long crcValue = crc.getValue();
        assertEquals(pat, crcValue);

        // crc 1
        CRC64 crc1 = new CRC64();
        crc1.update(data1.getBytes(), data1.length());
        long crcValue1 = crc1.getValue();
        long pat1 = Long.parseLong("-7395533204333446662");
        assertEquals(pat1, crcValue1);

        // crc 2
        CRC64 crc2 = new CRC64(crcValue1);
        crc2.update(data2.getBytes(), data2.length());
        long crcValue2 = crc2.getValue();
        assertEquals(crcValue, crcValue2);

        crc2.reset();
        crc2.update(data2.getBytes(), data2.length());
        long crcValue3 = crc2.getValue();
        assertEquals(crcValue, crcValue3);
    }

    @Test
    public void testBytes() {

        final byte[] TEST1 = "123456789".getBytes();
        final int TESTLEN1 = 9;
        final long TESTCRC1 = 0x995dc9bbdf1939faL; // ECMA.
        calcAndCheck(TEST1, TESTLEN1, TESTCRC1);

        final byte[] TEST2 = "This is a test of the emergency broadcast system.".getBytes();
        final int TESTLEN2 = 49;
        final long TESTCRC2 = 0x27db187fc15bbc72L; // ECMA.
        calcAndCheck(TEST2, TESTLEN2, TESTCRC2);

        final byte[] TEST3 = "IHATEMATH".getBytes();
        final int TESTLEN3 = 9;
        final long TESTCRC3 = 0x3920e0f66b6ee0c8L; // ECMA.
        calcAndCheck(TEST3, TESTLEN3, TESTCRC3);
    }


    @Test
    public void testPerformance() {
        byte[] b = new byte[65536];
        new Random().nextBytes(b);

        // warmup
        CRC64 crc = new CRC64();
        crc.update(b, b.length);

        // start bench
        long bytes = 0;
        long start = System.currentTimeMillis();
        crc = new CRC64();
        for (int i = 0; i < 100000; i++) {
            crc.update(b, b.length);
            bytes += b.length;
        }

        long duration = System.currentTimeMillis() - start;
        duration = (duration == 0) ? 1 : duration; // div0
        long bytesPerSec = (bytes / duration) * 1000;

        System.out.println(bytes / 1024 / 1024 + " MB processed in " + duration + " ms @ " + bytesPerSec / 1024 / 1024
                + " MB/s");
    }

    @Test
    public void testUpdateAndReset() {
        CRC64 crc = new CRC64();

        final byte[] TEST1 = "123456789".getBytes();
        final int TESTLEN1 = 9;
        final long TESTCRC1 = 0x995dc9bbdf1939faL; // ECMA.

        crc.update(TEST1, TESTLEN1);

        assertEquals("oh noes", TESTCRC1, crc.getValue());

        crc.reset();

        assertEquals("oh noes", 0, crc.getValue());

        final byte[] TEST2 = "This is a test of the emergency broadcast system.".getBytes();
        final int TESTLEN2 = 49;
        final long TESTCRC2 = 0x27db187fc15bbc72L; // ECMA.

        crc.update(TEST2, TESTLEN2);

        assertEquals("oh noes", TESTCRC2, crc.getValue());
    }

    private void calcAndCheck(byte[] b, int len, long crcValue) {

        /* Test CRC64 default calculation. */
        CRC64 crc = new CRC64(b, len);
        if (crc.getValue() != crcValue) {
            throw new RuntimeException("mismatch: " + String.format("%016x", crc.getValue()) + " should be "
                    + String.format("%016x", crcValue));
        }

        /* test combine() */
        CRC64 crc1 = new CRC64(b, (len + 1) >>> 1);
        CRC64 crc2 = new CRC64(Arrays.copyOfRange(b, (len + 1) >>> 1, b.length), len >>> 1);
        crc = CRC64.combine(crc1, crc2, len >>> 1);

        if (crc.getValue() != crcValue) {
            throw new RuntimeException("mismatch: " + String.format("%016x", crc.getValue()) + " should be "
                    + String.format("%016x", crcValue));
        }
    }
}
