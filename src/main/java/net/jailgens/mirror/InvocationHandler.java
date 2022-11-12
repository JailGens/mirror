package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * An invocation handler for proxies created via {@link Mirror}.
 *
 * @author Sparky983
 * @param <T> the proxy type.
 * @since 0.2.0
 */
public interface InvocationHandler<T extends @NonNull Object> {

    <R extends @Nullable Object> @NonNull R invoke(
            @NonNull T receiver,
            @NonNull Method<T, R> method,
            @Nullable Object @NonNull ... args) throws Throwable;
}
