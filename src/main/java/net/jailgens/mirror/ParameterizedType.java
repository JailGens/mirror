package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.SideEffectFree;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    static <T extends @Nullable Object> @NonNull ParameterizedType<T> of(
            final @NonNull Class<T> rawType,
            final @NonNull ParameterizedType<? extends @Nullable Object> @NonNull ... typeArguments) {

        return new ParameterizedTypeImpl<>(AnnotationValues.empty(), rawType, typeArguments);
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
    static <T extends @NonNull Object> @NonNull ParameterizedType<T> of(
            final @NonNull Class<@NonNull T> rawType,
            final @NonNull Class<? extends @NonNull Object> @NonNull ... typeArguments) {

        Objects.requireNonNull(typeArguments, "typeArguments cannot be null");

        return of(rawType,
                Arrays.stream(typeArguments)
                        .<ParameterizedType<?>>map(ParameterizedType::of)
                        .toArray(ParameterizedType[]::new));
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

        Objects.requireNonNull(rawType, "rawType cannot be null");

        return new ParameterizedTypeImpl<>(AnnotationValues.empty(), rawType);
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
    @SuppressWarnings({"unchecked", "rawtypes"})
    @SideEffectFree
    static <K extends @NonNull Object, V extends @NonNull Object>
            @NonNull ParameterizedType<@NonNull Map<@NonNull K, @NonNull V>> mapOf(
            final @NonNull Class<@NonNull K> k, final @NonNull Class<@NonNull V> v) {

        return of((Class) Map.class, k, v);
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
    @SuppressWarnings({"unchecked", "rawtypes"})
    @SideEffectFree
    static <E extends @NonNull Object> @NonNull ParameterizedType<@NonNull List<@NonNull E>> listOf(
            final @NonNull Class<@NonNull E> e) {

        return of((Class) List.class, e);
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
    @SuppressWarnings({"unchecked", "rawtypes"})
    @SideEffectFree
    static <E extends @NonNull Object> @NonNull ParameterizedType<@NonNull Set<@NonNull E>> setOf(
        final @NonNull Class<@NonNull E> e) {

        return of((Class) Set.class, e);
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
    @SuppressWarnings({"unchecked", "rawtypes"})
    @SideEffectFree
    static <E extends @NonNull Object> @NonNull ParameterizedType<@NonNull Collection<@NonNull E>> collectionOf(
        final @NonNull Class<@NonNull E> e) {

        return of((Class) Collection.class, e);
    }

    /**
     * Gets this parameterized type as a raw {@link java.lang.Class}.
     *
     * @return this parameterized type as a raw {@link java.lang.Class}.
     * @since 0.0.0
     */
    @Pure
    @NonNull Class<T> getRawType();

    /**
     * Gets the type arguments of this.
     *
     * @return the type arguments of this
     * @since 0.0.0
     */
    @Pure
    @NonNull List<@NonNull ParameterizedType<? extends @Nullable Object>> getTypeArguments();
}
