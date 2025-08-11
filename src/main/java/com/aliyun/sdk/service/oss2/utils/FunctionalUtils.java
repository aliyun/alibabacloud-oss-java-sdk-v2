package com.aliyun.sdk.service.oss2.utils;


import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class FunctionalUtils {

    private FunctionalUtils() {
    }

    public static <T> Consumer<T> noOpConsumer() {
        return ignored -> {
        };
    }

    public static Runnable noOpRunnable() {
        return () -> {
        };
    }

    public static <I> Consumer<I> safeConsumer(UnsafeConsumer<I> unsafeConsumer) {
        return (input) -> {
            try {
                unsafeConsumer.accept(input);
            } catch (Exception e) {
                throw asRuntimeException(e);
            }
        };
    }

    public static <T, R> Function<T, R> safeFunction(UnsafeFunction<T, R> unsafeFunction) {
        return t -> {
            try {
                return unsafeFunction.apply(t);
            } catch (Exception e) {
                throw asRuntimeException(e);
            }
        };
    }

    public static <T> Supplier<T> safeSupplier(UnsafeSupplier<T> unsafeSupplier) {
        return () -> {
            try {
                return unsafeSupplier.get();
            } catch (Exception e) {
                throw asRuntimeException(e);
            }
        };
    }

    public static Runnable safeRunnable(UnsafeRunnable unsafeRunnable) {
        return () -> {
            try {
                unsafeRunnable.run();
            } catch (Exception e) {
                throw asRuntimeException(e);
            }
        };
    }

    public static <I, O> Function<I, O> toFunction(Supplier<O> supplier) {
        return ignore -> supplier.get();
    }

    public static <T> T invokeSafely(UnsafeSupplier<T> unsafeSupplier) {
        return safeSupplier(unsafeSupplier).get();
    }

    public static void invokeSafely(UnsafeRunnable unsafeRunnable) {
        safeRunnable(unsafeRunnable).run();
    }

    private static RuntimeException asRuntimeException(Exception exception) {
        if (exception instanceof RuntimeException) {
            return (RuntimeException) exception;
        }
        if (exception instanceof IOException) {
            return new UncheckedIOException((IOException) exception);
        }
        if (exception instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
        return new RuntimeException(exception);
    }

    public interface UnsafeConsumer<I> {
        void accept(I input) throws Exception;
    }

    public interface UnsafeFunction<T, R> {
        R apply(T t) throws Exception;
    }

    public interface UnsafeSupplier<T> {
        T get() throws Exception;
    }

    public interface UnsafeRunnable {
        void run() throws Exception;
    }
}