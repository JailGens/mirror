package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The default {@link Method} implementation.
 *
 * @author Sparky983
 * @param <R> the receiver type. for static methods, this is the declaring class.
 * @param <T> the method's return type.
 */
final class MethodImpl<T extends @NonNull Object, R extends @Nullable Object> implements Method<@NonNull T, @NonNull R> {

    private final @NonNull TypeDefinition<@NonNull T> declaringType;
    private final java.lang.reflect.@NonNull Method method;
    private final @NonNull AnnotationValues annotations;
    private final @NonNull List<@NonNull Parameter<?>> parameters;
    private final @NonNull ParameterizedType<@NonNull R> returnType;
    private final @NonNull Set<@NonNull Modifier> modifiers;
    private final @NonNull List<@NonNull Annotation> rawAnnotations;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Pure
    MethodImpl(final @NonNull TypeDefinition<@NonNull T> declaringType,
               final java.lang.reflect.@NonNull Method method) {

        Objects.requireNonNull(declaringType, "declaringType cannot be null");
        Objects.requireNonNull(method, "method cannot be null");

        this.declaringType = declaringType;
        this.method = method;
        this.annotations = Reflections.createAnnotationValues(method);
        this.parameters = Arrays.stream(method.getParameters())
                .map(ParameterImpl::new)
                .collect(Collectors.toUnmodifiableList());
        this.returnType = ParameterizedType.of((Class) method.getReturnType());
        this.modifiers = Modifier.modifiersAsSet(method.getModifiers());
        this.rawAnnotations = List.of(method.getAnnotations());
    }

    @Override
    public @NonNull AnnotationValues getAnnotations() {

        return annotations;
    }

    @Override
    public <A extends @NonNull Annotation> @Nullable A getRawAnnotation(
            final @NonNull Class<@NonNull A> annotationType) {

        Objects.requireNonNull(annotationType, "annotationType cannot be null");

        return method.getAnnotation(annotationType);
    }

    @Override
    public @NonNull List<@NonNull Annotation> getRawAnnotations() {

        return rawAnnotations;
    }

    @Override
    public @NonNull List<@NonNull Parameter<?>> getParameters() {

        return parameters;
    }

    @Override
    public @NonNull ParameterizedType<@NonNull R> getType() {

        return returnType;
    }

    @Override
    public @NonNull Class<@NonNull R> getRawType() {

        return returnType.getRawType();
    }

    @Override
    public @NonNull String getName() {

        return method.getName();
    }

    @Override
    public @NonNull Set<@NonNull Modifier> getModifiers() {

        return modifiers;
    }

    @Override
    public @NonNull TypeDefinition<@NonNull T> getDeclaringType() {

        return declaringType;
    }

    @Override
    public @NonNull Class<@NonNull T> getRawDeclaringType() {

        return declaringType.getRawType();
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NonNull R invoke(final @Nullable T receiver,
                             final @Nullable Object @NonNull ... arguments) {

        Objects.requireNonNull(arguments, "arguments cannot be null");

        if (arguments.length != parameters.size()) {
            throw new IllegalArgumentException("Argument length differs from parameter length (" + parameters.size() + ")");
        }

        try {
            method.setAccessible(true);
            return (R) method.invoke(receiver, arguments);
        } catch (final InvocationTargetException e) {
            throw new InvocationException(e.getTargetException());
        } catch (final IllegalArgumentException e) {
            throw new ClassCastException(e.getMessage());
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            method.setAccessible(false);
        }
    }

    @Override
    public boolean equals(final @Nullable Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof MethodImpl)) {
            return false;
        }

        MethodImpl<?, ?> other = (MethodImpl<?, ?>) o;

        return declaringType.equals(other.declaringType) &&
                method.equals(other.method) &&
                annotations.equals(other.annotations) &&
                parameters.equals(other.parameters) &&
                returnType.equals(other.returnType) &&
                modifiers.equals(other.modifiers);
    }

    @Override
    public int hashCode() {

        return Objects.hash(declaringType, method, annotations, parameters, returnType, modifiers);
    }
}
