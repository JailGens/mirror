package net.jailgens.mirror;

import org.checkerframework.checker.lock.qual.NewObject;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.SideEffectFree;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;

/**
 * Represents a group of annotations.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface AnnotationValues {

    /**
     * Returns an annotation values object representing no annotations.
     *
     * @return an annotation values object representing no annotations.
     * @since 0.0.0
     */
    @Pure
    static @NonNull AnnotationValues empty() {

        // TODO(Sparky983): implement
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Creates a new annotation values builder.
     *
     * @return the newly created builder.
     * @since 0.0.0
     */
    @SideEffectFree
    static @NewObject @NonNull Builder builder() {

        // TODO(Sparky983): implement
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Checks whether an annotation with the specified type-name is present.
     *
     * @param annotationType the type-name.
     * @return whether an annotation with the specified type-name is present.
     * @throws NullPointerException if {@code annotationType} is {@code null}.
     * @since 0.0.0
     */
    @Pure
    boolean hasAnnotation(@Nullable String annotationType);

    /**
     * Checks whether an annotation with the specified type is present.
     *
     * @param annotationType the type-name.
     * @return whether an annotation with the specified type is present.
     * @throws NullPointerException if {@code annotationType} is {@code null}.
     * @since 0.0.0
     */
    @Pure
    boolean hasAnnotation(@NonNull Class<? extends @NonNull Annotation> annotationType);

    /**
     * Checks whether an annotation with the specified type is present.
     *
     * @param annotationType the type-name.
     * @return whether an annotation with the specified type is present.
     * @throws NullPointerException if {@code annotationType} is {@code null}.
     * @since 0.0.0
     */
    @Pure
    boolean hasAnnotation(@NonNull ParameterizedType<? extends @Nullable Annotation> annotationType);

    /**
     * Checks whether the specified element is present.
     *
     * @param annotationElement the element.
     * @return whether the specified element is present.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @Pure
    boolean hasElement(@NonNull AnnotationElement annotationElement);

    /**
     * Gets all the present elements.
     *
     * @return a set of all the present elements.
     * @since 0.0.0
     */
    @SideEffectFree
    @NonNull Set<@NonNull AnnotationElement> getElements();

    /**
     * Gets all the present annotation types.
     *
     * @return a set of all the annotation types.
     * @since 0.0.0
     */
    @SideEffectFree
    @NonNull Set<@NonNull String> getAnnotationTypes();

    /**
     * Returns an optional containing the integer value of the specified element.
     *
     * @param annotationElement the element.
     * @return the integer value of the specified element.
     * @throws ClassCastException if the value is not of type {@code int}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    @NonNull OptionalInt getInt(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the byte value of the specified element.
     *
     * @param annotationElement the element.
     * @return the byte value of the specified element.
     * @throws ClassCastException if the value is not of type {@code byte}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    @NonNull Optional<@NonNull Byte> getByte(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the short-integer value of the specified element.
     *
     * @param annotationElement the element.
     * @return the short-integer value of the specified element.
     * @throws ClassCastException if the value is not of type {@code short}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    @NonNull Optional<@NonNull Short> getShort(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the long-integer value of the specified element.
     *
     * @param annotationElement the element.
     * @return the long-integer value of the specified element.
     * @throws ClassCastException if the value is not of type {@code long}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    @NonNull OptionalLong getLong(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the floating-point value of the specified element.
     *
     * @param annotationElement the element.
     * @return the integer-point value of the specified element.
     * @throws ClassCastException if the value is not of type {@code float}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    @NonNull Optional<@NonNull Float> getFloat(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the double value of the specified element.
     *
     * @param annotationElement the element.
     * @return the double value of the specified element.
     * @throws ClassCastException if the value is not of type {@code double}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    @NonNull OptionalDouble getDouble(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the boolean value of the specified element.
     *
     * @param annotationElement the element.
     * @return the boolean value of the specified element.
     * @throws ClassCastException if the value is not of type {@code boolean}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    @NonNull Optional<@NonNull Boolean> getBoolean(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the character value of the specified element.
     *
     * @param annotationElement the element.
     * @return the char value of the specified element.
     * @throws ClassCastException if the value is not of type {@code char}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    @NonNull Optional<@NonNull Character> getChar(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the string value of the specified element.
     *
     * @param annotationElement the element.
     * @return the string value of the specified element.
     * @throws ClassCastException if the value is not of type {@link String}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    @NonNull Optional<@NonNull String> getString(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the enum value of the specified element.
     *
     * @param annotationElement the element.
     * @param enumType the enum type.
     * @return the enum value of the specified element.
     * @throws ClassCastException if the value is not of type {@code enumType}.
     * @throws NullPointerException if {@code annotationElement} or {@code enumType} are {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    <T extends @NonNull Enum<@NonNull T>> @NonNull Optional<@NonNull T> getEnum(
            @NonNull AnnotationElement annotationElement, @NonNull Class<@NonNull T> enumType);

    /**
     * Returns an optional containing the annotation value of the specified element.
     *
     * @param annotationElement the element.
     * @return the annotation value of the specified element.
     * @throws ClassCastException if the value is not of type an annotation.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    @NonNull Optional<@NonNull AnnotationValues> getAnnotation(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the class value of the specified element.
     *
     * @param annotationElement the element.
     * @return the class value of the specified element.
     * @throws ClassCastException if the value is not of type {@link Class}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    <T extends @NonNull Object> @NonNull Optional<@NonNull Class<? extends @NonNull T>> getClass(
            @NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the integer values of the specified element.
     * <p>
     * If the element is not present, this returns an empty int array.
     *
     * @param annotationElement the element.
     * @return the integer values of the specified element.
     * @throws ClassCastException if the value is not of type {@code int[]}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    int @NonNull [] getInts(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the byte values of the specified element.
     * <p>
     * If the element is not present, this returns an empty byte array.
     *
     * @param annotationElement the element.
     * @return the byte values of the specified element.
     * @throws ClassCastException if the value is not of type {@code byte[]}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    byte @NonNull [] getBytes(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the short-integer values of the specified element.
     * <p>
     * If the element is not present, this returns an empty short array.
     *
     * @param annotationElement the element.
     * @return the short-integer values of the specified element.
     * @throws ClassCastException if the value is not of type {@code short[]}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    short @NonNull [] getShorts(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the long-integer values of the specified element.
     * <p>
     * If the element is not present, this returns an empty long array.
     *
     * @param annotationElement the element.
     * @return the long-integer values of the specified element.
     * @throws ClassCastException if the value is not of type {@code long[]}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    long @NonNull [] getLongs(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the floating-point values of the specified element.
     * <p>
     * If the element is not present, this returns an empty float array.
     *
     * @param annotationElement the element.
     * @return the floating-point values of the specified element.
     * @throws ClassCastException if the value is not of type {@code float[]}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    float @NonNull [] getFloats(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the double values of the specified element.
     * <p>
     * If the element is not present, this returns an empty double array.
     *
     * @param annotationElement the element.
     * @return the double values of the specified element.
     * @throws ClassCastException if the value is not of type {@code double[]}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    double @NonNull [] getDoubles(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the boolean values of the specified element.
     * <p>
     * If the element is not present, this returns an empty boolean array.
     *
     * @param annotationElement the element.
     * @return the boolean values of the specified element.
     * @throws ClassCastException if the value is not of type {@code boolean[]}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    boolean @NonNull [] getBooleans(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the character values of the specified element.
     * <p>
     * If the element is not present, this returns an empty char array.
     *
     * @param annotationElement the element.
     * @return the character values of the specified element.
     * @throws ClassCastException if the value is not of type {@code char[]}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    char @NonNull [] getChars(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the string values of the specified element.
     * <p>
     * If the element is not present, this returns an empty string list.
     *
     * @param annotationElement the element.
     * @return the string values of the specified element.
     * @throws ClassCastException if the value is not of type {@link String}{@code []}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    @NonNull List<@NonNull String> getStrings(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the enum values of the specified element.
     * <p>
     * If the element is not present, this returns an empty enum list.
     *
     * @param annotationElement the element.
     * @param enumType enum values type.
     * @return the integer value of the specified element.
     * @throws ClassCastException if the value is not of type {@code enumType[]}.
     * @throws NullPointerException if {@code annotationElement} or {@code enumType} are {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    <T extends @NonNull Enum<@NonNull T>> @NonNull List<@NonNull T> getEnums(
            @NonNull AnnotationElement annotationElement, @NonNull Class<@NonNull T> enumType);

    /**
     * Returns an optional containing the annotation values of the specified element.
     * <p>
     * If the element is not present, this returns an empty annotation-values list.
     *
     * @param annotationElement the element.
     * @return the annotation values of the specified element.
     * @throws ClassCastException if the value is not of type {@code Annotation[]}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    @NonNull List<@NonNull AnnotationValues> getAnnotations(@NonNull AnnotationElement annotationElement);

    /**
     * Returns an optional containing the class values of the specified element.
     * <p>
     * If the element is not present, this returns an empty class list.
     *
     * @param annotationElement the element.
     * @return the class values of the specified element.
     * @throws ClassCastException if the value is not of type {@link Class}{@code []}.
     * @throws NullPointerException if {@code annotationElement} is {@code null}.
     * @since 0.0.0
     */
    @SideEffectFree
    @NonNull List<@NonNull Class<? extends @NonNull Object>> getClasses(@NonNull AnnotationElement annotationElement);

    /**
     * A {@link AnnotationValues} builder.
     *
     * @since 0.0.0
     */
    interface Builder {

        /**
         * Annotates the values with the specified annotation type.
         *
         * @param annotationType the annotation type.
         * @return the builder instance (for chaining).
         * @since 0.0.0
         */
        @NonNull @This Builder annotate(@Nullable String annotationType);

        /**
         * Annotates the values with the specified annotation type.
         *
         * @param annotationType the annotation type.
         * @return the builder instance (for chaining).
         * @since 0.0.0
         */
        @NonNull @This Builder annotate(@NonNull Class<? extends @NonNull Annotation> annotationType);

        /**
         * Annotates the values with the specified annotation type.
         *
         * @param annotationType the annotation type.
         * @return the builder instance (for chaining).
         * @since 0.0.0
         */
        @NonNull @This Builder annotate(@NonNull ParameterizedType<@Nullable ?> annotationType);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} is {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, int value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} is {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, byte value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} is {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, short value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} is {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, long value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} is {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, float value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} is {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, double value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} is {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, boolean value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} is {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, char value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} or {@code value} are {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, @NonNull String value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} or {@code value} are {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, @NonNull Enum<? extends @NonNull Object> value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} or {@code value} are {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, @NonNull AnnotationValues value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} or {@code value} are {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(
                @NonNull AnnotationElement element, @NonNull Class<? extends @NonNull Object> value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} or {@code value} are {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, int @NonNull ... value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} or {@code value} are {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, byte @NonNull ... value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} or {@code value} are {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, short @NonNull ... value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} or {@code value} are {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, long @NonNull ... value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} or {@code value} are {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, float @NonNull ... value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} or {@code value} are {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, double @NonNull ... value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} or {@code value} are {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, boolean @NonNull ... value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} or {@code value} are {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, char @NonNull ... value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} is null or {@code value} contains or is {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, @NonNull String @NonNull ... value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws ClassCastException if {@code value} contains different types of enums.
         * @throws NullPointerException if {@code element} is null or {@code value} contains or is {@code null}.
         * @since 0.0.0
         */
        @SuppressWarnings("unchecked") // reason: we can have a precondition to check this
        <T extends Enum<@NonNull T>> @NonNull @This Builder value(
                @NonNull AnnotationElement element, @NonNull Enum<@NonNull T> @NonNull ... value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} is null or {@code value} contains or is {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, @NonNull AnnotationValues @NonNull ... value);

        /**
         * Sets the value of the specified element to the specified value.
         *
         * @param element the element.
         * @param value the value.
         * @return the builder instance (for chaining).
         * @throws NullPointerException if {@code element} is null or {@code value} contains or is {@code null}.
         * @since 0.0.0
         */
        @NonNull @This Builder value(@NonNull AnnotationElement element, @NonNull Class<?> @NonNull ... value);

        /**
         * Builds the annotation values.
         *
         * @return the built annotation values.
         * @since 0.0.0
         */
        @NonNull AnnotationValues build();
    }
}
