package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.lang.reflect.Modifier.isAbstract;
import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isNative;
import static java.lang.reflect.Modifier.isPrivate;
import static java.lang.reflect.Modifier.isProtected;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import static java.lang.reflect.Modifier.isStrict;
import static java.lang.reflect.Modifier.isSynchronized;
import static java.lang.reflect.Modifier.isTransient;
import static java.lang.reflect.Modifier.isVolatile;

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
    STRICTFP;

    static @NonNull Set<@NonNull Modifier> modifiersAsSet(final int modifiers) {

        final Set<Modifier> modifiersSet = new HashSet<>();

        if (isPublic(modifiers)) {
            modifiersSet.add(Modifier.PUBLIC);
        }

        if (isPrivate(modifiers)) {
            modifiersSet.add(Modifier.PRIVATE);
        }

        if (isProtected(modifiers)) {
            modifiersSet.add(Modifier.PROTECTED);
        }

        if (isStatic(modifiers)) {
            modifiersSet.add(Modifier.STATIC);
        }

        if (isFinal(modifiers)) {
            modifiersSet.add(Modifier.FINAL);
        }

        if (isSynchronized(modifiers)) {
            modifiersSet.add(Modifier.SYNCHRONIZED);
        }

        if (isVolatile(modifiers)) {
            modifiersSet.add(Modifier.VOLATILE);
        }

        if (isTransient(modifiers)) {
            modifiersSet.add(Modifier.TRANSIENT);
        }

        if (isNative(modifiers)) {
            modifiersSet.add(Modifier.NATIVE);
        }

        if (isAbstract(modifiers)) {
            modifiersSet.add(Modifier.ABSTRACT);
        }

        if (isStrict(modifiers)) {
            modifiersSet.add(Modifier.STRICTFP);
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
}
