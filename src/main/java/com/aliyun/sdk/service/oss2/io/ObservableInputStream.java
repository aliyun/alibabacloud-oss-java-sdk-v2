package com.aliyun.sdk.service.oss2.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The {@link ObservableInputStream} allows, that an InputStream may be consumed by other receivers, apart from the
 * thread, which is reading it. The other consumers are implemented as instances of {@link StreamObserver}.
 */

public class ObservableInputStream extends FilterInputStream {

    private boolean noteFinishedDone;
    private final List<StreamObserver> observers;

    /**
     * Constructs a new ObservableInputStream for the given InputStream.
     *
     * @param inputStream the input stream to observe.
     * @param observers   List of observer callbacks.
     */
    private ObservableInputStream(final InputStream inputStream, final List<StreamObserver> observers) {
        super(inputStream);
        this.observers = observers;
        this.noteFinishedDone = false;
    }

    /**
     * Constructs a new ObservableInputStream for the given InputStream.
     *
     * @param inputStream the input stream to observe.
     */
    public ObservableInputStream(final InputStream inputStream) {
        this(inputStream, new ArrayList<>());
    }


    /**
     * Constructs a new ObservableInputStream for the given InputStream.
     *
     * @param inputStream the input stream to observe.
     * @param observers   List of observer callbacks.
     */
    public ObservableInputStream(final InputStream inputStream, final StreamObserver... observers) {
        this(inputStream, Arrays.asList(observers));
    }

    /**
     * Adds an Observer.
     *
     * @param observer the observer to add.
     */
    public void add(final StreamObserver observer) {
        observers.add(observer);
    }


    /**
     * Removes an Observer.
     *
     * @param observer the observer to remove
     */
    public void remove(final StreamObserver observer) {
        observers.remove(observer);
    }

    /**
     * Removes all Observers.
     */
    public void removeAllObservers() {
        observers.clear();
    }

    /**
     * Gets a copy of currently registered observers.
     *
     * @return a copy of the list of currently registered observers.
     */
    public List<StreamObserver> getObservers() {
        return new ArrayList<>(observers);
    }

    /**
     * Unwraps this instance by returning the underlying {@link InputStream}.
     *
     * @return the underlying {@link InputStream}.
     */
    public InputStream unwrap() {
        return in;
    }

    /**
     * Notifies the observers by invoking {@link StreamObserver#finished()}.
     * Ignore the exception thrown by observer
     */
    public void tryNoteFinished() {
        try {
            noteFinished();
        } catch (Exception ignore) {}
    }

    /**
     * Notifies the observers by invoking {@link StreamObserver#finished()}.
     *
     * @throws IOException Some observer has thrown an exception, which is being passed down.
     */
    protected void noteClosed() throws IOException {
        for (StreamObserver observer : this.observers) {
            observer.closed();
        }
    }

    /**
     * Notifies the observers by invoking {@link StreamObserver#data(int)} with the given arguments.
     *
     * @param value Passed to the observers.
     * @throws IOException Some observer has thrown an exception, which is being passed down.
     */
    protected void noteDataByte(final int value) throws IOException {
        for (StreamObserver observer : this.observers) {
            observer.data(value);
        }
    }

    /**
     * Notifies the observers by invoking {@link StreamObserver#data(byte[], int, int)} with the given arguments.
     *
     * @param buffer Passed to the observers.
     * @param offset Passed to the observers.
     * @param length Passed to the observers.
     * @throws IOException Some observer has thrown an exception, which is being passed down.
     */
    protected void noteDataBytes(final byte[] buffer, final int offset, final int length) throws IOException {
        for (StreamObserver observer : this.observers) {
            observer.data(buffer, offset, length);
        }
    }

    /**
     * Notifies the observers by invoking {@link StreamObserver#error(IOException)} with the given argument.
     *
     * @param exception Passed to the observers.
     * @throws IOException Some observer has thrown an exception, which is being passed down. This may be the same
     *                     exception, which has been passed as an argument.
     */
    protected void noteError(final IOException exception) throws IOException {
        for (StreamObserver observer : this.observers) {
            observer.error(exception);
        }
    }

    /**
     * Notifies the observers by invoking {@link StreamObserver#finished()}.
     *
     * @throws IOException Some observer has thrown an exception, which is being passed down.
     */
    protected void noteFinished() throws IOException {
        if (!this.noteFinishedDone) {
            for (StreamObserver observer : this.observers) {
                observer.finished();
            }
        }
        this.noteFinishedDone = true;
    }

    private void notify(final byte[] buffer, final int offset, final int result, final IOException ioe) throws IOException {
        if (ioe != null) {
            noteError(ioe);
            throw ioe;
        }
        if (result == -1) {
            noteFinished();
        } else if (result > 0) {
            noteDataBytes(buffer, offset, result);
        }
    }

    @Override
    public int read() throws IOException {
        int result = 0;
        IOException ioe = null;
        try {
            result = in.read();
        } catch (final IOException ex) {
            ioe = ex;
        }
        if (ioe != null) {
            noteError(ioe);
            throw ioe;
        }
        if (result == -1) {
            noteFinished();
        } else {
            noteDataByte(result);
        }
        return result;
    }

    @Override
    public int read(final byte[] buffer, final int offset, final int length) throws IOException {
        int result = 0;
        IOException ioe = null;
        try {
            result = in.read(buffer, offset, length);
        } catch (final IOException ex) {
            ioe = ex;
        }
        notify(buffer, offset, result, ioe);
        return result;
    }


    @Override
    public void close() throws IOException {
        IOException ioe = null;
        try {
            in.close();
        } catch (final IOException e) {
            ioe = e;
        }
        if (ioe == null) {
            noteClosed();
        } else {
            noteError(ioe);
        }
    }
}
