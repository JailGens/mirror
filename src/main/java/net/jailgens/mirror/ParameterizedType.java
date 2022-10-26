package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.SideEffectFree;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A parameterized type.
 *
 * @author Sparky983
 * @param <T> the type.
 * @since 0.0.0
 */
public interface ParameterizedType<T extends @Nullable Object> extends Annotated, Type {

    /**
     * Creates a parameterized type with the specified raw type with the specified type arguments.
     *
     * @param rawType the raw type.
     * @param typeArguments the type arguments.
     * @param <T> the type.
     * @return the newly created parameterized type.
     * @throws NullPointerException if {@code rawType} is {@code null} or {@code typeArguments} is or contains
     * {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    @SafeVarargs
    static <T extends @Nullable Object> @NonNull ParameterizedType<T> of(final @NonNull Class<T> rawType,
                final @NonNull ParameterizedType<? extends @NonNull Object> @NonNull ... typeArguments) {

        // TODO(Sparky983): implement
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Creates a parameterized type with the specified raw type with the specified type arguments.
     *
     * @param rawType the raw type.
     * @param typeArguments the type arguments.
     * @param <T> the type.
     * @return the newly created parameterized type.
     * @throws NullPointerException if {@code rawType} is {@code null} or {@code typeArguments} is or contains
     * {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    @SafeVarargs
    static <T extends @NonNull Object> @NonNull ParameterizedType<T> of(final @NonNull Class<@NonNull T> rawType,
                final @NonNull Class<? extends @NonNull Object> @NonNull ... typeArguments) {

        // TODO(Sparky983): implement
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Creates a parameterized type with the specified raw type with no type arguments.
     *
     * @param rawType the raw type.
     * @param <T> the type.
     * @return the newly created parameterized type.
     * @throws NullPointerException if {@code rawType} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    static <T extends @NonNull Object> @NonNull ParameterizedType<T> of(final @NonNull Class<@NonNull T> rawType) {

        // TODO(Sparky983): implement
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Creates a map parameterized type with the specified key and value type.
     *
     * @param k the key type.
     * @param v the value type.
     * @param <K> the key type.
     * @param <V> the value type.
     * @return the newly created parameterized type.
     * @throws NullPointerException if {@code key} or {@code value} are {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    static <K extends @NonNull Object, V extends @NonNull Object>
            @NonNull ParameterizedType<@NonNull Map<@NonNull K, @NonNull V>> listOf(
            final @NonNull Class<@NonNull K> k, final @NonNull Class<@NonNull V> v) {

        // TODO(Sparky983): implement
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Creates a list parameterized type with the specified element type.
     *
     * @param e the element type.
     * @param <E> the element type.
     * @return the newly created parameterized type.
     * @throws NullPointerException if {@code e} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    static <E extends @NonNull Object> @NonNull ParameterizedType<@NonNull List<@NonNull E>> listOf(
            final @NonNull Class<@NonNull E> e) {

        // TODO(Sparky983): implement
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Creates a set parameterized type with the specified element type.
     *
     * @param e the element type.
     * @param <E> the element type.
     * @return the newly created parameterized type.
     * @throws NullPointerException if {@code e} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    static <E extends @NonNull Object> @NonNull ParameterizedType<@NonNull Set<@NonNull E>> setOf(
        final @NonNull Class<@NonNull E> e) {

        // TODO(Sparky983): implement
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Creates a collection parameterized type with the specified element type.
     *
     * @param e the element type.
     * @param <E> the element type.
     * @return the newly created parameterized type.
     * @throws NullPointerException if {@code e} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    static <E extends @NonNull Object> @NonNull ParameterizedType<@NonNull Collection<@NonNull E>> collectionOf(
        final @NonNull Class<@NonNull E> e) {

        // TODO(Sparky983): implement
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Gets parameterized type of this as a raw `java.lang.reflect` class.
     *
     * @return this parameterized type of this as a raw `java.lang.reflect` clas.
     * @since 0.0.0
     */
    @Pure
    @NonNull Class<T> getRawType();
}
