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
                    final Class<?> returnType = elementMethod.getReturnType().isEnum() ?
                            Enum.class :
                            elementMethod.getReturnType();
                    final java.lang.reflect.Method valueMethod =
                            AnnotationValues.Builder.class.getMethod("value", AnnotationElement.class, returnType);

                    valueMethod.invoke(builder,
                            AnnotationElement.of(annotationType, elementMethod.getName()),
                            elementMethod.invoke(annotation));
                }
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        return builder.build();
    }
}
