package net.jailgens.mirror;

import org.checkerframework.checker.lock.qual.NewObject;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.SideEffectFree;

import java.lang.annotation.Annotation;

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

        // TODO(Sparky983): implement
        throw new RuntimeException("Not implemented yet");
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
            final @NonNull Class<? extends @NonNull Annotation> annotationType, final @NonNull String name) {

        // TODO(Sparky983): implement
        throw new RuntimeException("Not implemented yet");
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

        // TODO(Sparky983): implement
        throw new RuntimeException("Not implemented yet");
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
            final @NonNull ParameterizedType<? extends @NonNull Annotation> annotationType) {

        return of(annotationType, VALUE);
    }

    /**
     * Gets the name of this annotation element.
     *
     * @return the name of this annotation element.
     * @since 0.0.0
     */
    @Pure
    @NonNull String getName();

    /**
     * Returns the type name of the clas that declares this element.
     *
     * @return the type name of the class that declares this element.
     * @since 0.0.0
     */
    @Pure
    @NonNull String getAnnotationType();
}
