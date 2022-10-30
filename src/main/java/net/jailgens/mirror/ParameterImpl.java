package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.util.Objects;
import java.util.Set;

/**
 * The default {@link Parameter} implementation.
 *
 * @author Sparky983
 * @param <T>
 */
final class ParameterImpl<T extends @Nullable Object> implements Parameter<T> {

    private final java.lang.reflect.@NonNull Parameter parameter;
    private final @NonNull AnnotationValues annotations;
    private final @NonNull Set<@NonNull Modifier> modifiers;
    private final @NonNull ParameterizedType<@NonNull T> type;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Pure
    ParameterImpl(final java.lang.reflect.@NonNull Parameter parameter) {

        Objects.requireNonNull(parameter, "parameter cannot be null");

        this.parameter = parameter;
        this.annotations = Reflections.createAnnotationValues(parameter);
        this.modifiers = Modifier.modifiersAsSet(parameter.getModifiers());
        this.type = ParameterizedType.of((Class) parameter.getType());
    }

    @Override
    public @NonNull AnnotationValues getAnnotations() {

        return annotations;
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
}
