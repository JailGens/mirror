package net.jailgens.mirror;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("ZeroLengthArrayAllocation")
class AnnotationValuesTest {

    @Test
    void Given_TwoEqualAnnotationValues_When_CompareEquality_Then_ReturnsTrue() {

        final AnnotationValues values1 = AnnotationValues.builder()
                .value(AnnotationElement.value(Annotation.class), 5)
                .build();
        final AnnotationValues values2 = AnnotationValues.builder()
                .value(AnnotationElement.value(Annotation.class), 5)
                .build();

        final boolean equals = values1.equals(values2) && values2.equals(values1);

        assertTrue(equals);
    }

    @Test
    void Given_TwoNotEqualAnnotationValues_When_CompareEquality_Then_ReturnsFalse() {

        final AnnotationValues values1 = AnnotationValues.builder()
                .value(AnnotationElement.value(Annotation.class), 5)
                .build();
        final AnnotationValues values2 = AnnotationValues.builder()
                .value(AnnotationElement.value(Annotation.class), 10)
                .build();

        final boolean equals = values1.equals(values2) ||
                values2.equals(values1) ||
                values1.equals(new Object()) ||
                values2.equals(new Object());

        assertFalse(equals);
    }

    @Test
    void Given_TwoEqualAnnotationsValues_When_GetHashCode_Then_IsEqual() {

        final AnnotationValues values1 = AnnotationValues.builder()
                .value(AnnotationElement.value(Annotation.class), 5)
                .build();
        final AnnotationValues values2 = AnnotationValues.builder()
                .value(AnnotationElement.value(Annotation.class), 5)
                .build();

        final int values1HashCode = values1.hashCode();
        final int values2HashCode = values2.hashCode();

        assertEquals(values1HashCode, values2HashCode);
    }

    @Test
    void Given_AnnotationValues_When_ConvertToString_Then_ReturnCorrectStringRepresentation() {

        final AnnotationValues values = AnnotationValues.builder()
                .value(AnnotationElement.value(Retention.class), RetentionPolicy.SOURCE)
                .value(AnnotationElement.of(Target.class, "value"), ElementType.ANNOTATION_TYPE)
                .value(AnnotationElement.of(Target.class, "other_element"), "other value")
                .build();

        final String toString = values.toString();

        assertEquals("@java.lang.annotation.Retention(value=SOURCE)@java.lang.annotation.Target(value=ANNOTATION_TYPE,other_element=other value)", toString);
    }

    @Test
    void Given_AnnotationValuesWithElement_When_CheckForAnnotationPresent_Then_ReturnsTrue() {

        final AnnotationValues values = AnnotationValues.builder()
                .value(AnnotationElement.value(Target.class), 0)
                .value(AnnotationElement.value(ParameterizedType.of(Target.class, Object.class)), 0)
                .value(AnnotationElement.of(Retention.class, "name"), 0)
                .value(AnnotationElement.of(ParameterizedType.of(Retention.class, Object.class), "name2"), 0)
                .build();

        final boolean hasTarget = values.hasAnnotation(Target.class);
        final boolean hasParameterizedTarget = values.hasAnnotation(ParameterizedType.of(Target.class, ParameterizedType.of(Object.class)));
        final boolean hasRetention = values.hasAnnotation(Retention.class);
        final boolean hasParameterizedRetention = values.hasAnnotation(ParameterizedType.of(Retention.class, Object.class));

        assertTrue(hasTarget);
        assertTrue(hasParameterizedTarget);
        assertTrue(hasRetention);
        assertTrue(hasParameterizedRetention);
    }

    @SuppressWarnings("unchecked")
    @Test
    void Given_AnnotationValuesBuilder_When_PutEnumValuesWithDifferentTypes_Then_Throws() {

        final AnnotationValues.Builder builder = AnnotationValues.builder();

        assertThrows(ClassCastException.class,
                () -> builder.value(AnnotationElement.value(Annotation.class),
                        new Enum[]{RetentionPolicy.SOURCE, ElementType.ANNOTATION_TYPE}));
    }

    @Test
    void Given_AnnotatedAnnotationValues_When_CheckForHasAnnotation_Then_ReturnsTrue() {

        final AnnotationValues values = AnnotationValues.builder()
                .annotate(Annotation.class)
                .annotate(ParameterizedType.of(Annotation.class, Object.class))
                .annotate("net.jailgens.mirror.TestAnnotationTypeName")
                .build();

        final boolean hasTestAnnotation = values.hasAnnotation(Annotation.class);
        final boolean hasParameterizedTestAnnotation =
                values.hasAnnotation(ParameterizedType.of(Annotation.class, Object.class));
        final boolean hasTestAnnotationTypeName =
                values.hasAnnotation("net.jailgens.mirror.TestAnnotationTypeName");

        assertTrue(hasTestAnnotation);
        assertTrue(hasParameterizedTestAnnotation);
        assertTrue(hasTestAnnotationTypeName);
    }

    @Test
    void Given_UnannotatedAnnotationValues_When_CheckForNonPresentAnnotation_Then_ReturnsFalse() {

        final AnnotationValues values = AnnotationValues.builder().build();

        final boolean hasNonPresentAnnotation =
                values.hasAnnotation("net.jailgens.NotPresentAnnotation");

        assertFalse(hasNonPresentAnnotation);
    }

    @Test
    void Given_AnnotationValuesWithElements_When_CheckForHasElement_Then_ReturnsTrue() {

        final AnnotationValues values = AnnotationValues.builder()
                .value(AnnotationElement.value(Annotation.class), "value")
                .value(AnnotationElement.of(Annotation.class, "element"), "value")
                .build();

        final boolean hasTestAnnotationValue =
                values.hasElement(AnnotationElement.value(Annotation.class));
        final boolean hasTestAnnotationElement =
                values.hasElement(AnnotationElement.of(Annotation.class, "element"));

        assertTrue(hasTestAnnotationValue);
        assertTrue(hasTestAnnotationElement);
    }

    @Test
    void Given_AnnotationValuesWithoutElements_When_CheckForNonPresentElement_Then_ReturnsFalse() {

        final AnnotationValues values = AnnotationValues.builder().build();

        final boolean hasTestAnnotationValue =
                values.hasElement(AnnotationElement.value(Annotation.class));
        final boolean hasTestAnnotationElement =
                values.hasElement(AnnotationElement.of(Annotation.class, "element"));

        assertFalse(hasTestAnnotationValue);
        assertFalse(hasTestAnnotationElement);
    }

    @Test
    void Given_AnnotationValuesWithElements_When_GetElements_Then_ReturnsAllElements() {

        final AnnotationValues values = AnnotationValues.builder()
                .value(AnnotationElement.value(Annotation.class), "value")
                .value(AnnotationElement.of(Annotation.class, "element"), "value")
                .build();

        final Set<AnnotationElement> elements = values.getElements();

        assertEquals(
                Set.of(
                        AnnotationElement.value(Annotation.class),
                        AnnotationElement.of(Annotation.class, "element")),
                elements);
    }

    @Test
    void Given_AnnotatedAnnotationValues_When_GetAnnotationTypes_Then_ReturnsAllAnnotationTypes() {

        final AnnotationValues values = AnnotationValues.builder()
                .annotate(Annotation.class)
                .annotate(ParameterizedType.of(Annotation.class, Object.class))
                .annotate("net.jailgens.mirror.TestAnnotationTypeName")
                .build();

        final Set<String> annotationTypes = values.getAnnotationTypes();

        assertEquals(
                Set.of(
                        "java.lang.annotation.Annotation",
                        "java.lang.annotation.Annotation<java.lang.Object>",
                        "net.jailgens.mirror.TestAnnotationTypeName"),
                annotationTypes
        );
    }

    @Nested
    @DisplayName("Get* when present tests")
    class GetTest {

        @Test
        void Given_AnnotatedAnnotationValuesWithIntValue_When_GetInt_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), 1)
                    .build();

            final OptionalInt testAnnotationElementValue =
                    values.getInt(AnnotationElement.value(Annotation.class));

            assertEquals(OptionalInt.of(1), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithByteValue_When_GetByte_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), (byte) 1)
                    .build();

            final Optional<Byte> testAnnotationElementValue =
                    values.getByte(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of((byte) 1), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithShortValue_When_GetShort_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), (short) 1)
                    .build();

            final Optional<Short> testAnnotationElementValue =
                    values.getShort(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of((short) 1), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithLongValue_When_GetLong_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), (long) 1)
                    .build();

            final OptionalLong testAnnotationElementValue =
                    values.getLong(AnnotationElement.value(Annotation.class));

            assertEquals(OptionalLong.of(1), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithFloatValue_When_GetFloat_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), (float) 1)
                    .build();

            final Optional<Float> testAnnotationElementValue =
                    values.getFloat(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of((float) 1), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithDoubleValue_When_GetDouble_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), (double) 1)
                    .build();

            final OptionalDouble testAnnotationElementValue =
                    values.getDouble(AnnotationElement.value(Annotation.class));

            assertEquals(OptionalDouble.of(1), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithBooleanValue_When_GetBoolean_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), true)
                    .build();

            final Optional<Boolean> testAnnotationElementValue =
                    values.getBoolean(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of(true), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithCharValue_When_GetChar_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), 'a')
                    .build();

            final Optional<Character> testAnnotationElementValue =
                    values.getChar(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of('a'), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithStringValue_When_GetString_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "some value")
                    .build();

            final Optional<String> testAnnotationElementValue =
                    values.getString(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of("some value"), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithEnumValue_When_GetEnum_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), RetentionPolicy.SOURCE)
                    .build();

            final Optional<RetentionPolicy> testAnnotationElementValue =
                    values.getEnum(AnnotationElement.value(Annotation.class), RetentionPolicy.class);

            assertEquals(Optional.of(RetentionPolicy.SOURCE), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithAnnotationValue_When_GetAnnotation_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), AnnotationValues.empty())
                    .build();

            final Optional<AnnotationValues> testAnnotationElementValue =
                    values.getAnnotation(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of(AnnotationValues.empty()), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithClassValue_When_GetClass_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), Object.class)
                    .build();

            final Optional<Class<?>> testAnnotationElementValue =
                    values.getClass(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of(Object.class), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithIntValues_When_GetInts_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), new int[]{1})
                    .build();

            final int[] testAnnotationElementValue =
                    values.getInts(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new int[]{1}, testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithByteValues_When_GetBytes_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), new byte[]{1})
                    .build();

            final byte[] testAnnotationElementValue =
                    values.getBytes(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new byte[]{1}, testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithsHORTValues_When_GetsHORTs_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), new short[]{1})
                    .build();

            final short[] testAnnotationElementValue =
                    values.getShorts(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new short[]{1}, testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithLongValues_When_GetLongs_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), new long[]{1})
                    .build();

            final long[] testAnnotationElementValue =
                    values.getLongs(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new long[]{1}, testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithFloatValues_When_GetFloats_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), new float[]{1})
                    .build();

            final float[] testAnnotationElementValue =
                    values.getFloats(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new float[]{1}, testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithDoubleValues_When_GetDoubles_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), new double[]{1})
                    .build();

            final double[] testAnnotationElementValue =
                    values.getDoubles(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new double[]{1}, testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithBooleanValues_When_GetBooleans_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), new boolean[]{true})
                    .build();

            final boolean[] testAnnotationElementValue =
                    values.getBooleans(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new boolean[]{true}, testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithCharValues_When_GetChars_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), new char[]{'a'})
                    .build();

            final char[] testAnnotationElementValue =
                    values.getChars(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new char[]{'a'}, testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithStringValues_When_GetStrings_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), new String[]{"some value"})
                    .build();

            final List<String> testAnnotationElementValue =
                    values.getStrings(AnnotationElement.value(Annotation.class));

            assertEquals(List.of("some value"), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithEnumValues_When_GetEnums_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class),
                            new RetentionPolicy[]{RetentionPolicy.SOURCE})
                    .build();

            final List<RetentionPolicy> testAnnotationElementValue =
                    values.getEnums(AnnotationElement.value(Annotation.class),
                            RetentionPolicy.class);

            assertEquals(List.of(RetentionPolicy.SOURCE), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithAnnotationValues_When_GetAnnotations_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class),
                            new AnnotationValues[]{AnnotationValues.empty()})
                    .build();

            final List<AnnotationValues> testAnnotationElementValue =
                    values.getAnnotations(AnnotationElement.value(Annotation.class));

            assertEquals(List.of(AnnotationValues.empty()), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithClassValues_When_GetClasses_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), new Class[]{Object.class})
                    .build();

            final List<Class<?>> testAnnotationElementValue =
                    values.getClasses(AnnotationElement.value(Annotation.class));

            assertEquals(List.of(Object.class), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesWithAnyValue_When_GetBoxed_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "test")
                    .build();

            final Optional<Object> testAnnotationElementValue =
                    values.getBoxed(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of("test"), testAnnotationElementValue);
        }

        @Test
        void Given_AnnotatedAnnotationValuesBoxedValue_When_GetBoxed_Then_ReturnsValue() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), "test")
                    .build();

            final Optional<Object> testAnnotationElementValue =
                    values.getBoxed(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of("test"), testAnnotationElementValue);
        }
    }

    @Nested
    @DisplayName("Get* when not present tests")
    class GetWhenNotPresentTest {


        @Test
        void Given_EmptyAnnotationValues_When_GetInt_Then_ReturnsEmptyOptional() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final OptionalInt annotationElementValue =
                    values.getInt(AnnotationElement.value(Annotation.class));

            assertEquals(OptionalInt.empty(), annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetByte_Then_ReturnsEmptyOptional() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final Optional<Byte> annotationElementValue =
                    values.getByte(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.empty(), annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetShort_Then_ReturnsEmptyOptional() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final Optional<Short> annotationElementValue =
                    values.getShort(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.empty(), annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetLong_Then_ReturnsEmptyOptional() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final OptionalLong annotationElementValue =
                    values.getLong(AnnotationElement.value(Annotation.class));

            assertEquals(OptionalLong.empty(), annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetFloat_Then_ReturnsEmptyOptional() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final Optional<Float> annotationElementValue =
                    values.getFloat(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.empty(), annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetDouble_Then_ReturnsEmptyOptional() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final OptionalDouble annotationElementValue =
                    values.getDouble(AnnotationElement.value(Annotation.class));

            assertEquals(OptionalDouble.empty(), annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetBoolean_Then_ReturnsEmptyOptional() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final Optional<Boolean> annotationElementValue =
                    values.getBoolean(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.empty(), annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetChar_Then_ReturnsEmptyOptional() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final Optional<Character> annotationElementValue =
                    values.getChar(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.empty(), annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetString_Then_ReturnsEmptyOptional() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final Optional<String> annotationElementValue =
                    values.getString(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.empty(), annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetEnum_Then_ReturnsEmptyOptional() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final Optional<RetentionPolicy> annotationElementValue =
                    values.getEnum(AnnotationElement.value(Annotation.class), RetentionPolicy.class);

            assertEquals(Optional.empty(), annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetAnnotation_Then_ReturnsEmptyOptional() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final Optional<AnnotationValues> annotationElementValue =
                    values.getAnnotation(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.empty(), annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetClass_Then_ReturnsEmptyOptional() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final Optional<Class<?>> annotationElementValue =
                    values.getClass(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.empty(), annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetInts_Then_ReturnsEmptyIntArray() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final int[] annotationElementValue =
                    values.getInts(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new int[0], annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetBytes_Then_ReturnsEmptyByteArray() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final byte[] annotationElementValue =
                    values.getBytes(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new byte[0], annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetShorts_Then_ReturnsEmptyShortArray() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final short[] annotationElementValue =
                    values.getShorts(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new short[0], annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetLongs_Then_ReturnsEmptyLongArray() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final long[] annotationElementValue =
                    values.getLongs(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new long[0], annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetFloats_Then_ReturnsEmptyFloatArray() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final float[] annotationElementValue =
                    values.getFloats(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new float[0], annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetDoubles_Then_ReturnsEmptyDoubleArray() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final double[] annotationElementValue =
                    values.getDoubles(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new double[0], annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetBooleans_Then_ReturnsEmptyBooleanArray() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final boolean[] annotationElementValue =
                    values.getBooleans(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new boolean[0], annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetChars_Then_ReturnsEmptyCharArray() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final char[] annotationElementValue =
                    values.getChars(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new char[0], annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetStrings_Then_ReturnsEmptyStringList() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final List<String> annotationElementValue =
                    values.getStrings(AnnotationElement.value(Annotation.class));

            assertEquals(List.of(), annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetEnums_Then_ReturnsEmptyEnumList() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final List<RetentionPolicy> annotationElementValue =
                    values.getEnums(AnnotationElement.value(Annotation.class), RetentionPolicy.class);

            assertEquals(List.of(), annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetAnnotations_Then_ReturnsEmptyAnnotationValuesList() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final List<AnnotationValues> annotationElementValue =
                    values.getAnnotations(AnnotationElement.value(Annotation.class));

            assertEquals(List.of(), annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetClasses_Then_ReturnsEmptyClassList() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final List<Class<?>> annotationElementValue =
                    values.getClasses(AnnotationElement.value(Annotation.class));

            assertEquals(List.of(), annotationElementValue);
        }

        @Test
        void Given_EmptyAnnotationValues_When_GetBoxed_Then_ReturnsEmptyOptional() {

            final AnnotationValues values = AnnotationValues.builder().build();

            final Optional<?> annotationElementValue =
                    values.getBoxed(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.empty(), annotationElementValue);
        }
    }

    @Nested
    @DisplayName("Get* wrong type tests")
    class GetWrongTypeTest {

        @Test
        void Given_AnnotationValuesWithNotStringElement_When_GetString_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), 0)
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getString(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotIntElement_When_GetInt_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not int")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getInt(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotByteElement_When_GetByte_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not byte")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getByte(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotShortElement_When_GetShort_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not short")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getShort(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotLongElement_When_GetLong_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not long")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getLong(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotFloatElement_When_GetFloat_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not float")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getFloat(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotDoubleElement_When_GetDouble_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not double")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getDouble(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotBooleanElement_When_GetBoolean_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not boolean")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getBoolean(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotCharElement_When_GetChar_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not char")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getChar(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotEnumElement_When_GetEnum_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not enum")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getEnum(AnnotationElement.value(Annotation.class),
                            RetentionPolicy.class));
        }

        @Test
        void Given_AnnotationValuesWithNotAnnotationElement_When_GetAnnotation_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not annotation")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getAnnotation(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotClassElement_When_GetClass_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not class")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getClass(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotIntsElement_When_GetInts_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not ints")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getInts(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotBytesElement_When_GetBytes_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not bytes")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getBytes(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotShortsElement_When_GetShorts_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not shorts")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getShorts(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotLongsElement_When_GetLongs_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not longs")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getLongs(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotFloatsElement_When_GetFloats_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not floats")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getFloats(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotDoublesElement_When_GetDoubles_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not doubles")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getDoubles(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotBooleansElement_When_GetBooleans_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not booleans")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getBooleans(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotCharsElement_When_GetChars_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not chars")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getChars(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotStringsElement_When_GetStrings_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not strings")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getStrings(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotEnumsElement_When_GetEnums_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not enums")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getEnums(AnnotationElement.value(Annotation.class),
                            RetentionPolicy.class));
        }

        @Test
        void Given_AnnotationValuesWithNotAnnotationsElement_When_GetAnnotations_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not AnnotationElement")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getAnnotations(AnnotationElement.value(Annotation.class)));
        }

        @Test
        void Given_AnnotationValuesWithNotClassesElement_When_GetClasses_Then_Throws() {

            final AnnotationValues values = AnnotationValues.builder()
                    .value(AnnotationElement.value(Annotation.class), "not classes")
                    .build();

            assertThrows(ClassCastException.class,
                    () -> values.getClasses(AnnotationElement.value(Annotation.class)));
        }
    }

    @Nested
    @DisplayName("BoxedValue type tests")
    class BoxedValueTypeTest {

        @Test
        void Given_AnnotationValuesBuilder_When_BoxedValueUnsupportedType_Then_Throws() {

            final AnnotationValues.Builder builder = AnnotationValues.builder();

            assertThrows(ClassCastException.class,
                    () -> builder.boxedValue(AnnotationElement.value(Annotation.class), List.of()));
        }

        @Test
        void Given_AnnotationValuesWithBoxedInt_Then_GetInt_ReturnsInt() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), 1)
                    .build();

            final OptionalInt value = values.getInt(AnnotationElement.value(Annotation.class));

            assertEquals(OptionalInt.of(1), value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedByte_Then_GetByte_ReturnsByte() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), (byte) 1)
                    .build();

            final Optional<Byte> value = values.getByte(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of((byte) 1), value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedShort_Then_GetShort_ReturnsShort() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), (short) 1)
                    .build();

            final Optional<Short> value = values.getShort(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of((short) 1), value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedLong_Then_GetLong_ReturnsLong() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), 1L)
                    .build();

            final OptionalLong value = values.getLong(AnnotationElement.value(Annotation.class));

            assertEquals(OptionalLong.of(1L), value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedFloat_Then_GetFloat_ReturnsFloat() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), 1.0F)
                    .build();

            final Optional<Float> value = values.getFloat(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of(1.0f), value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedDouble_Then_GetDouble_ReturnsDouble() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), 1.0D)
                    .build();

            final OptionalDouble value = values.getDouble(AnnotationElement.value(Annotation.class));

            assertEquals(OptionalDouble.of(1.0d), value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedBoolean_Then_GetBoolean_ReturnsBoolean() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), true)
                    .build();

            final Optional<Boolean> value = values.getBoolean(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of(true), value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedChar_Then_GetChar_ReturnsChar() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), 'a')
                    .build();

            final Optional<Character> value = values.getChar(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of('a'), value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedString_Then_GetString_ReturnsString() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), "string")
                    .build();

            final Optional<String> value = values.getString(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of("string"), value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedEnum_Then_GetEnum_ReturnsEnum() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), RetentionPolicy.RUNTIME)
                    .build();

            final Optional<RetentionPolicy> value = values.getEnum(AnnotationElement.value(Annotation.class), RetentionPolicy.class);

            assertEquals(Optional.of(RetentionPolicy.RUNTIME), value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedAnnotation_Then_GetAnnotation_ReturnsAnnotation() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class),
                            AnnotationValues.builder()
                                    .value(AnnotationElement.value(Annotation.class), 10)
                                    .build()
                    ).build();

            final Optional<AnnotationValues> value = values.getAnnotation(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of(
                    AnnotationValues.builder()
                            .value(AnnotationElement.value(Annotation.class), 10)
                            .build()
            ), value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedClass_Then_GetClass_ReturnsClass() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), String.class)
                    .build();

            final Optional<Class<?>> value = values.getClass(AnnotationElement.value(Annotation.class));

            assertEquals(Optional.of(String.class), value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedInts_Then_GetInts_ReturnsInts() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), new int[]{1, 2, 3})
                    .build();

            final int[] value = values.getInts(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new int[]{1, 2, 3}, value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedBytes_Then_GetBytes_ReturnsBytes() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), new byte[]{1, 2, 3})
                    .build();

            final byte[] value = values.getBytes(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new byte[]{1, 2, 3}, value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedShorts_Then_GetShorts_ReturnsShorts() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), new short[]{1, 2, 3})
                    .build();

            final short[] value = values.getShorts(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new short[]{1, 2, 3}, value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedLongs_Then_GetLongs_ReturnsLongs() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), new long[]{1, 2, 3})
                    .build();

            final long[] value = values.getLongs(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new long[]{1, 2, 3}, value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedFloats_Then_GetFloats_ReturnsFloats() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), new float[]{1.0F, 2.0F, 3.0F})
                    .build();

            final float[] value = values.getFloats(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new float[]{1.0F, 2.0F, 3.0F}, value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedDoubles_Then_GetDoubles_ReturnsDoubles() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), new double[]{1.0D, 2.0D, 3.0D})
                    .build();

            final double[] value = values.getDoubles(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new double[]{1.0D, 2.0D, 3.0D}, value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedBooleans_Then_GetBooleans_ReturnsBooleans() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), new boolean[]{true, false, true})
                    .build();

            final boolean[] value = values.getBooleans(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new boolean[]{true, false, true}, value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedChars_Then_GetChars_ReturnsChars() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), new char[]{'a', 'b', 'c'})
                    .build();

            final char[] value = values.getChars(AnnotationElement.value(Annotation.class));

            assertArrayEquals(new char[]{'a', 'b', 'c'}, value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedStrings_Then_GetStrings_ReturnsStrings() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), new String[]{"a", "b", "c"})
                    .build();

            final List<String> value = values.getStrings(AnnotationElement.value(Annotation.class));

            assertEquals(List.of("a", "b", "c"), value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedEnums_Then_GetEnum_ReturnsEnum() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), new RetentionPolicy[]{RetentionPolicy.RUNTIME, RetentionPolicy.CLASS})
                    .build();

            final List<RetentionPolicy> value = values.getEnums(AnnotationElement.value(Annotation.class), RetentionPolicy.class);

            assertEquals(List.of(RetentionPolicy.RUNTIME, RetentionPolicy.CLASS), value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedAnnotations_Then_GetAnnotation_ReturnsAnnotation() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class),
                            new AnnotationValues[]{AnnotationValues.builder()
                                    .value(AnnotationElement.value(Annotation.class), 10)
                                    .build(),
                                    AnnotationValues.builder()
                                            .value(AnnotationElement.value(Annotation.class), 20)
                                            .build()
                            })
                    .build();

            final List<AnnotationValues> value = values.getAnnotations(AnnotationElement.value(Annotation.class));

            assertEquals(List.of(AnnotationValues.builder()
                            .value(AnnotationElement.value(Annotation.class), 10)
                            .build(),
                    AnnotationValues.builder()
                            .value(AnnotationElement.value(Annotation.class), 20)
                            .build()), value);
        }

        @Test
        void Given_AnnotationValuesWithBoxedClasses_Then_GetClass_ReturnsClass() {

            final AnnotationValues values = AnnotationValues.builder()
                    .boxedValue(AnnotationElement.value(Annotation.class), new Class[]{String.class, Integer.class})
                    .build();

            final List<Class<?>> value = values.getClasses(AnnotationElement.value(Annotation.class));

            assertEquals(List.of(String.class, Integer.class), value);
        }
    }
}
