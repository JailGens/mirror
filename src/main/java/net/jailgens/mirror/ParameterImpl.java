package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * The default {@link Parameter} implementation.
 *
 * @author Sparky983
 * @param <T>
 */
final class ParameterImpl<T extends @Nullable Object> implements Parameter<@NonNull T> {

    private final java.lang.reflect.@NonNull Parameter parameter;
    private final @NonNull AnnotationValues annotations;
    private final @NonNull Set<@NonNull Modifier> modifiers;
    private final @NonNull ParameterizedType<@NonNull T> type;
    private final @NonNull List<@NonNull Annotation> rawAnnotations;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Pure
    ParameterImpl(final java.lang.reflect.@NonNull Parameter parameter) {

        Objects.requireNonNull(parameter, "parameter cannot be null");

        this.parameter = parameter;
        this.annotations = Reflections.createAnnotationValues(parameter);
        this.modifiers = Modifier.modifiersAsSet(parameter.getModifiers());
        this.type = ParameterizedType.of((Class) parameter.getType());
        this.rawAnnotations = List.of(parameter.getAnnotations());
    }

    @Override
    public @NonNull AnnotationValues getAnnotations() {

        return annotations;
    }

    @Override
    public <A extends @NonNull Annotation> @Nullable A getRawAnnotation(
            final @NonNull Class<@NonNull A> annotationType) {

        Objects.requireNonNull(annotationType, "annotationType cannot be null");

        return parameter.getAnnotation(annotationType);
    }

    @Override
    public @NonNull List<@NonNull Annotation> getRawAnnotations() {

        return rawAnnotations;
    }

    @Override
    public @NonNull String getName() {

        return parameter.getName();
    }

    @Override
    public @NonNull Set<@NonNull Modifier> getModifiers() {

        return modifiers;
    }

    @Override
    public @NonNull ParameterizedType<@NonNull T> getType() {

        return type;
    }

    @Override
    public @NonNull Class<@NonNull T> getRawType() {

        return type.getRawType();
    }

    @Override
    public boolean equals(final @Nullable Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof ParameterImpl)) {
            return false;
        }

        ParameterImpl<?> other = (ParameterImpl<?>) o;

        return parameter.equals(other.parameter) &&
                annotations.equals(other.annotations) &&
                modifiers.equals(other.modifiers) &&
                type.equals(other.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(parameter, annotations, modifiers, type);
    }
}
