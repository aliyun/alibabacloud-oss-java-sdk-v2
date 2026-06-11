package com.aliyun.sdk.service.oss2.transform;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.zip.CRC32;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SelectFrameDecoderTest {

    // --- Helper methods ---

    private static void writeBE32(ByteArrayOutputStream buf, int value) {
        buf.write((value >> 24) & 0xFF);
        buf.write((value >> 16) & 0xFF);
        buf.write((value >> 8) & 0xFF);
        buf.write(value & 0xFF);
    }

    private static void writeBE64(ByteArrayOutputStream buf, long value) {
        for (int i = 7; i >= 0; i--) {
            buf.write((int) ((value >> (i * 8)) & 0xFF));
        }
    }

    private static byte[] buildFrame(int frameType, byte[] payload) {
        return buildFrame(frameType, payload, false, 0);
    }

    private static byte[] buildFrame(int frameType, byte[] payload, boolean useZeroChecksum, int checksumOverride) {
        ByteArrayOutputStream frame = new ByteArrayOutputStream();

        // Version(1B) + FrameType(3B)
        frame.write(1);
        frame.write((frameType >> 16) & 0xFF);
        frame.write((frameType >> 8) & 0xFF);
        frame.write(frameType & 0xFF);

        // PayloadLength(4B) = payload.length + 8 (offset)
        int payloadLength = payload.length + 8;
        writeBE32(frame, payloadLength);

        // HeaderChecksum(4B)
        writeBE32(frame, 0);

        // Offset(8B)
        writeBE64(frame, 0);

        // PayloadData
        frame.write(payload, 0, payload.length);

        // CRC32
        if (useZeroChecksum) {
            writeBE32(frame, 0);
        } else {
            byte[] frameBytes = frame.toByteArray();
            CRC32 crc = new CRC32();
            crc.update(frameBytes, 12, 8 + payload.length);
            int crcValue = checksumOverride != 0 ? checksumOverride : (int) crc.getValue();
            writeBE32(frame, crcValue);
        }

        return frame.toByteArray();
    }

    private static byte[] buildEndFrame(int status) {
        ByteArrayOutputStream payload = new ByteArrayOutputStream();
        writeBE64(payload, 100); // totalScanned
        writeBE32(payload, status);
        return buildFrame(SelectFrameDecoder.END_FRAME, payload.toByteArray());
    }

    private static byte[] buildEndFrameWithError(int status, String errorMessage) {
        ByteArrayOutputStream payload = new ByteArrayOutputStream();
        writeBE64(payload, 100); // totalScanned
        writeBE32(payload, status);
        byte[] errorBytes = errorMessage.getBytes();
        payload.write(errorBytes, 0, errorBytes.length);
        return buildFrame(SelectFrameDecoder.END_FRAME, payload.toByteArray());
    }

    // --- SelectFrameDecoder tests ---

    @Test
    void dataFrameForwardsPayload() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SelectFrameDecoder decoder = new SelectFrameDecoder((data, off, len) -> out.write(data, off, len));

        byte[] frame = buildFrame(SelectFrameDecoder.DATA_FRAME, "hello".getBytes());
        decoder.write(frame, 0, frame.length);

        assertThat(decoder.isError()).isFalse();
        assertThat(out.toString()).isEqualTo("hello");
    }

    @Test
    void continuousFrameIgnored() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SelectFrameDecoder decoder = new SelectFrameDecoder((data, off, len) -> out.write(data, off, len));

        byte[] frame = buildFrame(SelectFrameDecoder.CONTINUOUS_FRAME, new byte[0]);
        decoder.write(frame, 0, frame.length);

        assertThat(decoder.isError()).isFalse();
        assertThat(out.size()).isEqualTo(0);
    }

    @Test
    void multipleDataFrames() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SelectFrameDecoder decoder = new SelectFrameDecoder((data, off, len) -> out.write(data, off, len));

        byte[] f1 = buildFrame(SelectFrameDecoder.DATA_FRAME, "abc".getBytes());
        byte[] f2 = buildFrame(SelectFrameDecoder.DATA_FRAME, "def".getBytes());

        decoder.write(f1, 0, f1.length);
        decoder.write(f2, 0, f2.length);

        assertThat(decoder.isError()).isFalse();
        assertThat(out.toString()).isEqualTo("abcdef");
    }

    @Test
    void dataAndContinuousInterleaved() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SelectFrameDecoder decoder = new SelectFrameDecoder((data, off, len) -> out.write(data, off, len));

        byte[] f1 = buildFrame(SelectFrameDecoder.DATA_FRAME, "X".getBytes());
        byte[] fc = buildFrame(SelectFrameDecoder.CONTINUOUS_FRAME, new byte[0]);
        byte[] f2 = buildFrame(SelectFrameDecoder.DATA_FRAME, "Y".getBytes());

        ByteArrayOutputStream all = new ByteArrayOutputStream();
        all.write(f1);
        all.write(fc);
        all.write(f2);
        byte[] allBytes = all.toByteArray();

        decoder.write(allBytes, 0, allBytes.length);

        assertThat(decoder.isError()).isFalse();
        assertThat(out.toString()).isEqualTo("XY");
    }

    @Test
    void incrementalWrite() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SelectFrameDecoder decoder = new SelectFrameDecoder((data, off, len) -> out.write(data, off, len));

        byte[] frame = buildFrame(SelectFrameDecoder.DATA_FRAME, "test".getBytes());

        // Feed one byte at a time
        for (byte b : frame) {
            decoder.write(new byte[]{b}, 0, 1);
        }

        assertThat(decoder.isError()).isFalse();
        assertThat(out.toString()).isEqualTo("test");
    }

    @Test
    void crcMismatchSetsError() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SelectFrameDecoder decoder = new SelectFrameDecoder((data, off, len) -> out.write(data, off, len));

        byte[] frame = buildFrame(SelectFrameDecoder.DATA_FRAME, "hi".getBytes(), false, 0xDEADBEEF);

        assertThatThrownBy(() -> decoder.write(frame, 0, frame.length))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("CRC32 checksum mismatch");
        assertThat(decoder.isError()).isTrue();
    }

    @Test
    void zeroChecksumSkipsValidation() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SelectFrameDecoder decoder = new SelectFrameDecoder((data, off, len) -> out.write(data, off, len));

        byte[] frame = buildFrame(SelectFrameDecoder.DATA_FRAME, "ok".getBytes(), true, 0);

        decoder.write(frame, 0, frame.length);
        assertThat(decoder.isError()).isFalse();
        assertThat(out.toString()).isEqualTo("ok");
    }

    @Test
    void endFrameRecordsStatus() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SelectFrameDecoder decoder = new SelectFrameDecoder((data, off, len) -> out.write(data, off, len));

        byte[] frame = buildEndFrame(200);
        decoder.write(frame, 0, frame.length);

        assertThat(decoder.isError()).isFalse();
        assertThat(decoder.isFinished()).isTrue();
        assertThat(decoder.endStatus()).isEqualTo(200);
        assertThat(out.size()).isEqualTo(0);
    }

    @Test
    void endFrameWithNon2xxStatusThrows() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SelectFrameDecoder decoder = new SelectFrameDecoder((data, off, len) -> out.write(data, off, len));

        byte[] frame = buildEndFrameWithError(403, "AccessDenied");

        assertThatThrownBy(() -> decoder.write(frame, 0, frame.length))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("403");
        assertThat(decoder.isError()).isTrue();
        assertThat(decoder.errorMessage()).isEqualTo("AccessDenied");
    }

    // --- SelectObjectInputStream tests ---

    @Test
    void selectObjectInputStream_basicRead() throws IOException {
        byte[] dataFrame = buildFrame(SelectFrameDecoder.DATA_FRAME, "hello world".getBytes());
        byte[] endFrame = buildEndFrame(200);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(dataFrame);
        stream.write(endFrame);

        try (SelectObjectInputStream is = new SelectObjectInputStream(new ByteArrayInputStream(stream.toByteArray()))) {
            byte[] buf = new byte[1024];
            int n = is.read(buf);
            assertThat(new String(buf, 0, n)).isEqualTo("hello world");
            assertThat(is.read()).isEqualTo(-1);
        }
    }

    @Test
    void selectObjectInputStream_multipleFrames() throws IOException {
        byte[] f1 = buildFrame(SelectFrameDecoder.DATA_FRAME, "abc".getBytes());
        byte[] fc = buildFrame(SelectFrameDecoder.CONTINUOUS_FRAME, new byte[0]);
        byte[] f2 = buildFrame(SelectFrameDecoder.DATA_FRAME, "def".getBytes());
        byte[] end = buildEndFrame(200);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(f1);
        stream.write(fc);
        stream.write(f2);
        stream.write(end);

        try (SelectObjectInputStream is = new SelectObjectInputStream(new ByteArrayInputStream(stream.toByteArray()))) {
            byte[] buf = new byte[1024];
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            int n;
            while ((n = is.read(buf)) != -1) {
                result.write(buf, 0, n);
            }
            assertThat(result.toString()).isEqualTo("abcdef");
        }
    }

    @Test
    void selectObjectInputStream_singleByteRead() throws IOException {
        byte[] dataFrame = buildFrame(SelectFrameDecoder.DATA_FRAME, "AB".getBytes());
        byte[] endFrame = buildEndFrame(200);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(dataFrame);
        stream.write(endFrame);

        try (SelectObjectInputStream is = new SelectObjectInputStream(new ByteArrayInputStream(stream.toByteArray()))) {
            assertThat(is.read()).isEqualTo('A');
            assertThat(is.read()).isEqualTo('B');
            assertThat(is.read()).isEqualTo(-1);
        }
    }

    @Test
    void selectObjectInputStream_errorStatusThrows() {
        byte[] endFrame = buildEndFrameWithError(403, "Forbidden");

        assertThatThrownBy(() -> {
            try (SelectObjectInputStream is = new SelectObjectInputStream(new ByteArrayInputStream(endFrame))) {
                is.read(new byte[1024]);
            }
        }).isInstanceOf(IOException.class)
          .hasMessageContaining("403");
    }

    @Test
    void selectObjectInputStream_available() throws IOException {
        byte[] dataFrame = buildFrame(SelectFrameDecoder.DATA_FRAME, "test".getBytes());
        byte[] endFrame = buildEndFrame(200);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(dataFrame);
        stream.write(endFrame);

        try (SelectObjectInputStream is = new SelectObjectInputStream(new ByteArrayInputStream(stream.toByteArray()))) {
            assertThat(is.available()).isEqualTo(0);
            is.read(new byte[2]);
            assertThat(is.available()).isEqualTo(2);
        }
    }

    // --- SelectFrameDecodingChannel tests ---

    @Test
    void decodingChannel_forwardsDecodedData() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        WritableByteChannel innerChannel = Channels.newChannel(out);

        try (SelectFrameDecodingChannel channel = new SelectFrameDecodingChannel(innerChannel)) {
            byte[] dataFrame = buildFrame(SelectFrameDecoder.DATA_FRAME, "hello".getBytes());
            byte[] endFrame = buildEndFrame(200);

            channel.write(ByteBuffer.wrap(dataFrame));
            channel.write(ByteBuffer.wrap(endFrame));
        }

        assertThat(out.toString()).isEqualTo("hello");
    }

    @Test
    void decodingChannel_multipleFrames() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        WritableByteChannel innerChannel = Channels.newChannel(out);

        try (SelectFrameDecodingChannel channel = new SelectFrameDecodingChannel(innerChannel)) {
            byte[] f1 = buildFrame(SelectFrameDecoder.DATA_FRAME, "abc".getBytes());
            byte[] fc = buildFrame(SelectFrameDecoder.CONTINUOUS_FRAME, new byte[0]);
            byte[] f2 = buildFrame(SelectFrameDecoder.DATA_FRAME, "def".getBytes());
            byte[] end = buildEndFrame(200);

            ByteArrayOutputStream all = new ByteArrayOutputStream();
            all.write(f1);
            all.write(fc);
            all.write(f2);
            all.write(end);

            channel.write(ByteBuffer.wrap(all.toByteArray()));
        }

        assertThat(out.toString()).isEqualTo("abcdef");
    }

    @Test
    void decodingChannel_incrementalWrite() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        WritableByteChannel innerChannel = Channels.newChannel(out);

        byte[] dataFrame = buildFrame(SelectFrameDecoder.DATA_FRAME, "test".getBytes());
        byte[] endFrame = buildEndFrame(200);

        ByteArrayOutputStream all = new ByteArrayOutputStream();
        all.write(dataFrame);
        all.write(endFrame);
        byte[] allBytes = all.toByteArray();

        try (SelectFrameDecodingChannel channel = new SelectFrameDecodingChannel(innerChannel)) {
            // Feed one byte at a time
            for (byte b : allBytes) {
                channel.write(ByteBuffer.wrap(new byte[]{b}));
            }
        }

        assertThat(out.toString()).isEqualTo("test");
    }

    @Test
    void decodingChannel_crcMismatchThrows() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        WritableByteChannel innerChannel = Channels.newChannel(out);

        byte[] frame = buildFrame(SelectFrameDecoder.DATA_FRAME, "hi".getBytes(), false, 0xDEADBEEF);

        assertThatThrownBy(() -> {
            try (SelectFrameDecodingChannel channel = new SelectFrameDecodingChannel(innerChannel)) {
                channel.write(ByteBuffer.wrap(frame));
            }
        }).isInstanceOf(IOException.class)
          .hasMessageContaining("CRC32 checksum mismatch");
    }

    @Test
    void decodingChannel_directByteBuffer() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        WritableByteChannel innerChannel = Channels.newChannel(out);

        byte[] dataFrame = buildFrame(SelectFrameDecoder.DATA_FRAME, "direct".getBytes());
        byte[] endFrame = buildEndFrame(200);

        ByteArrayOutputStream all = new ByteArrayOutputStream();
        all.write(dataFrame);
        all.write(endFrame);
        byte[] allBytes = all.toByteArray();

        ByteBuffer directBuf = ByteBuffer.allocateDirect(allBytes.length);
        directBuf.put(allBytes);
        directBuf.flip();

        try (SelectFrameDecodingChannel channel = new SelectFrameDecodingChannel(innerChannel)) {
            channel.write(directBuf);
        }

        assertThat(out.toString()).isEqualTo("direct");
    }
}
