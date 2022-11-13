package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Represents anything that can be annotated.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface Annotated {

    /**
     * Gets the annotations this is annotated with.
     *
     * @return the annotations this is annotated with.
     * @since 0.0.0
     */
    @Pure
    @NonNull AnnotationValues getAnnotations();

    /**
     * Crates a proxy of the specified annotation for this.
     * <p>
     * This result is the same as {@code getAnnotations().synthesise(annotation)}.
     *
     * @param annotationType the annotation type to create a proxy for.
     * @param <T> the annotation type.
     * @return a proxy of the specified annotation for this or {@code null} if the annotation is
     * not present.
     * @throws NullPointerException if {@code annotationType} is {@code null}.
     * @since 0.4.0
     */
    default <T extends @NonNull Annotation> @Nullable T getRawAnnotation(
            @NonNull Class<@NonNull T> annotationType) {

        return null;
    }

    /**
     * Gets the raw annotations this is annotated with.
     *
     * @return the raw annotations this is annotated with.
     * @since 0.4.0
     */
    default @NonNull List<@NonNull Annotation> getRawAnnotations() {

        return List.of();
    }
}
