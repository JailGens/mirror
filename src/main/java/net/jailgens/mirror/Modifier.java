package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * An enum of all modifiers.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public enum Modifier {

    /**
     * The {@code public} modifier.
     *
     * @since 0.0.0
     */
    PUBLIC,

    /**
     * The {@code private} modifier.
     *
     * @since 0.0.0
     */
    PRIVATE,


    /**
     * The {@code protected} modifier.
     *
     * @since 0.0.0
     */
    PROTECTED,

    /**
     * The {@code static} modifier.
     *
     * @since 0.0.0
     */
    STATIC,

    /**
     * The {@code final} modifier.
     *
     * @since 0.0.0
     */
    FINAL,

    /**
     * The {@code synchronized} modifier.
     *
     * @since 0.0.0
     */
    SYNCHRONIZED,

    /**
     * The {@code volatile} modifier.
     *
     * @since 0.0.0
     */
    VOLATILE,

    /**
     * The {@code transient} modifier.
     *
     * @since 0.0.0
     */
    TRANSIENT,

    /**
     * The {@code native} modifier.
     *
     * @since 0.0.0
     */
    NATIVE,

    /**
     * The {@code abstract} modifier.
     *
     * @since 0.0.0
     */
    ABSTRACT,

    /**
     * The {@code strictfp} modifier.
     *
     * @since 0.0.0
     */
    STRICTFP,

    /**
     * The {@code default} modifier.
     *
     * @since 0.0.0
     */
    DEFAULT;

    /**
     * Returns the modifier as a string that can be used in java source code.
     *
     * @return the modifier as a string that can be used in java source code.
     * @since 0.0.0
     */
    @Override
    public @NonNull String toString() {
        return name().toLowerCase();
    }
}
