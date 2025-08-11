package com.aliyun.sdk.service.oss2.io;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

/**
 * A writer that duplicates its writes to all the provided writers,
 * similar to the Unix tee(1) command.
 */
public class MultiWriter extends Writer {
    /**
     * The underlying writers.
     */
    protected final Collection<Writer> writers;

    /**
     * Constructs a new filtered collection writer.
     *
     * @param writers Writers to provide the underlying targets.
     */
    public MultiWriter(final Collection<Writer> writers) {
        requireNonNull(writers);
        this.writers = writers;
    }

    /**
     * Constructs a new filtered collection writer.
     *
     * @param writers Writers to provide the underlying targets.
     */
    public MultiWriter(final Writer... writers) {
        requireNonNull(writers);
        this.writers = Arrays.asList(writers);
    }

    /**
     * Writes a single character.
     *
     * @throws IOException If an I/O error occurs
     */
    public void write(int c) throws IOException {
        for (Writer w : this.writers) {
            w.write(c);
        }
    }

    /**
     * Writes a portion of an array of characters.
     *
     * @param cbuf Buffer of characters to be written
     * @param off  Offset from which to start reading characters
     * @param len  Number of characters to be written
     * @throws IndexOutOfBoundsException If the values of the {@code off} and {@code len} parameters
     *                                   cause the corresponding method of the underlying {@code Writer}
     *                                   to throw an {@code IndexOutOfBoundsException}
     * @throws IOException               If an I/O error occurs
     */
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (Writer w : this.writers) {
            w.write(cbuf, off, len);
        }
    }

    /**
     * Writes a portion of a string.
     *
     * @param str String to be written
     * @param off Offset from which to start reading characters
     * @param len Number of characters to be written
     * @throws IndexOutOfBoundsException If the values of the {@code off} and {@code len} parameters
     *                                   cause the corresponding method of the underlying {@code Writer}
     *                                   to throw an {@code IndexOutOfBoundsException}
     * @throws IOException               If an I/O error occurs
     */
    public void write(String str, int off, int len) throws IOException {
        for (Writer w : this.writers) {
            w.write(str, off, len);
        }
    }

    /**
     * Flushes the stream.
     *
     * @throws IOException If an I/O error occurs
     */
    public void flush() throws IOException {
        for (Writer w : this.writers) {
            w.flush();
        }
    }

    public void close() throws IOException {
        for (Writer w : this.writers) {
            w.close();
        }
    }

}
