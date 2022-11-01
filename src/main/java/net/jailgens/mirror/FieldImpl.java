package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.util.Objects;
import java.util.Set;

import static java.lang.reflect.Modifier.isStatic;

/**
 * The default {@link Field} implementation.
 *
 * @author Sparky983
 * @param <R> the type that declares the field.
 * @param <T> the type of the field.
 */
final class FieldImpl<R extends @NonNull Object, T extends @Nullable Object> implements Field<@NonNull R, @NonNull T> {

    private final @NonNull TypeDefinition<@NonNull R> declaringType;
    private final java.lang.reflect.@NonNull Field field;
    private final @NonNull Set<@NonNull Modifier> modifiers;
    private final @NonNull ParameterizedType<@NonNull T> type;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Pure
    FieldImpl(final @NonNull TypeDefinition<@NonNull R> declaringType,
              final java.lang.reflect.@NonNull Field field) {

        Objects.requireNonNull(field, "field cannot be null");

        this.declaringType = declaringType;
        this.field = field;
        this.modifiers = Modifier.modifiersAsSet(field.getModifiers());
        this.type = ParameterizedType.of((Class) field.getType());
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NonNull T get(final @Nullable R receiver) {

        try {
            field.setAccessible(true);
            return (T) field.get(receiver);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            field.setAccessible(false);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void set(final @Nullable R receiver, final @NonNull T value) {

        if (!isStatic(field.getModifiers())) {
            Objects.requireNonNull(receiver, "receiver cannot be null");
        }

        if (modifiers.contains(Modifier.FINAL)) {
            throw new IllegalStateException("Cannot mutate final field");
        }

        try {
            field.setAccessible(true);
            field.set(receiver, value);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            field.setAccessible(false);
        }
    }

    @Override
    public @NonNull String getName() {

        return field.getName();
    }

    @Override
    public @NonNull Set<@NonNull Modifier> getModifiers() {

        return modifiers;
    }

    @Override
    public @NonNull TypeDefinition<@NonNull R> getDeclaringType() {

        return declaringType;
    }

    @Override
    public @NonNull Class<@NonNull R> getRawDeclaringType() {

        return declaringType.getRawType();
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
