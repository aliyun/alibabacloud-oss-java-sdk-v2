package com.aliyun.sdk.service.oss2.transport.apache5client;

import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.core5.util.TimeValue;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public final class IdleConnectionReaper {
    //private static final int REAP_INTERVAL_MILLISECONDS = 5 * 1000;
    private static final Duration REAP_INTERVAL = Duration.ofSeconds(10);

    private static final IdleConnectionReaper INSTANCE = new IdleConnectionReaper();

    private final Map<PoolingHttpClientConnectionManager, Long> connectionManagers;

    private final Supplier<ExecutorService> executorServiceSupplier;

    private final long sleepPeriod;

    private volatile ExecutorService exec;

    private volatile ReaperTask reaperTask;

    private IdleConnectionReaper() {
        this.connectionManagers = Collections.synchronizedMap(new WeakHashMap<>());

        this.executorServiceSupplier = () -> {
            ExecutorService e = Executors.newSingleThreadExecutor(r -> {
                Thread t = new Thread(r, "idle-connection-reaper");
                t.setDaemon(true);
                return t;
            });
            return e;
        };

        this.sleepPeriod = REAP_INTERVAL.toMillis();
    }

    IdleConnectionReaper(Map<PoolingHttpClientConnectionManager, Long> connectionManagers,
                         Supplier<ExecutorService> executorServiceSupplier,
                         long sleepPeriod) {

        this.connectionManagers = connectionManagers;
        this.executorServiceSupplier = executorServiceSupplier;
        this.sleepPeriod = sleepPeriod;
    }

    /**
     * @return The singleton instance of this class.
     */
    public static IdleConnectionReaper getInstance() {
        return INSTANCE;
    }

    /**
     * Register the connection manager with this reaper.
     *
     * @param manager     The connection manager.
     * @param maxIdleTime The maximum time connections in the connection manager are to remain idle before being reaped.
     * @return {@code true} If the connection manager was not previously registered with this reaper, {@code false}
     * otherwise.
     */
    @SuppressWarnings("UnusedReturnValue")
    public synchronized boolean registerConnectionManager(PoolingHttpClientConnectionManager manager, long maxIdleTime) {
        boolean notPreviouslyRegistered = connectionManagers.put(manager, maxIdleTime) == null;
        setupExecutorIfNecessary();
        return notPreviouslyRegistered;
    }

    /**
     * Deregister this connection manager with this reaper.
     *
     * @param manager The connection manager.
     * @return {@code true} If this connection manager was previously registered with this reaper and it was removed, {@code
     * false} otherwise.
     */
    @SuppressWarnings("UnusedReturnValue")
    public synchronized boolean deregisterConnectionManager(HttpClientConnectionManager manager) {
        boolean wasRemoved = connectionManagers.remove(manager) != null;
        cleanupExecutorIfNecessary();
        return wasRemoved;
    }

    private void setupExecutorIfNecessary() {
        if (exec != null) {
            return;
        }

        ExecutorService e = executorServiceSupplier.get();

        this.reaperTask = new ReaperTask(connectionManagers, sleepPeriod);

        e.execute(this.reaperTask);

        exec = e;
    }

    private void cleanupExecutorIfNecessary() {
        if (exec == null || !connectionManagers.isEmpty()) {
            return;
        }

        reaperTask.stop();
        reaperTask = null;
        exec.shutdownNow();
        exec = null;
    }

    private static final class ReaperTask implements Runnable {
        private final Map<PoolingHttpClientConnectionManager, Long> connectionManagers;
        private final long sleepPeriod;

        private volatile boolean stopping = false;

        private ReaperTask(Map<PoolingHttpClientConnectionManager, Long> connectionManagers,
                           long sleepPeriod) {
            this.connectionManagers = connectionManagers;
            this.sleepPeriod = sleepPeriod;
        }

        @Override
        public void run() {
            while (!stopping) {
                try {
                    Thread.sleep(sleepPeriod);

                    for (Map.Entry<PoolingHttpClientConnectionManager, Long> entry : connectionManagers.entrySet()) {
                        try {
                            entry.getKey().closeIdle(TimeValue.ofMilliseconds(entry.getValue()));
                        } catch (Exception t) {
                            //log.warn("Unable to close idle connections", t);
                        }
                    }
                } catch (Throwable t) {
                    //log.debug("Reaper thread: ", t);
                }
            }
            //log.debug("Shutting down reaper thread.");
        }

        private void stop() {
            stopping = true;
        }
    }
}