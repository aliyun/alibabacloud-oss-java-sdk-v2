package com.aliyun.sdk.service.oss2.io;


import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

/**
 * Always throws an exception from all {@link InputStream} methods where {@link IOException} is declared.
 */
public class BrokenInputStream extends InputStream {

    /**
     * The singleton instance using a default IOException.
     */
    public static final BrokenInputStream INSTANCE = new BrokenInputStream();

    /**
     * A supplier for the exception that is thrown by all methods of this class.
     */
    private final Supplier<Throwable> exceptionSupplier;

    /**
     * Constructs a new stream that always throws an {@link IOException}.
     */
    public BrokenInputStream() {
        this(() -> new IOException("Broken input stream"));
    }

    /**
     * Constructs a new stream that always throws the given exception.
     *
     * @param exception the exception to be thrown.
     * @deprecated Use {@link #BrokenInputStream(Throwable)}.
     */
    @Deprecated
    public BrokenInputStream(final IOException exception) {
        this(() -> exception);
    }

    /**
     * Constructs a new stream that always throws the supplied exception.
     *
     * @param exceptionSupplier a supplier for the IOException or RuntimeException to be thrown.
     * @since 2.12.0
     */
    public BrokenInputStream(final Supplier<Throwable> exceptionSupplier) {
        this.exceptionSupplier = exceptionSupplier;
    }

    /**
     * Constructs a new stream that always throws the given exception.
     *
     * @param exception the exception to be thrown.
     * @since 2.16.0
     */
    public BrokenInputStream(final Throwable exception) {
        this(() -> exception);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Throwable> RuntimeException rethrow(final Throwable throwable) throws T {
        throw (T) throwable;
    }

    /**
     * Throws the configured exception.
     *
     * @return nothing.
     * @throws IOException always throws the exception configured in a constructor.
     */
    @Override
    public int available() throws IOException {
        throw rethrow();
    }

    /**
     * Throws the configured exception.
     *
     * @throws IOException always throws the exception configured in a constructor.
     */
    @Override
    public void close() throws IOException {
        throw rethrow();
    }

    /**
     * Gets the Throwable to throw. Package-private for testing.
     *
     * @return the Throwable to throw.
     */
    Throwable getThrowable() {
        return exceptionSupplier.get();
    }

    /**
     * Throws the configured exception.
     *
     * @return nothing.
     * @throws IOException always throws the exception configured in a constructor.
     */
    @Override
    public int read() throws IOException {
        throw rethrow();
    }

    /**
     * Throws the configured exception.
     *
     * @throws IOException always throws the exception configured in a constructor.
     */
    @Override
    public synchronized void reset() throws IOException {
        throw rethrow();
    }

    /**
     * Throws the configured exception from its supplier.
     *
     * @return Throws the configured exception from its supplier.
     */
    private RuntimeException rethrow() {
        return rethrow(getThrowable());
    }

    /**
     * Throws the configured exception.
     *
     * @param n ignored.
     * @return nothing.
     * @throws IOException always throws the exception configured in a constructor.
     */
    @Override
    public long skip(final long n) throws IOException {
        throw rethrow();
    }

}