package net.jailgens.mirror;

import org.checkerframework.checker.lock.qual.NewObject;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Objects;

/**
 * An internal reflection utility class.
 *
 * @author Sparky983
 */
final class Reflections {

    private Reflections() {

    }

    @SideEffectFree
    static @NewObject @NonNull AnnotationValues createAnnotationValues(
            final @NonNull AnnotatedElement annotatedElement) {

        Objects.requireNonNull(annotatedElement, "annotatedElement cannot be null");

        final AnnotationValues.Builder builder = AnnotationValues.builder();

        try {
            for (final Annotation annotation : annotatedElement.getDeclaredAnnotations()) {
                final Class<? extends Annotation> annotationType = annotation.annotationType();

                builder.annotate(annotationType);

                for (final java.lang.reflect.Method elementMethod : annotationType.getDeclaredMethods()) {
                    final Class<?> returnType = elementMethod.getReturnType();
                    final Class<?> elementType = returnType.isArray() ?
                            returnType.getComponentType().isEnum() ?
                                    Enum[].class :
                                    returnType
                            : returnType.isEnum() ?
                                    Enum.class :
                                    returnType;
                            elementMethod.getReturnType();
                    final java.lang.reflect.Method valueMethod =
                            AnnotationValues.Builder.class.getMethod("value", AnnotationElement.class, elementType);

                    valueMethod.invoke(builder,
                            AnnotationElement.of(annotationType, elementMethod.getName()),
                            elementMethod.invoke(annotation));
                }
            }
        } catch (final ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        return builder.build();
    }
}
