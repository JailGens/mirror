package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A constructor.
 *
 * @param <T> the type this constructor constructs.
 */
public interface Constructor<T extends @NonNull Object> extends
    Annotated, Invokable<@NonNull T, @NonNull T> {

    /**
     * Calls the constructor with the specified arguments.
     *
     * @param arguments the arguments.
     * @return the constructed objects.
     * @throws InvocationException if the target constructor threw an exception.
     * <p>
     * The cause of the invocation exception is the exception that the target threw.
     * @throws NullPointerException if {@code arguments} is {@code null}.
     */
    @NonNull T construct(@Nullable Object @NonNull ... arguments);
}
