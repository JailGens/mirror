package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.util.Objects;

/**
 * The default {@link AnnotationElement} implementation.
 *
 * @author Sparky983
 */
final class AnnotationElementImpl implements AnnotationElement {

    private final @NonNull String annotationType;
    private final @NonNull String name;

    @Pure
    AnnotationElementImpl(final @NonNull String annotationType, final @NonNull String name) {

        Objects.requireNonNull(annotationType, "annotationType cannot be null");
        Objects.requireNonNull(name, "name cannot be null");

        this.annotationType = annotationType;
        this.name = name;
    }

    @Override
    public @NonNull String getAnnotationType() {

        return annotationType;
    }

    @Override
    public @NonNull String getName() {

        return name;
    }

    @Override
    public boolean equals(final @Nullable Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof AnnotationElement)) {
            return false;
        }

        final AnnotationElement that = (AnnotationElement) o;

        return annotationType.equals(that.getAnnotationType()) && name.equals(that.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(annotationType, name);
    }
}
