package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    PUBLIC(java.lang.reflect.Modifier.PUBLIC),

    /**
     * The {@code private} modifier.
     *
     * @since 0.0.0
     */
    PRIVATE(java.lang.reflect.Modifier.PRIVATE),

    /**
     * The {@code protected} modifier.
     *
     * @since 0.0.0
     */
    PROTECTED(java.lang.reflect.Modifier.PROTECTED),

    /**
     * The {@code static} modifier.
     *
     * @since 0.0.0
     */
    STATIC(java.lang.reflect.Modifier.STATIC),

    /**
     * The {@code final} modifier.
     *
     * @since 0.0.0
     */
    FINAL(java.lang.reflect.Modifier.FINAL),

    /**
     * The {@code synchronized} modifier.
     *
     * @since 0.0.0
     */
    SYNCHRONIZED(java.lang.reflect.Modifier.SYNCHRONIZED),

    /**
     * The {@code volatile} modifier.
     *
     * @since 0.0.0
     */
    VOLATILE(java.lang.reflect.Modifier.VOLATILE),

    /**
     * The {@code transient} modifier.
     *
     * @since 0.0.0
     */
    TRANSIENT(java.lang.reflect.Modifier.TRANSIENT),

    /**
     * The {@code native} modifier.
     *
     * @since 0.0.0
     */
    NATIVE(java.lang.reflect.Modifier.NATIVE),

    /**
     * The {@code abstract} modifier.
     *
     * @since 0.0.0
     */
    ABSTRACT(java.lang.reflect.Modifier.ABSTRACT),

    /**
     * The {@code strictfp} modifier.
     *
     * @since 0.0.0
     */
    STRICTFP(java.lang.reflect.Modifier.STRICT);

    private final int modifier;

    Modifier(int modifier) {

        this.modifier = modifier;
    }

    private static final class Modifiers {

        private static final Modifier[] VALUES = Modifier.values();
    }

    static @NonNull Set<@NonNull Modifier> modifiersAsSet(final int modifiers) {

        final Set<Modifier> modifiersSet = new HashSet<>();

        for (final Modifier modifier : Modifiers.VALUES) {
            if (modifier.in(modifiers)) {
                modifiersSet.add(modifier);
            }
        }

        return Collections.unmodifiableSet(modifiersSet);
    }

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

    /**
     * Checks if the given modifiers contain this modifier.
     *
     * @param modifiers the modifiers.
     * @return whether the given modifiers contain this modifier.
     * @since 0.0.0
     */
     public boolean in(final int modifiers) {

        return (modifier & modifiers) != 0;
     }
}
