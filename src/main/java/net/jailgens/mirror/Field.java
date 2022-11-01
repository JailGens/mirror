package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A field.
 *
 * @author Sparky983
 * @param <R> the type that declares the field.
 * @param <T> the type of the field.
 * @since 0.0.0
 */
public interface Field<R extends @NonNull Object, T extends @Nullable Object> extends Member<@NonNull R>, Typed<@NonNull T> {

    /**
     * Gets the value of this field.
     *
     * @param receiver the object to retrieve the field value from, may optionally be {@code null}
     * this has no receiver (static field).
     * @return the value of the field.
     * @throws IllegalStateException if this field is {@code final}.
     * @throws NullPointerException if {@code receiver} is {@code null}, and this requires a
     * receiver.
     * @since 0.0.0
     */
    @NonNull T get(@Nullable R receiver);

    /**
     * Sets the value of this field.
     *
     * @param receiver the object to set the field value on, may optionally be {@code null}
     * if this has no receiver (static field).
     * @param value the value.
     * @throws NullPointerException if {@code receiver} is {@code null}, and this requires a
     * receiver.
     * @since 0.0.0
     */
    void set(@Nullable R receiver, @NonNull T value);
}
