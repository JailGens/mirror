package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

class ParameterizedTypeImpl<T extends @Nullable Object> implements ParameterizedType<@NonNull T> {

    private final @NonNull AnnotationValues annotations;
    private final @NonNull Class<@NonNull T> rawType;
    private final @NonNull List<@NonNull ParameterizedType<? extends @NonNull Object>>
            typeArguments;

    @SafeVarargs
    @Pure
    ParameterizedTypeImpl(
            final @NonNull AnnotationValues annotations,
            final @NonNull Class<@NonNull T> rawType,
            final @NonNull ParameterizedType<? extends @NonNull Object> @NonNull ... typeArguments) {

        Objects.requireNonNull(annotations, "annotations cannot be null");
        Objects.requireNonNull(rawType, "rawType cannot be null");
        Objects.requireNonNull(typeArguments, "typeArguments cannot be null");
        // we already get a null check from List.of(), but we get a clearer message by doing one
        // here as well. List.of() also checks elements for nullness, but it's going to be too
        // messy to do one here.

        this.annotations = annotations;
        this.rawType = rawType;
        this.typeArguments = List.of(typeArguments);
    }

    @Pure
    ParameterizedTypeImpl(final @NonNull AnnotationValues annotations,
                          final @NonNull Class<@NonNull T> rawType) {

        Objects.requireNonNull(annotations, "annotations cannot be null");
        Objects.requireNonNull(rawType, "rawType cannot be null");

        this.annotations = annotations;
        this.rawType = rawType;
        this.typeArguments = List.of();
    }

    @Override
    public @NonNull AnnotationValues getAnnotations() {

        return annotations;
    }

    @Override
    public @NonNull Class<@NonNull T> getRawType() {

        return rawType;
    }

    @Override
    public @NonNull List<@NonNull ParameterizedType<?>> getTypeArguments() {

        return typeArguments;
    }

    @Override
    public boolean equals(final @Nullable Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ParameterizedType)) {
            return false;
        }

        final ParameterizedType<?> that = (ParameterizedType<?>) obj;

        return annotations.equals(that.getAnnotations()) &&
                rawType.equals(that.getRawType()) &&
                typeArguments.equals(that.getTypeArguments());
    }

    @Override
    public int hashCode() {

        return Objects.hash(annotations, rawType, typeArguments);
    }

    @Override
    public String getTypeName() {

        final StringBuilder typeName = new StringBuilder(getAnnotations().toString())
                .append(getRawType().getTypeName());

        if (!getTypeArguments().isEmpty()) {
            final StringJoiner typeArguments = new StringJoiner(",", "<", ">");

            for (final ParameterizedType<?> typeArgument : getTypeArguments()) {
                typeArguments.add(typeArgument.getTypeName());
            }

            typeName.append(typeArguments);
        }

        return typeName.toString();
    }
}
