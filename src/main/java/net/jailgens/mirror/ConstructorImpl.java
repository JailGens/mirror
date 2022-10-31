package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The default {@link Constructor} implementation.
 *
 * @author Sparky983
 * @param <T> the type this constructor constructs.
 */
final class ConstructorImpl<T extends @NonNull Object> implements Constructor<T> {

    private final @NonNull TypeDefinition<@NonNull T> declaringType;
    private final java.lang.reflect.@NonNull Constructor<@NonNull T> constructor;
    private final @NonNull AnnotationValues annotations;
    private final @NonNull List<@NonNull Parameter<?>> parameters;
    private final @NonNull ParameterizedType<@NonNull T> type;
    private final @NonNull Set<@NonNull Modifier> modifiers;

    @Pure
    public ConstructorImpl(final @NonNull TypeDefinition<@NonNull T> declaringType,
                           final java.lang.reflect.@NonNull Constructor<@NonNull T> constructor) {

        Objects.requireNonNull(declaringType, "declaringType cannot be null");
        Objects.requireNonNull(constructor, "constructor cannot be null");

        this.declaringType = declaringType;
        this.constructor = constructor;
        this.annotations = Reflections.createAnnotationValues(constructor);
        this.parameters = Arrays.stream(constructor.getParameters())
                .<Parameter<?>>map(ParameterImpl::new)
                .collect(Collectors.toUnmodifiableList());
        this.type = ParameterizedType.of(constructor.getDeclaringClass());
        this.modifiers = Modifier.modifiersAsSet(constructor.getModifiers());
    }

    @Override
    public @NonNull AnnotationValues getAnnotations() {

        return annotations;
    }

    @Override
    public @NonNull T construct(@Nullable Object @NonNull ... arguments) {

        Objects.requireNonNull(arguments, "arguments cannot be null");

        if (arguments.length != parameters.size()) {
            throw new IllegalArgumentException("Argument length differs from parameter length (" + parameters.size() + ")");
        }

        try {
            constructor.setAccessible(true);
            return constructor.newInstance(arguments);
        } catch (final InvocationTargetException e) {
            throw new InvocationException(e.getTargetException());
        } catch (final IllegalArgumentException e) {
            throw new ClassCastException(e.getMessage());
        } catch (final InstantiationException e) {
            throw new IllegalStateException("Constructor must be instantiatable");
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            constructor.setAccessible(false);
        }
    }

    @Override
    public @NonNull List<@NonNull Parameter<?>> getParameters() {

        return parameters;
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
    public @NonNull String getName() {

        return "<init>";
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
}
