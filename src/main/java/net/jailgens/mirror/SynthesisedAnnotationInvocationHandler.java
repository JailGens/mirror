package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The invocation handler used for synthesised annotations.
 *
 * @param <T> the type of the annotation.
 * @author Sparky983
 */
final class SynthesisedAnnotationInvocationHandler<T extends @NonNull Annotation> implements InvocationHandler<T> {

    private static final @NonNull String EQUALS_NAME = "equals";
    private static final @NonNull String HASH_CODE_NAME = "hashCode";
    private static final @NonNull String TO_STRING = "toString";
    private static final @NonNull String ANNOTATION_TYPE = "annotationType";
    private static final @NonNull List<@NonNull Class<? extends @NonNull Object>> EQUALS_PARAMETERS = List.of(Object.class);
    private static final @NonNull List<@NonNull Class<? extends @NonNull Object>> EMPTY_PARAMETERS = List.of();

    private final @NonNull TypeDefinition<@NonNull T> annotationTypeDefinition;
    private final @NonNull Class<@NonNull T> annotationType;
    private final @NonNull String annotationTypeName;
    private final @NonNull Map<@NonNull String, @NonNull Object> values;

    @SuppressWarnings({"unchecked", "OptionalGetWithoutIsPresent", "rawtypes"})
    @Pure
    SynthesisedAnnotationInvocationHandler(final @NonNull AnnotationValues annotations,
                                           final @NonNull TypeDefinition<@NonNull T> annotationTypeDefinition,
                                           final @NonNull Class<@NonNull T> annotationType) {

        Objects.requireNonNull(annotations, "annotations cannot be null");
        Objects.requireNonNull(annotationTypeDefinition, "annotationTypeDefinition cannot be null");
        Objects.requireNonNull(annotationType, "annotationType cannot be null");

        this.annotationTypeDefinition = annotationTypeDefinition;
        this.annotationType = annotationType;
        this.annotationTypeName = annotationType.getName();
        this.values = annotations.getElements()
                .stream()
                .filter((element) -> element.getAnnotationType().equals(annotationTypeName))
                .filter((element) -> annotations.getBoxed(element).isPresent())
                .collect(Collectors.toMap(
                        AnnotationElement::getName,
                        (element) -> {
                            final Object boxed = annotations.getBoxed(element).get();
                            if (boxed instanceof Collection) {
                                return ((Collection) boxed).toArray((size) -> Array.newInstance(getElementMethod(element.getName()).getRawType().getComponentType(), size));
                            }
                            return boxed;
                        }));
    }

    private @NonNull Method<@NonNull T, ? extends @Nullable Object> getElementMethod(final String name) {

        return annotationTypeDefinition.getMethods()
                .stream()
                .filter((meth) -> isMethod(meth, name, List.of()))
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends @Nullable Object> @NonNull R invoke(final @NonNull T receiver,
                                                          final @NonNull Method<T, R> method,
                                                          final @Nullable Object @NonNull ... args) throws Throwable {

        if (isMethod(method, EQUALS_NAME, EQUALS_PARAMETERS)) {
            assert method.getRawType().equals(boolean.class);

            if (!annotationType.isInstance(args[0])) {
                return (R) Boolean.FALSE;
            }

            final T t = (T) args[0];

            for (final Map.Entry<String, Object> value : values.entrySet()) {
                final Method<?, ?> elementMethod = getElementMethod(value.getKey());
                final Object otherValue = getElementMethod(value.getKey()).invoke(t);
                if (elementMethod.getRawType().isArray()) {
                    return (R) arrayEquals(elementMethod.getRawType(), value.getValue(), otherValue);
                }
                if (!Objects.equals(value.getValue(), otherValue)) {
                    return (R) Boolean.FALSE;
                }
            }
            return (R) Boolean.TRUE;
        }

        if (isMethod(method, HASH_CODE_NAME, EMPTY_PARAMETERS)) {
            assert method.getRawType().equals(int.class);

            return (R) (Integer) values.hashCode();
        }

        if (isMethod(method, TO_STRING, EMPTY_PARAMETERS)) {
            assert method.getRawType().equals(String.class);

            return (R) values.toString();
        }

        if (isMethod(method, ANNOTATION_TYPE, EMPTY_PARAMETERS)) {
            assert method.getRawType().equals(Class.class);

            return (R) annotationType;
        }

        final R r = (R) values.get(method.getName());

        if (r == null) {
            return null;
        }

        if (!r.getClass().isArray()) {
            return r;
        }

        return arrayClone(r);
    }

    @SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
    private <R extends @Nullable Object> @NonNull R arrayClone(final @NonNull R r) {

        final int length = Array.getLength(r);
        final R clone = (R) Array.newInstance(r.getClass().getComponentType(), length);
        System.arraycopy(r, 0, clone, 0, length);
        return clone;
    }

    private @NonNull Boolean arrayEquals(final @NonNull Class<?> type,
                                         final @NonNull Object arr1,
                                         final @NonNull Object arr2) throws Exception {
        final Class<?> arrayType;
        if (!type.getComponentType().isPrimitive()) {
            arrayType = Object[].class;
        } else {
            arrayType = type;
        }
        final java.lang.reflect.Method equals = Arrays.class.getDeclaredMethod("equals", arrayType, arrayType);
        return (Boolean) equals.invoke(null, arr1, arr2);
    }

    private boolean isMethod(final @NonNull Method<?, ?> method, final @NonNull String name, final @NonNull List<Class<?>> parameters) {

        return method.getName().equals(name) && method.getParameters()
                .stream()
                .map(Typed::getRawType)
                .collect(Collectors.toUnmodifiableList())
                .equals(parameters);
    }
}
