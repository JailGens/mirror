package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.Pure;

import java.util.Set;

/**
 * Represents a class member.
 *
 * @author Sparky983
 * @param <T> the declaring type.
 * @see java.lang.reflect.Member
 * @since 0.0.0
 */
public interface Member<T extends @NonNull Object> {

    /**
     * Gets this member's name.
     * <p>
     * For constructors this is {@literal <init>}
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

    /**
     * Gets this member's declaring type.
     *
     * @return this member's declaring type.
     * @since 0.0.0
     */
    @Pure
    @NonNull TypeDefinition<@NonNull T> getDeclaringType();

    /**
     * Gets this member's raw declaring type.
     *
     * @return this member's raw declaring type.
     * @since 0.0.0
     */
    @Pure
    @NonNull Class<@NonNull T> getRawDeclaringType();
}
