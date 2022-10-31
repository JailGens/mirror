package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"unused", "InstantiationOfUtilityClass"})
class MethodTest {

    Mirror mirror;

    @Retention(RetentionPolicy.RUNTIME)
    @interface TestAnnotation {

        String value();
    }

    @SuppressWarnings("unchecked")
    <R extends @NonNull Object, T extends @Nullable Object>
    Method<@NonNull R, @NonNull T> reflectMethod(final @NonNull Class<R> declaringClass,
                                                 final @NonNull String name) {

        return (Method<R, T>) mirror.reflect(declaringClass)
                .getMethods()
                .stream()
                .filter((method) -> method.getName().equals(name))
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    @BeforeEach
    void setUp() {

        mirror = Mirror.builder().build();
    }

    @Test
    void Given_Method_When_GetAnnotations_Then_ReturnsAnnotations() {

        class TestClass {

            @TestAnnotation("value")
            void method() {

            }
        }
        final Method<TestClass, ?> method = reflectMethod(TestClass.class, "method");

        final AnnotationValues annotations = method.getAnnotations();

        assertEquals(AnnotationValues.builder()
                        .value(AnnotationElement.value(TestAnnotation.class), "value")
                        .build(),
                annotations);
    }

    @Test
    void Given_Method_When_GetParameters_Then_ReturnsParameters() {

        class TestClass {

            void method(final @TestAnnotation("param1") String param1,
                        @TestAnnotation("param2") Integer param2) {

            }
        }
        final Method<TestClass, ?> method = reflectMethod(TestClass.class, "method");

        final List<Parameter<?>> parameters = method.getParameters();

        assertEquals(AnnotationValues.builder()
                        .value(AnnotationElement.value(TestAnnotation.class), "param1")
                        .build(),
                parameters.get(0).getAnnotations());
        assertEquals("param1", parameters.get(0).getName());
        assertEquals(Set.of(Modifier.FINAL), parameters.get(0).getModifiers());
        assertEquals(ParameterizedType.of(String.class), parameters.get(0).getType());
        assertEquals(String.class, parameters.get(0).getRawType());
        assertEquals(AnnotationValues.builder()
                        .value(AnnotationElement.value(TestAnnotation.class), "param2")
                        .build(),
                parameters.get(1).getAnnotations());
        assertEquals("param2", parameters.get(1).getName());
        assertEquals(Set.of(), parameters.get(1).getModifiers());
        assertEquals(ParameterizedType.of(Integer.class), parameters.get(1).getType());
        assertEquals(Integer.class, parameters.get(1).getRawType());
    }

    @Test
    void Given_Method_When_GetType_Then_ReturnsType() {

        class TestClass {

            String method1() {

                return "";
            }

            void method2() {

            }
        }
        final Method<TestClass, String> method1 = reflectMethod(TestClass.class, "method1");
        final Method<TestClass, Void> method2 = reflectMethod(TestClass.class, "method2");

        final ParameterizedType<String> method1Type = method1.getType();
        final ParameterizedType<Void> method2Type = method2.getType();

        assertEquals(ParameterizedType.of(String.class), method1Type);
        assertEquals(ParameterizedType.of(void.class), method2Type);
    }

    @Test
    void Given_Method_When_GetRawType_Then_ReturnsRawType() {

        class TestClass {

            String method1() {

                return "";
            }

            void method2() {

            }
        }
        final Method<TestClass, String> method1 = reflectMethod(TestClass.class, "method1");
        final Method<TestClass, Void> method2 = reflectMethod(TestClass.class, "method2");

        final Class<String> method1Type = method1.getRawType();
        final Class<Void> method2Type = method2.getRawType();

        assertEquals(String.class, method1Type);
        assertEquals(void.class, method2Type);
    }

    abstract static class ModifiersTestClass {

        public abstract void method();

        protected static synchronized strictfp void staticMethod() {

        }

        private final void finalMethod() {

        }
    }

    @Test
    void Given_Method_When_GetModifiers_Then_ReturnsModifiers() {

        final Method<?, ?> method = reflectMethod(ModifiersTestClass.class, "method");
        final Method<?, ?> staticMethod = reflectMethod(ModifiersTestClass.class, "staticMethod");
        final Method<?, ?> finalMethod = reflectMethod(ModifiersTestClass.class, "finalMethod");

        final Set<Modifier> methodModifiers = method.getModifiers();
        final Set<Modifier> staticModifiers = staticMethod.getModifiers();
        final Set<Modifier> finalModifiers = finalMethod.getModifiers();

        assertEquals(Set.of(Modifier.PUBLIC, Modifier.ABSTRACT), methodModifiers);
        assertEquals(Set.of(Modifier.PROTECTED, Modifier.STATIC, Modifier.SYNCHRONIZED, Modifier.STRICTFP), staticModifiers);
        assertEquals(Set.of(Modifier.PRIVATE, Modifier.FINAL), finalModifiers);
    }

    @Test
    void Given_Constructor_When_GetDeclaringType_Then_ReturnsDeclaringType() {

        class TestClass {

            void method() {

            }
        }
        final TypeDefinition<TestClass> typeDefinition = mirror.reflect(TestClass.class);
        final Method<TestClass, ?> method = typeDefinition.getMethods()
                .iterator()
                .next();

        final TypeDefinition<TestClass> declaringType = method.getDeclaringType();

        assertEquals(typeDefinition, declaringType);
    }

    @Test
    void Given_Method_When_GetRawDeclaringType_Then_ReturnsDeclaringType() {

        class TestClass {

            void testMethod() {

            }

        }
        final Method<TestClass, Void> method = reflectMethod(TestClass.class, "testMethod");

        final Class<TestClass> rawDeclaringType = method.getRawDeclaringType();

        assertEquals(TestClass.class, rawDeclaringType);
    }

    @Test
    void Given_InstanceMethod_When_InvokedOnInstance_Then_InvokesMethod() {

        class TestClass {

            String arg;

            String method(final String arg) {

                this.arg = arg;
                return "return value";
            }
        }
        final Method<TestClass, String> method = reflectMethod(TestClass.class, "method");
        final TestClass instance = new TestClass();

        final String returnValue = method.invoke(instance, "arg");

        assertEquals("return value", returnValue);
        assertEquals("arg", instance.arg);
    }

    static class StaticMethodTestClass {

        static String arg;

        static String method(final String arg) {

            StaticMethodTestClass.arg = arg;
            return "return value";
        }
    }

    @Test
    void Given_StaticMethod_When_InvokedOnNull_ThenInvokesMethods() {

        StaticMethodTestClass.arg = null;
        final Method<StaticMethodTestClass, String> method = reflectMethod(StaticMethodTestClass.class, "method");

        final String returnValue = method.invoke(null, "arg");

        assertEquals("return value", returnValue);
        assertEquals("arg", StaticMethodTestClass.arg);
    }

    @Test
    void Given_StaticMethod_When_InvokedOnInstance_ThenInvokesMethods() {

        StaticMethodTestClass.arg = null;
        final Method<StaticMethodTestClass, String> method = reflectMethod(StaticMethodTestClass.class, "method");

        final String returnValue = method.invoke(new StaticMethodTestClass(), "arg");

        assertEquals("return value", returnValue);
        assertEquals("arg", StaticMethodTestClass.arg);
    }

    @Test
    void Given_Method_When_CalledWithInvalidArgumentTypes_Then_Throws() {

        class TestClass {

            void method(final String s) {

            }
        }
        final Method<TestClass, Void> method = reflectMethod(TestClass.class, "method");

        assertThrows(ClassCastException.class, () -> method.invoke(new TestClass(), new Object()));
    }

    @Test
    void Given_Method_When_InvokedWithInvalidArgumentsLength_Then_Throws() {

        class TestClass {

            void method(final String arg) {

            }
        }
        final Method<TestClass, String> method = reflectMethod(TestClass.class, "method");

        assertThrows(IllegalArgumentException.class, () -> method.invoke(new TestClass(), "too", "many", "arguments"));
        assertThrows(IllegalArgumentException.class, () -> method.invoke(new TestClass()));
    }

    @Test
    void Given_ThrowingMethod_When_Invoked_Then_Throws() {

        final RuntimeException e = new RuntimeException();
        class TestClass {

            void method() {

                throw e;
            }
        }
        final Method<TestClass, Void> method = reflectMethod(TestClass.class, "method");

        final InvocationException thrown = assertThrows(InvocationException.class, () -> method.invoke(new TestClass()));
        assertEquals(e, thrown.getTargetException());
    }

    @Test
    void Given_InstanceMethod_When_InvokedOnNull_Then_Throws() {

        class TestClass {

            void method() {

            }
        }
        final Method<TestClass, Void> method = reflectMethod(TestClass.class, "method");

        assertThrows(NullPointerException.class, () -> method.invoke(null));
    }
}
