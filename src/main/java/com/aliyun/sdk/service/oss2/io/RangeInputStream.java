package com.aliyun.sdk.service.oss2.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An InputStream that reads from an OSS object using HTTP Range requests,
 * with automatic reconnection on network failures.
 * <p>
 * On read errors (IOException), the current stream is closed and a new Range request
 * is issued from the point of disconnection on the next read call, transparently to the caller.
 * <p>
 * ETag consistency is enforced: the ETag from the first response is recorded and compared
 * against subsequent reconnection responses. If the ETag changes (object was modified),
 * an IOException is thrown.
 */
public class RangeInputStream extends InputStream {

    /**
     * Functional interface for issuing a Range GET request.
     */
    @FunctionalInterface
    public interface RangeGetFunction {
        /**
         * Issues a Range GET request.
         *
         * @param offset the start offset (0-based)
         * @param count  the number of bytes to read; 0 means read to end
         * @return the output containing the response body and metadata
         * @throws IOException if the request fails
         */
        RangeGetOutput apply(long offset, long count) throws IOException;
    }

    /**
     * Output from a Range GET request.
     */
    public static class RangeGetOutput {
        private final InputStream body;
        private final long contentLength;
        private final String contentRange;
        private final String etag;

        public RangeGetOutput(InputStream body, long contentLength, String contentRange, String etag) {
            this.body = body;
            this.contentLength = contentLength;
            this.contentRange = contentRange;
            this.etag = etag;
        }

        public InputStream body() { return body; }
        public long contentLength() { return contentLength; }
        public String contentRange() { return contentRange; }
        public String etag() { return etag; }
    }

    private static final Pattern CONTENT_RANGE_PATTERN =
            Pattern.compile("bytes\\s+(\\d+)-(\\d+)/(\\d+|\\*)");

    private final RangeGetFunction rangeGet;

    // Current underlying stream
    private InputStream in;

    // Current range tracking (advances as data is read)
    private long httpRangeOffset;
    private long httpRangeCount;

    // Original request range (immutable after construction)
    private final long oriOffset;
    private final long oriCount;

    // Total bytes read so far
    private long offset;

    // ETag for consistency checking
    private String etag;

    // Total size of the object (learned from Content-Range or Content-Length)
    private long totalSize = -1;

    private boolean closed;

    /**
     * Creates a new RangeInputStream.
     *
     * @param rangeGet the function to issue Range GET requests
     * @param offset   the start offset (0-based); 0 to read from the beginning
     * @param count    the number of bytes to read; 0 means read to end of object
     * @param etag     expected ETag for consistency checking; null to use the first response's ETag
     */
    public RangeInputStream(RangeGetFunction rangeGet, long offset, long count, String etag) {
        this.rangeGet = Objects.requireNonNull(rangeGet, "rangeGet must not be null");
        this.httpRangeOffset = offset;
        this.httpRangeCount = count;
        this.oriOffset = offset;
        this.oriCount = count;
        this.offset = offset;
        this.etag = etag;
    }

    /**
     * Creates a new RangeInputStream that reads the entire object.
     *
     * @param rangeGet the function to issue Range GET requests
     */
    public RangeInputStream(RangeGetFunction rangeGet) {
        this(rangeGet, 0, 0, null);
    }

    @Override
    public int read() throws IOException {
        byte[] buf = new byte[1];
        int n = read(buf, 0, 1);
        if (n == -1) {
            return -1;
        }
        return buf[0] & 0xFF;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (closed) {
            throw new IOException("RangeInputStream is closed");
        }

        // Open stream if needed
        if (in == null) {
            openStream();
        }

        // Read from stream; on IOException, close and null out for auto-reconnect
        int n;
        try {
            n = in.read(b, off, len);
        } catch (IOException e) {
            closeCurrentStream();
            // Return 0 to signal "no data yet but not EOF" — next read() will reconnect.
            return 0;
        }

        if (n > 0) {
            offset += n;
            httpRangeOffset += n;
        }
        return n;
    }

    private void openStream() throws IOException {
        long remainsOffset = httpRangeOffset;
        long remainsCount = httpRangeCount;

        if (httpRangeCount > 0) {
            long gotNum = httpRangeOffset - oriOffset;
            if (gotNum > 0 && httpRangeCount > gotNum) {
                remainsCount = httpRangeCount - gotNum;
            }
        }

        RangeGetOutput output;
        try {
            output = rangeGet.apply(remainsOffset, remainsCount);
        } catch (IOException e) {
            throw e;
        }

        // ETag consistency check
        String responseEtag = output.etag();
        if (responseEtag != null) {
            if (etag == null) {
                etag = responseEtag;
            } else if (!etag.equals(responseEtag)) {
                closeQuietly(output.body());
                throw new IOException("Source file is changed, expect etag:" + etag + " ,got etag:" + responseEtag);
            }
        }

        // Content-Range offset check
        if (output.contentRange() != null) {
            long[] parsed = parseContentRange(output.contentRange());
            if (parsed != null) {
                long responseOffset = parsed[0];
                totalSize = parsed[2];
                if (responseOffset != remainsOffset) {
                    closeQuietly(output.body());
                    throw new IOException("Range get fail, expect offset:" + remainsOffset + ", got offset:" + responseOffset);
                }
            }
        } else {
            totalSize = output.contentLength();
        }

        // Wrap with BoundedInputStream if count is specified
        InputStream body = output.body();
        if (remainsCount > 0) {
            body = new BoundedInputStream(body, remainsCount);
        }
        in = body;
    }

    private void closeCurrentStream() {
        if (in != null) {
            closeQuietly(in);
            in = null;
        }
    }

    @Override
    public void close() throws IOException {
        if (closed) {
            return;
        }
        closed = true;
        closeCurrentStream();
    }

    /**
     * Returns the current read offset (total bytes position from start of object).
     */
    public long getOffset() {
        return offset;
    }

    /**
     * Returns the total size of the object, or -1 if not yet known.
     */
    public long getTotalSize() {
        return totalSize;
    }

    /**
     * Parses a Content-Range header value.
     *
     * @param contentRange the header value, e.g. "bytes 0-99/1000"
     * @return long[]{start, end, total} or null if parsing fails; total is -1 for "*"
     */
    public static long[] parseContentRange(String contentRange) {
        if (contentRange == null) {
            return null;
        }
        Matcher m = CONTENT_RANGE_PATTERN.matcher(contentRange);
        if (!m.find()) {
            return null;
        }
        long start = Long.parseLong(m.group(1));
        long end = Long.parseLong(m.group(2));
        long total;
        if ("*".equals(m.group(3))) {
            total = -1;
        } else {
            total = Long.parseLong(m.group(3));
        }
        return new long[]{start, end, total};
    }

    private static void closeQuietly(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException ignored) {
            }
        }
    }
}
