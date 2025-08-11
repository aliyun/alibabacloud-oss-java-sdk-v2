package com.aliyun.sdk.service.oss2.io;

import com.aliyun.sdk.service.oss2.utils.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static com.aliyun.sdk.service.oss2.utils.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.*;

public class ObservableInputStreamTest {

    static byte[] generateRandomByteStream(final int size) {
        final byte[] buffer = new byte[size];
        final Random rnd = new Random();
        rnd.nextBytes(buffer);
        return buffer;
    }

    private ObservableInputStream brokenObservableInputStream() {
        return new ObservableInputStream(BrokenInputStream.INSTANCE);
    }

    private InputStream createInputStream() {
        final byte[] buffer = generateRandomByteStream(IOUtils.DEFAULT_BUFFER_SIZE);
        return createInputStream(new ByteArrayInputStream(buffer));
    }

    private ObservableInputStream createInputStream(final InputStream origin) {
        return new ObservableInputStream(origin);
    }

    @Test
    void testAvailableAfterClose() throws Exception {
        final InputStream shadow;
        try (InputStream in = createInputStream()) {
            assertTrue(in.available() > 0);
            shadow = in;
        }
        assertEquals(IOUtils.DEFAULT_BUFFER_SIZE, shadow.available());
    }

    @Test
    void testAvailableAfterOpen() throws Exception {
        try (InputStream in = createInputStream()) {
            assertTrue(in.available() > 0);
            assertNotEquals(EOF, in.read());
            assertTrue(in.available() > 0);
        }
    }

    @Test
    void testBrokenInputStreamRead() throws IOException {
        try (ObservableInputStream ois = brokenObservableInputStream()) {
            assertThrows(IOException.class, ois::read);
        }
    }

    @Test
    void testBrokenInputStreamReadBuffer() throws IOException {
        try (ObservableInputStream ois = brokenObservableInputStream()) {
            assertThrows(IOException.class, () -> ois.read(new byte[1]));
        }
    }

    @Test
    void testBrokenInputStreamReadSubBuffer() throws IOException {
        try (ObservableInputStream ois = brokenObservableInputStream()) {
            assertThrows(IOException.class, () -> ois.read(new byte[2], 0, 1));
        }
    }

    /**
     * Tests that {@link StreamObserver#data(int)} is called.
     */
    @Test
    void testDataByteCalled_add() throws Exception {
        final byte[] buffer = generateRandomByteStream(IOUtils.DEFAULT_BUFFER_SIZE);
        final DataViewObserver lko = new DataViewObserver();
        try (ObservableInputStream ois = new ObservableInputStream(new ByteArrayInputStream(buffer))) {
            assertEquals(-1, lko.lastValue);
            ois.read();
            assertEquals(-1, lko.lastValue);
            assertEquals(0, lko.getFinishedCount());
            assertEquals(0, lko.getClosedCount());
            ois.add(lko);
            for (int i = 1; i < buffer.length; i++) {
                final int result = ois.read();
                assertEquals((byte) result, buffer[i]);
                assertEquals(result, lko.lastValue);
                assertEquals(0, lko.getFinishedCount());
                assertEquals(0, lko.getClosedCount());
            }
            final int result = ois.read();
            assertEquals(-1, result);
            assertEquals(1, lko.getFinishedCount());
            assertEquals(0, lko.getClosedCount());
            ois.close();
            assertEquals(1, lko.getFinishedCount());
            assertEquals(1, lko.getClosedCount());
        }
    }

    @Test
    void testDataByteCalled_ctor() throws Exception {
        final byte[] buffer = generateRandomByteStream(IOUtils.DEFAULT_BUFFER_SIZE);
        final DataViewObserver lko = new DataViewObserver();
        try (ObservableInputStream ois = new ObservableInputStream(new ByteArrayInputStream(buffer), lko)) {
            assertEquals(-1, lko.lastValue);
            ois.read();
            assertNotEquals(-1, lko.lastValue);
            assertEquals(0, lko.getFinishedCount());
            assertEquals(0, lko.getClosedCount());
            for (int i = 1; i < buffer.length; i++) {
                final int result = ois.read();
                assertEquals((byte) result, buffer[i]);
                assertEquals(result, lko.lastValue);
                assertEquals(0, lko.getFinishedCount());
                assertEquals(0, lko.getClosedCount());
            }
            final int result = ois.read();
            assertEquals(-1, result);
            assertEquals(1, lko.getFinishedCount());
            assertEquals(0, lko.getClosedCount());
            ois.close();
            assertEquals(1, lko.getFinishedCount());
            assertEquals(1, lko.getClosedCount());
        }
    }

    @Test
    void testDataBytesCalled() throws Exception {
        final byte[] buffer = generateRandomByteStream(IOUtils.DEFAULT_BUFFER_SIZE);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
             ObservableInputStream ois = createInputStream(bais)) {
            final DataViewObserver observer = new DataViewObserver();
            final byte[] readBuffer = new byte[23];
            assertNull(observer.buffer);
            ois.read(readBuffer);
            assertNull(observer.buffer);
            ois.add(observer);
            for (; ; ) {
                if (bais.available() >= 2048) {
                    final int result = ois.read(readBuffer);
                    if (result == -1) {
                        ois.close();
                        break;
                    }
                    assertEquals(readBuffer, observer.buffer);
                    assertEquals(0, observer.offset);
                    assertEquals(readBuffer.length, observer.length);
                } else {
                    final int res = Math.min(11, bais.available());
                    final int result = ois.read(readBuffer, 1, 11);
                    if (result == -1) {
                        ois.close();
                        break;
                    }
                    assertEquals(readBuffer, observer.buffer);
                    assertEquals(1, observer.offset);
                    assertEquals(res, observer.length);
                }
            }
        }
    }

    @Test
    void testGetObservers0() throws IOException {
        try (ObservableInputStream ois = new ObservableInputStream(new NullInputStream())) {
            assertTrue(ois.getObservers().isEmpty());
        }
    }

    @Test
    void testGetObservers1() throws IOException {
        final DataViewObserver observer0 = new DataViewObserver();
        try (ObservableInputStream ois = new ObservableInputStream(new NullInputStream(), observer0)) {
            assertEquals(observer0, ois.getObservers().get(0));
        }
    }

    @Test
    void testGetObserversOrder() throws IOException {
        final DataViewObserver observer0 = new DataViewObserver();
        final DataViewObserver observer1 = new DataViewObserver();
        try (ObservableInputStream ois = new ObservableInputStream(new NullInputStream(), observer0, observer1)) {
            assertEquals(observer0, ois.getObservers().get(0));
            assertEquals(observer1, ois.getObservers().get(1));
        }
    }

    private void testNotificationCallbacks(final int bufferSize) throws IOException {
        final byte[] buffer = new byte[IOUtils.DEFAULT_BUFFER_SIZE];
        final LengthObserver lengthObserver = new LengthObserver();
        final MethodCountObserver methodCountObserver = new MethodCountObserver();
        try (ObservableInputStream ois = new ObservableInputStream(new ByteArrayInputStream(buffer), lengthObserver, methodCountObserver)) {
            assertEquals(IOUtils.DEFAULT_BUFFER_SIZE, IOUtils.copy(ois, IOUtils.discradOutputStream(), bufferSize));
        }
        assertEquals(IOUtils.DEFAULT_BUFFER_SIZE, lengthObserver.getTotal());
        assertEquals(1, methodCountObserver.getClosedCount());
        assertEquals(1, methodCountObserver.getFinishedCount());
        assertEquals(0, methodCountObserver.getErrorCount());
        assertEquals(0, methodCountObserver.getDataCount());
        assertEquals(buffer.length / bufferSize, methodCountObserver.getDataBufferCount());
    }

    @Test
    void testNotificationCallbacksBufferSize1() throws Exception {
        testNotificationCallbacks(1);
    }

    @Test
    void testNotificationCallbacksBufferSize2() throws Exception {
        testNotificationCallbacks(2);
    }

    @Test
    void testNotificationCallbacksBufferSizeDefault() throws Exception {
        testNotificationCallbacks(IOUtils.DEFAULT_BUFFER_SIZE);
    }

    @Test
    void testReadAfterClose_ByteArrayInputStream() throws Exception {
        try (InputStream in = createInputStream()) {
            in.close();
            assertNotEquals(EOF, in.read());
        }
    }

    private static final class DataViewObserver extends MethodCountObserver {
        private byte[] buffer;
        private int lastValue = -1;
        private int length = -1;
        private int offset = -1;

        @Override
        public void data(final byte[] buffer, final int offset, final int length) throws IOException {
            this.buffer = buffer;
            this.offset = offset;
            this.length = length;
        }

        @Override
        public void data(final int value) throws IOException {
            super.data(value);
            lastValue = value;
        }
    }

    private static final class LengthObserver extends StreamObserver {
        private long total;

        @Override
        public void data(final byte[] buffer, final int offset, final int length) throws IOException {
            this.total += length;
        }

        @Override
        public void data(final int value) throws IOException {
            total++;
        }

        public long getTotal() {
            return total;
        }
    }

    private static class MethodCountObserver extends StreamObserver {
        private long closedCount;
        private long dataBufferCount;
        private long dataCount;
        private long errorCount;
        private long finishedCount;

        @Override
        public void closed() throws IOException {
            closedCount++;
        }

        @Override
        public void data(final byte[] buffer, final int offset, final int length) throws IOException {
            dataBufferCount++;
        }

        @Override
        public void data(final int value) throws IOException {
            dataCount++;
        }

        @Override
        public void error(final IOException exception) throws IOException {
            errorCount++;
        }

        @Override
        public void finished() throws IOException {
            finishedCount++;
        }

        public long getClosedCount() {
            return closedCount;
        }

        public long getDataBufferCount() {
            return dataBufferCount;
        }

        public long getDataCount() {
            return dataCount;
        }

        public long getErrorCount() {
            return errorCount;
        }

        public long getFinishedCount() {
            return finishedCount;
        }

    }
}