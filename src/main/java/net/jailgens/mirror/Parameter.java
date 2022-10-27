package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.Pure;

import java.util.Set;

/**
 * A parameter.
 *
 * @author Sparky983
 * @param <T> the type of the parameter.
 * @since 0.0.0
 */
public interface Parameter<T> extends Annotated, Typed<T> {

    /**
     * Gets this member's name.
     *
     * @return this member's name.
     * @since 0.0.0
     */
    @Pure
    @NonNull String getName();

    /**
     * Gets all this member's modifiers.
     *
     * @return all this member's modifiers.
     * @since 0.0.0
     */
    @Pure
    @NonNull Set<@NonNull Modifier> getModifiers();
}
