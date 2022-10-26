package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.Pure;

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
}
