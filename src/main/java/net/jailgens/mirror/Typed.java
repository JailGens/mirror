package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.Pure;

/**
 * Represents anything that is typed.
 *
 * @author Sparky983
 * @param <T> the type.
 * @since 0.0.0
 */
public interface Typed<T> {

    /**
     * Gets the type of this.
     *
     * @return the type of this.
     * @since 0.0.0
     */
    @Pure
    @NonNull ParameterizedType<T> getType();

    /**
     * Gets the raw type of this.
     *
     * @return the raw type of this.
     * @since 0.0.0
     */
    @Pure
    @NonNull Class<T> getRawType();
}
