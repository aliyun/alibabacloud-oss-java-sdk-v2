package com.aliyun.sdk.service.oss2.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

public class MultiOutputStream extends OutputStream {

    /**
     * The OutputStream to write to.
     */
    protected final Collection<OutputStream> branches;

    /**
     * Constructs a new filtered collection OutputStream.
     *
     * @param branches OutputStream to provide the underlying targets.
     */
    public MultiOutputStream(final Collection<OutputStream> branches) {
        requireNonNull(branches);
        this.branches = branches;
    }

    /**
     * Constructs a new filtered collection OutputStream.
     *
     * @param branches OutputStream to provide the underlying targets.
     */
    public MultiOutputStream(final OutputStream... branches) {
        requireNonNull(branches);
        this.branches = Arrays.asList(branches);
    }

    /**
     * Writes the specified <code>byte</code> to this output stream.
     * <p>
     * The <code>write</code> method of <code>FilterOutputStream</code>
     * calls the <code>write</code> method of its underlying output stream,
     * that is, it performs {@code out.write(b)}.
     * <p>
     * Implements the abstract {@code write} method of {@code OutputStream}.
     *
     * @param b the <code>byte</code>.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void write(int b) throws IOException {
        for (OutputStream branch : this.branches) {
            branch.write(b);
        }
    }

    /**
     * Writes <code>b.length</code> bytes to this output stream.
     * <p>
     * The <code>write</code> method of <code>FilterOutputStream</code>
     * calls its <code>write</code> method of three arguments with the
     * arguments <code>b</code>, <code>0</code>, and
     * <code>b.length</code>.
     * <p>
     * Note that this method does not call the one-argument
     * <code>write</code> method of its underlying output stream with
     * the single argument <code>b</code>.
     *
     * @param b the data to be written.
     * @throws IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#write(byte[], int, int)
     */
    @Override
    public void write(byte[] b) throws IOException {
        for (OutputStream branch : this.branches) {
            branch.write(b, 0, b.length);
        }
    }

    /**
     * Writes <code>len</code> bytes from the specified
     * <code>byte</code> array starting at offset <code>off</code> to
     * this output stream.
     * <p>
     * The <code>write</code> method of <code>FilterOutputStream</code>
     * calls the <code>write</code> method of one argument on each
     * <code>byte</code> to output.
     * <p>
     * Note that this method does not call the <code>write</code> method
     * of its underlying output stream with the same arguments. Subclasses
     * of <code>FilterOutputStream</code> should provide a more efficient
     * implementation of this method.
     *
     * @param b   the data.
     * @param off the start offset in the data.
     * @param len the number of bytes to write.
     * @throws IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#write(int)
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        for (OutputStream branch : this.branches) {
            branch.write(b, off, len);
        }
    }

    /**
     * Flushes this output stream and forces any buffered output bytes
     * to be written out to the stream.
     * <p>
     * The <code>flush</code> method of <code>FilterOutputStream</code>
     * calls the <code>flush</code> method of its underlying output stream.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void flush() throws IOException {
        for (OutputStream branch : this.branches) {
            branch.flush();
        }
    }

    /**
     * Closes this output stream and releases any system resources
     * associated with the stream.
     * <p>
     * When not already closed, the {@code close} method of {@code
     * FilterOutputStream} calls its {@code flush} method, and then
     * calls the {@code close} method of its underlying output stream.
     *
     * @throws IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#flush()
     */
    @Override
    public void close() throws IOException {
        for (OutputStream branch : this.branches) {
            branch.close();
        }
    }
}
