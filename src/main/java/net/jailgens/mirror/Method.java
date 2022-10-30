package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A method.
 *
 * @param <R> the receiver type. for static methods, this is the declaring class.
 * @param <T> the method's return type.
 */
public interface Method<R extends @NonNull Object, T extends @Nullable Object> extends
        Annotated, Invokable<@NonNull R, @NonNull T> {

    /**
     * Invokes this invokable on the receiver with the specified arguments.
     *
     * @param receiver the receiver, may optionally be {@code null} if this has no receiver.
     * @param arguments the arguments to call this with.
     * @return the result.
     * @throws ClassCastException if any of {@code arguments} could not be cast to this method's
     * parameter types.
     * @throws IllegalArgumentException if {@code arguments != getParameters().size()}.
     * @throws InvocationException if the target invokable (executable) threw an exception.
     * @throws NullPointerException if {@code receiver} is {@code null}, and this requires a receiver, or
     * {@code arguments} is {@code null}
     * @since 0.0.0
     */
    @NonNull T invoke(@Nullable R receiver, @Nullable Object @NonNull ... arguments);
}
