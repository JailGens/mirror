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
        Annotated, Invokable<@NonNull R, @NonNull T> {}
