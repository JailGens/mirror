package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The default {@link TypeDefinition} implementation.
 *
 * @author Sparky983
 * @param <T> The type.
 */
final class TypeDefinitionImpl<T extends @NonNull Object> implements TypeDefinition<@NonNull T> {

    private final @NonNull Class<@NonNull T> rawType;
    private final @NonNull AnnotationValues annotations;
    private final @NonNull Set<@NonNull Modifier> modifiers;

    private final @NonNull Collection<@NonNull Field<@NonNull T, ? extends @Nullable Object>> fields;
    private final @NonNull Collection<@NonNull Constructor<@NonNull T>> constructors;
    private final @NonNull Collection<@NonNull Method<T, ? extends @Nullable Object>> methods;
    private final @NonNull Collection<@NonNull Member<@NonNull T>> members;
    private final @NonNull List<@NonNull Annotation> rawAnnotations;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Pure
    TypeDefinitionImpl(final @NonNull Class<@NonNull T> cls) {

        Objects.requireNonNull(cls, "cls cannot be null");

        this.rawType = cls;
        this.annotations = Reflections.createAnnotationValues(cls);
        this.modifiers = Modifier.modifiersAsSet(cls.getModifiers());

        this.fields = Arrays.stream(cls.getDeclaredFields())
                .<Field<T, ?>>map((field) -> new FieldImpl<>(TypeDefinitionImpl.this, field))
                .collect(Collectors.toUnmodifiableList());

        // TODO(Sparky983): find out why it doesn't work without this cast which even IntelliJ says
        //  is redundant
        this.constructors = (Collection<Constructor<T>>) Arrays.stream(cls.getDeclaredConstructors())
                .<@NonNull Constructor<@NonNull T>>map((constructor) -> new ConstructorImpl<>(this, (java.lang.reflect.Constructor) constructor))
                .collect(Collectors.toUnmodifiableList());

        this.methods = (Collection<Method<T, ?>>) Arrays.stream(cls.getDeclaredMethods())
                .<Method<T, ?>>map((method) -> new MethodImpl(this, method))
                .collect(Collectors.toUnmodifiableList());

        this.members = Stream.of(fields, constructors, methods)
                .flatMap(Collection::stream)
                .collect(Collectors.toUnmodifiableList());

        this.rawAnnotations = List.of(cls.getAnnotations());
    }

    @Override
    public @NonNull AnnotationValues getAnnotations() {

        return annotations;
    }

    @Override
    public <A extends @NonNull Annotation> @Nullable A getRawAnnotation(
            final @NonNull Class<@NonNull A> annotationType) {

        Objects.requireNonNull(annotationType, "annotationType cannot be null");

        return rawType.getAnnotation(annotationType);
    }

    @Override
    public @NonNull List<@NonNull Annotation> getRawAnnotations() {

        return rawAnnotations;
    }

    @Override
    public boolean isClass() {

        return !isInterface();
    }

    @Override
    public boolean isInterface() {

        return rawType.isInterface();
    }

    @Override
    public @NonNull String getName() {

        return rawType.getSimpleName();
    }

    @Override
    public @NonNull Set<@NonNull Modifier> getModifiers() {

        return modifiers;
    }

    @Override
    public @NonNull Class<@NonNull T> getRawType() {

        return rawType;
    }

    @Override
    public @NonNull Collection<@NonNull Field<@NonNull T, ?>> getFields() {

        return fields;
    }

    @Override
    public @NonNull Collection<@NonNull Constructor<@NonNull T>> getConstructors() {

        return constructors;
    }

    @Override
    public @NonNull Collection<@NonNull Method<@NonNull T, ?>> getMethods() {

        return methods;
    }

    @Override
    public @NonNull Collection<@NonNull Member<@NonNull T>> getMembers() {

        return members;
    }

    @Override
    public boolean equals(final @Nullable Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof TypeDefinitionImpl)) {
            return false;
        }

        TypeDefinitionImpl<?> other = (TypeDefinitionImpl<?>) o;

        return rawType.equals(other.rawType) &&
                annotations.equals(other.annotations) &&
                modifiers.equals(other.modifiers) &&
                fields.equals(other.fields) &&
                constructors.equals(other.constructors) &&
                methods.equals(other.methods) &&
                members.equals(other.members);
    }

    @Override
    public int hashCode() {

        return Objects.hash(rawType, annotations, modifiers, fields, constructors, methods, members);
    }
}
