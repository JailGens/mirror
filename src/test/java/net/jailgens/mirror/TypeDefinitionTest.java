package net.jailgens.mirror;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TypeDefinitionTest {

    Mirror mirror;

    @BeforeEach
    void setUp() {

        mirror = Mirror.builder().build();
    }

    @Test
    void Given_TypeDefinition_When_GetAnnotations_Then_ReturnsAnnotations() {

        @TestAnnotation(10)
        class TestClass {

        }
        final TypeDefinition<TestClass> typeDefinition = mirror.reflect(TestClass.class);

        final AnnotationValues annotations = typeDefinition.getAnnotations();

        assertEquals(AnnotationValues.builder()
                .value(AnnotationElement.value(TestAnnotation.class), 10)
                .build(), annotations);
    }

    @Test
    void Given_ClassTypeDefinition_When_CheckIsClass_Then_ReturnsTrue() {

        class TestClass {

        }
        final TypeDefinition<TestClass> typeDefinition = mirror.reflect(TestClass.class);

        final boolean isClass = typeDefinition.isClass();
        final boolean isInterface = typeDefinition.isInterface();

        assertTrue(isClass);
        assertFalse(isInterface);
    }

    @Test
    void Given_InterfaceTypeDefinition_When_CheckIsInterface_Then_ReturnsTrue() {

        final TypeDefinition<TestInterface> typeDefinition = mirror.reflect(TestInterface.class);

        final boolean isInterface = typeDefinition.isInterface();
        final boolean isClass = typeDefinition.isClass();

        assertTrue(isInterface);
        assertFalse(isClass);
    }

    @Test
    void Given_TypeDefinition_When_GetName_Then_ReturnsName() {

        class TestClass {

        }
        final TypeDefinition<TestClass> typeDefinition = mirror.reflect(TestClass.class);

        final String name = typeDefinition.getName();

        assertEquals("TestClass", name);
    }

    @Test
    void Given_TypeDefinition_When_GetModifiers_Then_ReturnsModifiers() {

        abstract class AbstractTestClass {

        }
        final class FinalTestClass {

        }
        final TypeDefinition<AbstractTestClass> abstractTestClassTypeDefinition =
                mirror.reflect(AbstractTestClass.class);
        final TypeDefinition<FinalTestClass> finalTestClassTypeDefinition =
                mirror.reflect(FinalTestClass.class);

        final Set<Modifier> abstractTestClassModifiers =
                abstractTestClassTypeDefinition.getModifiers();
        final Set<Modifier> finalTestClassModifiers = finalTestClassTypeDefinition.getModifiers();

        assertEquals(Set.of(Modifier.ABSTRACT), abstractTestClassModifiers);
        assertEquals(Set.of(Modifier.FINAL), finalTestClassModifiers);
    }

    @Test
    void Given_TypeDefinition_When_GetRawType_Then_ReturnsRawType() {

        class TestClass {

        }
        final TypeDefinition<TestClass> typeDefinition = mirror.reflect(TestClass.class);

        final Class<TestClass> rawType = typeDefinition.getRawType();

        assertEquals(TestClass.class, rawType);
    }

    @Test
    void Given_TypeDefinition_When_GetMembers_Then_ReturnsFieldsConstructorsAndMethods() {

        class TestClass {

            String field;

            TestClass() {

            }

            void method() {

            }
        }
        final TypeDefinition<TestClass> typeDefinition = mirror.reflect(TestClass.class);

        final Collection<Member<TestClass>> members = typeDefinition.getMembers();

        final Collection<Member<TestClass>> expectedMembers = new ArrayList<>();
        expectedMembers.addAll(typeDefinition.getFields());
        expectedMembers.addAll(typeDefinition.getConstructors());
        expectedMembers.addAll(typeDefinition.getMethods());
        assertTrue(expectedMembers.containsAll(members));
        assertTrue(members.containsAll(expectedMembers));
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface TestAnnotation {

        int value();
    }

    interface TestInterface {

    }
}
