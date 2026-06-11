package com.aliyun.sdk.service.oss2.transform;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

final class SelectFrameDecoder {

    static final int FRAME_HEADER_LEN = 20;
    static final int DATA_FRAME = 0x800001;
    static final int CONTINUOUS_FRAME = 0x800004;
    static final int END_FRAME = 0x800005;
    static final int SELECT_VERSION = 1;

    interface DataOutput {
        void onData(byte[] data, int offset, int len) throws IOException;
    }

    private final DataOutput output;
    private final boolean crcEnabled;

    private final byte[] header = new byte[FRAME_HEADER_LEN];
    private int headerLen;

    private int frameType;
    private int payloadRemains;

    private final byte[] tail = new byte[4];
    private int tailLen;

    private CRC32 crc32;

    private byte[] endFrameData;
    private int endFrameDataLen;

    private boolean finished;
    private boolean error;
    private int endStatus;
    private String errorMessage;

    SelectFrameDecoder(DataOutput output, boolean crcEnabled) {
        this.output = output;
        this.crcEnabled = crcEnabled;
        if (crcEnabled) {
            this.crc32 = new CRC32();
        }
    }

    SelectFrameDecoder(DataOutput output) {
        this(output, true);
    }

    boolean isFinished() {
        return finished;
    }

    boolean isError() {
        return error;
    }

    int endStatus() {
        return endStatus;
    }

    String errorMessage() {
        return errorMessage;
    }

    void write(byte[] data, int offset, int len) throws IOException {
        if (error || finished) {
            return;
        }

        int pos = offset;
        int end = offset + len;

        while (pos < end && !error && !finished) {
            pos += parseFrame(data, pos, end - pos);
        }
    }

    private int parseFrame(byte[] data, int offset, int len) throws IOException {
        int consumed = 0;

        // Phase 1: accumulate header
        if (headerLen < FRAME_HEADER_LEN) {
            int need = FRAME_HEADER_LEN - headerLen;
            int copy = Math.min(need, len);
            System.arraycopy(data, offset, header, headerLen, copy);
            headerLen += copy;
            consumed += copy;

            if (headerLen == FRAME_HEADER_LEN) {
                if (header[0] != SELECT_VERSION) {
                    error = true;
                    errorMessage = "Invalid select version: " + header[0];
                    throw new IOException(errorMessage);
                }
                int payloadLength = ByteBuffer.wrap(header, 4, 4).getInt();
                payloadRemains = payloadLength - 8;
                frameType = ((header[1] & 0xFF) << 16) | ((header[2] & 0xFF) << 8) | (header[3] & 0xFF);

                if (crcEnabled) {
                    crc32.reset();
                    crc32.update(header, 12, 8);
                }

                if (frameType == END_FRAME) {
                    endFrameData = new byte[payloadRemains];
                    endFrameDataLen = 0;
                }
            }

            if (headerLen < FRAME_HEADER_LEN) {
                return consumed;
            }
        }

        int remaining = len - consumed;

        // Phase 2: payload
        if (payloadRemains > 0 && remaining > 0) {
            int copy = Math.min(payloadRemains, remaining);

            if (crcEnabled) {
                crc32.update(data, offset + consumed, copy);
            }

            if (frameType == DATA_FRAME) {
                output.onData(data, offset + consumed, copy);
            } else if (frameType == END_FRAME) {
                System.arraycopy(data, offset + consumed, endFrameData, endFrameDataLen, copy);
                endFrameDataLen += copy;
            }

            consumed += copy;
            payloadRemains -= copy;

            if (payloadRemains > 0) {
                return consumed;
            }
        }

        remaining = len - consumed;

        // Phase 3: tail (4 bytes checksum)
        if (tailLen < 4 && remaining > 0) {
            int need = 4 - tailLen;
            int copy = Math.min(need, remaining);
            System.arraycopy(data, offset + consumed, tail, tailLen, copy);
            tailLen += copy;
            consumed += copy;

            if (tailLen < 4) {
                return consumed;
            }
        }

        if (tailLen < 4) {
            return consumed;
        }

        // Validate CRC
        if (crcEnabled) {
            int expectedCrc = ByteBuffer.wrap(tail).getInt();
            if (expectedCrc != 0) {
                long actual = crc32.getValue();
                long expected = Integer.toUnsignedLong(expectedCrc);
                if (actual != expected) {
                    error = true;
                    errorMessage = "CRC32 checksum mismatch, expected: " + expected + ", actual: " + actual;
                    throw new IOException(errorMessage);
                }
            }
        }

        // Handle END frame
        if (frameType == END_FRAME && endFrameDataLen >= 12) {
            endStatus = ByteBuffer.wrap(endFrameData, 8, 4).getInt();
            if (endFrameDataLen > 12) {
                errorMessage = new String(endFrameData, 12, endFrameDataLen - 12, StandardCharsets.UTF_8);
            }
            finished = true;

            if (endStatus / 100 != 2) {
                error = true;
                String msg = "SelectObject failed, status: " + endStatus;
                if (errorMessage != null && !errorMessage.isEmpty()) {
                    msg += ", error: " + errorMessage;
                }
                throw new IOException(msg);
            }
        }

        // Reset for next frame
        headerLen = 0;
        tailLen = 0;
        payloadRemains = 0;
        frameType = 0;

        return consumed;
    }
}
