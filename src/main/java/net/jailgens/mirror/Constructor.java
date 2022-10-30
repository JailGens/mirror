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
     * @throws ClassCastException if any of {@code arguments} could not be cast to this
     * constructor's parameter types.
     * @throws IllegalArgumentException if {@code arguments != getParameters().size()}.
     * @throws IllegalStateException if this constructor is not instantiatable (part of abstract
     * class).
     * @throws InvocationException if the target constructor threw an exception.
     * @throws NullPointerException if {@code arguments} is {@code null}.
     */
    @NonNull T construct(@Nullable Object @NonNull ... arguments);
}
