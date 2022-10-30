package net.jailgens.mirror;

import org.checkerframework.checker.lock.qual.NewObject;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.SideEffectFree;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * Represents an annotation element (method).
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface AnnotationElement {

    /**
     * The name of the value element.
     *
     * @since 0.0.0
     */
    @NonNull String VALUE = "value";

    /**
     * Creates a new annotation element with the specified annotation-type's type-name and name.
     *
     * @param annotationType the annotation-type's type-name.
     * @param name the name.
     * @return the created annotation element.
     * @throws NullPointerException if {@code annotationType} or {@code name} are {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    static @NewObject @NonNull AnnotationElement of(final @NonNull String annotationType, final @NonNull String name) {

        return new AnnotationElementImpl(annotationType, name);
    }

    /*
    The reason we have 2 different methods with the exact same body
    (of(Class<? extends Annotation>, String) and of(ParameterizedType<? extends Annotation>, String))
    is because if we use the java.lang.reflect.Type interface it would remove all the type safety,
    by defaulting that. We encourage that callers use of(String, String) and call
    java.lang.reflect.Type.getTypeName() explicitly if they would like to remove type safety.
     */

    /**
     * Creates a new annotation element with the specified annotation type and name.
     *
     * @param annotationType the annotation type.
     * @param name the name.
     * @return the created annotation element.
     * @throws NullPointerException if {@code annotationType} or {@code name} are {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    static @NewObject @NonNull AnnotationElement of(
            final @NonNull Class<? extends @NonNull Annotation> annotationType, final @NonNull String name) {

        Objects.requireNonNull(annotationType, "annotationType cannot be null");
        return of(annotationType.getTypeName(), name);
    }

    /**
     * Creates a new annotation element with the specified annotation type and name.
     *
     * @param annotationType the annotation type.
     * @param name the name.
     * @return the created annotation element.
     * @throws NullPointerException if {@code annotationType} or {@code name} are {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    static @NewObject @NonNull AnnotationElement of(
            final @NonNull ParameterizedType<? extends @NonNull Annotation> annotationType, final @NonNull String name) {

        Objects.requireNonNull(annotationType, "annotationType");
        return of(annotationType.getTypeName(), name);
    }

    /**
     * Creates a new annotation element named {@link #VALUE} with the specified annotation-type's type-name.
     *
     * @param annotationType the annotation-type's type-name.
     * @return the created annotation element.
     * @throws NullPointerException if {@code annotationType} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    static @NewObject @NonNull AnnotationElement value(final @NonNull String annotationType) {

        return of(annotationType, VALUE);
    }

    /**
     * Creates a new annotation element with the specified annotation type and name.
     *
     * @param annotationType the annotation type.
     * @return the created annotation element.
     * @throws NullPointerException if {@code annotationType} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    static @NewObject @NonNull AnnotationElement value(
            final @NonNull Class<? extends @NonNull Annotation> annotationType) {

        return value(annotationType.getTypeName());
    }

    /**
     * Creates a new annotation element with the specified annotation type and name.
     *
     * @param annotationType the annotation type.
     * @return the created annotation element.
     * @throws NullPointerException if {@code annotationType} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    static @NewObject @NonNull AnnotationElement value(
            final @NonNull ParameterizedType<? extends @NonNull Annotation> annotationType) {

        return value(annotationType.getTypeName());
    }

    /**
     * Returns the type name of the clas that declares this element.
     *
     * @return the type name of the class that declares this element.
     * @since 0.0.0
     */
    @Pure
    @NonNull String getAnnotationType();

    /**
     * Gets the name of this annotation element.
     *
     * @return the name of this annotation element.
     * @since 0.0.0
     */
    @Pure
    @NonNull String getName();
}
