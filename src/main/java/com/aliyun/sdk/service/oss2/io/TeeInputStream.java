package com.aliyun.sdk.service.oss2.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TeeInputStream extends FilterInputStream {

    /**
     * The output stream that will receive a copy of all bytes read from the
     * proxied input stream.
     */
    private final OutputStream branch;

    /**
     * Flag for closing the associated output stream when this stream is closed.
     */
    private final boolean closeBranch;

    /**
     * Creates a <code>FilterInputStream</code>
     * by assigning the  argument <code>in</code>
     * to the field <code>this.in</code> so as
     * to remember it for later use.
     *
     * @param input  the underlying input stream, or <code>null</code> if
     *               this instance is to be created without an underlying stream.
     * @param branch output stream that will receive a copy of all bytes read
     */
    public TeeInputStream(final InputStream input, final OutputStream branch) {
        super(input);
        this.branch = branch;
        this.closeBranch = false;
    }

    /**
     * Creates a <code>FilterInputStream</code>
     * by assigning the  argument <code>in</code>
     * to the field <code>this.in</code> so as
     * to remember it for later use.
     *
     * @param input       the underlying input stream, or <code>null</code> if
     *                    this instance is to be created without an underlying stream.
     * @param branch      output stream that will receive a copy of all bytes read
     * @param closeBranch flag for closing also the output stream when this
     *                    stream is closed
     */
    public TeeInputStream(final InputStream input, final OutputStream branch, final boolean closeBranch) {
        super(input);
        this.branch = branch;
        this.closeBranch = closeBranch;
    }

    /**
     * Closes the proxied input stream and, if so configured, the associated
     * output stream. An exception thrown from one stream will not prevent
     * closing of the other stream.
     *
     * @throws IOException if either of the streams could not be closed
     */
    @Override
    public void close() throws IOException {
        try {
            super.close();
        } finally {
            if (closeBranch) {
                branch.close();
            }
        }
    }

    /**
     * Reads a single byte from the proxied input stream and writes it to
     * the associated output stream.
     *
     * @return next byte from the stream, or -1 if the stream has ended
     * @throws IOException if the stream could not be read (or written)
     */
    @Override
    public int read() throws IOException {
        final int ch = in.read();
        if (ch != -1) {
            branch.write(ch);
        }
        return ch;
    }

    /**
     * Reads bytes from the proxied input stream and writes the read bytes
     * to the associated output stream.
     *
     * @param bts byte buffer
     * @param st  start offset within the buffer
     * @param end maximum number of bytes to read
     * @return number of bytes read, or -1 if the stream has ended
     * @throws IOException if the stream could not be read (or written)
     */
    @Override
    public int read(final byte[] bts, final int st, final int end) throws IOException {
        final int n = in.read(bts, st, end);
        if (n != -1) {
            branch.write(bts, st, n);
        }
        return n;
    }

    /**
     * Unwraps this instance by returning the underlying {@link InputStream}.
     *
     * @return the underlying {@link InputStream}.
     */
    public InputStream unwrap() {
        return in;
    }
}
