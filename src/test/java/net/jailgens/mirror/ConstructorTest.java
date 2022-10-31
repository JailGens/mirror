package net.jailgens.mirror;

import org.checkerframework.checker.index.qual.Positive;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("unused")
class ConstructorTest {

    Mirror mirror;

    <T extends @NonNull Object> Constructor<@NonNull T> reflectConstructor(
            final @NonNull Class<@NonNull T> declaringClass) {

        return mirror.reflect(declaringClass).getConstructors().iterator().next();
    }

    <T extends @NonNull Object> Constructor<@NonNull T> reflectConstructorByParameterLength(
            final @NonNull Class<@NonNull T> declaringClass,
            final @Positive int parameterLength) {

        return mirror.reflect(declaringClass)
                .getConstructors()
                .stream()
                .filter((constructor) -> constructor.getParameters().size() == parameterLength)
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    /*
    Some classes cannot be local classes: https://gist.github.com/Sparky983/c64f08163e47b2e588c322798e135afe

    This is a OJDK bug, that is caused by parameters having two different indexes, 1 is their
    actual, bytecode-index and the other is the source-index (formal parameter index). The
    annotations are looked up using the bytecode-index in an array that uses formal parameter
    indexes.
     */

    @BeforeEach
    void setUp() {

        mirror = Mirror.builder().build();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface TestAnnotation {

        String value();
    }

    @Test
    void Given_Constructor_When_GetAnnotations_Then_ReturnsAnnotations() {

        class TestClass {

            @TestAnnotation("value")
            @Deprecated()
            TestClass() {

            }
        }
        final Constructor<TestClass> constructor = reflectConstructor(TestClass.class);

        final AnnotationValues annotations = constructor.getAnnotations();

        assertEquals(AnnotationValues.builder()
                .value(AnnotationElement.value(TestAnnotation.class), "value")
                .value(AnnotationElement.of(Deprecated.class, "since"), "")
                .value(AnnotationElement.of(Deprecated.class, "forRemoval"), false)
                .build(), annotations);
    }

    static class StringValueClass {

        final String argument;

        StringValueClass(String argument) {

            this.argument = argument;
        }
    }

    @Test
    void Given_Constructor_When_Construct_Then_CallTargetConstructorWithArguments() {

        final Constructor<StringValueClass> constructor = reflectConstructor(StringValueClass.class);

        final StringValueClass stringValueClass = constructor.construct("some argument");

        assertEquals("some argument", stringValueClass.argument);
    }

    static class InvalidArgumentsTypesTestClass {

        InvalidArgumentsTypesTestClass(String argument) {

        }
    }

    @Test
    void Given_Constructor_When_ConstructWithInvalidArgumentsTypes_Then_Throws() {

        final Constructor<StringValueClass> constructor = reflectConstructor(StringValueClass.class);

        assertThrows(ClassCastException.class, () -> constructor.construct(new Object()));
    }

    static class ArgumentsLengthTestClass {

        ArgumentsLengthTestClass() {

        }

        ArgumentsLengthTestClass(String p1) {

        }

        ArgumentsLengthTestClass(String p1, String p2) {

        }
    }

    @Test
    void Given_Constructor_When_ConstructWithInvalidArgumentsLength_Then_Throws() {

        final Constructor<ArgumentsLengthTestClass> constructor1 =
                reflectConstructorByParameterLength(ArgumentsLengthTestClass.class, 0);
        final Constructor<ArgumentsLengthTestClass> constructor2 =
                reflectConstructorByParameterLength(ArgumentsLengthTestClass.class, 1);
        final Constructor<ArgumentsLengthTestClass> constructor3 =
                reflectConstructorByParameterLength(ArgumentsLengthTestClass.class, 2);

        assertThrows(IllegalArgumentException.class, () -> constructor1.construct("too many arguments"));
        assertThrows(IllegalArgumentException.class, constructor2::construct);
        assertThrows(IllegalArgumentException.class, () -> constructor3.construct("not enough arguments"));
    }

    static abstract class TestAbstractClass {

    }

    @Test
    void Given_AbstractClassConstructor_When_Construct_Then_Throws() {

        final Constructor<TestAbstractClass> constructor = reflectConstructor(TestAbstractClass.class);

        assertThrows(IllegalStateException.class, constructor::construct);
    }

    static final RuntimeException e = new RuntimeException();

    static class ThrowingClass {

        ThrowingClass() {

            throw e;
        }
    }

    @Test
    void Given_ThrowingCons_Construct_Then_Throws() {

        final Constructor<ThrowingClass> constructor = reflectConstructor(ThrowingClass.class);

        final InvocationException thrown =
                assertThrows(InvocationException.class, constructor::construct);
        assertEquals(e, thrown.getTargetException());
    }

    static class ParametersTestClass {

        ParametersTestClass(final String p1, @TestAnnotation("value") Integer p2) {

        }
    }

    @Test
    void Given_Constructor_When_GetParameters_Then_ReturnsParameters() {

        final Constructor<ParametersTestClass> constructor =
                reflectConstructor(ParametersTestClass.class);

        final List<Parameter<?>> parameters = constructor.getParameters();

        assertEquals(Set.of(Modifier.FINAL), parameters.get(0).getModifiers());
        assertEquals("p1", parameters.get(0).getName());
        assertEquals(AnnotationValues.empty(), parameters.get(0).getAnnotations());
        assertEquals(ParameterizedType.of(String.class), parameters.get(0).getType());
        assertEquals(String.class, parameters.get(0).getRawType());

        assertEquals(Set.of(), parameters.get(1).getModifiers());
        assertEquals("p2", parameters.get(1).getName());
        assertEquals(AnnotationValues.builder()
                .value(AnnotationElement.value(TestAnnotation.class), "value")
                .build(), parameters.get(1).getAnnotations());
        assertEquals(ParameterizedType.of(Integer.class), parameters.get(1).getType());
        assertEquals(Integer.class, parameters.get(1).getRawType());
    }

    @Test
    void Given_Constructor_When_GetType_Then_ReturnType() {

        class TestClass {

            TestClass() {

            }
        }
        final Constructor<TestClass> constructor = reflectConstructor(TestClass.class);

        final ParameterizedType<TestClass> type = constructor.getType();

        assertEquals(ParameterizedType.of(TestClass.class), type);
    }

    @Test
    void Given_Constructor_When_GetRawType_Then_ReturnRawType() {

        class TestClass {

            TestClass() {

            }
        }
        final Constructor<TestClass> constructor = reflectConstructor(TestClass.class);

        final Class<TestClass> rawType = constructor.getRawType();

        assertEquals(TestClass.class, rawType);
    }

    @Test
    void Given_Constructor_When_GetName_Then_ReturnName() {

        class TestClass {

            TestClass() {

            }
        }
        final Constructor<TestClass> constructor = reflectConstructor(TestClass.class);

        final String name = constructor.getName();

        assertEquals("<init>", name);
    }

    @Test
    void Given_Constructor_When_GetModifiers_Then_ReturnsModifiers() {

        class TestClass {

            protected TestClass() {

            }
        }
        final Constructor<TestClass> constructor = reflectConstructor(TestClass.class);

        final Set<Modifier> modifiers = constructor.getModifiers();

        assertEquals(Set.of(Modifier.PROTECTED), modifiers);
    }

    @Test
    void Given_Constructor_When_GetDeclaringType_Then_ReturnsDeclaringType() {

        class TestClass {

            TestClass() {

            }
        }
        final TypeDefinition<TestClass> typeDefinition = mirror.reflect(TestClass.class);
        final Constructor<TestClass> constructor = typeDefinition.getConstructors()
                .iterator()
                .next();

        final TypeDefinition<TestClass> declaringType = constructor.getDeclaringType();

        assertEquals(typeDefinition, declaringType);
    }

    @Test
    void Given_Constructor_When_GetRawDeclaringType() {

        class TestClass {

            TestClass() {

            }
        }
        final Constructor<TestClass> constructor = reflectConstructor(TestClass.class);

        final Class<TestClass> rawDeclaringType = constructor.getRawDeclaringType();

        assertEquals(TestClass.class, rawDeclaringType);
    }
}
