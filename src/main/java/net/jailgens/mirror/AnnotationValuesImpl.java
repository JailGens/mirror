package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.checkerframework.dataflow.qual.Pure;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;

/**
 * The default {@link AnnotationElement} implementation.
 * <p>
 * The annotation elements and values are internally represented by a hash map for maximum
 * efficiency. A hash set used to store all annotations.
 *
 * @author Sparky983
 */
final class AnnotationValuesImpl implements AnnotationValues {

    public static final AnnotationValues EMPTY = new AnnotationValuesImpl(Set.of(), Map.of());

    private static final int[] EMPTY_INTS = new int[0];
    private static final byte[] EMPTY_BYTES = new byte[0];
    private static final short[] EMPTY_SHORTS = new short[0];
    private static final long[] EMPTY_LONGS = new long[0];
    private static final float[] EMPTY_FLOATS = new float[0];
    private static final double[] EMPTY_DOUBLES = new double[0];
    private static final boolean[] EMPTY_BOOLEANS = new boolean[0];
    private static final char[] EMPTY_CHARS = new char[0];

    private final @NonNull Set<@NonNull String> annotations;
    private final @NonNull Map<@NonNull AnnotationElement, @NonNull Object> values;

    @Pure
    AnnotationValuesImpl(final @NonNull Set<@NonNull String> annotations,
                         final @NonNull Map<@NonNull AnnotationElement, @NonNull Object> values) {

        this.annotations = Collections.unmodifiableSet(new LinkedHashSet<>(annotations));
        this.values = Collections.unmodifiableMap(new LinkedHashMap<>(values));
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof AnnotationValuesImpl)) {
            return false;
        }

        AnnotationValuesImpl that = (AnnotationValuesImpl) o;
        
        return annotations.equals(that.annotations) &&
                values.equals(that.values);
    }

    @Override
    public int hashCode() {

        return Objects.hash(annotations, values);
    }

    @Override
    public @NonNull String toString() {

        final StringBuilder builder = new StringBuilder();

        final Map<String, Map<String, Object>> annotations = new LinkedHashMap<>();

        for (final String annotation : getAnnotationTypes()) {
            annotations.put(annotation, new LinkedHashMap<>());
        }

        for (final Map.Entry<AnnotationElement, Object> entry : values.entrySet()) {
            final AnnotationElement annotationElement = entry.getKey();
            final String annotationType = annotationElement.getAnnotationType();
            final String name = annotationElement.getName();

            final Object value = entry.getValue();

            annotations.get(annotationType).put(name, value);
        }

        for (Map.Entry<String, Map<String, Object>> entry : annotations.entrySet()) {
            final String annotationType = entry.getKey();
            final Map<String, Object> values = entry.getValue();

            builder.append('@')
                    .append(annotationType)
                    .append('(');

            int i = 1;
            for (Map.Entry<String, Object> valueEntry : values.entrySet()) {
                final String elementName = valueEntry.getKey();
                final Object elementValue = valueEntry.getValue();

                builder.append(elementName)
                        .append('=')
                        .append(elementValue);

                if (i != values.size()) {
                    builder.append(',');
                }

                i++;
            }

            builder.append(')');
        }

        return builder.toString();
    }

    @Override
    public boolean hasAnnotation(final @Nullable String annotationType) {

        return annotations.contains(annotationType);
    }

    @Override
    public boolean hasAnnotation(
            final @NonNull Class<? extends @NonNull Annotation> annotationType) {

        return hasAnnotation(annotationType.getTypeName());
    }

    @Override
    public boolean hasAnnotation(
            final @NonNull ParameterizedType<? extends @Nullable Annotation> annotationType) {

        return hasAnnotation(annotationType.getTypeName());
    }

    @Override
    public boolean hasElement(final @NonNull AnnotationElement annotationElement) {

        return values.containsKey(annotationElement);
    }

    @Override
    public @NonNull Set<@NonNull AnnotationElement> getElements() {

        return values.keySet();
    }

    @Override
    public @NonNull Set<@NonNull String> getAnnotationTypes() {

        return annotations;
    }

    @SuppressWarnings("unchecked")
    private <T> @Nullable T getObject(final @NonNull AnnotationElement annotationElement) {

        Objects.requireNonNull(annotationElement, "annotationElement cannot be null");
        return (T) values.get(annotationElement);
    }

    private <T> @NonNull T getObjectOrDefault(final @NonNull AnnotationElement annotationElement,
                                               final @NonNull T defaultValue) {

        T value = getObject(annotationElement);

        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    @Override
    public @NonNull OptionalInt getInt(final @NonNull AnnotationElement annotationElement) {

        final Integer value = getObject(annotationElement);

        if (value == null) {
            return OptionalInt.empty();
        } else {
            return OptionalInt.of(value);
        }
    }

    @Override
    public @NonNull Optional<@NonNull Byte> getByte(final @NonNull AnnotationElement annotationElement) {

        return Optional.ofNullable(getObject(annotationElement));
    }

    @Override
    public @NonNull Optional<@NonNull Short> getShort(final @NonNull AnnotationElement annotationElement) {

        return Optional.ofNullable(getObject(annotationElement));
    }

    @Override
    public @NonNull OptionalLong getLong(final @NonNull AnnotationElement annotationElement) {

        final Long value = getObject(annotationElement);

        if (value == null) {
            return OptionalLong.empty();
        } else {
            return OptionalLong.of(value);
        }
    }

    @Override
    public @NonNull Optional<@NonNull Float> getFloat(final @NonNull AnnotationElement annotationElement) {

        return Optional.ofNullable(getObject(annotationElement));
    }

    @Override
    public @NonNull OptionalDouble getDouble(final @NonNull AnnotationElement annotationElement) {

        final Double value = getObject(annotationElement);

        if (value == null) {
            return OptionalDouble.empty();
        } else {
            return OptionalDouble.of(value);
        }
    }

    @Override
    public @NonNull Optional<@NonNull Boolean> getBoolean(final @NonNull AnnotationElement annotationElement) {

        return Optional.ofNullable(getObject(annotationElement));
    }

    @Override
    public @NonNull Optional<@NonNull Character> getChar(final @NonNull AnnotationElement annotationElement) {

        return Optional.ofNullable(getObject(annotationElement));
    }

    @Override
    public @NonNull Optional<@NonNull String> getString(final @NonNull AnnotationElement annotationElement) {

        return Optional.ofNullable(getObject(annotationElement));
    }

    @Override
    public @NonNull <T extends @NonNull Enum<@NonNull T>> Optional<@NonNull T> getEnum(
            final @NonNull AnnotationElement annotationElement,
            final @NonNull Class<@NonNull T> enumType) {

        return Optional.ofNullable(getObject(annotationElement));
    }

    @Override
    public @NonNull Optional<@NonNull AnnotationValues> getAnnotation(
            final @NonNull AnnotationElement annotationElement) {

        return Optional.ofNullable(getObject(annotationElement));
    }

    @Override
    public @NonNull <T> Optional<@NonNull Class<? extends @NonNull T>> getClass(
            final @NonNull AnnotationElement annotationElement) {

        return Optional.ofNullable(getObject(annotationElement));
    }

    @Override
    public int @NonNull [] getInts(final @NonNull AnnotationElement annotationElement) {

        return getObjectOrDefault(annotationElement, EMPTY_INTS);
    }

    @Override
    public byte @NonNull [] getBytes(final @NonNull AnnotationElement annotationElement) {

        return getObjectOrDefault(annotationElement, EMPTY_BYTES);
    }

    @Override
    public short @NonNull [] getShorts(final @NonNull AnnotationElement annotationElement) {

        return getObjectOrDefault(annotationElement, EMPTY_SHORTS);
    }

    @Override
    public long @NonNull [] getLongs(final @NonNull AnnotationElement annotationElement) {

        return getObjectOrDefault(annotationElement, EMPTY_LONGS);
    }

    @Override
    public float @NonNull [] getFloats(final @NonNull AnnotationElement annotationElement) {

        return getObjectOrDefault(annotationElement, EMPTY_FLOATS);
    }

    @Override
    public double @NonNull [] getDoubles(final @NonNull AnnotationElement annotationElement) {

        return getObjectOrDefault(annotationElement, EMPTY_DOUBLES);
    }

    @Override
    public boolean @NonNull [] getBooleans(final @NonNull AnnotationElement annotationElement) {

        return getObjectOrDefault(annotationElement, EMPTY_BOOLEANS);
    }

    @Override
    public char @NonNull [] getChars(final @NonNull AnnotationElement annotationElement) {

        return getObjectOrDefault(annotationElement, EMPTY_CHARS);
    }

    @Override
    public @NonNull List<@NonNull String> getStrings(final @NonNull AnnotationElement annotationElement) {

        return getObjectOrDefault(annotationElement, List.of());
    }

    @Override
    public @NonNull <T extends @NonNull Enum<@NonNull T>> List<@NonNull T> getEnums(@NonNull AnnotationElement annotationElement,
                                                                                    @NonNull Class<@NonNull T> enumType) {

        return getObjectOrDefault(annotationElement, List.of());
    }

    @Override
    public @NonNull List<@NonNull AnnotationValues> getAnnotations(final @NonNull AnnotationElement annotationElement) {

        return getObjectOrDefault(annotationElement, List.of());
    }

    @Override
    public @NonNull List<@NonNull Class<? extends @NonNull Object>> getClasses(final @NonNull AnnotationElement annotationElement) {

        return getObjectOrDefault(annotationElement, List.of());
    }

    static final class BuilderImpl implements Builder {

        private final @NonNull Set<@NonNull String> annotations = new LinkedHashSet<>();
        private final @NonNull Map<@NonNull AnnotationElement, @NonNull Object> values =
                new LinkedHashMap<>();

        @Override
        public @NonNull @This Builder annotate(final @Nullable String annotationType) {

            Objects.requireNonNull(annotationType, "annotationType cannot be null");
            annotations.add(annotationType);
            return this;
        }

        @Override
        public @NonNull @This Builder annotate(
                final @NonNull Class<? extends @NonNull Annotation> annotationType) {

            Objects.requireNonNull(annotationType, "annotationType cannot be null");
            return annotate(annotationType.getTypeName());
        }

        @Override
        public @NonNull @This Builder annotate(
                final @NonNull ParameterizedType<@Nullable ?> annotationType) {

            Objects.requireNonNull(annotationType, "annotationType cannot be null");
            return annotate(annotationType.getTypeName());
        }

        private @NonNull @This Builder boxValue(final @NonNull AnnotationElement element,
                                                final @NonNull Object value) {

            Objects.requireNonNull(element, "element cannot be null");
            Objects.requireNonNull(value, "value cannot be null");

            values.put(element, value);
            return annotate(element.getAnnotationType());
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final int value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final byte value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final short value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final long value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element, float value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            double value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            boolean value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element, char value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final @NonNull String value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final @NonNull Enum<?> value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final @NonNull AnnotationValues value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final @NonNull Class<?> value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final int @NonNull ... value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final byte @NonNull ... value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final short @NonNull ... value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final long @NonNull ... value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final float @NonNull ... value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final double @NonNull ... value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final boolean @NonNull ... value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final char @NonNull ... value) {

            return boxValue(element, value);
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final @NonNull String @NonNull ... value) {

            return boxValue(element, List.of(value));
        }

        @SafeVarargs
        @Override
        public final @NonNull @This <T extends Enum<@NonNull T>> Builder value(
                final @NonNull AnnotationElement element,
                final @NonNull Enum<@NonNull T> @NonNull ... value) {

            if (value.length >= 1) {
                final Class<T> enumClass = value[0].getDeclaringClass();
                for (int i = 0; i < value.length; i++) {
                    final Enum<?> t = value[i];
                    if (!t.getDeclaringClass().equals(enumClass)) {
                        throw new ClassCastException(
                                "Enums at index [0] and [" + i + "] differ in type");
                    }
                }
            }
            return boxValue(element, List.of(value));
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final @NonNull AnnotationValues @NonNull ... value) {

            return boxValue(element, List.of(value));
        }

        @Override
        public @NonNull @This Builder value(final @NonNull AnnotationElement element,
                                            final @NonNull Class<?> @NonNull ... value) {

            return boxValue(element, List.of(value));
        }

        @Override
        public @NonNull AnnotationValues build() {

            return new AnnotationValuesImpl(annotations, values);
        }
    }
}
